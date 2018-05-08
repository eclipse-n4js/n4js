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
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Platform
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.n4js.n4mf.ModuleFilter
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier
import org.eclipse.n4js.n4mf.ModuleFilterType
import org.eclipse.n4js.n4mf.N4mfPackage
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.SourceFragment
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

	@Check
	def void checkProjectDescription(ProjectDescription projectDescription) {
		val types = <Object>newArrayList
		val allPaths = <Object, List<String>>newHashMap
		val sourceTypes = projectDescription.sourceFragment
		val allSourcePaths = sourceTypes.toInvertedMap[paths]
		val pathsWhichMustExists = <EAttribute, List<String>>newHashMap
		types.addAll(sourceTypes)
		allPaths.putAll(allSourcePaths)
		val outputPathFeature = PROJECT_DESCRIPTION__OUTPUT_PATH_RAW
		types.add(outputPathFeature)
		// output path must not exist, usually not added to git and created on the fly, cf. IDEBUG-197
		allPaths.put(outputPathFeature,
			if (projectDescription.outputPath !== null) #[projectDescription.outputPath] else #[])
		val libraryPathFeature = PROJECT_DESCRIPTION__LIBRARY_PATHS_RAW
		types.add(libraryPathFeature)
		pathsWhichMustExists.put(libraryPathFeature, projectDescription.libraryPaths)
		val resourcesPathFeature = PROJECT_DESCRIPTION__RESOURCE_PATHS_RAW
		types.add(resourcesPathFeature)
		pathsWhichMustExists.put(resourcesPathFeature, projectDescription.resourcePaths)
		allPaths.putAll(pathsWhichMustExists)

		projectDescription.checkMainModule();

		projectDescription.checkForExistingPaths(pathsWhichMustExists)

		types.forEach [
			checkForDuplicatePaths(projectDescription, allPaths)
		]
	}

	/**
	 * Checks the <code>MainModule</code> property of the given project description for validity.
	 */
	def private void checkMainModule(ProjectDescription pd) {
		val mainModuleSpecifier = pd.mainModule;
		if (mainModuleSpecifier !== null) { // this property is optional, so null is fine!
			if (mainModuleSpecifier.empty || !isExistingModule(pd, mainModuleSpecifier)) {
				val specifierToShow = if (mainModuleSpecifier.empty) "<empty string>" else mainModuleSpecifier;
				addIssue(getMessageForNON_EXISTING_MODULE_SPECIFIER(specifierToShow), pd,
					PROJECT_DESCRIPTION__MAIN_MODULE, NON_EXISTING_MODULE_SPECIFIER);
			}
		}
	}

	def private checkForExistingPaths(ProjectDescription projectDescription,
		HashMap<EAttribute, List<String>> containerToPath) {
		val uri = projectDescription.eResource.URI
		val absoluteProjectPath = pathProvider.getAbsoluteProjectPath(uri)
		for (entry : containerToPath.entrySet) {
			for (path : entry.value) {
				val absolutePath = absoluteProjectPath + File.separator + path
				val file = new File(absolutePath)
				if (!file.exists || file.file) {
					val pathContainer = entry.key
					val messageFileDoesntExist = getMessageForNON_EXISTING_PATH(path)
					val messageFileNoFolder = getMessageForNO_FOLDER_PATH(path)
					val container = getContainer(pathContainer, projectDescription)
					val feature = getFeature(pathContainer, projectDescription)
					val index = getIndex(pathContainer, projectDescription, path)
					if (index == -2) {
						if (!file.exists) {
							addIssue(messageFileDoesntExist, container, feature, NON_EXISTING_PATH)
						} else {
							addIssue(messageFileNoFolder, container, feature, NO_FOLDER_PATH)
						}
					} else if (index > -1) {
						if (!file.exists) {
							addIssue(messageFileDoesntExist, container, feature, index, NON_EXISTING_PATH)
						} else {
							addIssue(messageFileNoFolder, container, feature, index, NO_FOLDER_PATH)
						}
					}
				}
			}
		}
	}

	@Check
	def void checkSourceFragment(SourceFragment sourceFragment) {
		sourceFragment.paths.forEach [
			checkForExistingPath(sourceFragment)
		]
		val paths = sourceFragment.paths
		checkForDuplicatePaths(sourceFragment, paths)
	}

	def checkForDuplicatePathsAmongContainers(SourceFragment sourceFragment, Map<SourceFragment, List<String>> paths) {
		val pathToContainer = <String, SourceFragment>newHashMap
		paths.entrySet.map[value -> key].forEach(e|e.key.forEach[pathToContainer.put(it, e.value)])
	}

	def checkForDuplicatePaths(Object pathContainer, ProjectDescription projectDescription,
		Map<Object, List<String>> paths) {
		val pathToContainer = <String, List<Object>>newHashMap
		paths.entrySet.map[value -> key].forEach(
			e |
				e.key.forEach [
					val grouped = pathToContainer.get(it) ?: newArrayList;
					grouped.add(e.value);
					pathToContainer.put(it, grouped)
				]
		)
		val duplicatePaths = pathToContainer.keySet.filter[pathToContainer.get(it).size > 1]
		val container = getContainer(pathContainer, projectDescription)
		val feature = getFeature(pathContainer, projectDescription)
		for (duplicatePath : duplicatePaths) {
			val duplicatePlaces = (pathToContainer.get(duplicatePath)).filter[it != pathContainer].map [
				switch (it) {
					SourceFragment:
						sourceFragmentType.getName
					EAttribute:
						switch (it) {
							case PROJECT_DESCRIPTION__RESOURCE_PATHS_RAW: "Resources"
							case PROJECT_DESCRIPTION__OUTPUT_PATH_RAW: "Output"
							case PROJECT_DESCRIPTION__LIBRARY_PATHS_RAW: "Libraries"
							default: it.name
						}
					default:
						it
				}
			]
			if (!duplicatePlaces.empty) {
				val message = getMessageForDUPLICATE_PATH(duplicatePlaces.map[toString].sort.join(", "))

				val index = getIndex(pathContainer, projectDescription, duplicatePath)
				if (index == -2) {
					addIssue(message, container, feature, DUPLICATE_PATH)
				} else if (index > -1) {
					addIssue(message, container, feature, index, DUPLICATE_PATH)
				}
			}
		}
	}

	private def getContainer(Object pathContainer, ProjectDescription projectDescription) {
		switch (pathContainer) {
			SourceFragment: pathContainer
			default: projectDescription
		}
	}

	private def getFeature(Object pathContainer, ProjectDescription projectDescription) {
		switch (pathContainer) {
			SourceFragment: SOURCE_FRAGMENT__PATHS_RAW
			EAttribute: pathContainer
		}
	}

	private def getIndex(Object pathContainer, ProjectDescription projectDescription, String path) {
		switch (pathContainer) {
			SourceFragment:
				pathContainer.paths.lastIndexOf(path)
			EAttribute:
				switch (pathContainer) {
					case PROJECT_DESCRIPTION__RESOURCE_PATHS_RAW:
						projectDescription.resourcePaths.lastIndexOf(path)
					case PROJECT_DESCRIPTION__OUTPUT_PATH_RAW:
						if (path.equals(projectDescription.outputPath)) -2 else -1
					case PROJECT_DESCRIPTION__LIBRARY_PATHS_RAW:
						projectDescription.libraryPaths.lastIndexOf(path)
					default:
						-1
				}
			default:
				-1
		}
	}

	private def checkForDuplicatePaths(SourceFragment sourceFragment, List<String> paths) {
		val duplicatePaths = getDuplicates(paths)
		duplicatePaths.forEach [
			addIssue(getMessageForDUPLICATE_PATH_INTERNAL, sourceFragment, SOURCE_FRAGMENT__PATHS_RAW,
				paths.indexOf(it.key), DUPLICATE_PATH_INTERNAL)
		]
	}

	private def getDuplicates(List<String> paths) {
		val groupedPaths = paths.groupBy[it]
		groupedPaths.entrySet.filter[value.size > 1]
	}

	private def getDuplicateModuleSpecifiers(Iterable<Pair<String, String>> paths) {
		val groupedPaths = paths.groupBy[it]
		groupedPaths.entrySet.filter[value.size > 1]
	}

	@Check
	def void checkOutputFolder(ProjectDescription projectDescription) {
		val outputPathName = projectDescription.outputPath;
		if (outputPathName === null) {
			return;
		}

		val outputPath = new Path(outputPathName);
		val sourceTypes = projectDescription.sourceFragment;

		for (SourceFragment sourceFrgmt : sourceTypes) {
			for (var i = 0; i < sourceFrgmt.paths.size; i++) {
				val sourcePathStr = sourceFrgmt.paths.get(i);
				val sourcePath = new Path(sourcePathStr);
				val srcFrgmtName = sourceFrgmt.sourceFragmentType.getName().toString;

				if (".".equals(sourcePathStr) || sourcePath.equals(outputPath) || sourcePath.isPrefixOf(outputPath)) {
					val containingFolder = "The output";
					val nestedFolder = "a " + srcFrgmtName;
					val message = getMessageForOUTPUT_AND_SOURCES_FOLDER_NESTING(containingFolder, nestedFolder);
					addIssue(message, projectDescription, PROJECT_DESCRIPTION__OUTPUT_PATH_RAW, OUTPUT_AND_SOURCES_FOLDER_NESTING);
				}

				if (outputPath.isPrefixOf(sourcePath)) {
					val containingFolder = "A " + srcFrgmtName;
					val nestedFolder = "the output";
					val message = getMessageForOUTPUT_AND_SOURCES_FOLDER_NESTING(containingFolder, nestedFolder);
					addIssue(message, sourceFrgmt, SOURCE_FRAGMENT__PATHS_RAW, i, OUTPUT_AND_SOURCES_FOLDER_NESTING);
				}
			}
		}
	}

	@Check
	def checkForDuplicateResources(ProjectDescription projectDescription) {
		val paths = projectDescription.resourcePaths
		val duplicatePaths = getDuplicates(paths)
		duplicatePaths.forEach [
			addIssue(getMessageForDUPLICATE_PATH_INTERNAL, projectDescription, PROJECT_DESCRIPTION__RESOURCE_PATHS_RAW,
				paths.lastIndexOf(it.key), DUPLICATE_PATH_INTERNAL)
		]
	}

	@Check
	def checkForDuplicateModuleSpecifiers(ModuleFilter moduleFilter) {
		val paths = moduleFilter.moduleSpecifiers.filter[sourcePath !== null].map [
			moduleSpecifierWithWildcard -> sourcePath
		].toList
		val filterWithOutExplicitSourceFolder = moduleFilter.moduleSpecifiers.filter[sourcePath === null]

		if (!filterWithOutExplicitSourceFolder.empty) {
			val sourcePaths = EcoreUtil2.getContainerOfType(moduleFilter, ProjectDescription).sourceFragment.map [
				it.paths
			].flatten.toSet
			for (spec : filterWithOutExplicitSourceFolder) {
				for (sourcePath : sourcePaths) {
					paths.add((spec.moduleSpecifierWithWildcard -> sourcePath))
				}
			}
		}
		val duplicatePaths = getDuplicateModuleSpecifiers(paths).map[key].map[key].toSet
		for (duplicatePath : duplicatePaths) {
			val moduleSpec = moduleFilter.moduleSpecifiers.findLast[moduleSpecifierWithWildcard == duplicatePath]
			addIssue(getMessageForDUPLICATE_MODULE_SPECIFIER, moduleSpec,
				MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD, DUPLICATE_MODULE_SPECIFIER)
		}

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
		val sourcePaths = EcoreUtil2.getContainerOfType(moduleFilterSpecifier, ProjectDescription).sourceFragment.map [
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

	def private checkForExistingPath(String path, SourceFragment sourceFragment) {
		if (path.contains("*") || path.contains("?")) {
			addIssue(getMessageForWILDCARD_NOT_ALLOWED, sourceFragment, SOURCE_FRAGMENT__PATHS_RAW,
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
			addIssue(getMessageForNON_EXISTING_SOURCE_PATH(path), sourceFragment, SOURCE_FRAGMENT__PATHS_RAW,
				sourceFragment.paths.indexOf(path), NON_EXISTING_SOURCE_PATH)
		}
		if (isGenerationAwareN4JSContainer(sourceFragment.sourceFragmentType)) {
			for (foundFile : foundFiles.filter[new File(it).file]) {
				addIssue(getMessageForNO_FOLDER_PATH(path), sourceFragment, SOURCE_FRAGMENT__PATHS_RAW,
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
				SIMPLE_PROJECT_DESCRIPTION__PROJECT_ID, PROJECT_NAME_MISMATCH)
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
					SIMPLE_PROJECT_DESCRIPTION__PROJECT_ID, PROJECT_NAME_ECLIPSE_MISMATCH)
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
			if (!projectDescription.sourceFragment.exists[paths.contains(moduleFilterSpecifier.sourcePath)]) {
				addIssue(getMessageForSRC_IN_IN_IS_NO_DECLARED_SOURCE(moduleFilterSpecifier.sourcePath),
					moduleFilterSpecifier, MODULE_FILTER_SPECIFIER__SOURCE_PATH, SRC_IN_IN_IS_NO_DECLARED_SOURCE)
			}
		}
	}

	@Check
	def checkOnlyOneLibraryPath(ProjectDescription projectDescription) {
		val libraryPaths = projectDescription.libraryPaths
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
			val feature = SIMPLE_PROJECT_DESCRIPTION__PROJECT_ID
			addIssue(message, projectDescription, feature, issueCode)
		}
	}

	/**
	 * Check all constraints related to properties defining API / implementation projects.
	 */
	@Check
	def checkApiImplProperties(ProjectDescription projectDescription) {
		val implId = projectDescription.implementationId;
		val implProjects = projectDescription.allImplementedProjects;

		for (pref : implProjects) {
			if (pref?.project?.projectId == projectDescription.projectId) {
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
				projectDescription.implementedProjects,
				implementedProjects_ImplementedProjects,
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
		val sourcePaths = pd.sourceFragment.map[paths].flatten.filterNull.map[replace('/', File.separator)];
		return sourcePaths.exists [ sp |
			val sourceFolder = new File(absoluteProjectPath, sp);
			return #[".n4js", ".n4jsx", ".n4jsd", ".js", ".jsx"].exists [ ext |
				new File(sourceFolder, relativeModulePath + ext).exists()
			];
		];
	}
}
