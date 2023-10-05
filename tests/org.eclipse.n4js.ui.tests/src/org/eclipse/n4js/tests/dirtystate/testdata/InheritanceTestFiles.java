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
package org.eclipse.n4js.tests.dirtystate.testdata;

public class InheritanceTestFiles {

	public static String module1() {
		return "my/deep/nested/pack";
	}

	public static String module2() {
		return "my/deep/nested/pack2";
	}

	public static String A() {
		return """
				import { B } from "«module2»/B"
				export public class A {
					@Internal public getB() : B {return null;}
					@Internal public foo() : B {return null;}
				}
				var a : A;
				""";
	}

	public static String AOtherMethodName() {
		return """
				import { B } from "«module2»/B"
				export public class A {
					@Internal public getB2() : B {return null;}
					@Internal public foo2() : A {return null;}
				}
				var a : A;
				""";
	}

	public static String B() {
		return """
				import { A as AObjectLiteral } from "«module1»/A";
				export public class B {
					@Internal public foo() : B {return null;}
				}
				var a : AObjectLiteral = new AObjectLiteral();
				a.foo();
				var b : B = new B();
				""";
	}

	public static String C() {
		return """
				import { A as AObjectLiteral } from "«module1»/A";
				export public class C extends AObjectLiteral {
					@Internal public getA() : AObjectLiteral {return null;}
				}
				var c : C = new C;
				c.getB;
				""";
	}

	public static String CWithAssignmentToSuperType() {
		return """
				import { A as AObjectLiteral } from "«module1»/A";
				export public class C extends AObjectLiteral {
					@Internal public getA() : AObjectLiteral {return null;}
				}
				var c : C = new C;
				var b : AObjectLiteral = c.getB();
				""";
	}

	public static String CWithAssignmentToActualType() {
		return """
				import { A as AObjectLiteral } from "«module1»/A";
				import { B } from "«module2»/B"
				export public class C extends AObjectLiteral {
					@Internal public getA() : AObjectLiteral {return null;}
				}
				var c : C = new C;
				var b : B = c.getB()
				""";
	}

	public static String D() {
		return """
				import { C as CObjectLiteral } from "«module2»/C";
				import { A } from "«module1»/A"
				import { B } from "«module2»/B"
				export public class D {
				}
				var c : CObjectLiteral = new CObjectLiteral();
				var a : A = c.getA();
				var b : B = c.getB();
				""";
	}

	public static String inheritanceModule() {
		return "inheritance";
	}

	public static String Parent() {
		return """
				export public class Parent {
					@Internal public printlnParent() : any {return null;}
					@Internal public printlnToOverride() : any {return null;}
				}
				""";
	}

	public static String Child() {
		return """
				import { Parent as ParentObjectLiteral } from "«inheritanceModule»/Parent";
				export public class Child extends ParentObjectLiteral {
					@Override
					@Internal public printlnToOverride() : any {return null;}
					printlnChild() : any {return null;}
				}
				""";
	}
}
