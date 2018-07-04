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
package org.eclipse.n4js.ui.containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.ui.internal.WorkspaceCacheAccess;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.xtext.ui.containers.AbstractStorage2UriMapperClient;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper for {@link N4JSAllContainersState} to calculate the visible handles. It also takes project dependencies in
 * consideration during calculation. Handling of changes (project closes, properties file changed) is done in
 * {@link N4JSProjectDescription}.
 * <p/>
 * Uses the project description read in from file manifest.n4mf by {@link N4JSProjectDescription}. So e.g. it can be
 * configured that all files in src and src-test should be part of the container.
 * <p/>
 */
@Singleton
@SuppressWarnings("javadoc")
public class N4JSProjectsStateHelper extends AbstractStorage2UriMapperClient {
	private static final String SOURCE_CONTAINER_PREFIX = "n4jssc:";
	private static final String PROJECT_CONTAINER_PREFIX = "n4jsproj:";

	@Inject
	private IN4JSEclipseCore core;

	@Inject
	private WorkspaceCacheAccess cacheAccess;

	public String initHandle(URI uri) {
		String handle = null;
		Optional<? extends IN4JSSourceContainer> containerOpt = core.findN4JSSourceContainer(uri);
		if (containerOpt.isPresent()) {
			handle = SOURCE_CONTAINER_PREFIX + containerOpt.get().getLocation();
		} else {
			Optional<? extends IN4JSEclipseProject> projectOpt = core.findProject(uri);
			if (projectOpt.isPresent()) {
				handle = PROJECT_CONTAINER_PREFIX + projectOpt.get().getLocation();
			}
		}

		return handle;
	}

	public List<String> initVisibleHandles(String handle) {
		if (handle.startsWith(PROJECT_CONTAINER_PREFIX)) {
			// similar to source-container-prefix but we are only interested in the project/archive and
			// don't have an actual file of the source-locations.
			URI uri = URI.createURI(handle.substring(PROJECT_CONTAINER_PREFIX.length()));

			List<String> result = Lists.newArrayList();
			// Project.
			Optional<? extends IN4JSEclipseProject> containerProjectOpt = core.findProject(uri);
			if (containerProjectOpt.isPresent()) {
				fullCollectLocationHandles(result, containerProjectOpt.get());
			} else {
				// archive
				Optional<? extends IN4JSArchive> containerArchiveOpt = core.findArchive(uri);
				if (containerArchiveOpt.isPresent()) {
					// out of archive
					IN4JSArchive archive = containerArchiveOpt.get();
					fullCollectLocationHandles(result, archive);
				} else {

					// Nothing found.
					return Collections.emptyList();
				}
			}
			return result;
		}
		URI uri = URI.createURI(handle.substring(SOURCE_CONTAINER_PREFIX.length()));
		Optional<? extends IN4JSSourceContainer> containerOpt = core.findN4JSSourceContainer(uri);
		List<String> result = Lists.newArrayList();
		if (containerOpt.isPresent()) {
			IN4JSSourceContainer container = containerOpt.get();
			if (container.isLibrary()) {
				IN4JSArchive archive = container.getLibrary();
				fullCollectLocationHandles(result, archive);
			} else {
				IN4JSProject project = container.getProject();
				fullCollectLocationHandles(result, project);
			}
			return result;
		}
		return Collections.emptyList();
	}

	private void fullCollectLocationHandles(List<String> result, IN4JSProject project) {
		collectLocationHandles(project, result);

		for (IN4JSProject dependency : project.getDependencies()) {
			collectLocationHandles(dependency, result);
		}
		for (IN4JSArchive dependency : project.getLibraries()) {
			collectLocationHandles(dependency, result);
		}
	}

	private void fullCollectLocationHandles(List<String> result, IN4JSArchive archive) {
		collectLocationHandles(archive, result);
		for (IN4JSArchive dependency : archive.getReferencedLibraries()) {
			collectLocationHandles(dependency, result);
		}
	}

	private void collectLocationHandles(IN4JSArchive archive, List<String> result) {
		for (IN4JSSourceContainer container : archive.getSourceContainers()) {
			result.add(SOURCE_CONTAINER_PREFIX + container.getLocation());
		}
	}

	private void collectLocationHandles(IN4JSProject project, List<String> result) {
		for (IN4JSSourceContainer container : project.getSourceContainers()) {
			result.add(SOURCE_CONTAINER_PREFIX + container.getLocation());
		}
	}

	public Collection<URI> initContainedURIs(String handle) {

		// special handling for searching with project-context
		if (handle.startsWith(PROJECT_CONTAINER_PREFIX)) {
			return Collections.emptyList();
		}

		URI uri = URI.createURI(handle.substring(SOURCE_CONTAINER_PREFIX.length()));
		Optional<? extends IN4JSSourceContainer> containerOpt = core.findN4JSSourceContainer(uri);
		Collection<URI> uris = new ArrayList<>();
		if (containerOpt.isPresent()) {
			Iterator<URI> iter = containerOpt.get().iterator();
			while (iter.hasNext()) {
				uris.add(iter.next());
			}
		}
		return uris;

	}

	void clearProjectCache(IProject project) {
		cacheAccess.discardEntry(project);
	}

	void clearProjectCache() {
		cacheAccess.discardEntries();
	}
}
