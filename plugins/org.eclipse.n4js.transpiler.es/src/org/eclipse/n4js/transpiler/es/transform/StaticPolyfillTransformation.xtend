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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import java.util.Set
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassDefinition
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.organize.imports.ScriptDependencyResolver
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TEnumLiteral
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.StaticPolyfillHelper
import org.eclipse.xtext.EcoreUtil2

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 */
class StaticPolyfillTransformation extends Transformation {

	@Inject private StaticPolyfillHelper staticPolyfillHelper;

	/**
	 * While inserting members into classes, we add here the elements referenced in the initializer expressions, fpar
	 * default expressions and bodies.
	 */
	private Set<SymbolTableEntryOriginal> referencedElements = newHashSet;
	/**
	 * Each referenced element for which we add an import gets its own alias. This set is used to keep track of those
	 * aliases (mainly when creating a new, unique alias).
	 */
	private Set<String> referencedElementsAliases = newHashSet;


	override assertPreConditions() {
		// none
	}

	override assertPostConditions() {
		// none
	}

	override analyze() {
		// nothing
	}

	override transform() {
		val isAware = state.resource.script.isContainedInStaticPolyfillAware;
		if(isAware) {
			val fillingResource = staticPolyfillHelper.getStaticPolyfillResource(state.resource);
			if(fillingResource!==null) {
				referencedElements.clear();
				collectNodes(state.im, N4ClassDeclaration, false).forEach[doStaticPolyfilling];
				referencedElements.forEach[addImportIfRequired(fillingResource)];
			}
		}
	}

	def private void doStaticPolyfilling(N4ClassDeclaration classDecl) {
		val defCls = state.info.getOriginalDefinedType(classDecl);
		val filler = staticPolyfillHelper.getStaticPolyfill(defCls);
		if(filler!==null) {
			doStaticPolyfilling(classDecl, filler);
		}
	}

	def private void doStaticPolyfilling(N4ClassDeclaration classFilled, N4ClassDeclaration classFiller) {
		// fill additionally implemented interfaces
		val currentIfcs = classFilled.implementedInterfaceRefs.filter(ParameterizedTypeRef_IM).map[declaredType_IM].filter(TInterface).toSet;
		classFiller.implementedInterfaceRefs.map[typeRef].forEach[classFilled.insertImplementedInterface(it, currentIfcs)];
		// fill members
		classFiller.ownedMembers.forEach[classFilled.insertMember(it)];
	}

	def private void insertImplementedInterface(N4ClassDefinition classFilled, TypeRef ifcRefToBeInserted,
		Set<TInterface> currentIfcs) {
		val ifcType = ifcRefToBeInserted.declaredType;
		if(ifcType instanceof TInterface) {
			if(!currentIfcs.contains(ifcType)) { // avoid duplicates!
				val ifcSTE = getSymbolTableEntryOriginal(ifcType, true);
				classFilled.implementedInterfaceRefs += <ParameterizedTypeRef>_TypeReferenceNode(_ParameterizedTypeRef(ifcSTE));
			}
		}
	}

	/**
	 * Create and insert a copy of <code>memberToBeInserted</code> into <code>classFilled</code>.
	 *
	 * @param classFilled  the class declaration to be filled, must be contained in the intermediate model.
	 * @param memberToBeInserted  this member is expected to be contained in the AST of the filling resource, so this is
	 *            neither contained in the original AST (of the resource to compile) nor the intermediate model!
	 */
	def private void insertMember(N4ClassDefinition classFilled, N4MemberDeclaration memberToBeInserted) {
		val existing = classFilled.ownedMembers.findFirst[
			eClass===memberToBeInserted.eClass && name==memberToBeInserted.name
		];
		val copy = copyAlienElement(memberToBeInserted);
		// store elements that are referenced from within this member
		// (e.g. from within the body, from initializer expressions of fields, from default expressions of fpars, etc.)
		referencedElements += copy.eAllContents.filter(ReferencingElement_IM).map[rewiredTarget].filter(SymbolTableEntryOriginal).toList;
		if(existing!==null) {
			replace(existing, copy);
		} else {
			classFilled.ownedMembersRaw += copy;
		}
		state.info.setOriginalDefinedMember(copy, memberToBeInserted.definedTypeElement);
	}

	def private void addImportIfRequired(SymbolTableEntryOriginal ste, N4JSResource fillingResource) {
		if(ste.importSpecifier===null) {
			val originalTarget = ste.originalTarget;
			val isNested = originalTarget instanceof TMember || originalTarget instanceof TEnumLiteral;
			if(!isNested) {
				val isLocal = originalTarget.eResource === state.resource;
				if(!isLocal && ScriptDependencyResolver.shouldBeImported(fillingResource.module, originalTarget)) {
					addImport(ste, fillingResource);
				}
			}
		}
	}

	/**
	 * Add an import for the element represented by the given SymbolTableEntry in the intermediate model.
	 */
	def private void addImport(SymbolTableEntryOriginal ste, N4JSResource fillingResource) {
		val importedElement = ste.originalTarget;
		val isNamespace = importedElement instanceof ModuleNamespaceVirtualType;

		// obtain module from which we import importedElement
		val remoteModule = if(isNamespace) {
			// warning: in case of namespaces, importedElement resides in the TModule of the fillingResource!
			// -> so we cannot just get the containing TModule in this case
			(importedElement as ModuleNamespaceVirtualType).module
		} else {
			// standard case: just find the containing TModule of importedElement
			EcoreUtil2.getContainerOfType(importedElement, TModule)
		};

		// search original import specification (in original AST of fillingResource)
		val impSpecsForContainingModule = fillingResource.script.scriptElements.filter(ImportDeclaration)
				.filter[module===remoteModule].map[importSpecifiers].flatten;
		val impSpec_original = if(isNamespace) {
			impSpecsForContainingModule.filter(NamespaceImportSpecifier).findFirst[definedType===importedElement]
		} else {
			impSpecsForContainingModule.filter(NamedImportSpecifier).findFirst[it.importedElement===importedElement]
		};
		val impDecl_original = impSpec_original?.eContainer;

		// if all this was successful, go ahead and add the import ...
		if(impDecl_original!==null && impSpec_original!==null) {
			val alias = chooseNewUniqueAlias(impSpec_original.alias ?: ste.exportedName ?: "unnamed");
			val impSpec = if(isNamespace) {
				_NamespaceImportSpecifier(alias, true)
			} else {
				_NamedImportSpecifier(ste.exportedName, alias, true)
			};
			val impDecl = _ImportDecl(impSpec);
			state.im.scriptElements.add(0, impDecl);
			ste.name = alias;
			ste.importSpecifier = impSpec;
			state.tracer.setOriginalASTNode(impDecl, impDecl_original);
			state.tracer.setOriginalASTNode(impSpec, impSpec_original);
			// store the imported module in information registry
			// (required my ModuleWrappingTransformation)
			state.info.setImportedModule(impDecl, remoteModule);
		}
	}

	def private String chooseNewUniqueAlias(String baseName) {
		var String alias;
		var cnt = 0;
		do {
			val cntStr = if(cnt===0) "" else Integer.toString(cnt);
			alias = baseName + cntStr + "$polyfilled";
			cnt++;
		} while(referencedElementsAliases.contains(alias));
		referencedElementsAliases.add(alias);
		return alias;
	}

	def private static final String getAlias(ImportSpecifier impSpec) {
		return switch(impSpec) {
			NamedImportSpecifier: impSpec.alias
			NamespaceImportSpecifier: impSpec.alias
		};
	}
}
