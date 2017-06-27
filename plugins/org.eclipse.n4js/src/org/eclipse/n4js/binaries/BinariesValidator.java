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

import com.google.inject.Inject;

import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.Version;
import org.eclipse.n4js.utils.process.ProcessResult;

/**
 * Class for validating {@link Binary binaries} with respect to their existence, accessibility and version.
 */
public class BinariesValidator {
	private static final Logger LOGGER = Logger.getLogger(BinariesValidator.class);

	@Inject
	private StatusHelper status;

	@Inject
	private BinaryCommandFactory commandFactory;

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
		final Version currentVersion = Version.createFromString(stdOutString.trim());
		if (!Version.isValid(currentVersion)) {
			return error(binary,
					"Cannot find current version of '" + binary.getLabel() + "' binary. Output was: "
							+ stdOutString);
		} else {
			final Version minimumVersion = binary.getMinimumVersion();
			if (0 < minimumVersion.compareTo(currentVersion)) {
				return error(binary,
						"The required minimum version of '" + binary.getLabel() + "' is '" + minimumVersion
								+ "'. Currently configured version is '" + currentVersion + "'.");
			}
			return OK_STATUS;
		}

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
