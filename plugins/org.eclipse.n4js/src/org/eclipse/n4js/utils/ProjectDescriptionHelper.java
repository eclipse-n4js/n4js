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
package org.eclipse.n4js.utils;

import static org.eclipse.n4js.internal.N4JSModel.DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.n4js.ArchiveURIUtil;
import org.eclipse.n4js.internal.FileBasedExternalPackageManager;
import org.eclipse.n4js.internal.LazyProjectDescriptionHandle;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.n4mf.ModuleFilterType;
import org.eclipse.n4js.n4mf.ModuleLoader;
import org.eclipse.n4js.n4mf.N4mfFactory;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.n4mf.VersionConstraint;
import org.eclipse.n4js.n4mf.utils.parsing.ManifestValuesParsingUtil;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Load a {@link ProjectDescription} from disk. For the moment, both package.json and the legacy manifest.n4mf are
 * considered.
 */
@Singleton
public class ProjectDescriptionHelper {

	// root-level properties:

	/** Key of package.json property "name". */
	public static final String PROP__NAME = "name";
	/** Key of package.json property "version". */
	public static final String PROP__VERSION = "version";
	/** Key of package.json property "dependencies". */
	public static final String PROP__DEPENDENCIES = "dependencies";
	/** Key of package.json property "devDependences". */
	public static final String PROP__DEV_DEPENDENCIES = "devDependencies";
	/** Key of package.json property "n4js". */
	public static final String PROP__N4JS = "n4js";

	// properties in section "n4js":

	/** Key of package.json property "projectType". */
	public static final String PROP__PROJECT_TYPE = "projectType";
	/** Key of package.json property "vendorId". */
	public static final String PROP__VENDOR_ID = "vendorId";
	/** Key of package.json property "vendorName". */
	public static final String PROP__VENDOR_NAME = "vendorName";
	/** Key of package.json property "output". */
	public static final String PROP__OUTPUT = "output";
	/** Key of package.json property "libraries". */
	public static final String PROP__LIBRARIES = "libraries";
	/** Key of package.json property "resources". */
	public static final String PROP__RESOURCES = "resources";
	/** Key of package.json property "sources". */
	public static final String PROP__SOURCES = "sources";
	/** Key of package.json property "moduleFilters". */
	public static final String PROP__MODULE_FILTERS = "moduleFilters";
	/** Key of package.json property "mainModule". */
	public static final String PROP__MAIN_MODULE = "mainModule";
	/** Key of package.json property "testedProjects". */
	public static final String PROP__TESTED_PROJECTS = "testedProjects";
	/** Key of package.json property "implementationId". */
	public static final String PROP__IMPLEMENTATION_ID = "implementationId";
	/** Key of package.json property "implementedProjects". */
	public static final String PROP__IMPLEMENTED_PROJECTS = "implementedProjects";
	/** Key of package.json property "extendedRuntimeEnvironment". */
	public static final String PROP__EXTENDED_RUNTIME_ENVIRONMENT = "extendedRuntimeEnvironment";
	/** Key of package.json property "providedRuntimeLibraries". */
	public static final String PROP__PROVIDED_RUNTIME_LIBRARIES = "providedRuntimeLibraries";
	/** Key of package.json property "requiredRuntimeLibraries". */
	public static final String PROP__REQUIRED_RUNTIME_LIBRARIES = "requiredRuntimeLibraries";
	/** Key of package.json property "moduleLoader". */
	public static final String PROP__MODULE_LOADER = "moduleLoader";
	/** Key of package.json property "initModules". */
	public static final String PROP__INIT_MODULES = "initModules";
	/** Key of package.json property "execModule". */
	public static final String PROP__EXEC_MODULE = "execModule";

	// properties of module filter specifiers:

	/** Key of package.json property "sourceContainer". */
	public static final String PROP__SOURCE_CONTAINER = "sourceContainer";
	/** Key of package.json property "module". */
	public static final String PROP__MODULE = "module";

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private IWorkspaceRoot workspace;

