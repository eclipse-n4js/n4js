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

import java.util.ArrayList
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.DestructNode
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.CatchBlock
import org.eclipse.n4js.n4JS.EqualityOperator
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyAssignment
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding
import org.eclipse.n4js.n4JS.VariableEnvironmentElement
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.VariableStatementKeyword
import org.eclipse.n4js.n4JS.WithStatement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesAfter
import org.eclipse.n4js.transpiler.TransformationDependency.Optional
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.xtext.EcoreUtil2

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.n4JS.DestructureUtils.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Transforms ES6 destructuring patterns into equivalent ES5 code. If the target engine supports ES6 destructuring
 * patterns, this transformation can simply be deactivated.
 * <p>
 * For details on destructuring patterns, see documentation of class {@link DestructNode}.
 */
@Optional(Destructuring)
@ExcludesAfter(StaticPolyfillTransformation) // otherwise destructuring patterns from filling module won't be processed!
class DestructuringTransformation extends Transformation {

	/** Counts destructuring patterns per variable environment. Used to avoid name clashes of helper variables. */
	private final Map<VariableEnvironmentElement,Integer> destructsPerScope = newHashMap;


	override assertPreConditions() {
		// true
		// (however, this transformation should run as early as possible)
	}
	override assertPostConditions() {
		// true
	}

	override analyze() {
		// ignore
	}

	override transform() {

		// compute nodes we have to transform before changing anything
		val destructBindings = collectNodes(state.im, VariableStatement, true)
			.filter[containsDestructuringPattern].toList;
		val destructAssignments = collectNodes(state.im, AssignmentExpression, true)
			.filter[isTopOfDestructuringAssignment].filter[isRoot].toList;
		val destructForStmnts = collectNodes(state.im, ForStatement, true)
			.filter[containsDestructuringPattern || isTopOfDestructuringForStatement].toList;

		// now perform the changes
		destructBindings.forEach[transformDestructuringBindings];
		destructAssignments.forEach[transformDestructuringAssignment];
		destructForStmnts.forEach[transformForStatementWithDestructuring];
	}

	/**
	 * Transforms not a single destructuring binding but all destructuring bindings in the given variable statement.
	 */
	def private void transformDestructuringBindings(VariableStatement stmnt) {
		val newVarDecls = computeVariableDeclarations(stmnt.varDeclsOrBindings);
		stmnt.varDeclsOrBindings.clear();
		stmnt.varDeclsOrBindings += newVarDecls;
	}

	/**
	 * Transforms (a single) destructuring assignment.
	 */
	def public void transformDestructuringAssignment(AssignmentExpression expr) {
		// We pass the value of the expression to the function call. GHOLD-407
		var String fparName;
		var FunctionExpression helperFun;

		fparName = "$destruct" + "Param0";
		val fpar = _FormalParameter(fparName);
		helperFun = _FunExpr(false, null, fpar);

		val helperFunContents = helperFun.body.statements;
		val rootNode = DestructNode.unify(expr);
		val helperVars = <VariableDeclaration>newArrayList;
		val simpleAssignments = <Pair<SymbolTableEntry,? extends Expression>>newArrayList;
		traverse(helperVars, simpleAssignments, rootNode, expr.rhs, fparName);

		helperFunContents += helperVars.map[_VariableStatement(it)];
		helperFunContents += simpleAssignments.map[
			_ExprStmnt(_AssignmentExpr(
				__NSSafe_IdentRef(it.key),
				it.value
			))
		];

		// the following return statement is required to make sure entire expression evaluates to the rhs of
		// the assignment expression 'expr' (without evaluating rhs again!)
		val firstHelperVarSTE = findSymbolTableEntryForElement(helperVars.get(0), false);
		helperFunContents += _ReturnStmnt(_IdentRef(firstHelperVarSTE));
		
		val callExpr = _CallExpr(_Parenthesis(helperFun), expr.rhs) // parentheses required because the expression might appear as a statement (to disambiguate from function declaration)
		replace(expr, callExpr);
	}

