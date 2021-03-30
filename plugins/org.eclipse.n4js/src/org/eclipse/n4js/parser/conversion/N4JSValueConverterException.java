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
package org.eclipse.n4js.parser.conversion;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.INode;

import org.eclipse.n4js.validation.IssueCodes;

/**
 * Extende value converter exception to hold issue code and message later to use by Xtext diagnostic producer.
 */
public class N4JSValueConverterException extends ValueConverterException {
	private final String issueCode;
	private final Severity severity;

	/**
	 * @param message
	 *            the issue message to be used for the diagnostic
	 * @param issueCode
	 *            the issue code to be used for the diagnostic
	 * @param node
	 *            the node from the node model where this exception had occured
	 * @param cause
	 *            the actual exception
	 */
	public N4JSValueConverterException(final String message, final String issueCode, final INode node,
			final Exception cause) {
		super(message, node, cause);
		this.issueCode = issueCode;
		this.severity = IssueCodes.getDefaultSeverity(issueCode);
	}

	/**
	 * @return the issue code to be used for the diagnostic
	 */
	public String getIssueCode() {
		return issueCode;
	}

	/**
	 * @return the default severity to be used for the diagnostic as long as there is no overrule
	 */
	public Severity getSeverity() {
		return severity;
	}
}
