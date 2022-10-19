/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json

import com.google.inject.Singleton
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic
import org.eclipse.xtext.testing.util.ParseHelper

import static org.junit.Assert.*

/**
 * A parse helper for JSON parsing tests.
 */
 @Singleton
class JSONParseHelper extends ParseHelper<JSONDocument> {
	/** 
	 * Asserts that the given {@code json} character sequence can be parsed correctly. Returns the
	 * resulting {@link JSONDocument} instance.
	 */
	public def JSONDocument parseSuccessfully(CharSequence json) {
		val doc = json.parse;
		assertTrue('''"«json»" ''' + doc.eResource.errors.join('\n')[line + ': ' + message], doc.eResource.errors.empty)
		return doc
	}
	
	/** 
	 * Asserts that the given {@code json} character sequences cannot be parsed correctly. 
	 */
	public def void parseUnsuccessfully(CharSequence json) {
		val doc = json.parse;
		assertFalse('''The following JSON text did not cause any syntax errors as expected: "«json»" ''', 
			doc.eResource.errors.filter(XtextSyntaxDiagnostic).empty);
	}
}
