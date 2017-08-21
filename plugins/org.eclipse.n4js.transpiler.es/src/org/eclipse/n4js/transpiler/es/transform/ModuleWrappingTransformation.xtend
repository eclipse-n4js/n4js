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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import java.util.LinkedHashMap
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.AdditiveOperator
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.CommaExpression
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.PostfixExpression
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.ScriptElement
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.ThrowStatement
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.VariableStatementKeyword
import org.eclipse.n4js.n4jsx.transpiler.utils.JSXBackendHelper
import org.eclipse.n4js.naming.QualifiedNameComputer
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesAfter
import org.eclipse.n4js.transpiler.es.assistants.DestructuringAssistant
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants.ModuleSpecifierAdjustment
import org.eclipse.xtend.lib.annotations.Data

import static org.eclipse.n4js.n4JS.BinaryLogicalOperator.*
import static org.eclipse.n4js.n4JS.EqualityOperator.*
import static org.eclipse.n4js.n4JS.UnaryOperator.*

import static extension org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Module/Script wrapping transformation.
 */
@ExcludesAfter(/* if present, must come before: */ DestructuringTransformation)
class ModuleWrappingTransformation extends Transformation {
	@Inject
	JSXBackendHelper jsx;

	@Inject
	extension QualifiedNameComputer qnameComputer
	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private DestructuringAssistant destructuringAssistant;


	private final Set<SymbolTableEntry> exportedSTEs = newLinkedHashSet;

	override analyze() {
		//
	}

	override assertPreConditions() {
		assertTrue("every import declaration should have an imported module",
			state.im.eAllContents.filter(ImportDeclaration).forall[state.info.getImportedModule(it)!==null]);
	}

	override assertPostConditions() {
		assertTrue("wrapped module has a single statement.",
			state.im.scriptElements.size == 1);
	}

	override transform() {
		// necessary preparation:
		convertDestructBindingsIntoDestructAssignments();

	/* Target-trafo:
			System.register([%imp%], function($n4Export) {
				// %vars%
				// %var-assigns%
				// %init_B%
				    return {
				        setters: [ // %setters%
				        ],
				        execute: function() {
							// %exec_A%
				        }
				    };
				});
	*/

		val script_im = state.im;
		val List<ScriptElement> content_im = script_im.scriptElements;

		// The basic question here is, how much have preceding trafos already removed ?
		// - yes, there are imports
		// - are there annotated statements (wrapped in annotationslists ?)

		// Expected in scriptElements are
		// - VariableDeclarations
		//   --> hoisting of var-name into %vars%
		//   --> hoisting of constructors in initialisers into variable-assignments %var-assigns%
		//   --> makeclass-calls (except ctors removed) into %exec_A%
		// - import statements
		//   --> hoisting of "used name(namspace,alias or thing)" into %vars%
		//   --> module-clustered import-function into %setters%
		//   --> list of imported modules in %imp% (! in _same order_ as the cluster above !)
		// - export statements ( only variable-exports at this trafo-stage left.)
		//   -->  normal variable declaration trafo
		//   -->  call to n4Export into %init_B%

		// %init_B%
		content_im.replaceExportStatementsAndExctractCalls;

		// Map Module to imported element.
		val LinkedHashMap<String,ImportEntry> importSetterMap =  processImports(content_im);

		val List<Statement> activeX = newArrayList();

		// new Element system.
		val call_System_dot_register_Expr = _CallExpr => [
			target = _PropertyAccessExpr => [
				target =  _IdentRef(steFor_System)
				property_IM = steFor_register
			]; // "System.register"
			arguments += _Argument(_ArrLit => [
				// list of imported modules: the order of the elements must correspond to the order in the setters-property down.
				it.elements += importSetterMap.values.map[itx| _ArrayElement(_StringLiteral(itx.actualModuleSpecifier))=>[
					//tracing
					state.tracer.copyTrace(itx.tobeReplacedImportSpecifier,it)
				] ]
			]); // fpar0
			arguments += _Argument(_FunExpr(false) => [  // fpar1
				fpars += _Fpar => [name = steFor_$n4Export.name ] // "$n4Export"
				body = _Block => [
					val hoist = hoistedVariablesAndInits(content_im)
					if (hoist !== null) {
						statements += hoist.key;  // var-statement
						statements += hoist.value; // list of initialisers.
						// keep track of initilizers for further processing
						activeX += hoist.value;
					}
					/* exported things. */
					// %init_B%
					statements += _ReturnStmnt(
						_ObjLit(
							_PropertyNameValuePair("setters",
								_ArrLit(
									importSetterMap.values.map[ importFE(it) ].map[ it._ArrayElement ]
								)
							),
							_PropertyNameValuePair("execute",
								_FunExpr(false
									/* original content */
									// %exec_A%
									// content_im.filter(Statement)
								) => [
									val intoExecute = content_im.filter(Statement).toList
									activeX += intoExecute;
									it.body.statements += intoExecute
								]
							)
						)
					); // end return
				]
			]);

		]
		val exprStatementSystem = _ExprStmnt(call_System_dot_register_Expr)

		val exprStatement = doWrapInCJSpatch(exprStatementSystem);

		// rewrite wrapped content into script
		script_im.scriptElements.clear
		script_im.scriptElements += exprStatement;

//		val activeStatements = ((((((
//			exprStatement.expression
//			as ParameterizedCallExpression).arguments.get(1)
//			as FunctionExpression).body.statements.last
//		 	as ReturnStatement).expression
//		 	as ObjectLiteral).propertyAssignments.last
//		 	as PropertyNameValuePair).expression
//		 	as FunctionExpression).body.statements;
//		transFormExportExpressions( activeStatements )
		transFormExportExpressions(activeX);

	}

