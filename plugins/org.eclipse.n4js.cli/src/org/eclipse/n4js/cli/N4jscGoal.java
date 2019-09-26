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

import java.util.ResourceBundle;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.EnumOptionHandler;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

import com.google.common.base.Strings;

/**
 *
 */
public enum N4jscGoal {
	/** Prints help */
	help,
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
	api

	;

	/** Subclass option handler to improve usage print outs */
	static public class N4jscGoalOptionHandler<T extends Enum<T>> extends OptionHandler<N4jscGoal> {
		final EnumOptionHandler<N4jscGoal> delegate;

		/** Constructor */
		public N4jscGoalOptionHandler(CmdLineParser parser, OptionDef option, Setter<N4jscGoal> setter) {
			super(parser, option, setter);
			delegate = new EnumOptionHandler<>(parser, option, setter, N4jscGoal.class);
		}

		@Override
		public int parseArguments(Parameters params) throws CmdLineException {
			try {
				return delegate.parseArguments(params);
			} catch (CmdLineException e) {
				// This exception is thrown in case no goal was given
				return 0;
			}
		}

		@Override
		public String getDefaultMetaVariable() {
			return delegate.getDefaultMetaVariable();
		}

		@Override
		public String getMetaVariable(ResourceBundle rb) {
			if (Strings.isNullOrEmpty(option.metaVar())) {
				return getDefaultMetaVariable();
			} else {
				return option.metaVar();
			}
		}
	}
}
