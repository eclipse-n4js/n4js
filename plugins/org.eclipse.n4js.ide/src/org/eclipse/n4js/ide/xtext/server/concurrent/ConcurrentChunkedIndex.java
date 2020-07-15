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
package org.eclipse.n4js.ide.xtext.server.concurrent;

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
import org.eclipse.n4js.xtext.workspace.IProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.IWorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;

/**
 * A concurrent map of container handles to {@link ResourceDescriptionsData}, similar to using a {@link ConcurrentMap}
 * but with some added functionality such as tracking visible containers and support for {@link IChunkedIndexListener
 * listeners}.
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
public class ConcurrentChunkedIndex {

	/** Map of all resource descriptions per container. */
	protected final Map<String, ResourceDescriptionsData> containerHandle2Data = new HashMap<>();
	/** A snapshot of the current workspace configuration. */
	protected IWorkspaceConfigSnapshot workspaceConfig = null;
	/** Registered listeners. */
	protected final List<IChunkedIndexListener> listeners = new CopyOnWriteArrayList<>();

	/** Listens for changes in a {@link ConcurrentChunkedIndex}. */
	public interface IChunkedIndexListener {
		/** Invoked when the index has changed. */
		public void onIndexChanged(
				IWorkspaceConfigSnapshot newWorkspaceConfig,
				Map<String, ? extends ResourceDescriptionsData> changedDescriptions,
				List<? extends IProjectConfigSnapshot> changedProjectConfigs,
				Set<String> removedContainers);
	}

	/** Set an initial workspace configuration. This method won't notify listeners. */
	public synchronized void initialize(IWorkspaceConfigSnapshot initialWorkspaceConfig) {
		workspaceConfig = initialWorkspaceConfig;
	}

	/** Removes all containers from this index. */
	public void removeAllContainers() {
		ImmutableSet<String> removedContainers;
		IWorkspaceConfigSnapshot workspaceConfigNew;
		synchronized (this) {
			removedContainers = ImmutableSet.copyOf(containerHandle2Data.keySet());
			containerHandle2Data.clear();
			workspaceConfigNew = workspaceConfig.clear();
			workspaceConfig = workspaceConfigNew;
		}
		notifyListeners(workspaceConfigNew, ImmutableMap.of(), ImmutableList.of(), removedContainers);
	}

	/** Returns the data for the container with the given handle or <code>null</code> if no such container. */
	public synchronized ResourceDescriptionsData getContainer(String containerHandle) {
		Objects.requireNonNull(containerHandle);
		return containerHandle2Data.get(containerHandle);
	}

	/** Returns the project configuration of the container with the given handle. */
	public synchronized IProjectConfigSnapshot getProjectConfig(String projectName) {
		Objects.requireNonNull(projectName);
		return workspaceConfig.findProjectByName(projectName);
	}

	/** Sets the resource descriptions for the container with the given handle. */
	public ResourceDescriptionsData setContainer(String containerHandle, ResourceDescriptionsData newData) {
		Objects.requireNonNull(containerHandle);
		Objects.requireNonNull(newData);
		ResourceDescriptionsData oldData;
		IWorkspaceConfigSnapshot newWorkspaceConfig;
		synchronized (this) {
			oldData = containerHandle2Data.put(containerHandle, newData);
			newWorkspaceConfig = workspaceConfig;
		}
		notifyListeners(newWorkspaceConfig, ImmutableMap.of(containerHandle, newData), ImmutableList.of(),
				ImmutableSet.of());
		return oldData;
	}

	/** Sets the given project configuration. */
	public void setProjectConfigSnapshot(IProjectConfigSnapshot projectConfig) {
		setProjectConfigSnapshots(Collections.singletonList(projectConfig));
	}

	/** Sets the given project configurations. */
	public void setProjectConfigSnapshots(Iterable<? extends IProjectConfigSnapshot> projectConfigs) {
		Objects.requireNonNull(projectConfigs);
		ImmutableList<? extends IProjectConfigSnapshot> changedProjectConfigs = ImmutableList.copyOf(projectConfigs);
		IWorkspaceConfigSnapshot newWorkspaceConfig;
		synchronized (this) {
			newWorkspaceConfig = workspaceConfig.update(changedProjectConfigs, Collections.emptyList());
			workspaceConfig = newWorkspaceConfig;
		}
		notifyListeners(newWorkspaceConfig, ImmutableMap.of(), changedProjectConfigs, ImmutableSet.of());
	}

	/** Sets the contents of the container with the given handle to the empty set, but does not remove the container. */
	public ResourceDescriptionsData clearContainer(String containerHandle) {
		return setContainer(containerHandle, new ResourceDescriptionsData(Collections.emptyList()));
	}

	/** Removes the container with the given handle from the index. */
	public ResourceDescriptionsData removeContainer(String containerHandle) {
		Objects.requireNonNull(containerHandle);
		ResourceDescriptionsData oldData;
		IWorkspaceConfigSnapshot newWorkspaceConfig;
		synchronized (this) {
			oldData = containerHandle2Data.remove(containerHandle);
			newWorkspaceConfig = workspaceConfig.update(Collections.emptyList(),
					Collections.singletonList(containerHandle));
			workspaceConfig = newWorkspaceConfig;
		}
		if (oldData != null) {
			notifyListeners(newWorkspaceConfig, ImmutableMap.of(), ImmutableList.of(),
					ImmutableSet.of(containerHandle));
		}
		return oldData;
	}

	/**
	 * Returns an immutable snapshot of entries in this index (not a view).
	 * <p>
	 * The caveat regarding the non-thread-safety of {@link ResourceDescriptionsData} still applies, see
	 * {@link ConcurrentChunkedIndex} for details.
	 */
	public synchronized ImmutableList<Entry<String, ResourceDescriptionsData>> entries() {
		return ImmutableList.<Entry<String, ResourceDescriptionsData>> builder()
				.addAll(Iterables.transform(
						containerHandle2Data.entrySet(),
						e -> Maps.immutableEntry(e.getKey(), e.getValue())))
				.build();
	}

	/** Adds the given listener. */
	public void addListener(IChunkedIndexListener listener) {
		listeners.add(listener);
	}

	/** Removes the given listener. */
	public void removeListener(IChunkedIndexListener listener) {
		listeners.remove(listener);
	}

	/** Notify all {@link #listeners listeners} about a change of resource descriptions. */
	protected /* NOT synchronized */ void notifyListeners(
			IWorkspaceConfigSnapshot newWorkspaceConfig,
			ImmutableMap<String, ? extends ResourceDescriptionsData> changedDescriptions,
			ImmutableList<? extends IProjectConfigSnapshot> changedVisibleContainers,
			ImmutableSet<String> removedContainers) {
		for (IChunkedIndexListener l : listeners) {
			l.onIndexChanged(newWorkspaceConfig, changedDescriptions, changedVisibleContainers, removedContainers);
		}
	}

	/** Create a snapshot of this index. */
	public synchronized ChunkedResourceDescriptions toDescriptions() {
		return new ChunkedResourceDescriptions(containerHandle2Data);
	}

	/** Create a snapshot of this index and attach it to the given resource set. */
	public synchronized ChunkedResourceDescriptions toDescriptions(ResourceSet resourceSet) {
		return new ChunkedResourceDescriptions(containerHandle2Data, resourceSet);
	}
}
