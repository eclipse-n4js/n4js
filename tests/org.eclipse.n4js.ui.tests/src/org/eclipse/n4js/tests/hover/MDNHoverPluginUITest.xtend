/**
 * Copyright (c) 2019 HAW Hamburg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Philip Aguilar Bremer, Max Neuwirt; HAW Hamburg
 */
package org.eclipse.n4js.tests.hover

import org.eclipse.n4js.N4JSUiInjectorProvider
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests if link to Mozilla Developer Network is automatically added to hover in case
 * of built-in types. 
 * The test name needs to have a suffix "PluginUITest in order to be correctly executed
 * by Maven.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSUiInjectorProvider)
class MDNHoverPluginUITest extends AbstractN4JSHoverTest {

	private static final String MDN_STRING_START = "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/";
	private static final String MDN_STRING_REGEX_END = "MDN Documentation(?s).*";

	@Before def void setup() {
		ProjectTestsUtils.createJSProject(projectName);
	}

	@Test def testHoverOverBuildInTypeDate() {
		val info = '''
			export public class Bla {
				private date: Date;
			}
		'''.getHtmlString("Date");

		info.assertRegexInHover("(?s).*classifier(?s).+" + "Date(?s).+" + MDN_STRING_START + "Date.+" +
			MDN_STRING_REGEX_END);
	}

	@Test def testHoverOverBuildInTypeString() {
		val info = '''
			export public class Bla {
				private str: String;
			}
		'''.getHtmlString("String");

		info.assertRegexInHover("(?s).*classifier(?s).+" + "String(?s).+" + MDN_STRING_START + "String.+" +
			MDN_STRING_REGEX_END);
	}

	@Test def testHoverOverDeclaration() {
		val info = '''
			export public class Bla {
				private date: Date;
			}
		'''.getHtmlString("date");

		info.assertRegexInHover("(?s).*date:(?s).+" + "Date(?s).+" + MDN_STRING_START + "Date.+" +
			MDN_STRING_REGEX_END);
	}

	@Test def testHoverOverProperty() {
		val info = '''
			export public class Bla {
				private date: Date;
				
				public check() {
				        this.date = new Date('December 17, 1995 03:24:00');				        
				}
			}
		'''.getHtmlString(".date");

		info.assertRegexInHover("(?s).*date:(?s).+" + "Date(?s).+" + MDN_STRING_START + "Date.+" +
			MDN_STRING_REGEX_END);
	}

	@Test def testHoverOverMethodgetDate() {
		val info = '''
			export public class Bla {
				private date: Date;
				public check() {
				        this.date = new Date('December 17, 1995 03:24:00');
				        this.date.getDate();
				    }
			}
		'''.getHtmlString("getDate");

		info.assertRegexInHover("(?s).*getDate\\(\\):(?s).+" + "number(?s).+" + MDN_STRING_START + "Date/getDate.+" +
			MDN_STRING_REGEX_END);
	}

	@Test def testHoverOverMethodgetMinutes() {
		val info = '''
			export public class Bla {
				private date: Date;
				public check() {
				        this.date = new Date('December 17, 1995 03:24:00');
				        this.date.getMinutes();
				    }
			}
		'''.getHtmlString("getMinutes");

		info.assertRegexInHover(
			"(?s).*getMinutes\\(\\):(?s).+" + "number(?s).+" + MDN_STRING_START + "Date/getMinutes.+" +
				MDN_STRING_REGEX_END);
	}
}
