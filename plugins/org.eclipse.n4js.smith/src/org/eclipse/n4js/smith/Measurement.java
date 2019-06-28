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
package org.eclipse.n4js.smith;

import org.eclipse.n4js.utils.TameAutoClosable;

/**
 * Generic interface for measurements. Obtained instance should be considered {@code started}. Caller can end given
 * measurement by calling {@link #close()}. Concrete implementations will track different data.
 */
public interface Measurement extends TameAutoClosable {

	/**
	 * Ends given measurement. Concrete implementations can do some data processing in this step. Collected data is
	 * passed to the {@link DataCollector} that created this instance. It is expected that caller invokes
	 * {@link #close()} only once, but concrete implementations need to assure that this method is safe to call multiple
	 * times, i.e. subsequent calls have no effects.
	 */
	@Override
	public void close();
}
