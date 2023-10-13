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

import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.junit.Test;

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
public class N4_04_12_2_ClassDeclarationsTest extends AbstractParserTest {

	@Test
	public void testEmptyDeclarations() throws Exception {
		Script script = parseHelper.parse("""
					class C {}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(1, script.getScriptElements().size());
		assertTrue(script.getScriptElements().get(0) instanceof N4ClassDeclaration);
	}

	@Test
	public void testClassDeclarations() throws Exception {
		Script script = parseHelper.parse("""
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
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testClassDeclarations2() throws Exception {
		Script script = parseHelper.parse("""
					public class A {}
					public class B {}

					public class C<T extends A> {
						<S> foo(p: union{A,B}) {}
					}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4ClassDeclaration cdecl = (N4ClassDeclaration) script.getScriptElements().get(2);
		assertEquals("C", cdecl.getName());
		N4MethodDeclaration methodDecl = (N4MethodDeclaration) cdecl.getOwnedMembers().get(0);
		assertEquals("foo", methodDecl.getName());
		FormalParameter fpar = methodDecl.getFpars().get(0);
		assertEquals("p", fpar.getName());
		UnionTypeExpression fparType = (UnionTypeExpression) fpar.getDeclaredTypeRefInAST();
		ParameterizedTypeRef unionElement0 = (ParameterizedTypeRef) fparType.getTypeRefs().get(0);
		assertEquals("A", unionElement0.getDeclaredType().getName());
		ParameterizedTypeRef unionElement1 = (ParameterizedTypeRef) fparType.getTypeRefs().get(1);
		assertEquals("B", unionElement1.getDeclaredType().getName());
		assertNotEquals(cdecl, unionElement1.getDeclaredType()); // decl vs. type

	}

	@Test
	public void testMethodDeclarations() throws Exception {
		Script script = parseHelper.parse("""
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
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testMethodDeclarationsTypeScriptNotation() throws Exception {
		Script script = parseHelper.parse("""
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
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testClassExample() throws Exception {
		Script script = parseHelper.parse("""
					class C {
						data: any;

						constructor(data: any) {
							this.data = data;
						}

						foo(): void { }
					}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testClassDeclarationWithRolesAndInterfacesExample() throws Exception {
		Script script = parseHelper.parse("""
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
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testGetterSetterExample() throws Exception {
		parseESSuccessfully("""
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
				""");
	}

	@Test
	public void testComputedFieldNames() throws Exception {
		parseESSuccessfully("""
					class C1 {
						['m']: string = "1";
						m: string = "2";
						["@abc"]: string = "3";
						[    '@abc1'    ]: string = "4";
						[ /*comment*/ '@abc2']: string = "5";
						['@abc3'  /*comment*/ ]: string = "6";
					}
				""");
	}

	@Test
	public void testFieldTypes() throws Exception {
		parseESSuccessfully("""
					class C1 {
						s: any = null;
						s: any = null;
						s = null;
					}
				""");
	}

}
