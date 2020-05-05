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
package org.eclipse.n4js.xtext.workspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

/**
 * Extension of {@link IWorkspaceConfig} to modify workspace during operation
 */
@SuppressWarnings("restriction")
public interface XIWorkspaceConfig extends IWorkspaceConfig {

	/** @return base directory of workspace */
	URI getPath();

	/** Updates internal data based on changes of the given resource */
	UpdateChanges update(URI changedResource);

	static public class UpdateChanges {
		protected List<URI> removedURIs;
		protected List<URI> addedURIs;
		protected List<ISourceFolder> removedSourceFolders;
		protected List<ISourceFolder> addedSourceFolders;

		/** Constructor */
		public UpdateChanges(List<URI> removedURIs, List<URI> addedURIs, List<ISourceFolder> removedSourceFolders,
				List<ISourceFolder> addedSourceFolders) {

			this.removedURIs = removedURIs;
			this.addedURIs = addedURIs;
			this.removedSourceFolders = removedSourceFolders;
			this.addedSourceFolders = addedSourceFolders;
		}

		public List<URI> getRemovedURIs() {
			return removedURIs;
		}

		public List<URI> getAddedURIs() {
			return addedURIs;
		}

		public List<ISourceFolder> getRemovedSourceFolders() {
			return removedSourceFolders;
		}

		public List<ISourceFolder> getAddedSourceFolders() {
			return addedSourceFolders;
		}

		public List<URI> getChangedURIs(IFileSystemScanner scanner) {
			List<URI> uris = new ArrayList<>(addedURIs);
			for (ISourceFolder sourceFolder : addedSourceFolders) {
				uris.addAll(sourceFolder.getAllResources(scanner));
			}
			return uris;
		}

	}
}
