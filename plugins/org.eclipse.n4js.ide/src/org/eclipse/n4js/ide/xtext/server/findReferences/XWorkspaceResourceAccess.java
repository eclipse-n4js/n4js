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
package org.eclipse.n4js.ide.xtext.server.findReferences;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ide.xtext.server.XProjectManager;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * @author kosyakov - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings("restriction")
public class XWorkspaceResourceAccess implements IReferenceFinder.IResourceAccess {

	private final XWorkspaceManager workspaceManager;

	/**
	 * @param workspaceManager
	 *            the workspace manager
	 */
	public XWorkspaceResourceAccess(XWorkspaceManager workspaceManager) {
		this.workspaceManager = workspaceManager;
	}

	@Override
	public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
		XProjectManager projectManager = workspaceManager.getProjectManager(targetURI);
		if (projectManager == null) {
			return null;
		}
		try {
			return work.exec(projectManager.getResourceSet());
		} catch (Exception e) {
			return Exceptions.throwUncheckedException(e);
		}
	}

}
