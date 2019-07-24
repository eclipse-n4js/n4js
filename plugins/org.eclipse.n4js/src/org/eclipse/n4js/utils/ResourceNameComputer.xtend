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
package org.eclipse.n4js.utils

import com.google.common.base.Strings
import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeDefs
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

import static org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import org.eclipse.n4js.semver.Semver.VersionNumber
import org.eclipse.n4js.projectModel.names.N4JSProjectName

/**
 * Helper class for computing descriptors for compiled files. Descriptors are used for file names and paths of generated files,
 * but also for meta data about them, e.g. for calculating import statements between compiled files or assembling code 
 * execution runtime paths.
 * 
 * Note that paths computed here are relative to the output folder of the specific compiler, for a specific project.
 * Callers interested in full project relative path need to call concrete compiler for more information.
 */
@Singleton
public final class ResourceNameComputer {
	/**
	 * https://github.com/eclipse/n4js/issues/394
	 * 
	 * for simplifying node js compilation target we want to avoid project name and version in the compiled code location segments.
	 * Instead we use node style specifiers that resolve to the project root. 
	 * Hide this behind the flag, as we anticipate that this needs to be configurable for other (than node.js) generators,
	 * or we might make this configurable in the manifest.
	 */
	private static final boolean MAKE_SIMPLE_DESCRIPTOR = true;

	/** 
	 * (pre-eclipse ticket)
	 * IDE-2069
	 * 
	 * as part of adding node.js support, version information was suppressed in the compiled code segments.
	 * Hidden behind feature flag, as we anticipate that this needs to be configurable for other (than node.js) generators,
	 * or we might make this configurable in the manifest.
	 */
	private static final boolean USE_PROJECT_VERSION = true

	/** 
	 * Default value for descriptors to be generated in a way that is safe to be used as identifiers in JS.
	 */
	private static final boolean AS_JS_IDENTIFIER = true

	@Inject private N4JSQualifiedNameProvider qualifiedNameProvider;
	@Inject private IQualifiedNameConverter converter;
	@Inject private ProjectResolveHelper projectResolver;

	/**
	 * The simple name of a declared type is the name which is used in its declaration. If type is anonymous, dummy name
	 * will be generated.
	 * <p>
	 * Example: <code>C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	def String getSimpleTypeName(Type type) {
		var String name = type.getName();
		if (name === null || name.isEmpty()) {
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
	def String getFullyQualifiedTypeName(Type type) {
		val EObject rootContainer = getRootContainer(type);
		if (rootContainer instanceof TypeDefs) {
			// 'type' is a built-in type
			return getSimpleTypeName(type);
		}
		var QualifiedName moduleFQN = qualifiedNameProvider.getFullyQualifiedName(rootContainer);
		if (N4TSQualifiedNameProvider.isModulePolyfill(moduleFQN)) {
			// IDE-1735 strip the extra ModulePolyfill-marker
			moduleFQN = moduleFQN.skipFirst(1);
		}
		return converter.toString(moduleFQN.append(getSimpleTypeName(type)));
	}

	/**
	 * Like {@link #getFullyQualifiedTypeName(Type)}, but uses "." instead of the correct delimiter.
	 * <p>
	 * <b>THIS IS ONLY INTENDED FOR LEGACY PURPOSES WHEN CREATING THE QUALIFIED NAMES FOR THE META-DATA (e.g. N4Class in
	 * transpiler, test catalog)!</b>
	 * <p>
	 * TODO IDE-2227 remove legacy support for old FQNs
	 */
	def String getFullyQualifiedTypeName_WITH_LEGACY_SUPPORT(Type type) {
		return getFullyQualifiedTypeName(type).replace(N4JSQualifiedNameConverter.DELIMITER, ".");
	}

	/**
	 * The versioned module specifier is used only internally. It is derived from the module specifier with the version
	 * (separated with a dash '-’) appended.
	 * <p>
	 * Based on provided file resource URI and extension will generate descriptor in form of
	 * Project-0.0.1/module/path/Module Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor} For
	 * delegation both project and unitPath are calculated from provided {@link TModule}.
	 * <p>
	 * Example: <code>project-1.0.0/p/C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 * 
	 * @module {@link TModule} for which we generate descriptor
	 */
	def String getCompleteModuleSpecifier(TModule module) {
		val project = resolveProject(module)
		val unitPath = module.getModuleSpecifier()
		return formatDescriptor(project, unitPath, "-", ".", "/", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER, MAKE_SIMPLE_DESCRIPTOR);
	}

