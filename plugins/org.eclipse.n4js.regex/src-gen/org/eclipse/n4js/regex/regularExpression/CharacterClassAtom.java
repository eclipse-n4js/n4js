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
 * A representation of the model object '<em><b>Character Class Atom</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.CharacterClassAtom#getCharacters <em>Characters</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterClassAtom()
 * @model
 * @generated
 */
public interface CharacterClassAtom extends CharacterClassElement
{
  /**
   * Returns the value of the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Characters</em>' attribute.
   * @see #setCharacters(String)
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#getCharacterClassAtom_Characters()
   * @model
   * @generated
   */
  String getCharacters();

  /**
   * Sets the value of the '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassAtom#getCharacters <em>Characters</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Characters</em>' attribute.
   * @see #getCharacters()
   * @generated
   */
  void setCharacters(String value);

} // CharacterClassAtom
