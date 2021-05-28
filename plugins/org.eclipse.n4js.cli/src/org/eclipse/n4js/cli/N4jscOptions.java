/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4JSCmdLineParser.N4JSBooleanOptionHandler;
import org.eclipse.n4js.cli.N4JSCmdLineParser.N4JSFileOptionHandler;
import org.eclipse.n4js.cli.N4JSCmdLineParser.N4JSIntOptionHandler;
import org.eclipse.n4js.cli.N4JSCmdLineParser.N4JSStringOptionHandler;
import org.eclipse.n4js.cli.N4JSCmdLineParser.N4JSSubCommandHandler;
import org.eclipse.n4js.cli.N4JSCmdLineParser.ParsedOption;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.NamedOptionDef;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommands;

import com.google.common.base.Preconditions;

/**
 */
public class N4jscOptions {

	/**
	 * Environment variable name to be used to specify the path of the performance report that is saved after running
	 * the headless compiler.
	 *
	 * If this environment variable is set, the headless compiler will always enable performance data collection,
	 * regardless of the parameter {@link AbstractCompileRelatedOptions#performanceReport}.
	 */
	public static final String N4JSC_PERFORMANCE_REPORT_ENV = "N4JSC_PERFORMANCE_REPORT";

	/** Marker used to distinguish between compile-messages and runner output. */
	public static final String MARKER_RUNNER_OUPTUT = "======= =======";

	/** Usage information template. */
	public static final String USAGE_TEMPLATE = "Usage: n4jsc %s%s [OPTION(s)]";
	/** Usage information. */
	public static final String USAGE = getUsage(new N4jscOptions());

	/** @return the usage string respecting the goal specified in the given options */
	public static String getUsage(N4jscOptions options) {
		String goal = options.options.isImplicitGoal() ? "[GOAL]" : options.getGoal().name();
		String dir = options.options.getDir() == null ? "" : " [DIR]";
		String usage = String.format(USAGE_TEMPLATE, goal, dir);
		return usage;
	}

	static abstract class AbstractOptions {

		abstract N4jscGoal getGoal();

		abstract AbstractOptions newInstance();

		void setDir(@SuppressWarnings("unused") File file) {
			// if necessary, overwrite this
		}

		File getDir() {
			return null;
		}

		boolean isImplicitGoal() {
			return false;
		}

		/** @return a string that lists all relevant settings of n4jsc.jar */
		public String toSettingsString() {
			String s = "";
			s += "\n  performanceReport=" + performanceReport;
			s += "\n  performanceKey=" + performanceKey;
			s += "\n  showSetup=" + showSetup;
			s += "\n  verbose=" + verbose;
			s += "\n  log=" + log;
			s += "\n  logFile=" + logFile;
			s += "\n  version=" + version;
			s += "\n  help=" + help;
			return s;
		}

		@Option(name = "--performanceReport", aliases = "-pr", //
				hidden = true, //
				usage = "enables performance data collection and specifies the path and name of the performance report. "
						+ "A date/time stamp will appended to the file name. If the file name ends in \".csv\", CSV file format will "
						+ "be emitted; otherwise a human-readable format is used.", //
				handler = N4JSFileOptionHandler.class)
		File performanceReport = new File("performance-report.txt");

		@Option(name = "--performanceKey", aliases = "-pk", //
				hidden = true, //
				usage = "enables performance data collection and specifies the key of the data collector whose performance data "
						+ "will be saved in the performance report. An asterisk may be used to emit data of all root data collectors (not "
						+ "supported for CSV output).", //
				handler = N4JSStringOptionHandler.class)
		String performanceKey = N4JSDataCollectors.dcBuild.getId();

		@Option(name = "--showSetup", usage = "prints n4jsc setup", //
				handler = N4JSBooleanOptionHandler.class)
		boolean showSetup = false;

		@Option(name = "--verbose", usage = "enables verbose output", //
				handler = N4JSBooleanOptionHandler.class)
		boolean verbose = false;

