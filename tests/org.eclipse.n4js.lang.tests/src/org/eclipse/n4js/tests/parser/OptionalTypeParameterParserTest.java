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
package org.eclipse.n4js.tests.parser;

import java.util.List;

import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.Test;

public class OptionalTypeParameterParserTest extends AbstractParserTest {

	@Test
	public void testClass() {
		Script script = parseESSuccessfully("""
					class G<T=any> {}
				""");

		assertOptionalTypeParam((N4ClassDeclaration) script.getScriptElements().get(0));
	}

	@Test
	public void testInterface() {
		Script script = parseESSuccessfully("""
					interface G<T=any> {}
				""");

		assertOptionalTypeParam((N4InterfaceDeclaration) script.getScriptElements().get(0));
	}

	@Test
	public void testTypeAlias() {
		Script script = parseESSuccessfully("""
					type Alias<T=any> = Array<T>;
				""");

		assertOptionalTypeParam((N4TypeAliasDeclaration) script.getScriptElements().get(0));
	}

	@Test
	public void testGenericFuncWithDefaultTypeArg() {
		Script script = parseESSuccessfully("""
					function <T=any> foo() {}
				""");

		assertOptionalTypeParam((FunctionDeclaration) script.getScriptElements().get(0));
	}

	@Test
	public void testGenericMethodWithDefaultTypeArg() {
		Script script = parseESSuccessfully("""
					class Cls {
						<T=any> method() {}
					}
				""");

		assertOptionalTypeParam((N4MethodDeclaration) ((N4ClassDeclaration) script.getScriptElements().get(0))
				.getOwnedMembersRaw().get(0));
	}

	@Test
	public void testGenericFuncTypeExprWithDefaultTypeArg() {
		Script script = parseESSuccessfully("""
					let fn: {function<T=any>(p:T):T};
				""");

		FunctionTypeExpression funTypeExpr = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), FunctionTypeExpression.class));
		assertNotNull(funTypeExpr);
		assertOptionalTypeParam(funTypeExpr.getOwnedTypeVars());
	}

	@Test
	public void testGenericMethodInObjectLitWithDefaultTypeArg() {
		Script script = parseESSuccessfully("""
					let obj: ~Object with {
						<T=any> m(p:T):T;
					};
				""");

		TStructMethod tStructMethod = (TStructMethod) IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ParameterizedTypeRefStructural.class))
				.getAstStructuralMembers().get(0);
		assertNotNull(tStructMethod);
		assertOptionalTypeParam(tStructMethod.getTypeVars());
	}

	private void assertOptionalTypeParam(GenericDeclaration genDecl) {
		assertNotNull(genDecl);
		assertEquals(1, genDecl.getTypeVars().size());
		N4TypeVariable typeParam = genDecl.getTypeVars().get(0);
		boolean isASTNode = EcoreUtil2.getContainerOfType(typeParam, Script.class) != null;
		assertTrue(isASTNode);
		assertNotNull(typeParam.getDeclaredDefaultArgumentNode());
		assertTrue(typeParam.getDeclaredDefaultArgumentNode().getTypeRefInAST() instanceof ParameterizedTypeRef);
	}

	private void assertOptionalTypeParam(List<? extends TypeVariable> typeParamsInAST) {
		assertEquals(1, typeParamsInAST.size());
		TypeVariable typeParam = typeParamsInAST.get(0);
		boolean isASTNode = EcoreUtil2.getContainerOfType(typeParam, Script.class) != null;
		assertTrue(isASTNode);
		assertTrue(typeParam.isOptional());
		assertNotNull(typeParam.getDefaultArgument());
		assertTrue(typeParam.getDefaultArgument() instanceof ParameterizedTypeRef);
	}
}
