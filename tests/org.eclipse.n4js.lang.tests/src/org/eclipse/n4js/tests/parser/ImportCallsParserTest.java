/**
 * Copyright (c) 2019 NumberFour AG.
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
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.findFirst;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;

import com.google.common.base.Joiner;

/**
 * Tests syntax of import calls (a.k.a. dynamic imports) according the corresponding TC39 proposal.
 *
 * @see <a href="https://github.com/tc39/proposal-dynamic-import/">TC39 proposal</a>
 * @see <a href="https://tc39.github.io/proposal-dynamic-import/">Proposed specification changes</a>
 */
public class ImportCallsParserTest extends AbstractParserTest {

	@Test
	public void testImportCalls() throws Exception {
		Script script = parseHelper.parse("""
					import * as X from "C";
					import {D as Y} from "D";

					import("M");
					(import("M"));
					import(`${'M'}`);
					import('' + `` + "");
					import(someString());
					let imp = import("M") ? import("M") : import("M");

					import("M").then(m=>console.log(m), err=>console.log(err));

					class C {}
					class D extends C {
						constructor() {
							super();
							import("M");
						}
					}

					async function testSimple(param: Promise<?,?>) {
						import("M");
						await import("M");
						await testSimple(import("M"));
						if(import("M")!==undefined) {
							let p = import("M");
							p = p!==undefined ? import("M") : import("M");
						}
						return import("M");
					}

					async function testPrecedence() {
						await (import("M").then());
						await import("M").then();
						await import("M")['then']();
						await import("M")('oof');
					}

					function someString() {
						return "M";
					}

					X,Y;
				""");
		Resource resource = script.eResource();

		assertTrue("unexpected syntax errors:\n\t" + Joiner.on("\n\t").join(resource.getErrors()),
				resource.getErrors().isEmpty());

		FunctionDeclaration funDecl = findFirst(filter(script.eAllContents(), FunctionDeclaration.class),
				fd -> Objects.equals(fd.getName(), "testPrecedence"));
		assertNotNull(funDecl);
		List<Expression> exprs = IterableExtensions.toList(IterableExtensions.map(
				IterableExtensions.filter(funDecl.getBody().getStatements(), ExpressionStatement.class),
				es -> es.getExpression()));
		assertEquals(4, exprs.size());

		assertStructureOfAST("""
					AwaitExpression {
						expression: ParameterizedCallExpression {
							target: ParameterizedPropertyAccessExpression {
								target: ParameterizedCallExpression {
									target: IdentifierRef,
									arguments: Argument {
										expression: StringLiteral
									}
								}
							}
						}
					}
				""", exprs.get(1));

		assertStructureOfAST("""
					AwaitExpression {
						expression: ParameterizedCallExpression {
							target: IndexedAccessExpression {
								target: ParameterizedCallExpression {
									target: IdentifierRef,
									arguments: Argument {
										expression: StringLiteral
									}
								},
								index: StringLiteral
							}
						}
					}
				""", exprs.get(2));

		assertStructureOfAST("""
					AwaitExpression {
						expression: ParameterizedCallExpression {
							target: ParameterizedCallExpression {
								target: IdentifierRef,
								arguments: Argument {
									expression: StringLiteral
								}
							},
							arguments: Argument {
								expression: StringLiteral
							}
						}
					}
				""", exprs.get(3));
	}

	private void assertStructureOfAST(CharSequence expectedStructureAsString, EObject astNode) {
		String actualStructureAsString = getStructureAsString(astNode);
		boolean isEqual = Objects.equals(removeWhitespace(expectedStructureAsString),
				removeWhitespace(actualStructureAsString));
		if (!isEqual) {
			fail("""
					unexpected structure of AST; expected:
						%s
					but got:
						%s""".formatted(expectedStructureAsString, actualStructureAsString));
		}
	}

	private String getStructureAsString(EObject obj) {
		StringBuilder sb = new StringBuilder();
		appendStructureAsString(false, obj, sb);
		return sb.toString();
	}

	private void appendStructureAsString(boolean includeRef, EObject obj, StringBuilder sb) {
		if (includeRef) {
			sb.append(obj.eContainmentFeature().getName());
			sb.append(": ");
		}
		sb.append(obj.eClass().getName());
		Iterator<EObject> iter = obj.eContents().iterator();
		if (iter.hasNext()) {
			sb.append(" { ");
			do {
				appendStructureAsString(true, iter.next(), sb);
				if (iter.hasNext()) {
					sb.append(", ");
				}
			} while (iter.hasNext());
			sb.append(" }");
		}
	}

	private String removeWhitespace(CharSequence input) {
		return input.toString().replaceAll("(\\s|\\n|\\r)+", "");
	}
}
