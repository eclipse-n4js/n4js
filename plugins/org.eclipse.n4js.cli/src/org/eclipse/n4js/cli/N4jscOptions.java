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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.n4js.cli.N4jscGoal.N4jscGoalOptionHandler;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.NamedOptionDef;
import org.kohsuke.args4j.Option;

/**
 */
public class N4jscOptions {

	/**
	 * Environment variable name to be used to specify the path of the performance report that is saved after running
	 * the headless compiler.
	 *
	 * If this environment variable is set, the headless compiler will always enable performance data collection,
	 * regardless of the parameter {@link Options#performanceReport}.
	 */
	private static String N4JSC_PERFORMANCE_REPORT_ENV = "N4JSC_PERFORMANCE_REPORT";

	/** Marker used to distinguish between compile-messages and runner output. */
	public static String MARKER_RUNNER_OUPTUT = "======= =======";

	/** Usage information. */
	public static String USAGE = "Usage: java -jar n4jsc.jar [GOAL] [FILE(s)] [OPTION(s)]";

	/** Use to specify the required goal for an option. */
	@Retention(RUNTIME)
	@Target({ FIELD, METHOD, PARAMETER })
	static public @interface GoalRequirements {
		/**  */
		N4jscGoal[] goals();
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
	static class Options {

		// OPTIONS

		@Option(name = "--help", aliases = "-h", usage = "prints help and exits", //
				handler = N4JSCmdLineParser.N4JSBooleanOptionHandler.class)
		boolean help = false;

		@Option(name = "--version", aliases = "-v", usage = "prints version and exits", //
				handler = N4JSCmdLineParser.N4JSBooleanOptionHandler.class)
		boolean version = false;

		@Option(name = "--showSetup", usage = "prints n4jsc setup", //
				handler = N4JSCmdLineParser.N4JSBooleanOptionHandler.class)
		boolean showSetup = false;

		@Option(name = "--verbose", usage = "enables verbose output", //
				handler = N4JSCmdLineParser.N4JSBooleanOptionHandler.class)
		boolean verbose = false;

		@Option(name = "--log", hidden = true, usage = "", //
				handler = N4JSCmdLineParser.N4JSBooleanOptionHandler.class)
		boolean log = false;

		@Option(name = "--logfile", hidden = true, usage = "specifies the log file name", //
				handler = N4JSCmdLineParser.N4JSStringOptionHandler.class)
		String logFile = "n4jsc.log";

		// OPTIONS for goal COMPILE

		@Option(name = "--testCatalog", aliases = "-tc", //
				usage = "[compile] generates a test catalog file to the given location. "
						+ "The test catalog lists all available tests among the compiled sources. "
						+ "Existing test catalog files will be replaced.", //
				handler = N4JSCmdLineParser.N4JSFileOptionHandler.class)
		@GoalRequirements(goals = N4jscGoal.compile)
		File testCatalog;

		@Option(name = "--noTests", //
				forbids = "--testOnly", //
				usage = "[compile] don't process test folders", //
				handler = N4JSCmdLineParser.N4JSBooleanOptionHandler.class)
		@GoalRequirements(goals = N4jscGoal.compile)
		boolean noTests = false;

		@Option(name = "--testOnly", //
				forbids = "--noTests", //
				usage = "[compile] only transpile contents of test folders", //
				handler = N4JSCmdLineParser.N4JSBooleanOptionHandler.class)
		@GoalRequirements(goals = N4jscGoal.compile)
		boolean testOnly = false;

		@Option(name = "--maxErrs", //
				usage = "[compile] set the maximum number of errors to print", //
				handler = N4JSCmdLineParser.N4JSIntOptionHandler.class)
		@GoalRequirements(goals = N4jscGoal.compile)
		int maxErrs = 0;

		@Option(name = "--maxWarns", //
				usage = "[compile] set the maximum number of warnings to print", //
				handler = N4JSCmdLineParser.N4JSIntOptionHandler.class)
		@GoalRequirements(goals = N4jscGoal.compile)
		int maxWarns = 0;

