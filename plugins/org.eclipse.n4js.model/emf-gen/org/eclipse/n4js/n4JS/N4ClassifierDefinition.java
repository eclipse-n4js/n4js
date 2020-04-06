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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Classifier Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Abstract base class for n4 classifiers, that is types containing members such as fields or methods.
 * Note that not all types can contain any members, e.g., interfaces must not contain fields.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMembersRaw <em>Owned Members Raw</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4ClassifierDefinition()
 * @model abstract="true"
 * @generated
 */
public interface N4ClassifierDefinition extends N4TypeDefinition {
	/**
	 * Returns the value of the '<em><b>Owned Members Raw</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.N4MemberDeclaration}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Members directly defined in this classifier, i.e. w/o inherited members.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Members Raw</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4ClassifierDefinition_OwnedMembersRaw()
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<N4MemberDeclaration> getOwnedMembersRaw();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-members and callable constructors.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<N4MemberDeclaration> getOwnedMembers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns explicitly defined constructor of receiving class or <code>null</code> if none was defined.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	N4MethodDeclaration getOwnedCtor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns explicitly defined callable constructor of receiving class or <code>null</code> if none was defined.
	 * This is *not* the actual constructor but instead the function used for direct invocations in call expressions.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	N4MethodDeclaration getOwnedCallableCtor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-methods.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<N4MethodDeclaration> getOwnedMethods();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-fields.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<N4FieldDeclaration> getOwnedFields();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-getters.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<N4GetterDeclaration> getOwnedGetters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-setters.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<N4SetterDeclaration> getOwnedSetters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Derived, returns extended class (if any) and implemented or extended interfaces.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.types.ParameterizedTypeRefIterable" unique="false"
	 * @generated
	 */
	Iterable<ParameterizedTypeRef> getSuperClassifierRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Derived, returns implemented or extended interfaces.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.types.ParameterizedTypeRefIterable" unique="false"
	 * @generated
	 */
	Iterable<ParameterizedTypeRef> getImplementedOrExtendedInterfaceRefs();

} // N4ClassifierDefinition
