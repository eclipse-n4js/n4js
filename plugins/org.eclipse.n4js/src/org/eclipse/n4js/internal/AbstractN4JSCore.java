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

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.external.ExternalLibraryUriHelper;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.n4mf.validation.WildcardPathFilter;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.resource.IResourceDescription;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 */
public abstract class AbstractN4JSCore implements IN4JSCore {

	@Inject
	private ExternalLibraryUriHelper externalLibraryUriHelper;

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

		if (externalLibraryUriHelper.isExternalLocation(nestedLocation)) {
			return true;
		}

		boolean noValidate = false;
		ModuleFilter validationFilter = getModuleValidationFilter(nestedLocation);
		if (validationFilter != null) {
			noValidate = isPathContainedByFilter(nestedLocation, validationFilter);
		}

		if (!noValidate) {
			ModuleFilter noModuleWrappingFilter = getNoModuleWrappingFilter(nestedLocation);
			if (noModuleWrappingFilter != null) {
				noValidate = isPathContainedByFilter(nestedLocation, noModuleWrappingFilter);
			}
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
			String moduleSpecWithWildcard = spec.getModuleSpecifierWithWildcard();
			moduleSpecWithWildcard = ((spec.getSourcePath() != null) ? spec.getSourcePath()
					: projectRelativeSourcePath) + "/" + moduleSpecWithWildcard;
			List<String> resolvedPaths = WildcardPathFilter.collectPathsByWildcardPath(absoluteLocationPath,
					moduleSpecWithWildcard);
			for (String resolvedPath : resolvedPaths) {
				relativeResolvedPaths.add(resolvedPath);
			}
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
		if (outputPath == null) {
			outputPath = "src-gen";
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
		final TModule existingModule = resource instanceof N4JSResource ? ((N4JSResource) resource).getModule() : null;
		if (existingModule != null) {
			return existingModule;
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
						// casted.resolve();
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
