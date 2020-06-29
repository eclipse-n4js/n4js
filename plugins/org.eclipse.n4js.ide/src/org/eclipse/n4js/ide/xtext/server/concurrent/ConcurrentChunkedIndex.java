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
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

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
public class ConcurrentChunkedIndex {

	/** Map of all resource descriptions per container. */
	protected final Map<String, ResourceDescriptionsData> containerHandle2Data = new HashMap<>();
	/** Map of all visible containers per container. */
	protected final Map<String, ImmutableSet<String>> containerHandle2VisibleContainers = new HashMap<>();
	/** Registered listeners. */
	protected final List<IChunkedIndexListener> listeners = new CopyOnWriteArrayList<>();

	/** Listens for changes in a {@link ConcurrentChunkedIndex}. */
	public interface IChunkedIndexListener {
		/** Invoked when the index has changed. */
		public void onIndexChanged(
				Map<String, ResourceDescriptionsData> changedDescriptions,
				Map<String, ImmutableSet<String>> changedVisibleContainers,
				Set<String> removedContainers);
	}

	/** Removes all containers from this index. */
	public void removeAllContainers() {
		ImmutableSet<String> removedContainers;
		synchronized (this) {
			removedContainers = ImmutableSet.copyOf(containerHandle2Data.keySet());
			containerHandle2Data.clear();
			containerHandle2VisibleContainers.clear();
		}
		notifyListeners(ImmutableMap.of(), ImmutableMap.of(), removedContainers);
	}

	/** Returns the data for the container with the given handle or <code>null</code> if no such container. */
	public synchronized ResourceDescriptionsData getContainer(String containerHandle) {
		Objects.requireNonNull(containerHandle);
		return containerHandle2Data.get(containerHandle);
	}

	/** Returns the set of handles of those containers that are visible from the container with the given handle. */
	public synchronized Set<String> getVisibleContainers(String containerHandle) {
		Objects.requireNonNull(containerHandle);
		return containerHandle2VisibleContainers.get(containerHandle);
	}

	/** Sets the resource descriptions for the container with the given handle. */
	public ResourceDescriptionsData setContainer(String containerHandle, ResourceDescriptionsData newData) {
		Objects.requireNonNull(containerHandle);
		Objects.requireNonNull(newData);
		ResourceDescriptionsData oldData;
		synchronized (this) {
			oldData = containerHandle2Data.put(containerHandle, newData);
		}
		notifyListeners(ImmutableMap.of(containerHandle, newData), ImmutableMap.of(), ImmutableSet.of());
		return oldData;
	}

	/** Sets the containers visible from the container with the given handle. */
	public void setVisibleContainers(String containerHandle, Iterable<String> visibleContainers) {
		setVisibleContainers(ImmutableMap.of(containerHandle, visibleContainers));
	}

	/** Sets the containers visible from the container with the given handle. */
	public void setVisibleContainers(Map<String, ? extends Iterable<String>> containerHandle2VisibleContainerHandles) {
		Objects.requireNonNull(containerHandle2VisibleContainerHandles);
		ImmutableMap.Builder<String, ImmutableSet<String>> builder = ImmutableMap.builder();
		for (Entry<String, ? extends Iterable<String>> entry : containerHandle2VisibleContainerHandles.entrySet()) {
			builder.put(entry.getKey(), ImmutableSet.copyOf(entry.getValue()));
		}
		ImmutableMap<String, ImmutableSet<String>> changedVisibleContainers = builder.build();
		synchronized (this) {
			containerHandle2VisibleContainers.putAll(changedVisibleContainers);
		}
		notifyListeners(ImmutableMap.of(), changedVisibleContainers, ImmutableSet.of());
	}

	/** Sets the contents of the container with the given handle to the empty set, but does not remove the container. */
	public ResourceDescriptionsData clearContainer(String containerHandle) {
		return setContainer(containerHandle, new ResourceDescriptionsData(Collections.emptyList()));
	}

	/** Removes the container with the given handle from the index. */
	public ResourceDescriptionsData removeContainer(String containerHandle) {
		Objects.requireNonNull(containerHandle);
		ResourceDescriptionsData oldData;
		synchronized (this) {
			oldData = containerHandle2Data.remove(containerHandle);
			containerHandle2VisibleContainers.remove(containerHandle);
		}
		if (oldData != null) {
			notifyListeners(ImmutableMap.of(), ImmutableMap.of(), ImmutableSet.of(containerHandle));
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
			ImmutableMap<String, ResourceDescriptionsData> changedDescriptions,
			ImmutableMap<String, ImmutableSet<String>> changedVisibleContainers,
			ImmutableSet<String> removedContainers) {
		for (IChunkedIndexListener l : listeners) {
			l.onIndexChanged(changedDescriptions, changedVisibleContainers, removedContainers);
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
