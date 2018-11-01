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
package org.eclipse.n4js.xpect.methods;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.postprocessing.ASTMetaInfoUtils;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;

import com.google.inject.Inject;

/**
 */
@XpectImport(N4JSOffsetAdapter.class)
public class TypeXpectMethod {
	@Inject
	private N4JSTypeSystem ts;

	/**
	 * Checks that an element/expression has a certain type. Usage:
	 *
	 * <pre>
	 * // Xpect type of 'location' --&gt; Type
	 * </pre>
	 *
	 * The location (of) is optional.
	 *
	 * @param arg1
	 *            the location identified by the offset. Note that there are different implementations of IEObjectOwner,
	 *            and we need IEStructuralFeatureAndEObject, while ICrossEReferenceAndEObject or IEAttributeAndEObject
	 *            would not work in all cases (as not all eobjects we test have cross references or attributes, but
	 *            feature is the join of both).
	 */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public void type(
			@StringExpectation IStringExpectation expectation,
			IEObjectCoveringRegion arg1) { // ICrossEReferenceAndEObject arg1) {
		String actual = getTypeString(arg1, false);
		if (expectation == null) {
			throw new IllegalStateException("No expectation specified, add '--> Type'");
		}
		expectation.assertEquals(actual);
	}

	/**
	 * Checks that an element/expression has a certain expected type (i.e. Xsemantics judgment expectedTypeIn). Usage:
	 *
	 * <pre>
	 * // Xpect expectedType at 'location' --&gt; Type
	 * </pre>
	 *
	 * The location (at) is optional.
	 *
	 * @param arg1
	 *            the location identified by the offset. Note that there are different implementations of IEObjectOwner,
	 *            and we need IEStructuralFeatureAndEObject, while ICrossEReferenceAndEObject or IEAttributeAndEObject
	 *            would not work in all cases (as not all eobjects we test have cross references or attributes, but
	 *            feature is the join of both).
	 */
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	@Xpect
	public void expectedType(
			@StringExpectation IStringExpectation expectation,
			IEObjectCoveringRegion arg1) { // ICrossEReferenceAndEObject arg1) {
		String actual = getTypeString(arg1, true);
		if (expectation == null) {
			throw new IllegalStateException("No expectation specified, add '--> Type'");
		}
		expectation.assertEquals(actual);
	}

	private String getTypeString(IEObjectCoveringRegion offset, boolean expectedType) {
		final String calculatedString;
		EObject eobject = offset.getEObject();
		if (eobject instanceof LiteralOrComputedPropertyName) {
			eobject = eobject.eContainer();
		}
		RuleEnvironment G = newRuleEnvironment(eobject);
		TypeRef result;
		if (expectedType) {
			if (!(eobject instanceof Expression && eobject.eContainer() != null))
				return "Not an Expression at given region (required to obtain expected type); got instead: "
						+ eobject.eClass().getName();
			result = ts.expectedTypeIn(G, eobject.eContainer(), (Expression) eobject).getValue();
		} else {
			if (eobject instanceof BindingProperty) {
				/*-
				 * Small tweak to allow testing the inferred type of variable declarations within binding patterns. For
				 * example, without this tweak, the following test would fail with a "Not a TypableElement at given
				 * region" exception:
				 *
				 * // Xpect type of 'len' --> number
				 * var {length:len} = "hello";
				 */
				if (((BindingProperty) eobject).getValue() != null
						&& ((BindingProperty) eobject).getValue().getVarDecl() != null) {
					eobject = ((BindingProperty) eobject).getValue().getVarDecl();
				}
			}
			if (!(eobject instanceof TypableElement))
				return "Not a TypableElement at given region; got instead: " + eobject.eClass().getName();
			result = ts.type(G, (TypableElement) eobject);
		}
		calculatedString = result.getTypeRefAsString();
		return calculatedString;
	}

	/**
	 * Checks that a call expression to a generic function/method has the correct type arguments. Mostly intended for
	 * checking the automatically inferred type arguments in case of a non-parameterized call expression.
	 *
	 * <pre>
	 * class C {
	 *     &lt;S,T> m(S p1, T p2) {}
	 * }
	 * var C c;
	 *
	 * // Xpect typeArgs of 'm' --> number, string
	 * c.m(42,"hello");
	 * </pre>
	 *
	 * Note that the offset denotes the target(!) of the call expression, not the call expression itself. Usually it is
	 * enough to provide the last IdentifierRef before the call expression's parentheses.
	 */
	@ParameterParser(syntax = "'of' arg1=OFFSET")
	@Xpect
	public void typeArgs(@StringExpectation IStringExpectation expectation, IEObjectCoveringRegion arg1) {
		final String actual = getTypeArgumentsString(arg1);
		if (expectation == null) {
			throw new IllegalStateException("no expectation specified, add '--> type arguments string'");
		}
		expectation.assertEquals(actual);
	}

	private String getTypeArgumentsString(IEObjectCoveringRegion offset) {
		final EObject eobject = offset != null ? offset.getEObject() : null;
		final EObject container = eobject != null ? eobject.eContainer() : null;
		if (eobject == null || !(container instanceof ParameterizedCallExpression
				&& ((ParameterizedCallExpression) container).getTarget() == eobject)) {
			// missing or invalid offset
			return "xpect method error: offset not given or does not point to target of a call expression";
		}
		if (!(eobject.eResource() instanceof N4JSResource)) {
			return "xpect method error: offset does not point to an EObject contained in a N4JSResource";
		}
		// offset points to the target of a call expression
		final ParameterizedCallExpression callExpr = (ParameterizedCallExpression) container;
		final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(eobject);
		final TypeRef targetTypeRef = ts.type(G, callExpr.getTarget());
		if (!(targetTypeRef instanceof FunctionTypeExprOrRef)) {
			return "xpect method error: cannot infer type of call expression target OR it's not a FunctionTypeExprOrRef";
		}
		final List<TypeVariable> typeParams = ((FunctionTypeExprOrRef) targetTypeRef).getTypeVars();
		final int expectedNumOfTypeArgs = typeParams.size(); // not interested in the actual typeParams, just the size
		final List<TypeRef> typeArgs;
		if (callExpr.getTypeArgs().isEmpty()) {
			// no type arguments given in call expression -> use inferred type arguments
			// (should be the standard case when testing)
			final List<TypeRef> inferredTypeArgs = ASTMetaInfoUtils.getInferredTypeArgs(callExpr);
			if (inferredTypeArgs != null) {
				typeArgs = inferredTypeArgs;
			} else {
				typeArgs = Collections.emptyList();
			}
		} else {
			// call expression is parameterized -> use the explicitly given type arguments
			// (just provided for completeness)
			typeArgs = callExpr.getTypeArgs();
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < expectedNumOfTypeArgs; i++) {
			final TypeRef inferredTypeArg = i < typeArgs.size() ? typeArgs.get(i) : null;
			if (sb.length() > 0)
				sb.append(", ");
			if (inferredTypeArg != null)
				sb.append(inferredTypeArg.getTypeRefAsString());
			else
				sb.append("*missing*");
		}
		return sb.toString();
	}
}
