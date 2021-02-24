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
package org.eclipse.n4js.xtext.server.findReferences;

import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.xtext.server.FutureUtil;
import org.eclipse.n4js.xtext.server.ResourceTaskContext;
import org.eclipse.n4js.xtext.server.ResourceTaskManager;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.concurrent.CancelableUnitOfWork;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * LSP specific implementation of the {@link IResourceAccess} for find references.
 */
@SuppressWarnings("restriction")
public class XWorkspaceResourceAccess implements IReferenceFinder.IResourceAccess {

	private final ResourceTaskManager resourceTaskManager;
	private final boolean resolveAndValidate;

	/**
	 * @param resourceTaskManager
	 *            the task manager
	 */
	public XWorkspaceResourceAccess(ResourceTaskManager resourceTaskManager) {
		this(resourceTaskManager, true);
	}

	/**
	 * @param resourceTaskManager
	 *            the task manager
	 * @param resolveAndValidate
	 *            see
	 *            {@link ResourceTaskManager#runInTemporaryContext(URI, String, boolean, java.util.function.BiFunction)
	 *            runInTemporaryContext(, , resolveAndValidate, )}
	 */
	public XWorkspaceResourceAccess(ResourceTaskManager resourceTaskManager, boolean resolveAndValidate) {
		this.resourceTaskManager = resourceTaskManager;
		this.resolveAndValidate = resolveAndValidate;
	}

	@Override
	public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
		URI uri = targetURI.trimFragment(); // note: targetURI may point to an EObject inside an EMF resource!
		ResourceTaskContext currRTC = resourceTaskManager.currentContext();
		if (currRTC != null) {
			return doWork(currRTC.getResourceSet(), work, CancelIndicator.NullImpl);
		}
		// TODO consider making a current context mandatory by removing the following (see GH-1774):
		CompletableFuture<R> future = resourceTaskManager.runInTemporaryContext(
				uri, "XWorkspaceResourceAccess", resolveAndValidate,
				(ofc, ci) -> doWork(ofc.getResourceSet(), work, ci));
		return FutureUtil.getCancellableResult(future);
	}

	/** Actually do the work in the context of the given resource set. */
	protected <R> R doWork(ResourceSet resourceSet, IUnitOfWork<R, ResourceSet> work, CancelIndicator ci) {
		try {
			if (work instanceof CancelableUnitOfWork) {
				((CancelableUnitOfWork<?, ?>) work).setCancelIndicator(ci);
			}
			return work.exec(resourceSet);
		} catch (Exception e) {
			return Exceptions.throwUncheckedException(e);
		}
	}
}
