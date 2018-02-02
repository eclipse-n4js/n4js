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
import org.eclipse.n4js.ModuleSpecifierAdjustment
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.AdditiveOperator
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.CommaExpression
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ImportDeclaration
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
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4idl.transpiler.utils.N4IDLTranspilerUtils
import org.eclipse.n4js.n4idl.versioning.VersionUtils
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesAfter
import org.eclipse.n4js.transpiler.es.assistants.DestructuringAssistant
import org.eclipse.n4js.transpiler.es.transform.internal.ImportAssignment
import org.eclipse.n4js.transpiler.es.transform.internal.ImportEntry
import org.eclipse.n4js.transpiler.es.transform.internal.NamedImportAssignment
import org.eclipse.n4js.transpiler.es.transform.internal.NamespaceImportAssignment
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.ResourceNameComputer

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
	private ResourceNameComputer resourceNameComputer
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

		val List<Statement> activeStatements = newArrayList();

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
					state.tracer.copyTrace(itx.toBeReplacedImportDeclaration,it)
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
						activeStatements += hoist.value;
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
									activeStatements += intoExecute;
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
		transFormExportExpressions(activeStatements);

	}

	/** FunctionExpression for import used inside of the setters-array*/
	private def FunctionExpression importFE(ImportEntry entry) {
		_FunExpr(false) => [
			fpars += _Fpar => [name = entry.fparName]
			body = _Block => [
				for (val iter = entry.variableSTE_actualName.iterator; iter.hasNext;) {
					val ImportAssignment current = iter.next;
					val refToFPar = _IdentRef(getSymbolTableEntryInternal(entry.fparName, true));
					val Expression rhs = if (current instanceof NamespaceImportAssignment) {
							refToFPar
						} else if(current instanceof NamedImportAssignment) {
							// NamedImportSpecifiers require property access.
							_PropertyAccessExpr => [
								property_IM =  getEntryForNamedImportedElement(current.ste) // ref to what we import.
								target = refToFPar;
							]
						} else {
							throw new IllegalStateException("unsupported subclass of ImportAssignment: " + current.class.simpleName)
						};
					statements += _ExprStmnt(_AssignmentExpr(_IdentRef(current.ste), rhs)) => [
						state.tracer.copyTrace(current.toBeReplacedImportSpecifier, it)
					];
				}
			]
			// tracing
			state.tracer.copyTrace(entry.toBeReplacedImportDeclaration, it)
		]
	}


	/**
	 * Returns the STE to use to import a named element from another module.
	 */
	private def SymbolTableEntry getEntryForNamedImportedElement(SymbolTableEntryOriginal importedElementEntry) {
		val IdentifiableElement originalTarget = importedElementEntry.getOriginalTarget();

		// if applicable use versioned internal name for internal STE
		if (VersionUtils.isTVersionable(originalTarget)) {
			return getSymbolTableEntryInternal(N4IDLTranspilerUtils.getVersionedInternalName(originalTarget), true);
		}
		return getSymbolTableEntryInternal(importedElementEntry.exportedName, true);
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

		var depNumber = 0
		for( val iter = contents_im.iterator; iter.hasNext;  ) {
			val elementIM = iter.next();
			if( elementIM instanceof ImportDeclaration ) {
				

				val module = state.info.getImportedModule(elementIM);

				var actualModuleSpecifier = computeActualModuleSpecifier(module)
				// name for parameter the setter function, it doesn't matter much
				// we use dollar, as it is invalid N4JS identifier - we avoid name collisions with user code
				// we use counter just as a hint for someone reading compiled code
				// (`$_dep_3` will be parameter of the 3rd setter function that corresponds to the 3rd module required by the SystemJS)
				if(!map.keySet.contains(actualModuleSpecifier))
					depNumber+=1
				val fparName = "$_dep_"+depNumber;
				
				var moduleEntry = map.get( actualModuleSpecifier )
				if( moduleEntry === null ) {
					moduleEntry = new ImportEntry(actualModuleSpecifier, fparName, newArrayList(), elementIM)
					map.put( actualModuleSpecifier, moduleEntry )
				}
				val finalModuleEntry = moduleEntry

				// local name : as used in Script
				// actual name : exported name.
				elementIM.importSpecifiers.forEach[
					switch(it) {
						NamespaceImportSpecifier: { // For NamespaceImports there is only one importSpecifier
							val nisSTE = findSymbolTableEntryForNamespaceImport(it);
							finalModuleEntry.variableSTE_actualName += new NamespaceImportAssignment(nisSTE, it);
						}
						NamedImportSpecifier: {
							val ste = findSymbolTableEntryForNamedImport( it );
							if (ste !== null) {
								finalModuleEntry.variableSTE_actualName += new NamedImportAssignment(ste, it.alias, it);
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

	private def String computeActualModuleSpecifier(TModule module) {
		val completeModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(module)
		val moduleSpecifierAdjustment = getModuleSpecifierAdjustment(module);

		if (moduleSpecifierAdjustment !== null && moduleSpecifierAdjustment.usePlainModuleSpecifier)
			return moduleSpecifierAdjustment.prefix + '/' + module.moduleSpecifier

		var specifier = completeModuleSpecifier
		val depProject = n4jsCore.findProject(module.eResource.URI).orNull
		if (depProject !== null) {
			val projectRelativeSegment = depProject.outputPath
			val depLocation = depProject.locationPath
			if (depLocation !== null) {
				val depLocationString = depLocation.toString
				val depProjecOutputPath = depProject.locationPath.resolve(projectRelativeSegment).normalize.toString
				val depRelativeSpecifier = depProjecOutputPath.substring(depLocationString.length -
					depProject.projectId.length)
				specifier = depRelativeSpecifier + '/' + completeModuleSpecifier
			}
		}
		if (moduleSpecifierAdjustment !== null)
			return moduleSpecifierAdjustment.prefix + '/' + specifier

		return specifier
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
					val pairsOfDeclarationAndInitialsers = scriptElement.varDeclsOrBindings.toHoistDeclarations;
					val listOfInitExpressionStmts = pairsOfDeclarationAndInitialsers.map[it.value].filterNull.toList;
					if( !listOfInitExpressionStmts.empty ) mapVarStatement2replacer.put( scriptElement, listOfInitExpressionStmts );
					pairsOfDeclarationAndInitialsers.map[it.key].flatten
				}
				ImportDeclaration : scriptElement.toHoistDeclaration
				default : null
			};
			return ret;
		].filterNull.flatten.toList

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


	/** Decouples VarDeclarations and their initializer expressions. Returns them as a Pair. Value can be {@code null}
	 */
	private def Iterable<Pair<List<VariableDeclaration>,ExpressionStatement>> toHoistDeclarations(List<VariableDeclarationOrBinding> varDeclsOrBindings) {
		return varDeclsOrBindings.map[entry|
			switch(entry) {
				VariableDeclaration:
					entry.hoistEntry
				VariableBinding:
					entry.hoistEntry
				default:
					throw new IllegalStateException("unknown subclass of " + VariableDeclarationOrBinding.simpleName
						+ ": " + entry.class.simpleName)
			}
		];
	}

	/** Decouple VariableDeclaration and initializer.
	 * If an initializer is given it will be wrapped into a new ExpressionStatement.
	 */
	private def Pair<List<VariableDeclaration>, ExpressionStatement> hoistEntry(VariableDeclaration vDeclIM) {

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

		return #[vDeclIM] -> exprStmt;
	}

	private def Pair<List<VariableDeclaration>, ExpressionStatement> hoistEntry(VariableBinding binding) {
		return binding.variableDeclarations -> binding.convertDestructBindingToDestructAssignment;
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
					insertAfter(container, _ExprStmnt(createExportExpression(ste)));
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
				if( container.isAppendableStatement) {
					// case 1 : contained in simple statement, then we can issue es 2nd statement just after.
					insertAfter(container, _ExprStmnt(createExportExpression(ste)));
				} else {
					switch (expr.op) {
						case INC: exprReplacement(expr, ste)
						case DEC: exprReplacement(expr, ste)
						default: {/*other unary operators do not modify original value, hence no replacement*/}
					}
				}
			}
		}
	}


	def private dispatch void inferExportCall(AssignmentExpression expr) {
		val lhs = expr.lhs;
		switch (lhs) {
			IdentifierRef_IM: {
				val ste = lhs.rewiredTarget;
				if( ste.isExported ) {
					val container = expr.eContainer
					if( container.isAppendableStatement ) {
						insertAfter(container, _ExprStmnt(createExportExpression(ste)));
					}
					else { // non toplevel, have to inject
						exprReplacement(expr, ste);
					}
				}
			}
			default:{ /*ntd. */ }
		}
	}

	/** The EObject is NOT a {@link ReturnStatement} nor {@link ThrowStatement} */
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
	 * Note: only <pre>++x</pre> and <pre>--x</pre> are setting the value of <pre>x</pre>.
	 * other Unary-Expressions are not setting the value. Caller needs to take care of this, as
	 * expression operator is not checked in this method.
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

	/** returns adjustments to be used based on the module loader specified for the provided module. May be null. */
	def private ModuleSpecifierAdjustment getModuleSpecifierAdjustment(TModule module) {
		val resourceURI = module?.eResource?.URI;
		if (resourceURI === null) return null;
		val project = n4jsCore.findProject(resourceURI);
		if (!project.present) return null;
		val loader = project.get.getModuleLoader();
		if (loader === null) return null;
		val adjustment = N4JSLanguageConstants.MODULE_LOADER_PREFIXES.get(loader);
		return adjustment;
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
		})(typeof module !== 'undefined' && module.exports ? require('n4js-node').System(require, module) : System);
		'''
	}

	/** patch the statement with commonJS-support */
	def private Statement doWrapInCJSpatch(ExpressionStatement statement) {

		// (function(System) {
		//     < ... statement ...>
		// })(typeof module !== 'undefined' && module.exports ? require('n4js-node').System(module) : global.System);

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
			    _IdentRef(steFor_require)._CallExpr( _StringLiteral('n4js-node') ).
			    _PropertyAccessExpr( steFor_System )._CallExpr( _IdentRef(steFor_require), _IdentRef(steFor_module) ),
			    	/*     FALSE-case 		*/
				_IdentRef( steFor_System )
			) // Conditional
		));
		return ret;
	}


	/**
	 * Given a destructuring binding, this method returns a destructuring assignment that performs the equivalent
	 * destructuring operation.
	 * <p>
	 * For example, given an array destructuring binding (i.e. the code between the 'let' and the '=') as in
	 * <pre>
	 * let [a,b] = [1,2];
	 * </pre>
	 * this method will return the following assignment expression
	 * <pre>
	 * [a,b] = [1,2]
	 * </pre>
	 * and given an object destructuring binding (again, the code between the 'let' and the '=') as in
	 * <pre>
	 * let {prop1: x, prop2: y} = {prop1: 1, prop2: 2};
	 * </pre>
	 * this method will return the following assignment expression
	 * <pre>
	 * ({prop1: x, prop2: y} = {prop1: 1, prop2: 2})
	 * </pre>
	 * (wrapped in a parenthesis expression).
	 */
	def private ExpressionStatement convertDestructBindingToDestructAssignment(VariableBinding binding) {
		val patternConverted = destructuringAssistant.convertBindingPatternToArrayOrObjectLiteral(binding.pattern);
		val assignmentExpr = _AssignmentExpr(patternConverted, binding.expression);
		val assignmentStmnt = _ExprStmnt(
			if(patternConverted instanceof ObjectLiteral) {
				_Parenthesis(assignmentExpr) // object destructuring must be wrapped into a ParenExpression
			} else {
				assignmentExpr
			}
		);
		// update hoisting info in information registry
		if(binding.variableDeclarations.exists[state.info.isToHoist(it)]) {
			binding.variableDeclarations.forEach[state.info.markAsToHoist(it)]; // if one is hoisted, all need to be hoisted
			state.info.markAsInitializerOfHoistedVariable(assignmentStmnt);
		}
		// tracing
		state.tracer.copyTrace(binding, assignmentStmnt);
		return assignmentStmnt;
	}

	/**
	 * Creates an export expression by the name of the given symbol table entry which exports a simple (identifier) reference
	 * to the symbol table entry.
	 *
	 */
	def protected ParameterizedCallExpression createExportExpression(SymbolTableEntry entry) {
		// otherwise create a default export expression
		return _N4ExportExpr(entry, _IdentRef(entry), steFor_$n4Export);
	}
}
