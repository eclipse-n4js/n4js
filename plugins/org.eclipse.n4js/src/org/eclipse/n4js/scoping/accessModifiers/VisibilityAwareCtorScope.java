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
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.TypeSystemHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.xtext.scoping.FilterWithErrorMarkerScope;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * Scope used to check accessibility of constructor in new-expressions.
 */
public class VisibilityAwareCtorScope extends FilterWithErrorMarkerScope {
	private final NewExpression context;

	private final ContainerTypesHelper containerTypesHelper;
	private final MemberVisibilityChecker checker;

	/**
	 * @param parent
	 *            parent scope
	 * @param checker
	 *            visibility rules for members. {@link TypeSystemHelper#getStaticType(RuleEnvironment, TypeTypeRef)}.
	 * @param context
	 *            new expression calling a constructor
	 */
	public VisibilityAwareCtorScope(IScope parent, MemberVisibilityChecker checker,
			ContainerTypesHelper containerTypesHelper, NewExpression context) {
		super(parent);
		this.checker = checker;
		this.context = context;
		this.containerTypesHelper = containerTypesHelper;
	}

	@Override
	protected IEObjectDescriptionWithError wrapFilteredDescription(IEObjectDescription originalDescr) {
		EObject proxyOrInstance = originalDescr.getEObjectOrProxy();
		// The cast to TClassifier always works (see the method isAccepted below).
		TClassifier ctorClassifier = (TClassifier) proxyOrInstance;
		return new InvisibleCtorDescription(originalDescr, ctorClassifier);
	}

	@Override
	protected boolean isAccepted(IEObjectDescription description) {
		EObject proxyOrInstance = description.getEObjectOrProxy();
		if (proxyOrInstance != null && !proxyOrInstance.eIsProxy()) {
			if (proxyOrInstance instanceof TClassifier) {
				TClassifier ctorClassifier = (TClassifier) proxyOrInstance;
				if (ctorClassifier.isAbstract()) {
					return true; // avoid duplicate error messages
				}
				// If the class is found, check if the visibility of the constructor is valid
				TMethod usedCtor = containerTypesHelper.fromContext(context).findConstructor(ctorClassifier);
				if (usedCtor != null && usedCtor.isConstructor()) {
					return checker.isConstructorVisible(context, TypeUtils.createTypeRef(ctorClassifier), usedCtor);
				}
			}
		}
		return true;
	}
}
