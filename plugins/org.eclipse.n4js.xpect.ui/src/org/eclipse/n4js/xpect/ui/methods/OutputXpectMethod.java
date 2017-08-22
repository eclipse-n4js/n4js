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
package org.eclipse.n4js.xpect.ui.methods;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.generator.common.GeneratorOption;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.runner.SystemLoaderInfo;
import org.eclipse.n4js.xpect.common.XpectCommentRemovalUtil;
import org.eclipse.n4js.xpect.ui.common.XpectN4JSES5TranspilerHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.Assert;
import org.xpect.expectation.IStringExpectation;
import org.xpect.expectation.StringExpectation;
import org.xpect.expectation.impl.AbstractExpectation;
import org.xpect.parameter.ParameterParser;
import org.xpect.runner.Xpect;
import org.xpect.xtext.lib.setup.FileSetupContext;
import org.xpect.xtext.lib.setup.ThisResource;

import com.google.inject.Inject;

/**
 * Provides execution output xpect test methods. Provided resource compiled on the fly and executed, captured output is
 * compared against provided expectations.
 */
@SuppressWarnings("restriction")
public class OutputXpectMethod {

	// FIXME replace this!!
	private static final GeneratorOption[] TEMP_OPTIONS = GeneratorOption.DEFAULT_OPTIONS;

	@Inject
	private XpectN4JSES5TranspilerHelper xpectN4JSES5TranpilerHelper;

	private final static List<SystemLoaderInfo> loaders = Arrays.asList(SystemLoaderInfo.SYSTEM_JS,
			SystemLoaderInfo.COMMON_JS);

	/**
	 * Compile provided then execute and compare execution output to provided expectation. During compilation
	 * dependencies are gathered and also compiled.
	 *
	 * @param expectation
	 *            the expected output after compiling n4js and executing the compiled js code
	 * @param resource
	 *            the resource to compile (only resources listed in the Xpect resource set configuration will be
	 *            additionally compiled)
	 * @param init
	 *            the Xpect initializer, holding e.g. the Xpect resource set configuration
	 * @param fileSetupContext
	 *            the Xpect file setup context (later required when creating a resource from a Xpect resource set
	 *            configuration entry)
	 * @param systemLoader
	 *            optional SystemLoader to use (e.g. SYSTEM_JS or COMMON_JS) if not given, this test will be run with
	 *            all known loaders.
	 */
	@Xpect
	@ParameterParser(syntax = "( 'with' arg4=ID )?")
	public void output(@StringExpectation(whitespaceSensitive = true) IStringExpectation expectation, // arg0
			@ThisResource XtextResource resource, // arg1
			org.xpect.setup.ISetupInitializer<Object> init, // arg2
			FileSetupContext fileSetupContext, // arg3
			String systemLoader // arg4
	) throws IOException {

		ConsumerX<SystemLoaderInfo> func = (loader) -> {

			String executionResult = xpectN4JSES5TranpilerHelper.doCompileAndExecute(resource, init, fileSetupContext,
					true, null, TEMP_OPTIONS, loader);
			try {
				expectation.assertEquals(executionResult);
			} catch (Throwable th) {
				// in case of mismatch, we have an error and thus prepend information about the current loader
				// (SYSTEM_JS, COMMON_JS, ...) to help during debugging:
				expectation.assertEquals("======================================================================\n"
						+ "Kind information from OutputXpectMethod:\n"
						+ "following output was produced using module loader \"" + loader + "\"\n"
						+ "======================================================================\n"
						+ executionResult);
			}

		};

		runWithAppropriateLoader(systemLoader, func);

	}

	/**
	 * Similar as {@link OutputXpectMethod#output} but instead of doing plain string comparison, uses regular expression
	 * matching. Assumes that provided expectation is a regex expression that will be used to check if execution output
	 * matches against it.
	 *
	 * @param expectation
	 *            regex expression that will be used as compiled output matcher
	 * @param systemLoader
	 *            optional SystemLoader to use (e.g. SYSTEM_JS or COMMON_JS) if not given, this test will be run with
	 *            all known loaders.
	 *
	 */
	@Xpect
	@ParameterParser(syntax = "( 'with' arg4=ID )?")
	public void outputRegex(@StringExpectation(whitespaceSensitive = true) IStringExpectation expectation, // arg0
			@ThisResource XtextResource resource, // arg1
			org.xpect.setup.ISetupInitializer<Object> init, // arg2
			FileSetupContext fileSetupContext, // arg3
			String systemLoader // arg4
	) throws IOException {

		ConsumerX<SystemLoaderInfo> func = (loader) -> {

			String executionResult = xpectN4JSES5TranpilerHelper.doCompileAndExecute(resource, init, fileSetupContext,
					true, null, TEMP_OPTIONS, loader);
			AbstractExpectation abstractEexpectation = (AbstractExpectation) expectation;

			String escapedActual = abstractEexpectation.getTargetSyntaxLiteral().escape(executionResult);
			String expected = abstractEexpectation.getExpectation();
			// tests failing on maven windows (work fine in eclipse windows though)
			// force special handling of new lines
			String escapedExpected = expected.replace("\r", "").replace("\n", "(\r?\n)");

			if (Pattern.compile(escapedExpected).matcher(escapedActual).matches() == false)
				Assert.assertEquals(
						"Regex did not match (Compare view not accurate, did not removed regex expressions)",
						regexToString(expected), regexToString(executionResult));

		};
		runWithAppropriateLoader(systemLoader, func);
	}

