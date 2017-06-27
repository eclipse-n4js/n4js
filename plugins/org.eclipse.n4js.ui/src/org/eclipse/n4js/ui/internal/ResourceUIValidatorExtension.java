/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.internal;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.validation.DefaultResourceUIValidatorExtension;
import org.eclipse.xtext.ui.validation.IResourceUIValidatorExtension;
import org.eclipse.xtext.ui.validation.MarkerEraser;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

/**
 * {@link IResourceUIValidatorExtension} implementation that uses the services associated with the validated resource
 * instead of injecting them with Guice. With this solution N4MF related validations can be triggered from N4JS
 * projects.
 */
public class ResourceUIValidatorExtension extends MarkerEraser implements IResourceUIValidatorExtension {

	private final static Logger LOGGER = Logger.getLogger(DefaultResourceUIValidatorExtension.class);

	@Override
	public void updateValidationMarkers(IFile file, Resource resource, CheckMode mode, IProgressMonitor monitor)
			throws OperationCanceledException {
		if (shouldProcess(file)) {
			addMarkers(file, resource, mode, monitor);
		}
	}

	@Override
	public void deleteValidationMarkers(IFile file, CheckMode checkMode, IProgressMonitor monitor) {
		super.deleteValidationMarkers(file, checkMode, monitor);
	}

	private void addMarkers(IFile file, Resource resource, CheckMode mode, IProgressMonitor monitor)
			throws OperationCanceledException {
		try {
			List<Issue> list = getValidator(resource).validate(resource, mode, getCancelIndicator(monitor));
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			deleteMarkers(file, mode, monitor);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			createMarkers(file, list, getMarkerCreator(resource), getMarkerTypeProvider(resource));
		} catch (OperationCanceledError error) {
			throw error.getWrapped();
		} catch (CoreException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void createMarkers(IFile file, List<Issue> list, MarkerCreator markerCreator,
			MarkerTypeProvider markerTypeProvider) throws CoreException {

		for (Issue issue : list) {
			markerCreator.createMarker(issue, file, markerTypeProvider.getMarkerType(issue));
		}
	}

	private CancelIndicator getCancelIndicator(final IProgressMonitor monitor) {
		return () -> monitor.isCanceled();
	}

	private IResourceValidator getValidator(Resource resource) {
		return getService(resource, IResourceValidator.class);
	}

	private MarkerCreator getMarkerCreator(Resource resource) {
		return getService(resource, MarkerCreator.class);
	}

	private MarkerTypeProvider getMarkerTypeProvider(Resource resource) {
		return getService(resource, MarkerTypeProvider.class);
	}

	private <T> T getService(final Resource resource, final Class<T> clazz) {
		final IResourceServiceProvider serviceProvider = ((XtextResource) resource).getResourceServiceProvider();
		return serviceProvider.get(clazz);
	}

}
