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

import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.VariableStatement
import org.junit.Test

/**
 */
class N4_7_1_11_ArrowExpressionTest extends AbstractParserTest {

	@Test
	def void testInvokedArrowExpression() {
		'''
			((a, b)=>a + b)()
		'''.parseESSuccessfully
	}

	@Test
	def void testPlainExpression() {
		val script = parseHelper.parse('''
			(a, b)=>a + b
		''');
		val expressionStatement = script.scriptElements.head as ExpressionStatement
		val functionExpression = expressionStatement.expression as FunctionExpression
		assertTrue(functionExpression.arrowFunction)
		assertEquals(2, functionExpression.fpars.size)
	}

	@Test
	def void testPlainExpressionWithTypeInformation() {
		val script = parseHelper.parse('''
			(a: any, b: any)=>a + b
		''');
		val expressionStatement = script.scriptElements.head as ExpressionStatement
		val functionExpression = expressionStatement.expression as FunctionExpression
		assertTrue(functionExpression.arrowFunction)
		assertEquals(2, functionExpression.fpars.size)
	}

	@Test
	def void testPlainExpressionWithTypeInformationTS() {
		val script = parseHelper.parse('''
			( a: any, b: any )=>a + b
		''');
		val expressionStatement = script.scriptElements.head as ExpressionStatement
		val functionExpression = expressionStatement.expression as FunctionExpression
		assertTrue(functionExpression.arrowFunction)
		assertEquals(2, functionExpression.fpars.size)
	}

	@Test
	def void testParenthesizedArrowExpression() {
		'''
			((a, b)=>a + b)
		'''.parseESSuccessfully
	}

	@Test
	def void testNoArgArrowExpression() {
		'''
			()=>{}
		'''.parseESSuccessfully
	}


	@Test
	def void testSingleArgArrowExpression() {
		val script = '''
			x=>x
		'''.parseESSuccessfully
		val expressionStatement = script.scriptElements.head as ExpressionStatement
		val functionExpression = expressionStatement.expression as FunctionExpression
		assertTrue(functionExpression.arrowFunction)
		assertEquals(1, functionExpression.fpars.size)
	}

	@Test
	def void testVariadicArgArrowExpression() {
		val script = '''
			(...x: any)=>x
		'''.parseESSuccessfully
		val expressionStatement = script.scriptElements.head as ExpressionStatement
		val functionExpression = expressionStatement.expression as FunctionExpression
		assertTrue(functionExpression.arrowFunction)
		assertEquals(1, functionExpression.fpars.size)
	}

	@Test
	def void testLineBreakBeforeArrow() {
		'''
			x
			=>x
		'''.parseESWithError
	}

	@Test
	def void testMLCommentBeforeArrow() {
		'''
			x /*
			*/ =>x
		'''.parseESWithError
	}

	@Test
	def void testSingleLineMLCommentBeforeArrow() {
		'''
			x /* comment */ =>x
		'''.parseESSuccessfully
	}

	@Test
	def void testNestedArrowExpressions() {
		val script = '''
			x => x => x
		'''.parseESSuccessfully
		val expressionStatement = script.scriptElements.head as ExpressionStatement
		val functionExpression = expressionStatement.expression as FunctionExpression
		assertTrue(functionExpression.arrowFunction)
		val nested = functionExpression.body.statements.head as ExpressionStatement
		val nestedFunctionExpression = nested.expression as FunctionExpression
		assertTrue(nestedFunctionExpression.arrowFunction)
	}

	@Test
	def void testTraceur_AlphaRename() {
		'''
			var global = this;
			var self = {};

			function outer() {
			  var f = () => {
			    assert.equal(this, self);

			    var g = () => {
			      assert.equal(this, self);
			    };
			    g();

			    var h = function() {
			      assert.equal(this, global);
			    };
			    h();
			  };

			  f();
			}

			outer.call(self);
		'''.parseESSuccessfully
	}

	@Test
	def void testTraceur_Arguments() {
		'''
			function f() {
			  var args = (() => arguments)();
			  assert.equal(args, arguments);
			}

			f();
		'''.parseESSuccessfully
	}