		@Option(name = "--clean", aliases = "-c", //
				usage = "[compile] output folders are cleaned before compilation.", //
				handler = N4JSCmdLineParser.N4JSBooleanOptionHandler.class)
		@GoalRequirements(goals = N4jscGoal.compile)
		boolean clean = false;

		@Option(name = "--performanceReport", aliases = "-pR", //
				hidden = true, //
				usage = "[compile] enables performance data collection and specifies the location of the performance report.", //
				handler = N4JSCmdLineParser.N4JSFileOptionHandler.class)
		@GoalRequirements(goals = N4jscGoal.compile)
		File performanceReport = null;

		@Option(name = "--performanceKey", aliases = "-pK", //
				hidden = true, //
				depends = "--performanceReport", //
				usage = "[compile] specifies the data collector key of the collector whose performance data is saved in the "
						+ "performance report.", //
				handler = N4JSCmdLineParser.N4JSStringOptionHandler.class)
		@GoalRequirements(goals = N4jscGoal.compile)
		String performanceKey = N4JSDataCollectors.HEADLESS_N4JS_COMPILER_COLLECTOR_NAME;

		// OPTIONS for goal LSP

		@Option(name = "--port", aliases = "-p", //
				usage = "[lsp] set the port of the lsp server", //
				handler = N4JSCmdLineParser.N4JSIntOptionHandler.class)
		@GoalRequirements(goals = N4jscGoal.lsp)
		int port = 5007;

		// ARGUMENTS

		@Argument(metaVar = "GOAL", multiValued = false, index = 0, required = false, //
				usage = "Goals are:"
						+ "\n\t compile  Compiles with given options"
						+ "\n\t clean    Cleans with given options"
						+ "\n\t lsp      Starts LSP server"
						+ "\n\t watch    Starts compiler daemon that watches the given folder(s)"
						+ "\n\t api      Generates API documentation from n4js files"
						+ "\n\t", //
				handler = N4jscGoalOptionHandler.class)
		N4jscGoal goal = N4jscGoal.compile;

		@Argument(metaVar = "FILE(s)", multiValued = true, index = 1, required = false, //
				usage = "names of either n4js files or n4js project directories")
		List<File> srcFiles = new ArrayList<>();
	}

	/** Internal data store of options */
	protected final Options options;
	/** Internal parser */
	protected final N4JSCmdLineParser parser;

	/** Constructor */
	public N4jscOptions() {
		options = new Options();
		parser = new N4JSCmdLineParser(options);
		parser.getProperties().withUsageWidth(130);
	}

	/**
	 * Parses given options and returns an instance of {@link N4jscOptions}. In case the options could not be parsed,
	 * null is returned.
	 */
	public void read(String... args) throws N4jscException {
		try {
			parser.definedOptions.clear();
			parser.parseArgument(args);
			integrateEnvironment();
			interpretAndAdjust();

		} catch (CmdLineException e) {
			throw new N4jscException(N4jscExitCode.CMD_LINE_PARSE_INVALID, e);
		}
	}

	private void integrateEnvironment() {
		// check for performance data collection system environment variable
		if (options.performanceReport == null && System.getenv(N4JSC_PERFORMANCE_REPORT_ENV) != null) {
			String rawPath = System.getenv(N4JSC_PERFORMANCE_REPORT_ENV);
			File performanceReportFile = new File(rawPath);
			options.performanceReport = performanceReportFile;
		}
	}

	private void interpretAndAdjust() {
		if (options.help) {
			options.goal = N4jscGoal.help;
			options.help = false;
		}
		if (options.version) {
			options.goal = N4jscGoal.version;
			options.version = false;
		}

		options.srcFiles = options.srcFiles.stream().map(f -> {
			try {
				return f.getCanonicalFile();
			} catch (IOException e) {
				return null;
			}
		}).filter(f -> f != null).collect(Collectors.toList());
	}

