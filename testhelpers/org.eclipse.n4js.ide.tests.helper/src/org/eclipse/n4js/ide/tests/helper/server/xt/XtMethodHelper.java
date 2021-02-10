/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

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
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

/**
 *
 */
public class XtMethodHelper {
	@Inject
	private N4JSTypeSystem ts;

	public String getTypeString(EObject eobject, boolean expectedType) {
		final String calculatedString;
		if (eobject instanceof LiteralOrComputedPropertyName) {
			eobject = eobject.eContainer();
		}
		RuleEnvironment G = newRuleEnvironment(eobject);
		TypeRef result;
		if (expectedType) {
			if (!(eobject instanceof Expression && eobject.eContainer() != null))
				return "Not an Expression at given region (required to obtain expected type); got instead: "
						+ eobject.eClass().getName();
			result = ts.expectedType(G, eobject.eContainer(), (Expression) eobject);
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

	public String getTypeArgumentsString(EObject eobject) {
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

	static public EObject getEObject(XtextResource resource, int offset, int length) {
		boolean haveRegion = length > 0;
		int endOffset = offset + length;
		EObject semanticObject = null;

		IParseResult parseResult = resource.getParseResult();
		INode node = NodeModelUtils.findLeafNodeAtOffset(parseResult.getRootNode(), offset);
		while (node != null) {
			EObject actualObject = NodeModelUtils.findActualSemanticObjectFor(node);
			if (actualObject != null) {
				if (haveRegion) {
					int nodeEndOffset = node.getEndOffset();
					if (nodeEndOffset <= endOffset || semanticObject == null) {
						semanticObject = actualObject;
					}
					if (nodeEndOffset >= endOffset) {
						break;
					}
				} else { // no region given, just a matched offset
					if (semanticObject == null) {
						semanticObject = actualObject;
						break;
					}
				}
			}
			node = node.getParent();
		}
		return semanticObject;
	}

}
