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
package org.eclipse.n4js.projectModel

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4mf.DeclaredVersion
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider
import org.eclipse.n4js.naming.SpecifierConverter
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeDefs
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

import static org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import com.google.common.base.Strings
import org.eclipse.emf.ecore.resource.Resource

/**
 * Helper class for computing descriptors for compiled files. Descriptors are used for file names and paths of generated files,
 * but also for meta data about them, e.g. for calculating import statements between compiled files or assembling code 
 * execution runtime paths.
 * 
 * Note that paths computed here are relative to the output folder of the specific compiler, for a specific project.
 * Callers interested in full project relative path need to call concrete compiler for more information.
 */
@Singleton
public class ResourceNameComputer {
	/** 
	 * https://github.com/eclipse/n4js/issues/394
	 * 
	 * for simplifying node js compilation target we want to avoid project name and version in the compiled code location segments.
	 * Instead we use node style specifiers that resolve to the project root. 
	 * Hide this behind the flag, as we anticipate that this needs to be configurable for other (than node.js) generators,
	 * or we might make this configurable in the manifest.
	 */
	public static final boolean USE_NODE_DESCRIPTOR = true;
	
	/** 
	 * (pre-eclipse ticket)
	 * IDE-2069
	 * 
	 * as part of adding node.js support, version information was suppressed in the compiled code segments.
	 * Hidden behind feature flag, as we anticipate that this needs to be configurable for other (than node.js) generators,
	 * or we might make this configurable in the manifest.
	 */
	public static final boolean USE_VERSIONS = false
	
	@Inject private N4JSQualifiedNameProvider qualifiedNameProvider;
	@Inject private IQualifiedNameConverter converter;
	@Inject private SpecifierConverter specifierConverter;
	@Inject private ProjectResolveHelper projectResolver;

