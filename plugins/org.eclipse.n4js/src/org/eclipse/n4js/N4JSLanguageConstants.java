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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.n4JS.LocalArgumentsVariable;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
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

	/**
	 * Default issue codes to suppress while running tests which are configured for issue suppression.
	 *
	 * You can use {@code N4JSInjectorProviderWithFilteredValidator} to configure JUnit tests
	 * or {@code SuppressIssuesSetup} to configure Xpect tests for issue suppression.
	 */
	public static final Collection<String> DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS = unmodifiableCollection(newHashSet(
			IssueCodes.CFG_LOCAL_VAR_UNUSED,
			IssueCodes.DFG_NULL_DEREFERENCE
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
	 * Future reserved words for ECMAScript. Contains already reserved words for N4JS such as {@code enum}.
	 * <p>
	 * Although {@code let} should be a strict mode reserved word it does cause runtime error when using as a function
	 * or method name.
	 */
	public static final Collection<String> FUTURE_RESERVED_WORDS = unmodifiableCollection(newHashSet(
			"let", "enum", "await"));

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
			"implements",
			"interface",
			"let",
			"package",
			"private",
			"protected",
			"public",
			"static",
			"yield");

	/** Name of the {@link LocalArgumentsVariable}. */
	public static final String LOCAL_ARGUMENTS_VARIABLE_NAME = "arguments";

	/** Name of built-in function 'eval'. */
	public static final String EVAL_NAME = "eval";

	/** Name of the built-in ES2015 property '__proto__'. */
	public static final String PROPERTY__PROTO__NAME = "__proto__";

	/**
	 * Suffix used in method compilation for the local function name as reported in error stack traces. Value:
	 * {@code "___n4"}
	 */
	public static final String METHOD_STACKTRACE_SUFFIX = "___n4";

	/**
	 * Property holding DI information used by N4JS dependency injection (in runtime).
	 */
	public static final String DI_PROP_NAME = "$di";

	/** The default project output folder that contains the generated output files of the transpiler. */
	public static final String DEFAULT_PROJECT_OUTPUT = "src-gen";

	/** The default project output folder that contains the sources. */
	public static final String DEFAULT_PROJECT_SRC = "src";

	/** The default project output folder that contains the tests. */
	public static final String DEFAULT_PROJECT_TEST = "test";

	private N4JSLanguageConstants() {
	}

	/** Elements exported by 'export default' syntax are visible under this name from outside. */
	public static final String EXPORT_DEFAULT_NAME = "default";
}
