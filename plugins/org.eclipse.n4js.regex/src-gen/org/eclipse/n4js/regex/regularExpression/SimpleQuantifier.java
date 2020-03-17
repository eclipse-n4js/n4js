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
 * A representation of the model object '<em><b>Simple Quantifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.SimpleQuantifier#getQuantifier <em>Quantifier</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getSimpleQuantifier()
 * @model
 * @generated
 */
public interface SimpleQuantifier extends Quantifier
{
  /**
   * Returns the value of the '<em><b>Quantifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Quantifier</em>' attribute.
   * @see #setQuantifier(String)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getSimpleQuantifier_Quantifier()
   * @model
   * @generated
   */
  String getQuantifier();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.SimpleQuantifier#getQuantifier <em>Quantifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Quantifier</em>' attribute.
   * @see #getQuantifier()
   * @generated
   */
  void setQuantifier(String value);

} // SimpleQuantifier
