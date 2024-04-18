/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.compileTime;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.symbolObjectType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.compileTime.CompileTimeValue.ValueBoolean;
import org.eclipse.n4js.compileTime.CompileTimeValue.ValueInvalid;
import org.eclipse.n4js.compileTime.CompileTimeValue.ValueNumber;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.postprocessing.ASTMetaInfoCache;
import org.eclipse.n4js.postprocessing.ASTMetaInfoUtils;
import org.eclipse.n4js.postprocessing.ASTProcessor;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TConstableElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;
import org.eclipse.n4js.validation.validators.N4JSExpressionValidator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.inject.Inject;

/**
 * Helper class to evaluate compile-time expressions.
 * <p>
 * <u>IMPORTANT IMPLEMENTATION NOTES:</u>
 * <ul>
 * <li>It is a design decision<sup>1</sup> to handle compile-time evaluation and computed property names as a separate,
 * up-front phase during post-processing before the main AST traversal begins (for details about the phases of
 * post-processing, see method {@link ASTProcessor#processAST(RuleEnvironment, Script, ASTMetaInfoCache)}).
 * <li>To achieve this, we must <b>avoid using type information during compile-time evaluation</b>, because typing is
 * part of main AST traversal, so typing an AST node would inevitably start the main AST traversal.
 * <li>the only place where this limitation becomes tricky is the evaluation of property access expressions, see
 * {@link #eval(RuleEnvironment, ParameterizedPropertyAccessExpression, RecursionGuard)}.
 * </ul>
 *
 * <sup>1</sup> main rationale for this decision was to keep the handling of computed property names from complicating
 * the scoping and main AST traversal. Without resolving computed property names beforehand, all the code in scoping,
 * AST traversal, type system, and helper classes such as {@link ContainerTypesHelper} would have to cope with
 * unresolved property names, i.e. {@code #getName()} on a property or member would return <code>null</code> or trigger
 * some potentially complex computation in the background that might confuse AST traversal.
 */
public class CompileTimeEvaluator {

	@Inject
	private N4JSElementKeywordProvider keywordProvider;

	/**
	 * <b> IMPORTANT: CLIENT CODE SHOULD NOT CALL THIS METHOD!<br>
	 * Instead, read compile-time values from the cache using method
	 * {@link ASTMetaInfoUtils#getCompileTimeValue(Expression)}.<br>
	 * If the evaluation result of the expression you are interested in is not being cached, add your use case to method
	 * {@link N4JSLanguageUtils#isProcessedAsCompileTimeExpression(Expression)}. Only expressions for which this method
	 * returns <code>true</code> will be evaluated and cached during post-processing. </b>
	 * <p>
	 * Computes and returns the value of the given expression as a {@link CompileTimeValue}. If the given expression is
	 * not a valid compile-time expression, the returned value will be {@link CompileTimeValue#isValid() invalid}. Never
	 * returns <code>null</code>.
	 */
	public CompileTimeValue evaluateCompileTimeExpression(RuleEnvironment G, Expression expr) {
		return eval(G, expr, new RecursionGuard<>());
	}

	// ---------------------------------------------------------------------------------------------------------------

	// catch-all case for expression not handled by any of the more specific dispatch methods
	private CompileTimeValue eval(RuleEnvironment G, Expression expr, RecursionGuard<EObject> guard) {
		if (expr instanceof ParenExpression) {
			Expression expression = ((ParenExpression) expr).getExpression();
			if (expression == null) {
				return CompileTimeValue.error();
			}
			return eval(G, expression, guard);
		}
		if (expr instanceof NullLiteral) {
			return CompileTimeValue.NULL;
		}
		if (expr instanceof BooleanLiteral) {
			return CompileTimeValue.of(((BooleanLiteral) expr).isTrue());
		}
		if (expr instanceof NumericLiteral) {
			return CompileTimeValue.of(((NumericLiteral) expr).getValue());
		}
		if (expr instanceof StringLiteral) {
			return CompileTimeValue.of(((StringLiteral) expr).getValue());
		}
		if (expr instanceof TemplateSegment) {
			return CompileTimeValue.of(((TemplateSegment) expr).getValue());
		}
		if (expr instanceof TemplateLiteral) {
			return eval(G, (TemplateLiteral) expr, guard);
		}
		if (expr instanceof UnaryExpression) {
			return eval(G, (UnaryExpression) expr, guard);
		}
		if (expr instanceof AdditiveExpression) {
			return eval(G, (AdditiveExpression) expr, guard);
		}
		if (expr instanceof MultiplicativeExpression) {
			return eval(G, (MultiplicativeExpression) expr, guard);
		}
		if (expr instanceof BinaryLogicalExpression) {
			return eval(G, (BinaryLogicalExpression) expr, guard);
		}
		if (expr instanceof ConditionalExpression) {
			return eval(G, (ConditionalExpression) expr, guard);
		}
		if (expr instanceof IdentifierRef) {
			return eval(G, (IdentifierRef) expr, guard);
		}
		if (expr instanceof ParameterizedPropertyAccessExpression) {
			return eval(G, (ParameterizedPropertyAccessExpression) expr, guard);
		}

		return CompileTimeValue.error(keywordProvider.keywordWithIndefiniteArticle(expr)
				+ " is never a compile-time expression", expr);
	}

