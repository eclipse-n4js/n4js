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
package org.eclipse.n4js.n4mf.resource

import com.google.common.collect.ImmutableMap
import com.google.inject.Singleton
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.ProjectReference
import org.eclipse.n4js.n4mf.ProjectType
import java.util.Collections
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.xtext.util.IAcceptor

import static extension com.google.common.base.Strings.nullToEmpty

/**
 * Customized resource description strategy for storing additional user data on the descriptions.
 */
@Singleton
class N4MFResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {

	/**
	 * Separator that is used to serialize multiple project identifiers as a string.
	 */
	public static val SEPARATOR = "/";

	/** The key of the user data for retrieving the project type as a string. */
	public static val PROJECT_TYPE_KEY = 'prjectType';

	/** The key of the user data for retrieving the project ID. */
	public static val PROJECT_ID_KEY = 'prjectId';
	
	/** The key of the user data for retrieving the project version. */
	public static val PROJECT_VERSION_KEY = 'prjectVersion';

	/** The key of the user data for retrieving the library dependencies for a particular project. */
	public static val LIB_DEPENDENCIES_KEY = 'libDependencies';

	/** Key for the project implementation ID value. {@code null} value will be mapped to empty string. */
	public static val IMPLEMENTATION_ID_KEY = 'implementationId';

	/**
	 * Key for storing the test project IDs.
	 * If a project does not have any tested projects this key will be missing from the user data.
	 * The values are separated with the {@link N4MFResourceDescriptionStrategy#SEPARATOR} character.
	 */
	public static val TESTED_PROJECT_IDS_KEY = 'testedProjectIds';

	/**
	 * Key for storing the implemented project IDs.
	 * If a project does not implement any projects this key will be missing from the user data.
	 * The values are separated with the {@link N4MFResourceDescriptionStrategy#SEPARATOR} character.
	 */
	public static val IMPLEMENTED_PROJECT_IDS_KEY = 'implementedProjectIds';

	/**
	 * Key for storing the project IDs of all direct dependencies.
	 * If a project does not have any direct projects this key will be missing from the user data.
	 * The values are separated with the {@link N4MFResourceDescriptionStrategy#SEPARATOR} character.
	 */
	public static val PROJECT_DEPENDENCY_IDS_KEY = 'projectDependencyIds';

	/**
	 * Key for storing the IDs of all provided runtime libraries.
	 * If the project does not provide any runtime libraries, then this value will be omitted form the user data.
	 * Multiple values are separated with the {@link N4MFResourceDescriptionStrategy#SEPARATOR} character.
	 */
	public static val PROVIDED_RUNTIME_LIBRARY_IDS_KEY = 'providedRuntimeLibraryIds';

	/**
	 * Key for storing the project IDs of all required runtime libraries.
	 * If the project does not have any runtime library requirement, this value will not be present in the user data.
	 * Multiple values will be joined with the {@link N4MFResourceDescriptionStrategy#SEPARATOR} separator.
	 */
	public static val REQUIRED_RUNTIME_LIBRARY_IDS_KEY = 'requiredRuntimeLibraryIds';

	/**
	 * Key for storing the unique project identifier of the extended runtime environment. If the project does not
	 * extend any runtime environment, then this value will not exist in the user data.
	 */
	public static val EXTENDED_RUNTIME_ENVIRONMENT_ID_KEY = 'extendedRuntimeEnvironmentId';

	override createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {

		if (eObject === null) {
			return false;
		}

		if (qualifiedNameProvider === null) {
			return false;
		}

		val qualifiedName = qualifiedNameProvider.getFullyQualifiedName(eObject);
		if (qualifiedName !== null) {
			val userData = eObject.userData;
			acceptor.accept(new EObjectDescription(qualifiedName, eObject, userData));
		}

		return true;
	}

	private dispatch def getUserData(EObject object) {
		Collections.<String, String>emptyMap;
	}

