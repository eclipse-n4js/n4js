/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.lang.tests.cache

import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.eclipse.n4js.ts.types.TypableElement
import org.junit.Test

/**
 * Test for GH-197 bug
 */
class GH197NullComputedName extends AbstractParserTest {

	@Test
	def void testNullComputedNameNoCacheMiss() {
		val script1 = '''
			let o: ~Object with {prop: ~Object with {propNested: string}} = {
				[missing]: {
					propNested: null
				}
			}; 
		'''.parse

		script1.testNoCacheMiss
		
		val script2 = '''
			export public const myConst = {
			    [p1]: {s
			        p2: val,
			    }
			};
		'''.parse

		script2.testNoCacheMiss
	}

	@Test
	def void testNotNullComputedNameNoCacheMiss() {
		val script1 = '''
			const someProp = 'someProp'
			let o: ~Object with {prop: ~Object with {propNested: string}} = {
				[someProp]: {
					propNested: null
				}
			}; 
		'''.parse

		script1.testNoCacheMiss

		val script2 = '''
			const p1 = 'p1'
			export public const myConst = {
			    [p1]: {s
			        p2: val,
			    }
			};
		'''.parse

		script2.testNoCacheMiss
	}

	def void testNoCacheMiss(Script script) {
		val res = script.eResource as N4JSResource
		res.performPostProcessing(null)
		
		val cache = res.getASTMetaInfoCacheVerifyContext();
		val allTypableASTNodes = script.eAllContents.filter(TypableElement);
		// The type of each typable element must have been cached 
		while (allTypableASTNodes.hasNext) {
			val typeRet = cache.getType(allTypableASTNodes.next);
			assertFalse(typeRet.failure)
		}
	}
}
