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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.n4mf.BootstrapModule;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.n4mf.ModuleFilterType;
import org.eclipse.n4js.n4mf.N4mfFactory;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.n4mf.VersionConstraint;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/*
 * NOTE:
 *
 * The following properties were verified to be unused in stdlib, fabelhaft, and OPR:
 * - ProjectReference#declaredVendorId
 * - ProjectDependency#versionConstraint (except in ProjectDescription#projectDependencies!!)
 * - ProjectDependency#declaredScope
 * - BootstrapModule#sourcePath
 * - libraryPathsRaw
 * - resourcePathsRaw
 *
 * Exceptions:
 * resourcePathsRaw used in: n4js-chrome
 *
 */

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
	/** Key of package.json property "main". */
	public static final String PROP__MAIN = "main";
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
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private PackageJsonHelper packageJsonHelper;

	/**
	 * Loads the project description of the N4JS project at the given {@code location}.
	 * <p>
	 * Returns {@code null} if the project description cannot be loaded successfully (e.g. missing package.json and
	 * manifest.n4mf file).
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(URI location) {
		JSONDocument packageJSON = loadPackageJSONAtLocation(location);
		return loadProjectDescriptionAtLocation(location, packageJSON, true);
	}

	/**
	 * Same as {@link #loadPackageJSONAtLocation(URI)}, but for cases in which the JSONDocument of the main package.json
	 * file (but not the fragment) has already been loaded.
	 *
	 * @param mergeFragment
	 *            if <code>true</code>, a {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON package.json fragment} will be loaded
	 *            and merged into the given package.json document before conversion to {@link ProjectDescription} (given
	 *            document will *not* be changed).
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(URI location, JSONDocument packageJSON,
			boolean mergeFragment) {
		if (mergeFragment) {
			packageJSON = EcoreUtil.copy(packageJSON);
			mergePackageJSONFragmentAtLocation(location, packageJSON);
		}
		adjustMainPathIfPointingToFolder(location, packageJSON);
		ProjectDescription pdFromPackageJSON = packageJSON != null
				? convertToProjectDescription(location, packageJSON, true)
				: null;
		if (pdFromPackageJSON != null) {
			return pdFromPackageJSON;
		}
		System.out.println("USING MANIFEST.N4MF: " + location);
		return loadManifestAtLocation(location);
		// ProjectDescription fromPackageJSON = loadPackageJSONAtLocation(location);
		// ProjectDescription fromManifest = loadManifestAtLocation(location);
		// ProjectDescription merged = mergeProjectDescriptions(fromPackageJSON, fromManifest);
		// return merged;
	}

	/**
	 * Loads the project description defined in a {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON package.json fragment} at the
	 * given location or <code>null</code> if no fragment is found at this location.
	 */
	public ProjectDescription loadProjectDescriptionFragmentAtLocation(URI location) {
		JSONDocument packageJSON = JSONFactory.eINSTANCE.createJSONDocument();
		if (mergePackageJSONFragmentAtLocation(location, packageJSON)) {
			adjustMainPathIfPointingToFolder(location, packageJSON);
			return convertToProjectDescription(location, packageJSON, false);
		}
		return null;
	}

	/**
	 * If the path value of the "main" property of the given package.json document points to a folder, then this method
	 * will append "/index.js" to that path value (in-place change of the given JSON document).
	 */
	private void adjustMainPathIfPointingToFolder(URI location, JSONDocument packageJSON) {
		JSONValue content = packageJSON.getContent();
		if (!(content instanceof JSONObject))
			return;
		JSONObject contentCasted = (JSONObject) content;
		String main = asStringOrNull(JSONModelUtils.getProperty(contentCasted, PROP__MAIN).orElse(null));
		if (main == null) {
			return;
		}
		String pattern = File.separatorChar != '/'
				? Pattern.quote(File.separator) + "|" + Pattern.quote("/")
				: Pattern.quote("/");
		String[] mainSegments = main.split(pattern, -1);
		URI locationWithMain = location.appendSegments(mainSegments);
		if (isDirectory(locationWithMain)) {
			if (!(main.endsWith("/") || main.endsWith(File.separator))) {
				main += "/";
			}
			main += "index.js";
			JSONModelUtils.setProperty(contentCasted, PROP__MAIN, main);
		}
	}

	private void applyDefaults(ProjectDescription pd, URI location) {
		// implementation note: we do not have to set the default for 'projectType' here,
		// because this default is already handled by EMF by defining VALIDATION as the
		// first literal of enum ProjectType in N4MF.xcore.
		if (pd.getProjectId() == null) {
			pd.setProjectId(location.lastSegment()); // name of folder containing the package.json file
		}
		if (pd.getProjectVersion() == null) {
			pd.setProjectVersion(parseVersion("0.0.1"));
		}
		if (pd.getVendorId() == null) {
			pd.setVendorId("vendor.default");
		}
		if (pd.getMainModule() == null) {
			pd.setMainModule("index");
		}
		if (pd.getOutputPathRaw() == null) {
			pd.setOutputPathRaw(".");
		}
		SourceContainerDescription scd = pd.getSourceContainers().stream()
				.filter(sc -> sc.getSourceContainerType() == SourceContainerType.SOURCE)
				.findFirst().orElse(null);
		if (scd == null) {
			SourceContainerDescription scdNew = N4mfFactory.eINSTANCE.createSourceContainerDescription();
			scdNew.setSourceContainerType(SourceContainerType.SOURCE);
			scdNew.getPathsRaw().add(".");
			pd.getSourceContainers().add(scdNew);
		} else if (scd.getPathsRaw().isEmpty()) {
			scd.getPathsRaw().add(".");
		}
	}

	private JSONDocument loadPackageJSONAtLocation(URI location) {
		JSONDocument packageJSON = loadXtextFileAtLocation(location, IN4JSProject.PACKAGE_JSON, JSONDocument.class);

		if (packageJSON == null) {
			packageJSON = loadXtextFileAtLocation(location,
					IN4JSProject.PACKAGE_JSON + "." + N4JSGlobals.XT_FILE_EXTENSION,
					JSONDocument.class);
		}

		return packageJSON;
	}

	/** Will change the given 'targetPackageJSON' document in place. */
	private boolean mergePackageJSONFragmentAtLocation(URI location, JSONDocument targetPackageJSON) {
		JSONDocument fragment = loadXtextFileAtLocation(location, N4JSGlobals.PACKAGE_FRAGMENT_JSON,
				JSONDocument.class);
		if (fragment == null) {
			return false;
		}
		if (fragment.getContent() instanceof JSONObject) {
			// note: dependencies are special in that we not actually merge (i.e. add) those from the fragment but
			// instead replace the dependencies in target. Rationale: the library manager is not interested in the
			// dependencies of the original project, only in those defined in the fragment.
			JSONValue targetContent = targetPackageJSON.getContent();
			if (targetContent instanceof JSONObject) {
				JSONModelUtils.removeProperty((JSONObject) targetContent, PROP__DEPENDENCIES);
				JSONModelUtils.removeProperty((JSONObject) targetContent, PROP__DEV_DEPENDENCIES);
			}
			// merge properties from fragment into targetPackageJSON
			JSONModelUtils.merge(targetPackageJSON, fragment, false, true);
		}
		return true;
	}

	private ProjectDescription loadManifestAtLocation(URI location) {
		return loadXtextFileAtLocation(location, IN4JSProject.N4MF_MANIFEST, ProjectDescription.class);
	}

	private <T extends EObject> T loadXtextFileAtLocation(URI location, String name, Class<T> expectedTypeOfRoot) {
		final T result;
		if (location.isPlatformResource() && location.segmentCount() == DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) {
			result = loadXtextFile(location.appendSegment(name), expectedTypeOfRoot);
		} else if (location.isFile()) {
			result = loadXtextFile(location.appendSegment(name), expectedTypeOfRoot);
		} else {
			// we only handle workspace and file-based cases
			return null;
		}
		return result;
	}

	private <T extends EObject> T loadXtextFile(URI uri, Class<T> expectedTypeOfRoot) {
		try {
			if (exists(uri)) {
				ResourceSet resourceSet = resourceSetProvider.get();
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
			// TODO Luca: I think this message is not valid anymore?
			throw new WrappedException("unexpected Xtext file URI: " + uri, e);
		}
	}

	/**
	 * Checks whether the given {@link URI} exists on the file system.
	 *
	 * This method can be used both for platform as well as file-based URIs.
	 */
	private boolean exists(URI uri) {
		// obtain file: based local URI
		final URI localURI = CommonPlugin.asLocalURI(uri);
		return new File(localURI.toFileString()).exists();
	}

	/**
	 * Same as {@link #exists(URI)}, but also checks if it is a directory.
	 */
	private boolean isDirectory(URI uri) {
		final URI localURI = CommonPlugin.asLocalURI(uri);
		return new File(localURI.toFileString()).isDirectory();
	}

	/**
	 * Transform the given {@code packageJSON} into an equivalent {@link ProjectDescription} instance.
	 * <p>
	 * Note: this methods does not implement the package.json feature that a "main" path may point to a folder and then
	 * a file "index.js" in that folder will be used as main module (for details see
	 * {@link ProjectDescriptionUtils#convertMainPathToModuleSpecifier(String, List)}).
	 */
	private ProjectDescription convertToProjectDescription(URI location, JSONDocument packageJSON,
			boolean applyDefaultValues) {
		JSONValue rootValue = packageJSON.getContent();
		if (rootValue instanceof JSONObject) {
			ProjectDescription result = N4mfFactory.eINSTANCE.createProjectDescription();
			List<NameValuePair> rootPairs = ((JSONObject) rootValue).getNameValuePairs();
			convertRootPairs(location, result, rootPairs, applyDefaultValues);
			return result;
		}
		return null;
	}

	private void convertRootPairs(URI location, ProjectDescription target, List<NameValuePair> rootPairs,
			boolean applyDefaultValues) {
		String valueOfTopLevelPropertyMain = null;
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
			case PROP__MAIN:
				// need to handle this value later after all source containers have been read (see below)
				valueOfTopLevelPropertyMain = asStringOrNull(value);
				break;
			case PROP__N4JS:
				convertN4jsPairs(target, asNameValuePairsOrEmpty(value));
				break;
			}
		}
		// set default values
		if (applyDefaultValues) {
			applyDefaults(target, location);
		}
		// sanitize and set value of top-level property "main"
		// (note: this makes use of the source containers, so it possibly relies on default values having been applied)
		if (valueOfTopLevelPropertyMain != null) {
			if (target.getMainModule() == null) { // only if no N4JS-specific "mainModule" property was given
				List<String> sourceContainerPaths = target.getSourceContainers().stream()
						.flatMap(scd -> scd.getPaths().stream())
						.collect(Collectors.toList());
				String mainModulePath = ProjectDescriptionUtils.convertMainPathToModuleSpecifier(
						valueOfTopLevelPropertyMain, sourceContainerPaths);
				if (mainModulePath != null) {
					target.setMainModule(mainModulePath);
				}
			}
		}
	}

	private void convertN4jsPairs(ProjectDescription target, List<NameValuePair> n4jsPairs) {
		for (NameValuePair pair : n4jsPairs) {
			String name = pair.getName();
			JSONValue value = pair.getValue();
			switch (name) {
			case PROP__PROJECT_TYPE:
				// parseProjectType returns null if value is invalid, this will
				// cause the setProjectType setter to use the default value of ProjectType.
				target.setProjectType(packageJsonHelper.parseProjectType(asStringOrNull(value)));
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
			case PROP__SOURCES:
				convertSourceContainers(target, value);
				break;
			case PROP__MODULE_FILTERS:
				convertModuleFilters(target, asNameValuePairsOrEmpty(value));
				break;
			case PROP__MAIN_MODULE:
				target.setMainModule(asStringOrNull(value));
				break;
			case PROP__TESTED_PROJECTS:
				target.getTestedProjects().addAll(toProjectDependencies(asProjectReferencesInArrayOrEmpty(value)));
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
				target.setModuleLoader(packageJsonHelper.parseModuleLoader(asStringOrNull(value)));
				break;
			case PROP__INIT_MODULES:
				target.getInitModules().addAll(asBootstrapModulesInArrayOrEmpty(value));
				break;
			case PROP__EXEC_MODULE:
				target.setExecModule(asBootstrapModuleOrNull(value));
				break;
			}
		}
	}

	private void convertDependencies(ProjectDescription target, List<NameValuePair> depPairs) {
		for (NameValuePair pair : depPairs) {
			String name = pair.getName();
			JSONValue value = pair.getValue();
			String valueStr = asStringOrNull(value);
			VersionConstraint versionConstraint = packageJsonHelper.parseVersionConstraint(valueStr);
			if (name != null && versionConstraint != null) {
				ProjectDependency dep = N4mfFactory.eINSTANCE.createProjectDependency();
				dep.setProjectId(name);
				if ("*".equals(valueStr) || "latest".equals(valueStr)) {
					dep.setVersionConstraint(null); // FIXME
				} else {
					dep.setVersionConstraint(versionConstraint);
				}
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
	private void convertSourceContainers(ProjectDescription target, JSONValue sourcesSection) {
		final List<SourceContainerDescription> sourceContainerDescriptions = packageJsonHelper
				.getSourceContainerDescriptions(sourcesSection);
		if (sourceContainerDescriptions != null) {
			target.getSourceContainers().addAll(sourceContainerDescriptions);
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
			ModuleFilterType type = packageJsonHelper.parseModuleFilterType(pair.getName());
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
		if (versionStr == null) {
			return null;
		}
		DeclaredVersion result = ProjectDescriptionUtils.parseVersion(versionStr);
		if (result == null) {
			System.err.println("WARNING: invalid or unsupported version: " + versionStr);
		}
		return result;
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

	private ProjectReference asProjectReferenceOrNull(JSONValue jsonValue) {
		String valueStr = asStringOrNull(jsonValue);
		if (valueStr != null) {
			final ProjectReference result = N4mfFactory.eINSTANCE.createProjectReference();
			result.setProjectId(valueStr);
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

	private BootstrapModule asBootstrapModuleOrNull(JSONValue jsonValue) {
		String valueStr = asStringOrNull(jsonValue);
		if (valueStr != null) {
			final BootstrapModule result = N4mfFactory.eINSTANCE.createBootstrapModule();
			result.setModuleSpecifierWithWildcard(valueStr);
			return result;
		}
		return null;
	}

	private List<BootstrapModule> asBootstrapModulesInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(this::asBootstrapModuleOrNull)
				.filter(boomod -> boomod != null)
				.collect(Collectors.toList());
	}

	@SuppressWarnings("unused")
	private List<String> asStringLiteralsInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(v -> (v instanceof JSONStringLiteral) ? ((JSONStringLiteral) v).getValue() : null)
				.filter(pref -> pref != null)
				.collect(Collectors.toList());
	}

	private List<ModuleFilterSpecifier> asModuleFilterSpecifierInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(packageJsonHelper::getModuleFilterSpecifier)
				.filter(mspec -> mspec != null)
				.collect(Collectors.toList());
	}

	private List<ProjectDependency> toProjectDependencies(Collection<? extends ProjectReference> prefs) {
		if (prefs == null) {
			return null;
		}
		List<ProjectDependency> result = new ArrayList<>(prefs.size());
		for (ProjectReference pref : prefs) {
			ProjectDependency pdep = N4mfFactory.eINSTANCE.createProjectDependency();
			pdep.setProjectId(pref.getProjectId());
			result.add(pdep);
		}
		return result;
	}

	// ******************************************************************************************
	// TODO remove the following method/class when removing legacy support for N4MF

	/**
	 * May return target or source unchanged or target with (in-place) changes.
	 */
	@SuppressWarnings("unused")
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
