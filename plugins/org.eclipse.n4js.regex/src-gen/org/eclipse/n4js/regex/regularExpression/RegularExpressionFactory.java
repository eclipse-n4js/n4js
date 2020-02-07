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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage
 * @generated
 */
public interface RegularExpressionFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  RegularExpressionFactory eINSTANCE = org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Literal</em>'.
   * @generated
   */
  RegularExpressionLiteral createRegularExpressionLiteral();

  /**
   * Returns a new object of class '<em>Body</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Body</em>'.
   * @generated
   */
  RegularExpressionBody createRegularExpressionBody();

  /**
   * Returns a new object of class '<em>Pattern</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Pattern</em>'.
   * @generated
   */
  Pattern createPattern();

  /**
   * Returns a new object of class '<em>Assertion</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Assertion</em>'.
   * @generated
   */
  Assertion createAssertion();

  /**
   * Returns a new object of class '<em>Line Start</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Line Start</em>'.
   * @generated
   */
  LineStart createLineStart();

  /**
   * Returns a new object of class '<em>Line End</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Line End</em>'.
   * @generated
   */
  LineEnd createLineEnd();

  /**
   * Returns a new object of class '<em>Word Boundary</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Word Boundary</em>'.
   * @generated
   */
  WordBoundary createWordBoundary();

  /**
   * Returns a new object of class '<em>Abstract Look Ahead</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Abstract Look Ahead</em>'.
   * @generated
   */
  AbstractLookAhead createAbstractLookAhead();

  /**
   * Returns a new object of class '<em>Pattern Character</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Pattern Character</em>'.
   * @generated
   */
  PatternCharacter createPatternCharacter();

  /**
   * Returns a new object of class '<em>Wildcard</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Wildcard</em>'.
   * @generated
   */
  Wildcard createWildcard();

  /**
   * Returns a new object of class '<em>Atom Escape</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Atom Escape</em>'.
   * @generated
   */
  AtomEscape createAtomEscape();

  /**
   * Returns a new object of class '<em>Character Class Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Character Class Escape Sequence</em>'.
   * @generated
   */
  CharacterClassEscapeSequence createCharacterClassEscapeSequence();

  /**
   * Returns a new object of class '<em>Character Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Character Escape Sequence</em>'.
   * @generated
   */
  CharacterEscapeSequence createCharacterEscapeSequence();

  /**
   * Returns a new object of class '<em>Control Letter Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Control Letter Escape Sequence</em>'.
   * @generated
   */
  ControlLetterEscapeSequence createControlLetterEscapeSequence();

  /**
   * Returns a new object of class '<em>Hex Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Hex Escape Sequence</em>'.
   * @generated
   */
  HexEscapeSequence createHexEscapeSequence();

  /**
   * Returns a new object of class '<em>Unicode Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Unicode Escape Sequence</em>'.
   * @generated
   */
  UnicodeEscapeSequence createUnicodeEscapeSequence();

  /**
   * Returns a new object of class '<em>Identity Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Identity Escape Sequence</em>'.
   * @generated
   */
  IdentityEscapeSequence createIdentityEscapeSequence();

  /**
   * Returns a new object of class '<em>Decimal Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Decimal Escape Sequence</em>'.
   * @generated
   */
  DecimalEscapeSequence createDecimalEscapeSequence();

  /**
   * Returns a new object of class '<em>Character Class</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Character Class</em>'.
   * @generated
   */
  CharacterClass createCharacterClass();

  /**
   * Returns a new object of class '<em>Character Class Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Character Class Element</em>'.
   * @generated
   */
  CharacterClassElement createCharacterClassElement();

  /**
   * Returns a new object of class '<em>Character Class Atom</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Character Class Atom</em>'.
   * @generated
   */
  CharacterClassAtom createCharacterClassAtom();

  /**
   * Returns a new object of class '<em>Escaped Character Class Atom</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Escaped Character Class Atom</em>'.
   * @generated
   */
  EscapedCharacterClassAtom createEscapedCharacterClassAtom();

  /**
   * Returns a new object of class '<em>Backspace</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Backspace</em>'.
   * @generated
   */
  Backspace createBackspace();

  /**
   * Returns a new object of class '<em>Group</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Group</em>'.
   * @generated
   */
  Group createGroup();

  /**
   * Returns a new object of class '<em>Quantifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Quantifier</em>'.
   * @generated
   */
  Quantifier createQuantifier();

  /**
   * Returns a new object of class '<em>Simple Quantifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Simple Quantifier</em>'.
   * @generated
   */
  SimpleQuantifier createSimpleQuantifier();

  /**
   * Returns a new object of class '<em>Exact Quantifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Exact Quantifier</em>'.
   * @generated
   */
  ExactQuantifier createExactQuantifier();

  /**
   * Returns a new object of class '<em>Flags</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Flags</em>'.
   * @generated
   */
  RegularExpressionFlags createRegularExpressionFlags();

  /**
   * Returns a new object of class '<em>Disjunction</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Disjunction</em>'.
   * @generated
   */
  Disjunction createDisjunction();

  /**
   * Returns a new object of class '<em>Sequence</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Sequence</em>'.
   * @generated
   */
  Sequence createSequence();

  /**
   * Returns a new object of class '<em>Look Ahead</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Look Ahead</em>'.
   * @generated
   */
  LookAhead createLookAhead();

  /**
   * Returns a new object of class '<em>Look Behind</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Look Behind</em>'.
   * @generated
   */
  LookBehind createLookBehind();

  /**
   * Returns a new object of class '<em>Character Class Range</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Character Class Range</em>'.
   * @generated
   */
  CharacterClassRange createCharacterClassRange();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  RegularExpressionPackage getRegularExpressionPackage();

} //RegularExpressionFactory
