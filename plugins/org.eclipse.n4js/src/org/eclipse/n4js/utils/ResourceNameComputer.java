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
package org.eclipse.n4js.utils;

import static org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.scoping.utils.PolyfillUtils;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.IntegerExtensions;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper class for computing descriptors for compiled files. Descriptors are used for file names and paths of generated
 * files, but also for meta data about them, e.g. for calculating import statements between compiled files or assembling
 * code execution runtime paths.
 *
 * Note that paths computed here are relative to the output folder of the specific compiler, for a specific project.
 * Callers interested in full project relative path need to call concrete compiler for more information.
 */
@Singleton
public final class ResourceNameComputer {
	/**
	 * https://github.com/eclipse/n4js/issues/394
	 *
	 * for simplifying node js compilation target we want to avoid project name and version in the compiled code
	 * location segments. Instead we use node style specifiers that resolve to the project root. Hide this behind the
	 * flag, as we anticipate that this needs to be configurable for other (than node.js) generators, or we might make
	 * this configurable in the manifest.
	 */
	private static final boolean MAKE_SIMPLE_DESCRIPTOR = true;

	/**
	 * (pre-eclipse ticket) IDE-2069
	 *
	 * as part of adding node.js support, version information was suppressed in the compiled code segments. Hidden
	 * behind feature flag, as we anticipate that this needs to be configurable for other (than node.js) generators, or
	 * we might make this configurable in the manifest.
	 */
	private static final boolean USE_PROJECT_VERSION = true;

	/**
	 * Default value for descriptors to be generated in a way that is safe to be used as identifiers in JS.
	 */
	private static final boolean AS_JS_IDENTIFIER = true;

	@Inject
	private N4JSQualifiedNameProvider qualifiedNameProvider;
	@Inject
	private IQualifiedNameConverter converter;
	@Inject
	private ProjectResolveHelper projectResolver;

	/**
	 * The simple name of a declared type is the name which is used in its declaration. If type is anonymous, dummy name
	 * will be generated.
	 * <p>
	 * Example: <code>C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	public String getSimpleTypeName(Type type) {
		String name = type.getName();
		if (name == null || name.isEmpty()) {
			name = "__Anonymous_" + type.hashCode();
		}
		return name;
	}

	/**
	 * The fully qualified name (FQN) of a declared type is its simple name, prefixed by the fully qualified module name
	 * it is defined in.
	 * <p>
	 * Example: <code>p/C/C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	public String getFullyQualifiedTypeName(Type type) {
		EObject rootContainer = getRootContainer(type);
		QualifiedName moduleFQN = qualifiedNameProvider.getFullyQualifiedName(rootContainer);
		if (PolyfillUtils.isModulePolyfill(moduleFQN)) {
			// IDE-1735 strip the extra ModulePolyfill-marker
			moduleFQN = moduleFQN.skipFirst(1);
		}
		return converter.toString(moduleFQN.append(getSimpleTypeName(type)));
	}

	/**
	 * The versioned module specifier is used only internally. It is derived from the module specifier with the version
	 * (separated with a dash '-’) appended.
	 * <p>
	 * Based on provided file resource URI and extension will generate descriptor in form of
	 * Project-0.0.1/module/path/Module Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor}
	 * For delegation both project and unitPath are calculated from provided {@link TModule}.
	 * <p>
	 * Example: <code>project-1.0.0/p/C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 *
	 * @module {@link TModule} for which we generate descriptor
	 */
	public String getCompleteModuleSpecifier(TModule module) {
		N4JSProjectConfigSnapshot project = resolveProject(module);
		String unitPath = module.getModuleSpecifier();
		return formatDescriptor(project, unitPath, "-", ".", "/", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER,
				MAKE_SIMPLE_DESCRIPTOR);
	}

	/**
	 * Based on provided URI generates project in form of Project-0.0.1.
	 *
	 * @n4jsSourceURI URI from file resource
	 */
	public String generateProjectDescriptor(Resource resource) {
		if (N4Scheme.isN4Scheme(resource.getURI())) {
			return N4JSGlobals.N4JS_RUNTIME.getRawName();
		}
		N4JSProjectConfigSnapshot project = projectResolver.resolveProject(resource);
		String unitPath = "";
		return formatDescriptor(project, unitPath, "-", ".", "", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER,
				!MAKE_SIMPLE_DESCRIPTOR);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of
	 * Project/file/path/File.js Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor} For
	 * delegation both project and unitPath are calculated from provided URI.
	 *
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	public String generateFileDescriptor(Resource resource, String fileExtension) {
		N4JSProjectConfigSnapshot project = projectResolver.resolveProject(resource);
		String unitPath = projectResolver.resolvePackageAndFileName(resource);
		return formatDescriptor(project, unitPath, "-", ".", "/", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER,
				MAKE_SIMPLE_DESCRIPTOR) +
				normalizeFileExtension(fileExtension);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of
	 * Project/file/path/File.js Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor} For
	 * delegation both project and unitPath are calculated from provided URI.
	 *
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	public String generateFileDescriptor(Notifier context, URI n4jsSourceURI, String fileExtension) {
		N4JSProjectConfigSnapshot project = projectResolver.resolveProject(context, n4jsSourceURI);
		String unitPath = projectResolver.resolvePackageAndFileName(context, n4jsSourceURI);
		return formatDescriptor(project, unitPath, "-", ".", "/", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER,
				MAKE_SIMPLE_DESCRIPTOR) +
				normalizeFileExtension(fileExtension);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of
	 * Project/file/path/File.js Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor} For
	 * delegation both project and unitPath are calculated from provided URI.
	 *
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	public String generateFileDescriptor(N4JSProjectConfigSnapshot project, URI n4jsSourceURI, String fileExtension) {
		String unitPath = projectResolver.resolvePackageAndFileName(n4jsSourceURI, project);
		return formatDescriptor(project, unitPath, "-", ".", "/", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER,
				MAKE_SIMPLE_DESCRIPTOR) +
				normalizeFileExtension(fileExtension);
	}

