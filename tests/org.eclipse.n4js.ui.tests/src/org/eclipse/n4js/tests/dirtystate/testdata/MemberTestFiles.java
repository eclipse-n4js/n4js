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

public class MemberTestFiles {

	public static String moduleFolder() {
		return "members";
	}

	public static String myClassOne() {
		return """
				import * as N from "%s/MyVariableTwo"

				export public class MyClassOne  {

					@Internal public myMethodOne() : void {
						N.two.myMethodTwo().getElement().myMethodFour()
						// myMethodFour is not yet scoped!
						// two.myAttributeTwo("test").myMethodFour()
					}
				}
				""".formatted(moduleFolder());
	}

	public static String myVariableTwo() {
		return """
				import { MyClassTwo } from "%s/MyClassTwo"
				export public var two : MyClassTwo;
				""".formatted(moduleFolder());
	}

	public static String myClassTwo() {
		return """
				import { MyInterfaceFour } from "%s/MyInterfaceFour"
				import { MyRoleThree } from "%s/MyRoleThree"
				export public class MyClassTwo {
					@Internal public myAttributeTwo: {function(param: String): MyInterfaceFour};

					@Internal public myMethodTwo(): MyRoleThree {
						return null;
					}
				}
				""".formatted(moduleFolder(), moduleFolder());
	}

	public static String myRoleThree() {
		return """
				import { MyInterfaceFour } from "%s/MyInterfaceFour"
				export public interface MyRoleThree {
				    @Internal public element : MyInterfaceFour;

					@Internal public getElement() : MyInterfaceFour {
						return this.element;
					}
				}
				""".formatted(moduleFolder());
	}

	public static String myRoleThreeChanged() {
		return """
				import { MyInterfaceFour } from "%s/MyInterfaceFour"
				export public interface MyRoleThree {
				    @Internal public element : MyInterfaceFour;

					@Internal public getElement2() : MyInterfaceFour {
						return this.element;
					}
				}
				""".formatted(moduleFolder());
	}

	public static String myInterfaceFour() {
		return """
				export @Internal public interface MyInterfaceFour {

					@Internal public myMethodFour() : void;
				}
				""";
	}

	public static String myInterfaceFourChanged() {
		return """
				export @Internal public interface MyInterfaceFour {

					@Internal public myMethodFour2() : void;
				}
				""";
	}
}
