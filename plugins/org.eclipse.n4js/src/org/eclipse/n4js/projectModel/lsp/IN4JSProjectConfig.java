/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.projectModel.lsp;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.lsp.ex.IProjectConfigEx;

/**
 * N4JS specific extension to the IProjectConfig.
 */
public interface IN4JSProjectConfig extends IProjectConfigEx {
	@Override
	IN4JSSourceFolder findSourceFolderContaining(URI member);

	@Override
	Set<? extends IN4JSSourceFolder> getSourceFolders();

	@Override
	IN4JSWorkspaceConfig getWorkspaceConfig();

	/**
	 * Return the wrapped n4js project.
	 */
	IN4JSProject toProject();
}
