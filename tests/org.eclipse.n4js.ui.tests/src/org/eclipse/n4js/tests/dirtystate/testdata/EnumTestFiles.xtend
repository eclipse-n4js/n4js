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
class EnumTestFiles {

	def static myEnum() '''
		export public enum MyEnum {

			ONE,
			TWO,
			THREE
		}
	'''

	def static myEnum_changed() '''
		export public enum MyEnum {

			ONE_HALF,
			TWO,
			THREE
		}
	'''

	def static myEnumUser() '''
		import { MyEnum } from "«moduleFolder»/MyEnum"

		class A {

			execute() {
				var myEnum = MyEnum.ONE
			}
		}
	'''

	def static moduleFolder() {
		"enums"
	}
}
