/**
 * *
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.regex.regularExpression;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exact Quantifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier#getMin <em>Min</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier#getMax <em>Max</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier#isUnboundedMax <em>Unbounded Max</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getExactQuantifier()
 * @model
 * @generated
 */
public interface ExactQuantifier extends Quantifier
{
  /**
   * Returns the value of the '<em><b>Min</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Min</em>' attribute.
   * @see #setMin(int)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getExactQuantifier_Min()
   * @model
   * @generated
   */
  int getMin();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier#getMin <em>Min</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Min</em>' attribute.
   * @see #getMin()
   * @generated
   */
  void setMin(int value);

  /**
   * Returns the value of the '<em><b>Max</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Max</em>' attribute.
   * @see #setMax(int)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getExactQuantifier_Max()
   * @model
   * @generated
   */
  int getMax();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier#getMax <em>Max</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Max</em>' attribute.
   * @see #getMax()
   * @generated
   */
  void setMax(int value);

  /**
   * Returns the value of the '<em><b>Unbounded Max</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Unbounded Max</em>' attribute.
   * @see #setUnboundedMax(boolean)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getExactQuantifier_UnboundedMax()
   * @model
   * @generated
   */
  boolean isUnboundedMax();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier#isUnboundedMax <em>Unbounded Max</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Unbounded Max</em>' attribute.
   * @see #isUnboundedMax()
   * @generated
   */
  void setUnboundedMax(boolean value);

} // ExactQuantifier
