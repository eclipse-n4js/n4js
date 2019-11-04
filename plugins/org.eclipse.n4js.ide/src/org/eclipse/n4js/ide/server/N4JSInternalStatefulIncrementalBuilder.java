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

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.build.BuildRequest;
import org.eclipse.xtext.build.IncrementalBuilder.InternalStatefulIncrementalBuilder;
import org.eclipse.xtext.build.Source2GeneratedMapping;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

/**
 * TODO: Fixes Xtext issue of double validation
 */
@SuppressWarnings("restriction")
public class N4JSInternalStatefulIncrementalBuilder extends InternalStatefulIncrementalBuilder {

	private List<Issue> lastValidationResult;

	@Override
	protected boolean validate(Resource resource) {
		IResourceValidator resourceValidator = getResourceServiceProvider(resource).getResourceValidator();
		if (resourceValidator == null) {
			return true;
		}
		lastValidationResult = resourceValidator.validate(resource, CheckMode.ALL, null);

		return getRequest().getAfterValidate().afterValidate(resource.getURI(), lastValidationResult);
	}

	private IResourceServiceProvider getResourceServiceProvider(Resource resource) {
		if (resource instanceof XtextResource) {
			return ((XtextResource) resource).getResourceServiceProvider();
		}
		return getContext().getResourceServiceProvider(resource.getURI());
	}

	@Override
	protected void generate(Resource resource, BuildRequest request, Source2GeneratedMapping newMappings) {
		for (Issue issue : lastValidationResult) {
			if (issue.getSeverity() == Severity.ERROR) {
				return;
			}
		}

		super.generate(resource, request, newMappings);
	}

}
