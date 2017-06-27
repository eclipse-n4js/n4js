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
import org.eclipse.n4js.n4JS.AdditiveOperator
import org.eclipse.n4js.n4JS.AwaitExpression
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ParameterizedAccess
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.PrimaryExpression
import org.eclipse.n4js.n4JS.PromisifyExpression
import org.eclipse.n4js.n4JS.RelationalExpression
import org.eclipse.n4js.n4JS.RelationalOperator
import org.eclipse.n4js.n4JS.TemplateLiteral
import org.eclipse.n4js.n4JS.TemplateSegment
import org.eclipse.n4js.naming.QualifiedNameComputer
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.utils.PromisifyHelper
import java.util.ArrayList

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Some expressions need special handling, this is done in this transformation.
 */
class ExpressionTransformation extends Transformation {

	@Inject private QualifiedNameComputer qualifiedNameComputer;
	@Inject private PromisifyHelper promisifyHelper;


	override assertPreConditions() {
		// true
	}

	override assertPostConditions() {
		// true
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, Expression, true).forEach[transformExpression];
	}

	def private dispatch void transformExpression(Expression relExpr) {
		// default case -> do nothing
	}

	def private dispatch void transformExpression(CastExpression castExpr) {
		replace(castExpr, castExpr.expression); // simply remove the cast
	}

	def private dispatch void transformExpression(RelationalExpression relExpr) {
		if(relExpr.op===RelationalOperator.INSTANCEOF) {
			val rhs = relExpr.rhs;
			val rhsType = if(rhs instanceof IdentifierRef_IM) {
				rhs.originalTargetOfRewiredTarget
			};

			val replacement = if(rhsType instanceof TInterface) {
				// case 1: direct reference to an interface on RHS -> can directly use own $implements function
				val $implementsSTE = steFor_$implements;
				val fqn = qualifiedNameComputer.getFullyQualifiedTypeName_WITH_LEGACY_SUPPORT(rhsType)
				_CallExpr(_IdentRef($implementsSTE), relExpr.lhs, _StringLiteral(fqn))
			} else if(rhsType instanceof TClass) {
				// case 2: direct reference to a class on RHS -> can use native Javascript 'instanceof' operator
				null // here 'null' means: nothing to transform
			} else {
				// case 3: all other cases have to be decided at runtime -> use own $instanceof function
				val $instanceofSTE = steFor_$instanceof;
				_CallExpr(_IdentRef($instanceofSTE), relExpr.lhs, relExpr.rhs)
			};

			if(replacement!==null) {
				replace(relExpr, replacement);
			}
		}
	}

	/**
	 * <b>IMPORTANT:</b>
	 * here we only do some special handling for the auto-promisify case within an AwaitExpression; the main
	 * handling of the AwaitExpression itself is done in a later transformation {@code BlockTransformation.transformBlockAsync(Block)}!!!
	 * <p>
	 * Changes
	 * <pre>await cls.meth(a, b)</pre>
	 * to
	 * <pre>$n4promisifyMethod(cls, 'meth', [a, b])</pre>
	 * OR
	 * <pre>await fun(a, b)</pre>
	 * to
	 * <pre>$n4promisifyFunction(fun, [a, b])</pre>
	 * assuming that method 'meth' and function 'fun' are annotated with <code>@Promisifiable</code>.
	 */
	def private dispatch void transformExpression(AwaitExpression awaitExpr) {
		val awaitExprOrig = state.tracer.getOriginalASTNodeOfSameType(awaitExpr, false);
		if(promisifyHelper.isAutoPromisify(awaitExprOrig)) {
			val callExpr = awaitExpr.expression as ParameterizedCallExpression; // cast is safe because isPromisifiableExpression() returned true
			val replacement = promisify(callExpr);
			replace(callExpr, replacement); // note: leaving awaitExpr in the IM; only replacing its contained expression!!
		}
	}

	/**
	 * Changes
	 * <pre>@Promisify cls.meth(a, b)</pre>
	 * to
	 * <pre>$n4promisifyMethod(cls, 'meth', [a, b])</pre>
	 * OR
	 * <pre>@Promisify fun(a, b)</pre>
	 * to
	 * <pre>$n4promisifyFunction(fun, [a, b])</pre>
	 */
	def private dispatch void transformExpression(PromisifyExpression promiExpr) {
		val callExpr = promiExpr.expression as ParameterizedCallExpression; // cast is safe because of validations
		val replacement = promisify(callExpr);
		replace(promiExpr, replacement);
	}

	def private ParameterizedCallExpression promisify(ParameterizedCallExpression callExpr) {
		val target = callExpr.target;
		val targetSTE = switch(target) {
			ParameterizedPropertyAccessExpression_IM: target.property_IM
			IdentifierRef_IM: target.id_IM
		};
		if(targetSTE instanceof SymbolTableEntryOriginal) {
			val originalTarget = targetSTE.originalTarget;
			if(originalTarget instanceof TFunction) { // could be a method
				val originalTargetTypeRef = TypeUtils.createTypeRef(originalTarget) as FunctionTypeRef;
				val returnTypeRef = promisifyHelper.extractPromisifiedReturnType(state.G, originalTargetTypeRef);
				val hasErrorValue = !TypeUtils.isUndefined(returnTypeRef.typeArgs.drop(1).head); // isUndefined() is null-safe
				val hasMoreThan1SuccessValue = state.G.isIterableN(returnTypeRef.typeArgs.head); // isIterableN() is null-safe
				if(target instanceof ParameterizedPropertyAccessExpression_IM && targetSTE.originalTarget instanceof TMethod) {
					// we have a method invocation, so we need to preserve the 'this' argument:

					// @Promisify cls.meth(a, b)
					// -->
					// $n4promisifyMethod(cls, 'meth', [a, b])
					return _CallExpr(
						_IdentRef(steFor_$n4promisifyMethod()),
						(target as ParameterizedPropertyAccessExpression_IM).target, // here we take the "cls" part of "cls.meth" as first argument
						_StringLiteralForSTE(targetSTE),
						_ArrLit(callExpr.arguments.map[_ArrayElement(spread, expression)]), // reuse arguments while preserving spread
						_BooleanLiteral(hasMoreThan1SuccessValue),
						_BooleanLiteral(!hasErrorValue)
					);
				} else {
					// in all other cases, we do not preserve the 'this' argument:

					// @Promisify fun(a, b)
					// -->
					// $n4promisifyFunction(fun, [a, b])
					return _CallExpr(
						_IdentRef(steFor_$n4promisifyFunction()),
						callExpr.target, // reuse target as first argument
						_ArrLit(callExpr.arguments.map[_ArrayElement(spread,expression)]), // reuse arguments while preserving spread
						_BooleanLiteral(hasMoreThan1SuccessValue),
						_BooleanLiteral(!hasErrorValue)
					);
				}
			}
		}
		// if anything goes awry, we just return callExpr as replacement, which means we simply remove the @Promisify
		return callExpr;
	}

	def private dispatch void transformExpression(TemplateLiteral template) {
		val replacement = switch(template.segments.size) {
			case 0:
				_StringLiteral("")
			case 1:
				template.segments.get(0)
			default:
				_Parenthesis(
					_AdditiveExpression(AdditiveOperator.ADD, template.segments.wrapIfRequired)
				)
		};
		replace(template, replacement);
	}
	def private dispatch void transformExpression(TemplateSegment segment) {
		replace(segment, _StringLiteral(segment.valueAsString, segment.rawValue.wrapAndQuote));
	}


	def private static Iterable<Expression> wrapIfRequired(Iterable<Expression> expressions) {
		val copy = new ArrayList(expressions.toList); // required due to possible concurrent modification (see below)
		return copy.map[expr|
			val boolean needParentheses = !(expr instanceof PrimaryExpression || expr instanceof ParameterizedAccess);
			if(needParentheses) {
				return _Parenthesis(expr) // this might change iterable 'expressions' (if an EMF containment reference was passed in)
			} else {
				return expr
			}
		];
	}

	/** put raw into double quote and escape all existing double-quotes {@code '"' -> '\"' } and newlines {@code '\n' -> '\\n'}. */
	def private static String wrapAndQuote(String raw){
		'"'+raw.replace('"','\\"').replace('\n','\\n')+'"'
	}
}
