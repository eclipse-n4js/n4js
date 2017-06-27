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
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4JSFactory
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.emf.common.notify.Adapter
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

/**
 * Helper for creating imports declarations.
 */
class ImportsFactory {
	private final static N4JSFactory N4JS_FACTORY = N4JSFactory::eINSTANCE;
	private final static TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	/** Create import declaration for provided import object. */
	def createImport(ImportableObject imp, Adapter nodelessMarker) {
		if (imp.isExportedAsDefault)
			createDefaultImport(imp.name, imp.eobj.qualifiedName.skipLast(1), nodelessMarker)
		else
			createNamedImport(imp.name, imp.eobj.qualifiedName.skipLast(1), nodelessMarker)
	}

	/** For each name in names create a new ImportDeclaration using the Module from declaration. */
	def createNamedImports(ImportDeclaration declaration, Iterable<String> names, Adapter nodelessMarker) {
		names.map[createNamedImport(it, declaration.module.qualifiedName, nodelessMarker)]
	}

	/** Creates a new named import of 'name' from 'module'*/
	private def ImportDeclaration createNamedImport(String name, QualifiedName module, Adapter nodelessMarker) {
		return createNamedImport(name, qualifiedNameConverter.toString(module), nodelessMarker)
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

	/** Creates a new default import with name 'name' from 'module'*/
	private def ImportDeclaration createDefaultImport(String name, QualifiedName module, Adapter nodelessMarker) {
		return createDefaultImport(name, qualifiedNameConverter.toString(module), nodelessMarker)
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

}
