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
package org.eclipse.n4js.n4idl.lang

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4idl.tests.helper.N4IDLParseHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.runner.RunWith

/**
 * N4JSX version of N4JS' AbstractParserTest
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public abstract class AbstractN4IDLParserTest extends Assert {
	@Inject
	protected extension N4IDLParseHelper

	protected def Script parseSuccessfully(CharSequence js) {
		val script = js.parseN4IDL
		assertTrue(script.eResource.errors.join('\n')[line + ': ' + message], script.eResource.errors.empty)
		return script
	}

	protected def parseWithError(CharSequence js) {
		val script = js.parseN4IDL
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
		return script
	}
}
