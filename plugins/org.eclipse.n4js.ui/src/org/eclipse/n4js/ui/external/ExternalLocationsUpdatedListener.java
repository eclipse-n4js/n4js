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
package org.eclipse.n4js.ui.external;

import java.net.URI;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 *
 */
public interface ExternalLocationsUpdatedListener {

	/** Gets called before external store locations changed. */
	public void locationsUpdated(Set<URI> removedLocations, Set<URI> addedLocations, IProgressMonitor monitor);

}
