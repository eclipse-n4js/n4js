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
package org.eclipse.n4js.compare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Helper class to collect associations between API and implementation projects and obtain the implementation IDs
 * defined in the implementation projects. Also performs some validation along the way, see {@link #hasErrors()}.
 */
public class ApiImplMapping {

	private final Map<N4JSProjectName, ApiImplAssociation> assocs = new LinkedHashMap<>();
	private final Set<N4JSProjectConfigSnapshot> projectsWithUndefImplIds = new LinkedHashSet<>();
	private final Map<Pair<N4JSProjectName, N4JSProjectName>, Set<N4JSProjectConfigSnapshot>> conflicts = new LinkedHashMap<>();

	/** A 1:N association between a single API project and its N implementation projects. */
	private static final class ApiImplAssociation {

		public final N4JSProjectConfigSnapshot api;
		public final Map<N4JSProjectName, N4JSProjectConfigSnapshot> impls = new LinkedHashMap<>();

		public ApiImplAssociation(N4JSProjectConfigSnapshot api) {
			this.api = api;
		}

		public Set<N4JSProjectName> getImplIds() {
			return impls.keySet();
		}

		public Collection<N4JSProjectConfigSnapshot> getImpls() {
			return impls.values();
		}

		public N4JSProjectConfigSnapshot getImpl(N4JSProjectName implId) {
			return impls.get(implId);
		}

		public N4JSProjectConfigSnapshot putImpl(N4JSProjectConfigSnapshot impl) {
			final String implId = impl.getImplementationId();
			if (implId != null)
				return impls.put(new N4JSProjectName(implId), impl);
			return null;
		}
	}

	/**
	 * Creates a new, empty mapping that can be filled via method
	 * {@link #put(N4JSProjectConfigSnapshot,N4JSProjectConfigSnapshot)}. Usually, client code should use one of the
	 * static <code>#of()</code> methods.
	 *
	 * @see ApiImplMapping#of(N4JSWorkspaceConfigSnapshot)
	 * @see ApiImplMapping#of(N4JSWorkspaceConfigSnapshot, Iterable)
	 * @see ApiImplMapping#of(N4JSWorkspaceConfigSnapshot, Iterable,Iterable)
	 */
	public ApiImplMapping() {
	}

	/**
	 * Create mapping of all API / implementation projects in the workspace.
	 */
	public static ApiImplMapping of(N4JSWorkspaceConfigSnapshot wc) {
		final ApiImplMapping mapping = new ApiImplMapping();
		for (N4JSProjectConfigSnapshot pImpl : wc.getProjects()) {
			for (ProjectReference pApi : pImpl.getProjectDescription().getImplementedProjects()) {
				N4JSProjectConfigSnapshot pApiResolved = wc.findProjectByName(pApi.getProjectName());
				if (pApiResolved != null) {
					mapping.put(pApiResolved, pImpl);
				}
			}
		}
		return mapping;
	}

	/**
	 * Create mapping of all API / implementation projects in the given list. The list may contain unrelated projects
	 * that are neither API nor implementation projects.
	 */
	public static ApiImplMapping of(N4JSWorkspaceConfigSnapshot wc, Iterable<N4JSProjectConfigSnapshot> projects) {
		return of(wc, projects, projects);
	}

	/**
	 * Create mapping of all API / implementation projects in the given lists. The mapping will only contain API
	 * projects also contained in list <code>apiProjects</code> and only implementation projects also contained in list
	 * <code>implProjects</code>, so the given lists serve as a kind of filter. The two given lists may contain
	 * unrelated projects that are neither API nor implementation projects and they may intersect.
	 */
	public static ApiImplMapping of(N4JSWorkspaceConfigSnapshot wc,
			Iterable<? extends N4JSProjectConfigSnapshot> apiProjects,
			Iterable<? extends N4JSProjectConfigSnapshot> implProjects) {
		return new ApiImplMapping().enhance(wc, apiProjects, implProjects);
	}

	/**
	 * Enhance the existing ApiImplMapping to also cover the given list of apiProjects. Only apiProjects contained in
	 * apiProject will be covered. Implementation of APIs not listed there are not taken into account.
	 *
	 * @param wc
	 *            the workspace configuration.
	 * @param apiProjects
	 *            list APIs
	 * @param implProjects
	 *            list of likely implementations.
	 * @return this
	 */
	public ApiImplMapping enhance(N4JSWorkspaceConfigSnapshot wc,
			Iterable<? extends N4JSProjectConfigSnapshot> apiProjects,
			Iterable<? extends N4JSProjectConfigSnapshot> implProjects) {

		final Set<String> apiProjectsIds = StreamSupport.stream(apiProjects.spliterator(), false)
				.map(p -> p.getName()).collect(Collectors.toSet());

		for (N4JSProjectConfigSnapshot pImpl : implProjects) {
			for (ProjectReference pApiRef : pImpl.getProjectDescription().getImplementedProjects()) {
				N4JSProjectConfigSnapshot pApi = wc.findProjectByName(pApiRef.getProjectName());
				if (pApi != null) {
					// note: #getImplementedProjects() will return implemented projects from entire workspace,
					// so we here have to make sure pApi is contained in apiProjects
					if (apiProjectsIds.contains(pApi.getName()))
						this.put(pApi, pImpl);
				}
			}
		}
		return this;
	}

	/**
	 * Add a single API -> implementation association to the receiving mapping (if it is not present already).
	 */
	public void put(N4JSProjectConfigSnapshot api, N4JSProjectConfigSnapshot impl) {
		final N4JSProjectName apiId = api.getN4JSProjectName();
		if (apiId == null)
			return; // just ignore (complaining about this problem is not our business)
		final String implIdStr = impl.getImplementationId();
		final N4JSProjectName implId = implIdStr != null ? new N4JSProjectName(implIdStr) : null;
		if (implId == null) {
			projectsWithUndefImplIds.add(impl);
			return;
		}
		ApiImplMapping.ApiImplAssociation assoc = assocs.get(apiId);
		if (assoc == null) {
			assoc = new ApiImplAssociation(api);
			assocs.put(apiId, assoc);
		}
		final N4JSProjectConfigSnapshot replaced = assoc.putImpl(impl);
		if (replaced != null && !Objects.equals(replaced, impl)) {
			// ooops! we have several projects defining an implementation for project 'api' with the same
			// implementation id -> remember them!
			putConflict(apiId, implId, replaced, impl);
		}
	}

	private void putConflict(N4JSProjectName apiId, N4JSProjectName implId, N4JSProjectConfigSnapshot... culprit) {
		final Pair<N4JSProjectName, N4JSProjectName> apiId_implId = new Pair<>(apiId, implId);
		Set<N4JSProjectConfigSnapshot> culprits = conflicts.get(apiId_implId);
		if (culprits == null) {
			culprits = new LinkedHashSet<>();
			conflicts.put(apiId_implId, culprits);
		}
		culprits.addAll(Arrays.asList(culprit));
	}

	/**
	 * Tells whether this mapping has seen any invalid or inconsistent mappings (via method
	 * {@link #put(N4JSProjectConfigSnapshot, N4JSProjectConfigSnapshot)}).
	 * <p>
	 * Clients may choose to simply ignore this value; in case of error all other methods will fall back to a default
	 * fail-safe behavior (e.g. implementation projects with a missing <code>ImplementationId</code> manifest property
	 * will simply be ignored).
	 */
	public boolean hasErrors() {
		return !projectsWithUndefImplIds.isEmpty() || !conflicts.isEmpty();
	}

	/**
	 * In {@link #hasErrors() case of error}, returns human-readable error messages. Otherwise, an empty list will be
	 * returned.
	 */
	public List<String> getErrorMessages() {
		final List<String> msgs = new ArrayList<>();
		for (N4JSProjectConfigSnapshot p : projectsWithUndefImplIds) {
			msgs.add("project '" + p.getName() + "' does not define an ImplementationId in its manifest");
		}
		for (Map.Entry<Pair<N4JSProjectName, N4JSProjectName>, Set<N4JSProjectConfigSnapshot>> currConflict : conflicts
				.entrySet()) {
			final N4JSProjectName apiId = currConflict.getKey().getKey();
			final N4JSProjectName implId = currConflict.getKey().getValue();
			final Set<N4JSProjectConfigSnapshot> culprits = currConflict.getValue();
			final String culpritsStr = " - "
					+ culprits.stream().map(c -> c.getName()).collect(Collectors.joining("\n - "));
			msgs.add("several projects define an implementation for API project '" + apiId
					+ "' with implementation ID '" + implId + "':\n" + culpritsStr);
		}
		return msgs;
	}

	/**
	 * Tells whether this mapping is empty, i.e. does not contain any API -> implementation mappings.
	 */
	public boolean isEmpty() {
		return assocs.isEmpty();
	}

	/**
	 * Returns the project IDs of all API projects.
	 */
	public List<N4JSProjectName> getApiIds() {
		final List<N4JSProjectName> allApiIds = new ArrayList<>(assocs.keySet());
		Collections.sort(allApiIds);
		return allApiIds;
	}

	/**
	 * Returns all implementation IDs contained in the receiving ApiImplMapping, i.e. all IDs for which at least one
	 * implementation project exists in the receiving ApiImplMapping.
	 * <p>
	 * This does *not* return the project IDs of implementation projects, but their implementation IDs (i.e. the IDs
	 * defined via property 'ImplementationId' in the manifest of implementation projects).
	 */
	public List<N4JSProjectName> getAllImplIds() {
		final List<N4JSProjectName> allImplIds = new ArrayList<>(assocs.values().stream()
				.flatMap(p -> p.getImplIds().stream())
				.collect(Collectors.toSet()));
		Collections.sort(allImplIds);
		return allImplIds;
	}

	/**
	 * Returns <code>true</code> iff this mapping contains an API -> implementation association p1 -> p2 with p1 having
	 * an projectName equal to the given name.
	 */
	public boolean isApi(N4JSProjectName projectName) {
		return assocs.containsKey(projectName);
	}

	/**
	 * Returns the API project with the given projectName or <code>null</code> if this mapping does not contain any API
	 * -> implementation associations for an API project with the given projectName.
	 */
	public N4JSProjectConfigSnapshot getApi(N4JSProjectName apiProjectName) {
		final ApiImplMapping.ApiImplAssociation assoc = assocs.get(apiProjectName);
		return assoc != null ? assoc.api : null;
	}

	/**
	 * Returns all implementation projects for the API project with the given projectName registered in this mapping.
	 */
	public Collection<N4JSProjectConfigSnapshot> getImpls(N4JSProjectName apiProjectName) {
		final ApiImplMapping.ApiImplAssociation pair = assocs.get(apiProjectName);
		return pair != null ? pair.getImpls() : Collections.emptyList();
	}

	/**
	 * Same as {@link #getImpls(N4JSProjectName)}, but returns the implementation IDs of the implementation projects,
	 * not the projects themselves.
	 */
	public Set<N4JSProjectName> getImplIds(N4JSProjectName apiProjectName) {
		final ApiImplMapping.ApiImplAssociation pair = assocs.get(apiProjectName);
		return pair != null ? pair.getImplIds() : Collections.emptySet();
	}

	/**
	 * Returns the implementation project for the API project with projectName <code>apiProjectName</code> for
	 * implementation ID <code>implId</code>.
	 * <p>
	 * Note that <code>apiProjectName</code> is a projectName (i.e. package.json property
	 * {@link PackageJsonProperties#NAME "name"}) whereas <code>implId</code> is an implementation ID (i.e. package.json
	 * property {@link PackageJsonProperties#IMPLEMENTATION_ID "implementationId"} and <b>not</b> <code>"name"</code>).
	 */
	public N4JSProjectConfigSnapshot getImpl(N4JSProjectName apiProjectName, N4JSProjectName implId) {
		final ApiImplMapping.ApiImplAssociation pair = assocs.get(apiProjectName);
		return pair != null ? pair.getImpl(implId) : null;
	}
}
