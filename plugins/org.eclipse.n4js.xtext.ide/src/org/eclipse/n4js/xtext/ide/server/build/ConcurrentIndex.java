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
package org.eclipse.n4js.xtext.ide.server.build;

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
import org.eclipse.n4js.xtext.workspace.ProjectSet;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
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
public class ConcurrentIndex {

	@Inject
	private ConfigSnapshotFactory configSnapshotFactory;

	/** Map of all project indices. */
	private final Map<String, ResourceDescriptionsData> projectID2Index = new HashMap<>();
	/** A snapshot of the current workspace configuration. */
	private WorkspaceConfigSnapshot workspaceConfig = null;
	/** Registered listeners. */
	private final List<IIndexListener> listeners = new CopyOnWriteArrayList<>();

	/** Listens for changes in a {@link ConcurrentIndex}. */
	public interface IIndexListener {
		/**
		 * Invoked when the index has changed.
		 * <p>
		 * <b>Attention #1:</b> In case an index for a new project is added and this new index is empty, there is no
		 * event emitted. The reason is to minimize events during startup.
		 * <p>
		 * <b>Attention #2:</b> For important notes on the meaning of the two parameters {@code changedProjects} and
		 * {@code removedProjects}, see method {@link ProjectSet#update(Iterable, Iterable)}.
		 */
		public void onIndexChanged(
				WorkspaceConfigSnapshot newWorkspaceConfig,
				Map<String, ? extends ResourceDescriptionsData> changedDescriptions,
				List<? extends ProjectConfigSnapshot> changedProjects,
				Set<String> removedProjects);
	}

	/** Set an initial workspace configuration. */
	public void initialize(WorkspaceConfigSnapshot initialWorkspaceConfig) {
		ImmutableSet.Builder<String> removedProjectIDsBuilder = ImmutableSet.builder();
		synchronized (this) {
			if (workspaceConfig != null) {
				removedProjectIDsBuilder.addAll(projectID2Index.keySet());
			}
			projectID2Index.clear();
			workspaceConfig = initialWorkspaceConfig;
		}
		ImmutableSet<String> removedProjectIDs = removedProjectIDsBuilder.build();
		if (!initialWorkspaceConfig.getProjects().isEmpty() || !removedProjectIDs.isEmpty()) {
			notifyListeners(initialWorkspaceConfig, ImmutableMap.of(),
					ImmutableList.copyOf(initialWorkspaceConfig.getProjects()), removedProjectIDs);
		}
	}

	/** Removes all projects and their indices. */
	public void removeAllProjects() {
		ImmutableSet<String> removedProjectIDs;
		WorkspaceConfigSnapshot workspaceConfigNew;
		synchronized (this) {
			removedProjectIDs = ImmutableSet.copyOf(projectID2Index.keySet());
			projectID2Index.clear();
			workspaceConfigNew = configSnapshotFactory.clear(workspaceConfig);
			workspaceConfig = workspaceConfigNew;
		}
		notifyListeners(workspaceConfigNew, ImmutableMap.of(), ImmutableList.of(), removedProjectIDs);
	}

	/** Returns the index for the given project name or <code>null</code> if no such project name exists. */
	public synchronized ResourceDescriptionsData getProjectIndex(String projectID) {
		Objects.requireNonNull(projectID);
		return projectID2Index.get(projectID);
	}

	/** Returns the current configuration of the workspace. */
	public synchronized WorkspaceConfigSnapshot getWorkspaceConfigSnapshot() {
		return workspaceConfig;
	}

	/** Returns the project configuration of the given project name. */
	public synchronized ProjectConfigSnapshot getProjectConfig(String projectID) {
		Objects.requireNonNull(projectID);
		return workspaceConfig.findProjectByID(projectID);
	}

	/** Sets the index for the given project name. */
	public ResourceDescriptionsData setProjectIndex(String projectID, ResourceDescriptionsData projectIndex) {
		Objects.requireNonNull(projectID);
		Objects.requireNonNull(projectIndex);
		ResourceDescriptionsData oldProjectIndex;
		WorkspaceConfigSnapshot currWorkspaceConfig;
		synchronized (this) {
			oldProjectIndex = projectID2Index.put(projectID, projectIndex);
			currWorkspaceConfig = workspaceConfig;
		}
		if ((oldProjectIndex != null && !oldProjectIndex.isEmpty()) || !projectIndex.isEmpty()) {
			// check avoids many notifications during initialization
			notifyListeners(currWorkspaceConfig, ImmutableMap.of(projectID, projectIndex), ImmutableList.of(),
					ImmutableSet.of());
		}
		return oldProjectIndex;
	}

	/** Sets the contents of the project with the given name to the empty set, but does not remove the project. */
	public ResourceDescriptionsData clearProjectIndex(String projectID) {
		return setProjectIndex(projectID, new ResourceDescriptionsData(Collections.emptyList()));
	}

	/**
	 * Add or remove projects, or change the configuration of existing projects.
	 * <p>
	 * For important notes on the meaning of the two parameters, see method
	 * {@link ProjectSet#update(Iterable, Iterable)}.
	 */
	public WorkspaceConfigSnapshot changeOrRemoveProjects(Iterable<? extends ProjectConfigSnapshot> changedProjects,
			Iterable<String> removedProjectIDs) {

		Objects.requireNonNull(changedProjects);
		Objects.requireNonNull(removedProjectIDs);
		ImmutableList<? extends ProjectConfigSnapshot> changedProjectsCpy = ImmutableList.copyOf(changedProjects);
		ImmutableSet<String> removedProjectIDsCpy = ImmutableSet.copyOf(removedProjectIDs);
		WorkspaceConfigSnapshot newWorkspaceConfig;
		ImmutableSet.Builder<String> actuallyRemovedProjectsBuilder = ImmutableSet.builder();
		synchronized (this) {
			for (String removedProjectID : removedProjectIDsCpy) {
				if (projectID2Index.remove(removedProjectID) != null) {
					actuallyRemovedProjectsBuilder.add(removedProjectID);
				}
			}

			newWorkspaceConfig = configSnapshotFactory.update(workspaceConfig, changedProjectsCpy,
					removedProjectIDsCpy);

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
						projectID2Index.entrySet(),
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
			ImmutableSet<String> removedProjectIDs) {

		for (IIndexListener l : listeners) {
			l.onIndexChanged(newWorkspaceConfig, changedDescriptions, changedProjects, removedProjectIDs);
		}
	}

	/** Create a snapshot of this index. */
	public synchronized ChunkedResourceDescriptions toDescriptions() {
		return new ChunkedResourceDescriptions(projectID2Index);
	}

	/** Create a snapshot of this index and attach it to the given resource set. */
	public synchronized ChunkedResourceDescriptions toDescriptions(ResourceSet resourceSet) {
		return new ChunkedResourceDescriptions(projectID2Index, resourceSet);
	}
}
