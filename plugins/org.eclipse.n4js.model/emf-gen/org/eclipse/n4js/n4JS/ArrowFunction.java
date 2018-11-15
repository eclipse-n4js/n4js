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
 * A representation of the model object '<em><b>Arrow Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ArrowFunction#isHasBracesAroundBody <em>Has Braces Around Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getArrowFunction()
 * @model
 * @generated
 */
public interface ArrowFunction extends FunctionExpression {
	/**
	 * Returns the value of the '<em><b>Has Braces Around Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Whether this arrow function has braces around its (single or multiple statements) body.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Has Braces Around Body</em>' attribute.
	 * @see #setHasBracesAroundBody(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getArrowFunction_HasBracesAroundBody()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasBracesAroundBody();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ArrowFunction#isHasBracesAroundBody <em>Has Braces Around Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Braces Around Body</em>' attribute.
	 * @see #isHasBracesAroundBody()
	 * @generated
	 */
	void setHasBracesAroundBody(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isArrowFunction();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * This method reports whether the body of the lambda consists of a single expression not enclosed in braces.
	 * 	 * The presence of enclosing braces implies block semantics for the lambda's body,
	 * ie a block encloses statements (even if only one, an expression statement).
	 * As usual, the block can be void-typed or some-value-typed;
	 * the latter case requires the presence of explicit return-some-value statements.
	 * 	 * An arrow function lacking braces and having a body consisting of just:
	 *   - return-some-value --- is malformed (syntax error)
	 *   - return;           --- is malformed (syntax error)
	 *   - expr              --- is ok, where expr instanceof ExpressionStatement;
	 *                           otherwise malformed and caught by the grammar.
	 * 	 * This method returns true only for the last case above.
	 * Please notice that isSingleExprImplicitReturn() === true doesn't allow drawing conclusion on the type of the lambda body,
	 * i.e. it could be either void-typed (eg, expr denotes the invocation of a void method)
	 * or some-value-typed. An implicit return is warranted only in the latter sub-case.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isSingleExprImplicitReturn();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * If {@link #isSingleExprImplicitReturn()} returns <code>true</code>, this method will return the single expression
	 * that makes up the body of this arrow function, otherwise the behavior is undefined (might throw exception).
	 * <p>
	 * In case of broken AST, this method may return <code>null</code> even if {@link #isSingleExprImplicitReturn()}
	 * returns <code>true</code>.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	Expression getSingleExpression();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The lambda's implicit return expression (precondition: isSingleExprImplicitReturn).
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	Expression implicitReturnExpr();

} // ArrowFunction
