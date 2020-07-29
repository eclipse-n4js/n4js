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
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ProjectDescription;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Implementation for sorted projects according to their build order.
 */
public class ProjectBuildOrderInfo implements IOrderInfo<ProjectDescription> {

	/** Issue key for cyclic dependencies */
	public static final String CYCLIC_PROJECT_DEPENDENCIES = ProjectBuildOrderInfo.class.getName()
			+ ".cyclicProjectDependencies";

	/**
	 * A provider for {@link ProjectBuildOrderInfo} instances.
	 */
	public static class Provider implements com.google.inject.Provider<IOrderInfo<ProjectDescription>> {
		/** Injector to be used for creating instances of {@link #ProjectBuildOrderInfo()} */
		@Inject
		protected Injector injector;

		/** Returns a new instanceof of {@link ProjectBuildOrderInfo}. No projects will be visited. */
		@Override
		public ProjectBuildOrderInfo get() {
			return injector.getInstance(ProjectBuildOrderInfo.class);
		}
	}

	/**
	 * {@link Iterator} that iterates over {@link ProjectBuildOrderInfo#sortedProjects}.
	 */
	public class ProjectBuildOrderIterator implements IOrderIterator<ProjectDescription> {
		/**
		 * Subset of {@link #sortedProjects}: when {@link #ProjectBuildOrderInfo()} is used as an iterator, only those
		 * projects are iterated over that are contained in this set
		 */
		final protected Set<String> visitProjectNames = new HashSet<>();
		/** Iterator delegate */
		final protected Iterator<ProjectDescription> iteratorDelegate;

		ProjectBuildOrderIterator() {
			this.iteratorDelegate = Iterables
					.filter(sortedProjects, input -> visitProjectNames.contains(input.getName())).iterator();
		}

		@Override
		public ProjectBuildOrderIterator visit(Collection<ProjectDescription> projectDescriptions) {
			for (ProjectDescription prj : projectDescriptions) {
				visitProjectNames.add(prj.getName());
			}
			return this;
		}

		/** Mark all projects as to be visited that are affected by a change in the given project. */
		public IOrderIterator<ProjectDescription> visitAffected(String projectName) {
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
		protected Set<ProjectDescription> getAffectedProjects(List<IResourceDescription.Delta> changes) {
			Set<String> changedProjectsNames = new HashSet<>();
			for (IResourceDescription.Delta change : changes) {
				ProjectBuilder projectManager = workspaceManager.getProjectBuilder(change.getUri());
				ProjectDescription pd = projectManager.getProjectDescription();
				changedProjectsNames.add(pd.getName());
			}

			Set<ProjectDescription> affectedProjects = new HashSet<>();
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
		public ProjectDescription next() {
			return iteratorDelegate.next();
		}
	}

	/** Workspace manager */
	@Inject
	protected XWorkspaceManager workspaceManager;

	/** Inversed set of project dependency information */
	final protected Multimap<String, ProjectDescription> inversedDependencies = HashMultimap.create();
	/** Build order of projects */
	final protected List<ProjectDescription> sortedProjects = new ArrayList<>();

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
	public ProjectBuildOrderIterator getIterator(Collection<ProjectDescription> projectDescriptions) {
		ProjectBuildOrderIterator iterator = getIterator();
		iterator.visit(projectDescriptions);
		return iterator;
	}

	/** Populates {@link #sortedProjects} and {@link #inversedDependencies} */
	@Inject
	protected void init() {
		LinkedHashSet<String> orderedProjectNames = new LinkedHashSet<>();
		for (ProjectBuilder pb : workspaceManager.getProjectBuilders()) {
			ProjectDescription pd = pb.getProjectDescription();
			for (String dependencyName : pd.getDependencies()) {
				inversedDependencies.put(dependencyName, pd);
			}
			computeOrder(pd, orderedProjectNames, new LinkedHashSet<>());
		}

		for (String projectName : orderedProjectNames) {
			ProjectBuilder pb = workspaceManager.getProjectBuilder(projectName);
			if (pb != null) { // can be null if project not on disk
				sortedProjects.add(pb.getProjectDescription());
			}
		}
	}

	/** Computes the build order of all projects in the workspace */
	protected void computeOrder(ProjectDescription pd, LinkedHashSet<String> orderedProjects,
			LinkedHashSet<String> projectStack) {

		String pdName = pd.getName();
		if (projectStack.contains(pdName)) {
			for (String cyclicPD : projectStack) {
				reportDependencyCycle(cyclicPD);
			}
		} else {
			projectStack.add(pdName);

			for (String depName : pd.getDependencies()) {
				ProjectBuilder pm = workspaceManager.getProjectBuilder(depName);
				if (pm != null) { // can be null if project not on disk
					ProjectDescription depPd = pm.getProjectDescription();
					computeOrder(depPd, orderedProjects, projectStack);
				}
			}

			orderedProjects.add(pdName);
			projectStack.remove(pdName);
		}
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