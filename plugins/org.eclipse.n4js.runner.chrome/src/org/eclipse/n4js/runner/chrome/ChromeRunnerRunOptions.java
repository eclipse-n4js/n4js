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
package org.eclipse.n4js.runner.chrome;

import java.util.ArrayList;
import java.util.List;

/**
 * Run options for chrome runner. Unlike configuration that is stored, this is just group of runtime options that are
 * passed to single execution (as command line parameters or environmental variables). These are not persisted, but
 * usually are created from data in stored configurations and runtime data provided by user (eg file selection).
 *
 * Note this contains nodejs options and chrome options.
 */
public class ChromeRunnerRunOptions {

	private String data = "{}";
	private String mod = "dummy module";
	private String paths = "./";
	private final List<String> initModules = new ArrayList<>();

	/** set name of the module to run. */
	public void setExecModule(String mod) {
		this.mod = mod;
	}

	/**
	 * get string representing execution data. This should contain user selection (e.g. module to run or computed list
	 * of tests) and be picked up (push or pull - details are contract between concrete runner/tester and supported
	 * runtime environments) by execution module.
	 *
	 * @returns string representing the module - project relative path to compiled module.
	 */
	public String getExecutionData() {
		return this.data;
	}

	/** set string representing execution data */
	public void setExecutionData(String data) {
		this.data = data;
	}

	/**
	 * get name of the module to run (execModule). This should be entry point to given project, e.g. Main module in
	 * application.
	 *
	 * @returns string representing the module - project relative path to compiled module.
	 */
	public String getExecModule() {
		return this.mod;
	}

	/** set string representing dependencies paths */
	public void setCoreProjectPaths(String paths) {
		this.paths = paths;
	}

	/**
	 * String representing paths that runner should scan for modules. Paths should be absolute and separated with
	 * appropriate separator. This value is used to set NODE_PATH environmental variable.
	 *
	 * @see <a href="http://nodejs.org/api/modules.html#modules_all_together">node require</a>
	 * @see <a href="https://gist.github.com/branneman/8048520#4-the-environment">better local require</a>
	 * @returns
	 */
	public String getCoreProjectPaths() {
		return this.paths;
	}

	/**
	 * get list of initialization modules (initModules) that have to be loaded before loading module to run
	 */
	public List<String> getInitModules() {
		return new ArrayList<>(this.initModules);
	}

	/** set list of modules to bootstrap before exec module */
	public void addInitModules(List<String> modules) {
		this.initModules.addAll(modules);
	}
}
