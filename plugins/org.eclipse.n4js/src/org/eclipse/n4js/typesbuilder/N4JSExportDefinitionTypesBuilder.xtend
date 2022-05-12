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
import java.util.Objects
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedExportSpecifier
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.ExportDefinition
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TExportableElement
import org.eclipse.n4js.ts.types.TExportingElement
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.n4js.n4JS.FunctionDefinition

/**
 * Does not create new types but instead creates {@link ExportDefinition}s for {@link ExportDeclaration}s.
 */
class N4JSExportDefinitionTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper

	// relinking export definitions not required
	// (see N4JSTypesBuilder#relinkTypes(EObject, AbstractNamespace, boolean, RelinkIndices) for details)

	def package void createExportDefinition(ExportDeclaration exportDecl, AbstractNamespace target, boolean preLinkingPhase) {
		val directlyExportedElem = exportDecl.exportedElement;
		if (directlyExportedElem !== null) {
			createExportDefinitionForDirectlyExportedElement(directlyExportedElem, target, preLinkingPhase);
		} else if (exportDecl.namespaceExport !== null) {
			val exportSpec = exportDecl.namespaceExport;
			val exportedModuleProxy = exportDecl.eGet(N4JSPackage.eINSTANCE.moduleRef_Module, false) as TModule;
			if (exportedModuleProxy !== null) {
				val exportedModuleProxyCopy = EcoreUtil2.cloneWithProxies(exportedModuleProxy);
				val alias = exportSpec.alias;
				if (alias !== null) {
					val mnvt = target.containingModule.addNewModuleNamespaceVirtualType(alias, exportedModuleProxy, false, exportSpec);
					addElementExportDefinition(target, alias, mnvt);
				} else {
					addModuleExportDefinition(target, exportedModuleProxyCopy);
				}
			}
		} else {
			for (NamedExportSpecifier exportSpec : exportDecl.namedExports) {
				val idRef = exportSpec.exportedElement;
				if (idRef !== null) {
					val expElemProxy = idRef.eGet(N4JSPackage.eINSTANCE.identifierRef_Id, false) as IdentifiableElement;
					if (expElemProxy !== null) {
						val expElemProxyCpy = TypesFactory.eINSTANCE.createTExportableElement();
						(expElemProxyCpy as InternalEObject).eSetProxyURI((expElemProxy as InternalEObject).eProxyURI());
						var expName = exportSpec.alias;
						if (expName === null) {
							// we do not use the name of the actually exported element here, because ...
							// 1) we only have a proxy to the exported element (i.e. expElemProxy) anyway and are not allowed
							//    to trigger proxy resolution in the types builder, so we cannot retrieve its name;
							// 2) the exported element might have been imported from another file under an alias and in that
							//    case the import specifier's alias will be the default exported name, not the element's name.
							expName = idRef.idAsText;
						}
						addElementExportDefinition(target, expName, expElemProxyCpy);
					}
				}
			}
		}
	}

	def package void createExportDefinitionForDirectlyExportedElement(ExportableElement directlyExportedElem, AbstractNamespace target, boolean preLinkingPhase) {
		if (directlyExportedElem instanceof VariableStatement) {
			// variable statements inherit from ExportableElement, but they do not actually represent
			// the element being exported (instead, the variable declarations represent those elements)
			// --> ignore them here
			return;
		}
		val tDirectlyExportedElem = getExportedTypesModelElement(directlyExportedElem);
		if (tDirectlyExportedElem === null) {
			return; // broken AST (e.g. class declaration with missing name)
		}
		createExportDefinitionForDirectlyExportedElement(tDirectlyExportedElem, directlyExportedElem.directlyExportedName, target, preLinkingPhase);
	}

	def package void createExportDefinitionForDirectlyExportedElement(TExportableElement tDirectlyExportedElem, String exportedName, AbstractNamespace target, boolean preLinkingPhase) {
		tDirectlyExportedElem.directlyExported = true;
		tDirectlyExportedElem.directlyExportedAsDefault = exportedName == N4JSLanguageConstants.EXPORT_DEFAULT_NAME;
		addElementExportDefinition(target, exportedName, tDirectlyExportedElem);
	}

	def private void addModuleExportDefinition(TExportingElement exportingElem, TModule exportedModule) {
		val expDef = TypesFactory.eINSTANCE.createModuleExportDefinition();
		expDef.exportedModule = exportedModule;
		exportingElem.exportDefinitions += expDef;
	}

	def private ExportDefinition addElementExportDefinition(TExportingElement exportingElem, String exportedName, TExportableElement exportedElem) {
		Objects.requireNonNull(exportingElem);
		Objects.requireNonNull(exportedName);
		Objects.requireNonNull(exportedElem);
		val expDef = TypesFactory.eINSTANCE.createElementExportDefinition();
		expDef.exportedName = exportedName;
		expDef.exportedElement = exportedElem;
		exportingElem.exportDefinitions += expDef;
		return expDef;
	}

	def private TExportableElement getExportedTypesModelElement(ExportableElement n4ExportableElem) {
		return switch n4ExportableElem {
			TypeDefiningElement: n4ExportableElem.definedType
			FunctionDefinition: n4ExportableElem.definedFunction
		};
	}
}
