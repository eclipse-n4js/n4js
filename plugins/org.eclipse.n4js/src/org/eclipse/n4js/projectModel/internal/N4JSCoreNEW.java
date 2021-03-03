/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.projectModel.internal;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.projectDescription.ModuleFilter;
import org.eclipse.n4js.projectDescription.ModuleFilterType;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCoreNEW;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.WildcardPathFilterHelper;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigAccess;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

public class N4JSCoreNEW implements IN4JSCoreNEW {

	@Inject
	private WildcardPathFilterHelper wildcardHelper;

	@Override
	public Optional<N4JSWorkspaceConfigSnapshot> getWorkspaceConfig(Notifier context) {
		ResourceSet resourceSet = EcoreUtil2.getResourceSet(context);
		WorkspaceConfigSnapshot config = resourceSet != null
				? WorkspaceConfigAccess.getWorkspaceConfig(resourceSet)
				: null;
		return Optional.fromNullable((N4JSWorkspaceConfigSnapshot) config);
	}

	@Override
	public ImmutableSet<N4JSProjectConfigSnapshot> findAllProjects(Notifier context) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent()
				? config.get().getProjects()
				: ImmutableSet.of();
	}

	@Override
	public Optional<N4JSProjectConfigSnapshot> findProject(Notifier context, URI nestedLocation) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent()
				? Optional.fromNullable(config.get().findProjectByNestedLocation(nestedLocation))
				: Optional.absent();
	}

	@Override
	public Optional<N4JSProjectConfigSnapshot> findProject(Notifier context, N4JSProjectName projectName) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent()
				? Optional.fromNullable(config.get().findProjectByName(projectName.getRawName()))
				: Optional.absent();
	}

	@Override
	public Optional<N4JSSourceFolderSnapshot> findN4JSSourceContainer(Notifier context, URI nestedLocation) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent()
				? Optional.fromNullable(config.get().findSourceFolderContaining(nestedLocation))
				: Optional.absent();
	}

	@Override
	public boolean isNoValidate(Notifier context, URI nestedLocation) {
		ModuleFilter validationFilter = getModuleValidationFilter(context, nestedLocation);
		if (validationFilter != null) {
			return wildcardHelper.isPathContainedByFilter(nestedLocation, validationFilter);
		}
		return false;
	}

	/**
	 * returns for the given URI the no-validate module filter
	 */
	private ModuleFilter getModuleValidationFilter(Notifier context, URI nestedLocation) {
		N4JSWorkspaceConfigSnapshot config = getWorkspaceConfig(context).orNull();
		N4JSProjectConfigSnapshot project = config != null ? config.findProjectContaining(nestedLocation) : null;
		if (project != null) {
			// FIXME could there be more than one module filter per type???
			for (ModuleFilter moduleFilter : project.getProjectDescription().getModuleFilters()) {
				if (moduleFilter.getModuleFilterType() == ModuleFilterType.NO_VALIDATE) {
					return moduleFilter;
				}
			}
		}
		return null;
	}

	@Override
	public String getOutputPath(Notifier context, URI nestedLocation) {
		N4JSWorkspaceConfigSnapshot config = getWorkspaceConfig(context).orNull();
		N4JSProjectConfigSnapshot project = config != null ? config.findProjectContaining(nestedLocation) : null;
		if (project != null) {
			ProjectDescription pd = project.getProjectDescription();
			if (pd != null) {
				return pd.getOutputPath();
			}
		}
		return null;
	}
}
