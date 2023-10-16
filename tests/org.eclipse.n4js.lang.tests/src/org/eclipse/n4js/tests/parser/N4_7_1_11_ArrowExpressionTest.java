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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.junit.Test;

public class N4_7_1_11_ArrowExpressionTest extends AbstractParserTest {

	@Test
	public void testInvokedArrowExpression() {
		parseESSuccessfully("""
					((a, b)=>a + b)()
				""");
	}

	@Test
	public void testPlainExpression() throws Exception {
		Script script = parseHelper.parse("""
					(a, b)=>a + b
				""");
		ExpressionStatement expressionStatement = (ExpressionStatement) script.getScriptElements().get(0);
		FunctionExpression functionExpression = (FunctionExpression) expressionStatement.getExpression();
		assertTrue(functionExpression.isArrowFunction());
		assertEquals(2, functionExpression.getFpars().size());
	}

	@Test
	public void testPlainExpressionWithTypeInformation() throws Exception {
		Script script = parseHelper.parse("""
					(a: any, b: any)=>a + b
				""");
		ExpressionStatement expressionStatement = (ExpressionStatement) script.getScriptElements().get(0);
		FunctionExpression functionExpression = (FunctionExpression) expressionStatement.getExpression();
		assertTrue(functionExpression.isArrowFunction());
		assertEquals(2, functionExpression.getFpars().size());
	}

	@Test
	public void testPlainExpressionWithTypeInformationTS() throws Exception {
		Script script = parseHelper.parse("""
					( a: any, b: any )=>a + b
				""");
		ExpressionStatement expressionStatement = (ExpressionStatement) script.getScriptElements().get(0);
		FunctionExpression functionExpression = (FunctionExpression) expressionStatement.getExpression();
		assertTrue(functionExpression.isArrowFunction());
		assertEquals(2, functionExpression.getFpars().size());
	}

	@Test
	public void testParenthesizedArrowExpression() {
		parseESSuccessfully("""
					((a, b)=>a + b)
				""");
	}

	@Test
	public void testNoArgArrowExpression() {
		parseESSuccessfully("""
					()=>{}
				""");
	}

	@Test
	public void testSingleArgArrowExpression() {
		Script script = parseESSuccessfully("""
					x=>x
				""");
		ExpressionStatement expressionStatement = (ExpressionStatement) script.getScriptElements().get(0);
		FunctionExpression functionExpression = (FunctionExpression) expressionStatement.getExpression();
		assertTrue(functionExpression.isArrowFunction());
		assertEquals(1, functionExpression.getFpars().size());
	}

	@Test
	public void testVariadicArgArrowExpression() {
		Script script = parseESSuccessfully("""
					(...x: any)=>x
				""");
		ExpressionStatement expressionStatement = (ExpressionStatement) script.getScriptElements().get(0);
		FunctionExpression functionExpression = (FunctionExpression) expressionStatement.getExpression();
		assertTrue(functionExpression.isArrowFunction());
		assertEquals(1, functionExpression.getFpars().size());
	}

	@Test
	public void testLineBreakBeforeArrow() {
		parseESWithError("""
					x
					=>x
				""");
	}

	@Test
	public void testMLCommentBeforeArrow() {
		parseESWithError("""
					x /*
					*/ =>x
				""");
	}

	@Test
	public void testSingleLineMLCommentBeforeArrow() {
		parseESSuccessfully("""
					x /* comment */ =>x
				""");
	}

	@Test
	public void testNestedArrowExpressions() {
		Script script = parseESSuccessfully("""
					x => x => x
				""");
		ExpressionStatement expressionStatement = (ExpressionStatement) script.getScriptElements().get(0);
		FunctionExpression functionExpression = (FunctionExpression) expressionStatement.getExpression();
		assertTrue(functionExpression.isArrowFunction());
		ExpressionStatement nested = (ExpressionStatement) functionExpression.getBody().getStatements().get(0);
		FunctionExpression nestedFunctionExpression = (FunctionExpression) nested.getExpression();
		assertTrue(nestedFunctionExpression.isArrowFunction());
	}

	@Test
	public void testTraceur_AlphaRename() {
		parseESSuccessfully("""
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
				""");
	}

