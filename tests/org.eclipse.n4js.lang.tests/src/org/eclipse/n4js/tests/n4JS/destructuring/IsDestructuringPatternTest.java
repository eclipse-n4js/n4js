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
package org.eclipse.n4js.tests.n4JS.destructuring;

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSValidationTestHelper;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Destructuring patterns may appear in the AST as an ArrayLiteral or ObjectLiteral. This class tests the utility method
 * for finding out whether an ArrayLiteral or ObjectLiteral is a destructuring pattern.
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class IsDestructuringPatternTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	N4JSValidationTestHelper valTestHelper;

	@Test
	public void testArrayLiteralInAssignmentExpression() {
		Script script = parseAndValidate("""

				var a,b,c,arr;

				([a,b,c] = [1,2,3]);

				arr = [1,2,3];

				""");

		valTestHelper.assertNoIssues(script);

		List<ArrayLiteral> arrLits = toList(filter(script.eAllContents(), ArrayLiteral.class));
		ArrayLiteral arrLit0 = arrLits.get(0);
		ArrayLiteral arrLit1 = arrLits.get(1);
		ArrayLiteral arrLit2 = arrLits.get(2);
		assertNotNull(arrLit0);
		assertNotNull(arrLit1);
		assertNotNull(arrLit2);

		assertTrue(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrLit0));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrLit1));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrLit2));

		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit0));
		assertTrue(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit1));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit2));
	}

	@Test
	public void testArrayLiteralInForStatement_plain() {
		Script script = parseAndValidate("""

				var a,b,c,arr;

				for([a,b,c] = [1,2,3] ; ; ) {}

				""");

		valTestHelper.assertNoIssuesExcept(script, IssueCodes.CFG_LOCAL_VAR_UNUSED);

		List<ArrayLiteral> arrLits = toList(filter(script.eAllContents(), ArrayLiteral.class));
		ArrayLiteral arrLit0 = arrLits.get(0);
		ArrayLiteral arrLit1 = arrLits.get(1);
		assertNotNull(arrLit0);
		assertNotNull(arrLit1);

		assertTrue(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrLit0));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrLit1));

		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit0));
		assertTrue(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit1));
	}

	@Test
	public void testArrayLiteralInForStatement_of() {
		Script script = parseAndValidate("""

				var a,b,c,arr;

				for([a,b,c] of [[1,2,3]]) {}

				""");

		valTestHelper.assertNoIssuesExcept(script, IssueCodes.CFG_LOCAL_VAR_UNUSED);

		ForStatement forStmt = toList(filter(script.eAllContents(), ForStatement.class)).get(0);
		Expression arrLit0 = forStmt.getInitExpr();
		Expression arrLit1 = forStmt.getExpression();
		ArrayLiteral arrLit2 = toList(filter(arrLit1.eAllContents(), ArrayLiteral.class)).get(0);
		assertNotNull(arrLit0);
		assertNotNull(arrLit1);
		assertNotNull(arrLit2);

		assertTrue(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrLit0));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrLit1));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrLit2));

		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit0));
		// NOTE: this must return false!!!
		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit1));
		// TODO unclear; see API doc of method
		assertTrue(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit2));
	}

	@Test
	public void testObjectLiteralInAssignmentExpression() {
		Script script = parseAndValidate("""

				var a,b,c,obj;

				({prop1:a,prop2:b,prop3:c} = {prop1:0,prop2:0,prop3:0});

				obj = {prop1:0,prop2:0,prop3:0};

				""");

		valTestHelper.assertNoIssues(script);

		List<ObjectLiteral> objLits = toList(filter(script.eAllContents(), ObjectLiteral.class));
		ObjectLiteral objLit0 = objLits.get(0);
		ObjectLiteral objLit1 = objLits.get(1);
		ObjectLiteral objLit2 = objLits.get(2);
		assertNotNull(objLit0);
		assertNotNull(objLit1);
		assertNotNull(objLit2);

		assertTrue(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(objLit0));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(objLit1));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(objLit2));

		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(objLit0));
		assertTrue(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(objLit1));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(objLit2));
	}

	@Test
	public void testObjectLiteralInForStatement_plain() {
		Script script = parseAndValidate("""

				var a,b;

				for({prop1:a,prop2:b} = {prop1:1,prop2:2} ; ; ) {}

				""");

		valTestHelper.assertNoIssues(script);

		List<ObjectLiteral> objLits = toList(filter(script.eAllContents(), ObjectLiteral.class));
		ObjectLiteral objLit0 = objLits.get(0);
		ObjectLiteral objLit1 = objLits.get(1);
		assertNotNull(objLit0);
		assertNotNull(objLit1);

		assertTrue(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(objLit0));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(objLit1));

		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(objLit0));
		assertTrue(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(objLit1));
	}

	@Test
	public void testObjectLiteralInForStatement_of() {
		Script script = parseAndValidate("""

				var a,b;

				for({prop1:a,prop2:b} of [{prop1:1,prop2:2}]) {}

				""");

		valTestHelper.assertNoIssues(script);

		ForStatement forStmt = toList(filter(script.eAllContents(), ForStatement.class)).get(0);
		Expression objLit0 = forStmt.getInitExpr();
		Expression arrLit0 = forStmt.getExpression();
		ObjectLiteral objLit1 = toList(filter(arrLit0.eAllContents(), ObjectLiteral.class)).get(0);

		assertNotNull(objLit0);
		assertNotNull(arrLit0);
		assertNotNull(objLit1);

		assertTrue(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(objLit0));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrLit0));
		assertFalse(DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(objLit1));

		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(objLit0));
		// NOTE: this must return false!!!
		assertFalse(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit0));
		// TODO unclear; see API doc of method
		assertTrue(DestructureUtils.isArrayOrObjectLiteralBeingDestructured(objLit1));
	}

	private Script parseAndValidate(CharSequence scriptSrc) {
		Script script;
		try {
			script = parseHelper.parse(scriptSrc);
			valTestHelper.validate(script);
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}
}
