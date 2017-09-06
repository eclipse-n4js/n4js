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
package org.eclipse.n4js.ui.organize.imports

import com.google.inject.Inject
import org.eclipse.emf.common.notify.Adapter
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4JSFactory
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.scoping.utils.ImportSpecifierUtil
import org.eclipse.n4js.ts.types.TExportableElement
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

/**
 * Helper for creating imports declarations.
 */
class ImportsFactory {
	private final static N4JSFactory N4JS_FACTORY = N4JSFactory::eINSTANCE;
	private final static TypesFactory TYPES_FACTORY = TypesFactory::eINSTANCE;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;
	@Inject
	private IN4JSCore core;

	/** Create import declaration for provided import object. */
	def createImport(ImportableObject imp, IN4JSProject contextProject, Adapter nodelessMarker) {
		//can be namespace and default, check order :: namespace -> default -> named
		if(imp.asNamespace)
			return createNamespaceImport(imp.name, contextProject, imp.te, nodelessMarker)
		if (imp.isExportedAsDefault)
			return createDefaultImport(imp.name, contextProject, imp.te, nodelessMarker)
		
		return createNamedImport(imp.name, contextProject, imp.te, nodelessMarker)
	}
	

	/** Creates a new named import of 'name' from 'module'*/
	private def ImportDeclaration createNamedImport(String name, IN4JSProject contextProject, TExportableElement te, Adapter nodelessMarker) {
		val moduleQN = te.containingModule.qualifiedName
		
		val qn = qualifiedNameConverter.toQualifiedName(moduleQN)
		val firstSegment = qn.getFirstSegment();
		val project = ImportSpecifierUtil.getDependencyWithID(firstSegment, contextProject)
		
		createImportDeclaration(qn, name, project, nodelessMarker)

		val considerProjectID = project !== null;
		switch (ImportSpecifierUtil.computeImportType( qn, considerProjectID, project)) {
			case PROJECT_IMPORT:
				return createNamedImport(name, project.projectId, nodelessMarker)
			case SIMPLE_IMPORT:
				return createNamedImport(name, qualifiedNameConverter.toString(qn), nodelessMarker)
			case COMPLETE_IMPORT:
				return createNamedImport(name,
					project.projectId + N4JSQualifiedNameConverter.DELIMITER +
						qualifiedNameConverter.toString(qn), nodelessMarker)
			default:
				throw new RuntimeException("Cannot resolve default import for " + name)
		}
	}
	
	private def ImportDeclaration createImportDeclaration(QualifiedName qn, String usedName, IN4JSProject fromProject,
		Adapter nodelessMarker) {
		val considerProjectID = fromProject !== null;
		switch (ImportSpecifierUtil.computeImportType( qn, considerProjectID, fromProject)) {
			case PROJECT_IMPORT:
				return createNamedImport(usedName, fromProject.projectId, nodelessMarker)
			case SIMPLE_IMPORT:
				return createNamedImport(usedName, qualifiedNameConverter.toString(qn), nodelessMarker)
			case COMPLETE_IMPORT:
				return createNamedImport(usedName, fromProject.projectId + N4JSQualifiedNameConverter.DELIMITER +
					qualifiedNameConverter.toString(qn), nodelessMarker)
			default:
				throw new RuntimeException("Cannot resolve default import for " + usedName)
		}
	}

	/** Creates a new default import with name 'name' from object description. */
	private def ImportDeclaration createDefaultImport(String name, IN4JSProject contextProject, TExportableElement te, Adapter nodelessMarker) {
		val moduleQN = te.containingModule.qualifiedName
		
		val qn = qualifiedNameConverter.toQualifiedName(moduleQN)

		
		val firstSegment = qn.getFirstSegment();
		val project = ImportSpecifierUtil.getDependencyWithID(firstSegment, contextProject)


		val considerProjectID = project !== null;
		switch (ImportSpecifierUtil.computeImportType( qn, considerProjectID, project)) {
			case PROJECT_IMPORT:
				return createDefaultImport(name, project.projectId, nodelessMarker)
			case SIMPLE_IMPORT:
				return createDefaultImport(name, qualifiedNameConverter.toString(qn), nodelessMarker)
			case COMPLETE_IMPORT:
				return createDefaultImport(name,
					project.projectId + N4JSQualifiedNameConverter.DELIMITER +
						qualifiedNameConverter.toString(qn), nodelessMarker)
			default:
				throw new RuntimeException("Cannot resolve default import for " + name)
		}
	}