	/** @return list of all user defined options */
	public List<N4JSCmdLineParser.ParsedOption> getDefinedOptions() {
		return parser.definedOptions;
	}

	/** @return given goal */
	public N4jscGoal getGoal() {
		return options.goal;
	}

	/** @return given source file(s) or project(s) */
	public List<File> getSrcFiles() {
		return options.srcFiles;
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

	/** @return true iff {@code --noTests} */
	public boolean isNoTests() {
		return options.noTests;
	}

	/** @return true iff {@code --testOnly} */
	public boolean isTestOnly() {
		return options.testOnly;
	}

	/** @return true iff {@code --clean} */
	public boolean isClean() {
		return options.clean;
	}

	/** @return S of {@code --logFile S} */
	public String getLogFile() {
		return options.logFile;
	}

	/** @return F of {@code --testCatalog F} */
	public File getTestCatalog() {
		return options.testCatalog;
	}

	/** @return N of {@code --maxErrs N} */
	public int getMaxErrs() {
		return options.maxErrs;
	}

	/** @return N of {@code --maxWarns N} */
	public int getMaxWarns() {
		return options.maxWarns;
	}

	/** @return F of {@code --performanceReport F} */
	public File getPerformanceReport() {
		return options.performanceReport;
	}

	/** @return S of {@code --performanceKey S} */
	public String getPerformanceKey() {
		return options.performanceKey;
	}

	/** @return lsp port */
	public int getPort() {
		return options.port;
	}

	/** Prints out the usage of n4jsc.jar. Usage string is compiled by args4j. */
	public void printUsage(PrintStream out) {
		out.println(N4jscOptions.USAGE);

		N4JSCmdLineParser parserWithDefault = new N4JSCmdLineParser(new Options());
		parserWithDefault.printUsage(out);
	}

	/** @return a string that lists all relevant settings of n4jsc.jar */
	public String toSettingsString() {
		String s = "N4jsc.options=";
		s += "\n  goal=" + getGoal();
		s += "\n  showSetup=" + options.showSetup;
		s += "\n  verbose=" + options.verbose;
		s += "\n  maxErrs=" + options.maxErrs;
		s += "\n  maxWarns=" + options.maxWarns;
		s += "\n  testCatalogFile=" + options.testCatalog;
		s += "\n  testOnly=" + options.testOnly;
		s += "\n  noTests=" + options.noTests;
		s += "\n  port=" + options.port;
		s += "\n  srcFiles=" + options.srcFiles.stream().map(f -> f.getAbsolutePath()).reduce((a, b) -> a + ", " + b);
		s += "\n  Current execution directory=" + new File(".").getAbsolutePath();
		return s;
	}

	/** @return the synthesized command line call string */
	public String toCallString() {
		String s = "java -jar n4jsc.jar";
		s += " " + getGoal().name();
		for (N4JSCmdLineParser.ParsedOption po : getDefinedOptions()) {
			NamedOptionDef od = po.optionDef;
			String value = po.givenValue;
			s += " " + od.name();
			s += value == null ? "" : " " + value;
		}
		s += " " + String.join(", ", getSrcFiles().stream().map(f -> f.getAbsolutePath()).collect(Collectors.toList()));
		return s;
	}

	/** @return a map that maps all options names to their {@link GoalRequirements}. */
	public Map<String, GoalRequirements> getOptionNameToGoalRequirementMap() {
		Map<String, GoalRequirements> nameFieldMap = new HashMap<>();
		Field[] fields = this.options.getClass().getDeclaredFields();
		for (Field field : fields) {
			Option option = field.getDeclaredAnnotation(Option.class);
			GoalRequirements goalRequirements = field.getDeclaredAnnotation(GoalRequirements.class);
			if (field.canAccess(this.options) && option != null && goalRequirements != null) {
				nameFieldMap.put(option.name(), goalRequirements);
			}
		}
		return nameFieldMap;
	}

	@Override
	public String toString() {
		return toCallString();
	}
}
