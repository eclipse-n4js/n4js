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
package org.eclipse.n4js.utils.resources;

import java.io.File;

import org.eclipse.core.resources.IResource;

/**
 * Representation of an external resource.
 */
public interface IExternalResource extends IResource {

	/**
	 * Returns with the wrapped external {@link File file} resource that is wrapped by the current Eclipse workspace
	 * based resource.
	 *
	 * @return the wrapped file resource.
	 */
	File getExternalResource();

}
