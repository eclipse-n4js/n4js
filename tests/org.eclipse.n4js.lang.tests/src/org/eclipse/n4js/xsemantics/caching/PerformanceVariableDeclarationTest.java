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

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test performance involving typing expressions
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class PerformanceVariableDeclarationTest extends AbstractTypesystemForPerformanceTest {

	@Test
	public void testVarDecl5() {
		varTyping(5);
	}

	@Test
	public void testVarDecl10() {
		varTyping(10);
	}

	@Test
	public void testVarDecl20() {
		varTyping(20);
	}

	@Test
	public void testVarDecl100() {
		// DON'T try to do that without caching :)
		varTyping(100);
	}

	protected void varTyping(int n) {
		/* @formatter:off
		 * var i_0 = 0;
		 * var i_1 = i_0;
		 * var i_2 = i_0 + i_1;
		 * var i_3 = i_0 + i_1 + i_2;
		 * etc..
		 * @formatter:on
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("""
				var i_0 = 0;
				var i_1 = i_0;
				""");
		for (int i = 2; i <= n; i++) {
			sb.append("var i_" + i + " = i_0");
			for (int j = 1; j <= i - 1; j++) {
				sb.append(" + i_" + j + ";\n");
			}
		}
		assertValidate(sb.toString(), 0);
	}

}
