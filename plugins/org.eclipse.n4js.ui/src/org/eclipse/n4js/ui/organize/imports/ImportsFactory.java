/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.organize.imports;

import java.util.function.BiFunction;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.scoping.utils.ImportSpecifierUtil;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

/**
 * Helper for creating imports declarations.
 */
public class ImportsFactory {
	private final static N4JSFactory N4JS_FACTORY = N4JSFactory.eINSTANCE;
	private final static TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;
	@Inject
	private IN4JSCore core;

	/** Create import declaration for provided import object. */
	public ImportDeclaration createImport(ImportableObject imp, IN4JSProject contextProject, Adapter nodelessMarker) {
		// can be namespace and default, check order :: namespace -> default -> named
		if (imp.isAsNamespace())
			return createNamespaceImport(imp.getName(), contextProject, imp.getTe(), nodelessMarker);

		if (imp.isExportedAsDefault())
			return createDefaultImport(imp.getName(), contextProject, imp.getTe(), nodelessMarker);

		return createNamedImport(imp.getName(), contextProject, imp.getTe(), nodelessMarker);
	}

	/** Creates a new named import of 'name' from 'module' */
	private ImportDeclaration createNamedImport(String name, IN4JSProject contextProject, TExportableElement te,
			Adapter nodelessMarker) {
		TModule tmodule = te.getContainingModule();
		IN4JSProject targetProject = core.findProject(te.eResource().getURI()).orNull();
		String moduleQN;
		if (targetProject != null && tmodule.getQualifiedName().toString().equals(targetProject.getMainModule())) {
			// If the project has a main module, use project import instead.
			moduleQN = targetProject.getProjectName().getRawName();
		} else {
			// Standard case
			moduleQN = te.getContainingModule().getQualifiedName();
		}
		QualifiedName qn = qualifiedNameConverter.toQualifiedName(moduleQN);
		N4JSProjectName firstSegment = new N4JSProjectName(qn.getFirstSegment());
		IN4JSProject project = ImportSpecifierUtil.getDependencyWithID(firstSegment, contextProject);

		return createImportDeclaration(qn, name, project, nodelessMarker, this::addNamedImport);
	}

	/** Creates a new default import with name 'name' from object description. */
	private ImportDeclaration createDefaultImport(String name, IN4JSProject contextProject, TExportableElement te,
			Adapter nodelessMarker) {
		String moduleQN = te.getContainingModule().getQualifiedName();
		QualifiedName qn = qualifiedNameConverter.toQualifiedName(moduleQN);
		N4JSProjectName firstSegment = new N4JSProjectName(qn.getFirstSegment());
		IN4JSProject project = ImportSpecifierUtil.getDependencyWithID(firstSegment, contextProject);

		return createImportDeclaration(qn, name, project, nodelessMarker, this::addDefaultImport);
	}

	/** Creates a new default import with name 'name' from object description. */
	private ImportDeclaration createNamespaceImport(String name, IN4JSProject contextProject,
			TExportableElement te,
			Adapter nodelessMarker) {
		String moduleQN = te.getContainingModule().getQualifiedName();
		QualifiedName qn = qualifiedNameConverter.toQualifiedName(moduleQN);
		N4JSProjectName firstSegment = new N4JSProjectName(qn.getFirstSegment());

		IN4JSProject project = ImportSpecifierUtil.getDependencyWithID(firstSegment, contextProject);
		if (project == null) {
			IN4JSProject projectByNamespace = ImportSpecifierUtil.getDependencyWithID(new N4JSProjectName(name),
					contextProject);
			IN4JSProject projectByEObject = core.findProject(te.eResource().getURI()).orNull();

			if (projectByNamespace != null && projectByEObject != null
					&& projectByNamespace.getLocation().equals(projectByEObject.getLocation()))
				project = projectByNamespace;
		}
		return createImportDeclaration(qn, name, project, nodelessMarker, this::addNamespaceImport);
	}

	@SuppressWarnings("null")
	/** If project is {@code null} then the we will use {@link #SIMPLE_IMPORT} which is not using project data. */
	private ImportDeclaration createImportDeclaration(QualifiedName qn, String usedName, IN4JSProject fromProject,
			Adapter nodelessMarker,
			BiFunction<String, ImportDeclaration, ImportDeclaration> specifierFactory) {

		boolean considerProjectName = fromProject != null;
		switch (ImportSpecifierUtil.computeImportType(qn, considerProjectName, fromProject)) {
		case PROJECT:
			return specifierFactory.apply(usedName,
					createImportDeclaration(nodelessMarker, fromProject.getProjectName().getRawName()));
		case PLAIN:
			return specifierFactory.apply(usedName,
					createImportDeclaration(nodelessMarker, qualifiedNameConverter.toString(qn)));
		case COMPLETE:
			return specifierFactory.apply(usedName, createImportDeclaration(nodelessMarker,
					fromProject.getProjectName() + N4JSQualifiedNameConverter.DELIMITER +
							qualifiedNameConverter.toString(qn)));
		default:
			throw new RuntimeException("Cannot resolve default import for " + usedName);
		}
	}

	private ImportDeclaration createImportDeclaration(Adapter nodelessMarker, String moduleName) {
		ImportDeclaration ret = N4JS_FACTORY.createImportDeclaration();
		TModule tmodule = TYPES_FACTORY.createTModule();
		tmodule.setQualifiedName(moduleName);
		ret.setModule(tmodule);
		ret.eAdapters().add(nodelessMarker);
		return ret;
	}

	private ImportDeclaration addNamedImport(String name, ImportDeclaration importDeclaration) {
		NamedImportSpecifier namedImportSpec = N4JS_FACTORY.createNamedImportSpecifier();
		TExportableElement importetElement = TYPES_FACTORY.createTExportableElement();
		importetElement.setName(name);
		namedImportSpec.setImportedElement(importetElement);
		importDeclaration.getImportSpecifiers().add(namedImportSpec);
		importDeclaration.setImportFrom(true);
		return importDeclaration;
	}

	private ImportDeclaration addDefaultImport(String name, ImportDeclaration importDeclaration) {
		DefaultImportSpecifier defaultImportSpec = N4JS_FACTORY.createDefaultImportSpecifier();
		TExportableElement importetElement = TYPES_FACTORY.createTExportableElement();
		importetElement.setName(name);
		defaultImportSpec.setImportedElement(importetElement);
		importDeclaration.getImportSpecifiers().add(defaultImportSpec);
		importDeclaration.setImportFrom(true);
		return importDeclaration;
	}

	private ImportDeclaration addNamespaceImport(String name, ImportDeclaration importDeclaration) {
		NamespaceImportSpecifier namespaceImportSpec = N4JS_FACTORY.createNamespaceImportSpecifier();
		ModuleNamespaceVirtualType namespace = TYPES_FACTORY.createModuleNamespaceVirtualType();
		namespaceImportSpec.setAlias(name);
		namespaceImportSpec.setDefinedType(namespace);
		importDeclaration.getImportSpecifiers().add(namespaceImportSpec);
		importDeclaration.setImportFrom(true);
		return importDeclaration;
	}

}
