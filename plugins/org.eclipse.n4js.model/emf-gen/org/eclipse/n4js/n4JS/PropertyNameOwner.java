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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Name Owner</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base for all entities that can have a literal or computed property name (see grammar rule
 * LiteralOrComputedPropertyName).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.PropertyNameOwner#getDeclaredName <em>Declared Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameOwner()
 * @model abstract="true"
 * @generated
 */
public interface PropertyNameOwner extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Name</em>' containment reference.
	 * @see #setDeclaredName(LiteralOrComputedPropertyName)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameOwner_DeclaredName()
	 * @model containment="true"
	 * @generated
	 */
	LiteralOrComputedPropertyName getDeclaredName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.PropertyNameOwner#getDeclaredName <em>Declared Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Name</em>' containment reference.
	 * @see #getDeclaredName()
	 * @generated
	 */
	void setDeclaredName(LiteralOrComputedPropertyName value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Convenience method; same as {@link LiteralOrComputedPropertyName#getName()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Convenience method; same as {@link LiteralOrComputedPropertyName#hasComputedPropertyName()}.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean hasComputedPropertyName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Used to detect early errors according the the ES6 spec.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValidName();

} // PropertyNameOwner
