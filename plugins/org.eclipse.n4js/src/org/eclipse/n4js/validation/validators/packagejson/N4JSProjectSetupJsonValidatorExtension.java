/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.validation.validators.packagejson;

import static com.google.common.base.Strings.nullToEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEV_DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.EXTENDED_RUNTIME_ENVIRONMENT;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.IMPLEMENTATION_ID;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.IMPLEMENTED_PROJECTS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.MODULE_FILTERS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NAME;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NV_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NV_SOURCE_CONTAINER;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROJECT_TYPE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROVIDED_RUNTIME_LIBRARIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.REQUIRED_RUNTIME_LIBRARIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.SOURCES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.TESTED_PROJECTS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.WORKSPACES_ARRAY;
import static org.eclipse.n4js.packagejson.projectDescription.ProjectType.API;
import static org.eclipse.n4js.packagejson.projectDescription.ProjectType.LIBRARY;
import static org.eclipse.n4js.packagejson.projectDescription.ProjectType.PLAINJS;
import static org.eclipse.n4js.packagejson.projectDescription.ProjectType.RUNTIME_ENVIRONMENT;
import static org.eclipse.n4js.packagejson.projectDescription.ProjectType.RUNTIME_LIBRARY;
import static org.eclipse.n4js.packagejson.projectDescription.ProjectType.VALIDATION;
import static org.eclipse.n4js.validation.IssueCodes.DUPLICATE_PROJECT_REF;
import static org.eclipse.n4js.validation.IssueCodes.EXTERNAL_PROJECT_REFERENCES_WORKSPACE_PROJECT;
import static org.eclipse.n4js.validation.IssueCodes.INVALID_API_PROJECT_DEPENDENCY;
import static org.eclipse.n4js.validation.IssueCodes.INVALID_FEATURE_FOR_PROJECT_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.INVALID_PROJECT_TYPE_REF;
import static org.eclipse.n4js.validation.IssueCodes.MISMATCHING_IMPLEMENTATION_ID;
import static org.eclipse.n4js.validation.IssueCodes.MISMATCHING_TESTED_PROJECT_TYPES;
import static org.eclipse.n4js.validation.IssueCodes.NON_EXISTING_PROJECT;
import static org.eclipse.n4js.validation.IssueCodes.NO_MATCHING_VERSION;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_APIIMPL_MISSING_IMPL_ID;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_EMPTY_PROJECT_REFERENCE;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_FILTER_NO_N4JS_MATCH;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_IMPL_PROJECT_IS_MISSING_FOR_TYPE_DEF;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_INVALID_MODULE_FILTER_SPECIFIER_EMPTY;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_INVALID_WILDCARD;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_MISSING_DEPENDENCY_N4JS_RUNTIME;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_MODULE_FILTER_DOES_NOT_MATCH;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_NO_RELATIVE_NAVIGATION;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_PNPM_WORKSPACES_OVERRIDE;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_PROJECT_REFERENCE_MUST_BE_DEPENDENCY;
import static org.eclipse.n4js.validation.IssueCodes.PKGJ_WRONG_DEPENDENCY_N4JS_RUNTIME;
import static org.eclipse.n4js.validation.IssueCodes.POLY_CLASH_IN_RUNTIMEDEPENDENCY;
import static org.eclipse.n4js.validation.IssueCodes.POLY_CLASH_IN_RUNTIMEDEPENDENCY_MULTI;
import static org.eclipse.n4js.validation.IssueCodes.POLY_ERROR_IN_RUNTIMEDEPENDENCY;
import static org.eclipse.n4js.validation.IssueCodes.PROJECT_DEPENDENCY_CYCLE;
import static org.eclipse.n4js.validation.IssueCodes.PROJECT_REFERENCES_ITSELF;
import static org.eclipse.n4js.validation.IssueCodes.SRCTEST_NO_TESTLIB_DEP;
import static org.eclipse.n4js.validation.validators.packagejson.ProjectTypePredicate.anyOf;
import static org.eclipse.n4js.validation.validators.packagejson.ProjectTypePredicate.not;
import static org.eclipse.n4js.validation.validators.packagejson.ProjectTypePredicate.or;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatMap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sort;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sortBy;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.tail;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toMap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterSpecifier;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.SemverMatcher;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.DependencyTraverser;
import org.eclipse.n4js.utils.DependencyTraverser.DependencyVisitor;
import org.eclipse.n4js.utils.ModuleFilterUtils;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;
import org.eclipse.n4js.validation.helper.SourceContainerAwareDependencyProvider;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.n4js.xtext.workspace.BuildOrderInfo;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IContainer.Manager;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A JSON validator extension that validates {@code package.json} resources in the context of higher-level concepts such
 * as project references, the general project setup and feature restrictions.
 *
 * Generally, this validator includes constraints that are implemented based on the converted {@link ProjectDescription}
 * as it can be obtained from the {@link ProjectDescriptionLoader}. This especially includes non-local validation such
 * as the resolution of referenced projects.
 *
 * For lower-level, structural and local validations with regard to {@code package.json} files , see
 * {@link PackageJsonValidatorExtension}.
 */
@Singleton
@SuppressWarnings("javadoc")
public class N4JSProjectSetupJsonValidatorExtension extends AbstractPackageJSONValidatorExtension {

	private static final Logger LOGGER = Logger.getLogger(N4JSProjectSetupJsonValidatorExtension.class);

	static ProjectTypePredicate API_TYPE = anyOf(API);
	static ProjectTypePredicate RE_TYPE = anyOf(RUNTIME_ENVIRONMENT);
	static ProjectTypePredicate RL_TYPE = anyOf(RUNTIME_LIBRARY);
	static ProjectTypePredicate TEST_TYPE = anyOf(ProjectType.TEST);
	static ProjectTypePredicate RE_OR_RL_TYPE = anyOf(RUNTIME_ENVIRONMENT, RUNTIME_LIBRARY);
	static ProjectTypePredicate PLAINJS_TYPE = anyOf(PLAINJS);

	/**
	 * Key to store a converted ProjectDescription instance in the validation context for re-use across different
	 * check-methods
	 *
	 * @apiNote {@link #getProjectDescription()}
	 */
	private static final String PROJECT_DESCRIPTION_CACHE = "PROJECT_DESCRIPTION_CACHE";

	/**
	 * Key to store a map of all available projects in the validation context for re-use across different check-methods.
	 *
	 * @apiNote {@link #getAllProjectsById()}
	 */
	private static String ALL_EXISTING_PROJECT_CACHE = "ALL_EXISTING_PROJECT_CACHE";

	/**
	 * Key to store a map of all declared project dependencies in the validation context for re-use across different
	 * check-methods.
	 *
	 * @apiNote {@link #getDeclaredProjectDependencies()}
	 */
	private static String DECLARED_DEPENDENCIES_CACHE = "DECLARED_DEPENDENCIES_CACHE";

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private Manager containerManager;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	protected N4JSElementKeywordProvider keywordProvider;

	@Inject
	protected SemverHelper semverHelper;

