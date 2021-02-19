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
package org.eclipse.n4js.xpect.methods.output;

import java.io.IOException;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.xpect.common.XpectCommentRemovalUtil;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.expectation.impl.AbstractExpectation;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.FileSetupContext;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.Assert;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

/**
 * Provides execution output test methods for Xpect. The provided source code is compiled on the fly and executed,
 * captured output is compared against the provided expectations.
 */
@SuppressWarnings("restriction")
public class OutputXpectMethod {

	/** {@link TestConfig Test configurations} used for output tests which do not specify a module loader. */
	private final static TestConfig[] DEFAULT_CONFIGS = {
			new TestConfig(GeneratorOption.MAX_TRANSPILE_OPTIONS),
			new TestConfig(GeneratorOption.DEFAULT_OPTIONS)
	};

	@Inject
	private XpectN4JSES5TranspilerHelper xpectN4JSES5TranpilerHelper;

	@Inject
	private XpectN4JSES5GeneratorHelper xpectGenerator;

	/**
	 * Compile provided then execute and compare execution output to provided expectation. During compilation
	 * dependencies are gathered and also compiled.
	 * <p>
	 * White space is ignored.
	 * <p>
	 * The code is compiled and executed twice, once for each of the two default sets of transpiler options:
	 * {@link GeneratorOption#ES5plus} and {@link GeneratorOption#NodeCurrent}.
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
	 */
	@Xpect
	public void output(@StringExpectation(whitespaceSensitive = false) IStringExpectation expectation, // arg0
			@ThisResource XtextResource resource, // arg1
			org.eclipse.xpect.setup.ISetupInitializer<Object> init, // arg2
			FileSetupContext fileSetupContext // arg3
	) throws IOException {

		TestExecutor func = (config) -> {

			String executionResult = xpectN4JSES5TranpilerHelper.doCompileAndExecute(resource, init, fileSetupContext,
					true, null, config.options);
			try {
				expectation.assertEquals(executionResult);
			} catch (Throwable th) {
				// in case of mismatch, we have an error and thus prepend information about the current generator
				// options to help during debugging:
				expectation.assertEquals("======================================================================\n"
						+ "Kind information from OutputXpectMethod:\n"
						+ "following output was produced using generator options \""
						+ Joiner.on(", ").join(config.options) + "\"\n"
						+ "======================================================================\n"
						+ executionResult);
			}

		};

		runWithDefaultConfigs(func);
	}

	/**
	 * Similar as {@link OutputXpectMethod#output} but instead of doing plain string comparison, uses regular expression
	 * matching. Assumes that provided expectation is a regex expression that will be used to check if execution output
	 * matches against it.
	 * <p>
	 * This method is white space sensitive. If white space is to be ignored, the regular expression has to be defined
	 * accordingly.
	 *
	 * @param expectation
	 *            regex expression that will be used as compiled output matcher
	 */
	@Xpect
	public void outputRegex(@StringExpectation(whitespaceSensitive = true) IStringExpectation expectation, // arg0
			@ThisResource XtextResource resource, // arg1
			org.eclipse.xpect.setup.ISetupInitializer<Object> init, // arg2
			FileSetupContext fileSetupContext // arg3
	) throws IOException {

		TestExecutor func = (config) -> {

			String executionResult = xpectN4JSES5TranpilerHelper.doCompileAndExecute(resource, init, fileSetupContext,
					true, null, config.options);
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

		runWithDefaultConfigs(func);
	}

	/**
	 * Transpiles provided resource and asserts that the target code is <b>equal</> to the provided expectation. Code is
	 * not executed!
	 * <p>
	 * This method is white space sensitive.
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
	 * <p>
	 * This method is white space sensitive.
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
		if (xpectGenerator.isCompilable(resource, errorResultSb)) {
			compileResultSb.append(xpectGenerator.compile(root, GeneratorOption.MAX_TRANSPILE_OPTIONS,
					replaceQuotes));
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

	/** Runs the passed in code with all test configurations defined in {@link #DEFAULT_CONFIGS}. */
	private static void runWithDefaultConfigs(TestExecutor func) throws IOException {
		// Support for multiple system-loader, we do need to run the test multiple times, unfortunately.
		for (TestConfig config : DEFAULT_CONFIGS) {
			func.accept(config);
		}
	}

	/**
	 * A test configuration comprises {@link GeneratorOption}s to use during compilation.
	 */
	private static final class TestConfig {
		GeneratorOption[] options;

		public TestConfig(GeneratorOption... options) {
			this.options = options;
		}
	}

	@FunctionalInterface
	private static interface TestExecutor {
		void accept(TestConfig config) throws IOException;
	}
}
