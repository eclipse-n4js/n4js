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
import org.eclipse.core.runtime.Path
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONPackage
import org.eclipse.n4js.json.JSON.JSONStringLiteral
import org.eclipse.n4js.json.JSON.JSONValue
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.json.validation.^extension.AbstractJSONValidatorExtension
import org.eclipse.n4js.json.validation.^extension.CheckProperty
import org.eclipse.n4js.n4mf.DeclaredVersion
import org.eclipse.n4js.n4mf.N4mfPackage
import org.eclipse.n4js.n4mf.ProjectDependency
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.ProjectReference
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.n4mf.SourceContainerDescription
import org.eclipse.n4js.n4mf.SourceContainerType
import org.eclipse.n4js.n4mf.utils.ProjectTypePredicate
import org.eclipse.n4js.projectModel.IN4JSArchive
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
import org.eclipse.n4js.utils.nodemodel.NodeModelUtilsN4
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.helper.PolyFilledProvisionPackageJson
import org.eclipse.n4js.validation.helper.SoureContainerAwareDependencyTraverser
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IContainer.Manager
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.validation.Check

import static com.google.common.base.CaseFormat.LOWER_CAMEL
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE
import static com.google.common.base.Preconditions.checkState
import static java.util.Collections.singletonList
import static java.util.Collections.unmodifiableMap
import static org.eclipse.n4js.n4mf.N4mfPackage.Literals.*
import static org.eclipse.n4js.n4mf.ProjectType.*
import static org.eclipse.n4js.n4mf.utils.ProjectTypePredicate.*
import static org.eclipse.n4js.validation.IssueCodes.*

import static extension com.google.common.base.Strings.nullToEmpty

/**
 * A JSON validator extension that validates {@code package.json} resources 
 * with regard to the N4JS project setup they declare.
 */
@Singleton
public class N4JSProjectSetupJsonValidatorExtension extends AbstractJSONValidatorExtension {
	
