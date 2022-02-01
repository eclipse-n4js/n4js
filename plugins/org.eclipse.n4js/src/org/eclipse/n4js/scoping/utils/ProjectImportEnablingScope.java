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
package org.eclipse.n4js.scoping.utils;

import static org.eclipse.n4js.N4JSGlobals.DTS_FILE_EXTENSION;
import static org.eclipse.n4js.N4JSGlobals.JS_FILE_EXTENSION;
import static org.eclipse.n4js.N4JSGlobals.N4JSD_FILE_EXTENSION;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ModuleSpecifierForm;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

/**
 * Normally we import from a module by only supplying the module specifier without the project ID of the containing
 * project:
 *
 * <pre>
 * import * as N from "some/path/to/Module"
 * </pre>
 *
 * By using this wrapping scope, we obtain support for two additional forms of import (called "project imports"):
 *
 * <pre>
 * import * as N from "projectName/some/path/to/Module"
 * </pre>
 *
 * and
 *
 * <pre>
 * import * as N from "projectName"
 * </pre>
 *
 * In both cases, <code>projectName</code>> stands for the N4JS project name of the project containing the module to
 * import from. The last case is only allowed if the containing project has defined the <code>MainModule</code> property
 * in its manifest and means that this main module will then be imported.
 * <p>
 * Since there may exist multiple <code>MainModule</code> instances in the workspace with the same name (but not in one
 * project!) this scope uses two external sources of elements. In case of imports not specifying target project project
 * ID we use provided <code>parent</code> as source of elements in scope. In case of imports specifying target project
 * artefactID we use <code>delegate</code> scope as source of elements. That distinction is necessary as
 * {@link IScope#getElements(EObject)} surprisingly performs filtering of elements with the same name. We assume that
 * provided <code>delegate</code> is not doing that, at least not for the main modules.
 */
public class ProjectImportEnablingScope implements IScope {
	private final N4JSWorkspaceConfigSnapshot workspaceConfigSnapshot;
	private final N4JSProjectConfigSnapshot contextProject;
	private final Optional<ImportDeclaration> importDeclaration;
	private final IScope parent;
	private final IScope delegate;
	// private final N4JSModel n4jsModel;

	/**
	 * Wraps the given parent scope to enable project imports (see {@link ProjectImportEnablingScope} for details).
	 * <p>
	 * To support tests that use multiple projects without properly setting up IN4JSCore, we simply return 'parent' in
	 * such cases; however, project imports will not be available in such tests.
	 *
	 * @param importDecl
	 *            if an import declaration is provided, imported error reporting will be activated (i.e. an
	 *            {@link IEObjectDescriptionWithError} will be returned instead of <code>null</code> in case of
	 *            unresolvable references).
	 */
	public static IScope create(N4JSWorkspaceConfigSnapshot ws, Resource resource,
			Optional<ImportDeclaration> importDecl,
			IScope parent, IScope delegate) {

		if (ws == null || resource == null || importDecl == null || parent == null) {
			throw new IllegalArgumentException("none of the arguments may be null");
		}
		if (importDecl.isPresent() && importDecl.get().eResource() != resource) {
			throw new IllegalArgumentException("given import declaration must be contained in the given resource");
		}
		final N4JSProjectConfigSnapshot contextProject = ws.findProjectContaining(resource.getURI());
		if (contextProject == null) {
			// we failed to obtain an IN4JSProject for the project containing 'importDecl'
			// -> it would be best to throw an exception in this case, but we have many tests that use multiple projects
			// without properly setting up the IN4JSCore; to not break those tests, we return 'parent' here
			return parent;
		}
		return new ProjectImportEnablingScope(ws, contextProject, importDecl, parent, delegate);
	}

