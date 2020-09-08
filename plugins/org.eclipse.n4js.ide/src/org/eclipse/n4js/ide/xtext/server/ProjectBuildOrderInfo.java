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
package org.eclipse.n4js.ide.xtext.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.ide.xtext.server.build.ProjectBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XWorkspaceManager;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IResourceDescription;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Implementation for sorted projects according to their build order.
 */
public class ProjectBuildOrderInfo implements IOrderInfo<XIProjectConfig> {

	/** Issue key for cyclic dependencies */
	public static final String CYCLIC_PROJECT_DEPENDENCIES = ProjectBuildOrderInfo.class.getName()
			+ ".cyclicProjectDependencies";

	/**
	 * A provider for {@link ProjectBuildOrderInfo} instances.
	 */
	public static class Provider implements com.google.inject.Provider<IOrderInfo<XIProjectConfig>> {
		/** Injector to be used for creating instances of {@link #ProjectBuildOrderInfo()} */
		@Inject
		protected Injector injector;

		/** Returns a new instance of {@link ProjectBuildOrderInfo}. No projects will be visited. */
		@Override
		public ProjectBuildOrderInfo get() {
			return injector.getInstance(ProjectBuildOrderInfo.class);
		}
	}

	/**
	 * {@link Iterator} that iterates over {@link ProjectBuildOrderInfo#sortedProjects}.
	 */
	public class ProjectBuildOrderIterator implements IOrderIterator<XIProjectConfig> {
		/**
		 * Subset of {@link #sortedProjects}: when {@link #ProjectBuildOrderInfo()} is used as an iterator, only those
		 * projects are iterated over that are contained in this set
		 */
		final protected Set<String> visitProjectNames = new HashSet<>();
		/** Iterator delegate */
		final protected Iterator<XIProjectConfig> iteratorDelegate;

		ProjectBuildOrderIterator() {
			this.iteratorDelegate = Iterables
					.filter(sortedProjects, input -> visitProjectNames.contains(input.getName())).iterator();
		}

		@Override
		public ProjectBuildOrderIterator visit(Collection<? extends XIProjectConfig> projectConfigs) {
			for (XIProjectConfig pc : projectConfigs) {
				visitProjectNames.add(pc.getName());
			}
			return this;
		}

		/** Mark all projects as to be visited that are affected by a change in the given project. */
		public IOrderIterator<XIProjectConfig> visitAffected(String projectName) {
			visit(inversedDependencies.get(projectName));
			return this;
		}

		@Override
		public ProjectBuildOrderIterator visitAffected(List<IResourceDescription.Delta> changes) {
			visit(getAffectedProjects(changes));
			return this;
		}

		@Override
		public ProjectBuildOrderIterator visitAll() {
			visit(sortedProjects);
			return this;
		}

		/** @return the set of projects that may contain resources that need to be rebuild given the list of changes */
		protected Set<XIProjectConfig> getAffectedProjects(List<IResourceDescription.Delta> changes) {
			Set<String> changedProjectsNames = new HashSet<>();
			for (IResourceDescription.Delta change : changes) {
				ProjectBuilder projectBuilder = workspaceManager.getProjectBuilder(change.getUri());
				changedProjectsNames.add(projectBuilder.getName());
			}

			Set<XIProjectConfig> affectedProjects = new HashSet<>();
			for (String changedProjectName : changedProjectsNames) {
				affectedProjects.addAll(inversedDependencies.get(changedProjectName));
			}

			return affectedProjects;
		}

		@Override
		public boolean hasNext() {
			return iteratorDelegate.hasNext();
		}

		@Override
		public XIProjectConfig next() {
			return iteratorDelegate.next();
		}
	}

	/** Workspace manager */
	@Inject
	protected XWorkspaceManager workspaceManager;

	/** Inverse set of project dependency information */
	final protected Multimap<String, XIProjectConfig> inversedDependencies = HashMultimap.create();
	/** Build order of projects */
	final protected List<XIProjectConfig> sortedProjects = new ArrayList<>();

	/**
	 * Creates a new instance of {@link ProjectBuildOrderIterator}. Assumes a succeeding call to
	 * {@link ProjectBuildOrderIterator#visit(Collection)} method.
	 */
	@Override
	public ProjectBuildOrderIterator getIterator() {
		return new ProjectBuildOrderIterator();
	}

	/** Creates a new instance of {@link ProjectBuildOrderIterator}. The given set of projects will be visited only. */
	@Override
	public ProjectBuildOrderIterator getIterator(Collection<? extends XIProjectConfig> projectDescriptions) {
		ProjectBuildOrderIterator iterator = getIterator();
		iterator.visit(projectDescriptions);
		return iterator;
	}

	/** Populates {@link #sortedProjects} and {@link #inversedDependencies} */
	@Inject
	protected void init() {
		LinkedHashSet<String> orderedProjectNames = new LinkedHashSet<>();
		for (ProjectBuilder pb : workspaceManager.getProjectBuilders()) {
			XIProjectConfig pc = pb.getProjectConfig();
			for (String dependencyName : getDependencies(pc)) {
				inversedDependencies.put(dependencyName, pc);
			}
			computeOrder(pc, orderedProjectNames, new LinkedHashSet<>());
		}

		for (String projectName : orderedProjectNames) {
			ProjectBuilder pb = workspaceManager.getProjectBuilder(projectName);
			if (pb != null) { // can be null if project not on disk
				sortedProjects.add(pb.getProjectConfig());
			}
		}
	}

	/** Computes the build order of all projects in the workspace */
	protected void computeOrder(XIProjectConfig pc, LinkedHashSet<String> orderedProjects,
			LinkedHashSet<String> projectStack) {

		String pdName = pc.getName();
		if (orderedProjects.contains(pdName)) {
			return;
		}

		if (projectStack.contains(pdName)) {
			for (String cyclicPD : projectStack) {
				reportDependencyCycle(cyclicPD);
			}
		} else {
			projectStack.add(pdName);

			for (String depName : getDependencies(pc)) {
				ProjectBuilder pb = workspaceManager.getProjectBuilder(depName);
				if (pb != null) { // can be null if project not on disk
					XIProjectConfig depPC = pb.getProjectConfig();
					computeOrder(depPC, orderedProjects, projectStack);
				}
			}

			orderedProjects.add(pdName);
			projectStack.remove(pdName);
		}
	}

	/**
	 * Returns the project dependencies to be considered for build order computation. By default, simply returns
	 * {@link XIProjectConfig#getDependencies()}; subclasses may override to customize this.
	 */
	protected Set<String> getDependencies(XIProjectConfig pc) {
		return pc.getDependencies();
	}

	/** Report cycle. */
	protected void reportDependencyCycle(String projectName) {
		ProjectBuilder pm = workspaceManager.getProjectBuilder(projectName);
		if (pm != null) { // can be null if project not on disk
			String msg = "Project has cyclic dependencies";
			pm.reportProjectIssue(msg, CYCLIC_PROJECT_DEPENDENCIES, Severity.ERROR);
		}
	}
}