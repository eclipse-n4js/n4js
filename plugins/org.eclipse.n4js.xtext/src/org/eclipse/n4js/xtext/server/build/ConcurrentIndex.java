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
package org.eclipse.n4js.xtext.server.build;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XWorkspaceConfigSnapshotProvider;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A concurrent map of project names to their {@link ResourceDescriptionsData project index}, similar to using a
 * {@link ConcurrentMap} but with some added functionality such as tracking visible projects and support for
 * {@link IIndexListener listeners}.
 * <p>
 * IMPORTANT: just as {@link ConcurrentMap}, this class does not ensure the thread-safety of the values (i.e. the
 * {@link ResourceDescriptionsData} instances) being passed in. Thus, client code must either
 * <ul>
 * <li>be sure to not modify a {@code ResourceDescriptionsData} instance after passing it to / retrieving it from this
 * class OR
 * <li>must create a {@link ResourceDescriptionsData#copy() copy}.
 * </ul>
 */
@Singleton
public class ConcurrentIndex implements XWorkspaceConfigSnapshotProvider {

	@Inject
	private ConfigSnapshotFactory configSnapshotFactory;

	/** Map of all project indices. */
	private final Map<String, ResourceDescriptionsData> project2Index = new HashMap<>();
	/** A snapshot of the current workspace configuration. */
	private WorkspaceConfigSnapshot workspaceConfig = null;
	/** Registered listeners. */
	private final List<IIndexListener> listeners = new CopyOnWriteArrayList<>();

	/** Listens for changes in a {@link ConcurrentIndex}. */
	public interface IIndexListener {
		/**
		 * Invoked when the index has changed.
		 * <p>
		 * <b>Attention:</b> In case an index for a new project is added and this new index is empty, there is no event
		 * emitted. The reason is to minimize events during startup.
		 */
		public void onIndexChanged(
				WorkspaceConfigSnapshot newWorkspaceConfig,
				Map<String, ? extends ResourceDescriptionsData> changedDescriptions,
				List<? extends ProjectConfigSnapshot> changedProjects,
				Set<String> removedProjects);
	}

	/** Set an initial workspace configuration. */
	public void initialize(WorkspaceConfigSnapshot initialWorkspaceConfig) {
		ImmutableSet.Builder<String> removedProjectsBuilder = ImmutableSet.builder();
		synchronized (this) {
			if (workspaceConfig != null) {
				removedProjectsBuilder.addAll(project2Index.keySet());
			}
			project2Index.clear();
			workspaceConfig = initialWorkspaceConfig;
		}
		ImmutableSet<String> removedProjects = removedProjectsBuilder.build();
		if (!initialWorkspaceConfig.getProjects().isEmpty() || !removedProjects.isEmpty()) {
			notifyListeners(initialWorkspaceConfig, ImmutableMap.of(),
					ImmutableList.copyOf(initialWorkspaceConfig.getProjects()), removedProjects);
		}
	}

	/** Removes all projects and their indices. */
	public void removeAllProjects() {
		ImmutableSet<String> removedProjectNames;
		WorkspaceConfigSnapshot workspaceConfigNew;
		synchronized (this) {
			removedProjectNames = ImmutableSet.copyOf(project2Index.keySet());
			project2Index.clear();
			workspaceConfigNew = configSnapshotFactory.clear(workspaceConfig);
			workspaceConfig = workspaceConfigNew;
		}
		notifyListeners(workspaceConfigNew, ImmutableMap.of(), ImmutableList.of(), removedProjectNames);
	}

	/** Returns the index for the given project name or <code>null</code> if no such project name exists. */
	public synchronized ResourceDescriptionsData getProjectIndex(String projectName) {
		Objects.requireNonNull(projectName);
		return project2Index.get(projectName);
	}

	@Override
	public synchronized WorkspaceConfigSnapshot getWorkspaceConfigSnapshot() {
		return workspaceConfig;
	}

	/** Returns the project configuration of the given project name. */
	public synchronized ProjectConfigSnapshot getProjectConfig(String projectName) {
		Objects.requireNonNull(projectName);
		return workspaceConfig.findProjectByName(projectName);
	}

	/** Sets the index for the given project name. */
	public ResourceDescriptionsData setProjectIndex(String projectName, ResourceDescriptionsData projectIndex) {
		Objects.requireNonNull(projectName);
		Objects.requireNonNull(projectIndex);
		ResourceDescriptionsData oldProjectIndex;
		WorkspaceConfigSnapshot currWorkspaceConfig;
		synchronized (this) {
			oldProjectIndex = project2Index.put(projectName, projectIndex);
			currWorkspaceConfig = workspaceConfig;
		}
		if ((oldProjectIndex != null && !oldProjectIndex.isEmpty()) || !projectIndex.isEmpty()) {
			// check avoids many notifications during initialization
			notifyListeners(currWorkspaceConfig, ImmutableMap.of(projectName, projectIndex), ImmutableList.of(),
					ImmutableSet.of());
		}
		return oldProjectIndex;
	}

	/** Sets the contents of the project with the given name to the empty set, but does not remove the project. */
	public ResourceDescriptionsData clearProjectIndex(String projectName) {
		return setProjectIndex(projectName, new ResourceDescriptionsData(Collections.emptyList()));
	}

	/**
	 * Add or remove projects, or change the configuration of existing projects.
	 *
	 * @param changedProjects
	 *            newly added projects and projects with a changed configuration (i.e. one of the properties in
	 *            {@link ProjectConfigSnapshot} has changed).
	 * @param removedProjects
	 *            names of removed projects.
	 */
	public WorkspaceConfigSnapshot changeOrRemoveProjects(Iterable<? extends ProjectConfigSnapshot> changedProjects,
			Iterable<String> removedProjects) {

		Objects.requireNonNull(changedProjects);
		Objects.requireNonNull(removedProjects);
		ImmutableList<? extends ProjectConfigSnapshot> changedProjectsCpy = ImmutableList.copyOf(changedProjects);
		ImmutableSet<String> removedProjectsCpy = ImmutableSet.copyOf(removedProjects);
		WorkspaceConfigSnapshot newWorkspaceConfig;
		ImmutableSet.Builder<String> actuallyRemovedProjectsBuilder = ImmutableSet.builder();
		synchronized (this) {
			for (String removedProjectName : removedProjectsCpy) {
				if (project2Index.remove(removedProjectName) != null) {
					actuallyRemovedProjectsBuilder.add(removedProjectName);
				}
			}

			newWorkspaceConfig = configSnapshotFactory.update(workspaceConfig, changedProjectsCpy, removedProjectsCpy);

			workspaceConfig = newWorkspaceConfig;
		}
		ImmutableSet<String> actuallyRemovedProjects = actuallyRemovedProjectsBuilder.build();
		if (!changedProjectsCpy.isEmpty() || !actuallyRemovedProjects.isEmpty()) {
			notifyListeners(newWorkspaceConfig, ImmutableMap.of(), changedProjectsCpy, actuallyRemovedProjects);
		}
		return newWorkspaceConfig;
	}

	/**
	 * Returns an immutable snapshot of entries in this index (not a view).
	 * <p>
	 * The caveat regarding the non-thread-safety of {@link ResourceDescriptionsData} still applies, see
	 * {@link ConcurrentIndex} for details.
	 */
	public synchronized ImmutableList<Entry<String, ResourceDescriptionsData>> entries() {
		return ImmutableList.<Entry<String, ResourceDescriptionsData>> builder()
				.addAll(Iterables.transform(
						project2Index.entrySet(),
						e -> Maps.immutableEntry(e.getKey(), e.getValue())))
				.build();
	}

	/** Adds the given listener. */
	public void addListener(IIndexListener listener) {
		listeners.add(listener);
	}

	/** Removes the given listener. */
	public void removeListener(IIndexListener listener) {
		listeners.remove(listener);
	}

	/** Notify all {@link #listeners listeners} about a change of resource descriptions. */
	protected /* NOT synchronized */ void notifyListeners(
			WorkspaceConfigSnapshot newWorkspaceConfig,
			ImmutableMap<String, ? extends ResourceDescriptionsData> changedDescriptions,
			ImmutableList<? extends ProjectConfigSnapshot> changedProjects,
			ImmutableSet<String> removedProjects) {

		for (IIndexListener l : listeners) {
			l.onIndexChanged(newWorkspaceConfig, changedDescriptions, changedProjects, removedProjects);
		}
	}

	/** Create a snapshot of this index. */
	public synchronized ChunkedResourceDescriptions toDescriptions() {
		return new ChunkedResourceDescriptions(project2Index);
	}

	/** Create a snapshot of this index and attach it to the given resource set. */
	public synchronized ChunkedResourceDescriptions toDescriptions(ResourceSet resourceSet) {
		return new ChunkedResourceDescriptions(project2Index, resourceSet);
	}
}