	/** FunctionExpression for import used inside of the setters-array*/
	private def FunctionExpression importFE(ImportEntry entry) {
		_FunExpr(false) => [
			fpars += _Fpar => [name = entry.fparName]
			body = _Block => [
				for (val iter = entry.variableSTE_actualName.iterator; iter.hasNext;) {
					val ImportAssignment current = iter.next;
					val refToFPar = _IdentRef(getSymbolTableEntryInternal(entry.fparName, true));
					val Expression rhs = if (current.isNameSpace) {
							refToFPar;
						} else {
							// NamedImportSpecifiers require property access.
							_PropertyAccessExpr => [
								property_IM = getSymbolTableEntryInternal(current.ste.exportedName, true) // ref to what we import.
								target = refToFPar;
							];
						};
					if (current.ste === null && JSXBackendHelper.isJsxBackendImportSpecifier(current.tobeReplacedIM)) {
						statements += _ExprStmnt(_IdentRef(steFor_React)._AssignmentExpr(rhs))
					} else {
						statements += _ExprStmnt(_IdentRef(current.ste)._AssignmentExpr(rhs)) => [
							state.tracer.copyTrace(current.tobeReplacedIM, it)
						];
					}
				}
			]
			// tracing
			state.tracer.copyTrace(entry.tobeReplacedImportSpecifier, it)
		]
	}

