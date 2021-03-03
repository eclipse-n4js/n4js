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
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.projectModel.internal.TempWorkspaceAccess;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.ImplementedBy;

@ImplementedBy(TempWorkspaceAccess.class)
public interface ITempWorkspaceAccess {

	public Optional<N4JSWorkspaceConfigSnapshot> getWorkspaceConfig(Notifier context);

	public ImmutableSet<N4JSProjectConfigSnapshot> findAllProjects(Notifier context);

	public Optional<N4JSProjectConfigSnapshot> findProject(Notifier context, URI nestedLocation);

	public Optional<N4JSProjectConfigSnapshot> findProject(Notifier context, N4JSProjectName projectName);

	public Optional<N4JSSourceFolderSnapshot> findN4JSSourceContainer(Notifier context, URI nestedLocation);

	public boolean isNoValidate(Notifier context, URI nestedLocation);

	public String getOutputPath(Notifier context, URI nestedLocation);
}
