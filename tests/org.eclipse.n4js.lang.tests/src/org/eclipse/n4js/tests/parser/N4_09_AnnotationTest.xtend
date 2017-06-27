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

class N4_09_AnnotationTest extends AbstractParserTest{

	@Test
	def void testScriptAnnotationExample() {
		val script = '''
			@@DoNotCompile
			import { A } from "p/A"
			@Force
			import { B } from "p/B"
			@Exlude
			import { C } from "p/C"

			@Internal @Final @NoInstantiate
			public class C {
				@ReadOnly
				public A = "Hello";
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(4, script.scriptElements.size);
	}

}
