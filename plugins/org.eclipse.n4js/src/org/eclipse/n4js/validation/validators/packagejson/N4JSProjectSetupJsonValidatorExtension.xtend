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
package org.eclipse.n4js.validation.validators.packagejson

import com.google.common.base.Optional
import com.google.common.base.Predicate
import com.google.common.base.Predicates
import com.google.common.collect.HashMultimap
import com.google.common.collect.Iterables
import com.google.common.collect.LinkedListMultimap
import com.google.common.collect.Multimap
import com.google.inject.Inject
import com.google.inject.Singleton
import java.io.File
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Set
import java.util.Stack
import org.apache.log4j.Logger
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IWorkspaceRoot
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Platform
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.external.ExternalIndexSynchronizer
import org.eclipse.n4js.external.ExternalLibraryWorkspace
import org.eclipse.n4js.external.ShadowingInfoHelper
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.JSON.JSONPackage
import org.eclipse.n4js.json.JSON.JSONStringLiteral
import org.eclipse.n4js.json.JSON.JSONValue
import org.eclipse.n4js.json.JSON.NameValuePair
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.packagejson.PackageJsonUtils
import org.eclipse.n4js.projectDescription.ModuleFilterSpecifier
import org.eclipse.n4js.projectDescription.ProjectDependency
import org.eclipse.n4js.projectDescription.ProjectDescription
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectDescription.SourceContainerDescription
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement
import org.eclipse.n4js.semver.SemverHelper
import org.eclipse.n4js.semver.SemverMatcher
import org.eclipse.n4js.semver.model.SemverSerializer
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.utils.DependencyTraverser
import org.eclipse.n4js.utils.DependencyTraverser.DependencyVisitor
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder
import org.eclipse.n4js.utils.ProjectDescriptionLoader
import org.eclipse.n4js.utils.WildcardPathFilterHelper
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.N4JSElementKeywordProvider
import org.eclipse.n4js.validation.helper.SourceContainerAwareDependencyProvider
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IContainer.Manager
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.validation.Check

import static com.google.common.base.Preconditions.checkState
import static org.eclipse.n4js.packagejson.PackageJsonProperties.*
import static org.eclipse.n4js.projectDescription.ProjectType.*
import static org.eclipse.n4js.validation.IssueCodes.*
import static org.eclipse.n4js.validation.validators.packagejson.ProjectTypePredicate.*

import static extension com.google.common.base.Strings.nullToEmpty
import org.eclipse.n4js.projectModel.names.N4JSProjectName
import org.eclipse.n4js.projectModel.names.EclipseProjectName

/**
 * A JSON validator extension that validates {@code package.json} resources in the context
 * of higher-level concepts such as project references, the general project setup and feature restrictions.
 * 
 * Generally, this validator includes constraints that are implemented based on the converted {@link ProjectDescription}
 * as it can be obtained from the {@link ProjectDescriptionLoader}. This especially includes non-local validation
 * such as the resolution of referenced projects.
 * 
 * For lower-level, structural and local validations with regard to {@code package.json} 
 * files , see {@link PackageJsonValidatorExtension}.
 */
@Singleton
public class N4JSProjectSetupJsonValidatorExtension extends AbstractJSONValidatorExtension {

	private static final Logger LOGGER = Logger.getLogger(N4JSProjectSetupJsonValidatorExtension);

	static val API_TYPE = anyOf(API);
	static val RE_TYPE = anyOf(RUNTIME_ENVIRONMENT);
	static val RL_TYPE = anyOf(RUNTIME_LIBRARY);
	static val TEST_TYPE = anyOf(TEST);
	static val RE_OR_RL_TYPE = anyOf(RUNTIME_ENVIRONMENT, RUNTIME_LIBRARY);
	static val PLAINJS_TYPE = anyOf(PLAINJS);


	/**
	 * Key to store a converted ProjectDescription instance in the validation context for re-use across different check-methods
	 * @See {@link #getProjectDescription()} 
	 */
	private static final String PROJECT_DESCRIPTION_CACHE = "PROJECT_DESCRIPTION_CACHE";

	/**
	 * Key to store a map of all available projects in the validation context for re-use across different check-methods.
	 * @See {@link #getAllExistingProjectNames()} 
	 */
	private static String ALL_EXISTING_PROJECT_CACHE = "ALL_EXISTING_PROJECT_CACHE";

	/**
	 * Key to store a map of all user projects and their node_modules folders in the validation context for re-use across different check-methods.
	 * @See {@link #getAllNodeModulesFolders()}
	 */
	private static String NODE_MODULES_LOCATION_CACHE = "NODE_MODULES_LOCATION_CACHE";

	/**
	 * Key to store a map of all declared project dependencies in the validation context for re-use across different check-methods.
	 * @See {@link #getDeclaredProjectDependencies()} 
	 */
	private static String DECLARED_DEPENDENCIES_CACHE = "DECLARED_DEPENDENCIES_CACHE";

	@Inject
	private extension IN4JSCore

	@Inject
	private Manager containerManager;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private XpectAwareFileExtensionCalculator fileExtensionCalculator;

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	@Inject
	private WildcardPathFilterHelper wildcardHelper;

	@Inject
	protected N4JSElementKeywordProvider keywordProvider;

	@Inject
	protected ShadowingInfoHelper shadowingInfoHelper;

	@Inject
	protected ExternalIndexSynchronizer indexSynchronizer;

	@Inject
	protected ExternalLibraryWorkspace extWS;

	@Inject
	protected SemverHelper semverHelper;

	@Inject
	protected NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;


	override boolean isResponsible(Map<Object, Object> context, EObject eObject) {
		// this validator extension only applies to package.json files
		return fileExtensionCalculator.getFilenameWithoutXpectExtension(eObject.eResource().getURI())
				.equals(IN4JSProject.PACKAGE_JSON);
	}

