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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("unchecked")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class InferenceContext_UnderconstrainedTest extends AbstractInferenceContextTest {

	@Test
	public void test_underconstrained_empty() {
		assertSolution(script,
				new TypeConstraint[] {
				// empty
				},
				Pair.of(alpha, anyTypeRef(_G)));
	}

	@Test
	public void test_underconstrained_reflexive01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", alpha)
				},
				Pair.of(alpha, anyTypeRef(_G)));
	}

	@Test
	public void test_underconstrained_reflexive02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "=", alpha)
				},
				Pair.of(alpha, anyTypeRef(_G)));
	}

	@Test
	public void test_underconstrained_dependency01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", beta)
				},
				Pair.of(alpha, anyTypeRef(_G)),
				Pair.of(beta, anyTypeRef(_G)));
	}

	@Test
	public void test_underconstrained_dependency02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "=", beta)
				},
				Pair.of(alpha, anyTypeRef(_G)),
				Pair.of(beta, anyTypeRef(_G)));
	}

	@Test
	public void test_underconstrained_transitive() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", beta),
						constraint(beta, "<:", gamma)
				},
				Pair.of(alpha, anyTypeRef(_G)),
				Pair.of(beta, anyTypeRef(_G)),
				Pair.of(gamma, anyTypeRef(_G)));
	}
}
