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
 * A representation of the model object '<em><b>Character Class Range</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.CharacterClassRange#getLeft <em>Left</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.CharacterClassRange#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterClassRange()
 * @model
 * @generated
 */
public interface CharacterClassRange extends CharacterClassElement
{
  /**
   * Returns the value of the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Left</em>' containment reference.
   * @see #setLeft(CharacterClassAtom)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterClassRange_Left()
   * @model containment="true"
   * @generated
   */
  CharacterClassAtom getLeft();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassRange#getLeft <em>Left</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left</em>' containment reference.
   * @see #getLeft()
   * @generated
   */
  void setLeft(CharacterClassAtom value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(CharacterClassAtom)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterClassRange_Right()
   * @model containment="true"
   * @generated
   */
  CharacterClassAtom getRight();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassRange#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(CharacterClassAtom value);

} // CharacterClassRange