	/**
	 * TODO has to be aligned with
	 * <ul>
	 * <li>{@link FileBasedExternalPackageManager#loadManifest(URI)} and
	 * {@link FileBasedExternalPackageManager#loadManifestFromProjectRoot(URI)}
	 * <li>{@link LazyProjectDescriptionHandle#createProjectElementHandle()} and
	 * {@link LazyProjectDescriptionHandle#loadManifest(URI)}
	 * </ul>
	 * (for headless case and external libraries)
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(URI location) {
		ProjectDescription fromPackageJSON = loadPackageJSONAtLocation(location);
		if (fromPackageJSON != null) {
			return fromPackageJSON;
		}
		return loadManifestAtLocation(location);
		// ProjectDescription fromPackageJSON = loadPackageJSONAtLocation(location);
		// ProjectDescription fromManifest = loadManifestAtLocation(location);
		// ProjectDescription merged = mergeProjectDescriptions(fromPackageJSON, fromManifest);
		// return merged;
	}

	private ProjectDescription loadPackageJSONAtLocation(URI location) {
		JSONDocument packageJSON = loadXtextFileAtLocation(location, IN4JSProject.PACKAGE_JSON, JSONDocument.class);
		return packageJSON != null ? convertToProjectDescription(packageJSON) : null;
	}

	private ProjectDescription loadManifestAtLocation(URI location) {
		return loadXtextFileAtLocation(location, IN4JSProject.N4MF_MANIFEST, ProjectDescription.class);
	}

	private <T extends EObject> T loadXtextFileAtLocation(URI location, String name, Class<T> expectedTypeOfRoot) {
		final T result;
		if (location.isPlatformResource() && location.segmentCount() == DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) {
			result = loadXtextFile(location.appendSegment(name), expectedTypeOfRoot);
		} else {
			result = loadXtextFile(ArchiveURIUtil.createURI(location, name), expectedTypeOfRoot);
		}
		return result;
	}

	private <T extends EObject> T loadXtextFile(URI uri, Class<T> expectedTypeOfRoot) {
		try {
			String platformPath = uri.toPlatformString(true);
			if (uri.isArchive() || (platformPath != null && workspace.getFile(new Path(platformPath)).exists())) {
				ResourceSet resourceSet = n4jsCore.createResourceSet(Optional.absent());
				Resource resource = resourceSet.getResource(uri, true);
				if (resource != null) {
					List<EObject> contents = resource.getContents();
					if (!contents.isEmpty()) {
						EObject root = contents.get(0);
						if (expectedTypeOfRoot.isInstance(root)) {
							@SuppressWarnings("unchecked")
							final T rootCasted = (T) root;
							contents.clear();
							return rootCasted;
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new WrappedException("unexpected Xtext file URI: " + uri, e);
		}
	}

	private ProjectDescription convertToProjectDescription(JSONDocument packageJSON) {
		JSONValue rootValue = packageJSON.getContent();
		if (rootValue instanceof JSONObject) {
			ProjectDescription result = N4mfFactory.eINSTANCE.createProjectDescription();
			List<NameValuePair> rootPairs = ((JSONObject) rootValue).getNameValuePairs();
			if (!rootPairs.stream().map(p -> p.getName()).anyMatch(name -> PROP__N4JS.equals(name))) {
				// FIXME temporary: ignore all package.json that do not have an "n4js" property on top level
				return null;
			}
			convertRootPairs(result, rootPairs);
			return result;
		}
		return null;
	}

	private void convertRootPairs(ProjectDescription target, List<NameValuePair> rootPairs) {
		for (NameValuePair pair : rootPairs) {
			String name = pair.getName();
			JSONValue value = pair.getValue();
			switch (name) {
			case PROP__NAME:
				target.setProjectId(asStringOrNull(value));
				break;
			case PROP__VERSION:
				target.setProjectVersion(parseVersion(asStringOrNull(value)));
				break;
			case PROP__DEPENDENCIES:
				convertDependencies(target, asNameValuePairsOrEmpty(value));
				break;
			case PROP__DEV_DEPENDENCIES:
				// TODO consider separating normal from dev deps internally
				convertDependencies(target, asNameValuePairsOrEmpty(value));
				break;
			case PROP__N4JS:
				convertN4jsPairs(target, asNameValuePairsOrEmpty(value));
				break;
			}
		}
	}

	private void convertN4jsPairs(ProjectDescription target, List<NameValuePair> n4jsPairs) {
		for (NameValuePair pair : n4jsPairs) {
			String name = pair.getName();
			JSONValue value = pair.getValue();
			switch (name) {
			case PROP__PROJECT_TYPE:
				target.setProjectType(parseProjectType(asStringOrNull(value)));
				break;
			case PROP__VENDOR_ID:
				target.setVendorId(asStringOrNull(value));
				break;
			case PROP__VENDOR_NAME:
				target.setVendorName(asStringOrNull(value));
				break;
			case PROP__OUTPUT:
				target.setOutputPath(asStringOrNull(value));
				break;
			case PROP__LIBRARIES:
				// TODO libraryPathsRaw (probably obsolete)
				break;
			case PROP__RESOURCES:
				// TODO resourcePathsRaw (probably obsolete)
				break;
			case PROP__SOURCES:
				convertSourceContainers(target, asNameValuePairsOrEmpty(value));
				break;
			case PROP__MODULE_FILTERS:
				convertModuleFilters(target, asNameValuePairsOrEmpty(value));
				break;
			case PROP__MAIN_MODULE:
				target.setMainModule(asStringOrNull(value));
				break;
			case PROP__TESTED_PROJECTS:
				// TODO
				break;
			case PROP__IMPLEMENTATION_ID:
				target.setImplementationId(asStringOrNull(value));
				break;
			case PROP__IMPLEMENTED_PROJECTS:
				target.getImplementedProjects().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case PROP__EXTENDED_RUNTIME_ENVIRONMENT:
				target.setExtendedRuntimeEnvironment(asProjectReferenceOrNull(value));
				break;
			case PROP__PROVIDED_RUNTIME_LIBRARIES:
				target.getProvidedRuntimeLibraries().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case PROP__REQUIRED_RUNTIME_LIBRARIES:
				target.getRequiredRuntimeLibraries().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case PROP__MODULE_LOADER:
				target.setModuleLoader(parseModuleLoader(asStringOrNull(value)));
				break;
			case PROP__INIT_MODULES:
				// TODO
				break;
			case PROP__EXEC_MODULE:
				// TODO
				break;
			}
		}
	}

	private void convertDependencies(ProjectDescription target, List<NameValuePair> depPairs) {
		for (NameValuePair pair : depPairs) {
			String name = pair.getName();
			JSONValue value = pair.getValue();
			VersionConstraint versionConstraint = parseVersionConstraint(asStringOrNull(value));
			if (name != null && versionConstraint != null) {
				ProjectDependency dep = N4mfFactory.eINSTANCE.createProjectDependency();
				dep.setProjectId(name);
				dep.setVersionConstraint(versionConstraint);
				// FIXME Scope !!!!!!!!!!!!!!
				target.getProjectDependencies().add(dep);
			}
		}
	}

	/**
	 * Format:
	 *
	 * <pre>
	 * "sources": {
	 *     "source": [
	 *         "src1",
	 *         "src2"
	 *     ],
	 *     "external": [
	 *         "src-ext"
	 *     ]
	 * }
	 * </pre>
	 */
	private void convertSourceContainers(ProjectDescription target, List<NameValuePair> sourceContainerPairs) {
		for (NameValuePair pair : sourceContainerPairs) {
			SourceContainerType type = parseSourceContainerType(pair.getName());
			List<String> paths = asStringsInArrayOrEmpty(pair.getValue());
			if (type != null && !paths.isEmpty()) {
				SourceContainerDescription sourceContainerDescription = N4mfFactory.eINSTANCE
						.createSourceContainerDescription();
				sourceContainerDescription.setSourceContainerType(type);
				sourceContainerDescription.getPathsRaw().addAll(paths);
				target.getSourceContainers().add(sourceContainerDescription);
			}
		}
	}

