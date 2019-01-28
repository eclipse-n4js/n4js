/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.external.libraries.update;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent;
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSStandaloneSetup;
import org.eclipse.n4js.binaries.BinariesLocatorHelper;
import org.eclipse.n4js.binaries.nodejs.NodeYarnProcessBuilder;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.external.libraries.ExternalLibraryFolderUtils;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Optional;
import com.google.inject.Injector;

/**
 * Updates the code in folder "shipped-code" in bundle "org.eclipse.n4js.external.libraries". For details see
 * {@link #updateShippedCode(Optional)}.
 */
public class UpdateShippedCode implements IWorkflowComponent {
	final static boolean SKIP_UPDATE = true;
	final static String PATH = "PATH";

	/** Name of the n4js-lang project */
	public static String N4JS_LANG_PROJECT_NAME = "n4js.lang";

	/** Name of the n4js-node project */
	public static String N4JS_NODE_PROJECT_NAME = "n4js-node";

	/** Names of files and folders *not* to be copied from "n4js-libs" to the "shipped-code" folder. */
	private static final String[] IGNORED_FILES = {
			".DS_Store",
			".Spotlight-V100",
			".Trashes",
			".git",
			".gitignore",
			".project",
	};

	@Override
	public void preInvoke() {
		// ignore
	}

	@Override
	public void invoke(IWorkflowContext ctx) {
		try {
			updateShippedCode(Optional.absent());
		} catch (IOException e) {
			throw new RuntimeException(e); // let MWE2 runner report the exception
		}
	}

	@Override
	public void postInvoke() {
		// ignore
	}

	private static String getCategoryForN4jsLibsProject(String projectName) {
		if (projectName.equals(N4JS_LANG_PROJECT_NAME)) {
			return ExternalLibrariesActivator.LANG_CATEGORY;
		} else if (projectName.contains(".mangelhaft.") || projectName.endsWith(".mangelhaft")) {
			return ExternalLibrariesActivator.MANGELHAFT_CATEGORY;
		} else {
			return ExternalLibrariesActivator.RUNTIME_CATEGORY; // the default category
		}
	}

	/**
	 * Compiles all code under top-level folder "n4js-libs" and copies it into folder "shipped-code" in bundle
	 * "org.eclipse.n4js.external.libraries".
	 *
	 * @param targetPath
	 *            path of a folder where the compiled and otherwise prepared runtime code is to be copied to; if not
	 *            present, folder "shipped-code" in bundle "org.eclipse.n4js.external.libraries" will be used.
	 */
	public static void updateShippedCode(Optional<Path> targetPath) throws IOException {
		if (SKIP_UPDATE) {
			println("==== Skipping UPDATE SHIPPED CODE ====");
			return;
		}

		println("==== Running UPDATE SHIPPED CODE ====");

		final Path n4jsRootPath = UtilN4.findN4jsRepoRootPath();
		final Path n4jsLibsRootPath = n4jsRootPath.resolve(N4JSGlobals.SHIPPED_CODE_SOURCES_FOLDER_NAME);
		final File n4jsLibsRoot = n4jsLibsRootPath.toFile();
		final Path actualTargetPath;
		if (targetPath.isPresent()) {
			actualTargetPath = targetPath.get();
		} else {
			actualTargetPath = ExternalLibrariesActivator.getShippedCodeFolderPath();
		}
		// step 1: clean then compile projects under top-level folder "n4js-libs"
		println("==== STEP 1/4: compiling code under top-level folder \"" + N4JSGlobals.N4JS_LIBS_FOLDER_NAME
				+ "\" in n4js repository:");
		cleanAndCompile(n4jsLibsRoot);
		// step 2: create/clean the "shipped-code" folder
		println("==== STEP 2/4: cleaning folder " + actualTargetPath);
		initFolder(actualTargetPath.toFile());
		// step 3: copy all projects from top-level folder "n4js-libs" to folder "shipped-code" inside our bundle
		println("==== STEP 3/4: copying all code from top-level folder \"" + N4JSGlobals.N4JS_LIBS_FOLDER_NAME
				+ "\" to target folder");
		println("    FROM: " + n4jsLibsRoot);
		println("    TO  : " + actualTargetPath);
		final File[] n4jsLibsSubfolders = n4jsLibsRoot
				.listFiles(file -> file.isDirectory());
		copyN4jsLibsToShippedCodeFolder(n4jsLibsSubfolders, actualTargetPath);
		// step 4: run "npm install" in project "n4js-node"
		// TODO let HLC resolve missing dependencies
		println("==== STEP 4/4: running \"" + N4JSGlobals.NPM_INSTALL + "\" in runtime project \""
				+ N4JS_NODE_PROJECT_NAME + "\"");
		final File n4jsNodeFolder = actualTargetPath.resolve(ExternalLibrariesActivator.RUNTIME_CATEGORY)
				.resolve(N4JS_NODE_PROJECT_NAME).toFile();

		final File n4jsNodePkgJson = n4jsNodeFolder.toPath().resolve(ExternalLibraryFolderUtils.PACKAGE_JSON).toFile();

		temporaryHackRemoveN4JSES5Dependency(n4jsNodePkgJson);
		runNpmInstall(n4jsNodeFolder);
		cleanJsonFiles(n4jsNodeFolder);

		println("==== UPDATE SHIPPED CODE finished ====");
	}

