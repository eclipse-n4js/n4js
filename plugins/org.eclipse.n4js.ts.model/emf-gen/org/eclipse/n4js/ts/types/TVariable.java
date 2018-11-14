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
 * A representation of the model object '<em><b>TVariable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Used for storing information about exported variables in the Xtext index, similar
 * to Types (such as TClass, TInterface, TFunction). It references the variable declaration of the
 * export declaration (via SyntaxRelatedTElement's astElement).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TVariable#isExternal <em>External</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TVariable#isObjectLiteral <em>Object Literal</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TVariable#isNewExpression <em>New Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTVariable()
 * @model
 * @generated
 */
public interface TVariable extends TExportableElement, TConstableElement, SyntaxRelatedTElement, TAnnotableElement, AccessibleTypeElement, TTypedElement {
	/**
	 * Returns the value of the '<em><b>External</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External</em>' attribute.
	 * @see #setExternal(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTVariable_External()
	 * @model unique="false"
	 * @generated
	 */
	boolean isExternal();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TVariable#isExternal <em>External</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External</em>' attribute.
	 * @see #isExternal()
	 * @generated
	 */
	void setExternal(boolean value);

	/**
	 * Returns the value of the '<em><b>Object Literal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Literal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Literal</em>' attribute.
	 * @see #setObjectLiteral(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTVariable_ObjectLiteral()
	 * @model unique="false"
	 * @generated
	 */
	boolean isObjectLiteral();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TVariable#isObjectLiteral <em>Object Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Literal</em>' attribute.
	 * @see #isObjectLiteral()
	 * @generated
	 */
	void setObjectLiteral(boolean value);

	/**
	 * Returns the value of the '<em><b>New Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Expression</em>' attribute.
	 * @see #setNewExpression(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTVariable_NewExpression()
	 * @model unique="false"
	 * @generated
	 */
	boolean isNewExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TVariable#isNewExpression <em>New Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Expression</em>' attribute.
	 * @see #isNewExpression()
	 * @generated
	 */
	void setNewExpression(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns string representation of variable according to syntax definition.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getVariableAsString();

} // TVariable
