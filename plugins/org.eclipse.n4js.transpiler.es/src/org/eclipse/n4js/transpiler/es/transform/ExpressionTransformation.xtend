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
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.AwaitExpression
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.CoalesceExpression
import org.eclipse.n4js.n4JS.EqualityOperator
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionWithTarget
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.PromisifyExpression
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.utils.PromisifyHelper
import org.eclipse.n4js.utils.ResourceNameComputer

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Some expressions need special handling, this is done in this transformation.
 */
class ExpressionTransformation extends Transformation {

	/**
	 * NOTE: we can use same temporary variable for optional chaining and the coalescing operator.
	 */
	private static final String CHAINING_COALESCING_TEMP_VAR_NAME = "$opt";

	@Inject private ResourceNameComputer resourceNameComputer;
	@Inject private PromisifyHelper promisifyHelper;

	private TGetter n4Object_n4type;
	private TGetter n4NamedElement_name;
	private TGetter n4Element_origin;
	private TGetter n4Type_fqn;

	override assertPreConditions() {
		// true
	}

	override assertPostConditions() {
		// true
	}

	override analyze() {
		n4Object_n4type = state.G.n4ObjectType.findOwnedMember(N4JSLanguageConstants.N4TYPE_NAME, false, true) as TGetter;
		n4NamedElement_name = state.G.n4NamedElementType.findOwnedMember("name", false, false) as TGetter;
		n4Element_origin = state.G.n4ElementType.findOwnedMember("origin", false, false) as TGetter;
		n4Type_fqn = state.G.n4TypeType.findOwnedMember("fqn", false, false) as TGetter;
		if (n4Object_n4type === null
			|| n4NamedElement_name === null
			|| n4Element_origin === null
			|| n4Type_fqn === null) {
			throw new IllegalStateException("could not find required members of built-in types");
		}
	}

	override transform() {
		collectNodes(state.im, Expression, true)
			.reverse // transforming expressions in bottom-up order (it's more natural and simplifies some nesting cases)
			.forEach[transformExpression];
	}

	def private dispatch void transformExpression(Expression expr) {
		// default case -> do nothing
	}

	def private dispatch void transformExpression(CastExpression castExpr) {
		replace(castExpr, castExpr.expression); // simply remove the cast
	}

	def private dispatch void transformExpression(CoalesceExpression coalExpr) {
		transformCoalesceExpression(coalExpr);
	}

	def private dispatch void transformExpression(ExpressionWithTarget exprWithTarget) {
		if (exprWithTarget instanceof ParameterizedPropertyAccessExpression_IM) {
			if (transformTrivialUsageOfReflection(exprWithTarget)) {
				return;
			}
		}
		transformOptionalChaining(exprWithTarget);
	}

