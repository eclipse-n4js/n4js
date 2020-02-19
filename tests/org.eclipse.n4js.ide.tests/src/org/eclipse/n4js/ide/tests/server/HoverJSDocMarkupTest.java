/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.server;

import org.eclipse.xtext.testing.HoverTestConfiguration;
import org.junit.Test;

/**
 * Hover test class for converting html in JSDoc to adoc
 */
public class HoverJSDocMarkupTest extends AbstractHoverTest {

	/** html br> */
	@Test
	public void testHtmlBR() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc <br> CC */class CC {};");
		htc.setLine(0);
		htc.setColumn("/** JSDoc <br> CC */class C".length());
		htc.setExpectedHover("[0:26 - 0:28] [n4js] class CC, [markdown] JSDoc \n\n CC");

		test(htc);
	}

	/** html br/> */
	@Test
	public void testHtmlBR_() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc <br/> CC */class CC {};");
		htc.setLine(0);
		htc.setColumn("/** JSDoc <br/> CC */class C".length());
		htc.setExpectedHover("[0:27 - 0:29] [n4js] class CC, [markdown] JSDoc \n\n CC");

		test(htc);
	}

	/** html p> */
	@Test
	public void testHtmlP() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc <p> CC */class CC {};");
		htc.setLine(0);
		htc.setColumn("/** JSDoc <p> CC */class C".length());
		htc.setExpectedHover("[0:25 - 0:27] [n4js] class CC, [markdown] JSDoc \n\n CC");

		test(htc);
	}

	/** html b> */
	@Test
	public void testHtmlB() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc <b>CC</b> */class CC {};");
		htc.setLine(0);
		htc.setColumn("/** JSDoc <b>CC</b> */class C".length());
		htc.setExpectedHover("[0:28 - 0:30] [n4js] class CC, [markdown] JSDoc **CC**");

		test(htc);
	}

	/** html it> */
	@Test
	public void testHtmlI() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc <i>CC</i> */class CC {};");
		htc.setLine(0);
		htc.setColumn("/** JSDoc <i>CC</i> */class C".length());
		htc.setExpectedHover("[0:28 - 0:30] [n4js] class CC, [markdown] JSDoc __CC__");

		test(htc);
	}

	/** html code> */
	@Test
	public void testHtmlCODE() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc <code>CC</code> */class CC {};");
		htc.setLine(0);
		htc.setColumn("/** JSDoc <code>CC</code> */class C".length());
		htc.setExpectedHover("[0:34 - 0:36] [n4js] class CC, [markdown] JSDoc ``++CC++``");

		test(htc);
	}

}
