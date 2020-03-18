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

import org.eclipse.n4js.ide.editor.contentassist.CamelCasePrefixMatcher;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class CamelCasePrefixMatcherTest extends AbstractPrefixMatcherTest<CamelCasePrefixMatcher> {

	@Override
	protected CamelCasePrefixMatcher createMatcher() {
		return new CamelCasePrefixMatcher();
	}

	@Test
	public void testCamelCasePrefix() {
		assertTrue(matcher.isCandidateMatchingPrefix("ExactMatch", "ExMa"));
		assertTrue(matcher.isCandidateMatchingPrefix("EXACTMatch", "EXACT"));
		assertTrue(matcher.isCandidateMatchingPrefix("EXactMatch", "EXM"));
		assertTrue(matcher.isCandidateMatchingPrefix("exactMatch", "exM"));
		assertTrue(matcher.isCandidateMatchingPrefix("EXactMatch", "EXACTm"));
		assertTrue(matcher.isCandidateMatchingPrefix("EXACTMatch", "EXaCT"));
		assertTrue(matcher.isCandidateMatchingPrefix("exactMatch", "exMa"));
		assertTrue(matcher.isCandidateMatchingPrefix("ExACTMatch", "EACMa"));
		assertTrue(matcher.isCandidateMatchingPrefix("ExACTMatch", "EAMa"));
	}

	@Test
	public void testCamelCaseMismatch() {
		assertFalse(matcher.isCandidateMatchingPrefix("ExactMatch", "EXMa"));
		assertFalse(matcher.isCandidateMatchingPrefix("ExACTMatch", "EAa"));
		assertFalse(matcher.isCandidateMatchingPrefix("ExACTMatch", "ECa"));
	}

}