	/**
	 * Based on provided URI generates project in form of Project-0.0.1
	 * Convenience method, delegates to {@link ResourceNameComputer#generateProjectDescriptor(IN4JSProject project)} with project
	 * derived from the URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 */
	def generateProjectDescriptor(URI n4jsSourceURI) {
		formatDescriptor(projectResolver.resolveProject(n4jsSourceURI), "", "-", ".", "", USE_VERSIONS, false, false);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project-0.0.1
	 * Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor}
	 * For delegation project is calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 */
	def generateProjectDescriptor(IN4JSProject project) {
		formatDescriptor(project, "", "-", ".", "", USE_VERSIONS, false, false);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project/file/path/File.js
	 * Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor}
	 * For delegation both project and unitPath are calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	def generateFileDescriptor(Resource resource, String fileExtension) {
		formatDescriptor(projectResolver.resolveProject(resource),
			projectResolver.resolvePackageAndFileName(resource), "-", ".", "/", USE_VERSIONS, false, USE_NODE_DESCRIPTOR) + normalizeFileExtension(fileExtension);
	}
	
	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project/file/path/File.js
	 * Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor}
	 * For delegation both project and unitPath are calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	def generateFileDescriptor(URI n4jsSourceURI, String fileExtension) {
		formatDescriptor(projectResolver.resolveProject(n4jsSourceURI),
			projectResolver.resolvePackageAndFileName(n4jsSourceURI), "-", ".", "/", USE_VERSIONS, false, USE_NODE_DESCRIPTOR) + normalizeFileExtension(fileExtension);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project/file/path/File.js
	 * Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor}
	 * For delegation both project and unitPath are calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	def generateFileDescriptor(IN4JSProject project, URI n4jsSourceURI, String fileExtension) {
		formatDescriptor(project, projectResolver.resolvePackageAndFileName(n4jsSourceURI, project), "-", ".", "/",
			USE_VERSIONS, false, USE_NODE_DESCRIPTOR) + normalizeFileExtension(fileExtension);
	}
	private def String normalizeFileExtension(String fileExtension){
		if(Strings.isNullOrEmpty(fileExtension))
			return ""
		
		if(fileExtension.startsWith("."))
			return fileExtension

		return "." + fileExtension;
	}

	/**
	 * Formats descriptor in form of
	 * <pre>
	 * projectId
	 *  + sep1 + Project.declaredVersion.getMajor
	 *  + sep2 + Project.declaredVersion.getMinor
	 *  + sep2 + Project.declaredVersion.getMinor
	 *  + sep3 + unitPath
	 * </pre>
	 * 
	 * @param project  used to resolve declared version and project name.
	 * @param unitPath  a path like string, can be derived from file Path, module specifier, or constructed manually.
	 * @param includeProjectVersion  tells if project version should be included or not. If false, then sep1 and sep2
	 *                               will be ignored.
	 * @param asJsIdentifier  tells if segments must be in form of a valid JS identifier.
	 * @param makeSimpleDescriptor  tells if simple form of descriptor is to be used.
	 */
	private def final static String formatDescriptor(IN4JSProject project, String unitPath, String sep1, String sep2,
		String sep3, boolean includeProjectVersion, boolean asJsIdentifier, boolean makeSimpleDescriptor) {

		var projectID = project.projectId
		var path = unitPath
		if (asJsIdentifier) {
			projectID = getValidJavascriptIdentifierName(project.projectId)
			path = getValidUnitPath(unitPath)
		}

		if (makeSimpleDescriptor) {
			return path;
		}

		if (includeProjectVersion) {
			return projectID + sep1 + projectVersionToStringWithoutQualifier(project.version, sep2) + sep3 + path;
		}

		return projectID + sep3 + path;
	}

	private static def final String getValidUnitPath(String unitPath) {
		return unitPath.split('/').map[getValidJavascriptIdentifierName].join('/');
	}

	private static def String getValidJavascriptIdentifierName(String moduleSpecifier) {
		if (moduleSpecifier === null || moduleSpecifier.length === 0) {
			return moduleSpecifier;
		}
		val sb = new StringBuilder();
		for (var int i = 0; i < moduleSpecifier.length; i++) {
			val ch = moduleSpecifier.charAt(i);
			val isValid = if (i === 0) Character.isJavaIdentifierStart(ch) else Character.isJavaIdentifierPart(ch);
			if (isValid) {
				sb.append(ch);
			} else {
				sb.append(toUnicode(ch));
			}
		}
		return sb.toString();
	}

	def static private toUnicode(char charAtPos) {
		return "_u" + Integer.toHexString(charAtPos.bitwiseOr(0x10000)).substring(1);
	}

	/**
	 * Transforms the version into a string used for variable, parameter, and file names.
	 */
	def private static projectVersionToStringWithoutQualifier(DeclaredVersion declaredVersion, String separatorChar) {
		return declaredVersion.getMajor + separatorChar + declaredVersion.getMinor + separatorChar +
			declaredVersion.getMicro;
	}

	/**
	 * The qualified name of a declared type is its simple name, prefixed by the fully qualified module name it is
	 * defined in.
	 * <p>
	 * Example: <code>p.C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	def public String getQualifiedModuleName(Script script) {
		return converter.toString(qualifiedNameProvider.getFullyQualifiedName(script));
	}

	/**
	 * The qualified name of a declared type is its simple name, prefixed by the fully qualified module name it is
	 * defined in.
	 * <p>
	 * Example: <code>p.C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	def public String getQualifiedModuleName(TModule module) {
		return converter.toString(qualifiedNameProvider.getFullyQualifiedName(module));
	}

	/**
	 * The simple name of a declared type is the name which is used in its declaration. If type is anonymous, dummy name
	 * will be generated.
	 * <p>
	 * Example: <code>C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	def public String getSimpleTypeName(Type type) {
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
	def public String getFullyQualifiedTypeName(Type type) {
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
	def public String getFullyQualifiedTypeName_WITH_LEGACY_SUPPORT(Type type) {
		return getFullyQualifiedTypeName(type).replace(N4JSQualifiedNameConverter.DELIMITER, ".");
	}

	/**
	 * In import declarations, modules are references using the qualified module path. This is the file name relative to
	 * the source folder without any extensions. We use the slash ’/’ as a delimiter.
	 * <p>
	 * Example: <code>p/C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	def public String getModuleSpecifier(Script script) {
		return specifierConverter.toString(qualifiedNameProvider.getFullyQualifiedName(script));
	}

	/**
	 * In import declarations, modules are references using the qualified module path. This is the file name relative to
	 * the source folder without any extensions. We use the slash ’/’ as a delimiter.
	 * <p>
	 * Example: <code>p/C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	def public String getModuleSpecifier(TModule module) {
		return specifierConverter.toString(qualifiedNameProvider.getFullyQualifiedName(module));
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
	def public String getCompleteModuleSpecifier(IN4JSProject project, TModule module) {
		return formatDescriptor(resolveProject(module), module.getModuleSpecifier(), "-", ".", "/", USE_VERSIONS, false, USE_NODE_DESCRIPTOR);
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
	def public String getCompleteModuleSpecifier(TModule module) {
		return getCompleteModuleSpecifier(resolveProject(module), module);
	}

	/**
	 * Based on provided file resource URI and extension will generate descriptor in form of
	 * Project_0_0_1_module_path_Module Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor} For
	 * delegation both project and unitPath are calculated from provided {@link TModule}. Characters that would be
	 * invalid in a Javascript identifier will be replaced with the unicode sign e.g. _u002e for the dot character (_ is
	 * used instead of / as Javascript would translate the unicode sign again to the not allowed character).
	 * <p>
	 * Example: <code>_import_project_1_0_0_p_C</code> for class C in file/module C in package p of project with version
	 * 1.0.0.
	 * </p>
	 * 
	 * @module {@link TModule} for which we generate descriptor
	 */
	def public String getCompleteModuleSpecifierAsIdentifier(IN4JSProject project, TModule module) {
		return getValidJavascriptIdentifierName(
			formatDescriptor(project, module.getModuleSpecifier(), "_", "_", "_", USE_VERSIONS, true, USE_NODE_DESCRIPTOR));
	}

	/**
	 * Based on provided file resource URI and extension will generate descriptor in form of
	 * Project_0_0_1_module_path_Module Convenience method. Delegates to {@link ResourceNameComputer#formatDescriptor} For
	 * delegation both project and unitPath are calculated from provided {@link TModule}. Characters that would be
	 * invalid in a Javascript identifier will be replaced with the unicode sign e.g. _u002e for the dot character (_ is
	 * used instead of / as Javascript would translate the unicode sign again to the not allowed character).
	 * <p>
	 * Example: <code>_import_project_1_0_0_p_C</code> for class C in file/module C in package p of project with version
	 * 1.0.0.
	 * </p>
	 * 
	 * @module {@link TModule} for which we generate descriptor
	 */
	def public String getCompleteModuleSpecifierAsIdentifier(TModule module) {
		return getCompleteModuleSpecifierAsIdentifier(resolveProject(module), module);
	}

	def private IN4JSProject resolveProject(TModule module) {
		return projectResolver.resolveProject(module.eResource().getURI());
	}

	/**
	 * The versioned type specifier is used only internally. It is derived from the complete module specifier with the
	 * qualified name of the type separated by dashes.
	 * <p>
	 * <p>
	 * Example: <code>project-1.0.0/p/C/C</code> for class C in file/module C in package p of project with version
	 * 1.0.0.
	 * </p>
	 */
	def public String getCompleteTypeSpecifier(Type type) {
		val TModule module = EcoreUtil2.getContainerOfType(type, TModule);
		return getCompleteModuleSpecifier(module) + "/" + getSimpleTypeName(type);
	}

}
