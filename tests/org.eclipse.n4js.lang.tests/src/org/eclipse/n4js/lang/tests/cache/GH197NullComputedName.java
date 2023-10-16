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
package org.eclipse.n4js.lang.tests.cache;

import java.util.Iterator;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.postprocessing.ASTMetaInfoCache;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tests.parser.AbstractParserTest;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.Test;

/**
 * Test for GH-197 bug
 */
public class GH197NullComputedName extends AbstractParserTest {

	@Test
	public void testNullComputedNameNoCacheMiss() throws Exception {
		Script script1 = parseHelper.parse("""
				let o: ~Object with {prop: ~Object with {propNested: string}} = {
					[missing]: {
						propNested: null
					}
				};
				""");

		testNoCacheMiss(script1);

		Script script2 = parseHelper.parse("""
				export public const myConst = {
				    [p1]: {s
				        p2: val,
				    }
				};
				""");

		testNoCacheMiss(script2);
	}

	@Test
	public void testNotNullComputedNameNoCacheMiss() throws Exception {
		Script script1 = parseHelper.parse("""
				const someProp = 'someProp'
				let o: ~Object with {prop: ~Object with {propNested: string}} = {
					[someProp]: {
						propNested: null
					}
				};
				""");

		testNoCacheMiss(script1);

		Script script2 = parseHelper.parse("""
				const p1 = 'p1'
				export public const myConst = {
				    [p1]: {s
				        p2: val,
				    }
				};
				""");

		testNoCacheMiss(script2);
	}

	public void testNoCacheMiss(Script script) {
		N4JSResource res = (N4JSResource) script.eResource();
		res.performPostProcessing(null);

		ASTMetaInfoCache cache = res.getASTMetaInfoCacheVerifyContext();
		Iterator<TypableElement> allTypableASTNodes = IteratorExtensions.filter(script.eAllContents(),
				TypableElement.class);
		// The type of each typable element must have been cached
		while (allTypableASTNodes.hasNext()) {
			// will throw exception in case of cache miss
			cache.getType(RuleEnvironmentExtensions.newRuleEnvironment(script), allTypableASTNodes.next());
		}
	}
}
