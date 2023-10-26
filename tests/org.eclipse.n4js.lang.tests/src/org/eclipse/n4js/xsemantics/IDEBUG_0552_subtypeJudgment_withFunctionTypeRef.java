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
package org.eclipse.n4js.xsemantics;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class IDEBUG_0552_subtypeJudgment_withFunctionTypeRef extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	private static final String CODE = """
			class A {}
			class B {}

			function foo(p: A) : B {    // form this we will create a FunctionTypeRef
				return null;
			}

			var fun: {function(A):B};    // this will provide us with a FunctionTypeExpression
			""";

	private RuleEnvironment G;
	private FunctionTypeRef functionTypeRef;
	private FunctionTypeExpression functionTypeExpression;

	@Before
	public void prepareTypeRefs() {
		Script script = createScript(JavaScriptVariant.n4js, CODE);
		valTestHelper.validate(script);

		TFunction foo = (TFunction) head(filter(script.eAllContents(), FunctionDeclaration.class)).getDefinedType();
		VariableDeclaration fun = head(filter(script.eAllContents(), VariableDeclaration.class));

		G = newRuleEnvironment(script);
		functionTypeExpression = (FunctionTypeExpression) fun.getDeclaredTypeRef();
		functionTypeRef = (FunctionTypeRef) TypeUtils.createTypeRef(foo);
	}

	@Test
	public void test_FunctionTypeRef_subtype_FunctionTypeExpression() {
		Result result = ts.subtype(G, functionTypeRef, functionTypeExpression);
		assertFalse("subtype judgment should not fail", result.isFailure());
		assertTrue("{function(A):B} should be a subtype of {function(A):B}", result.isSuccess());
	}

	@Test
	public void test_FunctionTypeExpression_subtype_FunctionTypeRef() {
		Result result = ts.subtype(G, functionTypeExpression, functionTypeRef);
		assertFalse("subtype judgment should not fail", result.isFailure());
		assertTrue("{function(A):B} should be a subtype of {function(A):B}", result.isSuccess());
	}
}
