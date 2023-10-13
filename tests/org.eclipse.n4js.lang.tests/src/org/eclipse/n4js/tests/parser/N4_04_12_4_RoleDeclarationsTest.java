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

import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
public class N4_04_12_4_RoleDeclarationsTest extends AbstractParserTest {

	@Test
	public void testEmptyDeclarations() throws Exception {
		Script script = parseHelper.parse("""
					interface R {}

				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(1, script.getScriptElements().size());
		assertTrue(script.getScriptElements().get(0) instanceof N4InterfaceDeclaration);

	}

	@Test
	public void testRoleDeclarations() throws Exception {
		Script script = parseHelper.parse("""
					public interface A {
						foo() { return null}
						public abstract bar(p: A): any
					}

					public interface C<T extends A> {
						<S> foo(p: union{A,C}) { return null; }
						abstract bar()
						baz(p: A?) { }
					}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testRoleExample1() throws Exception {
		Script script = parseHelper.parse("""
					interface R {
						data: any;
						foo(): void {}
					}
					interface S extends R {
						bar(): void {}
					}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testRoleInterfaceExample() throws Exception {
		Script script = parseHelper.parse("""
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
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

}
