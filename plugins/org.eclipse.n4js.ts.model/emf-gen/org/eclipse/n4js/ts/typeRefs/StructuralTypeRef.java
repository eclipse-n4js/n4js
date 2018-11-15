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
package org.eclipse.n4js.ts.typeRefs;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Structural Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * There are three use cases:
 * <ol>
 * <li>the StructuralTypeRef plays the role of an AST node,
 * <li>the StructuralTypeRef is a copy of an AST node (maybe modified),
 * <li>the StructuralTypeRef was created programmatically.
 * </ol>
 * The handling of structural members defined in with-clauses is a bit tricky and different across
 * these use cases. In case 1. the StructuralTypeRef may contain structural members in 'astStructuralMembers'
 * and the types builder will have created a TStructuralType in the module (referred to by property
 * 'structuralType'). In case 2. it will point to the TStructuralType in the module via 'structuralType'
 * (and useless copies of the original astStructuralMembers may be lying around in 'astStructuralMembers'
 * -> ignore them!). In case 3. it has only 'genStructuralMembers'.
 * <p>
 * When copying StructuralTypeRefs, the property 'astStructuralMembers' need not be copied because it only contains
 * members if the StructuralTypeRef is an AST node, but then the types builder will have created a TStructuralType
 * with the same members that is referred to via cross-reference property 'structuralType', so copying the value of
 * that cross-reference is sufficient. It is best to always use one of the copy methods in TypeUtils, i.e. TypeUtils#copy(),
 * TypeUtils#copyIfContained(), etc. because they take of that.
 * <p>
 * Note: copying StructuralTypeRefs directly with {@link EcoreUtil#copy(EObject)}, etc. won't do any harm; you just
 * have unnecessary members contained in your copied typeRef.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getAstStructuralMembers <em>Ast Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getStructuralType <em>Structural Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getGenStructuralMembers <em>Gen Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getPostponedSubstitutions <em>Postponed Substitutions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getStructuralTypeRef()
 * @model abstract="true"
 * @generated
 */
public interface StructuralTypeRef extends EObject {
	/**
	 * Returns the value of the '<em><b>Ast Structural Members</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TStructMember}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If and only if the StructuralTypeRef is used as an AST node, this property holds the structural
	 * members and 'genStructuralMembers' is empty and 'structuralType' is <code>null</code>.
	 * This occurs in a situation like this:
	 * <pre>
	 * class C { ... }
	 * var ~C with { number n; } myC_withN;
	 * </pre>
	 * Members should <b>never</b> be added to this property, except by the parser (in file N4JS.xtext).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ast Structural Members</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getStructuralTypeRef_AstStructuralMembers()
	 * @model containment="true"
	 * @generated
	 */
	EList<TStructMember> getAstStructuralMembers();

	/**
	 * Returns the value of the '<em><b>Structural Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If this StructuralTypeRef is used inside a TModule, this property <em>refers</em> to the TStructuralType that
	 * contains the structural members. In this case, properties 'astStructuralType' and 'genStructuralType'
	 * are empty.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Structural Type</em>' reference.
	 * @see #setStructuralType(TStructuralType)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getStructuralTypeRef_StructuralType()
	 * @model
	 * @generated
	 */
	TStructuralType getStructuralType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getStructuralType <em>Structural Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Structural Type</em>' reference.
	 * @see #getStructuralType()
	 * @generated
	 */
	void setStructuralType(TStructuralType value);

	/**
	 * Returns the value of the '<em><b>Gen Structural Members</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TStructMember}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If a StructuralTypeRef with additional structural members is to be generated programmatically <em>without
	 * having a TModule</em> at hand, then the structural members can be added to this property. If a TModule is
	 * available, it is better to create a TStructuralType and let the StructuralTypeRef refer to that via property
	 * 'structuralType'.
	 * <p>
	 * This property should be used only as a last resort if no containing TModule is available.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Gen Structural Members</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getStructuralTypeRef_GenStructuralMembers()
	 * @model containment="true"
	 * @generated
	 */
	EList<TStructMember> getGenStructuralMembers();

	/**
	 * Returns the value of the '<em><b>Postponed Substitutions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.typeRefs.TypeVariableMapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Postponed substitutions of type variables in the structural members of the receiving StructuralTypeRef.
	 * <p>
	 * When performing a type variable substitution on a StructuralTypeRef, we do not copy and change the
	 * structural members but instead postpone substitution until the types stored in the members (e.g. type
	 * of a field, return type of a method) are actually required and perform the substitution then. To do
	 * this, we store the mappings from type variables to type arguments at the time of the first substitution
	 * in this property and restore them into the rule environment when obtaining a type from a structural member.
	 * 	 * @see GenericsComputer#substTypeVariablesInStructuralMembers(RuleEnvironment,StructuralTypeRef)
	 * @see GenericsComputer#storePostponedSubstitutionsIn(RuleEnvironment,StructuralTypeRef)
	 * @see GenericsComputer#restorePostponedSubstitutionsFrom(RuleEnvironment,StructuralTypeRef)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Postponed Substitutions</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getStructuralTypeRef_PostponedSubstitutions()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeVariableMapping> getPostponedSubstitutions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the actual typing strategy.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypingStrategy getTypingStrategy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Sets the actual typing strategy, required for copy operation.
	 * <!-- end-model-doc -->
	 * @model typingStrategyUnique="false"
	 * @generated
	 */
	void setTypingStrategy(TypingStrategy typingStrategy);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns either the members of the structuralType (if non-null) or the astStructuralMembers
	 * (if non-empty) or the genStructuralMembers.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TStructMember> getStructuralMembers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true iff there exists a type variable mapping for 'typeVar' in property 'typeVarMappings'.
	 * <!-- end-model-doc -->
	 * @model unique="false" typeVarUnique="false"
	 * @generated
	 */
	boolean hasPostponedSubstitutionFor(TypeVariable typeVar);

} // StructuralTypeRef
