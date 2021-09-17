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
package org.eclipse.n4js.resource

import com.google.common.collect.ImmutableMap
import com.google.inject.Inject
import java.util.Collection
import java.util.Collections
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONPackage
import org.eclipse.n4js.json.^extension.IJSONResourceDescriptionExtension
import org.eclipse.n4js.packagejson.PackageJsonUtils
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference
import org.eclipse.n4js.semver.model.SemverSerializer
import org.eclipse.n4js.utils.ProjectDescriptionLoader
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.util.IAcceptor

import static extension com.google.common.base.Strings.nullToEmpty
import org.eclipse.n4js.workspace.utils.SemanticDependencySupplier

/**
 * {@link IJSONResourceDescriptionExtension} implementation that provides custom resource descriptions of
 * {@code package.json} resources.
 */
class PackageJsonResourceDescriptionExtension implements IJSONResourceDescriptionExtension {

	/**
	 * Separator that is used to serialize multiple project identifiers as a string.
	 */
	private static val SEPARATOR = ";";

	/** The key of the user data for retrieving the project name. */
	public static val PROJECT_NAME_KEY = 'projectName';
	
	/** The key of the user data for retrieving the project version. */
	public static val PROJECT_VERSION_KEY = 'projectVersion';

	/** The key of the user data for retrieving the project type as a string. */
	private static val PROJECT_TYPE_KEY = 'projectType';

	/** Key for the project implementation ID value. {@code null} value will be mapped to empty string. */
	private static val IMPLEMENTATION_ID_KEY = 'implementationId';

	/**
	 * Key for storing the test project names.
	 * If a project does not have any tested projects this key will be missing from the user data.
	 * The values are separated with the {@link PackageJsonResourceDescriptionExtension#SEPARATOR} character.
	 */
	private static val TESTED_PROJECT_NAMES_KEY = 'testedProjectNames';

	/**
	 * Key for storing the implemented project names.
	 * If a project does not implement any projects this key will be missing from the user data.
	 * The values are separated with the {@link PackageJsonResourceDescriptionExtension#SEPARATOR} character.
	 */
	private static val IMPLEMENTED_PROJECT_NAMES_KEY = 'implementedProjectNames';

	/**
	 * Key for storing the project names of all direct dependencies.
	 * If a project does not have any direct projects this key will be missing from the user data.
	 * The values are separated with the {@link PackageJsonResourceDescriptionExtension#SEPARATOR} character.
	 */
	private static val PROJECT_DEPENDENCY_NAMES_KEY = 'projectDependencyNames';

	/**
	 * Key for storing the project names of all provided runtime libraries.
	 * If the project does not provide any runtime libraries, then this value will be omitted form the user data.
	 * Multiple values are separated with the {@link PackageJsonResourceDescriptionExtension#SEPARATOR} character.
	 */
	private static val PROVIDED_RUNTIME_LIBRARY_NAMES_KEY = 'providedRuntimeLibraryNames';

	/**
	 * Key for storing the project names of all required runtime libraries.
	 * If the project does not have any runtime library requirement, this value will not be present in the user data.
	 * Multiple values will be joined with the {@link PackageJsonResourceDescriptionExtension#SEPARATOR} separator.
	 */
	private static val REQUIRED_RUNTIME_LIBRARY_NAMES_KEY = 'requiredRuntimeLibraryNames';

	/**
	 * Key for storing the unique project identifier of the extended runtime environment. If the project does not
	 * extend any runtime environment, then this value will not exist in the user data.
	 */
	private static val EXTENDED_RUNTIME_ENVIRONMENT_NAME_KEY = 'extendedRuntimeEnvironmentName';

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

    private static final Logger LOGGER = Logger.getLogger(PackageJsonResourceDescriptionExtension);


	override QualifiedName getFullyQualifiedName(EObject obj) {
		if (!obj.isPackageJSON) {
			return null; // not responsible
		}

		// delegate to the N4JS qualified name provider
		// (will return non-null only for JSONDocument, i.e. the root AST node in JSON files)
		return qualifiedNameProvider.getFullyQualifiedName(obj);
	}