	@Test
	def void testTraceur_ArrowFunctions() {
		'''
			// Options: --block-binding

			// These tests are from:
			// http://wiki.ecmascript.org/doku.php?id=strawman:arrow_function_syntax

			var empty = () => undefined;
			assert.equal(empty(), undefined);

			// Expression bodies needs no parentheses or braces
			var identity = (x) => x;
			assert.equal(identity(empty), empty);

			// Object literals needs to be wrapped in parens.
			var keyMaker = (val) => ({key: val});
			assert.equal(keyMaker(empty).key, empty);

			// => { starts a block.
			var emptyBlock = () => {a: 42};
			assert.equal(emptyBlock(), undefined);

			// Nullary arrow function starts with arrow (cannot begin statement)
			const preamble = 'hello';
			const body = 'world';
			var nullary = () => preamble + ': ' + body;
			assert.equal('hello: world', nullary());

			// No need for parens even for lower-precedence expression body
			var square = x => x * x;
			assert.equal(81, square(9));

			var oddArray = [];
			var array = [2, 3, 4, 5, 6, 7];
			array.forEach((v, i) => { if (i & 1) oddArray[i >>> 1] = v; });
			assert.equal('3,5,7', oddArray.toString());

			var f = (x = 42) => x;
			assert.equal(42, f());

			{
			  var g = (...xs) => xs;
			  assertArrayEquals([0, 1, true], g(0, 1, true));
			}

			var h = (x, ...xs) => xs;
			assertArrayEquals([0, 1, true], h(-1, 0, 1, true));

			assert.equal(typeof (() => {}), 'function');
			assert.equal(Object.getPrototypeOf(() => {}), Function.prototype);

			var i = ({a = 1}) => a;
			assert.equal(i({}), 1);
			assert.equal(i({a: 2}), 2);
		'''.parseESSuccessfully
	}

	@Test
	def void testTraceur_CoverInitialiser() {
		'''
			function f() {
			  (1 ? ({a=0}) => {} : 1);
			}
		'''.parseESSuccessfully
	}

	@Test
	def void testTraceur_Error_CoverInitialiser() {
		'''
			function f() {
			  ({a = (0, {a = 0})} = {})
			}
		'''.parseESWithError
	}

	@Test
	def void testTraceur_Error_CoverInitialiser2() {
		'''
			({a = 0});
		'''.parseESWithError
	}

	@Test
	def void testTraceur_Error_InvalidFormalParameter() {
		'''
			var f = (a, b + 5) => a + b;
		'''.parseESWithError
	}

	@Test
	def void testTraceur_Error_Precedence() {
		'''
			var identity = (x) => {x}.bind({});
		'''.parseESWithError
	}

	@Test
	def void testTraceur_Error_Precedence2() {
		'''
			(x) + (y) => y;
		'''.parseESWithError
	}

	@Test
	def void testTraceur_Error_Precedence3() {
		'''
			(x) + y => y;
		'''.parseESWithError
	}

	@Test
	def void testTraceur_Error_SpreadOutsideFormals() {
		'''
			var f = (x, ...xs);
		'''.parseESWithError
	}

	@Test
	def void testTraceur_FreeVariableChecker() {
		'''
			var identity = (identityParam) => identityParam;
			assert.equal(1234, identity(1234));
		'''.parseESSuccessfully
	}

	@Test
	def void testTraceur_ThisBindings() {
		'''
			// Options: --block-binding

			// These tests are from:
			// http://wiki.ecmascript.org/doku.php?id=strawman:arrow_function_syntax

			const obj = {
			  method: function () {
			    return () => this;
			  }
			};
			assert.equal(obj.method()(), obj);

			var fake = {steal: obj.method()};
			assert.equal(fake.steal(), obj);

			var real = {borrow: obj.method};
			assert.equal(real.borrow()(), real);
		'''.parseESSuccessfully
	}

