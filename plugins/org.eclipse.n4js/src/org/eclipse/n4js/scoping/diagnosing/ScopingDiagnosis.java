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
package org.eclipse.n4js.scoping.diagnosing;

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * A {@ScopingDiagnosis} describes a special case for linking failures of the N4JS Scoping mechanism.
 *
 * A diagnosis should provide an error message via {@link #diagnose(QualifiedName, Object)} that is more informative
 * than the generic linking diagnostic error.
 */
public abstract class ScopingDiagnosis<T> {

	/**
	 * Returns a {@link DiagnosticMessage} which replaces the generic linking diagnostic error message.
	 *
	 * This method may return {@code null} if potential further analysis shows, that the generic error message should be
	 * used instead. (if the special case not applicable anymore)
	 */
	abstract DiagnosticMessage diagnose(QualifiedName name, T element);

	/**
	 * Creates a {@link DiagnosticMessage} for the given issue code and message.
	 *
	 * See {@link IssueCodes} for valid issue codes.
	 */
	protected DiagnosticMessage createMessage(String issueCode, String message) {
		return new DiagnosticMessage(message, IssueCodes.getSeverityForName(issueCode), issueCode);
	}

}
