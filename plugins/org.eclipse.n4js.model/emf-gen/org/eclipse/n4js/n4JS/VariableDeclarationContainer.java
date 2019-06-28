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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Declaration Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Base class for elements containing variable declarations.
 * <p>
 * IMPORTANT: the variable declarations need not be <em>directly</em> contained! In order to support destructuring, a
 * {@link VariableBinding} and other destructuring-related nodes might appear in between. To get from a
 * {@link VariableDeclaration} to its <code>VariableDeclarationContainer</code> use method
 * {@link org.eclipse.n4js.n4JS.N4JSASTUtils#getVariableDeclarationContainer(VariableDeclaration)}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarDeclsOrBindings <em>Var Decls Or Bindings</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarStmtKeyword <em>Var Stmt Keyword</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableDeclarationContainer()
 * @model abstract="true"
 * @generated
 */
public interface VariableDeclarationContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Var Decls Or Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.VariableDeclarationOrBinding}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Decls Or Bindings</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableDeclarationContainer_VarDeclsOrBindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariableDeclarationOrBinding> getVarDeclsOrBindings();

	/**
	 * Returns the value of the '<em><b>Var Stmt Keyword</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4JS.VariableStatementKeyword}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Stmt Keyword</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.VariableStatementKeyword
	 * @see #setVarStmtKeyword(VariableStatementKeyword)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableDeclarationContainer_VarStmtKeyword()
	 * @model unique="false"
	 * @generated
	 */
	VariableStatementKeyword getVarStmtKeyword();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarStmtKeyword <em>Var Stmt Keyword</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Var Stmt Keyword</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.VariableStatementKeyword
	 * @see #getVarStmtKeyword()
	 * @generated
	 */
	void setVarStmtKeyword(VariableStatementKeyword value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<VariableDeclaration> getVarDecl();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isBlockScoped();

} // VariableDeclarationContainer
