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

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrayElement;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._BooleanLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteralForSTE;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isIterableN;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4ElementType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4NamedElementType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4ObjectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4TypeType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.ListExtensions.reverse;

import java.util.List;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.PromisifyHelper;
import org.eclipse.n4js.utils.ResourceNameComputer;

import com.google.inject.Inject;

/**
 * Some expressions need special handling, this is done in this transformation.
 */
public class ExpressionTransformation extends Transformation {

	@Inject
	private ResourceNameComputer resourceNameComputer;
	@Inject
	private PromisifyHelper promisifyHelper;

	private TGetter n4Object_n4type;
	private TGetter n4NamedElement_name;
	private TGetter n4Element_origin;
	private TGetter n4Type_fqn;

	@Override
	public void assertPreConditions() {
		// true
	}

	@Override
	public void assertPostConditions() {
		// true
	}

	@Override
	public void analyze() {
		n4Object_n4type = (TGetter) n4ObjectType(getState().G).findOwnedMember(N4JSLanguageConstants.N4TYPE_NAME, false,
				true);
		n4NamedElement_name = (TGetter) n4NamedElementType(getState().G).findOwnedMember("name", false, false);
		n4Element_origin = (TGetter) n4ElementType(getState().G).findOwnedMember("origin", false, false);
		n4Type_fqn = (TGetter) n4TypeType(getState().G).findOwnedMember("fqn", false, false);

		if (n4Object_n4type == null
				|| n4NamedElement_name == null
				|| n4Element_origin == null
				|| n4Type_fqn == null) {
			throw new IllegalStateException("could not find required members of built-in types");
		}
	}

	@Override
	public void transform() {
		for (Expression expr : reverse(collectNodes(getState().im, Expression.class, true))) {
			// transforming expressions in bottom-up order (it's more natural and simplifies some nesting cases)
			if (expr instanceof CastExpression) {
				transformExpression((CastExpression) expr);
			} else if (expr instanceof ExpressionWithTarget) {
				transformExpression((ExpressionWithTarget) expr);
			} else if (expr instanceof AwaitExpression) {
				transformExpression((AwaitExpression) expr);
			} else if (expr instanceof PromisifyExpression) {
				transformExpression((PromisifyExpression) expr);
			}
		}
	}

	private void transformExpression(CastExpression castExpr) {
		replace(castExpr, castExpr.getExpression()); // simply remove the cast
	}

	private void transformExpression(ExpressionWithTarget exprWithTarget) {
		if (exprWithTarget instanceof ParameterizedPropertyAccessExpression_IM) {
			if (transformTrivialUsageOfReflection((ParameterizedPropertyAccessExpression_IM) exprWithTarget)) {
				return;
			}
		}
	}

	/**
	 * <b>IMPORTANT:</b> here we only do some special handling for the auto-promisify case within an AwaitExpression;
	 * the main handling of the AwaitExpression itself is done in a later transformation
	 * {@code BlockTransformation.transformBlockAsync(Block)}!!!
	 * <p>
	 * Changes
	 *
	 * <pre>
	 * await cls.meth(a, b)
	 * </pre>
	 *
	 * to
	 *
	 * <pre>
	 * await $n4promisifyMethod(cls, 'meth', [a, b])
	 * </pre>
	 *
	 * OR
	 *
	 * <pre>
	 * await fun(a, b)
	 * </pre>
	 *
	 * to
	 *
	 * <pre>
	 * await $n4promisifyFunction(fun, [a, b])
	 * </pre>
	 *
	 * assuming that method 'meth' and function 'fun' are annotated with <code>@Promisifiable</code>.
	 */
	private void transformExpression(AwaitExpression awaitExpr) {
		AwaitExpression awaitExprOrig = getState().tracer.getOriginalASTNodeOfSameType(awaitExpr, false);
		if (promisifyHelper.isAutoPromisify(awaitExprOrig)) {
			// cast is safe because isPromisifiableExpression() returned true
			ParameterizedCallExpression callExpr = (ParameterizedCallExpression) awaitExpr.getExpression();
			ParameterizedCallExpression replacement = promisify(callExpr);
			// note: leaving awaitExpr in the IM; only replacing its contained expression!!
			replace(callExpr, replacement);
		}
	}

	/**
	 * Changes
	 *
	 * <pre>
	 * &#64;Promisify cls.meth(a, b)
	 * </pre>
	 *
	 * to
	 *
	 * <pre>
	 * $n4promisifyMethod(cls, 'meth', [a, b])
	 * </pre>
	 *
	 * OR
	 *
	 * <pre>
	 * &#64;Promisify fun(a, b)
	 * </pre>
	 *
	 * to
	 *
	 * <pre>
	 * $n4promisifyFunction(fun, [a, b])
	 * </pre>
	 */
	private void transformExpression(PromisifyExpression promiExpr) {
		// cast is safe because of validations
		ParameterizedCallExpression callExpr = (ParameterizedCallExpression) promiExpr.getExpression();
		ParameterizedCallExpression replacement = promisify(callExpr);
		replace(promiExpr, replacement);
	}

