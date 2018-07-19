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
package org.eclipse.n4js.ui.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import com.google.inject.Inject;

/**
 */
public class WorkspaceCacheAccess {

	@Inject
	private EclipseBasedN4JSWorkspace workspace;

	/**
	 * Allows to remove an entry from the workspace cache.
	 */
	public void discardEntry(IProject project) {
		workspace.discardEntry(URI.createPlatformResourceURI(project.getName(), true));
	}

	/**
	 * Allows to remove all entries from the workspace cache.
	 */
	public void discardEntries() {
		workspace.discardEntries();
	}

}
