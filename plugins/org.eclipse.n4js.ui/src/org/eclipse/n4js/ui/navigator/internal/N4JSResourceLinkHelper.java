/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.navigator.internal;

import static org.eclipse.jface.viewers.StructuredSelection.EMPTY;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.ts.ui.navigation.IURIBasedStorage;
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorage;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.n4js.utils.resources.IExternalResource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.internal.navigator.resources.workbench.ResourceLinkHelper;
import org.eclipse.ui.navigator.ILinkHelper;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;
import org.eclipse.xtext.ui.editor.XtextReadonlyEditorInput;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;

import com.google.inject.Inject;

/**
 * Customized {@link ILinkHelper resource link helper} that handles {@link ResourceNode resource node} instances that
 * are representing {@link IExternalResource external resources} in the navigator.
 */
@SuppressWarnings("restriction")
public class N4JSResourceLinkHelper extends ResourceLinkHelper {

	private static final Logger LOGGER = Logger.getLogger(N4JSResourceLinkHelper.class);

	@Inject
	private LanguageSpecificURIEditorOpener languageSpecificURIEditorOpener;

	@Inject
	private N4JSProjectExplorerHelper helper;

	// TODO 1259: Obsolete when virtual nodes are removed
	@Override
	public void activateEditor(final IWorkbenchPage page, final IStructuredSelection selection) {
		if (null != selection && !selection.isEmpty()) {
			final Object firstElement = selection.getFirstElement();
			if (firstElement instanceof ResourceNode) {
				SafeURI<?> nodeLocation = ((ResourceNode) firstElement).getLocation();
				if (nodeLocation.isFile()) {
					final URI uri = nodeLocation.toURI();
					final IEditorInput editorInput = EditorUtils.createEditorInput(new URIBasedStorage(uri));
					final IEditorPart editor = page.findEditor(editorInput);
					if (null != editor) {
						page.bringToTop(editor);
					} else {
						languageSpecificURIEditorOpener.open(uri, true);
					}
					return;
				}
			}
		}
		super.activateEditor(page, selection);
	}

	// TODO 1259: Obsolete when virtual nodes are removed
	@Override
	public IStructuredSelection findSelection(IEditorInput input) {

		final IStructuredSelection selection = super.findSelection(input);
		if (null == selection || selection.isEmpty() && input instanceof XtextReadonlyEditorInput) {
			try {
				final IStorage storage = ((XtextReadonlyEditorInput) input).getStorage();
				if (storage instanceof IURIBasedStorage) {
					final URI uri = ((IURIBasedStorage) storage).getURI();
					if (uri.isFile()) {
						final File file = new File(uri.toFileString());
						if (file.isFile()) {
							final Node node = getResourceNode(file);
							if (null != node) {
								return new StructuredSelection(node);
							}
						}
					}
				}
			} catch (final CoreException e) {
				LOGGER.error("Error while extracting storage from read-only Xtext editor input.", e);
				return EMPTY;
			}
		}
		return selection;
	}

	private Node getResourceNode(File fileResource) {

		for (final IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			final Node[] nodes = helper.getVirtualNodesForProject(project);
			if (!Arrays2.isEmpty(nodes)) {
				final Node node = nodes[0].findChild(fileResource);
				if (null != node) {
					return node;
				}
			}
		}

		return null;
	}
}
