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

public class TransitiveInheritMemberTestFiles {

	public static String moduleFolder() {
		return "transitivemember";
	}

	public static String A() {
		return """
				import { C } from "%s/C"
				export public class A {

					@Internal public myMethodA() {
						var c : C;
						c.myMethodBInC().myMethodC()
					}
				}
				""".formatted(moduleFolder());
	}

	public static String B() {
		return """
				import { C } from "%s/C"
				export public class B extends C {

					@Internal public myMethodB() : B {
						return null;
					}
				}
				""".formatted(moduleFolder());
	}

	public static String C() {
		return """
				import { B } from "%s/B"
				export public class C {

					@Internal public myMethodBInC() : B {
						return null;
					}

					@Internal public myMethodC() : C {
						return null;
					}
				}
				""".formatted(moduleFolder());
	}

	public static String CChanged() {
		return """
				import { B } from "%s/B"
				export public class C {

					@Internal public myMethodBInC() : B {
						return null;
					}

					@Internal public myMethodC2() : C {
						return null;
					}
				}
				""".formatted(moduleFolder());
	}
}
