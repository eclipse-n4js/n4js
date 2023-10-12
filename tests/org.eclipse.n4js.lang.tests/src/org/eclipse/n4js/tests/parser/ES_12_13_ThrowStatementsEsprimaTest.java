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

import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.MultiplicativeOperator;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.junit.Test;

public class ES_12_13_ThrowStatementsEsprimaTest extends AbstractParserTest {

	@Test
	public void testThrow_Simple() {
		Script script = parseESSuccessfully("throw x;");
		ThrowStatement throwStmt = (ThrowStatement) script.getScriptElements().get(0);
		IdentifierRef identifier = (IdentifierRef) throwStmt.getExpression();
		assertEquals("x", getText(identifier));
	}

	@Test
	public void testThrow_Mult() {
		Script script = parseESSuccessfully("throw x * y");
		ThrowStatement throwStmt = (ThrowStatement) script.getScriptElements().get(0);
		MultiplicativeExpression expression = (MultiplicativeExpression) throwStmt.getExpression();
		MultiplicativeOperator op = expression.getOp();
		assertEquals(MultiplicativeOperator.TIMES, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testThrow_ObjLit() {
		Script script = parseESSuccessfully("throw { message: \"Error\" }");
		ThrowStatement throwStmt = (ThrowStatement) script.getScriptElements().get(0);
		ObjectLiteral ol = (ObjectLiteral) throwStmt.getExpression();
		PropertyNameValuePair prop = (PropertyNameValuePair) ol.getPropertyAssignments().get(0);
		assertEquals("message", prop.getName());
		assertEquals("\"Error\"", getText(prop.getExpression()));
	}

}
