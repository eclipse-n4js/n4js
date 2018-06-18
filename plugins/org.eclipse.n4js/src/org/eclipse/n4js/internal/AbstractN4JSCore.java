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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.WildcardPathFilter;
import org.eclipse.xtext.resource.IResourceDescription;

import com.google.common.base.Optional;

/**
 */
public abstract class AbstractN4JSCore implements IN4JSCore {

	@Override
	public boolean isInSameProject(URI nestedLocation1, URI nestedLocation2) {
		final Optional<? extends IN4JSProject> project1 = findProject(nestedLocation1);
		if (project1.isPresent()) {
			final Optional<? extends IN4JSProject> project2 = findProject(nestedLocation2);
			return project2.isPresent() && project1.get().equals(project2.get());
		}
		return false;
	}

	@Override
	public boolean isNoValidate(URI nestedLocation) {
		boolean noValidate = false;

		ModuleFilter validationFilter = getModuleValidationFilter(nestedLocation);
		if (validationFilter != null) {
			noValidate |= isPathContainedByFilter(nestedLocation, validationFilter);
		}

		ModuleFilter noModuleWrappingFilter = getNoModuleWrappingFilter(nestedLocation);
		if (noModuleWrappingFilter != null) {
			noValidate |= isPathContainedByFilter(nestedLocation, noModuleWrappingFilter);
		}

		return noValidate;
	}

	private boolean isPathContainedByFilter(URI nestedLocation, ModuleFilter filter) {
		List<String> paths = getPaths(nestedLocation, filter);
		for (String path : paths) {
			if (getLocationPath(nestedLocation).equals(path)) {
				return true;
			}
		}
		return false;
	}

	private List<String> getPaths(URI location, ModuleFilter moduleFilter) {
		Optional<? extends IN4JSSourceContainer> sourceContainerOpt = findN4JSSourceContainer(location);
		if (sourceContainerOpt.isPresent()) {
			IN4JSSourceContainer sourceContainer = sourceContainerOpt.get();
			IN4JSProject project = sourceContainer.getProject();
			String projectLocation = getLocationPath(project.getLocation());
			return handleWildcardsAndRelativeNavigation(projectLocation, sourceContainer.getRelativeLocation(),
					moduleFilter);
		}
		return new ArrayList<>();
	}

	private String getLocationPath(URI location) {
		return CommonPlugin.asLocalURI(location).toFileString();
	}

	private List<String> handleWildcardsAndRelativeNavigation(String absoluteLocationPath,
			String projectRelativeSourcePath, ModuleFilter moduleFilter) {

		List<String> relativeResolvedPaths = new ArrayList<>();
		for (ModuleFilterSpecifier spec : moduleFilter.getModuleSpecifiers()) {
			String specPath = spec.getSourcePath();
			if (specPath != null && !projectRelativeSourcePath.equals(specPath)) {
				// different source container, different filter path
				// nothing will be found here
				continue;
			}

			Path basePath = new Path(absoluteLocationPath);
			if (specPath != null) {
				basePath.append(specPath);
			} else {
				basePath.append(projectRelativeSourcePath);
			}
			String basePathStr = basePath.toString();
			String pathsToFind = "/" + spec.getModuleSpecifierWithWildcard();

			List<String> resolvedPaths = WildcardPathFilter.collectPathsByWildcardPath(basePathStr, pathsToFind);
			relativeResolvedPaths.addAll(resolvedPaths);
		}
		return relativeResolvedPaths;
	}

	@Override
	public boolean isNoModuleWrapping(URI nestedLocation) {
		boolean noModuleWrapping = false;
		ModuleFilter filter = getNoModuleWrappingFilter(nestedLocation);
		if (filter != null) {
			noModuleWrapping = isPathContainedByFilter(nestedLocation, filter);
		}
		return noModuleWrapping;
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

	@Override
	public ModuleFilter getModuleValidationFilter(URI nestedLocation) {
		ModuleFilter moduleFilter = null;
		Optional<? extends IN4JSSourceContainer> container = findN4JSSourceContainer(nestedLocation);
		if (container.isPresent()) {
			moduleFilter = container.get().getProject().getModuleValidationFilter();
		}
		return moduleFilter;
	}

	@Override
	public ModuleFilter getNoModuleWrappingFilter(URI nestedLocation) {
		ModuleFilter moduleFilter = null;
		Optional<? extends IN4JSSourceContainer> container = findN4JSSourceContainer(nestedLocation);
		if (container.isPresent()) {
			moduleFilter = container.get().getProject().getNoModuleWrappingFilter();
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
