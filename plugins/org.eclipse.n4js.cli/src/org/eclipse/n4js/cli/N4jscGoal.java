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

import org.eclipse.n4js.cli.N4jscOptions.APIOptions;
import org.eclipse.n4js.cli.N4jscOptions.AbstractOptions;
import org.eclipse.n4js.cli.N4jscOptions.CleanOptions;
import org.eclipse.n4js.cli.N4jscOptions.ExplicitCompileOptions;
import org.eclipse.n4js.cli.N4jscOptions.HelpOptions;
import org.eclipse.n4js.cli.N4jscOptions.ImplicitCompileOptions;
import org.eclipse.n4js.cli.N4jscOptions.InitOptions;
import org.eclipse.n4js.cli.N4jscOptions.LSPOptions;
import org.eclipse.n4js.cli.N4jscOptions.SetVersionsOptions;
import org.eclipse.n4js.cli.N4jscOptions.VersionOptions;
import org.eclipse.n4js.cli.N4jscOptions.WatchOptions;

/**
 * Goals (a.k.a. commands) of the n4jsc.jar
 */
public enum N4jscGoal {
	/** Prints version */
	version(VersionOptions.class),
	/** Compiles with given options. Goal 'compile' was given explicitly */
	compile(ExplicitCompileOptions.class),
	/** Compiles with given options. No goal was given */
	compileImplicit(ImplicitCompileOptions.class),
	/** Cleans with given options */
	clean(CleanOptions.class),
	/** Starts LSP server */
	lsp(LSPOptions.class),
	/** Starts compiler daemon that watches the given folder(s) */
	watch(WatchOptions.class),
	/** Generates API documentation from n4js files */
	api(APIOptions.class),
	/** Creates an N4JS project */
	init(InitOptions.class),
	/** Shows help */
	help(HelpOptions.class),
	/** Sets version strings of all N4JS related packages to the given version */
	setversions("set-versions", SetVersionsOptions.class);

	final String realName;
	final Class<AbstractOptions> optionsClass;

	N4jscGoal(Class<? extends AbstractOptions> optionsClass) {
		this(null, optionsClass);
	}

	@SuppressWarnings("unchecked")
	N4jscGoal(String realName, Class<? extends AbstractOptions> optionsClass) {
		this.realName = realName == null ? name() : realName;
		this.optionsClass = (Class<AbstractOptions>) optionsClass;
	}
}
