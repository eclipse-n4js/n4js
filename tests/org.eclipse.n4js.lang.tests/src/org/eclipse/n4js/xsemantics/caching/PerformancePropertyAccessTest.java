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
package org.eclipse.n4js.xsemantics.caching;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test performance involving typing expressions
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class PerformancePropertyAccessTest extends AbstractTypesystemForPerformanceTest {

	@Test
	public void testPropAccess5() {
		propAccess(5);
	}

	@Test
	public void testPropAccess10() {
		propAccess(10);
	}

	@Test(timeout = 3500)
	public void testPropAccess100() {
		// DON'T try to do that without caching :)
		propAccess(100);
	}

	protected void propAccess(int n) {
		/*
		 * obj.p.p.p.p ... .p.p.p;
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("""
				var obj: any+ = new Object();
				obj
				""");
		for (int idx = 1; idx <= n; idx++) {
			sb.append(".p");
		}

		sb.append(";");

		assertValidate(sb.toString(), 0);
	}

}