	private ParameterizedCallExpression promisify(ParameterizedCallExpression callExpr) {
		Expression target = callExpr.getTarget();
		SymbolTableEntry targetSTE = null;
		if (target instanceof ParameterizedPropertyAccessExpression_IM) {
			targetSTE = ((ParameterizedPropertyAccessExpression_IM) target).getProperty_IM();
		}
		if (target instanceof IdentifierRef_IM) {
			targetSTE = ((IdentifierRef_IM) target).getId_IM();
		}
		if (targetSTE instanceof SymbolTableEntryOriginal) {
			SymbolTableEntryOriginal steo = (SymbolTableEntryOriginal) targetSTE;
			IdentifiableElement originalTarget = steo.getOriginalTarget();
			if (originalTarget instanceof TFunction) { // could be a method
				FunctionTypeRef originalTargetTypeRef = (FunctionTypeRef) TypeUtils
						.createTypeRef((TFunction) originalTarget);
				var returnTypeRef = promisifyHelper.extractPromisifiedReturnType(getState().G, originalTargetTypeRef);
				if (returnTypeRef != null) {
					List<TypeArgument> returnTypeRefTypeArgs = returnTypeRef.getTypeArgsWithDefaults();
					// isUndefined() is null-safe
					boolean hasErrorValue = !TypeUtils.isUndefined(returnTypeRefTypeArgs.get(1));
					// isIterableN() is null-safe
					boolean hasMoreThan1SuccessValue = isIterableN(getState().G, returnTypeRefTypeArgs.get(0));

					ArrayElement[] arrayElements = toList(map(callExpr.getArguments(),
							arg -> _ArrayElement(arg.isSpread(), arg.getExpression()))).toArray(new ArrayElement[0]);

					if (target instanceof ParameterizedPropertyAccessExpression_IM
							&& steo.getOriginalTarget() instanceof TMethod) {
						// we have a method invocation, so we need to preserve the 'this' argument:

						// @Promisify cls.meth(a, b)
						// -->
						// $n4promisifyMethod(cls, 'meth', [a, b])
						return _CallExpr(
								_IdentRef(steFor_$n4promisifyMethod()),
								// here we take the "cls" part of "cls.meth" as first argument
								((ParameterizedPropertyAccessExpression_IM) target).getTarget(),
								_StringLiteralForSTE(targetSTE),
								_ArrLit(arrayElements), // reuse arguments while preserving spread
								_BooleanLiteral(hasMoreThan1SuccessValue),
								_BooleanLiteral(!hasErrorValue));
					} else {
						// in all other cases, we do not preserve the 'this' argument:

						// @Promisify fun(a, b)
						// -->
						// $n4promisifyFunction(fun, [a, b])
						return _CallExpr(
								_IdentRef(steFor_$n4promisifyFunction()),
								callExpr.getTarget(), // reuse target as first argument
								_ArrLit(arrayElements), // reuse arguments while preserving spread
								_BooleanLiteral(hasMoreThan1SuccessValue),
								_BooleanLiteral(!hasErrorValue));
					}
				}
			}
		}
		// if anything goes awry, we just return callExpr as replacement, which means we simply remove the @Promisify
		return callExpr;
	}

	/**
	 * Replaces the following trivial uses of the reflection APIs by the resulting value (i.e. a string literal):
	 *
	 * <pre>
	 * MyClass.n4type.name
	 * MyClass.n4type.origin
	 * MyClass.n4type.fqn
	 * </pre>
	 *
	 * Thus, reflection will not actually be used in the output code in the above cases.
	 */
	private boolean transformTrivialUsageOfReflection(ParameterizedPropertyAccessExpression_IM propAccessExpr) {
		IdentifiableElement property = propAccessExpr.getOriginalTargetOfRewiredTarget();
		if (property == n4NamedElement_name
				|| property == n4Element_origin
				|| property == n4Type_fqn) {

			Expression target = propAccessExpr.getTarget();
			if (target instanceof ParameterizedPropertyAccessExpression_IM) {
				ParameterizedPropertyAccessExpression_IM ppae = (ParameterizedPropertyAccessExpression_IM) target;
				IdentifiableElement propertyOfTarget = ppae.getOriginalTargetOfRewiredTarget();
				if (propertyOfTarget == n4Object_n4type) {
					Expression targetOfTarget = ppae.getTarget();
					if (targetOfTarget instanceof IdentifierRef_IM) {
						IdentifiableElement id = ((IdentifierRef_IM) targetOfTarget).getOriginalTargetOfRewiredTarget();
						if (id instanceof TClass) {
							String value = null;
							if (property == n4NamedElement_name) {
								value = id.getName();
							} else if (property == n4Element_origin) {
								value = resourceNameComputer.generateProjectDescriptor(id.eResource());
							} else if (property == n4Type_fqn) {
								// avoid optimizing this case for built-in types
								// (we cannot know for sure the value of the 'fqn' property set in the .js files)
								if (!N4Scheme.isFromResourceWithN4Scheme(id)) {
									value = resourceNameComputer.getFullyQualifiedTypeName((TClass) id);
								}
							} else {
								throw new IllegalStateException(); // should not happen (see above)
							}
							if (value != null) {
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

}
