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
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Tests for miscellaneous methods and functionality of {@link InferenceContext} not directly related to constraint
 * solving.
 */
public class InferenceContextMiscTest extends AbstractN4JSTest {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private TypeSystemHelper tsh;

	@Inject
	private DeclMergingHelper declMergingHelper;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Test
	public void testNewInferenceVariablesForFunctionTypeExprOrRef01() {
		Script script = testHelper.parseAndValidateSuccessfully("""
				class G<T,S> {}

				function <T,S> foo(p: G<T,string>): S {
					return null;
				}
				""");

		TFunction fun = head(filter(script.eAllContents(), FunctionDeclaration.class)).getDefinedFunction();
		FunctionTypeRef funTypeRef = (FunctionTypeRef) TypeUtils.createTypeRef(fun);

		InferenceContext ic = createInferenceContext(script);

		assertEquals(0, ic.getInferenceVariables().size());
		assertTrue(funTypeRef.isGeneric());
		FunctionTypeExprOrRef result = ic.newInferenceVariablesFor(funTypeRef);
		assertEquals(2, ic.getInferenceVariables().size());
		assertFalse(result.isGeneric());
	}

	@Test
	public void testNewInferenceVariablesForFunctionTypeExprOrRef02() {
		Script script = testHelper.parseAndValidateSuccessfully("""
				function <T,S> foo(): void {}
				""");

		TFunction fun = head(filter(script.eAllContents(), FunctionDeclaration.class)).getDefinedFunction();
		FunctionTypeRef funTypeRef = (FunctionTypeRef) TypeUtils.createTypeRef(fun);

		InferenceContext ic = createInferenceContext(script);

		assertEquals(0, ic.getInferenceVariables().size());
		assertTrue(funTypeRef.isGeneric());
		FunctionTypeExprOrRef result = ic.newInferenceVariablesFor(funTypeRef);
		assertEquals(2, ic.getInferenceVariables().size());
		assertFalse(result.isGeneric());
	}

	private InferenceContext createInferenceContext(EObject context) {
		RuleEnvironment G = newRuleEnvironment(context);
		return new InferenceContext(ts, tsh, declMergingHelper, operationCanceledManager, CancelIndicator.NullImpl, G);
	}
}