	@Test
	public void testTraceur_Arguments() {
		parseESSuccessfully("""
					function f() {
					  var args = (() => arguments)();
					  assert.equal(args, arguments);
					}

					f();
				""");
	}

	@Test
	public void testTraceur_ArrowFunctions() {
		parseESSuccessfully("""
					// Options: --block-binding

					// These tests are from:
					// http://wiki.ecmascript.org/doku.php?id=strawman:arrow_function_syntax

					var empty = () => undefined;
					assert.equal(empty(), undefined);;

					// Expression bodies needs no parentheses or braces
					var identity = (x) => x;
					assert.equal(identity(empty), empty);;

					// Object literals needs to be wrapped in parens.
					var keyMaker = (val) => ({key: val});
					assert.equal(keyMaker(empty).key, empty);;

					// => { starts a block.
					var emptyBlock = () => {a: 42};
					assert.equal(emptyBlock(), undefined);;

					// Nullary arrow function starts with arrow (cannot begin statement)
					const preamble = 'hello';
					const body = 'world';
					var nullary = () => preamble + ': ' + body;
					assert.equal('hello: world', nullary());;

					// No need for parens even for lower-precedence expression body
					var square = x => x * x;
					assert.equal(81, square(9));;

					var oddArray = [];
					var array = [2, 3, 4, 5, 6, 7];
					array.forEach((v, i) => { if (i & 1) oddArray[i >>> 1] = v; });
					assert.equal('3,5,7', oddArray.toString());;

					var f = (x = 42) => x;
					assert.equal(42, f());;

					{
					  var g = (...xs) => xs;
					  assertArrayEquals([0, 1, true], g(0, 1, true));
					}

					var h = (x, ...xs) => xs;
					assertArrayEquals([0, 1, true], h(-1, 0, 1, true));;

					assert.equal(typeof (() => {}), 'function');;
					assert.equal(Object.getPrototypeOf(() => {}), Function.prototype);;

					var i = ({a = 1}) => a;
					assert.equal(i({}), 1);;
					assert.equal(i({a: 2}), 2);;
				""");
	}

	@Test
	public void testTraceur_CoverInitialiser() {
		parseESSuccessfully("""
					function f() {
					  (1 ? ({a=0}) => {} : 1);
					}
				""");
	}

	@Test
	public void testTraceur_Error_CoverInitialiser() {
		parseESWithError("""
					function f() {
					  ({a = (0, {a = 0})} = {})
					}
				""");
	}

	@Test
	public void testTraceur_Error_CoverInitialiser2() {
		parseESWithError("""
					({a = 0});
				""");
	}

	@Test
	public void testTraceur_Error_InvalidFormalParameter() {
		parseESWithError("""
					var f = (a, b + 5) => a + b;
				""");
	}

	@Test
	public void testTraceur_Error_Precedence() {
		parseESWithError("""
					var identity = (x) => {x}.bind({});
				""");
	}

	@Test
	public void testTraceur_Error_Precedence2() {
		parseESWithError("""
					(x) + (y) => y;
				""");
	}

	@Test
	public void testTraceur_Error_Precedence3() {
		parseESWithError("""
					(x) + y => y;
				""");
	}

	@Test
	public void testTraceur_Error_SpreadOutsideFormals() {
		parseESWithError("""
					var f = (x, ...xs);
				""");
	}

	@Test
	public void testTraceur_FreeVariableChecker() {
		parseESSuccessfully("""
					var identity = (identityParam) => identityParam;
					assert.equal(1234, identity(1234));;
				""");
	}

	@Test
	public void testTraceur_ThisBindings() {
		parseESSuccessfully("""
					// Options: --block-binding

					// These tests are from:
					// http://wiki.ecmascript.org/doku.php?id=strawman:arrow_function_syntax

					const obj = {
					  method: function () {
					    return () => this;
					  }
					};
					assert.equal(obj.method()(), obj);;

					var fake = {steal: obj.method()};
					assert.equal(fake.steal(), obj);;

					var real = {borrow: obj.method};
					assert.equal(real.borrow()(), real);;
				""");
	}

