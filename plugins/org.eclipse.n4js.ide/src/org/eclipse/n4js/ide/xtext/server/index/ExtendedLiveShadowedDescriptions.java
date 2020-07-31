/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server.index;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.impl.LiveShadowedChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.impl.ResourceSetBasedResourceDescriptions;
import org.eclipse.xtext.workspace.IProjectConfigProvider;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.inject.Inject;

/**
 *
 */
@SuppressWarnings("restriction")
public class ExtendedLiveShadowedDescriptions extends LiveShadowedChunkedResourceDescriptions {

	@Inject
	private ExtendedResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private IProjectConfigProvider projectConfigProvider;

	private IWorkspaceConfig workspaceConfig;

	@Override
	public void setContext(Notifier ctx) {
		ResourceSet resourceSet = EcoreUtil2.getResourceSet(ctx);
		if (ResourceDescriptionsData.ResourceSetAdapter.findResourceDescriptionsData(resourceSet) != null) {
			throw new IllegalArgumentException("Unexpected attached adapter");
		}
		ResourceSetBasedResourceDescriptions localDescriptions = (ResourceSetBasedResourceDescriptions) getLocalDescriptions();
		localDescriptions.setContext(resourceSet);

		setGlobalDescriptions(resourceDescriptionsProvider.getResourceDescriptionsExtended(resourceSet));
		workspaceConfig = projectConfigProvider.getProjectConfig(resourceSet).getWorkspaceConfig();
	}

	@Override
	public IWorkspaceConfig getWorkspaceConfig() {
		return workspaceConfig;
	}

}
