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
package org.eclipse.n4js.transpiler.es.tests

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithMockProject
import org.eclipse.n4js.n4JS.Script
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithMockProject)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GHOLD_184_TemplateStringTest extends AbstractTranspilerTest {

	@Inject extension ParseHelper<Script>

	@Test
	def void test_Compile() throws Throwable{

		val script = '''
			 var foo = `\n party`;
			 var bar = "\n party";
			 console.log(foo === bar); //should be true but is false

			 var foo2 = `"${bar}"`
			 var bar2 = '"\n party"';
		''';

		val moduleWrapped = '''
			'use strict';
			System.register([], function($n4Export) {
				var foo, bar, foo2, bar2;
				return {
					setters: [],
					execute: function() {
«««	The following line is wrong (BUG GHOLD-184):
«««						foo = '\\n party';
««« Correct version should be:
						foo = "\n party";
						bar = "\n party";
						console.log(foo === bar);
						foo2 = ("\"" + bar + "\"");
						bar2 = '"\n party"';
					}
				};
			});
		'''.cjsPatched;

	 	// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript;

   		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet);
		scriptNode.resolveLazyRefs;

		assertCompileResult(scriptNode,moduleWrapped);
	}

}
