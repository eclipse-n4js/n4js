/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.hlc.base;

import static com.google.common.base.Preconditions.checkState;
import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_CLEAN_ERROR;
import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_COMPILE_ERROR;
import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_CONFIGURATION_ERROR;
import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_DEPENDENCY_NOT_FOUND;
import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_MODULE_TO_RUN_NOT_FOUND;
import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR;
import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_WRONG_CMDLINE_OPTIONS;
import static org.eclipse.n4js.utils.git.GitUtils.hardReset;
import static org.eclipse.n4js.utils.git.GitUtils.pull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringJoiner;

import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.n4js.N4JSRuntimeModule;
import org.eclipse.n4js.N4JSStandaloneSetup;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.binaries.nodejs.NpmrcBinary;
import org.eclipse.n4js.external.HeadlessTargetPlatformInstallLocationProvider;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.external.TypeDefinitionGitLocationProvider;
import org.eclipse.n4js.external.libraries.PackageJson;
import org.eclipse.n4js.external.libraries.TargetPlatformFactory;
import org.eclipse.n4js.generator.headless.HeadlessHelper;
import org.eclipse.n4js.generator.headless.N4HeadlessCompiler;
import org.eclipse.n4js.generator.headless.N4JSCompileException;
import org.eclipse.n4js.generator.headless.N4JSHeadlessGeneratorModule;
import org.eclipse.n4js.generator.headless.logging.ConfigurableHeadlessLogger;
import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.hlc.base.running.HeadlessRunner;
import org.eclipse.n4js.hlc.base.testing.HeadlessTester;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4mf.N4MFStandaloneSetup;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.regex.RegularExpressionStandaloneSetup;
import org.eclipse.n4js.runner.SystemLoaderInfo;
import org.eclipse.n4js.tester.CliTestTreeTransformer;
import org.eclipse.n4js.tester.TestCatalogSupplier;
import org.eclipse.n4js.tester.TestTreeTransformer;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.extension.TesterRegistry;
import org.eclipse.n4js.ts.TypeExpressionsStandaloneSetup;
import org.eclipse.n4js.ts.TypesStandaloneSetup;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.util.Modules;

/**
 * Main logic of the N4JS headless builder (a.k.a. n4jsc.jar).
 *
 * This code was moved here from class {@code N4jsc} to allow method {@link #doMain(String...)} to be called from OSGI
 * bundles (e.g. tests, MWE2 work flows).
 */
public class N4jscBase implements IApplication {

	/**
	 * Marker used to distinguish between compile-messages and runner output.
	 */
	public static final String MARKER_RUNNER_OUPTUT = "======= =======";

	/** Printing of usage and exit */
	@Option(name = "--help", aliases = "-h", usage = "print help & exit")
	// , help=true // TODO new versions(>3.5.2014) of args4j, support help-flag
	boolean help = false;

	/** Debug print of all read-in arguments */
	@Option(name = "--debug", usage = "print information about the current setup of the compiler")
	boolean debug = false;

	@Option(name = "--projectlocations", aliases = "-pl", metaVar = "directory", required = false, usage = "provide folder(s) to search for projects. If not set, the base folder of the running jvm will be taken as the location. "
			+ "Multiple folders can be given separated by the systems path-separator(: or ;). Only direct subfolders will be queried for projects.")
	String projectLocations;

	@Option(name = "--preferences", usage = "provide custom compiler preference settings stored in a *.properties file")
	File preferencesProperties;

	@Option(name = "--buildType", aliases = "-bt", metaVar = "type", usage = "provide the type to build (defaults to dontcompile). "
			+ "\n'allprojects' compiles all projects found in the projectlocations, no other argument required. "
			+ "\n'projects' interprets the arguments as projects-folders and compiles them. "
			+ "\n'singlefile' interprets the arguments as paths to N4JS-source files and compiles only those both."
			+ "\n'dontcompile' don't generate anything.")
	BuildType buildtype = BuildType.dontcompile;

	@Option(name = "--testCatalogFile", aliases = "-tc", required = false, usage = "if specified, a test catalog file will be generated to the given file location. The generated test catalog file will represent all "
			+ "tests that are available among the compiled sources. If the file does not exists at the given location, a new file will be created to "
			+ "the specified location. If a file already exists at the given location then the content of the existing file content will be replaced "
			+ "with the newly assembled test catalog. If an invalid file location is specified as the test catalog file output, then the catalog generation will fail.")
	File testCatalogFile;