	@Inject
	protected NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	/**
	 * According to IDESpec §§12.04 Polyfills at most one Polyfill can be provided for a class. Here the consistency
	 * according to the given combination of runtime-environment and runtime-libraries of the project definition will be
	 * checked.
	 */
	@Check
	public void checkConsistentPolyfills(JSONDocument document) {
		// Take the RTE and RTL's check for duplicate fillings.
		// lookup of names in project description
		Map<N4JSPackageName, JSONStringLiteral> mQName2rtDep = new HashMap<>();

		N4JSWorkspaceConfigSnapshot ws = workspaceAccess.getWorkspaceConfig(document);

		ProjectDescription description = getProjectDescription();
		String projectName = description.getPackageName();

		// if the project name cannot be determined, exit early
		if (projectName == null) {
			return;
		}

		// gather required runtime libraries in terms of JSONStringLiterals
		JSONArray requiredRuntimeLibrariesValue = getSingleDocumentValue(REQUIRED_RUNTIME_LIBRARIES, JSONArray.class);
		if (requiredRuntimeLibrariesValue == null) {
			return;
		}
		Iterable<? extends JSONStringLiteral> rteAndRtl = filter(requiredRuntimeLibrariesValue.getElements(),
				JSONStringLiteral.class);

		// Describing Self-Project as RuntimeDependency to handle clash with filled Members from current Project
		// consistently.
		JSONStringLiteral selfProject = JSONModelUtils.createStringLiteral(projectName);
		N4JSProjectConfigSnapshot optOwnProject = getProjectConfigSnapshot();
		if (optOwnProject != null) {
			rteAndRtl = Iterables.concat(rteAndRtl, List.of(selfProject));
		}

		for (JSONStringLiteral libraryLiteral : rteAndRtl) {
			if (null != libraryLiteral) {
				String libPPqname = libraryLiteral.getValue();
				mQName2rtDep.put(new N4JSPackageName(libPPqname), libraryLiteral);
			}
		}

		// own Project can provide filled members as well, if of type RuntimeEnvironment/RuntimeLibrary:
		List<IEObjectDescription> allPolyFillTypes = getAllNonStaticPolyfills(document.eResource());

		// 1.a. For Each containing File: get the Exported Polyfills: <QN, PolyFilledProvision>
		// if the file is from our self, then ignore it; validation will be done for the local file separately.
		LinkedListMultimap<String, PolyFilledProvision> exportedPolyfills_QN_to_PolyProvision = LinkedListMultimap
				.create();
		for (IEObjectDescription ieoT : allPolyFillTypes) {
			N4JSProjectConfigSnapshot optSrcContainer = ws.findProjectContaining(ieoT.getEObjectURI());
			if (optSrcContainer != null) {
				N4JSPackageName depQName = new N4JSPackageName(optSrcContainer.getName());
				JSONStringLiteral dependency = mQName2rtDep.get(depQName);
				if (dependency == null) {
					// TODO IDE-1735 typically a static Polyfill - can only be used inside of a single project
				} else if (dependency != selfProject) {
					exportedPolyfills_QN_to_PolyProvision.put(
							ieoT.getQualifiedName().toString(),
							new PolyFilledProvision(depQName, dependency, ieoT));
				}
			} else {
				// TODO GH-2002 consider removing temporary debug logging
				N4JSProjectConfigSnapshot containingProject = ws.findProjectByNestedLocation(ieoT.getEObjectURI());
				String projectLookupInfo = (containingProject != null)
						? "(info: found containing project " + containingProject.getName() + ")"
						: "(info: could not find containing project for URI)";
				throw new IllegalStateException("No container library found for " + ieoT.getQualifiedName()
						+ " with URI: " + ieoT.getEObjectURI() + "\n"
						+ projectLookupInfo);
			}
		}

		IResourceDescriptions xtextIndex = workspaceAccess.getXtextIndex(document).orNull();
		ResourceSet contextResourceSet = document.eResource() == null ? null : document.eResource().getResourceSet();
		if (xtextIndex == null || contextResourceSet == null) {
			return;
		}

		// Search for clashes in Polyfill:
		// markermap: {lib1,lib2,...}->"filledname"
		// Value is QualifiedName of polyfill_Element
		Multimap<Set<JSONStringLiteral>, String> markerMapLibs2FilledName = LinkedListMultimap.create();
		for (String polyExport_QN : exportedPolyfills_QN_to_PolyProvision.keySet()) {
			List<PolyFilledProvision> polyProvisions = exportedPolyfills_QN_to_PolyProvision.get(polyExport_QN);
			if (polyProvisions.size() > 1) {

				// For each filled member determine the set of fillers:
				Multimap<String, PolyFilledProvision> m = LinkedListMultimap.create(); // memberName->PolyProvisionA,PolyProvisionB
																						// ...
				for (PolyFilledProvision prov : polyProvisions) {

					// contextScope.getSingleElement( prov.ieoDescrOfPolyfill.qualifiedName )
					EObject eoPolyFiller = prov.ieoDescrOfPolyfill.getEObjectOrProxy();

					if (eoPolyFiller instanceof TClassifier) {
						// WARNING: simply doing
						// val resolvedEoPolyFiller = EcoreUtil.resolve(eoPolyFiller, document.eResource);
						// here would result in the resource of eoPolyFiller being loaded from source, because
						// we are in the context of a package.json file (not an N4JSResource, etc.) and therefore
						// do not get automatic "load from index" behavior!
						TClassifier resolvedEoPolyFiller = (TClassifier) eoPolyFiller;
						if (resolvedEoPolyFiller.eIsProxy()) {
							URI targetObjectURI = prov.ieoDescrOfPolyfill.getEObjectURI();
							resolvedEoPolyFiller = (TClassifier) workspaceAccess.loadEObjectFromIndex(xtextIndex,
									contextResourceSet, targetObjectURI, false);
						}
						if (resolvedEoPolyFiller == null || resolvedEoPolyFiller.eIsProxy()) {
							// unable to resolve -> ignore
						} else if (!resolvedEoPolyFiller.isPolyfill()) {
							throw new IllegalStateException(
									"Expected a polyfill, but wasn't: " + resolvedEoPolyFiller.getName());
						} else { // yes, it's an polyfiller.
							for (TMember member : resolvedEoPolyFiller.getOwnedMembers()) {

								// Usually if the name is equal - no matter what the rest of the signature tells - it's
								// a clash.
								// TODO Situation which are allowed: one provides getter only, other provides setter
								// only.
								m.put(member.getName(), prov);
							}
						}
					}
				}

				for (String filledInMemberName : m.keySet()) {
					Collection<PolyFilledProvision> providers = m.get(filledInMemberName);
					if (providers.size() > 1) {

						// register for error:
						Set<JSONStringLiteral> keySet = new HashSet<>();
						for (PolyFilledProvision prov : providers) {
							keySet.add(prov.libraryProjectReferenceLiteral);
						}
						String filledTypeFQN = head(providers).descriptionStandard;// or: polyExport_QN
						String message = filledTypeFQN + "#" + filledInMemberName;
						markerMapLibs2FilledName.put(keySet, message);
					}
				}

			}
		}

		// just issue markers. only case a,b and c without self
		// Cases to consider:
		// a) standard - clash with multiple dependent libraries (not self)
		// b) clash with only one library (not self) --> library is not error-free
		// obsolete: c) clash with multiple libraries and self --> something
		// obsolete: d) clash with only one library and it is self --> we have errors
		//
		for (Set<JSONStringLiteral> keyS : markerMapLibs2FilledName.keySet()) {

			Collection<String> polyFilledMemberAsStrings = markerMapLibs2FilledName.get(keyS);
			String libsString = Strings.join(", ", sort(map(keyS, it -> it.getValue())));

			String userPresentablePolyFills = Strings.join(", ",
					sort(map(polyFilledMemberAsStrings, it -> '"' + it + '"')));

			if (keyS.size() > 1) {

				// case a: multiple dependencies clash
				IssueItem issueItem = (polyFilledMemberAsStrings.size() == 1)
						? POLY_CLASH_IN_RUNTIMEDEPENDENCY.toIssueItem(libsString, userPresentablePolyFills)
						: POLY_CLASH_IN_RUNTIMEDEPENDENCY_MULTI.toIssueItem(libsString, userPresentablePolyFills);

				// add Issue for each
				for (JSONStringLiteral it : keyS) {
					addIssue(it, issueItem);
				}
			} else {
				// case b: not error-free:
				addIssue(head(keyS), POLY_ERROR_IN_RUNTIMEDEPENDENCY.toIssueItem(libsString, userPresentablePolyFills));

			}

		}
	}

	/**
	 * Get a list of all polyfill types & IEObjecdescriptions accessible in the whole Project.
	 *
	 * @param projectDescriptionResourceUsedAsContext
	 *            just a resource to build the scope
	 * @return List of Type->IEObjectdescription
	 */
	private List<IEObjectDescription> getAllNonStaticPolyfills(Resource projectDescriptionResourceUsedAsContext) {

		XtextResource asXtextRes = (XtextResource) projectDescriptionResourceUsedAsContext;
		IResourceDescription resDescr = asXtextRes.getResourceServiceProvider().getResourceDescriptionManager()
				.getResourceDescription(asXtextRes);

		List<IContainer> visibleContainers = containerManager.getVisibleContainers(
				resDescr,
				resourceDescriptionsProvider.getResourceDescriptions(projectDescriptionResourceUsedAsContext));

		List<IEObjectDescription> types = new ArrayList<>();
		for (IEObjectDescription descr : flatten(
				map(visibleContainers, it -> it.getExportedObjectsByType(TypesPackage.Literals.TYPE)))) {
			boolean isPolyFill = N4JSResourceDescriptionStrategy.getPolyfill(descr);
			boolean isStaticPolyFill = N4JSResourceDescriptionStrategy.getStaticPolyfill(descr);

			if (isPolyFill && !isStaticPolyFill) {
				types.add(descr);
			}
		}
		return types;
	}

