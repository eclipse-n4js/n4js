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

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.xtext.server.ResourceTaskContext;
import org.eclipse.n4js.xtext.server.build.WorkspaceAwareResourceSet;
import org.eclipse.n4js.xtext.server.build.XWorkspaceManager;

/**
 * A utility providing access to a {@link WorkspaceConfigSnapshot} when given a resource set as context. This works both
 * inside the LSP builder and inside open editors or other {@link ResourceTaskContext}s.
 */
public class WorkspaceConfigAccess {

	public static WorkspaceConfigSnapshot getWorkspaceConfig(ResourceSet resourceSet) {
		if (resourceSet == null) {
			return null;
		}
		// in the LSP builder we can obtain the workspace config via the XWorkspaceManager:
		if (resourceSet instanceof WorkspaceAwareResourceSet) {
			XWorkspaceManager workspaceManager = ((WorkspaceAwareResourceSet) resourceSet).getWorkspaceManager();
			if (workspaceManager != null) {
				WorkspaceConfigSnapshot workspaceConfig = workspaceManager.getWorkspaceConfig();
				if (workspaceConfig != null) {
					return workspaceConfig;
				}
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

	public static void setWorkspaceConfig(ResourceSet resourceSet, WorkspaceConfigSnapshot workspaceConfig) {
		if (resourceSet == null) {
			return;
		}
		unsetWorkspaceConfig(resourceSet);
		EcoreUtilN4.doWithDeliver(false, () -> {
			resourceSet.eAdapters().add(new WorkspaceConfigAdapter(workspaceConfig));
		}, resourceSet);
	}

	public static void unsetWorkspaceConfig(ResourceSet resourceSet) {
		if (resourceSet == null) {
			return;
		}
		EcoreUtilN4.doWithDeliver(false, () -> {
			resourceSet.eAdapters().removeIf(a -> a instanceof WorkspaceConfigAdapter);
		}, resourceSet);
	}

	private static final class WorkspaceConfigAdapter extends AdapterImpl {

		public final WorkspaceConfigSnapshot workspaceConfig;

		private WorkspaceConfigAdapter(WorkspaceConfigSnapshot workspaceConfig) {
			this.workspaceConfig = workspaceConfig;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == WorkspaceConfigAdapter.class;
		}
	}
}
