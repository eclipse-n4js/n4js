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
package org.eclipse.n4js.ide;

import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskContext;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Adjustments of {@link WorkspaceAccess} when running in the LSP context.
 */
@Singleton
public class IdeWorkspaceAccess extends WorkspaceAccess {

	@Inject
	private ResourceTaskManager resourceTaskManager;

	/**
	 * This delegates to {@link ResourceTaskManager} for creating resource sets, to make sure they are set up the same
	 * way as those used in {@link ResourceTaskContext}s.
	 */
	@Override
	public XtextResourceSet createResourceSet() {
		return resourceTaskManager.createTemporaryResourceSet();
	}
}
