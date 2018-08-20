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

import org.eclipse.n4js.packagejson.PackageJsonConstants;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Helper class to collect associations between API and implementation projects and obtain the implementation IDs
 * defined in the implementation projects. Also performs some validation along the way, see {@link #hasErrors()}.
 */
public class ApiImplMapping {

	private final Map<String, ApiImplAssociation> assocs = new LinkedHashMap<>();
	private final Set<IN4JSProject> projectsWithUndefImplIds = new LinkedHashSet<>();
	private final Map<Pair<String, String>, Set<IN4JSProject>> conflicts = new LinkedHashMap<>();

	/** A 1:N association between a single API project and its N implementation projects. */
	private static final class ApiImplAssociation {

		public final IN4JSProject api;
		public final Map<String, IN4JSProject> impls = new LinkedHashMap<>();

		public ApiImplAssociation(IN4JSProject api) {
			this.api = api;
		}

		public Set<String> getImplIds() {
			return impls.keySet();
		}

		public Collection<IN4JSProject> getImpls() {
			return impls.values();
		}

		public IN4JSProject getImpl(String implId) {
			return impls.get(implId);
		}

		public IN4JSProject putImpl(IN4JSProject impl) {
			final String implId = impl.getImplementationId().or("undef implementation ID");
			if (implId != null)
				return impls.put(implId, impl);
			return null;
		}
	}

	/**
	 * Creates a new, empty mapping that can be filled via method {@link #put(IN4JSProject,IN4JSProject)}. Usually,
	 * client code should use one of the static <code>#of()</code> methods.
	 *
	 * @see ApiImplMapping#of(IN4JSCore)
	 * @see ApiImplMapping#of(Iterable)
	 * @see ApiImplMapping#of(Iterable,Iterable)
	 */
	public ApiImplMapping() {
	}

	/**
	 * Create mapping of all API / implementation projects in the workspace.
	 */
	public static ApiImplMapping of(IN4JSCore n4jsCore) {
		final ApiImplMapping mapping = new ApiImplMapping();
		n4jsCore.findAllProjects().forEach(
				pImpl -> pImpl.getImplementedProjects().forEach(
						pApi -> mapping.put(pApi, pImpl)));
		return mapping;
	}

	/**
	 * Create mapping of all API / implementation projects in the given list. The list may contain unrelated projects
	 * that are neither API nor implementation projects.
	 */
	public static ApiImplMapping of(Iterable<IN4JSProject> projects) {
		return of(projects, projects);
	}

	/**
	 * Create mapping of all API / implementation projects in the given lists. The mapping will only contain API
	 * projects also contained in list <code>apiProjects</code> and only implementation projects also contained in list
	 * <code>implProjects</code>, so the given lists serve as a kind of filter. The two given lists may contain
	 * unrelated projects that are neither API nor implementation projects and they may intersect.
	 */
	public static ApiImplMapping of(Iterable<IN4JSProject> apiProjects, Iterable<IN4JSProject> implProjects) {
		return new ApiImplMapping().enhance(apiProjects, implProjects);
	}

	/**
	 * Enhance the existing ApiImplMapping to also cover the given list of apiProjects. Only apiProjects contained in
	 * apiProject will be covered. Implementation of APIs not listed there are not taken into account.
	 *
	 * @param apiProjects
	 *            list APIs
	 * @param implProjects
	 *            list of likely implementations.
	 * @return this
	 */
	public ApiImplMapping enhance(Iterable<IN4JSProject> apiProjects, Iterable<IN4JSProject> implProjects) {
		final Set<String> apiProjectsIds = StreamSupport.stream(apiProjects.spliterator(), false)
				.map(p -> p.getProjectName()).collect(Collectors.toSet());

		for (IN4JSProject pImpl : implProjects) {
			for (IN4JSProject pApi : pImpl.getImplementedProjects()) {
				// note: #getImplementedProjects() will return implemented projects from entire workspace,
				// so we here have to make sure pApi is contained in apiProjects
				if (apiProjectsIds.contains(pApi.getProjectName()))
					this.put(pApi, pImpl);
			}
		}
		return this;
	}

	/**
	 * Add a single API -> implementation association to the receiving mapping (if it is not present already).
	 */
	public void put(IN4JSProject api, IN4JSProject impl) {
		final String apiId = api.getProjectName();
		if (apiId == null)
			return; // just ignore (complaining about this problem is not our business)
		final String implId = impl.getImplementationId().orNull();
		if (implId == null) {
			projectsWithUndefImplIds.add(impl);
			return;
		}
		ApiImplMapping.ApiImplAssociation assoc = assocs.get(apiId);
		if (assoc == null) {
			assoc = new ApiImplAssociation(api);
			assocs.put(apiId, assoc);
		}
		final IN4JSProject replaced = assoc.putImpl(impl);
		if (replaced != null && !Objects.equals(replaced, impl)) {
			// ooops! we have several projects defining an implementation for project 'api' with the same
			// implementation id -> remember them!
			putConflict(apiId, implId, replaced, impl);
		}
	}

	private void putConflict(String apiId, String implId, IN4JSProject... culprit) {
		final Pair<String, String> apiId_implId = new Pair<>(apiId, implId);
		Set<IN4JSProject> culprits = conflicts.get(apiId_implId);
		if (culprits == null) {
			culprits = new LinkedHashSet<>();
			conflicts.put(apiId_implId, culprits);
		}
		culprits.addAll(Arrays.asList(culprit));
	}

	/**
	 * Tells whether this mapping has seen any invalid or inconsistent mappings (via method
	 * {@link #put(IN4JSProject, IN4JSProject)}).
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
		for (IN4JSProject p : projectsWithUndefImplIds) {
			msgs.add("project '" + p.getProjectName() + "' does not define an ImplementationId in its manifest");
		}
		for (Map.Entry<Pair<String, String>, Set<IN4JSProject>> currConflict : conflicts.entrySet()) {
			final String apiId = currConflict.getKey().getKey();
			final String implId = currConflict.getKey().getValue();
			final Set<IN4JSProject> culprits = currConflict.getValue();
			final String culpritsStr = " - "
					+ culprits.stream().map(c -> c.getProjectName()).collect(Collectors.joining("\n - "));
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
	public List<String> getApiIds() {
		final List<String> allApiIds = new ArrayList<>(assocs.keySet());
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
	public List<String> getAllImplIds() {
		final List<String> allImplIds = new ArrayList<>(assocs.values().stream()
				.flatMap(p -> p.getImplIds().stream())
				.collect(Collectors.toSet()));
		Collections.sort(allImplIds);
		return allImplIds;
	}

	/**
	 * Returns <code>true</code> iff this mapping contains an API -> implementation association p1 -> p2 with p1 having
	 * an projectName equal to the given name.
	 */
	public boolean isApi(String projectName) {
		return assocs.containsKey(projectName);
	}

	/**
	 * Returns the API project with the given projectName or <code>null</code> if this mapping does not contain any API
	 * -> implementation associations for an API project with the given projectName.
	 */
	public IN4JSProject getApi(String apiProjectName) {
		final ApiImplMapping.ApiImplAssociation assoc = assocs.get(apiProjectName);
		return assoc != null ? assoc.api : null;
	}

	/**
	 * Returns all implementation projects for the API project with the given projectName registered in this mapping.
	 */
	public Collection<IN4JSProject> getImpls(String apiProjectName) {
		final ApiImplMapping.ApiImplAssociation pair = assocs.get(apiProjectName);
		return pair != null ? pair.getImpls() : Collections.emptyList();
	}

	/**
	 * Same as {@link #getImpls(String)}, but returns the implementation IDs of the implementation projects, not the
	 * projects themselves.
	 */
	public Set<String> getImplIds(String apiProjectName) {
		final ApiImplMapping.ApiImplAssociation pair = assocs.get(apiProjectName);
		return pair != null ? pair.getImplIds() : Collections.emptySet();
	}

	/**
	 * Returns the implementation project for the API project with projectName <code>apiProjectName</code> for
	 * implementation ID <code>implId</code>.
	 * <p>
	 * Note that <code>apiProjectName</code> is a projectName (i.e. package.json property
	 * {@link PackageJsonConstants#PROP__NAME "name"}) whereas <code>implId</code> is an implementation ID (i.e.
	 * package.json property {@link PackageJsonConstants#PROP__IMPLEMENTATION_ID "implementationId"} and <b>not</b>
	 * <code>"name"</code>).
	 */
	public IN4JSProject getImpl(String apiProjectName, String implId) {
		final ApiImplMapping.ApiImplAssociation pair = assocs.get(apiProjectName);
		return pair != null ? pair.getImpl(implId) : null;
	}
}
