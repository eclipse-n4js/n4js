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
package org.eclipse.n4js.transpiler.es.tests

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderMockProjectSuppressedValidator
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

import static extension org.junit.Assert.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderMockProjectSuppressedValidator)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class BlockTransformationTest extends AbstractTranspilerTest {

	@Inject extension ParseHelper<Script>



	@Test
	def void testAsyncAwait() {

		val state = '''
			async function fetchModule(p : string, o : string) :Object {
			    var module : Object,
			        normalizedPath = await System2.normalize(p);
			    return module;
			}
			export public class System2 {
			    public static async normalize(p : string) : string {
			    	return "!NORMAL!/"+p;
			    }
			}
		'''.createTranspilerState.transform;

// Old transpiler output:
//		'''
//			fetchModule = function fetchModule(p,o){ return $spawn(function*() {
//
//						    var  module,
//						        normalizedPath = yield System2.normalize(p);
//			 yield  module;
//			 return;
//			}.bind(this));};
// .....
//	        normalize: { value: function
//		        			    normalize___n4(p){ return $spawn(function*() {
//		         yield  "!NORMAL!/"+p;
//		         return;
//		        }.bind(this));}}
//	 	   },
//		'''

		val F_AST = state.findFirstInAST(FunctionDeclaration);
		val F_IM = state.findFirstInIM(FunctionExpression,[name=="fetchModule"]);
		val N_IM = state.findFirstInIM(FunctionExpression,[name=="normalize"+N4JSLanguageConstants.METHOD_STACKTRACE_SUFFIX]);

		// precondition:
		"Should have 2 parameters".assertEquals(2, F_AST.fpars.size)
		"has async".assertTrue( F_AST.isAsync() )

		"Content of async function should be wrapped in one return statement.".assertEquals(1,F_IM.body.statements.size);
		val expr_spawnCall_IM = (F_IM.body.statements.get(0) as ReturnStatement).expression;
		val spawnCall_IM = expr_spawnCall_IM as ParameterizedCallExpression
		assertEquals("$spawn", (spawnCall_IM.target as IdentifierRef_IM).id_IM.name);

		"Content of async method normalize___n4() should be wrapped in one return statement.".assertEquals(1,N_IM.body.statements.size);
		val N_expr_spawnCall_IM = (N_IM.body.statements.get(0) as ReturnStatement).expression;
		val N_spawnCall_IM = N_expr_spawnCall_IM as ParameterizedCallExpression
		assertEquals("$spawn", (N_spawnCall_IM.target as IdentifierRef_IM).id_IM.name);

	}


		@Test
	def void testReplace_Arguments_CompileResult() throws Throwable{

		val script = '''
			function f() {
				var aa = arguments.length;
				var ab = arguments[1];
			}
		''';

		val moduleWrapped = '''
			'use strict';
			System.register([], function($n4Export) {
				var f;
				f = function f() {
					var $capturedArgs = arguments;
					var aa = $capturedArgs.length;
					var ab = $capturedArgs[1];
				};
				return {
					setters: [],
					execute: function() {}
				};
			});
		'''.cjsPatched

	 	// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript;

   		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet)
		scriptNode.resolveLazyRefs

		assertCompileResult(scriptNode,moduleWrapped)
	}
	@Test
	def void testReplaceAsyncAwait_CompileResult() throws Throwable {

		val script = '''
			async function fetchModule(p: string, o: string) :Object {
			    var module: Object,
			        normalizedPath = await System2.normalize(p);
			    return module;
			}
			export public class System2 {
			    public static async normalize(p: string): string {
			    	return "!NORMAL!/"+p;
			    }
			}
		''';

		val moduleWrapped = '''
			'use strict';
			System.register([], function($n4Export) {
				var fetchModule, System2;
				fetchModule = function fetchModule(p, o) {

					return $spawn(function*() {

						var module, normalizedPath = (yield System2.normalize(p));«/* await -> yield */»
						return module;
					}.apply(this,arguments));
				};
				System2 = function System2() {};
				$n4Export('System2', System2);
				return {
					setters: [],
					execute: function() {

						$makeClass(System2, N4Object, [], {}, {
							normalize: {
								value: function normalize___n4(p) {
									return $spawn(function*() {

									         return "!NORMAL!/"+p;
									        }.apply(this,arguments));
									}
							}
						}, function(instanceProto, staticProto) {

							var metaClass = new N4Class({
								name: 'System2',
								origin: 'test',
								fqn: 'A.System2',
								n4superType: N4Object.n4type,
								allImplementedInterfaces: [],
								ownedMembers: [
									new N4Method({
										name: 'normalize',
										isStatic: true,
										jsFunction:staticProto['normalize'],
										annotations: []
									})
								],
								consumedMembers: [],
								annotations: []
							});
							return metaClass;
						});
					}
				};
			});
		'''.cjsPatched;

	 	// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript;

   		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet);
		scriptNode.resolveLazyRefs;

		assertCompileResult(scriptNode,moduleWrapped);


	}


}