	@CheckProperty(property = DEPENDENCIES)
	public void checkCyclicDependencies(JSONValue dependenciesValue) {
		// exit early in case of a malformed dependencies section (structural validation is handled elsewhere)
		if (!(dependenciesValue instanceof JSONObject)) {
			return;
		}

		N4JSWorkspaceConfigSnapshot wc = workspaceAccess.getWorkspaceConfig(dependenciesValue);

		// pairs that represent project dependencies
		EList<NameValuePair> dependencyPairs = ((JSONObject) dependenciesValue).getNameValuePairs();
		// obtain project description for higher-level access to contained information
		String projectName = getProjectDescription().getId();
		// get project build order of current build
		BuildOrderInfo projectOrderInfo = wc.getBuildOrderInfo();

		// check for dependency cycles
		for (List<String> projectCycle : projectOrderInfo.getProjectCycles()) {
			if (projectCycle.contains(projectName)) {
				for (NameValuePair dependencyPair : dependencyPairs) {
					for (String projectCycleName : projectCycle) {
						if (projectCycleName.endsWith("/" + dependencyPair.getName())) {
							String dependencyCycle = Strings.join(", ", projectCycle);
							addIssue(dependencyPair, JSONPackage.Literals.NAME_VALUE_PAIR__NAME,
									PROJECT_DEPENDENCY_CYCLE.toIssueItem(dependencyCycle));
						}
					}
				}
			}
		}

		// check for required dependency to mangelhaft
		if (projectOrderInfo.getProjectCycles().isEmpty()) {
			// the following cannot be checked in presence of dependency cycles
			N4JSProjectConfigSnapshot project = workspaceAccess.findProjectByNestedLocation(getDocument(),
					getDocument().eResource().getURI());
			if (null != project) {
				holdsProjectWithTestFragmentDependsOnTestLibrary(wc, project);
			}
		}
	}

	/**
	 * Checks if a project containing {@link SourceContainerType#TEST} depends (directly or transitively) on a
	 * {@link ProjectType#RUNTIME_LIBRARY} test runtime library.
	 */
	private void holdsProjectWithTestFragmentDependsOnTestLibrary(N4JSWorkspaceConfigSnapshot wc,
			N4JSProjectConfigSnapshot project) {

		JSONValue sourcesSection = getSingleDocumentValue(SOURCES, JSONValue.class);
		List<SourceContainerDescription> sourceContainers = PackageJsonUtils
				.asSourceContainerDescriptionsOrEmpty(sourcesSection);

		if (sourceContainers == null) {
			return;
		}

		boolean hasTestFragment = findFirst(sourceContainers,
				sf -> SourceContainerType.TEST.equals(sf.getType())) != null;

		if (!hasTestFragment) {
			return;
		}

		if (!anyDependsOnTestLibrary(wc, List.of(project))) {
			addIssuePreferred(Collections.emptyList(), SRCTEST_NO_TESTLIB_DEP.toIssueItem(N4JSGlobals.MANGELHAFT));
		}
	}

	/**
	 * check if any project in the list has dependency on test library, if so return true. Otherwise invoke recursively
	 * in dependencies list of each project in initial list.
	 *
	 * @returns true if any of the projects in the provided list depends (transitively) on the test library.
	 */
	private boolean anyDependsOnTestLibrary(N4JSWorkspaceConfigSnapshot wc,
			List<? extends N4JSProjectConfigSnapshot> projects) {
		SourceContainerAwareDependencyProvider dependencyProvider = new SourceContainerAwareDependencyProvider(wc,
				true);
		HasTestDependencyVisitor hasTestDependencyVisitor = new HasTestDependencyVisitor();
		for (N4JSProjectConfigSnapshot project : projects) {
			DependencyTraverser<N4JSProjectConfigSnapshot> dependencyTraverser = new DependencyTraverser<>(project,
					hasTestDependencyVisitor, dependencyProvider, true);
			dependencyTraverser.traverse();
		}
		return hasTestDependencyVisitor.hasTestDependencies;
	}

	static class HasTestDependencyVisitor implements DependencyVisitor<N4JSProjectConfigSnapshot> {
		boolean hasTestDependencies = false;

		@Override
		public void accept(N4JSProjectConfigSnapshot project) {
			if (hasTestDependency(project)) {
				hasTestDependencies = true;
			}
		}

