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

	/** @return a new instance of {@link N4jscTestOptions} */
	static public N4jscTestOptions get() {
		return new N4jscTestOptions();
	}

	Options options;

	/** Set goal to compile */
	public N4jscTestOptions COMPILE() {
		options.goal = N4jscGoal.compile;
		return this;
	}

	/** Set goal to compile */
	public N4jscTestOptions LSP() {
		options.goal = N4jscGoal.lsp;
		return this;
	}

	/** Set goal to compile */
	public N4jscTestOptions CLEAN() {
		options.goal = N4jscGoal.clean;
		return this;
	}

	/** Set goal to compile */
	public N4jscTestOptions HELP() {
		options.goal = N4jscGoal.help;
		return this;
	}

	/** Set goal to compile */
	public N4jscTestOptions WATCH() {
		options.goal = N4jscGoal.watch;
		return this;
	}

	/** Set goal to compile */
	public N4jscTestOptions API() {
		options.goal = N4jscGoal.api;
		return this;
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
