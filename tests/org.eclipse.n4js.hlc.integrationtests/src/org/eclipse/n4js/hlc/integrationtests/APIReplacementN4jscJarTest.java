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
package org.eclipse.n4js.hlc.integrationtests;

import static org.eclipse.n4js.hlc.integrationtests.HlcTestingConstants.WORKSPACE_FOLDER;
import static org.junit.Assert.assertEquals;

import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.test.helper.hlc.N4CliHelper;
import org.junit.Test;

/**
 * Tests API / implementation replacement when running via n4jsc.
 * <p>
 * IMPORTANT: for info on how to run this test locally, see {@link AbstractN4jscJarTest}!
 */
public class APIReplacementN4jscJarTest extends AbstractN4jscJarTest {

	/**
	 * Doin' what constructors do best: creating an instance.
	 */
	public APIReplacementN4jscJarTest() {
		super("fixtureAPIReplacement");
	}

	/**
	 * Success case, explicitly choosing one of the two available implementations: impl.polite
	 */
	@Test
	public void testCompileAndRunWithImplementation() throws Exception {
		logFile();

		Process p = createAndStartProcess(
				"--buildType", "allprojects",
				"--projectlocations", WORKSPACE_FOLDER,
				"--runWith", "nodejs",
				"--run", WORKSPACE_FOLDER + "/" + "org.eclipse.n4js.client/src/A.n4js",
				"--implementationId", "impl.polite");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);

		// check output
		N4CliHelper.assertContainsString("Polite implementation says: Hello Bob, my friend !!", outputLogFile);
	}

	/**
	 * Success case, explicitly choosing one of the two available implementations: impl.nasty
	 */
	@Test
	public void testCompileAndRunWithOtherImplementation() throws Exception {
		logFile();

		Process p = createAndStartProcess(
				"--buildType", "allprojects",
				"--projectlocations", WORKSPACE_FOLDER,
				"--runWith", "nodejs",
				"--run", WORKSPACE_FOLDER + "/" + "org.eclipse.n4js.client/src/A.n4js",
				"--implementationId", "impl.nasty");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);

		// check output
		N4CliHelper.assertContainsString("Nasty implementation says: f*$# off, Bob, my fiend !!", outputLogFile);
	}

	/**
	 * Error case, not specifying an implementation ID while two implementations are available.
	 */
	@Test
	public void testCompileAndRunWithMissingImplementation() throws Exception {
		logFile();

		Process p = createAndStartProcess(
				"--buildType", "allprojects",
				"--projectlocations", WORKSPACE_FOLDER,
				"--runWith", "nodejs",
				"--run", WORKSPACE_FOLDER + "/" + "org.eclipse.n4js.client/src/A.n4js"); // note: not defining an
																							// implementation id!

		int exitCode = p.waitFor();
		assertEquals("runner throws exception", ErrorExitCode.EXITCODE_RUNNER_STOPPED_WITH_ERROR.getExitCodeValue(),
				exitCode);

		// check output
		N4CliHelper
				.assertContainsString(
						"java.lang.IllegalStateException: no implementationId specified while several are available in the workspace: [impl.nasty, impl.polite]",
						outputLogFile);
	}

	/**
	 * Success case, not specifying an implementation ID while only a single implementation available.
	 */
	@Test
	public void testCompileAndRunWithImplicitImplementation() throws Exception {
		logFile();

		// delete the nasty implementation, so there is only one implementation left
		// and it will be picked up by default (without the need to specify an implementation id)
		deleteProject("org.eclipse.n4js.sample.nasty");

		Process p = createAndStartProcess(
				"--buildType", "allprojects",
				"--projectlocations", WORKSPACE_FOLDER,
				"--runWith", "nodejs",
				"--run", WORKSPACE_FOLDER + "/" + "org.eclipse.n4js.client/src/A.n4js"); // note: not defining an
																							// implementation id!

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);

		// check output
		N4CliHelper.assertContainsString("Polite implementation says: Hello Bob, my friend !!", outputLogFile);
	}

	/**
	 * Corner case: adding a default method to an interface on implementation side (i.e. the method is not known in the
	 * API and thus not known at compile time).
	 */
	@Test
	public void testInterfaceDefaultMethodOnlyOnImplementationSide() throws Exception {
		logFile();

		Process p = createAndStartProcess(
				"--buildType", "allprojects",
				"--projectlocations", WORKSPACE_FOLDER,
				"--runWith", "nodejs",
				"--run", WORKSPACE_FOLDER + "/" + "org.eclipse.n4js.client/src/CornerCase.n4js",
				"--implementationId", "impl.polite");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);

		// check output
		N4CliHelper.assertContainsString("in method: #implDetail()", outputLogFile);
		N4CliHelper.assertContainsString("result: SOME_VALUE | some_value", outputLogFile);
	}

	/**
	 * IDEBUG-503: usually, external classes do not have reflection meta data and thus the n4superType attribute of a
	 * subclass will be 'undefined'. However, if the external super class is annotated with <code>@N4JS</code>, then the
	 * superclass property of subclasses should have a correct value.
	 */
	@Test
	public void testMetaDataForExternalSuperClassIfAnnotatedWithN4JS() throws Exception {
		logFile();

		Process p = createAndStartProcess(
				"--buildType", "allprojects",
				"--projectlocations", WORKSPACE_FOLDER,
				"--runWith", "nodejs",
				"--run", WORKSPACE_FOLDER + "/" + "org.eclipse.n4js.client/src/MetaInfoForExternalSuperClass.n4js",
				"--implementationId", "impl.polite");

		int exitCode = p.waitFor();

		assertEquals("successful termination", 0, exitCode);

		// check output
		N4CliHelper.assertContainsString("{{ true / Greeter }}", outputLogFile);
	}
}
