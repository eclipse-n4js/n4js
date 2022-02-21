/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.transform

import com.google.common.base.Joiner
import com.google.inject.Inject
import java.util.Arrays
import java.util.Map
import java.util.Objects
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ModuleSpecifierForm
import org.eclipse.n4js.packagejson.projectDescription.ProjectType
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TNestedModule
import org.eclipse.n4js.utils.N4JSLanguageHelper
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.ResourceNameComputer
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot
import org.eclipse.n4js.workspace.WorkspaceAccess
import org.eclipse.n4js.workspace.utils.N4JSPackageName

/**
 * Converts the module specifiers of import statements from N4JS to ES6.
 * <p>
 * For details, see {@link #computeModuleSpecifierForOutputCode(ImportDeclaration)}.
 */
class ModuleSpecifierTransformation extends Transformation {

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private N4JSLanguageHelper n4jsLanguageHelper;

	private String[] localModulePath = null; // will be set in #analyze()
	private Map<String,String> definedModuleSpecifierRewrites = null; // will be set in #analyze()

	override assertPreConditions() {
		// true
	}

	override assertPostConditions() {
		// true
	}

	override analyze() {
		val localModule = state.resource.module;
		val localModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(localModule);
		val localModuleSpecifierSegments = localModuleSpecifier.split("/", -1);
		localModulePath = Arrays.copyOf(localModuleSpecifierSegments, localModuleSpecifierSegments.length - 1);
		definedModuleSpecifierRewrites = state.project.projectDescription.generatorRewriteModuleSpecifiers;
	}

	override transform() {
		// adjust module specifiers in imports
		collectNodes(state.im, ImportDeclaration, false).forEach[transformImportDecl];
	}

	def private void transformImportDecl(ImportDeclaration importDeclIM) {
		val definedRewrite = definedModuleSpecifierRewrites.get(importDeclIM.moduleSpecifierAsText);
		if (definedRewrite !== null) {
			// special case: a rewrite for this module specifier was defined in the package.json
			importDeclIM.moduleSpecifierAsText = definedRewrite;
			return;
		}

		val moduleSpecifier = computeModuleSpecifierForOutputCode(importDeclIM);
		val moduleSpecifierNormalized = moduleSpecifier.replace("/./", "/");
		importDeclIM.moduleSpecifierAsText = moduleSpecifierNormalized;
	}

	/**
	 * For the following reasons, we cannot simply reuse the module specifier from the N4JS source code
	 * in the generated output code:
	 * <ol>
	 * <li>in N4JS, module specifiers are always absolute whereas in plain Javascript module specifiers
	 * must be relative (i.e. start with a segment '.' or '..') when importing from a module within the
	 * same npm package. N4JS does not even support relative module specifiers.
	 * <li>in N4JS, the project name as the first segment of an absolute module specifier is optional
	 * (see {@link ModuleSpecifierForm#PLAIN} vs. {@link ModuleSpecifierForm#COMPLETE}); this is not
	 * supported by plain Javascript.
	 * <li>in N4JS, module specifiers do not contain the path to the output folder, whereas in plain
	 * Javascript absolute module specifiers must always contain the full path from a project's root
	 * folder to the module.
	 * <li>in N4JS, module specifiers do not include file extensions; in Javascript executed with node's
	 * native support for ES6 modules, file extensions are mandatory (note: this was not the case when
	 * using "esm" for handling ES6 modules).
	 * </ol>
	 * Importing from a runtime library is an exception to the above: in this case we must never include
	 * the runtime library's project name nor its path to the output folder in the module specifier.
	 */
	def private String computeModuleSpecifierForOutputCode(ImportDeclaration importDeclIM) {
		val targetModule = state.info.getImportedModule(importDeclIM);

		if (targetModule instanceof TNestedModule) {
			// SPECIAL CASE #1a
			// pointing to a module explicitly declared in a .d.ts file, such as a node built-in library:
			// import * as path_lib from "path"
			// --> always use plain module specifier
			return targetModule.moduleSpecifier; // no file extension to add!
		} else if (!(targetModule instanceof TModule)) {
			throw new UnsupportedOperationException("unsupported subclass of AbstractModule: " + targetModule.getClass.simpleName);
		}
		val targetTModule = targetModule as TModule;

		val targetProject = workspaceAccess.findProjectContaining(targetTModule);
		if (targetProject.type === ProjectType.RUNTIME_LIBRARY) {
			// SPECIAL CASE #1b
			// pointing to a module in a runtime library, such as importing a node built-in library:
			// import * as path_lib from "path"
			// --> always use plain module specifier
			return targetModule.moduleSpecifier; // no file extension to add!
		}

		val importingFromModuleInSameProject = targetProject.pathAsFileURI == state.project.pathAsFileURI;
		if (importingFromModuleInSameProject) {
			// SPECIAL CASE #2
			// module specifiers are always absolute in N4JS, but Javascript requires relative module
			// specifiers when importing from a module within the same npm package
			// --> need to create a relative module specifier here:
			return createRelativeModuleSpecifier(targetTModule);
		}

		val moduleSpecifierForm = importDeclIM.moduleSpecifierForm;
		if (moduleSpecifierForm === ModuleSpecifierForm.PROJECT
			|| moduleSpecifierForm === ModuleSpecifierForm.PROJECT_NO_MAIN) {
			// SPECIAL CASE #3
			// in case of project imports (a.k.a. bare imports) we simply use
			// the target project's name as module specifier:
			return getActualProjectName(targetProject).rawName; // no file extension to add!
		}

		return createAbsoluteModuleSpecifier(targetProject, targetTModule);
	}

