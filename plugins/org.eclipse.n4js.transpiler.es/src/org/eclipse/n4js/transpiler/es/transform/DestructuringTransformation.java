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
package org.eclipse.n4js.transpiler.es.transform;

import static com.google.common.collect.Iterables.toArray;
import static org.eclipse.n4js.n4JS.DestructureUtils.containsDestructuringPattern;
import static org.eclipse.n4js.n4JS.DestructureUtils.isRoot;
import static org.eclipse.n4js.n4JS.DestructureUtils.isTopOfDestructuringAssignment;
import static org.eclipse.n4js.n4JS.DestructureUtils.isTopOfDestructuringForStatement;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._AssignmentExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Block;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ConditionalExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._EqualityExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ExprStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._FormalParameter;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._FunExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IndexAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._NumericLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Parenthesis;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ReturnStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Snippet;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableStatement;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.n4JS.WithStatement;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesAfter;
import org.eclipse.n4js.transpiler.TransformationDependency.Optional;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryIMOnly;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Strings;

/**
 * Transforms ES6 destructuring patterns into equivalent ES5 code. If the target engine supports ES6 destructuring
 * patterns, this transformation can simply be deactivated.
 * <p>
 * For details on destructuring patterns, see documentation of class {@link DestructNode}.
 */
@Optional(GeneratorOption.Destructuring)
@ExcludesAfter(StaticPolyfillTransformation.class) // otherwise destructuring patterns from filling module won't be
													// processed!
public class DestructuringTransformation extends Transformation {

	/** Counts destructuring patterns per variable environment. Used to avoid name clashes of helper variables. */
	private final Map<VariableEnvironmentElement, Integer> destructsPerScope = new HashMap<>();

	@Override
	public void assertPreConditions() {
		// true
		// (however, this transformation should run as early as possible)
	}

