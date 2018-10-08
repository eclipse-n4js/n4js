/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.building;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.external.ExternalLibraryErrorMarkerManager;
import org.eclipse.n4js.ui.internal.N4JSEclipseProject;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.xtext.builder.builderState.MarkerUpdaterImpl;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSMarkerUpdater extends MarkerUpdaterImpl {

	@Inject
	private IStorage2UriMapper mapper;

	private ExternalLibraryErrorMarkerManager markerManager;

	private IN4JSCore n4jsCore;

	@Inject
	private void injectISharedStateContributionRegistry(ISharedStateContributionRegistry registry) {
		try {
			this.n4jsCore = registry.getSingleContributedInstance(IN4JSCore.class);
			this.markerManager = registry.getSingleContributedInstance(ExternalLibraryErrorMarkerManager.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateMarkers(Delta delta, /* @Nullable */ ResourceSet resourceSet, IProgressMonitor monitor)
			throws OperationCanceledException {

		URI uri = delta.getUri();

		// quick exit for js files
		// (pure performance tweak, because those resources have an empty AST anyway; see N4JSResource#doLoad())
		String uriExt = uri.fileExtension();
		if (N4JSLanguageHelper.OPAQUE_JS_MODULES
				&& (N4JSGlobals.JS_FILE_EXTENSION.equals(uriExt) || N4JSGlobals.JSX_FILE_EXTENSION.equals(uriExt))) {
			return;
		}

		Iterable<Pair<IStorage, IProject>> pairs = mapper.getStorages(uri);
		if (resourceSet != null && pairs.iterator().hasNext()) {
			Pair<IStorage, IProject> pair = pairs.iterator().next();
			if (!(pair.getFirst() instanceof IFile)) {
				updateMarkersForExternalLibraries(delta, resourceSet, monitor);
				return;
			}
		}
		super.updateMarkers(delta, resourceSet, monitor);
	}

	private void updateMarkersForExternalLibraries(Delta delta, ResourceSet resourceSet, IProgressMonitor monitor) {
		URI uri = delta.getUri();
		if (n4jsCore.isNoValidate(uri)) {
			return;
		}

		Resource resource = resourceSet.getResource(uri, true);
		IResourceValidator validator = getValidator(resource);
		IN4JSProject prj = n4jsCore.findProject(uri).orNull();
		CancelIndicator cancelIndicator = getCancelIndicator(monitor);

		if (prj != null && prj.isExternal() && prj.exists() && prj instanceof N4JSEclipseProject) {
			List<Issue> list = validator.validate(resource, CheckMode.NORMAL_AND_FAST, cancelIndicator);
			markerManager.setIssues(uri, list);
		}
	}

	private CancelIndicator getCancelIndicator(final IProgressMonitor monitor) {
		return () -> monitor.isCanceled();
	}

	private IResourceValidator getValidator(Resource resource) {
		return getService(resource, IResourceValidator.class);
	}

	private <T> T getService(final Resource resource, final Class<T> clazz) {
		final IResourceServiceProvider serviceProvider = ((XtextResource) resource).getResourceServiceProvider();
		return serviceProvider.get(clazz);
	}

}
