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
package org.eclipse.n4js.xsemantics

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class IDEBUG_0552_subtypeJudgment_withFunctionTypeRef extends AbstractTypesystemTest {

	@Inject
	extension ValidationTestHelper
	@Inject
	private N4JSTypeSystem ts;


	private static final String CODE = '''
		class A {}
		class B {}

		function foo(p: A) : B {    // form this we will create a FunctionTypeRef
			return null;
		}

		var fun: {function(A):B};    // this will provide us with a FunctionTypeExpression
	''';


	private RuleEnvironment G;
	private FunctionTypeRef functionTypeRef;
	private FunctionTypeExpression functionTypeExpression;


	@Before
	def void prepareTypeRefs() {
		val script = createScript(JavaScriptVariant.n4js, CODE);
		script.validate();

		val foo = script.eAllContents.filter(FunctionDeclaration).head.definedType as TFunction;
		val fun = script.eAllContents.filter(VariableDeclaration).head;

		G = script.newRuleEnvironment;
		functionTypeExpression = fun.declaredTypeRef as FunctionTypeExpression;
		functionTypeRef = TypeUtils.createTypeRef(foo) as FunctionTypeRef;
	}


	@Test
	def void test_FunctionTypeRef_subtype_FunctionTypeExpression() {
		val result = ts.subtype(G, functionTypeRef, functionTypeExpression);
		assertFalse("subtype judgment should not fail", result.failure);
		assertTrue("{function(A):B} should be a subtype of {function(A):B}", result.success);
	}


	@Test
	def void test_FunctionTypeExpression_subtype_FunctionTypeRef() {
		val result = ts.subtype(G, functionTypeExpression, functionTypeRef);
		assertFalse("subtype judgment should not fail", result.failure);
		assertTrue("{function(A):B} should be a subtype of {function(A):B}", result.success);
	}
}
