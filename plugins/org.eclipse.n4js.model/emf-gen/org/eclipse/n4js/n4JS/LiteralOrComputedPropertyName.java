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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Literal Or Computed Property Name</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getLiteralName <em>Literal Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getComputedName <em>Computed Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLiteralOrComputedPropertyName()
 * @model
 * @generated
 */
public interface LiteralOrComputedPropertyName extends EObject {
	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4JS.PropertyNameKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  This is set by the IAstFactory while parsing a property assignment.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.PropertyNameKind
	 * @see #setKind(PropertyNameKind)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLiteralOrComputedPropertyName_Kind()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	PropertyNameKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.PropertyNameKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(PropertyNameKind value);

	/**
	 * Returns the value of the '<em><b>Literal Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  The literal name given in the source code or <code>null</code> iff this is a computed property name.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Literal Name</em>' attribute.
	 * @see #setLiteralName(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLiteralOrComputedPropertyName_LiteralName()
	 * @model unique="false"
	 * @generated
	 */
	String getLiteralName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getLiteralName <em>Literal Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Literal Name</em>' attribute.
	 * @see #getLiteralName()
	 * @generated
	 */
	void setLiteralName(String value);

	/**
	 * Returns the value of the '<em><b>Computed Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Iff this is a computed property name and the expression is one of the special cases that can be evaluated at
	 * compile time, then during post-processing this will be set to the value of the expression, i.e. the computed name.
	 * @see ComputedNameProcessor
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Computed Name</em>' attribute.
	 * @see #setComputedName(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLiteralOrComputedPropertyName_ComputedName()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getComputedName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getComputedName <em>Computed Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Computed Name</em>' attribute.
	 * @see #getComputedName()
	 * @generated
	 */
	void setComputedName(String value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  The expression provided in the source code for computing the property name, or <code>null</code>.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLiteralOrComputedPropertyName_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Tells if this element has a name computed from an expression instead of a literal name given in the source code.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean hasComputedPropertyName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns either the literal name given in the source code or the computed name.
	 * In case of computed names, this method will return <code>null</code> if the expression is invalid (e.g.
	 * not a constant expression) or if it has not yet been evaluated (this happens during post-processing).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getName();

} // LiteralOrComputedPropertyName
