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
package org.eclipse.n4js.projectModel;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.projectModel.internal.N4JSCoreNEW;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.ImplementedBy;

@ImplementedBy(N4JSCoreNEW.class)
public interface IN4JSCoreNEW {

	// FIXME consider making this fail fast and never return null/absent
	public Optional<N4JSWorkspaceConfigSnapshot> getWorkspaceConfig(Notifier context);

	public ImmutableSet<N4JSProjectConfigSnapshot> findAllProjects(Notifier context);

	public Optional<N4JSProjectConfigSnapshot> findProject(Resource resource);

	public Optional<N4JSProjectConfigSnapshot> findProject(Notifier context, URI nestedLocation);

	public Optional<N4JSProjectConfigSnapshot> findProject(Notifier context, N4JSProjectName projectName);

	public Optional<N4JSProjectConfigSnapshot> findProjectContaining(Notifier context, URI nestedLocation);

	public Optional<N4JSSourceFolderSnapshot> findN4JSSourceContainer(Notifier context, URI nestedLocation);

	public boolean isNoValidate(Notifier context, URI nestedLocation);

	public XtextResourceSet createResourceSet(); // TODO pass in a workspace context object

	public Optional<IResourceDescriptions> getXtextIndex(Notifier context);

	public TModule loadModuleFromIndex(ResourceSet resourceSet, IResourceDescription resourceDescription,
			boolean allowFullLoad);

	@Deprecated
	public ImmutableSet<N4JSProjectConfigSnapshot> findAllProjects();

	@Deprecated
	public Optional<N4JSProjectConfigSnapshot> findProject(URI nestedLocation);
}