		private boolean hasTestDependency(N4JSProjectConfigSnapshot p) {
			for (String depNameRaw : p.getDependencies()) {
				N4JSPackageName depName = new N4JSPackageName(depNameRaw);
				if (N4JSGlobals.MANGELHAFT.equals(depName) || N4JSGlobals.MANGELHAFT_ASSERT.equals(depName)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Checks whether a test project either tests APIs or libraries. Raises a validation issue, if the test project
	 * tests both APIs and libraries. Does nothing if the project description of the validated project is NOT a test
	 * project.
	 */
	@CheckProperty(property = TESTED_PROJECTS)
	public void checkTestedProjectsType(JSONValue testedProjectsValue) {
		ProjectDescription description = getProjectDescription();

		// make sure the listed tested projects do not mismatch in their type (API vs. library)
		if (ProjectType.TEST == description.getProjectType()) {
			List<ProjectReference> projects = description.getTestedProjects();
			if (!projects.isEmpty()) {
				Map<String, N4JSProjectConfigSnapshot> allProjects = getAllProjectsById();
				ProjectReference head = head(projects);
				N4JSProjectConfigSnapshot pcs = getProjectConfigSnapshot();
				String headProjectId = pcs.getProjectIdForPackageName(head.getPackageName());
				N4JSProjectConfigSnapshot headProject = allProjects.get(headProjectId);
				ProjectType refProjectType = headProject == null ? null : headProject.getType();

				// check whether 'projects' contains a dependency to an existing project of different
				// type than 'head'
				if (exists(projects, testedProject -> {
					String projectId = pcs.getProjectIdForPackageName(testedProject.getPackageName());
					N4JSProjectConfigSnapshot prj = allProjects.get(projectId);
					return prj != null && refProjectType != prj.getType();
				})) {
					addIssue(
							testedProjectsValue,
							MISMATCHING_TESTED_PROJECT_TYPES.toIssueItem());
				}
			}
		}
	}

	/**
	 * Checks whether a library project, that belongs to a specific implementation (has defined implementation ID) does
	 * not depend on any other libraries that belong to any other implementation. In such cases, raises validation
	 * issue.
	 */
	@CheckProperty(property = DEPENDENCIES)
	public void checkHasConsistentImplementationIdChain(JSONValue dependenciesValue) {
		// exit early in case of a malformed dependencies section (structural validation is handled elsewhere)
		if (!(dependenciesValue instanceof JSONObject)) {
			return;
		}

		// pairs that represent project dependencies
		EList<NameValuePair> dependencyPairs = ((JSONObject) dependenciesValue).getNameValuePairs();
		// obtain project description for higher-level access to contained information
		ProjectDescription description = getProjectDescription();

		if (LIBRARY == description.getProjectType()
				&& !com.google.common.base.Strings.isNullOrEmpty(description.getImplementationId())) {

			String expectedImplementationId = description.getImplementationId();
			Map<String, N4JSProjectConfigSnapshot> allProjects = getAllProjectsById();
			N4JSProjectConfigSnapshot pcs = getProjectConfigSnapshot();

			for (NameValuePair pair : filterNull(dependencyPairs)) {
				String dependencyProjectName = pcs.getProjectIdForPackageName(pair.getName());
				N4JSProjectConfigSnapshot pcs2 = allProjects.get(dependencyProjectName);
				String actualImplementationId = pcs2 == null ? null : pcs2.getImplementationId();
				if (actualImplementationId != null
						&& !Objects.equals(actualImplementationId, expectedImplementationId)) {
					addIssue(pair, MISMATCHING_IMPLEMENTATION_ID.toIssueItem(expectedImplementationId, pair.getName(),
							actualImplementationId));
				}
			}
		}
	}

	/**
	 * Checks if any transitive external dependency of a workspace project references to a workspace project. If so,
	 * raises a validation warning.
	 */
	@CheckProperty(property = DEPENDENCIES)
	public void checkExternalProjectDoesNotReferenceWorkspaceProject(JSONValue dependenciesValue) {
		Map<String, N4JSProjectConfigSnapshot> allProjects = getAllProjectsById();
		ProjectDescription description = getProjectDescription();

		// if the project name cannot be determined, exit early
		if (description.getPackageName() == null) {
			return;
		}

		N4JSProjectConfigSnapshot currentProject = allProjects.get(description.getPackageName());

		// Nothing to do with non-existing, missing and/or external projects.
		if (null == currentProject || currentProject.isExternal()) {
			return;
		}

		Set<String> visitedProjectNames = new HashSet<>();
		Stack<N4JSProjectConfigSnapshot> stack = new Stack<>();
		stack.addAll(toList(filter(
				filterNull(map(currentProject.getDependencies(), it -> allProjects.get(it))),
				pcs -> pcs.isExternal())));

		while (!stack.isEmpty()) {
			N4JSProjectConfigSnapshot actual = stack.pop();
			String actualId = actual.getPackageName();
			Preconditions.checkState(actual.isExternal(),
					"Implementation error. Only external projects are expected: %s.".formatted(actual));

			if (!visitedProjectNames.add(actualId)) {
				// Cyclic dependency. This will be handles somewhere else. Nothing to do.
				return;
			}

			Iterable<N4JSProjectConfigSnapshot> actualDirectDependencies = filterNull(
					map(actual.getDependencies(), it -> allProjects.get(it)));
			// If external has any *NON* external dependency we should raise a warning.
			N4JSProjectConfigSnapshot workspaceDependency = findFirst(actualDirectDependencies,
					pcs -> !pcs.isExternal());
			if (null != workspaceDependency) {
				String workspaceDependencyId = workspaceDependency.getName();
				addIssue(
						dependenciesValue,
						EXTERNAL_PROJECT_REFERENCES_WORKSPACE_PROJECT.toIssueItem(actualId, workspaceDependencyId));
				return;
			}

			stack.addAll(toList(filter(actualDirectDependencies, pcs -> pcs.isExternal())));
		}
	}

	@Check
	public void checkDependenciesAndDevDependencies(JSONDocument document) {
		// determine whether current project is external
		N4JSProjectConfigSnapshot project = workspaceAccess.findProjectByNestedLocation(document,
				document.eResource().getURI());
		boolean isExternal = project != null && project.isExternal();

		// collect all references, skip devDependencies if project is external, because these
		// are not installed by npm for transitive dependencies
		List<ValidationProjectReference> references = toList(getDependencies(!isExternal));

		if (!references.isEmpty()) {
			checkReferencedProjects(references, createDependenciesPredicate(), "dependencies or devDependencies", false,
					false);
		}

		// special validation for API projects
		if (getProjectDescription().getProjectType() == API) {
			internalValidateAPIProjectReferences(references);
		}
	}

	@Check
	public void checkMandatoryDependencies(@SuppressWarnings("unused") JSONDocument document) {

		ProjectDescription description = getProjectDescription();
		String projectName = description.getPackageName();
		ProjectType projectType = description.getProjectType();
		if (Objects.equals(projectName, N4JSGlobals.N4JS_RUNTIME.getRawName())) {
			return; // not applicable ("n4js-runtime" does not need to have a dependency to "n4js-runtime"!)
		}
		if (!N4JSGlobals.PROJECT_TYPES_REQUIRING_N4JS_RUNTIME.contains(projectType)) {
			return; // not applicable
		}

		Iterable<NameValuePair> dependencies = flatMap(filter(getDocumentValues(DEPENDENCIES), JSONObject.class),
				obj -> obj.getNameValuePairs());
		if (!exists(dependencies, nvp -> Objects.equals(nvp.getName(), N4JSGlobals.N4JS_RUNTIME.getRawName()))) {
			Iterable<NameValuePair> devDependencies = flatMap(
					filter(getDocumentValues(DEV_DEPENDENCIES), JSONObject.class), obj -> obj.getNameValuePairs());
			NameValuePair matchingDevDep = findFirst(devDependencies,
					nvp -> Objects.equals(nvp.getName(), N4JSGlobals.N4JS_RUNTIME.getRawName()));
			if (matchingDevDep == null) {
				// dependency to 'n4js-runtime' missing entirely
				JSONValue projectTypeValue = head(getDocumentValues(PROJECT_TYPE));
				if (projectTypeValue != null) { // should always be non-null, because we check for 3 non-default project
												// types above!
					addIssue(projectTypeValue, PKGJ_MISSING_DEPENDENCY_N4JS_RUNTIME.toIssueItem());
				}
			} else {
				// dependency to 'n4js-runtime' defined in wrong section (under 'devDependencies' instead of
				// 'dependencies')
				addIssue(matchingDevDep, PKGJ_WRONG_DEPENDENCY_N4JS_RUNTIME.toIssueItem());
			}
		}
	}

	/**
	 * Returns a representation of all declared runtime dependencies of the currently validate document (cf.
	 * {@link #getDocument()}).
	 *
	 * @param includeDevDependencies
	 *            Specifies whether the returned iterable should also include devDependencies.
	 */
	private Iterable<ValidationProjectReference> getDependencies(boolean includeDevDependencies) {
		List<ValidationProjectReference> references = new ArrayList<>();

		for (JSONValue value : getDocumentValues(DEPENDENCIES)) {
			references.addAll(getReferencesFromDependenciesObject(value));
		}
		if (includeDevDependencies) {
			for (JSONValue value : getDocumentValues(DEV_DEPENDENCIES)) {
				references.addAll(getReferencesFromDependenciesObject(value));
			}
		}
		return references;
	}

	/**
	 * Checks that an API project does not declare any dependencies on implementation projects (library projects with
	 * implementation ID).
	 */
	public void internalValidateAPIProjectReferences(Iterable<ValidationProjectReference> references) {
		Map<String, N4JSProjectConfigSnapshot> allProjects = getAllProjectsById();
		Iterable<Pair<ValidationProjectReference, N4JSProjectConfigSnapshot>> libraryDependenciesWithImplId = filter(
				map(references, ref -> Pair.of(ref, allProjects.get(ref.referencedProjectId))),
				pair -> pair != null && pair.getValue() != null && pair.getValue().getType() == LIBRARY
						&& pair.getValue().getImplementationId() != null);

		for (Pair<ValidationProjectReference, N4JSProjectConfigSnapshot> projectPair : libraryDependenciesWithImplId) {
			ValidationProjectReference reference = projectPair.getKey();
			addIssue(reference.astRepresentation,
					INVALID_API_PROJECT_DEPENDENCY.toIssueItem(reference.referencedProjectName));
		}
	}

	/** Checks the 'n4js.extendedRuntimeEnvironment' section. */
	@CheckProperty(property = EXTENDED_RUNTIME_ENVIRONMENT)
	public void checkExtendedRuntimeEnvironment(JSONValue extendedRuntimeEnvironmentValue) {
		// make sure 'extendedRuntimeEnvironment' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("extended runtime environment", extendedRuntimeEnvironmentValue, RE_TYPE)) {
			return;
		}
		URI projectDescriptionFileURI = getDocument().eResource().getURI();
		N4JSProjectConfigSnapshot currentProject = workspaceAccess.findProjectByNestedLocation(getDocument(),
				projectDescriptionFileURI);
		List<ValidationProjectReference> references = getReferencesFromJSONStringLiteral(currentProject,
				extendedRuntimeEnvironmentValue);
		checkReferencedProjects(references, forN4jsProjects(RE_TYPE), "extended runtime environment", false, false);
	}

	/** Checks the 'n4js.requiredRuntimeLibraries' section. */
	@CheckProperty(property = REQUIRED_RUNTIME_LIBRARIES)
	public void checkRequiredRuntimeLibraries(JSONValue requiredRuntimeLibrariesValue) {
		// make sure 'requiredRuntimeLibraries' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("required runtime libraries", requiredRuntimeLibrariesValue, not(RE_TYPE))) {
			return;
		}

		List<ValidationProjectReference> references = getReferencesFromJSONStringArray(requiredRuntimeLibrariesValue);

		checkReferencedProjects(references, forN4jsProjects(RL_TYPE), "required runtime libraries", true, false);
	}

	/** Checks the 'n4js.providedRuntimeLibraries' section. */
	@CheckProperty(property = PROVIDED_RUNTIME_LIBRARIES)
	public void checkProvidedRuntimeLibraries(JSONValue providedRuntimeLibraries) {
		// make sure 'requiredRuntimeLibraries' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("provided runtime libraries", providedRuntimeLibraries, RE_TYPE)) {
			return;
		}

		List<ValidationProjectReference> references = getReferencesFromJSONStringArray(providedRuntimeLibraries);
		checkReferencedProjects(references, forN4jsProjects(RL_TYPE), "provided runtime libraries", false, false);
	}

	/** Checks the 'n4js.testedProjects' section. */
	@CheckProperty(property = TESTED_PROJECTS)
	public void checkTestedProjects(JSONValue testedProjectsValue) {
		// make sure 'testedProjects' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("tested projects", testedProjectsValue, TEST_TYPE)) {
			return;
		}

		List<ValidationProjectReference> references = getReferencesFromJSONStringArray(testedProjectsValue);

