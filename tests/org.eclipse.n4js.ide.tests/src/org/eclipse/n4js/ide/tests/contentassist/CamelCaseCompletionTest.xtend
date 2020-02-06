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

import org.eclipse.xtext.testing.AbstractLanguageServerTest
import org.junit.Test
import org.junit.Before
import org.eclipse.n4js.ide.N4JSIdeSetup
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.LanguageInfo
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints
import java.io.File

/** 
 */
class CamelCaseCompletionTest extends AbstractLanguageServerTest {
	/** 
	 */
	new() {
		super("n4js")
	}
	
	@Before
	override void setup() {
		val injector = new N4JSIdeSetup().createInjectorAndDoEMFRegistration
		injector.injectMembers(this)

		val resourceServiceProvider = resourceServerProviderRegistry.extensionToFactoryMap.get(fileExtension)
		if (resourceServiceProvider instanceof IResourceServiceProvider)
			languageInfo = resourceServiceProvider.get(LanguageInfo)

		// register notification callbacks
		languageServer.connect(ServiceEndpoints.toServiceObject(this, languageClientClass))
		// initialize
		languageServer.supportedMethods()

		// create workingdir
		root = new File(new File("").absoluteFile, TEST_PROJECT_PATH)
	}

	@Test def void testCamelCasePrefix_01() {
		testCompletion [ 
			model = 'EvE'
			column = model.length
			expectedCompletionItems = '''
				EvalError -> EvalError [[0, 0] .. [0, 3]]
			'''
		]
	}
	
	@Test def void testCamelCasePrefix_02() {
		testCompletion [ 
			model = 'eURIC'
			column = model.length
			expectedCompletionItems = '''
				encodeURIComponent -> encodeURIComponent [[0, 0] .. [0, 5]]
			'''
		]
	}
	
	@Test def void testCamelCasePrefix_03() {
		testCompletion [ 
			model = 'eUC'
			column = model.length
			expectedCompletionItems = '''
				encodeURIComponent -> encodeURIComponent [[0, 0] .. [0, 3]]
			'''
		]
	}
	
	@Test def void testCamelCasePrefix_04() {
		testCompletion [ 
			model = 'eC'
			column = model.length
			expectedCompletionItems = ''
		]
	}
}