	private CompileTimeValue eval(RuleEnvironment G, TemplateLiteral expr, RecursionGuard<EObject> guard) {
		StringBuilder buff = new StringBuilder();
		List<CompileTimeValue> invalidValues = new ArrayList<>();
		for (Expression seg : expr.getSegments()) {
			CompileTimeValue segValue = eval(G, seg, guard);
			if (segValue.isValid()) {
				buff.append(segValue.toString());
			} else {
				invalidValues.add(segValue);
			}
		}
		if (!invalidValues.isEmpty()) {
			return CompileTimeValue.combineErrors(invalidValues.toArray(new CompileTimeValue[0]));
		}
		return CompileTimeValue.of(buff.toString());
	}

	private CompileTimeValue eval(RuleEnvironment G, UnaryExpression expr, RecursionGuard<EObject> guard) {
		CompileTimeValue value = (expr.getExpression() != null) ? eval(G, expr.getExpression(), guard) : null;
		switch (expr.getOp()) {
		case NOT:
			return CompileTimeValue.invert(value, expr.getExpression());
		case POS: {
			ValueInvalid tmpV = CompileTimeValue.requireValueType(value, ValueNumber.class, "operand must be a number",
					expr.getExpression());
			return tmpV != null ? tmpV : value;
		}
		case NEG:
			return CompileTimeValue.negate(value, expr.getExpression());
		case VOID:
			return CompileTimeValue.UNDEFINED;
		default:
			return CompileTimeValue.error("invalid operator: " + expr.getOp(), expr);
		}
	}

	private CompileTimeValue eval(RuleEnvironment G, AdditiveExpression expr, RecursionGuard<EObject> guard) {
		Expression lhs = expr.getLhs();
		Expression rhs = expr.getRhs();
		CompileTimeValue leftValue = (lhs != null) ? eval(G, lhs, guard) : null;
		CompileTimeValue rightValue = (rhs != null) ? eval(G, rhs, guard) : null;
		switch (expr.getOp()) {
		case ADD:
			return CompileTimeValue.add(leftValue, rightValue, expr);
		case SUB:
			return CompileTimeValue.subtract(leftValue, rightValue, lhs, rhs);
		default:
			return CompileTimeValue.error("invalid operator: " + expr.getOp(), expr);
		}
	}

	private CompileTimeValue eval(RuleEnvironment G, MultiplicativeExpression expr, RecursionGuard<EObject> guard) {
		Expression lhs = expr.getLhs();
		Expression rhs = expr.getRhs();
		CompileTimeValue leftValue = (lhs != null) ? eval(G, lhs, guard) : null;
		CompileTimeValue rightValue = (rhs != null) ? eval(G, rhs, guard) : null;
		switch (expr.getOp()) {
		case TIMES:
			return CompileTimeValue.multiply(leftValue, rightValue, lhs, rhs);
		case DIV:
			return CompileTimeValue.divide(leftValue, rightValue, lhs, rhs);
		case MOD:
			return CompileTimeValue.remainder(leftValue, rightValue, lhs, rhs);
		default:
			return CompileTimeValue.error("invalid operator: " + expr.getOp(), expr);
		}
	}

	private CompileTimeValue eval(RuleEnvironment G, BinaryLogicalExpression expr, RecursionGuard<EObject> guard) {
		Expression lhs = expr.getLhs();
		Expression rhs = expr.getRhs();
		CompileTimeValue leftValue = (lhs != null) ? eval(G, lhs, guard) : null;
		CompileTimeValue rightValue = (rhs != null) ? eval(G, rhs, guard) : null;
		switch (expr.getOp()) {
		case AND:
			return CompileTimeValue.and(leftValue, rightValue, lhs, rhs);
		case OR:
			return CompileTimeValue.or(leftValue, rightValue, lhs, rhs);
		default:
			return CompileTimeValue.error("invalid operator: " + expr.getOp(), expr);
		}
	}