	/**
	 * Goes over imports from content_im and collect imported things in a meta-data-structure
	 * in map[module_name -> ImportEntry]
	 *
	 * This method builds up the data structure to handle imports but doesn't change the intermediate model.
	 *
	 * @param contents_im script-content of intermediate model
	 * @return map of completeModuleSpecifier to ImportEntries.
	 */
	private def LinkedHashMap<String,ImportEntry> processImports(List<ScriptElement> contents_im) {

		val LinkedHashMap<String,ImportEntry> map = newLinkedHashMap()

		for( val iter = contents_im.iterator; iter.hasNext;  ) {
			val elementIM = iter.next();
			if( elementIM instanceof ImportDeclaration ) {

				val module = state.info.getImportedModule(elementIM);

				// calculate names in output
				val completeModuleSpecifier =
					if (JSXBackendHelper.isJsxBackendModule(module)) {
						jsx.jsxBackendModuleSpecifier(module, state.resource)
					} else {
						module.completeModuleSpecifier
					}

				val fparName = if (JSXBackendHelper.isJsxBackendModule(module)) {
						jsx.getJsxBackendCompleteModuleSpecifierAsIdentifier(module)
					} else {
						"$_import_"+module.completeModuleSpecifierAsIdentifier
					}



				val moduleSpecifierAdjustment = getModuleSpecifierAdjustment(module);
				val actualModuleSpecifier = if(moduleSpecifierAdjustment!==null) {
					if(moduleSpecifierAdjustment.usePlainModuleSpecifier) {
						moduleSpecifierAdjustment.prefix + '/' + module.moduleSpecifier
					} else {
						moduleSpecifierAdjustment.prefix + '/' + completeModuleSpecifier
					}
				} else {
					completeModuleSpecifier
				};

				var moduleEntry = map.get( completeModuleSpecifier )
				if( moduleEntry === null ) {
					moduleEntry = new ImportEntry(completeModuleSpecifier, actualModuleSpecifier, fparName, newArrayList(), elementIM)
					map.put( completeModuleSpecifier, moduleEntry )
				}
				val finalModuleEntry = moduleEntry


				// local name : as used in Script
				// actual name : exported name.
				elementIM.importSpecifiers.forEach[
					switch(it) {
						NamespaceImportSpecifier: { // For NamespaceImports there is only one importSpecifier
							val nisSTE = findSymbolTableEntryForNamespaceImport(it);
							finalModuleEntry.variableSTE_actualName += new ImportAssignment( nisSTE , null , it, true  );
						}
						NamedImportSpecifier: {
							val ste = findSymbolTableEntryForNamedImport( it );
							if (ste !== null) {
								finalModuleEntry.variableSTE_actualName += new ImportAssignment( ste , it.alias, it , false ) ;
							}
						}
					}
				];

//				val firstSpec = elementIM.importSpecifiers.head
//				switch(firstSpec) {
//					NamespaceImportSpecifier: { // For NamespaceImports there is only one importSpecifier
//						val nisSTE = findSymbolTableEntryForNamespaceImport(firstSpec);
//						finalModuleEntry.variableSTE_actualName += new ImportAssignment( nisSTE , null , firstSpec, true  );
//					}
//					NamedImportSpecifier: {
//						elementIM.importSpecifiers.map[it as NamedImportSpecifier].forEach[
//
//							val ste = findSymbolTableEntryForNamedImport( it );
//							if (ste !== null) {
//								finalModuleEntry.variableSTE_actualName += new ImportAssignment( ste , it.alias, it , false ) ;
//							}
//						]
//					}
//				}
				// DO NOT delete since hoisting requires it as well:
				// - iter.remove() // delete import
			}
		}

		return map;
	}

	/* Data bag for import-rewriting. */
	@Data
	static class ImportEntry{
		// specifier of the module to import from
		String completeModuleSpecifier
		// this string will be used in the list of dependencies (i.e. 1st argument to the System.register() call)
		String actualModuleSpecifier
		// name of module-parameter passed into setter
		String fparName
		// Mappings of things to import. the actualName can be null, which means NamespaceImport
		List<ImportAssignment> variableSTE_actualName
		// for Tracing: IM-element which will be replaced:
		ImportDeclaration tobeReplacedImportSpecifier
	}
	@Data
	static class ImportAssignment {
		/* imported thing (symbol for exported thing from other file) */
		SymbolTableEntryOriginal ste;
		String actualName;
		ImportSpecifier tobeReplacedIM;
		boolean isNameSpace;
	}