	private N4JSProjectConfigSnapshot resolveProject(TModule module) {
		return projectResolver.resolveProject(module.eResource());
	}

	/** Simple normalization of provided file extension to form {@code .abc} or empty string. */
	private static String normalizeFileExtension(String fileExtension) {
		if (Strings.isNullOrEmpty(fileExtension)) {
			return "";
		}

		if (fileExtension.startsWith(".")) {
			return fileExtension;
		}

		return "." + fileExtension;
	}

	/**
	 * Formats descriptor in form of
	 *
	 * <pre>
	 * projectName
	 * 		+ sep1 + Project.declaredVersion.getMajor
	 * 		+ sep2 + Project.declaredVersion.getMinor
	 * 		+ sep2 + Project.declaredVersion.getMinor
	 * 		+ sep3 + unitPath
	 * </pre>
	 *
	 * @param project
	 *            used to resolve declared version and project name.
	 * @param unitPath
	 *            a path like string, can be derived from file Path, module specifier, or constructed manually.
	 * @param useProjectVersion
	 *            tells if project version should be included or not. If false, then sep1 and sep2 will be ignored.
	 * @param asJsIdentifier
	 *            tells if segments must be in form of a valid JS identifier.
	 * @param makeSimpleDescriptor
	 *            tells if simple form of descriptor is to be used.
	 */
	private static String formatDescriptor(N4JSProjectConfigSnapshot project, String unitPath, String sep1, String sep2,
			String sep3, boolean useProjectVersion, boolean asJsIdentifier, boolean makeSimpleDescriptor) {

		String packageName = project.getPackageName() == null ? project.getName() : project.getPackageName();
		String path = unitPath;
		if (asJsIdentifier) {
			packageName = getValidJavascriptIdentifierName(packageName);
			path = getValidUnitPath(unitPath);
		}

		if (makeSimpleDescriptor) {
			return path;
		}

		if (useProjectVersion) {
			return packageName + sep1 + projectVersionToStringWithoutQualifier(project.getVersion(), sep2) + sep3
					+ path;
		}

		return packageName + sep3 + path;
	}

	/** Ensures that all parts of the unit path are valid JS identifiers */
	private static String getValidUnitPath(String unitPath) {
		if (unitPath == null) {
			return "";
		}
		String result = "";
		for (String seg : unitPath.split("/")) {
			if (!result.isEmpty()) {
				result += "/";
			}
			result += getValidJavascriptIdentifierName(seg);
		}

		return result;
	}

	/**
	 * Transforms given input string to form that can be used as JS identifier. Assumes that Java identifiers are valid
	 * JavaScript identifiers, hence it actually just checks Java identifier validity and does not dive into JS
	 * specifics. Uses {@link Character#isJavaIdentifierStart} and {@link Character#isJavaIdentifierPart} for heavy
	 * validity checks. Invalid characters are transformed to Unicode equivalents, see {@link #toUnicode}. Valid inputs
	 * are returned unchanged.
	 */
	private static String getValidJavascriptIdentifierName(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			boolean isValid = (i == 0) ? Character.isJavaIdentifierStart(ch) : Character.isJavaIdentifierPart(ch);
			if (isValid) {
				sb.append(ch);
			} else {
				sb.append(toUnicode(ch));
			}
		}
		return sb.toString();
	}

	private static String toUnicode(char character) {
		return "_u" + Integer.toHexString(IntegerExtensions.bitwiseOr(character, 0x10000)).substring(1);
	}

	/** Transforms the version into a string used for variable, parameter, and file names. */
	private static String projectVersionToStringWithoutQualifier(VersionNumber declaredVersion, String separatorChar) {
		return declaredVersion.getMajor() + separatorChar + declaredVersion.getMinor() + separatorChar +
				declaredVersion.getPatch();
	}

}
