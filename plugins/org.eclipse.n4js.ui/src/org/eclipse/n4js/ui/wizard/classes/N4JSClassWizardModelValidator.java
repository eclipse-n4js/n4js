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
package org.eclipse.n4js.ui.wizard.classes;

import org.eclipse.xtext.resource.IEObjectDescription;

import org.eclipse.n4js.ui.wizard.classifiers.N4JSClassifierWizardModelValidator;
import org.eclipse.n4js.ui.wizard.model.ClassifierReference;

/**
 * A validator for a {@link N4JSClassWizardModel}.
 */
public class N4JSClassWizardModelValidator extends N4JSClassifierWizardModelValidator<N4JSClassWizardModel> {

	private static final String THE_SUPER_CLASS_CANNOT_BE_FOUND = "The super class cannot be found.";

	@Override
	protected void run() throws ValidationException {
		super.run();
		validateSuperClass();
	}

	/**
	 * Validates the super class property.
	 */
	protected void validateSuperClass() throws ValidationException {

		ClassifierReference superClassRef = getModel().getSuperClass();

		if (!superClassRef.getFullSpecifier().isEmpty()) {
			if (!isValidClass(superClassRef)) {
				throw new ValidationException(THE_SUPER_CLASS_CANNOT_BE_FOUND);
			} else if (superClassRef.uri == null) {
				IEObjectDescription classDescription = getClassifierObjectDescriptionForFQN(
						superClassRef.getFullSpecifier());
				if (classDescription != null) {
					superClassRef.uri = classDescription.getEObjectURI();
				}
			}
		}

	}

}
