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
package org.eclipse.n4js.validation;

import org.eclipse.xtext.diagnostics.Severity;

/**
 * Data container for concrete issues.
 */
public class IssueItem {
	/** Reference to the general {@link IssueCodes} */
	public final IssueCodes code;
	/** Severity */
	public final Severity severity;
	/** Complete message */
	public final String message;
	/** Auxiliary data */
	public final String[] data;

	/** Constructor */
	public IssueItem(IssueCodes code, Severity severity, String message) {
		this(code, severity, message, new String[0]);
	}

	/** Constructor */
	public IssueItem(IssueCodes code, Severity severity, String message, String[] data) {
		this.code = code;
		this.severity = severity;
		this.message = message;
		this.data = data;
	}

	/** Returns the issue code name */
	public String getID() {
		return code.name();
	}
}