	private static void initFolder(File folder) throws IOException {
		if (folder.exists()) {
			cleanFolder(folder);
		} else {
			folder.mkdirs();
		}
	}

	private static void cleanFolder(File folder) throws IOException {
		if (folder.exists() && folder.isDirectory()) {
			for (File child : folder.listFiles()) {
				FileDeleter.delete(child);
			}
		}
	}

	private static void copyN4jsLibsToShippedCodeFolder(File[] n4jsLibsSubfolders, Path shippedCodeRootPath)
			throws IOException {
		for (File subfolder : n4jsLibsSubfolders) {
			final String projectName = subfolder.getName();
			if (projectName.contains("test")) {
				println("NOT copying project " + projectName + " (because project name contains substring \"test\")");
			} else if (projectName.contains("n4js-cli") || projectName.contains("n4js-mangelhaft-cli")
					|| projectName.contains("org.eclipse.n4js.mangelhaft.reporter.console")
					|| projectName.contains("org.eclipse.n4js.mangelhaft.reporter.xunit")) {
				println("NOT copying project " + projectName
						+ " (because this project will be published to npm registry only)");
			} else {
				println("    copying project " + projectName);
				final String category = getCategoryForN4jsLibsProject(projectName);
				final Path destPath = shippedCodeRootPath.resolve(category).resolve(projectName);
				destPath.toFile().mkdirs();
				FileCopier.copy(subfolder.toPath(), destPath, UpdateShippedCode::isFilenameAllowed);
			}
		}
	}

	private static boolean isFilenameAllowed(Path path) {
		return !org.eclipse.xtext.util.Arrays.contains(IGNORED_FILES, path.getFileName().toString());
	}

