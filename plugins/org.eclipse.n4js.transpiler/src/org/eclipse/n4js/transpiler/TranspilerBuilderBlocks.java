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
package org.eclipse.n4js.transpiler;

import static com.google.common.collect.Iterables.toArray;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.reduce;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.ListExtensions.map;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.AdditiveOperator;
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrayPadding;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AssignmentOperator;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalOperator;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4MemberAnnotationList;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ObjectBindingPattern;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.RelationalOperator;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.postprocessing.CompileTimeExpressionProcessor;
import org.eclipse.n4js.postprocessing.ComputedNameProcessor;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.ImFactory;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.Snippet;
import org.eclipse.n4js.transpiler.im.StringLiteralForSTE;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Builder methods for intermediate elements.
 */
@SuppressWarnings("javadoc")
public class TranspilerBuilderBlocks {

	// ############################################################################################
	// n4js.xcore

	public static ImportDeclaration _ImportDecl(ImportSpecifier... importSpecifiers) {
		ImportDeclaration result = N4JSFactory.eINSTANCE.createImportDeclaration();
		result.setModule(null); // must always be null, because we are in the intermediate model
		result.getImportSpecifiers().addAll(toList(filterNull(Arrays.asList(importSpecifiers))));
		result.setImportFrom(result.getImportSpecifiers().size() > 0);
		return result;
	}

	public static NamedImportSpecifier _NamedImportSpecifier(String importedElementName, String alias,
			boolean usedInCode) {

		NamedImportSpecifier result = N4JSFactory.eINSTANCE.createNamedImportSpecifier();
		result.setImportedElement(null); // must always be null, because we are in the intermediate model
		result.setImportedElementAsText(importedElementName);
		result.setAlias(alias);
		result.setFlaggedUsedInCode(usedInCode);
		return result;
	}

	public static NamedImportSpecifier _DefaultImportSpecifier(String importedElementName, boolean usedInCode) {
		DefaultImportSpecifier result = N4JSFactory.eINSTANCE.createDefaultImportSpecifier();
		result.setImportedElement(null); // must always be null, because we are in the intermediate model
		result.setImportedElementAsText(importedElementName);
		result.setFlaggedUsedInCode(usedInCode);
		return result;
	}

	public static NamespaceImportSpecifier _NamespaceImportSpecifier(String namespaceName, boolean usedInCode) {
		NamespaceImportSpecifier result = N4JSFactory.eINSTANCE.createNamespaceImportSpecifier();
		result.setAlias(namespaceName);
		result.setFlaggedUsedInCode(usedInCode);
		return result;
	}

	public static VariableStatement _VariableStatement(VariableDeclaration... varDecls) {
		return _VariableStatement(VariableStatementKeyword.VAR, varDecls);
	}

	public static VariableStatement _VariableStatement(VariableStatementKeyword keyword,
			VariableDeclarationOrBinding... varDecls) {

		VariableStatement result = N4JSFactory.eINSTANCE.createVariableStatement();
		result.setVarStmtKeyword(keyword);
		result.getVarDeclsOrBindings().addAll(toList(filterNull(Arrays.asList(varDecls))));
		return result;
	}

	public static VariableDeclaration _VariableDeclaration(String name) {
		VariableDeclaration result = N4JSFactory.eINSTANCE.createVariableDeclaration();
		result.setName(name);
		return result;
	}

	public static VariableDeclaration _VariableDeclaration(String name, Expression exp) {
		VariableDeclaration result = N4JSFactory.eINSTANCE.createVariableDeclaration();
		result.setName(name);
		result.setExpression(exp);
		return result;
	}

	public static VariableBinding _VariableBinding(Iterable<? extends BindingProperty> properties, Expression exp) {
		ObjectBindingPattern pattern = N4JSFactory.eINSTANCE.createObjectBindingPattern();
		pattern.getProperties().addAll(toList(properties));
		VariableBinding result = N4JSFactory.eINSTANCE.createVariableBinding();
		result.setPattern(pattern);
		result.setExpression(exp);
		return result;
	}

	public static ExportDeclaration _ExportDeclaration(ExportableElement exported) {
		ExportDeclaration result = N4JSFactory.eINSTANCE.createExportDeclaration();
		result.setExportedElement(exported);
		return result;
	}

