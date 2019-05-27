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
package org.eclipse.n4js.tests.parser

import com.google.common.base.Joiner
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.junit.Test

/**
 * Tests syntax of import calls (a.k.a. dynamic imports) according the corresponding TC39 proposal.
 * 
 * @see <a href="https://github.com/tc39/proposal-dynamic-import/">TC39 proposal</a>
 * @see <a href="https://tc39.github.io/proposal-dynamic-import/">Proposed specification changes</a>
 */
class ImportCallsParserTest extends AbstractParserTest {

	@Test
	def testImportCalls() {
		val script = '''
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
		'''.parse;
		val resource = script.eResource;

		assertTrue("unexpected syntax errors:\n\t" + Joiner.on("\n\t").join(resource.errors), resource.errors.empty);

		val funDecl = script.eAllContents.filter(FunctionDeclaration).findFirst[name == "testPrecedence"];
		assertNotNull(funDecl);
		val exprs = funDecl.body.statements.filter(ExpressionStatement).map[expression].toList;
		assertEquals(4, exprs.size);

		assertStructureOfAST('''
			AwaitExpression {
				expression: ParameterizedCallExpression {
					target: ParameterizedPropertyAccessExpression {
						target: ImportCallExpression {
							arguments: Argument {
								expression: StringLiteral
							}
						}
					}
				}
			}
		''', exprs.get(1));

		assertStructureOfAST('''
			AwaitExpression {
				expression: ParameterizedCallExpression {
					target: IndexedAccessExpression {
						target: ImportCallExpression {
							arguments: Argument {
								expression: StringLiteral
							}
						},
						index: StringLiteral
					}
				}
			}
		''', exprs.get(2));

		assertStructureOfAST('''
			AwaitExpression {
				expression: ParameterizedCallExpression {
					target: ImportCallExpression {
						arguments: Argument {
							expression: StringLiteral
						}
					},
					arguments: Argument {
						expression: StringLiteral
					}
				}
			}
		''', exprs.get(3));
	}

	def private void assertStructureOfAST(CharSequence expectedStructureAsString, EObject astNode) {
		val actualStructureAsString = getStructureAsString(astNode);
		val isEqual = expectedStructureAsString.removeWhitespace == actualStructureAsString.removeWhitespace;
		if (!isEqual) {
			fail('''
				unexpected structure of AST; expected:
					«expectedStructureAsString»
				but got:
					«actualStructureAsString»''');
		}
	}

	def private String getStructureAsString(EObject obj) {
		val sb = new StringBuilder();
		appendStructureAsString(false, obj, sb);
		return sb.toString;
	}

	def private void appendStructureAsString(boolean includeRef, EObject obj, StringBuilder sb) {
		if (includeRef) {
			sb.append(obj.eContainmentFeature.name);
			sb.append(": ");
		}
		sb.append(obj.eClass.name);
		val iter = obj.eContents.iterator;
		if (iter.hasNext) {
			sb.append(" { ");
			do {
				appendStructureAsString(true, iter.next, sb);
				if (iter.hasNext) {
					sb.append(", ");
				}
			} while (iter.hasNext);
			sb.append(" }");
		}
	}

	def private String removeWhitespace(CharSequence input) {
		return input.toString.replaceAll("(\\s|\\n|\\r)+", "");
	}
}
