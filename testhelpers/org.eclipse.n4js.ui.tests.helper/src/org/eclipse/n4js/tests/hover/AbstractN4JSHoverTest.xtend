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

import org.eclipse.xtext.ui.testing.AbstractHoverTest
import org.eclipse.jface.text.Region

/**
 * An abstract class that can return the html text of a hover to test if it matches a particular regex.
 * Concrete test subclassing this class needs to have a suffix "PluginUITest" in order to be correctly executed by Maven.
 */
public abstract class AbstractN4JSHoverTest extends AbstractHoverTest {
	
	override protected getFileName() {
		return "src/hover";
	}
	
	/**
	 * Tests if the html string matches the given regex.
	 * 
	 * @param html the html string of the hover info
	 * @param regex the regex that needs to be matched against the html string
	 */
	def assertRegexInHover(String html, String regex) {
		assertNotNull("No hover found!", html)
		assertTrue('''
			Could not match the regex '«regex»' in the hover popup:
				«html»
		''', html.matches(regex))
		}
	
	/**
	 * Returns the html source code of the hover info window as a string.
	 * 
	 * @param it the initial DSL text. 
	 * @param hoverText the text you are hovering over.
	 */
	def String getHtmlString(CharSequence it, String hoverText) {
		val info =
		dslFile.
		// when
		hoveringOver(new Region(toString.indexOf(hoverText), hoverText.length));
		return info?.html;
	}
	
}