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
package org.eclipse.n4js.scoping.validation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Validator used to check accessibility of constructor in new-expressions.
 */
public class VisibilityAwareCtorScopeValidator implements IScopeValidator {
	private final NewExpression context;

	private final ContainerTypesHelper containerTypesHelper;
	private final MemberVisibilityChecker checker;

	/**
	 * @param checker
	 *            visibility rules for members. {@link TypeSystemHelper#getStaticType(RuleEnvironment, TypeTypeRef)}.
	 * @param context
	 *            new expression calling a constructor
	 */
	public VisibilityAwareCtorScopeValidator(MemberVisibilityChecker checker, ContainerTypesHelper containerTypesHelper,
			NewExpression context) {

		this.checker = checker;
		this.context = context;
		this.containerTypesHelper = containerTypesHelper;
	}

	@Override
	public boolean isValid(IEObjectDescription objDescr) {
		EObject proxyOrInstance = objDescr.getEObjectOrProxy();
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

	@Override
	public ScopeElementIssue getIssue(IEObjectDescription objDescr) {
		EObject proxyOrInstance = objDescr.getEObjectOrProxy();
		// The cast to TClassifier always works (see the method isAccepted below).
		TClassifier ctorClassifier = (TClassifier) proxyOrInstance;

		String containerName = ctorClassifier.getTypeAsString();
		String message = IssueCodes.getMessageForVIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR("constructor",
				containerName);

		return new ScopeElementIssue(objDescr, message, IssueCodes.VIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR);
	}

}