	/**
	 * Format:
	 *
	 * <pre>
	 * "moduleFilters": {
	 *     "noValidate": [
	 *         "abc*",
	 *         ["src", "abc*"],
	 *         {
	 *             "sourceContainer": "src"
	 *             "module": "abc*",
	 *         }
	 *     ],
	 *     "noModuleWrap": [
	 *         // as above
	 *     ]
	 * }
	 * </pre>
	 */
	private void convertModuleFilters(ProjectDescription target, List<NameValuePair> moduleFilterPairs) {
		for (NameValuePair pair : moduleFilterPairs) {
			ModuleFilterType type = parseModuleFilterType(pair.getName());
			if (type != null) {
				List<ModuleFilterSpecifier> mspecs = asModuleFilterSpecifierInArrayOrEmpty(pair.getValue());
				if (!mspecs.isEmpty()) {
					ModuleFilter mfilter = N4mfFactory.eINSTANCE.createModuleFilter();
					mfilter.setModuleFilterType(type);
					mfilter.getModuleSpecifiers().addAll(mspecs);
					target.getModuleFilters().add(mfilter);
				}
			}
		}
	}

	private DeclaredVersion parseVersion(String versionStr) {
		return ManifestValuesParsingUtil.parseDeclaredVersion(versionStr).getAST();
	}

