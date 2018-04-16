/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import org.eclipse.core.runtime.IStatus;

/**
 * Utility to inspect {@link IStatus} instances
 */
public class StatusUtils {

	/** @return the message of the status or its child that has a state of not {@link IStatus#OK} */
	static public String getErrorMessage(IStatus status, boolean checkErrorLog) {
		String msg = getAllErrorMessages(status);

		if (checkErrorLog) {
			msg += "\nSee error log for details.";
		}
		return msg;
	}

	static private String getAllErrorMessages(IStatus status) {
		String msg = "";

		IStatus[] children = status.getChildren();
		if (children.length == 0) {
			msg += status.isOK() ? "" : status.getMessage();
		} else {
			for (IStatus childStatus : children) {
				msg += getAllErrorMessages(childStatus);
			}
		}
		return msg;
	}

}
