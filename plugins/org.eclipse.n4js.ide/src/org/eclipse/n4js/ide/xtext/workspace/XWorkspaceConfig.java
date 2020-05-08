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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.workspace.WorkspaceConfig;

/**
 * FIXME: Necessary?
 */
@SuppressWarnings("restriction")
public class XWorkspaceConfig extends WorkspaceConfig implements XIWorkspaceConfig {

	@Override
	public WorkspaceChanges update(URI changedResource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
