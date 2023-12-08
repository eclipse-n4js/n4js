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
package org.eclipse.n4js.transpiler.es.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ModuleSpecifierForm;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.DeclMergingUtils;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;

import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * Converts the module specifiers of import statements from N4JS to ES6.
 * <p>
 * For details, see {@link #computeModuleSpecifierForOutputCode(ImportDeclaration)}.
 */
public class ModuleSpecifierTransformation extends Transformation {

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private N4JSLanguageHelper n4jsLanguageHelper;

	private String[] localModulePath = null; // will be set in #analyze()
	private Map<String, String> definedModuleSpecifierRewrites = null; // will be set in #analyze()

	@Override
	public void assertPreConditions() {
		// true
	}

	@Override
	public void assertPostConditions() {
		// true
	}

	@Override
	public void analyze() {
		TModule localModule = getState().resource.getModule();
		String localModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(localModule);
		String[] localModuleSpecifierSegments = localModuleSpecifier.split("/", -1);
		localModulePath = Arrays.copyOf(localModuleSpecifierSegments, localModuleSpecifierSegments.length - 1);
		definedModuleSpecifierRewrites = getState().project.getProjectDescription()
				.getGeneratorRewriteModuleSpecifiers();
	}

	@Override
	public void transform() {
		// adjust module specifiers in imports
		for (ImportDeclaration id : collectNodes(getState().im, ImportDeclaration.class, false)) {
			transformImportDecl(id);
		}

	}

	/** Returns file extension of output module */
	protected String getActualFileExtension(TModule targetModule) {
		return n4jsLanguageHelper.getOutputFileExtension(getState().index, targetModule);
	}

	private void transformImportDecl(ImportDeclaration importDeclIM) {
		String definedRewrite = definedModuleSpecifierRewrites.get(importDeclIM.getModuleSpecifierAsText());
		if (definedRewrite != null) {
			// special case: a rewrite for this module specifier was defined in the package.json
			importDeclIM.setModuleSpecifierAsText(definedRewrite);
			return;
		}

		String moduleSpecifier = computeModuleSpecifierForOutputCode(importDeclIM);
		String moduleSpecifierNormalized = moduleSpecifier.replace("/./", "/");
		importDeclIM.setModuleSpecifierAsText(moduleSpecifierNormalized);
	}

	/**
	 * For the following reasons, we cannot simply reuse the module specifier from the N4JS source code in the generated
	 * output code:
	 * <ol>
	 * <li>in N4JS, module specifiers are always absolute whereas in plain Javascript module specifiers must be relative
	 * (i.e. start with a segment '.' or '..') when importing from a module within the same npm package. N4JS does not
	 * even support relative module specifiers.
	 * <li>in N4JS, the project name as the first segment of an absolute module specifier is optional (see
	 * {@link ModuleSpecifierForm#PLAIN} vs. {@link ModuleSpecifierForm#COMPLETE}); this is not supported by plain
	 * Javascript.
	 * <li>in N4JS, module specifiers do not contain the path to the output folder, whereas in plain Javascript absolute
	 * module specifiers must always contain the full path from a project's root folder to the module.
	 * <li>in N4JS, module specifiers do not include file extensions; in Javascript executed with node's native support
	 * for ES6 modules, file extensions are mandatory (note: this was not the case when using "esm" for handling ES6
	 * modules).
	 * </ol>
	 * Importing from a runtime library is an exception to the above: in this case we must never include the runtime
	 * library's project name nor its path to the output folder in the module specifier.
	 */
	private String computeModuleSpecifierForOutputCode(ImportDeclaration importDeclIM) {
		TModule targetModule = getState().info.getImportedModule(importDeclIM);

		if (URIUtils.isVirtualResourceURI(targetModule.eResource().getURI())
				&& !DeclMergingUtils.isModuleAugmentation(targetModule)) {
			// SPECIAL CASE #1a
			// pointing to a module explicitly declared in a .d.ts file, such as a node built-in library:
			// import * as path_lib from "path"
			// --> always use plain module specifier
			return targetModule.getModuleSpecifier(); // no file extension to add!
		}

		N4JSProjectConfigSnapshot targetProject = workspaceAccess.findProjectContaining(targetModule);
		if (targetProject.getType() == ProjectType.RUNTIME_LIBRARY) {
			// SPECIAL CASE #1b
			// pointing to a module in a runtime library, such as importing a node built-in library:
			// import * as path_lib from "path"
			// --> always use plain module specifier
			return targetModule.getModuleSpecifier(); // no file extension to add!
		}

		boolean importingFromModuleInSameProject = Objects.equals(targetProject.getPathAsFileURI(), getState().project
				.getPathAsFileURI());
		if (importingFromModuleInSameProject) {
			// SPECIAL CASE #2
			// module specifiers are always absolute in N4JS, but Javascript requires relative module
			// specifiers when importing from a module within the same npm package
			// --> need to create a relative module specifier here:
			return createRelativeModuleSpecifier(targetModule);
		}

		ModuleSpecifierForm moduleSpecifierForm = importDeclIM.getModuleSpecifierForm();
		if (moduleSpecifierForm == ModuleSpecifierForm.PROJECT
				|| moduleSpecifierForm == ModuleSpecifierForm.PROJECT_NO_MAIN) {
			// SPECIAL CASE #3
			// in case of project imports (a.k.a. bare imports) we simply use
			// the target project's name as module specifier:
			return getActualProjectName(targetProject).getRawName(); // no file extension to add!
		} else if (moduleSpecifierForm == ModuleSpecifierForm.PROJECT_EXPORTS) {
			// SPECIAL CASE #4
			// in case of project exports imports (defined by package.json property 'exports') we simply use
			// the original module specifier:
			return importDeclIM.getModuleSpecifierAsText();
		}

		return createAbsoluteModuleSpecifier(targetProject, targetModule);
	}

	private String createRelativeModuleSpecifier(TModule targetModule) {
		String targetModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(targetModule);
		String[] targetModuleSpecifierSegments = targetModuleSpecifier.split("/", -1);
		String targetModuleName = targetModuleSpecifierSegments[targetModuleSpecifierSegments.length - 1];
		String[] targetModulePath = Arrays.copyOf(targetModuleSpecifierSegments,
				targetModuleSpecifierSegments.length - 1);
		int l = Math.min(targetModulePath.length, localModulePath.length);
		int i = 0;
		while (i < l && Objects.equals(targetModulePath[i], localModulePath[i])) {
			i++;
		}
		String[] differingSegments = Arrays.copyOfRange(targetModulePath, i, targetModulePath.length);
		List<String> allSegm = new ArrayList<>(Arrays.asList(differingSegments));
		allSegm.add(targetModuleName);
		int goUpCount = localModulePath.length - i;
		String ext = getActualFileExtension(targetModule);
		String result = ((goUpCount > 0) ? "../".repeat(goUpCount) : "./")
				+ org.eclipse.n4js.utils.Strings.join("/", allSegm)
				+ ((ext != null && !ext.isEmpty()) ? "." + ext : "");
		return result;
	}

	private String createAbsoluteModuleSpecifier(N4JSProjectConfigSnapshot targetProject, TModule targetModule) {
		if (N4JSLanguageUtils.isMainModule(targetProject, targetModule)) {
			// 'targetModule' is the main module of 'targetProject', so we can use a project import:
			return getActualProjectName(targetProject).toString();
		}

		StringBuilder sb = new StringBuilder();

		// first segment is the project name
		N4JSPackageName targetProjectName = getActualProjectName(targetProject);
		if (targetProjectName != null) {
			sb.append(targetProjectName);
		}

		// followed by the path to the output folder
		String outputPath = targetProject.getOutputPath();
		if (!Strings.isNullOrEmpty(outputPath)) {
			if (!outputPath.startsWith("/")) {
				sb.append("/");
			}
			sb.append(outputPath);
			if (!outputPath.endsWith("/")) {
				sb.append("/");
			}
		} else {
			if (sb.length() > 0) {
				sb.append("/");
			}
		}

		// and finally the target module's FQN (i.e. the path-to-module)
		String targetModuleSpecifier = resourceNameComputer.getCompleteModuleSpecifier(targetModule);
		sb.append(targetModuleSpecifier);

		String ext = getActualFileExtension(targetModule);
		if (ext != null && !ext.isEmpty()) {
			sb.append('.');
			sb.append(ext);
		}

		return sb.toString();
	}

	private N4JSPackageName getActualProjectName(N4JSProjectConfigSnapshot project) {
		if (project.getType() == ProjectType.DEFINITION) {
			N4JSPackageName definedProjectName = project.getDefinesPackage();
			if (definedProjectName != null && !definedProjectName.isEmpty()) {
				return definedProjectName;
			}
		}
		return new N4JSPackageName(project.getPackageName());
	}
}
