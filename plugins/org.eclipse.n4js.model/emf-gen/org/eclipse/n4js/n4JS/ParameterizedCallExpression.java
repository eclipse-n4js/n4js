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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Call Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ParameterizedCallExpression#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getParameterizedCallExpression()
 * @model
 * @generated
 */
public interface ParameterizedCallExpression extends ExpressionWithTarget, ParameterizedAccess {
	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.Argument}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * About the target property:
	 * It is the callee or target of the call, e.g. the name of the called function. This is not the receiver (which may be null).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Arguments</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getParameterizedCallExpression_Arguments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Argument> getArguments();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method returning the receiver expression or null, if call has no receiver.
	 * 
	 * The semantics of receiver and target are best understood by example:
	 * <code> receiver.target(arguments) </code>
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	Expression getReceiver();

} // ParameterizedCallExpression
