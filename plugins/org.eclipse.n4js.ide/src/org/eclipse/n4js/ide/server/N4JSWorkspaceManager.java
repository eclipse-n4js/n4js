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
import org.eclipse.n4js.ide.xtext.server.XBuildManager;
import org.eclipse.n4js.ide.xtext.server.XBuildManager.XBuildable;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.IWorkspaceConfig;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@SuppressWarnings("restriction")
// TODO: Xtext - make WorkspaceManager a @Singleton?
@Singleton
public class N4JSWorkspaceManager extends XWorkspaceManager {

	private Procedure2<? super URI, ? super Iterable<Issue>> issueAcceptor;
	private N4JSBuildManager accessibleBuildManager;

	@Override
	public void initialize(URI pBaseDir, Procedure2<? super URI, ? super Iterable<Issue>> pIssueAcceptor,
			CancelIndicator cancelIndicator) {

		// Stopwatch sw = Stopwatch.createStarted();
		super.initialize(pBaseDir, pIssueAcceptor, cancelIndicator);
		this.issueAcceptor = pIssueAcceptor;
		// System.out.println("Workspace.initialize took: " + sw);
	}

	@Override
	@Inject
	public void setBuildManager(XBuildManager buildManager) {
		super.setBuildManager(buildManager);
		this.accessibleBuildManager = (N4JSBuildManager) buildManager;
	}

	N4JSBuildManager getBuildManager() {
		return accessibleBuildManager;
	}

	/**
	 * TODO: Integrate to IDE-API.
	 * <p>
	 * This method will clear those issues that relate to a non-source resource.
	 */
	@Override
	public XBuildable didClose(URI uri) {
		IProjectConfig projectConfig = getWorkspaceConfig().findProjectContaining(uri);
		final XBuildable closedBuildable = super.didClose(uri);

		XBuildable cleaningBuildable = new XBuildable() {
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
	 * TODO: Xtext - make accessible?
	 *
	 * @return the base directory of the current workspace
	 */
	public URI getBaseDir() {
		return ReflectionUtils.getFieldValue(XWorkspaceManager.class, this, "baseDir");
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
	public IWorkspaceConfig getWorkspaceConfig() {
		return super.getWorkspaceConfig();
	}
}