	@Option(name = "--targetPlatformInstallLocation", aliases = "-tl", required = false, usage = "if specified and the target platform file is given as well, then all third party dependencies "
			+ "specified in the target platform file will be downloaded to that given location. If the target platform file is given, but the target platform install location is not specified, "
			+ "then a the compilation phase will be aborted and the execution will be interrupted."
			+ "If --targetPlatformSkipInstall is provided this parameter is ignored.")
	File targetPlatformInstallLocation;

	@Option(name = "--installMissingDependencies", aliases = "-imd", required = false, usage = "usually projects have dependencies that have to be fetched before the compilation. "
			+ "If this flag is provided, compiler will calculate missing dependencies based on the manifest files of the projects provided as input to the compilation."
			+ "Calculated missing dependencies will be fetched by Library Manager priori to the compilation.")
	boolean installMissingDependencies = false;

	@Option(name = "--keepCompiling", usage = "keep compiling - even if errors are encountered")
	boolean keepCompiling = false;

	@Option(name = "--notests", usage = "don't process test-folders")
	// , forbids = "--testonly" // TODO new versions(>3.5.2014) of arg4j allow exclusions
	boolean notests = false;

	@Option(name = "--testonly", usage = "only transpile contents of test-folders")
	// , forbids = "--notests" // TODO new versions(>3.5.2014) of arg4j allow exclusions
	boolean testonly = false;

	@Option(name = "--verbose", aliases = "-v", usage = "verbose output during build")
	boolean verbose = false;

	@Option(name = "--clean", aliases = "-c", required = false, usage = "use this flag to indicate that the output folder should be cleaned before compilation. "
			+ "If this flag is activated, the flag -bt must be activated as well. "
			+ "This flag can NOT be used in combination with -bt singlefile.")
	boolean clean = false;

	// -- --- --- - -- --- --- - -- --- --- - -- --- --- - -- --- --- - -- --- --- -
	// Options for execution (=running)
	@Option(name = "--run", aliases = "-r", metaVar = "pathToFileToRun", usage = "path to the file that should be run after compilation.")
	File runThisFile = null;

	@Option(name = "--runWith", aliases = "-rw", metaVar = "runnerId", usage = "ID of runner to use, last segment is sufficient, e.g. nodejs.")
	String runner = "nodejs";

	@Option(name = "--systemLoader", aliases = "-sl", required = false, usage = "when specified the given javascript system loader will be used "
			+ "for running the module. If not specified, the by default the System.js loader will be used. The following system "
			+ "loaders are available: sjs and cjs where sjs stands for System.js and cjs stands for Common JS.")
	String systemLoader;

	@Option(name = "--nodejsLocation", required = false, usage = "when configured then the Node.js binary located under the given absolute path "
			+ "will be used for executing modules. When specified then the absolute path of the folder that contains the Node.js binary should be "
			+ "specified. If not set, then the default /usr/local/bin (on Unix systems) or C:\\Program Files\\nodejs (on Windows systems) location "
			+ "will be used to look for the Node.js binary. ")
	File nodeJsBinaryRoot;

	@Option(name = "--npmrcRootLocation", required = false, usage = "when configured then the nprc setting used from given path"
			+ "will be used for installing npm packages modules. When specified then the absolute path of the folder that contains the '.npmrc' file should be "
			+ "specified. If not set, then the default to value specified by 'user.home' property value returned by java.lang.System ")
	File npmrcRoot;

	@Option(name = "--listRunners", aliases = "-lr", usage = "show list of available runners.")
	boolean listRunners = false;

	@Option(name = "--test", aliases = "-t", metaVar = "path", usage = "path must point to a project, folder, or file containing tests.")
	File testThisLocation = null;

	@Option(name = "--testReportRoot", required = false, usage = "when provided, it is expected to be directory in which test report will be written."
			+ "If test report already exists in that location it is removed overwritten. If not provided, test report generation is skipped.")
	File testReportRoot;

	@Option(name = "--testWith", aliases = "-tw", metaVar = "testerId", usage = "ID of tester to use, last segment is sufficient, e.g. nodejs_mangelhaft")
	String tester = "nodejs_mangelhaft";

	@Option(name = "--listTesters", aliases = "-lt", usage = "show list of available testers")
	boolean listTesters = false;

	@Option(name = "--implementationId", aliases = "-impl", metaVar = "ID", required = false, usage = "if there are API projects among the dependencies of the module to run, this specifies the ID of the "
			+ "implementation to use; only required when running/testing if there are API projects and there exists "
			+ "more than one implementation.")
	String implementationId = null;

	// -- --- --- - -- --- --- - -- --- --- - -- --- --- - -- --- --- - -- --- --- -
	// Special debugging features, hidden from normal usage
	@Option(name = "--log",
			// no usage, do not show in help
			required = false)
	boolean log = false;

	@Option(name = "--logfile",
			// no usage, do not show in help
			required = false)
	String logFile = "n4jsc.log";

