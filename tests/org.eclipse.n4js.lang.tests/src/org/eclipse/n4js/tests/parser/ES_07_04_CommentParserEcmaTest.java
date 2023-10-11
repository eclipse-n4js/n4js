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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_07_04_CommentParserEcmaTest extends AbstractParserTest {

	@Test
	public void testDoubleClosedComment() {
		Script script = parseESWithError("/* var */ x*/");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		MultiplicativeExpression multi = (MultiplicativeExpression) statement.getExpression();
		assertEquals("x", getText((multi.getLhs())));
		RegularExpressionLiteral regex = (RegularExpressionLiteral) multi.getRhs();
		assertEquals("/", regex.getValue());
	}
}
