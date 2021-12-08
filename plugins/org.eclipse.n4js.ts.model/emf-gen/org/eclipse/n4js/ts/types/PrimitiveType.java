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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Primitive Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Primitive types are modeled similar to TClassifiers, except that they have no access modifier and cannot have members.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.PrimitiveType#getAssignmentCompatible <em>Assignment Compatible</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.PrimitiveType#getAutoboxedType <em>Autoboxed Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getPrimitiveType()
 * @model
 * @generated
 */
public interface PrimitiveType extends GenericType, ArrayLike {
	/**
	 * Returns the value of the '<em><b>Assignment Compatible</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies a type to and from which this type is assignment compatible.
	 * Only used for special stringish types such as typename or pathSelector,
	 * in these cases {@code string} is the value of this field.
	 * Note however that this relation is not bidirectional, that is string does not
	 * know which other types may be assignment compatible. During type inferencing
	 * and other tests the client has to check that.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Assignment Compatible</em>' reference.
	 * @see #setAssignmentCompatible(PrimitiveType)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getPrimitiveType_AssignmentCompatible()
	 * @model
	 * @generated
	 */
	PrimitiveType getAssignmentCompatible();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.PrimitiveType#getAssignmentCompatible <em>Assignment Compatible</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assignment Compatible</em>' reference.
	 * @see #getAssignmentCompatible()
	 * @generated
	 */
	void setAssignmentCompatible(PrimitiveType value);

	/**
	 * Returns the value of the '<em><b>Autoboxed Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * the corresponding object type whose properties are also available to the primitive type
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Autoboxed Type</em>' reference.
	 * @see #setAutoboxedType(TClassifier)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getPrimitiveType_AutoboxedType()
	 * @model
	 * @generated
	 */
	TClassifier getAutoboxedType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.PrimitiveType#getAutoboxedType <em>Autoboxed Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Autoboxed Type</em>' reference.
	 * @see #getAutoboxedType()
	 * @generated
	 */
	void setAutoboxedType(TClassifier value);

} // PrimitiveType
