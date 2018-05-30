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
package org.eclipse.n4js.n4mf.validation

import com.google.inject.Inject
import java.io.File
import java.lang.reflect.Method
import java.util.List
import java.util.Map
import java.util.Map.Entry
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Platform
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4mf.ModuleFilter
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier
import org.eclipse.n4js.n4mf.ModuleFilterType
import org.eclipse.n4js.n4mf.N4mfPackage
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.SourceContainerDescription
import org.eclipse.n4js.n4mf.SourceContainerType
import org.eclipse.n4js.n4mf.utils.IPathProvider
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.util.Exceptions
import org.eclipse.xtext.validation.AbstractDeclarativeValidator
import org.eclipse.xtext.validation.AbstractDeclarativeValidator.MethodWrapper
import org.eclipse.xtext.validation.AbstractDeclarativeValidator.State
import org.eclipse.xtext.validation.Check

import static org.eclipse.n4js.n4mf.N4mfPackage.Literals.*
import static org.eclipse.n4js.n4mf.utils.N4MFUtils.*
import static org.eclipse.n4js.n4mf.validation.IssueCodes.*

class N4MFValidator extends AbstractN4MFValidator {

	private extension N4mfPackage = N4mfPackage.eINSTANCE;

	@Inject
	private IPathProvider pathProvider

	override createMethodWrapper(AbstractDeclarativeValidator instanceToUse, Method method) {
		return new MethodWrapper(instanceToUse, method) {
			override void handleInvocationTargetException(Throwable targetException, State state) {

				// ignore GuardException, check is just not evaluated if guard is false
				// ignore NullPointerException, as not having to check for NPEs all the time is a convenience feature
				super.handleInvocationTargetException(targetException, state);
				if (targetException instanceof NullPointerException) {
					Exceptions.throwUncheckedException(targetException);
				}
			}
		};
	}
	
	private enum FolderType {
		SOURCE,
		SOURCE_EXTERNAL,
		SOURCE_TEST,
		OUTPUT,
		LIBRARY,
		RESOURCE;
	}
	
	static def FolderType sourceContainerFolderType(SourceContainerType type) {
		return switch (type) {
			case SourceContainerType.SOURCE: FolderType.SOURCE
			case SourceContainerType.EXTERNAL: FolderType.SOURCE_EXTERNAL
			case SourceContainerType.TEST: FolderType.SOURCE_TEST
		}
	}
	
	static def String folderTypeDescription(FolderType type) {
		return switch (type) {
			case FolderType.SOURCE:
				"SOURCE"
			case FolderType.SOURCE_EXTERNAL:
				"EXTERNAL"
			case FolderType.SOURCE_TEST:
				"TEST"
			case FolderType.RESOURCE:
				"Resources"
			case FolderType.LIBRARY:
				"Libraries"
			case FolderType.OUTPUT:
				"Output"
		}
	}
	
