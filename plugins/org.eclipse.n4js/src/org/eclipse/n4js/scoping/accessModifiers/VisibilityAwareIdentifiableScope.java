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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker.TypeVisibility;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * A scope implementation that prefers visible types over invisible types.
 */
public class VisibilityAwareIdentifiableScope extends VisibilityAwareTypeScope {

	private final VariableVisibilityChecker checker;

	/**
	 * Creates a new scope instance.
	 */
	public VisibilityAwareIdentifiableScope(IScope parent, VariableVisibilityChecker checker,
			TypeVisibilityChecker typeVisibilityChecker, EObject context) {
		super(parent, typeVisibilityChecker, context);
		this.checker = checker;
	}

	@Override
	protected boolean isAccepted(IEObjectDescription description) {
		if (TVariable.class.isAssignableFrom(description.getEClass().getInstanceClass())) {
			EObject proxyOrInstance = description.getEObjectOrProxy();
			if (proxyOrInstance instanceof TVariable && !proxyOrInstance.eIsProxy()) {
				TVariable type = (TVariable) proxyOrInstance;

				TypeVisibility visibility = checker.isVisible(this.context, type);

				if (!visibility.visibility) {
					this.accessModifierSuggestionStore.put(description.getEObjectURI().toString(),
							visibility.accessModifierSuggestion);
				}

				return visibility.visibility;
			}
		}
		return super.isAccepted(description);
	}

	@Override
	protected boolean tryAcceptWithoutResolve(IEObjectDescription description) {
		if (TVariable.class.isAssignableFrom(description.getEClass().getInstanceClass())) {
			TypeVisibility visibility = checker.isVisible(this.context, description);
			if (!visibility.visibility) {
				this.accessModifierSuggestionStore.put(description.getEObjectURI().toString(),
						visibility.accessModifierSuggestion);
			}
			return visibility.visibility;
		}
		return super.tryAcceptWithoutResolve(description);
	}

}