	private dispatch def getUserData(ProjectDescription it) {
		val builder = ImmutableMap.builder;
		builder.put(PROJECT_TYPE_KEY, '''«projectType»''');
		builder.put(PROJECT_ID_KEY, projectId.nullToEmpty);
		builder.put(IMPLEMENTATION_ID_KEY, implementationId.nullToEmpty);

		val vers = projectVersion;
		if (vers !== null) {
			val versionStr = '''«vers.major».«vers.minor».«vers.micro»''';
			val versionWithQualifierStr = if (vers.qualifier.nullOrEmpty)
					versionStr else '''«versionStr»:«vers.qualifier.nullToEmpty»''';

			builder.put(PROJECT_VERSION_KEY, versionWithQualifierStr);
		}

		val testedProjects = allTestedProjects;
		if (!testedProjects.nullOrEmpty) {
			builder.put(TESTED_PROJECT_IDS_KEY, testedProjects.asString);
		}

		val implementedProjects = allImplementedProjects;
		if (!implementedProjects.nullOrEmpty) {
			builder.put(IMPLEMENTED_PROJECT_IDS_KEY, implementedProjects.asString);
		}

		val projectDependencies = allProjectDependencies;
		if (!projectDependencies.nullOrEmpty) {
			builder.put(PROJECT_DEPENDENCY_IDS_KEY, projectDependencies.asString);
		}

		val providedRuntimeLibraries = allProvidedRuntimeLibraries;
		if (!providedRuntimeLibraries.nullOrEmpty) {
			builder.put(PROVIDED_RUNTIME_LIBRARY_IDS_KEY, providedRuntimeLibraries.asString);
		}

		val requiredRuntimeLibraries = allRequiredRuntimeLibraries;
		if (!requiredRuntimeLibraries.nullOrEmpty) {
			builder.put(REQUIRED_RUNTIME_LIBRARY_IDS_KEY, requiredRuntimeLibraries.asString);
		}

		val extRuntimeEnvironment = extendedRuntimeEnvironment?.extendedRuntimeEnvironment;
		if (extRuntimeEnvironment !== null) {
			builder.put(EXTENDED_RUNTIME_ENVIRONMENT_ID_KEY, Collections.singleton(extRuntimeEnvironment).asString);
		}

		return builder.build;
	}

	private def asString(Iterable<? extends ProjectReference> it) {
		map[project].filterNull.map[projectId].filterNull.join(SEPARATOR)
	}

	/**
	 * Optionally returns with the project type extracted from the user data of the given EObject description argument.
	 */
	static def getProjectType(IEObjectDescription it) {
		if (it === null) {
			return null;
		}
		val typeLiteral = it.getUserData(PROJECT_TYPE_KEY);
		if (typeLiteral === null) {
			return null;
		}
		return ProjectType.get(typeLiteral);
	}

	/**
	 * Optionally returns with the project project ID extracted from the user data of the given EObject description argument.
	 */
	static def getProjectId(IEObjectDescription it) {
		if (it === null) {
			return null;
		}
		return it.getUserData(PROJECT_ID_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the tested projects. Never returns with {@code null}.
	 */
	static def getTestedProjectIds(IEObjectDescription it) {
		return getProjectIdsUserDataOf(TESTED_PROJECT_IDS_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the implemented projects. Never returns with {@code null}.
	 */
	static def getImplementedProjectIds(IEObjectDescription it) {
		return getProjectIdsUserDataOf(IMPLEMENTED_PROJECT_IDS_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the project dependencies. Never returns with {@code null}.
	 */
	static def getProjectDependencyIds(IEObjectDescription it) {
		return getProjectIdsUserDataOf(PROJECT_DEPENDENCY_IDS_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the provided runtime libraries. Never returns with {@code null}.
	 */
	static def getProvidedRuntimeLibraryIds(IEObjectDescription it) {
		return getProjectIdsUserDataOf(PROVIDED_RUNTIME_LIBRARY_IDS_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the required runtime libraries. Never returns with {@code null}.
	 */
	static def getRequiredRuntimeLibraryIds(IEObjectDescription it) {
		return getProjectIdsUserDataOf(REQUIRED_RUNTIME_LIBRARY_IDS_KEY);
	}

	/**
	 * Returns with the ID of the extended runtime environment. May return with {@code null} if argument is {@code null}
	 * or if the value of the user data key is {@code null}. In a nutshell, if a project does not extend a RE.
	 */
	static def getExtendedRuntimeEnvironmentId(IEObjectDescription it) {
		if (it === null) {
			return null;
		}
		return it.getUserData(EXTENDED_RUNTIME_ENVIRONMENT_ID_KEY);
	}

	/**
	 * Returns with a collection of distinct project IDs extracted from the user data. Never returns with {@code null}.
	 */
	private static def getProjectIdsUserDataOf(IEObjectDescription it, String key) {
		if (it === null) {
			return emptySet;
		}
		return it.getUserData(key).nullToEmpty.split(SEPARATOR).toSet;
	}

}