	/**
	 *
	 * @see <a href=
	 *      "http://stackoverflow.com/questions/22939130/when-should-i-use-arrow-functions-in-ecmascript-6"/>link</a>
	 */
	@Test
	public void testFromStackoverflow_01() {
		parseESSuccessfully("""
					function CommentCtrl($scope, articles) {
					    $scope.comments = [];

					    articles.getList()
					        .then((articles) => Promise.all(articles.map((article) => article.comments.getList())))
					        .then((commentLists) => {
					            $scope.comments = commentLists.reduce((a, b) => a.concat(b));
					        });
					}
				""");
	}

	/**
	 * @see <a href=
	 *      "http://stackoverflow.com/questions/22939130/when-should-i-use-arrow-functions-in-ecmascript-6"/>link</a>
	 */
	@Test
	public void testFromStackoverflow_02() {
		parseESSuccessfully("""
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
				""");
	}

	/**
	 * @see <a href="http://www.nczonline.net/blog/2013/09/10/understanding-ecmascript-6-arrow-functions/"/>link</a>
	 */
	@Test
	public void testNczonline_01() {
		parseESSuccessfully("""
					var reflect = value => value;

					// effectively equivalent to:

					var reflect = function(value) {
					    return value;
					};
				""");
	}

	@Test
	public void testNczonline_02() {
		parseESSuccessfully("""
					var sum = (num1, num2) => num1 + num2;

					// effectively equivalent to:

					var sum = function(num1, num2) {
					    return num1 + num2;
					};
				""");
	}

	@Test
	public void testNczonline_03() {
		parseESSuccessfully("""
					var sum = () => 1 + 2;

					// effectively equivalent to:

					var sum = function() {
					    return 1 + 2;
					};
				""");
	}

	@Test
	public void testNczonline_04() {
		parseESSuccessfully("""
					var sum = (num1, num2) => { return num1 + num2; }

					// effectively equivalent to:

					var sum = function(num1, num2) {
					    return num1 + num2;
					};
				""");
	}

	@Test
	public void testNczonline_05() {
		parseESSuccessfully("""
					var getTempItem = id => ({ id: id, name: "Temp" });

					// effectively equivalent to:

					var getTempItem = function(id) {

					    return {
					        id: id,
					        name: "Temp"
					    };
					};
				""");
	}

	@Test
	public void testNczonline_06() {
		parseESSuccessfully("""
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
				""");
	}

	@Test
	public void testNczonline_07() {
		parseESSuccessfully("""
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
				""");
	}

	@Test
	public void testNczonline_08() {
		parseESSuccessfully("""
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
				""");
	}

	@Test
	public void testNczonline_09() {
		parseESSuccessfully("""
					var result = values.sort(function(a, b) {
					    return a - b;
					});
					var result = values.sort((a, b) => a - b);
				""");
	}

	@Test
	public void testError_01() {
		Script script = parseESWithError("""
						 function f() {
						 	var z = (x)=>{
						 		()=>
						 		return z(x)
						 	}
						 }
				""");
		FunctionDeclaration fun = (FunctionDeclaration) script.getScriptElements().get(0);
		VariableStatement variableStmt = (VariableStatement) fun.getBody().getStatements().get(0);
		VariableDeclaration z = variableStmt.getVarDecl().get(0);
		ArrowFunction init = (ArrowFunction) z.getExpression();
		ExpressionStatement nestedStmt = (ExpressionStatement) init.getBody().getStatements().get(0);
		ArrowFunction arrow = (ArrowFunction) nestedStmt.getExpression();
		assertNotNull(arrow);
		ReturnStatement nestedReturnStmt = (ReturnStatement) last(init.getBody().getStatements());
		assertNotNull(nestedReturnStmt.getExpression());
	}

	@Test
	public void testError_02() {
		Script script = parseESWithError("""
					 function f() {
					 	var z = (x)=>{
					 		()=>
					 		return z(x);
					 	}
					 }
				""");
		FunctionDeclaration fun = (FunctionDeclaration) script.getScriptElements().get(0);
		VariableStatement variableStmt = (VariableStatement) fun.getBody().getStatements().get(0);
		VariableDeclaration z = variableStmt.getVarDecl().get(0);
		ArrowFunction init = (ArrowFunction) z.getExpression();
		ExpressionStatement nestedStmt = (ExpressionStatement) init.getBody().getStatements().get(0);
		ArrowFunction arrow = (ArrowFunction) nestedStmt.getExpression();
		assertNotNull(arrow);
		ReturnStatement nestedReturnStmt = (ReturnStatement) last(init.getBody().getStatements());
		assertNotNull(nestedReturnStmt.getExpression());
	}

