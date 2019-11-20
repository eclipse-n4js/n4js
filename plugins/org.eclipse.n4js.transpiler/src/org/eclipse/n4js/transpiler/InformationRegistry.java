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
package org.eclipse.n4js.transpiler;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.utils.ConcreteMembersOrderedForTranspiler;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;

import com.google.common.collect.HashMultimap;

/**
 * Data storage for meta-information that will be passed from one transformation to another and should not be stored in
 * the intermediate model. This means some transformations will add information to this registry and others will read
 * from it.
 */
public class InformationRegistry {

	private final HashMultimap<Tag, EObject> hmTagged = HashMultimap.create();
	private final Map<ImportDeclaration, TModule> importedModules = new HashMap<>();
	private final Map<N4TypeDeclaration, Type> originalDefinedTypes = new HashMap<>();
	private final Map<N4MemberDeclaration, TMember> originalDefinedMembers = new HashMap<>();
	private final Map<TClassifier, ConcreteMembersOrderedForTranspiler> cachedCMOFTs = new HashMap<>();

	/**
	 */
	private enum Tag {
		/** Tag for IM nodes that were, originally, an explicit super call. */
		explicitSuperCall,

		/** Tag for members which were consumed from an interface. */
		consumedFromInterface,

		/** Tag for members which were filled in by way of static polyfills. */
		staticlyPolyfilled,
	}

	/** Tells if the given call expression has been derived from an explicit super call within a constructor. */
	public boolean isExplicitSuperCall(ParameterizedCallExpression callExpr) {
		return isTaggedAs(Tag.explicitSuperCall, callExpr);
	}

	/** Marks given call expression as being derived from an explicit super call within a constructor. */
	public void markAsExplicitSuperCall(ParameterizedCallExpression callExpr) {
		tag(Tag.explicitSuperCall, callExpr);
	}

	/** Tells if the given member was consumed from an interface. */
	public boolean isConsumedFromInterface(N4MemberDeclaration element) {
		return isTaggedAs(Tag.consumedFromInterface, element);
	}

	/** Marks given member as being consumed from an interface. */
	public void markAsConsumedFromInterface(N4MemberDeclaration element) {
		tag(Tag.consumedFromInterface, element);
	}

	/** Tells if the given member was statically polyfilled. */
	public boolean isStaticlyPolyfilled(N4MemberDeclaration element) {
		return isTaggedAs(Tag.staticlyPolyfilled, element);
	}

	/** Marks given member as statically polyfilled. */
	public void markAsStaticlyPolyfilled(N4MemberDeclaration element) {
		tag(Tag.staticlyPolyfilled, element);
	}

	private boolean isTaggedAs(Tag tag, EObject element) {
		return hmTagged.containsEntry(tag, element);
	}

	private void tag(Tag tag, EObject element) {
		hmTagged.put(tag, element);
	}

	/** Return true if the element is an IM element. */
	public boolean isIntermediateModelElement(EObject eobj) {
		return TranspilerUtils.isIntermediateModelElement(eobj);
	}

	/**
	 * For {@link ImportDeclaration}s that do not have an original AST node (i.e. that were created by some
	 * transformation on-the-fly), this returns the imported module.
	 */
	public TModule getImportedModule(ImportDeclaration importDeclInIM) {
		return getImportedModule(importDeclInIM, true);
	}

	/**
	 * For {@link ImportDeclaration}s that do not have an original AST node (i.e. that were created by some
	 * transformation on-the-fly), this returns the imported module.
	 */
	public TModule getImportedModule(ImportDeclaration importDeclInIM, boolean assertIntermediateModel) {
		if (assertIntermediateModel) {
			TranspilerUtils.assertIntermediateModelElement(importDeclInIM);
		}
		return importedModules.get(importDeclInIM);
	}

	/**
	 * Sets the imported module for the given {@link ImportDeclaration} in the intermediate model. For import
	 * declarations that were created from the original AST, we expect the {@link PreparationStep} to set this value,
	 * for import declarations that were created by some transformation on-the-fly, we expect the transformation that
	 * creates such an import declaration to define the imported module via this method.
	 */
	public void setImportedModule(ImportDeclaration importDeclInIM, TModule module) {
		TranspilerUtils.assertIntermediateModelElement(importDeclInIM);
		setImportedModule_internal(importDeclInIM, module);
	}

	/**
	 * As {@link #setImportedModule(ImportDeclaration,TModule)}, but does not assert that <code>elementInIM</code> is
	 * actually contained in the intermediate model. Should only be called from {@link PreparationStep}.
	 */
	public void setImportedModule_internal(ImportDeclaration importDeclInIM, TModule module) {
		importedModules.put(importDeclInIM, module);
	}

