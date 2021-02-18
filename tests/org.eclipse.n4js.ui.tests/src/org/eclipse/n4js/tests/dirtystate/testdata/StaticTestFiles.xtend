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

/**
 */
class StaticTestFiles {

	def static moduleFolder() {
		"static"
	}

	def static caller() '''
		import { Callee } from "«moduleFolder»/Callee"
		import * as SC from "«moduleFolder»/SubCallee"

		export public class Caller {

			@Internal public call() {
				var a = Callee.myStaticField
				Callee.myStaticMethod
				a = Callee.myPrivateStaticAccessor
				Callee.myPrivateStaticAccessor = a

				var callee : Callee = new Callee
				callee.myNonStaticField
				callee.myNonStaticMethod
				a = callee.myPrivateNonStaticAccessor
				callee.myPrivateNonStaticAccessor = a

				var subCallee : SC.SubCallee = new SC.SubCallee
				subCallee.myNonStaticField
				subCallee.myNonStaticMethod
				subCallee.call
				SC.SubCallee.callStatic
				SC.SubCallee.myStaticField
				SC.SubCallee.myStaticMethod
				SC.SubCallee.callStatic

				var subCallee2 : Callee = new SC.SubCallee
				subCallee2.myNonStaticField
				subCallee2.myNonStaticMethod

				var fun1 = function(cCallee : constructor{Callee}) {
					return new cCallee ()
				}

				fun1(SC.SubCallee)

				var fun2 = function(tCallee : type{Callee}) {
					// NOTE (mor): had to comment out following line after adding validation that new X() requires a ConstructorTypeRef
					//return new tCallee () // have to be marked with an error
				}

				fun2(SC.SubCallee)
			}
		}
	'''

	def static callee() '''
		export public class Callee {
			«calleeStaticMembers»

			«calleeNonStaticMembers»

			«calleeStaticAccessors»

			«calleeNonStaticAccessors»
		}
	'''

	def private static calleeStaticMembers() '''
		@Internal public static myStaticField : string = "myStaticField";

		@Internal public static myStaticMethod() : string {
			return "myStaticMethod";
		}
	'''

	def private static calleeNonStaticMembers() '''
		@Internal public myNonStaticField : string = "myNonStaticField";

		@Internal public myNonStaticMethod() : string {
			return "myNonStaticMethod";
		}
	'''

	def private static calleeStaticAccessors() '''
	   private static myPrivateStaticField : string = "myPrivateStaticField";

	   @Internal public static get myPrivateStaticAccessor() : string {
	   		return this.myPrivateStaticField;
	   }

	   @Internal public static set myPrivateStaticAccessor(myPrivateStaticParam : string) {
	   		/*this*/Callee.myPrivateStaticField = myPrivateStaticParam;
	   }
	'''

	def private static calleeNonStaticAccessors() '''
	   private myPrivateNonStaticField : string = "myPrivateField";

	   @Internal public get myPrivateNonStaticAccessor() : string {
	   		return this.myPrivateNonStaticField;
	   }

	   @Internal public set myPrivateNonStaticAccessor(myPrivateParam : string) {
	   		this.myPrivateNonStaticField = myPrivateParam;
	   }
	'''

	def static callee_changedStaticMember() '''
		export public class Callee {
			«calleeStaticMembersChanged»

			«calleeNonStaticMembers»

			«calleeStaticAccessors»

			«calleeNonStaticAccessors»
		}
	'''

	def private static calleeStaticMembersChanged() '''
		@Internal public myStaticField : string = "myStaticField";

		@Internal public static myStaticMethod() : string {
			return "myStaticMethod";
		}
	'''

	def static callee_changedNonStaticAccessors() '''
		export public class Callee {
			«calleeStaticMembers»

			«calleeNonStaticMembers»

			«calleeStaticAccessors»

			«calleeNonStaticAccessorsChanged»
		}
	'''

	def private static calleeNonStaticAccessorsChanged() '''
	   private myPrivateNonStaticField : string = "myPrivateField";

	   @Internal public static get myPrivateNonStaticAccessor() : string {
	   		return this.myPrivateNonStaticField;
	   }

	   @Internal public set myPrivateNonStaticAccessor(myPrivateParam : string) {
	   		this.myPrivateNonStaticField = myPrivateParam;
	   }
	'''

	def static subCallee() '''
		import { Callee } from "«moduleFolder»/Callee"

		export public class SubCallee extends Callee {

		   @Internal public call() {
				this.myNonStaticField
		   }

		   @Internal public static callStatic() {
				this.myStaticField
		   }

		   @Override
		   @Internal public static myStaticMethod() : string {
				return "myStaticMethod";
		   }
		}
	'''

	def static subCallee_changed() '''
		import { Callee } from "«moduleFolder»/Callee"

		export public class SubCallee extends Callee {

		   @Internal public call() {
				this.myNonStaticField
		   }

		   @Internal public static callStatic() {
				this.myStaticField
		   }

		   @Internal public static myStaticMethod2() : string {
				return "myStaticMethod";
		   }
		}
	'''

	def static A() '''
		import { B } from "«moduleFolder»/B"

		export public class A {

			@Internal public execute() {
				var b : B;
				b.c.d.e
			}

			@Internal public execute2() {
				B.c.d.e
			}
		}
	'''

	def static B() '''
		import { C } from "«moduleFolder»/C"

		export public class B {

			@Internal public get c() : type{C} {
				return null;
			}

			@Internal public static get c() : C {
				return null;
			}
		}
	'''

	def static C() '''
		import { D } from "«moduleFolder»/D"

		export @Internal public class C {

			@Internal public get d() : D {
				return null;
			}

			@Internal public static get d() : type{D} {
				return null;
			}
		}
	'''

	def static C_changed() '''
		import { D } from "«moduleFolder»/D"

		export public class C {

			@Internal public get d() : D {
				return null;
			}

			@Internal public static get d2() : type{D} {
				return null;
			}
		}
	'''

	def static D() '''
		export public class D {

			@Internal public get e() {
				return null;
			}

			@Internal public static get e() {
				return null;
			}
		}
	'''
}
