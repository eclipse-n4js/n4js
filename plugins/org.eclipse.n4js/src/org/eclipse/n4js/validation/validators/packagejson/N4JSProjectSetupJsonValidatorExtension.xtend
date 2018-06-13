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
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Set
import java.util.Stack
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.JSON.JSONPackage
import org.eclipse.n4js.json.JSON.JSONStringLiteral
import org.eclipse.n4js.json.JSON.JSONValue
import org.eclipse.n4js.json.JSON.NameValuePair
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.json.validation.^extension.AbstractJSONValidatorExtension
import org.eclipse.n4js.json.validation.^extension.CheckProperty
import org.eclipse.n4js.n4mf.DeclaredVersion
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.n4mf.SourceContainerDescription
import org.eclipse.n4js.n4mf.SourceContainerType
import org.eclipse.n4js.n4mf.VersionConstraint
import org.eclipse.n4js.n4mf.utils.ProjectTypePredicate
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.utils.PackageJsonHelper
import org.eclipse.n4js.utils.ProjectDescriptionHelper
import org.eclipse.n4js.utils.Version
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.helper.PolyFilledProvisionPackageJson
import org.eclipse.n4js.validation.helper.SoureContainerAwareDependencyTraverser
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IContainer.Manager
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.validation.Check

import static com.google.common.base.Preconditions.checkState
import static org.eclipse.n4js.n4mf.ProjectType.*
import static org.eclipse.n4js.n4mf.utils.ProjectTypePredicate.*
import static org.eclipse.n4js.validation.IssueCodes.*

import static extension com.google.common.base.Strings.nullToEmpty

/**
 * A JSON validator extension that validates {@code package.json} resources in the context
 * of higher-level concepts such as project references, the general project setup and feature restrictions.
 * 
 * Generally, this validator includes constraints that are implemented based on the converted {@link ProjectDescription}
 * as it can be obtained from the {@link ProjectDescriptionHelper}. This especially includes non-local validation
 * such as the resolution of referenced projects.
 * 
 * For lower-level, structural and local validations with regard to {@code package.json} 
 * files , see {@link PackageJsonValidatorExtension}. 
 */
@Singleton
public class N4JSProjectSetupJsonValidatorExtension extends AbstractJSONValidatorExtension {
	
	static val API_TYPE = anyOf(API);
	static val LIBRARY_TYPE = anyOf(LIBRARY);
	static val RE_TYPE = anyOf(RUNTIME_ENVIRONMENT);
	static val RL_TYPE = anyOf(RUNTIME_LIBRARY);
	static val TEST_TYPE = anyOf(TEST);
	static val RE_OR_RL_TYPE = anyOf(RUNTIME_ENVIRONMENT, RUNTIME_LIBRARY);

	/**
	 * Key to store a converted ProjectDescription instance in the validation context for re-use across different check-methods
	 * @See {@link #getProjectDescription()} 
	 */
	private static final String PROJECT_DESCRIPTION_CACHE = "PROJECT_DESCRIPTION_CACHE";
	
	/**
	 * Key to store a map of all available projects in the validation context for re-use across different check-methods.
	 * @See {@link #getAllExistingProjectIds()} 
	 */
	private static String ALL_EXISTING_PROJECT_CACHE = "ALL_EXISTING_PROJECT_CACHE";

	@Inject
	private extension IN4JSCore

	@Inject
	private Manager containerManager;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private XpectAwareFileExtensionCalculator fileExtensionCalculator;
	
	@Inject
	private PackageJsonHelper packageJsonHelper;
	
	@Inject
	private ProjectDescriptionHelper projectDescriptionHelper;
	
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
		// lookup of Names in n4mf &
		val Map<String, JSONStringLiteral> mQName2rtDep = newHashMap()
	
		val description = getProjectDescription();
		val projectName = description.projectId;
		
		// if the project name cannot be determined, exit early
		if (projectName === null) {
			return;
		}

