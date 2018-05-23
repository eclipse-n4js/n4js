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
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.validation.extension.AbstractJSONValidatorExtension;
import org.eclipse.n4js.json.validation.extension.CheckProperty;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.validation.IssueCodes;

import com.google.inject.Inject;

/**
 * A JSON validator extension that adds custom validation to {@code package.json} resources.
 */
public class PackageJsonValidatorExtension extends AbstractJSONValidatorExtension {

	/** regular expression for valid package.json identifier (e.g. package name) */
	private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("(^)?[A-z_][A-z_\\-\\.0-9]*");

	private static final String PACKAGE_JSON_FILENAME = "package.json";

	@Inject
	private XpectAwareFileExtensionCalculator fileExtensionCalculator;

	@Override
	protected boolean isResponsible(Map<Object, Object> context, EObject eObject) {
		// this validator extension only applies to package.json files
		return fileExtensionCalculator.getFilenameWithoutXpectExtension(eObject.eResource().getURI())
				.equals(PACKAGE_JSON_FILENAME);
	}

	/** Validates the project/package name. */
	@CheckProperty(propertyPath = "name", mandatory = true)
	public void checkName(JSONValue projectNameValue) {
		// first check for the type of the name value
		if (!checkIsType(projectNameValue, JSONPackage.Literals.JSON_STRING_LITERAL)) {
			return;
		}
		final JSONStringLiteral projectName = (JSONStringLiteral) projectNameValue;

		if (!IDENTIFIER_PATTERN.matcher(projectName.getValue()).matches()) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_PROJECT_NAME(projectName.getValue()),
					projectNameValue, IssueCodes.PKGJ_INVALID_PROJECT_NAME);
		}
	}
}
