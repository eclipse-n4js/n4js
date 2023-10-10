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

@SuppressWarnings("unused")
public class StaticTestFiles {

	public static String moduleFolder() {
		return "static";
	}

	public static String caller() {
		return """
				import { Callee } from "%s/Callee"
				import * as SC from "%s/SubCallee"

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
				"""
				.formatted(moduleFolder(), moduleFolder());
	}

	public static String callee() {
		return """
				export public class Callee {
					%s

					%s

					%s

					%s
				}
				""".formatted(calleeStaticMembers(), calleeNonStaticMembers(), calleeStaticAccessors(),
				calleeNonStaticAccessors());
	}

	private static String calleeStaticMembers() {
		return """
				@Internal public static myStaticField : string = "myStaticField";

				@Internal public static myStaticMethod() : string {
					return "myStaticMethod";
				}
				""";
	}

	private static String calleeNonStaticMembers() {
		return """
				@Internal public myNonStaticField : string = "myNonStaticField";

				@Internal public myNonStaticMethod() : string {
					return "myNonStaticMethod";
				}
				""";
	}

	private static String calleeStaticAccessors() {
		return """
				private static myPrivateStaticField : string = "myPrivateStaticField";

				@Internal public static get myPrivateStaticAccessor() : string {
					return this.myPrivateStaticField;
				}

				@Internal public static set myPrivateStaticAccessor(myPrivateStaticParam : string) {
					/*this*/Callee.myPrivateStaticField = myPrivateStaticParam;
				}
				""";
	}

	private static String calleeNonStaticAccessors() {
		return """
				private myPrivateNonStaticField : string = "myPrivateField";

				@Internal public get myPrivateNonStaticAccessor() : string {
					return this.myPrivateNonStaticField;
				}

				@Internal public set myPrivateNonStaticAccessor(myPrivateParam : string) {
					this.myPrivateNonStaticField = myPrivateParam;
				}
				""";
	}

	public static String callee_changedStaticMember() {
		return """
				export public class Callee {
					%s

					%s

					%s

					%s
				}
				""".formatted(calleeStaticMembersChanged(), calleeNonStaticMembers(), calleeStaticAccessors(),
				calleeNonStaticAccessors());
	}

	private static String calleeStaticMembersChanged() {
		return """
				@Internal public myStaticField : string = "myStaticField";

				@Internal public static myStaticMethod() : string {
					return "myStaticMethod";
				}
				""";
	}

	public static String callee_changedNonStaticAccessors() {
		return """
				export public class Callee {
					%s

					%s

					%s

					%s
				}
				""".formatted(calleeStaticMembers(), calleeNonStaticMembers(), calleeStaticAccessors(),
				calleeNonStaticAccessorsChanged());
	}

	private static String calleeNonStaticAccessorsChanged() {
		return """
				private myPrivateNonStaticField : string = "myPrivateField";

				@Internal public static get myPrivateNonStaticAccessor() : string {
					return this.myPrivateNonStaticField;
				}

				@Internal public set myPrivateNonStaticAccessor(myPrivateParam : string) {
					this.myPrivateNonStaticField = myPrivateParam;
				}
				""";
	}

	public static String subCallee() {
		return """
				import { Callee } from "%s/Callee"

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
				""".formatted(moduleFolder());
	}

	public static String subCallee_changed() {
		return """
				import { Callee } from "%s/Callee"

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
				""".formatted(moduleFolder());
	}

	public static String A() {
		return """
				import { B } from "%s/B"

				export public class A {

					@Internal public execute() {
						var b : B;
						b.c.d.e
					}

					@Internal public execute2() {
						B.c.d.e
					}
				}
				""".formatted(moduleFolder());
	}

	public static String B() {
		return """
				import { C } from "%s/C"

				export public class B {

					@Internal public get c() : type{C} {
						return null;
					}

					@Internal public static get c() : C {
						return null;
					}
				}
				""".formatted(moduleFolder());
	}

	public static String C() {
		return """
				import { D } from "%s/D"

				export @Internal public class C {

					@Internal public get d() : D {
						return null;
					}

					@Internal public static get d() : type{D} {
						return null;
					}
				}
				""".formatted(moduleFolder());
	}

	public static String C_changed() {
		return """
				import { D } from "%s/D"

				export public class C {

					@Internal public get d() : D {
						return null;
					}

					@Internal public static get d2() : type{D} {
						return null;
					}
				}
				""".formatted(moduleFolder());
	}

	public static String D() {
		return """
				export public class D {

					@Internal public get e() {
						return null;
					}

					@Internal public static get e() {
						return null;
					}
				}
				""";
	}
}