	/**
	 * @see http://stackoverflow.com/questions/22939130/when-should-i-use-arrow-functions-in-ecmascript-6
	 */
	@Test
	def void testFromStackoverflow_01() {
		'''
			function CommentCtrl($scope, articles) {
			    $scope.comments = [];

			    articles.getList()
			        .then((articles) => Promise.all(articles.map((article) => article.comments.getList())))
			        .then((commentLists) => {
			            $scope.comments = commentLists.reduce((a, b) => a.concat(b));
			        });
			}
		'''.parseESSuccessfully
	}

	/**
	 * @see http://stackoverflow.com/questions/22939130/when-should-i-use-arrow-functions-in-ecmascript-6
	 */
	@Test
	def void testFromStackoverflow_02() {
		'''
			function CommentCtrl($scope, articles) {
			    $scope.comments = [];

			    articles.getList()
			        .then(function (articles) {
			            return Promise.all(articles.map(function (article) {
			                return article.comments.getList();
			            }));
			        })
			        .then(function (commentLists) {
			            $scope.comments = commentLists.reduce(function (a, b) {
			                return a.concat(b);
			            });
			        });
			}
		'''.parseESSuccessfully
	}

	/**
	 * @see http://www.nczonline.net/blog/2013/09/10/understanding-ecmascript-6-arrow-functions/
	 */
	@Test
	def void testNczonline_01() {
		'''
			var reflect = value => value;

			// effectively equivalent to:

			var reflect = function(value) {
			    return value;
			};
		'''.parseESSuccessfully
	}

	@Test
	def void testNczonline_02() {
		'''
			var sum = (num1, num2) => num1 + num2;

			// effectively equivalent to:

			var sum = function(num1, num2) {
			    return num1 + num2;
			};
		'''.parseESSuccessfully
	}

	@Test
	def void testNczonline_03() {
		'''
			var sum = () => 1 + 2;

			// effectively equivalent to:

			var sum = function() {
			    return 1 + 2;
			};
		'''.parseESSuccessfully
	}

	@Test
	def void testNczonline_04() {
		'''
			var sum = (num1, num2) => { return num1 + num2; }

			// effectively equivalent to:

			var sum = function(num1, num2) {
			    return num1 + num2;
			};
		'''.parseESSuccessfully
	}

	@Test
	def void testNczonline_05() {
		'''
			var getTempItem = id => ({ id: id, name: "Temp" });

			// effectively equivalent to:

			var getTempItem = function(id) {

			    return {
			        id: id,
			        name: "Temp"
			    };
			};
		'''.parseESSuccessfully
	}

	@Test
	def void testNczonline_06() {
		'''
			var PageHandler = {

			    id: "123456",

			    init: function() {
			        document.addEventListener("click", function(event) {
			            this.doSomething(event.type);     // error
			        }, false);
			    },

			    doSomething: function(type) {
			        console.log("Handling " + type  + " for " + this.id);
			    }
			};
		'''.parseESSuccessfully
	}

	@Test
	def void testNczonline_07() {
		'''
			var PageHandler = {

			    id: "123456",

			    init: function() {
			        document.addEventListener("click", (function(event) {
			            this.doSomething(event.type);     // error
			        }).bind(this), false);
			    },

			    doSomething: function(type) {
			        console.log("Handling " + type  + " for " + this.id);
			    }
			};
		'''.parseESSuccessfully
	}

	@Test
	def void testNczonline_08() {
		'''
			var PageHandler = {

			    id: "123456",

			    init: function() {
			        document.addEventListener("click",
			                event => this.doSomething(event.type), false);
			    },

			    doSomething: function(type) {
			        console.log("Handling " + type  + " for " + this.id);
			    }
			};
		'''.parseESSuccessfully
	}

	@Test
	def void testNczonline_09() {
		'''
			var result = values.sort(function(a, b) {
			    return a - b;
			});
			var result = values.sort((a, b) => a - b);
		'''.parseESSuccessfully
	}