	/**
	 *
	 * @param contextProject
	 *            the project containing the import declaration (not the project containing the module to import from)!
	 */
	private ProjectImportEnablingScope(N4JSWorkspaceConfigSnapshot ws, N4JSProjectConfigSnapshot contextProject,
			Optional<ImportDeclaration> importDecl, IScope parent, IScope delegate) {

		if (ws == null || contextProject == null || importDecl == null || parent == null) {
			throw new IllegalArgumentException("none of the arguments may be null");
		}
		this.workspaceConfigSnapshot = ws;
		this.contextProject = contextProject;
		this.parent = parent;
		this.importDeclaration = importDecl;
		this.delegate = delegate;
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		final List<IEObjectDescription> result = Lists.newArrayList(getElements(name));
		int size = result.size();
		if (size == 1) {
			// main case
			return result.get(0);
		}

		// use linked map for determinism in error message
		final Map<IEObjectDescription, N4JSProjectConfigSnapshot> descriptionsToProject = new LinkedHashMap<>();
		for (IEObjectDescription objDescr : result) {
			N4JSProjectConfigSnapshot n4jsdProject = workspaceConfigSnapshot
					.findProjectContaining(objDescr.getEObjectURI());
			descriptionsToProject.put(objDescr, n4jsdProject);
		}

		IEObjectDescription overwritingModule = handleCollisions(result, descriptionsToProject);
		if (overwritingModule != null) {
			return overwritingModule;
		}

		// if no import declaration was given, we skip the advanced error reporting
		if (!importDeclaration.isPresent()) {
			return null;
		}

		// handle error cases to help user fix the issue
		StringBuilder sbErrrorMessage = new StringBuilder("Cannot resolve ");

		ModuleSpecifierForm importType = computeImportType(name, this.contextProject);
		switch (importType) {
		case PROJECT:
			sbErrrorMessage.append("project import");
			break;
		case COMPLETE:
			sbErrrorMessage.append("complete module specifier (with project name as first segment)");
			break;
		case PLAIN:
			sbErrrorMessage.append("plain module specifier (without project name as first segment)");
			break;
		case PROJECT_NO_MAIN:
			sbErrrorMessage.append("project import: target project does not define a main module");
			break;
		default:
			sbErrrorMessage.append("module specifier");
			break;
		}

		if (!importType.equals(ModuleSpecifierForm.PROJECT_NO_MAIN)) {
			sbErrrorMessage.append(": ");
			if (size == 0) {
				sbErrrorMessage.append("no matching module found");
			} else {
				sbErrrorMessage.append("multiple matching modules found: ");

				String matchingModules = Strings.join(", ",
						e -> //
						e.getValue().getPackageName() + "/" + e.getKey().getQualifiedName().toString(),
						descriptionsToProject.entrySet());

				sbErrrorMessage.append(matchingModules);
			}
		}

		sbErrrorMessage.append('.');

		final EObject originalProxy = (EObject) this.importDeclaration.get()
				.eGet(N4JSPackage.eINSTANCE.getImportDeclaration_Module(), false);
		return new IssueCodeBasedEObjectDescription(EObjectDescription.create("impDecl", originalProxy),
				sbErrrorMessage.toString(), IssueCodes.IMP_UNRESOLVED);
	}

