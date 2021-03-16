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
package org.eclipse.n4js.jsdoc2spec;

import java.io.IOException;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.ComparisonFailure;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Runs a full test, that is, reading n4js projects and generate new spec. Is based on LaTeX tests which refer to
 * IDEBUGs.
 */
@SuppressWarnings("javadoc")
abstract public class JSDoc2SpecProcessorFullTest extends AbstractIdeTest {

	abstract protected void fullTest(String projectName)
			throws IOException, InterruptedException, InterruptedException;

	/**
	 * Only set to true after having double-checked changes.
	 */
	protected final static boolean UPDATE_EXPECTION = false;

	@Inject
	protected Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	protected ProjectDescriptionLoader projectDescriptionLoader;

	/**
	 * Full test with SpecSample1 project
	 */
	@Test
	public void testSample1() throws IOException, InterruptedException {
		fullTest("SpecSample1");
	}

	/**
	 * Full test with SpecSample2 project
	 */
	@Test
	public void testSample2() throws IOException, InterruptedException {
		fullTest("SpecSample2");
	}

	/**
	 * Full test with SpecSample3 project, cf. IDEBUG-560
	 */
	@Test
	public void testSample3() throws IOException, InterruptedException {
		fullTest("SpecSample3_RedundantLines");
	}

	/**
	 * Full test with SpecSample4 project, cf. IDEBUG-586
	 */
	@Test
	public void testSample4() throws IOException, InterruptedException {
		fullTest("SpecSample4_TesteeForFunctions");
	}

	/**
	 * Full test with SpecSample5 project, cf. IDEBUG-610
	 */
	@Test
	public void testSample5() throws IOException, InterruptedException {
		fullTest("SpecSample5_LinkToReqIDs");
	}

	/**
	 * Full test with SpecSample6 project, cf. IDEBUG-632: signatures
	 */
	@Test
	public void testSample6() throws IOException, InterruptedException {
		fullTest("SpecSample6_ExtendedSignature");
	}

	/**
	 * Full test with SpecSample7 project, cf. IDEBUG-651: static polyfills
	 */
	@Test
	public void testSample7() throws IOException, InterruptedException {
		fullTest("SpecSample7_StaticPolyfills");
	}

	/**
	 * Full test with SpecSample8 project, cf. IDE-2165
	 */
	@Test
	public void testSample8() throws IOException, InterruptedException {
		fullTest("SpecSample8_NoTodoForReqIDs");
	}

	/**
	 * Full test with SpecSample9 project, cf. IDE-2160
	 */
	@Test
	public void testSample9() throws IOException, InterruptedException {
		fullTest("SpecSample9_StaticPolyfill");
	}

	/**
	 * Full test with SpecSample10 project, cf. IDE-2163
	 */
	@Test
	public void testSample10() throws IOException, InterruptedException {
		fullTest("SpecSample10_TodoTags");
	}

	/**
	 * Full test with SpecSample12 project
	 */
	@Test
	public void testSample12() throws IOException, InterruptedException {
		fullTest("SpecSample12");
	}

	/**
	 * Positive tests of assert method.
	 */
	@Test
	public void testAssertEqualsIgnoreWS() {
		assertEqualsIgnoreWS("", "");
		assertEqualsIgnoreWS("x", "x");
		assertEqualsIgnoreWS(" ", " ");
		assertEqualsIgnoreWS("", " ");
		assertEqualsIgnoreWS(" ", "");
		assertEqualsIgnoreWS(" x ", " x ");
		assertEqualsIgnoreWS(" x\n", " x");
	}

	/**
	 * Negative tests of assert method.
	 */
	@Test(expected = ComparisonFailure.class)
	public void testAssertEqualsIgnoreWSFail1() {
		assertEqualsIgnoreWS("x", "y");
	}

	/**
	 * Negative tests of assert method.
	 */
	@Test(expected = ComparisonFailure.class)
	public void testAssertEqualsIgnoreWSFail2() {
		assertEqualsIgnoreWS(" x", "y");
	}

	/**
	 * Negative tests of assert method.
	 */
	@Test(expected = ComparisonFailure.class)
	public void testAssertEqualsIgnoreWSFail3() {
		assertEqualsIgnoreWS("xx", "x");
	}

	/**
	 * Negative tests of assert method.
	 */
	@Test(expected = ComparisonFailure.class)
	public void testAssertEqualsIgnoreWSFail4() {
		assertEqualsIgnoreWS(" x\nx", " xx");
	}

	/**
	 * Similar to Assert.equals but all whitespaces are smartely ignore, i.e. type and number of whitespaces are ignored
	 * but at least there need to be whitespaces at the same positions.
	 */
	public static void assertEqualsIgnoreWS(String expected, String actual) {
		int ie = 0, ia = 0;
		char e = ' ';
		char a = e;
		int le = 1, la = 1;
		while (ie < expected.length() && ia < actual.length()) {
			boolean expectWS = false;
			boolean actualWS = false;
			do {
				e = expected.charAt(ie);
				ie++;
				if (e == '\n') {
					le++;
				}
				if (Character.isWhitespace(e)) {
					expectWS = true;
				}
			} while (ie < expected.length() && Character.isWhitespace(e));

			do {
				a = actual.charAt(ia);
				ia++;
				if (a == '\n') {
					la++;
				}
				if (Character.isWhitespace(a)) {
					actualWS = true;
				}
			} while (ia < actual.length() && Character.isWhitespace(a));

			if (a != e || expectWS != actualWS) {
				if (a == e) {
					throw new ComparisonFailure(
							"Strings differ on line " + le + " (exp.) and " + la
									+ " (actual), ', expected " + (expectWS ? "" : "no") + " whitespaces but got "
									+ (actualWS ? "some" : "none") + ".",
							expected, actual);
				} else {
					throw new ComparisonFailure(
							"Strings differ on line " + le + " (exp.) and " + la + " (actual), '" + e + "' != '" + a
									+ "'",
							expected, actual);
				}
			}
		}
		while (ie < expected.length()) {
			e = expected.charAt(ie);
			ie++;
			if (!Character.isWhitespace(e)) {
				throw new ComparisonFailure(
						"Got shorter string than expected, last matching line were " + le + " (exp.) and " + la
								+ " (actual).",
						expected, actual);
			}
		}
		while (ia < actual.length()) {
			a = actual.charAt(ia);
			ia++;
			if (!Character.isWhitespace(a)) {
				throw new ComparisonFailure(
						"Got longer string than expected, last matching line were " + le + " (exp.) and " + la
								+ " (actual).",
						expected, actual);
			}
		}
	}
}
