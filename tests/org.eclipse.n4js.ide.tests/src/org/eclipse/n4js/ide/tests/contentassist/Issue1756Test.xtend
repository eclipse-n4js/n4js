/** 
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.contentassist

import org.eclipse.n4js.ide.tests.helper.server.AbstractCompletionTest
import org.junit.Test

/**
 * see https://github.com/eclipse/n4js/issues/1756
 */
class Issue1756Test extends AbstractCompletionTest {

	@Test
	def void test01() {
		testAtCursor('''
			let value;
			
			export function foo(): any {
				return (arg) => {
					let x = async () => {
						value = new C((p: Object) => {
							someFun<|>
						});
						return null;
					};
				}
			}
			
			class C {
				constructor(f: Function) {}
			}
			
			function someFunctionWithAnExtremelyLooooongName() {}
		''', ''' 
			(someFunctionWithAnExtremelyLooooongName, Function, someFunctionWithAnExtremelyLooooongName, , , 00000, , , , ([6:4 - 6:11], someFunctionWithAnExtremelyLooooongName), [], [], , )
		''');
	}

	@Test
	def void test02() {
		testAtCursor('''
			export function xxxxx(): any {
				((arg) => {
					let x = async () => {
						value = new C((p: Object) => {
							xxx<|>
						});
					};
				})
			}
			function xxxx() {}
		''', ''' 
			(xxxx, Function, xxxx, , , 00000, , , , ([4:4 - 4:7], xxxx), [], [], , )
			(xxxxx, Function, MyModule, , , 00001, , , , ([4:4 - 4:7], xxxxx), [], [], , )
		''');
	}

}
