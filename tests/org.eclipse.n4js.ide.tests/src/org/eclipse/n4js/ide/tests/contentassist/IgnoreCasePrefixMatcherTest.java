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

import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher;
import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher.IgnoreCase;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class IgnoreCasePrefixMatcherTest extends AbstractPrefixMatcherTest<IPrefixMatcher.IgnoreCase> {

	@Override
	protected IgnoreCase createMatcher() {
		return new IPrefixMatcher.IgnoreCase();
	}

	@Test
	public void testCamelCaseMismatch() {
		assertFalse(matcher.isCandidateMatchingPrefix("ExactMatch", "ExMa"));
		assertFalse(matcher.isCandidateMatchingPrefix("exactMatch", "exM"));
		assertFalse(matcher.isCandidateMatchingPrefix("eXactMatch", "eXM"));
	}
}