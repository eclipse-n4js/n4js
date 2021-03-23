/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.scoping;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.scoping.TopLevelElementsCollector;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.workspace.IN4JSCoreNEW;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 *
 */
public class N4IDLAwareTopLevelElementsCollector extends TopLevelElementsCollector {
	private static final Logger LOGGER = Logger.getLogger(N4IDLAwareTopLevelElementsCollector.class);

	@Inject
	private IN4JSCoreNEW n4jsCore;

	@Override
	public Iterable<IEObjectDescription> getTopLevelElements(TModule module, Resource contextResource) {
		Optional<N4JSProjectConfigSnapshot> project = n4jsCore.findProject(contextResource);
		Iterable<IEObjectDescription> allTopLevelElements = super.getTopLevelElements(module, contextResource);

		// if project isn't available, include all top-level elements
		if (!project.isPresent()) {
			LOGGER.warn(String.format("Failed to determine project of resource %s.", contextResource.getURI()));
			return allTopLevelElements;
		}

		// if the containing resources isn't available, include all top-level elements
		if (null == module.eResource()) {
			LOGGER.warn(String.format("Failed to determine resource of TModule %s.", module));
			return allTopLevelElements;
		}

		// otherwise filter by context version
		Optional<N4JSProjectConfigSnapshot> moduleProject = n4jsCore.findProject(module.eResource());

		if (!moduleProject.isPresent()) {
			LOGGER.warn(String.format("Failed to determine project of TModule %s.", module.getQualifiedName()));
			return allTopLevelElements;
		}

		N4JSProjectConfigSnapshot contextN4JSProject = project.get();
		N4JSProjectConfigSnapshot versionedN4JSProject = moduleProject.get();

		final int contextVersion = getProjectContextVersion(versionedN4JSProject, contextN4JSProject);

		// in case of an invalid context version
		if (contextVersion < 1) {
			return allTopLevelElements;
		}

		// filter top-level elements according to N4IDL versioning rules
		N4IDLVersionableFilter versionableFilter = new N4IDLVersionableFilter(contextVersion);
		return versionableFilter.filterElements(allTopLevelElements);
	}

	/**
	 * Returns the context version of the versioned project when accessed from the context project.
	 *
	 * @param versionedProject
	 *            The project that contains versioned types
	 * @param contextProject
	 *            The project from which the type is accessed
	 */
	private int getProjectContextVersion(N4JSProjectConfigSnapshot versionedProject,
			N4JSProjectConfigSnapshot contextProject) {
		// disable logic to get context version from project dependency
		// ProjectDependency moduleDependency = contextProject.getProjectDependencies().stream()
		// .filter(dep -> dep.getProject().getProjectName().equals(versionedProject.getProjectName()))
		// .findAny().orElse(null);
		//
		// if (null == moduleDependency) {
		// return Integer.MAX_VALUE;
		// }
		//
		// VersionConstraint versionConstraint = moduleDependency.getVersionConstraint();
		//
		// if (null == versionConstraint) {
		// return Integer.MAX_VALUE;
		// }
		//
		// DeclaredVersion lowerVersion = versionConstraint.getLowerVersion();
		// DeclaredVersion upperVersion = versionConstraint.getUpperVersion();
		//
		// if (null == lowerVersion && null == upperVersion) {
		// // no version given in the manifest
		// return Integer.MAX_VALUE;
		// } else if (null != lowerVersion && null == upperVersion) {
		// return lowerVersion.getMajor();
		// } else if (null != upperVersion) {
		// return upperVersion.getMajor();
		// } else {
		// // this cannot happen
		// throw new IllegalStateException("Declared version of project dependencies cannot be parsed correctly.");
		// }
		return Integer.MAX_VALUE;
	}
}