	/**
	 * According to IDESpec §§12.04 Polyfills at most one Polyfill can be provided for a class.
	 * Here the consistency according to the given combination of runtime-environment and runtime-libraries of the
	 * project definition will be checked.
	 */
	@Check
	def checkConsistentPolyfills(JSONDocument document) {
		// Take the RTE and RTL's check for duplicate fillings.
		// lookup of names in project description
		val Map<N4JSProjectName, JSONStringLiteral> mQName2rtDep = newHashMap()

		val description = getProjectDescription();
		val projectName = description.projectName;

		// if the project name cannot be determined, exit early
		if (projectName === null) {
			return;
		}

		// gather required runtime libraries in terms of JSONStringLiterals
		val requiredRuntimeLibrariesValue = getSingleDocumentValue(REQUIRED_RUNTIME_LIBRARIES, JSONArray);
		if (requiredRuntimeLibrariesValue === null) {
			return;
		}
		var Iterable<? extends JSONStringLiteral> rteAndRtl = requiredRuntimeLibrariesValue.elements.filter(
			JSONStringLiteral);

		// Describing Self-Project as RuntimeDependency to handle clash with filled Members from current Project consistently.
		val selfProject = JSONModelUtils.createStringLiteral(projectName);
		val Optional<? extends IN4JSProject> optOwnProject = findProject(document.eResource.URI)
		if (optOwnProject.present) {
			rteAndRtl = Iterables.concat(rteAndRtl, #{selfProject})
		}

		for (JSONStringLiteral libraryLiteral : rteAndRtl) {
			if (null !== libraryLiteral) {
				val String libPPqname = libraryLiteral.value
				mQName2rtDep.put(new N4JSProjectName(libPPqname), libraryLiteral)
			}
		}

		// own Project can provide filled members as well, if of type RuntimeEnvironment/RuntimeLibrary:
		val List<IEObjectDescription> allPolyFillTypes = getAllNonStaticPolyfills(document.eResource)

		// 1.a. For Each containing File: get the Exported Polyfills:   <QN, PolyFilledProvision>
		// if the file is from our self, then ignore it; validation will be done for the local file separately.
		val LinkedListMultimap<String, PolyFilledProvision> exportedPolyfills_QN_to_PolyProvision = LinkedListMultimap.create
		for (ieoT : allPolyFillTypes) {
			val optSrcContainer = findN4JSSourceContainer(ieoT.EObjectURI)
			if (optSrcContainer.present) {
				val srcCont = optSrcContainer.get;
				val depQName = srcCont.project.projectName;
				val dependency = mQName2rtDep.get(depQName);
				if (dependency === null) {
					// TODO IDE-1735 typically a static Polyfill - can only be used inside of a single project
				} else if (dependency !== selfProject) {
					exportedPolyfills_QN_to_PolyProvision.put(
						ieoT.qualifiedName.toString,
						new PolyFilledProvision(depQName, dependency, ieoT)
					)
				}
			} else {
				throw new IllegalStateException("No container library found for " + ieoT.qualifiedName)
			}
		}

		// Search for clashes in Polyfill:
		// markermap: {lib1,lib2,...}->"filledname"
		val Multimap<Set<JSONStringLiteral>, String> markerMapLibs2FilledName = LinkedListMultimap.create // Value is QualifiedName of polyfill_Element
		for (String polyExport_QN : exportedPolyfills_QN_to_PolyProvision.keySet) {
			val polyProvisions = exportedPolyfills_QN_to_PolyProvision.get(polyExport_QN);
			if (polyProvisions.size > 1) {

				// For each filled member determine the set of fillers:
				val m = LinkedListMultimap.<String, PolyFilledProvision>create // memberName->PolyProvisionA,PolyProvisionB ...
				for (prov : polyProvisions) {

					// contextScope.getSingleElement( prov.ieoDescrOfPolyfill.qualifiedName )
					var eoPolyFiller = prov.ieoDescrOfPolyfill.EObjectOrProxy

					if (eoPolyFiller instanceof TClassifier) {
						val resolvedEoPolyFiller = EcoreUtil.resolve(eoPolyFiller, document.eResource) as TClassifier
						if (!resolvedEoPolyFiller.isPolyfill) {
							throw new IllegalStateException(
								"Expected a polyfill, but wasn't: " + resolvedEoPolyFiller.name)
						} else { // yes, it's an polyfiller.
							for (TMember member : resolvedEoPolyFiller.ownedMembers) {

								// Usually if the name is equal - no matter what the rest of the signature tells - it's a clash.
								// TODO Situation which are allowed: one provides getter only, other provides setter only.
								m.put(member.name, prov)
							}
						}
					}
				}

				for (filledInMemberName : m.keySet) {
					val providers = m.get(filledInMemberName)
					if (providers.size > 1) {

						// register for error:
						val keySet = newHashSet()
						providers.forEach[keySet.add(it.libraryProjectReferenceLiteral)]
						val filledTypeFQN = providers.head.descriptionStandard // or: polyExport_QN
						val message = filledTypeFQN + "#" + filledInMemberName
						markerMapLibs2FilledName.put(keySet, message)
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
		for (Set<JSONStringLiteral> keyS : markerMapLibs2FilledName.keySet) {

			val polyFilledMemberAsStrings = markerMapLibs2FilledName.get(keyS)
			val libsString = keyS.toList.map[it.getValue].sort.join(", ")

			val userPresentablePolyFills = polyFilledMemberAsStrings.toList.map['"' + it + '"'].sort.join(", ")

			if (keyS.size > 1) {

				// case a: multiple dependencies clash
				val issMsg = if (polyFilledMemberAsStrings.size == 1) {
						IssueCodes.getMessageForPOLY_CLASH_IN_RUNTIMEDEPENDENCY(libsString, userPresentablePolyFills)
					} else {
						IssueCodes.
							getMessageForPOLY_CLASH_IN_RUNTIMEDEPENDENCY_MULTI(libsString, userPresentablePolyFills)
					}

				// add Issue for each
				keyS.forEach [
					addIssue(issMsg, it, IssueCodes.POLY_CLASH_IN_RUNTIMEDEPENDENCY)
				]
			} else {
				// case b: not error-free:
				val issMsg = IssueCodes.
					getMessageForPOLY_ERROR_IN_RUNTIMEDEPENDENCY(libsString, userPresentablePolyFills)
				addIssue(issMsg, keyS.head, IssueCodes.POLY_ERROR_IN_RUNTIMEDEPENDENCY)

			}

		}
	}


	/** Get a list of all polyfill types & IEObjecdescriptions accessible in the whole Project.
	 * @param manifestResourceUsedAsContext just a resource to build the scope
	 * @return List of Type->IEObjectdescription
	 * */
	private def List<IEObjectDescription> getAllNonStaticPolyfills(Resource projectDescriptionResourceUsedAsContext) {

		val asXtextRes = projectDescriptionResourceUsedAsContext as XtextResource;
		val resDescr = asXtextRes.resourceServiceProvider.resourceDescriptionManager.getResourceDescription(asXtextRes);

		val List<IContainer> visibleContainers = containerManager.getVisibleContainers(
			resDescr,
			resourceDescriptionsProvider.getResourceDescriptions(projectDescriptionResourceUsedAsContext)
		);

		val types = newArrayList()
		for (IEObjectDescription descr : visibleContainers.map[it.getExportedObjectsByType(TypesPackage.Literals.TYPE)].flatten) {
			val isPolyFill = N4JSResourceDescriptionStrategy.getPolyfill(descr);
			val isStaticPolyFill = N4JSResourceDescriptionStrategy.getStaticPolyfill(descr);

			if (isPolyFill && !isStaticPolyFill) {
				types.add(descr);
			}
		}
		return types;
	}
	
	/** IDEBUG-266 issue error warning on cyclic dependencies. */
	@Check
	def checkCyclicDependencies(JSONDocument document) {
		val project = findProject(document.eResource.URI).orNull;
		if (null !== project) {
			
			val dependencyProvider = new SourceContainerAwareDependencyProvider(true);
			val traverser = new DependencyTraverser(project, dependencyProvider, true);
			
			val traversalResult = traverser.findCycle();
			
			if (traversalResult.hasCycle) {
				// add issue to 'name' property or alternatively to the whole document
				val nameValue = getSingleDocumentValue(NAME);
				val message = getMessageForPROJECT_DEPENDENCY_CYCLE(traversalResult.prettyPrint([calculateName.rawName]));
				addIssuePreferred(#[nameValue], message, PROJECT_DEPENDENCY_CYCLE);
			} else {
				//for performance reasons following is not separate check
				/*
				 * otherwise we would traverse all transitive dependencies multiple times,
				 * and we would care for cycles
				 */
				project.holdsProjectWithTestFragmentDependsOnTestLibrary()
			}
		}
	}


	/**
	 * Checks if a project containing {@link SourceContainerType#TEST} depends 
	 * (directly or transitively) on a {@link ProjectType#RUNTIME_LIBRARY} test runtime library.
	 */
	private def holdsProjectWithTestFragmentDependsOnTestLibrary(IN4JSProject project) {

		val JSONValue sourcesSection = getSingleDocumentValue(SOURCES, JSONValue);
		val List<SourceContainerDescription> sourceContainers = PackageJsonUtils.asSourceContainerDescriptionsOrEmpty(sourcesSection);
		
		if (sourceContainers === null) {
			return;
		}
		
		val hasTestFragment = sourceContainers.findFirst[sf| SourceContainerType.TEST.equals(sf.getSourceContainerType)] !== null;

		if(!hasTestFragment){
			return;
		}
		
		if(!anyDependsOnTestLibrary(#[project])){
			addIssuePreferred(#[], getMessageForSRCTEST_NO_TESTLIB_DEP(N4JSGlobals.MANGELHAFT), SRCTEST_NO_TESTLIB_DEP);
		}
	}


	/**
	 * check if any project in the list has dependency on test library, if so return true.
	 * Otherwise invoke recursively in dependencies list of each project in initial list.
	 *
	 * @returns true if any of the projects in the provided list depends (transitively) on the test library.
	 */
	private def boolean anyDependsOnTestLibrary(List<? extends IN4JSProject> projects) {
		val dependencyProvider = new SourceContainerAwareDependencyProvider(true);
		val hasTestDependencyVisitor = new HasTestDependencyVisitor();
		for (IN4JSProject project : projects) {
			val dependencyTraverser = new DependencyTraverser<IN4JSProject>(project, hasTestDependencyVisitor, dependencyProvider, true);
			dependencyTraverser.traverse;
		}
		return hasTestDependencyVisitor.hasTestDependencies;
	}

	static class HasTestDependencyVisitor implements DependencyVisitor<IN4JSProject> {
		boolean hasTestDependencies = false;

		override accept(IN4JSProject project) {
			if (hasTestDependency(project)) {
				hasTestDependencies = true;
			}
		}

		private def boolean hasTestDependency(IN4JSProject p) {
			for (IN4JSProject pDep : p.dependencies) {
				if ((N4JSGlobals.VENDOR_ID.equals(pDep.vendorID) && (N4JSGlobals.MANGELHAFT.equals(pDep.projectName))
					|| N4JSGlobals.MANGELHAFT_ASSERT.equals(pDep.projectName)
				)) {
					return true;
				}
			}
			return false;
		}
	}

	private def N4JSProjectName calculateName(IN4JSProject it) {
		it.projectName;
	}

	/**
	 * Checks whether a test project either tests APIs or libraries. Raises a validation issue, if the test project
	 * tests both APIs and libraries. Does nothing if the project description of the validated project is NOT a test
	 * project.
	 */
	@CheckProperty(property = TESTED_PROJECTS)
	def checkTestedProjectsType(JSONValue testedProjectsValue) {
		val description = getProjectDescription();
		
		// make sure the listed tested projects do not mismatch in their type (API vs. library)
		if (TEST == description.projectType) {
			val projects = description.testedProjects;
			if (!projects.nullOrEmpty) {
				val allProjects = getAllProjectsByName();
				val head = projects.head;
				val refProjectType = allProjects.get(new N4JSProjectName(head.projectName))?.projectType
				
				// check whether 'projects' contains a dependency to an existing project of different
				// type than 'head'
				if (projects.exists[testedProject | allProjects.containsKey(new N4JSProjectName(testedProject.projectName)) &&
					refProjectType != allProjects.get(new N4JSProjectName(testedProject.projectName))?.projectType
				]) {
					addIssue(
						messageForMISMATCHING_TESTED_PROJECT_TYPES, 
						testedProjectsValue,
						MISMATCHING_TESTED_PROJECT_TYPES);
				}
			}
		}
	}

	/**
	 * Checks whether a library project, that belongs to a specific implementation (has defined implementation ID) does not
	 * depend on any other libraries that belong to any other implementation. In such cases, raises validation issue.
	 */
	@CheckProperty(property = DEPENDENCIES)
	def checkHasConsistentImplementationIdChain(JSONValue dependenciesValue) {
		// exit early in case of a malformed dependencies section (structural validation is handled elsewhere)
		if (!(dependenciesValue instanceof JSONObject)) {
			return;
		}
		
		// pairs that represent project dependencies
		val dependencyPairs = (dependenciesValue as JSONObject).nameValuePairs;
		// obtain project description for higher-level access to contained information
		val description = getProjectDescription();
		
		if (LIBRARY == description.projectType && !description.implementationId.nullOrEmpty) {
			val expectedImplementationId = new N4JSProjectName(description.implementationId);
			val allProjects = getAllProjectsByName();
			
			dependencyPairs.filterNull.forEach[ pair |
				val dependencyProjectName = new N4JSProjectName(pair.name);
				val actualImplementationId = allProjects.get(dependencyProjectName)?.implementationId?.orNull;
				if (actualImplementationId !== null && actualImplementationId != expectedImplementationId) {
					val message = getMessageForMISMATCHING_IMPLEMENTATION_ID(expectedImplementationId, 
						dependencyProjectName, actualImplementationId);
					addIssue(message, pair, MISMATCHING_IMPLEMENTATION_ID);
				}
			];
		}
	}

	/**
	 * Checks if any transitive external dependency of a workspace project references to a workspace
	 * project. If so, raises a validation warning.
	 */
	@CheckProperty(property = DEPENDENCIES)
	def checkExternalProjectDoesNotReferenceWorkspaceProject(JSONValue dependenciesValue) {
		val allProjects = getAllProjectsByName();
		val description = getProjectDescription();
		
		// if the project name cannot be determined, exit early
		if (description.projectName === null) {
			return;
		}
		
		val currentProject = allProjects.get(new N4JSProjectName(description.projectName));

		// Nothing to do with non-existing, missing and/or external projects.
		if (null === currentProject || !currentProject.exists || currentProject.external) {
			return;
		}

		val visitedProjectNames = newHashSet();
		val stack = new Stack;
		stack.addAll(currentProject.allDirectDependencies.filter(IN4JSProject).filter[external]);

		while (!stack.isEmpty) {

			val actual = stack.pop;
			val actualId = actual.projectName;
			checkState(actual.external, '''Implementation error. Only external projects are expected: «actual».''');

			if (!visitedProjectNames.add(actualId)) {
				// Cyclic dependency. This will be handles somewhere else. Nothing to do.
				return;
			}

			val actualDirectDependencies = actual.allDirectDependencies.filter(IN4JSProject);
			// If external has any *NON* external dependency we should raise a warning.
			val workspaceDependency = actualDirectDependencies.findFirst[!external];
			if (null !== workspaceDependency) {
				val workspaceDependencyId = workspaceDependency.projectName;
				val message = getMessageForEXTERNAL_PROJECT_REFERENCES_WORKSPACE_PROJECT(actualId, workspaceDependencyId);
				addIssue(
					message,
					dependenciesValue,
					EXTERNAL_PROJECT_REFERENCES_WORKSPACE_PROJECT
				);
				return;
			}

			stack.addAll(actualDirectDependencies.filter[external]);
		}
	}

	@Check
	def void checkDependenciesAndDevDependencies(JSONDocument document) {
		// determine whether current project is external
		val project = findProject(document.eResource.URI);
		val isExternal = project.present && project.get.external;

		// collect all references, skip devDependencies if project is external, because these 
		// are not installed by npm for transitive dependencies
		val references = getDependencies(!isExternal);

		if (!references.empty) {
			checkReferencedProjects(references, createDependenciesPredicate(), "dependencies or devDependencies", false, false);
		}

		// special validation for API projects
		if (projectDescription.projectType == API) {
			internalValidateAPIProjectReferences(references);
		}
	}

	@Check
	def void checkMandatoryDependencies(JSONDocument document) {

		val description = getProjectDescription();
		val projectName = description.projectName;
		val projectType = description.projectType;
		if (projectName == N4JSGlobals.N4JS_RUNTIME.rawName) {
			return; // not applicable ("n4js-runtime" does not need to have a dependency to "n4js-runtime"!)
		}
		if (!N4JSGlobals.PROJECT_TYPES_REQUIRING_N4JS_RUNTIME.contains(projectType)) {
			return; // not applicable
		}

		val dependencies = getDocumentValues(DEPENDENCIES).filter(JSONObject).flatMap[nameValuePairs].toList;
		if (!dependencies.exists[name == N4JSGlobals.N4JS_RUNTIME.rawName]) {
			val devDependencies = getDocumentValues(DEV_DEPENDENCIES).filter(JSONObject).flatMap[nameValuePairs].toList;
			val matchingDevDep = devDependencies.findFirst[name == N4JSGlobals.N4JS_RUNTIME.rawName];
			if (matchingDevDep === null) {
				// dependency to 'n4js-runtime' missing entirely
				val msg = IssueCodes.getMessageForPKGJ_MISSING_DEPENDENCY_N4JS_RUNTIME;
				val projectTypeValue = getDocumentValues(PROJECT_TYPE).head;
				if (projectTypeValue !== null) { // should always be non-null, because we check for 3 non-default project types above!
					addIssue(msg, projectTypeValue, IssueCodes.PKGJ_MISSING_DEPENDENCY_N4JS_RUNTIME);
				}
			} else {
				// dependency to 'n4js-runtime' defined in wrong section (under 'devDependencies' instead of 'dependencies')
				val msg = IssueCodes.getMessageForPKGJ_WRONG_DEPENDENCY_N4JS_RUNTIME;
				addIssue(msg, matchingDevDep, IssueCodes.PKGJ_WRONG_DEPENDENCY_N4JS_RUNTIME);
			}
		}
	}

	/**
	 * Returns a representation of all declared runtime dependencies of 
	 * the currently validate document (cf. {@link #getDocument()}).
	 * 
	 * @param includeDevDependencies 
	 * 					Specifies whether the returned iterable should also include devDependencies.	
	 */
	private def Iterable<ValidationProjectReference> getDependencies(boolean includeDevDependencies) {
		val references = newArrayList;
		
		for (value : getDocumentValues(DEPENDENCIES)) {
			references += getReferencesFromDependenciesObject(value);
		}
		if (includeDevDependencies) {
			for (value : getDocumentValues(DEV_DEPENDENCIES)) {
				references += getReferencesFromDependenciesObject(value);
			}
		}
		return references;
	} 
	
	/**
	 * Checks that an API project does not declare any dependencies on implementation
	 * projects (library projects with implementation ID).
	 */
	def internalValidateAPIProjectReferences(Iterable<ValidationProjectReference> references) {
		val libraryDependenciesWithImplId = references
			.map[ref | Pair.of(ref, allProjectsByName.get(ref.referencedProjectName))]
			.filter[pair | pair !== null && pair.value !== null]
			.filter[pair | pair.value.projectType == LIBRARY && pair.value.implementationId.present];

		for (projectPair : libraryDependenciesWithImplId) {
			val reference = projectPair.key;
			addIssue(IssueCodes.getMessageForINVALID_API_PROJECT_DEPENDENCY(reference.referencedProjectName), reference.astRepresentation, 
				IssueCodes.INVALID_API_PROJECT_DEPENDENCY);
		}
	}

	/** Checks the 'n4js.extendedRuntimeEnvironment' section. */
	@CheckProperty(property = EXTENDED_RUNTIME_ENVIRONMENT)
	def checkExtendedRuntimeEnvironment(JSONValue extendedRuntimeEnvironmentValue) {
		// make sure 'extendedRuntimeEnvironment' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("extended runtime environment", extendedRuntimeEnvironmentValue, RE_TYPE)) {
			return;
		}

		val references =  extendedRuntimeEnvironmentValue.referencesFromJSONStringLiteral
		checkReferencedProjects(references, RE_TYPE.forN4jsProjects, "extended runtime environment", false, false);
	}
	
	/** Checks the 'n4js.requiredRuntimeLibraries' section. */
	@CheckProperty(property = REQUIRED_RUNTIME_LIBRARIES)
	def checkRequiredRuntimeLibraries(JSONValue requiredRuntimeLibrariesValue) {
		// make sure 'requiredRuntimeLibraries' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("required runtime libraries", requiredRuntimeLibrariesValue, not(RE_TYPE))) {
			return;
		}
		
		val references =  requiredRuntimeLibrariesValue.referencesFromJSONStringArray
		
		checkReferencedProjects(references, RL_TYPE.forN4jsProjects, "required runtime libraries", true, false);
	}
	
	/** Checks the 'n4js.providedRuntimeLibraries' section. */
	@CheckProperty(property = PROVIDED_RUNTIME_LIBRARIES)
	def checkProvidedRuntimeLibraries(JSONValue providedRuntimeLibraries) {
		// make sure 'requiredRuntimeLibraries' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("provided runtime libraries", providedRuntimeLibraries, RE_TYPE)) {
			return;
		}
		
		val references =  providedRuntimeLibraries.referencesFromJSONStringArray
		checkReferencedProjects(references, RL_TYPE.forN4jsProjects, "provided runtime libraries", false, false);
	}
	
	/** Checks the 'n4js.testedProjects' section. */
	@CheckProperty(property = TESTED_PROJECTS)
	def checkTestedProjects(JSONValue testedProjectsValue) {
		// make sure 'testedProjects' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("tested projects", testedProjectsValue, TEST_TYPE)) {
			return;
		}
		
		val references =  testedProjectsValue.referencesFromJSONStringArray
		
		checkReferencedProjects(references, not(TEST_TYPE).forN4jsProjects, "tested projects", true, false);
	}
	
	/** Checks the 'n4js.implementationId' section. */
	@CheckProperty(property = IMPLEMENTATION_ID)
	def checkImplementationId(JSONValue implementationIdValue) {
		// implemenationId usage restriction
		checkFeatureRestrictions(IMPLEMENTATION_ID.name, implementationIdValue, 
			not(or(RE_OR_RL_TYPE, TEST_TYPE)));
	}
	
	/** Checks the 'n4js.implementedProjects' section. */
	@CheckProperty(property = IMPLEMENTED_PROJECTS)
	def checkImplementedProjects(JSONValue implementedProjectsValue) {
		// implementedProjects usage restriction
		if(checkFeatureRestrictions(IMPLEMENTED_PROJECTS.name, implementedProjectsValue, 
			not(or(RE_OR_RL_TYPE, TEST_TYPE)))) {
		
			val references = implementedProjectsValue.referencesFromJSONStringArray;
			checkReferencedProjects(references, API_TYPE.forN4jsProjects, "implemented projects", false, true);
			
			// make sure an implementationId has been declared
			val JSONValue implementationIdValue = getSingleDocumentValue(IMPLEMENTATION_ID);
			if (!references.isEmpty() && implementationIdValue === null ) {
				addIssue(IssueCodes.getMessageForPKGJ_APIIMPL_MISSING_IMPL_ID(), implementedProjectsValue.eContainer,
					JSONPackage.Literals.NAME_VALUE_PAIR__NAME, IssueCodes.PKGJ_APIIMPL_MISSING_IMPL_ID);
			}
		}
	}
	
	/**
	 * Validates the declared module filters. 
	 * 
	 * For structural and duplicate validation see {@link PackageJsonValidatorExtension#checkModuleFilters}.
	 * 
	 * This includes limited syntactical validation of the wildcards as well as a check whether the filters
	 * actually filter any resources or whether they are obsolete.
	 */
	@CheckProperty(property = MODULE_FILTERS)
	def checkModuleFilters(JSONValue moduleFiltersValue) {
		val project = findProject(moduleFiltersValue.eResource.URI).get;
		
		// early-exit for malformed structure
		if (!(moduleFiltersValue instanceof JSONObject)) {
			return;
		}

		// collect all declared module filter specifiers
		val nameValuePairs = collectObjectValues(moduleFiltersValue as JSONObject);
		val filterSpecifierTraceables = nameValuePairs.values
			.filter(JSONArray)
			.flatMap[elements]
			.filterNull
			.map[filter | ASTTraceable.of(filter, PackageJsonUtils.asModuleFilterSpecifierOrNull(filter))];

		holdsValidModuleSpecifiers(filterSpecifierTraceables, project);
	}
	
	
	private def holdsValidModuleSpecifiers(Iterable<ASTTraceable<ModuleFilterSpecifier>> moduleFilterSpecifiers, IN4JSProject project) {
		val validFilterSpecifier = new ArrayList<ASTTraceable<ModuleFilterSpecifier>>();

		for (ASTTraceable<ModuleFilterSpecifier> filterSpecifier : moduleFilterSpecifiers) {
			val valid = holdsValidModuleFilterSpecifier(filterSpecifier);
			if (valid) {
				validFilterSpecifier.add(filterSpecifier);
			}
		}

		internalCheckModuleSpecifierHasFile(project, validFilterSpecifier);
	}

	private def holdsValidModuleFilterSpecifier(ASTTraceable<ModuleFilterSpecifier> filterSpecifierTraceable) {
		val wrongWildcardPattern = "***"

		val ModuleFilterSpecifier filterSpecifier = filterSpecifierTraceable?.element;

		// check for invalid character sequences within wildcard patterns
		if (filterSpecifier?.moduleSpecifierWithWildcard !== null) {
			if (filterSpecifier.moduleSpecifierWithWildcard.contains(wrongWildcardPattern)) {
				addIssue(
					getMessageForPKGJ_INVALID_WILDCARD(wrongWildcardPattern),
					filterSpecifierTraceable.astElement,
					PKGJ_INVALID_WILDCARD
				)
				return false
			}
			val wrongRelativeNavigation = "../"
			if (filterSpecifier.moduleSpecifierWithWildcard.contains(wrongRelativeNavigation)) {
				addIssue(
					getMessageForPKGJ_NO_RELATIVE_NAVIGATION,
					filterSpecifierTraceable.astElement,
					PKGJ_NO_RELATIVE_NAVIGATION
				)
				return false
			}
		}

		// check for empty module filter or source container values
		// (need to read from AST because these values are normalized during loading/conversion)
		val astElement = filterSpecifierTraceable.astElement as JSONValue;
		val moduleSpecifierWithWildcardFromAST = switch (astElement) {
			JSONStringLiteral:
				astElement.value
			JSONObject:
				JSONModelUtils.getPropertyAsStringOrNull(astElement, NV_MODULE.name)
		};
		val sourceContainerFromAST = switch (astElement) {
			JSONObject:
				JSONModelUtils.getPropertyAsStringOrNull(astElement, NV_SOURCE_CONTAINER.name)
		};
		if ((moduleSpecifierWithWildcardFromAST !== null && moduleSpecifierWithWildcardFromAST.empty)
			|| (sourceContainerFromAST !== null && sourceContainerFromAST.empty)) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_MODULE_FILTER_SPECIFIER_EMPTY(),
					filterSpecifierTraceable.astElement, IssueCodes.PKGJ_INVALID_MODULE_FILTER_SPECIFIER_EMPTY);
			return false;
		}

		return true;
	}

	private def internalCheckModuleSpecifierHasFile(IN4JSProject project, List<ASTTraceable<ModuleFilterSpecifier>> filterSpecifiers) {
		// keep track of filter specifiers with matches (initialize with false for no matches)
		val checkedFilterSpecifiers = new HashMap<ASTTraceable<ModuleFilterSpecifier>, Boolean>();
		checkedFilterSpecifiers.putAll(filterSpecifiers.filter[element!==null].toMap([p | p], [false]));

		try {
			val treeWalker = new ModuleSpecifierFileVisitor(this, project, checkedFilterSpecifiers);
			Files.walkFileTree(project.location.toFileSystemPath, treeWalker);
		} catch (IOException e) {
			LOGGER.error("Failed to check module filter section of package.json file " + document.eResource.URI + ".");
			e.printStackTrace;
		}

		// obtain list of filter specifiers for which no file matches could be found
		val unmatchedSpecifiers = checkedFilterSpecifiers.entrySet
			.filter[e | e.value == false].map[e | e.key]

		for (ASTTraceable<ModuleFilterSpecifier> filterSpecifier : unmatchedSpecifiers) {
			val msg = getMessageForPKGJ_MODULE_FILTER_DOES_NOT_MATCH(filterSpecifier.element.moduleSpecifierWithWildcard);
			addIssue(msg, filterSpecifier.astElement, PKGJ_MODULE_FILTER_DOES_NOT_MATCH);
		}
	}

	private static class ModuleSpecifierFileVisitor extends SimpleFileVisitor<Path> {
		private final N4JSProjectSetupJsonValidatorExtension setupValidator;
		private final IN4JSProject project;
		private final Map<ASTTraceable<ModuleFilterSpecifier>, Boolean> filterSpecifiers;

		new (N4JSProjectSetupJsonValidatorExtension validatorExtension, IN4JSProject project, Map<ASTTraceable<ModuleFilterSpecifier>, Boolean> filterSpecifiers) {
			this.setupValidator = validatorExtension;
			this.project = project;
			this.filterSpecifiers = filterSpecifiers;
		}

		override visitFile(Path path, BasicFileAttributes attrs) throws IOException {
			for (val iter = filterSpecifiers.entrySet.iterator(); iter.hasNext();) {
				val entry = iter.next();
				val filterSpecifierTraceable = entry.key;
				val specifier = filterSpecifierTraceable.element?.moduleSpecifierWithWildcard;

				// only check for valid filter specifiers for matches
				val checkForMatches = specifier !== null && 
					isN4JSFile(specifier) && path.toFile.isFile || !isN4JSFile(specifier);
				
				// compute the source container path the filter applies to
				val location = getFileInSources(project, filterSpecifierTraceable.element, path);
				
				if (checkForMatches && location !== null) {
					val matchesFile = setupValidator.wildcardHelper.isPathContainedByFilter(location, filterSpecifierTraceable.element);
					val matchesN4JSFile = matchesFile && isN4JSFile(path.toString()); 
					
					// check whether the current filter matches the current file
					if (matchesFile) {
						// mark entry as matched
						entry.value = true;
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

			if (filterSpecifiers.empty) {
				return FileVisitResult.TERMINATE;
			} else {
				return FileVisitResult.CONTINUE;
			}
		}

		def private URI getFileInSources(IN4JSProject project, ModuleFilterSpecifier filterSpecifier, Path filePath) {
			val lPath = project.location.toFileSystemPath;
			val filePathString = lPath.relativize(filePath);
			return project.location.appendPath(filePathString.toString).toURI;
		}

		def private boolean isN4JSFile(String fileSpecifier) {
			return fileSpecifier.endsWith("." + N4JSGlobals.N4JS_FILE_EXTENSION) || 
				fileSpecifier.endsWith("." + N4JSGlobals.N4JSX_FILE_EXTENSION) || 
				fileSpecifier.endsWith("." + N4JSGlobals.N4JSD_FILE_EXTENSION);
		}
	}

	private def addNoValidationForN4JSFilesIssue(ASTTraceable<ModuleFilterSpecifier> filterSpecifier) {
		val moduleFilterType = (filterSpecifier.astElement.eContainer.eContainer as NameValuePair).name;
		addIssue(getMessageForPKGJ_FILTER_NO_N4JS_MATCH(moduleFilterType), filterSpecifier.astElement,
			PKGJ_FILTER_NO_N4JS_MATCH);
	}

	/**
	 * Checks whether a given {@code feature} can be used with the declared project ({@link #getProjectDescription()}),
	 * using the criteria defined by {@code supportedTypesPredicate}.
	 * 
	 * Adds INVALID_FEATURE_FOR_PROJECT_TYPE to {@code pair} otherwise.
	 * 
	 * @param featureDescription A textual user-facing description of the checked feature.
	 * @param value The JSONValue that has been declared for the given feature.
	 * @param supportedTypesPredicate A predicate which indicates whether the feature may be used for a given project type.
	 */
	def boolean checkFeatureRestrictions(String featureDescription, JSONValue value, Predicate<ProjectType> supportedTypesPredicate) {
		val type = getProjectDescription()?.projectType;
		if (type === null) {
			// cannot check feature if project type cannot be determined
			return false;
		}
		
		// empty values are always allowed
		if (isEmptyValue(value)) {
			return true;
		}
		
		// if container is a NameValuePair use whole pair as issue target
		val issueTarget = if (value.eContainer instanceof NameValuePair) value.eContainer else value;
		
		// check whether the feature can be used with the current project type
		if (!supportedTypesPredicate.apply(type)) {
			addIssue(getMessageForINVALID_FEATURE_FOR_PROJECT_TYPE(featureDescription.toFirstUpper, type.label),
				issueTarget, INVALID_FEATURE_FOR_PROJECT_TYPE);
			return false;
		}
		
		return true;
	}
	
	/** Return {@code true} iff the given value is considered empty (empty array or empty object). */
	private def isEmptyValue(JSONValue value) {
		return ((value instanceof JSONArray) && ((value as JSONArray).elements.empty)) ||
			((value instanceof JSONObject) && ((value as JSONObject).nameValuePairs.empty));
	}

	/**
	 * Returns with a new predicate instance that provides {@code true} only if the given N4JS project
	 * may be declared a dependency of a project of type {@link ProjectType#API}.
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
	private def Predicate<IN4JSProject> createAPIDependenciesPredicate() {
		return Predicates.or(API_TYPE.forN4jsProjects, 
			[anyOf(LIBRARY, VALIDATION, RUNTIME_LIBRARY, PLAINJS).apply(projectType)]
		);
	}
	
	/**
	 * Returns with a new predicate instance that specifies the type of projects
	 * that may be declared as dependency to the currently validated project description.
	 */
	private def Predicate<IN4JSProject> createDependenciesPredicate() {
		return switch(projectDescription.projectType) {
			case API: createAPIDependenciesPredicate
			// runtime libraries may only depend on other runtime libraries
			case RUNTIME_LIBRARY: RL_TYPE.forN4jsProjects
			// definition project may depend on any type of project but plainjs projects
			case DEFINITION: not(PLAINJS_TYPE).forN4jsProjects
			// otherwise, any project type may be declared as dependency
			default: Predicates.alwaysTrue
		}
	}

	/**
	  * Intermediate validation-only representation of a project reference.
	  * 
	  * This may or may not include a {@link #versionConstraint}.
	  * 
	  * Holds a trace link {@link #astRepresentation} to its original AST element, so that
	  * check methods can add issues to the actual elements. 
	  */
	@Data
	private static class ValidationProjectReference {
		N4JSProjectName referencedProjectName;
		NPMVersionRequirement npmVersion;
		EObject astRepresentation;
	}
	
	/**
	 * Returns a list of {@link ValidationProjectReference}s that can be extracted from the given {@code value},
	 * assuming it represents a valid {@code package.json} array of project IDs.
	 * 
	 * The returned references do not include version constraints. 
	 * 
	 * Fails silently and returns an empty list, in case {@code value} is malformed. 
	 */
	private def List<ValidationProjectReference> getReferencesFromJSONStringArray(JSONValue value) {
		if (!(value instanceof JSONArray)) {
			return emptyList;
		}
		return (value as JSONArray).elements.filter(JSONStringLiteral)
			.flatMap[literal | literal.referencesFromJSONStringLiteral ].toList;
	}
	
	/**
	 * Returns a list of {@link ValidationProjectReference}s that can be extracted from the given {@code value},
	 * assuming it represents a valid {@code package.json} dependencies object.
	 * 
	 * The returned references include version constraints. 
	 * 
	 * Fails silently and returns an empty list, in case {@code value} is malformed. 
	 */
	private def List<ValidationProjectReference> getReferencesFromDependenciesObject(JSONValue value) {
		if (!(value instanceof JSONObject)) {
			return emptyList;
		}

		val jsonObj = value as JSONObject;
		val vprs = new ArrayList<ValidationProjectReference>();
		for (NameValuePair pair : jsonObj.nameValuePairs) {
			if (pair.value instanceof JSONStringLiteral) {
				val stringLit = pair.value as JSONStringLiteral;
				val prjID = new N4JSProjectName(pair.name);
				val npmVersion = semverHelper.parse(stringLit.value);
				val vpr = new ValidationProjectReference(prjID, npmVersion, pair);
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
	private def List<ValidationProjectReference> getReferencesFromJSONStringLiteral(JSONValue value) {
		if (value instanceof JSONStringLiteral) {
			return #[new ValidationProjectReference(new N4JSProjectName(value.value), null, value)];
		} else {
			emptyList;
		}
	}
	
	/**
	 * Checks the given iterable of referenced projects.
	 * 
	 * This includes checking whether the referenced project can be found and validating the given 
	 * {@link ValidationProjectReference#versionConstraint} if specified.
	 * 
	 * @param references 
	 * 				The list of project references to validate.
	 * @param allProjects 
	 * 				A map of all projects that are accessible in the workspace.
	 * @param sectionLabel 
	 * 				A user-facing description of which section of project references is currently validated (e.g. "tested projects").
	 * @param enforceDependency 
	 * 				Additionally enforces that all given references are also listed as explicit project dependencies
	 * @param allowReflexive 
	 * 				Specifies whether reflexive (self) references are allowed. 
	 */
	private def void checkReferencedProjects(Iterable<ValidationProjectReference> references, Predicate<IN4JSProject> projectPredicate, 
		String sectionLabel, boolean enforceDependency, boolean allowReflexive) {

		val description = getProjectDescription();
		val allProjects = getAllProjectsByName();
		val allNodeModuleFolders = getAllNodeModulesFolders();

		// keeps track of all valid references
		val existentIds = HashMultimap.<N4JSProjectName, ValidationProjectReference>create;

		val projectDescriptionFileURI = document.eResource.URI;
		val currentProject = findProject(projectDescriptionFileURI).orNull;

		val allReferencedProjectNames = references.map[referencedProjectName].toSet;

		for(ref : references) {
			// check project existence.
			val id = ref.referencedProjectName;
			// Assuming completely broken AST.
			if (null !== id) {
				checkReference(ref, allProjects, allNodeModuleFolders, description, currentProject, allReferencedProjectNames,
					existentIds, allowReflexive, projectPredicate, sectionLabel
				);
			}
		}

		// check for duplicates among otherwise valid references
		checkForDuplicateProjectReferences(existentIds)

		// if specified, check that all references also occur in the dependencies sections
		if (enforceDependency) {
			checkDeclaredDependencies(existentIds.values, sectionLabel)
		}
	}

	private def void checkReference(ValidationProjectReference ref, Map<N4JSProjectName, IN4JSProject> allProjects,
		Map<N4JSProjectName, File> allNodeModuleFolders, ProjectDescription description, IN4JSProject currentProject,
		Set<N4JSProjectName> allReferencedProjectNames, HashMultimap<N4JSProjectName, ValidationProjectReference> existentIds,
		boolean allowReflexive, Predicate<IN4JSProject> projectPredicate, String sectionLabel
	) {
		// check project existence.
		val id = ref.referencedProjectName;
		val currentProjectName = new N4JSProjectName(description.projectName);

		// check for empty project ID
		if (id.isEmpty) {
			addIssue(IssueCodes.getMessageForPKGJ_EMPTY_PROJECT_REFERENCE(), ref.astRepresentation,
				IssueCodes.PKGJ_EMPTY_PROJECT_REFERENCE)
			return;
		}

		// obtain corresponding IN4JSProject handle
		val project = allProjects.get(id);

		// type cannot be resolved from index, hence project does not exist in workspace.
		if (null === project || null === project.projectType) {
			if (!currentProject.isExternal) {
				val msg = getMessageForNON_EXISTING_PROJECT(id);
				val packageVersion = if (ref.npmVersion === null) "" else ref.npmVersion.toString;
				addIssue(msg, ref.astRepresentation, null, NON_EXISTING_PROJECT, id.rawName, packageVersion);
			}
			return;
		} else {
			// keep track of actually existing projects
			existentIds.put(id, ref);
		}

		if (!currentProject.isExternal) {
			if (project.isExternal && !indexSynchronizer.isInIndex(project.projectDescriptionLocation as FileURI)) {
				val msg = getMessageForNON_REGISTERED_PROJECT(id);
				addIssue(msg, ref.astRepresentation, null, NON_REGISTERED_PROJECT, id.rawName);
				return;
			}

			val currNodeModulesFolder = allNodeModuleFolders.get(currentProjectName)?.toPath;
			val currNPM = currNodeModulesFolder?.resolve(id.rawName);
			if (currNPM !== null && !currNPM.toFile.exists) {
				val packageVersion = if (ref.npmVersion === null) "" else ref.npmVersion.toString;
				if (project.external) {
					val msg = getMessageForNON_EXISTING_PROJECT(id);
					addIssue(msg, ref.astRepresentation, null, NON_EXISTING_PROJECT, id.rawName, packageVersion);
				} else {
					val msg = getMessageForMISSING_YARN_WORKSPACE(id);
					addIssue(msg, ref.astRepresentation, null, MISSING_YARN_WORKSPACE, id.rawName, packageVersion);
				}
			}
		}

		// create only a single validation issue for a particular project reference.
		if (currentProjectName == id && !allowReflexive) {
			// reflexive self-references
			addProjectReferencesItselfIssue(ref.astRepresentation);
			return;
		} else if (!projectPredicate.apply(project)) {
			// reference to project of invalid type
			addInvalidProjectTypeIssue(ref.astRepresentation, id.rawName, 
				project.projectType, sectionLabel);
			return;
		}

		// check version constraint if the current project is not external and has no nested 
		// node_modules folder
		val boolean ignoreVersion = (currentProject.isExternal && description.hasNestedNodeModulesFolder);
		if (!ignoreVersion) {
			checkVersions(currentProject, ref, id, allProjects);
		}

		if (description.projectType !== ProjectType.DEFINITION) {
			checkImplProjectPresentForReferencedTypeDef(ref, project, allReferencedProjectNames);
		}
	}

	private def void checkImplProjectPresentForReferencedTypeDef(ValidationProjectReference ref,
		IN4JSProject referencedProject, Set<N4JSProjectName> allReferencedProjectNames) {

		if (referencedProject.projectType === ProjectType.DEFINITION) {
			val nameOfProjectDefinedByReferencedProject = referencedProject.definesPackageName;
			if (nameOfProjectDefinedByReferencedProject !== null) {
				if (!allReferencedProjectNames.contains(nameOfProjectDefinedByReferencedProject)) {
					val msg = IssueCodes.getMessageForPKGJ_IMPL_PROJECT_IS_MISSING_FOR_TYPE_DEF(
						nameOfProjectDefinedByReferencedProject, ref.referencedProjectName);
					addIssue(msg, ref.astRepresentation, IssueCodes.PKGJ_IMPL_PROJECT_IS_MISSING_FOR_TYPE_DEF);
				}
			}
		}
	}

	private def checkForDuplicateProjectReferences(Multimap<N4JSProjectName, ValidationProjectReference> validProjectRefs) {
		// obtain vendor ID of the currently validated project
		val currentVendor = getProjectDescription().vendorId;
		
		validProjectRefs.asMap.keySet.forEach [
			// grouped just by projectName
			if (validProjectRefs.get(it).size > 1) {
				val referencesByNameAndVendor = HashMultimap.<String, ValidationProjectReference>create;
				validProjectRefs.get(it).forEach [
					//use vendor id of the referring project if not provided explicitly
					referencesByNameAndVendor.put(currentVendor, it)
				]

				referencesByNameAndVendor.keySet.forEach [
					val mappedRefs = referencesByNameAndVendor.get(it);
					if (mappedRefs.size > 1) {
						mappedRefs 
							.sortBy[NodeModelUtils.findActualNodeFor(it.astRepresentation).offset]
							.tail
							.forEach [ ref | addDuplicateProjectReferenceIssue(ref.astRepresentation, ref.referencedProjectName)];
					}
				]
			}
		];
	}

	/**
	 * Checks that the given {@code reference}s are also declared as explicit project dependencies
	 * under {@code dependencies} or {@code devDependencies}.
	 */
	private def checkDeclaredDependencies(Iterable<ValidationProjectReference> references, String sectionLabel) {
		val declaredDependencies = getDeclaredProjectDependencies();

		references.forEach[reference |
			if (!declaredDependencies.containsKey(reference.referencedProjectName)) {
				addIssue(IssueCodes.getMessageForPKGJ_PROJECT_REFERENCE_MUST_BE_DEPENDENCY(reference.referencedProjectName, sectionLabel),
					reference.astRepresentation, IssueCodes.PKGJ_PROJECT_REFERENCE_MUST_BE_DEPENDENCY);
			}
		]
	}

	/** Checks if version constraint of the project reference is satisfied by any available project.*/
	private def checkVersions(IN4JSProject curPrj, ValidationProjectReference ref, N4JSProjectName id, Map<N4JSProjectName, IN4JSProject> allProjects) {
		val desiredVersion = ref.npmVersion;
		if (desiredVersion === null) {
			return;
		}

		val depProject = allProjects.get(id);
		val availableVersion = depProject.version;
		val availableVersionMatches = SemverMatcher.matches(availableVersion, desiredVersion);
		if (availableVersionMatches) {
			return; // version does match
		}

		// versions do not match

		val curPrjShadows = shadowingInfoHelper.isShadowingProject(curPrj);
		val dependencyShadows = shadowingInfoHelper.isShadowingProject(depProject);
		val desiredStr = SemverSerializer.serialize(desiredVersion);
		val availableStr = SemverSerializer.serialize(availableVersion);

		if (curPrjShadows || dependencyShadows) {
			val curPrjShadowsStr = if (curPrjShadows) "shadowing " else "";
			val dependencyShadowsStr = if (dependencyShadows) "shadowed " else "";
			val msg = getMessageForNO_MATCHING_VERSION_SHADOWING(curPrjShadowsStr, dependencyShadowsStr, id, desiredStr, availableStr);
			addIssue(msg, ref.astRepresentation, NO_MATCHING_VERSION_SHADOWING);
		} else {
			val msg = getMessageForNO_MATCHING_VERSION(id, desiredStr, availableStr);
			addIssue(msg, ref.astRepresentation, null, NO_MATCHING_VERSION, id.rawName, desiredVersion.toString);
		}
	}

	/**
	 * Returns the {@link ProjectDescription} that can be created based on the information
	 * to be found in the currently validated {@link JSONDocument}.
	 *
	 * @See {@link ProjectDescriptionLoader}
	 */
	protected def ProjectDescription getProjectDescription() {
		return contextMemoize(PROJECT_DESCRIPTION_CACHE, [
			val doc = getDocument();
			projectDescriptionLoader.loadProjectDescriptionAtLocation(doc.eResource.URI.trimSegments(1), doc);
		]);
	}

	/**
	 * Returns a cached view on all declared project dependencies mapped to the dependency project ID.
	 *
	 * @See {@link #getProjectDescription()}. 
	 */
	protected def Map<N4JSProjectName, ProjectDependency> getDeclaredProjectDependencies() {
		return contextMemoize(DECLARED_DEPENDENCIES_CACHE, [
			val description = getProjectDescription();
			return description.projectDependencies.toMap[d | new N4JSProjectName(d.projectName)];
		]);
	}

	private def getLabel(ProjectType it) {
		if (null === it) '''''' else it.toString.upperUnderscoreToHumanReadable
	}

	private def upperUnderscoreToHumanReadable(String s) {
		s.nullToEmpty.replaceAll('_', ' ').toLowerCase
	}

	private def addProjectReferencesItselfIssue(EObject target) {
		addIssue(messageForPROJECT_REFERENCES_ITSELF, target, PROJECT_REFERENCES_ITSELF);
	}

	private def addInvalidProjectTypeIssue(EObject target, String projectName, ProjectType type, String sectionLabel) {
		addIssue(getMessageForINVALID_PROJECT_TYPE_REF(projectName, type.label, sectionLabel),
			target, INVALID_PROJECT_TYPE_REF);
	}

	private def addDuplicateProjectReferenceIssue(EObject target, N4JSProjectName name) {
		addIssue(getMessageForDUPLICATE_PROJECT_REF(name), target, DUPLICATE_PROJECT_REF);
	}

	/**
	 * Adds an issue to every non-null element in {@code preferredTargets}.
	 *
	 * If {@code preferredTargets} is empty (or contains null entries only), adds an issue to 
	 * the {@code name} property of the {@code package.json} file. 
	 * 
	 * If there is no {@code name} property, adds an issue to the whole document (see {@link #getDocument()}).
	 */ 
	private def void addIssuePreferred(Iterable<? extends EObject> preferredTargets, String message, String issueCode) {
		// add issue to preferred targets
		if (!preferredTargets.filterNull.empty) {
			preferredTargets.filterNull
				.forEach[t | addIssue(message, t, issueCode); ]
			return;
		}
		// fall back to property 'name' 
		val nameValue = getSingleDocumentValue(NAME);
		if (nameValue !== null) {
			addIssue(message, nameValue, issueCode);
			return;
		}
		// finally fall back to document
		addIssue(message, document, issueCode)
	}

	/**
	 * Returns a map between all available project IDs and their corresponding 
	 * {@link IN4JSProject} representations.
	 *
	 * The result of this method is cached in the validation context.
	 */
	private def Map<N4JSProjectName, IN4JSProject> getAllProjectsByName() {
		return contextMemoize(ALL_EXISTING_PROJECT_CACHE) [
			val Map<N4JSProjectName, IN4JSProject> res = new HashMap;
			findAllProjects.forEach[p | res.put(p.projectName, p)];

			// also add those unnecessary projects that are not shadowed
			for (org.eclipse.xtext.util.Pair<FileURI, ProjectDescription> pair: extWS.projectsIncludingUnnecessary) {
				val location = pair.first;
				val project = findProject(location.toURI).orNull;
				if (project !== null && !shadowingInfoHelper.isShadowedProject(project) && !res.containsKey(project.projectName)) {
					res.put(project.projectName, project);
				}
			}
			return res;
		]
	}

	/**
	 * Returns a map between all user projects and their corresponding 
	 * node_modules folder locations.
	 *
	 * The result of this method is cached in the validation context.
	 */
	private def Map<N4JSProjectName, File> getAllNodeModulesFolders() {
		return contextMemoize(NODE_MODULES_LOCATION_CACHE) [
			val Map<N4JSProjectName, File> res = new HashMap;

			if (Platform.isRunning) { // necessary for xpect tests (non-ui)
				val IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				for (IProject project : root.projects) {
					if (project.isAccessible) {
						val iPath = project.location;
						val projectPath = iPath.toFile.toPath;
						val NodeModulesFolder nmFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectPath);
						if (nmFolder !== null) {
							val projectName = new EclipseProjectName(project.name).toN4JSProjectName
							res.put(projectName, nmFolder.nodeModulesFolder);
						}
					}
				}
			}
			return res;
		]
	}

	/** Transforms a {@link ProjectTypePredicate} into a predicate for {@link IN4JSProject}. */
	private def Predicate<IN4JSProject> forN4jsProjects(Predicate<ProjectType> predicate) {
		return [predicate.apply(projectType)];
	}
}
