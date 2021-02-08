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

class TransitiveInheritMemberTestFiles {

	def static moduleFolder() {
		"transitivemember"
	}

	def static A() '''
		import { C } from "«moduleFolder»/C"
		export public class A {

			@Internal public myMethodA() {
				var c : C;
				c.myMethodBInC().myMethodC()
			}
		}
	'''

	def static B() '''
		import { C } from "«moduleFolder»/C"
		export public class B extends C {

			@Internal public myMethodB() : B {
				return null;
			}
		}
	'''

	def static C() '''
		import { B } from "«moduleFolder»/B"
		export public class C {

			@Internal public myMethodBInC() : B {
				return null;
			}

			@Internal public myMethodC() : C {
				return null;
			}
		}
	'''

	def static CChanged() '''
		import { B } from "«moduleFolder»/B"
		export public class C {

			@Internal public myMethodBInC() : B {
				return null;
			}

			@Internal public myMethodC2() : C {
				return null;
			}
		}
	'''
}
