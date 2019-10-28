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

import org.eclipse.xtext.ui.testing.AbstractHoverTest
import org.eclipse.jface.text.Region

/**
 *
 */
public abstract class AbstractN4JSHoverTest extends AbstractHoverTest {
	
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
		val info =
		dslFile.
		// when
		hoveringOver(new Region(toString.indexOf(hoverText), hoverText.length));
		return info?.html;
	}
	
}