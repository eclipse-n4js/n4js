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

import static com.google.common.collect.FluentIterable.from;
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.google.inject.Inject;

import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 * Property tester implementation checking whether the tester receiver can be an N4JS source container or not. Provides
 * {@code true} result if the selection is an {@link IResource},
 * <ul>
 * <li>and represents an accessible N4JS project, or</li>
 * <li>the selection is on a folder and the folder is a source container of an accessible N4JS project, or</li>
 * <li>the selection is on a folder that is a sub-folder of a source container in an accessible N4JS project.</li>
 * </ul>
 * Otherwise returns with {@code false} test result.
 * <p>
 * This property tester is used for including common wizard in the common navigator when N4JS content is enabled.
 */
public class N4JSSourceContainerPropertyTester extends PropertyTester {

	@Inject
	private IN4JSCore core;

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		if (receiver instanceof IResource) {

			final IResource resource = (IResource) receiver;

			if (resource instanceof IProject) {

				final IN4JSProject n4Project = getN4Project((IProject) resource);
				return isExistingN4Project(n4Project);

			} else if (resource instanceof IFolder) {

				final IProject project = resource.getProject();
				if (null != project && project.isAccessible()) {
					final IN4JSProject n4Project = getN4Project(project);
					if (isExistingN4Project(n4Project)) {

						final String relativePath = resource.getProjectRelativePath().toString();
						// Source container itself.
						if (isSourceContainer(n4Project, relativePath)) {
							return true;
						}

						// The parent is a folder check that recursively.
						if (resource.getParent() instanceof IFolder) {
							return test(resource.getParent(), property, args, expectedValue);
						}

					}
				}

			}
		}
		return false;
	}

	private boolean isSourceContainer(final IN4JSProject n4Project, final String relativePath) {
		return from(n4Project.getSourceContainers())
				.transform(src -> src.getRelativeLocation())
				.contains(relativePath);
	}

	private IN4JSProject getN4Project(final IProject project) {
		return core.findProject(createPlatformResourceURI(project.getName(), true)).orNull();
	}

	private boolean isExistingN4Project(final IN4JSProject n4Project) {
		return null != n4Project && n4Project.exists() && !n4Project.isExternal();
	}

}
