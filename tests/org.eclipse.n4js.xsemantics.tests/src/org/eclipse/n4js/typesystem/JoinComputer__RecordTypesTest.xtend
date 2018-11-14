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
 * Tests for {@link TypeSystemHelper#join(RuleEnvironment, TypeRef...)} method with union types.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class JoinComputer__RecordTypesTest extends AbstractTypeSystemHelperTests {

	@Before
	def void prepareTypeDefs() {
		setDefaultTypeDefinitions()
	}

	@Test
	def void testJoinSimpleWithRecord1() {
		assertJoin("any", "~Object with { a: A; b: B; }", "A"); // better expectation would be: "Object"
	}

	@Test
	def void testJoinSimpleWithRecord2() {
		assertJoin("~Object with { a: A; b: B }", "~Object with { a: A; b: B; }");
	}

	@Test
	def void testJoinSimpleWithRecord3() {
		assertJoin("~Object", "~Object with { a: A; b: B; }", "~Object with { a: A; b: B; }"); // better expectation would be: "~Object with { A a; B b; }"
	}

	@Test
	def void testJoinSimpleWithRecordInUnion() {
		assertJoin("union{A,~Object with { a: A; b: B }}", "union{A,~Object with { a: A; b: B; }}", "A");
	}

	@Test
	def void testJoinSimpleWithRecordAsTypeArg() {
		assertJoin("G<? super intersection{A,~Object with { a: A; b: B }}>", "G<~Object with {a: A; b: B;}>", "G<A>");
	// TODO is above expectation correct? old expectation was: "G<? extends Object>"
	}

}
