/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.validation.validators.packagejson;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.validation.extension.AbstractJSONValidatorExtension;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.validation.Check;

import com.google.inject.Inject;

/**
 * A JSON validator extension that adds custom validation to {@code package.json} resources.
 */
public class PackageJsonValidatorExtension extends AbstractJSONValidatorExtension {

	private static final String PACKAGE_JSON_FILENAME = "package.json";

	@Inject
	private XpectAwareFileExtensionCalculator fileExtensionCalculator;

	@Override
	protected boolean isResponsible(Map<Object, Object> context, EObject eObject) {
		// this validator extension only applies to package.json files
		return fileExtensionCalculator.getFilenameWithoutXpectExtension(eObject.eResource().getURI())
				.equals(PACKAGE_JSON_FILENAME);
	}

	/***/
	@Check
	public void checkJSONDocument(JSONDocument document) {
		// validate package.json files
		addIssue(IssueCodes.ANN__ONLY_IN_N4JS, document, IssueCodes.ANN__ONLY_IN_N4JS);
	}
}
