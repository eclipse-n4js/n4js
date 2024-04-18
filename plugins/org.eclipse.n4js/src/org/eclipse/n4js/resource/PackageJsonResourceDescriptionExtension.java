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
package org.eclipse.n4js.resource;

import static com.google.common.base.Strings.nullToEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isNullOrEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.extension.IJSONResourceDescriptionExtension;
import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.SemanticDependencySupplier;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.util.IAcceptor;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.inject.Inject;

/**
 * {@link IJSONResourceDescriptionExtension} implementation that provides custom resource descriptions of
 * {@code package.json} resources.
 */
public class PackageJsonResourceDescriptionExtension implements IJSONResourceDescriptionExtension {

	/**
	 * Separator that is used to serialize multiple project identifiers as a string.
	 */
	private static String SEPARATOR = ";";

	/** The key of the user data for retrieving the project name. */
	public static String PROJECT_NAME_KEY = "projectName";

	/** The key of the user data for retrieving the project version. */
	public static String PROJECT_VERSION_KEY = "projectVersion";

	/** The key of the user data for retrieving the project type as a string. */
	private static String PROJECT_TYPE_KEY = "projectType";

	/** Key for the project implementation ID value. {@code null} value will be mapped to empty string. */
	private static String IMPLEMENTATION_ID_KEY = "implementationId";

	/**
	 * Key for storing the test project names. If a project does not have any tested projects this key will be missing
	 * from the user data. The values are separated with the {@link PackageJsonResourceDescriptionExtension#SEPARATOR}
	 * character.
	 */
	private static String TESTED_PROJECT_NAMES_KEY = "testedProjectNames";

	/**
	 * Key for storing the implemented project names. If a project does not implement any projects this key will be
	 * missing from the user data. The values are separated with the
	 * {@link PackageJsonResourceDescriptionExtension#SEPARATOR} character.
	 */
	private static String IMPLEMENTED_PROJECT_NAMES_KEY = "implementedProjectNames";

	/**
	 * Key for storing the project names of all direct dependencies. If a project does not have any direct projects this
	 * key will be missing from the user data. The values are separated with the
	 * {@link PackageJsonResourceDescriptionExtension#SEPARATOR} character.
	 */
	private static String PROJECT_DEPENDENCY_NAMES_KEY = "projectDependencyNames";

	/**
	 * Key for storing the project names of all provided runtime libraries. If the project does not provide any runtime
	 * libraries, then this value will be omitted form the user data. Multiple values are separated with the
	 * {@link PackageJsonResourceDescriptionExtension#SEPARATOR} character.
	 */
	private static String PROVIDED_RUNTIME_LIBRARY_NAMES_KEY = "providedRuntimeLibraryNames";

	/**
	 * Key for storing the project names of all required runtime libraries. If the project does not have any runtime
	 * library requirement, this value will not be present in the user data. Multiple values will be joined with the
	 * {@link PackageJsonResourceDescriptionExtension#SEPARATOR} separator.
	 */
	private static String REQUIRED_RUNTIME_LIBRARY_NAMES_KEY = "requiredRuntimeLibraryNames";

	/**
	 * Key for storing the unique project identifier of the extended runtime environment. If the project does not extend
	 * any runtime environment, then this value will not exist in the user data.
	 */
	private static String EXTENDED_RUNTIME_ENVIRONMENT_NAME_KEY = "extendedRuntimeEnvironmentName";

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	private static final Logger LOGGER = Logger.getLogger(PackageJsonResourceDescriptionExtension.class);

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (!isPackageJSON(obj)) {
			return null; // not responsible
		}