	public static ReturnStatement _ReturnStmnt() {
		ReturnStatement result = N4JSFactory.eINSTANCE.createReturnStatement();
		return result;
	}

	public static ReturnStatement _ReturnStmnt(Expression expr) {
		ReturnStatement result = N4JSFactory.eINSTANCE.createReturnStatement();
		result.setExpression(expr);
		return result;
	}

	public static IfStatement _IfStmnt(Expression condition, Statement ifStmnt) {
		return _IfStmnt(condition, ifStmnt, null);
	}

	public static IfStatement _IfStmnt(Expression condition, Statement ifStmnt, Statement elseStmnt) {
		IfStatement result = N4JSFactory.eINSTANCE.createIfStatement();
		result.setExpression(condition);
		result.setIfStmt(ifStmnt);
		result.setElseStmt(elseStmnt);
		return result;
	}

	public static ThrowStatement _ThrowStmnt(Expression expression) {
		ThrowStatement result = N4JSFactory.eINSTANCE.createThrowStatement();
		result.setExpression(expression);
		return result;
	}

	public static ConditionalExpression _ConditionalExpr(Expression condition, Expression trueExpr,
			Expression falseExpr) {

		ConditionalExpression result = N4JSFactory.eINSTANCE.createConditionalExpression();
		result.setExpression(condition);
		result.setTrueExpression(trueExpr);
		result.setFalseExpression(falseExpr);
		return result;
	}

	public static YieldExpression _YieldExpr(Expression expr) {
		YieldExpression result = N4JSFactory.eINSTANCE.createYieldExpression();
		result.setExpression(expr);
		return result;
	}

	public static ParameterizedCallExpression _CallExpr() {
		ParameterizedCallExpression result = N4JSFactory.eINSTANCE.createParameterizedCallExpression();
		return result;
	}

	public static ParameterizedCallExpression _CallExpr(Expression target, Expression... arguments) {
		return _CallExpr(target, false, arguments);
	}

	public static ParameterizedCallExpression _CallExpr(Expression target, boolean optionalChaining,
			Expression... arguments) {
		ParameterizedCallExpression result = N4JSFactory.eINSTANCE.createParameterizedCallExpression();
		result.setTarget(target);
		result.setOptionalChaining(optionalChaining);
		result.getArguments().addAll(toList(map(filterNull(Arrays.asList(arguments)), arg -> _Argument(arg))));
		return result;
	}

	public static Argument _Argument(Expression expression) {
		return _Argument(false, expression);
	}

	public static Argument _Argument(boolean spread, Expression expression) {
		Argument result = N4JSFactory.eINSTANCE.createArgument();
		result.setSpread(spread);
		result.setExpression(expression);
		return result;
	}

	public static ExpressionStatement _ExprStmnt(Expression expr) {
		ExpressionStatement result = N4JSFactory.eINSTANCE.createExpressionStatement();
		result.setExpression(expr);
		return result;
	}

	public static AssignmentExpression _AssignmentExpr() {
		AssignmentExpression result = N4JSFactory.eINSTANCE.createAssignmentExpression();
		result.setOp(AssignmentOperator.ASSIGN);
		return result;
	}

	public static AssignmentExpression _AssignmentExpr(Expression lhs, Expression rhs) {
		AssignmentExpression result = N4JSFactory.eINSTANCE.createAssignmentExpression();
		result.setLhs(lhs);
		result.setOp(AssignmentOperator.ASSIGN);
		result.setRhs(rhs);
		return result;
	}

	public static ParameterizedPropertyAccessExpression_IM _PropertyAccessExpr() {
		ParameterizedPropertyAccessExpression_IM result = ImFactory.eINSTANCE
				.createParameterizedPropertyAccessExpression_IM();
		return result;
	}

	public static ParameterizedPropertyAccessExpression_IM _PropertyAccessExpr(SymbolTableEntry target,
			SymbolTableEntry... properties) {
		return _PropertyAccessExpr(_IdentRef(target), properties);
	}

