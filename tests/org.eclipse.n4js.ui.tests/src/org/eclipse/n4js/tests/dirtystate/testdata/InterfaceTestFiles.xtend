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

class InterfaceTestFiles {

	def static moduleFolder() {
		"interfaces"
	}

	def static interfaceA() '''
		export interface InterfaceA {

			public methodIA();

		}
	'''

	def static interfaceAChanged() '''
		export interface InterfaceA {

			public methodIA2();

		}
	'''

	def static interfaceB() '''
		import { InterfaceA } from "«moduleFolder»/InterfaceA"
		export interface InterfaceB extends InterfaceA {

			public methodIB();

		}
	'''

	def static classWithInterfaces() '''
		import { InterfaceB } from "«moduleFolder»/InterfaceB"
		abstract class ClassWithInterfaces implements InterfaceB {

			test() : void {
				this.methodIA
				this.methodIB
			}
		}
	'''
}
