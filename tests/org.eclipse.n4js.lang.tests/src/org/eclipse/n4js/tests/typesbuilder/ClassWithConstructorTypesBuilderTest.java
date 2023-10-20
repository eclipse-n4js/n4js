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
package org.eclipse.n4js.tests.typesbuilder;

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tests.parser.AbstractParserTest;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.junit.Test;

public class ClassWithConstructorTypesBuilderTest extends AbstractParserTest {

	@Test
	public void testConstructorSimple() throws Exception {
		Script script = parseHelper.parse("""
				public class C {
					public constructor() {}
					public m() {}
				}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4ClassDeclaration classDecl = (N4ClassDeclaration) script.getScriptElements().get(0);
		performChecks(classDecl);
	}

	@Test
	public void testConstructorInN4JSD() throws Exception {
		Script script = parseHelper.parse("""
				export external public class C {
					public constructor()
					public m()
				}
				""", JavaScriptVariant.external);

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4ClassDeclaration classDecl = (N4ClassDeclaration) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		performChecks(classDecl);
	}

	private void performChecks(N4ClassDeclaration classDecl) {
		assertEquals("C", classDecl.getName());

		N4MethodDeclaration ctorDecl = (N4MethodDeclaration) classDecl.getOwnedMembers().get(0);
		assertTrue(ctorDecl.getDefinedType() instanceof TMethod);
		TMethod ctor = (TMethod) ctorDecl.getDefinedType();
		assertSame(ctorDecl, ctor.getAstElement());

		assertEquals("constructor", ctorDecl.getName());
		assertEquals("constructor", ctor.getName());
		assertTrue(ctorDecl.isConstructor());
		assertTrue(ctor.isConstructor());
		assertNull(ctorDecl.getDeclaredReturnTypeRefInAST());
		assertTrue(ctor.getReturnTypeRef() instanceof DeferredTypeRef);

		N4MethodDeclaration mDecl = (N4MethodDeclaration) classDecl.getOwnedMembers().get(1);
		TMethod m = (TMethod) mDecl.getDefinedType();
		assertEquals("m", mDecl.getName());
		assertEquals("m", m.getName());
		assertFalse(mDecl.isConstructor());
		assertFalse(m.isConstructor());

		N4JSResource.postProcessContainingN4JSResourceOf(classDecl);
		assertEquals("this[C]", ctor.getReturnTypeRef().getTypeRefAsString());
	}
}
