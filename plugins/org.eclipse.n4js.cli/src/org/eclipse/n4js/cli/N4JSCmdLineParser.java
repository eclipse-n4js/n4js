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
import java.util.LinkedHashMap;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.NamedOptionDef;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.BooleanOptionHandler;
import org.kohsuke.args4j.spi.FileOptionHandler;
import org.kohsuke.args4j.spi.IntOptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;
import org.kohsuke.args4j.spi.StringOptionHandler;

/**
 * This class encapsulates the {@link #definedOptions} which keep track of all given options
 */
public class N4JSCmdLineParser extends CmdLineParser {

	/** Constructor */
	public N4JSCmdLineParser(Object bean) {
		super(bean);
	}

	/** All user given options */
	final Map<String, ParsedOption> definedOptions = new LinkedHashMap<>();

	private void addDefinedOption(OptionDef optionDef, String defaultValue, String givenValue) {
		if (optionDef instanceof NamedOptionDef) {
			NamedOptionDef nod = (NamedOptionDef) optionDef;
			ParsedOption parsedOption = new ParsedOption(nod, defaultValue, givenValue);
			definedOptions.put(nod.name(), parsedOption);
		}
	}

	/** Data class to hold information about user given command line options */
	static public class ParsedOption {
		/** Option the user gave */
		public final NamedOptionDef optionDef;
		/** Default value of that option */
		public final String defaultValue;
		/** User given value for that option */
		public final String givenValue;

		/** Constructor */
		public ParsedOption(NamedOptionDef optionDef, String defaultValue, String givenValue) {
			this.optionDef = optionDef;
			this.defaultValue = defaultValue;
			this.givenValue = givenValue;
		}
	}

	/** Subclass to track user given options */
	static public class N4JSBooleanOptionHandler extends BooleanOptionHandler {
		final private N4JSCmdLineParser clp;

		/** Constructor */
		public N4JSBooleanOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Boolean> setter) {
			super(parser, option, setter);
			clp = (N4JSCmdLineParser) parser;
		}

		@Override
		public int parseArguments(Parameters params) throws CmdLineException {
			clp.addDefinedOption(option, this.printDefaultValue(), null);
			return super.parseArguments(params);
		}
	}

	/** Subclass to track user given options */
	static public class N4JSIntOptionHandler extends IntOptionHandler {
		final private N4JSCmdLineParser clp;

		/** Constructor */
		public N4JSIntOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Integer> setter) {
			super(parser, option, setter);
			clp = (N4JSCmdLineParser) parser;
		}

		@Override
		protected Integer parse(String argument) {
			clp.addDefinedOption(option, this.printDefaultValue(), argument);
			return super.parse(argument);
		}
	}

	/** Subclass to track user given options */
	static public class N4JSStringOptionHandler extends StringOptionHandler {
		final private N4JSCmdLineParser clp;

		/** Constructor */
		public N4JSStringOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super String> setter) {
			super(parser, option, setter);
			clp = (N4JSCmdLineParser) parser;
		}

		@Override
		public int parseArguments(Parameters params) throws CmdLineException {
			clp.addDefinedOption(option, this.printDefaultValue(), params.getParameter(0));
			return super.parseArguments(params);
		}
	}

	/** Subclass to track user given options */
	static public class N4JSFileOptionHandler extends FileOptionHandler {
		final private N4JSCmdLineParser clp;

		/** Constructor */
		public N4JSFileOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super File> setter) {
			super(parser, option, setter);
			clp = (N4JSCmdLineParser) parser;
		}

		@Override
		protected File parse(String argument) throws CmdLineException {
			clp.addDefinedOption(option, this.printDefaultValue(), argument);
			return super.parse(argument);
		}
	}
}
