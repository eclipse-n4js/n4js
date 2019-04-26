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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.build.IncrementalBuilder.Result;
import org.eclipse.xtext.ide.server.BuildManager;
import org.eclipse.xtext.ide.server.ProjectManager;
import org.eclipse.xtext.ide.server.WorkspaceManager;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.HashMultimap;
import com.google.inject.Inject;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSBuildManager extends BuildManager {

	@Inject
	LspLogger logger;

	@Override
	public Buildable submit(List<URI> dirtyFiles, List<URI> deletedFiles) {
		Buildable submit = super.submit(dirtyFiles, deletedFiles);
		return submit;
	}

	@Override
	protected List<Delta> internalBuild(CancelIndicator cancelIndicator) {
		System.out.println("internalBuild");
		try {
			List<Delta> internalBuild = super_internalBuild(cancelIndicator);
			return internalBuild;
		} catch (Exception e) {
			System.out.println("CATCHED: internalBuild. " + e.getClass().getName());
			throw e;
		}
	}

	/***/
	protected List<IResourceDescription.Delta> super_internalBuild(CancelIndicator cancelIndicator) {
		ArrayList<URI> allDirty = new ArrayList<>(getDirtyFiles());
		HashMultimap<ProjectDescription, URI> project2dirty = HashMultimap.create();
		for (URI dirty : allDirty) {
			ProjectDescription projectDescription = getWorkspaceManager().getProjectManager(dirty)
					.getProjectDescription();
			project2dirty.put(projectDescription, dirty);
		}
		HashMultimap<ProjectDescription, URI> project2deleted = HashMultimap.create();
		for (URI deleted : getDeletedFiles()) {
			ProjectDescription projectDescription = getWorkspaceManager().getProjectManager(deleted)
					.getProjectDescription();
			project2deleted.put(projectDescription, deleted);
		}
		Set<ProjectDescription> allPDs = new HashSet<>();
		allPDs.addAll(project2dirty.keySet());
		allPDs.addAll(project2deleted.keySet());
		List<ProjectDescription> sortedDescriptions = sortByDependencies(allPDs);

		List<IResourceDescription.Delta> result = new ArrayList<>();
		for (ProjectDescription pDescr : sortedDescriptions) {
			logger.log("Building: " + pDescr.getName());
			ProjectManager projectManager = getWorkspaceManager().getProjectManager(pDescr.getName());
			List<URI> projectDirty = new LinkedList<>(project2dirty.get(pDescr));
			List<URI> projectDeleted = new LinkedList<>(project2deleted.get(pDescr));
			Result partialResult = projectManager.doBuild(projectDirty, projectDeleted, result, cancelIndicator);
			allDirty.addAll(partialResult.getAffectedResources().stream()
					.map(delta -> delta.getUri())
					.collect(Collectors.toList()));

			getDirtyFiles().removeAll(projectDirty);
			getDeletedFiles().removeAll(projectDeleted);

			result.addAll(partialResult.getAffectedResources());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Set<URI> getDirtyFiles() {
		try {
			Field dirtyFilesField = BuildManager.class.getDeclaredField("dirtyFiles");
			dirtyFilesField.setAccessible(true);
			Set<URI> dirtyFiles = (Set<URI>) dirtyFilesField.get(this);
			return dirtyFiles;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptySet();
	}

	@SuppressWarnings("unchecked")
	private Set<URI> getDeletedFiles() {
		try {
			Field deletedFilesField = BuildManager.class.getDeclaredField("deletedFiles");
			deletedFilesField.setAccessible(true);
			Set<URI> deletedFiles = (Set<URI>) deletedFilesField.get(this);
			return deletedFiles;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptySet();
	}

	private WorkspaceManager getWorkspaceManager() {
		try {
			Field workspaceManagerField = BuildManager.class.getDeclaredField("workspaceManager");
			workspaceManagerField.setAccessible(true);
			WorkspaceManager workspaceManager = (WorkspaceManager) workspaceManagerField.get(this);
			return workspaceManager;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private String getFilesString(Collection<URI> files) {
		String filesString = files.stream()
				.map(uri -> uri.segment(uri.segmentCount() - 1))
				.reduce((a, b) -> a + ", " + b).orElse("");
		return filesString;
	}

}
