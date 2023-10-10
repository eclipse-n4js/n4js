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
package org.eclipse.n4js.transpiler.es.tests;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GHOLD_93_AsyncFunctionTest extends AbstractTranspilerTest {

	@Test
	@Ignore
	public void testWrapImplicitReturn() {

		TranspilerState state = transform(createTranspilerState("""
				()=>5
				"""));

		// val generated = """
		// System.register([], function($n4Export) {
		// return {
		// setters: [],
		// execute: function() {
		// (function() {
		// 5;
		// }).bind(this);
		// }
		// };
		// });
		// //# sourceMappingURL=X.map
		// """

		ArrowFunction F_AST = findFirstInAST(state, ArrowFunction.class);
		List<FunctionExpression> allFEs = toList(EcoreUtil2.eAllOfType(state.im, FunctionExpression.class));

		// precondition:
		assertEquals("Should have 0 parameters", 0, F_AST.getFpars().size());
		assertEquals("Should have 1 statement", 1, F_AST.getBody().getStatements().size());
		ExpressionStatement exprStmt = (ExpressionStatement) F_AST.getBody().getStatements().get(0);
		IntLiteral intLit = (IntLiteral) exprStmt.getExpression();
		assertEquals("statement should be int (5)", BigDecimal.valueOf(5L), intLit.getValue());

		// After transpilations:

		assertEquals("Exactly 3+1 FunctionExpressions expected.", 3 + 1, allFEs.size()); // +1 for CJS-wrapper IDE-2050
		FunctionExpression F_IM = allFEs.get(2 + 1);

		assertEquals("Should have 0 parameters", 0, F_IM.getFpars().size());
		assertEquals("Should have 1 statement", 1, F_IM.getBody().getStatements().size());
		ReturnStatement retStmt_IM = (ReturnStatement) F_IM.getBody().getStatements().get(0);
		IntLiteral intLit_IM = (IntLiteral) retStmt_IM.getExpression();
		assertEquals("statement should be int (5)", BigDecimal.valueOf(5L), intLit_IM.getValue());

		// .System x2 , .exports, .register , .bind expected :
		List<ParameterizedCallExpression> allCallExpressions = toList(
				EcoreUtil2.eAllOfType(state.im, ParameterizedCallExpression.class));
		assertEquals("expect 5 call-expressions, second should be '.bind()' ", 5, allCallExpressions.size());

		ParameterizedCallExpression bindCall_IM = allCallExpressions.get(2);
		ParameterizedPropertyAccessExpression_IM methodRef_IM = (ParameterizedPropertyAccessExpression_IM) (bindCall_IM
				.getTarget());
		assertEquals("bind", methodRef_IM.getRewiredTarget().getName());

	}
}
