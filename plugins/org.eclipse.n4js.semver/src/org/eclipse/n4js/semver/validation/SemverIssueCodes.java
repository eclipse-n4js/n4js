/**
 * Copyright (c) 2023 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.semver.validation;

import static org.eclipse.xtext.diagnostics.Severity.ERROR;

import org.eclipse.xtext.diagnostics.Severity;

/**
 * Enum contains all issues
 */
@SuppressWarnings("javadoc")
public enum SemverIssueCodes {

	/** No parameters */
	SEMVER_TOO_MANY_NUMBERS(ERROR,
			"Too many version parts. Semantic versions consist only of major, minor and patch."),

	/** No parameters */
	SEMVER_TOO_MANY_COMPARATORS(ERROR,
			"Only zero or one comparator allowed.")

	;

	public final Severity severity;
	private final String msgTemplate;

	SemverIssueCodes(Severity severity, String msgTemplate) {
		this.severity = severity;
		this.msgTemplate = msgTemplate;
	}

	public String getMessage(Object... values) {
		if (values == null) {
			return msgTemplate;
		}
		return msgTemplate.formatted(values);
	}
}
