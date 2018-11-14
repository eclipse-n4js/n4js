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
package org.eclipse.n4js.typesystem

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/*
 * Tests for {@link TypeSystemHelper#meet(RuleEnvironment, TypeRef...)} method.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class MeetComputer_RawTypesTest extends AbstractTypeSystemHelperTests {

	@Before
	def void prepareTypeDefs() {
		setDefaultTypeDefinitions()
	}

	@Test
	def void testMeet() {
		assertMeet("A", "A", "A");
		assertMeet("B", "A", "B");
		assertMeet("B", "B", "A");
	}

	@Test
	def void testMeetWithIntersection() {

		assertMeet("intersection{A,D}", "D", "A");
		assertMeet("intersection{A,D}", "A", "D");

		assertMeet("intersection{B,D}", "A", "B", "D");
		assertMeet("intersection{B,D}", "A", "D", "B");
		assertMeet("intersection{B,D}", "B", "A", "D");
		assertMeet("intersection{B,D}", "B", "D", "A");
		assertMeet("intersection{B,D}", "D", "A", "B");
		assertMeet("intersection{B,D}", "D", "B", "A");
	}

}
