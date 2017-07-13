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
package org.eclipse.n4js.utils

import com.google.common.base.Function
import org.eclipse.xtext.xbase.lib.Functions.Function1

import static extension com.google.common.base.Preconditions.checkNotNull

/**
 * Sugar for converting a {@link Function1 Xtext function} it into a {@link Function Guava function}.
 */
class FunctionDelegate<F, T> implements Function<F, T> {

	val Function1<F, T> delegate

	/** Creates a new function instance with the delegate. */
	new(Function1<F, T> delegate) {
		this.delegate = delegate.checkNotNull;
	}

	override apply(F input) {
		delegate.apply(input);
	}

}
