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
package org.eclipse.n4js.tests.scoping;

import static org.junit.Assert.assertSame;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class LabelScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Test
	public void testOneLabel() throws Exception {
		Script script = parseHelper.parse("""
				L1: while(true) break L1
				""");
		LabelledStatement label = (LabelledStatement) script.getScriptElements().get(0);
		WhileStatement loop = (WhileStatement) label.getStatement();
		BreakStatement breakStmt = (BreakStatement) loop.getStatement();
		assertSame(label, breakStmt.getLabel());
	}

	@Test
	public void testLabelUsedTwice() throws Exception {
		Script script = parseHelper.parse("""
				L1: while(true) break L1
				L1: while(true) break L1
				""");
		for (ScriptElement element : script.getScriptElements()) {
			LabelledStatement label = (LabelledStatement) element;
			WhileStatement loop = (WhileStatement) label.getStatement();
			BreakStatement breakStmt = (BreakStatement) loop.getStatement();
			assertSame(label, breakStmt.getLabel());
		}
	}

	@Test
	public void testNestedLabel() throws Exception {
		Script script = parseHelper.parse("""
				L1: while(true)
					L2: while(true) break L1
					""");
		LabelledStatement outer = (LabelledStatement) script.getScriptElements().get(0);
		WhileStatement outerLoop = (WhileStatement) outer.getStatement();
		LabelledStatement label = (LabelledStatement) outerLoop.getStatement();
		WhileStatement loop = (WhileStatement) label.getStatement();
		BreakStatement breakStmt = (BreakStatement) loop.getStatement();
		assertSame(outer, breakStmt.getLabel());
	}

	@Test
	public void testInvalidLabel() throws Exception {
		Script script = parseHelper.parse("""
				L1: while(true)
					L2: while(true) break L1
				while(true) break L2
				""");
		LabelledStatement outer = (LabelledStatement) script.getScriptElements().get(0);
		WhileStatement outerLoop = (WhileStatement) outer.getStatement();
		LabelledStatement label = (LabelledStatement) outerLoop.getStatement();
		WhileStatement loop = (WhileStatement) label.getStatement();
		BreakStatement breakStmt = (BreakStatement) loop.getStatement();
		assertSame(outer, breakStmt.getLabel());
		WhileStatement secondLoop = (WhileStatement) IterableExtensions.last(script.getScriptElements());
		BreakStatement secondBreakStmt = (BreakStatement) secondLoop.getStatement();
		assertSame(label, secondBreakStmt.getLabel());
	}
}
