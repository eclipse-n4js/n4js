/**
 * Copyright (c) 2019 NumberFour AG.
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
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ModifiableElement
import org.eclipse.n4js.n4JS.ModuleSpecifierForm
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.ResourceNameComputer

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*
import org.eclipse.n4js.projectModel.names.N4JSProjectName

/**
 * This transformation will prepare the output code for module loading. Since dropping support for commonjs and SystemJS
 * and instead using ECMAScript 2015 imports/exports in the output code, this transformation is no longer doing much.
 */
class ModuleWrappingTransformation extends Transformation {

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	private String[] localModuleSpecifierSegments = null; // will be set in #analyze()

	override assertPreConditions() {
		// true
	}

	override assertPostConditions() {
		// true
	}

	override analyze() {
		val localModule = state.resource.module;
		val localModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(localModule);
		localModuleSpecifierSegments = localModuleSpecifier.split("/", -1);
	}

	override transform() {
		// adjust module specifiers in imports
		collectNodes(state.im, ImportDeclaration, false).forEach[transformImportDecl];

		// strip modifiers off all exported elements
		// (e.g. remove "public" from something like "export public let a = 'hello';")
		collectNodes(state.im, ExportDeclaration, false).map[exportedElement].filter(ModifiableElement).forEach [
			it.declaredModifiers.clear
		];

		// the following is only required because earlier transformations are producing
		// invalid "export default var|let|const ..."
		// TODO instead of the next line, change the earlier transformations to not produce these invalid constructs
		collectNodes(state.im, ExportDeclaration, false).forEach[splitDefaultExportFromVarDecl];

		// add implicit import of "n4js-runtime"
		addEmptyImport(N4JSGlobals.N4JS_RUNTIME.rawName);
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

		val targetProject = n4jsCore.findProject(targetModule.eResource.URI).orNull;

		if (targetProject.projectType === ProjectType.RUNTIME_LIBRARY) {
			// SPECIAL CASE #1
			// pointing to a module in a runtime library
			// --> always use plain module specifier
			return targetModule.moduleSpecifier;
		}

		val importingFromModuleInSameProject = targetProject.location == state.project.location;
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
		val targetSegments = targetModuleSpecifier.split("/", -1);
		val l = Math.min(targetSegments.length, localModuleSpecifierSegments.length);
		var i = 0;
		while (i < l && Objects.equals(targetSegments.get(i), localModuleSpecifierSegments.get(i))) {
			i++;
		}
		val differingSegments = Arrays.copyOfRange(targetSegments, i, targetSegments.length);
		val goUpCount = localModuleSpecifierSegments.length - 1 - i;
		val result = (if (goUpCount > 0) "../".repeat(goUpCount) else "./") + Joiner.on("/").join(differingSegments);
		return result;
	}

	def private String createAbsoluteModuleSpecifier(IN4JSProject targetProject, TModule targetModule) {
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

	def private N4JSProjectName getActualProjectName(IN4JSProject project) {
		if (project.projectType === ProjectType.DEFINITION) {
			val definedProjectName = project.definesPackageName;
			if (definedProjectName !== null && !definedProjectName.isEmpty) {
				return definedProjectName;
			}
		}
		return project.projectName;
	}

	/**
	 * Turns
	 * <pre>
	 * export default var|let|const C = ...
	 * </pre>
	 * into
	 * <pre>
	 * var|let|const C = ...
	 * export default C;
	 * </pre>
	 */
	def private void splitDefaultExportFromVarDecl(ExportDeclaration exportDecl) {
		if (exportDecl.isDefaultExport) {
			val exportedElement = exportDecl.exportedElement;
			if (exportedElement instanceof VariableStatement) {
				if (!exportedElement.varDeclsOrBindings.filter(VariableBinding).isEmpty) {
					throw new UnsupportedOperationException("unsupported: default-exported variable binding");
				}
				if (exportedElement.varDeclsOrBindings.size > 1) {
					throw new UnsupportedOperationException(
						"unsupported: several default-exported variable declarations in a single export declaration");
				}
				val varDecl = exportedElement.varDeclsOrBindings.head as VariableDeclaration;
				val varDeclSTE = findSymbolTableEntryForElement(varDecl, true);
				insertBefore(exportDecl, exportedElement); // will remove exportedElement from exportDecl
				exportDecl.defaultExportedExpression = _IdentRef(varDeclSTE);
			}
		}
	}
}
