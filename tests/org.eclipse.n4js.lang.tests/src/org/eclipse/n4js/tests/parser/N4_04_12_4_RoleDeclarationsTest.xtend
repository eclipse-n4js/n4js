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

import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.junit.Test

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_04_12_4_RoleDeclarationsTest extends AbstractParserTest {


	@Test
	def void testEmptyDeclarations() {
		val script = parseHelper.parse('''
			interface R {}

		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(1, script.scriptElements.size);
		assertTrue(script.scriptElements.get(0) instanceof N4InterfaceDeclaration)

	}

	@Test
	def void testRoleDeclarations() {
		val script = parseHelper.parse('''
			public interface A {
				foo() { return null}
				public abstract bar(p: A): any
			}

			public interface C<T extends A> {
				<S> foo(p: union{A,C}) { return null; }
				abstract bar()
				baz(p: A?) { }
			}
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testRoleExample1() {
		val script = parseHelper.parse('''
			interface R {
				data: any;
				foo(): void {}
			}
			interface S extends R {
				bar(): void {}
			}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}


	@Test
	def void testRoleInterfaceExample() {
		val script = parseHelper.parse('''
			interface I {
				foo(): void
			}
			interface I2 extends I {
				bar(): void
			}
			interface R extends I {
				data: any;
				foo(): void {}
			}
			interface S extends R, I2 {
				bar(): void {}
			}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

}
