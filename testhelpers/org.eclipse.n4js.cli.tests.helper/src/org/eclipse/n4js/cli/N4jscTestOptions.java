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
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.cli.N4JSCmdLineParser.ParsedOption;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.NamedOptionDef;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionDef;

import com.google.common.base.Objects;

/** Helper class to create n4jsc option programmatically */
public class N4jscTestOptions extends N4jscOptions {

	/**
	 * Will set options {@code --clean} and {@code --noPersist}
	 *
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions IMPLICIT_COMPILE() {
		return IMPLICIT_COMPILE(true, null);
	}

	/**
	 * Will set options {@code --clean} and {@code --noPersist}
	 *
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions IMPLICIT_COMPILE(File file) {
		return IMPLICIT_COMPILE(true, file);
	}

	/**
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions IMPLICIT_COMPILE(boolean cleanNoPersist, File file) {
		return ABSTRACT_COMPILE(new ImplicitCompileOptions(), cleanNoPersist, file);
	}

	/**
	 * Will set options {@code --clean} and {@code --noPersist}
	 *
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions COMPILE(File file) {
		return COMPILE(true, file);
	}

	/**
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions COMPILE(boolean cleanNoPersist, File file) {
		return ABSTRACT_COMPILE(new ExplicitCompileOptions(), cleanNoPersist, file);
	}

	/**
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions ABSTRACT_COMPILE(AbstractCompileRelatedOptions options, boolean cleanNoPersist,
			File file) {

		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = options;
		instance.f(file);
		if (cleanNoPersist) {
			instance.clean().noPersist();
		}
		return instance;
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal clean */
	static public N4jscTestOptions CLEAN(File file) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new CleanOptions();
		return instance.f(file);
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal watch */
	static public N4jscTestOptions WATCH(File file) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new WatchOptions();
		return instance.f(file);
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal api */
	static public N4jscTestOptions API(File file) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new APIOptions();
		return instance.f(file);
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal lsp */
	static public N4jscTestOptions LSP() {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new LSPOptions();
		return instance;
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal version */
	static public N4jscTestOptions VERSION() {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new VersionOptions();
		return instance;
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal set-versions */
	static public N4jscTestOptions SET_VERSIONS(String setVersions) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new SetVersionsOptions();
		instance.setDefinedOption(() -> ((SetVersionsOptions) instance.options).setVersion = setVersions);
		return instance;
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal init */
	static public N4jscTestOptions INIT() {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new InitOptions();
		return instance;
	}

	private final LinkedHashMap<String, ParsedOption<NamedOptionDef>> definedOptions = new LinkedHashMap<>();

	private final List<ParsedOption<OptionDef>> definedArguments = new ArrayList<>();

	/** Set goal to compile */
	public N4jscTestOptions f(File file) {
		setDefinedOption(() -> {
			options.setDir(file);
			interpretAndAdjustDirs();
		});
		return this;
	}

	/** Sets option */
	public N4jscTestOptions help() {
		setDefinedOption(() -> options.help = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions verbose() {
		setDefinedOption(() -> options.verbose = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions showSetup() {
		setDefinedOption(() -> options.showSetup = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions log() {
		setDefinedOption(() -> options.log = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions logFile(String pLogFile) {
		setDefinedOption(() -> options.logFile = pLogFile);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions clean() {
		setDefinedOption(() -> ((AbstractCompileRelatedOptions) options).clean = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions noPersist() {
		setDefinedOption(() -> ((AbstractCompileRelatedOptions) options).noPersist = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions testOnly() {
		setDefinedOption(() -> ((AbstractCompileRelatedOptions) options).testOnly = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions noTests() {
		setDefinedOption(() -> ((AbstractCompileRelatedOptions) options).noTests = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions maxErrs(int pMaxErrs) {
		setDefinedOption(() -> ((AbstractCompileRelatedOptions) options).maxErrs = pMaxErrs);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions maxWarns(int pMaxWarns) {
		setDefinedOption(() -> ((AbstractCompileRelatedOptions) options).maxWarns = pMaxWarns);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions port(int pPort) {
		setDefinedOption(() -> ((LSPOptions) options).port = pPort);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions performanceKey(String pPerformanceKey) {
		setDefinedOption(() -> options.performanceKey = pPerformanceKey);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions performanceReport(File pPerformanceReport) {
		setDefinedOption(() -> options.performanceReport = pPerformanceReport);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions version() {
		setDefinedOption(() -> options.version = true);
		interpretAndAdjustVersionOption();
		return this;
	}

	/** Sets option */
	public N4jscTestOptions yes() {
		setDefinedOption(() -> ((InitOptions) options).yes = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions answers(String answers) {
		setDefinedOption(() -> ((InitOptions) options).answers = answers);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions scope() {
		setDefinedOption(() -> ((InitOptions) options).scope = true);
		return this;
	}

	/** Sets option */
	public N4jscTestOptions workspaces(File workspaces) {
		setDefinedOption(() -> ((InitOptions) options).workspaces = workspaces);
		return this;
	}

	/** Sets the working directory */
	public N4jscTestOptions setWorkingDirectory(Path directory) {
		setDefinedOption(() -> this.workingDir = directory.toAbsolutePath());
		return this;
	}

	@Override
	public Map<String, ParsedOption<NamedOptionDef>> getDefinedOptions() {
		return definedOptions;
	}

	@Override
	public List<ParsedOption<OptionDef>> getDefinedArguments() {
		if (!(options instanceof ImplicitCompileOptions)) {
			try {
				Field fieldCmd = ImplicitCompileOptions.class.getDeclaredField("cmd");
				Argument argumentAnnotation = fieldCmd.getAnnotationsByType(Argument.class)[0];
				OptionDef od = new OptionDef(argumentAnnotation, argumentAnnotation.multiValued());
				ParsedOption<OptionDef> goal = new ParsedOption<>(od, null, options.getGoal().goalName());
				List<ParsedOption<OptionDef>> extArguments = new ArrayList<>();
				extArguments.add(goal);
				extArguments.addAll(definedArguments);
				return extArguments;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return definedArguments;
	}

	/**
	 * Since the options are not read by a parser (instead just set in this class), the definedOptions need to be
	 * reconstructed using the actual option variables and annotations.
	 */
	private void setDefinedOption(Runnable setter) {
		Map<Field, Object> optionFieldValues = new LinkedHashMap<>();
		Map<Field, Object> argumentFieldValues = new LinkedHashMap<>();

		try {
			List<Field> fields = new ArrayList<>();
			Class<?> optionClass = options.getClass();
			while (optionClass != null) {
				fields.addAll(Arrays.asList(optionClass.getDeclaredFields()));
				optionClass = optionClass.getSuperclass();
			}

			for (Field field : fields) {
				Object currentValue = field.get(options);
				if (currentValue != null && currentValue != Boolean.FALSE) {
					if (field.getAnnotation(Option.class) != null) {
						optionFieldValues.put(field, currentValue);
					}
					if (field.getAnnotation(Argument.class) != null) {
						argumentFieldValues.put(field, currentValue);
					}
				}
			}

			setter.run();

			for (Field field : fields) {
				Object currentValue = field.get(options);
				if (currentValue != null && currentValue != Boolean.FALSE) {
					Option annotationOption = field.getAnnotation(Option.class);
					if (annotationOption != null) {
						Object lastValue = optionFieldValues.get(field);
						if (!Objects.equal(lastValue, currentValue)) {
							NamedOptionDef nod = new NamedOptionDef(annotationOption);
							String lastValueStr = lastValue == null ? "" : String.valueOf(lastValue);
							String currentValueStr = currentValue == Boolean.TRUE ? null : String.valueOf(currentValue);

							ParsedOption<NamedOptionDef> pOption = new ParsedOption<>(nod, lastValueStr,
									currentValueStr);

							definedOptions.put(nod.name(), pOption);
						}
					}

					Argument annotationArgument = field.getAnnotation(Argument.class);
					if (annotationArgument != null) {
						Object lastValue = argumentFieldValues.get(field);
						if (!Objects.equal(lastValue, currentValue)) {
							OptionDef od = new OptionDef(annotationArgument, annotationArgument.multiValued());
							String lastValueStr = lastValue == null ? "" : String.valueOf(lastValue);
							String currentValueStr = currentValue == Boolean.TRUE ? null : String.valueOf(currentValue);

							ParsedOption<OptionDef> pArgument = new ParsedOption<>(od, lastValueStr, currentValueStr);

							definedArguments.add(pArgument);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
