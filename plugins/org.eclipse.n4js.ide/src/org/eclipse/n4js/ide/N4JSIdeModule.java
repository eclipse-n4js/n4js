/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide;

import org.eclipse.n4js.ide.sever.N4JSProjectManager;
import org.eclipse.n4js.ide.sever.N4JSProjectWorkspaceConfigFactory;
import org.eclipse.xtext.ide.server.IWorkspaceConfigFactory;
import org.eclipse.xtext.ide.server.ProjectManager;

/**
 * Use this class to register ide components.
 */
public class N4JSIdeModule extends AbstractN4JSIdeModule {

	public ClassLoader bindClassLoaderToInstance() {
		return getClass().getClassLoader();
	}

	// public Class<? extends WorkspaceManager> bindWorkspaceManager() {
	// return N4JSWorkspaceManager.class;
	// }

	public Class<? extends IWorkspaceConfigFactory> bindIWorkspaceConfigFactory() {
		return N4JSProjectWorkspaceConfigFactory.class;
	}

	public Class<? extends ProjectManager> bindProjectManager() {
		return N4JSProjectManager.class;
	}

}
