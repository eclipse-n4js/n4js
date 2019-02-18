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
package org.eclipse.n4js.binaries;

import static org.eclipse.core.runtime.Status.OK_STATUS;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.SemverMatcher;
import org.eclipse.n4js.semver.SemverMatcher.VersionNumberRelation;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.process.ProcessResult;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Class for validating {@link Binary binaries} with respect to their existence, accessibility and version.
 */
@Singleton
public class BinariesValidator {
	private static final Logger LOGGER = Logger.getLogger(BinariesValidator.class);

	@Inject
	private StatusHelper status;

	@Inject
	private BinariesCommandFactory commandFactory;

	@Inject
	private SemverHelper semverHelper;

	/**
	 * Validates the availability, accessibility and version of the given binary. Returns with a status representing the
	 * outcome of the validation process.
	 *
	 * @param binary
	 *            the binary to validate.
	 * @return a status representing the outcome of the validation process.
	 */
	public IStatus validate(final Binary binary) {

		IStatus simpleValidation = validateBinaryFile(binary);
		if (!simpleValidation.isOK())
			return simpleValidation;

		final File file = new File(binary.getBinaryAbsolutePath());
		if (!file.canExecute()) {
			return error(binary, "Cannot execute '" + binary.getLabel() + "' binary at: " + file + ".");
		}

		final ProcessResult result = commandFactory.checkBinaryVersionCommand(binary).execute();

		if (!result.isOK()) {
			return error(binary, "Expected exit code 0 when checking version of '" + binary.getLabel() + "' got "
					+ result.getExitCode() + "' instead.\n" + result.getStdErr());
		}
		if (LOGGER.isDebugEnabled()) {
			final String stdErrString = result.getStdErr();
			if (!stdErrString.isEmpty())
				LOGGER.debug(stdErrString);
		}

		final String stdOutString = result.getStdOut();
		final IParseResult parseResult = semverHelper.getParseResult(stdOutString.trim());
		if (parseResult.hasSyntaxErrors()) {
			INode firstErrorNode = parseResult.getSyntaxErrors().iterator().next();
			String syntaxErr = firstErrorNode.getSyntaxErrorMessage().getMessage();
			String msg = "Error in binary '" + binary.getLabel()
					+ "' when parsing version '" + stdOutString + "': " + syntaxErr;
			return error(binary, msg);
		}

		VersionNumber versionNumber = semverHelper.parseVersionNumber(parseResult);
		if (versionNumber == null) {
			String msg = "Error in binary '" + binary.getLabel() +
					"' when parsing version '" + stdOutString + "': No version number found.";
			return error(binary, msg);
		}

		VersionNumber minimumVersion = binary.getMinimumVersion();
		VersionNumberRelation versionRelation = SemverMatcher.relation(versionNumber, minimumVersion);
		if (!versionRelation.isGreaterOrEqual()) {
			String minimumVersionText = SemverSerializer.serialize(minimumVersion);
			String parsedVersionText = SemverSerializer.serialize(versionNumber);
			String msg = "The required minimum version of '" + binary.getLabel() + "' is '" + minimumVersionText
					+ "'. Currently configured version is '" + parsedVersionText
					+ "' which is '" + versionRelation + "' than the minimum version but it must be greater or equal.";
			return error(binary, msg);
		}

		return OK_STATUS;
	}

	/**
	 * Does simple validation, checking if a file of the binary exits.
	 *
	 * @param binary
	 *            the binary to validate.
	 * @return a status representing the outcome of the validation process.
	 */
	public IStatus validateBinaryFile(final Binary binary) {

		final File file = new File(binary.getBinaryAbsolutePath());
		if (!file.exists()) {
			return error(binary, "'" + binary.getLabel() + "' binary does not exist at " + file
					+ ". Please check your preferences.");
		}
		if (!file.isFile()) {
			return error(binary, "Invalid '" + binary.getLabel() + "' configuration. Expected file at: " + file
					+ ". Got a directory instead.");
		}
		return OK_STATUS;

	}

	private IStatus error(final Binary binary, final String message) {
		return error(binary, message, null);
	}

	private IStatus error(final Binary binary, final String message, Throwable t) {
		final IStatus delegate = status.createError(message, IllegalBinaryStateException.ISSUE_CODE, t);
		return new BinaryStatus(delegate, binary);
	}
}
