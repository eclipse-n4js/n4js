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
 * A representation of the model object '<em><b>Look Ahead</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.LookAhead#isBackwards <em>Backwards</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.LookAhead#isNot <em>Not</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.LookAhead#getPattern <em>Pattern</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getLookAhead()
 * @model
 * @generated
 */
public interface LookAhead extends Assertion
{
  /**
   * Returns the value of the '<em><b>Backwards</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Backwards</em>' attribute.
   * @see #setBackwards(boolean)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getLookAhead_Backwards()
   * @model
   * @generated
   */
  boolean isBackwards();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.LookAhead#isBackwards <em>Backwards</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Backwards</em>' attribute.
   * @see #isBackwards()
   * @generated
   */
  void setBackwards(boolean value);

  /**
   * Returns the value of the '<em><b>Not</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Not</em>' attribute.
   * @see #setNot(boolean)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getLookAhead_Not()
   * @model
   * @generated
   */
  boolean isNot();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.LookAhead#isNot <em>Not</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Not</em>' attribute.
   * @see #isNot()
   * @generated
   */
  void setNot(boolean value);

  /**
   * Returns the value of the '<em><b>Pattern</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pattern</em>' containment reference.
   * @see #setPattern(Pattern)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getLookAhead_Pattern()
   * @model containment="true"
   * @generated
   */
  Pattern getPattern();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.LookAhead#getPattern <em>Pattern</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pattern</em>' containment reference.
   * @see #getPattern()
   * @generated
   */
  void setPattern(Pattern value);

} // LookAhead
