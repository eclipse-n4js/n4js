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
package org.eclipse.n4js.json.tests.validation

import com.google.inject.Inject
import org.eclipse.n4js.json.JSONInjectorProvider
import org.eclipse.n4js.json.JSONParseHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.json.validation.JSONIssueCodes

/**
 * Tests that checks for the validation of comments in JSON resources.
 */
@RunWith(XtextRunner)
@InjectWith(JSONInjectorProvider)
class JSONCommentValidationTest extends Assert {

	@Inject extension JSONParseHelper
	@Inject extension ValidationTestHelper
	
	/**
	 * Tests that comments in JSON resources cause warnings
	 */
	@Test def void testCommentsValidation() {
		'''
		//single line comment
		{
			"content": "json",
			"other": "key"
			/*
			   multi-line comment
			*/
		}'''.assertIssues('''
		1 21 WARNING: «JSONIssueCodes.JSON_COMMENT_UNSUPPORTED»
		5 29 WARNING: «JSONIssueCodes.JSON_COMMENT_UNSUPPORTED»''');
	}
	
	@Test def void testTrailingComment() {
		'''
		{
			"a" : "stre"// trailing comment
		}
		'''.assertIssues('''
		2 19 WARNING: JSON_COMMENT_UNSUPPORTED''')
	}
	
	@Test def void testInlineComment() {
		'''[1, 2, /* comment */ 3, 4]'''.assertIssues(
			'''1 13 WARNING: JSON_COMMENT_UNSUPPORTED''')
	}
	
	@Test def void testCopyrightHeader() {
		'''/*
		 * Copyright (c) 2016 NumberFour AG.
		 * All rights reserved. This program and the accompanying materials
		 * are made available under the terms of the Eclipse Public License v1.0
		 * which accompanies this distribution, and is available at
		 * http://www.eclipse.org/legal/epl-v10.html
		 *
		 * Contributors:
		 *   NumberFour AG - Initial API and implementation
		 */ {"a" : "key"}'''.assertIssues(
		 	'''1 379 WARNING: JSON_COMMENT_UNSUPPORTED''');
	}
	
	/** 
	 * Asserts that the given {@code jsonText} validated as JSON resources,
	 * yields a list of issues as described by {@code expectations} in the following format:
	 * 
	 * <pre><code>
	 * <LINE> <LENGTH> <SEVERITY>: <ISSUE CODE> 
	 * </code></pre>
	 * 
	 * The issues must be listed in the order of appearance by issue offset.
	 */
	private def void assertIssues(CharSequence jsonText, String expectations) {
		val document = jsonText.parse;
		val issues = validate(document);
		val issueDescription = issues.map[issue | issue.lineNumber + " " + issue.length + " " + issue.severity.toString + ": " + issue.code]
			.join("\n");
		assertEquals(expectations, issueDescription);
	}
}
