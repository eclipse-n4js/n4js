/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.validation;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.scoping.utils.SourceElementExtensions;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Handles the invisible/hollow elements that
 * {@link SourceElementExtensions#collectVisibleIdentifiableElements(org.eclipse.n4js.n4JS.VariableEnvironmentElement)}
 * collects.
 */
public class VeeScopeValidator implements IScopeValidator {
	private final JavaScriptVariantHelper jsVariantHelper;
	private final EObject context;

	/** See {@link VeeScopeValidator}. */
	public VeeScopeValidator(EObject context, JavaScriptVariantHelper jsVariantHelper) {
		if (context.eContainer() instanceof TypeReferenceNode<?>) {
			context = context.eContainer();
		}
		this.jsVariantHelper = jsVariantHelper;
		this.context = context;
	}

	@Override
	public boolean isValid(IEObjectDescription originalDescr) {
		EObject eObjectOrProxy = originalDescr.getEObjectOrProxy();
		if (!(eObjectOrProxy instanceof IdentifiableElement)) {
			return true;
		}
		if (eObjectOrProxy.eIsProxy()
				// also checking for TInterface is only a performance optimization
				// since only TInterfaces provide information to determine "isHollow" below
				&& eObjectOrProxy instanceof TInterface) {
			eObjectOrProxy = EcoreUtil.resolve(eObjectOrProxy, context);
		}
		IdentifiableElement ie = (IdentifiableElement) eObjectOrProxy;

		boolean isHollow = N4JSLanguageUtils.isHollowElement(ie, jsVariantHelper);
		return !isHollow;
	}

	@Override
	public ScopeElementIssue getIssue(IEObjectDescription originalDescr) {
		EObject eObjectOrProxy = originalDescr.getEObjectOrProxy();
		if (eObjectOrProxy.eIsProxy() || !(eObjectOrProxy instanceof IdentifiableElement)) {
			return null;
		}
		QualifiedName name = originalDescr.getName();
		return new ScopeElementIssue(originalDescr,
				IssueCodes.AST_ELEMENT_MISUSED_AS_VALUE_OR_TYPE,
				IssueCodes.getMessageForAST_ELEMENT_MISUSED_AS_VALUE_OR_TYPE(name.toString(), "value"));
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), context);
	}

}
