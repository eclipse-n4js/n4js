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
package org.eclipse.n4js.internal.lsp;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.lsp.IN4JSSourceFolder;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

/**
 * Wrapper around {@link IN4JSProject}.
 */
@SuppressWarnings("restriction")
public class N4JSProjectConfig implements IProjectConfig {

	private final IWorkspaceConfig workspace;
	private final IN4JSProject delegate;

	/**
	 * Constructor
	 */
	public N4JSProjectConfig(IWorkspaceConfig workspace, IN4JSProject delegate) {
		this.workspace = workspace;
		this.delegate = delegate;
	}

	@Override
	public String getName() {
		return delegate.getProjectName().getRawName();
	}

	/** @return the wrapped n4js project. */
	public IN4JSProject toProject() {
		return delegate;
	}

	@Override
	public URI getPath() {
		return delegate.getLocation().withTrailingPathDelimiter().toURI();
	}

	private class SourceContainerForPackageJson implements IN4JSSourceFolder {
		private final URI pckjsonURI;

		SourceContainerForPackageJson() {
			pckjsonURI = delegate.getLocation().appendSegment(N4JSGlobals.PACKAGE_JSON).toURI();
		}

		@Override
		public String getName() {
			return N4JSGlobals.PACKAGE_JSON;
		}

		@Override
		public List<URI> getAllResources(IFileSystemScanner scanner) {
			return Collections.singletonList(pckjsonURI);
		}

		@Override
		public IProjectConfig getProject() {
			return N4JSProjectConfig.this;
		}

		@Override
		public boolean contains(URI uri) {
			return pckjsonURI.equals(uri);
		}

		@Override
		public URI getPath() {
			return delegate.getLocation().toURI();
		}
	}

	@Override
	public Set<? extends IN4JSSourceFolder> getSourceFolders() {
		Set<IN4JSSourceFolder> sourceFolders = new LinkedHashSet<>();
		delegate.getSourceContainers().forEach(container -> sourceFolders.add(new N4JSSourceFolder(this, container)));
		sourceFolders.add(new SourceContainerForPackageJson());
		return sourceFolders;
	}

	@Override
	public IN4JSSourceFolder findSourceFolderContaining(URI member) {
		IN4JSSourceContainer sourceContainer = delegate.findSourceContainerWith(member);
		if (sourceContainer == null) {
			return null;
		}
		return new N4JSSourceFolder(this, sourceContainer);
	}

	/** @return the output folders of this project */
	public List<URI> getOutputFolders() {
		return Collections.singletonList(delegate.getLocation().appendPath(delegate.getOutputPath()).toURI());
	}

	@Override
	public IWorkspaceConfig getWorkspaceConfig() {
		return workspace;
	}

	/** @return true iff this project should be indexed only */
	public boolean indexOnly() {
		URI projectBase = getPath();
		String lastSegment = projectBase.lastSegment();
		if (lastSegment == null || lastSegment.isBlank()) {
			projectBase = projectBase.trimSegments(1);
		}
		projectBase = projectBase.trimSegments(1); // trim the project name
		lastSegment = projectBase.lastSegment();
		if (lastSegment != null && lastSegment.startsWith("@")) {
			projectBase = projectBase.trimSegments(1);
			lastSegment = projectBase.lastSegment();
		}
		if (lastSegment != null && N4JSGlobals.NODE_MODULES.equals(lastSegment)) {
			// index only true for npm libraries
			return true;
		}

		return false;
	}

}
