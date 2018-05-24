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
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.validation.extension.AbstractJSONValidatorExtension;
import org.eclipse.n4js.json.validation.extension.CheckProperty;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
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
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__NAME, mandatory = true)
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

	@CheckProperty(propertyPath = "n4js." + ProjectDescriptionHelper.PROP__SOURCES, mandatory = true)
	public void checkSourceContainers(JSONValue sourceContainerValue) {
		// first check for the type of the source-container value
		if (!checkIsType(sourceContainerValue, JSONPackage.Literals.JSON_OBJECT, " in source container section.")) {
			return;
		}
		final JSONObject sourceContainerObject = (JSONObject) sourceContainerValue;

		for (NameValuePair pair : sourceContainerObject.getNameValuePairs()) {
			checkSourceContainerSection(pair);
		}
	}

	private boolean checkSourceContainerSection(NameValuePair pair) {
		final String sourceContainerType = pair.getName();

		// check that sourceContainerType represents a valid source container type
		if (!isValidSourceContainerTypeLiteral(sourceContainerType)) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_SOURCE_CONTAINER_TYPE(sourceContainerType),
					pair.getValue(), IssueCodes.PKGJ_INVALID_SOURCE_CONTAINER_TYPE);
			return false;
		}

		// check type of RHS
		if (!checkIsType(pair.getValue(), JSONPackage.Literals.JSON_ARRAY)) {
			return false;
		}

		// check each source container specifier
		final JSONArray sourceContainerSpecifiers = (JSONArray) pair.getValue();
		for (JSONValue specifier : sourceContainerSpecifiers.getElements()) {

		}

		return true;
	}

	/**
	 * Returns {@code true} iff the given {@code typeLiteral} represents a valid SourceContainerType (e.g. source,
	 * test).
	 */
	private boolean isValidSourceContainerTypeLiteral(String typeLiteral) {
		// check that typeLiteral is all lower-case and a corresponding enum literal exists
		return typeLiteral.toLowerCase().equals(typeLiteral) &&
				SourceContainerType.get(typeLiteral.toUpperCase()) != null;
	}
}
