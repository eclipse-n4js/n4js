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

import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TMember;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Field Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#getDefinedField <em>Defined Field</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#isDeclaredOptional <em>Declared Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4FieldDeclaration()
 * @model
 * @generated
 */
public interface N4FieldDeclaration extends AnnotableN4MemberDeclaration, TypedElement, ThisArgProvider, PropertyNameOwner {
	/**
	 * Returns the value of the '<em><b>Defined Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Defined Field</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Defined Field</em>' reference.
	 * @see #setDefinedField(TField)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4FieldDeclaration_DefinedField()
	 * @model transient="true"
	 * @generated
	 */
	TField getDefinedField();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#getDefinedField <em>Defined Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Field</em>' reference.
	 * @see #getDefinedField()
	 * @generated
	 */
	void setDefinedField(TField value);

	/**
	 * Returns the value of the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Optional</em>' attribute.
	 * @see #setDeclaredOptional(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4FieldDeclaration_DeclaredOptional()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#isDeclaredOptional <em>Declared Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Optional</em>' attribute.
	 * @see #isDeclaredOptional()
	 * @generated
	 */
	void setDeclaredOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Initializer expression.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4FieldDeclaration_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#getExpression <em>Expression</em>}' containment reference.
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
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TMember getDefinedTypeElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if the field is declared as const.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isConst();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isStatic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValid();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValidName();

} // N4FieldDeclaration
