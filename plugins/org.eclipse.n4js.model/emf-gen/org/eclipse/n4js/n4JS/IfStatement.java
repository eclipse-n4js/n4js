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
 * A representation of the model object '<em><b>If Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.IfStatement#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.IfStatement#getIfStmt <em>If Stmt</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.IfStatement#getElseStmt <em>Else Stmt</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getIfStatement()
 * @model
 * @generated
 */
public interface IfStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getIfStatement_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.IfStatement#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

	/**
	 * Returns the value of the '<em><b>If Stmt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>If Stmt</em>' containment reference.
	 * @see #setIfStmt(Statement)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getIfStatement_IfStmt()
	 * @model containment="true"
	 * @generated
	 */
	Statement getIfStmt();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.IfStatement#getIfStmt <em>If Stmt</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>If Stmt</em>' containment reference.
	 * @see #getIfStmt()
	 * @generated
	 */
	void setIfStmt(Statement value);

	/**
	 * Returns the value of the '<em><b>Else Stmt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Else Stmt</em>' containment reference.
	 * @see #setElseStmt(Statement)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getIfStatement_ElseStmt()
	 * @model containment="true"
	 * @generated
	 */
	Statement getElseStmt();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.IfStatement#getElseStmt <em>Else Stmt</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Else Stmt</em>' containment reference.
	 * @see #getElseStmt()
	 * @generated
	 */
	void setElseStmt(Statement value);

} // IfStatement
