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

import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_01_4_ArrayInitializerEcmaTest extends AbstractParserTest {

	@Test
	public void testArrayInitializer_01() {
		Script script = parseESSuccessfully("x = [,,]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(2, array.getElements().size());
	}

}
