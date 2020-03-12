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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Quantifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.Quantifier#isNonGreedy <em>Non Greedy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getQuantifier()
 * @model
 * @generated
 */
public interface Quantifier extends EObject
{
  /**
   * Returns the value of the '<em><b>Non Greedy</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Non Greedy</em>' attribute.
   * @see #setNonGreedy(boolean)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getQuantifier_NonGreedy()
   * @model
   * @generated
   */
  boolean isNonGreedy();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.Quantifier#isNonGreedy <em>Non Greedy</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Non Greedy</em>' attribute.
   * @see #isNonGreedy()
   * @generated
   */
  void setNonGreedy(boolean value);

} // Quantifier
