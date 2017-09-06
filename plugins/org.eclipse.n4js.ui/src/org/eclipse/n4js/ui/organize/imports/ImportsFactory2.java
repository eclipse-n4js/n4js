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
public class ImportsFactory2 {
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

		String moduleQN = te.getContainingModule().getQualifiedName();
		QualifiedName qn = qualifiedNameConverter.toQualifiedName(moduleQN);
		String firstSegment = qn.getFirstSegment();
		IN4JSProject project = ImportSpecifierUtil.getDependencyWithID(firstSegment, contextProject);

		return createImportDeclaration(qn, name, project, nodelessMarker, this::createNamedImport, this::adapt);

	}

	/** Creates a new default import with name 'name' from object description. */
	private ImportDeclaration createDefaultImport(String name, IN4JSProject contextProject, TExportableElement te,
			Adapter nodelessMarker) {
		String moduleQN = te.getContainingModule().getQualifiedName();
		QualifiedName qn = qualifiedNameConverter.toQualifiedName(moduleQN);
		String firstSegment = qn.getFirstSegment();
		IN4JSProject project = ImportSpecifierUtil.getDependencyWithID(firstSegment, contextProject);

		return createImportDeclaration(qn, name, project, nodelessMarker, this::createDefaultImport, this::adapt);
	}

	/** Creates a new default import with name 'name' from object description. */
	private ImportDeclaration createNamespaceImport(String name, IN4JSProject contextProject, TExportableElement te,
			Adapter nodelessMarker) {
		String moduleQN = te.getContainingModule().getQualifiedName();
		QualifiedName qn = qualifiedNameConverter.toQualifiedName(moduleQN);
		String firstSegment = qn.getFirstSegment();

		IN4JSProject project = ImportSpecifierUtil.getDependencyWithID(firstSegment, contextProject);

		if (project == null) {

			IN4JSProject projectByNamespace = ImportSpecifierUtil.getDependencyWithID(name, contextProject);
			IN4JSProject projectByEObject = core.findProject(te.eResource().getURI()).orNull();

			if (projectByNamespace != null && projectByEObject != null
					&& projectByNamespace.getLocation() == projectByEObject.getLocation())

				project = projectByNamespace;
		}
		return createImportDeclaration(qn, name, project, nodelessMarker, this::createNamespaceImport, this::adapt);
	}

	@SuppressWarnings("null")
	/** If project is {@code null} then the we will use {@link #SIMPLE_IMPORT} which is not using project data. */
	private ImportDeclaration createImportDeclaration(QualifiedName qn, String usedName, IN4JSProject fromProject,
			Adapter nodelessMarker,
			BiFunction<String, String, ImportDeclaration> factory,
			BiFunction<ImportDeclaration, Adapter, ImportDeclaration> adapter) {

		boolean considerProjectID = fromProject != null;
		switch (ImportSpecifierUtil.computeImportType(qn, considerProjectID, fromProject)) {
		case PROJECT_IMPORT:
			return adapter.apply(factory.apply(usedName, fromProject.getProjectId()), nodelessMarker);
		case SIMPLE_IMPORT:
			return adapter.apply(factory.apply(usedName, qualifiedNameConverter.toString(qn)), nodelessMarker);
		case COMPLETE_IMPORT:
			return adapter
					.apply(factory.apply(usedName, fromProject.getProjectId() + N4JSQualifiedNameConverter.DELIMITER +
							qualifiedNameConverter.toString(qn)), nodelessMarker);
		default:
			throw new RuntimeException("Cannot resolve default import for " + usedName);
		}
	}

	/** Adapts provided import declaration with the adapter and returns modified instance. */
	private ImportDeclaration adapt(ImportDeclaration id, Adapter nodelessMarker) {
		id.eAdapters().add(nodelessMarker);
		return id;
	}

	/** Creates a new named import of 'name' from 'moduleName' */
	private ImportDeclaration createNamedImport(String name, String moduleName) {
		ImportDeclaration ret = N4JS_FACTORY.createImportDeclaration();

		NamedImportSpecifier namedImportSpec = N4JS_FACTORY.createNamedImportSpecifier();
		TModule tmodule = TYPES_FACTORY.createTModule();
		tmodule.setQualifiedName(moduleName);
		TExportableElement idfEle = TYPES_FACTORY.createTExportableElement();
		idfEle.setExportedName(name);
		namedImportSpec.setImportedElement(idfEle);

		ret.getImportSpecifiers().add(namedImportSpec);
		ret.setModule(tmodule);

		return ret;
	}

	/** Creates a new default import with name 'name' from 'moduleName' */
	private ImportDeclaration createDefaultImport(String name, String moduleName) {
		ImportDeclaration ret = N4JS_FACTORY.createImportDeclaration();

		DefaultImportSpecifier defaultImportSpec = N4JS_FACTORY.createDefaultImportSpecifier();
		TModule tmodule = TYPES_FACTORY.createTModule();
		tmodule.setQualifiedName(moduleName);
		TExportableElement idfEle = TYPES_FACTORY.createTExportableElement();
		idfEle.setExportedName(name);
		defaultImportSpec.setImportedElement(idfEle);

		ret.getImportSpecifiers().add(defaultImportSpec);
		ret.setModule(tmodule);

		return ret;
	}

	/** Creates a new named import of 'name' from 'moduleName' */
	private ImportDeclaration createNamespaceImport(String name, String moduleName) {
		ImportDeclaration ret = N4JS_FACTORY.createImportDeclaration();

		NamespaceImportSpecifier namespaceImportSpec = N4JS_FACTORY.createNamespaceImportSpecifier();
		TModule tmodule = TYPES_FACTORY.createTModule();
		tmodule.setQualifiedName(moduleName);
		ModuleNamespaceVirtualType idfEle = TYPES_FACTORY.createModuleNamespaceVirtualType();
		namespaceImportSpec.setAlias(name);
		namespaceImportSpec.setDefinedType(idfEle);

		ret.getImportSpecifiers().add(namespaceImportSpec);
		ret.setModule(tmodule);

		return ret;
	}

}
