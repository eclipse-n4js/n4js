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

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.Block#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBlock()
 * @model
 * @generated
 */
public interface Block extends Statement, VariableEnvironmentElement {
	/**
	 * Returns the value of the '<em><b>Statements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.Statement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statements</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBlock_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Statement> getStatements();

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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all return expressions contained in this block, including those in nested blocks
	 * but without delving into nested classes, or into nested expressions including functions.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.IteratorOfExpression" unique="false"
	 * @generated
	 */
	Iterator<Expression> getAllExpressions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all return yield expressions contained in this block, including those in nested blocks
	 * but without delving into nested classes, or into nested expressions including functions.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.IteratorOfYieldExpression" unique="false"
	 * @generated
	 */
	Iterator<YieldExpression> getAllYieldExpressions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all return yield expressions contained in this block that dont't return any value, including those in nested blocks
	 * but without delving into nested classes, or into nested expressions including functions.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.IteratorOfYieldExpression" unique="false"
	 * @generated
	 */
	Iterator<YieldExpression> getAllVoidYieldExpressions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all return yield expressions contained in this block that return some value, including those in nested blocks
	 * but without delving into nested classes, or into nested expressions including functions.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.IteratorOfYieldExpression" unique="false"
	 * @generated
	 */
	Iterator<YieldExpression> getAllNonVoidYieldExpressions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * True iff one or more yield expressions exist in this block that return some value.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean hasNonVoidYield();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all statements that belong to this block. This includes statements of nested blocks
	 * but excludes statements in nested classes, nested functions, etc.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.IteratorOfStatement" unique="false"
	 * @generated
	 */
	Iterator<Statement> getAllStatements();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all return statements contained in this block (whether they return a value or not), including those in nested blocks
	 * but without delving into nested classes, or into nested expressions including functions.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.IteratorOfReturnStatement" unique="false"
	 * @generated
	 */
	Iterator<ReturnStatement> getAllReturnStatements();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all return statements contained in this block that return some value, including those in nested blocks
	 * but without delving into nested classes, or into nested expressions including functions.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.IteratorOfReturnStatement" unique="false"
	 * @generated
	 */
	Iterator<ReturnStatement> getAllNonVoidReturnStatements();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all return statements contained in this block that don't return any value, including those in nested blocks
	 * but without delving into nested classes, or into nested expressions including functions.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.IteratorOfReturnStatement" unique="false"
	 * @generated
	 */
	Iterator<ReturnStatement> getAllVoidReturnStatements();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * True iff one or more return statements exist in this block that return some value.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean hasNonVoidReturn();

} // Block
