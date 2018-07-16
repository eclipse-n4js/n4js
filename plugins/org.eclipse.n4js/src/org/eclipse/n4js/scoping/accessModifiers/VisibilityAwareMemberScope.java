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
package org.eclipse.n4js.scoping.accessModifiers;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker.MemberVisibility;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.xtext.scoping.FilterWithErrorMarkerScope;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * Filters members that are not accessible from the given context (that is, from the location of the
 * {@link ParameterizedPropertyAccessExpression} given in the constructor).
 */
public class VisibilityAwareMemberScope extends FilterWithErrorMarkerScope {

	private final MemberVisibilityChecker checker;
	private final EObject context; // usually a ParameterizedPropertyAccessExpression
	private final TypeRef receiverType;

	private final HashMap<String, String> accessModifierSuggestionStore = new HashMap<>();

	/**
	 * Creates a new scope instance filtering given parent.
	 *
	 * @param context
	 *            usually a ParameterizedPropertyAccessExpression.
	 */
	public VisibilityAwareMemberScope(IScope parent, MemberVisibilityChecker checker, TypeRef receiverType,
			EObject context) {
		super(parent);
		this.checker = checker;
		this.receiverType = receiverType;
		this.context = context;
	}

	@Override
	protected IEObjectDescriptionWithError wrapFilteredDescription(IEObjectDescription description) {
		String uriString = description.getEObjectURI().toString();
		return new InvisibleMemberDescription(description, accessModifierSuggestionStore.get(uriString));
	}

	@Override
	protected boolean isAccepted(IEObjectDescription description) {
		EObject proxyOrInstance = description.getEObjectOrProxy();
		if (proxyOrInstance != null && !proxyOrInstance.eIsProxy()) {
			if (proxyOrInstance instanceof TMember) {
				TMember member = (TMember) proxyOrInstance;
				MemberVisibility result = checker.isVisible(context, receiverType, member);

				if (!result.visibility) {
					String uriString = description.getEObjectURI().toString();
					accessModifierSuggestionStore.put(uriString, result.accessModifierSuggestion);
				}
				return result.visibility;

			} else if (proxyOrInstance instanceof TEnumLiteral) {
				return checker.isEnumLiteralVisible(context, receiverType);
			}
		}
		return true;
	}

}
