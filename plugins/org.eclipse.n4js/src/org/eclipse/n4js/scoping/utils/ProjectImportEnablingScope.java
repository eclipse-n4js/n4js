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

import static org.eclipse.n4js.N4JSGlobals.ALL_JS_FILE_EXTENSIONS;
import static org.eclipse.n4js.N4JSGlobals.DTS_FILE_EXTENSION;
import static org.eclipse.n4js.N4JSGlobals.N4JSD_FILE_EXTENSION;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.ModuleSpecifierForm;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectExports;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.DeclMergingUtils;
import org.eclipse.n4js.utils.EcoreUtilN4;
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
import com.google.common.collect.ImmutableSet;
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
	private final Optional<ModuleRef> importOrExportDecl;
	private final IScope parent;
	private final IScope delegate;

	private final DeclMergingHelper declMergingHelper;

	/**
	 * Wraps the given parent scope to enable project imports (see {@link ProjectImportEnablingScope} for details).
	 * <p>
	 * To support tests that use multiple projects without properly setting up IN4JSCore, we simply return 'parent' in
	 * such cases; however, project imports will not be available in such tests.
	 *
	 * @param importOrExportDecl
	 *            if an import/export declaration is provided, corresponding error reporting will be activated (i.e. an
	 *            {@link IEObjectDescriptionWithError} will be returned instead of <code>null</code> in case of
	 *            unresolvable references).
	 */
	public static IScope create(N4JSWorkspaceConfigSnapshot ws, Resource resource,
			Optional<ModuleRef> importOrExportDecl,
			IScope parent, IScope delegate,
			DeclMergingHelper declMergingHelper) {

		if (ws == null || resource == null || importOrExportDecl == null || parent == null) {
			throw new IllegalArgumentException("none of the arguments may be null");
		}
		if (importOrExportDecl.isPresent() && importOrExportDecl.get().eResource() != resource) {
			throw new IllegalArgumentException("given import declaration must be contained in the given resource");
		}
		final N4JSProjectConfigSnapshot contextProject = ws.findProjectContaining(resource.getURI());
		if (contextProject == null) {
			// we failed to obtain an IN4JSProject for the project containing 'importDecl'
			// -> it would be best to throw an exception in this case, but we have many tests that use multiple projects
			// without properly setting up the IN4JSCore; to not break those tests, we return 'parent' here
			return parent;
		}
		return new ProjectImportEnablingScope(ws, contextProject, importOrExportDecl, parent, delegate,
				declMergingHelper);
	}

	/**
	 *
	 * @param contextProject
	 *            the project containing the import declaration (not the project containing the module to import from)!
	 */
	private ProjectImportEnablingScope(N4JSWorkspaceConfigSnapshot ws, N4JSProjectConfigSnapshot contextProject,
			Optional<ModuleRef> importOrExportDecl,
			IScope parent, IScope delegate,
			DeclMergingHelper declMergingHelper) {

		if (ws == null || contextProject == null || importOrExportDecl == null || parent == null) {
			throw new IllegalArgumentException("none of the arguments may be null");
		}
		this.workspaceConfigSnapshot = ws;
		this.contextProject = contextProject;
		this.parent = parent;
		this.importOrExportDecl = importOrExportDecl;
		this.delegate = delegate;
		this.declMergingHelper = declMergingHelper;
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		List<IEObjectDescription> result = Lists.newArrayList(getElements(name));
		int size = result.size();

		// handle combination of .js / .cjs / .mjs files with same base name
		if (size > 1) {
			removeSuperfluousPlainJsFiles(result);
			size = result.size();
		}
		// handle ambient module declarations vs. module augmentations
		if (size > 1) {
			if (QualifiedNameUtils.isAmbient(name)) {
				// filter for ambient module declarations only
				Iterator<IEObjectDescription> iter = result.iterator();
				while (iter.hasNext()) {
					IEObjectDescription next = iter.next();
					if (DeclMergingUtils.isAugmentationModuleOrModule(next)) {
						iter.remove();
					}
				}
			} else {
				// if mixed -> filter for module augmentations only
				// else -> nothing
				List<IEObjectDescription> modAugmentations = new ArrayList<>();
				for (IEObjectDescription res : result) {
					if (DeclMergingUtils.isAugmentationModuleOrModule(res)) {
						modAugmentations.add(res);
					}
				}
				result = modAugmentations.isEmpty() ? result : modAugmentations;
			}
			size = result.size();
		}
		// handle merged declared modules
		if (size > 1) {
			result = declMergingHelper.chooseRepresentatives(result);
			size = result.size();
		}

		if (size == 1) {
			// main case
			return result.get(0);
		}

		// use sorted entries and linked map for determinism in error message
		Collections.sort(result, Comparator.comparing(IEObjectDescription::getEObjectURI, URIUtils::compare));
		final Map<IEObjectDescription, N4JSProjectConfigSnapshot> descriptionsToProject = new LinkedHashMap<>();

		for (IEObjectDescription objDescr : result) {
			URI uri = objDescr.getEObjectURI();
			N4JSProjectConfigSnapshot n4jsdProject = workspaceConfigSnapshot.findProjectContaining(uri);
			descriptionsToProject.put(objDescr, n4jsdProject);
		}

		IEObjectDescription overwritingModule = handleCollisions(result, descriptionsToProject);
		if (overwritingModule != null) {
			return overwritingModule;
		}

		// if no import declaration was given, we skip the advanced error reporting
		if (!importOrExportDecl.isPresent()) {
			return null;
		}

		// handle error cases to help user fix the issue
		StringBuilder sbErrrorMessage = new StringBuilder("Cannot resolve ");

		ModuleSpecifierForm importType = computeImportType(name, this.contextProject);
		switch (importType) {
		case PROJECT:
			sbErrrorMessage.append("project import");
			break;
		case PROJECT_EXPORTS:
			sbErrrorMessage.append("project 'exports' import");
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

				String matchingModules = "";
				for (IEObjectDescription descr : descriptionsToProject.keySet()) {
					if (!matchingModules.isEmpty()) {
						matchingModules += ", ";
					}

					if (descr.getEObjectURI() != null) {
						URI uri = descr.getEObjectURI().trimFragment();
						URI relUri = uri.deresolve(workspaceConfigSnapshot.getPath());
						Path relPath = Path.of(relUri.toFileString());
						relPath = relPath.subpath(1, relPath.getNameCount());
						matchingModules += relPath.toString();
					} else {
						matchingModules += descriptionsToProject.get(descr).getPackageName() + "/"
								+ descr.getQualifiedName().toString();
					}
				}

				sbErrrorMessage.append(matchingModules);
			}
		}

		sbErrrorMessage.append('.');

		final EObject originalProxy = (EObject) this.importOrExportDecl.get()
				.eGet(N4JSPackage.eINSTANCE.getModuleRef_Module(), false);
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
	 * In case both an N4JSD and a d.ts module exist, the n4jsd module is returned. Otherwise either the N4JSD or the
	 * d.ts module is returned.
	 */
	private IEObjectDescription handleCollisions(List<IEObjectDescription> result,
			Map<IEObjectDescription, N4JSProjectConfigSnapshot> descriptionsToProject) {

		if (result.isEmpty()) {
			return null;
		}

		Set<String> considerExtensions = ImmutableSet.<String> builder()
				.addAll(ALL_JS_FILE_EXTENSIONS)
				.add(N4JSD_FILE_EXTENSION)
				.add(DTS_FILE_EXTENSION)
				.build();
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

		// NOTE: the priority between .js/.cjs/.mjs we implement in the following loop based on the order in constant
		// ALL_JS_FILE_EXTENSIONS does not have an effect in practice, because conflicts between .js/.cjs/.mjs files are
		// resolved up front (see #removeSuperfluousPlainJsFiles() and its invocation in #getSingleElement()), meaning
		// we will always have only one of .js/.cjs/.mjs when reaching this point in the code:
		N4JSProjectConfigSnapshot jsProject = null;
		for (String jsFileExt : ALL_JS_FILE_EXTENSIONS) {
			N4JSProjectConfigSnapshot removed = prj4Ext.remove(jsFileExt);
			if (removed != null) {
				jsProject = removed;
			}
		}
		if (jsProject == null) {
			return null; // return null due to missing implementation module
		}

		Iterator<Entry<String, N4JSProjectConfigSnapshot>> iter = prj4Ext.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, N4JSProjectConfigSnapshot> entry = iter.next();
			String ext = entry.getKey();
			N4JSProjectConfigSnapshot prj = entry.getValue();

			if (!Objects.equals(jsProject.getPathAsFileURI(), prj.getPathAsFileURI())) {
				// case both modules are in different projects: check here iff related
				switch (ext) {
				case N4JSD_FILE_EXTENSION:
				case DTS_FILE_EXTENSION:
					if (prj.getType() != ProjectType.DEFINITION
							|| !Objects.equals(prj.getDefinesPackage(), new N4JSPackageName(jsProject.getName()))) {
						// case: n4jsd file is not inside a related n4js-definition project
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
		if (name == null) {
			return Collections.emptyList();
		}
		if (QualifiedNameUtils.isAmbient(name)) {
			List<String> segments = new ArrayList<>(name.getSegments());
			segments.remove(0);
			name = QualifiedName.create(segments);
		}

		ModuleSpecifierForm moduleSpecifierForm = loadModuleSpecifierFormInAST();
		if (moduleSpecifierForm == null || moduleSpecifierForm == ModuleSpecifierForm.UNKNOWN) {
			moduleSpecifierForm = computeImportType(name, this.contextProject);
			storeModuleSpecifierFormInAST(moduleSpecifierForm);
		}

		switch (moduleSpecifierForm) {
		case PROJECT: {
			final N4JSPackageName firstSegment = new N4JSPackageName(name.getFirstSegment());
			return findModulesInProject(Optional.absent(), firstSegment);
		}
		case PROJECT_EXPORTS: {
			final N4JSPackageName firstSegment = new N4JSPackageName(name.getFirstSegment());
			final QualifiedName exportsName = name.skipFirst(1);
			return findModulesInProjectExports(firstSegment, exportsName);
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
	 * Finds elements with the assumption that the given qualified name starts with a project identifier. If so, all
	 * referring elements of that project are returned. Otherwise returns an empty list.
	 */
	public Collection<IEObjectDescription> findElementsInProject(QualifiedName projectQName) {
		ModuleSpecifierForm moduleSpecifierForm = computeImportType(projectQName, this.contextProject);
		if (moduleSpecifierForm == ModuleSpecifierForm.PROJECT || moduleSpecifierForm == ModuleSpecifierForm.COMPLETE) {
			N4JSPackageName projectName = new N4JSPackageName(projectQName.getFirstSegment());
			N4JSProjectConfigSnapshot targetProject = findProject(projectName, contextProject, true);
			QualifiedName mainModuleName = ImportSpecifierUtil.getMainModuleOfProject(targetProject);
			ArrayList<String> newSegments = new ArrayList<>(projectQName.getSegments());
			newSegments.remove(0);
			newSegments.add(0, mainModuleName.toString());
			QualifiedName transformedQN = QualifiedName.create(newSegments);
			return getElementsWithDesiredProjectName(transformedQN, targetProject);
		}
		return Collections.emptyList();
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
				? getElementsWithDesiredProjectName(moduleNameToSearch, targetProject)
				: Collections.emptyList();

		if (result.isEmpty()
				&& targetProject != null
				&& !Objects.equals(targetProject.getN4JSPackageName(), projectName)) {
			// no elements found AND #findProject() returned a different project than we asked for (happens if a
			// type definition project is available)
			// -> try in project we asked for (i.e. the defined project)

			targetProject = findProject(projectName, contextProject, false);
			if (useMainModule) {
				moduleNameToSearch = ImportSpecifierUtil.getMainModuleOfProject(targetProject);
			} else {
				// leave 'moduleNameToSearch' unchanged
			}
			result = moduleNameToSearch != null
					? getElementsWithDesiredProjectName(moduleNameToSearch, targetProject)
					: Collections.emptyList();
		}

		if (result.isEmpty()
				&& moduleName.isPresent()
				&& projectName.getScopeName() == null
				&& targetProject != null
				&& !targetProject.getProjectDescription().getExports().isEmpty()) {
			// try virtual packages defined in property package.json#exports

			String exportsName = moduleName.orNull() == null ? null : moduleName.orNull().toString();

			for (ProjectExports exports : targetProject.getProjectDescription().getExports()) {
				if (Objects.equals(exportsName, exports.getExportsPathClean())) {
					result = getElementsWithDesiredProjectName(exports.getMainModule(), targetProject);
					break;
				}
			}
		}

		return result;
	}

	private Collection<IEObjectDescription> findModulesInProjectExports(N4JSPackageName projectName,
			QualifiedName exportsName) {

		N4JSProjectConfigSnapshot targetProject = findProject(projectName, contextProject, true);
		String exportsNameStr = exportsName.toString();

		for (ProjectExports exports : targetProject.getProjectDescription().getExports()) {
			if (Objects.equals(exportsNameStr, exports.getExportsPathClean())) {
				return getElementsWithDesiredProjectName(exports.getMainModule(), targetProject);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * This method asks {@link #delegate} for elements matching provided <code>moduleSpecifier</code>. Returned results
	 * are filtered by expected {@link N4JSPackageName}.
	 */
	public Collection<IEObjectDescription> getElementsWithDesiredProjectName(QualifiedName moduleSpecifier,
			N4JSProjectConfigSnapshot targetProject) {

		final Iterable<IEObjectDescription> moduleSpecifierMatchesWithPossibleDuplicates = delegate
				.getElements(moduleSpecifier);

		// delegate may return multiple entries since it allows duplication (normal 'shadowing' of scopes is not
		// applied). We filter duplicates by uniqueness of target EObject URI.
		final Map<String, IEObjectDescription> result = new HashMap<>();
		for (IEObjectDescription desc : moduleSpecifierMatchesWithPossibleDuplicates) {
			URI uri = desc.getEObjectURI();
			if (targetProject == null) {
				result.put(uri.toString(), desc);

			} else {
				N4JSProjectConfigSnapshot containingProject = workspaceConfigSnapshot.findProjectContaining(uri);
				if (containingProject == targetProject) {
					result.put(uri.toString(), desc);
				}
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
	public ModuleSpecifierForm computeImportType(QualifiedName name, N4JSProjectConfigSnapshot project) {
		final String firstSegment = name.getFirstSegment();
		final N4JSProjectConfigSnapshot targetProject = findProject(new N4JSPackageName(firstSegment), project, true);
		final boolean firstSegmentIsProjectName = targetProject != null;
		return ImportSpecifierUtil.computeImportType(name, firstSegmentIsProjectName, targetProject);
	}

	/**
	 * Stores the given module specifier form in the AST iff 1) {@link #importOrExportDecl} is present and 2) it was not
	 * set already; otherwise invoking this method has no effect.
	 */
	private void storeModuleSpecifierFormInAST(ModuleSpecifierForm moduleSpecifierForm) {
		if (importOrExportDecl.isPresent()) {
			ModuleRef impExpDecl = importOrExportDecl.get();
			if (impExpDecl.getModuleSpecifierForm() == ModuleSpecifierForm.UNKNOWN) {
				EcoreUtilN4.doWithDeliver(false, () -> {
					impExpDecl.setModuleSpecifierForm(moduleSpecifierForm);
				}, impExpDecl);
			}
		}
	}

	private ModuleSpecifierForm loadModuleSpecifierFormInAST() {
		if (importOrExportDecl.isPresent()) {
			ModuleRef impExpDecl = importOrExportDecl.get();
			return impExpDecl.getModuleSpecifierForm();
		}
		return null;
	}

	/**
	 * If the given list contains one or more {@link IEObjectDescription}s representing a plain JS file (i.e. a file
	 * with one of the extensions in {@link N4JSGlobals#ALL_JS_FILE_EXTENSIONS}), this method will retain only one of
	 * those descriptions, favoring {@code .mjs} files over {@code .cjs} files over {@code .js} files, and remove the
	 * others. Otherwise, the given list will remain unchanged.
	 */
	private void removeSuperfluousPlainJsFiles(List<IEObjectDescription> descs) {
		int size = descs.size();
		if (size < 2) {
			return;
		}
		IEObjectDescription firstJS = null;
		IEObjectDescription firstCJS = null;
		IEObjectDescription firstMJS = null;
		Iterator<IEObjectDescription> iter = descs.iterator();
		while (iter.hasNext()) {
			IEObjectDescription curr = iter.next();
			if (curr.getEClass() != TypesPackage.Literals.TMODULE) {
				// the special handling applies only to files, which are represented on the level of
				// IEObjectDescriptions as TModules, so for everything else we can continue here:
				continue;
			}
			String currExt = curr.getEObjectURI().fileExtension();
			if (N4JSGlobals.JS_FILE_EXTENSION.equals(currExt)
					|| N4JSGlobals.JSX_FILE_EXTENSION.equals(currExt)) {
				if (firstJS == null) {
					firstJS = curr;
				}
				iter.remove();
			} else if (N4JSGlobals.CJS_FILE_EXTENSION.equals(currExt)) {
				if (firstCJS == null) {
					firstCJS = curr;
				}
				iter.remove();
			} else if (N4JSGlobals.MJS_FILE_EXTENSION.equals(currExt)) {
				if (firstMJS == null) {
					firstMJS = curr;
				}
				iter.remove();
			}
		}
		if (firstMJS != null) {
			descs.add(firstMJS);
		} else if (firstCJS != null) {
			descs.add(firstCJS);
		} else if (firstJS != null) {
			descs.add(firstJS);
		}
	}
}
