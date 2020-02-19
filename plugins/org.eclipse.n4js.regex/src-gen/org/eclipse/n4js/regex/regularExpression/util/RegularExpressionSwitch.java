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
package org.eclipse.n4js.regex.regularExpression.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.n4js.regex.regularExpression.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage
 * @generated
 */
public class RegularExpressionSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static RegularExpressionPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegularExpressionSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = RegularExpressionPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL:
      {
        RegularExpressionLiteral regularExpressionLiteral = (RegularExpressionLiteral)theEObject;
        T result = caseRegularExpressionLiteral(regularExpressionLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.REGULAR_EXPRESSION_BODY:
      {
        RegularExpressionBody regularExpressionBody = (RegularExpressionBody)theEObject;
        T result = caseRegularExpressionBody(regularExpressionBody);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.PATTERN:
      {
        Pattern pattern = (Pattern)theEObject;
        T result = casePattern(pattern);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.ASSERTION:
      {
        Assertion assertion = (Assertion)theEObject;
        T result = caseAssertion(assertion);
        if (result == null) result = casePattern(assertion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.LINE_START:
      {
        LineStart lineStart = (LineStart)theEObject;
        T result = caseLineStart(lineStart);
        if (result == null) result = caseAssertion(lineStart);
        if (result == null) result = casePattern(lineStart);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.LINE_END:
      {
        LineEnd lineEnd = (LineEnd)theEObject;
        T result = caseLineEnd(lineEnd);
        if (result == null) result = caseAssertion(lineEnd);
        if (result == null) result = casePattern(lineEnd);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.WORD_BOUNDARY:
      {
        WordBoundary wordBoundary = (WordBoundary)theEObject;
        T result = caseWordBoundary(wordBoundary);
        if (result == null) result = caseAssertion(wordBoundary);
        if (result == null) result = casePattern(wordBoundary);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.ABSTRACT_LOOK_AHEAD:
      {
        AbstractLookAhead abstractLookAhead = (AbstractLookAhead)theEObject;
        T result = caseAbstractLookAhead(abstractLookAhead);
        if (result == null) result = caseAssertion(abstractLookAhead);
        if (result == null) result = casePattern(abstractLookAhead);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.PATTERN_CHARACTER:
      {
        PatternCharacter patternCharacter = (PatternCharacter)theEObject;
        T result = casePatternCharacter(patternCharacter);
        if (result == null) result = casePattern(patternCharacter);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.WILDCARD:
      {
        Wildcard wildcard = (Wildcard)theEObject;
        T result = caseWildcard(wildcard);
        if (result == null) result = casePattern(wildcard);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.ATOM_ESCAPE:
      {
        AtomEscape atomEscape = (AtomEscape)theEObject;
        T result = caseAtomEscape(atomEscape);
        if (result == null) result = casePattern(atomEscape);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE:
      {
        CharacterClassEscapeSequence characterClassEscapeSequence = (CharacterClassEscapeSequence)theEObject;
        T result = caseCharacterClassEscapeSequence(characterClassEscapeSequence);
        if (result == null) result = caseAtomEscape(characterClassEscapeSequence);
        if (result == null) result = caseEscapedCharacterClassAtom(characterClassEscapeSequence);
        if (result == null) result = casePattern(characterClassEscapeSequence);
        if (result == null) result = caseCharacterClassAtom(characterClassEscapeSequence);
        if (result == null) result = caseCharacterClassElement(characterClassEscapeSequence);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.CHARACTER_ESCAPE_SEQUENCE:
      {
        CharacterEscapeSequence characterEscapeSequence = (CharacterEscapeSequence)theEObject;
        T result = caseCharacterEscapeSequence(characterEscapeSequence);
        if (result == null) result = caseAtomEscape(characterEscapeSequence);
        if (result == null) result = caseEscapedCharacterClassAtom(characterEscapeSequence);
        if (result == null) result = casePattern(characterEscapeSequence);
        if (result == null) result = caseCharacterClassAtom(characterEscapeSequence);
        if (result == null) result = caseCharacterClassElement(characterEscapeSequence);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.CONTROL_LETTER_ESCAPE_SEQUENCE:
      {
        ControlLetterEscapeSequence controlLetterEscapeSequence = (ControlLetterEscapeSequence)theEObject;
        T result = caseControlLetterEscapeSequence(controlLetterEscapeSequence);
        if (result == null) result = caseAtomEscape(controlLetterEscapeSequence);
        if (result == null) result = caseEscapedCharacterClassAtom(controlLetterEscapeSequence);
        if (result == null) result = casePattern(controlLetterEscapeSequence);
        if (result == null) result = caseCharacterClassAtom(controlLetterEscapeSequence);
        if (result == null) result = caseCharacterClassElement(controlLetterEscapeSequence);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.HEX_ESCAPE_SEQUENCE:
      {
        HexEscapeSequence hexEscapeSequence = (HexEscapeSequence)theEObject;
        T result = caseHexEscapeSequence(hexEscapeSequence);
        if (result == null) result = caseAtomEscape(hexEscapeSequence);
        if (result == null) result = caseEscapedCharacterClassAtom(hexEscapeSequence);
        if (result == null) result = casePattern(hexEscapeSequence);
        if (result == null) result = caseCharacterClassAtom(hexEscapeSequence);
        if (result == null) result = caseCharacterClassElement(hexEscapeSequence);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE:
      {
        UnicodeEscapeSequence unicodeEscapeSequence = (UnicodeEscapeSequence)theEObject;
        T result = caseUnicodeEscapeSequence(unicodeEscapeSequence);
        if (result == null) result = caseAtomEscape(unicodeEscapeSequence);
        if (result == null) result = caseEscapedCharacterClassAtom(unicodeEscapeSequence);
        if (result == null) result = casePattern(unicodeEscapeSequence);
        if (result == null) result = caseCharacterClassAtom(unicodeEscapeSequence);
        if (result == null) result = caseCharacterClassElement(unicodeEscapeSequence);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.IDENTITY_ESCAPE_SEQUENCE:
      {
        IdentityEscapeSequence identityEscapeSequence = (IdentityEscapeSequence)theEObject;
        T result = caseIdentityEscapeSequence(identityEscapeSequence);
        if (result == null) result = caseAtomEscape(identityEscapeSequence);
        if (result == null) result = caseEscapedCharacterClassAtom(identityEscapeSequence);
        if (result == null) result = casePattern(identityEscapeSequence);
        if (result == null) result = caseCharacterClassAtom(identityEscapeSequence);
        if (result == null) result = caseCharacterClassElement(identityEscapeSequence);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.DECIMAL_ESCAPE_SEQUENCE:
      {
        DecimalEscapeSequence decimalEscapeSequence = (DecimalEscapeSequence)theEObject;
        T result = caseDecimalEscapeSequence(decimalEscapeSequence);
        if (result == null) result = caseAtomEscape(decimalEscapeSequence);
        if (result == null) result = caseEscapedCharacterClassAtom(decimalEscapeSequence);
        if (result == null) result = casePattern(decimalEscapeSequence);
        if (result == null) result = caseCharacterClassAtom(decimalEscapeSequence);
        if (result == null) result = caseCharacterClassElement(decimalEscapeSequence);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.CHARACTER_CLASS:
      {
        CharacterClass characterClass = (CharacterClass)theEObject;
        T result = caseCharacterClass(characterClass);
        if (result == null) result = casePattern(characterClass);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.CHARACTER_CLASS_ELEMENT:
      {
        CharacterClassElement characterClassElement = (CharacterClassElement)theEObject;
        T result = caseCharacterClassElement(characterClassElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.CHARACTER_CLASS_ATOM:
      {
        CharacterClassAtom characterClassAtom = (CharacterClassAtom)theEObject;
        T result = caseCharacterClassAtom(characterClassAtom);
        if (result == null) result = caseCharacterClassElement(characterClassAtom);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.ESCAPED_CHARACTER_CLASS_ATOM:
      {
        EscapedCharacterClassAtom escapedCharacterClassAtom = (EscapedCharacterClassAtom)theEObject;
        T result = caseEscapedCharacterClassAtom(escapedCharacterClassAtom);
        if (result == null) result = caseCharacterClassAtom(escapedCharacterClassAtom);
        if (result == null) result = caseCharacterClassElement(escapedCharacterClassAtom);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.BACKSPACE:
      {
        Backspace backspace = (Backspace)theEObject;
        T result = caseBackspace(backspace);
        if (result == null) result = caseEscapedCharacterClassAtom(backspace);
        if (result == null) result = caseCharacterClassAtom(backspace);
        if (result == null) result = caseCharacterClassElement(backspace);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.GROUP:
      {
        Group group = (Group)theEObject;
        T result = caseGroup(group);
        if (result == null) result = casePattern(group);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.QUANTIFIER:
      {
        Quantifier quantifier = (Quantifier)theEObject;
        T result = caseQuantifier(quantifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.SIMPLE_QUANTIFIER:
      {
        SimpleQuantifier simpleQuantifier = (SimpleQuantifier)theEObject;
        T result = caseSimpleQuantifier(simpleQuantifier);
        if (result == null) result = caseQuantifier(simpleQuantifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.EXACT_QUANTIFIER:
      {
        ExactQuantifier exactQuantifier = (ExactQuantifier)theEObject;
        T result = caseExactQuantifier(exactQuantifier);
        if (result == null) result = caseQuantifier(exactQuantifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.REGULAR_EXPRESSION_FLAGS:
      {
        RegularExpressionFlags regularExpressionFlags = (RegularExpressionFlags)theEObject;
        T result = caseRegularExpressionFlags(regularExpressionFlags);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.DISJUNCTION:
      {
        Disjunction disjunction = (Disjunction)theEObject;
        T result = caseDisjunction(disjunction);
        if (result == null) result = casePattern(disjunction);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.SEQUENCE:
      {
        Sequence sequence = (Sequence)theEObject;
        T result = caseSequence(sequence);
        if (result == null) result = casePattern(sequence);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.LOOK_AHEAD:
      {
        LookAhead lookAhead = (LookAhead)theEObject;
        T result = caseLookAhead(lookAhead);
        if (result == null) result = caseAbstractLookAhead(lookAhead);
        if (result == null) result = caseAssertion(lookAhead);
        if (result == null) result = casePattern(lookAhead);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.LOOK_BEHIND:
      {
        LookBehind lookBehind = (LookBehind)theEObject;
        T result = caseLookBehind(lookBehind);
        if (result == null) result = caseAbstractLookAhead(lookBehind);
        if (result == null) result = caseAssertion(lookBehind);
        if (result == null) result = casePattern(lookBehind);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case RegularExpressionPackage.CHARACTER_CLASS_RANGE:
      {
        CharacterClassRange characterClassRange = (CharacterClassRange)theEObject;
        T result = caseCharacterClassRange(characterClassRange);
        if (result == null) result = caseCharacterClassElement(characterClassRange);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRegularExpressionLiteral(RegularExpressionLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Body</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Body</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRegularExpressionBody(RegularExpressionBody object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Pattern</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Pattern</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePattern(Pattern object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Assertion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Assertion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAssertion(Assertion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Line Start</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Line Start</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLineStart(LineStart object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Line End</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Line End</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLineEnd(LineEnd object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Word Boundary</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Word Boundary</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseWordBoundary(WordBoundary object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Abstract Look Ahead</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Abstract Look Ahead</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAbstractLookAhead(AbstractLookAhead object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Pattern Character</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Pattern Character</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePatternCharacter(PatternCharacter object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Wildcard</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Wildcard</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseWildcard(Wildcard object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Atom Escape</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Atom Escape</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAtomEscape(AtomEscape object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Character Class Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Character Class Escape Sequence</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCharacterClassEscapeSequence(CharacterClassEscapeSequence object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Character Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Character Escape Sequence</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCharacterEscapeSequence(CharacterEscapeSequence object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Control Letter Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Control Letter Escape Sequence</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseControlLetterEscapeSequence(ControlLetterEscapeSequence object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Hex Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Hex Escape Sequence</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseHexEscapeSequence(HexEscapeSequence object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Unicode Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Unicode Escape Sequence</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUnicodeEscapeSequence(UnicodeEscapeSequence object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Identity Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Identity Escape Sequence</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIdentityEscapeSequence(IdentityEscapeSequence object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Decimal Escape Sequence</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Decimal Escape Sequence</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDecimalEscapeSequence(DecimalEscapeSequence object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Character Class</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Character Class</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCharacterClass(CharacterClass object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Character Class Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Character Class Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCharacterClassElement(CharacterClassElement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Character Class Atom</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Character Class Atom</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCharacterClassAtom(CharacterClassAtom object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Escaped Character Class Atom</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Escaped Character Class Atom</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEscapedCharacterClassAtom(EscapedCharacterClassAtom object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Backspace</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Backspace</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBackspace(Backspace object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Group</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Group</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGroup(Group object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Quantifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Quantifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseQuantifier(Quantifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Simple Quantifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Simple Quantifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSimpleQuantifier(SimpleQuantifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Exact Quantifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Exact Quantifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExactQuantifier(ExactQuantifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Flags</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Flags</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRegularExpressionFlags(RegularExpressionFlags object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Disjunction</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Disjunction</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDisjunction(Disjunction object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Sequence</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Sequence</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSequence(Sequence object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Look Ahead</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Look Ahead</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLookAhead(LookAhead object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Look Behind</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Look Behind</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLookBehind(LookBehind object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Character Class Range</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Character Class Range</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCharacterClassRange(CharacterClassRange object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //RegularExpressionSwitch