	/**
	 * Catch all last arguments as files. The actual meaning of these files depends on the {@link #buildtype} (-bt)
	 * option
	 */
	@Argument(multiValued = true, usage = "filename of source (or project, see -bt) to compile")
	List<File> srcFiles = new ArrayList<>();

	@Inject
	private N4HeadlessCompiler headless;

	@Inject
	private HeadlessRunner headlessRunner;

	@Inject
	private HeadlessTester headlessTester;

	@Inject
	private FileBasedWorkspace n4jsFileBasedWorkspace;

	@Inject
	private TestCatalogSupplier testCatalogSupplier;

	@Inject
	private TargetPlatformInstallLocationProvider installLocationProvider;

	@Inject
	private LibraryManager npmManager;

	@Inject
	private TesterRegistry testerRegistry;

	@Inject
	private Provider<NodeJsBinary> nodeJsBinaryProvider;

	@Inject
	private Provider<NpmBinary> npmBinaryProvider;

	@Inject
	private Provider<NpmrcBinary> npmrcBinaryProvider;

	@Inject
	private BinariesPreferenceStore binariesPreferenceStore;

	@Inject
	private TypeDefinitionGitLocationProvider gitLocationProvider;

	@Inject
	private HeadlessExtensionRegistrationHelper headlessExtensionRegistrationHelper;

