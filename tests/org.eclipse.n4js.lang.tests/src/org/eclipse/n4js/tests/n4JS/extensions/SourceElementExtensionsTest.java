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
package org.eclipse.n4js.tests.n4JS.extensions;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.scoping.utils.SourceElementExtensions;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
@RunWith(XtextRunner.class)
public class SourceElementExtensionsTest {
	@Inject
	private ParseHelper<Script> parseHelper;
	@Inject
	private ValidationTestHelper valTestHelper;

	@Inject
	SourceElementExtensions srcElemExt;

	@Test
	public void testCollectVisibleIdentifiableElementsForFunctionExpression() throws Exception {
		Script script = parseHelper.parse("""
				var _ = 2
				var a = function fun1() {
					var x = 1;
					var b = function fun2() {
						var y = 2;
					}
				}
				""");
		assertTrue(valTestHelper.validate(script).isEmpty());

		// use of '_' discouraged : http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.27.1

		FunctionExpression fun1 = head(filter(filter(script.eAllContents(), FunctionExpression.class),
				fe -> Objects.equals(fe.getName(), "fun1")));
		FunctionExpression fun2 = head(filter(filter(script.eAllContents(), FunctionExpression.class),
				fe -> Objects.equals(fe.getName(), "fun2")));

		assertEquals("_, a", toVisibleElementNameList(script));
		assertEquals("fun1, x, b, arguments", toVisibleElementNameList(fun1));
		assertEquals("fun2, y, arguments", toVisibleElementNameList(fun2));
	}

	@Test
	public void testCollectVisibleIdentifiableElements_Var_vs_Let() throws Exception {
		Script script = parseHelper.parse("""
				var v0;
				let l0;

				if(true) {
					var v1;
					let l1;
				}
				""");
		assertTrue(valTestHelper.validate(script).isEmpty());

		Block block = (Block) head(filter(script.eAllContents(), IfStatement.class)).getIfStmt();
		assertNotNull(block);

		assertEquals("v0, l0, v1", toVisibleElementNameList(script));
		assertEquals("l1", toVisibleElementNameList(block));
	}

	private String toVisibleElementNameList(VariableEnvironmentElement element) {
		return toNameList(Iterables.concat(srcElemExt.collectVisibleIdentifiableElements(element),
				srcElemExt.collectLocalArguments(element)));
	}

	private String toNameList(Iterable<IdentifiableElement> visibleElements) {
		return Strings.join(", ", map(visibleElements, id -> id.getName()));
	}
}