	def private void transformForStatementWithDestructuring(ForStatement stmnt) {

		if(stmnt.forPlain) {

			if(!stmnt.varDeclsOrBindings.empty) {
				// something like: for( var [a,b]=[1,2] ;;) {}

				// note: pretty much the same case as method #transformDestructuringBindings() above
				val newVarDecls = computeVariableDeclarations(stmnt.varDeclsOrBindings);
				stmnt.varDeclsOrBindings.clear();
				stmnt.varDeclsOrBindings += newVarDecls;

			} else {
				// something like: for( [a,b]=[1,2] ;;) {}

				// here, stmnt.initExpr is an ordinary destructuring assignment that was already handled by method
				// #transformDestructuringAssignment(AssignmentExpression) above -> nothing to do here!
			}

		} else {
			// now: for..in OR for..of

			val depth = stmnt.nestingDepth;

			val iterVar = _VariableDeclaration("$destructStep$"+depth);
			val iterVarSTE = createSymbolTableEntryIMOnly(iterVar);

			var needDeclarations = false;
			var varStmtKeyword = VariableStatementKeyword.VAR;
			val helperVars = <VariableDeclaration>newArrayList;
			val simpleAssignments = <Pair<SymbolTableEntry,? extends Expression>>newArrayList;
			if(!stmnt.varDeclsOrBindings.empty) {
				// something like: for( var [a,b] of [ [1,2], [3,4] ] ) {}

				assertTrue("there should be exactly one VariableBinding in stmnt.varDeclsOrBindings",
					stmnt.varDeclsOrBindings.size===1 && stmnt.varDeclsOrBindings.get(0) instanceof VariableBinding);

				val rootNode = DestructNode.unify(stmnt.varDeclsOrBindings.head as VariableBinding);
				traverse(helperVars, simpleAssignments, rootNode, _IdentRef(iterVarSTE), null);  // fparname = null since we do not generate any function.
				needDeclarations = true;
				varStmtKeyword = stmnt.varStmtKeyword;

			} else if(stmnt.initExpr instanceof ArrayLiteral || stmnt.initExpr instanceof ObjectLiteral) {
				// something like: for( [a,b] of [ [1,2], [3,4] ] ) {}

				val rootNode = DestructNode.unify(stmnt);
				traverse(helperVars, simpleAssignments, rootNode, _IdentRef(iterVarSTE), null); // fparname = null since we do not generate any function.
				needDeclarations = false;

			} else {
				throw new IllegalArgumentException();
			}

			if(!(stmnt.statement instanceof Block)) {
				stmnt.statement = _Block(stmnt.statement);
			}
			val body = stmnt.statement as Block;

			val toBeInserted = <Statement>newArrayList;
			if(needDeclarations) {
				toBeInserted += _VariableStatement(varStmtKeyword, simpleAssignments.map[
					val varDecl = key.getVariableDeclarationFromSTE;
					varDecl.expression = value;
					return varDecl;
				]);
			} else {
				toBeInserted += _VariableStatement(helperVars);
				toBeInserted += simpleAssignments.map[
					_ExprStmnt(_AssignmentExpr(
						__NSSafe_IdentRef(it.key),
						it.value
					))
				];
			}

			body.statements.addAll(0, toBeInserted);

			stmnt.initExpr = null; // initExpr has been move to beginning of body (if any)
			stmnt.varDeclsOrBindings.clear(); // variable declarations have been moved to beginning of body (if any)
			stmnt.varDeclsOrBindings += iterVar; // only declared variable in the for statement is the iteration variable
		}
	}

