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
package org.eclipse.n4js.typesystem;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for {@link TypeSystemHelper#join(RuleEnvironment, TypeRef...)} method with generic types.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class JoinComputer_GenericTest extends AbstractTypeSystemHelperTests {

	@Before
	public void prepareTypeDefs() {
		setDefaultTypeDefinitions();
	}

	@Test
	public void testJoinWithGenericsSameNoWildcard() {
		assertJoin("G<B>", "G<B>", "G<B>");
	}

	@Test
	public void testJoinWithGenericsNoWildcard() {
		assertJoin("G<? extends A>", "G<A>", "G<B>");
		assertJoin("G<? extends B>", "G<C>", "G<B>");

		// and reversed order:
		assertJoin("G<? extends A>", "G<B>", "G<A>");
		assertJoin("G<? extends B>", "G<B>", "G<C>");
	}

	@Test
	public void testJoinWithUnboundWildcard() {
		assertJoin("G<?>", "G<?>", "G<?>");
	}

	@Test
	public void testJoinWithUnboundWildcardAndBound() {
		assertJoin("G<? extends any>", "G<?>", "G<B>");
		// and reversed order:
		assertJoin("G<? extends any>", "G<B>", "G<?>");
	}

	@Test
	public void testJoinWithUnboundWildcardAndUpperBound() {
		assertJoin("G<? extends any>", "G<?>", "G<? extends B>");
		// and reversed order:
		assertJoin("G<? extends any>", "G<? extends B>", "G<?>");
	}

	@Test
	public void testJoinWithUnboundWildcardAndLowerBound() {
		assertJoin("G<? extends any>", "G<?>", "G<? super B>");
		// and reversed order:
		assertJoin("G<? extends any>", "G<? super B>", "G<?>");
	}

	@Test
	public void testJoinWithUpperBoundWildcard() {
		assertJoin("G<? extends A>", "G<? extends A>", "G<B>");
		assertJoin("G<? extends B>", "G<? extends B>", "G<B>");
		assertJoin("G<? extends B>", "G<? extends C>", "G<B>");
		assertJoin("G<? extends B>", "G<? extends C>", "G<? extends B>");
		assertJoin("G<? extends A>", "G<? extends A>", "G<? extends B>");

		assertJoin("G<? extends B>", "G<? extends B>", "G<? extends B>");

		// and reversed order
		assertJoin("G<? extends A>", "G<B>", "G<? extends A>");
		assertJoin("G<? extends B>", "G<B>", "G<? extends B>");
		assertJoin("G<? extends B>", "G<B>", "G<? extends C>");
		assertJoin("G<? extends B>", "G<? extends B>", "G<? extends C>");
		assertJoin("G<? extends A>", "G<? extends B>", "G<? extends A>");
	}

	/**
	 * This test expects results, which seem (theoretically) better then the Java 6/7 solutions and are also accepted by
	 * Java 8
	 */
	@Test
	public void testJoinWithLowerBoundWildcardJensStyle() {
		// cf. https://bugs.eclipse.org/bugs/show_bug.cgi?id=421974
		assertJoin("G<? super B>", "G<? super A>", "G<B>");

		assertJoin("G<? super B>", "G<? super B>", "G<B>");
		assertJoin("G<? super C>", "G<? super C>", "G<B>");

		assertJoin("G<? super B>", "G<? super B>", "G<? super B>");
	}

	@Test
	public void testJoinWithNestedGenerics() {
		assertJoin("G<G<A>>", "G<G<A>>", "G<G<A>>");
		assertJoin("G<? extends G<? extends A>>", "G<G<A>>", "G<G<B>>");
		assertJoin("G<? extends G<? super B>>", "G<G<? super A>>", "G<G<B>>");
		// TODO review!
		assertJoin("G<? super intersection{G<B>,A}>", "G<? super A>", "G<G<B>>");
	}
}
