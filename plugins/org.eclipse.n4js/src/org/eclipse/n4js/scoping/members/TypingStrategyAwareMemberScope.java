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
package org.eclipse.n4js.scoping.members;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.extensions.ExpressionExtensions;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareMemberScope;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.xtext.scoping.FilterWithErrorMarkerScope;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * Scope aware of structural typing, i.e., filtering out (with error markers) some members based on the typing strategy.
 */
public class TypingStrategyAwareMemberScope extends FilterWithErrorMarkerScope {

	private final TypingStrategyFilterDesc strategyFilter;
	private final boolean useSite;
	private final String receiverTypeName;

	/**
	 * Creates new scope instance, filtering out members of parent not matching current typing strategy.
	 *
	 * @param parent
	 *            the nested parent scope, usually a {@link VisibilityAwareMemberScope}
	 * @param receiverType
	 *            in case of use site structural typing, the strategy is set in the reference
	 */
	public TypingStrategyAwareMemberScope(IScope parent, TypeRef receiverType, EObject context) {
		super(parent);
		TypingStrategy typingStrategy = TypeUtils.retrieveTypingStrategy(receiverType);

		boolean isLeftHand = ExpressionExtensions.isLeftHandSide(context);
		strategyFilter = new TypingStrategyFilterDesc(typingStrategy, isLeftHand);
		useSite = receiverType != null && receiverType.isUseSiteStructuralTyping();
		receiverTypeName = (receiverType == null || receiverType.eIsProxy()) ? "unknown type"
				: receiverType.getTypeRefAsString();
	}

	@Override
	protected IEObjectDescriptionWithError wrapFilteredDescription(IEObjectDescription originalDescr) {
		return new WrongTypingStrategyDescription(originalDescr,
				strategyFilter.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELDS, useSite, receiverTypeName);
	}

	@Override
	protected boolean isAccepted(IEObjectDescription originalDescr) {
		if (originalDescr == null || IEObjectDescriptionWithError.isErrorDescription(originalDescr)) {
			return true; // do not change error containing descriptions
		}
		return strategyFilter.apply(originalDescr);
	}

}
