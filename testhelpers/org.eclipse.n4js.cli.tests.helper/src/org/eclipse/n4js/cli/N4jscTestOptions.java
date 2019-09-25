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
import java.util.Arrays;
import java.util.List;

/** Helper class to create n4jsc option programmatically */
public class N4jscTestOptions extends N4jscOptions {

	/** @return a new instance of {@link N4jscTestOptions} with goal compile */
	static public N4jscTestOptions COMPILE(File... files) {
		return COMPILE(Arrays.asList(files));
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal compile */
	static public N4jscTestOptions COMPILE(List<File> files) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options.goal = N4jscGoal.compile;
		return instance.f(files);
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal clean */
	static public N4jscTestOptions CLEAN(File... files) {
		return CLEAN(Arrays.asList(files));
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal clean */
	static public N4jscTestOptions CLEAN(List<File> files) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options.goal = N4jscGoal.clean;
		return instance.f(files);
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal watch */
	static public N4jscTestOptions WATCH(File... files) {
		return COMPILE(Arrays.asList(files));
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal watch */
	static public N4jscTestOptions WATCH(List<File> files) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options.goal = N4jscGoal.watch;
		return instance.f(files);
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal api */
	static public N4jscTestOptions API(File... files) {
		return API(Arrays.asList(files));
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal api */
	static public N4jscTestOptions API(List<File> files) {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options.goal = N4jscGoal.api;
		return instance.f(files);
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal help */
	static public N4jscTestOptions HELP() {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options.goal = N4jscGoal.help;
		return instance;
	}

	/** @return a new instance of {@link N4jscTestOptions} with goal lsp */
	static public N4jscTestOptions LSP() {
		N4jscTestOptions instance = new N4jscTestOptions();
		instance.options.goal = N4jscGoal.lsp;
		return instance;
	}

	/** Set goal to compile */
	public N4jscTestOptions f(File... files) {
		return f(Arrays.asList(files));
	}

	/** Set goal to compile */
	public N4jscTestOptions f(List<File> files) {
		options.srcFiles = files;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions verbose() {
		options.verbose = true;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions showSetup() {
		options.showSetup = true;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions clean() {
		options.clean = true;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions testOnly() {
		options.testOnly = true;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions noTests() {
		options.noTests = true;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions log() {
		options.log = true;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions logFile(String pLogFile) {
		options.logFile = pLogFile;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions maxErrs(int pMaxErrs) {
		options.maxErrs = pMaxErrs;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions maxWarns(int pMaxWarns) {
		options.maxWarns = pMaxWarns;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions port(int pPort) {
		options.port = pPort;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions testCatalog(File pTestCatalog) {
		options.testCatalog = pTestCatalog;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions performanceKey(String pPerformanceKey) {
		options.performanceKey = pPerformanceKey;
		return this;
	}

	/** Sets option */
	public N4jscTestOptions performanceReport(File pPerformanceReport) {
		options.performanceReport = pPerformanceReport;
		return this;
	}
}