	@Override
	public void assertPostConditions() {
		// true
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {

		// compute nodes we have to transform before changing anything
		List<VariableStatement> destructBindings = toList(filter(
				collectNodes(getState().im, VariableStatement.class, true), vs -> containsDestructuringPattern(vs)));
		List<AssignmentExpression> destructAssignments = toList(filter(
				collectNodes(getState().im, AssignmentExpression.class, true),
				ae -> isTopOfDestructuringAssignment(ae) && isRoot(ae)));
		List<ForStatement> destructForStmnts = toList(filter(collectNodes(getState().im, ForStatement.class, true),
				fs -> containsDestructuringPattern(fs) || isTopOfDestructuringForStatement(fs)));

		// now perform the changes
		for (VariableStatement vs : destructBindings) {
			transformDestructuringBindings(vs);
		}
		for (AssignmentExpression ae : destructAssignments) {
			transformDestructuringAssignment(ae);
		}
		for (ForStatement fs : destructForStmnts) {
			transformForStatementWithDestructuring(fs);
		}
	}

	/**
	 * Transforms not a single destructuring binding but all destructuring bindings in the given variable statement.
	 */
	private void transformDestructuringBindings(VariableStatement stmnt) {
		List<VariableDeclaration> newVarDecls = computeVariableDeclarations(stmnt.getVarDeclsOrBindings());
		stmnt.getVarDeclsOrBindings().clear();
		stmnt.getVarDeclsOrBindings().addAll(newVarDecls);
	}

	/**
	 * Transforms (a single) destructuring assignment.
	 */
	public void transformDestructuringAssignment(AssignmentExpression expr) {
		// We pass the value of the expression to the function call. GHOLD-407
		String fparName;
		FunctionExpression helperFun;

		fparName = "$destruct" + "Param0";
		FormalParameter fpar = _FormalParameter(fparName);
		helperFun = _FunExpr(false, null, fpar);

		EList<Statement> helperFunContents = helperFun.getBody().getStatements();
		DestructNode rootNode = DestructNode.unify(expr);
		List<VariableDeclaration> helperVars = new ArrayList<>();
		List<Pair<SymbolTableEntry, ? extends Expression>> simpleAssignments = new ArrayList<>();
		traverse(helperVars, simpleAssignments, rootNode, expr.getRhs(), fparName);

		helperFunContents.addAll(toList(map(helperVars, vd -> _VariableStatement(vd))));
		helperFunContents.addAll(toList(map(simpleAssignments, sa -> _ExprStmnt(_AssignmentExpr(
				__NSSafe_IdentRef(sa.getKey()), sa.getValue())))));

		// the following return statement is required to make sure entire expression evaluates to the rhs of
		// the assignment expression 'expr' (without evaluating rhs again!)
		SymbolTableEntry firstHelperVarSTE = findSymbolTableEntryForElement(helperVars.get(0), false);
		helperFunContents.add(_ReturnStmnt(_IdentRef(firstHelperVarSTE)));

		// parentheses required because the expression might appear as a statement (to disambiguate from function
		// declaration) replace(expr, callExpr);
		ParameterizedCallExpression callExpr = _CallExpr(_Parenthesis(helperFun), expr.getRhs());
		replace(expr, callExpr);
	}

	private void transformForStatementWithDestructuring(ForStatement stmnt) {

		if (stmnt.isForPlain()) {

			if (!stmnt.getVarDeclsOrBindings().isEmpty()) {
				// something like: for( var [a,b]=[1,2] ;;) {}

				// note: pretty much the same case as method #transformDestructuringBindings() above
				List<VariableDeclaration> newVarDecls = computeVariableDeclarations(stmnt.getVarDeclsOrBindings());
				stmnt.getVarDeclsOrBindings().clear();
				stmnt.getVarDeclsOrBindings().addAll(newVarDecls);

			} else {
				// something like: for( [a,b]=[1,2] ;;) {}

				// here, stmnt.initExpr is an ordinary destructuring assignment that was already handled by method
				// #transformDestructuringAssignment(AssignmentExpression) above -> nothing to do here!
			}

		} else {
			// now: for..in OR for..of

			int depth = getNestingDepth(stmnt);

			VariableDeclaration iterVar = _VariableDeclaration("$destructStep$" + depth);
			SymbolTableEntryIMOnly iterVarSTE = createSymbolTableEntryIMOnly(iterVar);

			boolean needDeclarations = false;
			VariableStatementKeyword varStmtKeyword = VariableStatementKeyword.VAR;
			List<VariableDeclaration> helperVars = new ArrayList<>();
			List<Pair<SymbolTableEntry, ? extends Expression>> simpleAssignments = new ArrayList<>();
			if (!stmnt.getVarDeclsOrBindings().isEmpty()) {
				// something like: for( var [a,b] of [ [1,2], [3,4] ] ) {}

				if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
					assertTrue("there should be exactly one VariableBinding in stmnt.varDeclsOrBindings",
							stmnt.getVarDeclsOrBindings().size() == 1
									&& stmnt.getVarDeclsOrBindings().get(0) instanceof VariableBinding);
				}

				DestructNode rootNode = DestructNode.unify((VariableBinding) stmnt.getVarDeclsOrBindings().get(0));
				// fparname = null since we do not generate any function.
				traverse(helperVars, simpleAssignments, rootNode, _IdentRef(iterVarSTE), null);
				needDeclarations = true;
				varStmtKeyword = stmnt.getVarStmtKeyword();

			} else if (stmnt.getInitExpr() instanceof ArrayLiteral || stmnt.getInitExpr() instanceof ObjectLiteral) {
				// something like: for( [a,b] of [ [1,2], [3,4] ] ) {}

				DestructNode rootNode = DestructNode.unify(stmnt);
				// fparname = null since we do not generate any function.
				traverse(helperVars, simpleAssignments, rootNode, _IdentRef(iterVarSTE), null);
				needDeclarations = false;

			} else {
				throw new IllegalArgumentException();
			}

			if (!(stmnt.getStatement() instanceof Block)) {
				stmnt.setStatement(_Block(stmnt.getStatement()));
			}
			Block body = (Block) stmnt.getStatement();

			List<Statement> toBeInserted = new ArrayList<>();
			if (needDeclarations) {
				toBeInserted.add(_VariableStatement(varStmtKeyword, toArray(map(simpleAssignments, sa -> {
					VariableDeclaration varDecl = getVariableDeclarationFromSTE(sa.getKey());
					varDecl.setExpression(sa.getValue());
					return varDecl;
				}), VariableDeclarationOrBinding.class)));
			} else {
				toBeInserted.add(_VariableStatement(toArray(helperVars, VariableDeclaration.class)));
				toBeInserted.addAll(toList(map(simpleAssignments, sa -> _ExprStmnt(_AssignmentExpr(
						__NSSafe_IdentRef(sa.getKey()), sa.getValue())))));
			}

			body.getStatements().addAll(0, toBeInserted);

			// initExpr has been move to beginning of body (if any)
			stmnt.setInitExpr(null);
			// variable declarations have been moved to beginning of body (if any)
			stmnt.getVarDeclsOrBindings().clear();
			// only declared variable in the for statement is the iteration variable
			stmnt.getVarDeclsOrBindings().add(iterVar);
		}
	}

