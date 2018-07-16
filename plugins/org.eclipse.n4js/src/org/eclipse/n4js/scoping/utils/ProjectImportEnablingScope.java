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

import static org.eclipse.n4js.N4JSGlobals.JS_FILE_EXTENSION;
import static org.eclipse.n4js.N4JSGlobals.N4JSD_FILE_EXTENSION;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

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
 * import * as N from "projectId/some/path/to/Module"
 * </pre>
 *
 * and
 *
 * <pre>
 * import * as N from "projectId"
 * </pre>
 *
 * In both cases, <code>projectId</code>> stands for the project ID of the project containing the module to import from.
 * The last case is only allowed if the containing project has defined the <code>MainModule</code> property in its
 * manifest and means that this main module will then be imported.
 *
 *
 * Since there may exist multiple <code>MainModule</code> instances in the workspace with the same name (but not in one
 * project!) this scope uses two external sources of elements. In case of imports not specifying target project project
 * ID we use provided <code>parent</code> as source of elements in scope. In case of imports specifying target project
 * artefactID we use <code>delegate</code> scope as source of elements. That distinction is necessary as
 * {@link IScope#getElements(EObject)} surprisingly performs filtering of elements with the same name. We assume that
 * provided <code>delegate</code> is not doing that, at least not for the main modules.
 */
public class ProjectImportEnablingScope implements IScope {

	private final IN4JSCore n4jsCore;
	private final IN4JSProject contextProject;
	private final Optional<ImportDeclaration> importDeclaration;
	private final IScope parent;
	private final IScope delegate;

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
	public static IScope create(IN4JSCore n4jsCore, Resource resource, Optional<ImportDeclaration> importDecl,
			IScope parent, IScope delegate) {
		if (n4jsCore == null || resource == null || importDecl == null || parent == null) {
			throw new IllegalArgumentException("none of the arguments may be null");
		}
		if (importDecl.isPresent() && importDecl.get().eResource() != resource) {
			throw new IllegalArgumentException("given import declaration must be contained in the given resource");
		}
		final Optional<? extends IN4JSProject> contextProject = n4jsCore.findProject(resource.getURI());
		if (!contextProject.isPresent()) {
			// we failed to obtain an IN4JSProject for the project containing 'importDecl'
			// -> it would be best to throw an exception in this case, but we have many tests that use multiple projects
			// without properly setting up the IN4JSCore; to not break those tests, we return 'parent' here
			return parent;
		}
		return new ProjectImportEnablingScope(n4jsCore, contextProject.get(), importDecl, parent, delegate);
	}

	/**
	 *
	 * @param contextProject
	 *            the project containing the import declaration (not the project containing the module to import from)!
	 */
	private ProjectImportEnablingScope(IN4JSCore n4jsCore, IN4JSProject contextProject,
			Optional<ImportDeclaration> importDecl, IScope parent, IScope delegate) {
		if (n4jsCore == null || contextProject == null || importDecl == null || parent == null) {
			throw new IllegalArgumentException("none of the arguments may be null");
		}
		this.n4jsCore = n4jsCore;
		this.contextProject = contextProject;
		this.parent = parent;
		this.importDeclaration = importDecl;
		this.delegate = delegate;
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		final Iterable<IEObjectDescription> result = getElements(name);
		int size = Iterables.size(result);
		if (size == 1) {
			return result.iterator().next();
		}

		// Special case handling when we have a definition and a pure JS file in the scope.
		// In such cases we return with the description that corresponds to the definition file.
		if (size == 2) {

			final IEObjectDescription first = Iterables.get(result, 0);
			final IEObjectDescription second = Iterables.get(result, 1);

			final String firstExtension = first.getEObjectURI().fileExtension();
			final String secondExtension = second.getEObjectURI().fileExtension();

			if (JS_FILE_EXTENSION.equals(firstExtension) && N4JSD_FILE_EXTENSION.equals(secondExtension)) {
				return second;
			}

			if (N4JSD_FILE_EXTENSION.equals(firstExtension) && JS_FILE_EXTENSION.equals(secondExtension)) {
				return first;
			}

		}

		// if no import declaration was given, we skip the advanced error reporting
		if (!importDeclaration.isPresent()) {
			return null;
		}

		// handle error cases to help user fix the issue
		StringBuilder sbErrrorMessage = new StringBuilder("Cannot resolve import target ::");

		ImportType importType = computeImportType(name, this.contextProject);
		switch (importType) {
		case PROJECT_IMPORT:
			sbErrrorMessage.append(" resolving project import :");
			break;
		case COMPLETE_IMPORT:
			sbErrrorMessage.append(" resolving full module import :");
			break;
		case SIMPLE_IMPORT:
			sbErrrorMessage.append(" resolving simple module import :");
			break;
		case PROJECT_IMPORT_NO_MAIN:
			sbErrrorMessage.append(" no main module in target project");
			break;
		default:
			sbErrrorMessage.append(" unrecognized import structure :");
			break;
		}

		if (!importType.equals(ImportType.PROJECT_IMPORT_NO_MAIN)) {
			if (size == 0) {
				sbErrrorMessage.append(" found no matching modules");
			} else {
				sbErrrorMessage.append(" found multiple matching modules ");
				sbErrrorMessage.append(IterableExtensions.join(result, ","));
			}
		}

		final EObject originalProxy = (EObject) this.importDeclaration.get()
				.eGet(N4JSPackage.eINSTANCE.getImportDeclaration_Module(), false);
		return new IssueCodeBasedEObjectDescription(EObjectDescription.create("impDecl", originalProxy),
				sbErrrorMessage.toString(), IssueCodes.IMP_UNRESOLVED);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {

		switch (computeImportType(name, this.contextProject)) {
		case PROJECT_IMPORT:
			final String firstSegment = name.getFirstSegment();
			final IN4JSProject targetProject = findProject(firstSegment, contextProject);
			return getElementsWithDesiredProjectID(ImportSpecifierUtil.getMainModuleOfProject(targetProject),
					name.getFirstSegment());
		case COMPLETE_IMPORT:
			return getElementsWithDesiredProjectID(name.skipFirst(1), name.getFirstSegment());
		case SIMPLE_IMPORT:
			return parent.getElements(name);

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
	 * This method asks {@link #delegate} for elements matching provided <code>moduleSpecifier</code>. Returned results
	 * are filtered by expected {@link IN4JSProject#getProjectId()}.
	 */
	private Collection<IEObjectDescription> getElementsWithDesiredProjectID(QualifiedName moduleSpecifier,
			String projectId) {
		final Iterable<IEObjectDescription> moduleSpecifierMatchesWithPossibleDuplicates = delegate
				.getElements(moduleSpecifier);

		// delegate may return multiple entries since it allows duplication (normal 'shadowing' of scopes is not
		// applied). We filter duplicates by uniqueness of target EObject URI.
		final Map<String, IEObjectDescription> result = new HashMap<>();
		for (IEObjectDescription desc : moduleSpecifierMatchesWithPossibleDuplicates) {
			final IN4JSProject containingProject = n4jsCore.findProject(desc.getEObjectURI()).orNull();
			if (containingProject != null && projectId.equals(containingProject.getProjectId())) {
				result.put(desc.getEObjectURI().toString(), desc);
			}
		}
		return result.values();
	}

	private IN4JSProject findProject(String projectId, IN4JSProject project) {
		if (Objects.equals(project.getProjectId(), projectId)) {
			return project;
		}
		for (IN4JSProject p : project.getDependencies()) {
			if (Objects.equals(p.getProjectId(), projectId)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Convenience method over {@link ImportSpecifierUtil#computeImportType(QualifiedName, boolean, IN4JSProject)}
	 */
	private ImportType computeImportType(QualifiedName name, IN4JSProject project) {
		final String firstSegment = name.getFirstSegment();
		final IN4JSProject targetProject = findProject(firstSegment, project);
		final boolean firstSegmentIsProjectId = targetProject != null;
		return ImportSpecifierUtil.computeImportType(name, firstSegmentIsProjectId, targetProject);
	}

}
