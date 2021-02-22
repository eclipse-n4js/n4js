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
package org.eclipse.n4js.transpiler.assistants

import com.google.inject.Inject
import java.util.List
import org.eclipse.n4js.n4JS.AnnotationList
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.transpiler.AbstractTranspiler
import org.eclipse.n4js.transpiler.InformationRegistry
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.transpiler.utils.ConcreteMembersOrderedForTranspiler
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TObjectPrototype
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.validation.JavaScriptVariantHelper

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
class TypeAssistant extends TransformationAssistant {

	@Inject private ContainerTypesHelper containerTypesHelper;
	@Inject private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * Some assertions related to {@link N4ClassifierDeclaration}s that apply to several transformations and are
	 * therefore factored out into this helper method.
	 */
	def public void assertClassifierPreConditions() {
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			val allClassifierDecls = collectNodes(state.im, N4ClassifierDeclaration, false);
			assertTrue("all classifier declarations must have an original defined type",
				allClassifierDecls.forall[state.info.getOriginalDefinedType(it)!==null]);
			assertTrue("all class declarations must have a superClassRef pointing to a STE with an original target (if non-null)",
				allClassifierDecls.filter(N4ClassDeclaration).map[superClassRef].filterNull.filter(ParameterizedTypeRef_IM)
				.forall[
					val originalDeclType = originalTargetOfRewiredTarget;
					return originalDeclType instanceof TClass || originalDeclType instanceof TObjectPrototype;
				]);
			assertTrue("all classifier declarations must have all implementedOrExtendedInterfaceRefs pointing to a STE with an original target",
				allClassifierDecls.map[implementedOrExtendedInterfaceRefs].flatten.filter(ParameterizedTypeRef_IM)
				.forall[
					val originalDeclType = originalTargetOfRewiredTarget;
					return originalDeclType instanceof TInterface;
				]);
			
		}
	}

	/**
	 * Same as {@link InformationRegistry#getOriginalProcessedTypeRef(TypeReferenceNode)}, but will return
	 * the 'typeRefInAST' contained in the given type reference node as a fall back.
	 */
	def public TypeRef getOriginalOrContainedTypeRef(TypeReferenceNode<?> typeRefNodeInIM) {
		var originalTypeRef = state.info.getOriginalProcessedTypeRef(typeRefNodeInIM);
		if (originalTypeRef !== null) {
			return originalTypeRef;
		}
		return typeRefNodeInIM.typeRefInAST;
	}

	// keep aligned to following method!
	def public SymbolTableEntryOriginal getOriginalDeclaredTypeSTE(TypeReferenceNode<?> typeRefNodeInIM) {
		val typeRef = getOriginalOrContainedTypeRef(typeRefNodeInIM);
		if (typeRef instanceof ParameterizedTypeRef_IM) {
			val rewiredTarget = typeRef.rewiredTarget;
			if (rewiredTarget instanceof SymbolTableEntryOriginal) {
				return rewiredTarget;
			}
		}
		val declType = typeRef?.declaredType;
		if (declType !== null) {
			return getSymbolTableEntryOriginal(declType, true);
		}
		return null;
	}

	// keep aligned to previous method!
	def public Type getOriginalDeclaredType(TypeReferenceNode<?> typeRefNodeInIM) {
		val typeRef = getOriginalOrContainedTypeRef(typeRefNodeInIM);
		if (typeRef instanceof ParameterizedTypeRef_IM) {
			val originalTarget = typeRef.originalTargetOfRewiredTarget as Type;
			if (originalTarget !== null) {
				return originalTarget;
			}
		}
		return typeRef?.declaredType;
	}

	/**
	 * Returns symbol table entry for super class of given class declaration.
	 */
	def public SymbolTableEntryOriginal getSuperClassSTE(N4ClassDeclaration classDecl) {
		val superClassRef = classDecl?.superClassRef;
		if (superClassRef !== null) {
			val superClassSTE = getOriginalDeclaredTypeSTE(superClassRef);
			if(superClassSTE !== null) {
				return superClassSTE;
			}
		}
		return getSymbolTableEntryOriginal(state.G.n4ObjectType, true);
	}

	/**
	 * Returns super interfaces (i.e. implemented or extended interfaces) of given classifier.
	 */
	def public List<SymbolTableEntryOriginal> getSuperInterfacesSTEs(N4ClassifierDeclaration classifierDecl) {
		val superIfcRefNodes = switch(classifierDecl) {
			N4ClassDeclaration: classifierDecl.implementedInterfaceRefs
			N4InterfaceDeclaration: classifierDecl.superInterfaceRefs
			default: throw new IllegalStateException("unsupported subclass of N4ClassifierDeclaration: " + classifierDecl?.name)
		}
		return superIfcRefNodes
			.map[getOriginalDeclaredTypeSTE(it)]
			.filterNull
			.toList;
	}

	/**
	 * Tells if the given classifier is declared on top level.
	 */
	def public boolean isTopLevel(TypeDefiningElement typeDef) {
		var parent = typeDef.eContainer;
		while(parent instanceof ExportDeclaration || parent instanceof AnnotationList) {
			parent = parent.eContainer;
		}
		return parent instanceof Script;
	}

	/**
	 * Tells if the given type is defined in an N4JSD file.
	 * <p>
	 * WARNING: for interfaces it is not enough to check {@link TInterface#isExternal()}, for this purpose,
	 * because structural interfaces in N4JSD files need not be declared external!
	 */
	def public boolean inN4JSD(Type type) {
		return jsVariantHelper.isExternalMode(type);
	}

	/**
	 * For a member name that represents a symbol, such as <code>#iterator</code>, this method will return a property
	 * access expression that will evaluate to the corresponding symbol, e.g. <code>Symbol.iterator</code>.
	 */
	def public ParameterizedPropertyAccessExpression_IM getMemberNameAsSymbol(String memberName) {
		if(!memberName.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX)) {
			throw new IllegalArgumentException("given member name does not denote a symbol");
		}
		return _PropertyAccessExpr(
			getSymbolTableEntryOriginal(state.G.symbolObjectType, true),
			getSymbolTableEntryInternal(memberName.substring(1), true)
		);
	}

	/**
	 * Returns an instance of {@link ConcreteMembersOrderedForTranspiler} for the given classifier, using a cached
	 * instance if available.
	 */
	def public ConcreteMembersOrderedForTranspiler getOrCreateCMOFT(TClassifier classifier) {
		val cachedCMOFT = state.info.getCachedCMOFT(classifier);
		if(cachedCMOFT!==null) {
			return cachedCMOFT;
		} else {
			val newCMOFT = ConcreteMembersOrderedForTranspiler.create(
				containerTypesHelper, classifier, state.resource.script);
			state.info.cacheCMOFT(classifier, newCMOFT);
			return newCMOFT;
		}
	}
}
