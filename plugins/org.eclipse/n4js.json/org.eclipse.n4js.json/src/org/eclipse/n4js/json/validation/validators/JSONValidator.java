/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.validation.validators;

import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.validation.AbstractResourceDependentJSONValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ComposedChecks;
import org.eclipse.xtext.validation.EValidatorRegistrar;

/**
 * This class contains general validation with regard to JSON files. 
 */
@ComposedChecks(validators = {
	PackageJsonValidator.class
})
public class JSONValidator extends AbstractResourceDependentJSONValidator {
	
	/**
	 * NEEDED
	 *
	 * When removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator.
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}
	
	/** Checks the validity of a {@link JSONDocument} element. */
	@Check
	public void checkDocument(JSONDocument document) {
		// validate JSON here
	}
}
