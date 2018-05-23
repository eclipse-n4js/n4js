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
package org.eclipse.n4js.utils.validation;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.CancelableDiagnostician;

import com.google.inject.Inject;

/**
 * Introduces a pre- and post-validation phase to standard Xtext (declarative) validation by adding virtual objects
 * before and after the validation of the real object and its content.
 * <p>
 * That is, before the root container of the resource is validated, a pseudo object of type {@link PreValidation} is to
 * be validated. After the last element of the real content has been validated, another pseudo object of type
 * {@link PostValidation} is validated. Both objects are {@link EObject}s, and their resource getter
 * {@link EObject#eResource()} is overridden to return the currently validated resource.
 * <p>
 * These pre- and post-validation phases can be used to collect and evaluate validation results. E.g., it could be used
 * to enable waring generation for unused {@code @SuppressWarning} annotations.
 * </p>
 * This class has to be bind in the runtime module via
 *
 * <pre>
 * &#064;Override
 * &#064;SingletonBinding
 * public Class&lt;? extends Diagnostician&gt; bindDiagnostician() {
 * 	return PrePostDiagnostician.class;
 * }
 * </pre>
 */
public class PrePostDiagnostician extends CancelableDiagnostician {

	/**
	 * Delegates to super constructor.
	 */
	@Inject
	public PrePostDiagnostician(Registry registry) {
		super(registry);
	}

	@Override
	public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		preValidate(eObject, diagnostics, context);
		boolean result = super.validate(eObject, diagnostics, context);
		postValidate(eObject, diagnostics, context);
		return result;
	}

	private boolean preValidate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (eObject.eContainer() == null && !(eObject instanceof ValidationMarker)) {
			PreValidation pre = ValidationFactory.eINSTANCE.createPreValidation();
			pre.setDelegateResource(eObject.eResource());
			boolean result = super.validate(pre, diagnostics, context);
			if (!(result || diagnostics != null)) {
				return result;
			}
		}
		return true;
	}

	private boolean postValidate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (eObject.eContainer() == null && !(eObject instanceof ValidationMarker)) {
			PostValidation post = ValidationFactory.eINSTANCE.createPostValidation();
			post.setDelegateResource(eObject.eResource());
			return super.validate(post, diagnostics, context);
		}
		return true;
	}
}
