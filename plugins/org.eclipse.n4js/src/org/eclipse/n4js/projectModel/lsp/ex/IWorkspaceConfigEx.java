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
package org.eclipse.n4js.projectModel.lsp.ex;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

/**
 * Enhancement to {@link IWorkspaceConfig}.
 */
@SuppressWarnings("restriction")
public interface IWorkspaceConfigEx extends IWorkspaceConfig {

	@Override
	Set<? extends IProjectConfigEx> getProjects();

	@Override
	IProjectConfigEx findProjectContaining(URI member);

	@Override
	IProjectConfigEx findProjectByName(String name);

}