		// gather required runtime libraries in terms of JSONStringLiterals
		val requiredRuntimeLibrariesValue = getSingleDocumentValue(
			ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__REQUIRED_RUNTIME_LIBRARIES, JSONArray);
		if (requiredRuntimeLibrariesValue === null) {
			return;
		}
		var Iterable<? extends JSONStringLiteral> rteAndRtl = requiredRuntimeLibrariesValue.elements.filter(JSONStringLiteral);

		// Describing Self-Project as RuntimeDependency to handle clash with filled Members from current Project consistently.
		val selfProject = JSONModelUtils.createStringLiteral(projectName);
		val Optional<? extends IN4JSProject> optOwnProject = findProject(document.eResource.URI)
		if (optOwnProject.present) {
			rteAndRtl = Iterables.concat(rteAndRtl, #{selfProject})
		}

		for (JSONStringLiteral libraryLiteral : rteAndRtl) {
			if (null !== libraryLiteral) {
				val String libPPqname = libraryLiteral.value
				mQName2rtDep.put(libPPqname, libraryLiteral)
			}
		}

		// own Project can provide filled members as well, if of type RuntimeEnvironment/RuntimeLibrary:
		val List<IEObjectDescription> allPolyFillTypes = getAllNonStaticPolyfills(document.eResource)

		// 1.a. For Each containing File: get the Exported Polyfills:   <QN, PolyFilledProvision>
		// if the file is from our self, then ignore it; validation will be done for the local file separately.
		val LinkedListMultimap<String, PolyFilledProvisionPackageJson> exportedPolyfills_QN_to_PolyProvision = LinkedListMultimap.
			create
		for (ieoT : allPolyFillTypes) {
			val optSrcContainer = findN4JSSourceContainer(ieoT.EObjectURI)
			if (optSrcContainer.present) {
				val srcCont = optSrcContainer.get;
				val depQName = srcCont.project.projectId;
				val dependency = mQName2rtDep.get(depQName);
				if (dependency === null ) {
					// TODO IDE-1735 typically a static Polyfill - can only be used inside of a single project
				} else
				if (dependency !== selfProject) {
					exportedPolyfills_QN_to_PolyProvision.put(
						ieoT.qualifiedName.toString,
						new PolyFilledProvisionPackageJson(depQName, dependency, ieoT)
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
				val m = LinkedListMultimap.<String, PolyFilledProvisionPackageJson>create // memberName->PolyProvisionA,PolyProvisionB ...
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
						val filledTypeFQN = providers.head.descriptionStandard //or: polyExport_QN
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
			val libsString = keyS.toList.map [it.getValue].sort.join(", ")

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
			val polyFill = Boolean.valueOf(descr.getUserData(N4JSResourceDescriptionStrategy.POLYFILL_KEY))
			val staticPolyFill = Boolean.valueOf(descr.getUserData(N4JSResourceDescriptionStrategy.STATIC_POLYFILL_KEY))
			if (polyFill == Boolean.TRUE && staticPolyFill == Boolean.FALSE) {
				types.add(descr);
			}
		}
		return types;
	}
	
	/** IDEBUG-266 issue error warning on cyclic dependencies*/
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__DEPENDENCIES)
	def checkCyclicDependencies(JSONValue dependenciesValue) {
		val project = findProject(dependenciesValue.eResource.URI).orNull;
		if (null !== project) {
			val result = new SoureContainerAwareDependencyTraverser(project).result;
			if (result.hasCycle) {
				addIssue(
					getMessageForPROJECT_DEPENDENCY_CYCLE(result.prettyPrint([calculateName])),
					dependenciesValue.eContainer,
					JSONPackage.Literals.NAME_VALUE_PAIR__NAME,
					PROJECT_DEPENDENCY_CYCLE
				);
			} else {
				//for performance reasons following is not separate check
				/*
				 * otherwise we would traverse all transitive dependencies multiple times,
				 * and we would care for cycles
				 */
				project.holdsProjectWithTestFragmentDependsOnTestLibrary(dependenciesValue)
			}
		}
	}


	/**
	 * Checks if a project containing {@link SourceContainerType#TEST} depends 
	 * (directly or transitively) on a {@link ProjectType#RUNTIME_LIBRARY} test runtime library.
	 */
	private def holdsProjectWithTestFragmentDependsOnTestLibrary(IN4JSProject project, JSONValue dependenciesValue) {

		val JSONValue sourcesSection = getSingleDocumentValue(ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__SOURCES, JSONValue);
		val List<SourceContainerDescription> sourceContainers = packageJsonHelper.getSourceContainerDescriptions(sourcesSection);
		
		if (sourceContainers === null) {
			return;
		}
		
		val hasTestFragment = sourceContainers.findFirst[sf| SourceContainerType.TEST.equals(sf.getSourceContainerType)] !== null;

		if(!hasTestFragment){
			return;
		}

		if(!anyDependsOnTestLibrary(#[project])){
			addIssue(
					getMessageForSRCTEST_NO_TESTLIB_DEP(N4JSGlobals.MANGELHAFT),
					dependenciesValue.eContainer,
					JSONPackage.Literals.NAME_VALUE_PAIR__NAME,
					PROJECT_DEPENDENCY_CYCLE
				);
		}
	}
	
	/**
	 * check if any project in the list has dependency on test library, if so return true.
	 * Otherwise invoke recursively in dependencies list of each project in initial list.
	 *
	 * NOTE: this implementation is not cycle safe!
	 *
	 * @returns true if any of the projects in the provided list depends (transitively) on the test library.
	 */
	private def boolean anyDependsOnTestLibrary(List<? extends IN4JSProject> projects) {
		projects.findFirst[p|
				p.dependencies.findFirst[N4JSGlobals.VENDOR_ID.equals(vendorID) && (N4JSGlobals.MANGELHAFT.equals(projectId) || N4JSGlobals.MANGELHAFT_ASSERT.equals(projectId))] !== null
				|| anyDependsOnTestLibrary(p.dependencies)
			] !== null
	}

	private def String calculateName(IN4JSSourceContainerAware it) {
		it.projectId;
	}

	/**
	 * Checks whether a test project either tests APIs or libraries. Raises a validation issue, if the test project
	 * tests both APIs and libraries. Does nothing if the project description of the validated project is NOT a test
	 * project.
	 */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__TESTED_PROJECTS)
	def checkTestedProjectsType(JSONValue testedProjectsValue) {
		val description = getProjectDescription();
		
		// make sure the listed tested projects do not mismatch in their type (API vs. library)
		if (TEST == description.projectType) {
			val projects = description.testedProjects;
			if (!projects.nullOrEmpty) {
				val allProjects = getAllExistingProjectIds();
				val head = projects.head;
				val refProjectType = allProjects.get(head.projectId)?.projectType
				if (projects.exists[testedProject | refProjectType != allProjects.get(testedProject.projectId)?.projectType]) {
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
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__DEPENDENCIES)
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
			val expectedImplementationId = description.implementationId;
			val allProjects = getAllExistingProjectIds();
			
			dependencyPairs.filterNull.forEach[ pair |
				val dependencyProjectId = pair.name;
				val actualImplementationId = allProjects.get(dependencyProjectId)?.implementationId?.orNull;
				if (!actualImplementationId.nullOrEmpty && actualImplementationId != expectedImplementationId) {
					val message = getMessageForMISMATCHING_IMPLEMENTATION_ID(expectedImplementationId, 
						dependencyProjectId, actualImplementationId);
					addIssue(message, pair, MISMATCHING_IMPLEMENTATION_ID);
				}
			];
		}
	}

	/**
	 * Checks if any transitive external dependency of a workspace project references to a workspace
	 * project. If so, raises a validation warning.
	 */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__DEPENDENCIES)
	def checkExternalProjectDoesNotReferenceWorkspaceProject(JSONValue dependenciesValue) {
		val allProjects = getAllExistingProjectIds();
		val description = getProjectDescription();
		
		// if the project name cannot be determined, exit early
		if (description.projectId === null) {
			return;
		}
		
		val currentProject = allProjects.get(description.projectId);

		// Nothing to do with non-existing, missing and/or external projects.
		if (null === currentProject || !currentProject.exists || currentProject.external) {
			return;
		}

		val visitedProjectIds = newHashSet();
		val stack = new Stack;
		stack.addAll(currentProject.allDirectDependencies.filter(IN4JSProject).filter[external]);

		while (!stack.isEmpty) {

			val actual = stack.pop;
			val actualId = actual.projectId;
			checkState(actual.external, '''Implementation error. Only external projects are expected: «actual».''');

			if (!visitedProjectIds.add(actualId)) {
				// Cyclic dependency. This will be handles somewhere else. Nothing to do.
				return;
			}

			val actualDirectDependencies = actual.allDirectDependencies.filter(IN4JSProject);
			// If external has any *NON* external dependency we should raise a warning.
			val workspaceDependency = actualDirectDependencies.findFirst[!external];
			if (null !== workspaceDependency) {
				val workspaceDependencyId = workspaceDependency.projectId;
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

	/** Validates the 'dependencies' section of the {@code package.json}. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__DEPENDENCIES)
	def checkDependencies(JSONValue dependenciesValue) {
		// make sure 'dependencies' feature is allowed in combination with the current project type
		if (!checkFeatureRestrictions("dependencies", dependenciesValue, not(RE_OR_RL_TYPE))) {
			return;
		}
		
		val projectType = getProjectDescription().projectType;
		
		val dependenciesPredicate = switch(projectType) {
			case TEST: not(RE_OR_RL_TYPE).forN4jsProjects
			case API: createProjectPredicateForAPIs
			default: not(or(RE_OR_RL_TYPE, TEST_TYPE)).forN4jsProjects
		}

		val references = getReferencesFromDependenciesObject(dependenciesValue);
		checkReferencedProjects(references, dependenciesPredicate, "dependencies"); 
	}
	
	/** Validates the 'devDependencies' section of the {@code package.json}. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__DEV_DEPENDENCIES)
	def checkDevDependencies(JSONValue devDependenciesValue) {
		// make sure 'devDependencies' are allowed in combination with the current project type
		if (!checkFeatureRestrictions("devDependencies", devDependenciesValue, not(RE_OR_RL_TYPE))) {
			return;
		}
		
		val projectType = getProjectDescription().projectType;
		
		val dependenciesPredicate = switch(projectType) {
			case TEST: not(RE_OR_RL_TYPE).forN4jsProjects
			case API: createProjectPredicateForAPIs
			default: not(or(RE_OR_RL_TYPE, TEST_TYPE)).forN4jsProjects
		}

		val references = getReferencesFromDependenciesObject(devDependenciesValue);
		checkReferencedProjects(references, dependenciesPredicate, "devDependencies"); 
	}

	/** Checks the 'n4js.extendedRuntimeEnvironment' section. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__EXTENDED_RUNTIME_ENVIRONMENT)
	def checkExtendedRuntimeEnvironment(JSONValue extendedRuntimeEnvironmentValue) {
		// make sure 'extendedRuntimeEnvironment' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("extended runtime environment", extendedRuntimeEnvironmentValue, RE_TYPE)) {
			return;
		}

		val references =  extendedRuntimeEnvironmentValue.referencesFromJSONStringLiteral
		checkReferencedProjects(references, RE_TYPE.forN4jsProjects, "extended runtime environment");
	}
	
	/** Checks the 'n4js.requiredRuntimeLibraries' section. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__REQUIRED_RUNTIME_LIBRARIES)
	def checkRequiredRuntimeLibraries(JSONValue requiredRuntimeLibrariesValue) {
		// make sure 'requiredRuntimeLibraries' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("required runtime libraries", requiredRuntimeLibrariesValue, not(RE_TYPE))) {
			return;
		}
		
		val references =  requiredRuntimeLibrariesValue.referencesFromJSONStringArray
		checkReferencedProjects(references, RL_TYPE.forN4jsProjects, "required runtime libraries");
	}
	
	/** Checks the 'n4js.providedRuntimeLibraries' section. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__PROVIDED_RUNTIME_LIBRARIES)
	def checkProvidedRuntimeLibraries(JSONValue providedRuntimeLibraries) {
		// make sure 'requiredRuntimeLibraries' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("provided runtime libraries", providedRuntimeLibraries, RE_TYPE)) {
			return;
		}
		
		val references =  providedRuntimeLibraries.referencesFromJSONStringArray
		checkReferencedProjects(references, RL_TYPE.forN4jsProjects, "provided runtime libraries");
	}
	
	/** Checks the 'n4js.testedProjects' section. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__TESTED_PROJECTS)
	def checkTestedProjects(JSONValue testedProjectsValue) {
		// make sure 'testedProjects' is allowed in combination with the current project type
		if (!checkFeatureRestrictions("tested projects", testedProjectsValue, TEST_TYPE)) {
			return;
		}
		
		val references =  testedProjectsValue.referencesFromJSONStringArray
		checkReferencedProjects(references, not(TEST_TYPE).forN4jsProjects, "tested projects");
	}
	
	/** Checks the 'n4js.initModules' section. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__INIT_MODULES)
	def checkInitModules(JSONValue initModulesValue) {
		// initModule usage restriction
		checkFeatureRestrictions(ProjectDescriptionHelper.PROP__INIT_MODULES, initModulesValue, RE_OR_RL_TYPE);
	}
	
	/** Checks the 'n4js.execModule' section. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__EXEC_MODULE)
	def checkExecModule(JSONValue execModuleValue) {
		// execModule usage restriction
		checkFeatureRestrictions(ProjectDescriptionHelper.PROP__EXEC_MODULE, execModuleValue, RE_OR_RL_TYPE);
	}
	
	/** Checks the 'n4js.implementationId' section. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__IMPLEMENTATION_ID)
	def checkImplementationId(JSONValue implementationIdValue) {
		// implemenationId usage restriction
		checkFeatureRestrictions(ProjectDescriptionHelper.PROP__IMPLEMENTATION_ID, implementationIdValue, 
			not(or(RE_OR_RL_TYPE, TEST_TYPE)));
	}
	
	/** Checks the 'n4js.implementedProjects' section. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__IMPLEMENTED_PROJECTS)
	def checkImplementedProjects(JSONValue implementedProjectsValue) {
		// implementedProjects usage restriction
		if(checkFeatureRestrictions(ProjectDescriptionHelper.PROP__IMPLEMENTED_PROJECTS, implementedProjectsValue, 
			not(or(RE_OR_RL_TYPE, TEST_TYPE)))) {
		
			val references = implementedProjectsValue.referencesFromJSONStringArray;
			checkReferencedProjects(references, API_TYPE.forN4jsProjects, "implemented projects");
		}
	}

	/**
	 * Checks whether a given {@code feature} can be used with the declared project ({@link #getProjectDescription()}),
	 * using the criteria defined by {@code supportedTypesPredicate}.
	 * 
	 * Adds INVALID_FEATURE_FOR_PROJECT_TYPE to {@code pair} otherwise.
	 * 
	 * @param featureDescription A textual user-facing description of the checked feature.
	 * @param supportedTypesPredicate A predicate which indicates whether the feature may be used for a given project type.
	 */
	def boolean checkFeatureRestrictions(String featureDescription, JSONValue value, Predicate<ProjectType> supportedTypesPredicate) {
		val type = getProjectDescription()?.projectType;
		if (type === null) {
			// cannot check feature if project type cannot be determined
			return false;
		}
		
		// if container is a NameValuePair use whole pair as issue target
		val issueTarget = if (value.eContainer instanceof NameValuePair) value.eContainer else value;
		
		// check whether the feature can be used with the current project type
		if (!supportedTypesPredicate.apply(type)) {
			addIssue(getMessageForINVALID_FEATURE_FOR_PROJECT_TYPE(featureDescription.toFirstUpper, type.label),
				issueTarget, INVALID_FEATURE_FOR_PROJECT_TYPE);
			return false;
		}
		
		// add obsolete-block-warning for empty arrays
		if (value instanceof JSONArray && (value as JSONArray).elements.empty) {
			addIssue(getMessageForOBSOLETE_BLOCK(featureDescription), issueTarget, OBSOLETE_BLOCK);
			return false;
		}
		
		return true;
	}

	/**
	 * Returns with a new predicate instance that provides {@code true} only and if only the followings are true:
	 * <ul>
	 * <li>The project type is API or</li>
	 * <li>The project type is library and no implementation ID is specified for the library.</li>
	 * </ul>
	 * Otherwise the predicate provides {@code false} value.
	 */
	private def createProjectPredicateForAPIs() {
		return Predicates.or(API_TYPE.forN4jsProjects, [LIBRARY_TYPE.apply(projectType) && !implementationId.present]);
	}

	/**
	  * Intermediate validation-only representation of a project reference.
	  * 
	  * This may or may not include an {@link #versionConstraint}.
	  * 
	  * Holds a trace link {@link #astRepresentation} to its original AST element, so that
	  * check methods can add issues to the actual elements. 
	  */
	@Data
	private static class ValidationProjectReference {
		String referencedProjectId;
		VersionConstraint versionConstraint;
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
		return (value as JSONObject).nameValuePairs
			.filter[pair | pair.value instanceof JSONStringLiteral]
			.map[pair |
				return new ValidationProjectReference(
					pair.name, 
					packageJsonHelper.parseVersionConstraint((pair.value as JSONStringLiteral).value),
					 pair
				);
			].toList
	}
	
	/**
	 * Returns a singleton list of the {@link ValidationProjectReference} that can be created from {@code literal}
	 * assuming is of type {@link JSONStringLiteral}.
	 * 
	 * Returns an empty list, if this does not hold true. 
	 */ 
	private def List<ValidationProjectReference> getReferencesFromJSONStringLiteral(JSONValue value) {
		if (value instanceof JSONStringLiteral) {
			return #[new ValidationProjectReference(value.value, null, value)];
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
	 * @param currentProjectId The project name of the currently validated project.
	 * @param references The list of project references to validate.
	 * @param allProjects A map of all projects that are accessible in the workspace.
	 * @param sectionLabel A user-facing description of which section of project references is currently validated (e.g. "tested projects").
	 */
	private def checkReferencedProjects(Iterable<ValidationProjectReference> references, Predicate<IN4JSProject> projectPredicate, String sectionLabel) {

		val description = getProjectDescription();
		val currentProjectId = description.projectId;
		val allProjects = getAllExistingProjectIds();
		
		val existentIds = HashMultimap.<String, ValidationProjectReference>create;

		// Check project existence.
		references.forEach[ ref |
			val id = ref.referencedProjectId;
			// Assuming completely broken AST.
			if (null !== id) {
				// obtain corresponding IN4JSProject handle
				var project = allProjects.get(id);

				// Type cannot be resolved from index, hence project does not exist in workspace.
				if (null === project || null === project.projectType) {
					val projectDescriptionFileURI = ref.astRepresentation.eResource.URI;
					val currentProject = findProject(projectDescriptionFileURI).orNull;
					if (!currentProject.isExternal) { // in GH-821: remove this condition
						addIssue(getMessageForNON_EXISTING_PROJECT(id), ref.astRepresentation, NON_EXISTING_PROJECT);
					}
				} else {
					// create only one single validation issue for a particular project reference.
					if (currentProjectId == id) {
						addProjectReferencesItselfIssue(ref.astRepresentation);
					} else if (!projectPredicate.apply(project)) {
						addInvalidProjectTypeIssue(ref.astRepresentation, id, 
							project.projectType, sectionLabel);
					} else{
						checkVersions(ref, id, allProjects)
					}
					existentIds.put(id, ref);
				}
			}
		];

		checkForDuplicateRuntimeLibraries(existentIds)
	}

	private def checkForDuplicateRuntimeLibraries(HashMultimap<String, ValidationProjectReference> validProjectRefs) {
		// obtain vendor ID of the currently validated project
		val currentVendor = getProjectDescription().vendorId;
		
		validProjectRefs.asMap.keySet.forEach [
			// grouped just by projectID
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
							.forEach [ ref | addDuplicateProjectReferenceIssue(ref.astRepresentation, ref.referencedProjectId)];
					}
				]
			}
		];
	}

	/** Checks if version constraint of the project reference is satisfied by any available project.*/
	private def checkVersions(ValidationProjectReference ref, String id, Map<String, IN4JSProject> allProjects) {
		val desiredVersion = ref.versionConstraint
		if (desiredVersion !== null) {
			val availableVersion = allProjects.get(id).version
			val available = new Version(availableVersion.major, availableVersion.minor, availableVersion.micro, availableVersion.qualifier);
			val desiredLower = desiredVersion.lowerVersion
			val desiredUpper = desiredVersion.upperVersion
			if(desiredLower !==null){
				if(desiredUpper !== null){
					checkLowerVersion(desiredLower, desiredVersion.exclLowerBound, available, id)
					checkUpperVersion(desiredUpper, desiredVersion.exclUpperBound, available, id)
				}else{
					checkExactVersion(desiredLower, available, id)
				}
			}
		}
	}

	private def checkExactVersion(DeclaredVersion exactVersion, Version available, String id) {
			val lower = new Version(exactVersion.major, exactVersion.minor, exactVersion.micro, exactVersion.qualifier);
			if(!lower.equals(Version.MISSING))
				if(available.compareTo(lower) !== 0) 
					addVersionMismatchIssue(exactVersion, id, lower.toString, available.toString);
	}

	private def checkLowerVersion(DeclaredVersion desiredLower, boolean exclusive, Version available, String id) {
			val lower = new Version(desiredLower.major, desiredLower.minor, desiredLower.micro, desiredLower.qualifier);
			switch (available.compareTo(lower)) {
				case 0: {
					if (exclusive)
						addVersionMismatchIssue(desiredLower, id, "higher than " + lower.toString, available.toString);
				}
				case -1: {
					addVersionMismatchIssue(desiredLower, id, "higher than " + lower.toString, available.toString);
				}
		}
	}

	private def checkUpperVersion(DeclaredVersion desiredUpper, boolean exclusive, Version available, String id) {
			val upper = new Version(desiredUpper.major, desiredUpper.minor, desiredUpper.micro, desiredUpper.qualifier);
			switch (available.compareTo(upper)) {
				case 1: {
					addVersionMismatchIssue(desiredUpper, id, "lower than " + upper.toString, available.toString);
				}
				case 0: {
					if (exclusive)
						addVersionMismatchIssue(desiredUpper, id, "lower than " + upper.toString, available.toString);
				}
		}
	}

	/**
	 * Returns the {@link ProjectDescription} that can be created based on the information
	 * to be found in the currently validated {@link JSONDocument}.
	 * 
	 * @See {@link ProjectDescriptionHelper}
	 */
	protected def ProjectDescription getProjectDescription() {
		return contextMemoize(PROJECT_DESCRIPTION_CACHE, [
			val doc = getDocument();
			projectDescriptionHelper.loadProjectDescriptionAtLocation(doc.eResource.URI, doc, false);
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

	private def addInvalidProjectTypeIssue(EObject target, String projectId, ProjectType type, String sectionLabel) {
		addIssue(getMessageForINVALID_PROJECT_TYPE_REF(projectId, type.label, sectionLabel),
			target, INVALID_PROJECT_TYPE_REF);
	}

	private def addDuplicateProjectReferenceIssue(EObject target, String name) {
		addIssue(getMessageForDUPLICATE_PROJECT_REF(name), target, DUPLICATE_PROJECT_REF);
	}
	
	private def addVersionMismatchIssue(EObject target, String name, String requiredVersion, String presentVersion) {
		addIssue(getMessageForNO_MATCHING_VERSION(name, requiredVersion, presentVersion), target, NO_MATCHING_VERSION)
	}

	/**
	 * Returns a map between all available project IDs and their corresponding 
	 * {@link IN4JSProject} representations.
	 *
	 * The result of this method is cached in the validation context.
	 */
	private def Map<String, IN4JSProject> getAllExistingProjectIds() {
		return contextMemoize(ALL_EXISTING_PROJECT_CACHE) [
			val Map<String, IN4JSProject> res = new HashMap
			findAllProjects.filter[exists].forEach[p | res.put(p.projectId, p)]
			return res
		]
	}

	/** Transforms a {@link ProjectTypePredicate} into a predicate for {@link IN4JSProject}. */
	private def Predicate<IN4JSProject> forN4jsProjects(ProjectTypePredicate predicate) {
		return [predicate.apply(projectType)];
	}



}