		@Option(name = "--log", hidden = true, usage = "", //
				handler = N4JSBooleanOptionHandler.class)
		boolean log = false;

		@Option(name = "--logfile", hidden = true, usage = "specifies the log file name", //
				handler = N4JSStringOptionHandler.class)
		String logFile = "n4jsc.log";

		@Option(name = "--version", aliases = "-v", usage = "prints version and exits", //
				handler = N4JSBooleanOptionHandler.class, //
				help = true)
		boolean version = false;

		@Option(name = "--help", aliases = "-h", usage = "prints help and exits. Define a goal for goal-specific help.", //
				handler = N4JSBooleanOptionHandler.class, //
				help = true)
		boolean help = false;
	}

	/** This class defines option fields for compile related commands. */
	static abstract public class AbstractCompileRelatedOptions extends AbstractOptions {

		@Override
		public String toSettingsString() {
			String s = super.toSettingsString();
			s += "\n  noTests=" + noTests;
			s += "\n  testOnly=" + testOnly;
			s += "\n  maxErrs=" + maxErrs;
			s += "\n  maxWarns=" + maxWarns;
			s += "\n  clean=" + clean;
			s += "\n  noPersist=" + noPersist;
			return s;
		}

		@Option(name = "--noTests", //
				forbids = "--testOnly", //
				usage = "don't process test folders", //
				handler = N4JSBooleanOptionHandler.class)
		boolean noTests = false;

		@Option(name = "--testOnly", //
				forbids = "--noTests", //
				usage = "only transpile test folders", //
				handler = N4JSBooleanOptionHandler.class)
		boolean testOnly = false;

		@Option(name = "--maxErrs", //
				usage = "set the maximum number of errors to print", //
				handler = N4JSIntOptionHandler.class)
		int maxErrs = 0;

		@Option(name = "--maxWarns", //
				usage = "set the maximum number of warnings to print", //
				handler = N4JSIntOptionHandler.class)
		int maxWarns = 0;

		@Option(name = "--clean", aliases = "-c", //
				usage = "clean output folders at start", //
				handler = N4JSBooleanOptionHandler.class)
		boolean clean = false;

		@Option(name = "--noPersist", aliases = "-np", //
				usage = "disable persisting of type index to disk.", //
				handler = N4JSBooleanOptionHandler.class)
		boolean noPersist = false;
	}

	/**
	 * This class is necessary to encapsulate the option fields.
	 * <p>
	 * Note that:
	 * <ul>
	 * <li/>args4j does not allow final fields, and that
	 * <li/>Eclipse adds the 'final' modifier to all effectively final private fields. (Can only be disabled globally)
	 * </ul>
	 */
	static public class ImplicitCompileOptions extends AbstractCompileRelatedOptions {
		@Override
		AbstractOptions newInstance() {
			return new ImplicitCompileOptions();
		}

		@Override
		N4jscGoal getGoal() {
			return N4jscGoal.compile;
		}

		@Override
		boolean isImplicitGoal() {
			return true;
		}

		@Override
		File getDir() {
			return dir;
		}

		@Override
		void setDir(File file) {
			dir = file;
		}

		@Argument(metaVar = "GOAL", multiValued = false, index = 0, required = false, //
				usage = "Goals of n4jsc (default: compile)" + "\n" +
						"  Compile src folders" + "\n" +
						"  Clean output folders and type index" + "\n" +
						"  Start LSP server" + "\n" +
						// not implemented:
						// " Start daemon to watch a given directory" + "\n" +
						// " Generate API documentation from n4js files" + "\n" +
						"  Set versions of n4js-related dependencies" + "\n" +
						"  Create an empty n4js project" + "\n" +
						"  Print version of this tool", //
				handler = N4JSSubCommandHandler.class)
		@SubCommands({
				@SubCommand(name = "compile", impl = ExplicitCompileOptions.class),
				@SubCommand(name = "clean", impl = CleanOptions.class),
				@SubCommand(name = "lsp", impl = LSPOptions.class),
				// not implemented:
				// @SubCommand(name = "watch", impl = WatchOptions.class),
				// @SubCommand(name = "api", impl = APIOptions.class),
				@SubCommand(name = "setversions", impl = SetVersionsOptions.class),
				@SubCommand(name = "init", impl = InitOptions.class),
				@SubCommand(name = "version", impl = VersionOptions.class)
		})
		AbstractOptions cmd = this;

