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
package org.eclipse.n4js.tests.typesbuilder

import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.validation.JavaScriptVariant
import org.junit.Test

class ClassWithConstructorTypesBuilderTest extends AbstractParserTest {

	@Test
	def void testConstructorSimple() {
		val script = '''
			public class C {
				public constructor() {}
				public m() {}
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val classDecl = script.scriptElements.get(0) as N4ClassDeclaration
		performChecks(classDecl);
	}

	@Test
	def void testConstructorInN4JSD() {
		val script = '''
			export external public class C {
				public constructor()
				public m()
			}
		'''.parse(JavaScriptVariant.external)

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val classDecl = (script.scriptElements.get(0) as ExportDeclaration).exportedElement as N4ClassDeclaration;
		performChecks(classDecl);
	}

	def private void performChecks(N4ClassDeclaration classDecl) {
		assertEquals("C", classDecl.name)

		val ctorDecl = classDecl.getOwnedMembers.get(0) as N4MethodDeclaration
		assertTrue(ctorDecl.definedType instanceof TMethod);
		val ctor = ctorDecl.definedType as TMethod;
		assertSame(ctorDecl, ctor.astElement);

		assertEquals("constructor", ctorDecl.name);
		assertEquals("constructor", ctor.name);
		assertTrue(ctorDecl.constructor);
		assertTrue(ctor.constructor);
		assertNull(ctorDecl.declaredReturnTypeRefInAST);
		assertTrue(ctor.returnTypeRef instanceof DeferredTypeRef);

		val mDecl = classDecl.getOwnedMembers.get(1) as N4MethodDeclaration
		val m = mDecl.definedType as TMethod
		assertEquals("m", mDecl.name);
		assertEquals("m", m.name);
		assertFalse(mDecl.constructor)
		assertFalse(m.constructor)

		N4JSResource.postProcessContainingN4JSResourceOf(classDecl);
		assertEquals("this[C]", ctor.returnTypeRef?.typeRefAsString);
	}
}
