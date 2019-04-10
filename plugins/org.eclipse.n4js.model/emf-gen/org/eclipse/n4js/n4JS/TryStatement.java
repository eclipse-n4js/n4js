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
 * A representation of the model object '<em><b>Try Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.TryStatement#getBlock <em>Block</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.TryStatement#getCatch <em>Catch</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.TryStatement#getFinally <em>Finally</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTryStatement()
 * @model
 * @generated
 */
public interface TryStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Block</em>' containment reference.
	 * @see #setBlock(Block)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTryStatement_Block()
	 * @model containment="true"
	 * @generated
	 */
	Block getBlock();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TryStatement#getBlock <em>Block</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Block</em>' containment reference.
	 * @see #getBlock()
	 * @generated
	 */
	void setBlock(Block value);

	/**
	 * Returns the value of the '<em><b>Catch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catch</em>' containment reference.
	 * @see #setCatch(CatchBlock)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTryStatement_Catch()
	 * @model containment="true"
	 * @generated
	 */
	CatchBlock getCatch();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TryStatement#getCatch <em>Catch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catch</em>' containment reference.
	 * @see #getCatch()
	 * @generated
	 */
	void setCatch(CatchBlock value);

	/**
	 * Returns the value of the '<em><b>Finally</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Finally</em>' containment reference.
	 * @see #setFinally(FinallyBlock)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTryStatement_Finally()
	 * @model containment="true"
	 * @generated
	 */
	FinallyBlock getFinally();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TryStatement#getFinally <em>Finally</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Finally</em>' containment reference.
	 * @see #getFinally()
	 * @generated
	 */
	void setFinally(FinallyBlock value);

} // TryStatement
