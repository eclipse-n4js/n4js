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

import com.google.common.base.Function;

/**
 * Procedure is a special {@link Function function} representation without any return value.
 */
public interface Procedure<F> extends Function<F, Void> {

	@Override
	default Void apply(F input) {
		doApply(input);
		return null;
	}

	/**
	 * Applies the procedure on the input argument.
	 *
	 * @param input
	 *            the input
	 */
	void doApply(final F input);

}
