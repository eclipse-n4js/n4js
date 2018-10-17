/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.external;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.external.ComputeProjectOrder.VertexFilter;
import org.eclipse.n4js.ui.external.ComputeProjectOrder.VertexMapper;
import org.eclipse.n4js.ui.external.ComputeProjectOrder.VertexOrder;
import org.eclipse.n4js.ui.internal.N4JSEclipseProject;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * Utility class for providing build order (as {@link IBuildConfiguration build configuration} instances) for external
 * projects.
 */
public class BuildOrderComputer {

	@Inject
	private IN4JSCore core;

	private final UriToProjectMapper uri2PrjMapper = new UriToProjectMapper();

	private class UriToProjectMapper implements VertexMapper<String, IN4JSProject> {
		@Override
		public IN4JSProject get(String projectURI) {
			URI uri = org.eclipse.emf.common.util.URI.createURI(projectURI);
			IN4JSProject project = core.findProject(uri).orNull();
			return project;
		}

		@Override
		public Class<IN4JSProject> getTargetClass() {
			return IN4JSProject.class;
		}
	}

	static private class FilterUnreqestedOut implements VertexFilter<String> {
		final private Set<String> requestedProjectNames;

		FilterUnreqestedOut(String[] requestedProjectNames) {
			this.requestedProjectNames = new HashSet<>(Arrays.asList(requestedProjectNames));
		}

		@Override
		public boolean matches(String vertex) {
			boolean filterOut = !requestedProjectNames.contains(vertex);
			return filterOut;
		}
	}

	/**
	 * Convenience method for {@link #getBuildOrder(N4JSProject[])}
	 */
	public VertexOrder<IN4JSProject> getBuildOrder(N4JSExternalProject[] projects) {
		N4JSProject[] n4jsPrjs = new N4JSProject[projects.length];
		for (int i = 0; i < projects.length; i++) {
			N4JSExternalProject project = projects[i];
			URI locationURI = URIUtils.convert(project);
			n4jsPrjs[i] = (N4JSProject) uri2PrjMapper.get(locationURI.toString());
		}
		return getBuildOrder(n4jsPrjs);
	}

	/**
	 * Computes the global total ordering of all projects in the workspace. If a project P references another project Q,
	 * then Q should come before P in the resulting ordering. Note the following properties:<br/>
	 * <ul>
	 * <li>Dependency graph of all given projects is determined first.
	 * <li>Graph includes all the projects that are transitive dependencies (that exist and are open) of P
	 * <li>Final build order list is filtered to include only the given projects.
	 * </ul>
	 * When the build configuration reference graph contains cyclic references, it is not impossible to honor all of the
	 * relationships. In this case, the result ignores as few relationships as possible. For example, if P2 references
	 * P1, P4 references P3, and P2 and P3 reference each other, then exactly one of the relationships between P2 and P3
	 * will have to be ignored. The outcome will be either [P1, P2, P3, P4] or [P1, P3, P2, P4]. The result also
	 * contains complete details of any cycles present.
	 * <p>
	 *
	 * @param requestedProjects
	 *            the projects to calculate the active build configuration order among them.
	 *
	 * @return result describing the global active build configuration order
	 */
	public VertexOrder<IN4JSProject> getBuildOrder(N4JSProject[] requestedProjects) {
		String[] requestedProjectURIs = new String[requestedProjects.length];
		for (int i = 0; i < requestedProjectURIs.length; i++) {
			requestedProjectURIs[i] = requestedProjects[i].getLocation().toString();
		}

		VertexOrder<String> completeOrder = getBuildOrderOfURIs(requestedProjectURIs);
		VertexOrder<IN4JSProject> requestedOrder = ComputeProjectOrder.mapVertexOrder(completeOrder, uri2PrjMapper);

		return requestedOrder;
	}

	private VertexOrder<String> getBuildOrderOfURIs(String[] requestedProjectURIs) {
		FilterUnreqestedOut filter = new FilterUnreqestedOut(requestedProjectURIs);
		VertexOrder<String> completeOrder = computeVertexOrder(requestedProjectURIs);
		VertexOrder<String> requestedOrder = ComputeProjectOrder.filterVertexOrder(completeOrder, filter);

		return requestedOrder;
	}

	private VertexOrder<String> computeVertexOrder(String[] requestedProjectURIs) {
		// init worklist with existing projects
		TreeSet<String> worklist = new TreeSet<>();
		for (String projectURI : requestedProjectURIs) {
			IN4JSProject project = uri2PrjMapper.get(projectURI);
			if (project != null && project.exists()) {
				worklist.add(projectURI);
			}
		}

		// find dependency edges and project closure
		Multimap<String, String> edges = HashMultimap.create();
		TreeSet<String> projectsClosure = new TreeSet<>();
		while (!worklist.isEmpty()) {
			String projectURI = worklist.pollFirst();
			projectsClosure.add(projectURI);

			IN4JSProject project = uri2PrjMapper.get(projectURI);
			Set<N4JSProject> prjDependencies = getDependencies(project);
			Set<String> prjDependencyURIs = new HashSet<>();

			// add edges
			for (N4JSProject prjDependency : prjDependencies) {
				String depURI = prjDependency.getLocation().toString();
				edges.put(projectURI, depURI);
				prjDependencyURIs.add(depURI);
			}

			// add only new dependencies to the worklist
			prjDependencyURIs.removeAll(projectsClosure);
			worklist.addAll(prjDependencyURIs);
		}

		// transform dependency edges
		List<String[]> edges2 = new ArrayList<>();
		for (Map.Entry<String, String> dependency : edges.entries()) {
			String prj = dependency.getKey();
			String depOn = dependency.getValue();
			edges2.add(new String[] { prj, depOn });
		}

		VertexOrder<Object> orderedObjects = ComputeProjectOrder.computeVertexOrder(projectsClosure, edges2);
		VertexOrder<String> orderedURIs = ComputeProjectOrder.castVertexOrder(orderedObjects, String.class);

		return orderedURIs;
	}

	private Set<N4JSProject> getDependencies(IN4JSProject project) {
		Set<N4JSProject> prjDependencies = new HashSet<>();
		ImmutableList<? extends IN4JSProject> deps = project.getAllDirectDependencies();
		for (IN4JSProject dep : deps) {
			IN4JSProject pDep = core.findProject(dep.getLocation()).orNull();

			boolean isValidDep = true;
			isValidDep &= pDep != null && pDep.exists();
			isValidDep &= !(pDep instanceof N4JSEclipseProject) || ((N4JSEclipseProject) pDep).getProject().isOpen();

			if (isValidDep) {
				prjDependencies.add((N4JSProject) pDep);
			}
		}
		return prjDependencies;
	}

}
