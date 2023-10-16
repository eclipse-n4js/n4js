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

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ES_07_09_SemicolonInjectionTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Test
	public void testFunctionWithDeadCode() throws Exception {
		Script script = parseHelper.parse("""
				var c = function() {
					return
					1
				}
				""");
		NumericLiteral secondStatement = (NumericLiteral) IteratorExtensions.findFirst(script.eAllContents(),
				it -> it instanceof NumericLiteral);
		ExpressionStatement statement = (ExpressionStatement) secondStatement.eContainer();
		Block block = (Block) statement.eContainer();
		Assert.assertEquals(2, block.getStatements().size());
	}

}