	override boolean isAffected(Collection<Delta> deltas, IResourceDescription candidate, IResourceDescriptions context) {
		if (!candidate.isPackageJSON) {
			return false; // not responsible
		}
		
		val changedProjectNames = deltas
			.filter[it.uri.isPackageJSON]
			.map[(if (it.getNew === null) it.old else it.getNew).exportedObjects]
			.filter[!it.empty]
			.map[it.get(0).getProjectName]
			.map[SemanticDependencySupplier.convertProjectIdToName(it)]
			.toSet;


		// Collect all referenced project IDs of the candidate.
		val referencedProjectNames = newLinkedList;
		candidate.getExportedObjectsByType(JSONPackage.Literals.JSON_DOCUMENT).forEach[
			referencedProjectNames.addAll(testedProjectNames);
			referencedProjectNames.addAll(implementedProjectNames);
			referencedProjectNames.addAll(projectDependencyNames);
			referencedProjectNames.addAll(providedRuntimeLibraryNames);
			referencedProjectNames.addAll(requiredRuntimeLibraryNames);
			val extRuntimeEnvironmentId = extendedRuntimeEnvironmentName;
			if (!extRuntimeEnvironmentId.nullOrEmpty) {
				referencedProjectNames.add(extRuntimeEnvironmentId);
			}
		];

		// Here we consider only direct project dependencies because this implementation is aligned to the
		// N4JS based resource description manager's #isAffected logic. In the N4JS implementation we consider
		// only direct project dependencies when checking whether a candidate is affected or not.
		//
		// See: N4JSResourceDescriptionManager#basicIsAffected and N4JSResourceDescriptionManager#hasDependencyTo
		for (referencedProjectName : referencedProjectNames) {
			if (changedProjectNames.contains(referencedProjectName)) {
				return true;
			}
		}

		return false;
	}

	override void createJSONDocumentDescriptions(JSONDocument document, IAcceptor<IEObjectDescription> acceptor) {
		val qualifiedName = getFullyQualifiedName(document);
		if (qualifiedName === null) {
			return; // not responsible
		}

		val projectLocation = document?.eResource?.URI?.trimSegments(1);
		if(projectLocation === null) {
			LOGGER.error("creation of EObjectDescriptions failed: cannot derive project location from document");
			return;
		}
		val description = projectDescriptionLoader.loadProjectDescriptionAtLocation(projectLocation, null, document);
		if(description === null) {
			// this can happen when package.json files are opened that do not belong to a valid N4JS or PLAINJS project
			// (maybe during manual creation of a new project); therefore we cannot log an error here:
			// LOGGER.error("creation of EObjectDescriptions failed: cannot load project description at location: " + projectLocation);
			return;
		}
		val userData = createProjectDescriptionUserData(description);
		acceptor.accept(new EObjectDescription(qualifiedName, document, userData));
	}

	/**
	 * Creates the user data of a {@link ProjectDescription} {@link IEObjectDescription}.
	 */
	private def Map<String, String> createProjectDescriptionUserData(ProjectDescription it) {
		val builder = ImmutableMap.builder;
		builder.put(PROJECT_TYPE_KEY, '''«PackageJsonUtils.getProjectTypeStringRepresentation(getType)»''');
		builder.put(PROJECT_NAME_KEY, id.nullToEmpty);
		builder.put(IMPLEMENTATION_ID_KEY, implementationId.nullToEmpty);

		val vers = getVersion;
		if (vers !== null) {
			val versionStr = SemverSerializer.serialize(vers);
			builder.put(PROJECT_VERSION_KEY, versionStr);
		}

		val testedProjects = it.testedProjects;
		if (!testedProjects.nullOrEmpty) {
			builder.put(PackageJsonResourceDescriptionExtension.TESTED_PROJECT_NAMES_KEY, testedProjects.asString);
		}

		val implementedProjects = it.implementedProjects;
		if (!implementedProjects.nullOrEmpty) {
			builder.put(IMPLEMENTED_PROJECT_NAMES_KEY, implementedProjects.asString);
		}

		val projectDependencies = it.projectDependencies;
		if (!projectDependencies.nullOrEmpty) {
			builder.put(PROJECT_DEPENDENCY_NAMES_KEY, projectDependencies.asString);
		}

		val providedRuntimeLibraries = providedRuntimeLibraries;
		if (!providedRuntimeLibraries.nullOrEmpty) {
			builder.put(PROVIDED_RUNTIME_LIBRARY_NAMES_KEY, providedRuntimeLibraries.asString);
		}

		val requiredRuntimeLibraries = requiredRuntimeLibraries;
		if (!requiredRuntimeLibraries.nullOrEmpty) {
			builder.put(REQUIRED_RUNTIME_LIBRARY_NAMES_KEY, requiredRuntimeLibraries.asString);
		}

		val extRuntimeEnvironment = it.extendedRuntimeEnvironment;
		if (extRuntimeEnvironment !== null) {
			builder.put(EXTENDED_RUNTIME_ENVIRONMENT_NAME_KEY, Collections.singleton(it.extendedRuntimeEnvironment).asString);
		}

		return builder.build;
	}

