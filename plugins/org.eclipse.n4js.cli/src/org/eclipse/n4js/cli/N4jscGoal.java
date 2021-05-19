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

/**
 * Goals (a.k.a. commands) of the n4jsc.jar
 */
public enum N4jscGoal {
	/** Prints version */
	version,
	/** Compiles with given options */
	compile,
	/** Cleans with given options */
	clean,
	/** Starts LSP server */
	lsp,
	/** Starts compiler daemon that watches the given folder(s) */
	watch,
	/** Generates API documentation from n4js files */
	api,
	/** Creates an N4JS project */
	init,
	/** Sets version strings of all N4JS related packages to the given version */
	setVersions("set-versions")

	;

	final String goalName;

	N4jscGoal() {
		this(null);
	}

	N4jscGoal(String goalName) {
		this.goalName = goalName;
	}

	/** @return the name of the goal */
	public String goalName() {
		return goalName == null ? name() : goalName;
	}
}
