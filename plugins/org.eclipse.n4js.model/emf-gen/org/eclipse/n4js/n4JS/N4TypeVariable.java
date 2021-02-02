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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Type Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4TypeVariable#isDeclaredCovariant <em>Declared Covariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4TypeVariable#isDeclaredContravariant <em>Declared Contravariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4TypeVariable#getDeclaredUpperBound <em>Declared Upper Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4TypeVariable#getDeclaredUpperBoundInAST <em>Declared Upper Bound In AST</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4TypeVariable()
 * @model
 * @generated
 */
public interface N4TypeVariable extends TypeDefiningElement, IdentifiableElement, NamedElement {
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
	 * Returns the value of the '<em><b>Declared Upper Bound</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Upper Bound</em>' reference.
	 * @see #setDeclaredUpperBound(TypeRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4TypeVariable_DeclaredUpperBound()
	 * @model transient="true"
	 * @generated
	 */
	TypeRef getDeclaredUpperBound();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4TypeVariable#getDeclaredUpperBound <em>Declared Upper Bound</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Upper Bound</em>' reference.
	 * @see #getDeclaredUpperBound()
	 * @generated
	 */
	void setDeclaredUpperBound(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Declared Upper Bound In AST</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Upper Bound In AST</em>' containment reference.
	 * @see #setDeclaredUpperBoundInAST(TypeRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4TypeVariable_DeclaredUpperBoundInAST()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredUpperBoundInAST();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4TypeVariable#getDeclaredUpperBoundInAST <em>Declared Upper Bound In AST</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Upper Bound In AST</em>' containment reference.
	 * @see #getDeclaredUpperBoundInAST()
	 * @generated
	 */
	void setDeclaredUpperBoundInAST(TypeRef value);

} // N4TypeVariable
