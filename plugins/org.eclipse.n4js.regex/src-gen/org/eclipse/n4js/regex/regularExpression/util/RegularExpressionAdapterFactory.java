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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.regex.regularExpression.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage
 * @generated
 */
public class RegularExpressionAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static RegularExpressionPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegularExpressionAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = RegularExpressionPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RegularExpressionSwitch<Adapter> modelSwitch =
    new RegularExpressionSwitch<Adapter>()
    {
      @Override
      public Adapter caseRegularExpressionLiteral(RegularExpressionLiteral object)
      {
        return createRegularExpressionLiteralAdapter();
      }
      @Override
      public Adapter caseRegularExpressionBody(RegularExpressionBody object)
      {
        return createRegularExpressionBodyAdapter();
      }
      @Override
      public Adapter casePattern(Pattern object)
      {
        return createPatternAdapter();
      }
      @Override
      public Adapter caseAssertion(Assertion object)
      {
        return createAssertionAdapter();
      }
      @Override
      public Adapter caseLineStart(LineStart object)
      {
        return createLineStartAdapter();
      }
      @Override
      public Adapter caseLineEnd(LineEnd object)
      {
        return createLineEndAdapter();
      }
      @Override
      public Adapter caseWordBoundary(WordBoundary object)
      {
        return createWordBoundaryAdapter();
      }
      @Override
      public Adapter caseAbstractLookAhead(AbstractLookAhead object)
      {
        return createAbstractLookAheadAdapter();
      }
      @Override
      public Adapter casePatternCharacter(PatternCharacter object)
      {
        return createPatternCharacterAdapter();
      }
      @Override
      public Adapter caseWildcard(Wildcard object)
      {
        return createWildcardAdapter();
      }
      @Override
      public Adapter caseAtomEscape(AtomEscape object)
      {
        return createAtomEscapeAdapter();
      }
      @Override
      public Adapter caseCharacterClassEscapeSequence(CharacterClassEscapeSequence object)
      {
        return createCharacterClassEscapeSequenceAdapter();
      }
      @Override
      public Adapter caseCharacterEscapeSequence(CharacterEscapeSequence object)
      {
        return createCharacterEscapeSequenceAdapter();
      }
      @Override
      public Adapter caseControlLetterEscapeSequence(ControlLetterEscapeSequence object)
      {
        return createControlLetterEscapeSequenceAdapter();
      }
      @Override
      public Adapter caseHexEscapeSequence(HexEscapeSequence object)
      {
        return createHexEscapeSequenceAdapter();
      }
      @Override
      public Adapter caseUnicodeEscapeSequence(UnicodeEscapeSequence object)
      {
        return createUnicodeEscapeSequenceAdapter();
      }
      @Override
      public Adapter caseIdentityEscapeSequence(IdentityEscapeSequence object)
      {
        return createIdentityEscapeSequenceAdapter();
      }
      @Override
      public Adapter caseDecimalEscapeSequence(DecimalEscapeSequence object)
      {
        return createDecimalEscapeSequenceAdapter();
      }
      @Override
      public Adapter caseCharacterClass(CharacterClass object)
      {
        return createCharacterClassAdapter();
      }
      @Override
      public Adapter caseCharacterClassElement(CharacterClassElement object)
      {
        return createCharacterClassElementAdapter();
      }
      @Override
      public Adapter caseCharacterClassAtom(CharacterClassAtom object)
      {
        return createCharacterClassAtomAdapter();
      }
      @Override
      public Adapter caseEscapedCharacterClassAtom(EscapedCharacterClassAtom object)
      {
        return createEscapedCharacterClassAtomAdapter();
      }
      @Override
      public Adapter caseBackspace(Backspace object)
      {
        return createBackspaceAdapter();
      }
      @Override
      public Adapter caseGroup(Group object)
      {
        return createGroupAdapter();
      }
      @Override
      public Adapter caseQuantifier(Quantifier object)
      {
        return createQuantifierAdapter();
      }
      @Override
      public Adapter caseSimpleQuantifier(SimpleQuantifier object)
      {
        return createSimpleQuantifierAdapter();
      }
      @Override
      public Adapter caseExactQuantifier(ExactQuantifier object)
      {
        return createExactQuantifierAdapter();
      }
      @Override
      public Adapter caseRegularExpressionFlags(RegularExpressionFlags object)
      {
        return createRegularExpressionFlagsAdapter();
      }
      @Override
      public Adapter caseDisjunction(Disjunction object)
      {
        return createDisjunctionAdapter();
      }
      @Override
      public Adapter caseSequence(Sequence object)
      {
        return createSequenceAdapter();
      }
      @Override
      public Adapter caseLookAhead(LookAhead object)
      {
        return createLookAheadAdapter();
      }
      @Override
      public Adapter caseLookBehind(LookBehind object)
      {
        return createLookBehindAdapter();
      }
      @Override
      public Adapter caseCharacterClassRange(CharacterClassRange object)
      {
        return createCharacterClassRangeAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral <em>Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral
   * @generated
   */
  public Adapter createRegularExpressionLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionBody <em>Body</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionBody
   * @generated
   */
  public Adapter createRegularExpressionBodyAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.Pattern <em>Pattern</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.Pattern
   * @generated
   */
  public Adapter createPatternAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.Assertion <em>Assertion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.Assertion
   * @generated
   */
  public Adapter createAssertionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.LineStart <em>Line Start</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.LineStart
   * @generated
   */
  public Adapter createLineStartAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.LineEnd <em>Line End</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.LineEnd
   * @generated
   */
  public Adapter createLineEndAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.WordBoundary <em>Word Boundary</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.WordBoundary
   * @generated
   */
  public Adapter createWordBoundaryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.AbstractLookAhead <em>Abstract Look Ahead</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.AbstractLookAhead
   * @generated
   */
  public Adapter createAbstractLookAheadAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.PatternCharacter <em>Pattern Character</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.PatternCharacter
   * @generated
   */
  public Adapter createPatternCharacterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.Wildcard <em>Wildcard</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.Wildcard
   * @generated
   */
  public Adapter createWildcardAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.AtomEscape <em>Atom Escape</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.AtomEscape
   * @generated
   */
  public Adapter createAtomEscapeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence <em>Character Class Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence
   * @generated
   */
  public Adapter createCharacterClassEscapeSequenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence <em>Character Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence
   * @generated
   */
  public Adapter createCharacterEscapeSequenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence <em>Control Letter Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence
   * @generated
   */
  public Adapter createControlLetterEscapeSequenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.HexEscapeSequence <em>Hex Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.HexEscapeSequence
   * @generated
   */
  public Adapter createHexEscapeSequenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.UnicodeEscapeSequence <em>Unicode Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.UnicodeEscapeSequence
   * @generated
   */
  public Adapter createUnicodeEscapeSequenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.IdentityEscapeSequence <em>Identity Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.IdentityEscapeSequence
   * @generated
   */
  public Adapter createIdentityEscapeSequenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence <em>Decimal Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence
   * @generated
   */
  public Adapter createDecimalEscapeSequenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClass <em>Character Class</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClass
   * @generated
   */
  public Adapter createCharacterClassAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassElement <em>Character Class Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassElement
   * @generated
   */
  public Adapter createCharacterClassElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassAtom <em>Character Class Atom</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassAtom
   * @generated
   */
  public Adapter createCharacterClassAtomAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.EscapedCharacterClassAtom <em>Escaped Character Class Atom</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.EscapedCharacterClassAtom
   * @generated
   */
  public Adapter createEscapedCharacterClassAtomAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.Backspace <em>Backspace</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.Backspace
   * @generated
   */
  public Adapter createBackspaceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.Group <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.Group
   * @generated
   */
  public Adapter createGroupAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.Quantifier <em>Quantifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.Quantifier
   * @generated
   */
  public Adapter createQuantifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.SimpleQuantifier <em>Simple Quantifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.SimpleQuantifier
   * @generated
   */
  public Adapter createSimpleQuantifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.ExactQuantifier <em>Exact Quantifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.ExactQuantifier
   * @generated
   */
  public Adapter createExactQuantifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags <em>Flags</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags
   * @generated
   */
  public Adapter createRegularExpressionFlagsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.Disjunction <em>Disjunction</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.Disjunction
   * @generated
   */
  public Adapter createDisjunctionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.Sequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.Sequence
   * @generated
   */
  public Adapter createSequenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.LookAhead <em>Look Ahead</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.LookAhead
   * @generated
   */
  public Adapter createLookAheadAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.LookBehind <em>Look Behind</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.LookBehind
   * @generated
   */
  public Adapter createLookBehindAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.n4js.regex.regularExpression.CharacterClassRange <em>Character Class Range</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.n4js.regex.regularExpression.CharacterClassRange
   * @generated
   */
  public Adapter createCharacterClassRangeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //RegularExpressionAdapterFactory