	@Test
	def void testError_01() {
		val script = '''
				 function f() {
				 	var z = (x)=>{
				 		()=>
				 		return z(x)
				 	}
				 }
		'''.parseESWithError
		val fun = script.scriptElements.head as FunctionDeclaration
		val variableStmt = fun.body.statements.head as VariableStatement
		val z = variableStmt.varDecl.head
		val init = z.expression as ArrowFunction
		val nestedStmt = init.body.statements.head as ExpressionStatement
		val arrow = nestedStmt.expression as ArrowFunction
		assertNotNull(arrow)
		val nestedReturnStmt = init.body.statements.last as ReturnStatement
		assertNotNull(nestedReturnStmt.expression)
	}

	@Test
	def void testError_02() {
		val script = '''
			 function f() {
			 	var z = (x)=>{
			 		()=>
			 		return z(x);
			 	}
			 }
		'''.parseESWithError
		val fun = script.scriptElements.head as FunctionDeclaration
		val variableStmt = fun.body.statements.head as VariableStatement
		val z = variableStmt.varDecl.head
		val init = z.expression as ArrowFunction
		val nestedStmt = init.body.statements.head as ExpressionStatement
		val arrow = nestedStmt.expression as ArrowFunction
		assertNotNull(arrow)
		val nestedReturnStmt = init.body.statements.last as ReturnStatement
		assertNotNull(nestedReturnStmt.expression)
	}

	@Test
	def void testError_03() {
		val script = '''
			 function f() {
			 	var z = (x)=>{
			 		()=>
			 		return z(x);
			 	};
			 };
		'''.parseESWithError
		val fun = script.scriptElements.head as FunctionDeclaration
		val variableStmt = fun.body.statements.head as VariableStatement
		val z = variableStmt.varDecl.head
		val init = z.expression as ArrowFunction
		val nestedStmt = init.body.statements.head as ExpressionStatement
		val arrow = nestedStmt.expression as ArrowFunction
		assertNotNull(arrow)
		val nestedReturnStmt = init.body.statements.last as ReturnStatement
		assertNotNull(nestedReturnStmt.expression)
	}
	
	@Test
	def void testError_04() {
		val script = '''
			 function f() {
			 	var z = (x)=>{
			 		(a)=>
			 		return z(x)
			 	}
			 }
		'''.parseESWithError
		val fun = script.scriptElements.head as FunctionDeclaration
		val variableStmt = fun.body.statements.head as VariableStatement
		val z = variableStmt.varDecl.head
		val init = z.expression as ArrowFunction
		val nestedStmt = init.body.statements.head as ExpressionStatement
		val arrow = nestedStmt.expression as ArrowFunction
		assertNotNull(arrow)
		val nestedReturnStmt = init.body.statements.last as ReturnStatement
		assertNotNull(nestedReturnStmt.expression)
	}
	
	@Test
	def void testError_05() {
		val script = '''
			 function f() {
			 	var z = (x)=>{
			 		(a: a)=>
			 		return z(x)
			 	}
			 }
		'''.parseESWithError
		val fun = script.scriptElements.head as FunctionDeclaration
		val variableStmt = fun.body.statements.head as VariableStatement
		val z = variableStmt.varDecl.head
		val init = z.expression as ArrowFunction
		val nestedStmt = init.body.statements.head as ExpressionStatement
		val arrow = nestedStmt.expression as ArrowFunction
		assertNotNull(arrow)
		val nestedReturnStmt = init.body.statements.last as ReturnStatement
		assertNotNull(nestedReturnStmt.expression)
	}
	
	@Test
	def void testError_06() {
		val script = '''
			 function f() {
			 	var z = (x)=>{
			 		(a: any)=>
			 		return z(x)
			 	}
			 }
		'''.parseESWithError
		val fun = script.scriptElements.head as FunctionDeclaration
		val variableStmt = fun.body.statements.head as VariableStatement
		val z = variableStmt.varDecl.head
		val init = z.expression as ArrowFunction
		val nestedStmt = init.body.statements.head as ExpressionStatement
		val arrow = nestedStmt.expression as ArrowFunction
		assertNotNull(arrow)
		val nestedReturnStmt = init.body.statements.last as ReturnStatement
		assertNotNull(nestedReturnStmt.expression)
	}
}
