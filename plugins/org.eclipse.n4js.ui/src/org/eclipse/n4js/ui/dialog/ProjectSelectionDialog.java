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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.ide.IDE;

import com.google.inject.Inject;

import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.utils.UIUtils;

/**
 * Browse dialog to select a IProject of the current workspace.
 *
 * Returns values of type {@link IProject}
 */
public class ProjectSelectionDialog extends ListDialog {

	/**
	 * Create a new project selection dialog
	 */
	@Inject
	public ProjectSelectionDialog(N4JSProjectContentProvider n4jsProjectContentProvider) {
		super(UIUtils.getShell());

		this.setContentProvider(n4jsProjectContentProvider);
		this.setLabelProvider(new N4JSProjectLabelProvider());
		this.setInput(ResourcesPlugin.getWorkspace().getRoot());
		this.setHelpAvailable(false);

	}

	/**
	 * Returns the first result if there is one. {@code null} otherwise
	 */
	public Object getFirstResult() {
		Object[] result = getResult();

		if (null != result && result.length > 0) {
			return result[0];
		}
		return null;
	}

	/**
	 * A content provider for all n4js projects in the given workspace.
	 */
	private static final class N4JSProjectContentProvider implements IStructuredContentProvider {

		@Inject
		private IN4JSCore n4jsCore;

		@Override
		public void dispose() {
			// Nothing to dispose
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// Nothing to update here
		}

		/**
		 * Returns true if the given project is a N4JS project
		 *
		 * @param project
		 *            The eclipse project
		 * @return {@code true} if project is a n4js project, {@code false} otherwise
		 */
		private boolean isN4JSProject(IProject project) {
			URI uri = URI.createPlatformResourceURI(project.getName(), true);
			IN4JSProject n4Project = n4jsCore.findProject(uri).orNull();
			return null != n4Project && n4Project.exists();
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof IContainer) {
				try {
					List<IResource> projects = Arrays.asList(((IContainer) inputElement).members()).stream()
							.filter(member -> member instanceof IProject)
							.filter(project -> isN4JSProject((IProject) project))
							.sorted(new ResourceComparator())
							.collect(Collectors.toList());

					return projects.toArray(new IResource[projects.size()]);

				} catch (CoreException e) {
					e.printStackTrace();
					return null;
				}
			}
			return null;
		}

	}

	/**
	 * A simple comparator for {@link IResource}s
	 */
	private static final class ResourceComparator implements Comparator<IResource> {
		@Override
		public int compare(IResource o1, IResource o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

	/**
	 * A simple label provider for {@link IProject}s
	 */
	private static final class N4JSProjectLabelProvider extends LabelProvider {

		private final Image projectImage = PlatformUI.getWorkbench().getSharedImages()
				.getImage(IDE.SharedImages.IMG_OBJ_PROJECT);

		@Override
		public Image getImage(Object element) {
			return projectImage;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof IResource) {
				return ((IResource) element).getName();
			}
			return super.getText(element);
		}
	}
}