	public static ParameterizedPropertyAccessExpression_IM _PropertyAccessExpr(Expression target,
			SymbolTableEntry... properties) {
		if (properties == null || exists(Arrays.asList(properties), p -> p == null)) {
			throw new IllegalArgumentException("none of the properties may be null");
		}
		var result = ImFactory.eINSTANCE.createParameterizedPropertyAccessExpression_IM();
		result.setTarget(target);
		if (properties.length > 0) {
			result.setRewiredTarget(properties[0]);
			for (int idx = 1; idx < properties.length; idx++) {
				ParameterizedPropertyAccessExpression_IM newResult = ImFactory.eINSTANCE
						.createParameterizedPropertyAccessExpression_IM();
				newResult.setTarget(result);
				newResult.setRewiredTarget(properties[idx]);
				result = newResult;
			}
		}
		return result;
	}

	public static IndexedAccessExpression _IndexAccessExpr() {
		return _IndexAccessExpr((Expression) null, null);
	}

	public static IndexedAccessExpression _IndexAccessExpr(SymbolTableEntry target, Expression index) {
		return _IndexAccessExpr(_IdentRef(target), index);
	}

	public static IndexedAccessExpression _IndexAccessExpr(Expression target, Expression index) {
		IndexedAccessExpression result = N4JSFactory.eINSTANCE.createIndexedAccessExpression();
		result.setTarget(target);
		result.setIndex(index);
		return result;
	}

	public static NewExpression _NewExpr(Expression callee, Expression... arguments) {
		NewExpression result = N4JSFactory.eINSTANCE.createNewExpression();
		result.setCallee(callee);
		result.setWithArgs(!isEmpty(filterNull(Arrays.asList(arguments))));
		result.getArguments().addAll(toList(map(filterNull(Arrays.asList(arguments)), a -> _Argument(a))));
		return result;
	}

	public static RelationalExpression _RelationalExpr(Expression lhs, RelationalOperator op, Expression rhs) {
		RelationalExpression result = N4JSFactory.eINSTANCE.createRelationalExpression();
		result.setLhs(lhs);
		result.setOp(op);
		result.setRhs(rhs);
		return result;
	}

	public static EqualityExpression _EqualityExpr(Expression lhs, EqualityOperator op, Expression rhs) {
		EqualityExpression result = N4JSFactory.eINSTANCE.createEqualityExpression();
		result.setLhs(lhs);
		result.setOp(op);
		result.setRhs(rhs);
		return result;
	}

	public static UnaryExpression _NOT(Expression expr) {
		return _UnaryExpr(UnaryOperator.NOT, expr);
	}

	public static Expression _OR(Expression... operands) {
		return reduce(Arrays.asList(operands), (op1, op2) -> _BinaryLogicalExpr(op1, BinaryLogicalOperator.OR, op2));
	}

	public static Expression _AND(Expression... operands) {
		return reduce(Arrays.asList(operands), (op1, op2) -> _BinaryLogicalExpr(op1, BinaryLogicalOperator.AND, op2));
	}

	public static UnaryExpression _Void0() {
		return _Void(_NumericLiteral(0));
	}

	public static UnaryExpression _Void(Expression expr) {
		return _UnaryExpr(UnaryOperator.VOID, expr);
	}

	public static UnaryExpression _UnaryExpr(UnaryOperator op, Expression expr) {
		UnaryExpression result = N4JSFactory.eINSTANCE.createUnaryExpression();
		result.setOp(op);
		result.setExpression(expr);
		return result;
	}

	public static BinaryLogicalExpression _BinaryLogicalExpr(Expression lhs, BinaryLogicalOperator op, Expression rhs) {
		BinaryLogicalExpression result = N4JSFactory.eINSTANCE.createBinaryLogicalExpression();
		result.setLhs(lhs);
		result.setOp(op);
		result.setRhs(rhs);
		return result;
	}

	public static CommaExpression _CommaExpression(Expression... expressions) {
		CommaExpression result = N4JSFactory.eINSTANCE.createCommaExpression();
		result.getExprs().addAll(Arrays.asList(expressions));
		return result;
	}

	public static AdditiveExpression _AdditiveExpression(Expression lhs, AdditiveOperator op, Expression rhs) {
		AdditiveExpression result = N4JSFactory.eINSTANCE.createAdditiveExpression();
		result.setLhs(lhs);
		result.setOp(op);
		result.setRhs(rhs);
		return result;
	}

