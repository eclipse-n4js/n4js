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
package org.eclipse.n4js.utils;

/**
 * Representation of a cancelable concept.
 */
public interface Cancelable {

	/**
	 * Returns {@code true} if the concrete instance is cancelable. Otherwise returns with {@code false}.
	 *
	 * @return {@code true} if the instance is cancelable, otherwise {@code false}.
	 */
	boolean isCancelable();

	/**
	 * Sets the cancelable state of the concrete instance based on the boolean argument.
	 *
	 * @param b
	 *            the desired cancelable state of the instance.
	 */
	void setCancelable(boolean b);

}
