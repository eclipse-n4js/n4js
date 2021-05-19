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
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.NamedOptionDef;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.spi.BooleanOptionHandler;
import org.kohsuke.args4j.spi.FileOptionHandler;
import org.kohsuke.args4j.spi.IntOptionHandler;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;
import org.kohsuke.args4j.spi.StringOptionHandler;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

/**
 * This class enhances kohsuke's library:
 * <ul>
 * <li/>encapsule the {@link #definedOptions} to keep track of all given options
 * <li/>enhance usage print outs (especially of sub commands)
 * <li/>patch default sub command
 * </ul>
 */
public class N4JSCmdLineParser extends CmdLineParser {

	/** Constructor */
	public N4JSCmdLineParser(Object bean) {
		this(bean, new LinkedHashMap<>(), new ArrayList<>());
	}

	/** Constructor */
	public N4JSCmdLineParser(Object bean, LinkedHashMap<String, ParsedOption<NamedOptionDef>> definedOptions,
			List<ParsedOption<OptionDef>> definedArguments) {

		super(bean);
		this.definedOptions = definedOptions;
		this.definedArguments = definedArguments;
	}

	/** All user given options */
	final LinkedHashMap<String, ParsedOption<NamedOptionDef>> definedOptions;

	/** All user given arguments */
	final List<ParsedOption<OptionDef>> definedArguments;

	@SuppressWarnings("rawtypes")
	@Override
	public void printUsage(Writer out, ResourceBundle rb, OptionHandlerFilter filter) {
		PrintWriter w = new PrintWriter(out);
		// determine the length of the option + metavar first
		int len = 0;
		for (OptionHandler h : getArguments()) {
			int curLen = getPrefixLen(h, rb);
			len = Math.max(len, curLen);
		}
		for (OptionHandler h : getOptions()) {
			int curLen = getPrefixLen(h, rb);
			len = Math.max(len, curLen);
		}

		// then print
		for (OptionHandler h : getArguments()) {
			printOption(w, h, len, rb, filter);
		}
		for (OptionHandler h : getOptions()) {
			printOption(w, h, len, rb, filter);
		}

		w.flush();
	}

	@SuppressWarnings("rawtypes")
	private int getPrefixLen(OptionHandler h, ResourceBundle rb) {
		if (h.option.hidden())
			return 0;
		if (h.option.usage().length() == 0)
			return 0;

		String[] nameAndMetaLines = h.getNameAndMeta(rb, getProperties()).split("\n");
		int maxLength = 0;
		for (int i = 0; i < nameAndMetaLines.length; i++) {
			maxLength = Math.max(maxLength, nameAndMetaLines[i].length());
		}

		return maxLength;
	}

	private void addDefinedOption(OptionDef optionDef, String defaultValue, String givenValue) {
		if (optionDef instanceof NamedOptionDef) {
			ParsedOption<NamedOptionDef> parsedOption = new ParsedOption<>((NamedOptionDef) optionDef, defaultValue,
					givenValue);
			NamedOptionDef nod = (NamedOptionDef) optionDef;
			definedOptions.put(nod.name(), parsedOption);
		} else {
			ParsedOption<OptionDef> parsedOption = new ParsedOption<>(optionDef, defaultValue, givenValue);
			definedArguments.add(parsedOption);
		}
	}

	/** Patched {@link SubCommandHandler} */
	static public class N4JSSubCommandHandler extends SubCommandHandler {
		final private N4JSCmdLineParser clp;
		private final SubCommands commands;

		/** Constructor */
		public N4JSSubCommandHandler(CmdLineParser parser, OptionDef option, Setter<Object> setter) {
			super(parser, option, setter);
			clp = (N4JSCmdLineParser) parser;
			commands = setter.asAnnotatedElement().getAnnotation(SubCommands.class);
		}

		@Override
		protected Object subCommand(SubCommand c, final Parameters params) throws CmdLineException {
			clp.addDefinedOption(option, this.printDefaultValue(), params.getParameter(0));
			return super.subCommand(c, params);
		}

		@Override
		protected int fallback(String subCmd) throws CmdLineException {
			return 0;
		}

		@Override
		protected CmdLineParser configureParser(Object subCmd, SubCommand c) {
			// owner.getArguments().clear(); // very dirty hack to avoid complaints about missing dir argument
			N4JSCmdLineParser n4jsCmdLineParser = (N4JSCmdLineParser) owner;
			return new N4JSCmdLineParser(subCmd, n4jsCmdLineParser.definedOptions, n4jsCmdLineParser.definedArguments);
		}

		@Override
		public String getDefaultMetaVariable() {
			String superResult = super.getDefaultMetaVariable();
			String[] split = superResult.substring(1, superResult.length() - 1).split("\\s\\|\\s");
			return this.option.metaVar() + "\n  " + String.join("\n  ", split);
		}

		@Override
		public String printDefaultValue() {
			return null;
		}
	}

	/** Data class to hold information about user given command line options */
	static public class ParsedOption<T extends OptionDef> {
		/** Option the user gave */
		public final T optionDef;
		/** Default value of that option */
		public final String defaultValue;
		/** User given value for that option */
		public final String givenValue;

		/** Constructor */
		public ParsedOption(T optionDef, String defaultValue, String givenValue) {
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
