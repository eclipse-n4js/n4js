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

class CaseSensitiveTestFiles {

	def static caller() '''
		import { CaseSensitiveCallee } from "«moduleFolder»/CaseSensitiveCallee"
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
	'''

	def static callee() '''
		export public class CaseSensitiveCallee {

			@Internal public mymethod() {

			}

			@Internal public myMethod() {

			}

			@Internal public mYMETHOD() {

			}
		}
	'''

	def static calleeChanged() '''
		export public class CaseSensitiveCallee {

			@Internal public mymethod2() {

			}

			@Internal public myMethod() {

			}

			@Internal public mYMETHOD() {

			}
		}
	'''

	def static moduleFolder() {
		"casesensitive"
	}
}