		checkReferencedProjects(references, forN4jsProjects(not(TEST_TYPE)), "tested projects", true, false);
	}

	/** Checks the 'n4js.implementationId' section. */
	@CheckProperty(property = IMPLEMENTATION_ID)
	public void checkImplementationId(JSONValue implementationIdValue) {
		// implemenationId usage restriction
		checkFeatureRestrictions(IMPLEMENTATION_ID.name, implementationIdValue,
				not(or(RE_OR_RL_TYPE, TEST_TYPE)));
	}

	/** Checks the 'n4js.implementedProjects' section. */
	@CheckProperty(property = IMPLEMENTED_PROJECTS)
	public void checkImplementedProjects(JSONValue implementedProjectsValue) {
		// implementedProjects usage restriction
		if (checkFeatureRestrictions(IMPLEMENTED_PROJECTS.name, implementedProjectsValue,
				not(or(RE_OR_RL_TYPE, TEST_TYPE)))) {

			List<ValidationProjectReference> references = getReferencesFromJSONStringArray(implementedProjectsValue);
			checkReferencedProjects(references, forN4jsProjects(API_TYPE), "implemented projects", false, true);

			// make sure an implementationId has been declared
			JSONValue implementationIdValue = getSingleDocumentValue(IMPLEMENTATION_ID);
			if (!references.isEmpty() && implementationIdValue == null) {
				addIssue(implementedProjectsValue.eContainer(), JSONPackage.Literals.NAME_VALUE_PAIR__NAME,
						PKGJ_APIIMPL_MISSING_IMPL_ID.toIssueItem());
			}
		}
	}

	/**
	 * Validates the declared module filters.
	 *
	 * For structural and duplicate validation see {@link PackageJsonValidatorExtension#checkModuleFilters}.
	 *
	 * This includes limited syntactical validation of the wildcards as well as a check whether the filters actually
	 * filter any resources or whether they are obsolete.
	 */
	@CheckProperty(property = MODULE_FILTERS)
	public void checkModuleFilters(JSONValue moduleFiltersValue) {
		N4JSProjectConfigSnapshot project = workspaceAccess.findProjectContaining(moduleFiltersValue);

		// early-exit for malformed structure
		if (!(moduleFiltersValue instanceof JSONObject)) {
			return;
		}

		// collect all declared module filter specifiers
		Multimap<String, JSONValue> nameValuePairs = collectObjectValues((JSONObject) moduleFiltersValue);
		Iterable<ASTTraceable<ModuleFilterSpecifier>> filterSpecifierTraceables = map(
				filterNull(flatMap(filter(nameValuePairs.values(), JSONArray.class), arr -> arr.getElements())),
				filter -> ASTTraceable.of(filter, PackageJsonUtils.asModuleFilterSpecifierOrNull(filter)));

		holdsValidModuleSpecifiers(filterSpecifierTraceables, project);
	}

	/**
	 * Checks if there is a pnpm-worspaces.yaml file that overrides the workspaces property.
	 */
	@CheckProperty(property = WORKSPACES_ARRAY) // also works for WORKSPACES_OBJECT
	public void checkWorspaceDefinitionArray(JSONValue workspacesValue) {
		ProjectDescription description = getProjectDescription();
		if (description.isPnpmWorkspaceRoot()) {
			addIssue(workspacesValue.eContainer(), JSONPackage.Literals.NAME_VALUE_PAIR__NAME,
					PKGJ_PNPM_WORKSPACES_OVERRIDE.toIssueItem());
		}
	}

	private void holdsValidModuleSpecifiers(Iterable<ASTTraceable<ModuleFilterSpecifier>> moduleFilterSpecifiers,
			N4JSProjectConfigSnapshot project) {
		ArrayList<ASTTraceable<ModuleFilterSpecifier>> validFilterSpecifier = new ArrayList<>();

		for (ASTTraceable<ModuleFilterSpecifier> filterSpecifier : moduleFilterSpecifiers) {
			boolean valid = holdsValidModuleFilterSpecifier(filterSpecifier);
			if (valid) {
				validFilterSpecifier.add(filterSpecifier);
			}
		}

		internalCheckModuleSpecifierHasFile(project, validFilterSpecifier);
	}

	private boolean holdsValidModuleFilterSpecifier(ASTTraceable<ModuleFilterSpecifier> filterSpecifierTraceable) {
		if (filterSpecifierTraceable == null) {
			return false;
		}
		String wrongWildcardPattern = "***";

		ModuleFilterSpecifier filterSpecifier = filterSpecifierTraceable.element;

		// check for invalid character sequences within wildcard patterns
		if (filterSpecifier != null && filterSpecifier.getSpecifierWithWildcard() != null) {
			if (filterSpecifier.getSpecifierWithWildcard().contains(wrongWildcardPattern)) {
				addIssue(
						filterSpecifierTraceable.astElement,
						PKGJ_INVALID_WILDCARD.toIssueItem(wrongWildcardPattern));
				return false;
			}
			String wrongRelativeNavigation = "../";
			if (filterSpecifier.getSpecifierWithWildcard().contains(wrongRelativeNavigation)) {
				addIssue(
						filterSpecifierTraceable.astElement,
						PKGJ_NO_RELATIVE_NAVIGATION.toIssueItem());
				return false;
			}
		}

		// check for empty module filter or source container values
		// (need to read from AST because these values are normalized during loading/conversion)
		JSONValue astElement = (JSONValue) filterSpecifierTraceable.astElement;
		String moduleSpecifierWithWildcardFromAST = null;

		if (astElement instanceof JSONStringLiteral) {
			moduleSpecifierWithWildcardFromAST = ((JSONStringLiteral) astElement).getValue();
		}

		if (astElement instanceof JSONObject) {
			moduleSpecifierWithWildcardFromAST = JSONModelUtils.getPropertyAsStringOrNull((JSONObject) astElement,
					NV_MODULE.name);
		}

		String sourceContainerFromAST = null;

		if (astElement instanceof JSONObject) {
			sourceContainerFromAST = JSONModelUtils.getPropertyAsStringOrNull((JSONObject) astElement,
					NV_SOURCE_CONTAINER.name);
		}

		if ((moduleSpecifierWithWildcardFromAST != null && moduleSpecifierWithWildcardFromAST.isEmpty())
				|| (sourceContainerFromAST != null && sourceContainerFromAST.isEmpty())) {
			addIssue(filterSpecifierTraceable.astElement, PKGJ_INVALID_MODULE_FILTER_SPECIFIER_EMPTY.toIssueItem());
			return false;
		}

		return true;
	}

	private void internalCheckModuleSpecifierHasFile(N4JSProjectConfigSnapshot project,
			List<ASTTraceable<ModuleFilterSpecifier>> filterSpecifiers) {
		// keep track of filter specifiers with matches (initialize with false for no matches)
		Map<ASTTraceable<ModuleFilterSpecifier>, Boolean> checkedFilterSpecifiers = new HashMap<>();
		checkedFilterSpecifiers.putAll(toMap(filter(filterSpecifiers, a -> a.element != null), p -> p, x -> false));

		try {
			ModuleSpecifierFileVisitor treeWalker = new ModuleSpecifierFileVisitor(this, project,
					checkedFilterSpecifiers);
			Files.walkFileTree(project.getPathAsFileURI().toFileSystemPath(), treeWalker);
		} catch (IOException e) {
			LOGGER.error("Failed to check module filter section of package.json file "
					+ getDocument().eResource().getURI() + ".");
			e.printStackTrace();
		}

		// obtain list of filter specifiers for which no file matches could be found
		Iterable<ASTTraceable<ModuleFilterSpecifier>> unmatchedSpecifiers = map(
				filter(checkedFilterSpecifiers.entrySet(),
						e -> e.getValue() == false),
				e -> e.getKey());

		for (ASTTraceable<ModuleFilterSpecifier> filterSpecifier : unmatchedSpecifiers) {
			addIssue(filterSpecifier.astElement,
					PKGJ_MODULE_FILTER_DOES_NOT_MATCH.toIssueItem(filterSpecifier.element.getSpecifierWithWildcard()));
		}
	}

	private static class ModuleSpecifierFileVisitor extends SimpleFileVisitor<Path> {
		private final N4JSProjectSetupJsonValidatorExtension setupValidator;
		private final N4JSProjectConfigSnapshot project;
		private final Map<ASTTraceable<ModuleFilterSpecifier>, Boolean> filterSpecifiers;