	/**
	 * Removes "export" :
	 * Iterates over scriptContent_im and replaces each export-statement with it's containing statement.
	 * @param scriptContent_im current intermediate top-level elements
	 * @return exportMethodCalls - container collecting the newly created "$n4Export()" calls - detached from intermediate model.
	 */
	private def void replaceExportStatementsAndExctractCalls(List<ScriptElement> scriptContent_im) {
		for( var i=0; i< scriptContent_im.size; i++) {
			val orig = scriptContent_im.get(i);
			if( orig instanceof ExportDeclaration ) { // precondition assumes that there are only ExportedVariableStatement left.
				val exportedVarStmt = orig.exportedElement as ExportedVariableStatement;
				if( exportedVarStmt!==null ) {
					val bareVarStmt = removeExport(exportedVarStmt);
					bareVarStmt.varDecl.map[it.findSymbolTableEntryForElement(true)].forEach[ exportedSTEs.add(it) ];
				}
			}
		}
	}

	/**
	 * Precondition: The scriptContent only contains VariableDeclarations and ImportStatements.
	 *
	 * All hoisted variables will be extracted and returned in a new VariableStatement
	 *
	 * Removed things:
	 * - Import Declaration
	 * - Variable declarations without initializers (they are hoisted)
	 *
	 * Moved things:
	 * - initializer expressions will be extracted from declarations.
	 *
	 * @return the statement with all hoisted variable AND the list of initializers to be hoisted
	 */
	private def Pair<VariableStatement, List<ExpressionStatement>> hoistedVariablesAndInits( List<ScriptElement> scriptContent) {

		// Split up  all varDecls of a varStmt into declaration + init-expression.
		// all init-expressions will replace the scriptElement
		// all declarations will be wrapped in own stmt and hoisted.

		val Map<VariableStatement, List<ExpressionStatement>> mapVarStatement2replacer = newLinkedHashMap(); // order should be preserved

		// over each element
		val Iterable<VariableDeclaration> allHoisted = scriptContent.map[ scriptElement |
			val Iterable<VariableDeclaration> ret = switch( scriptElement ) {
				VariableStatement : {
					val pairsOfDeclarationAndInitialsers = scriptElement.varDecl.toHoistDeclaration;
					val listOfInitExpressionStmts = pairsOfDeclarationAndInitialsers.map[it.value].filterNull.toList;
					if( !listOfInitExpressionStmts.empty ) mapVarStatement2replacer.put( scriptElement, listOfInitExpressionStmts );
					pairsOfDeclarationAndInitialsers.map[it.key]
				}
				ImportDeclaration : scriptElement.toHoistDeclaration
				default : null
			};
			return ret;
		].filterNull.flatten

		val VariableStatement varStmtHoistedVariables = _VariableStatement => [
			varDeclsOrBindings += allHoisted
		]

		// remove all imports and
		// remove all vardecl without initializer:
		for( var i = scriptContent.size-1; i >= 0; i-- ) {
			var scriptElement = scriptContent.get(i)
			// imports:
			if( scriptElement instanceof ImportDeclaration ) {
				remove(scriptElement);
				// note: in some cases, previous line will lead to dangling references in symbol table entries (i.e.
				// some SymbolTableEntryOriginal will point to an 'importSpecifier' that is no longer contained in the
				// IM); so far, we allow this -> so we do not have to clean up anything here
			}
			else if( scriptElement instanceof VariableStatement ) {
				// remove only those with no replacement:
				if( ! mapVarStatement2replacer.containsKey(scriptElement) ) {
					// empty
					remove(scriptElement);
				}
			}
		}

		val List<ExpressionStatement> hoistedInitializer = newArrayList;

		// replace variable declaration with initializer by ExpressionStatement containing the initializer
		mapVarStatement2replacer.entrySet.forEach[
			replace( key, value );
			hoistedInitializer += value.filter[ state.info.isInitializerOfHoistedVariable(it) ];
		]

		if ( varStmtHoistedVariables.varDeclsOrBindings.empty ) return null;

		return varStmtHoistedVariables -> hoistedInitializer;
	}


