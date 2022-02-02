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

import org.eclipse.xtext.testing.InjectWith;
import org.junit.runner.RunWith;

import org.eclipse.n4js.JSLibSingleTestConfig;
import org.eclipse.n4js.JSLibSingleTestConfigProvider;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.TestCodeProvider;
import org.eclipse.n4js.XtextParametrizedRunner;
import org.eclipse.n4js.XtextParametrizedRunner.Parameters;

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
public class ECMA5TestInSuite extends AbstractECMATestInSuite {

	/**
	 * Archive containing ECMAScript test files.
	 */
	public static final Collection<String> ECMASCRIPT_TEST_SUITE_ARCHIVE = Arrays.asList("test262_5.0.zip");

	/**
	 * Files requiring the validator to check certain constraints.
	 */
	public static final String REQUIRE_VALIDATOR_FILENAME = "validator_5.0.txt";

	/**
	 * Files requiring the validator to check certain constraints, not yet working
	 */
	public static final String REQUIRE_VALIDATOR_TODO_FILENAME = "validatorTODO_5.0.txt";

	/**
	 * Blacklist of files requiring an execution engine.
	 */
	public static final String BLACKLIST_FILENAME = "blacklist_5.0.txt";

	/**
	 * Blacklist of files requiring strict mode validation.
	 */
	public static final String BLACKLIST_STRICT_MODE_FILENAME = "onlystrict_5.0.txt";

	/**
	 * Blacklist of files throwing errors at runtime.
	 */
	public static final String BLACKLIST_EXECUTION_FILENAME = "execution_5.0.txt";

	/***/
	public ECMA5TestInSuite(JSLibSingleTestConfig config) {
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

}