		ModuleSpecifierFileVisitor(N4JSProjectSetupJsonValidatorExtension validatorExtension,
				N4JSProjectConfigSnapshot project, Map<ASTTraceable<ModuleFilterSpecifier>, Boolean> filterSpecifiers) {
			this.setupValidator = validatorExtension;
			this.project = project;
			this.filterSpecifiers = filterSpecifiers;
		}

		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
			for (Iterator<Entry<ASTTraceable<ModuleFilterSpecifier>, Boolean>> iter = filterSpecifiers.entrySet()
					.iterator(); iter.hasNext();) {
				Entry<ASTTraceable<ModuleFilterSpecifier>, Boolean> entry = iter.next();
				ASTTraceable<ModuleFilterSpecifier> filterSpecifierTraceable = entry.getKey();
				ModuleFilterSpecifier element = filterSpecifierTraceable.element;
				String specifier = element == null ? null : element.getSpecifierWithWildcard();

				// only check for valid filter specifiers for matches
				boolean checkForMatches = specifier != null &&
						isN4JSFile(specifier) && path.toFile().isFile() || !isN4JSFile(specifier);

				// compute the source container path the filter applies to
				URI location = getFileInSources(path);

				if (checkForMatches && location != null) {
					boolean matchesFile = ModuleFilterUtils.isPathContainedByFilter(project, location,
							filterSpecifierTraceable.element);
					boolean matchesN4JSFile = matchesFile && isN4JSFile(path.toString());

					// check whether the current filter matches the current file
					if (matchesFile) {
						// mark entry as matched
						entry.setValue(true);
					}

					// check whether the current filter matches an N4JS file (which is invalid)
					if (matchesN4JSFile) {
						setupValidator.addNoValidationForN4JSFilesIssue(filterSpecifierTraceable);
					}

					if (matchesFile && matchesN4JSFile) {
						// if both checks apply, we no longer need to
						// consider this filter during further traversal
						iter.remove();
					}
				}
			}

			if (filterSpecifiers.isEmpty()) {
				return FileVisitResult.TERMINATE;
			} else {
				return FileVisitResult.CONTINUE;
			}
		}

		private URI getFileInSources(Path filePath) {
			Path lPath = project.getPathAsFileURI().toFileSystemPath();
			Path filePathString = lPath.relativize(filePath);
			return project.getPathAsFileURI().appendPath(filePathString.toString()).toURI();
		}