	private CompileTimeValue eval(RuleEnvironment G, ConditionalExpression expr, RecursionGuard<EObject> guard) {
		Expression condition = expr.getExpression();
		Expression trueExpr = expr.getTrueExpression();
		Expression falseExpr = expr.getFalseExpression();
		CompileTimeValue conditionValue = (condition != null) ? eval(G, condition, guard) : null;
		CompileTimeValue trueValue = (trueExpr != null) ? eval(G, trueExpr, guard) : null;
		CompileTimeValue falseValue = (falseExpr != null) ? eval(G, falseExpr, guard) : null;
		ValueInvalid requireValueType = CompileTimeValue.requireValueType(conditionValue, ValueBoolean.class,
				"condition must be a boolean",
				expr.getExpression());
		ValueInvalid error = CompileTimeValue.combineErrors(requireValueType, trueValue, falseValue);
		if (error != null) {
			return error;
		}
		return (conditionValue != null && ((ValueBoolean) conditionValue).getValue()) ? trueValue : falseValue;
	}

	private CompileTimeValue eval(RuleEnvironment G, IdentifierRef expr, RecursionGuard<EObject> guard) {
		if (N4JSLanguageUtils.isUndefinedLiteral(G, expr)) {
			return CompileTimeValue.UNDEFINED;
		}
		IdentifiableElement id = expr.getId();
		// ^^ triggers scoping; this is unproblematic, because the scoping of IdentifierRefs does not
		// require type information and will thus not interfere with our goal of handling compile-time expressions and
		// computed property names as an up-front preparatory step before main AST traversal.
		if (id != null && !id.eIsProxy()) {
			return obtainValueIfConstFieldOrVariable(G, id, expr, guard);
		}
		return CompileTimeValue.error();
	}

