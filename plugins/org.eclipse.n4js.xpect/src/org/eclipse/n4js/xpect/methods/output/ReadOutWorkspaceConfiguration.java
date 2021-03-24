/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.methods.output;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xpect.xtext.lib.setup.FileSetupContext;
import org.eclipse.xpect.xtext.lib.setup.workspace.Workspace;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.IResourceDescription;

/**
 * Reads out Xpect Workspace setup configuration to retrieve EMF resources from there.
 */
public class ReadOutWorkspaceConfiguration extends ReadOutConfiguration {
	private Workspace configuredWorkspace;
	private final FileExtensionProvider fileExtensionProvider;

	ReadOutWorkspaceConfiguration(FileSetupContext ctx, WorkspaceAccess core,
			FileExtensionProvider fileExtensionProvider) {
		super(ctx, core);
		this.fileExtensionProvider = fileExtensionProvider;
	}

	/**
	 * @param workspace
	 *            the Xpect configuration item to be read out
	 */
	public void add(org.eclipse.xpect.xtext.lib.setup.workspace.Workspace workspace) {
		this.configuredWorkspace = workspace;
	}

	/**
	 * @return the workspace configured by Xpect, null if not configured by Xpect
	 */
	public Workspace getXpectConfiguredWorkspace() {
		return configuredWorkspace;
	}

	/**
	 * @return the resources retrieved from the Xpect resource set configuration
	 */
	@Override
	public List<Resource> getResources() {
		final List<Resource> configuredResources = newArrayList();
		if (configuredWorkspace != null && fileSetupCtx != null) {
			for (IResourceDescription res : index.getAllResourceDescriptions()) {
				if (fileExtensionProvider.isValid(res.getURI().fileExtension())) {
					configuredResources.add(resourceSet.getResource(res.getURI(), true));
				}
			}
		}
		return configuredResources;
	}
}
