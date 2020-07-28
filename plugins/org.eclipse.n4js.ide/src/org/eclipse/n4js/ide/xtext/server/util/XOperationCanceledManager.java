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
package org.eclipse.n4js.ide.xtext.server.util;

import java.util.concurrent.CancellationException;

import org.eclipse.xtext.service.OperationCanceledManager;

/**
 * Specialized to support {@link CancellationException} as valid indication for a cancellation.
 *
 * Obsolete with more recent Xtext version.
 */
public class XOperationCanceledManager extends OperationCanceledManager {

	@Override
	protected RuntimeException getPlatformOperationCanceledException(Throwable t) {
		if (t instanceof CancellationException) {
			return (CancellationException) t;
		}
		return super.getPlatformOperationCanceledException(t);
	}

}
