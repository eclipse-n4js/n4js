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

import org.eclipse.n4js.ide.sever.N4JSWorkspaceManager;
import org.eclipse.xtext.ide.server.WorkspaceManager;

/**
 * Use this class to register ide components.
 */
public class N4JSIdeModule extends AbstractN4JSIdeModule {
	
	public ClassLoader bindClassLoaderToInstance() {
		return getClass().getClassLoader();
	}
	
	
	
	public Class<? extends WorkspaceManager> bindN4JSWorkspaceManager() {
		return N4JSWorkspaceManager.class;
	}
	
}
