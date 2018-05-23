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
package org.eclipse.n4js.jsdoc2spec;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 *
 */
@FunctionalInterface
public interface CheckCanceled {

	/**
	 * Performs this operation on the given argument.
	 *
	 * @param monitor
	 *            the input argument
	 */
	void check(IProgressMonitor monitor) throws InterruptedException;

	/**
	 * This method checks if the given monitor is canceled or interrupted. If so, an {@link InterruptedException} is
	 * thrown.
	 */
	static void checkUserCanceled(IProgressMonitor monitor) throws InterruptedException {
		if (monitor.isCanceled() || Thread.interrupted()) {
			throw new InterruptedException("User canceled Operation.");
		}
	}
}
