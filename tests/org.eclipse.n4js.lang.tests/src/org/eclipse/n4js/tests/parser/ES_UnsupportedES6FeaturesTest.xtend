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

class ES_UnsupportedES6FeaturesTest extends AbstractParserTest {

	// single name syntax in object literal temporarily disallowed (not supported by V8 yet)
	@Test
	def void testObjectInitializer_NoSingleName() {
		val script = 'x = {a,prop:b};'.parse
		assertEquals(1,script.eResource.errors.size)
		assertEquals(
				"Single name syntax in object literals is unsupported at the moment (only allowed in object destructuring patterns).",
				script.eResource.errors.get(0).message)
	}

	// single name syntax in object literal allowed in certain cases ...
	@Test
	def void testObjectInitializer_SingleNamePositiveCases() {
		val script = '''
			({a,prop:b}=null); // simple case
			({a,prop1:{b},prop2:[x,{c},y]}=null); // nesting cases
			for({a,prop1:{b},prop2:[x,{c},y]} in null){} // for..in loop
			for({a,prop1:{b},prop2:[x,{c},y]} of null){} // for..of loop
		'''.parse
		assertTrue(script.eResource.errors.toString,script.eResource.errors.empty);
	}
}
