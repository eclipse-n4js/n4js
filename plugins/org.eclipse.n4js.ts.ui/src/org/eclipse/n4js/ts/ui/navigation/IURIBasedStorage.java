/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.ui.navigation;

import org.eclipse.core.resources.IEncodedStorage;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;

/**
 * An {@link IStorage} based on a given URI. See {@link URIBasedStorage default implementation} for details.
 */
public interface IURIBasedStorage extends IEncodedStorage {

	/** An URI pointing to the resource represented by this storage. */
	public URI getURI();
}
