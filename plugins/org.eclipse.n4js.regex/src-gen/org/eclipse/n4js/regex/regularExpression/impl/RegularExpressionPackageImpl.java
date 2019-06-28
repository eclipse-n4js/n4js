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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.n4js.regex.regularExpression.Assertion;
import org.eclipse.n4js.regex.regularExpression.AtomEscape;
import org.eclipse.n4js.regex.regularExpression.Backspace;
import org.eclipse.n4js.regex.regularExpression.CharacterClass;
import org.eclipse.n4js.regex.regularExpression.CharacterClassAtom;
import org.eclipse.n4js.regex.regularExpression.CharacterClassElement;
import org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.CharacterClassRange;
import org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.Disjunction;
import org.eclipse.n4js.regex.regularExpression.EscapedCharacterClassAtom;
import org.eclipse.n4js.regex.regularExpression.ExactQuantifier;
import org.eclipse.n4js.regex.regularExpression.Group;
import org.eclipse.n4js.regex.regularExpression.HexEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.IdentityEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.LineEnd;
import org.eclipse.n4js.regex.regularExpression.LineStart;
import org.eclipse.n4js.regex.regularExpression.LookAhead;
import org.eclipse.n4js.regex.regularExpression.Pattern;
import org.eclipse.n4js.regex.regularExpression.PatternCharacter;
import org.eclipse.n4js.regex.regularExpression.Quantifier;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionBody;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionFactory;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage;
import org.eclipse.n4js.regex.regularExpression.Sequence;
import org.eclipse.n4js.regex.regularExpression.SimpleQuantifier;
import org.eclipse.n4js.regex.regularExpression.UnicodeEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.Wildcard;
import org.eclipse.n4js.regex.regularExpression.WordBoundary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RegularExpressionPackageImpl extends EPackageImpl implements RegularExpressionPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass regularExpressionLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass regularExpressionBodyEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass patternEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass assertionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass lineStartEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass lineEndEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass wordBoundaryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass lookAheadEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass patternCharacterEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass wildcardEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass atomEscapeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass characterClassEscapeSequenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass characterEscapeSequenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass controlLetterEscapeSequenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass hexEscapeSequenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass unicodeEscapeSequenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass identityEscapeSequenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass decimalEscapeSequenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass characterClassEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass characterClassElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass characterClassAtomEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass escapedCharacterClassAtomEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass backspaceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass groupEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass quantifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass simpleQuantifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass exactQuantifierEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass regularExpressionFlagsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass disjunctionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass sequenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass characterClassRangeEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private RegularExpressionPackageImpl()
  {
    super(eNS_URI, RegularExpressionFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link RegularExpressionPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static RegularExpressionPackage init()
  {
    if (isInited) return (RegularExpressionPackage)EPackage.Registry.INSTANCE.getEPackage(RegularExpressionPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredRegularExpressionPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    RegularExpressionPackageImpl theRegularExpressionPackage = registeredRegularExpressionPackage instanceof RegularExpressionPackageImpl ? (RegularExpressionPackageImpl)registeredRegularExpressionPackage : new RegularExpressionPackageImpl();

    isInited = true;

    // Create package meta-data objects
    theRegularExpressionPackage.createPackageContents();

    // Initialize created meta-data
    theRegularExpressionPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theRegularExpressionPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(RegularExpressionPackage.eNS_URI, theRegularExpressionPackage);
    return theRegularExpressionPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getRegularExpressionLiteral()
  {
    return regularExpressionLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRegularExpressionLiteral_Body()
  {
    return (EReference)regularExpressionLiteralEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRegularExpressionLiteral_Flags()
  {
    return (EReference)regularExpressionLiteralEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getRegularExpressionBody()
  {
    return regularExpressionBodyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRegularExpressionBody_Pattern()
  {
    return (EReference)regularExpressionBodyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPattern()
  {
    return patternEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPattern_Quantifier()
  {
    return (EReference)patternEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getAssertion()
  {
    return assertionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getLineStart()
  {
    return lineStartEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getLineEnd()
  {
    return lineEndEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getWordBoundary()
  {
    return wordBoundaryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getWordBoundary_Not()
  {
    return (EAttribute)wordBoundaryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getLookAhead()
  {
    return lookAheadEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLookAhead_Not()
  {
    return (EAttribute)lookAheadEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getLookAhead_Pattern()
  {
    return (EReference)lookAheadEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPatternCharacter()
  {
    return patternCharacterEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPatternCharacter_Value()
  {
    return (EAttribute)patternCharacterEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getWildcard()
  {
    return wildcardEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getAtomEscape()
  {
    return atomEscapeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCharacterClassEscapeSequence()
  {
    return characterClassEscapeSequenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCharacterClassEscapeSequence_Sequence()
  {
    return (EAttribute)characterClassEscapeSequenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCharacterEscapeSequence()
  {
    return characterEscapeSequenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCharacterEscapeSequence_Sequence()
  {
    return (EAttribute)characterEscapeSequenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getControlLetterEscapeSequence()
  {
    return controlLetterEscapeSequenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getControlLetterEscapeSequence_Sequence()
  {
    return (EAttribute)controlLetterEscapeSequenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getHexEscapeSequence()
  {
    return hexEscapeSequenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getHexEscapeSequence_Sequence()
  {
    return (EAttribute)hexEscapeSequenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getUnicodeEscapeSequence()
  {
    return unicodeEscapeSequenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getUnicodeEscapeSequence_Sequence()
  {
    return (EAttribute)unicodeEscapeSequenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getIdentityEscapeSequence()
  {
    return identityEscapeSequenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getIdentityEscapeSequence_Sequence()
  {
    return (EAttribute)identityEscapeSequenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDecimalEscapeSequence()
  {
    return decimalEscapeSequenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDecimalEscapeSequence_Sequence()
  {
    return (EAttribute)decimalEscapeSequenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCharacterClass()
  {
    return characterClassEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCharacterClass_Negated()
  {
    return (EAttribute)characterClassEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getCharacterClass_Elements()
  {
    return (EReference)characterClassEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCharacterClassElement()
  {
    return characterClassElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCharacterClassAtom()
  {
    return characterClassAtomEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCharacterClassAtom_Character()
  {
    return (EAttribute)characterClassAtomEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getEscapedCharacterClassAtom()
  {
    return escapedCharacterClassAtomEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getBackspace()
  {
    return backspaceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getGroup()
  {
    return groupEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getGroup_NonCapturing()
  {
    return (EAttribute)groupEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getGroup_Pattern()
  {
    return (EReference)groupEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getQuantifier()
  {
    return quantifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getQuantifier_NonGreedy()
  {
    return (EAttribute)quantifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getSimpleQuantifier()
  {
    return simpleQuantifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSimpleQuantifier_Quantifier()
  {
    return (EAttribute)simpleQuantifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getExactQuantifier()
  {
    return exactQuantifierEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getExactQuantifier_Min()
  {
    return (EAttribute)exactQuantifierEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getExactQuantifier_Max()
  {
    return (EAttribute)exactQuantifierEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getExactQuantifier_UnboundedMax()
  {
    return (EAttribute)exactQuantifierEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getRegularExpressionFlags()
  {
    return regularExpressionFlagsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getRegularExpressionFlags_Flags()
  {
    return (EAttribute)regularExpressionFlagsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDisjunction()
  {
    return disjunctionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getDisjunction_Elements()
  {
    return (EReference)disjunctionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getSequence()
  {
    return sequenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getSequence_Elements()
  {
    return (EReference)sequenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCharacterClassRange()
  {
    return characterClassRangeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getCharacterClassRange_Left()
  {
    return (EReference)characterClassRangeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getCharacterClassRange_Right()
  {
    return (EReference)characterClassRangeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public RegularExpressionFactory getRegularExpressionFactory()
  {
    return (RegularExpressionFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    regularExpressionLiteralEClass = createEClass(REGULAR_EXPRESSION_LITERAL);
    createEReference(regularExpressionLiteralEClass, REGULAR_EXPRESSION_LITERAL__BODY);
    createEReference(regularExpressionLiteralEClass, REGULAR_EXPRESSION_LITERAL__FLAGS);

    regularExpressionBodyEClass = createEClass(REGULAR_EXPRESSION_BODY);
    createEReference(regularExpressionBodyEClass, REGULAR_EXPRESSION_BODY__PATTERN);

    patternEClass = createEClass(PATTERN);
    createEReference(patternEClass, PATTERN__QUANTIFIER);

    assertionEClass = createEClass(ASSERTION);

    lineStartEClass = createEClass(LINE_START);

    lineEndEClass = createEClass(LINE_END);

    wordBoundaryEClass = createEClass(WORD_BOUNDARY);
    createEAttribute(wordBoundaryEClass, WORD_BOUNDARY__NOT);

    lookAheadEClass = createEClass(LOOK_AHEAD);
    createEAttribute(lookAheadEClass, LOOK_AHEAD__NOT);
    createEReference(lookAheadEClass, LOOK_AHEAD__PATTERN);

    patternCharacterEClass = createEClass(PATTERN_CHARACTER);
    createEAttribute(patternCharacterEClass, PATTERN_CHARACTER__VALUE);

    wildcardEClass = createEClass(WILDCARD);

    atomEscapeEClass = createEClass(ATOM_ESCAPE);

    characterClassEscapeSequenceEClass = createEClass(CHARACTER_CLASS_ESCAPE_SEQUENCE);
    createEAttribute(characterClassEscapeSequenceEClass, CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE);

    characterEscapeSequenceEClass = createEClass(CHARACTER_ESCAPE_SEQUENCE);
    createEAttribute(characterEscapeSequenceEClass, CHARACTER_ESCAPE_SEQUENCE__SEQUENCE);

    controlLetterEscapeSequenceEClass = createEClass(CONTROL_LETTER_ESCAPE_SEQUENCE);
    createEAttribute(controlLetterEscapeSequenceEClass, CONTROL_LETTER_ESCAPE_SEQUENCE__SEQUENCE);

    hexEscapeSequenceEClass = createEClass(HEX_ESCAPE_SEQUENCE);
    createEAttribute(hexEscapeSequenceEClass, HEX_ESCAPE_SEQUENCE__SEQUENCE);

    unicodeEscapeSequenceEClass = createEClass(UNICODE_ESCAPE_SEQUENCE);
    createEAttribute(unicodeEscapeSequenceEClass, UNICODE_ESCAPE_SEQUENCE__SEQUENCE);

    identityEscapeSequenceEClass = createEClass(IDENTITY_ESCAPE_SEQUENCE);
    createEAttribute(identityEscapeSequenceEClass, IDENTITY_ESCAPE_SEQUENCE__SEQUENCE);

    decimalEscapeSequenceEClass = createEClass(DECIMAL_ESCAPE_SEQUENCE);
    createEAttribute(decimalEscapeSequenceEClass, DECIMAL_ESCAPE_SEQUENCE__SEQUENCE);

    characterClassEClass = createEClass(CHARACTER_CLASS);
    createEAttribute(characterClassEClass, CHARACTER_CLASS__NEGATED);
    createEReference(characterClassEClass, CHARACTER_CLASS__ELEMENTS);

    characterClassElementEClass = createEClass(CHARACTER_CLASS_ELEMENT);

    characterClassAtomEClass = createEClass(CHARACTER_CLASS_ATOM);
    createEAttribute(characterClassAtomEClass, CHARACTER_CLASS_ATOM__CHARACTER);

    escapedCharacterClassAtomEClass = createEClass(ESCAPED_CHARACTER_CLASS_ATOM);

    backspaceEClass = createEClass(BACKSPACE);

    groupEClass = createEClass(GROUP);
    createEAttribute(groupEClass, GROUP__NON_CAPTURING);
    createEReference(groupEClass, GROUP__PATTERN);

    quantifierEClass = createEClass(QUANTIFIER);
    createEAttribute(quantifierEClass, QUANTIFIER__NON_GREEDY);

    simpleQuantifierEClass = createEClass(SIMPLE_QUANTIFIER);
    createEAttribute(simpleQuantifierEClass, SIMPLE_QUANTIFIER__QUANTIFIER);

    exactQuantifierEClass = createEClass(EXACT_QUANTIFIER);
    createEAttribute(exactQuantifierEClass, EXACT_QUANTIFIER__MIN);
    createEAttribute(exactQuantifierEClass, EXACT_QUANTIFIER__MAX);
    createEAttribute(exactQuantifierEClass, EXACT_QUANTIFIER__UNBOUNDED_MAX);

    regularExpressionFlagsEClass = createEClass(REGULAR_EXPRESSION_FLAGS);
    createEAttribute(regularExpressionFlagsEClass, REGULAR_EXPRESSION_FLAGS__FLAGS);

    disjunctionEClass = createEClass(DISJUNCTION);
    createEReference(disjunctionEClass, DISJUNCTION__ELEMENTS);

    sequenceEClass = createEClass(SEQUENCE);
    createEReference(sequenceEClass, SEQUENCE__ELEMENTS);

    characterClassRangeEClass = createEClass(CHARACTER_CLASS_RANGE);
    createEReference(characterClassRangeEClass, CHARACTER_CLASS_RANGE__LEFT);
    createEReference(characterClassRangeEClass, CHARACTER_CLASS_RANGE__RIGHT);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    assertionEClass.getESuperTypes().add(this.getPattern());
    lineStartEClass.getESuperTypes().add(this.getAssertion());
    lineEndEClass.getESuperTypes().add(this.getAssertion());
    wordBoundaryEClass.getESuperTypes().add(this.getAssertion());
    lookAheadEClass.getESuperTypes().add(this.getAssertion());
    patternCharacterEClass.getESuperTypes().add(this.getPattern());
    wildcardEClass.getESuperTypes().add(this.getPattern());
    atomEscapeEClass.getESuperTypes().add(this.getPattern());
    characterClassEscapeSequenceEClass.getESuperTypes().add(this.getAtomEscape());
    characterClassEscapeSequenceEClass.getESuperTypes().add(this.getEscapedCharacterClassAtom());
    characterEscapeSequenceEClass.getESuperTypes().add(this.getAtomEscape());
    characterEscapeSequenceEClass.getESuperTypes().add(this.getEscapedCharacterClassAtom());
    controlLetterEscapeSequenceEClass.getESuperTypes().add(this.getAtomEscape());
    controlLetterEscapeSequenceEClass.getESuperTypes().add(this.getEscapedCharacterClassAtom());
    hexEscapeSequenceEClass.getESuperTypes().add(this.getAtomEscape());
    hexEscapeSequenceEClass.getESuperTypes().add(this.getEscapedCharacterClassAtom());
    unicodeEscapeSequenceEClass.getESuperTypes().add(this.getAtomEscape());
    unicodeEscapeSequenceEClass.getESuperTypes().add(this.getEscapedCharacterClassAtom());
    identityEscapeSequenceEClass.getESuperTypes().add(this.getAtomEscape());
    identityEscapeSequenceEClass.getESuperTypes().add(this.getEscapedCharacterClassAtom());
    decimalEscapeSequenceEClass.getESuperTypes().add(this.getAtomEscape());
    decimalEscapeSequenceEClass.getESuperTypes().add(this.getEscapedCharacterClassAtom());
    characterClassEClass.getESuperTypes().add(this.getPattern());
    characterClassAtomEClass.getESuperTypes().add(this.getCharacterClassElement());
    escapedCharacterClassAtomEClass.getESuperTypes().add(this.getCharacterClassAtom());
    backspaceEClass.getESuperTypes().add(this.getEscapedCharacterClassAtom());
    groupEClass.getESuperTypes().add(this.getPattern());
    simpleQuantifierEClass.getESuperTypes().add(this.getQuantifier());
    exactQuantifierEClass.getESuperTypes().add(this.getQuantifier());
    disjunctionEClass.getESuperTypes().add(this.getPattern());
    sequenceEClass.getESuperTypes().add(this.getPattern());
    characterClassRangeEClass.getESuperTypes().add(this.getCharacterClassElement());

    // Initialize classes and features; add operations and parameters
    initEClass(regularExpressionLiteralEClass, RegularExpressionLiteral.class, "RegularExpressionLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRegularExpressionLiteral_Body(), this.getRegularExpressionBody(), null, "body", null, 0, 1, RegularExpressionLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRegularExpressionLiteral_Flags(), this.getRegularExpressionFlags(), null, "flags", null, 0, 1, RegularExpressionLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(regularExpressionBodyEClass, RegularExpressionBody.class, "RegularExpressionBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRegularExpressionBody_Pattern(), this.getPattern(), null, "pattern", null, 0, 1, RegularExpressionBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(patternEClass, Pattern.class, "Pattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPattern_Quantifier(), this.getQuantifier(), null, "quantifier", null, 0, 1, Pattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(assertionEClass, Assertion.class, "Assertion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(lineStartEClass, LineStart.class, "LineStart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(lineEndEClass, LineEnd.class, "LineEnd", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(wordBoundaryEClass, WordBoundary.class, "WordBoundary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getWordBoundary_Not(), ecorePackage.getEBoolean(), "not", null, 0, 1, WordBoundary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(lookAheadEClass, LookAhead.class, "LookAhead", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLookAhead_Not(), ecorePackage.getEBoolean(), "not", null, 0, 1, LookAhead.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLookAhead_Pattern(), this.getPattern(), null, "pattern", null, 0, 1, LookAhead.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(patternCharacterEClass, PatternCharacter.class, "PatternCharacter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPatternCharacter_Value(), ecorePackage.getEString(), "value", null, 0, 1, PatternCharacter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(wildcardEClass, Wildcard.class, "Wildcard", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(atomEscapeEClass, AtomEscape.class, "AtomEscape", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(characterClassEscapeSequenceEClass, CharacterClassEscapeSequence.class, "CharacterClassEscapeSequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCharacterClassEscapeSequence_Sequence(), ecorePackage.getEString(), "sequence", null, 0, 1, CharacterClassEscapeSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(characterEscapeSequenceEClass, CharacterEscapeSequence.class, "CharacterEscapeSequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCharacterEscapeSequence_Sequence(), ecorePackage.getEString(), "sequence", null, 0, 1, CharacterEscapeSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(controlLetterEscapeSequenceEClass, ControlLetterEscapeSequence.class, "ControlLetterEscapeSequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getControlLetterEscapeSequence_Sequence(), ecorePackage.getEString(), "sequence", null, 0, 1, ControlLetterEscapeSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(hexEscapeSequenceEClass, HexEscapeSequence.class, "HexEscapeSequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getHexEscapeSequence_Sequence(), ecorePackage.getEString(), "sequence", null, 0, 1, HexEscapeSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(unicodeEscapeSequenceEClass, UnicodeEscapeSequence.class, "UnicodeEscapeSequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getUnicodeEscapeSequence_Sequence(), ecorePackage.getEString(), "sequence", null, 0, 1, UnicodeEscapeSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(identityEscapeSequenceEClass, IdentityEscapeSequence.class, "IdentityEscapeSequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getIdentityEscapeSequence_Sequence(), ecorePackage.getEString(), "sequence", null, 0, 1, IdentityEscapeSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(decimalEscapeSequenceEClass, DecimalEscapeSequence.class, "DecimalEscapeSequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDecimalEscapeSequence_Sequence(), ecorePackage.getEString(), "sequence", null, 0, 1, DecimalEscapeSequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(characterClassEClass, CharacterClass.class, "CharacterClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCharacterClass_Negated(), ecorePackage.getEBoolean(), "negated", null, 0, 1, CharacterClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCharacterClass_Elements(), this.getCharacterClassElement(), null, "elements", null, 0, -1, CharacterClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(characterClassElementEClass, CharacterClassElement.class, "CharacterClassElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(characterClassAtomEClass, CharacterClassAtom.class, "CharacterClassAtom", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCharacterClassAtom_Character(), ecorePackage.getEString(), "character", null, 0, 1, CharacterClassAtom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(escapedCharacterClassAtomEClass, EscapedCharacterClassAtom.class, "EscapedCharacterClassAtom", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(backspaceEClass, Backspace.class, "Backspace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(groupEClass, Group.class, "Group", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGroup_NonCapturing(), ecorePackage.getEBoolean(), "nonCapturing", null, 0, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGroup_Pattern(), this.getPattern(), null, "pattern", null, 0, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(quantifierEClass, Quantifier.class, "Quantifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getQuantifier_NonGreedy(), ecorePackage.getEBoolean(), "nonGreedy", null, 0, 1, Quantifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(simpleQuantifierEClass, SimpleQuantifier.class, "SimpleQuantifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSimpleQuantifier_Quantifier(), ecorePackage.getEString(), "quantifier", null, 0, 1, SimpleQuantifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(exactQuantifierEClass, ExactQuantifier.class, "ExactQuantifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getExactQuantifier_Min(), ecorePackage.getEInt(), "min", null, 0, 1, ExactQuantifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExactQuantifier_Max(), ecorePackage.getEInt(), "max", null, 0, 1, ExactQuantifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExactQuantifier_UnboundedMax(), ecorePackage.getEBoolean(), "unboundedMax", null, 0, 1, ExactQuantifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(regularExpressionFlagsEClass, RegularExpressionFlags.class, "RegularExpressionFlags", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRegularExpressionFlags_Flags(), ecorePackage.getEString(), "flags", null, 0, -1, RegularExpressionFlags.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(disjunctionEClass, Disjunction.class, "Disjunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getDisjunction_Elements(), this.getPattern(), null, "elements", null, 0, -1, Disjunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(sequenceEClass, Sequence.class, "Sequence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSequence_Elements(), this.getPattern(), null, "elements", null, 0, -1, Sequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(characterClassRangeEClass, CharacterClassRange.class, "CharacterClassRange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCharacterClassRange_Left(), this.getCharacterClassAtom(), null, "left", null, 0, 1, CharacterClassRange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCharacterClassRange_Right(), this.getCharacterClassAtom(), null, "right", null, 0, 1, CharacterClassRange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //RegularExpressionPackageImpl
