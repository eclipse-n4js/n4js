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
package org.eclipse.n4js.ts.parser

import com.google.inject.Inject
import org.eclipse.n4js.ts.TypesInjectorProvider
import org.eclipse.n4js.ts.types.TypeDefs
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.runner.RunWith
import org.junit.Test
import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(TypesInjectorProvider)
/**
 */
class TypeDefsParserTest {

	@Inject
	extension ParseHelper<TypeDefs>

	@Test
	def void testEmptyClass() {
		val typedefs = '''
			public class C {
			}
		'''.parse;
		assertNotNull(typedefs);
		assertTrue(typedefs.eResource.errors.toString, typedefs.eResource.errors.empty)
	}

	@Test
	def void testClassFields() {
		val typedefs = '''
			primitive number{}
			primitive int{}
			primitive string{}
			publicInternal class A {
				public m1: string
				publicInternal m2: string
				private m2: int
			}
		'''.parse;
		assertNotNull(typedefs);
		assertTrue(typedefs.eResource.errors.toString, typedefs.eResource.errors.empty)
	}


	@Test
	def void testClassMethods() {
		val typedefs = '''
			primitive number{}
			primitive int{}
			primitive string{}
			publicInternal class A {
				publicInternal foo(): int
				publicInternal abstract bar(): void
				protectedInternal static doST(): int
				protected baz(aInt: int, aString: string, a: A): void
				public calc(a: A?): int
				project calc2(a: A+): int
			}
		'''.parse;
		assertNotNull(typedefs);
		assertTrue(typedefs.eResource.errors.toString, typedefs.eResource.errors.empty)
	}

	@Test
	def void testClassGenenerics() {
		val typedefs = '''
			public class C<S,T>{}
			publicInternal class G<T> extends G<T,T> {
				publicInternal foo(): T
				publicInternal abstract bar(): T
				protectedInternal static doST(): T
				protected baz(aT: T, aString: string, a: A): void
				public calc(aT: T?): T
				public calc2(aC: C<T,?>): T
				public calc3(aC: C<? extends C,?>): T
				publicInternal static <U> gen1(aU: U): T
				publicInternal static <U> gen2(aT: T): U
			}
		'''.parse;
		assertNotNull(typedefs);
		assertTrue(typedefs.eResource.errors.toString, typedefs.eResource.errors.empty)
	}

	@Test
	def void testCRI() {
		val typedefs = '''
			publicInternal class C {}
			publicInternal interface I {}
			publicInternal interface R{}
			publicInternal class A extends C implements R, I {}
		'''.parse;
		assertNotNull(typedefs);
		assertTrue(typedefs.eResource.errors.toString, typedefs.eResource.errors.empty)
	}

}