	@Inject
	private DependenciesHelper dependencyHelper;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		int exitCode;
		try {
			// TODO get args from application context, https://stackoverflow.com/a/17919860/52564
			String[] args = new String[] { "--help" };
			SuccessExitStatus success = new N4jscBase().doMain(args);
			exitCode = success.code;
		} catch (ExitCodeException e) {
			exitCode = e.getExitCode();
			System.err
					.println(e.getMessage() + " exitcode: " + exitCode + e.explanationOfExitCode());
		}
		System.out.flush();
		System.err.flush();
		return new Integer(exitCode);
	}

	@Override
	public void stop() {
		// nothing to do
	}

	/**
	 * POJO style entry point to start the compiler.
	 *
	 * @param args
	 *            the parameters.
	 */
	public static void main(String[] args) {
		int exitCode;
		try {
			SuccessExitStatus success = new N4jscBase().doMain(args);
			exitCode = success.code;
		} catch (ExitCodeException e) {
			exitCode = e.getExitCode();
			System.err
					.println(e.getMessage() + " exitcode: " + exitCode + e.explanationOfExitCode());
		}
		System.out.flush();
		System.err.flush();
		System.exit(exitCode);
	}

	/**
	 * This method can be used when the headless builder (a.k.a. n4jsc.jar) is to be invoked programmatically from
	 * outside bundle {@code org.eclipse.n4js.hlc}, e.g. in tests or in MWE2 work flows.
	 * <p>
	 * Obviously, this method is not intended to be used in the IDE product (this bundle should not be included in the
	 * IDE product anyway).
	 *
	 * @param args
	 *            parameters from command-line
	 * @throws ExitCodeException
	 *             in case of errors.
	 *
	 * @return SuccessExitStatus {@link SuccessExitStatus#INSTANCE success status} when everything went fine
	 */
	public SuccessExitStatus doMain(String... args) throws ExitCodeException {
		try {

			CmdLineParser parser = new CmdLineParser(this);
			parser.getProperties().withUsageWidth(130);

			try {

				parser.parseArgument(args);

			} catch (CmdLineException e) {
				System.err.println(e.getMessage());
				printExtendedUsage(parser, System.err);
				// exit with error-code 1
				throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS);
			}

			// debug before help, shortcut for check-settings without running
			if (debug) {
				printDebugLines();
			} else {
				// Reconfigure Logging to be quiet:
				Logger.getRootLogger().removeAllAppenders();
				Logger.getRootLogger().addAppender(new NullAppender());
			}

			// Check help option:
			if (help) {
				// print and exit:
				printExtendedUsage(parser, System.out);
				return SuccessExitStatus.INSTANCE;
			}

			// Injection should not be called before making sure the argument parsing successfully finished. Such as
			// help.
			initInjection(refProperties());

			// Register extensions manually
			headlessExtensionRegistrationHelper.registerExtensions();

			if (listRunners) {
				printAvailableRunners(System.out);
				return SuccessExitStatus.INSTANCE;
			}
			if (listTesters) {
				printAvailableTesters(System.out);
				return SuccessExitStatus.INSTANCE;
			}

			EnumSet<BuildType> noSrcRequired = EnumSet.of(BuildType.allprojects, BuildType.dontcompile);
			// missing arguments (only build all doesn't require one OR if nothing will be compiled).
			if (srcFiles.isEmpty() && (!noSrcRequired.contains(buildtype))) {
				// print and exit:
				System.out.println("Missing arguments.");
				printExtendedUsage(parser, System.out);
				throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS);
			}

			// check missing runner
			if ((runThisFile != null) && isNotGiven(runner)) {
				// print and exit:
				System.out.println("Missing arguments for running: runner must be named with option -rw");
				printExtendedUsage(parser, System.out);
				throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS);
			}
			// check missing tester
			if ((testThisLocation != null) && isNotGiven(tester)) {
				// print and exit:
				System.out.println("Missing arguments for testing: tester must be named with option -tw");
				printExtendedUsage(parser, System.out);
				throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS);
			}
			// check runner/tester conflict
			if (runThisFile != null && testThisLocation != null) {
				// print and exit:
				System.out.println("Conflicting arguments: must not provide both -r and -t");
				printExtendedUsage(parser, System.out);
				throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS);
			}

			if (null != testCatalogFile) {

				if (testCatalogFile.exists()) {
					if (testCatalogFile.isDirectory()) {
						final String msg = "The location of the test catalog file points to a directory and not to a file. "
								+ testCatalogFile;
						System.out.println(msg);
						printExtendedUsage(parser, System.out);
						throw new ExitCodeException(EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR, msg);
					}

					if (!testCatalogFile.delete()) {
						final String msg = "Error while deleting existing test catalog file. " + testCatalogFile;
						System.out.println(msg);
						printExtendedUsage(parser, System.out);
						throw new ExitCodeException(EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR, msg);
					}
				}

				try {
					if (!testCatalogFile.createNewFile()) {
						final String msg = "Error while creating test catalog file at: " + testCatalogFile;
						System.out.println(msg);
						printExtendedUsage(parser, System.out);
						throw new ExitCodeException(EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR, msg);
					}
				} catch (final IOException e) {
					System.out.println("Error while creating test catalog file. " + e.getMessage());
					printExtendedUsage(parser, System.out);
					throw new ExitCodeException(EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR, e);
				}

				if (!testCatalogFile.exists() || !testCatalogFile.canWrite()) {
					final String msg = "Cannot access test catalog file at: " + testCatalogFile;
					System.out.println(msg);
					printExtendedUsage(parser, System.out);
					throw new ExitCodeException(EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR, msg);
				}

			}

			if (clean && ((buildtype == BuildType.dontcompile) || (buildtype == BuildType.singlefile))) {
				String msg = "";
				if (buildtype == BuildType.dontcompile) {
					msg = "The flag -bt must be specified when --clean/-c is activated";
				} else if (buildtype == BuildType.singlefile) {
					msg = "The flag --clean/-c flag can not be used in combination with -bt singlefile";
				}
				System.out.println(msg);
				printExtendedUsage(parser, System.out);
				throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS);
			}

			final SystemLoaderInfo systemLoaderType = SystemLoaderInfo.fromString(systemLoader);
			if (null == systemLoaderType) {
				systemLoader = SystemLoaderInfo.SYSTEM_JS.getId();
			}

			checkTargetPlatformConfigurations();
			if (null != nodeJsBinaryRoot) {
				binariesPreferenceStore.setPath(nodeJsBinaryProvider.get(), nodeJsBinaryRoot.toURI());
				binariesPreferenceStore.save();
			}
			if (npmrcRoot != null) {
				binariesPreferenceStore.setPath(npmrcBinaryProvider.get(), npmrcRoot.toURI());
				binariesPreferenceStore.save();
			}

			validateBinaries();
			cloneGitRepositoryAndInstallNpmPackages();

			if (clean) {
				// clean without compiling anything.
				clean();
			} else {
				if (installMissingDependencies) {
					Map<String, String> dependencies = dependencyHelper
							.discoverMissingDependencies(projectLocations, srcFiles);
					if (verbose) {
						System.out.println("installing missing dependencies:");
						dependencies.forEach((name, version) -> {
							System.out.println("  # " + name + "@" + version);
						});
					}

					IStatus status = npmManager.installNPMs(dependencies, new NullProgressMonitor());
					if (!status.isOK())
						if (keepCompiling)
							warn(status.getMessage());
						else
							throw new ExitCodeException(EXITCODE_DEPENDENCY_NOT_FOUND,
									"Cannot install dependencies.");
				}

				// run and dispatch.
				doCompileAndTestAndRun();
			}
		} catch (ExitCodeException e) {
			dumpThrowable(e);
			throw e;
		} finally {
			targetPlatformInstallLocation = null;
		}

		// did everything there was to be done
		return SuccessExitStatus.INSTANCE;
	}

	private void clean() throws ExitCodeException {
		if (buildtype == BuildType.dontcompile) {
			throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS,
					"The flag -bt must be specified when cleaning");
		}
		try {
			switch (buildtype) {
			case projects:
				headless.cleanProjects(srcFiles);
				break;

			case allprojects:
				if (projectLocations == null) {
					throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS,
							"Require option for projectlocations to clean all projects.");
				} else {
					if (!srcFiles.isEmpty()) {
						warn("The list of projects are ignored because we are cleaning all projects in  "
								+ projectLocations);
					}
					List<File> toClean = new ArrayList<>();
					toClean.addAll(ProjectLocationsUtil.getTargetPlatformWritableDir(installLocationProvider));
					toClean.addAll(ProjectLocationsUtil.convertToFiles(projectLocations));
					headless.cleanProjectsInSearchPath(toClean);
				}
				break;
			default:
				// Do nothing
			}
		} catch (N4JSCompileException e) {
			// dump all information to error-stream.
			e.userDump(System.err);
			throw new ExitCodeException(EXITCODE_CLEAN_ERROR);
		}
	}

	private void validateBinaries() throws ExitCodeException {
		if (installMissingDependencies || runThisFile != null || testThisLocation != null) {
			IStatus status = nodeJsBinaryProvider.get().validate();
			if (!status.isOK()) {
				System.out.println(status.getMessage());
				if (null != status.getException()) {
					dumpThrowable(status.getException());
				}
				throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR, status.getMessage(), status.getException());
			}
		}
		if (installMissingDependencies) {
			IStatus status = npmBinaryProvider.get().validate();
			if (!status.isOK()) {
				System.out.println(status.getMessage());
				if (null != status.getException()) {
					dumpThrowable(status.getException());
				}
				throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR, status.getMessage(), status.getException());
			}
		}
	}

	/** depends on the checks done in {@link #checkTargetPlatformConfigurations} */
	private void cloneGitRepositoryAndInstallNpmPackages() throws ExitCodeException {
		checkState(installLocationProvider instanceof HeadlessTargetPlatformInstallLocationProvider);
		HeadlessTargetPlatformInstallLocationProvider locationProvider = (HeadlessTargetPlatformInstallLocationProvider) installLocationProvider;

		if (!installMissingDependencies) {
			if (verbose)
				System.out.println("Skipping scanning and installation of dependencies.");
			return;
		}

		// pull n4jsd to install location
		java.net.URI gitRepositoryLocation = locationProvider.getTargetPlatformLocalGitRepositoryLocation();
		Path localClonePath = new File(gitRepositoryLocation).toPath();
		hardReset(gitLocationProvider.getGitLocation().getRepositoryRemoteURL(), localClonePath,
				gitLocationProvider.getGitLocation().getRemoteBranch(), true);
		pull(localClonePath);

		// generate n4tp file for NpmManager to use
		PackageJson packageJson = TargetPlatformFactory.createN4Default();
		java.net.URI platformLocation = locationProvider.getTargetPlatformInstallLocation();
		File packageJsonFile = new File(new File(platformLocation), PackageJson.PACKAGE_JSON);
		try {
			if (!packageJsonFile.exists()) {
				packageJsonFile.createNewFile();
			}
			try (PrintWriter pw = new PrintWriter(packageJsonFile)) {
				pw.write(packageJson.toString());
				pw.flush();
				locationProvider.setTargetPlatformFileLocation(packageJsonFile.toURI());

			}
		} catch (IOException e) {
			throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR,
					"Error while consuming target platform file.", e);
		}
	}

	/**
	 * Checks state of target platform location is valid. If needed temp location is used. Valid location is saved in
	 * {@link TargetPlatformInstallLocationProvider}
	 *
	 * @throws ExitCodeException
	 *             if configuration is inconsistent or cannot be fixed.
	 */
	private void checkTargetPlatformConfigurations() throws ExitCodeException {
		HeadlessTargetPlatformInstallLocationProvider locationProvider = (HeadlessTargetPlatformInstallLocationProvider) installLocationProvider;
		if (targetPlatformInstallLocation != null) {
			// validate and save existing one

			if (!targetPlatformInstallLocation.exists()) {
				try {
					checkState(targetPlatformInstallLocation.mkdirs());
				} catch (Exception e) {
					throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR,
							"Target platform install location cannot be created at: "
									+ targetPlatformInstallLocation + ".",
							e);
				}
			} else {
				if (!targetPlatformInstallLocation.isDirectory()) {
					throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR,
							"Target platform install location does not point to a directory at: "
									+ targetPlatformInstallLocation + ".");
				}
				if (!targetPlatformInstallLocation.canWrite()) {
					throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR,
							"Target platform install location cannot be accessed at: " + targetPlatformInstallLocation
									+ ".");
				}

				if (clean) {
					try {
						FileDeleter.delete(targetPlatformInstallLocation);
					} catch (Exception e) {
						throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR,
								"Existing target platform install location cannot be cleared at: "
										+ targetPlatformInstallLocation + ".",
								e);
					}
					try {
						checkState(targetPlatformInstallLocation.mkdirs());
					} catch (Exception e) {
						throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR,
								"Target platform install location cannot be created at: "
										+ targetPlatformInstallLocation + ".",
								e);
					}
				}

			}

			locationProvider.setTargetPlatformInstallLocation(targetPlatformInstallLocation.toURI());
		} else {
			if (verbose)
				System.out.println("Setting up tmp location for dependencies.");

			try {
				locationProvider.configureWithTempFolders();
			} catch (IOException e1) {
				throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR,
						"Error while creating temp locations for dependencies.", e1);
			}
		}

	}

	/**
	 * Dumps available runners to a stream
	 *
	 * @param out
	 *            stream to print to
	 */
	private void printAvailableRunners(PrintStream out) {
		if (testerRegistry.getDescriptors().isEmpty())
			out.println("No runners found.");
		else {
			StringJoiner sj = new StringJoiner("\n\t");
			sj.add("Available runners are:");
			testerRegistry.getDescriptors().values().forEach(td -> sj.add(td.getId()));
			out.println(sj);
		}
	}

	/**
	 * Dumps available testers to a stream
	 *
	 * @param out
	 *            stream to print to
	 */
	private void printAvailableTesters(PrintStream out) {
		if (testerRegistry.getDescriptors().isEmpty())
			out.println("No testers found.");
		else {
			StringJoiner sj = new StringJoiner("\n\t");
			sj.add("Available testers are:");
			testerRegistry.getDescriptors().values().forEach(td -> sj.add(td.getId()));
			out.println(sj);
		}
	}

	/**
	 * @param string
	 *            to check
	 * @return true if null or just whitespaces.
	 */
	private static boolean isNotGiven(String string) {
		return string == null || string.trim().length() == 0;
	}

	/** Creates the injector for the test and injects all fields with the initialized injector. */
	private void initInjection(Properties properties) {

		// STEP 1: set up language N4JS

		// the following is doing roughly the same as N4JSStandaloneSetup.doSetup(), but is using a custom-built
		// Guice module for injector creation:

		TypesPackage.eINSTANCE.getNsURI();
		TypeRefsPackage.eINSTANCE.getNsURI();
		N4JSPackage.eINSTANCE.getNsURI();
		N4mfPackage.eINSTANCE.getNsURI();
		XMLTypePackage.eINSTANCE.getNsURI();

		// combine all modules for N4JSC
		final Module combinedModule = Modules.combine(new N4JSRuntimeModule(), new TesterModule(),
				new N4JSHeadlessGeneratorModule(properties));

		// override with customized bindings
		final Module overridenModule = Modules.override(combinedModule).with(binder -> {
			binder.bind(TestTreeTransformer.class)
					.to(CliTestTreeTransformer.class);
			binder.bind(IHeadlessLogger.class)
					.toInstance(new ConfigurableHeadlessLogger(verbose, debug));
		});

		RegularExpressionStandaloneSetup.doSetup();
		TypesStandaloneSetup.doSetup();
		N4MFStandaloneSetup.doSetup();
		TypeExpressionsStandaloneSetup.doSetup();

		final Injector injector = Guice.createInjector(overridenModule);
		new N4JSStandaloneSetup().register(injector);
		injector.injectMembers(this);
	}

	/**
	 * @param parser
	 *            cli
	 * @param ps
	 *            stream to write to
	 */
	private static void printExtendedUsage(CmdLineParser parser, PrintStream ps) {
		ps.println("Usage: java -jar n4jsc.jar [options] FILE FILE ...");
		parser.printUsage(ps);

	}

	/**
	 * Core algorithm to call the invoke compilation testing and running.
	 *
	 * @throws ExitCodeException
	 *             in error cases.
	 */
	private void doCompileAndTestAndRun() throws ExitCodeException {
		if (debug) {
			System.out.println("N4JS compiling...");
		}

		try {
			compile();
			writeTestCatalog();
			testAndRun();

		} finally {
			cleanTemporaryArtifacts();
		}

		if (debug) {
			System.out.println("... done.");
		}
	}

	/** dispatch to proper build method based on {@link #buildtype} */
	private void compile() throws ExitCodeException {
		try {
			switch (buildtype) {
			case singlefile:
				compileArgumentsAsSingleFiles();
				break;
			case projects:
				compileArgumentsAsProjects();
				break;
			case allprojects:
				compileAllProjects();
				break;
			case dontcompile:
			default:
				registerProjects();
			}
		} catch (N4JSCompileException e) {
			// dump all information to error-stream.
			e.userDump(System.err);
			throw new ExitCodeException(EXITCODE_COMPILE_ERROR);
		}
	}

	/** writes test catalog based on {@link #testCatalogFile} */
	private void writeTestCatalog() throws ExitCodeException {
		if (null != testCatalogFile) {
			final String catalog = testCatalogSupplier.get();
			try (final FileOutputStream fos = new FileOutputStream(testCatalogFile)) {
				fos.write(catalog.getBytes());
				fos.flush();
			} catch (IOException e) {
				System.out.println("Error while writing test catalog file at: " + testCatalogFile);
				throw new ExitCodeException(EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR);
			}
		}
	}

	/** triggers runners and testers based on {@link #testThisLocation} and {@link #runThisFile} */
	private void testAndRun() throws ExitCodeException {
		if (testThisLocation != null) {
			if (buildtype != BuildType.dontcompile) {
				flushAndIinsertMarkerInOutputs();
			}
			headlessTester.runTests(tester, implementationId, checkLocationToTest(), testReportRoot);
		}

		if (runThisFile != null) {
			if (buildtype != BuildType.dontcompile) {
				flushAndIinsertMarkerInOutputs();
			}
			headlessRunner.startRunner(runner, implementationId, systemLoader, checkFileToRun(),
					new File(installLocationProvider.getTargetPlatformInstallLocation()));
		}
	}

	/** In some cases compiler is creating files and folders in temp locations. This method deletes those leftovers. */
	private void cleanTemporaryArtifacts() {
		if (installLocationProvider != null) {
			HeadlessTargetPlatformInstallLocationProvider locationProvider = (HeadlessTargetPlatformInstallLocationProvider) installLocationProvider;
			// TODO GH-521 reset state for HLC tests
			final java.net.URI uri = locationProvider.getTempRoot();
			locationProvider.resetState();
			if (uri != null) {
				File tempInstallToClean = new File(uri);
				try {
					if (tempInstallToClean.exists())
						FileDeleter.delete(tempInstallToClean);
				} catch (IOException e) {
					warn("Cannot clean temp install locations " + tempInstallToClean);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Inserts a Marker
	 */
	private static void flushAndIinsertMarkerInOutputs() {
		System.out.flush();
		System.err.flush();
		System.out.println(MARKER_RUNNER_OUPTUT);
		System.out.flush();
	}

	/**
	 * Check if the file is there.
	 *
	 * @return URI of the location to be executed
	 * @throws ExitCodeException
	 *             if module is missing
	 */
	private URI checkFileToRun() throws ExitCodeException {
		if (runThisFile == null || !runThisFile.exists()) {
			throw new ExitCodeException(EXITCODE_MODULE_TO_RUN_NOT_FOUND);
		}

		return HlcFileUtils.fileToURI(runThisFile);
	}

	/**
	 * Check if the project, folder, or file is there.
	 *
	 * @return URI of the location to be tested
	 * @throws ExitCodeException
	 *             if missing
	 */
	private URI checkLocationToTest() throws ExitCodeException {
		if (testThisLocation == null || !testThisLocation.exists()) {
			throw new ExitCodeException(EXITCODE_MODULE_TO_RUN_NOT_FOUND);
		}
		return HlcFileUtils.fileToURI(testThisLocation);
	}

	/**
	 * Compile type: single listed files.
	 *
	 * @throws ExitCodeException
	 *             in cases of wrong configuration
	 * @throws N4JSCompileException
	 *             signaling compile-errors.
	 */
	private void compileArgumentsAsSingleFiles() throws ExitCodeException, N4JSCompileException {
		srcFiles.stream().forEach(HlcFileUtils::isExistingReadibleFile);

		List<File> toBuild = new ArrayList<>();
		toBuild.addAll(ProjectLocationsUtil.getTargetPlatformWritableDir(installLocationProvider));

		if (projectLocations != null)
			toBuild.addAll(ProjectLocationsUtil.convertToFiles(projectLocations));

		headless.compileSingleFiles(toBuild, srcFiles);
	}

	/**
	 * Compile type: single projects
	 *
	 * @throws ExitCodeException
	 *             in cases of wrong configuration
	 * @throws N4JSCompileException
	 *             in error cases
	 */
	private void compileArgumentsAsProjects() throws ExitCodeException, N4JSCompileException {
		List<File> toBuild = new ArrayList<>();
		toBuild.addAll(ProjectLocationsUtil.getTargetPlatformWritableDir(installLocationProvider));

		if (projectLocations != null)
			toBuild.addAll(ProjectLocationsUtil.convertToFiles(projectLocations));

		headless.compileProjects(toBuild, srcFiles);
	}

	/**
	 * Compile type: all projects.
	 *
	 * @throws N4JSCompileException
	 *             in error cases
	 * @throws ExitCodeException
	 *             indicating cmdline parameters
	 */
	private void compileAllProjects() throws N4JSCompileException, ExitCodeException {
		if (projectLocations == null)
			throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS,
					"Require option for projectlocations to compile all projects.");

		if (!srcFiles.isEmpty()) {
			warn("The list of source files is obsolete for built all projects. The following will be ignored: "
					+ Joiner.on(", ").join(srcFiles));
		}

		List<File> toBuild = new ArrayList<>();
		toBuild.addAll(ProjectLocationsUtil.getTargetPlatformWritableDir(installLocationProvider));
		toBuild.addAll(ProjectLocationsUtil.convertToFiles(projectLocations));
		headless.compileAllProjects(toBuild);

	}

	/**
	 * Configure the injected file based workspace in order to run (called if no compile precedes the run)
	 */
	private void registerProjects() throws ExitCodeException, N4JSCompileException {
		if (projectLocations == null)
			throw new ExitCodeException(EXITCODE_WRONG_CMDLINE_OPTIONS,
					"Require option for projectlocations.");

		HeadlessHelper.registerProjects(ProjectLocationsUtil.getTargetPlatformWritableDir(installLocationProvider),
				n4jsFileBasedWorkspace);
		HeadlessHelper.registerProjects(ProjectLocationsUtil.convertToFiles(projectLocations), n4jsFileBasedWorkspace);
	}

	/**
	 * Headless compiler setup after parsing the arguments for the proper properties.
	 *
	 * @return ready to use instance of {@link N4HeadlessCompiler}
	 */
	@Inject
	private N4HeadlessCompiler configHeadlessCompiler() {
		headless.setKeepOnCompiling(keepCompiling);
		headless.setCompileSourceCode(!testonly);
		headless.setProcessTestCode(!notests);
		if (log) {
			headless.setLogFile(logFile);
		}
		return headless;
	}

	/**
	 * Print to error stream.
	 *
	 * @param msg
	 *            message
	 */
	protected static void warn(String msg) {
		if (msg == null)
			return;
		System.err.println(msg);
	}

	/**
	 * @return Properties or null
	 * @throws ExitCodeException
	 *             in cases of wrong config
	 */
	private Properties refProperties() throws ExitCodeException {
		if (preferencesProperties == null)
			return null;

		Properties ret = new Properties();
		try (FileInputStream stream = new FileInputStream(preferencesProperties)) {
			ret.load(stream);
		} catch (Exception e) {
			throw new ExitCodeException(EXITCODE_CONFIGURATION_ERROR,
					"Cannot load preference-properties from given file " + preferencesProperties, e);
		}
		return ret;
	}

	/**
	 * Dumping all options to screen.
	 */
	private void printDebugLines() {
		final char NL = '\n';
		StringBuffer sb = new StringBuffer();
		sb.append("N4jsc.options=").append(NL) //
				.append("debug=").append(debug).append(NL) //
				.append("runner=").append(runner).append(NL) //
				.append("listRunners=").append(listRunners).append(NL) //
				.append("runThisFile=").append(runThisFile).append(NL) //
				.append("tester=").append(tester).append(NL) //
				.append("listTesters=").append(listTesters).append(NL) //
				.append("testThisLocation=").append(testThisLocation).append(NL) //
				.append("preferencesProperties=").append(preferencesProperties).append(NL) //
				.append("projectLocations=").append(projectLocations).append(NL) //
				.append("type=").append(buildtype).append(NL)
				.append("testCatalogFile=").append(testCatalogFile).append(NL) //
				.append("srcFiles=");
		sb.append(srcFiles.stream()//
				.map(f -> f.toString())//
				.reduce((a, b) -> a + ", " + b)//
				.orElse("ø")).append(NL);
		sb.append("testonly=").append(testonly).append(NL) //
				.append("notests=").append(notests).append(NL) //
				.append("verbose=").append(verbose).append(NL);
		sb.append("Current execution directory = " + new File(".").getAbsolutePath());

		System.out.println(sb.toString());
	}

	/**
	 * Captures all info from throwable into string and then prints it into {@link System#err} - decreases interleaving
	 * of different writes.
	 */
	private void dumpThrowable(Throwable throwable) {
		System.err.println(Throwables.getStackTraceAsString(throwable));
	}
}
