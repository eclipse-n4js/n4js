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

import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.junit.Test
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_04_12_2_ClassDeclarationsTest extends AbstractParserTest{


	@Test
	def void testEmptyDeclarations() {
		val script = '''
			class C {}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(1, script.scriptElements.size);
		assertTrue(script.scriptElements.get(0) instanceof N4ClassDeclaration)
	}

	@Test
	def void testClassDeclarations() {
		val script = '''
			public class A {
				private a: A;
				public 	b: A = null;

				foo() {}
				public bar(p: A): any { }
			}

			public abstract class C<T extends A> {
				t: T;
				a: A;

				static STATICVAR: any;


				static create(): C<T> { return null; }
				<S> foo(p: union{A,C}) {}
				protected abstract bar()
				baz(p: A?) {}
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testClassDeclarations2() {
		val script = '''
			public class A {}
			public class B {}

			public class C<T extends A> {
				<S> foo(p: union{A,B}) {}
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val cdecl = script.scriptElements.get(2) as N4ClassDeclaration
		assertEquals("C", cdecl.name)
		val methodDecl = cdecl.ownedMembers.get(0) as N4MethodDeclaration;
		assertEquals("foo", methodDecl.name);
		val fpar = methodDecl.fpars.get(0)
		assertEquals("p", fpar.name)
		val fparType = fpar.declaredTypeRefInAST as UnionTypeExpression
		val unionElement0 = fparType.typeRefs.get(0) as ParameterizedTypeRef
		assertEquals("A", unionElement0.declaredType.name)
		val unionElement1 = fparType.typeRefs.get(1) as ParameterizedTypeRef
		assertEquals("B", unionElement1.declaredType.name)
		assertNotEquals(cdecl, unionElement1.declaredType) // decl vs. type

	}

	@Test
	def void testMethodDeclarations() {
		val script = '''
			public class A {

				f1(): void {}

				abstract f1(): void
				abstract f2(): void;

				@Internal
				public abstract f3(): void

				protected f4(): any { return null; }

				public f5(): any { return null; }

				@Internal
				public <T> f6(): T { return null; }

				@Internal
				public f7(p1: any, p2: any): any { return p1; }
				private <T> f8(p1: T, p2: T):any { }

				@Internal
				public f9(p1: any, p2: any?): any {}
				@Internal
				public f10(p1: any, p2: any): any {}
				@Internal
				public f11(p1: any?, p2: any?): any {}

				static s1(): void {}
				static s1(): any { return null; }
				@Internal
				public static <T> s1(): T { return null; }
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testMethodDeclarationsTypeScriptNotation() {
		val script = '''
			public class A {

				f1(): void {}

				abstract f1() : void
				abstract f2() : void;

				@Internal
				public abstract f3() : void

				protected f4(): any { return null; }

				public f5() : any { return null; }

				@Internal
				public <T> f6(): T { return null; }

				@Internal
				public f7(p1: any, p2: any): any { return p1; }
				private <T> f8(p1: T, p2: T): void { }

				@Internal
				public f9(p1: any, p2: any?):any {}
				@Internal
				public f10(p1: any, p2: any): any {}
				@Internal
				public f11(p1: any?, p2: any?): void {}

				static s1(): void {}
				static s1(): any { return null; }
				@Internal
				public static <T> s1(): T { return null; }
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testClassExample() {
		val script = '''
			class C {
				data: any;

				constructor(data: any) {
					this.data = data;
				}

				foo(): void { }
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testClassDeclarationWithRolesAndInterfacesExample() {
		val script = '''
			interface I {
				foo(): void
			}
			interface R {
				bar(): void {}
			}
			class C{}
			class X extends C implements R, I {
				@Overrides
				foo(): void;
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testGetterSetterExample() {
		'''
			class A {}

			class C {
				data: A = null;

				get A() {
					if (data==null) {
						data = new A();
					}
					return this.data;
				}

				set A(data: A) {
					this.data = data;
					this.notifyListeners();
				}

				notifyListeners(): void {
					// ...
				}
			}
		'''.parseESSuccessfully
	}

	@Test
	def void testComputedFieldNames() {
		'''
			class C1 {
				['m']: string = "1";
				m: string = "2";
				["@abc"]: string = "3";
				[    '@abc1'    ]: string = "4";
				[ /*comment*/ '@abc2']: string = "5";
				['@abc3'  /*comment*/ ]: string = "6";
			}
		'''.parseESSuccessfully
	}

	@Test
	def void testFieldTypes() {
		'''
			class C1 {
				s: any = null;
				s: any = null;
				s = null;
			}
		'''.parseESSuccessfully
	}


}