		@Argument(metaVar = "DIR", index = 1, required = false, //
				usage = "name of n4js project or workspace directory", //
				handler = N4JSFileOptionHandler.class)
		File dir = new File(".");
	}

	/** Options for compile related goals when given explicitly */
	static abstract public class AbstractExplicitCompileRelatedOptions extends AbstractCompileRelatedOptions {
		@Override
		File getDir() {
			return dir;
		}

		@Override
		void setDir(File file) {
			dir = file;
		}

		@Argument(metaVar = "DIR", index = 0, required = false, //
				usage = "name of n4js project or workspace directory", //
				handler = N4JSFileOptionHandler.class)
		File dir = new File(".");
	}

	/** Option for goal compile given explicitly */
	static public class ExplicitCompileOptions extends AbstractExplicitCompileRelatedOptions {
		@Override
		AbstractOptions newInstance() {
			return new ExplicitCompileOptions();
		}

		@Override
		N4jscGoal getGoal() {
			return N4jscGoal.compile;
		}
	}

	/** Option for goal clean */
	static public class CleanOptions extends AbstractExplicitCompileRelatedOptions {
		@Override
		AbstractOptions newInstance() {
			return new CleanOptions();
		}

		@Override
		N4jscGoal getGoal() {
			return N4jscGoal.clean;
		}
	}

	/** Option for goal api */
	static public class APIOptions extends AbstractExplicitCompileRelatedOptions {
		@Override
		AbstractOptions newInstance() {
			return new APIOptions();
		}

		@Override
		N4jscGoal getGoal() {
			return N4jscGoal.api;
		}
	}

	/** Option for goal watch */
	static abstract public class SingleDirOptions extends AbstractOptions {
		@Override
		File getDir() {
			return dir;
		}

		@Override
		void setDir(File file) {
			dir = file;
		}

		@Argument(metaVar = "DIR", index = 0, required = false, //
				usage = "name of n4js project or workspace directory", //
				handler = N4JSFileOptionHandler.class)
		File dir = new File(".");
	}

	/** Option for goal watch */
	static public class WatchOptions extends SingleDirOptions {
		@Override
		AbstractOptions newInstance() {
			return new WatchOptions();
		}

		@Override
		N4jscGoal getGoal() {
			return N4jscGoal.watch;
		}
	}

	/** Option for goal LSP */
	static public class LSPOptions extends AbstractOptions {
		@Override
		AbstractOptions newInstance() {
			return new LSPOptions();
		}

		@Override
		N4jscGoal getGoal() {
			return N4jscGoal.lsp;
		}

		@Override
		public String toSettingsString() {
			String s = super.toSettingsString();
			s += "\n  port=" + port;
			s += "\n  stdio=" + stdio;
			s += "\n  exec=" + exec;
			return s;
		}

		@Option(name = "--port", aliases = "-p", //
				usage = "set the port of the lsp server", //
				handler = N4JSIntOptionHandler.class)
		int port = 5007;

		@Option(name = "--stdio", //
				usage = "uses stdin/stdout for communication instead of sockets", //
				forbids = { "--port", "--exec" }, //
				handler = N4JSBooleanOptionHandler.class)
		boolean stdio = false;

		@Option(name = "--exec", //
				hidden = true, //
				usage = "executes the given command string once the LSP server is listening for clients and shuts "
						+ "down the server after the first client disconnects. Must not be used with option --stdio.", //
				forbids = "--stdio", //
				handler = N4JSStringOptionHandler.class)
		String exec = null;
	}

