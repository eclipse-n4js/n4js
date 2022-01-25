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
package org.eclipse.n4js.tests.ecmatestsuite;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.eclipse.n4js.JSLibSingleTestConfig;
import org.eclipse.n4js.JSLibSingleTestConfigProvider;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.TestCodeProvider;
import org.eclipse.n4js.XtextParametrizedRunner;
import org.eclipse.n4js.XtextParametrizedRunner.Parameters;
import org.eclipse.n4js.analysis.Analyser;
import org.eclipse.n4js.analysis.NegativeAnalyser;
import org.eclipse.n4js.analysis.PositiveAnalyser;
import org.eclipse.n4js.n4JS.Script;

/**
 * This class executes ECMA test suite 262. Based on provided resource (zipped ECMA test262 suite) it will create tests
 * that parse given JS test files and check for parsing errors.
 *
 * <pre>
 * Execution remarks:
 *  - it is simple jUnit test class, but it uses specialized runner {@link XtextParametrizedRunner}
 *  - these tests can be resource consuming, check provided launch configuration.
 *  - these configuration adds extra logging in the console.
 * </pre>
 */
@RunWith(XtextParametrizedRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ECMA6TestInSuite extends AbstractECMATestInSuite {

	/**
	 * Archive containing ECMAScript test files.
	 */
	public static final Collection<String> ECMASCRIPT_TEST_SUITE_ARCHIVE = Arrays.asList("test262_6.0.zip");

	/**
	 * Files requiring the validator to check certain constraints.
	 */
	public static final String REQUIRE_VALIDATOR_FILENAME = "validator_6.0.txt";

	/**
	 * Files requiring the validator to check certain constraints, not yet working
	 */
	public static final String REQUIRE_VALIDATOR_TODO_FILENAME = "validatorTODO_6.0.txt";

	/**
	 * Blacklist of files requiring an execution engine.
	 */
	public static final String BLACKLIST_FILENAME = "blacklist_6.0.txt";

	/**
	 * Blacklist of files requiring strict mode validation.
	 */
	public static final String BLACKLIST_STRICT_MODE_FILENAME = "onlystrict_6.0.txt";

	/**
	 * Blacklist of files throwing errors at runtime.
	 */
	public static final String BLACKLIST_EXECUTION_FILENAME = "execution_6.0.txt";

	/***/
	public ECMA6TestInSuite(JSLibSingleTestConfig config) {
		super(config);
	}

	/**
	 * Generates collection of ZipEntry instances that will be used as data provided parameter is mapped to name of the
	 * test (takes advantage of fact that ZipEntry.toString() is the same as entry.getName())
	 *
	 * @see TestCodeProvider#getDataFromZippedRoot(String, JSLibSingleTestConfigProvider)
	 * @return a collection of pairs {@link ZipEntry} -> blacklist
	 */
	@Parameters(name = "{0}")
	public static Collection<JSLibSingleTestConfig> data() throws URISyntaxException, ZipException, IOException {
		return TestCodeProvider.getDataFromZippedRoots(ECMASCRIPT_TEST_SUITE_ARCHIVE,
				new ECMAScriptSingleTestConfigProvider(
						REQUIRE_VALIDATOR_FILENAME,
						REQUIRE_VALIDATOR_TODO_FILENAME,
						BLACKLIST_FILENAME,
						BLACKLIST_EXECUTION_FILENAME, BLACKLIST_STRICT_MODE_FILENAME));
	}

	/**
	 * generated instances of the tests will use this base implementation
	 */
	@Override
	@Test
	public void test() throws Exception {
		if (this.parserN4JS == null) {
			throw new Error("parser instance is null");
		}

		String code = TestCodeProvider.getContentsFromFileEntry(config.entry, config.resourceName);
		if (code == null) {
			throw new Error("test data code instance is null");
		}

		Analyser analyser = createAnalyzer(code);
		XtextResourceSet resourceSet = resourceSetProvider.get();
		URI uri = URI.createURI(config.entry.getName());
		// ECMA 6 test suite was changed - "use strict" is synthesized by the test runner
		// we do the same here
		if (code.contains("flags: [onlyStrict]")) {
			// by using the proper file extension
			uri = uri.trimFileExtension().appendFileExtension("n4js");
		}
		Script script = doParse(code, uri, resourceSet, analyser);
		if (config.isValidator()) {
			// validation flag is bogus since it will produce linking issues
			// thus the negative tests will likely succeed for bogus reasons
			throw new IllegalStateException(config.entry.getName());
		}
		// try {
		analyser.analyse(script, config.entry.getName(), code);
		// } catch (AssertionError e) {
		// System.out.println(config.entry.getName());
		// throw e;
		// }
	}

	/**
	 * Creates the analyser for the given test code.
	 */
	@Override
	protected Analyser createAnalyzer(String code) {
		Analyser analyser;
		if (code.contains("negative: TypeError")) {
			analyser = new PositiveAnalyser(logger, tester);
		} else if (code.contains("negative: ") || code.contains("* @negative ")
				|| code.contains("that are named \"prototype\" throw a TypeError")) {
			analyser = new NegativeAnalyser(logger);
		} else {
			analyser = new PositiveAnalyser(logger, tester);
		}
		return analyser;
	}

}
