/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedExportSpecifier
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.ts.types.AbstractModule
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.ExportDefinition
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TExportableElement
import org.eclipse.n4js.ts.types.TExportingElement
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.xtext.EcoreUtil2

/**
 * Does not create new types but instead creates {@link ExportDefinition}s for {@link ExportDeclaration}s.
 */
class N4JSExportDefinitionTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper

	def package boolean relinkExportDefinition(ExportDeclaration exportDecl, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		// FIXME implement this!
		return true;
	}

	def package void createExportDefinition(ExportDeclaration exportDecl, AbstractNamespace target, boolean preLinkingPhase) {
		val directlyExportedElem = exportDecl.exportedElement;
		if (directlyExportedElem !== null) {
			if (directlyExportedElem instanceof ExportedVariableStatement) {
				return; // FIXME could we move the variable special case to here???
			}
			createExportDefinitionForDirectlyExportedElement(directlyExportedElem, target, preLinkingPhase);
		} else if (exportDecl.namespaceExport !== null) {
			val exportSpec = exportDecl.namespaceExport;
			val exportedModuleProxy = exportDecl.eGet(N4JSPackage.eINSTANCE.moduleRef_Module, false) as AbstractModule;
			if (exportedModuleProxy !== null) {
				val exportedModuleProxyCopy = EcoreUtil2.cloneWithProxies(exportedModuleProxy);
				val alias = exportSpec.alias;
				if (alias !== null) {
					val mnvt = target.containingRootModule.addNewModuleNamespaceVirtualType(alias, exportedModuleProxy, false, exportSpec);
					addElementExportDefinition(target, alias, mnvt);
				} else {
					addModuleExportDefinition(target, exportedModuleProxyCopy);
				}
			}
		} else {
			for (NamedExportSpecifier exportSpec : exportDecl.namedExports) {
				val idRef = exportSpec.element;
				if (idRef !== null) {
					val idProxy = idRef.eGet(N4JSPackage.eINSTANCE.identifierRef_Id, false) as IdentifiableElement;
					val exportedElemProxy = TypesFactory.eINSTANCE.createTExportableElement();
					(exportedElemProxy as InternalEObject).eSetProxyURI((idProxy as InternalEObject).eProxyURI());
					var declExpName = exportSpec.alias;
					if (declExpName === null && exportDecl.isReexport()) {
						// in case of re-exports, break the dependency on the other file by providing the exported name explicitly:
						declExpName = idRef.idAsText;
					}
					addElementExportDefinition(target, declExpName, exportedElemProxy);
				}
			}
		}
	}

	def package void createExportDefinitionForDirectlyExportedElement(ExportableElement directlyExportedElem, AbstractNamespace target, boolean preLinkingPhase) {
		val tDirectlyExportedElem = getExportedTypesModelElement(directlyExportedElem);
		if (tDirectlyExportedElem === null) {
			throw new IllegalStateException("types builder for directly exported element must be invoked before the export definition types builder");
		}
		createExportDefinitionForDirectlyExportedElement(tDirectlyExportedElem, directlyExportedElem.exportedName, target, preLinkingPhase);
	}

	def package void createExportDefinitionForDirectlyExportedElement(TExportableElement tDirectlyExportedElem, String declaredExportedName, AbstractNamespace target, boolean preLinkingPhase) {
		tDirectlyExportedElem.directlyExported = true;
		addElementExportDefinition(target, declaredExportedName, tDirectlyExportedElem);
	}

	def private void addModuleExportDefinition(TExportingElement exportingElem, AbstractModule exportedModule) {
		val expDef = TypesFactory.eINSTANCE.createModuleExportDefinition();
		expDef.exportedModule = exportedModule;
		exportingElem.exportDefinitions += expDef;
	}

	def private ExportDefinition addElementExportDefinition(TExportingElement exportingElem, String declaredExportedName, TExportableElement exportedElem) {
		val expDef = TypesFactory.eINSTANCE.createElementExportDefinition();
		if (declaredExportedName !== null
				&& (exportedElem.eIsProxy() || declaredExportedName != exportedElem.name)) {
			expDef.declaredExportedName = declaredExportedName;
		}
		expDef.exportedElement = exportedElem;
		exportingElem.exportDefinitions += expDef;
		return expDef;
	}

	def private TExportableElement getExportedTypesModelElement(ExportableElement n4ExportableElem) {
		return switch n4ExportableElem {
			TypeDefiningElement: n4ExportableElem.definedType
		};
	}
}
