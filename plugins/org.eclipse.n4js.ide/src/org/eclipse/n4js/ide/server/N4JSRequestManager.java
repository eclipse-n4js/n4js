/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

import org.eclipse.xtext.ide.server.concurrent.AbstractRequest;
import org.eclipse.xtext.ide.server.concurrent.RequestManager;

/**
 * Prints stack traces for requests that threw an exception
 */
public class N4JSRequestManager extends RequestManager {

	@Override
	protected <V> CompletableFuture<V> submit(AbstractRequest<V> request) {
		CompletableFuture<V> future = super.submit(request);
		future.exceptionally((throwable) -> {
			if (!(throwable instanceof CancellationException)) {
				throwable.printStackTrace();
			} else {
				StackTraceElement[] stackTrace = throwable.getStackTrace();
				if (stackTrace.length > 6) {
					System.out.println("Cancel: " + stackTrace[6].toString());
				}
			}
			return null;
		});

		return future;
	}

}
