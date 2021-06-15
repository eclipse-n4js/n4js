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
import java.util.Objects
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ModuleSpecifierForm
import org.eclipse.n4js.packagejson.projectDescription.ProjectType
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.ResourceNameComputer
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot
import org.eclipse.n4js.workspace.WorkspaceAccess
import org.eclipse.n4js.workspace.utils.N4JSProjectName

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

	private String[] localModulePath = null; // will be set in #analyze()

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
	}

	override transform() {
		// adjust module specifiers in imports
		collectNodes(state.im, ImportDeclaration, false).forEach[transformImportDecl];
	}

	def private void transformImportDecl(ImportDeclaration importDeclIM) {
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
	 * </ol>
	 * Importing from a runtime library is an exception to the above: in this case we must never include
	 * the runtime library's project name nor its path to the output folder in the module specifier.
	 */
	def private String computeModuleSpecifierForOutputCode(ImportDeclaration importDeclIM) {
		val targetModule = state.info.getImportedModule(importDeclIM);

		val targetProject = workspaceAccess.findProjectContaining(targetModule);

		if (targetProject.type === ProjectType.RUNTIME_LIBRARY) {
			// SPECIAL CASE #1
			// pointing to a module in a runtime library
			// --> always use plain module specifier
			return targetModule.moduleSpecifier;
		}

		val importingFromModuleInSameProject = targetProject.pathAsFileURI == state.project.pathAsFileURI;
		if (importingFromModuleInSameProject) {
			// SPECIAL CASE #2
			// module specifiers are always absolute in N4JS, but Javascript requires relative module
			// specifiers when importing from a module within the same npm package
			// --> need to create a relative module specifier here:
			return createRelativeModuleSpecifier(targetModule);
		}

		val moduleSpecifierForm = importDeclIM.moduleSpecifierForm;
		if (moduleSpecifierForm === ModuleSpecifierForm.PROJECT
			|| moduleSpecifierForm === ModuleSpecifierForm.PROJECT_NO_MAIN) {
			// SPECIAL CASE #3
			// in case of project imports (a.k.a. bare imports) we simply use
			// the target project's name as module specifier:
			return getActualProjectName(targetProject).rawName;
		}

		return createAbsoluteModuleSpecifier(targetProject, targetModule);
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
		val result = (if (goUpCount > 0) "../".repeat(goUpCount) else "./")
			+ Joiner.on("/").join(differingSegments + #[targetModuleName]);
		return result;
	}

	def private String createAbsoluteModuleSpecifier(N4JSProjectConfigSnapshot targetProject, TModule targetModule) {
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

		return sb.toString();
	}

	def private N4JSProjectName getActualProjectName(N4JSProjectConfigSnapshot project) {
		if (project.type === ProjectType.DEFINITION) {
			val definedProjectName = project.definesPackage;
			if (definedProjectName !== null && !definedProjectName.isEmpty) {
				return definedProjectName;
			}
		}
		return new N4JSProjectName(project.name);
	}
}
