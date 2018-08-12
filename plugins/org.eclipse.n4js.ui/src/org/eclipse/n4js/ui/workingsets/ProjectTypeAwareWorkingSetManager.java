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
package org.eclipse.n4js.ui.workingsets;

import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.n4js.projectDescription.ProjectType.API;
import static org.eclipse.xtext.util.Strings.toFirstUpper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;

/**
 * N4JS project type aware working set manager implementation.
 */
public class ProjectTypeAwareWorkingSetManager extends WorkingSetManagerImpl {

	@Inject
	private IN4JSCore core;

	@Override
	public String getLabel() {
		return "N4JS Project Type";
	}

	@Override
	public Optional<Image> getImage() {
		return ImageRef.TYPES.asImage();
	}

	@Override
	protected List<WorkingSet> initializeWorkingSets() {
		final Collection<ProjectType> types = newArrayList(ProjectType.values());
		types.add(null); // For 'Other Projects'.
		return newArrayList(from(types)
				.transform(type -> new ProjectTypeWorkingSet(type, core, ProjectTypeAwareWorkingSetManager.this)));
	}

	/**
	 * Working set for logically grouping N4JS projects based on their project type.
	 */
	public static final class ProjectTypeWorkingSet extends WorkingSetImpl {

		private final ProjectType type;
		private final IN4JSCore core;

		private static String typeToId(ProjectType type) {
			if (null == type) {
				return OTHERS_WORKING_SET_ID;
			}

			return API.equals(type)
					? API.getLiteral()
					: toFirstUpper(nullToEmpty(type.getLiteral()).replaceAll("_", " ").toLowerCase());
		}

		/**
		 * Creates a new working set instance with the optional {@link ProjectType N4JS project type} and the container
		 * working set manager.
		 *
		 * @param type
		 *            the associated project type. Could be {@code null} if the working set is for
		 *            {@link WorkingSet#OTHERS_WORKING_SET_ID <em>'Other Project'</em>} purposes.
		 * @param core
		 *            the N4JS core to retrieve N4JS specific properties of the grouped projects.
		 * @param manager
		 *            the container manager.
		 */
		public ProjectTypeWorkingSet(/* nullable */ final ProjectType type, final IN4JSCore core,
				final WorkingSetManager manager) {

			super(typeToId(type), manager);
			this.type = type;
			this.core = core;
		}

		@Override
		public IAdaptable[] getElements() {
			final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			final IProject[] elements = new IProject[projects.length];
			int elementCount = 0;
			for (int i = 0, size = projects.length; i < size; i++) {
				final IProject project = projects[i];
				final IN4JSProject n4Project = core.findProject(toUri(project)).orNull();
				if (type == null) { // Other Projects
					if (n4Project == null || !n4Project.exists()) {
						elements[elementCount++] = project;
					}
				} else {
					if (n4Project != null && n4Project.exists() && type.equals(n4Project.getProjectType())) {
						elements[elementCount++] = project;
					}
				}
			}
			return Arrays.copyOfRange(elements, 0, elementCount);
		}

		/**
		 * Returns with the wrapped N4JS project type, or {@code null} if the working set has no associated project
		 * type.
		 *
		 * @return the project type or {@code null}.
		 */
		public ProjectType getType() {
			return type;
		}

		private URI toUri(final IProject project) {
			return URI.createPlatformResourceURI(project.getName(), true);
		}

	}

}
