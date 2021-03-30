/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.workspace;

import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * A resource set implementation that has a reference to the containing workspace and can provide the current workspace
 * configuration, see {@link #getWorkspaceConfiguration()}.
 */
public interface IWorkspaceAwareResourceSet extends ResourceSet {

	/**
	 * Returns the current state of the workspace or <code>null</code> if not available.
	 */
	public WorkspaceConfigSnapshot getWorkspaceConfiguration();
}
