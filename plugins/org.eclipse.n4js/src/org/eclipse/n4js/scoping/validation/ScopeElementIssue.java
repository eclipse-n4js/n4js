/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.validation;

import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IEObjectDescription;

/** Data class that holds information about issues related to elements in a scope. */
public final class ScopeElementIssue {
	/** The object description the issue refers to */
	public final IEObjectDescription delegate;
	/** Severity of the issue */
	public final Severity severity;
	/** Issue code */
	public final String issueCode;
	/** Message of the issue */
	public final String message;

	/** Constructor */
	public ScopeElementIssue(IEObjectDescription delegate, String issueCode, String message) {
		this(delegate, Severity.ERROR, issueCode, message);
	}

	/** Constructor */
	public ScopeElementIssue(IEObjectDescription delegate, Severity severity, String issueCode, String message) {
		this.delegate = delegate;
		this.severity = severity;
		this.issueCode = issueCode;
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}