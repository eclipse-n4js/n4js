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
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.scoping.utils.ImportSpecifierUtil
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.ResourceNameComputer
import org.eclipse.xtext.naming.IQualifiedNameProvider

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*


/**
 * This transformation will prepare the output code for module loading. Since dropping support for commonjs and SystemJS
 * and instead using ECMAScript 2015 imports/exports in the output code, this transformation is no longer doing much.
 */
class ModuleWrappingTransformation extends Transformation {

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

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
		addEmptyImport(N4JSGlobals.N4JS_RUNTIME);
	}

	def private void transformImportDecl(ImportDeclaration importDeclIM) {
		val module = state.info.getImportedModule(importDeclIM);
		val actualModuleSpecifier = computeActualModuleSpecifier(module);
		val actualModuleSpecifierNormalized = actualModuleSpecifier.replace("/./", "/");
		importDeclIM.moduleSpecifierAsText = actualModuleSpecifierNormalized;
	}

	def private String computeActualModuleSpecifier(TModule targetModule) {
		val targetModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(targetModule);

		var targetProject = n4jsCore.findProject(targetModule.eResource.URI).orNull;
		if (targetProject !== null && targetProject.projectType === ProjectType.DEFINITION) {
			val definedPackageName = targetProject.definesPackageName;
			if (definedPackageName !== null) {
				targetProject = n4jsCore.findAllProjectMappings.get(definedPackageName);
			}
		}
		if (targetProject !== null) {
			if (targetProject.projectType === ProjectType.RUNTIME_LIBRARY) {
				// pointing to a module in a runtime library
				// --> always use plain module specifier
				return targetModule.moduleSpecifier;
			} else if (targetProject.location == state.project.location) {
				// pointing to a target module in same project
				return createRelativeModuleSpecifier(targetProject, targetModuleSpecifier);
			} else {
				// pointing to a target module in another project
				return createAbsoluteModuleSpecifier(targetProject, targetModule, targetModuleSpecifier);
			}
		}

		return targetModuleSpecifier;
	}

	def private String createRelativeModuleSpecifier(IN4JSProject targetProject, String targetModuleSpecifier) {
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

	def private String createAbsoluteModuleSpecifier(IN4JSProject targetProject, TModule targetModule,
		String targetModuleSpecifier) {

		// 1) check if a project import (a.k.a. "bare import") to the target project's main module can be used
		if (targetProject.mainModule !== null) {
			val targetProjectMainModuleFQN = ImportSpecifierUtil.getMainModuleOfProject(targetProject);
			val targetModuleFQN = qualifiedNameProvider.getFullyQualifiedName(targetModule);
			if (targetProjectMainModuleFQN !== null && targetProjectMainModuleFQN == targetModuleFQN) {
				// use a project import 
				return targetProject.projectName;
			}
		}
		// 2) construct a complete module specifier (a.k.a. "deep import")
		val targetProjectName = targetProject.projectName;
		if (targetProjectName.isNullOrEmpty) {
			return targetModuleSpecifier;
		}
		var outputPath = targetProject.outputPath;
		// normalize outputPath: should be non-null and start/end with a '/'
		if (outputPath.isNullOrEmpty) {
			outputPath = '/';
		} else {
			if (!outputPath.startsWith('/')) {
				outputPath = '/' + outputPath;
			}
			if (!outputPath.endsWith('/')) {
				outputPath = outputPath + '/';
			}
		}
		return targetProjectName + outputPath + targetModuleSpecifier;
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
