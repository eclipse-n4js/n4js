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
package org.eclipse.n4js.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectDescription.ModuleFilter;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.WildcardPathFilterHelper;
import org.eclipse.xtext.resource.IResourceDescription;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 */
public abstract class AbstractN4JSCore implements IN4JSCore {

	@Inject
	private WildcardPathFilterHelper wildcardHelper;

	@Override
	public boolean isNoValidate(URI nestedLocation) {
		boolean noValidate = false;
		ModuleFilter validationFilter = getModuleValidationFilter(nestedLocation);
		if (validationFilter != null) {
			noValidate |= wildcardHelper.isPathContainedByFilter(nestedLocation, validationFilter);
		}

		return noValidate;
	}

	@Override
	public String getOutputPath(URI nestedLocation) {
		String outputPath = null;
		Optional<? extends IN4JSSourceContainer> container = findN4JSSourceContainer(nestedLocation);
		if (container.isPresent()) {
			outputPath = container.get().getProject().getOutputPath();
		}
		return outputPath;
	}

	/**
	 * returns for the given URI the no-validate module filter
	 */
	protected ModuleFilter getModuleValidationFilter(URI nestedLocation) {
		ModuleFilter moduleFilter = null;
		Optional<? extends IN4JSSourceContainer> container = findN4JSSourceContainer(nestedLocation);
		if (container.isPresent()) {
			moduleFilter = container.get().getProject().getModuleValidationFilter();
		}
		return moduleFilter;
	}

	@Override
	public TModule loadModuleFromIndex(final ResourceSet resourceSet,
			final IResourceDescription resourceDescription, boolean allowFullLoad) {
		final URI resourceURI = resourceDescription.getURI();
		Resource resource = resourceSet.getResource(resourceURI, false);
		if (resource instanceof N4JSResource) {
			final N4JSResource resourceCasted = (N4JSResource) resource;
			final Script existingScript = resourceCasted.getScript();
			final TModule existingModule = resourceCasted.getModule();
			if (existingModule != null) {
				// resource exists already and it already has a TModule
				// -> simply return that
				return existingModule;
			} else if (existingScript != null && !existingScript.eIsProxy()) {
				// resource exists already and it already has its AST loaded (though no TModule yet)
				// -> we have to create the TModule from that AST instead of loading it from index
				resourceCasted.installDerivedState(false); // trigger installation of derived state (i.e. types builder)
				return resourceCasted.getModule();
			}
		}
		if (resource == null) {
			resource = resourceSet.createResource(resourceURI);
		}
		if (resource instanceof N4JSResource) {
			if (resource.getContents().isEmpty()) {
				final N4JSResource casted = (N4JSResource) resource;
				try {
					if (casted.loadFromDescription(resourceDescription)) {
						casted.performPostProcessing();
						return casted.getModule();
					} else if (allowFullLoad) {
						casted.unload();
						casted.load(resourceSet.getLoadOptions());
						casted.installDerivedState(false);
						return casted.getModule();
					}
				} catch (final Exception e) {
					casted.unload();
					return null;
				}
			}
		}
		return null;
	}

}
