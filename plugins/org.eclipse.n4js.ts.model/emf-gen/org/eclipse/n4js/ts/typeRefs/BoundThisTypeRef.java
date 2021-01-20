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

import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bound This Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Reference to this type actually bound to a concrete type,
 * this is only done by the type system and not by a user declaration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getActualThisTypeRef <em>Actual This Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getBoundThisTypeRef()
 * @model
 * @generated
 */
public interface BoundThisTypeRef extends ThisTypeRef, StructuralTypeRef {
	/**
	 * Returns the value of the '<em><b>Actual This Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actual This Type Ref</em>' containment reference.
	 * @see #setActualThisTypeRef(ParameterizedTypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getBoundThisTypeRef_ActualThisTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	ParameterizedTypeRef getActualThisTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getActualThisTypeRef <em>Actual This Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actual This Type Ref</em>' containment reference.
	 * @see #getActualThisTypeRef()
	 * @generated
	 */
	void setActualThisTypeRef(ParameterizedTypeRef value);

	/**
	 * Returns the value of the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.ts.types.TypingStrategy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The defined (declared or inferred) typing strategy on use site, nominal typing by default. Could be changed to structural or structural
	 * field (via tilde or tilde-tilde operators).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Defined Typing Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypingStrategy
	 * @see #setDefinedTypingStrategy(TypingStrategy)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getBoundThisTypeRef_DefinedTypingStrategy()
	 * @model unique="false"
	 * @generated
	 */
	TypingStrategy getDefinedTypingStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Typing Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypingStrategy
	 * @see #getDefinedTypingStrategy()
	 * @generated
	 */
	void setDefinedTypingStrategy(TypingStrategy value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the actual typing strategy, that is either the defined typing strategy of the reference, or the typing strategy of the
	 * actual this type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypingStrategy getTypingStrategy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model typingStrategyUnique="false"
	 * @generated
	 */
	void setTypingStrategy(TypingStrategy typingStrategy);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#internalGetTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	ParameterizedTypeRef getDeclaredUpperBound();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if typingStrategy of the declared type is STRUCTURAL
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isDefSiteStructuralTyping();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if defined typing strategy neither DEFAULT nor NOMINAL.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isUseSiteStructuralTyping();

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
	 * Override VersionedElement#getVersion() to return the version of the actual this type reference.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	int getVersion();

} // BoundThisTypeRef
