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
package org.eclipse.n4js.xtext.workspace;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IWorkspaceConfig;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ImmutableSet;

/**
 * Immutable equivalent of an {@link IWorkspaceConfig}.
 */
@SuppressWarnings("restriction")
public class WorkspaceConfigSnapshot extends Snapshot {

	/** The path used for the {@link #EMPTY default empty} workspace configuration. */
	public static final URI EMPTY_PATH = URI.createFileURI("/empty-workspace");

	/** An empty workspace configuration with a {@link #EMPTY_PATH default path}. */
	public static final WorkspaceConfigSnapshot EMPTY = new WorkspaceConfigSnapshot(EMPTY_PATH, ProjectSet.EMPTY,
			BuildOrderInfo.NULL);

	/** The root path of the workspace */
	protected final URI path;
	/** Set of projects in the workspace, prepared for fast lookup by name, path, etc. */
	protected final ProjectSet projects;
	/** Project build order */
	protected final BuildOrderInfo buildOrderInfo;

	/** See {@link WorkspaceConfigSnapshot}. */
	public WorkspaceConfigSnapshot(URI path, ProjectSet projects, BuildOrderInfo buildOrderInfo) {
		this.path = path;
		this.projects = projects;
		this.buildOrderInfo = buildOrderInfo;
	}

	/** Getter for the root path. */
	public URI getPath() {
		return path;
	}

	/** Tells whether this workspace is empty, i.e. does not contain any projects. */
	public boolean isEmpty() {
		return projects.isEmpty();
	}

	/** Number of projects in this workspace. */
	public int size() {
		return projects.size();
	}

	/** Get all the projects known in this snapshot. */
	public ImmutableSet<? extends ProjectConfigSnapshot> getProjects() {
		return projects.getProjects();
	}

	/** Find the project with the given name. */
	public ProjectConfigSnapshot findProjectByName(String name) {
		return projects.findProjectByName(name);
	}

	/** See {@link ProjectSet#findProjectByPath(URI)}. */
	@SuppressWarnings("hiding")
	public ProjectConfigSnapshot findProjectByPath(URI path) {
		return projects.findProjectByPath(path);
	}

	/** See {@link ProjectSet#findProjectByNestedLocation(URI)}. */
	public ProjectConfigSnapshot findProjectByNestedLocation(URI nestedLocation) {
		return projects.findProjectByNestedLocation(nestedLocation);
	}

	/** See {@link ProjectSet#findProjectContaining(URI)}. */
	public ProjectConfigSnapshot findProjectContaining(URI nestedSourceLocation) {
		return projects.findProjectContaining(nestedSourceLocation);
	}

	/** See {@link ProjectSet#findSourceFolderContaining(URI)}. */
	public SourceFolderSnapshot findSourceFolderContaining(URI nestedSourceLocation) {
		return projects.findSourceFolderContaining(nestedSourceLocation);
	}

	/** See {@link ProjectSet#findProjectAndSourceFolderContaining(URI)}. */
	public Pair<? extends ProjectConfigSnapshot, ? extends SourceFolderSnapshot> findProjectAndSourceFolderContaining(
			URI nestedSourceLocation) {
		return projects.findProjectAndSourceFolderContaining(nestedSourceLocation);
	}

	/** Returns all projects that depend on the project with the given name or an empty set if the name is not found. */
	public ImmutableSet<? extends ProjectConfigSnapshot> getProjectsDependingOn(String projectName) {
		return projects.getProjectsDependingOn(projectName);
	}

	/** Get build order of all projects of this workspace snapshot */
	public BuildOrderInfo getBuildOrderInfo() {
		return buildOrderInfo;
	}

	/** Return true iff there exist projects in this workspace that have a dependency cycle */
	public boolean hasDependencyCycle() {
		return !buildOrderInfo.projectCycles.isEmpty();
	}

	/** Return true iff the given project is part of a dependency cycle */
	public boolean isInDependencyCycle(String projectName) {
		for (List<String> projectCycle : buildOrderInfo.projectCycles) {
			if (projectCycle.contains(projectName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected int computeHashCode() {
		// note: no need to consider "buildOrderInfo" because it is derived information
		return Objects.hash(
				path,
				projects.getProjects());
	}

	@Override
	protected boolean computeEquals(Object obj) {
		// note: no need to check "buildOrderInfo" because it is derived information
		WorkspaceConfigSnapshot other = (WorkspaceConfigSnapshot) obj;
		return Objects.equals(path, other.path)
				&& Objects.equals(projects.getProjects(), other.projects.getProjects());
	}

	@Override
	public String toString() {
		return "WorkspaceConfigSnapshot [path=" + path + ", projects=" + projects.getProjectsByName() + "]";
	}

}
