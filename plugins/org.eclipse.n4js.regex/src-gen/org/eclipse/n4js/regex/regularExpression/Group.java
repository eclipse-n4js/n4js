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
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.Group#isNonCapturing <em>Non Capturing</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.Group#isNamed <em>Named</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.Group#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.Group#getPattern <em>Pattern</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends Pattern
{
  /**
   * Returns the value of the '<em><b>Non Capturing</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Non Capturing</em>' attribute.
   * @see #setNonCapturing(boolean)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getGroup_NonCapturing()
   * @model
   * @generated
   */
  boolean isNonCapturing();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.Group#isNonCapturing <em>Non Capturing</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Non Capturing</em>' attribute.
   * @see #isNonCapturing()
   * @generated
   */
  void setNonCapturing(boolean value);

  /**
   * Returns the value of the '<em><b>Named</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Named</em>' attribute.
   * @see #setNamed(boolean)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getGroup_Named()
   * @model
   * @generated
   */
  boolean isNamed();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.Group#isNamed <em>Named</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Named</em>' attribute.
   * @see #isNamed()
   * @generated
   */
  void setNamed(boolean value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getGroup_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.Group#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Pattern</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pattern</em>' containment reference.
   * @see #setPattern(Pattern)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getGroup_Pattern()
   * @model containment="true"
   * @generated
   */
  Pattern getPattern();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.Group#getPattern <em>Pattern</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pattern</em>' containment reference.
   * @see #getPattern()
   * @generated
   */
  void setPattern(Pattern value);

} // Group
