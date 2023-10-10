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
package org.eclipse.n4js.transpiler.es.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class TranspilerUtilsTest extends AbstractTranspilerTest {

	@Test
	public void testEmptyBlock() {
		Script script = createScript("function a (){}");
		resolveLazyRefs(script);

		FunctionDeclaration f1 = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("a", f1.getName());

		List<EObject> collected = TranspilerUtils.collectNodesWithinSameThisEnvironment(f1, EObject.class);
		assertEquals(1, collected.size()); // gets the empty Block element.
		assertTrue(collected.get(0) instanceof Block);
	}

	@Test
	public void testSimpleBlock() {
		Script script = createScript("""
				function a (){
					var boolean b = true;
					if( b ) { return "B" }
					else { return "C" }

					if( b ) {
						{
							return "D";
						}
					}

					return "A";
				}
				""");
		resolveLazyRefs(script);

		FunctionDeclaration f1 = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("a", f1.getName());

		List<ReturnStatement> collected = TranspilerUtils.collectNodesWithinSameThisEnvironment(f1,
				ReturnStatement.class);
		assertEquals(4, collected.size());

		Set<String> values = new TreeSet<>();
		for (ReturnStatement rs : collected) {
			Expression expr = rs.getExpression();
			if (expr instanceof StringLiteral) {
				values.add(((StringLiteral) expr).getValue());
			}
		}

		String[] expected = { "A", "B", "C", "D" };

		assertArrayEquals(expected, values.toArray(new String[0]));

	}

	@Test
	public void testNestedBlock() {
		Script script = createScript("""
				function a (){
					var boolean b = true;
					if( b ) { return "B" }
					else { return "C" }

					if( b ) {
						{
							return "D";
						}
					}

					var x = function() {
						var z = function z () { return "G"; }
						return "E";
					}

					var y = ()=>{ return "F"; }

					switch(5) {
						case 4: return "H";
					}

					var z1 = () => {   return "I"; 	}
					var z2 = async () => { 	return "J"; 	}
					var z2 = async () => { 	return await "J"; 	}

					return "A";
				}
				""");

		resolveLazyRefs(script);

		FunctionDeclaration f1 = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("a", f1.getName());

		List<ReturnStatement> collected = TranspilerUtils.collectNodesWithinSameThisEnvironment(f1,
				ReturnStatement.class);

		Set<String> values = new TreeSet<>();
		for (ReturnStatement rs : collected) {
			Expression expr = rs.getExpression();
			if (expr instanceof StringLiteral) {
				values.add(((StringLiteral) expr).getValue());
			}
		}
		String[] expected = { "A", "B", "C", "D", "H" };

		assertArrayEquals(expected, values.toArray(new String[0]));

	}

}
