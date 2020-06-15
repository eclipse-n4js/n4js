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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

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
 * but with some added functionality such as support for {@link IChunkedIndexListener listeners}.
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

	/** Map of all containers. */
	protected final Map<String, ResourceDescriptionsData> containerHandle2Data = new HashMap<>();
	/** Registered listeners. */
	protected final List<IChunkedIndexListener> listeners = new ArrayList<>();

	/** Listens for changes in a {@link ConcurrentChunkedIndex}. */
	public interface IChunkedIndexListener {
		/** Invoked when the index has changed. */
		public void onIndexChanged(Map<String, ResourceDescriptionsData> changedContainers,
				Set<String> removedContainers);
	}

	/** Removes all containers of this index. */
	public synchronized void clear() {
		ImmutableSet<String> removedContainers = ImmutableSet.copyOf(containerHandle2Data.keySet());
		containerHandle2Data.clear();
		notifyListeners(ImmutableMap.of(), removedContainers);
	}

	/** Returns the data for the container with the given handle or <code>null</code> if no such container. */
	public synchronized ResourceDescriptionsData getContainer(String containerHandle) {
		Objects.requireNonNull(containerHandle);
		return containerHandle2Data.get(containerHandle);
	}

	/** Sets the data for this container. */
	public synchronized ResourceDescriptionsData setContainer(String containerHandle,
			ResourceDescriptionsData newData) {
		Objects.requireNonNull(containerHandle);
		Objects.requireNonNull(newData);
		ResourceDescriptionsData oldData = containerHandle2Data.put(containerHandle, newData);
		notifyListeners(ImmutableMap.of(containerHandle, newData), ImmutableSet.of());
		return oldData;
	}

	/** Removes the container with the given handle from the index. */
	public synchronized ResourceDescriptionsData removeContainer(String containerHandle) {
		Objects.requireNonNull(containerHandle);
		ResourceDescriptionsData oldData = containerHandle2Data.remove(containerHandle);
		if (oldData != null) {
			notifyListeners(ImmutableMap.of(), ImmutableSet.of(containerHandle));
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
	public synchronized void addListener(IChunkedIndexListener listener) {
		listeners.add(listener);
	}

	/** Removes the given listener. */
	public synchronized void removeListener(IChunkedIndexListener listener) {
		listeners.remove(listener);
	}

	/** Notify all {@link #listeners listeners}. */
	protected synchronized void notifyListeners(ImmutableMap<String, ResourceDescriptionsData> changedContainers,
			ImmutableSet<String> removedContainers) {
		for (IChunkedIndexListener l : listeners) {
			l.onIndexChanged(changedContainers, removedContainers);
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
