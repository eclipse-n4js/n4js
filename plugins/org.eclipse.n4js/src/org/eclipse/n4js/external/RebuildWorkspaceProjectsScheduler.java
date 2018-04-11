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
package org.eclipse.n4js.external;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.utils.collections.Arrays2;

import com.google.common.collect.Iterables;

/**
 * Schedules on-demand Eclipse workspace project builds. Requires running platform.
 */
@SuppressWarnings("restriction")
public class RebuildWorkspaceProjectsScheduler {

	/**
	 * Schedules a build with the given projects. Does nothing if the {@link Platform platform} is not running, or the
	 * iterable of projects is empty.
	 *
	 * @param toUpdate
	 *            an iterable of projects to build.
	 */
	public void scheduleBuildIfNecessary(final Iterable<IProject> toUpdate) {
		if (Platform.isRunning() && !Iterables.isEmpty(toUpdate)) {
			final Workspace workspace = (Workspace) ResourcesPlugin.getWorkspace();
			final IBuildConfiguration[] projectsToReBuild = from(asList(workspace.getBuildOrder()))
					.filter(config -> Iterables.contains(toUpdate, config.getProject()))
					.toArray(IBuildConfiguration.class);

			if (!Arrays2.isEmpty(projectsToReBuild)) {
				new RebuildWorkspaceProjectsJob(projectsToReBuild).schedule();
			}
		}
	}

}
