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

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._FunDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._FunExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableDeclaration;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.TranspilerStateOperations;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class OperationReplaceTest extends AbstractTranspilerTest {

	@Test
	public void testReplace_classToFunctionDeclaration() {

		TranspilerState state = createTranspilerState("""

				class C {}

				""");

		TClass C = findFirstInModule(state, TClass.class);
		N4ClassDeclaration C_AST = findFirstInAST(state, N4ClassDeclaration.class);
		N4ClassDeclaration C_IM = findFirstInIM(state, N4ClassDeclaration.class);

		FunctionDeclaration replacement = _FunDecl("C");

		assertTracing(state, C_IM, C_AST);
		assertSymbolTableEntry(state, "C")
				.originalTarget(C) // pointing to TClass "C"
				.elementsOfThisName(C_IM);

		TranspilerStateOperations.replace(state, C_IM, replacement);

		assertNoTracing(state, C_IM); // replaced element should no longer have tracing info
		assertTracing(state, replacement, C_AST); // replacement should now have this tracing info

		assertSymbolTableEntry(state, "C")
				.originalTarget(C) // still pointing to original TClass "C"
				.elementsOfThisName(replacement); // now pointing to replacement
	}

	@Test
	public void testReplace_functionDeclarationToVariableDeclaration() {

		TranspilerState state = createTranspilerState("""

				function C() {}

				""");

		TFunction C = findFirstInModule(state, TFunction.class);
		FunctionDeclaration C_AST = findFirstInAST(state, FunctionDeclaration.class);
		FunctionDeclaration C_IM = findFirstInIM(state, FunctionDeclaration.class);

		VariableDeclaration replacementDecl = _VariableDeclaration("C", _FunExpr(false));

		assertTracing(state, C_IM, C_AST);
		assertSymbolTableEntry(state, "C")
				.originalTarget(C) // pointing to TClass "C"
				.elementsOfThisName(C_IM);

		TranspilerStateOperations.replace(state, C_IM, replacementDecl);

		assertNoTracing(state, C_IM); // replaced element should no longer have tracing info

		VariableStatement replacementStmnt = (VariableStatement) replacementDecl.eContainer();
		assertTracing(state, replacementStmnt, C_AST); // replacementStmnt should now have this tracing info

		assertSymbolTableEntry(state, "C")
				.originalTarget(C) // still pointing to original TClass "C"
				.elementsOfThisName(replacementDecl); // pointing to replacement
	}
}
