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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TypeVariable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Type Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4TypeVariable#getDefinedTypeVariable <em>Defined Type Variable</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4TypeVariable#isDeclaredCovariant <em>Declared Covariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4TypeVariable#isDeclaredContravariant <em>Declared Contravariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4TypeVariable#getDeclaredUpperBoundNode <em>Declared Upper Bound Node</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4TypeVariable()
 * @model
 * @generated
 */
public interface N4TypeVariable extends IdentifiableElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Defined Type Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Defined Type Variable</em>' reference.
	 * @see #setDefinedTypeVariable(TypeVariable)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4TypeVariable_DefinedTypeVariable()
	 * @model transient="true"
	 * @generated
	 */
	TypeVariable getDefinedTypeVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4TypeVariable#getDefinedTypeVariable <em>Defined Type Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Type Variable</em>' reference.
	 * @see #getDefinedTypeVariable()
	 * @generated
	 */
	void setDefinedTypeVariable(TypeVariable value);

	/**
	 * Returns the value of the '<em><b>Declared Covariant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Covariant</em>' attribute.
	 * @see #setDeclaredCovariant(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4TypeVariable_DeclaredCovariant()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredCovariant();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4TypeVariable#isDeclaredCovariant <em>Declared Covariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Covariant</em>' attribute.
	 * @see #isDeclaredCovariant()
	 * @generated
	 */
	void setDeclaredCovariant(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Contravariant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Contravariant</em>' attribute.
	 * @see #setDeclaredContravariant(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4TypeVariable_DeclaredContravariant()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredContravariant();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4TypeVariable#isDeclaredContravariant <em>Declared Contravariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Contravariant</em>' attribute.
	 * @see #isDeclaredContravariant()
	 * @generated
	 */
	void setDeclaredContravariant(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Upper Bound Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Upper Bound Node</em>' containment reference.
	 * @see #setDeclaredUpperBoundNode(TypeReferenceNode)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4TypeVariable_DeclaredUpperBoundNode()
	 * @model containment="true"
	 * @generated
	 */
	TypeReferenceNode<TypeRef> getDeclaredUpperBoundNode();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4TypeVariable#getDeclaredUpperBoundNode <em>Declared Upper Bound Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Upper Bound Node</em>' containment reference.
	 * @see #getDeclaredUpperBoundNode()
	 * @generated
	 */
	void setDeclaredUpperBoundNode(TypeReferenceNode<TypeRef> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getDeclaredUpperBound();

} // N4TypeVariable