	/**
	 * Transpiles provided resource and asserts that the target code is <b>equal</> to the provided expectation. Code is
	 * not executed!
	 *
	 * @deprecated don't use compiled code string comparison, use to output execution test
	 */
	@Deprecated
	@Xpect
	public void compileResult(@StringExpectation(whitespaceSensitive = true) IStringExpectation expectation,
			@ThisResource XtextResource resource) {
		final String compileResult = getCompileResult(resource, false);
		expectation.assertEquals(compileResult);
	}

	/**
	 * Transpiles provided resource and asserts that the target code <b>contains</b> the provided expectation string. In
	 * addition, white-space is normalized, i.e. any non-empty sequence of white-space characters is normalized into a
	 * single " " (space) on both sides before comparison (also within string literals!). Code is not executed.
	 */
	@Xpect
	public void compileResultContains(
			@StringExpectation(whitespaceSensitive = true) IStringExpectation expectation,
			@ThisResource XtextResource resource) {
		final String compileResult = getCompileResult(resource, false);
		assertExpectationContained(expectation, compileResult, "compile result");
	}

	private String getCompileResult(Resource resource, boolean replaceQuotes) {
		Script root = (Script) resource.getContents().get(0);
		StringBuilder compileResultSb = new StringBuilder();
		StringBuilder errorResultSb = new StringBuilder();
		if (xpectN4JSES5TranpilerHelper.isCompilable(resource, errorResultSb)) {
			compileResultSb.append(xpectN4JSES5TranpilerHelper.compile(root, TEMP_OPTIONS, replaceQuotes));
		}
		String compileResult = "";
		if (errorResultSb.length() > 0) {
			compileResult = errorResultSb.toString();
		} else {
			compileResult = compileResultSb.toString();
		}
		String newCompileResult = compileResult;
		do {
			compileResult = newCompileResult;
			newCompileResult = XpectCommentRemovalUtil.removeAllXpectComments(newCompileResult);
		} while (compileResult.length() != newCompileResult.length());
		return compileResult;
	}

	private static final Pattern NORMALIZE_WHITE_SPACE = Pattern.compile("(\\s)+"); // note: \s includes \r and \n

	/**
	 * Asserts that the given expectation is <b>contained</b> in the actual string. Also normalizes white-space, i.e.
	 * any non-empty sequence of white-space characters is normalized into a single " " (space) on both sides before
	 * comparison (also within string literals!).
	 *
	 * @param nameOfActual
	 *            human-readable description of what "actual" represents (for error messages). E.g. "compile result".
	 */
	private static void assertExpectationContained(IStringExpectation expectation, String actual, String nameOfActual) {
		final String expectationStr = ((AbstractExpectation) expectation).getExpectation();
		final String expectationStrNormalized = NORMALIZE_WHITE_SPACE.matcher(expectationStr).replaceAll(" ").trim();
		final String actualStrNormalized = NORMALIZE_WHITE_SPACE.matcher(actual).replaceAll(" ").trim();
		if (!actualStrNormalized.contains(expectationStrNormalized)) {
			Assert.fail("Expectation string not contained in " + nameOfActual + " (after white-space normalization)\n"
					+ "------ expectation string:\n"
					+ expectationStr + "\n"
					+ "------ " + nameOfActual + ":\n"
					+ actual);
		}
	}

	private static String regexToString(String input) {
		StringBuffer buffer = new StringBuffer(input.length());
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '\\') {
				buffer.append(".\\.");
			} else if (input.charAt(i) == '\n') {
				buffer.append("\\n");
			} else if (input.charAt(i) == '\r') {
				buffer.append("\\r");
			} else if (input.charAt(i) == '\t') {
				buffer.append("\\t");
			} else {
				buffer.append(input.charAt(i));
			}
		}
		return buffer.toString();
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	// Helper to execute with different loader.

	/**
	 * If {@code systemLoader} is given, then runs with this particular runner, if {@code null} the test will be run
	 * with all known loaders sequentially.
	 */
	private void runWithAppropriateLoader(String systemLoader /* nullable */, ConsumerX<SystemLoaderInfo> func)
			throws IOException {
		SystemLoaderInfo systemLoaderInfo = SystemLoaderInfo.fromString(systemLoader);
		if (systemLoaderInfo != null) {
			func.accept(systemLoaderInfo);
		} else {
			withAllLoaders(func);
		}
	}

	/** Runs the passed in code with all loaders defined in {@link #loaders} */
	private static void withAllLoaders(ConsumerX<SystemLoaderInfo> func) throws IOException {
		// Support for multiple system-loader, we do need to run the test multiple times, unfortunately.
		for (SystemLoaderInfo loader : loaders) {
			func.accept(loader);
		}
	}

	@FunctionalInterface
	private static interface ConsumerX<T> {
		void accept(T t) throws IOException;
	}
}
