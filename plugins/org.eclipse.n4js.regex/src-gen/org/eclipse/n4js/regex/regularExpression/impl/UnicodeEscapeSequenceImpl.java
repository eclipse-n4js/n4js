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
package org.eclipse.n4js.regex.regularExpression.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.regex.regularExpression.CharacterClassAtom;
import org.eclipse.n4js.regex.regularExpression.CharacterClassElement;
import org.eclipse.n4js.regex.regularExpression.EscapedCharacterClassAtom;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage;
import org.eclipse.n4js.regex.regularExpression.UnicodeEscapeSequence;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unicode Escape Sequence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.UnicodeEscapeSequenceImpl#getCharacters <em>Characters</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.UnicodeEscapeSequenceImpl#getSequence <em>Sequence</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UnicodeEscapeSequenceImpl extends AtomEscapeImpl implements UnicodeEscapeSequence
{
  /**
   * The default value of the '{@link #getCharacters() <em>Characters</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCharacters()
   * @generated
   * @ordered
   */
  protected static final String CHARACTERS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCharacters() <em>Characters</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCharacters()
   * @generated
   * @ordered
   */
  protected String characters = CHARACTERS_EDEFAULT;

  /**
   * The default value of the '{@link #getSequence() <em>Sequence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSequence()
   * @generated
   * @ordered
   */
  protected static final String SEQUENCE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSequence() <em>Sequence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSequence()
   * @generated
   * @ordered
   */
  protected String sequence = SEQUENCE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected UnicodeEscapeSequenceImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return RegularExpressionPackage.Literals.UNICODE_ESCAPE_SEQUENCE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getCharacters()
  {
    return characters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCharacters(String newCharacters)
  {
    String oldCharacters = characters;
    characters = newCharacters;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__CHARACTERS, oldCharacters, characters));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getSequence()
  {
    return sequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSequence(String newSequence)
  {
    String oldSequence = sequence;
    sequence = newSequence;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__SEQUENCE, oldSequence, sequence));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__CHARACTERS:
        return getCharacters();
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__SEQUENCE:
        return getSequence();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__CHARACTERS:
        setCharacters((String)newValue);
        return;
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__SEQUENCE:
        setSequence((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__CHARACTERS:
        setCharacters(CHARACTERS_EDEFAULT);
        return;
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__SEQUENCE:
        setSequence(SEQUENCE_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__CHARACTERS:
        return CHARACTERS_EDEFAULT == null ? characters != null : !CHARACTERS_EDEFAULT.equals(characters);
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__SEQUENCE:
        return SEQUENCE_EDEFAULT == null ? sequence != null : !SEQUENCE_EDEFAULT.equals(sequence);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
  {
    if (baseClass == CharacterClassElement.class)
    {
      switch (derivedFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == CharacterClassAtom.class)
    {
      switch (derivedFeatureID)
      {
        case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__CHARACTERS: return RegularExpressionPackage.CHARACTER_CLASS_ATOM__CHARACTERS;
        default: return -1;
      }
    }
    if (baseClass == EscapedCharacterClassAtom.class)
    {
      switch (derivedFeatureID)
      {
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
  {
    if (baseClass == CharacterClassElement.class)
    {
      switch (baseFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == CharacterClassAtom.class)
    {
      switch (baseFeatureID)
      {
        case RegularExpressionPackage.CHARACTER_CLASS_ATOM__CHARACTERS: return RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE__CHARACTERS;
        default: return -1;
      }
    }
    if (baseClass == EscapedCharacterClassAtom.class)
    {
      switch (baseFeatureID)
      {
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (characters: ");
    result.append(characters);
    result.append(", sequence: ");
    result.append(sequence);
    result.append(')');
    return result.toString();
  }

} //UnicodeEscapeSequenceImpl
