/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ide.server.BuildManager.Buildable;
import org.eclipse.xtext.ide.server.WorkspaceManager;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.IWorkspaceConfig;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSWorkspaceManager extends WorkspaceManager {

	private Procedure2<? super URI, ? super Iterable<Issue>> issueAcceptor;

	@Override
	public void initialize(URI baseDir, Procedure2<? super URI, ? super Iterable<Issue>> pIssueAcceptor,
			CancelIndicator cancelIndicator) {

		super.initialize(baseDir, pIssueAcceptor, cancelIndicator);
		this.issueAcceptor = pIssueAcceptor;
	}

	/**
	 * TODO: Integrate to IDE-API.
	 * <p>
	 * This method will clear those issues that relate to a non-source resource.
	 */
	@Override
	public Buildable didClose(URI uri) {
		IProjectConfig projectConfig = getWorkspaceConfig().findProjectContaining(uri);
		final Buildable closedBuildable = super.didClose(uri);

		Buildable cleaningBuildable = new Buildable() {
			@Override
			public List<Delta> build(CancelIndicator cancelIndicator) {
				List<Delta> build = closedBuildable.build(cancelIndicator);
				ISourceFolder sourceFolder = projectConfig.findSourceFolderContaining(uri);
				if (sourceFolder == null) {
					clearIssues(uri);
				}
				return build;
			}
		};
		return cleaningBuildable;
	}

	/**
	 * TODO: Integrate to IDE-API.
	 * <p>
	 * Clears all issues of the given URI
	 */
	public void clearIssues(URI uri) {
		issueAcceptor.apply(uri, Collections.emptyList());
	}

	@Override
	protected IWorkspaceConfig getWorkspaceConfig() {
		return super.getWorkspaceConfig();
	}
}
