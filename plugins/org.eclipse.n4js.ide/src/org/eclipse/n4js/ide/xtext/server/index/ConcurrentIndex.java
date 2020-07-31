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
package org.eclipse.n4js.ide.xtext.server.index;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.CoarseGrainedChangeEvent;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.Wrapper;

import com.google.common.collect.ImmutableSet;
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

	/**
	 * TODO Allow to guard external access patterns that need more than one value, e.g. workspaceIndex and
	 * workspaceConfig together.
	 */
	private final ReentrantReadWriteLock lock;

	/** The current workspace index */
	private ImmutableChunkedResourceDescriptions workspaceIndex;

	/** A snapshot of the current workspace configuration. */
	private WorkspaceConfigSnapshot workspaceConfig = null;

	/** Registered listeners. */
	private final List<IIndexListener> listeners = new CopyOnWriteArrayList<>();

	/** Listens for changes in a {@link ConcurrentIndex}. */
	public interface IIndexListener {
		/** Invoked when the index has changed. */
		public void onIndexChanged(
				ImmutableChunkedResourceDescriptions newWorkspaceIndex,
				WorkspaceConfigSnapshot newWorkspaceConfig,
				IResourceDescription.Event event);
	}

	/**
	 * Constructor for the index of an empty workspace.
	 */
	public ConcurrentIndex() {
		this.lock = new ReentrantReadWriteLock(true);
		this.workspaceIndex = ImmutableChunkedResourceDescriptions.empty();
		this.workspaceConfig = null;
	}

	/** Set an initial workspace configuration. This method won't notify listeners. */
	public void initialize(WorkspaceConfigSnapshot initialWorkspaceConfig) {
		lock.writeLock().lock();
		try {
			workspaceConfig = initialWorkspaceConfig;
		} finally {
			lock.writeLock().unlock();
		}
	}

	/** Removes all projects from this index. */
	public void removeAllProjectsIndices() {
		ImmutableChunkedResourceDescriptions newWorkspaceIndex;
		lock.writeLock().lock();
		WorkspaceConfigSnapshot newWorkspaceConfig;
		try {
			ExtendedChunkedResourceDescriptions mutable = workspaceIndex.builder();
			mutable.clear();
			newWorkspaceIndex = mutable.snapshot();
			workspaceIndex = newWorkspaceIndex;
			workspaceConfig = newWorkspaceConfig = workspaceConfig.clear();
		} finally {
			lock.writeLock().unlock();
		}
		notifyListeners(newWorkspaceIndex, newWorkspaceConfig, new CoarseGrainedChangeEvent());
	}

	/**
	 * Returns the current workspace index
	 */
	public ImmutableChunkedResourceDescriptions getWorkspaceIndex() {
		lock.readLock().lock();
		try {
			return workspaceIndex;
		} finally {
			lock.readLock().unlock();
		}
	}

	/** Returns the workspace configuration */
	public WorkspaceConfigSnapshot getWorkspaceConfig() {
		lock.readLock().lock();
		try {
			return workspaceConfig;
		} finally {
			lock.readLock().unlock();
		}
	}

	/** Sets the index for the given project name. */
	public ImmutableResourceDescriptionsData setProjectIndex(String projectName,
			ImmutableResourceDescriptionsData projectIndex,
			IResourceDescription.Event event) {
		Objects.requireNonNull(projectName);
		Objects.requireNonNull(projectIndex);
		ImmutableResourceDescriptionsData oldProjectIndex;
		ImmutableChunkedResourceDescriptions newWorkspaceIndex;
		WorkspaceConfigSnapshot newWorkspaceConfig;
		lock.writeLock().lock();
		try {
			ExtendedChunkedResourceDescriptions mutable = workspaceIndex.builder();
			oldProjectIndex = mutable.setContainer(projectName, projectIndex);
			newWorkspaceIndex = mutable.snapshot();
			this.workspaceIndex = newWorkspaceIndex;
			newWorkspaceConfig = this.workspaceConfig;
		} finally {
			lock.writeLock().unlock();
		}
		notifyListeners(newWorkspaceIndex, newWorkspaceConfig, event);
		return oldProjectIndex;
	}

	/** Sets the given project configuration. */
	public void setProjectConfigSnapshot(ProjectConfigSnapshot projectConfig) {
		setProjectConfigSnapshots(ImmutableSet.of(projectConfig));
	}

	/** Sets the given project configurations. */
	public void setProjectConfigSnapshots(ImmutableSet<? extends ProjectConfigSnapshot> projectConfigs) {
		Objects.requireNonNull(projectConfigs);
		ImmutableChunkedResourceDescriptions currentWorkspaceIndex;
		WorkspaceConfigSnapshot newWorkspaceConfig;
		lock.writeLock().lock();
		try {
			currentWorkspaceIndex = this.workspaceIndex;
			newWorkspaceConfig = workspaceConfig.update(projectConfigs, Collections.emptyList());
			workspaceConfig = newWorkspaceConfig;
		} finally {
			lock.writeLock().unlock();
		}
		notifyListeners(currentWorkspaceIndex, newWorkspaceConfig, new CoarseGrainedChangeEvent());
	}

	/** Removes the project with the given name from the index. */
	public ImmutableResourceDescriptionsData removeProjectIndex(String projectName) {
		Objects.requireNonNull(projectName);
		Wrapper<ImmutableResourceDescriptionsData> oldProjectIndex = Wrapper.wrap(null);
		ImmutableChunkedResourceDescriptions newWorkspaceIndex;
		WorkspaceConfigSnapshot newWorkspaceConfig;
		lock.writeLock().lock();
		try {
			ExtendedChunkedResourceDescriptions mutable = workspaceIndex.builder();
			oldProjectIndex.set(mutable.removeContainer(projectName));
			newWorkspaceIndex = mutable.snapshot();
			workspaceIndex = newWorkspaceIndex;
			workspaceConfig = newWorkspaceConfig = workspaceConfig.update(Collections.emptyList(),
					Collections.singletonList(projectName));
		} finally {
			lock.writeLock().unlock();
		}
		if (!oldProjectIndex.isEmpty()) {
			notifyListeners(newWorkspaceIndex, newWorkspaceConfig, new CoarseGrainedChangeEvent());
		}
		return oldProjectIndex.get();
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
			ImmutableChunkedResourceDescriptions newWorkspaceIndex,
			WorkspaceConfigSnapshot newWorkspaceConfig,
			IResourceDescription.Event event) {

		for (IIndexListener l : listeners) {
			l.onIndexChanged(newWorkspaceIndex, newWorkspaceConfig, event);
		}
	}

}
