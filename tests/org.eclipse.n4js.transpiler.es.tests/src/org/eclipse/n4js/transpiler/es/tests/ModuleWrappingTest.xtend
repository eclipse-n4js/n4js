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
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.N4JSInjectorProviderWithMockProject
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.util.ResourceHelper
import org.eclipse.xtext.util.CancelIndicator
import org.junit.FixMethodOrder
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

import static org.junit.Assert.*

/**
 * All tests herein starting with temp_ should be replaced with textual comparisons.
 * <pre>
 * %% Template:
 * def _00_XX() throws Throwable  {
 *     val script =
 *         '''
 *         '''
 *     val moduleWrapped =
 *         '''
 *         '''
 *     assertCompileResult(script,moduleWrapped)
 * }
 * </pre>
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithMockProject)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ModuleWrappingTest extends AbstractTranspilerTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ResourceHelper

	/** builder-patter-like additional Script after #installExportedScript was called. */
	def ResourceSet andInstallOtherExportedScript(ResourceSet rs) throws Throwable {
		val otherExportedScript =
		'''
		export public class C1 { public m1(){} }
		export public class C2 { public m2(){} }
		'''
		otherExportedScript.resource(URI.createURI("src/OtherExportedStuff."+N4JSGlobals.N4JS_FILE_EXTENSION),rs);
		return rs;
	}

	/** Testing  a completely empty script.  */
	@Test
	def _01_emptyModule() throws Throwable  {
		val emptyScript = '''
		'''

		val script = emptyScript.parse()
		script.resolveLazyRefs

		// As long as Pretty print is not here, we get a dump of the structure
		val generatedResult = esSubGen.getCompileResultAsText(script, GENERATOR_OPTIONS);

		val moduleWrapped='''
			'use strict';
			System.register([], function($n4Export) {

			    return {
			        setters: [],
			        execute: function() {

			        }
			    };
			});
		'''.cjsPatched;

		// ignoring pretty printing, we want to compare:
		AbstractTranspilerTest.assertSameExceptWhiteSpace ( moduleWrapped, generatedResult );
	}


	@Test
	def _02_singleStatement() throws Throwable {
		val script = '''console.log('"hi"')'''
		val moduleWrapped =
		'''
			'use strict';
			System.register([], function($n4Export) {

			    return {
			        setters: [],
			        execute: function() {
			console.log('"hi"');
			        }
			    };
			});
		'''.cjsPatched;
		// current behaviour replaces quotes and inserts a semicolon.
		assertCompileResult(script,moduleWrapped)
	}

	@Test
	def structure_02_singleStatement() throws Throwable {
		val script = '''console.log('hi')'''
		// Expected textual output:
		//   		val moduleWrapped =
		//   		'''
		//		(function(global) { var System = (typeof module !== 'undefined' && module.exports ? require('runtime-env.nodejs/node-n4js').System(module) : global.System);
		//			System.register([], function($n4Export) {
		//
		//			    return {
		//			        setters: [],
		//			        execute: function() {
		//			console.log('hi')
		//			        }
		//			    };
		//			});
		//		})(typeof global === 'object' ? global : self);
		//   		'''
		val scriptResource = script.resource as N4JSResource
		scriptResource.resolveLazyCrossReferences(CancelIndicator.NullImpl)
		val transpilerState = prepareAndTransform(scriptResource);

		assertEquals("CJS wrapper embraces all in one statement.",1,transpilerState.im.scriptElements.size)


		// create a view to assert the part without insertion-Wrapping.
		val cjs_wrapper = (transpilerState.im.scriptElements.get(0) as ExpressionStatement).expression as ParameterizedCallExpression;
		val cjs_fun_expr = (cjs_wrapper.target as ParenExpression).expression as FunctionExpression;

		assertEquals("There should be only one statement: System.register call (plus strict mode directive).",1+1, cjs_fun_expr.body.statements.size);

		val sys_reg = cjs_fun_expr.body.statements.get(1) // 1st stmt is original System-register call:

		// ASSERTION CODE WITHOUT CJS WRAPPING:
		{
			//  WAS: 		val im_n = transpilerState.im
			//	WAS:		assertEquals( "Exactly one ExpressionStatement expected.", 1, im_n.scriptElements.size );
			//	WAS:		val excprStmt = im_n.scriptElements.get(0) as ExpressionStatement
			val excprStmt = sys_reg as ExpressionStatement;

			val fe_with_n4Export =  (excprStmt.expression as ParameterizedCallExpression).arguments.get(1).expression as FunctionExpression;
			assertEquals("$n4Export", fe_with_n4Export.fpars.get(0).name )

			val ol_in_ret = (fe_with_n4Export.body.statements.get(0) as ReturnStatement).expression as ObjectLiteral
			assertEquals("setters", ol_in_ret.propertyAssignments.get(0).name)
			assertEquals("execute", ol_in_ret.propertyAssignments.get(1).name)

			// setter:
			val setter_al = (ol_in_ret.propertyAssignments.get(0) as PropertyNameValuePair).expression as ArrayLiteral
			assertEquals("no setters expeceted", 0 , setter_al.elements.size );

			// execute:
			val execute_body_stmts = ((ol_in_ret.propertyAssignments.get(1) as PropertyNameValuePair).expression as FunctionExpression).body.statements
			assertEquals("one statement expected", 1 , execute_body_stmts.size );
		}

	}

	@Test
	def _03_hoisted_declared_variable() throws Throwable {
		val script = '''
		console.log('hi')
		var a;
		'''
		val moduleWrapped =
		'''
		'use strict';
		System.register([], function($n4Export) {
		    var a;

		    return {
		        setters: [],
		        execute: function() {
		console.log('hi');
		        }
		    };
		});
		'''.cjsPatched;
		assertCompileResult(script,moduleWrapped)
	}

	@Ignore // interfaces not yet implemented.
	@Test
	def _03b_hoisted_multiple() throws Throwable {
		val script = '''
		class C{}
		interface F {}
		function f () {}
		var  v = 'val';
		'''
		val moduleWrapped =
		'''
		'use strict';
		System.register([], function($n4Export) {
		    var f, v, C, F;
		C = function C() {}; // maybe initializer should be in the execute funciton
		F = { }; // maybe initializer should be in the execute funciton
		    return {
		        setters: [],
		        execute: function() {
		$makeClass(C, /* ... */ );
		$makeInterface(	F, /* ... */);
		f = function f(){};
		  v = 'val';
		        }
		    };
		});
		'''.cjsPatched;
		assertCompileResult(script,moduleWrapped)
	}

	@Test
	def _04_unused_Imports() throws Throwable {
		val script = '''
		import { C1 } from "ExportedStuff"
		import { C2 } from "ExportedStuff"
		var c2: C2 = new C2();
		c2.m2();
		'''
		val moduleWrapped =
		'''
			'use strict';
			System.register(['ExportedStuff'],
			function ($n4Export ){
			   var C2 ,
			       c2
			   ;
			   return {
			            setters  : [function ($exports){
			            	          // ExportedStuff
			                          C2 = $exports.C2;
			                        }                                                    ],
			            execute  : function (){
			            			 c2 = new C2();
			                         c2.m2();
			                       }
			          }                                                                    ;
			 }                                                                              );

		'''.cjsPatched;

		// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript
		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"), resSet)
		scriptNode.resolveLazyRefs

		val import = scriptNode.scriptElements.filter(ImportDeclaration).head
		assertFalse(import.module.eIsProxy)
		assertFalse((import.importSpecifiers.head as NamedImportSpecifier).importedElement.eIsProxy)

		assertCompileResult(scriptNode, moduleWrapped)
	}

	@Test
	def _05_named_imports() throws Throwable {
		val script = '''
		import { C1 } from "ExportedStuff"
		import { C2 } from "ExportedStuff"

		console.log(C1)
		console.log(C2)
		'''
		val moduleWrapped =
		'''
		'use strict';
		System.register(['ExportedStuff'],
		function ($n4Export ){
		   var C1 ,
		       C2
		   ;
		   return {
		            setters  : [function ($exports){
		                          // ExportedStuff
		                          C1 = $exports.C1;
		                          C2 = $exports.C2;
		                        }                                                    ],
		            execute  : function (){
		                         console.log(C1);
		                         console.log(C2);
		                       }
		          }                                                                    ;
		 }                                                                              );

		'''.cjsPatched;

		// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript
		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet)
		scriptNode.resolveLazyRefs


		assertCompileResult(scriptNode,moduleWrapped)
	}

	@Test
	def _06_multiple_named_imports() throws Throwable {
		val script = '''
		import { C1 } from "ExportedStuff"
		import { C2 as XX } from "ExportedStuff"
		import { C2 } from "OtherExportedStuff"

		console.log(C1)
		console.log(C2)
		console.log(XX)
		'''
		val moduleWrapped =
		'''
		'use strict';
		System.register(['ExportedStuff', 'OtherExportedStuff'],
		function ($n4Export ){
		   var C1 ,
		       XX ,
		       C2
		   ;
		   return {
		            setters  : [function ($exports ){
		                          // ExportedStuff
		                          C1 = $exports.C1;
		                          XX = $exports.C2;
		                        }
		                        , function ($exports){
		                          // OtherExportedStuff
								  C2 = $exports.C2;
								}],
		            execute  : function (){
		                         console.log(C1);
		                         console.log(C2);
		                         console.log(XX);
		                       }
		          };
		});
		'''.cjsPatched;

		// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript
					.andInstallOtherExportedScript;

		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet)
		scriptNode.resolveLazyRefs

		assertCompileResult(scriptNode,moduleWrapped)
	}

	@Test
	def _07_namespace_import() throws Throwable {
		val script = '''
		import { C2 } from "OtherExportedStuff"
		import * as Namespace from "ExportedStuff"

		console.log(Namespace.C1)
		console.log(C2)
		console.log(Namespace.C2)
		'''
		val moduleWrapped =
		'''
		'use strict';
		System.register(['OtherExportedStuff', 'ExportedStuff'],
		function ($n4Export ){
		   var C2 ,
		       Namespace
		   ;
		   return {
		            setters  : [
		                function ($exports){
		                    // OtherExportedStuff
		                    C2 = $exports.C2;
		                },
		                function ($exports){
		                    // ExportedStuff
		                    Namespace = $exports;
		                }
		            ],
		            execute  : function (){
		                         console.log(Namespace.C1);
		                         console.log(C2);
		                         console.log(Namespace.C2);
		                       }
		          };
		 } );
		'''.cjsPatched;

		// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript
					.andInstallOtherExportedScript;

		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet)
		scriptNode.resolveLazyRefs

		assertCompileResult(scriptNode,moduleWrapped)
	}

	@Test
	def _08_exports() throws Throwable  {
		val script = '''
		export var a;
		export function f(){}
		'''
		val moduleWrapped =
		'''
			'use strict';
			System.register([], function($n4Export) {
				var a, f;
				f = function f() {};
				$n4Export('f', f);
				return {
					setters: [],
					execute: function() {}
				};
			});
		'''.cjsPatched;
		assertCompileResult(script,moduleWrapped)
	}

	@Test
	def _09_Class_declaration_field_init() throws Throwable  {
		val script = '''
			import { C1 } from "ExportedStuff"
			export public class A {
				C1 c1 = new C1();

				constructor() {
					console.log("ctor A")
				}

				method1() {
					console.log("do it in method1");
				}
			}
		'''
		val moduleWrapped =
		'''
			'use strict';
			System.register(['ExportedStuff'],
			function ($n4Export ){
			    var C1 ,
			        A
			    ;
				A = function A() {

						this.c1 =  new C1();

						console.log("ctor A");
					};
				$n4Export('A', A);

			    return {
			             setters  : [function ($exports){
			                           // ExportedStuff
			                           C1 = $exports.C1;
			                         }                                                      ],
			             execute  : function (){
			                          $makeClass(A, N4Object, [], {
			                                                  method1  : {
			                                                               value  : function method1___n4(){

			                                                                            console.log("do it in method1");

			                                                                        }
			                                                             },
			                                                   c1: {
			                                          					value: undefined,
			                                   							writable: true
			                                                   		}
			                                                } ,
			                                          {},
			                                          function (instanceProto , staticProto ){

			                                              var metaClass  = new N4Class({
			                                                             name  : 'A',
			                                                             origin  : 'test',
			                                                             fqn  : 'A.A',
			                                                             n4superType  : N4Object.n4type,
			                                                             allImplementedInterfaces  : [],
			                                                             ownedMembers  : [new N4DataField({
			                                                                                name  : 'c1',
			                                                                                isStatic  : false,
			                                                                                annotations  : []
			                                                                              }                   ),
			                                                                               new N4Method({
			                                                                                name  : 'constructor',
			                                                                                isStatic  : false,
			                                                                                jsFunction:instanceProto['constructor'],
			                                                                                annotations  : []
			                                                                              }                       ),
			                                                                              new N4Method({
			                                                                                name  : 'method1',
			                                                                                isStatic  : false,
			                                                                                jsFunction:instanceProto['method1'],
			                                                                                annotations  : []
			                                                                              }                   )],
			                                                             consumedMembers  : [],
			                                                             annotations  : []
			                                                           } );
			                              return metaClass;
				               });« /*End make class */»
			              }«/* End Execute function */»
			           }  ;«/* End return OL */»
			  }  );
		'''.cjsPatched;

		// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript
					.andInstallOtherExportedScript;

		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet)
		scriptNode.resolveLazyRefs

		assertCompileResult(scriptNode,moduleWrapped)

	}
	@Test
	def _10_Class_declaration_static_field_init() throws Throwable  {
		val script = '''
			import { C1 } from "ExportedStuff"
			export public class A {
				static c1: C1 = new C1();

				constructor() {
					console.log("ctor A")
				}

				method1() {
					console.log("do it in method1");
				}
			}
		'''
		val moduleWrapped =
		'''
			'use strict';
			System.register(['ExportedStuff'],
			function ($n4Export ){
			    var C1 ,
			        A
			    ;
				A = function A() {

						console.log("ctor A");
					};
				$n4Export('A', A);

			    return {
			             setters  : [function ($exports){
			                          // ExportedStuff
			                           C1 = $exports.C1;
			                         }                                                      ],
			             execute  : function (){
			                          $makeClass(A, N4Object, [], {
			                                                  method1  : {
			                                                               value  : function method1___n4(){
			                                                                            console.log("do it in method1");
			                                                                        }
			                                                             }},
			                                                  {
			                                                  c1: {
			                                            				value: undefined,
			                                                  			writable: true

			                                                }
			                                          },
			                                          function (instanceProto , staticProto ){

												         var metaClass  = new N4Class({
												                                 name  : 'A',
												                                 origin  : 'test',
												                                 fqn  : 'A.A',
												                                 n4superType  : N4Object.n4type,
												                                 allImplementedInterfaces  : [],
												                                 ownedMembers  : [new N4DataField({
						                                                                    name  : 'c1',
						                                                                    isStatic  : true,
						                                                                    annotations  : []
						                                                                  }                  ),
						                                                                  new N4Method({
			                                                                               name  : 'constructor',
			                                                                               isStatic  : false,
			                                                                               jsFunction: instanceProto['constructor'],
			                                                                               annotations  : []
			                                                                             }                       ),
			                                                                             new N4Method({
			                                                                               name  : 'method1',
			                                                                               isStatic  : false,
			                                                                               jsFunction: instanceProto['method1'],
			                                                                               annotations  : []
			                                                                             }                   )],
												                                 consumedMembers  : [],
												                                 annotations  : []
												                               }  );
												             return metaClass;
														}  );
										A.c1 =  new C1();

			                        }
			           }  ;
			  }  );
		'''.cjsPatched;

		// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript
					.andInstallOtherExportedScript;

		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet)
		scriptNode.resolveLazyRefs

		assertCompileResult(scriptNode,moduleWrapped)

	}

	@Test
	def void structure_10_Class_declaration_static_field_init() throws Throwable  {
		val script = '''
			import { C1 } from "ExportedStuff"
			export public class A {
				static c1: C1 = new C1();

				constructor() {
					console.log("ctor A")
				}

				method1() {
					console.log("do it in method1");
				}
			}
		'''
		// Expected output §-marked thinks to check:
		//   		'''

		//		(function(global) { var System = (typeof module !== 'undefined' && module.exports ? require('runtime-env.nodejs/node-n4js').System(module) : global.System);

		//			System.register(["test/ExportedStuff"],
		//			function ($n4Export ){
		//			    var C1 ,
		//			        A
		//			    ;
		//	§1			A = function A() {
		//	§2					console.log("ctor A");
		//					};
		//				$n4Export("A", A);
		//
		//			    return {
		//			             setters  : [function ($_import_ExportedStuff ){
		//			                           C1 = $_import_ExportedStuff.C1;
		//			                         }                                                      ],
		//			             execute  : function (){
		//			                          $makeClass(A, Object, {
		//			                                                  method1  : {
		//			                                                               value  : function method1___n4(){
		//			                                                                          {
		//			                                                                            console.log("do it in method1");
		//			                                                                          }
		//			                                                                        }
		//			                                                             }
		//			                                                } ,
		//			                                          {},
		//			                                          function (instanceProto , staticProto ){
		//			                                            {  }
		//			                                          } );
		//
		//	§3									A.c1 =  new C1();
		//
		//			                        }
		//			           }  ;
		//			  }  );

		//		})(typeof global === 'object' ? global : self);

		//   		'''

		// Prepare ResourceSet to contain exportedScript:
		val resSet = installExportedScript
					.andInstallOtherExportedScript;

		val Script scriptNode = script.parse(URI.createURI("src/A.n4js"),resSet)
		scriptNode.resolveLazyRefs


		val transpilerState = prepareAndTransform(scriptNode.eResource as N4JSResource);
		val im_n = transpilerState.im


		assertEquals( "Exactly one ExpressionStatement expected.", 1, im_n.scriptElements.size );

		val cjs_wrapper = (transpilerState.im.scriptElements.get(0) as ExpressionStatement).expression as ParameterizedCallExpression;
		val cjs_fun_expr = (cjs_wrapper.target as ParenExpression).expression as FunctionExpression;

		assertEquals("There should be only one statement: System.register call (plus strict mode directive).",1+1, cjs_fun_expr.body.statements.size);

		val system_register_excprStmt = cjs_fun_expr.body.statements.get(1) as ExpressionStatement // 1st stmt is original System-register call:

		val fe_with_n4Export =  (system_register_excprStmt.expression as ParameterizedCallExpression).arguments.get(1).expression as FunctionExpression;
		assertEquals("$n4Export", fe_with_n4Export.fpars.get(0).name )

		// §1
		val assignClassInit = (fe_with_n4Export.body.statements.get(1) as ExpressionStatement).expression as AssignmentExpression
		assertEquals( "A",(assignClassInit.lhs as IdentifierRef_IM).id_IM.name )

		val ctor_A = (assignClassInit.rhs as FunctionExpression)
		assertEquals("cTor of A expected","A", ctor_A.name)
		assertEquals("cTor of A with no FPars expected",0, ctor_A.fpars.size )

		// §2
		val calls = EcoreUtil2.eAllOfType(ctor_A.body, ParameterizedCallExpression)
		assertEquals("One call for console.log expected", 1, calls.size)
		val pCall = calls.get(0)
		assertEquals("argument of console.log expected","ctor A", (pCall.arguments.get(0).expression as StringLiteral).value)

		val assignments = EcoreUtil2.eAllOfType(ctor_A.body,AssignmentExpression)
		assertEquals("there should not be any assignment in ctor.",0,assignments.size)

		val ol_in_ret = (fe_with_n4Export.body.statements.get(3) as ReturnStatement).expression as ObjectLiteral
		assertEquals("setters", ol_in_ret.propertyAssignments.get(0).name)
		assertEquals("execute", ol_in_ret.propertyAssignments.get(1).name)

		// setter:
		val setter_al = (ol_in_ret.propertyAssignments.get(0) as PropertyNameValuePair).expression as ArrayLiteral
		assertEquals("one setter expeceted", 1 , setter_al.elements.size );

		// execute:
		val execute_body = ((ol_in_ret.propertyAssignments.get(1) as PropertyNameValuePair).expression as FunctionExpression).body
		val execute_body_stmts = execute_body.statements
		assertTrue("at least two statements expected", 2 <= execute_body_stmts.size );

		// §3
		val exec_assigns = EcoreUtil2.eAllOfType( execute_body, AssignmentExpression )
		val last_assign_in_execute = exec_assigns.last

		assertNotNull("Static initializer expected", last_assign_in_execute)
		val lhs = last_assign_in_execute.lhs as ParameterizedPropertyAccessExpression_IM
		assertEquals ("Expecting property c1","c1",lhs.property_IM.name);
		assertEquals ("Expect name of local class","A",(lhs.target as IdentifierRef_IM).rewiredTarget.name);

		val rhs = last_assign_in_execute.rhs as NewExpression
		assertEquals( "Expected imported class-name","C1", (rhs.callee as IdentifierRef_IM).rewiredTarget.name )


	}

	@Test
	def _12_external_JS_module_wrap_resource() throws Throwable  {
		val script = '''
			var x = 5;
		'''
		val moduleWrapped = '''
			System.registerDynamic([], true, function(require, exports, module) {
				var x = 5;
			});
		'''.cjsPatched(false);

		val jsResource = script.installJSScript();
		val scriptNode = jsResource.contents.get(0) as Script;

		assertCompileResult(scriptNode, moduleWrapped)
	}

	@Test
	def _13_external_JSX_module_wrap_resource() throws Throwable  {
		val script = '''
			var x = 5;
		'''
		val moduleWrapped = '''
			System.registerDynamic([], true, function(require, exports, module) {
				var x = 5;
			});
		'''.cjsPatched(false);

		val jsResource = script.installJSXScript();
		val scriptNode = jsResource.contents.get(0) as Script;

		assertCompileResult(scriptNode, moduleWrapped)
	}

	/** Helper method transpiling and checking.  */
	def assertCompileResult(String script, String expectedTranspilerText) throws Throwable  {
		val scriptNode = script.parse()
		scriptNode.resolveLazyRefs

		assertCompileResult(scriptNode, expectedTranspilerText)
	}

}
