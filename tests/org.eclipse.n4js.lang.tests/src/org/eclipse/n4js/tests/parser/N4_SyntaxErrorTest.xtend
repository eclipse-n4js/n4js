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
package org.eclipse.n4js.tests.parser

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.AssignmentOperator
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.IndexedAccessExpression
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.RegularExpressionLiteral
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.diagnostics.Diagnostic
import org.eclipse.xtext.linking.impl.XtextLinkingDiagnostic
import org.eclipse.xtext.nodemodel.impl.InvariantChecker
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic
import org.junit.Test

/**
 */
class N4_SyntaxErrorTest extends AbstractParserTest {

	@Test
	def void testSyntaxErrorInClassBody() {
		val script = '''
			public class C {
				c: C = syntax error
			}
		'''.parse

		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
		val n4Class = script.scriptElements.head as N4ClassDeclaration
		val n4Field = n4Class.ownedMembers.head as N4FieldDeclaration
		assertEquals('c', n4Field.name)
	}

	@Test
	def void testSyntaxErrorInRegExLiteral_01() {
		val script = '''
			{ /* empty block followed by unclosed regex*/ } /

		'''.parse

		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
		val stmt = script.scriptElements.last as ExpressionStatement
		val regEx = stmt.expression as RegularExpressionLiteral
		assertEquals('/', regEx.value)
		val diagnostic = script.eResource.errors.head as XtextSyntaxDiagnostic
		assertEquals('Invalid regular expression literal', diagnostic.message)
	}

	@Test
	def void testSyntaxErrorInRegExLiteral_02() {
		val script = '''
			{ /* empty block followed by unclosed regex*/ } /=

		'''.parse

		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
		val stmt = script.scriptElements.last as ExpressionStatement
		val regEx = stmt.expression as RegularExpressionLiteral
		assertEquals('/=', regEx.value)
		val diagnostic = script.eResource.errors.head as XtextSyntaxDiagnostic
		assertEquals('Invalid regular expression literal', diagnostic.message)
	}

	@Test
	def void testBrowserTests_MissingParentheses() {
		val script = '''
			YUI.add('browser-tests', function(Y) {

		    var suite = new Y.Test.Suite('YUI: Core Browser');

		    suite.add(new Y.Test.Case({
		        test_attach_after: function() {
		            var Assert = Y.Assert;
		            YUI.add('after-test', function(Y) {
		                Y.afterTest = true;
		                Assert.isObject(Y.Node, 'Node not loaded before this module');
		            }, '1.0.0', {
		                after: [ 'node' ]
		            });

		            YUI().use('after-test', function(Y2) {
		                Assert.isTrue(Y2.afterTest, 'after-test module was not loaded');
		            });
		        },
		        test_rollup_false: function() {
		            var Assert = Y.Assert;
		            YUI().use('dd', function(Y) {
		                Assert.isUndefined(Y.Env._attached.dd, 'DD Alias Module was attached');
		            });
		            YUI().use('node', function(Y) {
		                Assert.isUndefined(Y.Env._attached.node, 'Node Alias Module was attached');
		            });
		        }
		    });

		    Y.Test.Runner.add(suite);

		});
		'''.parse

		assertEquals(script.eResource.errors.toString, 1, script.eResource.errors.size)
		val diagnostic = script.eResource.errors.head
		assertEquals("missing ')' at ';'", diagnostic.message)
	}