	private VersionConstraint parseVersionConstraint(String versionConstraintStr) {
		return ManifestValuesParsingUtil.parseVersionConstraint(versionConstraintStr).getAST();
	}

	private ProjectType parseProjectType(String projectTypeStr) {
		EEnumLiteral eLit = projectTypeStr != null
				? N4mfPackage.eINSTANCE.getProjectType().getEEnumLiteral(projectTypeStr)
				: null;
		return eLit != null
				? (ProjectType) eLit.getInstance()
				: (ProjectType) N4mfPackage.eINSTANCE.getProjectType().getDefaultValue();
	}

	private ModuleLoader parseModuleLoader(String moduleLoaderStr) {
		EEnumLiteral eLit = moduleLoaderStr != null
				? N4mfPackage.eINSTANCE.getModuleLoader().getEEnumLiteral(moduleLoaderStr)
				: null;
		return eLit != null
				? (ModuleLoader) eLit.getInstance()
				: (ModuleLoader) N4mfPackage.eINSTANCE.getModuleLoader().getDefaultValue();
	}

	private SourceContainerType parseSourceContainerType(String sourceContainerTypeStr) {
		EEnumLiteral eLit = sourceContainerTypeStr != null
				? N4mfPackage.eINSTANCE.getSourceContainerType().getEEnumLiteral(sourceContainerTypeStr)
				: null;
		return eLit != null
				? (SourceContainerType) eLit.getInstance()
				: (SourceContainerType) N4mfPackage.eINSTANCE.getSourceContainerType().getDefaultValue();
	}

	private ModuleFilterType parseModuleFilterType(String moduleFilterTypeStr) {
		EEnumLiteral eLit = moduleFilterTypeStr != null
				? N4mfPackage.eINSTANCE.getModuleFilterType().getEEnumLiteral(moduleFilterTypeStr)
				: null;
		return eLit != null
				? (ModuleFilterType) eLit.getInstance()
				: (ModuleFilterType) N4mfPackage.eINSTANCE.getModuleFilterType().getDefaultValue();
	}

	private String asStringOrNull(JSONValue jsonValue) {
		return jsonValue instanceof JSONStringLiteral ? ((JSONStringLiteral) jsonValue).getValue() : null;
	}

	private List<JSONValue> asArrayElementsOrEmpty(JSONValue jsonValue) {
		return jsonValue instanceof JSONArray ? ((JSONArray) jsonValue).getElements() : Collections.emptyList();
	}

	private List<NameValuePair> asNameValuePairsOrEmpty(JSONValue jsonValue) {
		return jsonValue instanceof JSONObject ? ((JSONObject) jsonValue).getNameValuePairs() : Collections.emptyList();
	}