	public static AdditiveExpression _AdditiveExpression(AdditiveOperator op, Expression... operands) {
		if (operands == null || exists(Arrays.asList(operands), e -> e == null)) {
			throw new IllegalArgumentException("none of the operands may be null");
		}
		if (operands.length < 2) {
			throw new IllegalArgumentException("need at least two operands");
		}
		var result = N4JSFactory.eINSTANCE.createAdditiveExpression();
		result.setLhs(operands[0]);
		result.setOp(op);
		result.setRhs(operands[1]);
		for (int idx = 2; idx < operands.length; idx++) {
			AdditiveExpression newResult = N4JSFactory.eINSTANCE.createAdditiveExpression();
			newResult.setLhs(result);
			newResult.setOp(op);
			newResult.setRhs(operands[idx]);
			result = newResult;
		}
		return result;
	}

	public static ObjectLiteral _ObjLit() { // required to resolve the ambiguity between the other two methods
		return _ObjLit((PropertyAssignment[]) null);
	}

	/**
	 * Convenience method for creating object literals that only contain {@link PropertyNameValuePair}s. It is legal to
	 * pass in one or more <code>null</code> values (they will be ignored).
	 */
	@SuppressWarnings("unchecked")
	public static ObjectLiteral _ObjLit(Pair<String, Expression>... nameValuePairs) {
		return _ObjLit(toArray(map(filterNull(Arrays.asList(nameValuePairs)),
				p -> _PropertyNameValuePair(p.getKey(), p.getValue())), PropertyNameValuePair.class));
	}

	public static ObjectLiteral _ObjLit(PropertyAssignment... pas) {
		ObjectLiteral result = N4JSFactory.eINSTANCE.createObjectLiteral();
		if (pas != null) {
			result.getPropertyAssignments().addAll(toList(filterNull(Arrays.asList(pas))));
		}
		return result;
	}

	public static PropertyNameValuePair _PropertyNameValuePair(String name, Expression value) {
		return _PropertyNameValuePair(_LiteralOrComputedPropertyName(name), value);
	}

	public static PropertyNameValuePair _PropertyNameValuePair(LiteralOrComputedPropertyName name, Expression value) {
		PropertyNameValuePair result = N4JSFactory.eINSTANCE.createPropertyNameValuePair();
		result.setDeclaredName(name);
		result.setExpression(value);
		return result;
	}

	public static PropertyGetterDeclaration _PropertyGetterDecl(String name, Statement... stmnts) {
		PropertyGetterDeclaration result = N4JSFactory.eINSTANCE.createPropertyGetterDeclaration();
		result.setDeclaredName(_LiteralOrComputedPropertyName(name));
		result.setBody(_Block(stmnts));
		return result;
	}

	public static ArrayLiteral _ArrLit() { // required to resolve the ambiguity between the other two methods
		return _ArrLit((ArrayElement[]) null);
	}

	public static ArrayLiteral _ArrLit(Expression... elements) {
		return _ArrLit(
				toArray(map(filterNull(Arrays.asList(elements)), e -> _ArrayElement(e)), ArrayElement.class));
	}

	public static ArrayLiteral _ArrLit(ArrayElement... elements) {
		ArrayLiteral result = N4JSFactory.eINSTANCE.createArrayLiteral();
		if (elements != null) {
			result.getElements().addAll(toList(filterNull(Arrays.asList(elements))));
		}
		return result;
	}

	public static ArrayElement _ArrayElement(Expression expression) {
		return _ArrayElement(false, expression);
	}

	public static ArrayElement _ArrayElement(boolean spread, Expression expression) {
		ArrayElement result = N4JSFactory.eINSTANCE.createArrayElement();
		result.setSpread(spread);
		result.setExpression(expression);
		return result;
	}

	public static ArrayPadding _ArrayPadding() {
		ArrayPadding result = N4JSFactory.eINSTANCE.createArrayPadding();
		return result;
	}

	public static FunctionDeclaration _FunDecl(String name, Statement... statements) {
		return _FunDecl(name, new FormalParameter[0], statements);
	}

	public static FunctionDeclaration _FunDecl(String name, FormalParameter[] fpars, Statement... statements) {
		FunctionDeclaration result = N4JSFactory.eINSTANCE.createFunctionDeclaration();
		result.setName(name);
		result.getFpars().addAll(Arrays.asList(fpars));
		result.setBody(_Block(statements));
		return result;
	}

	public static FunctionExpression _FunExpr(boolean async, Statement... statements) {
		return _FunExpr(async, null, new FormalParameter[0], statements);
	}