	/** Decouples VarDeclarations and their initializer expressions. Returns them as a Pair. Value kann be {@null}
	 */
	private def Iterable<Pair<VariableDeclaration,ExpressionStatement>> toHoistDeclaration(List<VariableDeclaration> varDecl) {
		varDecl.map[
			entry | entry.hoistEntry
		]
	}

	/** Decouple Variabledeclaration and initialiser.
	 * If an initialiser is given it will be wrapped into a new ExpressionStatement.
	 */
	private def Pair<VariableDeclaration,ExpressionStatement> hoistEntry(VariableDeclaration vDeclIM) {

		// extract exression:
		val exprStmt = if( vDeclIM.expression !== null) {

			val initExpr = vDeclIM.expression;
			vDeclIM.expression = null;

			// create new call to this variable:
			val ste = findSymbolTableEntryForElement(vDeclIM , true );

			val stmt = _ExprStmnt( _IdentRef( ste )._AssignmentExpr( initExpr ) );
			if(state.info.isToHoist(vDeclIM)) {
				state.info.markAsInitializerOfHoistedVariable(stmt);
			}

			// tracing:
			state.tracer.copyTrace(vDeclIM,stmt);

			//
			stmt;
		};

		return vDeclIM -> exprStmt;
	}

	/** Creates a list of VariableDeclarations without initialisers. Used in hoisting. */
	private def Iterable<VariableDeclaration> toHoistDeclaration(ImportDeclaration importDecl){
		return importDecl.importSpecifiers.map[
			switch(it) {
				NamespaceImportSpecifier:
					namespaceToHoistDeclaration(it)
				NamedImportSpecifier:
					namedImportsToHoistDeclaration(it)
			}
		];
	}

	/** Creates a single VariableDeclaration without initialiser. */
	private def VariableDeclaration namedImportsToHoistDeclaration(NamedImportSpecifier nis) {
		_VariableDeclaration( findSymbolTableEntryForNamedImport(nis).name )=>[
			// tracing:
			state.tracer.copyTrace(nis,it);
		]
	}

	/** Creates a single VariableDeclaration without initialiser. */
	private def VariableDeclaration namespaceToHoistDeclaration(NamespaceImportSpecifier nsImport){
		return _VariableDeclaration( nsImport.alias )=>[state.tracer.copyTrace(nsImport,it)];
	}

	def private transFormExportExpressions(List<Statement> list) {
		val toProcess = #[]+list;
		// go into statements
		toProcess.forEach[ collectNodes(it,Expression,true ).forEach[ it.inferExportCall ] ]

	}

	def private dispatch void inferExportCall(PostfixExpression expr) {

		val subExpr = expr.expression;
		if( subExpr instanceof IdentifierRef_IM ) {
			val ste = subExpr.rewiredTarget
			if( ste.isExported ) {
				val container = expr.eContainer;
				if( container.isAppendableStatement ) {
					// case 1 : contained in simple statement, then we can issue as 2nd statement just after.
					insertAfter(container, _ExprStmnt( _N4ExportExpr(ste,steFor_$n4Export)));
				} else {
					// case 2: contained in other expression, must in-line the export call.
					switch (expr.op) {
						case INC: {
							postfixReplacement(expr, ste, AdditiveOperator.SUB)
						}
						case DEC: {
							postfixReplacement(expr, ste, AdditiveOperator.ADD)
						}
					};
				}
			}
		}
	}

