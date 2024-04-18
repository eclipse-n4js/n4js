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
package org.eclipse.n4js.typesbuilder;

import java.util.Objects;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedExportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceExportSpecifier;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.ElementExportDefinition;
import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.ModuleExportDefinition;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TExportingElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Inject;

/**
 * Does not create new types but instead creates {@link ExportDefinition}s for {@link ExportDeclaration}s.
 */
class N4JSExportDefinitionTypesBuilder {

	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	// relinking export definitions not required
	// (see N4JSTypesBuilder#relinkTypes(EObject, AbstractNamespace, boolean, RelinkIndices) for details)

	void createExportDefinition(ExportDeclaration exportDecl, AbstractNamespace target, boolean preLinkingPhase) {
		ExportableElement directlyExportedElem = exportDecl.getExportedElement();
		if (directlyExportedElem != null) {
			createExportDefinitionForDirectlyExportedElement(directlyExportedElem, target, preLinkingPhase);
		} else if (exportDecl.getNamespaceExport() != null) {
			NamespaceExportSpecifier exportSpec = exportDecl.getNamespaceExport();
			TModule exportedModuleProxy = (TModule) exportDecl.eGet(N4JSPackage.eINSTANCE.getModuleRef_Module(), false);
			if (exportedModuleProxy != null) {
				TModule exportedModuleProxyCopy = EcoreUtil2.cloneWithProxies(exportedModuleProxy);
				String alias = exportSpec.getAlias();
				if (alias != null) {
					ModuleNamespaceVirtualType mnvt = _n4JSTypesBuilderHelper.addNewModuleNamespaceVirtualType(
							target.getContainingModule(), alias, exportedModuleProxy, false, exportSpec);
					addElementExportDefinition(target, alias, mnvt);
				} else {
					addModuleExportDefinition(target, exportedModuleProxyCopy);
				}
			}
		} else {
			for (NamedExportSpecifier exportSpec : exportDecl.getNamedExports()) {
				IdentifierRef idRef = exportSpec.getExportedElement();
				if (idRef != null) {
					IdentifiableElement expElemProxy = (IdentifiableElement) idRef
							.eGet(N4JSPackage.eINSTANCE.getIdentifierRef_Id(), false);
					if (expElemProxy != null) {
						TExportableElement expElemProxyCpy = TypesFactory.eINSTANCE.createTExportableElement();
						((InternalEObject) expElemProxyCpy).eSetProxyURI(((InternalEObject) expElemProxy).eProxyURI());
						var expName = exportSpec.getAlias();
						if (expName == null) {
							// we do not use the name of the actually exported element here, because ...
							// 1) we only have a proxy to the exported element (i.e. expElemProxy) anyway and are not
							// allowed
							// to trigger proxy resolution in the types builder, so we cannot retrieve its name;
							// 2) the exported element might have been imported from another file under an alias and in
							// that
							// case the import specifier's alias will be the default exported name, not the element's
							// name.
							expName = idRef.getIdAsText();
						}
						addElementExportDefinition(target, expName, expElemProxyCpy);
					}
				}
			}
		}
	}

	void createExportDefinitionForDirectlyExportedElement(ExportableElement directlyExportedElem,
			AbstractNamespace target, boolean preLinkingPhase) {
		if (directlyExportedElem instanceof VariableStatement) {
			// variable statements inherit from ExportableElement, but they do not actually represent
			// the element being exported (instead, the variable declarations represent those elements)
			// --> ignore them here
			return;
		}
		TExportableElement tDirectlyExportedElem = getExportedTypesModelElement(directlyExportedElem);
		if (tDirectlyExportedElem == null) {
			return; // broken AST (e.g. class declaration with missing name)
		}
		createExportDefinitionForDirectlyExportedElement(tDirectlyExportedElem,
				directlyExportedElem.getDirectlyExportedName(), target, preLinkingPhase);
	}

	void createExportDefinitionForDirectlyExportedElement(TExportableElement tDirectlyExportedElem, String exportedName,
			AbstractNamespace target, @SuppressWarnings("unused") boolean preLinkingPhase) {
		tDirectlyExportedElem.setDirectlyExported(true);
		tDirectlyExportedElem
				.setDirectlyExportedAsDefault(N4JSLanguageConstants.EXPORT_DEFAULT_NAME.equals(exportedName));
		addElementExportDefinition(target, exportedName, tDirectlyExportedElem);
	}

	private void addModuleExportDefinition(TExportingElement exportingElem, TModule exportedModule) {
		ModuleExportDefinition expDef = TypesFactory.eINSTANCE.createModuleExportDefinition();
		expDef.setExportedModule(exportedModule);
		exportingElem.getExportDefinitions().add(expDef);
	}

	private ExportDefinition addElementExportDefinition(TExportingElement exportingElem, String exportedName,
			TExportableElement exportedElem) {
		Objects.requireNonNull(exportingElem);
		Objects.requireNonNull(exportedName);
		Objects.requireNonNull(exportedElem);
		ElementExportDefinition expDef = TypesFactory.eINSTANCE.createElementExportDefinition();
		expDef.setExportedName(exportedName);
		expDef.setExportedElement(exportedElem);
		exportingElem.getExportDefinitions().add(expDef);
		return expDef;
	}

	private TExportableElement getExportedTypesModelElement(ExportableElement n4ExportableElem) {
		if (n4ExportableElem instanceof TypeDefiningElement) {
			return ((TypeDefiningElement) n4ExportableElem).getDefinedType();
		}
		return null;
	}
}
