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
package org.eclipse.n4js.workspace.utils;

import org.eclipse.xtext.util.IAcceptor;

/**
 *
 */
public interface FileSystemAcceptor<T> extends IAcceptor<T> {
	/**
	 * Overwrite to indicate whether the scan can be canceled early.
	 *
	 * @return true iff to continue
	 */
	default boolean continueScan(@SuppressWarnings("unused") T t) {
		return true;
	}
}
