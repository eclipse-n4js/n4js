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
 * A representation of the model object '<em><b>Comma Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.CommaExpression#getExprs <em>Exprs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getCommaExpression()
 * @model
 * @generated
 */
public interface CommaExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Exprs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.Expression}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exprs</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getCommaExpression_Exprs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getExprs();

} // CommaExpression
