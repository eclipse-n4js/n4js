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
package org.eclipse.n4js.ui.dialog;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.dialogs.ListDialog;

import com.google.inject.Inject;

import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.ImageDescriptorCache;
import org.eclipse.n4js.ui.utils.UIUtils;

/**
 * Provides {@link SourceFolderSelectionDialog}s. Use the createDialog method to instantiate a new dialog.
 *
 * The dialog takes a {@link IProject} as input and returns a {@link String} of the project relative source folder path
 * as single result.
 */
public class SourceFolderSelectionDialog extends ListDialog {

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Create a new source folder selection dialog
	 */
	public SourceFolderSelectionDialog() {
		super(UIUtils.getShell());

		this.setLabelProvider(new SourceFolderLabelProvider());
		this.setContentProvider(new ArrayContentProvider());
		this.setHelpAvailable(false);
	}

	/**
	 * Sets the input of the dialog.
	 *
	 * Only objects of type {@link IProject} are allowed as input.
	 */
	@Override
	public void setInput(Object input) {
		if (!(input instanceof IProject)) {
			throw new IllegalArgumentException("Only inputs of type IProject are allowed");
		}
		// Adapt title
		this.setTitle("Select a source folder in the project " + ((IProject) input).getName());

		// Collect source folders
		List<String> sourceFolders = collectSourceFolders((IProject) input);
		super.setInput(sourceFolders.toArray());
	}

	private List<String> collectSourceFolders(IProject project) {
		if (null == project) {
			return null;
		}

		// Collect source folders for the given project
		URI uri = URI.createPlatformResourceURI(project.getName(), true);
		IN4JSProject n4Project = n4jsCore.findProject(uri).orNull();

		if (null == n4Project) {
			return null;
		}

		return n4Project.getSourceContainers().stream()
				.filter(src -> (src.isTest() || src.isSource())) // Filter out external and library folders
				.map(src -> src.getRelativeLocation())
				.collect(Collectors.toList());
	}

	/**
	 * Returns the selected source container or {@code null} if the wizard didn't complete successfully.
	 */
	public String getFirstResult() {
		Object[] results = getResult();

		if (null != results && results.length > 0) {
			return (String) results[0];
		}

		return null;
	}

	private static class SourceFolderLabelProvider extends LabelProvider {

		private final Image sourceFolderImage = ImageDescriptorCache.ImageRef.SRC_FOLDER.asImage().orNull();

		@Override
		public Image getImage(Object element) {
			return sourceFolderImage;
		}
	}
}
