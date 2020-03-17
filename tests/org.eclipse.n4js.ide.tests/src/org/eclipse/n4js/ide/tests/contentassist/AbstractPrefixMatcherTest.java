/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.contentassist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract base class for prefix matcher tests.
 */
@SuppressWarnings("javadoc")
public abstract class AbstractPrefixMatcherTest<Matcher extends IPrefixMatcher> {

	protected Matcher matcher;

	@Before
	public void setUp() throws Exception {
		matcher = createMatcher();
	}

	protected abstract Matcher createMatcher();

	@After
	public void tearDown() throws Exception {
		matcher = null;
	}

	@Test
	public void testEmptyPrefix() {
		assertTrue(matcher.isCandidateMatchingPrefix("name", ""));
		assertTrue(matcher.isCandidateMatchingPrefix("", ""));
	}

	@Test
	public void testExactMatch() {
		assertTrue(matcher.isCandidateMatchingPrefix("", ""));
		assertTrue(matcher.isCandidateMatchingPrefix("Exact", "Exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("exact", "exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("eXact", "eXact"));
	}

	@Test
	public void testMismatch() {
		assertFalse(matcher.isCandidateMatchingPrefix("Exact", "Mismatch"));
		assertFalse(matcher.isCandidateMatchingPrefix("Exact", "ExactMismatch"));
	}

	@Test
	public void testExactPrefix() {
		assertTrue(matcher.isCandidateMatchingPrefix("Match", ""));
		assertTrue(matcher.isCandidateMatchingPrefix("ExactMatch", "Exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("exactMatch", "exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("eXactMatch", "eXact"));
	}

	@Test
	public void testLowerCaseMatch() {
		assertTrue(matcher.isCandidateMatchingPrefix("Exact", "exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("EXACT", "exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("exact", "exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("eXact", "exact"));
	}

	@Test
	public void testLowerCasePrefix() {
		assertTrue(matcher.isCandidateMatchingPrefix("ExactMatch", "exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("EXACTMatch", "exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("exactMatch", "exact"));
		assertTrue(matcher.isCandidateMatchingPrefix("eXactMatch", "exact"));
	}

	@Test
	public void testUpperCasePrefix() {
		assertTrue(matcher.isCandidateMatchingPrefix("ExactMatch", "EXACT"));
		assertTrue(matcher.isCandidateMatchingPrefix("EXACTMatch", "EXACT"));
		assertTrue(matcher.isCandidateMatchingPrefix("exactMatch", "EXACT"));
		assertTrue(matcher.isCandidateMatchingPrefix("eXactMatch", "EXACT"));
	}

}