	@Test
	public void testError_03() {
		Script script = parseESWithError("""
					 function f() {
					 	var z = (x)=>{
					 		()=>
					 		return z(x);
					 	};
					 };
				""");
		FunctionDeclaration fun = (FunctionDeclaration) script.getScriptElements().get(0);
		VariableStatement variableStmt = (VariableStatement) fun.getBody().getStatements().get(0);
		VariableDeclaration z = variableStmt.getVarDecl().get(0);
		ArrowFunction init = (ArrowFunction) z.getExpression();
		ExpressionStatement nestedStmt = (ExpressionStatement) init.getBody().getStatements().get(0);
		ArrowFunction arrow = (ArrowFunction) nestedStmt.getExpression();
		assertNotNull(arrow);
		ReturnStatement nestedReturnStmt = (ReturnStatement) last(init.getBody().getStatements());
		assertNotNull(nestedReturnStmt.getExpression());
	}

	@Test
	public void testError_04() {
		Script script = parseESWithError("""
					 function f() {
					 	var z = (x)=>{
					 		(a)=>
					 		return z(x)
					 	}
					 }
				""");
		FunctionDeclaration fun = (FunctionDeclaration) script.getScriptElements().get(0);
		VariableStatement variableStmt = (VariableStatement) fun.getBody().getStatements().get(0);
		VariableDeclaration z = variableStmt.getVarDecl().get(0);
		ArrowFunction init = (ArrowFunction) z.getExpression();
		ExpressionStatement nestedStmt = (ExpressionStatement) init.getBody().getStatements().get(0);
		ArrowFunction arrow = (ArrowFunction) nestedStmt.getExpression();
		assertNotNull(arrow);
		ReturnStatement nestedReturnStmt = (ReturnStatement) last(init.getBody().getStatements());
		assertNotNull(nestedReturnStmt.getExpression());
	}

	@Test
	public void testError_05() {
		Script script = parseESWithError("""
					 function f() {
					 	var z = (x)=>{
					 		(a: a)=>
					 		return z(x)
					 	}
					 }
				""");
		FunctionDeclaration fun = (FunctionDeclaration) script.getScriptElements().get(0);
		VariableStatement variableStmt = (VariableStatement) fun.getBody().getStatements().get(0);
		VariableDeclaration z = variableStmt.getVarDecl().get(0);
		ArrowFunction init = (ArrowFunction) z.getExpression();
		ExpressionStatement nestedStmt = (ExpressionStatement) init.getBody().getStatements().get(0);
		ArrowFunction arrow = (ArrowFunction) nestedStmt.getExpression();
		assertNotNull(arrow);
		ReturnStatement nestedReturnStmt = (ReturnStatement) last(init.getBody().getStatements());
		assertNotNull(nestedReturnStmt.getExpression());
	}

	@Test
	public void testError_06() {
		Script script = parseESWithError("""
					 function f() {
					 	var z = (x)=>{
					 		(a: any)=>
					 		return z(x)
					 	}
					 }
				""");
		FunctionDeclaration fun = (FunctionDeclaration) script.getScriptElements().get(0);
		VariableStatement variableStmt = (VariableStatement) fun.getBody().getStatements().get(0);
		VariableDeclaration z = variableStmt.getVarDecl().get(0);
		ArrowFunction init = (ArrowFunction) z.getExpression();
		ExpressionStatement nestedStmt = (ExpressionStatement) init.getBody().getStatements().get(0);
		ArrowFunction arrow = (ArrowFunction) nestedStmt.getExpression();
		assertNotNull(arrow);
		ReturnStatement nestedReturnStmt = (ReturnStatement) last(init.getBody().getStatements());
		assertNotNull(nestedReturnStmt.getExpression());
	}
}