	/**
	 * <b>IMPORTANT:</b>
	 * here we only do some special handling for the auto-promisify case within an AwaitExpression; the main
	 * handling of the AwaitExpression itself is done in a later transformation {@code BlockTransformation.transformBlockAsync(Block)}!!!
	 * <p>
	 * Changes
	 * <pre>await cls.meth(a, b)</pre>
	 * to
	 * <pre>await $n4promisifyMethod(cls, 'meth', [a, b])</pre>
	 * OR
	 * <pre>await fun(a, b)</pre>
	 * to
	 * <pre>await $n4promisifyFunction(fun, [a, b])</pre>
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

	/**
	 * Replaces the following trivial uses of the reflection APIs by the resulting value (i.e. a string literal):
	 * <pre>
	 * MyClass.n4type.name
	 * MyClass.n4type.origin
	 * MyClass.n4type.fqn
	 * </pre>
	 * Thus, reflection will not actually be used in the output code in the above cases.
	 */
	def private boolean transformTrivialUsageOfReflection(ParameterizedPropertyAccessExpression_IM propAccessExpr) {
		val property = propAccessExpr.originalTargetOfRewiredTarget;
		if (property === n4NamedElement_name
			|| property === n4Element_origin
			|| property === n4Type_fqn) {
			val target = propAccessExpr.target;
			if (target instanceof ParameterizedPropertyAccessExpression_IM) {
				val propertyOfTarget = target.originalTargetOfRewiredTarget;
				if (propertyOfTarget === n4Object_n4type) {
					val targetOfTarget = target.target;
					if (targetOfTarget instanceof IdentifierRef_IM) {
						val id = targetOfTarget.originalTargetOfRewiredTarget;
						if (id instanceof TClass) {
							val value = switch(property) {
								case n4NamedElement_name:
									id.name
								case n4Element_origin:
									resourceNameComputer.generateProjectDescriptor(id.eResource.URI)
								case n4Type_fqn:
									// avoid optimizing this case for built-in types
									// (we cannot know for sure the value of the 'fqn' property set in the .js files)
									if (!N4Scheme.isFromResourceWithN4Scheme(id)) {
										resourceNameComputer.getFullyQualifiedTypeName(id)
									}
								default:
									throw new IllegalStateException() // should not happen (see above)
							}
							if (value !== null) {
								replace(propAccessExpr, _StringLiteral(value));
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Converts
	 * <pre>a ?? b</pre>
	 * to
	 * <pre>($temp = a) != null ? $temp : b</pre>
	 */
	def private void transformCoalesceExpression(CoalesceExpression coalExpr) {
		// convert
		val tempVarSTE = addOrGetTemporaryVariable(CHAINING_COALESCING_TEMP_VAR_NAME, coalExpr);
		val replacement = _ConditionalExpr(
			_EqualityExpr(
				_Parenthesis(
					_AssignmentExpr(
						_IdentRef(tempVarSTE),
						coalExpr.expression
					)
				),
				EqualityOperator.NEQ,
				_NULL
			),
			_IdentRef(tempVarSTE),
			coalExpr.defaultExpression
		);
		if (coalExpr.eContainer instanceof Expression) {
			replace(coalExpr, _Parenthesis(replacement));
		} else {
			replace(coalExpr, replacement);
		}
	}

	/**
	 * Converts
	 * <pre>a?.b.c</pre>
	 * to
	 * <pre>($temp = a) == null ? void 0 : $temp.b.c</pre>
	 */
	def private boolean transformOptionalChaining(ExpressionWithTarget exprWithTarget) {
		if (!exprWithTarget.optionalChaining) {
			return false;
		}
		exprWithTarget.optionalChaining = false;

		val target = exprWithTarget.target;
		val tempVarSTE = addOrGetTemporaryVariable(CHAINING_COALESCING_TEMP_VAR_NAME, exprWithTarget);

		replace(target, _IdentRef(tempVarSTE));

		val toBeReplaced = getLongShortCircuitingDesitnation(exprWithTarget);
		val replacement = _ConditionalExpr(
			_EqualityExpr(
				_Parenthesis(
					_AssignmentExpr(
						_IdentRef(tempVarSTE),
						target
					)
				),
				EqualityOperator.EQ,
				_NULL
			),
			if (toBeReplaced instanceof UnaryExpression) _TRUE else _Void0,
			null // will be set below
		);
		if (toBeReplaced.eContainer instanceof Expression) {
			replace(toBeReplaced, _Parenthesis(replacement));
		} else {
			replace(toBeReplaced, replacement);
		}
		replacement.falseExpression = toBeReplaced;

		if (exprWithTarget instanceof ParameterizedCallExpression
			&& target instanceof ParameterizedPropertyAccessExpression) {

			preserveCallContext(
				exprWithTarget as ParameterizedCallExpression,
				target as ParameterizedPropertyAccessExpression);
		}

		return true;
	}

	def private Expression getLongShortCircuitingDesitnation(ExpressionWithTarget expr) {
		var dest = expr as Expression;
		var destParent = dest.eContainer;
		while (destParent instanceof ExpressionWithTarget) {
			dest = destParent;
			destParent = dest.eContainer;
		}
		if (destParent instanceof UnaryExpression) {
			if (destParent.op === UnaryOperator.DELETE && destParent.expression === dest) {
				// go up one more level to turn "delete a?.b"
				// into "($temp = a) == null ? true : delete a.b"
				dest = destParent;
			}
		}
		return dest;
	}

	/**
	 * When method {@link #transformOptionalChaining(ExpressionWithTarget)} is invoked with something like
	 * <pre>foo.bar1.bar2.bar3.bar4?.(5)</pre>
	 * it will convert that to
	 * <pre>($opt = foo.bar1.bar2.bar3.bar4) == null ? void 0 : $opt(5)</pre>
	 * However, that code would lose the call context of the original code.
	 * <p>
	 * Therefore, {@link #transformOptionalChaining(ExpressionWithTarget)} will call this method to further convert to:
	 * <pre>($opt = ($optR = foo.bar1.bar2.bar3).bar4) == null ? void 0 : $opt.call($optR, 5)</pre>
	 */
	def private void preserveCallContext(ParameterizedCallExpression exprWithTarget, ParameterizedPropertyAccessExpression target) {
		var Expression callContextExpr;

		// step 1: convert
		// $opt = foo.bar1.bar2.bar3.bar4
		// to
		// $opt = ($optR = foo.bar1.bar2.bar3).bar4
		val receiver = target.target;
		if (receiver instanceof IdentifierRef_IM && !isIdentifierRefToChainingTempVar(receiver)) {
			// special case: receiver is a plain IdentifierRef, which can be evaluated more than once without side effects
			// -> no need for introducing a second temporary variable
			callContextExpr = _IdentRef((receiver as IdentifierRef_IM).id_IM);
		} else {
			val tempVarReceiverSTE = addOrGetTemporaryVariable(CHAINING_COALESCING_TEMP_VAR_NAME + "R", exprWithTarget);
			val receiverAssignment = _AssignmentExpr(
				_IdentRef(tempVarReceiverSTE),
				null // will be set below
			);
			replace(receiver, _Parenthesis(receiverAssignment));
			receiverAssignment.rhs = receiver;
			callContextExpr = _IdentRef(tempVarReceiverSTE);
		}

		// step 2: convert
		// $opt(5)
		// to
		// $opt.call($optR, 5)
		exprWithTarget.target = _PropertyAccessExpr(
			exprWithTarget.target,
			steFor_Function_call
		);
		exprWithTarget.arguments.add(0, _Argument(callContextExpr));
	}

	def private boolean isIdentifierRefToChainingTempVar(Expression expr) {
		if (expr instanceof IdentifierRef_IM) {
			val name = expr.id_IM?.name;
			return name !== null && name.startsWith(CHAINING_COALESCING_TEMP_VAR_NAME);
		}
		return false;
	}
}
