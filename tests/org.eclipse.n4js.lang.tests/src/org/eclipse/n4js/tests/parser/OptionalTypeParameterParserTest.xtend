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
package org.eclipse.n4js.tests.parser

import java.util.Collection
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import org.eclipse.n4js.ts.types.TStructMethod
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.xtext.EcoreUtil2
import org.junit.Test

class OptionalTypeParameterParserTest extends AbstractParserTest {

	@Test
	def void testClass() {
		val script = '''
			class G<T=any> {}
		'''.parseESSuccessfully;

		assertOptionalTypeParam(script.scriptElements.head as N4ClassDeclaration);
	}

	@Test
	def void testInterface() {
		val script = '''
			interface G<T=any> {}
		'''.parseESSuccessfully;

		assertOptionalTypeParam(script.scriptElements.head as N4InterfaceDeclaration);
	}

	@Test
	def void testTypeAlias() {
		val script = '''
			type Alias<T=any> = Array<T>;
		'''.parseESSuccessfully;

		assertOptionalTypeParam(script.scriptElements.head as N4TypeAliasDeclaration);
	}

	@Test
	def void testInvalid01() {
		val script = '''
			function <T=any> foo() {}
		'''.parseESWithError;

		assertOptionalTypeParam(script.scriptElements.head as FunctionDeclaration);

		val res = script.eResource;
		assertEquals(1, res.errors.size);
		assertEquals("Only type parameters of classes, interfaces, and type aliases may be declared optional.", res.errors.head.message);
	}

	@Test
	def void testInvalid02() {
		val script = '''
			class Cls {
				<T=any> method() {}
			}
		'''.parseESWithError;

		assertOptionalTypeParam((script.scriptElements.head as N4ClassDeclaration).ownedMembersRaw.head as N4MethodDeclaration);

		val res = script.eResource;
		assertEquals(1, res.errors.size);
		assertEquals("Only type parameters of classes, interfaces, and type aliases may be declared optional.", res.errors.head.message);
	}

	@Test
	def void testInvalid03() {
		val script = '''
			let fn: {function<T=any>(p:T):T};
		'''.parseESWithError;

		val funTypeExpr = script.eAllContents.filter(FunctionTypeExpression).head;
		assertNotNull(funTypeExpr);
		assertOptionalTypeParam(funTypeExpr.ownedTypeVars);

		val res = script.eResource;
		assertEquals(1, res.errors.size);
		assertEquals("Only type parameters of classes, interfaces, and type aliases may be declared optional.", res.errors.head.message);
	}

	@Test
	def void testInvalid04() {
		val script = '''
			let obj: ~Object with {
				<T=any> m(p:T):T;
			};
		'''.parseESWithError;

		val tStructMethod = script.eAllContents.filter(ParameterizedTypeRefStructural).head.astStructuralMembers.head as TStructMethod;
		assertNotNull(tStructMethod);
		assertOptionalTypeParam(tStructMethod.typeVars);

		val res = script.eResource;
		assertEquals(1, res.errors.size);
		assertEquals("Only type parameters of classes, interfaces, and type aliases may be declared optional.", res.errors.head.message);
	}

	def private void assertOptionalTypeParam(GenericDeclaration genDecl) {
		assertNotNull(genDecl);
		assertEquals(1, genDecl.typeVars.size);
		val typeParam = genDecl.typeVars.head;
		val isASTNode = EcoreUtil2.getContainerOfType(typeParam, Script) !== null;
		assertTrue(isASTNode);
		assertNotNull(typeParam.declaredDefaultArgumentNode);
		assertTrue(typeParam.declaredDefaultArgumentNode.typeRefInAST instanceof ParameterizedTypeRef);
	}

	def private void assertOptionalTypeParam(Collection<? extends TypeVariable> typeParamsInAST) {
		assertEquals(1, typeParamsInAST.size);
		val typeParam = typeParamsInAST.head;
		val isASTNode = EcoreUtil2.getContainerOfType(typeParam, Script) !== null;
		assertTrue(isASTNode);
		assertTrue(typeParam.optional);
		assertNotNull(typeParam.defaultArgument);
		assertTrue(typeParam.defaultArgument instanceof ParameterizedTypeRef);
	}
}
