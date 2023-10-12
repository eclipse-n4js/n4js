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

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithoutStructureValidation.class)
public class ES_07_09_ASI_InfiniteLoopProblemTest extends AbstractParserTest {

	public void hasChildren(Script p, int count) {
		assertEquals(count, p.getScriptElements().size());
	}

	@Test
	public void testSomeASIs() {
		parseESSuccessfully("""
				var a,b
				a = 1
				b = a
				console.log(a+b)
				""");
	}

	/**
	 * In some cases, the ASI rewinds the input when an EOL is found and inserts a semicolon there. In this test, this
	 * happens after "a.field1". However, somehow the parse tries to parse members of the class expression B. This is
	 * completely wrong at this position, I'm not quite sure how this happens. Anyway, the recover leads to an infinite
	 * loop, since inserting a semicolon after "a.field1" does not solve the real problem -- so the parser again finds a
	 * problem and tries to recover at the same position. In order to prevent this to happen,
	 * org.eclipse.n4js.parser.SemicolonInjectionHelper.Callback.allowASI(RecognitionException) stores the last state in
	 * which an EOL has been interpreted as semicolon, and when we try to do this again, we break the cycle. Although
	 * this works, we should solve the problem at its cause and not prevent some symptom. The only problem: What is the
	 * real cause?
	 */
	@Test
	public void testInfiniteLoopBug() {
		parseESWithError("""
				var a = class A {
				b = class B {
					bar(): any {
						var  a.field1
						a.foo()
						return null;
					}
				};
				static field1: any;
				static foo(): any { return null; }
				""");
	}

}
