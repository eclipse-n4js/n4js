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

import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
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
	def void testInvalid01() {
		val script = '''
			function <T=any> foo() {}
		'''.parseESWithError;

		assertOptionalTypeParam(script.scriptElements.head as FunctionDeclaration);

		val res = script.eResource;
		assertEquals(1, res.errors.size);
		assertEquals("Only type parameters of classes and interfaces may be declared optional.", res.errors.head.message);
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
		assertEquals("Only type parameters of classes and interfaces may be declared optional.", res.errors.head.message);
	}

	def private void assertOptionalTypeParam(GenericDeclaration genDecl) {
		assertNotNull(genDecl);
		assertEquals(1, genDecl.typeVars.size);
		assertNotNull(genDecl.typeVars.head.defaultArgumentNode);
		assertTrue(genDecl.typeVars.head.defaultArgumentNode.typeRefInAST instanceof ParameterizedTypeRef);
	}
}