	static val API_TYPE = anyOf(API);
	static val LIBRARY_TYPE = anyOf(LIBRARY);
	static val RE_TYPE = anyOf(RUNTIME_ENVIRONMENT);
	static val RL_TYPE = anyOf(RUNTIME_LIBRARY);
	static val TEST_TYPE = anyOf(TEST);
	static val RE_OR_RL_TYPE = anyOf(RUNTIME_ENVIRONMENT, RUNTIME_LIBRARY);

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
	 *
	 */
	@Check
	def checkConsistentPolyfills(JSONDocument document) {
		// Take the RTE and RTL's check for duplicate fillings.
		// lookup of Names in n4mf &
		val Map<String, JSONStringLiteral> mQName2rtDep = newHashMap()
	
		// obtain ProjectDescription representation of 'document'
		val projectNameLiteral = getSingleDocumentValue(ProjectDescriptionHelper.PROP__NAME, JSONStringLiteral);
		// if project name cannot be determined, exit early
		if (projectNameLiteral === null || projectNameLiteral.value === null) {
			return;
		}
		val projectName = projectNameLiteral.value;

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
	def checkCyclicDependencyies(JSONValue dependenciesValue) {
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
					dependenciesValue,
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
	 * Raises a validation warning if the project type is 'System'. <br>
	 * This check (and the corresponding issue code) should be cleaned up once 'System' type is eliminated.
	 *
	 * <p>
	 * Also raises a warning if the project type is Library but 'lib' is used instead of the recommended 'library'.
	 *
	 * <p>
	 * Also raises a warning if the project type is Library but 'app' is used instead of the recommended 'application'.
	 *
	 */
	@Check
	def checkProjectType(ProjectDescription it) {
		if (LIBRARY == projectType) {
			val projectTypeNodes = NodeModelUtils.findNodesForFeature(it, PROJECT_DESCRIPTION__PROJECT_TYPE);
			if (projectTypeNodes.nullOrEmpty || projectTypeNodes.exists[!text.nullToEmpty.toLowerCase.contains(LIBRARY.literal.toLowerCase)]) {
				addIssue(
					getMessageForDEPRECATED_PROJECT_TYPE('lib', '''Use 'library' instead.'''),
					it,
					PROJECT_DESCRIPTION__PROJECT_TYPE,
					DEPRECATED_PROJECT_TYPE
				);
			}
		} else if (APPLICATION == projectType) {
			val projectTypeNodes = NodeModelUtils.findNodesForFeature(it, PROJECT_DESCRIPTION__PROJECT_TYPE);
			if (projectTypeNodes.nullOrEmpty || projectTypeNodes.exists[!text.nullToEmpty.toLowerCase.contains(APPLICATION.literal.toLowerCase)]) {
				addIssue(
					getMessageForDEPRECATED_PROJECT_TYPE('app', '''Use 'application' instead.'''),
					it,
					PROJECT_DESCRIPTION__PROJECT_TYPE,
					DEPRECATED_PROJECT_TYPE
				);
			}
		}
	}

	/**
	 * Checks whether a test project either tests APIs or libraries. Raises a validation issue, if the test project
	 * tests both APIs and libraries. Does nothing if the project description of the validated project is NOT a test
	 * project.
	 */
	@Check
	def checkTestedProjects(ProjectDescription it) {
		if (TEST == projectType) {
			val projects = it.testedProjects;
			if (!projects.nullOrEmpty) {
				val allProjects = it.existingProjectIds;
				val head = projects.head;
				val refProjectType = allProjects.get(head.projectId)?.projectType
				if (projects.exists[testedProject | refProjectType != allProjects.get(testedProject.projectId)?.projectType]) {
					addIssue(
						messageForMISMATCHING_TESTED_PROJECT_TYPES,
						it,
						PROJECT_DESCRIPTION__TESTED_PROJECTS,
						MISMATCHING_TESTED_PROJECT_TYPES
					);
				}
			}
		}
	}

	/**
	 * Checks whether a library project, that belongs to a specific implementation (has defined implementation ID) does not
	 * depend on any other libraries that belong to any other implementation. In such cases, raises validation issue.
	 */
	@Check
	def checkHasConsistentImplementationIdChain(ProjectDescription it) {
		if (LIBRARY == projectType && !implementationId.nullOrEmpty) {
			val expectedImplementationId = implementationId;
			val dependencies = it.projectDependencies;
			if (!dependencies.nullOrEmpty) {
				val allProjects = existingProjectIds;
				dependencies.filterNull.forEach[
					val actualImplementationId = allProjects.get(it.projectId)?.implementationId?.orNull;
					if (!actualImplementationId.nullOrEmpty && actualImplementationId != expectedImplementationId) {
						addIssue(
							getMessageForMISMATCHING_IMPLEMENTATION_ID(expectedImplementationId, it.projectId, actualImplementationId),
							it.eContainer,
							PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES,
							dependencies.indexOf(it),
							MISMATCHING_IMPLEMENTATION_ID
						);
					}
				];
			}
		}
	}

	/**
	 * Checks if any transitive external dependency of a workspace project references to a workspace
	 * project. If so, raises a validation warning.
	 */
	@Check
	def checkExternalProjectDoesNotReferenceeWorkspaceProject(ProjectDescription desc) {

		// Probably it has a broken model state.
		if (desc.projectId.nullOrEmpty) {
			return;
		}

		val allProjects = desc.existingProjectIds;
		val currentProject = allProjects.get(desc.projectId);

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
					desc,
					PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES,
					EXTERNAL_PROJECT_REFERENCES_WORKSPACE_PROJECT
				);
				return;
			}

			stack.addAll(actualDirectDependencies.filter[external]);
		}

	}

	@Check
	def checkReferencedProjects(ProjectDescription it) {

		val allProjects = existingProjectIds;

		// Project dependencies feature check. Obsolete or not allowed.
		val projectDescriptionFeatures = #[N4mfPackage.Literals.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES];
		if (checkFeature(
			projectDescriptionFeatures,
			not(RE_OR_RL_TYPE)
		)) {
			// Non-existing project dependencies or duplicates.
			val predicate = switch(projectType) {
				case TEST: not(RE_OR_RL_TYPE).forN4jsProjects
				case API: createProjectPredicateForAPIs
				default: not(or(RE_OR_RL_TYPE, TEST_TYPE)).forN4jsProjects
			}
			checkReferencedProjects(projectDescriptionFeatures, allProjects, predicate);
		}

		// Extended runtime environment feature check. Obsolete or not allowed.
		val extendedREFeatures = #[N4mfPackage.Literals.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT];
		if (checkFeature(
			extendedREFeatures,
			RE_TYPE
		)) {
			// Extended RE check.
			checkReferencedProjects(extendedREFeatures, allProjects, RE_TYPE.forN4jsProjects);
		}

		// Required runtime library feature check. Obsolete or not allowed.
		val requiredRLFeatures = #[N4mfPackage.Literals.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES];
		if (checkFeature(
			requiredRLFeatures,
			not(RE_TYPE)
		)) {
			// Required RL check.
			checkReferencedProjects(requiredRLFeatures, allProjects, RL_TYPE.forN4jsProjects);
		}

		// Provided RL feature check. Obsolete or not allowed.
		val providedRLFeatures = #[N4mfPackage.Literals.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES];
		if (checkFeature(
			providedRLFeatures,
			RE_TYPE
		)) {
			// Provided RL check.
			checkReferencedProjects(providedRLFeatures, allProjects, RL_TYPE.forN4jsProjects);
		}

		// Tested projects feature check. Obsolete or not allowed.
		val testProjectsFeatures = #[N4mfPackage.Literals.PROJECT_DESCRIPTION__TESTED_PROJECTS];
		if (checkFeature(
			testProjectsFeatures,
			TEST_TYPE
		)) {
			// Tested projects check.
			checkReferencedProjects(testProjectsFeatures, allProjects, not(TEST_TYPE).forN4jsProjects);
		}


		// Initializer module feature check for REs and RLs. Obsolete or not allowed.
		if (checkFeature(
			#[N4mfPackage.Literals.PROJECT_DESCRIPTION__INIT_MODULES],
			RE_OR_RL_TYPE
		)) {
			//
		}

		// Executer module feature check for REs and RLs. Obsolete or not allowed.
		if (checkFeature(
			#[N4mfPackage.Literals.PROJECT_DESCRIPTION__EXEC_MODULE],
			RE_OR_RL_TYPE
		)) {
			//
		}

		// Implementation ID feature check for applications, libraries and processors. Obsolete or not allowed.
		if (checkFeature(
			#[N4mfPackage.Literals.PROJECT_DESCRIPTION__IMPLEMENTATION_ID],
			not(or(RE_OR_RL_TYPE, TEST_TYPE))
		)) {
			//
		}

		// Implemented projects feature check for applications, libraries and processors. Obsolete or not allowed.
		val implementedProjectsFeatures = #[N4mfPackage.Literals.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS];
		if (checkFeature(
			implementedProjectsFeatures,
			LIBRARY_TYPE
		)) {
			//
			checkReferencedProjects(implementedProjectsFeatures, allProjects, API_TYPE.forN4jsProjects);
		}
	}

	@Check
	def void checkOutputFolder(ProjectDescription projectDescription) {
		val outputPathName = projectDescription.outputPath;
		if (projectDescription.projectType === ProjectType.VALIDATION || outputPathName === null) {
			return;
		}

		val outputPath = new Path(outputPathName);
		val sourceTypes = projectDescription.sourceContainers;

		for (SourceContainerDescription sourceFrgmt : sourceTypes) {
			for (var i = 0; i < sourceFrgmt.paths.size; i++) {
				val sourcePathStr = sourceFrgmt.paths.get(i);
				val sourcePath = new Path(sourcePathStr);
				val srcFrgmtName = sourceFrgmt.getSourceContainerType.getName().toString;

				if (".".equals(sourcePathStr) || sourcePath.equals(outputPath) || sourcePath.isPrefixOf(outputPath)) {
					val containingFolder = "The output";
					val nestedFolder = "a " + srcFrgmtName;
					val message = getMessageForOUTPUT_AND_SOURCES_FOLDER_NESTING(containingFolder, nestedFolder);
					addIssue(message, projectDescription, PROJECT_DESCRIPTION__OUTPUT_PATH_RAW, OUTPUT_AND_SOURCES_FOLDER_NESTING);
				}

				if (".".equals(outputPathName) || outputPath.isPrefixOf(sourcePath)) {
					val containingFolder = "A " + srcFrgmtName;
					val nestedFolder = "the output";
					val message = getMessageForOUTPUT_AND_SOURCES_FOLDER_NESTING(containingFolder, nestedFolder);
					addIssue(message, sourceFrgmt, SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW, i, OUTPUT_AND_SOURCES_FOLDER_NESTING);
				}
			}
		}
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

	private def checkReferencedProjects(ProjectDescription desc, Iterable<? extends EStructuralFeature> features,
		Map<String, IN4JSProject> allProjects, Predicate<IN4JSProject> projectPredicate) {

		if (null === desc || features.nullOrEmpty) {
			return;
		}

		val references = getNestedValues(desc, features);
		val existentIds = HashMultimap.<String, ProjectReference>create;

		// Check project existence.
		references.filter(ProjectReference).forEach[

			val id = it?.projectId;
			// Assuming completely broken AST.
			if (null !== id) {

				var project = null as IN4JSProject;
				if (allProjects.containsKey(id)) {
					project = allProjects.get(id);
				} else {
					val currentProject = allProjects.get(desc.projectId);
					project = currentProject?.libraryDependencies?.get(id);
				}

				// Type cannot be resolved neither from index, hence project does not exist in workspace.
				// Nor from used libraries of the current project.
				if (null === project || null === project.projectType) {
					val manifestURI = it.eResource.URI;
					val projectOfManifest = findProject(manifestURI).orNull;
					if (!projectOfManifest.isExternal) { // in GH-821: remove this condition
						addIssue(getMessageForNON_EXISTING_PROJECT(id), it, NON_EXISTING_PROJECT);
					}
				} else {
					// Create only one single validation issue for a particular project reference.
					if (desc?.projectId == id) {
						addProjectReferencesItselfIssue(it.eContainer, features.last, references.indexOf(it));
					}else if (!projectPredicate.apply(project)) {
						addInvalidProjectTypeIssue(it.eContainer, id, project.projectType, features.last, references.indexOf(it));
					}else{
						checkVersions(it, id, allProjects)
					}
					existentIds.put(id, it);
				}
			}
		];

		checkForDuplicateRuntimeLibraries(desc, existentIds)
	}

	private def checkForDuplicateRuntimeLibraries(ProjectDescription desc,
		HashMultimap<String, ProjectReference> validProjectRefs) {

		val currentVendor = desc.vendorId
		validProjectRefs.asMap.keySet.forEach [
			//grouped just by projectID
			if (validProjectRefs.get(it).size > 1) {
				val referencesByNameAndVendor = HashMultimap.<String, ProjectReference>create;
				validProjectRefs.get(it).forEach [
					var refVendor = it.vendorId
					//use vendor id of the refering project if not provided explicitly
					if (refVendor === null)
						refVendor = currentVendor
					referencesByNameAndVendor.put(refVendor, it)
				]

				referencesByNameAndVendor.keySet.forEach [
					val mappedRefs = referencesByNameAndVendor.get(it);
					if (mappedRefs.size > 1) {
						mappedRefs 
							.sortBy[NodeModelUtils.findActualNodeFor(it).offset]
							.tail
							.forEach [ref | addDuplicateProjectReferenceIssue(ref, ref.projectId)];
					}
				]
			}
		];
	}

	/** Checks if version constraint of the project reference is satisfied by any available project.*/
	private def checkVersions(ProjectReference it, String id, Map<String, IN4JSProject> allProjects) {
		if (it instanceof ProjectDependency) {
			val desiredVersion = it.versionConstraint
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

	private def boolean checkFeature(ProjectDescription pd, Iterable<? extends EStructuralFeature> features, Predicate<ProjectType> supportedTypesPredicate) {

		// Assuming completely broken AST.
		if (null === pd || features.nullOrEmpty) {
			return false;
		}

		// AST element does not exist at all.
		val value = pd.eGet(features.head);
		if (null === value) {
			return false;
		}
		val pdNode = NodeModelUtils.findActualNodeFor(pd);
		if (pdNode === null) {
			return false;
		}
		val regionKwAndBlock = NodeModelUtilsN4.findRegionOfKeywordWithOptionalBlock(pdNode, features.head.name);
		if (regionKwAndBlock === null) {
			return false;
		}

		val rootAstElement = if (value instanceof EObject) value else pd;
		val values = pd.getNestedValues(features);

		if (supportedTypesPredicate.apply(pd.projectType)) {
			if (values.empty) {
				addIssue(getMessageForOBSOLETE_BLOCK(features.head.label), rootAstElement,
					regionKwAndBlock.offset, regionKwAndBlock.length, OBSOLETE_BLOCK);
			}
		} else {
			addIssue(getMessageForINVALID_FEATURE_FOR_PROJECT_TYPE(features.head.label.toFirstUpper, pd.projectType.label),
				rootAstElement, regionKwAndBlock.offset, regionKwAndBlock.length, INVALID_FEATURE_FOR_PROJECT_TYPE
			);
			return false; // Interrupt any further validation.
		}

		return true;
	}

	private def getNestedValues(EObject it, Iterable<? extends EStructuralFeature> features) {
		if (null === it || features.nullOrEmpty) {
			return emptyList;
		}
		var obj = it as Object;
		for (val itr = features.iterator; itr.hasNext; /*nothing*/) {
			val feature = itr.next;
			if (obj instanceof EObject) {
				val value = obj.eGet(feature);
				if (null === value) {
					return emptyList;
				}
				obj = value;
				if (!itr.hasNext) {
					return if (value instanceof List<?>) value.filterNull.toList else singletonList(value);
				}
			}
		}
		return emptyList;
	}

	private def getLabel(ProjectType it) {
		if (null === it) '''''' else it.toString.upperUnderscoreToHumanReadable
	}

	private def getLabel(EStructuralFeature it) {
		return LOWER_CAMEL.to(UPPER_UNDERSCORE, name).upperUnderscoreToHumanReadable
	}

	private def upperUnderscoreToHumanReadable(String s) {
		s.nullToEmpty.replaceAll('_', ' ').toLowerCase
	}

	private def addProjectReferencesItselfIssue(EObject eObject, EStructuralFeature feature, int index) {
		addIssue(messageForPROJECT_REFERENCES_ITSELF, eObject, feature, index, PROJECT_REFERENCES_ITSELF);
	}

	private def addInvalidProjectTypeIssue(EObject eObject, String projectId, ProjectType type, EStructuralFeature feature, int index) {
		addIssue(getMessageForINVALID_PROJECT_TYPE_REF(projectId, type.label, feature.label),
			eObject, feature, index, INVALID_PROJECT_TYPE_REF
		);
	}

	private def addDuplicateProjectReferenceIssue(EObject eObject, String name) {
		addIssue(getMessageForDUPLICATE_PROJECT_REF(name), eObject, DUPLICATE_PROJECT_REF);
	}
	
	private def addVersionMismatchIssue(EObject eObject, String name, String requiredVersion, String presentVersion) {
		addIssue(getMessageForNO_MATCHING_VERSION(name, requiredVersion, presentVersion), eObject, NO_MATCHING_VERSION)
	}

	private def Map<String, IN4JSProject> getExistingProjectIds(EObject it) {
		val Map<String, IN4JSProject> res = new HashMap
		findAllProjects.filter[exists].forEach[res.put(it.projectId, it)]
		return res
	}

	private def getLibraryDependencies(IN4JSProject it) {
		if (null === it || !exists) {
			return emptyMap;
		}
		val map = <String, IN4JSProject>newHashMap();
		allDirectDependencies.filter(IN4JSArchive).map[projectId -> getProject]
			.forEach[map.put(key, value)];
		return unmodifiableMap(map);
	}


	/** Transforms a {@link ProjectTypePredicate} into a predicate for {@link IN4JSProject}. */
	private def Predicate<IN4JSProject> forN4jsProjects(ProjectTypePredicate predicate) {
		return [predicate.apply(projectType)];
	}



}
