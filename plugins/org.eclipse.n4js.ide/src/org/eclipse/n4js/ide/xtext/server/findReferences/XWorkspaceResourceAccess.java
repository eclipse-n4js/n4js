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

import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ide.xtext.server.FutureUtil;
import org.eclipse.n4js.ide.xtext.server.ResourceTaskContext;
import org.eclipse.n4js.ide.xtext.server.ResourceTaskManager;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * @author kosyakov - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings("restriction")
public class XWorkspaceResourceAccess implements IReferenceFinder.IResourceAccess {

	/*
	 * Review feedback: Accept the ResourceTaskManager instead in the constructor.
	 */
	private final ResourceTaskManager resourceTaskManager;

	/**
	 * @param resourceTaskManager
	 *            the task manager
	 */
	public XWorkspaceResourceAccess(ResourceTaskManager resourceTaskManager) {
		this.resourceTaskManager = resourceTaskManager;
	}

	@Override
	public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
		URI uri = targetURI.trimFragment(); // note: targetURI may point to an EObject inside an EMF resource!
		ResourceTaskContext currRTC = resourceTaskManager.currentContext();
		if (currRTC != null) {
			return doWork(currRTC.getResourceSet(), work);
		}
		// TODO consider making a current context mandatory by removing the following (see GH-1774):
		CompletableFuture<R> future = resourceTaskManager.runInTemporaryContext(uri, "XWorkspaceResourceAccess", true,
				(ofc, ci) -> doWork(ofc.getResourceSet(), work));
		return FutureUtil.getCancellableResult(future);
	}

	/** Actually do the work in the context of the given resource set. */
	protected <R> R doWork(ResourceSet resourceSet, IUnitOfWork<R, ResourceSet> work) {
		try {
			return work.exec(resourceSet);
		} catch (Exception e) {
			return Exceptions.throwUncheckedException(e);
		}
	}
}