	private final def void postfixReplacement(PostfixExpression expr, SymbolTableEntry ste, AdditiveOperator op) {
		// EXPR --> "( $n4Export( "ste", (DUMMY, ste )), ste +/– 1)"
		// with init: DUMMY-->EXPR   resulting in "( $n4Export( "ste", (EXPR, ste )), ste -/+ 1)"
		val replaceExp = _Parenthesis(
			_CommaExpression( _N4ExportExpr( ste,
				_Parenthesis(
					_CommaExpression(/*expr*/_ObjLit /* Dummy-Expression will be overriden */, __NSSafe_IdentRef( ste )/* evaluate after postfix*/)
				)  ,steFor_$n4Export
			),
				_AdditiveExpression( __NSSafe_IdentRef(ste) , op, _IntLiteral(1)  )
		));
		val initFunction = [ PostfixExpression pe |
							((((replaceExp.expression
								as CommaExpression).exprs.get(0)
								as ParameterizedCallExpression).arguments.get(1).expression
								as ParenExpression).expression
								as CommaExpression)
						=> [it.exprs.set(0,pe)]; return;	];
		wrapExistingExpression( expr, replaceExp, initFunction);
	}



	def private dispatch void inferExportCall(UnaryExpression expr) {

		val subExpr = expr.expression;
		if( subExpr instanceof IdentifierRef_IM ) {
			val ste = subExpr.rewiredTarget
			if( ste.isExported ) {
				val container = expr.eContainer;
				if( container.isAppendableStatement ) {
					// case 1 : contained in simple statement, then we can issue es 2nd statement just after.
					insertAfter(container, _ExprStmnt(_N4ExportExpr(ste,steFor_$n4Export)));
				} else {
					exprReplacement(expr, ste);
				}
			}
		}
	}


	def private dispatch void inferExportCall(AssignmentExpression expr) {

		val lhs = expr.lhs;
		val isTopLevel_but_not_return = expr.eContainer.isAppendableStatement;
		switch (lhs) {
			IdentifierRef_IM: {
				val ste = lhs.rewiredTarget;
				if( ste.isExported ) {
					if( isTopLevel_but_not_return ) { insertAfter(expr.eContainer, _ExprStmnt(_N4ExportExpr(ste,steFor_$n4Export))); }
					else { // non toplevel, have to inject
						exprReplacement(expr, ste);
					}
				}
			}
			default:{
				// ntd.
			}
		}
	}

	def private static boolean isAppendableStatement(EObject container) {
		return container instanceof Statement
				&& !(container instanceof ReturnStatement )
				&& !(container instanceof ThrowStatement);
	}
	/*
	 * Reusing an expression {@code expr1} of IM wrapped into a comma-expression including call to $n4Export:
	 * <pre>
	 * ....expr1/setting valueof x/.... -->  ....($n4Export("x",expr1),x)....
	 * </pre>
	 *
	 * Only applicable if {@code expr1} evaluates (while actually setting) to the new value of x.
	 * This is true to Assignment- and Unary-Expressions <b>but not</b> for PostfixExprssion
	 *
	 * <p>
	 * Generates a ParenExpression and a callback-function
	 *
	 * After inserting the ParenExpression into the old place of expr1,
	 * executing the callback with parameter expr1 will insert expr1 as a child at the correct place.
	 *
	 * @param ste symboltableentry for {@code x}
	 *
	 */
	private final def void exprReplacement(Expression expr, SymbolTableEntry ste) {
		// The new code snippet, the ObjectLiteral will be replaced by init-function
		val replaceExp =
			_Parenthesis(
				_CommaExpression(
					_N4ExportExpr( ste, /*orig assign-expression */ _ObjLit /*Dummy will be replaced */, steFor_$n4Export )
					, __NSSafe_IdentRef( ste )
				)
			);
		// 	The callback-function
		val initFunc =
			[ Expression ae |
				((replaceExp.expression
					as CommaExpression).exprs.get(0)
					as ParameterizedCallExpression)
					=> [ it.arguments.set(1, _Argument(ae)) ];
				return;
			];
		wrapExistingExpression( expr, replaceExp, initFunc );
	}



	def private dispatch void inferExportCall(Expression expression) {
		// general Expressions are untouched.
	}

	def private boolean isExported(SymbolTableEntry ste) {
		return exportedSTEs.contains(ste);
	}