		private boolean isN4JSFile(String fileSpecifier) {
			return N4JSGlobals.endsWithN4JSFileExtension(fileSpecifier);
		}
	}

	private void addNoValidationForN4JSFilesIssue(ASTTraceable<ModuleFilterSpecifier> filterSpecifier) {
		String moduleFilterType = ((NameValuePair) filterSpecifier.astElement.eContainer().eContainer()).getName();
		addIssue(filterSpecifier.astElement, PKGJ_FILTER_NO_N4JS_MATCH.toIssueItem(moduleFilterType));
	}

	/**
	 * Checks whether a given {@code feature} can be used with the declared project ({@link #getProjectDescription()}),
	 * using the criteria defined by {@code supportedTypesPredicate}.
	 *
	 * Adds INVALID_FEATURE_FOR_PROJECT_TYPE to {@code pair} otherwise.
	 *
	 * @param featureDescription
	 *            A textual user-facing description of the checked feature.
	 * @param value
	 *            The JSONValue that has been declared for the given feature.
	 * @param supportedTypesPredicate
	 *            A predicate which indicates whether the feature may be used for a given project type.
	 */
	public boolean checkFeatureRestrictions(String featureDescription, JSONValue value,
			Predicate<ProjectType> supportedTypesPredicate) {
		ProjectDescription pd = getProjectDescription();
		if (pd == null) {
			// cannot check feature if project description is null
			return false;
		}

		ProjectType type = pd.getProjectType();
		if (type == null) {
			// cannot check feature if project type cannot be determined
			return false;
		}

		// empty values are always allowed
		if (isEmptyValue(value)) {
			return true;
		}

		// if container is a NameValuePair use whole pair as issue target
		EObject issueTarget = (value.eContainer() instanceof NameValuePair) ? value.eContainer() : value;

		// check whether the feature can be used with the current project type
		if (!supportedTypesPredicate.apply(type)) {
			addIssue(issueTarget, INVALID_FEATURE_FOR_PROJECT_TYPE
					.toIssueItem(StringExtensions.toFirstUpper(featureDescription), getLabel(type)));
			return false;
		}

		return true;
	}

	/** Return {@code true} iff the given value is considered empty (empty array or empty object). */
	private boolean isEmptyValue(JSONValue value) {
		return ((value instanceof JSONArray) && (((JSONArray) value).getElements().isEmpty())) ||
				((value instanceof JSONObject) && (((JSONObject) value).getNameValuePairs().isEmpty()));
	}

	/**
	 * Returns with a new predicate instance that provides {@code true} only if the given N4JS project may be declared a
	 * dependency of a project of type {@link ProjectType#API}.
	 *
	 * More specifically the given project must fulfill one of the following requirements:
	 * <ul>
	 * <li>The project type is API.</li>
	 * <li>The project type is library.</li>
	 * <li>The project type is validation.</li>
	 * <li>The project type is plainjs.</li>
	 * <li>The project type is runtime library.</li>
	 * </ul>
	 * Otherwise the predicate provides {@code false} value.
	 */
	private Predicate<N4JSProjectConfigSnapshot> createAPIDependenciesPredicate() {
		return Predicates.or(forN4jsProjects(API_TYPE),
				p -> anyOf(LIBRARY, VALIDATION, RUNTIME_LIBRARY, PLAINJS).apply(p.getType()));
	}

	/**
	 * Returns with a new predicate instance that specifies the type of projects that may be declared as dependency to
	 * the currently validated project description.
	 */
	private Predicate<N4JSProjectConfigSnapshot> createDependenciesPredicate() {
		switch (getProjectDescription().getProjectType()) {
		case API:
			return createAPIDependenciesPredicate();
		// runtime libraries may only depend on other runtime libraries
		case RUNTIME_LIBRARY:
			return forN4jsProjects(RL_TYPE);
		// definition project may depend on any type of project but plainjs projects
		case DEFINITION:
			return forN4jsProjects(not(PLAINJS_TYPE));
		// otherwise, any project type may be declared as dependency
		default:
			return Predicates.alwaysTrue();
		}
	}

	/**
	 * Intermediate validation-only representation of a project reference.
	 *
	 * This may or may not include a {@link ValidationProjectReference#npmVersion}.
	 *
	 * Holds a trace link {@link #astRepresentation} to its original AST element, so that check methods can add issues
	 * to the actual elements.
	 */

	private static class ValidationProjectReference {
		private final String referencedProjectName;

		private final String referencedProjectId;

		private final NPMVersionRequirement npmVersion;

		private final boolean hasSyntaxErrors;

		private final EObject astRepresentation;

		public ValidationProjectReference(final String referencedProjectName, final String referencedProjectId,
				final NPMVersionRequirement npmVersion, final boolean hasSyntaxErrors,
				final EObject astRepresentation) {
			super();
			this.referencedProjectName = referencedProjectName;
			this.referencedProjectId = referencedProjectId;
			this.npmVersion = npmVersion;
			this.hasSyntaxErrors = hasSyntaxErrors;
			this.astRepresentation = astRepresentation;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((this.referencedProjectName == null) ? 0 : this.referencedProjectName.hashCode());
			result = prime * result + ((this.referencedProjectId == null) ? 0 : this.referencedProjectId.hashCode());
			result = prime * result + ((this.npmVersion == null) ? 0 : this.npmVersion.hashCode());
			result = prime * result + (this.hasSyntaxErrors ? 1231 : 1237);
			return prime * result + ((this.astRepresentation == null) ? 0 : this.astRepresentation.hashCode());
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			N4JSProjectSetupJsonValidatorExtension.ValidationProjectReference other = (N4JSProjectSetupJsonValidatorExtension.ValidationProjectReference) obj;
			if (this.referencedProjectName == null) {
				if (other.referencedProjectName != null)
					return false;
			} else if (!this.referencedProjectName.equals(other.referencedProjectName))
				return false;
			if (this.referencedProjectId == null) {
				if (other.referencedProjectId != null)
					return false;
			} else if (!this.referencedProjectId.equals(other.referencedProjectId))
				return false;
			if (this.npmVersion == null) {
				if (other.npmVersion != null)
					return false;
			} else if (!this.npmVersion.equals(other.npmVersion))
				return false;
			if (other.hasSyntaxErrors != this.hasSyntaxErrors)
				return false;
			if (this.astRepresentation == null) {
				if (other.astRepresentation != null)
					return false;
			} else if (!this.astRepresentation.equals(other.astRepresentation))
				return false;
			return true;
		}

		@Override
		public String toString() {
			ToStringBuilder b = new ToStringBuilder(this);
			b.add("referencedProjectName", this.referencedProjectName);
			b.add("referencedProjectId", this.referencedProjectId);
			b.add("npmVersion", this.npmVersion);
			b.add("hasSyntaxErrors", this.hasSyntaxErrors);
			b.add("astRepresentation", this.astRepresentation);
			return b.toString();
		}
	}

	/**
	 * Returns a list of {@link ValidationProjectReference}s that can be extracted from the given {@code value},
	 * assuming it represents a valid {@code package.json} array of project IDs.
	 *
	 * The returned references do not include version constraints.
	 *
	 * Fails silently and returns an empty list, in case {@code value} is malformed.
	 */
	private List<ValidationProjectReference> getReferencesFromJSONStringArray(JSONValue value) {
		if (!(value instanceof JSONArray)) {
			return Collections.emptyList();
		}
		URI projectDescriptionFileURI = getDocument().eResource().getURI();
		N4JSProjectConfigSnapshot currentProject = workspaceAccess.findProjectByNestedLocation(getDocument(),
				projectDescriptionFileURI);
		return toList(flatMap(filter(((JSONArray) value).getElements(), JSONStringLiteral.class),
				literal -> getReferencesFromJSONStringLiteral(currentProject, literal)));
	}

	/**
	 * Returns a list of {@link ValidationProjectReference}s that can be extracted from the given {@code value},
	 * assuming it represents a valid {@code package.json} dependencies object.
	 *
	 * The returned references include version constraints.
	 *
	 * Fails silently and returns an empty list, in case {@code value} is malformed.
	 */
	private List<ValidationProjectReference> getReferencesFromDependenciesObject(JSONValue value) {
		if (!(value instanceof JSONObject)) {
			return Collections.emptyList();
		}

		URI projectDescriptionFileURI = getDocument().eResource().getURI();
		N4JSProjectConfigSnapshot currentProject = workspaceAccess.findProjectByNestedLocation(getDocument(),
				projectDescriptionFileURI);
		JSONObject jsonObj = (JSONObject) value;
		List<ValidationProjectReference> vprs = new ArrayList<>();
		for (NameValuePair pair : jsonObj.getNameValuePairs()) {
			if (pair.getValue() instanceof JSONStringLiteral) {
				JSONStringLiteral stringLit = (JSONStringLiteral) pair.getValue();
				String prjName = pair.getName();
				String prjID = prjName;
				if (currentProject.isKnownDependency(prjName)) {
					prjID = currentProject.getProjectIdForPackageName(prjName);
				} else {
					N4JSWorkspaceConfigSnapshot wsConfig = workspaceAccess.getWorkspaceConfig(getDocument());
					N4JSPackageName pckName = N4JSPackageName.create(prjName);
					N4JSProjectConfigSnapshot depPrj = wsConfig.findProjectByPackageName(pckName);
					if (depPrj != null) {
						prjID = depPrj.getName();
					}
				}
				IParseResult parseResult = semverHelper.getParseResult(stringLit.getValue());
				NPMVersionRequirement npmVersion = semverHelper.parse(parseResult);
				ValidationProjectReference vpr = new ValidationProjectReference(prjName, prjID, npmVersion,
						parseResult.hasSyntaxErrors(), pair);
				vprs.add(vpr);
			}
		}

		return vprs;
	}

	/**
	 * Returns a singleton list of the {@link ValidationProjectReference} that can be created from {@code literal}
	 * assuming is of type {@link JSONStringLiteral}.
	 *
	 * Returns an empty list, if this does not hold true.
	 */
	private List<N4JSProjectSetupJsonValidatorExtension.ValidationProjectReference> getReferencesFromJSONStringLiteral(
			N4JSProjectConfigSnapshot currentProject, JSONValue value) {
		if (value instanceof JSONStringLiteral) {
			String prjName = ((JSONStringLiteral) value).getValue();
			String prjID = currentProject.getProjectIdForPackageName(prjName);
			return List.of(new ValidationProjectReference(prjName, prjID, null, false, value));
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * Checks the given iterable of referenced projects.
	 *
	 * This includes checking whether the referenced project can be found and validating the given
	 * {@link ValidationProjectReference#npmVersion} if specified.
	 *
	 * @param references
	 *            The list of project references to validate.
	 * @param projectPredicate
	 *            A map of all projects that are accessible in the workspace.
	 * @param sectionLabel
	 *            A user-facing description of which section of project references is currently validated (e.g. "tested
	 *            projects").
	 * @param enforceDependency
	 *            Additionally enforces that all given references are also listed as explicit project dependencies
	 * @param allowReflexive
	 *            Specifies whether reflexive (self) references are allowed.
	 */
	private void checkReferencedProjects(Iterable<ValidationProjectReference> references,
			Predicate<N4JSProjectConfigSnapshot> projectPredicate,
			String sectionLabel, boolean enforceDependency, boolean allowReflexive) {

		ProjectDescription description = getProjectDescription();
		Map<String, N4JSProjectConfigSnapshot> allProjects = getAllProjectsById();

		// keeps track of all valid references
		Multimap<String, ValidationProjectReference> existentIds = HashMultimap.create();

		URI projectDescriptionFileURI = getDocument().eResource().getURI();
		N4JSProjectConfigSnapshot currentProject = workspaceAccess.findProjectByNestedLocation(getDocument(),
				projectDescriptionFileURI);
		if (currentProject == null) {
			// TODO
		}

		Set<String> allReferencedProjectNames = toSet(map(references, refx -> refx.referencedProjectName));

		for (ValidationProjectReference ref : references) {
			// check project existence.
			String id = ref.referencedProjectId;
			// Assuming completely broken AST.
			if (null != id) {
				checkReference(ref, allProjects, description, currentProject, allReferencedProjectNames,
						existentIds, allowReflexive, projectPredicate, sectionLabel);
			}
		}

		// check for duplicates among otherwise valid references
		checkForDuplicateProjectReferences(existentIds);

		// if specified, check that all references also occur in the dependencies sections
		if (enforceDependency) {
			checkDeclaredDependencies(existentIds.values(), sectionLabel);
		}
	}

	private void checkReference(ValidationProjectReference ref, Map<String, N4JSProjectConfigSnapshot> allProjects,
			ProjectDescription description, N4JSProjectConfigSnapshot currentProject,
			Set<String> allReferencedProjectNames, Multimap<String, ValidationProjectReference> existentIds,
			boolean allowReflexive, Predicate<N4JSProjectConfigSnapshot> projectPredicate, String sectionLabel) {
		// check project existence.
		String refId = ref.referencedProjectId;
		String refName = ref.referencedProjectName;
		String currentProjectName = description.getPackageName();

		// check for empty project ID
		if (refId == null || refId.isEmpty()) {
			addIssue(ref.astRepresentation, PKGJ_EMPTY_PROJECT_REFERENCE.toIssueItem());
			return;
		}

		// obtain corresponding N4JSProjectConfigSnapshot
		N4JSProjectConfigSnapshot project = allProjects.get(refId);

		// type cannot be resolved from index, hence project does not exist in workspace.
		if (null == project || null == project.getType()) {
			if (!currentProject.isExternal()) {
				String packageVersion = (ref.npmVersion == null) ? "" : ref.npmVersion.toString();
				IssueItem issueItem = NON_EXISTING_PROJECT.toIssueItemWithData(
						List.of(refName, packageVersion),
						refName);
				addIssue(ref.astRepresentation, null, issueItem);
			}
			return;
		} else {
			// keep track of actually existing projects
			existentIds.put(refId, ref);
		}

		// create only a single validation issue for a particular project reference.
		if (Objects.equals(currentProjectName, refName) && !allowReflexive) {
			// reflexive self-references
			addProjectReferencesItselfIssue(ref.astRepresentation);
			return;
		} else if (!projectPredicate.apply(project)) {
			// reference to project of invalid type
			addInvalidProjectTypeIssue(ref.astRepresentation, refName, project.getType(), sectionLabel);
			return;
		}

		// check version constraint if the current project is not external and has no nested
		// node_modules folder
		boolean ignoreVersion = (currentProject.isExternal()); // FIXME: WE NEED?: &&
																// description.hasNestedNodeModulesFolder
		if (!ignoreVersion) {
			checkVersions(ref, allProjects);
		}

		if (description.getProjectType() != ProjectType.DEFINITION) {
			checkImplProjectPresentForReferencedTypeDef(ref, project, allReferencedProjectNames);
		}
	}

	private void checkImplProjectPresentForReferencedTypeDef(ValidationProjectReference ref,
			N4JSProjectConfigSnapshot referencedProject, Set<String> allReferencedProjectNames) {

		if (referencedProject.getType() == ProjectType.DEFINITION) {
			String nameOfProjectDefinedByReferencedProject = referencedProject.getDefinesPackage() == null ? null
					: referencedProject.getDefinesPackage().toString();
			if (nameOfProjectDefinedByReferencedProject != null) {
				if (!allReferencedProjectNames.contains(nameOfProjectDefinedByReferencedProject)) {
					IssueItem issueItem = PKGJ_IMPL_PROJECT_IS_MISSING_FOR_TYPE_DEF
							.toIssueItem(nameOfProjectDefinedByReferencedProject, ref.referencedProjectName);
					addIssue(ref.astRepresentation, issueItem);
				}
			}
		}
	}

	private void checkForDuplicateProjectReferences(Multimap<String, ValidationProjectReference> validProjectRefs) {
		// obtain vendor ID of the currently validated project
		String currentVendor = getProjectDescription().getVendorId();

		for (String vprName : validProjectRefs.asMap().keySet()) {
			// grouped just by projectName
			if (validProjectRefs.get(vprName).size() > 1) {
				HashMultimap<String, ValidationProjectReference> referencesByNameAndVendor = HashMultimap.create();
				for (ValidationProjectReference vpr : validProjectRefs.get(vprName)) {
					// use vendor id of the referring project if not provided explicitly
					referencesByNameAndVendor.put(currentVendor, vpr);
				}

				for (String nav : referencesByNameAndVendor.keySet()) {
					Set<ValidationProjectReference> mappedRefs = referencesByNameAndVendor.get(nav);
					if (mappedRefs.size() > 1) {
						Iterable<ValidationProjectReference> vprs = tail(sortBy(mappedRefs,
								vpr -> NodeModelUtils.findActualNodeFor(vpr.astRepresentation).getOffset()));
						for (ValidationProjectReference ref : vprs) {
							addDuplicateProjectReferenceIssue(ref.astRepresentation, ref.referencedProjectName);
						}
					}
				}
			}
		}
	}

	/**
	 * Checks that the given {@code reference}s are also declared as explicit project dependencies under
	 * {@code dependencies} or {@code devDependencies}.
	 */
	private void checkDeclaredDependencies(Iterable<ValidationProjectReference> references, String sectionLabel) {
		Map<String, ProjectDependency> declaredDependencies = getDeclaredProjectDependencies();
		for (ValidationProjectReference reference : references) {
			if (!declaredDependencies.containsKey(reference.referencedProjectName)) {
				addIssue(reference.astRepresentation, PKGJ_PROJECT_REFERENCE_MUST_BE_DEPENDENCY
						.toIssueItem(reference.referencedProjectName, sectionLabel));
			}
		}
	}

	/** Checks if version constraint of the project reference is satisfied by any available project. */
	private void checkVersions(ValidationProjectReference ref, Map<String, N4JSProjectConfigSnapshot> allProjects) {
		NPMVersionRequirement desiredVersion = ref.npmVersion;
		if (desiredVersion == null) {
			return;
		}
		if (ref.hasSyntaxErrors) {
			// another validation will show an issue for the syntax error, see
			// PackageJsonValidatorExtension#checkVersion(JSONValue)
			return;
		}

		N4JSProjectConfigSnapshot depProject = allProjects.get(ref.referencedProjectId);
		VersionNumber availableVersion = depProject.getVersion();
		if (availableVersion == null) {
			// version 'null' equals empty version which we are unable to check.
			return;
		}
		boolean availableVersionMatches = SemverMatcher.matches(availableVersion, desiredVersion);
		if (availableVersionMatches) {
			return; // version does match
		}

		// versions do not match

		String desiredStr = SemverSerializer.serialize(desiredVersion);
		String availableStr = SemverSerializer.serialize(availableVersion);
		IssueItem issueItem = NO_MATCHING_VERSION.toIssueItemWithData(
				List.of(ref.referencedProjectId, desiredVersion.toString()),
				ref.referencedProjectName, desiredStr, availableStr);
		addIssue(ref.astRepresentation, null, issueItem);
	}

	/**
	 * Returns the {@link ProjectDescription} of the currently validated {@link JSONDocument}.
	 */
	protected ProjectDescription getProjectDescription() {
		return getProjectConfigSnapshot().getProjectDescription();
	}

	/**
	 * Returns the cached {@link N4JSProjectConfigSnapshot} of the currently validated {@link JSONDocument}.
	 */
	protected N4JSProjectConfigSnapshot getProjectConfigSnapshot() {
		return contextMemoize(PROJECT_DESCRIPTION_CACHE, () -> {
			JSONDocument doc = getDocument();
			URI resUri = doc.eResource().getURI();
			N4JSWorkspaceConfigSnapshot wscs = workspaceAccess.getWorkspaceConfig(doc);
			N4JSProjectConfigSnapshot pcs = wscs.findProjectContaining(resUri);
			return pcs;
		});
	}

	/**
	 * Returns a cached view on all declared project dependencies mapped to the dependency project ID.
	 *
	 * @apiNote {@link #getProjectDescription()}.
	 */
	protected Map<String, ProjectDependency> getDeclaredProjectDependencies() {
		return contextMemoize(DECLARED_DEPENDENCIES_CACHE, () -> {
			ProjectDescription description = getProjectDescription();
			return toMap(description.getProjectDependencies(), d -> d.getPackageName());
		});
	}

	private String getLabel(ProjectType it) {
		if (null == it) {
			return "";
		}
		return upperUnderscoreToHumanReadable(it.toString());
	}

	private String upperUnderscoreToHumanReadable(String s) {
		return nullToEmpty(s).replaceAll("_", " ").toLowerCase();
	}

	private void addProjectReferencesItselfIssue(EObject target) {
		addIssue(target, PROJECT_REFERENCES_ITSELF.toIssueItem());
	}

	private void addInvalidProjectTypeIssue(EObject target, String projectName, ProjectType type, String sectionLabel) {
		addIssue(target, INVALID_PROJECT_TYPE_REF.toIssueItem(projectName, getLabel(type), sectionLabel));
	}

	private void addDuplicateProjectReferenceIssue(EObject target, String name) {
		addIssue(target, DUPLICATE_PROJECT_REF.toIssueItem(name));
	}

	/**
	 * Adds an issue to every non-null element in {@code preferredTargets}.
	 *
	 * If {@code preferredTargets} is empty (or contains null entries only), adds an issue to the {@code name} property
	 * of the {@code package.json} file.
	 *
	 * If there is no {@code name} property, adds an issue to the whole document (see {@link #getDocument()}).
	 */
	private void addIssuePreferred(Iterable<? extends EObject> preferredTargets, IssueItem issueItem) {
		// add issue to preferred targets
		if (!isEmpty(filterNull(preferredTargets))) {
			for (EObject tgt : filterNull(preferredTargets)) {
				addIssue(tgt, issueItem);
			}
			return;
		}
		// fall back to property 'name'
		JSONValue nameValue = getSingleDocumentValue(NAME);
		if (nameValue != null) {
			addIssue(nameValue, issueItem);
			return;
		}
		// finally fall back to document
		addIssue(getDocument(), issueItem);
	}

	/**
	 * Returns a map between all available project IDs and their corresponding {@link N4JSProjectConfigSnapshot}
	 * representations.
	 *
	 * The result of this method is cached in the validation context.
	 */
	private Map<String, N4JSProjectConfigSnapshot> getAllProjectsById() {
		return contextMemoize(ALL_EXISTING_PROJECT_CACHE, () -> {
			JSONDocument document = getDocument();
			Map<String, N4JSProjectConfigSnapshot> res = new HashMap<>();
			for (N4JSProjectConfigSnapshot prj : workspaceAccess.findAllProjects(document)) {
				res.put(prj.getName(), prj);
			}
			return res;
		});
	}

	/** Transforms a {@link ProjectTypePredicate} into a predicate for {@link N4JSProjectConfigSnapshot}. */
	private Predicate<N4JSProjectConfigSnapshot> forN4jsProjects(Predicate<ProjectType> predicate) {
		return pcs -> predicate.apply(pcs.getType());
	}

	private void addIssue(EObject source, EStructuralFeature feature, IssueItem issueItem) {
		super.addIssue(issueItem.message, source, feature, issueItem.getID(), issueItem.data);
	}

	private void addIssue(EObject source, IssueItem issueItem) {
		super.addIssue(issueItem.message, source, null, issueItem.getID(), issueItem.data);
	}
}