	public static FunctionExpression _FunExpr(boolean async, String name, Statement... statements) {
		return _FunExpr(async, name, new FormalParameter[0], statements);
	}

	public static FunctionExpression _FunExpr(boolean async, String name, FormalParameter... formalParams) {
		return _FunExpr(async, name, formalParams, new Statement[0]);
	}

	public static FunctionExpression _FunExpr(boolean async, String name, FormalParameter[] fpars,
			Statement... statements) {
		if (statements != null && statements.length == 1 && statements[0] instanceof Block) {
			// safe guard: in case complex EMF inheritance hierarchy causes wrong overload to be invoked
			return _FunExprWithBlock(async, name, fpars, (Block) statements[0]);
		}
		FunctionExpression result = N4JSFactory.eINSTANCE.createFunctionExpression();
		result.setDeclaredAsync(async);
		result.setName(name);
		result.getFpars().addAll(Arrays.asList(fpars));
		result.setBody(_Block(statements));
		return result;
	}

	public static FormalParameter _FormalParameter(String name) {
		FormalParameter result = N4JSFactory.eINSTANCE.createFormalParameter();
		result.setName(name);
		return result;
	}

	public static FunctionExpression _FunExpr(boolean async, String name, FormalParameter[] fpars, Block block) {
		return _FunExprWithBlock(async, name, fpars, block);
	}

	private static FunctionExpression _FunExprWithBlock(boolean async, String name, FormalParameter[] fpars,
			Block block) {
		FunctionExpression result = N4JSFactory.eINSTANCE.createFunctionExpression();
		result.setDeclaredAsync(async);
		result.setName(name);
		result.getFpars().addAll(Arrays.asList(fpars));
		result.setBody(block);
		return result;
	}

	/** Creates a {@link ArrowFunction#isSingleExprImplicitReturn() single-expression arrow function}. */
	public static ArrowFunction _ArrowFunc(boolean async, FormalParameter[] fpars, Expression expression) {
		ArrowFunction result = N4JSFactory.eINSTANCE.createArrowFunction();
		result.setDeclaredAsync(async);
		result.getFpars().addAll(Arrays.asList(fpars));
		result.setBody(_Block(_ExprStmnt(expression)));
		result.setHasBracesAroundBody(false);
		return result;
	}

	public static ArrowFunction _ArrowFunc(boolean async, FormalParameter[] fpars, Statement... statements) {
		ArrowFunction result = N4JSFactory.eINSTANCE.createArrowFunction();
		result.setDeclaredAsync(async);
		result.getFpars().addAll(Arrays.asList(fpars));
		result.setBody(_Block(statements));
		result.setHasBracesAroundBody(true);
		return result;
	}

	public static N4MemberDeclaration _N4MemberDecl(TMember template, Statement... statements) {
		if (template instanceof TField && statements.length > 0) {
			throw new IllegalArgumentException("fields cannot have statements");
		}
		PropertyNameOwner result;
		if (template instanceof TField) {
			result = N4JSFactory.eINSTANCE.createN4FieldDeclaration();
		} else if (template instanceof TGetter) {
			result = N4JSFactory.eINSTANCE.createN4GetterDeclaration();
		} else if (template instanceof TSetter) {
			result = N4JSFactory.eINSTANCE.createN4SetterDeclaration();
		} else if (template instanceof TMethod) {
			result = N4JSFactory.eINSTANCE.createN4MethodDeclaration();
		} else {
			throw new IllegalArgumentException("unsupported subtype of TMember: " + template.eClass().getName());
		}

		AnnotableN4MemberDeclaration resultAsMD = (AnnotableN4MemberDeclaration) result;

		// basic properties
		result.setDeclaredName(_LiteralOrComputedPropertyName(template.getName()));
		// body
		if (result instanceof FunctionOrFieldAccessor) {
			((FunctionOrFieldAccessor) result).setBody(_Block(
					toArray(filterNull(Arrays.asList(statements)), Statement.class)));
		}
		// formal parameters
		if (template instanceof TSetter) {
			String fparName = "value";
			if (((TSetter) template).getFpar() != null) {
				fparName = ((TSetter) template).getFpar().getName();
			}
			((N4SetterDeclaration) result).setFpar(_Fpar(fparName));
		}
		if (template instanceof TMethod) {
			TMethod tMethod = (TMethod) template;
			((N4MethodDeclaration) result).getFpars().addAll(
					toList(map(tMethod.getFpars(), fpar -> _Fpar(fpar.getName()))));
			((N4MethodDeclaration) result).setDeclaredAsync(tMethod.isDeclaredAsync());
		}
		// static / non-static
		if (template.isStatic()) {
			resultAsMD.getDeclaredModifiers().add(N4Modifier.STATIC);
		}
		// access modifiers
		switch (template.getMemberAccessModifier()) {
		case PUBLIC:
			resultAsMD.getDeclaredModifiers().add(N4Modifier.PUBLIC);
			break;
		case PUBLIC_INTERNAL:
			resultAsMD.getDeclaredModifiers().add(N4Modifier.PUBLIC);
			getOrCreateMemberAnnotationList(resultAsMD).getAnnotations()
					.add(_Annotation(AnnotationDefinition.INTERNAL));
			break;
		case PROTECTED:
			resultAsMD.getDeclaredModifiers().add(N4Modifier.PROTECTED);
			break;
		case PROTECTED_INTERNAL:
			resultAsMD.getDeclaredModifiers().add(N4Modifier.PROTECTED);
			getOrCreateMemberAnnotationList(resultAsMD).getAnnotations()
					.add(_Annotation(AnnotationDefinition.INTERNAL));
			break;
		case PROJECT:
			resultAsMD.getDeclaredModifiers().add(N4Modifier.PROJECT);
			break;
		case PRIVATE:
			resultAsMD.getDeclaredModifiers().add(N4Modifier.PRIVATE);
			break;
		case UNDEFINED: {
			/* NOP */}
		}

		return resultAsMD;
	}