	/**
	 * Breaks down all VariableBindings in the given list into simple variable declarations and returns a list
	 * containing the variable declarations that were contained in the given list from the beginning plus those created
	 * when breaking down the variable bindings. The order of the elements in the given list is preserved!
	 */
	private List<VariableDeclaration> computeVariableDeclarations(
			List<VariableDeclarationOrBinding> varDeclsOrBindings) {
		List<VariableDeclaration> result = new ArrayList<>();
		for (VariableDeclarationOrBinding vdeclOrBinding : varDeclsOrBindings) {
			if (vdeclOrBinding instanceof VariableDeclaration) {
				result.add((VariableDeclaration) vdeclOrBinding);
			} else if (vdeclOrBinding instanceof VariableBinding) {
				DestructNode rootNode = DestructNode.unify(vdeclOrBinding);
				List<VariableDeclaration> helperVars = new ArrayList<>();
				List<Pair<SymbolTableEntry, ? extends Expression>> simpleAssignments = new ArrayList<>();
				// fparname = null since we do not generate any function.
				traverse(helperVars, simpleAssignments, rootNode, vdeclOrBinding.getExpression(), null);
				result.addAll(toList(map(simpleAssignments, sa -> {
					VariableDeclaration varDecl = getVariableDeclarationFromSTE(sa.getKey());
					varDecl.setExpression(sa.getValue());
					return varDecl;
				})));
			}
		}
		return result;
	}

	private void traverse(List<VariableDeclaration> helperVars,
			List<Pair<SymbolTableEntry, ? extends Expression>> simpleAssignments,
			DestructNode rootNode, Expression value, String fparName) {

		VariableEnvironmentElement scope = N4JSASTUtils.getScope(rootNode.getAstElement(), false);
		if (scope == null) {
			scope = getState().im;
		}
		int n = destructsPerScope.merge(scope, 1, (i, j) -> i + j) - 1;
		traverse(helperVars, simpleAssignments, rootNode.getNestedNodes(), value, fparName, Integer.toString(n));
	}

	/**
	 * Breaks down the destructuring pattern, represented by the given {@link DestructNode}s, into a number of simple
	 * assignments (without destructuring) that will be added to argument 'simpleAssignments'. The order of those simple
	 * assignments matters. Nested patterns as returned by {@link DestructNode#getNestedNodes()} are also broken down.
	 * fparName, if not null, is the parameter name of the enclosing function.
	 */
	private void traverse(List<VariableDeclaration> helperVars,
			List<Pair<SymbolTableEntry, ? extends Expression>> simpleAssignments,
			DestructNode[] nodes, Expression value, String fparName, String helperVarSuffix) {

		int len = nodes.length;
		boolean isPositionalPattern = IterableExtensions.exists(Arrays.asList(nodes), n -> n.isPositional());
		boolean isRest = isPositionalPattern && len > 0 && nodes[len - 1].isRest();

		// STEP 1: create code to prepare the value to be destructured and to assign it to a helper variable

		// creating a new helper variable
		String currHelperVarName = "$destruct" + helperVarSuffix;
		VariableDeclaration currHelperVarDecl = _VariableDeclaration(currHelperVarName);
		helperVars.add(currHelperVarDecl);
		SymbolTableEntry currHelperVarSTE = findSymbolTableEntryForElement(currHelperVarDecl, true);
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			assertTrue("", getVariableDeclarationFromSTE(currHelperVarSTE) == currHelperVarDecl);
			assertTrue("", currHelperVarSTE.getElementsOfThisName().contains(currHelperVarDecl));
		}
		var $sliceToArrayForDestructSTE = steFor_$sliceToArrayForDestruct();

		if (isRest) {
			// result.add(currHelperVar+" = function(arr){return Array.isArray(arr) ? arr :
			// Array.from(arr);}("+value+")");
			simpleAssignments.add(Pair.of(currHelperVarSTE, _CallExpr(
					_Snippet("function(arr){return Array.isArray(arr) ? arr : Array.from(arr);}"),
					value)));
		} else {
			Expression passValue;
			if (Strings.isNullOrEmpty(fparName)) {
				passValue = value;
			} else {
				// If the fparName is not empty, the generated function for destructuring has a parameter and we should
				// use that parameter instead. GHOLD-407
				SymbolTableEntryInternal fparSTE = getSymbolTableEntryInternal(fparName, true);
				passValue = _IdentRef(fparSTE);
			}
			if (isPositionalPattern) {
				// result.add(currHelperVar+" = $sliceToArrayForDestruct(("+value+"), "+len+")");
				simpleAssignments.add(Pair.of(currHelperVarSTE, _CallExpr(
						_IdentRef($sliceToArrayForDestructSTE),
						_Parenthesis(passValue),
						_NumericLiteral(len))));
			} else {
				// result.add(currHelperVar+" = ("+value+")");
				simpleAssignments.add(Pair.of(currHelperVarSTE, _Parenthesis(
						passValue)));
			}
		}

