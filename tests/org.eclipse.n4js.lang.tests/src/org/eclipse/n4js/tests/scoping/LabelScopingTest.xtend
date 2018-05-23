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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.runner.RunWith
import org.junit.Test
import org.eclipse.n4js.n4JS.LabelledStatement
import org.eclipse.n4js.n4JS.WhileStatement
import org.eclipse.n4js.n4JS.BreakStatement

import static org.junit.Assert.*

@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class LabelScopingTest {

	@Inject extension ParseHelper<Script>

	@Test
	def void testOneLabel() {
		val script = '''
			L1: while(true) break L1
		'''.parse
		val label = script.scriptElements.head as LabelledStatement
		val loop = label.statement as WhileStatement
		val break = loop.statement as BreakStatement
		assertSame(label, break.label)
	}

	@Test
	def void testLabelUsedTwice() {
		val script = '''
			L1: while(true) break L1
			L1: while(true) break L1
		'''.parse
		for(element: script.scriptElements) {
			val label = element as LabelledStatement
			val loop = label.statement as WhileStatement
			val break = loop.statement as BreakStatement
			assertSame(label, break.label)
		}
	}

	@Test
	def void testNestedLabel() {
		val script = '''
			L1: while(true)
				L2: while(true) break L1
		'''.parse
		val outer = script.scriptElements.head as LabelledStatement
		val outerLoop = outer.statement as WhileStatement
		val label = outerLoop.statement as LabelledStatement
		val loop = label.statement as WhileStatement
		val break = loop.statement as BreakStatement
		assertSame(outer, break.label)
	}

	@Test
	def void testInvalidLabel() {
		val script = '''
			L1: while(true)
				L2: while(true) break L1
			while(true) break L2
		'''.parse
		val outer = script.scriptElements.head as LabelledStatement
		val outerLoop = outer.statement as WhileStatement
		val label = outerLoop.statement as LabelledStatement
		val loop = label.statement as WhileStatement
		val break = loop.statement as BreakStatement
		assertSame(outer, break.label)
		val secondLoop = script.scriptElements.last as WhileStatement
		val secondBreak = secondLoop.statement as BreakStatement
		assertSame(label, secondBreak.label)
	}
}
