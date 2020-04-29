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

import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ProjectDescription;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 *
 */
public class OrderInfo implements IOrderInfo<ProjectDescription> {

	/** A provider for {@link OrderInfo} instances. */
	public static class Provider implements com.google.inject.Provider<IOrderInfo<ProjectDescription>> {
		@Inject
		protected Injector injector;

		/** Returns a new instanceof of {@link OrderInfo} */
		@Override
		public IOrderInfo<ProjectDescription> get() {
			return injector.getInstance(OrderInfo.class);
		}

		public IOrderInfo<ProjectDescription> get(Collection<ProjectDescription> projectDescriptions) {
			IOrderInfo<ProjectDescription> orderInfo = get();
			orderInfo.alsoVisit(projectDescriptions);
			return orderInfo;
		}
	}

	@Inject
	protected XWorkspaceManager workspaceManager;

	final protected Multimap<String, ProjectDescription> inversedDependencies = HashMultimap.create();
	final protected List<ProjectDescription> sortedProjects = new ArrayList<>();
	final protected Set<String> visitProjectNames = new HashSet<>();

	@Override
	public Iterator<ProjectDescription> iterator() {
		return Iterables.filter(sortedProjects, input -> visitProjectNames.contains(input.getName())).iterator();
	}

	@Override
	public void visitAffected(List<IResourceDescription.Delta> changes) {
		alsoVisit(getAffectedProjects(changes));
	}

	@Override
	public void alsoVisit(Collection<ProjectDescription> projectDescriptions) {
		for (ProjectDescription prj : projectDescriptions) {
			visitProjectNames.add(prj.getName());
		}
	}

	@Inject
	protected void init() {
		for (XProjectManager pm : workspaceManager.getProjectManagers()) {
			ProjectDescription pd = pm.getProjectDescription();
			for (String dependencyName : pd.getDependencies()) {
				inversedDependencies.put(dependencyName, pd);
			}
		}

		LinkedHashSet<ProjectDescription> orderedProjects = new LinkedHashSet<>();
		for (String projectName : inversedDependencies.keySet()) {
			ProjectDescription pd = workspaceManager.getProjectManager(projectName).getProjectDescription();
			computeOrder(pd, orderedProjects, new LinkedHashSet<>());
		}

		sortedProjects.addAll(orderedProjects);
		for (ProjectDescription prj : orderedProjects) {
			visitProjectNames.add(prj.getName());
		}
	}

	protected void computeOrder(ProjectDescription pd, LinkedHashSet<ProjectDescription> orderedProjects,
			LinkedHashSet<ProjectDescription> projectStack) {

		if (orderedProjects.add(pd)) {
			projectStack.add(pd);
			for (ProjectDescription affectedPD : inversedDependencies.get(pd.getName())) {
				computeOrder(affectedPD, orderedProjects, projectStack);
			}
			projectStack.remove(pd);
		} else {
			if (projectStack.contains(pd)) {
				for (ProjectDescription cyclicPD : projectStack) {
					reportDependencyCycle(cyclicPD);
				}
			}
		}
	}

	protected Set<ProjectDescription> getAffectedProjects(List<IResourceDescription.Delta> changes) {
		Set<String> changedProjectsNames = new HashSet<>();
		for (IResourceDescription.Delta change : changes) {
			change.getUri();
			XProjectManager projectManager = workspaceManager.getProjectManager(change.getUri());
			ProjectDescription pd = projectManager.getProjectDescription();
			changedProjectsNames.add(pd.getName());
		}

		Set<ProjectDescription> affectedProjects = new HashSet<>();
		for (String changedProjectName : changedProjectsNames) {
			affectedProjects.addAll(inversedDependencies.get(changedProjectName));
		}

		return affectedProjects;
	}

	/** Report cycle. */
	protected void reportDependencyCycle(ProjectDescription prjDescription) {
		XProjectManager projectManager = workspaceManager.getProjectManager(prjDescription.getName());
		String msg = "Project has cyclic dependencies";
		projectManager.reportProjectIssue(msg, XBuildManager.CYCLIC_PROJECT_DEPENDENCIES, Severity.ERROR);
	}
}