	private static N4MemberAnnotationList getOrCreateMemberAnnotationList(AnnotableN4MemberDeclaration memberDecl) {
		N4MemberAnnotationList annList = memberDecl.getAnnotationList();
		if (annList == null) {
			annList = N4JSFactory.eINSTANCE.createN4MemberAnnotationList();
			memberDecl.setAnnotationList(annList);
		}
		return annList;
	}

	public static N4FieldDeclaration _N4FieldDecl(boolean isStatic, String declaredName, Expression initExpr) {
		return _N4FieldDecl(isStatic, _LiteralOrComputedPropertyName(declaredName), initExpr);
	}

	public static N4FieldDeclaration _N4FieldDecl(boolean isStatic, LiteralOrComputedPropertyName declaredName,
			Expression initExpr) {
		N4FieldDeclaration result = N4JSFactory.eINSTANCE.createN4FieldDeclaration();
		if (isStatic) {
			result.getDeclaredModifiers().add(N4Modifier.STATIC);
		}
		result.setDeclaredName(declaredName);
		result.setExpression(initExpr);
		return result;
	}

	public static N4GetterDeclaration _N4GetterDecl(LiteralOrComputedPropertyName declaredName, Block body) {
		N4GetterDeclaration result = N4JSFactory.eINSTANCE.createN4GetterDeclaration();
		result.setDeclaredName(declaredName);
		result.setBody(body);
		return result;
	}

	public static N4SetterDeclaration _N4SetterDecl(LiteralOrComputedPropertyName declaredName, FormalParameter fpar,
			Block body) {
		N4SetterDeclaration result = N4JSFactory.eINSTANCE.createN4SetterDeclaration();
		result.setDeclaredName(declaredName);
		result.setFpar(fpar);
		result.setBody(body);
		return result;
	}

	public static N4MethodDeclaration _N4MethodDecl(String name, Statement... statements) {
		return _N4MethodDecl(name, new FormalParameter[0], statements);
	}

	public static N4MethodDeclaration _N4MethodDecl(String name, FormalParameter[] fpars, Statement... statements) {
		return _N4MethodDecl(false, _LiteralOrComputedPropertyName(name), fpars,
				_Block(toArray(filterNull(Arrays.asList(statements)), Statement.class)));
	}

	public static N4MethodDeclaration _N4MethodDecl(LiteralOrComputedPropertyName declaredName, Block body) {
		return _N4MethodDecl(false, declaredName, new FormalParameter[0], body);
	}