	/**
	 * Optionally returns with the project type extracted from the user data of the given EObject description argument.
	 */
	public static def getProjectType(IEObjectDescription it) {
		if (it === null) {
			return null;
		}
		val projectTypeStr = it.getUserData(PROJECT_TYPE_KEY);
		if (projectTypeStr === null) {
			return null;
		}
		return PackageJsonUtils.parseProjectType(projectTypeStr);
	}

	/**
	 * Optionally returns with the project name extracted from the user data of the given EObject description argument.
	 */
	public static def getProjectName(IEObjectDescription it) {
		if (it === null) {
			return null;
		}
		return it.getUserData(PROJECT_NAME_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the tested projects. Never returns with {@code null}.
	 */
	public static def getTestedProjectNames(IEObjectDescription it) {
		return getProjectNamesUserDataOf(PackageJsonResourceDescriptionExtension.TESTED_PROJECT_NAMES_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the implemented projects. Never returns with {@code null}.
	 */
	public static def getImplementedProjectNames(IEObjectDescription it) {
		return getProjectNamesUserDataOf(IMPLEMENTED_PROJECT_NAMES_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the project dependencies. Never returns with {@code null}.
	 */
	public static def getProjectDependencyNames(IEObjectDescription it) {
		return getProjectNamesUserDataOf(PROJECT_DEPENDENCY_NAMES_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the provided runtime libraries. Never returns with {@code null}.
	 */
	public static def getProvidedRuntimeLibraryNames(IEObjectDescription it) {
		return getProjectNamesUserDataOf(PROVIDED_RUNTIME_LIBRARY_NAMES_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the required runtime libraries. Never returns with {@code null}.
	 */
	public static def getRequiredRuntimeLibraryNames(IEObjectDescription it) {
		return getProjectNamesUserDataOf(REQUIRED_RUNTIME_LIBRARY_NAMES_KEY);
	}

	/**
	 * Returns with the ID of the extended runtime environment. May return with {@code null} if argument is {@code null}
	 * or if the value of the user data key is {@code null}. In a nutshell, if a project does not extend a RE.
	 */
	public static def getExtendedRuntimeEnvironmentName(IEObjectDescription it) {
		if (it === null) {
			return null;
		}
		return it.getUserData(EXTENDED_RUNTIME_ENVIRONMENT_NAME_KEY);
	}

	/**
	 * Returns with a collection of distinct project IDs extracted from the user data. Never returns with {@code null}.
	 */
	private static def getProjectNamesUserDataOf(IEObjectDescription it, String key) {
		if (it === null) {
			return emptySet;
		}
		return it.getUserData(key).nullToEmpty.split(SEPARATOR).filter[!nullOrEmpty].toSet;
	}

	private static def String asString(Iterable<? extends ProjectReference> it) {
		it.filterNull.map[getPackageName].filterNull.join(SEPARATOR)
	}

	private static def boolean isPackageJSON(IResourceDescription desc) {
		return desc !== null && isPackageJSON(desc.URI);
	}

	private static def boolean isPackageJSON(EObject obj) {
		return obj !== null && isPackageJSON(obj.eResource);
	}

	private static def boolean isPackageJSON(Resource res) {
		return res !== null && isPackageJSON(res.URI);
	}

	private static def boolean isPackageJSON(URI uri) {
		return uri !== null && uri.lastSegment == N4JSGlobals.PACKAGE_JSON;
	}
}
