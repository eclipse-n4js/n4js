/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

/**
 * Extends {@link Runnable} with {@link Exception} thrown by {@link Runnable#run()}.
 */
@FunctionalInterface
public interface RunnableWithException<T extends Exception> {

	/**
	 * Invokes the given lambda. See {@link Runnable#run()}.
	 */
	void run() throws T;
}