	/** This class defines option fields for command set-versions. */
	static public class SetVersionsOptions extends AbstractOptions {
		@Override
		AbstractOptions newInstance() {
			return new SetVersionsOptions();
		}

		@Override
		N4jscGoal getGoal() {
			return N4jscGoal.setversions;
		}

		@Argument(metaVar = "VERSION", index = 0, required = true, //
				usage = "new version string to set for all n4js related dependencies", //
				handler = N4JSStringOptionHandler.class)
		String setVersion;
	}

	/** This class defines option fields for command init. */
	static public class InitOptions extends AbstractOptions {
		@Override
		AbstractOptions newInstance() {
			return new InitOptions();
		}

		@Override
		N4jscGoal getGoal() {
			return N4jscGoal.init;
		}

		@Option(name = "--yes", aliases = "-y", forbids = "--answers", //
				usage = "skips the questionnaire", //
				handler = N4JSBooleanOptionHandler.class)
		boolean yes = false;

		@Option(name = "--answers", aliases = "-a", forbids = "--yes", //
				usage = "comma separated string of answers for the questionnaire. Can be incomplete.", //
				handler = N4JSStringOptionHandler.class)
		String answers;

		@Option(name = "--scope", aliases = "-s", forbids = "--n4js", //
				usage = "creates a scoped project. uses the parent directory as the scope name", //
				handler = N4JSBooleanOptionHandler.class)
		boolean scope = false;

		@Option(name = "--n4js", aliases = "-n", forbids = { "--scope", "--workspaces" }, //
				usage = "extends an existing npm project in the current working directory with n4js entries", //
				handler = N4JSBooleanOptionHandler.class)
		boolean n4js = false;

		@Option(name = "--workspaces", aliases = "-w", forbids = "--n4js", //
				usage = "creates the new project inside the given workspaces directory. "
						+ "Will also create a new workspace if not existing already."
						+ "In case the current working directory is inside an existing workspaces directory,"
						+ "this option will be activated implicitly using the cwd.", //
				handler = N4JSFileOptionHandler.class)
		File workspaces;
	}

	/** This class defines option fields for command init. */
	static public class VersionOptions extends AbstractOptions {
		@Override
		AbstractOptions newInstance() {
			return new VersionOptions();
		}

		@Override
		N4jscGoal getGoal() {
			return N4jscGoal.version;
		}
	}

	/** Internal parser */
	protected N4JSCmdLineParser parser;

	/** Internal data store of options */
	protected AbstractOptions options;

	/** Working directory */
	protected Path workingDir = new File(".").getAbsoluteFile().toPath();

	/** Constructor */
	public N4jscOptions() {
		options = new ImplicitCompileOptions();
		parser = new N4JSCmdLineParser(options);
	}

	/**
	 * Parses given options and returns an instance of {@link N4jscOptions}. In case the options could not be parsed,
	 * null is returned.
	 */
	public void read(String... args) throws N4jscException {
		try {
			parser.getProperties().withUsageWidth(130);

			parser.definedOptions.clear();
			parser.parseArgument(args);

			options = ((ImplicitCompileOptions) options).cmd;

			integrateEnvironment();
			interpretAndAdjust();

		} catch (CmdLineException e) {
			throw new N4jscException(N4jscExitCode.CMD_LINE_PARSE_INVALID, e);
		}
	}

	/** Interprets environment variables */
	protected void integrateEnvironment() {
		if (options instanceof AbstractCompileRelatedOptions) {
			// check for performance data collection system environment variable
			Map<String, ParsedOption<NamedOptionDef>> opts = getDefinedOptions();
			if (!opts.containsKey("--performanceReport") && System.getenv(N4JSC_PERFORMANCE_REPORT_ENV) != null) {
				String rawPath = System.getenv(N4JSC_PERFORMANCE_REPORT_ENV);
				File performanceReportFile = new File(rawPath);
				((AbstractCompileRelatedOptions) options).performanceReport = performanceReportFile;
			}
		}
	}