	/**
	 * Breaks down all VariableBindings in the given list into simple variable declarations and returns a list containing
	 * the variable declarations that were contained in the given list from the beginning plus those created when
	 * breaking down the variable bindings. The order of the elements in the given list is preserved!
	 */
	def private List<VariableDeclaration> computeVariableDeclarations(List<VariableDeclarationOrBinding> varDeclsOrBindings) {
		val result = newArrayList;
		for(VariableDeclarationOrBinding vdeclOrBinding : new ArrayList(varDeclsOrBindings)) {
			if(vdeclOrBinding instanceof VariableDeclaration) {
				result += vdeclOrBinding;
			} else if(vdeclOrBinding instanceof VariableBinding) {
				val rootNode = DestructNode.unify(vdeclOrBinding);
				val helperVars = <VariableDeclaration>newArrayList;
				val simpleAssignments = <Pair<SymbolTableEntry,? extends Expression>>newArrayList;
				traverse(helperVars, simpleAssignments, rootNode, vdeclOrBinding.expression, null); // fparname = null since we do not generate any function.
				result += simpleAssignments.map[
					var varDecl = key.getVariableDeclarationFromSTE;
					varDecl.expression = value;
					return varDecl;
				];
			}
		}
		return result;
	}
	def private void traverse(List<VariableDeclaration> helperVars, List<Pair<SymbolTableEntry,? extends Expression>> simpleAssignments,
			DestructNode rootNode, Expression value, String fparName) {
		val scope = N4JSASTUtils.getScope(rootNode.astElement, false) ?: state.im;
		val n = destructsPerScope.merge(scope, 1, [i,j|i+j]) - 1;
		traverse(helperVars, simpleAssignments, rootNode.nestedNodes, value, fparName, Integer.toString(n));
	}
	/**
	 * Breaks down the destructuring pattern, represented by the given {@link DestructNode}s, into a number of simple
	 * assignments (without destructuring) that will be added to argument 'simpleAssignments'. The order of those simple
	 * assignments matters. Nested patterns as returned by {@link DestructNode#getNestedNodes()} are also broken down.
	 * fparName, if not null, is the parameter name of the enclosing function.
	 */
	def private void traverse(List<VariableDeclaration> helperVars, List<Pair<SymbolTableEntry,? extends Expression>> simpleAssignments,
			DestructNode[] nodes, Expression value, String fparName, String helperVarSuffix) {
		val len = nodes.length;
		val isPositionalPattern = nodes.exists[positional];
		val isRest = isPositionalPattern && !nodes.empty && nodes.last.rest;

		// STEP 1: create code to prepare the value to be destructured and to assign it to a helper variable

		// creating a new helper variable
		val currHelperVarName = "$destruct"+helperVarSuffix;
		val currHelperVarDecl = _VariableDeclaration(currHelperVarName);
		helperVars += currHelperVarDecl;
		val SymbolTableEntry currHelperVarSTE = findSymbolTableEntryForElement(currHelperVarDecl, true);
		assertTrue("", currHelperVarSTE.getVariableDeclarationFromSTE === currHelperVarDecl);
		assertTrue("", currHelperVarSTE.elementsOfThisName.contains(currHelperVarDecl));

		var $sliceToArrayForDestructSTE = steFor_$sliceToArrayForDestruct;

		if(isRest) {
			//result.add(currHelperVar+" = function(arr){return Array.isArray(arr) ? arr : Array.from(arr);}("+value+")");
			simpleAssignments += currHelperVarSTE -> _CallExpr(
				_Snippet("function(arr){return Array.isArray(arr) ? arr : Array.from(arr);}"),
				value
			);
		} else {
			val passValue = if (fparName.isNullOrEmpty) {
				value
			} else {
				// If the fparName is not empty, the generated function for destructuring has a parameter and we should use that parameter instead. GHOLD-407
				val fparSTE = getSymbolTableEntryInternal(fparName, true);
				_IdentRef(fparSTE)
			}
			if(isPositionalPattern) {
				//result.add(currHelperVar+" = $sliceToArrayForDestruct(("+value+"), "+len+")");
				simpleAssignments += currHelperVarSTE -> _CallExpr(
					_IdentRef($sliceToArrayForDestructSTE),
					_Parenthesis(passValue),
					_NumericLiteral(len)
				);
			} else {
				//result.add(currHelperVar+" = ("+value+")");
				simpleAssignments += currHelperVarSTE -> _Parenthesis(
					passValue
				);
			}
		}

		// STEP 2: create code to perform the actual destructuring

		val sliceSTE = getSymbolTableEntryForMember(state.G.arrayType, "slice", false, false, true);

		var nestedPatternsCounter = 0;
		for(var i=0;i<len;i++) {
			val currNode = nodes.get(i);

			// get the current element or property out of 'value' (i.e. the one that corresponds to 'currNode')
			val currValueRaw = if(isRest && i===len-1) {
				// currHelperVar.slice(i)
				_CallExpr(_PropertyAccessExpr(currHelperVarSTE, sliceSTE), _NumericLiteral(i))
			} else if(isPositionalPattern) {
				// currHelperVar[i]
				_IndexAccessExpr(currHelperVarSTE, _NumericLiteral(i));
			} else {
				// currHelperVar['propName']
				_IndexAccessExpr(currHelperVarSTE, _StringLiteral(currNode.propName));
			};

			val currValue = if(currNode.defaultExpr!==null) {
				//currValueRaw+" === undefined ? ("+transformAST.doTransform(currNode.defaultExpr)+") : "+currValueRaw
				_ConditionalExpr(
					_EqualityExpr(
						copy(currValueRaw), // must copy because used twice in this conditional expression!
						EqualityOperator.SAME,
						undefinedRef()
					),
					_Parenthesis(
						currNode.defaultExpr
					),
					currValueRaw
				)
			} else {
				currValueRaw
			};

			if(currNode.varRef!==null || currNode.varDecl!==null) {
				// actual destructuring
				// (assigning an element or property from 'value', i.e. the 'currValue', to the variable with name
				// currNode.varName)
				val varSource = currNode.varRef ?: currNode.varDecl;
				val varSTE = switch(varSource) {
					IdentifierRef_IM: varSource.id_IM
					VariableDeclaration: findSymbolTableEntryForElement(varSource, true)
				};
				simpleAssignments += varSTE -> currValue;
			}
			else if(currNode.nestedNodes!==null && !currNode.nestedNodes.empty) {
				// nested destructuring
				// (assigning the current value in 'currValue' to the nested destructuring pattern)
				nestedPatternsCounter++;
				// fparname = null since we do not generate any function
				traverse(helperVars, simpleAssignments, currNode.nestedNodes, currValue, null, helperVarSuffix+"$"+nestedPatternsCounter);
			}
			else {
				// padding entry (from elision)
				// -> do nothing (but consume the current index 'i')
			}
		}
	}

