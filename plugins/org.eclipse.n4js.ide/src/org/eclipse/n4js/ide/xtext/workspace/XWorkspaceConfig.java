/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.workspace;

import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.IWorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.workspace.WorkspaceConfig;

/**
 * FIXME: Necessary?
 */
@SuppressWarnings("restriction")
public class XWorkspaceConfig extends WorkspaceConfig implements XIWorkspaceConfig {

	@Override
	public URI getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<? extends XIProjectConfig> getProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XIProjectConfig findProjectContaining(URI member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XIProjectConfig findProjectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkspaceChanges update(URI changedResource, Function<String, ProjectDescription> pdProvider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWorkspaceConfigSnapshot toSnapshot() {
		// TODO Auto-generated method stub
		return null;
	}

}
