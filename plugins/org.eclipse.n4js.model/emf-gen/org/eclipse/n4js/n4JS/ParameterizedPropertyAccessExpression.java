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

import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Property Access Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getPropertyAsText <em>Property As Text</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getParameterizedPropertyAccessExpression()
 * @model
 * @generated
 */
public interface ParameterizedPropertyAccessExpression extends Expression, MemberAccess, ParameterizedAccess {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Target (aka receiver)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getParameterizedPropertyAccessExpression_Target()
	 * @model containment="true"
	 * @generated
	 */
	Expression getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Expression value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' reference.
	 * @see #setProperty(IdentifiableElement)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getParameterizedPropertyAccessExpression_Property()
	 * @model
	 * @generated
	 */
	IdentifiableElement getProperty();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getProperty <em>Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' reference.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(IdentifiableElement value);

	/**
	 * Returns the value of the '<em><b>Property As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property As Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property As Text</em>' attribute.
	 * @see #setPropertyAsText(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getParameterizedPropertyAccessExpression_PropertyAsText()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getPropertyAsText();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getPropertyAsText <em>Property As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property As Text</em>' attribute.
	 * @see #getPropertyAsText()
	 * @generated
	 */
	void setPropertyAsText(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValidSimpleAssignmentTarget();

} // ParameterizedPropertyAccessExpression
