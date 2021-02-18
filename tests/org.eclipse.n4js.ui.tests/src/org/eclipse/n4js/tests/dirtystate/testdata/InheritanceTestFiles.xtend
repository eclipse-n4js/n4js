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
package org.eclipse.n4js.tests.dirtystate.testdata

class InheritanceTestFiles {

	def static module1() {
		"my/deep/nested/pack"
	}

	def static module2() {
		"my/deep/nested/pack2"
	}


	def static A() '''
		import { B } from "«module2»/B"
		export public class A {
			@Internal public getB() : B {return null;}
			@Internal public foo() : B {return null;}
		}
		var a : A;
	'''

	def static AOtherMethodName() '''
		import { B } from "«module2»/B"
		export public class A {
			@Internal public getB2() : B {return null;}
			@Internal public foo2() : A {return null;}
		}
		var a : A;
	'''

	def static B() '''
		import { A as AObjectLiteral } from "«module1»/A";
		export public class B {
			@Internal public foo() : B {return null;}
		}
		var a : AObjectLiteral = new AObjectLiteral();
		a.foo();
		var b : B = new B();
	'''

	def static C() '''
		import { A as AObjectLiteral } from "«module1»/A";
		export public class C extends AObjectLiteral {
			@Internal public getA() : AObjectLiteral {return null;}
		}
		var c : C = new C;
		c.getB;
	'''

	def static CWithAssignmentToSuperType() '''
		import { A as AObjectLiteral } from "«module1»/A";
		export public class C extends AObjectLiteral {
			@Internal public getA() : AObjectLiteral {return null;}
		}
		var c : C = new C;
		var b : AObjectLiteral = c.getB();
	'''

	def static CWithAssignmentToActualType() '''
		import { A as AObjectLiteral } from "«module1»/A";
		import { B } from "«module2»/B"
		export public class C extends AObjectLiteral {
			@Internal public getA() : AObjectLiteral {return null;}
		}
		var c : C = new C;
		var b : B = c.getB()
	'''

	def static D() '''
		import { C as CObjectLiteral } from "«module2»/C";
		import { A } from "«module1»/A"
		import { B } from "«module2»/B"
		export public class D {
		}
		var c : CObjectLiteral = new CObjectLiteral();
		var a : A = c.getA();
		var b : B = c.getB();
	'''

	def static inheritanceModule() {
		"inheritance"
	}

	def static Parent() '''
		export public class Parent {
			@Internal public printlnParent() : any {return null;}
			@Internal public printlnToOverride() : any {return null;}
		}
	'''

	def static Child() '''
		import { Parent as ParentObjectLiteral } from "«inheritanceModule»/Parent";
		export public class Child extends ParentObjectLiteral {
			@Override
			@Internal public printlnToOverride() : any {return null;}
			printlnChild() : any {return null;}
		}
	'''
}
