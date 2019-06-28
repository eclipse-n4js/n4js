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
package org.eclipse.n4js.ui.validation;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.internal.RaceDetectionHelper;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.validation.N4JSResourceValidator;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * A custom resource validator that will not create issues for resources which are contained in folders that are not
 * marked as source containers.
 */
public class SourceContainerAwareResourceValidator extends N4JSResourceValidator {

	private final IN4JSEclipseCore eclipseCore;
	private final OperationCanceledManager operationCanceledManager;

	@Inject
	private SourceContainerAwareResourceValidator(IN4JSEclipseCore eclipseCore,
			OperationCanceledManager operationCanceledManager) {
		this.eclipseCore = eclipseCore;
		this.operationCanceledManager = operationCanceledManager;
	}

	@Override
	public List<Issue> validate(Resource resource, CheckMode mode, CancelIndicator cancelIndicator) {
		try (Measurement m = N4JSDataCollectors.dcManifestAwareResourceValidator
				.getMeasurement(resource.getURI().toString());) {

			operationCanceledManager.checkCanceled(cancelIndicator);
			if (!isInSourceFolder(resource)) {
				return Collections.emptyList();
			}
			RaceDetectionHelper.log("Validating: %s", resource.getURI());
			List<Issue> res = super.validate(resource, mode, cancelIndicator);
			return res;
		}
	}

	private boolean isInSourceFolder(Resource resource) {
		URI uri = resource.getURI();
		Optional<? extends IN4JSSourceContainer> sourceContainerOpt = eclipseCore.findN4JSSourceContainer(uri);
		if (sourceContainerOpt.isPresent()) {
			IN4JSSourceContainer sourceContainer = sourceContainerOpt.get();
			return !sourceContainer.isExternal();
		}
		return false;
	}

}