	/** Post-processing of parsed arguments and options */
	protected void interpretAndAdjust() {
		// note: contents of this methods are split-up since they are individually called from N4jscTestOptions
		interpretAndAdjustDirs();
		interpretAndAdjustVersionOption();
	}

	/** Post-processing of parsed arguments dirs */
	protected void interpretAndAdjustDirs() {
		File file = getDir();

		if (getDir() != null) {
			try {
				File canonicalFile = file.getCanonicalFile();
				if (N4JSGlobals.PACKAGE_JSON.equals(canonicalFile.getName())) {
					canonicalFile = canonicalFile.getParentFile();
				}
				options.setDir(canonicalFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/** Post-processing of parsed option version */
	protected void interpretAndAdjustVersionOption() {
		if (options.version) {
			VersionOptions newOptions = new VersionOptions();
			newOptions.help = options.help;
			newOptions.log = options.log;
			newOptions.showSetup = options.showSetup;
			newOptions.verbose = options.verbose;
			newOptions.version = options.version;
			newOptions.logFile = options.logFile;
			newOptions.performanceKey = options.performanceKey;
			newOptions.performanceReport = options.performanceReport;
			options = newOptions;
		}
	}

	/** @return list of all user defined arguments */
	public List<ParsedOption<OptionDef>> getDefinedArguments() {
		return parser.definedArguments;
	}

	/** @return list of all user defined options */
	public Map<String, ParsedOption<NamedOptionDef>> getDefinedOptions() {
		return parser.definedOptions;
	}

	/** @return given goal */
	public N4jscGoal getGoal() {
		return options.getGoal();
	}

	/** @return given project directory(s) */
	public File getDir() {
		return options.getDir();
	}

	/** @return true iff {@code --help} */
	public boolean isHelp() {
		return options.help;
	}

	/** @return true iff {@code --showSetup} */
	public boolean isShowSetup() {
		return options.showSetup;
	}

	/** @return true iff {@code --verbose} */
	public boolean isVerbose() {
		return options.verbose;
	}

	/** @return true iff {@code --log} */
	public boolean isLog() {
		return options.log;
	}

	/** @return S of {@code --logFile S} */
	public String getLogFile() {
		return options.logFile;
	}

	/** @return F of {@code --performanceReport F} */
	public File getPerformanceReport() {
		return options.performanceReport;
	}

	/** @return S of {@code --performanceKey S} */
	public String getPerformanceKey() {
		return options.performanceKey;
	}

	/** @return true iff {@code --noTests} */
	public boolean isNoTests() {
		return (options instanceof AbstractCompileRelatedOptions) && ((AbstractCompileRelatedOptions) options).noTests;
	}

	/** @return true iff {@code --testOnly} */
	public boolean isTestOnly() {
		return (options instanceof AbstractCompileRelatedOptions) && ((AbstractCompileRelatedOptions) options).testOnly;
	}

	/** @return true iff {@code --clean} */
	public boolean isClean() {
		return (options instanceof AbstractCompileRelatedOptions) && ((AbstractCompileRelatedOptions) options).clean;
	}

	/** @return argument of setVersions */
	public String getSetVersions() {
		return ((SetVersionsOptions) options).setVersion;
	}

	/** @return true iff {@code --noPersist} */
	public boolean isNoPersist() {
		return (options instanceof AbstractCompileRelatedOptions)
				&& ((AbstractCompileRelatedOptions) options).noPersist;
	}

	/** @return N of {@code --maxErrs N} */
	public int getMaxErrs() {
		Preconditions.checkState(options instanceof AbstractCompileRelatedOptions);
		return ((AbstractCompileRelatedOptions) options).maxErrs;
	}

	/** @return N of {@code --maxWarns N} */
	public int getMaxWarns() {
		Preconditions.checkState(options instanceof AbstractCompileRelatedOptions);
		return ((AbstractCompileRelatedOptions) options).maxWarns;
	}

	/** @return lsp port */
	public int getPort() {
		Preconditions.checkState(options instanceof LSPOptions);
		return ((LSPOptions) options).port;
	}

	/** @return true iff {@code --stdio} */
	public boolean isStdio() {
		Preconditions.checkState(options instanceof LSPOptions);
		return ((LSPOptions) options).stdio;
	}

	/** @return the user command if given via {@code --exec}. {@code null} otherwise. */
	public String getExec() {
		Preconditions.checkState(options instanceof LSPOptions);
		return ((LSPOptions) options).exec;
	}

	/** @return true iff either option {@code performanceKey} or {@code performanceReport} was given */
	public boolean isDefinedPerformanceOption() {
		Map<String, ParsedOption<NamedOptionDef>> opts = getDefinedOptions();
		if (opts.containsKey("--performanceKey") || opts.containsKey("--performanceReport")) {
			return true;
		}
		return false;
	}

	/** @return true iff {@code --yes} */
	public boolean isYes() {
		Preconditions.checkState(options instanceof InitOptions);
		return ((InitOptions) options).yes;
	}

	/** @return true iff {@code --answers} */
	public String getAnswers() {
		Preconditions.checkState(options instanceof InitOptions);
		return ((InitOptions) options).answers;
	}

	/** @return true iff {@code --scope} */
	public boolean isScope() {
		Preconditions.checkState(options instanceof InitOptions);
		return ((InitOptions) options).scope;
	}

	/** @return workspaces if given via {@code --workspaces}. {@code null} otherwise. */
	public File getWorkspaces() {
		Preconditions.checkState(options instanceof InitOptions);
		return ((InitOptions) options).workspaces;
	}

	/** @return the working directory of n4jsc.jar */
	public Path getWorkingDirectory() {
		return workingDir;
	}

	/** Prints out the usage of n4jsc.jar. Usage string is compiled by args4j. */
	public void printUsage(PrintStream out) {
		out.println(getUsage(this));

		N4JSCmdLineParser parserWithDefaults = new N4JSCmdLineParser(this.options.newInstance());

		// switch to English locale because args4j will use the user locale for some words like "Vorgabe"
		Locale curLocale = Locale.getDefault();
		Locale.setDefault(new Locale("en"));
		parserWithDefaults.printUsage(out);
		Locale.setDefault(curLocale);
	}

	/** @return a string that lists all relevant settings of n4jsc.jar */
	public String toSettingsString() {
		String s = "N4jsc.options=";
		s += "\n  Current execution directory=" + new File(".").toPath().toAbsolutePath();
		s += "\n  goal=" + getGoal().name();
		s += "\n  dir=" + options.getDir();
		s += "\n  showSetup=" + options.showSetup;
		s += "\n  verbose=" + options.verbose;
		s += options.toSettingsString();
		return s;
	}

	/** @return array all arguments followed by all options */
	public List<String> toArgs() {
		List<String> args = new ArrayList<>();

		for (ParsedOption<OptionDef> pa : getDefinedArguments()) {
			String value = pa.givenValue;
			if (value != null) {
				if (value.contains(" ")) {
					value = "\"" + value + "\"";
				}
				args.add(value);
			}
		}

		for (ParsedOption<NamedOptionDef> po : getDefinedOptions().values()) {
			NamedOptionDef od = po.optionDef;
			String value = po.givenValue;
			args.add(od.name());
			if (value != null) {
				if (value.contains(" ")) {
					value = "\"" + value + "\"";
				}
				args.add(value);
			}
		}

		return args;
	}

	/** @return the synthesized command line call string */
	public String toCallString() {
		String s = "java -jar n4jsc.jar";
		s += " " + String.join(" ", toArgs());
		return s;
	}

	@Override
	public String toString() {
		return toCallString();
	}
}