	/** Creates a new default import with name 'name' from object description. */
	private def ImportDeclaration createNamespaceImport(String name, IN4JSProject contextProject, TExportableElement te, Adapter nodelessMarker) {
		val moduleQN = te.containingModule.qualifiedName
		
		val qn = qualifiedNameConverter.toQualifiedName(moduleQN)
		val firstSegment = qn.getFirstSegment();
		
		var  IN4JSProject project = null;
		val projectByNamespace = ImportSpecifierUtil.getDependencyWithID(name, contextProject)
		val projectByFirstSegment = ImportSpecifierUtil.getDependencyWithID(firstSegment, contextProject)
		val projectByEObject = core.findProject(te.eResource.URI).orNull
		
		if(projectByFirstSegment !== null){
			project = projectByFirstSegment
		}else{
			if(projectByNamespace !== null && projectByEObject !== null && projectByNamespace.location === projectByEObject.location){
				project = projectByNamespace
			}
		}
		
		val considerProjectID = project !== null;
		switch (ImportSpecifierUtil.computeImportType( qn, considerProjectID, project)) {
			case PROJECT_IMPORT:
				return createNamespaceImport(name, project.projectId, nodelessMarker)
			case SIMPLE_IMPORT:
				return createNamespaceImport(name, qualifiedNameConverter.toString(qn), nodelessMarker)
			case COMPLETE_IMPORT:
				return createNamespaceImport(name,
					project.projectId + N4JSQualifiedNameConverter.DELIMITER +
						qualifiedNameConverter.toString(qn), nodelessMarker)
			default:
				throw new RuntimeException("Cannot resolve default import for " + name)
		}
	}
	
	/** Creates a new named import of 'name' from 'moduleName'*/
	private def ImportDeclaration createNamedImport(String name, String moduleName, Adapter nodelessMarker) {
		val ret = N4JS_FACTORY.createImportDeclaration

		val namedImportSpec = N4JS_FACTORY.createNamedImportSpecifier
		val tmodule = TYPES_FACTORY.createTModule
		tmodule.qualifiedName = moduleName
		val idfEle = TYPES_FACTORY.createTExportableElement
		idfEle.name = name
		namedImportSpec.importedElement = idfEle

		ret.importSpecifiers.add(namedImportSpec)
		ret.eAdapters.add(nodelessMarker)
		ret.module = tmodule

		return ret
	}
	
	/** Creates a new default import with name 'name' from 'moduleName'*/
	private def ImportDeclaration createDefaultImport(String name, String moduleName, Adapter nodelessMarker) {
		val ret = N4JS_FACTORY.createImportDeclaration

		val defaultImportSpec = N4JS_FACTORY.createDefaultImportSpecifier
		val tmodule = TYPES_FACTORY.createTModule
		tmodule.qualifiedName = moduleName
		val idfEle = TYPES_FACTORY.createTExportableElement
		idfEle.name = name
		defaultImportSpec.importedElement = idfEle

		ret.importSpecifiers.add(defaultImportSpec)
		ret.eAdapters.add(nodelessMarker)
		ret.module = tmodule

		return ret
	}
	
	/** Creates a new named import of 'name' from 'moduleName'*/
	private def ImportDeclaration createNamespaceImport(String name, String moduleName, Adapter nodelessMarker) {
		val ret = N4JS_FACTORY.createImportDeclaration

		val namespaceImportSpec = N4JS_FACTORY.createNamespaceImportSpecifier
		val tmodule = TYPES_FACTORY.createTModule
		tmodule.qualifiedName = moduleName
		val idfEle = TYPES_FACTORY.createModuleNamespaceVirtualType
		namespaceImportSpec.alias = name
		namespaceImportSpec.definedType = idfEle

		ret.importSpecifiers.add(namespaceImportSpec)
		ret.eAdapters.add(nodelessMarker)
		ret.module = tmodule

		return ret
	}
}
