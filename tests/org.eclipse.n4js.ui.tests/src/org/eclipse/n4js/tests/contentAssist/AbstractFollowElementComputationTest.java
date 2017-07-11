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

import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
public abstract class AbstractFollowElementComputationTest {

	protected abstract Collection<FollowElement> getFollowSet(String input) throws Exception;

	@Inject
	protected N4JSGrammarAccess grammarAccess;

	@Test
	public void testNoModifiers() throws Exception {
		String input = "class A {}\n" +
				"function* foo(req: A) { \n" +
				"  yield 5; // removing 5 fixes the problem\n" +
				"  req.";
		Set<AbstractElement> expected = Sets
				.<AbstractElement> newHashSet(
						grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0(),
						grammarAccess.getRootStatementAccess().getAlternatives(),
						grammarAccess.getScriptAccess().getScriptElementsAssignment_2());
		assertFollowers(input, expected);
	}

	@Test
	public void testPublicModifier() throws Exception {
		String input = "class A {}\n" +
				"public function* foo(req: A) { \n" +
				"  yield 5; // removing 5 fixes the problem\n" +
				"  req.";
		Set<AbstractElement> expected = Sets
				.<AbstractElement> newHashSet(
						grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0(),
						grammarAccess.getRootStatementAccess().getAlternatives(),
						grammarAccess.getScriptAccess().getScriptElementsAssignment_2());
		assertFollowers(input, expected);
	}

	@Test
	public void testExportModifier() throws Exception {
		String input = "class A {}\n" +
				"export function* foo(req: A) { \n" +
				"  yield 5; // removing 5 fixes the problem\n" +
				"  req.";
		Set<AbstractElement> expected = Sets
				.<AbstractElement> newHashSet(
						grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0(),
						grammarAccess.getScriptAccess().getScriptElementsAssignment_2());
		assertFollowers(input, expected);
	}

	@Test
	public void testExportPublicModifier() throws Exception {
		String input = "class A {}\n" +
				"export public function* foo(req: A) { \n" +
				"  yield 5; // removing 5 fixes the problem\n" +
				"  req.";
		Set<AbstractElement> expected = Sets
				.<AbstractElement> newHashSet(
						grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0(),
						grammarAccess.getScriptAccess().getScriptElementsAssignment_2());
		assertFollowers(input, expected);
	}

	private void assertFollowers(String input, Set<AbstractElement> expected) throws Exception {
		Collection<FollowElement> followSet = getFollowSet(input);
		// Collection<FollowElement> followList = com.google.common.collect.Lists.newArrayList(getFollowSet(input));
		assertEquals(expected.size(), followSet.size());
		Set<AbstractElement> grammarElements = computeSearchElements(followSet);
		// Collection<AbstractElement> followElementList =
		// com.google.common.collect.Lists.newArrayList(grammarElements);
		assertEquals(expected, grammarElements);
	}

	protected Set<AbstractElement> computeSearchElements(Collection<FollowElement> followSet) {
		return Sets.newHashSet(
				Iterables.transform(followSet, new Function<FollowElement, AbstractElement>() {
					@Override
					public AbstractElement apply(FollowElement from) {
						return from.getGrammarElement();
					}
				}));
	}

}
