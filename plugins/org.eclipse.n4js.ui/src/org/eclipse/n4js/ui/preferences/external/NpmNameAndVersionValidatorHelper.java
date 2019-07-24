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
package org.eclipse.n4js.ui.preferences.external;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.NpmCLI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.ui.utils.InputComposedValidator;
import org.eclipse.n4js.ui.utils.InputFunctionalValidator;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;

import com.google.inject.Inject;

/**
 * Used for preference page actions of external libraries only
 */
public class NpmNameAndVersionValidatorHelper {
	@Inject
	private NpmCLI npmCli;
	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;
	@Inject
	private SemverHelper semverHelper;

	IInputValidator getPackageVersionValidator() {
		return InputFunctionalValidator.from(
				(final String version) -> parsingVersionValidator(version));
	}

	/**
	 * version validator based on N4MF parser (and its support for version syntax).
	 *
	 * @return error message or null if there are no errors
	 */
	private String parsingVersionValidator(final String data) {
		String result = null;

		IParseResult parseResult = semverHelper.getParseResult(data);
		if (parseResult == null) {
			result = "Could not create version from string :" + data + ":\n";
		} else if (parseResult.hasSyntaxErrors()) {
			INode firstErrorNode = parseResult.getSyntaxErrors().iterator().next();
			result = "Parsing error: " + firstErrorNode.getSyntaxErrorMessage().getMessage();
		}

		// otherwise, parsedVersion is valid and result remains 'null'
		// to indicate validity (see {@link IInputValidator#isValid})

		return result;
	}

	/**
	 * Validator that checks if given name is valid package name and can be used to install new package (i.e. there is
	 * no installed package with the same name).
	 *
	 * @return validator checking if provided name can be used to install new package
	 */
	IInputValidator getPackageNameToInstallValidator() {
		return InputComposedValidator.compose(
				getBasicPackageValidator(), InputFunctionalValidator.from(
						(final String name) -> !isNpmWithNameInstalled(new N4JSProjectName(name)) ? null
								/* error message */
								: "The npm package '" + name + "' is already available."));
	}

	/**
	 * Validator that checks if given name is valid package name and can be used to uninstall new package (i.e. there is
	 * installed package with the same name).
	 *
	 * @return validator checking if provided name can be used to install new package
	 */
	IInputValidator getPackageNameToUninstallValidator() {
		return InputComposedValidator.compose(
				getBasicPackageValidator(), InputFunctionalValidator.from(
						(final String name) -> isNpmWithNameInstalled(new N4JSProjectName(name)) ? null
								/* error case */
								: "The npm package '" + name + "' is not installed."));
	}

	// TODO refactor with libManager internal logic of validating package name
	private IInputValidator getBasicPackageValidator() {
		return InputFunctionalValidator.from(
				(final String name) -> {
					N4JSProjectName projectName = new N4JSProjectName(name);
					if (npmCli.invalidPackageName(projectName))
						return "The npm package name should be specified.";
					for (int i = 0; i < name.length(); i++) {
						if (Character.isWhitespace(name.charAt(i)))
							return "The npm package name must not contain any whitespaces.";

						if (Character.isUpperCase(name.charAt(i)))
							return "The npm package name must not contain any upper case letter.";
					}
					return null;
				});
	}

	private boolean isNpmWithNameInstalled(final N4JSProjectName packageName) {
		return externalLibraryWorkspace.getProject(packageName) != null;
	}

}