	@Test
	def void testIDEBUG_5_01() {
		val script = '''
			var A<?
		'''.parse

		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testIDEBUG_5_02() {
		val script = '''
			var A<
		'''.parse
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
		val res = script.eResource as XtextResource
		res.update(6, 0, '?')
		val newScript = res.contents.head as Script
		assertNotSame(script, newScript)
		assertFalse(newScript.eResource.errors.toString, newScript.eResource.errors.empty)
	}

	@Test
	def void testIDEBUG_8_01() {
		val script = '''
			var A<? extends
		'''.parse
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testIDEBUG_8_02() {
		val script = '''
			var A<? super
		'''.parse
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testIDEBUG_7_01() {
		val script = '''
			class C {}
			class D<A,> extends C {}
		'''.parse
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
		EcoreUtil.resolveAll(script.eResource)
	}

	@Test
	def void testIDEBUG_29_01() {
		val script = '''
			class A {
				method() {
					new class {
						var x: string;
					};
				}
			}
		'''.parse
		val errorList = script.eResource.errors
		assertEquals(errorList.toString, 1, errorList.size)
		val firstError = errorList.head
		assertTrue(firstError.message, firstError.message.contains("no viable alternative at input 'x'"))
	}

	@Test
	def void testIDEBUG_207_01() {
		val script = '''
			class A {
				method() {
					new class {
						var;
						x: string;
					};
				}
			}
		'''.parse
		val errorList = script.eResource.errors
		assertEquals(errorList.toString, 0, errorList.size)
	}

	@Test
	def void testIDEBUG_29_02() {
		val script = '''
			new class {
				var x: string;
			};
		'''.parse
		val errorList = script.eResource.errors
		assertEquals(errorList.toString, 1, errorList.size)
		val firstError = errorList.head
		assertTrue(firstError.message, firstError.message.contains("no viable alternative at input 'x'"))
	}

	@Test
	def void testRecoveredIndexAccessExpression() {
		val script = '''
			var testArray = ["A""];
			testArray[0];
		'''.parse
		assertEquals(script.eResource.errors.toString, 1, script.eResource.errors.size)
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression
		assertTrue(String.valueOf(expr), expr instanceof IndexedAccessExpression)
		val error = script.eResource.errors.head
		assertEquals("no viable alternative at input '\"];'", error.message)
	}

	@Test
	def void testIDE_1172_01() {
		val script = '''
			var s};
		'''.parse
		assertEquals(script.eResource.errors.toString, 1, script.eResource.errors.size)
		val error = script.eResource.errors.head
		assertEquals("missing EOF at '}'", error.message)
		assertEquals('var s}'.length - 1, (error as Diagnostic).offset)
		assertTrue(script.scriptElements.last instanceof VariableStatement)
	}

	@Test
	def void testIDE_1172_02() {
		// var v = a + b, c + d;
		// this is tricky but valid according to the n4js java type annotation syntax:
		// the user obviously sees two binary operations where the parser sees this:
		// var v = a + b /* binary op*/,
		//         <TypeRef type:'c' dynamic:'+'> <name:'d'>
		// so basically two variable declarations in the same variable statement:
		// variable v with an inferred type and an initializer expression
		// variable d with the given dynamic type c
		//
		// since colon type annotation syntax is used now, this is no longer valid:

		val script = '''
			var v = a + b,
			        c + d;
		'''.parse
		assertEquals(script.eResource.errors.toString, 1, script.eResource.errors.size)
	}

	@Test
	def void testIDE_1172_02a() {
		// same as testIDE_1172_02 but with colon type annotation syntax, making it less tricky

		val script = '''
			var v = a + b,  d: c+;
		'''.parse
		assertEquals(script.eResource.errors.toString, 0, script.eResource.errors.size)
	}

	@Test
	def void testIDE_1172_03() {
		val script = '''
			var v = a + b,
			        class A { a = new A(); };
		'''.parse
		assertEquals(script.eResource.errors.toString, 1, script.eResource.errors.size)
		val error = script.eResource.errors.head
		assertEquals("no viable alternative at input 'class'", error.message)
	}

	@Test
	def void testIDEBUG_384_01() {
		val script = '`${}\\'.parse
		assertEquals(script.eResource.errors.toString, 1, script.eResource.errors.size)
		val error = script.eResource.errors.head
		assertEquals("Template literal is not properly closed by a backtick.", error.message)
	}

	@Test
	def void testIDEBUG_384_02() {
		val script = '"\\'.parse
		assertEquals(script.eResource.errors.toString, 1, script.eResource.errors.size)
		val error = script.eResource.errors.head
		assertEquals("String literal is not properly closed by a double-quote.", error.message)
	}

	@Test
	def void testIDEBUG_384_03() {
		val script = "'\\".parse
		assertEquals(script.eResource.errors.toString, 1, script.eResource.errors.size)
		val error = script.eResource.errors.head
		assertEquals("String literal is not properly closed by a quote.", error.message)
	}

	@Test
	def void testIDEBUG_281_01() {
		val scriptAsText = '''
			var class;
			var 1a = null;
		'''
		val script = scriptAsText.parse
		val insertHere = scriptAsText.indexOf('1')
		val resource = script.eResource as N4JSResource

		val errors = resource.errors
		assertEquals(errors.join('\n'), 4, errors.size)
		resource.update(insertHere, 0, 'a')
		assertEquals(2, errors.size)
		resource.update(insertHere, 0, 'a')
		assertEquals(2, errors.size)
	}

	@Test
	def void testRegExWithLookAhead() {
		'''
			var v = {
			    a: (b = "".match(/^a/)),
			    c: null
			};
		'''.parseESSuccessfully
	}

	@Test
	def void testIDEBUG_352_01() {
		'''
			async function asyncParty(): number {
			    return 4;
			}

			async function doSomeStuff() {
			    var hello = "hi"
			    await asyncParty();
			    hello.charAt(0)

			    await asyncParty()
			    hello.charAt(0)

			    await asyncParty()
			}
		'''.parseESSuccessfully
	}

	@Test
	def void testIDEBUG_352_01_old() {
		'''
			async function  asyncParty(): number {
			    return 4;
			}

			async function doSomeStuff() {
			    var hello = "hi"
			    await asyncParty();
			    hello.charAt(0)

			    await asyncParty()
			    hello.charAt(0)

			    await asyncParty()
			}
		'''.parseESSuccessfully
	}

	@Test
	def void testIDEBUG_352_02() {
		val script = '''
			a >>>= b =
		'''.parseESWithError
		val stmt = script.scriptElements.head as ExpressionStatement
		val expr = stmt.expression as AssignmentExpression
		assertEquals(AssignmentOperator.USHR_ASSIGN, expr.op)
	}

	@Test
	def void testIDEBUG_594_01() {
		val script = '''
			function f(x: {function():type?}) {}
		'''.parseESWithError
		val f = script.scriptElements.head as FunctionDeclaration
		val param = f.fpars.head
		assertEquals('x', param.name)
		assertTrue(param.declaredTypeRef instanceof FunctionTypeExpression)
		val paramType = param.declaredTypeRef as FunctionTypeExpression
		assertTrue(paramType.returnTypeRef instanceof TypeTypeRef)
		// returnType.getTypeArg is now interpreted as wildcard, but check other AST elements:
	}

	@Test
	def void testIDEBUG_594_02() {
		val script = '''
			function f(x: {function():constructor?}) {}
		'''.parseESWithError
		val f = script.scriptElements.head as FunctionDeclaration
		val param = f.fpars.head
		assertEquals('x', param.name)
		assertTrue(param.declaredTypeRef instanceof FunctionTypeExpression)
		val paramType = param.declaredTypeRef as FunctionTypeExpression
		assertTrue(paramType.returnTypeRef instanceof TypeTypeRef)
		// returnType.getTypeArg is now interpreted as wildcard, but check other AST elements:
	}

	@Test
	def void testNodeModelInvariants_01() {
		val script = '''
			var ~Object with {
			var s;
		'''.parseESWithError
		val parseResult = (script.eResource as XtextResource).parseResult
		val node = parseResult.rootNode
		assertSame(node.rootNode, node)
	}

	@Test
	def void testNodeModelInvariant_02() {
		val script = '''
			var ~Object with {a: string; b: string;;
			var s: string;
			s = r.a;
			s = r.b;
		'''.parseESWithError
		val parseResult = (script.eResource as XtextResource).parseResult
		val node = parseResult.rootNode
		assertSame(node.rootNode, node)
		new InvariantChecker().checkInvariant(node)
	}

	@Test
	def void testNodeModelInvariant_03() {
		val script = '''
			var ~Object with {a: string; b: string;}
			var s: string;
			s = r.a;
			s = r.b;
		'''.parseESWithError
		val parseResult = (script.eResource as XtextResource).parseResult
		val node = parseResult.rootNode
		assertSame(node.rootNode, node)
		new InvariantChecker().checkInvariant(node)
	}

	@Test
	def void testNodeModelInvariant_03b() {
		val script = '''
			var ~Object with {b;}
			var s;
		'''.parseESWithError
		val parseResult = (script.eResource as XtextResource).parseResult
		val node = parseResult.rootNode
		assertSame(node.rootNode, node)
		new InvariantChecker().checkInvariant(node)
		assertEquals(4, (script.eResource.errors.head as XtextSyntaxDiagnostic).offset)
	}

	@Test
	def void testNodeModelInvariant_04() {
		val script = '''
			export var ~Object with {a: string; b: string;}
			var s: string;
			s = r.a;
			s = r.b;
		'''.parseESWithError
		val parseResult = (script.eResource as XtextResource).parseResult
		val node = parseResult.rootNode
		assertSame(node.rootNode, node)
		new InvariantChecker().checkInvariant(node)
	}

	@Test
	def void testNodeModelInvariant_05() {
		val script = '''
			var ~Object with {}
			var s;
		'''.parseESWithError
		val parseResult = (script.eResource as XtextResource).parseResult
		val node = parseResult.rootNode
		assertSame(node.rootNode, node)
		new InvariantChecker().checkInvariant(node)
	}

	@Test
	def void testNodeModelInvariant_06() {
		val script = '''
			class A {}
			class B extends A {}
			class C extends B {}
			class D {}

			class G<T> {}
			class H<T,S> {}

			interface R {}
			interface I {}
			enum E{ LITERAL } // cannot be empty


			var ~Object with {a: A;b: B;;
			var rd: ~A with{B b;}+;
		'''.parseESWithError
		val parseResult = (script.eResource as XtextResource).parseResult
		val node = parseResult.rootNode
		assertSame(node.rootNode, node)
		new InvariantChecker().checkInvariant(node)
	}

	@Test
	def void testNodeModelInvariant_07() {
		val script = '''
			class A {}
			class B extends A {}
			class C extends B {}
			class D {}

			class G<T> {}
			class H<T,S> {}

			interface R {}
			interface I {}
			enum E{ LITERAL } // cannot be empty


			var ~Object with {a: A;b: B;}
			var rd: ~A with{B b;}+;
		'''.parseESWithError
		val parseResult = (script.eResource as XtextResource).parseResult
		val node = parseResult.rootNode
		assertSame(node.rootNode, node)
		new InvariantChecker().checkInvariant(node)
	}

	@Test
	def void testForAwaitOf01() {
		val script = '''
			for await (let i = 0; i < 100; i++) {}
		'''.parseESWithError;
		val errors = script.eResource.errors;
		assertEquals(1, errors.size);
		assertEquals(IssueCodes.AST_INVALID_FOR_AWAIT, (errors.head as XtextLinkingDiagnostic).code);
	}

	@Test
	def void testForAwaitOf02() {
		val script = '''
			for await (let p in []) {}
		'''.parseESWithError;
		val errors = script.eResource.errors;
		assertEquals(1, errors.size);
		assertEquals(IssueCodes.AST_INVALID_FOR_AWAIT, (errors.head as XtextLinkingDiagnostic).code);
	}

	@Test
	def void testForAwaitOf03() {
		'''
			for await (let p of []) {}
		'''.parseESSuccessfully;
	}
}