		// STEP 2: create code to perform the actual destructuring

		SymbolTableEntryOriginal sliceSTE = getSymbolTableEntryForMember(arrayType(getState().G), "slice", false, false,
				true);

		var nestedPatternsCounter = 0;
		for (var i = 0; i < len; i++) {
			DestructNode currNode = nodes[i];

			// get the current element or property out of 'value' (i.e. the one that corresponds to 'currNode')
			Expression currValueRaw;
			if (isRest && i == len - 1) {
				// currHelperVar.slice(i)
				currValueRaw = _CallExpr(_PropertyAccessExpr(currHelperVarSTE, sliceSTE), _NumericLiteral(i));
			} else if (isPositionalPattern) {
				// currHelperVar[i]
				currValueRaw = _IndexAccessExpr(currHelperVarSTE, _NumericLiteral(i));
			} else {
				// currHelperVar['propName']
				currValueRaw = _IndexAccessExpr(currHelperVarSTE, _StringLiteral(currNode.getPropName()));
			}

			Expression currValue;
			if (currNode.getDefaultExpr() != null) {
				// currValueRaw+" == undefined ? ("+transformAST.doTransform(currNode.defaultExpr)+") : "+currValueRaw
				currValue = _ConditionalExpr(
						_EqualityExpr(
								copy(currValueRaw), // must copy because used twice in this conditional expression!
								EqualityOperator.SAME,
								undefinedRef()),
						_Parenthesis(
								currNode.getDefaultExpr()),
						currValueRaw);
			} else {
				currValue = currValueRaw;
			}

			if (currNode.getVarRef() != null || currNode.getVarDecl() != null) {
				// actual destructuring
				// (assigning an element or property from 'value', i.e. the 'currValue', to the variable with name
				// currNode.varName)
				TypableElement varSource = currNode.getVarRef() != null ? currNode.getVarRef() : currNode.getVarDecl();
				SymbolTableEntry varSTE = null;

				if (varSource instanceof IdentifierRef_IM) {
					varSTE = ((IdentifierRef_IM) varSource).getId_IM();
				} else if (varSource instanceof VariableDeclaration) {
					varSTE = findSymbolTableEntryForElement((VariableDeclaration) varSource, true);
				}
				simpleAssignments.add(Pair.of(varSTE, currValue));
			} else if (currNode.getNestedNodes() != null && currNode.getNestedNodes().length != 0) {
				// nested destructuring
				// (assigning the current value in 'currValue' to the nested destructuring pattern)
				nestedPatternsCounter++;
				// fparname = null since we do not generate any function
				traverse(helperVars, simpleAssignments, currNode.getNestedNodes(), currValue, null,
						helperVarSuffix + "$" + nestedPatternsCounter);
			} else {
				// padding entry (from elision)
				// -> do nothing (but consume the current index 'i')
			}
		}
	}

	private static final int getNestingDepth(ForStatement stmnt) {
		int d = 0;
		EObject obj = stmnt;
		while ((obj = obj.eContainer()) != null) {
			if (obj instanceof ForStatement) {
				d++;
			}
		}
		return d;
	}

	/**
	 * Returns a list of statements that are the root statements of the next outer variable environment directly
	 * containing the given AST node.
	 */
	static EList<? super Statement> getContainingVariableEnvironmentContent(EObject astNode) {
		VariableEnvironmentElement vee = EcoreUtil2.getContainerOfType(astNode.eContainer(),
				VariableEnvironmentElement.class);
		if (vee == null) {
			throw new IllegalArgumentException("given AST node does not have an outer variable environment");
		}
		if (vee instanceof Script) {
			return ((Script) vee).getScriptElements();
		}
		if (vee instanceof FunctionOrFieldAccessor) {
			return ((FunctionOrFieldAccessor) vee).getBody().getStatements();
		}
		if (vee instanceof CatchBlock) {
			return ((CatchBlock) vee).getBlock().getStatements();
		}
		if (vee instanceof PropertyAssignment) {
			return getContainingVariableEnvironmentContent(vee);
		}
		if (vee instanceof WithStatement) {
			WithStatement ws = (WithStatement) vee;
			if (!(ws.getStatement() instanceof Block)) {
				ws.setStatement(_Block(ws.getStatement()));
			}
			return ((Block) ws.getStatement()).getStatements();
		}
		throw new IllegalArgumentException("given AST node does not have an outer variable environment");
	}

	private static VariableDeclaration getVariableDeclarationFromSTE(SymbolTableEntry ste) {
		return head(filter(ste.getElementsOfThisName(), VariableDeclaration.class));
	}
}
