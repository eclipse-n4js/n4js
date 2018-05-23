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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.regex.regularExpression.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RegularExpressionFactoryImpl extends EFactoryImpl implements RegularExpressionFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static RegularExpressionFactory init()
  {
    try
    {
      RegularExpressionFactory theRegularExpressionFactory = (RegularExpressionFactory)EPackage.Registry.INSTANCE.getEFactory(RegularExpressionPackage.eNS_URI);
      if (theRegularExpressionFactory != null)
      {
        return theRegularExpressionFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new RegularExpressionFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegularExpressionFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL: return createRegularExpressionLiteral();
      case RegularExpressionPackage.REGULAR_EXPRESSION_BODY: return createRegularExpressionBody();
      case RegularExpressionPackage.PATTERN: return createPattern();
      case RegularExpressionPackage.ASSERTION: return createAssertion();
      case RegularExpressionPackage.LINE_START: return createLineStart();
      case RegularExpressionPackage.LINE_END: return createLineEnd();
      case RegularExpressionPackage.WORD_BOUNDARY: return createWordBoundary();
      case RegularExpressionPackage.LOOK_AHEAD: return createLookAhead();
      case RegularExpressionPackage.PATTERN_CHARACTER: return createPatternCharacter();
      case RegularExpressionPackage.WILDCARD: return createWildcard();
      case RegularExpressionPackage.ATOM_ESCAPE: return createAtomEscape();
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE: return createCharacterClassEscapeSequence();
      case RegularExpressionPackage.CHARACTER_ESCAPE_SEQUENCE: return createCharacterEscapeSequence();
      case RegularExpressionPackage.CONTROL_LETTER_ESCAPE_SEQUENCE: return createControlLetterEscapeSequence();
      case RegularExpressionPackage.HEX_ESCAPE_SEQUENCE: return createHexEscapeSequence();
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE: return createUnicodeEscapeSequence();
      case RegularExpressionPackage.IDENTITY_ESCAPE_SEQUENCE: return createIdentityEscapeSequence();
      case RegularExpressionPackage.DECIMAL_ESCAPE_SEQUENCE: return createDecimalEscapeSequence();
      case RegularExpressionPackage.CHARACTER_CLASS: return createCharacterClass();
      case RegularExpressionPackage.CHARACTER_CLASS_ELEMENT: return createCharacterClassElement();
      case RegularExpressionPackage.CHARACTER_CLASS_ATOM: return createCharacterClassAtom();
      case RegularExpressionPackage.ESCAPED_CHARACTER_CLASS_ATOM: return createEscapedCharacterClassAtom();
      case RegularExpressionPackage.BACKSPACE: return createBackspace();
      case RegularExpressionPackage.GROUP: return createGroup();
      case RegularExpressionPackage.QUANTIFIER: return createQuantifier();
      case RegularExpressionPackage.SIMPLE_QUANTIFIER: return createSimpleQuantifier();
      case RegularExpressionPackage.EXACT_QUANTIFIER: return createExactQuantifier();
      case RegularExpressionPackage.REGULAR_EXPRESSION_FLAGS: return createRegularExpressionFlags();
      case RegularExpressionPackage.DISJUNCTION: return createDisjunction();
      case RegularExpressionPackage.SEQUENCE: return createSequence();
      case RegularExpressionPackage.CHARACTER_CLASS_RANGE: return createCharacterClassRange();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegularExpressionLiteral createRegularExpressionLiteral()
  {
    RegularExpressionLiteralImpl regularExpressionLiteral = new RegularExpressionLiteralImpl();
    return regularExpressionLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegularExpressionBody createRegularExpressionBody()
  {
    RegularExpressionBodyImpl regularExpressionBody = new RegularExpressionBodyImpl();
    return regularExpressionBody;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Pattern createPattern()
  {
    PatternImpl pattern = new PatternImpl();
    return pattern;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Assertion createAssertion()
  {
    AssertionImpl assertion = new AssertionImpl();
    return assertion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LineStart createLineStart()
  {
    LineStartImpl lineStart = new LineStartImpl();
    return lineStart;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LineEnd createLineEnd()
  {
    LineEndImpl lineEnd = new LineEndImpl();
    return lineEnd;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WordBoundary createWordBoundary()
  {
    WordBoundaryImpl wordBoundary = new WordBoundaryImpl();
    return wordBoundary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LookAhead createLookAhead()
  {
    LookAheadImpl lookAhead = new LookAheadImpl();
    return lookAhead;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PatternCharacter createPatternCharacter()
  {
    PatternCharacterImpl patternCharacter = new PatternCharacterImpl();
    return patternCharacter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Wildcard createWildcard()
  {
    WildcardImpl wildcard = new WildcardImpl();
    return wildcard;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AtomEscape createAtomEscape()
  {
    AtomEscapeImpl atomEscape = new AtomEscapeImpl();
    return atomEscape;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CharacterClassEscapeSequence createCharacterClassEscapeSequence()
  {
    CharacterClassEscapeSequenceImpl characterClassEscapeSequence = new CharacterClassEscapeSequenceImpl();
    return characterClassEscapeSequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CharacterEscapeSequence createCharacterEscapeSequence()
  {
    CharacterEscapeSequenceImpl characterEscapeSequence = new CharacterEscapeSequenceImpl();
    return characterEscapeSequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ControlLetterEscapeSequence createControlLetterEscapeSequence()
  {
    ControlLetterEscapeSequenceImpl controlLetterEscapeSequence = new ControlLetterEscapeSequenceImpl();
    return controlLetterEscapeSequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public HexEscapeSequence createHexEscapeSequence()
  {
    HexEscapeSequenceImpl hexEscapeSequence = new HexEscapeSequenceImpl();
    return hexEscapeSequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UnicodeEscapeSequence createUnicodeEscapeSequence()
  {
    UnicodeEscapeSequenceImpl unicodeEscapeSequence = new UnicodeEscapeSequenceImpl();
    return unicodeEscapeSequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IdentityEscapeSequence createIdentityEscapeSequence()
  {
    IdentityEscapeSequenceImpl identityEscapeSequence = new IdentityEscapeSequenceImpl();
    return identityEscapeSequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DecimalEscapeSequence createDecimalEscapeSequence()
  {
    DecimalEscapeSequenceImpl decimalEscapeSequence = new DecimalEscapeSequenceImpl();
    return decimalEscapeSequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CharacterClass createCharacterClass()
  {
    CharacterClassImpl characterClass = new CharacterClassImpl();
    return characterClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CharacterClassElement createCharacterClassElement()
  {
    CharacterClassElementImpl characterClassElement = new CharacterClassElementImpl();
    return characterClassElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CharacterClassAtom createCharacterClassAtom()
  {
    CharacterClassAtomImpl characterClassAtom = new CharacterClassAtomImpl();
    return characterClassAtom;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EscapedCharacterClassAtom createEscapedCharacterClassAtom()
  {
    EscapedCharacterClassAtomImpl escapedCharacterClassAtom = new EscapedCharacterClassAtomImpl();
    return escapedCharacterClassAtom;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Backspace createBackspace()
  {
    BackspaceImpl backspace = new BackspaceImpl();
    return backspace;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Group createGroup()
  {
    GroupImpl group = new GroupImpl();
    return group;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Quantifier createQuantifier()
  {
    QuantifierImpl quantifier = new QuantifierImpl();
    return quantifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SimpleQuantifier createSimpleQuantifier()
  {
    SimpleQuantifierImpl simpleQuantifier = new SimpleQuantifierImpl();
    return simpleQuantifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExactQuantifier createExactQuantifier()
  {
    ExactQuantifierImpl exactQuantifier = new ExactQuantifierImpl();
    return exactQuantifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegularExpressionFlags createRegularExpressionFlags()
  {
    RegularExpressionFlagsImpl regularExpressionFlags = new RegularExpressionFlagsImpl();
    return regularExpressionFlags;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Disjunction createDisjunction()
  {
    DisjunctionImpl disjunction = new DisjunctionImpl();
    return disjunction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Sequence createSequence()
  {
    SequenceImpl sequence = new SequenceImpl();
    return sequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CharacterClassRange createCharacterClassRange()
  {
    CharacterClassRangeImpl characterClassRange = new CharacterClassRangeImpl();
    return characterClassRange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegularExpressionPackage getRegularExpressionPackage()
  {
    return (RegularExpressionPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static RegularExpressionPackage getPackage()
  {
    return RegularExpressionPackage.eINSTANCE;
  }

} //RegularExpressionFactoryImpl
