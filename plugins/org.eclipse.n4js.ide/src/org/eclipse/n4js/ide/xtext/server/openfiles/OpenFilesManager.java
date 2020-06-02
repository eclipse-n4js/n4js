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
package org.eclipse.n4js.ide.xtext.server.openfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@SuppressWarnings("javadoc")
@Singleton
public class OpenFilesManager {

	@Inject
	private XWorkspaceManager workspaceManager;

	@Inject
	private Provider<OpenFileManager> openFileManagerProvider;

	@Inject
	private IResourceDescription.Manager resourceDescriptionManager;

	protected final Map<URI, OpenFileManager> openFiles = new HashMap<>();
	protected final ResourceDescriptionsData sharedDirtyState = new ResourceDescriptionsData(Collections.emptyList());

	public void openFile(URI uri, int version, String content) {
		if (openFiles.containsKey(uri)) {
			return; // FIXME content gets lost in this case!
		}
		OpenFileManager ofm = createOpenFileManager(uri);
		openFiles.put(uri, ofm);
		ofm.initOpenFile(version, content, CancelIndicator.NullImpl); // FIXME use proper indicator!
	}

	public void changeFile(URI uri, int version, Iterable<? extends TextDocumentContentChangeEvent> changes,
			CancelIndicator cancelIndicator) {

		OpenFileManager ofm = openFiles.get(uri);
		if (ofm == null) {
			return;
		}
		ofm.refreshOpenFile(changes, cancelIndicator);
	}

	public void closeFile(URI uri) {
		openFiles.remove(uri);
		sharedDirtyState.removeDescription(uri);
		// TODO closing a file may lead to a change in other open files
		// TODO what about publishing diagnostics, here?
	}

	protected OpenFileManager createOpenFileManager(URI uri) {
		@SuppressWarnings("restriction")
		String projectName = workspaceManager.getProjectConfig(uri).getName();
		OpenFileManager ofm = openFileManagerProvider.get();
		ofm.init(uri, projectName);
		return ofm;
	}

	/**
	 * If the given origin resource set is one that is used to process an open file, then this method will return the
	 * resource set responsible for containing a resource with the given uri; otherwise <code>null</code> is returned.
	 * Might return the origin resource set itself or might return a newly created resource set.
	 */
	// FIXME find better solution for this:
	public ResourceSet getOrCreateResourceSetForURI(ResourceSet origin, URI uri) {
		OpenFileManager ofm = findOpenFileManager(origin);
		if (ofm == null) {
			return null; // origin is not a resource set related to an open file
		}
		return ofm.getResourceSetForURI(uri, true);
	}

	protected OpenFileManager findOpenFileManager(ResourceSet resourceSet) {
		return openFiles.values().stream()
				.filter(ofi -> ofi.containerHandle2ResourceSet.containsValue(resourceSet))
				.findAny().orElse(null);
	}

	private boolean refreshingAffectedOpenFiles = false; // FIXME thread safety!

	protected void onResourceChanged(IResourceDescription.Delta delta, CancelIndicator cancelIndicator) {
		if (refreshingAffectedOpenFiles) {
			return;
		}
		URI changedURI = delta.getUri();
		List<OpenFileManager> affectedOFMs = new ArrayList<>();
		for (OpenFileManager candidateOFM : openFiles.values()) {
			URI candidateURI = candidateOFM.getURI();
			if (candidateURI.equals(changedURI)) {
				continue;
			}
			IResourceDescription candidateDesc = sharedDirtyState.getResourceDescription(candidateURI);
			if (resourceDescriptionManager.isAffected(delta, candidateDesc)) {
				affectedOFMs.add(candidateOFM);
			}
		}
		try {
			refreshingAffectedOpenFiles = true;
			for (OpenFileManager affectedOFM : affectedOFMs) {
				affectedOFM.refreshOpenFile(cancelIndicator);
			}
		} finally {
			refreshingAffectedOpenFiles = false;
		}
	}
}
