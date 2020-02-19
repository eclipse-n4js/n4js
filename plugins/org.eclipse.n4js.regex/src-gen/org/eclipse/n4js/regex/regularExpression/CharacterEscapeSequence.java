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
 * A representation of the model object '<em><b>Character Escape Sequence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence#getSequence <em>Sequence</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterEscapeSequence()
 * @model
 * @generated
 */
public interface CharacterEscapeSequence extends AtomEscape, EscapedCharacterClassAtom
{
  /**
   * Returns the value of the '<em><b>Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sequence</em>' attribute.
   * @see #setSequence(String)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterEscapeSequence_Sequence()
   * @model
   * @generated
   */
  String getSequence();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence#getSequence <em>Sequence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sequence</em>' attribute.
   * @see #getSequence()
   * @generated
   */
  void setSequence(String value);

} // CharacterEscapeSequence