	def private String createRelativeModuleSpecifier(TModule targetModule) {
		val targetModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(targetModule);
		val targetModuleSpecifierSegments = targetModuleSpecifier.split("/", -1);
		val targetModuleName = targetModuleSpecifierSegments.last();
		val targetModulePath = Arrays.copyOf(targetModuleSpecifierSegments, targetModuleSpecifierSegments.length - 1);
		val l = Math.min(targetModulePath.length, localModulePath.length);
		var i = 0;
		while (i < l && Objects.equals(targetModulePath.get(i), localModulePath.get(i))) {
			i++;
		}
		val differingSegments = Arrays.copyOfRange(targetModulePath, i, targetModulePath.length);
		val goUpCount = localModulePath.length - i;
		val ext = getActualFileExtension(targetModule);
		val result = (if (goUpCount > 0) "../".repeat(goUpCount) else "./")
			+ Joiner.on("/").join(differingSegments + #[targetModuleName])
			+ (if (ext !== null && !ext.empty) "." + ext else "");
		return result;
	}

	def protected String createAbsoluteModuleSpecifier(N4JSProjectConfigSnapshot targetProject, TModule targetModule) {
		if (N4JSLanguageUtils.isMainModule(targetProject, targetModule)) {
			// 'targetModule' is the main module of 'targetProject', so we can use a project import:
			return getActualProjectName(targetProject).toString();
		}

		val sb = new StringBuilder();

		// first segment is the project name
		val targetProjectName = getActualProjectName(targetProject);
		if (targetProjectName !== null) {
			sb.append(targetProjectName);
		}

		// followed by the path to the output folder
		var outputPath = targetProject.outputPath;
		if (!outputPath.isNullOrEmpty) {
			if (!outputPath.startsWith('/')) {
				sb.append('/');
			}
			sb.append(outputPath);
			if (!outputPath.endsWith('/')) {
				sb.append('/');
			}
		} else {
			if (sb.length > 0) {
				sb.append('/');
			}
		}

		// and finally the target module's FQN (i.e. the path-to-module)
		val targetModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(targetModule);
		sb.append(targetModuleSpecifier);

		val ext = getActualFileExtension(targetModule);
		if (ext !== null && !ext.empty) {
			sb.append('.');
			sb.append(ext);
		}

		return sb.toString();
	}

	def protected String getActualFileExtension(TModule targetModule) {
		return n4jsLanguageHelper.getOutputFileExtension(state.index, targetModule);
	}

	def protected N4JSPackageName getActualProjectName(N4JSProjectConfigSnapshot project) {
		if (project.type === ProjectType.DEFINITION) {
			val definedProjectName = project.definesPackage;
			if (definedProjectName !== null && !definedProjectName.isEmpty) {
				return definedProjectName;
			}
		}
		return new N4JSPackageName(project.packageName);
	}
}
