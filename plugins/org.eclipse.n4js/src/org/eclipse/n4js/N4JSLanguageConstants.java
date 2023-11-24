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
package org.eclipse.n4js;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.valueOf;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.json.validation.JSONIssueCodes;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.validation.IssueCodes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Contains constants for the N4JS language.
 */
public abstract class N4JSLanguageConstants {

	/** Use {@link LanguageConstantsHelper} for a full list of keywords. */

	//@formatter:off

	/** The reserved {@value} keyword. */
	public static final String CONSTRUCTOR = N4JSASTUtils.CONSTRUCTOR;

	/** Direct access to the export keyword */
	public static final String EXPORT_KEYWORD = "export";
	/** Direct access to the external keyword */
	public static final String EXTERNAL_KEYWORD = "external";
	/** Direct access to the yield keyword */
	public static final String YIELD_KEYWORD = "yield";
	/** Direct access to the yield keyword */
	public static final String EXTENDS_KEYWORD = "extends";
	/** Direct access to the yield keyword */
	public static final String IMPLEMENTS_KEYWORD = "implements";
	/** Direct access to the import keyword */
	public static final String IMPORT_KEYWORD = "import";

	/**
	 * Default issue codes to suppress while running tests which are configured for issue suppression.
	 *
	 * You can use {@code N4JSInjectorProviderWithFilteredValidator} to configure JUnit tests
	 * or {@code SuppressIssuesSetup} to configure Xpect tests for issue suppression.
	 */
	public static final Set<String> DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS = unmodifiableSet(newHashSet(
			IssueCodes.CFG_LOCAL_VAR_UNUSED,
			IssueCodes.DFG_NULL_DEREFERENCE,
			JSONIssueCodes.JSON_COMMENT_UNSUPPORTED.name()
	));

	//@formatter:on

	/** Base ECMAScript and N4JS primitive types and literals. */
	public static final Collection<String> BASE_TYPES = unmodifiableCollection(newHashSet(
			"boolean", "number", "string", "null", "any"));

	/** Direct access to the get keyword */
	public static final String GET_KEYWORD = "get";
	/** Direct access to the set keyword */
	public static final String SET_KEYWORD = "set";

	/** Getter and setter keywords. */
	public static final Collection<String> GETTER_SETTER = unmodifiableCollection(newHashSet(
			GET_KEYWORD, SET_KEYWORD));

	/** Boolean literals such as {@code true} and {@code false}. */
	public static final Collection<String> BOOLEAN_LITERALS = unmodifiableCollection(newHashSet(
			valueOf(TRUE), valueOf(FALSE)));

	/**
	 * Keywords as of [ECM15] (11.6.2, pp. 165), as defined in grammar TypeExpressions.xtext, rule "ReservedWord".
	 * <p>
	 * WARNING: {@link #FUTURE_RESERVED_WORDS future reserved words} are not included in this set!
	 */
	public static final Set<String> RESERVED_WORDS = ImmutableSet.of(
			"break", "case", "catch", "class", "const", "continue", "debugger", "default", "delete", "do", "else",
			"export", "extends", "finally", "for", "function", "if", "import", "in", "instanceof", "new", "return",
			"super", "switch", "this", "throw", "try", "typeof", "var", "void", "while", "with", "yield",
			// null literal
			"null",
			// boolean literal
			"true", "false");

	/**
	 * Future reserved words for ECMAScript. Contains already reserved words for N4JS such as {@code enum}.
	 * <p>
	 * Although {@code let} should be a strict mode reserved word it does cause runtime error when using as a function
	 * or method name.
	 */
	public static final Collection<String> FUTURE_RESERVED_WORDS = unmodifiableCollection(newHashSet(
			"let", "enum", "await"));

	/**
	 * Keywords as defined in grammar TypeExpressions.xtext, rule "N4Keyword".
	 */
	public static final Set<String> N4_KEYWORDS = ImmutableSet.of(
			"get", "set", "let", "project", "external", "abstract", "static", "as", "from", "constructor", "of",
			"target", "type", "union", "intersection", "This", "Promisify",
			// future reserved keyword in [ECM15] only in modules, we add additional validation
			"await",
			// async is not a reserved keyword, i.e. it can be used as a variable name
			"async",
			// future reserved keywords in [ECM15], restricted via static semantic in [ECM15]
			"implements", "interface", "private", "protected", "public", // package not used in N4JS
			// definition-site variance
			"out",
			// namespace
			"namespace");

	/** Access modifiers for the N4JS language. */
	public static final Collection<String> ACCESS_MODIFIERS = unmodifiableCollection(newHashSet(
			"private", "project", "protected", "public"));

	/**
	 * A map of characters (with their corresponding human readable names) that are discouraged to be used anywhere in
	 * variable names although ECMAScript allows their usage.
	 * <p>
	 * <b>Key:&nbsp;</b>The actual characters.<br>
	 * <b>Values:&nbsp;</b>The human readable names of the characters.
	 */
	public static final Map<String, String> DISCOURAGED_CHARACTERS = ImmutableMap.of(
			"$", "dollar sign");

	/**
	 * Identifiers that are reserved words (only) in strict mode.
	 */
	public static final Set<String> RESERVED_WORDS_IN_STRICT_MODE = ImmutableSet.of(
			"import",
			"implements",
			"interface",
			"let",
			"package",
			"private",
			"protected",
			"public",
			"static",
			"yield");

	/** Name of the implicit "arguments" variable in functions/methods. */
	public static final String LOCAL_ARGUMENTS_VARIABLE_NAME = "arguments";

	/** Name of built-in function 'eval'. */
	public static final String EVAL_NAME = "eval";

	/** Name of the built-in ES2015 property '__proto__'. */
	public static final String PROPERTY__PROTO__NAME = "__proto__";

	/** Name of the getter in N4JS classes, interfaces, and enums use to obtain reflection information. */
	public static final String N4TYPE_NAME = "n4type";

	/**
	 * Key of the symbol used for storing N4JS dependency injection information in class constructors. The actual
	 * property name is <code>Symbol.for('org.eclipse.n4js/diInfo')</code> (must be evaluated at runtime).
	 */
	public static final String DI_SYMBOL_KEY = "org.eclipse.n4js/diInfo";

	/** The default project output folder that contains the generated output files of the transpiler. */
	public static final String DEFAULT_PROJECT_OUTPUT = "src-gen";

	/** The default project output folder that contains the sources. */
	public static final String DEFAULT_PROJECT_SRC = "src";

	/** The default project output folder that contains the tests. */
	public static final String DEFAULT_PROJECT_TEST = "test";

	private N4JSLanguageConstants() {
	}

	/** Elements exported using the 'export default ...' syntax are visible under this name from outside. */
	public static final String EXPORT_DEFAULT_NAME = UtilN4.EXPORT_DEFAULT_NAME;
}