	/** Convenience method for type-safe usage of {@link #getOriginalDefinedType(N4TypeDeclaration)}. */
	public TEnum getOriginalDefinedType(N4EnumDeclaration elementInIM) {
		return (TEnum) getOriginalDefinedType((N4TypeDeclaration) elementInIM);
	}

	/** Convenience method for type-safe usage of {@link #getOriginalDefinedType(N4TypeDeclaration)}. */
	public TClassifier getOriginalDefinedType(N4ClassifierDeclaration elementInIM) {
		return (TClassifier) getOriginalDefinedType((N4TypeDeclaration) elementInIM);
	}

	/** Convenience method for type-safe usage of {@link #getOriginalDefinedType(N4TypeDeclaration)}. */
	public TClass getOriginalDefinedType(N4ClassDeclaration elementInIM) {
		return (TClass) getOriginalDefinedType((N4TypeDeclaration) elementInIM);
	}

	/** Convenience method for type-safe usage of {@link #getOriginalDefinedType(N4TypeDeclaration)}. */
	public TInterface getOriginalDefinedType(N4InterfaceDeclaration elementInIM) {
		return (TInterface) getOriginalDefinedType((N4TypeDeclaration) elementInIM);
	}

	/**
	 * Returns the original TModule element, i.e. defined type, of the given type declaration in the intermediate model.
	 * <p>
	 * Currently, we only use this for N4TypeDeclarations - i.e. classes, interfaces and enums - but in the future other
	 * subclasses of {@link TypeDefiningElement} may be allowed as well.
	 */
	public Type getOriginalDefinedType(N4TypeDeclaration elementInIM) {
		TranspilerUtils.assertIntermediateModelElement(elementInIM);
		return originalDefinedTypes.get(elementInIM);
	}

	/**
	 * Sets the <em>original defined type</em> of the given type declaration in the intermediate model.
	 */
	public void setOriginalDefinedType(N4TypeDeclaration elementInIM, Type originalDefinedType) {
		TranspilerUtils.assertIntermediateModelElement(elementInIM);
		setOriginalDefinedType_internal(elementInIM, originalDefinedType);
	}

	/**
	 * As {@link #setOriginalDefinedType(N4TypeDeclaration, Type)}, but does not assert that <code>elementInIM</code> is
	 * actually contained in the intermediate model. Should only be called from {@link PreparationStep}.
	 */
	public void setOriginalDefinedType_internal(N4TypeDeclaration elementInIM, Type originalDefinedType) {
		originalDefinedTypes.put(elementInIM, originalDefinedType);
	}

	/**
	 * Returns the original TModule element, i.e. defined member, of the given member declaration in the intermediate
	 * model.
	 */
	public TMember getOriginalDefinedMember(N4MemberDeclaration elementInIM) {
		TranspilerUtils.assertIntermediateModelElement(elementInIM);
		return originalDefinedMembers.get(elementInIM);
	}

	/**
	 * Sets the <em>original defined member</em> of the given member declaration in the intermediate model.
	 */
	public void setOriginalDefinedMember(N4MemberDeclaration elementInIM, TMember originalDefinedMember) {
		TranspilerUtils.assertIntermediateModelElement(elementInIM);
		setOriginalDefinedMember_internal(elementInIM, originalDefinedMember);
	}

	/**
	 * As {@link #setOriginalDefinedMember(N4MemberDeclaration, TMember)}, but does not assert that
	 * <code>elementInIM</code> is actually contained in the intermediate model. Should only be called from
	 * {@link PreparationStep}.
	 */
	public void setOriginalDefinedMember_internal(N4MemberDeclaration elementInIM, TMember originalDefinedMember) {
		originalDefinedMembers.put(elementInIM, originalDefinedMember);
	}

	/**
	 * DO NOT USE THIS METHOD DIRECTLY, USE {@link TypeAssistant#getOrCreateCMOFT(TClassifier)} INSTEAD!
	 */
	public ConcreteMembersOrderedForTranspiler getCachedCMOFT(TClassifier classifierDecl) {
		return cachedCMOFTs.get(classifierDecl);
	}

	/**
	 * DO NOT USE THIS METHOD DIRECTLY, USE {@link TypeAssistant#getOrCreateCMOFT(TClassifier)} INSTEAD!
	 */
	public void cacheCMOFT(TClassifier classifierDecl, ConcreteMembersOrderedForTranspiler cmoft) {
		cachedCMOFTs.put(classifierDecl, cmoft);
	}
}