		// delegate to the N4JS qualified name provider
		// (will return non-null only for JSONDocument, i.e. the root AST node in JSON files)
		return qualifiedNameProvider.getFullyQualifiedName(obj);
	}

	@Override
	public boolean isAffected(Collection<Delta> deltas, IResourceDescription candidate, IResourceDescriptions context) {
		if (!isPackageJSON(candidate)) {
			return false; // not responsible
		}

		Set<String> changedProjectNames = new LinkedHashSet<>();

		for (Delta delta : deltas) {
			if ((delta.getNew() == null || delta.getOld() == null || delta.haveEObjectDescriptionsChanged())
					&& isPackageJSON(delta.getUri())) {

				Iterable<IEObjectDescription> exportedObjects = ((delta.getNew() == null)
						? delta.getOld()
						: delta.getNew()).getExportedObjects();

				Iterator<IEObjectDescription> iter = exportedObjects.iterator();
				if (iter.hasNext()) {
					String projectName = getProjectName(iter.next());
					String packageName = SemanticDependencySupplier.convertProjectIdToPackageName(projectName);
					changedProjectNames.add(packageName);
				}
			}
		}

		// Collect all referenced project IDs of the candidate.
		List<String> referencedProjectNames = new LinkedList<>();
		for (IEObjectDescription od : candidate.getExportedObjectsByType(JSONPackage.Literals.JSON_DOCUMENT)) {
			if (getProjectType(od) == ProjectType.PLAINJS) {
				// never rebuild plain-js projects on incremental builds
				// return false;
			}
			referencedProjectNames.addAll(getTestedProjectNames(od));
			referencedProjectNames.addAll(getImplementedProjectNames(od));
			referencedProjectNames.addAll(getProjectDependencyNames(od));
			referencedProjectNames.addAll(getProvidedRuntimeLibraryNames(od));
			referencedProjectNames.addAll(getRequiredRuntimeLibraryNames(od));
			String extRuntimeEnvironmentId = getExtendedRuntimeEnvironmentName(od);
			if (!Strings.isNullOrEmpty(extRuntimeEnvironmentId)) {
				referencedProjectNames.add(extRuntimeEnvironmentId);
			}
		}

		// Here we consider only direct project dependencies because this implementation is aligned to the
		// N4JS based resource description manager's #isAffected logic. In the N4JS implementation we consider
		// only direct project dependencies when checking whether a candidate is affected or not.
		//
		// See: N4JSResourceDescriptionManager#basicIsAffected and N4JSResourceDescriptionManager#hasDependencyTo
		for (String referencedProjectName : referencedProjectNames) {
			if (changedProjectNames.contains(referencedProjectName)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void createJSONDocumentDescriptions(JSONDocument document, IAcceptor<IEObjectDescription> acceptor) {
		QualifiedName qualifiedName = getFullyQualifiedName(document);
		if (qualifiedName == null) {
			return; // not responsible
		}

		URI projectLocation = document == null || document.eResource() == null || document.eResource().getURI() == null
				? null
				: document.eResource().getURI().trimSegments(1);
		if (projectLocation == null) {
			LOGGER.error("creation of EObjectDescriptions failed: cannot derive project location from document");
			return;
		}
		ProjectDescription description = projectDescriptionLoader
				.loadProjectDescriptionAtLocation(new FileURI(projectLocation), null, document);
		if (description == null) {
			// this can happen when package.json files are opened that do not belong to a valid N4JS or PLAINJS project
			// (maybe during manual creation of a new project); therefore we cannot log an error here:
			// LOGGER.error("creation of EObjectDescriptions failed: cannot load project description at location: " +
			// projectLocation);
			return;
		}
		Map<String, String> userData = createProjectDescriptionUserData(description);
		acceptor.accept(new EObjectDescription(qualifiedName, document, userData));
	}

	/**
	 * Creates the user data of a {@link ProjectDescription} {@link IEObjectDescription}.
	 */
	private Map<String, String> createProjectDescriptionUserData(ProjectDescription pd) {
		Builder<String, String> builder = ImmutableMap.builder();
		builder.put(PROJECT_TYPE_KEY, PackageJsonUtils.getProjectTypeStringRepresentation(pd.getProjectType()));
		builder.put(PROJECT_NAME_KEY, nullToEmpty(pd.getId()));
		builder.put(IMPLEMENTATION_ID_KEY, nullToEmpty(pd.getImplementationId()));

		VersionNumber vers = pd.getVersion();
		if (vers != null) {
			String versionStr = SemverSerializer.serialize(vers);
			builder.put(PROJECT_VERSION_KEY, versionStr);
		}

		List<ProjectReference> testedProjects = pd.getTestedProjects();
		if (!isNullOrEmpty(testedProjects)) {
			builder.put(PackageJsonResourceDescriptionExtension.TESTED_PROJECT_NAMES_KEY, asString(testedProjects));
		}

		List<ProjectReference> implementedProjects = pd.getImplementedProjects();
		if (!isNullOrEmpty(implementedProjects)) {
			builder.put(IMPLEMENTED_PROJECT_NAMES_KEY, asString(implementedProjects));
		}

		List<ProjectDependency> projectDependencies = pd.getProjectDependencies();
		if (!isNullOrEmpty(projectDependencies)) {
			builder.put(PROJECT_DEPENDENCY_NAMES_KEY, asString(projectDependencies));
		}

		List<ProjectReference> providedRuntimeLibraries = pd.getProvidedRuntimeLibraries();
		if (!isNullOrEmpty(providedRuntimeLibraries)) {
			builder.put(PROVIDED_RUNTIME_LIBRARY_NAMES_KEY, asString(providedRuntimeLibraries));
		}

		List<ProjectReference> requiredRuntimeLibraries = pd.getRequiredRuntimeLibraries();
		if (!isNullOrEmpty(requiredRuntimeLibraries)) {
			builder.put(REQUIRED_RUNTIME_LIBRARY_NAMES_KEY, asString(requiredRuntimeLibraries));
		}

		ProjectReference extRuntimeEnvironment = pd.getExtendedRuntimeEnvironment();
		if (extRuntimeEnvironment != null) {
			builder.put(EXTENDED_RUNTIME_ENVIRONMENT_NAME_KEY,
					asString(Collections.singleton(pd.getExtendedRuntimeEnvironment())));
		}

		return builder.build();
	}

	/**
	 * Optionally returns with the project type extracted from the user data of the given EObject description argument.
	 */
	public static ProjectType getProjectType(IEObjectDescription it) {
		if (it == null) {
			return null;
		}
		String projectTypeStr = it.getUserData(PROJECT_TYPE_KEY);
		if (projectTypeStr == null) {
			return null;
		}
		return PackageJsonUtils.parseProjectType(projectTypeStr);
	}

	/**
	 * Optionally returns with the project name extracted from the user data of the given EObject description argument.
	 */
	public static String getProjectName(IEObjectDescription it) {
		if (it == null) {
			return null;
		}
		return it.getUserData(PROJECT_NAME_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the tested projects. Never returns with {@code null}.
	 */
	public static Set<String> getTestedProjectNames(IEObjectDescription od) {
		return getProjectNamesUserDataOf(od, PackageJsonResourceDescriptionExtension.TESTED_PROJECT_NAMES_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the implemented projects. Never returns with {@code null}.
	 */
	public static Set<String> getImplementedProjectNames(IEObjectDescription od) {
		return getProjectNamesUserDataOf(od, IMPLEMENTED_PROJECT_NAMES_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the project dependencies. Never returns with {@code null}.
	 */
	public static Set<String> getProjectDependencyNames(IEObjectDescription od) {
		return getProjectNamesUserDataOf(od, PROJECT_DEPENDENCY_NAMES_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the provided runtime libraries. Never returns with {@code null}.
	 */
	public static Set<String> getProvidedRuntimeLibraryNames(IEObjectDescription od) {
		return getProjectNamesUserDataOf(od, PROVIDED_RUNTIME_LIBRARY_NAMES_KEY);
	}

	/**
	 * Returns with a collection of distinct IDs of the required runtime libraries. Never returns with {@code null}.
	 */
	public static Set<String> getRequiredRuntimeLibraryNames(IEObjectDescription od) {
		return getProjectNamesUserDataOf(od, REQUIRED_RUNTIME_LIBRARY_NAMES_KEY);
	}

	/**
	 * Returns with the ID of the extended runtime environment. May return with {@code null} if argument is {@code null}
	 * or if the value of the user data key is {@code null}. In a nutshell, if a project does not extend a RE.
	 */
	public static String getExtendedRuntimeEnvironmentName(IEObjectDescription od) {
		if (od == null) {
			return null;
		}
		return od.getUserData(EXTENDED_RUNTIME_ENVIRONMENT_NAME_KEY);
	}

	/**
	 * Returns with a collection of distinct project IDs extracted from the user data. Never returns with {@code null}.
	 */
	private static Set<String> getProjectNamesUserDataOf(IEObjectDescription it, String key) {
		if (it == null) {
			return Collections.emptySet();
		}
		return toSet(filter(Arrays.asList(nullToEmpty(it.getUserData(key)).split(SEPARATOR)),
				str -> !Strings.isNullOrEmpty(str)));
	}

	private static String asString(Iterable<? extends ProjectReference> it) {
		return org.eclipse.n4js.utils.Strings.join(SEPARATOR,
				filterNull(map(filterNull(it), pr -> pr.getPackageName())));
	}

	private static boolean isPackageJSON(IResourceDescription desc) {
		return desc != null && isPackageJSON(desc.getURI());
	}

	private static boolean isPackageJSON(EObject obj) {
		return obj != null && isPackageJSON(obj.eResource());
	}

	private static boolean isPackageJSON(Resource res) {
		return res != null && isPackageJSON(res.getURI());
	}

	private static boolean isPackageJSON(URI uri) {
		return uri != null && Objects.equals(uri.lastSegment(), N4JSGlobals.PACKAGE_JSON);
	}
}
