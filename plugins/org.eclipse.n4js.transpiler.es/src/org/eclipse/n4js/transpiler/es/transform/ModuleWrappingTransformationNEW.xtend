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
import org.eclipse.n4js.ModuleSpecifierAdjustment
import org.eclipse.n4js.N4JSLanguageConstants
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
		val actualModuleSpecifierNormalized = actualModuleSpecifier.replace("/./", "/");
		importDeclIM.moduleSpecifierAsText = actualModuleSpecifierNormalized;
	}

	def private String computeActualModuleSpecifier(TModule module) {
		val moduleSpecifierAdjustment = getModuleSpecifierAdjustment(module);
		if (moduleSpecifierAdjustment !== null && moduleSpecifierAdjustment.usePlainModuleSpecifier) {
			return module.moduleSpecifier;
		}
		// note: ModuleSpecifierAdjustment#prefix is obsolete and entirely ignored in this transformation!

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

	/** returns adjustments to be used based on the module loader specified for the provided module. May be null. */
	def private ModuleSpecifierAdjustment getModuleSpecifierAdjustment(TModule module) {
		val resourceURI = module?.eResource?.URI;
		if (resourceURI === null) return null;
		val project = n4jsCore.findProject(resourceURI);
		if (!project.present) return null;
		val loader = project.get.getModuleLoader();
		if (loader === null) return null;
		val adjustment = N4JSLanguageConstants.MODULE_LOADER_PREFIXES.get(loader);
		return adjustment;
	}
}
