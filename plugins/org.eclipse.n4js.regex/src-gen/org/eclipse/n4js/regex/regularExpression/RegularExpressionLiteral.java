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
 * A representation of the model object '<em><b>Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral#getFlags <em>Flags</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getRegularExpressionLiteral()
 * @model
 * @generated
 */
public interface RegularExpressionLiteral extends EObject
{
  /**
   * Returns the value of the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Body</em>' containment reference.
   * @see #setBody(RegularExpressionBody)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getRegularExpressionLiteral_Body()
   * @model containment="true"
   * @generated
   */
  RegularExpressionBody getBody();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral#getBody <em>Body</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Body</em>' containment reference.
   * @see #getBody()
   * @generated
   */
  void setBody(RegularExpressionBody value);

  /**
   * Returns the value of the '<em><b>Flags</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Flags</em>' containment reference.
   * @see #setFlags(RegularExpressionFlags)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getRegularExpressionLiteral_Flags()
   * @model containment="true"
   * @generated
   */
  RegularExpressionFlags getFlags();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral#getFlags <em>Flags</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Flags</em>' containment reference.
   * @see #getFlags()
   * @generated
   */
  void setFlags(RegularExpressionFlags value);

} // RegularExpressionLiteral
