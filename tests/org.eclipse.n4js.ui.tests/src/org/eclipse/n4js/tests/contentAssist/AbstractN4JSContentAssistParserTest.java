/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.contentAssist;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Set;

import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 *
 */
public abstract class AbstractN4JSContentAssistParserTest extends AbstractFollowElementComputationTest {

	protected abstract Collection<FollowElement> getFollowSet(FollowElement input) throws Exception;

	@Test
	public void testNoModifiersFollowUp_01() throws Exception {
		String input = "class A {}\n" +
				"function* foo(req: A) { \n" +
				"  yield 5; // removing 5 fixes the problem\n" +
				"  req.";
		Set<AbstractElement> expected = Sets.newHashSet(grammarAccess.getRootStatementAccess().getAlternatives());
		assertFollowers(input, expected, grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0());
	}

	@Test
	public void testNoModifiersFollowUp_02() throws Exception {
		String input = "class A {}\n" +
				"function* foo(req: A) { \n" +
				"  yield 5; // removing 5 fixes the problem\n" +
				"  req.";
		Set<AbstractElement> expected = Sets.newHashSet(
				grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0(),
				grammarAccess.getRootStatementAccess().getAlternatives());
		assertFollowers(input, expected, grammarAccess.getRootStatementAccess().getAlternatives());
	}

	@Test
	public void testNoModifiersFollowUp_03() throws Exception {
		String input = "class A {}\n" +
				"function* foo(req: A) { \n" +
				"  yield 5; // removing 5 fixes the problem\n" +
				"  req.";
		Set<AbstractElement> expected = Sets.newHashSet();
		assertFollowers(input, expected, grammarAccess.getScriptAccess().getScriptElementsAssignment_2());
	}

	@Test
	public void testExportPublicFollowUp_01() throws Exception {
		String input = "class A {}\n" +
				"export public function* foo(req: A) { \n" +
				"  yield 5; // removing 5 fixes the problem\n" +
				"  req.";
		Set<AbstractElement> expected = Sets.newHashSet(grammarAccess.getRootStatementAccess().getAlternatives());
		assertFollowers(input, expected, grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0());
	}

	@Test
	public void testExportPublicFollowUp_02() throws Exception {
		String input = "class A {}\n" +
				"export public function* foo(req: A) { \n" +
				"  yield 5; // removing 5 fixes the problem\n" +
				"  req.";

		Set<AbstractElement> expected = Sets.newHashSet();
		assertFollowers(input, expected, grammarAccess.getScriptAccess().getScriptElementsAssignment_2());
	}

	private void assertFollowers(String input, Set<AbstractElement> expected, AbstractElement startWith)
			throws Exception {
		Collection<FollowElement> firstShot = getFollowSet(input);
		FollowElement pickMe = null;
		for (FollowElement element : firstShot) {
			if (element.getGrammarElement() == startWith) {
				Assert.assertNull(pickMe);
				pickMe = element;
			}
		}
		Assert.assertNotNull(pickMe);
		if (pickMe.getLookAhead() == 1) {
			Assert.assertTrue(expected.isEmpty());
		} else {
			Collection<FollowElement> followSet = getFollowSet(pickMe);
			Set<AbstractElement> grammarElements = computeSearchElements(followSet);
			assertEquals(expected, grammarElements);
		}
	}
}
