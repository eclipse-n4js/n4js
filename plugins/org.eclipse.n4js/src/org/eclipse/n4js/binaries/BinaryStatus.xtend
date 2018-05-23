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
package org.eclipse.n4js.binaries

import org.eclipse.core.runtime.IStatus
import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate

import static extension org.eclipse.xtend.lib.annotations.AccessorType.*

/**
 * Representation of a binary status that wraps the actual {@link Binary binary} as well.
 */
class BinaryStatus implements IStatus {

	@Delegate
	val IStatus delegate;

	@Accessors(AccessorType.PUBLIC_GETTER)
	val Binary binary;

	new(IStatus delegate, Binary binary) {
		this.delegate = delegate;
		this.binary = binary;
	}

}
