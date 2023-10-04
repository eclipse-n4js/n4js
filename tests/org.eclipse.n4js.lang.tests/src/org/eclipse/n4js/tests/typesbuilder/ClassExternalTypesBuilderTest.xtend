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

import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.junit.Test

/**
 * Parser tests for external modifier for classes.
 */
class ClassExternalTypesBuilderTest extends AbstractParserTest {

	@Test
	def void testClassExternal() {
		val script = parseHelper.parse('''
			public class C {
			}
			external public class D {
			}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val cdecl = script.scriptElements.get(0) as N4ClassDeclaration
		assertEquals("C", cdecl.getName)
		assertFalse(cdecl.external)
		assertFalse(cdecl.definedTypeAsClass.external)

		val ddecl = script.scriptElements.get(1) as N4ClassDeclaration
		assertEquals("D", ddecl.getName)
		assertTrue(ddecl.external)
		assertTrue(ddecl.definedTypeAsClass.external)
	}
}