	private List<String> asStringsInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(this::asStringOrNull)
				.filter(pref -> pref != null)
				.collect(Collectors.toList());
	}

	private ProjectReference asProjectReferenceOrNull(JSONValue jsonValue) {
		String valueStr = asStringOrNull(jsonValue);
		if (valueStr != null) {
			final ProjectReference result = N4mfFactory.eINSTANCE.createProjectReference();
			result.setProjectId(valueStr);
			// TODO do we need support for 'declaredVendorId' here?
			return result;
		}
		return null;
	}

	private List<ProjectReference> asProjectReferencesInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(this::asProjectReferenceOrNull)
				.filter(pref -> pref != null)
				.collect(Collectors.toList());
	}

	/**
	 * The following variants are supported:
	 *
	 * <pre>
	 * "abc*"
	 *
	 * ["src", "abc*"]
	 *
	 * {
	 *     "sourceContainer": "src"
	 *     "module": "abc*",
	 * }
	 * </pre>
	 */
	private ModuleFilterSpecifier asModuleFilterSpecifierOrNull(JSONValue jsonValue) {
		// 1st variant:
		String singleString = asStringOrNull(jsonValue);
		if (singleString != null) {
			return createModuleFilterSpecifier(null, singleString);
		}
		// 2nd variant:
		List<JSONValue> elements = asArrayElementsOrEmpty(jsonValue);
		if (elements.size() == 2) {
			String elem0 = asStringOrNull(elements.get(0));
			String elem1 = asStringOrNull(elements.get(1));
			if (elem0 != null && elem1 != null) {
				return createModuleFilterSpecifier(elem0, elem1);
			}
		}
		// 3rd variant:
		List<NameValuePair> pairs = asNameValuePairsOrEmpty(jsonValue);
		NameValuePair pathNVP = pairs.stream().filter(p -> PROP__SOURCE_CONTAINER.equals(p.getName())).findFirst()
				.orElse(null);
		NameValuePair moduleNVP = pairs.stream().filter(p -> PROP__MODULE.equals(p.getName())).findFirst().orElse(null);
		String pathStr = pathNVP != null ? asStringOrNull(pathNVP.getValue()) : null;
		String moduleStr = moduleNVP != null ? asStringOrNull(moduleNVP.getValue()) : null;
		if (moduleStr != null) { // pathStr may be null, i.e. "sourceContainer" is optional
			return createModuleFilterSpecifier(pathStr, moduleStr);
		}
		return null;
	}

	private List<ModuleFilterSpecifier> asModuleFilterSpecifierInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(this::asModuleFilterSpecifierOrNull)
				.filter(mspec -> mspec != null)
				.collect(Collectors.toList());
	}

	private ModuleFilterSpecifier createModuleFilterSpecifier(String sourcePath, String moduleSpecifierWithWildcard) {
		final ModuleFilterSpecifier result = N4mfFactory.eINSTANCE.createModuleFilterSpecifier();
		result.setSourcePath(sourcePath);
		result.setModuleSpecifierWithWildcard(moduleSpecifierWithWildcard);
		return result;

	}

	// ******************************************************************************************
	// TODO remove the following method/class when removing legacy support for N4MF

	/**
	 * May return target or source unchanged or target with (in-place) changes.
	 */
	private ProjectDescription mergeProjectDescriptions(ProjectDescription target, ProjectDescription source) {
		if (source == null) {
			return target; // covers case that both are null
		} else if (target == null) {
			return source;
		}
		// both are non-null
		// -> so copy values missing in 'target' from 'source' to 'target'
		// (and only if missing in 'target', because values in 'target' have priority)
		return new MergingCopier<>(target).merge(source);
	}

	private static final class MergingCopier<T extends EObject> extends Copier {

		private final T target;
		private EObject source;

		public MergingCopier(T target) {
			Objects.requireNonNull(target);
			this.target = target;
		}

		@SuppressWarnings("unchecked")
		public T merge(EObject eObject) {
			if (eObject.eClass() != target.eClass()) {
				throw new IllegalArgumentException("cannot merge source into target due to type mismatch");
			}
			this.source = eObject;
			T result = (T) super.copy(eObject);
			this.source = null;
			return result;
		}

		@Override
		protected EObject createCopy(EObject eObject) {
			if (eObject == source) {
				return target;
			}
			return super.createCopy(eObject);
		}

		@Override
		protected void copyAttribute(EAttribute eAttribute, EObject eObject, EObject copyEObject) {
			if (eObject == source && isDefinedInTarget(eAttribute)) {
				return;
			}
			super.copyAttribute(eAttribute, eObject, copyEObject);
		}

		@Override
		protected void copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
			if (eObject == source && isDefinedInTarget(eReference)) {
				return;
			}
			super.copyReference(eReference, eObject, copyEObject);
		}

		@Override
		protected void copyContainment(EReference eReference, EObject eObject, EObject copyEObject) {
			if (eObject == source && isDefinedInTarget(eReference)) {
				return;
			}
			super.copyContainment(eReference, eObject, copyEObject);
		}

		private boolean isDefinedInTarget(EStructuralFeature eFeature) {
			if (eFeature.isUnsettable() && !target.eIsSet(eFeature)) {
				return false;
			}
			return eFeature.isMany()
					? !((Collection<?>) target.eGet(eFeature)).isEmpty()
					: target.eGet(eFeature) != null;
		}
	}
}