	/**
	 * Collect all specified paths in the section Output, Libraries, Resources and Sources and checks for duplicates. 
	 */
	@Check
	def void checkProjectDescription(ProjectDescription projectDescription) {
		// keep list of all occurring folder types
		val types = <FolderType>newArrayList
		
		// keep map of all paths in the description (maps type to list of folders)
		val allPaths = <FolderType, List<String>>newHashMap
		
		// extract all source paths to be found in the source container section
		val sourceTypes = projectDescription.sourceContainers
		val allSourcePaths = sourceTypes.toMap(
			[type | type.sourceContainerType.sourceContainerFolderType], 
			[type | type.paths]
		)
		// add source container types to list of folder types
		types.addAll(sourceTypes.map[d | d.sourceContainerType.sourceContainerFolderType])
		allPaths.putAll(allSourcePaths)
	
		// if present add output path feature to types
		types.add(FolderType.OUTPUT)
		// output path does not have to exist, usually not added to git and created on the fly, cf. IDEBUG-197
		// if present, add output folder to list of paths
		allPaths.put(FolderType.OUTPUT,
			if (projectDescription.outputPath !== null) #[projectDescription.outputPath] else #[])

		// Libraries section
		types.add(FolderType.LIBRARY);
		allPaths.put(FolderType.LIBRARY, projectDescription.libraryPaths);

		// Resources section
		types.add(FolderType.RESOURCE)
		allPaths.put(FolderType.RESOURCE, projectDescription.resourcePaths);
		
		// for each folder type, check for duplicate paths across all collection sections
		types.forEach [ folderType |
			folderType.checkForDuplicatePaths(projectDescription, allPaths)
		]
	}

	/**
	 * Checks the <code>MainModule</code> property of the given project description for validity.
	 */
	@Check
	def public void checkMainModule(ProjectDescription pd) {
		val mainModuleSpecifier = pd.mainModule;
		if (mainModuleSpecifier !== null) { // this property is optional, so null is fine!
			if (mainModuleSpecifier.empty || !isExistingModule(pd, mainModuleSpecifier)) {
				val specifierToShow = if (mainModuleSpecifier.empty) "<empty string>" else mainModuleSpecifier;
				addIssue(getMessageForNON_EXISTING_MAIN_MODULE(specifierToShow), pd,
					PROJECT_DESCRIPTION__MAIN_MODULE, NON_EXISTING_MAIN_MODULE);
			}
		}
	}

	/**
	 * Checks whether all folders in the given {@code containerToPath} map
	 * actually exist on the file system.
	 */
	def private checkForExistingPaths(ProjectDescription projectDescription,
		Map<FolderType, List<String>> containerToPath) {
		val uri = projectDescription.eResource.URI
		val absoluteProjectPath = pathProvider.getAbsoluteProjectPath(uri)
		
		// iterate over all paths in containerToPath
		for (entry : containerToPath.entrySet) {
			for (path : entry.value) {
				val absolutePath = absoluteProjectPath + File.separator + path
				val file = new File(absolutePath)
				if (!file.exists || file.file) {
					val messageFileDoesntExist = getMessageForNON_EXISTING_PATH(path)
					val messageFileNoFolder = getMessageForNO_FOLDER_PATH(path)

					val FolderType folderType = entry.key
					
					// obtain EObject of the attribute that contains the path  
					val container = getContainer(folderType, projectDescription)
					// obtain EAttribute for the given folder type
					val feature = getAttribute(folderType)
					val index = projectDescription.getIndex(folderType, path)
					
					if (index == -2) {
						// add issue to single-valued feature 'feature'
						if (!file.exists) {
							addIssue(messageFileDoesntExist, container, feature, NON_EXISTING_PATH)
						} else { // file must be directory
							addIssue(messageFileNoFolder, container, feature, NO_FOLDER_PATH)
						}
					} else if (index > -1) {
						// add issue to index-th element of multi-valued feature 'feature'
						if (!file.exists) {
							addIssue(messageFileDoesntExist, container, feature, index, NON_EXISTING_PATH)
						} else { // file must be directory
							addIssue(messageFileNoFolder, container, feature, index, NO_FOLDER_PATH)
						}
					}
				}
			}
		}
	}

	/** Checks a source fragment (e.g. only the sources.external section)*/
	@Check
	def void checkSourceFragment(SourceContainerDescription sourceFragment) {
		sourceFragment.paths.forEach [
			checkForExistingPath(sourceFragment)
		]
		val paths = sourceFragment.paths
		checkForDuplicatePaths(sourceFragment, paths)
	}

	/**
	 * Determines whether {@code paths} contains any path that is a duplicate in the manifest file.
	 * 
	 * Adds issues to all paths of {@code folderType} if a duplicate has been declared in other
	 * sections of the manifest.
	 */
	def checkForDuplicatePaths(FolderType folderType, ProjectDescription projectDescription,
		Map<FolderType, List<String>> paths) {

		// Invert 'paths' and group by path. After that, a path maps to the 
		// folder types for which it is defined in the manifest 
		val pathToContainer = <String, List<FolderType>>newHashMap
		paths.entrySet.map[value -> key].forEach(
			e |
				e.key.forEach [
					val grouped = pathToContainer.get(it) ?: newArrayList;
					grouped.add(e.value);
					pathToContainer.put(it, grouped)
				]
		)
		// find all paths that are mapped to more than one folder type (== duplicate)
		val duplicatePaths = pathToContainer.keySet.filter[pathToContainer.get(it).size > 1]
		
		// obtain feature and container of the currently examined FolderType
		val container = getContainer(folderType, projectDescription)
		val feature = getAttribute(folderType)
		
		for (duplicatePath : duplicatePaths) {
			// find duplicate places (elsewhere than 'folderType')	
			val duplicatePlaces = (pathToContainer.get(duplicatePath)).filter[it != folderType]
			
			// double-check that there are actually other places (apart from 'folderType' and that 
			// the duplicate is not a source-and-output duplicate
			if (!duplicatePlaces.empty && !isSourcesAndOutputDuplicate(feature, duplicatePlaces)) {
				// construct error message
				val message = getMessageForDUPLICATE_PATH(duplicatePlaces
						.map[p | p.folderTypeDescription].sort.join(", "))
				
				// compute index of duplicatePath in projectDescription
				val index = projectDescription.getIndex(folderType, duplicatePath)

				if (index == -2) {
					// index == -2 indicates a single-valued feature (e.g. Output)
					addIssue(message, container, feature, DUPLICATE_PATH)
				} else if (index > -1) {
					// otherwise add the issue only to index-th element of multi-valued feature 'feature'
					addIssue(message, container, feature, index, DUPLICATE_PATH)
				}
			}
		}
	}
	
	/** 
	 * Returns {@code true} iff the given list of {@code duplicatePlaces} represent a duplicate
	 * for which a path is listed once in the Output section and once in the Source section.
	 */
	private def boolean isSourcesAndOutputDuplicate(EAttribute feature, Iterable<FolderType> duplicatePlaces) {
		val placesHasOutput = !duplicatePlaces.filter[ p | p == FolderType.OUTPUT].isEmpty;
		val placesHasSTE = !duplicatePlaces.filter[ p | FolderType.SOURCE == p || FolderType.SOURCE_TEST == p || FolderType.SOURCE_EXTERNAL == p].isEmpty;

		if (placesHasSTE && feature.name == "outputPathRaw")
			return true;
		if (placesHasOutput && feature.name == "pathsRaw")
			return true;
		return false;
	}

	/** Returns the EObject that holds the list of paths for the given FolderType. */
	private def EObject getContainer(FolderType pathContainer, ProjectDescription projectDescription) {
		switch (pathContainer) {
			case SOURCE: projectDescription.sourceContainers.findFirst[c | c.sourceContainerType == SourceContainerType.SOURCE]
			case SOURCE_EXTERNAL: projectDescription.sourceContainers.findFirst[c | c.sourceContainerType == SourceContainerType.EXTERNAL]
			case SOURCE_TEST: projectDescription.sourceContainers.findFirst[c | c.sourceContainerType == SourceContainerType.TEST]
			default: projectDescription
		}
	}

	/**
	 * Returns the {@link EAttribute} that correspond to the given {@link FolderType}.
	 */
	private def EAttribute getAttribute(FolderType type) {
		switch (type) {
			case SOURCE: SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW
			case SOURCE_EXTERNAL: SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW
			case SOURCE_TEST: SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW
			case RESOURCE: PROJECT_DESCRIPTION__RESOURCE_PATHS_RAW
			case LIBRARY: PROJECT_DESCRIPTION__LIBRARY_PATHS_RAW
			case OUTPUT: PROJECT_DESCRIPTION__OUTPUT_PATH_RAW
		}
	}

	/**
	 * Returns the index of {@code path} in the corresponding manifest section that specifies {@code FolderType}
	 * folders. 
	 * 
	 * In case of single-valued features (e.g. Output) this method returns {@code -2}.
	 * 
	 * In case the index cannot be determined this method returns {@code -1}.
	 */
	private def getIndex(ProjectDescription projectDescription, FolderType folderType, String path) {
		switch (folderType) {
			case FolderType.SOURCE: {
				val container = getContainer(folderType, projectDescription) as SourceContainerDescription;
				return container.paths.lastIndexOf(path)
			}
			case FolderType.SOURCE_EXTERNAL: {
				val container = getContainer(folderType, projectDescription) as SourceContainerDescription;
				return container.paths.lastIndexOf(path)
			}
			case FolderType.SOURCE_TEST: {
				val container = getContainer(folderType, projectDescription) as SourceContainerDescription;
				return container.paths.lastIndexOf(path)
			}
			default:
				switch (folderType) {
					case FolderType.RESOURCE:
						projectDescription.resourcePaths.lastIndexOf(path)
					case FolderType.OUTPUT:
						if (path.equals(projectDescription.outputPath)) -2 else -1
					case FolderType.LIBRARY:
						projectDescription.libraryPaths.lastIndexOf(path)
					default:
						-1
				}
		}
	}
	
	/** Checks for duplicate paths within a SourceContainerDescription (e.g. sources.external) section. */
	private def checkForDuplicatePaths(SourceContainerDescription sourceFragment, List<String> paths) {
		val duplicatePaths = getDuplicates(paths)
		duplicatePaths.forEach [
			addIssue(getMessageForDUPLICATE_PATH_INTERNAL, sourceFragment, SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW,
				paths.indexOf(it.key), DUPLICATE_PATH_INTERNAL)
		]
	}

	/** Returns a list of all duplicates in the given list of paths. */
	private def Iterable<Entry<String, List<String>>> getDuplicates(List<String> paths) {
		val groupedPaths = paths.groupBy[it]
		groupedPaths.entrySet.filter[value.size > 1]
	}

	/** 
	 * Returns a list of duplicate module specifiers. That is of specifiers that equal
	 * both in moduleSpecifierFilter as well as in source path.
	 */
	private def getDuplicateModuleSpecifiers(Iterable<Pair<String, String>> paths) {
		val groupedPaths = paths.groupBy[it]
		groupedPaths.entrySet.filter[value.size > 1]
	}

	/** Checks for duplicate paths within the Resources section. */
	@Check
	def checkResourcesSection(ProjectDescription projectDescription) {
		val paths = projectDescription.resourcePaths
		
		// check for existence
		checkForExistingPaths(projectDescription, newHashMap(FolderType.RESOURCE -> paths))
		
		// check for duplicates
		val duplicatePaths = getDuplicates(paths)
		duplicatePaths.forEach [
			addIssue(getMessageForDUPLICATE_PATH_INTERNAL, projectDescription, PROJECT_DESCRIPTION__RESOURCE_PATHS_RAW,
				paths.lastIndexOf(it.key), DUPLICATE_PATH_INTERNAL)
		]
	}

	/** Checks for duplicate paths within a single ModuleFilter section. */
	@Check
	def checkForDuplicateModuleSpecifiers(ModuleFilter moduleFilter) {
		// keep a list of all sourcePaths in moduleFilter
		val paths = moduleFilter.moduleSpecifiers.filter[sourcePath !== null].map [
			moduleSpecifierWithWildcard -> sourcePath
		].toList
		
		// obtain list of all specifiers that do not explicitly specify a source path
		val filterWithOutExplicitSourceFolder = moduleFilter.moduleSpecifiers.filter[sourcePath === null]

		if (!filterWithOutExplicitSourceFolder.empty) {
			// obtain all Sources-section source paths
			val sourcePaths = EcoreUtil2.getContainerOfType(moduleFilter, ProjectDescription).sourceContainers.map [
				it.paths
			].flatten.toSet
			for (spec : filterWithOutExplicitSourceFolder) {
				// add the filter of 'spec' for each of the declared source folders
				for (sourcePath : sourcePaths) {
					paths.add((spec.moduleSpecifierWithWildcard -> sourcePath))
				}
			}
		}
		val duplicatePaths = getDuplicateModuleSpecifiers(paths).map[key].map[key].toSet
		
		// Add issues for all duplicate module specifiers (equal source container + moduleSpecifierFilter)
		for (duplicatePath : duplicatePaths) {
			val moduleSpec = moduleFilter.moduleSpecifiers.findLast[moduleSpecifierWithWildcard == duplicatePath]
			addIssue(getMessageForDUPLICATE_MODULE_SPECIFIER, moduleSpec,
				MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD, DUPLICATE_MODULE_SPECIFIER)
		}
	}
	
	@Check
	def public checkForValidModuleSpecifiersInFilter(ModuleFilter moduleFilter) {
		moduleFilter.moduleSpecifiers.forEach [
			val valid = checkForValidWildcardModuleSpecifier(moduleSpecifierWithWildcard)
			if (valid) {
				checkForExistingWildcardModuleSpecifier(moduleSpecifierWithWildcard)
			}
		]
	}

	def private checkForValidWildcardModuleSpecifier(ModuleFilterSpecifier moduleFilterSpecifier,
		String moduleFilterSpecifierWithWildcard) {
		val wrongWildcardPattern = "***"
		if (moduleFilterSpecifier?.moduleSpecifierWithWildcard !== null) {
			if (moduleFilterSpecifier.moduleSpecifierWithWildcard.contains(wrongWildcardPattern)) {
				addIssue(
					getMessageForINVALID_WILDCARD(wrongWildcardPattern),
					moduleFilterSpecifier,
					MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD,
					INVALID_WILDCARD
				)
				return false
			}
			val wrongRelativeNavigation = "../"
			if (moduleFilterSpecifier.moduleSpecifierWithWildcard.contains(wrongRelativeNavigation)) {
				addIssue(
					getMessageForNO_RELATIVE_NAVIGATION,
					moduleFilterSpecifier,
					MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD,
					NO_RELATIVE_NAVIGATION
				)
				return false
			}
		}
		return true
	}

	def private checkForExistingWildcardModuleSpecifier(ModuleFilterSpecifier moduleFilterSpecifier,
		String moduleFilterSpecifierWithWildcard) {
		val sourcePaths = EcoreUtil2.getContainerOfType(moduleFilterSpecifier, ProjectDescription).sourceContainers.map [
			paths
		].flatten
		val uri = moduleFilterSpecifier.eResource.URI
		val absoluteProjectPath = pathProvider.getAbsoluteProjectPath(uri)
		val hasIssue = !sourcePaths.filter [
			var res = false
			if (moduleFilterSpecifier.sourcePath !== null)
				res = it == moduleFilterSpecifier.sourcePath
			else
				res = true
			return res
		].exists [
			val basePathToCheck = absoluteProjectPath + "/" + it
			val pathsToFind = "/" + moduleFilterSpecifier.moduleSpecifierWithWildcard
			val foundFiles = WildcardPathFilter.collectPathsByWildcardPath(basePathToCheck, pathsToFind)
			foundFiles.filter[endsWith(".n4js") || endsWith(".n4jsx")].forEach [
				handleNoValidationForN4JSFiles(moduleFilterSpecifier, moduleFilterSpecifierWithWildcard)
			]
			return !foundFiles.empty
		]

		if (hasIssue) {
			addIssue(getMessageForNON_EXISTING_MODULE_SPECIFIER(moduleFilterSpecifier.moduleSpecifierWithWildcard),
				moduleFilterSpecifier, MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD,
				NON_EXISTING_MODULE_SPECIFIER)
		}
	}

	def private handleNoValidationForN4JSFiles(ModuleFilterSpecifier moduleFilterSpecifier,
		String moduleFilterSpecifierWithWildcard) {
		addIssue(
			getMessageForDISALLOWED_NO_VALIDATE_FOR_N4JS(
				(moduleFilterSpecifier.eContainer as ModuleFilter).moduleFilterType.getModuleFilterName
			),
			moduleFilterSpecifier,
			MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD,
			DISALLOWED_NO_VALIDATE_FOR_N4JS
		)
	}

	def private checkForExistingPath(String path, SourceContainerDescription sourceFragment) {
		if (path.contains("*") || path.contains("?")) {
			addIssue(getMessageForWILDCARD_NOT_ALLOWED, sourceFragment, SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW,
				sourceFragment.paths.indexOf(path), WILDCARD_NOT_ALLOWED)
			return;
		}
		val uri = sourceFragment.eResource.URI
		val absoluteProjectPath = pathProvider.getAbsoluteProjectPath(uri)
		val foundFiles = {
			val absolutePath = absoluteProjectPath + File.separator + path
			if (new File(absolutePath).exists) {
				#[absolutePath]
			} else
				#[]
		}
		if (foundFiles.empty) {
			addIssue(getMessageForNON_EXISTING_SOURCE_PATH(path), sourceFragment, SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW,
				sourceFragment.paths.indexOf(path), NON_EXISTING_SOURCE_PATH)
		}
		if (isGenerationAwareN4JSContainer(sourceFragment.getSourceContainerType)) {
			for (foundFile : foundFiles.filter[new File(it).file]) {
				addIssue(getMessageForNO_FOLDER_PATH(path), sourceFragment, SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW,
					sourceFragment.paths.indexOf(path), NO_FOLDER_PATH)
			}
		}
	}

	@Check
	def checkForProjectIdProjectFolderNameMismatch(ProjectDescription projectDescription) {
		val projectId = projectDescription.projectId
		val folderName = projectDescription.eResource.URI.trimSegments(1).lastSegment
		if (folderName != projectId) {
			addIssue(getMessageForPROJECT_NAME_MISMATCH(projectId, folderName), projectDescription,
				PROJECT_DESCRIPTION__PROJECT_ID, PROJECT_NAME_MISMATCH)
		}
	}

	@Check
	def checkForProjectIdEclipseProjectNameMismatch(ProjectDescription projectDescription) {
		val projectId = projectDescription.projectId
		if (Platform.isRunning) {
			val root = ResourcesPlugin.getWorkspace.root;
			val manifestUri = projectDescription.eResource.URI;

			val eclipseFolderName = if (manifestUri.isPlatformResource) {
					val platformURI = manifestUri.trimSegments(1).toPlatformString(true);
					val resolved = root.findMember(platformURI);
					if (resolved instanceof IProject) {
						resolved.name;
					}
				} else {
					new File(manifestUri.toFileString).parentFile.name
				}

			if (eclipseFolderName != projectId) {
				addIssue(getMessageForPROJECT_NAME_ECLIPSE_MISMATCH(projectId, eclipseFolderName), projectDescription,
					PROJECT_DESCRIPTION__PROJECT_ID, PROJECT_NAME_ECLIPSE_MISMATCH)
			}
		}
	}

	@Check
	def checkMultipleModuleFiltersForSameType(ProjectDescription projectDescription) {
		val duplicateEntries = projectDescription.moduleFilters.groupBy[it.moduleFilterType].entrySet.filter [
			value.size > 1
		]
		if (duplicateEntries.size > 0) {
			val issueCode = MULTIPLE_MODULE_FILTERS_FOR_SAME_TYPE
			val feature = MODULE_FILTER__MODULE_FILTER_TYPE
			for (duplicateEntry : duplicateEntries) {
				val message = getMessageForMULTIPLE_MODULE_FILTERS_FOR_SAME_TYPE(duplicateEntry.key.getModuleFilterName)
				val moduleFilter = duplicateEntry.value.last
				addIssue(message, moduleFilter, feature, issueCode)
			}
		}
	}

	def private getModuleFilterName(ModuleFilterType moduleFilterType) {
		switch (moduleFilterType) {
			case NO_VALIDATE:
				"noValidate"
			case NO_MODULE_WRAPPING:
				"noModuleWrap"
			default: {
				"unknown filter type"
			}
		}
	}

	@Check
	def checkInSrcForIsDeclaredSourceContainer(ModuleFilterSpecifier moduleFilterSpecifier) {
		if (moduleFilterSpecifier.sourcePath !== null) {
			val projectDescription = EcoreUtil2.getContainerOfType(moduleFilterSpecifier, ProjectDescription)
			if (!projectDescription.sourceContainers.exists[paths.contains(moduleFilterSpecifier.sourcePath)]) {
				addIssue(getMessageForSRC_IN_IN_IS_NO_DECLARED_SOURCE(moduleFilterSpecifier.sourcePath),
					moduleFilterSpecifier, MODULE_FILTER_SPECIFIER__SOURCE_PATH, SRC_IN_IN_IS_NO_DECLARED_SOURCE)
			}
		}
	}

	/** Checks the library section. */
	@Check
	def checkLibrarySection(ProjectDescription projectDescription) {
		val libraryPaths = projectDescription.libraryPaths
		
		// check for existence of all library paths
		checkForExistingPaths(projectDescription, newHashMap(FolderType.LIBRARY -> libraryPaths))
		
		// ensure that there is only one library path
		if (libraryPaths.size > 1) {
			val message = getMessageForMULTIPLE_LIBRARY_PATHS
			val issueCode = MULTIPLE_LIBRARY_PATHS
			val feature = PROJECT_DESCRIPTION__LIBRARY_PATHS_RAW
			addIssue(message, projectDescription, feature, 1, issueCode)
		}
	}

	@Check
	def checkOutputDefined(ProjectDescription projectDescription) {
		if (projectDescription.outputPath === null) {
			val message = getMessageForNO_OUTPUT_FOLDER
			val issueCode = NO_OUTPUT_FOLDER
			val feature = PROJECT_DESCRIPTION__PROJECT_ID
			addIssue(message, projectDescription, feature, issueCode)
		}
	}

	/** Check all constraints related to properties defining API / implementation projects. */
	@Check
	def checkApiImplProperties(ProjectDescription projectDescription) {
		val implId = projectDescription.implementationId;
		val implProjects = projectDescription.implementedProjects;

		for (pref : implProjects) {
			if (pref?.projectId == projectDescription.projectId) {
				// reflexive implementation
				addIssue(
					messageForAPIIMPL_REFLEXIVE,
					pref,
					APIIMPL_REFLEXIVE
				);
			}
		}

		if (implId === null && !implProjects.empty) {
			// missing implementation ID
			addIssue(
				messageForAPIIMPL_MISSING_IMPL_ID,
				projectDescription,
				projectDescription_ImplementedProjects,
				APIIMPL_MISSING_IMPL_ID
			);
		}

		if (implId !== null && implProjects.empty) {
			// missing implemented projects
			addIssue(
				messageForAPIIMPL_MISSING_IMPL_PROJECTS,
				projectDescription,
				projectDescription_ImplementationId,
				APIIMPL_MISSING_IMPL_PROJECTS
			);
		}
	}

	/**
	 * Tells if for the given moduleSpecifier of the form "a/b/c/M" (without project ID) a module exists in the N4JS
	 * project represented by the given project description. Checks if a corresponding .js, .jsx, .n4js, .n4jsx, or .n4jsd file exists
	 * in any of the project's source containers.
	 */
	def private boolean isExistingModule(ProjectDescription pd, String moduleSpecifier) {
		val uri = pd.eResource.URI;
		val relativeModulePath = moduleSpecifier.replace('/', File.separator);
		val absoluteProjectPath = new File(pathProvider.getAbsoluteProjectPath(uri));
		val sourcePaths = pd.sourceContainers.map[paths].flatten.filterNull.map[replace('/', File.separator)];
		return sourcePaths.exists [ sp |
			val sourceFolder = new File(absoluteProjectPath, sp);
			return #[".n4js", ".n4jsx", ".n4jsd", ".js", ".jsx"].exists [ ext |
				new File(sourceFolder, relativeModulePath + ext).exists()
			];
		];
	}
}
