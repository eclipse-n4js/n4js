/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.constraints.InferenceContext
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.util.CancelIndicator
import org.junit.Test

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Tests for miscellaneous methods and functionality of {@link InferenceContext} not directly related to
 * constraint solving.
 */
class InferenceContextMiscTest extends AbstractN4JSTest {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private TypeSystemHelper tsh;

	@Inject
	private OperationCanceledManager operationCanceledManager;


	@Test
	def void testNewInferenceVariablesForFunctionTypeExprOrRef01() {
		val script = '''
			class G<T,S> {}

			function <T,S> foo(p: G<T,string>): S {
				return null;
			}
		'''.parseAndValidateSuccessfully;

		val fun = script.eAllContents.filter(FunctionDeclaration).head.definedFunction;
		val funTypeRef = TypeUtils.createTypeRef(fun) as FunctionTypeRef;

		val ic = script.createInferenceContext;

		assertEquals(0, ic.inferenceVariables.size);
		assertTrue(funTypeRef.isGeneric);
		val result = ic.newInferenceVariablesFor(funTypeRef);
		assertEquals(2, ic.inferenceVariables.size);
		assertFalse(result.isGeneric);
	}

	@Test
	def void testNewInferenceVariablesForFunctionTypeExprOrRef02() {
		val script = '''
			function <T,S> foo(): void {}
		'''.parseAndValidateSuccessfully;

		val fun = script.eAllContents.filter(FunctionDeclaration).head.definedFunction;
		val funTypeRef = TypeUtils.createTypeRef(fun) as FunctionTypeRef;

		val ic = script.createInferenceContext;

		assertEquals(0, ic.inferenceVariables.size);
		assertTrue(funTypeRef.isGeneric);
		val result = ic.newInferenceVariablesFor(funTypeRef);
		assertEquals(2, ic.inferenceVariables.size);
		assertFalse(result.isGeneric);
	}

	def private InferenceContext createInferenceContext(EObject context) {
		val G = context.newRuleEnvironment;
		return new InferenceContext(ts, tsh, operationCanceledManager, CancelIndicator.NullImpl, G);
	}
}
