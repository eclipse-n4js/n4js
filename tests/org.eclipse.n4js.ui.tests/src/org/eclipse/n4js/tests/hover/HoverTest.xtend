/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.hover

import org.eclipse.jface.internal.text.html.BrowserInformationControlInput
import org.eclipse.jface.text.Region
import org.eclipse.n4js.N4JSUiInjectorProvider
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.ui.testing.AbstractHoverTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSUiInjectorProvider)
class HoverTest extends AbstractHoverTest {
	
	private static final String MDN_STRING_START = "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/";
	private static final String MDN_STRING_REGEX_END = "MDN Documentation(?s).*";
	
	@Before def void setup() {
		ProjectTestsUtils.createJSProject(projectName);
	}
	
	def assertRegexInHover(String html, String regex) {
			assertNotNull("No hover found!", html)
			assertTrue('''
			Could not match the regex '«regex»' in the hover popup:
				«html»
		''', html.matches(regex))
		}
	
	override protected getFileName() {
		return "src/hover";
	}
	
	def String getInfo(CharSequence it, String hoverText) {
		val BrowserInformationControlInput info =
		dslFile.
		// when
		hoveringOver(new Region(toString.indexOf(hoverText), hoverText.length));
		return info?.html;
	}
	
	@Test def testHoverOverBuildInTypeDate() {
		val info = '''
			export public class Bla {
				private date: Date;
			}
		'''.getInfo("Date");
		
		info.assertRegexInHover("(?s).*classifier(?s).+" +
		"Date(?s).+" +
		MDN_STRING_START +
		"Date.+" +
		MDN_STRING_REGEX_END);	
	}
		
		@Test def testHoverOverBuildInTypeString() {
		val info = '''
			export public class Bla {
				private str: String;
			}
		'''.getInfo("String");
		
		info.assertRegexInHover("(?s).*classifier(?s).+" +
		"String(?s).+" +
		MDN_STRING_START + 
		"String.+" +
		MDN_STRING_REGEX_END);	
	}
	
		@Test def testHoverOverDeclaration() {
		val info = '''
			export public class Bla {
				private date: Date;
			}
		'''.getInfo("date");
		
		info.assertRegexInHover("(?s).*date:(?s).+" +
		"Date(?s).+" +
		MDN_STRING_START +
		"Date.+" +
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
		'''.getInfo(".date");
		
		info.assertRegexInHover("(?s).*date:(?s).+" +
		"Date(?s).+" +
		MDN_STRING_START +
		"Date.+" +
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
		'''.getInfo("getDate");
		
		info.assertRegexInHover("(?s).*getDate\\(\\):(?s).+" +
		"number(?s).+" +
		MDN_STRING_START + 
		"Date/getDate.+" +
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
		'''.getInfo("getMinutes");
		
		info.assertRegexInHover("(?s).*getMinutes\\(\\):(?s).+" +
		"number(?s).+" +
		MDN_STRING_START + 
		"Date/getMinutes.+" +
		"MDN Documentation(?s).*");	
	}
}