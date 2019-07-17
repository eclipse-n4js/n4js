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
import org.eclipse.n4js.projectModel.lsp.IN4JSProjectConfig;
import org.eclipse.n4js.projectModel.lsp.IN4JSSourceFolder;
import org.eclipse.n4js.projectModel.lsp.IN4JSWorkspaceConfig;
import org.eclipse.xtext.generator.URIBasedFileSystemAccess;

/**
 * Wrapper around {@link IN4JSProject}.
 */
public class N4JSProjectConfig implements IN4JSProjectConfig {

	private final IN4JSWorkspaceConfig workspace;
	private final IN4JSProject delegate;

	/**
	 * Constructor
	 */
	public N4JSProjectConfig(IN4JSWorkspaceConfig workspace, IN4JSProject delegate) {
		this.workspace = workspace;
		this.delegate = delegate;
	}

	@Override
	public String getName() {
		return delegate.getProjectName().getRawName();
	}

	@Override
	public IN4JSProject toProject() {
		return delegate;
	}

	/**
	 * FIXME: MISSING JAVA-DOC<br/>
	 * THIS METHOD MUST RETURN AN URI THAT ENDS WITH '/'.<br/>
	 * ADD TO JAVA-DOC
	 * <p>
	 * Otherwise, {@link URIBasedFileSystemAccess#getURI(String, String)} will omit the project directory, ie. the last
	 * directory. The getURI call happens during {@link URIBasedFileSystemAccess#generateFile(String, CharSequence)
	 * generateFile}.
	 */
	@Override
	public URI getPath() {
		return delegate.getLocation().withTrailingPathDelimiter().toURI();
	}

	private class SourceContainerForPackageJson implements IN4JSSourceFolder {
		@Override
		public String getName() {
			return N4JSGlobals.PACKAGE_JSON;
		}

		@Override
		public List<URI> getAllResources() {
			return Collections
					.singletonList(delegate.getLocation().appendSegment(N4JSGlobals.PACKAGE_JSON).toURI());
		}

		@Override
		public IN4JSProjectConfig getProject() {
			return N4JSProjectConfig.this;
		}

		@Override
		public boolean contains(URI uri) {
			return getAllResources().contains(uri);
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

	@Override
	public List<URI> getOutputFolders() {
		return Collections.singletonList(delegate.getLocation().appendPath(delegate.getOutputPath()).toURI());
	}

	@Override
	public IN4JSWorkspaceConfig getWorkspaceConfig() {
		return workspace;
	}

}