	/**
	 * Special case handling when we have a definition and a pure JS file in the scope. In such cases we return with the
	 * description that corresponds to the definition file. Given several modules with the same name, there exists a
	 * resolution only iff:
	 * <ul>
	 * <li/>There is a plain js module, and
	 * <li/>An N4JSD module must be the corresponding definition module inside the corresponding definition project, and
	 * <li/>An d.ts module must be the corresponding definition module inside the corresponding definition project
	 * </ul>
	 * In case both and N4JSD and a d.ts module exist, the n4jsd module is returned. Otherwise either the N4JSD or the
	 * d.ts module is returned.
	 */
	private IEObjectDescription handleCollisions(List<IEObjectDescription> result,
			Map<IEObjectDescription, N4JSProjectConfigSnapshot> descriptionsToProject) {

		Set<String> considerExtensions = Set.of(JS_FILE_EXTENSION, N4JSD_FILE_EXTENSION, DTS_FILE_EXTENSION);
		Map<String, IEObjectDescription> descr4Ext = new HashMap<>();
		Map<String, N4JSProjectConfigSnapshot> prj4Ext = new HashMap<>();
		for (IEObjectDescription res : result) {
			String ext = URIUtils.fileExtension(res.getEObjectURI());
			if (!considerExtensions.contains(ext)) {
				continue;
			}

			N4JSProjectConfigSnapshot prj = descriptionsToProject.get(res);

			if (descr4Ext.containsKey(ext)) {
				return null; // return null due to conflict
			}
			if (ext != null && prj != null) {
				descr4Ext.put(ext, res);
				prj4Ext.put(ext, prj);
			}
		}

		if (descr4Ext.size() < 2) {
			return null; // return null due to missing project information
		}

		if (!descr4Ext.containsKey(JS_FILE_EXTENSION)) {
			return null; // return null due to missing implementation module
		}
		N4JSProjectConfigSnapshot jsProject = prj4Ext.remove(JS_FILE_EXTENSION);

		Iterator<Entry<String, N4JSProjectConfigSnapshot>> iter = prj4Ext.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, N4JSProjectConfigSnapshot> entry = iter.next();
			String ext = entry.getKey();
			N4JSProjectConfigSnapshot prj = entry.getValue();

			if (!Objects.equals(jsProject.getPathAsFileURI(), prj.getPathAsFileURI())) {
				// case both modules are in different projects: check here iff related
				switch (ext) {
				case N4JSD_FILE_EXTENSION:
					if (prj.getType() != ProjectType.DEFINITION
							|| !Objects.equals(prj.getDefinesPackage(), new N4JSPackageName(jsProject.getName()))) {
						// case: n4jsd file is not inside a related n4js-definition project
						return null; // return null due to conflict
					}
					break;
				case DTS_FILE_EXTENSION:
					if (prj.getType() != ProjectType.PLAINJS
							|| !prj.getName().endsWith("/" + jsProject.getName())) {
						// case: d.ts file is not inside a related @types definition project
						return null; // return null due to conflict
					}
					break;
				default:
					// all fine
				}
			} else {
				// case both modules are in same project: assume one being the definition of the other
			}
		}

		if (prj4Ext.size() == 1) {
			String dExt = prj4Ext.keySet().iterator().next();
			return descr4Ext.get(dExt);

		} else if (prj4Ext.size() == 2) {
			if (descr4Ext.containsKey(N4JSD_FILE_EXTENSION)) {
				// paranoia check - should always be true
				return descr4Ext.get(N4JSD_FILE_EXTENSION);
			}
			if (descr4Ext.containsKey(DTS_FILE_EXTENSION)) {
				// paranoia check - should always be true
				return descr4Ext.get(DTS_FILE_EXTENSION);
			}
		}

