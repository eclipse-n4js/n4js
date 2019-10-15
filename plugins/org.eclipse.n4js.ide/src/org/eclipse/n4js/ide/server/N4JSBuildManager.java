/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.build.IncrementalBuilder;
import org.eclipse.xtext.ide.server.BuildManager;
import org.eclipse.xtext.ide.server.ProjectManager;
import org.eclipse.xtext.ide.server.WorkspaceManager;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * Customized to sport a fully incremenal build on restart of a language server
 */
@SuppressWarnings("restriction")
public class N4JSBuildManager extends BuildManager {

	private WorkspaceManager accessibleWorkspaceManager;

	@Override
	public void setWorkspaceManager(WorkspaceManager workspaceManager) {
		super.setWorkspaceManager(workspaceManager);
		accessibleWorkspaceManager = workspaceManager;
	}

	private final Multimap<ProjectDescription, URI> myDirtyFiles = LinkedHashMultimap.create();

	private final Multimap<ProjectDescription, URI> myDeletedFiles = LinkedHashMultimap.create();

	private List<IResourceDescription.Delta> myUnreportedDeltas = new ArrayList<>();

	@Override
	public Buildable submit(List<URI> dirtyFiles, List<URI> deletedFiles) {
		for (URI dirtyFile : dirtyFiles) {
			// TODO hack
			if (!ProjectStatePersister.FILENAME.equals(dirtyFile.lastSegment())) {
				ProjectManager projectManager = accessibleWorkspaceManager.getProjectManager(dirtyFile);
				ProjectDescription description = projectManager.getProjectDescription();
				myDeletedFiles.remove(description, dirtyFile);
				myDirtyFiles.put(description, dirtyFile);
			}
		}
		for (URI deletedFile : deletedFiles) {
			if (!ProjectStatePersister.FILENAME.equals(deletedFile.lastSegment())) {
				ProjectDescription description = accessibleWorkspaceManager.getProjectManager(deletedFile)
						.getProjectDescription();
				myDeletedFiles.put(description, deletedFile);
				myDirtyFiles.remove(description, deletedFile);
			}
		}
		return this::internalBuild;
	}

	@Override
	public List<IResourceDescription.Delta> doInitialBuild(final List<ProjectDescription> projects,
			final CancelIndicator indicator) {
		// TODO deltas for the now-absent projects?
		myDeletedFiles.keySet().retainAll(projects);
		myDirtyFiles.keySet().retainAll(projects);
		return internalBuild(indicator);
	}

	void enqueue(ProjectDescription description, List<URI> dirtyFiles, List<URI> deletedFiles) {
		myDeletedFiles.get(description).removeAll(dirtyFiles);
		myDeletedFiles.putAll(description, deletedFiles);
		myDirtyFiles.get(description).removeAll(deletedFiles);
		myDirtyFiles.putAll(description, dirtyFiles);
	}

	@Override
	protected void queue(Set<URI> files, Collection<URI> toRemove, Collection<URI> toAdd) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected List<IResourceDescription.Delta> internalBuild(final CancelIndicator cancelIndicator) {
		final Multimap<ProjectDescription, URI> project2dirty = myDirtyFiles;
		final Multimap<ProjectDescription, URI> project2deleted = myDeletedFiles;

		Set<ProjectDescription> dirtyProjects = project2dirty.keySet();
		Set<ProjectDescription> deletedProjects = project2deleted.keySet();
		final List<ProjectDescription> sortedDescriptions = this
				.sortByDependencies(FluentIterable.concat(dirtyProjects, deletedProjects));
		for (final ProjectDescription it : sortedDescriptions) {
			{
				final ProjectManager projectManager = accessibleWorkspaceManager.getProjectManager(it.getName());
				final List<URI> projectDirty = new ArrayList<>(project2dirty.get(it));
				final List<URI> projectDeleted = new ArrayList<>(project2deleted.get(it));
				final IncrementalBuilder.Result partialResult = projectManager.doBuild(projectDirty, projectDeleted,
						this.myUnreportedDeltas, cancelIndicator);
				myDirtyFiles.get(it).removeAll(projectDirty);
				myDeletedFiles.get(it).removeAll(projectDeleted);
				List<IResourceDescription.Delta> unreportedDeltas = new ArrayList<>();
				List<URI> unreportedDeltaUris = new ArrayList<>();
				for (IResourceDescription.Delta affected : partialResult.getAffectedResources()) {
					unreportedDeltas.add(affected);
					if (!projectDirty.contains(affected.getUri()) && !projectDeleted.contains(affected.getUri())) {
						unreportedDeltaUris.add(affected.getUri());
					}
				}
				submit(unreportedDeltaUris, Collections.emptyList());
				this.mergeWithUnreportedDeltas(unreportedDeltas);
			}
		}
		final List<IResourceDescription.Delta> result = this.myUnreportedDeltas;
		this.myUnreportedDeltas = new ArrayList<>();
		return result;
	}

	/**
	 * @since 2.18
	 */
	@Override
	protected Boolean mergeWithUnreportedDeltas(final List<IResourceDescription.Delta> newDeltas) {
		if (this.myUnreportedDeltas.isEmpty()) {
			this.myUnreportedDeltas.addAll(newDeltas);
		} else {
			final Map<URI, IResourceDescription.Delta> unreportedByUri = IterableExtensions.<URI, IResourceDescription.Delta> toMap(
					this.myUnreportedDeltas, IResourceDescription.Delta::getUri);
			for (IResourceDescription.Delta newDelta : newDeltas) {
				final IResourceDescription.Delta unreportedDelta = unreportedByUri.get(newDelta.getUri());
				if ((unreportedDelta == null)) {
					unreportedByUri.put(newDelta.getUri(), newDelta);
				} else {
					unreportedByUri.remove(unreportedDelta.getUri());
					IResourceDescription _old = unreportedDelta.getOld();
					IResourceDescription _new = newDelta.getNew();
					if (_old != _new) {
						DefaultResourceDescriptionDelta delta = new DefaultResourceDescriptionDelta(_old, _new);
						unreportedByUri.put(delta.getUri(), delta);
					}
				}
			}
			this.myUnreportedDeltas = new ArrayList<>(unreportedByUri.values());
		}
		return null;
	}

}
