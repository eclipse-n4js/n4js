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

public class EnumTestFiles {

	public static String myEnum() {
		return """
				export public enum MyEnum {

					ONE,
					TWO,
					THREE
				}
				""";
	}

	public static String myEnum_changed() {
		return """
				export public enum MyEnum {

					ONE_HALF,
					TWO,
					THREE
				}
				""";
	}

	public static String myEnumUser() {
		return """
				import { MyEnum } from "%s/MyEnum"

				class A {

					execute() {
						var myEnum = MyEnum.ONE
					}
				}
				""".formatted(moduleFolder());
	}

	public static String moduleFolder() {
		return "enums";
	}
}
