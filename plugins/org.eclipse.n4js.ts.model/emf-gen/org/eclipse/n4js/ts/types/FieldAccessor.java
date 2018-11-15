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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field Accessor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for getter or setter, either of an ObjectLiteral or a Class
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.FieldAccessor#isOptional <em>Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.FieldAccessor#isDeclaredAbstract <em>Declared Abstract</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.FieldAccessor#getDeclaredThisType <em>Declared This Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getFieldAccessor()
 * @model abstract="true"
 * @generated
 */
public interface FieldAccessor extends TMemberWithAccessModifier {
	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getFieldAccessor_Optional()
	 * @model unique="false"
	 * @generated
	 */
	boolean isOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.FieldAccessor#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #isOptional()
	 * @generated
	 */
	void setOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Abstract</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Abstract</em>' attribute.
	 * @see #setDeclaredAbstract(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getFieldAccessor_DeclaredAbstract()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredAbstract();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.FieldAccessor#isDeclaredAbstract <em>Declared Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Abstract</em>' attribute.
	 * @see #isDeclaredAbstract()
	 * @generated
	 */
	void setDeclaredAbstract(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared This Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared This Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared This Type</em>' containment reference.
	 * @see #setDeclaredThisType(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getFieldAccessor_DeclaredThisType()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredThisType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.FieldAccessor#getDeclaredThisType <em>Declared This Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared This Type</em>' containment reference.
	 * @see #getDeclaredThisType()
	 * @generated
	 */
	void setDeclaredThisType(TypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getDeclaredTypeRef();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if the method is either declared abstract or it is implicitly abstract, i.e. it is declared in a role and has no body.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAbstract();

} // FieldAccessor