	private static void cleanAndCompile(File... foldersContainingProjectFolders) {
		final String foldersContainingProjectsStr = Stream.of(foldersContainingProjectFolders)
				.map(file -> file.getAbsolutePath())
				.collect(Collectors.joining(File.pathSeparator));
		// Clean all projects first
		final String[] cleanArgs = {
				"--clean",
				"--buildType", "allprojects",
				"--projectlocations", foldersContainingProjectsStr,
		};
		try {
			new N4jscBase().doMain(cleanArgs);
		} catch (ExitCodeException e) {
			println("ERROR: while cleaning the projects, an ExitCodeException is thrown; "
					+ "code: " + e.getExitCode() + ", "
					+ "message: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		// Then compile the projects
		final String[] args = {
				"--buildType", "allprojects",
				"--installMissingDependencies",
				"--projectlocations", foldersContainingProjectsStr
		};

		try {
			new N4jscBase().doMain(args);
		} catch (ExitCodeException e) {
			println("ERROR: headless compiler threw ExitCodeException (probably code compiled with errors); "
					+ "code: " + e.getExitCode() + ", "
					+ "message: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static void runNpmInstall(File workingDirectory) {
		// Initialize DI
		Injector injector = N4JSStandaloneSetup.doSetup();
		final BinariesLocatorHelper locator = injector.getInstance(BinariesLocatorHelper.class);
		final NodeYarnProcessBuilder nodeProcessBuilder = injector.getInstance(NodeYarnProcessBuilder.class);

		println("Running \"" + N4JSGlobals.NPM_INSTALL + "\" in folder \"" + workingDirectory + "\"");
		final String npmBinaryPath = locator.findNodePath().toString();
		if (npmBinaryPath == null || npmBinaryPath.isEmpty()) {
			println("");
			println("Could not identify location of node.");
			println("!!! IMPORTANT !!!");
			println("You have to manually run \"" + N4JSGlobals.NPM_INSTALL + "\" in folder " + workingDirectory);
			println("");
			return;
		}
		println("Identified npm path at: " + npmBinaryPath);

		try {
			String npm = npmBinaryPath + File.separator + N4JSGlobals.NPM;
			final String[] cmd = new String[] { npm, "install" };
			println("Invoking: " + String.join(" ", cmd));
			println("  in working directory: " + workingDirectory);

			ProcessBuilder pb = nodeProcessBuilder.getInstallNpmPackageProcessBuilder(workingDirectory, "", true);
			final Process p = pb.start();
			final int exitCode = p.waitFor();
			if (exitCode != 0) {
				throw new IllegalStateException("npm exited with non-zero exit code: " + exitCode);
			}
			println(N4JSGlobals.NPM_INSTALL + " finished.");
		} catch (Throwable th) {
			println("Error while running \"" + N4JSGlobals.NPM_INSTALL + "\"");
			th.printStackTrace();
			throw new RuntimeException(th);
		}
	}

	private static void cleanJsonFiles(File workingDirectory) {
		println("Cleaning of Json file started...");
		FileVisitor<Path> fileVisitor = new PackageJsonVisitor();
		try {
			Files.walkFileTree(workingDirectory.toPath(),
					fileVisitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		println("Cleaning of Json file finished.");
	}

	/** TODO: REMOVE THIS HACK when we can copy the n4js-libs with canary version to the shipped code */
	// TODO when removing this, also remove dependency to com.fasterxml.jackson from this bundle!!
	private static void temporaryHackRemoveN4JSES5Dependency(File packJson) {
		println("  Remove n4js-es5 from dependency: " + packJson);
		ObjectMapper mapper = new ObjectMapper();
		try {
			ObjectNode root = (ObjectNode) mapper.readTree(packJson);
			Iterator<Entry<String, JsonNode>> rootFieldIterator = root.fields();
			List<String> removeFields = new LinkedList<>();
			JsonNode dependenciesNode = null;
			while (rootFieldIterator.hasNext()) {
				Entry<String, JsonNode> fieldInRoot = rootFieldIterator.next();
				String name = fieldInRoot.getKey();
				if ("dependencies".equals(name)) {
					dependenciesNode = fieldInRoot.getValue();
					Iterator<Entry<String, JsonNode>> dependenciesIterator = dependenciesNode.fields();
					while (dependenciesIterator.hasNext()) {
						Entry<String, JsonNode> fieldInDependencies = dependenciesIterator.next();
						String fieldInDependenciesName = fieldInDependencies.getKey();
						if (fieldInDependenciesName.contains("n4js-es5")) {
							removeFields.add(fieldInDependenciesName);
						}
					}
				}
			}

			final StringBuilder sb = new StringBuilder();
			sb.append("    removing fields: ");
			for (String fieldName : removeFields) {
				sb.append(fieldName + " ");
				if (dependenciesNode != null) {
					((ObjectNode) dependenciesNode).remove(fieldName);
				}
			}
			println(sb.toString());

			String cleanJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			FileWriter f2 = new FileWriter(packJson, false);
			f2.write(cleanJson);
			f2.write('\n'); // note: by convention, N4JS repository uses \n as line separator (independent of OS)
			f2.close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void cleanJsonFile(File packJson) {
		println("  Cleaning: " + packJson);
		ObjectMapper mapper = new ObjectMapper();
		try {
			ObjectNode root = (ObjectNode) mapper.readTree(packJson);
			Iterator<Entry<String, JsonNode>> fieldIterator = root.fields();
			List<String> removeFields = new LinkedList<>();
			while (fieldIterator.hasNext()) {
				Entry<String, JsonNode> field = fieldIterator.next();
				String name = field.getKey();

				if (name != null && name.startsWith("_")) {
					removeFields.add(name);
				}
			}

			final StringBuilder sb = new StringBuilder();
			sb.append("    removing fields: ");
			for (String fieldName : removeFields) {
				sb.append(fieldName + " ");
				root.remove(fieldName);
			}
			println(sb.toString());

			String cleanJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			FileWriter f2 = new FileWriter(packJson, false);
			f2.write(cleanJson);
			f2.write('\n'); // note: by convention, N4JS repository uses \n as line separator (independent of OS)
			f2.close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class PackageJsonVisitor implements FileVisitor<Path> {
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			final String fileName = file.getFileName().toString();
			if (fileName.equals(N4JSGlobals.PACKAGE_JSON)) {
				cleanJsonFile(file.toFile());
				return FileVisitResult.SKIP_SUBTREE;
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
	}

	/**
	 * Don't use a log4j logger here, because:
	 * <ol>
	 * <li>the call to {@link N4jscBase#doMain(String...)} will alter the dependency injection and logger configuration,
	 * <li>we want to make sure the messages printed by via this method always show up.
	 * </ol>
	 */
	private static void println(String message) {
		System.out.println(message);
	}
}
