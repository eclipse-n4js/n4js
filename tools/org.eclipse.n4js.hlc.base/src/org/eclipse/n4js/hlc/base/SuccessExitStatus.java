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
package org.eclipse.n4js.hlc.base;

/**
 * POJO for compiler signaling successful execution.
 */
public final class SuccessExitStatus {
	/** Simple explanation for user message. */
	public final String explanation = "normal successful exit";
	/** Code for terminating the VM. */
	public final int code = 0;

	/** utility instance */
	public static final SuccessExitStatus INSTANCE = new SuccessExitStatus();
}