	def private ModuleSpecifierAdjustment getModuleSpecifierAdjustment(TModule module) {
		val resourceURI = module?.eResource?.URI;
		if(resourceURI!==null) {
			val project = n4jsCore.findProject(resourceURI);
			if(project.present) {
				val loader = project.get.getModuleLoader();
				if(loader!==null) {
					val adjustment = N4JSLanguageConstants.MODULE_LOADER_PREFIXES.get(loader);
					if(adjustment!==null) {
						return adjustment;
					}
				}
			}
		}
		return null; // no adjustment
	}

	/** SystemJS-wrapping of external JS-code which is not transpiled by ourselves.
	 * Note: the code which will be wrapped usually is a non-stand-alone JS-snippet already referring to the parameters {@code require},
	 * {@code exports} and {@code module} which are hereby introduced as formal parameters.
	 */
	def public static CharSequence wrapPlainJSCode(CharSequence cs) {
		'''
		(function(System) {
			System.registerDynamic([], true, function(require, exports, module) {
				«cs»
			});
		})(typeof module !== 'undefined' && module.exports ? require('n4js-node/index').System(require, module) : System);
		'''
	}

	/** patch the statement with commonJS-support */
	def private Statement doWrapInCJSpatch(ExpressionStatement statement) {

		// (function(System) {
		//     < ... statement ...>
		// })(typeof module !== 'undefined' && module.exports ? require('n4js-node/index').System(module) : global.System);

		val ret = _ExprStmnt( _CallExpr (
			_Parenthesis(
				_FunExpr(false, null, #[ _Fpar( steFor_System.name ) ],
					_ExprStmnt(_StringLiteral("use strict")), // enable strict mode
					statement
				)
			),
			_ConditionalExpr(
				_BinaryLogicalExpr(
					_EqualityExpr( TYPEOF._UnaryExpr(_IdentRef(steFor_module) ),  NSAME , _StringLiteralForSTE(steFor_undefined)),
					AND,
					steFor_module._PropertyAccessExpr( steFor_exports )
				),
					/*     TRUE-case 		*/
			    _IdentRef(steFor_require)._CallExpr( _StringLiteral('n4js-node/index') ).
			    _PropertyAccessExpr( steFor_System )._CallExpr( _IdentRef(steFor_require), _IdentRef(steFor_module) ),
			    	/*     FALSE-case 		*/
				_IdentRef( steFor_System )
			) // Conditional
		));
		return ret;
	}


	def private void convertDestructBindingsIntoDestructAssignments() {
		collectNodes(state.im, VariableStatement, true).forEach[
			convertDestructBindingsIntoDestructAssignments(it)
		];
	}

	// FIXME API doc
	def private void convertDestructBindingsIntoDestructAssignments(VariableStatement varStmnt) {
		val bindings = varStmnt.varDeclsOrBindings.filter(VariableBinding).toList;
		// (1) for each destructuring binding in varStmnt, add an equivalent destructuring assignment after varStmnt
		val assignmentStmnts = bindings.map[binding|
			val patternConverted = destructuringAssistant.convertBindingPatternToArrayOrObjectLiteral(binding.pattern);
			val assignmentExpr = _AssignmentExpr(patternConverted, binding.expression);
			if(patternConverted instanceof ObjectLiteral) {
				_Parenthesis(assignmentExpr) // object destructuring must be wrapped into a ParenExpression
			} else {
				assignmentExpr
			}
		].map[_ExprStmnt];
		insertAfter(varStmnt, assignmentStmnts);
		// (2) below varStmnt, replace each destructuring binding by its contained variable declarations
		for(binding : bindings) {
			replace(binding, binding.variableDeclarations);
		}
		// (3) if we do this for 'const', we have to change it to 'let', because we removed the initializer expression
		if (varStmnt.varStmtKeyword===VariableStatementKeyword.CONST) {
			varStmnt.varStmtKeyword = VariableStatementKeyword.LET;
		}
	}
}
