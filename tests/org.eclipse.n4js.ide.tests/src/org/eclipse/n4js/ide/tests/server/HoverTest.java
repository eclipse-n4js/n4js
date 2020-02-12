/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ide.tests.server;

import org.eclipse.xtext.testing.HoverTestConfiguration;
import org.junit.Test;

/**
 * Signature help test class
 */
public class HoverTest extends AbstractHoverTest {

	/** Example test for signature help */
	@Test
	public void test() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }");
		htc.setLine(0);
		htc.setColumn("class A { foo(a: A) { } } class Main { main(a: A) { a.fo".length());
		htc.setExpectedHover("[0:54 - 0:57] [] method foo(a: A): void");

		test(htc);
	}

}