	public static N4MethodDeclaration _N4MethodDecl(boolean isStatic, LiteralOrComputedPropertyName declaredName,
			FormalParameter[] fpars, Block body) {
		N4MethodDeclaration result = N4JSFactory.eINSTANCE.createN4MethodDeclaration();
		if (isStatic) {
			result.getDeclaredModifiers().add(N4Modifier.STATIC);
		}
		result.setDeclaredName(declaredName);
		result.getFpars().addAll(Arrays.asList(fpars));
		result.setBody(body);
		return result;
	}

	public static FormalParameter _Fpar() {
		return _Fpar(null, false, false);
	}

	public static FormalParameter _Fpar(String name) {
		return _Fpar(name, false, false);
	}

	public static FormalParameter _Fpar(String name, boolean variadic) {
		return _Fpar(name, variadic, false);
	}

	public static FormalParameter _Fpar(String name, boolean variadic, boolean isSpecFpar) {
		FormalParameter result = N4JSFactory.eINSTANCE.createFormalParameter();
		result.setName(name);
		result.setVariadic(variadic);
		if (isSpecFpar) {
			result.getAnnotations().add(_Annotation(AnnotationDefinition.SPEC));
		}
		return result;
	}

	public static Annotation _Annotation(AnnotationDefinition annDef) {
		Annotation result = N4JSFactory.eINSTANCE.createAnnotation();
		result.setName(annDef.name);
		return result;
	}

	public static AnnotationList _AnnotationList(List<AnnotationDefinition> annDef) {
		AnnotationList result = N4JSFactory.eINSTANCE.createAnnotationList();
		if (annDef != null)
			result.getAnnotations().addAll(map(annDef, ad -> _Annotation(ad)));
		return result;
	}

	public static PropertyAssignmentAnnotationList _PropertyAssignmentAnnotationList(Annotation[] annotations) {
		PropertyAssignmentAnnotationList result = N4JSFactory.eINSTANCE.createPropertyAssignmentAnnotationList();
		result.getAnnotations().addAll(Arrays.asList(annotations));
		return result;
	}

	public static Block _Block(Statement... statements) {
		Block result = N4JSFactory.eINSTANCE.createBlock();
		result.getStatements().addAll(toList(filterNull(Arrays.asList(statements))));
		return result;
	}

	public static ParenExpression _Parenthesis(Expression expr) {
		ParenExpression result = N4JSFactory.eINSTANCE.createParenExpression();
		result.setExpression(expr);
		return result;
	}

	public static ParameterizedCallExpression _ParameterizedCallExpression(Expression expr) {
		ParameterizedCallExpression result = N4JSFactory.eINSTANCE.createParameterizedCallExpression();
		result.setTarget(expr);
		return result;
	}

	public static NullLiteral _NULL() {
		return N4JSFactory.eINSTANCE.createNullLiteral();
	}

	public static BooleanLiteral _TRUE() {
		return _BooleanLiteral(true);
	}

	public static BooleanLiteral _FALSE() {
		return _BooleanLiteral(false);
	}

	public static BooleanLiteral _BooleanLiteral(boolean value) {
		BooleanLiteral result = N4JSFactory.eINSTANCE.createBooleanLiteral();
		result.setTrue(value);
		return result;
	}

	public static NumericLiteral _NumericLiteral(int num) {
		return _NumericLiteral(BigDecimal.valueOf(num));
	}

	public static NumericLiteral _NumericLiteral(BigDecimal num) {
		NumericLiteral result = N4JSFactory.eINSTANCE.createNumericLiteral();
		result.setValue(num);
		return result;
	}

	public static StringLiteral _StringLiteral(String s, String rawValue) {
		StringLiteral result = _StringLiteral(s);
		result.setRawValue(rawValue);
		return result;
	}

	public static StringLiteral _StringLiteral(String s) {
		StringLiteral result = N4JSFactory.eINSTANCE.createStringLiteral();
		result.setValue(s);
		return result;
	}

	public static StringLiteral _StringLiteralForSTE(SymbolTableEntry symbolTableEntry) {
		return _StringLiteralForSTE(symbolTableEntry, false);
	}

	public static StringLiteral _StringLiteralForSTE(SymbolTableEntry symbolTableEntry, boolean useExportedName) {
		StringLiteralForSTE result = ImFactory.eINSTANCE.createStringLiteralForSTE();
		result.setEntry(symbolTableEntry);
		result.setUseExportedName(useExportedName);
		return result;
	}

