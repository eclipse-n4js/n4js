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
package org.eclipse.n4js.xtext.workspace;

import java.util.Objects;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.utils.EcoreUtilN4;

/**
 * A utility providing access to a {@link WorkspaceConfigSnapshot} when given a resource set as context. This works both
 * inside the LSP builder and inside open editors or other {@code ResourceTaskContext}s.
 */
public class WorkspaceConfigAdapter extends AdapterImpl {

	/** Obtain the workspace configuration attached to the given resource set. */
	public static WorkspaceConfigSnapshot getWorkspaceConfig(ResourceSet resourceSet) {
		if (resourceSet == null) {
			return null;
		}
		// in the LSP builder we can obtain the workspace config via the XWorkspaceManager:
		if (resourceSet instanceof IWorkspaceAwareResourceSet) {
			WorkspaceConfigSnapshot workspaceConfig = ((IWorkspaceAwareResourceSet) resourceSet)
					.getWorkspaceConfiguration();
			if (workspaceConfig != null) {
				return workspaceConfig;
			}
		}
		// in all other cases (i.e. in a ResourceTaskContext) we expect the workspace config to be set on the resource
		// set with an adapter:
		WorkspaceConfigAdapter adapter = (WorkspaceConfigAdapter) EcoreUtil.getAdapter(
				resourceSet.eAdapters(), WorkspaceConfigAdapter.class);
		if (adapter != null) {
			return adapter.workspaceConfig;
		}
		return null;
	}

	/** Attach the given workspace configuration to the given resource set. */
	public static void installWorkspaceConfig(ResourceSet resourceSet, WorkspaceConfigSnapshot workspaceConfig) {
		if (resourceSet == null) {
			return;
		}
		uninstallWorkspaceConfig(resourceSet);
		EcoreUtilN4.doWithDeliver(false, () -> {
			resourceSet.eAdapters().add(new WorkspaceConfigAdapter(workspaceConfig));
		}, resourceSet);
	}

	/** Remove the workspace configuration attached to the given resource set (if any). */
	public static void uninstallWorkspaceConfig(ResourceSet resourceSet) {
		if (resourceSet == null) {
			return;
		}
		EcoreUtilN4.doWithDeliver(false, () -> {
			resourceSet.eAdapters().removeIf(a -> a instanceof WorkspaceConfigAdapter);
		}, resourceSet);
	}

	private final WorkspaceConfigSnapshot workspaceConfig;

	private WorkspaceConfigAdapter(WorkspaceConfigSnapshot workspaceConfig) {
		this.workspaceConfig = workspaceConfig;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == WorkspaceConfigAdapter.class;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " --> " + Objects.toString(workspaceConfig);
	}
}
