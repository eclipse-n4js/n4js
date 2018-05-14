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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * A custom {@link IEObjectDescriptionWithError} that allows to specify a custom error message and code to display to
 * users (@See {@link IssueCodes}).
 */
public class IssueCodeBasedEObjectDescription extends AbstractDescriptionWithError {
	private final String message;
	private final String issueCode;
	private final Severity severity;

	/** Instantiates a new {@link IssueCodeBasedEObjectDescription} with the given issue message and code. */
	public IssueCodeBasedEObjectDescription(IEObjectDescription delegate, String message, String issueCode) {
		super(delegate);
		this.issueCode = issueCode;
		this.message = message;
		this.severity = Severity.ERROR;
	}

	/** Instantiates a new {@link IssueCodeBasedEObjectDescription} with the given issue message and code. */
	public IssueCodeBasedEObjectDescription(IEObjectDescription delegate, String message, String issueCode,
			Severity severity) {
		super(delegate);
		this.issueCode = issueCode;
		this.message = message;
		this.severity = severity;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getIssueCode() {
		return this.issueCode;
	}

	@Override
	public Severity getSeverity() {
		return this.severity;
	}

}