	/**
	 * Based on provided URI generates project in form of Project-0.0.1
	 * Convenience method, delegates to {@link ResourceNameComputer#generateProjectDescriptor(IN4JSProject project)} with project
	 * derived from the URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 */
	def public String generateProjectDescriptor(URI n4jsSourceURI) {
		val project = projectResolver.resolveProject(n4jsSourceURI)
		val unitPath = ""
		formatDescriptor(project, unitPath, "-", ".", "", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER, !MAKE_SIMPLE_DESCRIPTOR);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project/file/path/File.js
	 * Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor}
	 * For delegation both project and unitPath are calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	def public String generateFileDescriptor(Resource resource, String fileExtension) {
		val project = projectResolver.resolveProject(resource)
		val unitPath = projectResolver.resolvePackageAndFileName(resource)
		formatDescriptor(project, unitPath, "-", ".", "/", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER, MAKE_SIMPLE_DESCRIPTOR) +
			normalizeFileExtension(fileExtension);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project/file/path/File.js
	 * Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor}
	 * For delegation both project and unitPath are calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	def public String generateFileDescriptor(URI n4jsSourceURI, String fileExtension) {
		val project = projectResolver.resolveProject(n4jsSourceURI)
		val unitPath = projectResolver.resolvePackageAndFileName(n4jsSourceURI)
		formatDescriptor(project, unitPath, "-", ".", "/", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER, MAKE_SIMPLE_DESCRIPTOR) +
			normalizeFileExtension(fileExtension);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project/file/path/File.js
	 * Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor}
	 * For delegation both project and unitPath are calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	def public String generateFileDescriptor(IN4JSProject project, URI n4jsSourceURI, String fileExtension) {
		val unitPath = projectResolver.resolvePackageAndFileName(n4jsSourceURI, project)
		formatDescriptor(project, unitPath, "-", ".", "/", !USE_PROJECT_VERSION, !AS_JS_IDENTIFIER, MAKE_SIMPLE_DESCRIPTOR) +
			normalizeFileExtension(fileExtension);
	}

	def private IN4JSProject resolveProject(TModule module) {
		return projectResolver.resolveProject(module.eResource().getURI());
	}

	/** Simple normalization of provided file extension to form {@code .abc} or empty string. */
	def private static String normalizeFileExtension(String fileExtension) {
		if (Strings.isNullOrEmpty(fileExtension))
			return ""

		if (fileExtension.startsWith("."))
			return fileExtension

		return "." + fileExtension;
	}

	/**
	 * Formats descriptor in form of
	 * <pre>
	 * projectName
	 *  + sep1 + Project.declaredVersion.getMajor
	 *  + sep2 + Project.declaredVersion.getMinor
	 *  + sep2 + Project.declaredVersion.getMinor
	 *  + sep3 + unitPath
	 * </pre>
	 * 
	 * @param project  used to resolve declared version and project name.
	 * @param unitPath  a path like string, can be derived from file Path, module specifier, or constructed manually.
	 * @param useProjectVersion  tells if project version should be included or not. If false, then sep1 and sep2
	 *                               will be ignored.
	 * @param asJsIdentifier  tells if segments must be in form of a valid JS identifier.
	 * @param makeSimpleDescriptor  tells if simple form of descriptor is to be used.
	 */
	def private static String formatDescriptor(IN4JSProject project, String unitPath, String sep1, String sep2,
		String sep3, boolean useProjectVersion, boolean asJsIdentifier, boolean makeSimpleDescriptor) {

		var projectName = project.projectName
		var path = unitPath
		if (asJsIdentifier) {
			projectName = new N4JSProjectName(getValidJavascriptIdentifierName(project.projectName.rawName))
			path = getValidUnitPath(unitPath)
		}

		if (makeSimpleDescriptor) {
			return path;
		}

		if (useProjectVersion) {
			return projectName + sep1 + projectVersionToStringWithoutQualifier(project.version, sep2) + sep3 + path;
		}

		return projectName + sep3 + path;
	}

	/** Ensures that all parts of the unit path are valid JS identifiers */
	def private static String getValidUnitPath(String unitPath) {
		return unitPath.split('/').map[getValidJavascriptIdentifierName].join('/');
	}

	/** Transforms given input string to form that can be used as JS identifier. Assumes that Java identifiers are valid JavaScript
	 * identifiers, hence it actually just checks Java identifier validity and does not dive into JS specifics. Uses {@link Character#isJavaIdentifierStart}
	 * and {@link Character#isJavaIdentifierPart} for heavy validity checks. Invalid characters are transformed to Unicode equivalents, see {@link #toUnicode}.
	 * Valid inputs are returned unchanged.*/
	def private static String getValidJavascriptIdentifierName(String input) {
		if (input === null || input.length === 0) {
			return input;
		}
		val sb = new StringBuilder();
		for (var int i = 0; i < input.length; i++) {
			val ch = input.charAt(i);
			val isValid = if (i === 0) Character.isJavaIdentifierStart(ch) else Character.isJavaIdentifierPart(ch);
			if (isValid) {
				sb.append(ch);
			} else {
				sb.append(toUnicode(ch));
			}
		}
		return sb.toString();
	}

	def private static toUnicode(char character) {
		return "_u" + Integer.toHexString(character.bitwiseOr(0x10000)).substring(1);
	}

	/** Transforms the version into a string used for variable, parameter, and file names. */
	def private static projectVersionToStringWithoutQualifier(VersionNumber declaredVersion, String separatorChar) {
		return declaredVersion.getMajor + separatorChar + declaredVersion.getMinor + separatorChar +
			declaredVersion.getPatch;
	}

}
