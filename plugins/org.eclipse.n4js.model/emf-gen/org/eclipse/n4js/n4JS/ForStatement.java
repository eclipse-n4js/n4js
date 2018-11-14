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
 * A representation of the model object '<em><b>For Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ForStatement#getInitExpr <em>Init Expr</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ForStatement#getUpdateExpr <em>Update Expr</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ForStatement#isForIn <em>For In</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ForStatement#isForOf <em>For Of</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getForStatement()
 * @model
 * @generated
 */
public interface ForStatement extends VariableDeclarationContainer, IterationStatement, VariableEnvironmentElement {
	/**
	 * Returns the value of the '<em><b>Init Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Expr</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Expr</em>' containment reference.
	 * @see #setInitExpr(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getForStatement_InitExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getInitExpr();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ForStatement#getInitExpr <em>Init Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init Expr</em>' containment reference.
	 * @see #getInitExpr()
	 * @generated
	 */
	void setInitExpr(Expression value);

	/**
	 * Returns the value of the '<em><b>Update Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update Expr</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update Expr</em>' containment reference.
	 * @see #setUpdateExpr(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getForStatement_UpdateExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getUpdateExpr();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ForStatement#getUpdateExpr <em>Update Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update Expr</em>' containment reference.
	 * @see #getUpdateExpr()
	 * @generated
	 */
	void setUpdateExpr(Expression value);

	/**
	 * Returns the value of the '<em><b>For In</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>For In</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>For In</em>' attribute.
	 * @see #setForIn(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getForStatement_ForIn()
	 * @model unique="false"
	 * @generated
	 */
	boolean isForIn();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ForStatement#isForIn <em>For In</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>For In</em>' attribute.
	 * @see #isForIn()
	 * @generated
	 */
	void setForIn(boolean value);

	/**
	 * Returns the value of the '<em><b>For Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>For Of</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>For Of</em>' attribute.
	 * @see #setForOf(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getForStatement_ForOf()
	 * @model unique="false"
	 * @generated
	 */
	boolean isForOf();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ForStatement#isForOf <em>For Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>For Of</em>' attribute.
	 * @see #isForOf()
	 * @generated
	 */
	void setForOf(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isForPlain();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * See {@link VariableEnvironmentElement#appliesOnlyToBlockScopedElements()}.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean appliesOnlyToBlockScopedElements();

} // ForStatement
