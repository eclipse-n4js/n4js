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
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("unchecked")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class InferenceContext_GenericFunctionTypesTest extends AbstractInferenceContextTest {

	@Test
	public void test_baseCase() {

		// {function<T,S>(G2<T,S>):T} <: {function(G2<alpha,beta>):alpha}

		TypeVariable T = createTypeVar("T");
		TypeVariable S = createTypeVar("S");
		FunctionTypeExpression left = functionType(new TypeVariable[] { T, S }, ref(T), of(G2, T, S));
		FunctionTypeExpression right = functionType(ref(alpha), of(G2, alpha, beta));

		assertSolution(script,
				new TypeConstraint[] {
						constraint(left, "<:", right)
				},
				Pair.of(alpha, anyTypeRef(_G)),
				Pair.of(beta, anyTypeRef(_G)));
	}

	@Test
	public void test_returnTypeFixed() {

		// {function<T,S>(G2<T,S>):string} <: {function(G2<alpha,beta>):alpha}

		TypeVariable T = createTypeVar("T");
		TypeVariable S = createTypeVar("S");
		FunctionTypeExpression left = functionType(new TypeVariable[] { T, S }, stringTypeRef(_G), of(G2, T, S));
		FunctionTypeExpression right = functionType(ref(alpha), of(G2, alpha, beta));

		assertSolution(script,
				new TypeConstraint[] {
						constraint(left, "<:", right)
				},
				Pair.of(alpha, stringTypeRef(_G)),
				Pair.of(beta, anyTypeRef(_G)));
	}

	@Test
	public void test_fparFixed() {

		// {function<T,S>(G2<string,S>):T} <: {function(G2<alpha,beta>):alpha}

		TypeVariable T = createTypeVar("T");
		TypeVariable S = createTypeVar("S");
		FunctionTypeExpression left = functionType(new TypeVariable[] { T, S }, ref(T), of(G2, stringType(_G), S));
		FunctionTypeExpression right = functionType(ref(alpha), of(G2, alpha, beta));

		assertSolution(script,
				new TypeConstraint[] {
						constraint(left, "<:", right)
				},
				Pair.of(alpha, stringTypeRef(_G)),
				Pair.of(beta, anyTypeRef(_G)));
	}
}