	def private static final int getNestingDepth(ForStatement stmnt) {
		var d = 0;
		var EObject obj = stmnt;
		while((obj = obj.eContainer)!==null) {
			if(obj instanceof ForStatement)
				d++;
		}
		return d;
	}

	/**
	 * Returns a list of statements that are the root statements of the next outer variable environment directly
	 * containing the given AST node.
	 */
	def static EList<? super Statement> getContainingVariableEnvironmentContent(EObject astNode) {
		val vee = EcoreUtil2.getContainerOfType(astNode.eContainer, VariableEnvironmentElement);
		if(vee===null) {
			throw new IllegalArgumentException("given AST node does not have an outer variable environment");
		}
		return switch(vee) {
			Script: vee.scriptElements
			FunctionOrFieldAccessor: vee.body.statements
			CatchBlock: vee.block.statements
			PropertyAssignment: getContainingVariableEnvironmentContent(vee)
			WithStatement: {
				if(!(vee.statement instanceof Block)) {
					vee.statement = _Block(vee.statement);
				}
				(vee.statement as Block).statements
			}
		};
	}

	def private static VariableDeclaration getVariableDeclarationFromSTE(SymbolTableEntry ste) {
		return ste.elementsOfThisName.filter(VariableDeclaration).head;
	}
}
