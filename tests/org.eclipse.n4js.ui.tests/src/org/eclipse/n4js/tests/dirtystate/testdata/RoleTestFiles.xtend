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

class RoleTestFiles {

	def static roleA() '''
		export interface ARole {

			public myMethodA() {}
		}
	'''

	def static roleAChanged() '''
		export interface ARole {

			public myMethodA2() {}
		}
	'''

	def static moduleFolder() {
		"roles"
	}

	def static roleB() '''
		import { ARole } from "«moduleFolder»/ARole"
		export interface BRole extends ARole {

			public myMethodB() {
				this.myMethodA()
			}
		}
	'''

	def static roleBChanged() '''
		import { ARole } from "«moduleFolder»/ARole"
		export interface BRole extends ARole {

			public myMethodB2() {
				this.myMethodA()
			}
		}
	'''

	def static roleBChanged2() '''
		import { ARole } from "«moduleFolder»/ARole"
		export interface BRole /*extends ARole*/ {

			public myMethodA() {

			}
		}
	'''

	def static roleBChanged3() '''
		import { ARole } from "«moduleFolder»/ARole"
		export interface BRole /*extends ARole*/ {

			public myMethodA() {

			}

			public myMethodB() {

			}
		}
	'''

	def static roleC() '''
		import { BRole } from "«moduleFolder»/BRole"
		export interface CRole extends BRole {
			name : String;

			myMethodC() {
				this.myMethodA()
				this.myMethodB()
			}
		}
	'''

	def static classD() '''
		export class D {

			public myMethodB() {
			}
		}
	'''

	def static classDChanged() '''
		export class D {

			public myMethodB2() {
			}
		}
	'''

	def static classE() '''
		import { D } from "«moduleFolder»/D"
		import { BRole } from "«moduleFolder»/BRole"
		export class E extends D implements BRole {

			public myMethodE() {
				this.myMethodB()
			}
		}
	'''

	def static classEChanged() '''
		import { D } from "«moduleFolder»/D"
		import { BRole } from "«moduleFolder»/BRole"
		export class E extends D /*extends BRole*/ {

			public myMethodE() {
				this.myMethodB()
			}
		}
	'''
}
