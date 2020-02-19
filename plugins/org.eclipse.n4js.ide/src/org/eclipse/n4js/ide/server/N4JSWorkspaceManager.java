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
package org.eclipse.n4js.ide.server;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.XProjectManager;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;

import com.google.inject.Singleton;

/**
 * Customized to support lookup of resource sets per n4js uri scheme.
 */
@Singleton
public class N4JSWorkspaceManager extends XWorkspaceManager {

	/**
	 * @param uri
	 *            the contained uri
	 * @return the project manager.
	 */
	@Override
	public XProjectManager getProjectManager(URI uri) {
		if (N4Scheme.isN4Scheme(uri)) {
			return anyProject();
		}
		return super.getProjectManager(uri);
	}

	private XProjectManager anyProject() {
		Collection<XProjectManager> allProjectManagers = getProjectManagers();
		if (!allProjectManagers.isEmpty()) {
			XProjectManager anyProjectManager = allProjectManagers.iterator().next();
			return anyProjectManager;
		}
		return null;
	}

}
