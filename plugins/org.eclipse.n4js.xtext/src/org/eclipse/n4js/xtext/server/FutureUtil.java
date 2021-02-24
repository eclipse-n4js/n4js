/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.server;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.util.Exceptions;

/**
 * Static utility methods for dealing with {@link Future}s.
 */
public class FutureUtil {

	/**
	 * Obtains the given future's result via {@link Future#get()}, but converts <code>java.util.concurrent</code>'s
	 * {@link CancellationException} to Xtext's {@link OperationCanceledException} and wraps checked exceptions in a
	 * {@link RuntimeException}.
	 */
	public static <T> T getCancellableResult(Future<T> future) {
		try {
			return future.get();
		} catch (Throwable e) {
			Throwable cancellation = getCancellation(e);
			if (cancellation instanceof OperationCanceledError) {
				throw (OperationCanceledError) cancellation;
			} else if (cancellation instanceof OperationCanceledException) {
				throw (OperationCanceledException) cancellation;
			} else if (cancellation instanceof CancellationException) {
				String msg = e.getMessage();
				if (msg != null) {
					throw new OperationCanceledException(e.getMessage());
				}
				throw new OperationCanceledException();
			}
			return Exceptions.throwUncheckedException(e);
		}
	}

	private static Throwable getCancellation(Throwable e) {
		while (e != null) {
			if (e instanceof OperationCanceledError
					|| e instanceof OperationCanceledException
					|| e instanceof CancellationException) {
				return e;
			}
			e = e.getCause();
		}
		return null;
	}
}
