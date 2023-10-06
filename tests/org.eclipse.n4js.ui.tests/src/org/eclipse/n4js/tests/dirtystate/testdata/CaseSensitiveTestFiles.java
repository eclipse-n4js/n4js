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

public class CaseSensitiveTestFiles {

	public static String caller() {
		return """
				import { CaseSensitiveCallee } from "%s/CaseSensitiveCallee"
				export public class CaseSensitiveCaller {
					@Internal public callee : CaseSensitiveCallee;

					@Internal public lowerCaseCaller() {
						this.callee.mymethod()
					}

					@Internal public mixedCaseCaller() {
						this.callee.myMethod()
					}

					@Internal public upperCaseCaller() {
						this.callee.mYMETHOD()
					}
				}
				""".formatted(moduleFolder());
	}

	public static String callee() {
		return """
				export public class CaseSensitiveCallee {

					@Internal public mymethod() {

					}

					@Internal public myMethod() {

					}

					@Internal public mYMETHOD() {

					}
				}
				""";
	}

	public static String calleeChanged() {
		return """
				export public class CaseSensitiveCallee {

					@Internal public mymethod2() {

					}

					@Internal public myMethod() {

					}

					@Internal public mYMETHOD() {

					}
				}
				""";
	}

	public static String moduleFolder() {
		return "casesensitive";
	}
}
