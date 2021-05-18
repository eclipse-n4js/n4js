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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kohsuke.args4j.NamedOptionDef;
import org.kohsuke.args4j.Option;

import com.google.common.base.Objects;

/** Helper class to create n4jsc option programmatically */
public class N4jscTestOptions extends N4jscOptions {

	/**
	 * Will set options {@code --clean} and {@code --noPersist}
	 *
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions IMPLICIT_COMPILE(File... files) {
		return IMPLICIT_COMPILE(true, Arrays.asList(files));
	}

	/**
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions IMPLICIT_COMPILE(boolean cleanNoPersist, List<File> files) {
		return ABSTRACT_COMPILE(new ImplicitCompileOptions(), cleanNoPersist, files);
	}

	/**
	 * Will set options {@code --clean} and {@code --noPersist}
	 *
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions COMPILE(File... files) {
		return COMPILE(true, Arrays.asList(files));
	}

	/**
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions COMPILE(boolean cleanNoPersist, File... files) {
		return COMPILE(cleanNoPersist, Arrays.asList(files));
	}

	/**
	 * Will set options {@code --clean} and {@code --noPersist}
	 *
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions COMPILE(List<File> files) {
		return COMPILE(true, files);
	}

	/**
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions COMPILE(boolean cleanNoPersist, List<File> files) {
		return ABSTRACT_COMPILE(new ExplicitCompileOptions(), cleanNoPersist, files);
	}

	/**
	 * @return a new instance of {@link N4jscTestOptions} with goal compile
	 */
	static public N4jscTestOptions ABSTRACT_COMPILE(AbstractCompileRelatedOptions options, boolean cleanNoPersist,
			List<File> files) {

		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = options;
		instance.f(files);
		if (cleanNoPersist) {
			instance.clean().noPersist();
		}
		return instance;
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal clean */
	static public N4jscTestOptions CLEAN(File... files) {
		return CLEAN(Arrays.asList(files));
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal clean */
	static public N4jscTestOptions CLEAN(List<File> files) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new CleanOptions();
		return instance.f(files);
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal watch */
	static public N4jscTestOptions WATCH(File... files) {
		return COMPILE(Arrays.asList(files));
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal watch */
	static public N4jscTestOptions WATCH(List<File> files) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new WatchOptions();
		return instance.f(files);
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal api */
	static public N4jscTestOptions API(File... files) {
		return API(Arrays.asList(files));
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal api */
	static public N4jscTestOptions API(List<File> files) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new APIOptions();
		return instance.f(files);
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
	static public N4jscTestOptions SET_VERSIONS() {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new SetVersionsOptions();
		return instance;
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal init */
	static public N4jscTestOptions INIT() {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options = new InitOptions();
		return instance;
	}

	private final Map<String, N4JSCmdLineParser.ParsedOption> definedOptions = new LinkedHashMap<>();

	/** Set goal to compile */
	public N4jscTestOptions f(File... files) {
		return f(Arrays.asList(files));
	}

	/** Set goal to compile */
	public N4jscTestOptions f(List<File> files) {
		options.setDirs(files);
		interpretAndAdjustDirs();
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

	@Override
	public Map<String, N4JSCmdLineParser.ParsedOption> getDefinedOptions() {
		return definedOptions;
	}

	/**
	 * Since the options are not read by a parser (instead just set in this class), the definedOptions need to be
	 * reconstructed using the actual option variables and annotations.
	 */
	private boolean setDefinedOption(Runnable setter) {
		int definedOptionsCount = definedOptions.size();

		Map<Field, Object> optionFieldValues = new LinkedHashMap<>();
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
					Option annotationOption = field.getAnnotation(Option.class);
					if (annotationOption != null) {

						optionFieldValues.put(field, currentValue);
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

							N4JSCmdLineParser.ParsedOption pOption = new N4JSCmdLineParser.ParsedOption(
									nod, lastValueStr, currentValueStr);

							definedOptions.put(nod.name(), pOption);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return definedOptionsCount < definedOptions.size();
	}
}
