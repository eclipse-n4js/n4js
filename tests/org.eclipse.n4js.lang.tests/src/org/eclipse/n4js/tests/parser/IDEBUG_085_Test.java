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

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;

import java.util.List;

import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class IDEBUG_085_Test extends AbstractParserTest {

	@Test
	public void testNoFunctionExpressions_01() {
		Script script = parseESSuccessfully("""
				function lenFunctionDeclaration(s: string) : number {
					return s.length;
				}

				function wrap(){
					@This(any)
					function lenAnnotatedFunctionDeclarationYYY(s: string) : number {
						return s.length;
					}

					function lenAnnotatedFunctionDeclarationXXX(s: string) : number {
						return s.length;
					}
				}

				@This(any)
				function lenAnnotatedScriptElement(s: string) : number {
					return s.length;
				}

				export function lenExportedFunctionDeclaration(s: string) : number {
					return s.length;
				}

				@This(any)
				export function lenAnnotatedExportableElement(s: string) : number {
					return s.length;
				}
				""");
		List<FunctionExpression> expressions = toList(filter(script.eAllContents(), FunctionExpression.class));
		assertTrue(expressions.toString(), expressions.isEmpty());
	}

	@Test
	public void testNoFunctionExpressions_02() {
		Script script = parseESSuccessfully("""
				function f(){
					@This(any)
					function g() {}
				}
				""");
		List<FunctionExpression> expressions = toList(filter(script.eAllContents(), FunctionExpression.class));
		assertTrue(expressions.toString(), expressions.isEmpty());
	}

}
