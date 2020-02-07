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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionFactory
 * @model kind="package"
 * @generated
 */
public interface RegularExpressionPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "regularExpression";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/n4js/regex/RegularExpression";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "regularExpression";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  RegularExpressionPackage eINSTANCE = org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionLiteralImpl <em>Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionLiteralImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getRegularExpressionLiteral()
   * @generated
   */
  int REGULAR_EXPRESSION_LITERAL = 0;

  /**
   * The feature id for the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REGULAR_EXPRESSION_LITERAL__BODY = 0;

  /**
   * The feature id for the '<em><b>Flags</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REGULAR_EXPRESSION_LITERAL__FLAGS = 1;

  /**
   * The number of structural features of the '<em>Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REGULAR_EXPRESSION_LITERAL_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionBodyImpl <em>Body</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionBodyImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getRegularExpressionBody()
   * @generated
   */
  int REGULAR_EXPRESSION_BODY = 1;

  /**
   * The feature id for the '<em><b>Pattern</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REGULAR_EXPRESSION_BODY__PATTERN = 0;

  /**
   * The number of structural features of the '<em>Body</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REGULAR_EXPRESSION_BODY_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.PatternImpl <em>Pattern</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.PatternImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getPattern()
   * @generated
   */
  int PATTERN = 2;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PATTERN__QUANTIFIER = 0;

  /**
   * The number of structural features of the '<em>Pattern</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PATTERN_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.AssertionImpl <em>Assertion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.AssertionImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getAssertion()
   * @generated
   */
  int ASSERTION = 3;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSERTION__QUANTIFIER = PATTERN__QUANTIFIER;

  /**
   * The number of structural features of the '<em>Assertion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSERTION_FEATURE_COUNT = PATTERN_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.LineStartImpl <em>Line Start</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.LineStartImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getLineStart()
   * @generated
   */
  int LINE_START = 4;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINE_START__QUANTIFIER = ASSERTION__QUANTIFIER;

  /**
   * The number of structural features of the '<em>Line Start</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINE_START_FEATURE_COUNT = ASSERTION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.LineEndImpl <em>Line End</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.LineEndImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getLineEnd()
   * @generated
   */
  int LINE_END = 5;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINE_END__QUANTIFIER = ASSERTION__QUANTIFIER;

  /**
   * The number of structural features of the '<em>Line End</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINE_END_FEATURE_COUNT = ASSERTION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.WordBoundaryImpl <em>Word Boundary</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.WordBoundaryImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getWordBoundary()
   * @generated
   */
  int WORD_BOUNDARY = 6;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORD_BOUNDARY__QUANTIFIER = ASSERTION__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Not</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORD_BOUNDARY__NOT = ASSERTION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Word Boundary</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORD_BOUNDARY_FEATURE_COUNT = ASSERTION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.AbstractLookAheadImpl <em>Abstract Look Ahead</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.AbstractLookAheadImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getAbstractLookAhead()
   * @generated
   */
  int ABSTRACT_LOOK_AHEAD = 7;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_LOOK_AHEAD__QUANTIFIER = ASSERTION__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Not</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_LOOK_AHEAD__NOT = ASSERTION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Pattern</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_LOOK_AHEAD__PATTERN = ASSERTION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Abstract Look Ahead</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_LOOK_AHEAD_FEATURE_COUNT = ASSERTION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.PatternCharacterImpl <em>Pattern Character</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.PatternCharacterImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getPatternCharacter()
   * @generated
   */
  int PATTERN_CHARACTER = 8;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PATTERN_CHARACTER__QUANTIFIER = PATTERN__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PATTERN_CHARACTER__VALUE = PATTERN_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Pattern Character</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PATTERN_CHARACTER_FEATURE_COUNT = PATTERN_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.WildcardImpl <em>Wildcard</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.WildcardImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getWildcard()
   * @generated
   */
  int WILDCARD = 9;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WILDCARD__QUANTIFIER = PATTERN__QUANTIFIER;

  /**
   * The number of structural features of the '<em>Wildcard</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WILDCARD_FEATURE_COUNT = PATTERN_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.AtomEscapeImpl <em>Atom Escape</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.AtomEscapeImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getAtomEscape()
   * @generated
   */
  int ATOM_ESCAPE = 10;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ATOM_ESCAPE__QUANTIFIER = PATTERN__QUANTIFIER;

  /**
   * The number of structural features of the '<em>Atom Escape</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ATOM_ESCAPE_FEATURE_COUNT = PATTERN_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassEscapeSequenceImpl <em>Character Class Escape Sequence</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassEscapeSequenceImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClassEscapeSequence()
   * @generated
   */
  int CHARACTER_CLASS_ESCAPE_SEQUENCE = 11;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_ESCAPE_SEQUENCE__QUANTIFIER = ATOM_ESCAPE__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_ESCAPE_SEQUENCE__CHARACTERS = ATOM_ESCAPE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE = ATOM_ESCAPE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Character Class Escape Sequence</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_ESCAPE_SEQUENCE_FEATURE_COUNT = ATOM_ESCAPE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterEscapeSequenceImpl <em>Character Escape Sequence</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterEscapeSequenceImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterEscapeSequence()
   * @generated
   */
  int CHARACTER_ESCAPE_SEQUENCE = 12;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_ESCAPE_SEQUENCE__QUANTIFIER = ATOM_ESCAPE__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_ESCAPE_SEQUENCE__CHARACTERS = ATOM_ESCAPE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_ESCAPE_SEQUENCE__SEQUENCE = ATOM_ESCAPE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Character Escape Sequence</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_ESCAPE_SEQUENCE_FEATURE_COUNT = ATOM_ESCAPE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.ControlLetterEscapeSequenceImpl <em>Control Letter Escape Sequence</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.ControlLetterEscapeSequenceImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getControlLetterEscapeSequence()
   * @generated
   */
  int CONTROL_LETTER_ESCAPE_SEQUENCE = 13;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROL_LETTER_ESCAPE_SEQUENCE__QUANTIFIER = ATOM_ESCAPE__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROL_LETTER_ESCAPE_SEQUENCE__CHARACTERS = ATOM_ESCAPE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROL_LETTER_ESCAPE_SEQUENCE__SEQUENCE = ATOM_ESCAPE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Control Letter Escape Sequence</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROL_LETTER_ESCAPE_SEQUENCE_FEATURE_COUNT = ATOM_ESCAPE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.HexEscapeSequenceImpl <em>Hex Escape Sequence</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.HexEscapeSequenceImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getHexEscapeSequence()
   * @generated
   */
  int HEX_ESCAPE_SEQUENCE = 14;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HEX_ESCAPE_SEQUENCE__QUANTIFIER = ATOM_ESCAPE__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HEX_ESCAPE_SEQUENCE__CHARACTERS = ATOM_ESCAPE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HEX_ESCAPE_SEQUENCE__SEQUENCE = ATOM_ESCAPE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Hex Escape Sequence</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HEX_ESCAPE_SEQUENCE_FEATURE_COUNT = ATOM_ESCAPE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.UnicodeEscapeSequenceImpl <em>Unicode Escape Sequence</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.UnicodeEscapeSequenceImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getUnicodeEscapeSequence()
   * @generated
   */
  int UNICODE_ESCAPE_SEQUENCE = 15;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNICODE_ESCAPE_SEQUENCE__QUANTIFIER = ATOM_ESCAPE__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNICODE_ESCAPE_SEQUENCE__CHARACTERS = ATOM_ESCAPE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNICODE_ESCAPE_SEQUENCE__SEQUENCE = ATOM_ESCAPE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Unicode Escape Sequence</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNICODE_ESCAPE_SEQUENCE_FEATURE_COUNT = ATOM_ESCAPE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.IdentityEscapeSequenceImpl <em>Identity Escape Sequence</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.IdentityEscapeSequenceImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getIdentityEscapeSequence()
   * @generated
   */
  int IDENTITY_ESCAPE_SEQUENCE = 16;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IDENTITY_ESCAPE_SEQUENCE__QUANTIFIER = ATOM_ESCAPE__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IDENTITY_ESCAPE_SEQUENCE__CHARACTERS = ATOM_ESCAPE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IDENTITY_ESCAPE_SEQUENCE__SEQUENCE = ATOM_ESCAPE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Identity Escape Sequence</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IDENTITY_ESCAPE_SEQUENCE_FEATURE_COUNT = ATOM_ESCAPE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.DecimalEscapeSequenceImpl <em>Decimal Escape Sequence</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.DecimalEscapeSequenceImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getDecimalEscapeSequence()
   * @generated
   */
  int DECIMAL_ESCAPE_SEQUENCE = 17;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECIMAL_ESCAPE_SEQUENCE__QUANTIFIER = ATOM_ESCAPE__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECIMAL_ESCAPE_SEQUENCE__CHARACTERS = ATOM_ESCAPE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECIMAL_ESCAPE_SEQUENCE__SEQUENCE = ATOM_ESCAPE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Decimal Escape Sequence</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECIMAL_ESCAPE_SEQUENCE_FEATURE_COUNT = ATOM_ESCAPE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassImpl <em>Character Class</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClass()
   * @generated
   */
  int CHARACTER_CLASS = 18;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS__QUANTIFIER = PATTERN__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Negated</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS__NEGATED = PATTERN_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS__ELEMENTS = PATTERN_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Character Class</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_FEATURE_COUNT = PATTERN_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassElementImpl <em>Character Class Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassElementImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClassElement()
   * @generated
   */
  int CHARACTER_CLASS_ELEMENT = 19;

  /**
   * The number of structural features of the '<em>Character Class Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_ELEMENT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassAtomImpl <em>Character Class Atom</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassAtomImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClassAtom()
   * @generated
   */
  int CHARACTER_CLASS_ATOM = 20;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_ATOM__CHARACTERS = CHARACTER_CLASS_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Character Class Atom</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_ATOM_FEATURE_COUNT = CHARACTER_CLASS_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.EscapedCharacterClassAtomImpl <em>Escaped Character Class Atom</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.EscapedCharacterClassAtomImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getEscapedCharacterClassAtom()
   * @generated
   */
  int ESCAPED_CHARACTER_CLASS_ATOM = 21;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ESCAPED_CHARACTER_CLASS_ATOM__CHARACTERS = CHARACTER_CLASS_ATOM__CHARACTERS;

  /**
   * The number of structural features of the '<em>Escaped Character Class Atom</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ESCAPED_CHARACTER_CLASS_ATOM_FEATURE_COUNT = CHARACTER_CLASS_ATOM_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.BackspaceImpl <em>Backspace</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.BackspaceImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getBackspace()
   * @generated
   */
  int BACKSPACE = 22;

  /**
   * The feature id for the '<em><b>Characters</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BACKSPACE__CHARACTERS = ESCAPED_CHARACTER_CLASS_ATOM__CHARACTERS;

  /**
   * The number of structural features of the '<em>Backspace</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BACKSPACE_FEATURE_COUNT = ESCAPED_CHARACTER_CLASS_ATOM_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.GroupImpl <em>Group</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.GroupImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getGroup()
   * @generated
   */
  int GROUP = 23;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__QUANTIFIER = PATTERN__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Non Capturing</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__NON_CAPTURING = PATTERN_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Named</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__NAMED = PATTERN_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__NAME = PATTERN_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Pattern</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__PATTERN = PATTERN_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Group</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP_FEATURE_COUNT = PATTERN_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.QuantifierImpl <em>Quantifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.QuantifierImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getQuantifier()
   * @generated
   */
  int QUANTIFIER = 24;

  /**
   * The feature id for the '<em><b>Non Greedy</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUANTIFIER__NON_GREEDY = 0;

  /**
   * The number of structural features of the '<em>Quantifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUANTIFIER_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.SimpleQuantifierImpl <em>Simple Quantifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.SimpleQuantifierImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getSimpleQuantifier()
   * @generated
   */
  int SIMPLE_QUANTIFIER = 25;

  /**
   * The feature id for the '<em><b>Non Greedy</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SIMPLE_QUANTIFIER__NON_GREEDY = QUANTIFIER__NON_GREEDY;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SIMPLE_QUANTIFIER__QUANTIFIER = QUANTIFIER_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Simple Quantifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SIMPLE_QUANTIFIER_FEATURE_COUNT = QUANTIFIER_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.ExactQuantifierImpl <em>Exact Quantifier</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.ExactQuantifierImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getExactQuantifier()
   * @generated
   */
  int EXACT_QUANTIFIER = 26;

  /**
   * The feature id for the '<em><b>Non Greedy</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXACT_QUANTIFIER__NON_GREEDY = QUANTIFIER__NON_GREEDY;

  /**
   * The feature id for the '<em><b>Min</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXACT_QUANTIFIER__MIN = QUANTIFIER_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Max</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXACT_QUANTIFIER__MAX = QUANTIFIER_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Unbounded Max</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXACT_QUANTIFIER__UNBOUNDED_MAX = QUANTIFIER_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Exact Quantifier</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXACT_QUANTIFIER_FEATURE_COUNT = QUANTIFIER_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionFlagsImpl <em>Flags</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionFlagsImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getRegularExpressionFlags()
   * @generated
   */
  int REGULAR_EXPRESSION_FLAGS = 27;

  /**
   * The feature id for the '<em><b>Flags</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REGULAR_EXPRESSION_FLAGS__FLAGS = 0;

  /**
   * The number of structural features of the '<em>Flags</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REGULAR_EXPRESSION_FLAGS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.DisjunctionImpl <em>Disjunction</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.DisjunctionImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getDisjunction()
   * @generated
   */
  int DISJUNCTION = 28;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISJUNCTION__QUANTIFIER = PATTERN__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISJUNCTION__ELEMENTS = PATTERN_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Disjunction</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISJUNCTION_FEATURE_COUNT = PATTERN_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.SequenceImpl <em>Sequence</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.SequenceImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getSequence()
   * @generated
   */
  int SEQUENCE = 29;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SEQUENCE__QUANTIFIER = PATTERN__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SEQUENCE__ELEMENTS = PATTERN_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Sequence</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SEQUENCE_FEATURE_COUNT = PATTERN_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.LookAheadImpl <em>Look Ahead</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.LookAheadImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getLookAhead()
   * @generated
   */
  int LOOK_AHEAD = 30;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_AHEAD__QUANTIFIER = ABSTRACT_LOOK_AHEAD__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Not</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_AHEAD__NOT = ABSTRACT_LOOK_AHEAD__NOT;

  /**
   * The feature id for the '<em><b>Pattern</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_AHEAD__PATTERN = ABSTRACT_LOOK_AHEAD__PATTERN;

  /**
   * The number of structural features of the '<em>Look Ahead</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_AHEAD_FEATURE_COUNT = ABSTRACT_LOOK_AHEAD_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.LookBehindImpl <em>Look Behind</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.LookBehindImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getLookBehind()
   * @generated
   */
  int LOOK_BEHIND = 31;

  /**
   * The feature id for the '<em><b>Quantifier</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_BEHIND__QUANTIFIER = ABSTRACT_LOOK_AHEAD__QUANTIFIER;

  /**
   * The feature id for the '<em><b>Not</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_BEHIND__NOT = ABSTRACT_LOOK_AHEAD__NOT;

  /**
   * The feature id for the '<em><b>Pattern</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_BEHIND__PATTERN = ABSTRACT_LOOK_AHEAD__PATTERN;

  /**
   * The number of structural features of the '<em>Look Behind</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_BEHIND_FEATURE_COUNT = ABSTRACT_LOOK_AHEAD_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassRangeImpl <em>Character Class Range</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassRangeImpl
   * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClassRange()
   * @generated
   */
  int CHARACTER_CLASS_RANGE = 32;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_RANGE__LEFT = CHARACTER_CLASS_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_RANGE__RIGHT = CHARACTER_CLASS_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Character Class Range</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHARACTER_CLASS_RANGE_FEATURE_COUNT = CHARACTER_CLASS_ELEMENT_FEATURE_COUNT + 2;


  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral <em>Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Literal</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral
   * @generated
   */
  EClass getRegularExpressionLiteral();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral#getBody <em>Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Body</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral#getBody()
   * @see #getRegularExpressionLiteral()
   * @generated
   */
  EReference getRegularExpressionLiteral_Body();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral#getFlags <em>Flags</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Flags</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral#getFlags()
   * @see #getRegularExpressionLiteral()
   * @generated
   */
  EReference getRegularExpressionLiteral_Flags();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionBody <em>Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Body</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionBody
   * @generated
   */
  EClass getRegularExpressionBody();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionBody#getPattern <em>Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Pattern</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionBody#getPattern()
   * @see #getRegularExpressionBody()
   * @generated
   */
  EReference getRegularExpressionBody_Pattern();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.Pattern <em>Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Pattern</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Pattern
   * @generated
   */
  EClass getPattern();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.n4js.regex.regularExpression.Pattern#getQuantifier <em>Quantifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Quantifier</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Pattern#getQuantifier()
   * @see #getPattern()
   * @generated
   */
  EReference getPattern_Quantifier();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.Assertion <em>Assertion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Assertion</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Assertion
   * @generated
   */
  EClass getAssertion();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.LineStart <em>Line Start</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Line Start</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.LineStart
   * @generated
   */
  EClass getLineStart();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.LineEnd <em>Line End</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Line End</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.LineEnd
   * @generated
   */
  EClass getLineEnd();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.WordBoundary <em>Word Boundary</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Word Boundary</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.WordBoundary
   * @generated
   */
  EClass getWordBoundary();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.WordBoundary#isNot <em>Not</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Not</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.WordBoundary#isNot()
   * @see #getWordBoundary()
   * @generated
   */
  EAttribute getWordBoundary_Not();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.AbstractLookAhead <em>Abstract Look Ahead</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Abstract Look Ahead</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.AbstractLookAhead
   * @generated
   */
  EClass getAbstractLookAhead();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.AbstractLookAhead#isNot <em>Not</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Not</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.AbstractLookAhead#isNot()
   * @see #getAbstractLookAhead()
   * @generated
   */
  EAttribute getAbstractLookAhead_Not();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.n4js.regex.regularExpression.AbstractLookAhead#getPattern <em>Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Pattern</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.AbstractLookAhead#getPattern()
   * @see #getAbstractLookAhead()
   * @generated
   */
  EReference getAbstractLookAhead_Pattern();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.PatternCharacter <em>Pattern Character</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Pattern Character</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.PatternCharacter
   * @generated
   */
  EClass getPatternCharacter();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.PatternCharacter#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.PatternCharacter#getValue()
   * @see #getPatternCharacter()
   * @generated
   */
  EAttribute getPatternCharacter_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.Wildcard <em>Wildcard</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Wildcard</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Wildcard
   * @generated
   */
  EClass getWildcard();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.AtomEscape <em>Atom Escape</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Atom Escape</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.AtomEscape
   * @generated
   */
  EClass getAtomEscape();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence <em>Character Class Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Character Class Escape Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence
   * @generated
   */
  EClass getCharacterClassEscapeSequence();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence#getSequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence#getSequence()
   * @see #getCharacterClassEscapeSequence()
   * @generated
   */
  EAttribute getCharacterClassEscapeSequence_Sequence();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence <em>Character Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Character Escape Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence
   * @generated
   */
  EClass getCharacterEscapeSequence();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence#getSequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence#getSequence()
   * @see #getCharacterEscapeSequence()
   * @generated
   */
  EAttribute getCharacterEscapeSequence_Sequence();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence <em>Control Letter Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Control Letter Escape Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence
   * @generated
   */
  EClass getControlLetterEscapeSequence();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence#getSequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence#getSequence()
   * @see #getControlLetterEscapeSequence()
   * @generated
   */
  EAttribute getControlLetterEscapeSequence_Sequence();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.HexEscapeSequence <em>Hex Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Hex Escape Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.HexEscapeSequence
   * @generated
   */
  EClass getHexEscapeSequence();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.HexEscapeSequence#getSequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.HexEscapeSequence#getSequence()
   * @see #getHexEscapeSequence()
   * @generated
   */
  EAttribute getHexEscapeSequence_Sequence();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.UnicodeEscapeSequence <em>Unicode Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Unicode Escape Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.UnicodeEscapeSequence
   * @generated
   */
  EClass getUnicodeEscapeSequence();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.UnicodeEscapeSequence#getSequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.UnicodeEscapeSequence#getSequence()
   * @see #getUnicodeEscapeSequence()
   * @generated
   */
  EAttribute getUnicodeEscapeSequence_Sequence();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.IdentityEscapeSequence <em>Identity Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Identity Escape Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.IdentityEscapeSequence
   * @generated
   */
  EClass getIdentityEscapeSequence();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.IdentityEscapeSequence#getSequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.IdentityEscapeSequence#getSequence()
   * @see #getIdentityEscapeSequence()
   * @generated
   */
  EAttribute getIdentityEscapeSequence_Sequence();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence <em>Decimal Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Decimal Escape Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence
   * @generated
   */
  EClass getDecimalEscapeSequence();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence#getSequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence#getSequence()
   * @see #getDecimalEscapeSequence()
   * @generated
   */
  EAttribute getDecimalEscapeSequence_Sequence();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClass <em>Character Class</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Character Class</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClass
   * @generated
   */
  EClass getCharacterClass();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.CharacterClass#isNegated <em>Negated</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Negated</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClass#isNegated()
   * @see #getCharacterClass()
   * @generated
   */
  EAttribute getCharacterClass_Negated();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.regex.regularExpression.CharacterClass#getElements <em>Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClass#getElements()
   * @see #getCharacterClass()
   * @generated
   */
  EReference getCharacterClass_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassElement <em>Character Class Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Character Class Element</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassElement
   * @generated
   */
  EClass getCharacterClassElement();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassAtom <em>Character Class Atom</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Character Class Atom</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassAtom
   * @generated
   */
  EClass getCharacterClassAtom();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassAtom#getCharacters <em>Characters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Characters</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassAtom#getCharacters()
   * @see #getCharacterClassAtom()
   * @generated
   */
  EAttribute getCharacterClassAtom_Characters();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.EscapedCharacterClassAtom <em>Escaped Character Class Atom</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Escaped Character Class Atom</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.EscapedCharacterClassAtom
   * @generated
   */
  EClass getEscapedCharacterClassAtom();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.Backspace <em>Backspace</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Backspace</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Backspace
   * @generated
   */
  EClass getBackspace();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.Group <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Group</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Group
   * @generated
   */
  EClass getGroup();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.Group#isNonCapturing <em>Non Capturing</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Non Capturing</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Group#isNonCapturing()
   * @see #getGroup()
   * @generated
   */
  EAttribute getGroup_NonCapturing();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.Group#isNamed <em>Named</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Named</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Group#isNamed()
   * @see #getGroup()
   * @generated
   */
  EAttribute getGroup_Named();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.Group#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Group#getName()
   * @see #getGroup()
   * @generated
   */
  EAttribute getGroup_Name();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.n4js.regex.regularExpression.Group#getPattern <em>Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Pattern</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Group#getPattern()
   * @see #getGroup()
   * @generated
   */
  EReference getGroup_Pattern();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.Quantifier <em>Quantifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Quantifier</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Quantifier
   * @generated
   */
  EClass getQuantifier();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.Quantifier#isNonGreedy <em>Non Greedy</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Non Greedy</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Quantifier#isNonGreedy()
   * @see #getQuantifier()
   * @generated
   */
  EAttribute getQuantifier_NonGreedy();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.SimpleQuantifier <em>Simple Quantifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Simple Quantifier</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.SimpleQuantifier
   * @generated
   */
  EClass getSimpleQuantifier();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.SimpleQuantifier#getQuantifier <em>Quantifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Quantifier</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.SimpleQuantifier#getQuantifier()
   * @see #getSimpleQuantifier()
   * @generated
   */
  EAttribute getSimpleQuantifier_Quantifier();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier <em>Exact Quantifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Exact Quantifier</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.ExactQuantifier
   * @generated
   */
  EClass getExactQuantifier();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier#getMin <em>Min</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Min</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.ExactQuantifier#getMin()
   * @see #getExactQuantifier()
   * @generated
   */
  EAttribute getExactQuantifier_Min();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier#getMax <em>Max</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Max</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.ExactQuantifier#getMax()
   * @see #getExactQuantifier()
   * @generated
   */
  EAttribute getExactQuantifier_Max();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier#isUnboundedMax <em>Unbounded Max</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Unbounded Max</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.ExactQuantifier#isUnboundedMax()
   * @see #getExactQuantifier()
   * @generated
   */
  EAttribute getExactQuantifier_UnboundedMax();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags <em>Flags</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Flags</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags
   * @generated
   */
  EClass getRegularExpressionFlags();

  /**
   * Returns the meta object for the attribute list '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags#getFlags <em>Flags</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Flags</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags#getFlags()
   * @see #getRegularExpressionFlags()
   * @generated
   */
  EAttribute getRegularExpressionFlags_Flags();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.Disjunction <em>Disjunction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Disjunction</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Disjunction
   * @generated
   */
  EClass getDisjunction();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.regex.regularExpression.Disjunction#getElements <em>Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Disjunction#getElements()
   * @see #getDisjunction()
   * @generated
   */
  EReference getDisjunction_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.Sequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Sequence</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Sequence
   * @generated
   */
  EClass getSequence();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.regex.regularExpression.Sequence#getElements <em>Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.Sequence#getElements()
   * @see #getSequence()
   * @generated
   */
  EReference getSequence_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.LookAhead <em>Look Ahead</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Look Ahead</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.LookAhead
   * @generated
   */
  EClass getLookAhead();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.LookBehind <em>Look Behind</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Look Behind</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.LookBehind
   * @generated
   */
  EClass getLookBehind();

  /**
   * Returns the meta object for class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassRange <em>Character Class Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Character Class Range</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassRange
   * @generated
   */
  EClass getCharacterClassRange();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassRange#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassRange#getLeft()
   * @see #getCharacterClassRange()
   * @generated
   */
  EReference getCharacterClassRange_Left();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassRange#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassRange#getRight()
   * @see #getCharacterClassRange()
   * @generated
   */
  EReference getCharacterClassRange_Right();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  RegularExpressionFactory getRegularExpressionFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionLiteralImpl <em>Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionLiteralImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getRegularExpressionLiteral()
     * @generated
     */
    EClass REGULAR_EXPRESSION_LITERAL = eINSTANCE.getRegularExpressionLiteral();

    /**
     * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REGULAR_EXPRESSION_LITERAL__BODY = eINSTANCE.getRegularExpressionLiteral_Body();

    /**
     * The meta object literal for the '<em><b>Flags</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REGULAR_EXPRESSION_LITERAL__FLAGS = eINSTANCE.getRegularExpressionLiteral_Flags();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionBodyImpl <em>Body</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionBodyImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getRegularExpressionBody()
     * @generated
     */
    EClass REGULAR_EXPRESSION_BODY = eINSTANCE.getRegularExpressionBody();

    /**
     * The meta object literal for the '<em><b>Pattern</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REGULAR_EXPRESSION_BODY__PATTERN = eINSTANCE.getRegularExpressionBody_Pattern();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.PatternImpl <em>Pattern</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.PatternImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getPattern()
     * @generated
     */
    EClass PATTERN = eINSTANCE.getPattern();

    /**
     * The meta object literal for the '<em><b>Quantifier</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PATTERN__QUANTIFIER = eINSTANCE.getPattern_Quantifier();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.AssertionImpl <em>Assertion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.AssertionImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getAssertion()
     * @generated
     */
    EClass ASSERTION = eINSTANCE.getAssertion();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.LineStartImpl <em>Line Start</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.LineStartImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getLineStart()
     * @generated
     */
    EClass LINE_START = eINSTANCE.getLineStart();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.LineEndImpl <em>Line End</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.LineEndImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getLineEnd()
     * @generated
     */
    EClass LINE_END = eINSTANCE.getLineEnd();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.WordBoundaryImpl <em>Word Boundary</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.WordBoundaryImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getWordBoundary()
     * @generated
     */
    EClass WORD_BOUNDARY = eINSTANCE.getWordBoundary();

    /**
     * The meta object literal for the '<em><b>Not</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORD_BOUNDARY__NOT = eINSTANCE.getWordBoundary_Not();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.AbstractLookAheadImpl <em>Abstract Look Ahead</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.AbstractLookAheadImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getAbstractLookAhead()
     * @generated
     */
    EClass ABSTRACT_LOOK_AHEAD = eINSTANCE.getAbstractLookAhead();

    /**
     * The meta object literal for the '<em><b>Not</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ABSTRACT_LOOK_AHEAD__NOT = eINSTANCE.getAbstractLookAhead_Not();

    /**
     * The meta object literal for the '<em><b>Pattern</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ABSTRACT_LOOK_AHEAD__PATTERN = eINSTANCE.getAbstractLookAhead_Pattern();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.PatternCharacterImpl <em>Pattern Character</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.PatternCharacterImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getPatternCharacter()
     * @generated
     */
    EClass PATTERN_CHARACTER = eINSTANCE.getPatternCharacter();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PATTERN_CHARACTER__VALUE = eINSTANCE.getPatternCharacter_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.WildcardImpl <em>Wildcard</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.WildcardImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getWildcard()
     * @generated
     */
    EClass WILDCARD = eINSTANCE.getWildcard();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.AtomEscapeImpl <em>Atom Escape</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.AtomEscapeImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getAtomEscape()
     * @generated
     */
    EClass ATOM_ESCAPE = eINSTANCE.getAtomEscape();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassEscapeSequenceImpl <em>Character Class Escape Sequence</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassEscapeSequenceImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClassEscapeSequence()
     * @generated
     */
    EClass CHARACTER_CLASS_ESCAPE_SEQUENCE = eINSTANCE.getCharacterClassEscapeSequence();

    /**
     * The meta object literal for the '<em><b>Sequence</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE = eINSTANCE.getCharacterClassEscapeSequence_Sequence();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterEscapeSequenceImpl <em>Character Escape Sequence</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterEscapeSequenceImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterEscapeSequence()
     * @generated
     */
    EClass CHARACTER_ESCAPE_SEQUENCE = eINSTANCE.getCharacterEscapeSequence();

    /**
     * The meta object literal for the '<em><b>Sequence</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHARACTER_ESCAPE_SEQUENCE__SEQUENCE = eINSTANCE.getCharacterEscapeSequence_Sequence();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.ControlLetterEscapeSequenceImpl <em>Control Letter Escape Sequence</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.ControlLetterEscapeSequenceImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getControlLetterEscapeSequence()
     * @generated
     */
    EClass CONTROL_LETTER_ESCAPE_SEQUENCE = eINSTANCE.getControlLetterEscapeSequence();

    /**
     * The meta object literal for the '<em><b>Sequence</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTROL_LETTER_ESCAPE_SEQUENCE__SEQUENCE = eINSTANCE.getControlLetterEscapeSequence_Sequence();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.HexEscapeSequenceImpl <em>Hex Escape Sequence</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.HexEscapeSequenceImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getHexEscapeSequence()
     * @generated
     */
    EClass HEX_ESCAPE_SEQUENCE = eINSTANCE.getHexEscapeSequence();

    /**
     * The meta object literal for the '<em><b>Sequence</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute HEX_ESCAPE_SEQUENCE__SEQUENCE = eINSTANCE.getHexEscapeSequence_Sequence();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.UnicodeEscapeSequenceImpl <em>Unicode Escape Sequence</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.UnicodeEscapeSequenceImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getUnicodeEscapeSequence()
     * @generated
     */
    EClass UNICODE_ESCAPE_SEQUENCE = eINSTANCE.getUnicodeEscapeSequence();

    /**
     * The meta object literal for the '<em><b>Sequence</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UNICODE_ESCAPE_SEQUENCE__SEQUENCE = eINSTANCE.getUnicodeEscapeSequence_Sequence();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.IdentityEscapeSequenceImpl <em>Identity Escape Sequence</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.IdentityEscapeSequenceImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getIdentityEscapeSequence()
     * @generated
     */
    EClass IDENTITY_ESCAPE_SEQUENCE = eINSTANCE.getIdentityEscapeSequence();

    /**
     * The meta object literal for the '<em><b>Sequence</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IDENTITY_ESCAPE_SEQUENCE__SEQUENCE = eINSTANCE.getIdentityEscapeSequence_Sequence();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.DecimalEscapeSequenceImpl <em>Decimal Escape Sequence</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.DecimalEscapeSequenceImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getDecimalEscapeSequence()
     * @generated
     */
    EClass DECIMAL_ESCAPE_SEQUENCE = eINSTANCE.getDecimalEscapeSequence();

    /**
     * The meta object literal for the '<em><b>Sequence</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DECIMAL_ESCAPE_SEQUENCE__SEQUENCE = eINSTANCE.getDecimalEscapeSequence_Sequence();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassImpl <em>Character Class</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClass()
     * @generated
     */
    EClass CHARACTER_CLASS = eINSTANCE.getCharacterClass();

    /**
     * The meta object literal for the '<em><b>Negated</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHARACTER_CLASS__NEGATED = eINSTANCE.getCharacterClass_Negated();

    /**
     * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CHARACTER_CLASS__ELEMENTS = eINSTANCE.getCharacterClass_Elements();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassElementImpl <em>Character Class Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassElementImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClassElement()
     * @generated
     */
    EClass CHARACTER_CLASS_ELEMENT = eINSTANCE.getCharacterClassElement();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassAtomImpl <em>Character Class Atom</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassAtomImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClassAtom()
     * @generated
     */
    EClass CHARACTER_CLASS_ATOM = eINSTANCE.getCharacterClassAtom();

    /**
     * The meta object literal for the '<em><b>Characters</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHARACTER_CLASS_ATOM__CHARACTERS = eINSTANCE.getCharacterClassAtom_Characters();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.EscapedCharacterClassAtomImpl <em>Escaped Character Class Atom</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.EscapedCharacterClassAtomImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getEscapedCharacterClassAtom()
     * @generated
     */
    EClass ESCAPED_CHARACTER_CLASS_ATOM = eINSTANCE.getEscapedCharacterClassAtom();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.BackspaceImpl <em>Backspace</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.BackspaceImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getBackspace()
     * @generated
     */
    EClass BACKSPACE = eINSTANCE.getBackspace();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.GroupImpl <em>Group</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.GroupImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getGroup()
     * @generated
     */
    EClass GROUP = eINSTANCE.getGroup();

    /**
     * The meta object literal for the '<em><b>Non Capturing</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GROUP__NON_CAPTURING = eINSTANCE.getGroup_NonCapturing();

    /**
     * The meta object literal for the '<em><b>Named</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GROUP__NAMED = eINSTANCE.getGroup_Named();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GROUP__NAME = eINSTANCE.getGroup_Name();

    /**
     * The meta object literal for the '<em><b>Pattern</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GROUP__PATTERN = eINSTANCE.getGroup_Pattern();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.QuantifierImpl <em>Quantifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.QuantifierImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getQuantifier()
     * @generated
     */
    EClass QUANTIFIER = eINSTANCE.getQuantifier();

    /**
     * The meta object literal for the '<em><b>Non Greedy</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute QUANTIFIER__NON_GREEDY = eINSTANCE.getQuantifier_NonGreedy();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.SimpleQuantifierImpl <em>Simple Quantifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.SimpleQuantifierImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getSimpleQuantifier()
     * @generated
     */
    EClass SIMPLE_QUANTIFIER = eINSTANCE.getSimpleQuantifier();

    /**
     * The meta object literal for the '<em><b>Quantifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SIMPLE_QUANTIFIER__QUANTIFIER = eINSTANCE.getSimpleQuantifier_Quantifier();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.ExactQuantifierImpl <em>Exact Quantifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.ExactQuantifierImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getExactQuantifier()
     * @generated
     */
    EClass EXACT_QUANTIFIER = eINSTANCE.getExactQuantifier();

    /**
     * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXACT_QUANTIFIER__MIN = eINSTANCE.getExactQuantifier_Min();

    /**
     * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXACT_QUANTIFIER__MAX = eINSTANCE.getExactQuantifier_Max();

    /**
     * The meta object literal for the '<em><b>Unbounded Max</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXACT_QUANTIFIER__UNBOUNDED_MAX = eINSTANCE.getExactQuantifier_UnboundedMax();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionFlagsImpl <em>Flags</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionFlagsImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getRegularExpressionFlags()
     * @generated
     */
    EClass REGULAR_EXPRESSION_FLAGS = eINSTANCE.getRegularExpressionFlags();

    /**
     * The meta object literal for the '<em><b>Flags</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REGULAR_EXPRESSION_FLAGS__FLAGS = eINSTANCE.getRegularExpressionFlags_Flags();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.DisjunctionImpl <em>Disjunction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.DisjunctionImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getDisjunction()
     * @generated
     */
    EClass DISJUNCTION = eINSTANCE.getDisjunction();

    /**
     * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DISJUNCTION__ELEMENTS = eINSTANCE.getDisjunction_Elements();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.SequenceImpl <em>Sequence</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.SequenceImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getSequence()
     * @generated
     */
    EClass SEQUENCE = eINSTANCE.getSequence();

    /**
     * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SEQUENCE__ELEMENTS = eINSTANCE.getSequence_Elements();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.LookAheadImpl <em>Look Ahead</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.LookAheadImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getLookAhead()
     * @generated
     */
    EClass LOOK_AHEAD = eINSTANCE.getLookAhead();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.LookBehindImpl <em>Look Behind</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.LookBehindImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getLookBehind()
     * @generated
     */
    EClass LOOK_BEHIND = eINSTANCE.getLookBehind();

    /**
     * The meta object literal for the '{@link org.eclipse.n4js.regex.regularExpression.impl.CharacterClassRangeImpl <em>Character Class Range</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.n4js.regex.regularExpression.impl.CharacterClassRangeImpl
     * @see org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionPackageImpl#getCharacterClassRange()
     * @generated
     */
    EClass CHARACTER_CLASS_RANGE = eINSTANCE.getCharacterClassRange();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CHARACTER_CLASS_RANGE__LEFT = eINSTANCE.getCharacterClassRange_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CHARACTER_CLASS_RANGE__RIGHT = eINSTANCE.getCharacterClassRange_Right();

  }

} //RegularExpressionPackage