		return null;
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {

		ModuleSpecifierForm moduleSpecifierForm = computeImportType(name, this.contextProject);

		storeModuleSpecifierFormInAST(moduleSpecifierForm);

		switch (moduleSpecifierForm) {
		case PROJECT: {
			final N4JSPackageName firstSegment = new N4JSPackageName(name.getFirstSegment());
			return findModulesInProject(Optional.absent(), firstSegment);
		}
		case COMPLETE: {
			final N4JSPackageName firstSegment = new N4JSPackageName(name.getFirstSegment());
			return findModulesInProject(Optional.of(name.skipFirst(1)), firstSegment);
		}
		case PLAIN: {
			return parent.getElements(name);
		}
		default:
			return Collections.emptyList();
		}
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		return parent.getSingleElement(object);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		return parent.getElements(object);
	}

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return parent.getAllElements();
	}

	/**
	 * Finds all modules with the given module FQN (without project name) in the given project. Supports two special
	 * cases:
	 * <ol>
	 * <li>if the module name is absent, the given project's main module will be returned.
	 * <li>if a {@link ProjectDescription#getDefinesPackage() definition project} exists for the given project, modules
	 * of the definition project will be returned and only if that fails, modules of the given project will be returned.
	 * </ol>
	 */
	private Collection<IEObjectDescription> findModulesInProject(Optional<QualifiedName> moduleName,
			N4JSPackageName projectName) {

		boolean useMainModule = !moduleName.isPresent();

		N4JSProjectConfigSnapshot targetProject = findProject(projectName, contextProject, true);
		QualifiedName moduleNameToSearch = useMainModule
				? ImportSpecifierUtil.getMainModuleOfProject(targetProject)
				: moduleName.get();

		Collection<IEObjectDescription> result;
		result = moduleNameToSearch != null
				? getElementsWithDesiredProjectName(moduleNameToSearch, targetProject.getN4JSPackageName())
				: Collections.emptyList();

		if (result.isEmpty()
				&& targetProject != null
				&& !Objects.equals(targetProject.getN4JSPackageName(), projectName)) {
			// no elements found AND #findProject() returned a different project than we asked for (happens if a
			// type definition project is available)
			// -> as a fall back, try again in project we asked for (i.e. the defined project)
			if (useMainModule) {
				targetProject = findProject(projectName, contextProject, false);
				moduleNameToSearch = ImportSpecifierUtil.getMainModuleOfProject(targetProject);
			} else {
				// leave 'moduleNameToSearch' unchanged
			}
			result = moduleNameToSearch != null
					? getElementsWithDesiredProjectName(moduleNameToSearch, projectName)
					: Collections.emptyList();
		}

		return result;
	}

	/**
	 * This method asks {@link #delegate} for elements matching provided <code>moduleSpecifier</code>. Returned results
	 * are filtered by expected {@link N4JSPackageName}.
	 */
	private Collection<IEObjectDescription> getElementsWithDesiredProjectName(QualifiedName moduleSpecifier,
			N4JSPackageName projectName) {

		final Iterable<IEObjectDescription> moduleSpecifierMatchesWithPossibleDuplicates = delegate
				.getElements(moduleSpecifier);

		// delegate may return multiple entries since it allows duplication (normal 'shadowing' of scopes is not
		// applied). We filter duplicates by uniqueness of target EObject URI.
		final Map<String, IEObjectDescription> result = new HashMap<>();
		for (IEObjectDescription desc : moduleSpecifierMatchesWithPossibleDuplicates) {
			final N4JSProjectConfigSnapshot containingProject = workspaceConfigSnapshot
					.findProjectContaining(desc.getEObjectURI());
			if (containingProject != null && projectName.equals(containingProject.getN4JSPackageName())) {
				result.put(desc.getEObjectURI().toString(), desc);
			}
		}
		return result.values();
	}

	private N4JSProjectConfigSnapshot findProject(N4JSPackageName projectName, N4JSProjectConfigSnapshot project,
			boolean preferDefinitionProject) {

		if (Objects.equals(project.getN4JSPackageName(), projectName)) {
			return project;
		}

		// We here rely on dependencies being sorted such that definition projects appear before the corresponding
		// defined projects. Since the dependency list stored in (N4JS)ProjectConfigSnapshots is the list of "semantic
		// dependencies" (see N4JSProjectConfig#computeSemanticDependencies()) we can simply invoke #getDependencies()
		// here:
		Iterable<String> dependencies = project.getDependencies();
		for (String pName : dependencies) {
			N4JSProjectConfigSnapshot p = workspaceConfigSnapshot.findProjectByID(pName);
			if (p == null) {
				continue;
			}
			// note: dependencies are sorted, so the following two checks can be done in the same loop:
			if (preferDefinitionProject && Objects.equals(p.getDefinesPackage(), projectName)) {
				return p;
			}
			if (Objects.equals(p.getN4JSPackageName(), projectName)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Convenience method over
	 * {@link ImportSpecifierUtil#computeImportType(QualifiedName, boolean, N4JSProjectConfigSnapshot)}
	 */
	private ModuleSpecifierForm computeImportType(QualifiedName name, N4JSProjectConfigSnapshot project) {
		final String firstSegment = name.getFirstSegment();
		final N4JSProjectConfigSnapshot targetProject = findProject(new N4JSPackageName(firstSegment), project, true);
		final boolean firstSegmentIsProjectName = targetProject != null;
		return ImportSpecifierUtil.computeImportType(name, firstSegmentIsProjectName, targetProject);
	}

	/**
	 * Stores the given module specifier form in the AST iff 1) {@link #importDeclaration} is present and 2) it was not
	 * set already; otherwise invoking this method has no effect.
	 */
	private void storeModuleSpecifierFormInAST(ModuleSpecifierForm moduleSpecifierForm) {
		if (importDeclaration.isPresent()) {
			ImportDeclaration impDecl = importDeclaration.get();
			if (impDecl.getModuleSpecifierForm() == ModuleSpecifierForm.UNKNOWN) {
				EcoreUtilN4.doWithDeliver(false, () -> {
					impDecl.setModuleSpecifierForm(moduleSpecifierForm);
				}, impDecl);
			}
		}
	}
}
