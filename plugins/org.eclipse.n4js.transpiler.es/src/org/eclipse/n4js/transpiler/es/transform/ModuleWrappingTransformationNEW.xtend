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

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.ResourceNameComputer

/**
 *
 */
class ModuleWrappingTransformationNEW extends Transformation {

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ResourceNameComputer resourceNameComputer

	override assertPreConditions() {
		// true
	}

	override assertPostConditions() {
		// true
	}

	override analyze() {
		// no need to analyze the AST ahead of time
	}

	override transform() {
		collectNodes(state.im, ImportDeclaration, false).forEach[transformImportDecl];
	}

	def private void transformImportDecl(ImportDeclaration importDeclIM) {
		val module = state.info.getImportedModule(importDeclIM);
		val actualModuleSpecifier = computeActualModuleSpecifier(module);
		importDeclIM.moduleSpecifierAsText = actualModuleSpecifier;
	}

	def private String computeActualModuleSpecifier(TModule module) {
		val completeModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(module);

		var depProject = n4jsCore.findProject(module.eResource.URI).orNull;
		if (depProject !== null && depProject.projectType === ProjectType.DEFINITION) {
			val definedPackageName = depProject.definesPackageName;
			if (definedPackageName !== null) {
				depProject = n4jsCore.findAllProjectMappings.get(definedPackageName);
			}
		}
		if (depProject !== null) {
			val projectName = depProject.projectName;
			var outputPath = depProject.outputPath;
			if (projectName !== null && outputPath !== null) {
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
				return projectName + outputPath + completeModuleSpecifier;
			}
		}

		return completeModuleSpecifier;
	}
}
