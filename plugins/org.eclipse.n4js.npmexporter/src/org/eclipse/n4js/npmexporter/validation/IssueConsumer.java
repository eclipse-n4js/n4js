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
package org.eclipse.n4js.npmexporter.validation;

/**
 * IssueConsumer - interface to pass validation (code,message) pairs to interested participants.
 */
public interface IssueConsumer {

	/**
	 * Takes an issue code as defined in messages.properties and an formatted msg, preprocessed by ExperterIssueCode
	 * class. {@link ExporterIssueCodes}
	 *
	 * @param issueCode
	 *            a predefined code
	 * @param msg
	 *            a customized corresponding user-readable message.
	 */
	public void accept(String issueCode, String msg);

}
