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
package org.eclipse.n4js.ts.types;

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.util.Variance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TypeVariable#isDeclaredCovariant <em>Declared Covariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TypeVariable#isDeclaredContravariant <em>Declared Contravariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TypeVariable#getDeclaredUpperBound <em>Declared Upper Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TypeVariable#getDefinedTypeVariable <em>Defined Type Variable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeVariable()
 * @model
 * @generated
 */
public interface TypeVariable extends Type {
	/**
	 * Returns the value of the '<em><b>Declared Covariant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Covariant</em>' attribute.
	 * @see #setDeclaredCovariant(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeVariable_DeclaredCovariant()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredCovariant();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TypeVariable#isDeclaredCovariant <em>Declared Covariant</em>}' attribute.
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
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeVariable_DeclaredContravariant()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredContravariant();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TypeVariable#isDeclaredContravariant <em>Declared Contravariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Contravariant</em>' attribute.
	 * @see #isDeclaredContravariant()
	 * @generated
	 */
	void setDeclaredContravariant(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Upper Bound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Upper Bound</em>' containment reference.
	 * @see #setDeclaredUpperBound(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeVariable_DeclaredUpperBound()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredUpperBound();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TypeVariable#getDeclaredUpperBound <em>Declared Upper Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Upper Bound</em>' containment reference.
	 * @see #getDeclaredUpperBound()
	 * @generated
	 */
	void setDeclaredUpperBound(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Defined Type Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If and only if this TypeVariable is an AST node (note: instances of TypeVariable are used for both the AST
	 * and in the TModule), then this property will point to the corresponding TypeVariable instance created in the
	 * TModule, similar to {@code TypeDefiningElement#definedType}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Defined Type Variable</em>' reference.
	 * @see #setDefinedTypeVariable(TypeVariable)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeVariable_DefinedTypeVariable()
	 * @model transient="true"
	 * @generated
	 */
	TypeVariable getDefinedTypeVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TypeVariable#getDefinedTypeVariable <em>Defined Type Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Type Variable</em>' reference.
	 * @see #getDefinedTypeVariable()
	 * @generated
	 */
	void setDefinedTypeVariable(TypeVariable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns this type variable's {@link org.eclipse.n4js.ts.types.util.Variance variance}. Always returns
	 * invariant, unless the type variable was explicitly declared on definition site to be co- or contravariant.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.types.Variance" unique="false"
	 * @generated
	 */
	Variance getVariance();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TypeVariable> getTypeVars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getTypeAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" upperBoundUnique="false"
	 * @generated
	 */
	String getTypeVariableAsString(TypeRef upperBound);

} // TypeVariable