	/**
	 * Handles compile-time evaluation of property access expressions.
	 * <p>
	 * <u>IMPORTANT IMPLEMENTATION NOTES:</u>
	 * <ul>
	 * <li>We must not make use of type information during compile-time evaluation (see {@link CompileTimeEvaluator} for
	 * details why this rule exists).
	 * <li>Since scoping of property access requires type information, we cannot use this form of scoping.
	 * <li>Since this scoping would be triggered when invoking {@code #getProperty()} on the given property access
	 * expression, we cannot make use of that property in this method.
	 * <li>APPROACH: avoid using (ordinary) scoping but instead implement custom member lookup for the very limited
	 * cases supported by compile-time expressions.
	 * </ul>
	 * YES, this approach introduces an unfortunate duplication of logic, but greatly simplifies other parts of the
	 * system, i.e. (ordinary) scoping, AST traversal, type system.
	 */
	private CompileTimeValue eval(RuleEnvironment G, ParameterizedPropertyAccessExpression expr,
			RecursionGuard<EObject> guard) {
		Expression targetExpr = expr.getTarget();
		String propName = expr.getPropertyAsText(); // IMPORTANT: don't invoke expr.getProperty()!!
		EObject targetElem = null;
		if (targetExpr instanceof IdentifierRef) {
			if (targetExpr.eIsProxy()) {
				return CompileTimeValue.error("Expression is a proxy '" + propName + "'", expr);
			}
			targetElem = ((IdentifierRef) targetExpr).getId();
		}
		EObject sym = symbolObjectType(G);
		if (targetElem == sym) {
			// A) Is 'expr' an access to a built-in symbol, e.g. Symbol.iterator?
			// IMPORTANT: pass in 'false', to disallow proxy resolution (which would trigger scoping, type inference,
			// etc.)
			TMember memberInSym = N4JSLanguageUtils.getAccessedBuiltInSymbol(G, expr, false);
			if (memberInSym != null) {
				// yes, it is!
				return CompileTimeValue.of(memberInSym);
			} else {
				return CompileTimeValue.error("Unknown Symbol property '" + propName + "'", expr);
			}
		} else if (targetElem instanceof TEnum) {
			// B) Is 'expr' an access to the literal of a @NumberBased or @StringBased enum?
			EnumKind enumKind = N4JSLanguageUtils.getEnumKind((TEnum) targetElem);
			if (enumKind != EnumKind.Normal) {
				// custom scoping logic!
				TEnumLiteral litInEnum = IterableExtensions.findFirst(((TEnum) targetElem).getLiterals(),
						l -> Objects.equals(l.getName(), propName));
				if (litInEnum != null) {
					// yes, it is!
					switch (enumKind) {
					case Normal:
						throw new IllegalStateException("cannot happen");
					case NumberBased:
						return CompileTimeValue.of(litInEnum.getValueNumber());
					case StringBased:
						return CompileTimeValue.of(litInEnum.getValueString());
					}
				}
			}
		} else if (targetElem instanceof TClassifier) {
			// C) Is 'expr' an access to a const field initialized by a compile-time expression?
			// custom scoping logic!
			TMember member = findFirst(filterNull(((TClassifier) targetElem).getOwnedMembers()),
					m -> Objects.equals(m.getName(), propName) && m.isReadable() && m.isStatic());
			// IMPORTANT: don't use "targetElem.findOwnedMember(memberName, false, true)" in previous line, because
			// #findOwnedMember() will create and cache a MemberByNameAndAccessMap, which will be incomplete if the
			// TClassifier contains members with unresolved computed property names!
			if (member instanceof TField && !((TField) member).isHasComputedName()) {
				// yes, it is!
				return obtainValueIfConstFieldOrVariable(G, member, expr, guard);
			} else {
				// we get here in two cases:
				//
				// 1) member not found
				// -> there are a number of possible reasons:
				// 1.a) member has a computed property name which was not yet evaluated,
				// 1.b) member is inherited, consumed, polyfilled, etc.,
				// 1.c) member does not exist at all.
				// At this point, i.e. before computed names are processed, we cannot distinguish between these
				// cases. So we create a dummy error here that will be improved later.
				//
				// 2) member was found but it has a (resolved) computed property name (since processing of computed
				// property names for the current resource has not started yet, this happens only if the member is
				// located in another file an its full type information, including its name, was found in the index)
				// -> for consistency with 1.a above, we have to raise an error also in this case
				return CompileTimeValue.error(new UnresolvedPropertyAccessError(expr));
			}
		}
		// D) all other cases:
		if (targetElem != sym && !(targetElem instanceof TClassifier || targetElem instanceof TEnum)) {
			return CompileTimeValue.error(
					"target of a property access must be a direct reference to a class, interface, or enum", expr,
					N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET);
		}
		return CompileTimeValue.error(
				"property access must point to const fields, literals of @NumberBased/@StringBased enums, or built-in symbols",
				expr);
	}

	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * Iff the given element is a const field or variable with a valid compile-time expression as initializer, then this
	 * method returns its compile-time value; otherwise, an invalid compile-time value with an appropriate error message
	 * is returned. Never returns <code>null</code>.
	 * <p>
	 * This method only handles infinite recursion; main logic in
	 * {@link #obtainValueIfConstFieldOrVariableUnguarded(RuleEnvironment, IdentifiableElement, EObject, RecursionGuard)}.
	 */
	private CompileTimeValue obtainValueIfConstFieldOrVariable(RuleEnvironment G, IdentifiableElement targetElem,
			EObject astNodeForErrorMessage, RecursionGuard<EObject> guard) {

		if (guard.tryNext(targetElem)) {
			try {
				return obtainValueIfConstFieldOrVariableUnguarded(G, targetElem, astNodeForErrorMessage, guard);
			} finally {
				guard.done(targetElem);
			}
		} else {
			return CompileTimeValue.error("cyclic definition of compile-time expression", astNodeForErrorMessage);
		}
	}

	private CompileTimeValue obtainValueIfConstFieldOrVariableUnguarded(RuleEnvironment G,
			IdentifiableElement targetElem, EObject astNodeForErrorMessage, RecursionGuard<EObject> guard) {

		boolean targetElemIsConst = false;
		if (targetElem instanceof TConstableElement) {
			targetElemIsConst = ((TConstableElement) targetElem).isConst();
		}
		if (targetElem instanceof N4FieldDeclaration) {
			targetElemIsConst = ((N4FieldDeclaration) targetElem).isConst();
		}
		if (targetElem instanceof VariableDeclaration) {
			targetElemIsConst = ((VariableDeclaration) targetElem).isConst();
		}
		if (!targetElemIsConst) {
			return CompileTimeValue.error(
					keywordProvider.keyword(targetElem) + " " + targetElem.getName() + " is not const",
					astNodeForErrorMessage);
		}

		CompileTimeValue valueOfTargetElem = obtainCompileTimeValueOfTargetElement(G,
				astNodeForErrorMessage.eResource(), targetElem, guard);
		if (valueOfTargetElem != null) {
			if (valueOfTargetElem instanceof ValueInvalid) {
				String baseMsg = keywordProvider.keyword(targetElem) + " " + targetElem.getName() +
						" is const but does not have a compile-time expression as initializer";
				String msg = combineErrorMessageWithNestedErrors(baseMsg,
						((ValueInvalid) valueOfTargetElem).getErrors().toArray(new CompileTimeEvaluationError[0]));
				EReference feature = null;
				if (astNodeForErrorMessage instanceof ParameterizedPropertyAccessExpression) {
					feature = N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property();
				}
				return CompileTimeValue.error(msg, astNodeForErrorMessage, feature);
			}
			return valueOfTargetElem;
		}
		return CompileTimeValue.error(
				"only references to const variables with a compile-time expression as initializer are allowed",
				astNodeForErrorMessage);
	}

	private CompileTimeValue obtainCompileTimeValueOfTargetElement(RuleEnvironment G, Resource currentResource,
			IdentifiableElement targetElem, RecursionGuard<EObject> guard) {

		if (targetElem.eResource() == currentResource || hasLoadedASTElement(targetElem)) {
			// 'targetElem' is in same resource OR is in a different resource that already has a fully-loaded AST
			// -> compute value from the initializer expression of 'targetElem'
			EObject astNodeOfTargetElem = (targetElem instanceof SyntaxRelatedTElement)
					? ((SyntaxRelatedTElement) targetElem).getAstElement()
					// NOTE: this will never trigger demand-loading of an AST, because above we
					// ensured that we are still in 'currentResource' OR method #hasLoadedASTElement() has returned true
					: targetElem // here we simply assume that elem is already an AST node
			;
			Expression expressionOfTargetElem = null;
			if (astNodeOfTargetElem instanceof N4FieldDeclaration) {
				expressionOfTargetElem = ((N4FieldDeclaration) astNodeOfTargetElem).getExpression();
			}
			if (astNodeOfTargetElem instanceof VariableDeclaration) {
				expressionOfTargetElem = ((VariableDeclaration) astNodeOfTargetElem).getExpression();
			}
			if (expressionOfTargetElem != null) {
				return eval(G, expressionOfTargetElem, guard);
			}
		} else {
			// 'targetElem' is in another resource with an AST proxy
			// -> read value from TModule to avoid demand-loading of AST
			if (targetElem instanceof TConstableElement) {
				return CompileTimeValue.deserialize(((TConstableElement) targetElem).getCompileTimeValue());
			}
		}
		return null; // no value found
	}

	/**
	 * Tells if given element has an AST element in an already loaded AST (i.e. it is safe to invoke method
	 * {@code #getASTElement()} without triggering a demand-load of the AST).
	 */
	private static boolean hasLoadedASTElement(IdentifiableElement elem) {
		EObject astElemNonResolved = null;
		if (elem instanceof SyntaxRelatedTElement) {
			astElemNonResolved = (EObject) elem.eGet(TypesPackage.eINSTANCE.getSyntaxRelatedTElement_AstElement(),
					false);
		}
		return astElemNonResolved != null && !astElemNonResolved.eIsProxy();
	}

	private static String combineErrorMessageWithNestedErrors(String mainMessage,
			CompileTimeEvaluationError... nestedErrors) {

		if (nestedErrors.length == 0) {
			return mainMessage;
		} else if (nestedErrors.length == 1) {
			return mainMessage + ": " + nestedErrors[0].getMessageWithLocation();
		} else {
			return mainMessage + ":\n- "
					+ Strings.join("\n- ", map(List.of(nestedErrors), e -> e.getMessageWithLocation()));
		}
	}

	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * Special kind of {@link CompileTimeEvaluationError} used to denote a particular case in which the
	 * {@link CompileTimeEvaluator} cannot come up with the correct error message and thus delegates finding a proper
	 * message to the validation, i.e. to class {@link N4JSExpressionValidator}.
	 */
	public static final class UnresolvedPropertyAccessError extends CompileTimeEvaluationError {

		/***/
		public UnresolvedPropertyAccessError(ParameterizedPropertyAccessExpression astNode) {
			super("*** UnresolvedPropertyAccessError ***", astNode,
					N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property());
		}

		/***/
		public ParameterizedPropertyAccessExpression getAstNodeCasted() {
			return (ParameterizedPropertyAccessExpression) astNode;
		}
	}
}