	public static IntLiteral _IntLiteral(int i) {
		IntLiteral result = N4JSFactory.eINSTANCE.createIntLiteral();
		result.setValue(BigDecimal.valueOf(i));
		return result;
	}

	public static ThisLiteral _ThisLiteral() {
		ThisLiteral result = N4JSFactory.eINSTANCE.createThisLiteral();
		return result;
	}

	public static SuperLiteral _SuperLiteral() {
		SuperLiteral result = N4JSFactory.eINSTANCE.createSuperLiteral();
		return result;
	}

	public static EmptyStatement _emptyStatement() {
		return N4JSFactory.eINSTANCE.createEmptyStatement();
	}

	public static N4EnumDeclaration _EnumDeclaration(String name, List<N4EnumLiteral> literals) {
		N4EnumDeclaration result = N4JSFactory.eINSTANCE.createN4EnumDeclaration();
		result.setName(name);
		result.getLiterals().addAll(literals);
		return result;
	}

	public static N4EnumLiteral _EnumLiteral(String name, String value) {
		N4EnumLiteral result = N4JSFactory.eINSTANCE.createN4EnumLiteral();
		result.setName(name);
		result.setValueExpression((value != null) ? _StringLiteral(value) : null);
		return result;
	}

	public static N4ClassDeclaration _N4ClassDeclaration(String name) {
		N4ClassDeclaration result = N4JSFactory.eINSTANCE.createN4ClassDeclaration();
		result.setName(name);
		return result;
	}

	public static N4InterfaceDeclaration _N4InterfaceDeclaration(String name) {
		N4InterfaceDeclaration result = N4JSFactory.eINSTANCE.createN4InterfaceDeclaration();
		result.setName(name);
		return result;
	}

	public static N4TypeVariable _N4TypeVariable(String name, boolean covariant, boolean contravariant) {
		N4TypeVariable result = N4JSFactory.eINSTANCE.createN4TypeVariable();
		result.setName(name);
		result.setDeclaredCovariant(covariant);
		result.setDeclaredContravariant(contravariant);
		return result;
	}

	public static LiteralOrComputedPropertyName _LiteralOrComputedPropertyName(String name) {
		LiteralOrComputedPropertyName result = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
		result.setKind(PropertyNameKind.STRING);
		result.setLiteralName(name);
		return result;
	}

	/**
	 * @param computedName
	 *            the string representation of the computed property name. This should be the value that is usually
	 *            computed and set by {@link CompileTimeExpressionProcessor} and {@link ComputedNameProcessor}.
	 */
	public static LiteralOrComputedPropertyName _LiteralOrComputedPropertyName(Expression nameExpr,
			String computedName) {
		LiteralOrComputedPropertyName result = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
		result.setKind(PropertyNameKind.COMPUTED);
		result.setExpression(nameExpr);
		result.setComputedName(computedName);
		return result;
	}

	// ############################################################################################
	// IM.xcore

	public static <T extends TypeRef> TypeReferenceNode_IM<T> _TypeReferenceNode(TranspilerState state,
			TypeRef typeRef) {
		TypeReferenceNode_IM<T> result = ImFactory.eINSTANCE.createTypeReferenceNode_IM();
		if (typeRef != null) {
			state.info.setOriginalProcessedTypeRef_internal(result, TypeUtils.copyIfContained(typeRef));
		}
		return result;
	}

	public static IdentifierRef_IM _IdentRef(SymbolTableEntry symbolTableEntry) {
		if (symbolTableEntry == null) {
			throw new IllegalArgumentException("when creating an IdentifierRef_IM: symbol table entry may not be null");
		}
		IdentifierRef_IM result = ImFactory.eINSTANCE.createIdentifierRef_IM();
		result.setRewiredTarget(symbolTableEntry);
		return result;
	}

	public static SymbolTableEntry _SymbolTableEntry(@SuppressWarnings("unused") String name) {
		throw new UnsupportedOperationException(
				"do not manually create symbol table entries; use methods #createSymbolTableEntry() or #getSymbolTableEntry() instead");
	}

	public static ExpressionStatement _SnippetAsStmnt(String code) {
		return _ExprStmnt(_Snippet(code));
	}

	public static Snippet _Snippet(String codeToEmit) {
		Snippet result = ImFactory.eINSTANCE.createSnippet();
		result.setCodeToEmit(codeToEmit);
		return result;
	}
}
