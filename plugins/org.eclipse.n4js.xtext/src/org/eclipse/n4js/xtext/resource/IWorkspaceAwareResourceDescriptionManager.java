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
package org.eclipse.n4js.xtext.resource;

import java.util.Collection;

import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

/**
 * Extends the standard resource description manager by methods taking a workspace configuration into account.
 */
public interface IWorkspaceAwareResourceDescriptionManager extends IResourceDescription.Manager {

	/**
	 * In addition to {@link #isAffected(Collection, IResourceDescription, IResourceDescriptions)}, this method will use
	 * information from the given workspace configuration during <code>isAffected</code> computation.
	 */
	boolean isAffected(Collection<IResourceDescription.Delta> deltas, IResourceDescription candidate,
			IResourceDescriptions context, WorkspaceConfigSnapshot contextWorkspaceConfig)
			throws IllegalArgumentException;
}
