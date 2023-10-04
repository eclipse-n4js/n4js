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
package org.eclipse.n4js.tests.parser

import org.junit.Test

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_4_18_4_FieldAccessorsTest extends AbstractParserTest{


	@Test
	def void testGetterSetterExample() {
		val script = parseHelper.parse('''
			class A {}

			class C {
				private _data: A = null;

				public get data() {
					if (this._data==null) {
						this._data = new A();
					}
					return this._data;
				}

				public set data(data: A) {
					this._data = data;
					this.notifyListeners();
				}

				notifyListeners(): void {
					// ...
				}
			}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

}
