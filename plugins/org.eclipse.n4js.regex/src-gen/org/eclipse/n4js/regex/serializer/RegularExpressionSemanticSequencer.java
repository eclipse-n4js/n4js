/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.regex.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.regex.regularExpression.Backspace;
import org.eclipse.n4js.regex.regularExpression.CharacterClass;
import org.eclipse.n4js.regex.regularExpression.CharacterClassAtom;
import org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.CharacterClassRange;
import org.eclipse.n4js.regex.regularExpression.CharacterEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.Disjunction;
import org.eclipse.n4js.regex.regularExpression.ExactQuantifier;
import org.eclipse.n4js.regex.regularExpression.Group;
import org.eclipse.n4js.regex.regularExpression.HexEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.IdentityEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.LineEnd;
import org.eclipse.n4js.regex.regularExpression.LineStart;
import org.eclipse.n4js.regex.regularExpression.LookAhead;
import org.eclipse.n4js.regex.regularExpression.LookBehind;
import org.eclipse.n4js.regex.regularExpression.PatternCharacter;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionBody;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage;
import org.eclipse.n4js.regex.regularExpression.Sequence;
import org.eclipse.n4js.regex.regularExpression.SimpleQuantifier;
import org.eclipse.n4js.regex.regularExpression.UnicodeEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.Wildcard;
import org.eclipse.n4js.regex.regularExpression.WordBoundary;
import org.eclipse.n4js.regex.services.RegularExpressionGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class RegularExpressionSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private RegularExpressionGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == RegularExpressionPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case RegularExpressionPackage.BACKSPACE:
				sequence_Backspace(context, (Backspace) semanticObject); 
				return; 
			case RegularExpressionPackage.CHARACTER_CLASS:
				if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getCharacterClassRule()) {
					sequence_CharacterClass(context, (CharacterClass) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_CharacterClass_Term(context, (CharacterClass) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.CHARACTER_CLASS_ATOM:
				sequence_CharacterClassAtom(context, (CharacterClassAtom) semanticObject); 
				return; 
			case RegularExpressionPackage.CHARACTER_CLASS_ESCAPE_SEQUENCE:
				if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getAtomEscapeRule()
						|| rule == grammarAccess.getCharacterClassEscapeSequenceRule()
						|| rule == grammarAccess.getCharacterClassElementRule()
						|| action == grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()
						|| rule == grammarAccess.getCharacterClassAtomRule()
						|| rule == grammarAccess.getEscapedCharacterClassAtomRule()) {
					sequence_CharacterClassEscapeSequence(context, (CharacterClassEscapeSequence) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_CharacterClassEscapeSequence_Term(context, (CharacterClassEscapeSequence) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.CHARACTER_CLASS_RANGE:
				sequence_CharacterClassElement(context, (CharacterClassRange) semanticObject); 
				return; 
			case RegularExpressionPackage.CHARACTER_ESCAPE_SEQUENCE:
				if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getAtomEscapeRule()
						|| rule == grammarAccess.getCharacterEscapeSequenceRule()
						|| rule == grammarAccess.getCharacterClassElementRule()
						|| action == grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()
						|| rule == grammarAccess.getCharacterClassAtomRule()
						|| rule == grammarAccess.getEscapedCharacterClassAtomRule()) {
					sequence_CharacterEscapeSequence(context, (CharacterEscapeSequence) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_CharacterEscapeSequence_Term(context, (CharacterEscapeSequence) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.CONTROL_LETTER_ESCAPE_SEQUENCE:
				if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getAtomEscapeRule()
						|| rule == grammarAccess.getControlLetterEscapeSequenceRule()
						|| rule == grammarAccess.getCharacterClassElementRule()
						|| action == grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()
						|| rule == grammarAccess.getCharacterClassAtomRule()
						|| rule == grammarAccess.getEscapedCharacterClassAtomRule()) {
					sequence_ControlLetterEscapeSequence(context, (ControlLetterEscapeSequence) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_ControlLetterEscapeSequence_Term(context, (ControlLetterEscapeSequence) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.DECIMAL_ESCAPE_SEQUENCE:
				if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getAtomEscapeRule()
						|| rule == grammarAccess.getDecimalEscapeSequenceRule()
						|| rule == grammarAccess.getCharacterClassElementRule()
						|| action == grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()
						|| rule == grammarAccess.getCharacterClassAtomRule()
						|| rule == grammarAccess.getEscapedCharacterClassAtomRule()) {
					sequence_DecimalEscapeSequence(context, (DecimalEscapeSequence) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_DecimalEscapeSequence_Term(context, (DecimalEscapeSequence) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.DISJUNCTION:
				sequence_Disjunction(context, (Disjunction) semanticObject); 
				return; 
			case RegularExpressionPackage.EXACT_QUANTIFIER:
				sequence_ExactQuantifier(context, (ExactQuantifier) semanticObject); 
				return; 
			case RegularExpressionPackage.GROUP:
				if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getGroupRule()) {
					sequence_Group(context, (Group) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_Group_Term(context, (Group) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.HEX_ESCAPE_SEQUENCE:
				if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getAtomEscapeRule()
						|| rule == grammarAccess.getHexEscapeSequenceRule()
						|| rule == grammarAccess.getCharacterClassElementRule()
						|| action == grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()
						|| rule == grammarAccess.getCharacterClassAtomRule()
						|| rule == grammarAccess.getEscapedCharacterClassAtomRule()) {
					sequence_HexEscapeSequence(context, (HexEscapeSequence) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_HexEscapeSequence_Term(context, (HexEscapeSequence) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.IDENTITY_ESCAPE_SEQUENCE:
				if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getAtomEscapeRule()
						|| rule == grammarAccess.getIdentityEscapeSequenceRule()
						|| rule == grammarAccess.getCharacterClassElementRule()
						|| action == grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()
						|| rule == grammarAccess.getCharacterClassAtomRule()
						|| rule == grammarAccess.getEscapedCharacterClassAtomRule()) {
					sequence_IdentityEscapeSequence(context, (IdentityEscapeSequence) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_IdentityEscapeSequence_Term(context, (IdentityEscapeSequence) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.LINE_END:
				sequence_LineEnd(context, (LineEnd) semanticObject); 
				return; 
			case RegularExpressionPackage.LINE_START:
				sequence_LineStart(context, (LineStart) semanticObject); 
				return; 
			case RegularExpressionPackage.LOOK_AHEAD:
				sequence_AbstractLookAhead(context, (LookAhead) semanticObject); 
				return; 
			case RegularExpressionPackage.LOOK_BEHIND:
				sequence_AbstractLookAhead(context, (LookBehind) semanticObject); 
				return; 
			case RegularExpressionPackage.PATTERN_CHARACTER:
				if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getPatternCharacterRule()) {
					sequence_PatternCharacter(context, (PatternCharacter) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_PatternCharacter_Term(context, (PatternCharacter) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.REGULAR_EXPRESSION_BODY:
				sequence_RegularExpressionBody(context, (RegularExpressionBody) semanticObject); 
				return; 
			case RegularExpressionPackage.REGULAR_EXPRESSION_FLAGS:
				sequence_RegularExpressionFlags(context, (RegularExpressionFlags) semanticObject); 
				return; 
			case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL:
				sequence_RegularExpressionLiteral(context, (RegularExpressionLiteral) semanticObject); 
				return; 
			case RegularExpressionPackage.SEQUENCE:
				sequence_Alternative(context, (Sequence) semanticObject); 
				return; 
			case RegularExpressionPackage.SIMPLE_QUANTIFIER:
				sequence_SimpleQuantifier(context, (SimpleQuantifier) semanticObject); 
				return; 
			case RegularExpressionPackage.UNICODE_ESCAPE_SEQUENCE:
				if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_Term_UnicodeEscapeSequence(context, (UnicodeEscapeSequence) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getAtomEscapeRule()
						|| rule == grammarAccess.getUnicodeEscapeSequenceRule()
						|| rule == grammarAccess.getCharacterClassElementRule()
						|| action == grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()
						|| rule == grammarAccess.getCharacterClassAtomRule()
						|| rule == grammarAccess.getEscapedCharacterClassAtomRule()) {
					sequence_UnicodeEscapeSequence(context, (UnicodeEscapeSequence) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.WILDCARD:
				if (rule == grammarAccess.getDisjunctionRule()
						|| action == grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()
						|| rule == grammarAccess.getAlternativeRule()
						|| action == grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()
						|| rule == grammarAccess.getTermRule()) {
					sequence_Term_Wildcard(context, (Wildcard) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAtomRule()
						|| rule == grammarAccess.getWildcardRule()) {
					sequence_Wildcard(context, (Wildcard) semanticObject); 
					return; 
				}
				else break;
			case RegularExpressionPackage.WORD_BOUNDARY:
				sequence_WordBoundary(context, (WordBoundary) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     Disjunction returns LookAhead
	 *     Disjunction.Disjunction_0_1_0 returns LookAhead
	 *     Alternative returns LookAhead
	 *     Alternative.Sequence_1_0 returns LookAhead
	 *     Term returns LookAhead
	 *     Assertion returns LookAhead
	 *     AbstractLookAhead returns LookAhead
	 *
	 * Constraint:
	 *     (not?='(?!'? pattern=Disjunction)
	 */
	protected void sequence_AbstractLookAhead(ISerializationContext context, LookAhead semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns LookBehind
	 *     Disjunction.Disjunction_0_1_0 returns LookBehind
	 *     Alternative returns LookBehind
	 *     Alternative.Sequence_1_0 returns LookBehind
	 *     Term returns LookBehind
	 *     Assertion returns LookBehind
	 *     AbstractLookAhead returns LookBehind
	 *
	 * Constraint:
	 *     (not?='(?<!'? pattern=Disjunction)
	 */
	protected void sequence_AbstractLookAhead(ISerializationContext context, LookBehind semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns Sequence
	 *     Disjunction.Disjunction_0_1_0 returns Sequence
	 *     Alternative returns Sequence
	 *
	 * Constraint:
	 *     (elements+=Alternative_Sequence_1_0 elements+=Term+)
	 */
	protected void sequence_Alternative(ISerializationContext context, Sequence semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     CharacterClassElement returns Backspace
	 *     CharacterClassElement.CharacterClassRange_1_0_0 returns Backspace
	 *     CharacterClassAtom returns Backspace
	 *     EscapedCharacterClassAtom returns Backspace
	 *     Backspace returns Backspace
	 *
	 * Constraint:
	 *     {Backspace}
	 */
	protected void sequence_Backspace(ISerializationContext context, Backspace semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     CharacterClassElement returns CharacterClassAtom
	 *     CharacterClassElement.CharacterClassRange_1_0_0 returns CharacterClassAtom
	 *     CharacterClassAtom returns CharacterClassAtom
	 *
	 * Constraint:
	 *     (
	 *         characters=',' | 
	 *         characters='=' | 
	 *         characters=':' | 
	 *         characters='!' | 
	 *         characters='_' | 
	 *         characters='-' | 
	 *         characters='^' | 
	 *         characters='$' | 
	 *         characters='.' | 
	 *         characters='*' | 
	 *         characters='+' | 
	 *         characters='?' | 
	 *         characters='(' | 
	 *         characters=')' | 
	 *         characters='[' | 
	 *         characters='{' | 
	 *         characters='}' | 
	 *         characters='|' | 
	 *         characters='/' | 
	 *         characters='<' | 
	 *         characters='>' | 
	 *         characters='(?' | 
	 *         characters='(?<' | 
	 *         characters='(?=' | 
	 *         characters='(?!' | 
	 *         characters='(?<!' | 
	 *         characters='(?<=' | 
	 *         characters=PATTERN_CHARACTER_NO_DASH | 
	 *         characters=UNICODE_LETTER | 
	 *         characters=UNICODE_DIGIT
	 *     )
	 */
	protected void sequence_CharacterClassAtom(ISerializationContext context, CharacterClassAtom semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     CharacterClassElement returns CharacterClassRange
	 *
	 * Constraint:
	 *     (left=CharacterClassElement_CharacterClassRange_1_0_0 right=CharacterClassAtom)
	 */
	protected void sequence_CharacterClassElement(ISerializationContext context, CharacterClassRange semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.CHARACTER_CLASS_RANGE__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.CHARACTER_CLASS_RANGE__LEFT));
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.CHARACTER_CLASS_RANGE__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.CHARACTER_CLASS_RANGE__RIGHT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getCharacterClassElementAccess().getRightCharacterClassAtomParserRuleCall_1_0_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns CharacterClassEscapeSequence
	 *     AtomEscape returns CharacterClassEscapeSequence
	 *     CharacterClassEscapeSequence returns CharacterClassEscapeSequence
	 *     CharacterClassElement returns CharacterClassEscapeSequence
	 *     CharacterClassElement.CharacterClassRange_1_0_0 returns CharacterClassEscapeSequence
	 *     CharacterClassAtom returns CharacterClassEscapeSequence
	 *     EscapedCharacterClassAtom returns CharacterClassEscapeSequence
	 *
	 * Constraint:
	 *     sequence=CHARACTER_CLASS_ESCAPE
	 */
	protected void sequence_CharacterClassEscapeSequence(ISerializationContext context, CharacterClassEscapeSequence semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.CHARACTER_CLASS_ESCAPE_SEQUENCE__SEQUENCE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0(), semanticObject.getSequence());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns CharacterClassEscapeSequence
	 *     Disjunction.Disjunction_0_1_0 returns CharacterClassEscapeSequence
	 *     Alternative returns CharacterClassEscapeSequence
	 *     Alternative.Sequence_1_0 returns CharacterClassEscapeSequence
	 *     Term returns CharacterClassEscapeSequence
	 *
	 * Constraint:
	 *     (sequence=CHARACTER_CLASS_ESCAPE quantifier=Quantifier?)
	 */
	protected void sequence_CharacterClassEscapeSequence_Term(ISerializationContext context, CharacterClassEscapeSequence semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns CharacterClass
	 *     CharacterClass returns CharacterClass
	 *
	 * Constraint:
	 *     (negated?='^'? elements+=CharacterClassElement*)
	 */
	protected void sequence_CharacterClass(ISerializationContext context, CharacterClass semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns CharacterClass
	 *     Disjunction.Disjunction_0_1_0 returns CharacterClass
	 *     Alternative returns CharacterClass
	 *     Alternative.Sequence_1_0 returns CharacterClass
	 *     Term returns CharacterClass
	 *
	 * Constraint:
	 *     (negated?='^'? elements+=CharacterClassElement* quantifier=Quantifier?)
	 */
	protected void sequence_CharacterClass_Term(ISerializationContext context, CharacterClass semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns CharacterEscapeSequence
	 *     AtomEscape returns CharacterEscapeSequence
	 *     CharacterEscapeSequence returns CharacterEscapeSequence
	 *     CharacterClassElement returns CharacterEscapeSequence
	 *     CharacterClassElement.CharacterClassRange_1_0_0 returns CharacterEscapeSequence
	 *     CharacterClassAtom returns CharacterEscapeSequence
	 *     EscapedCharacterClassAtom returns CharacterEscapeSequence
	 *
	 * Constraint:
	 *     sequence=CONTROL_ESCAPE
	 */
	protected void sequence_CharacterEscapeSequence(ISerializationContext context, CharacterEscapeSequence semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.CHARACTER_ESCAPE_SEQUENCE__SEQUENCE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.CHARACTER_ESCAPE_SEQUENCE__SEQUENCE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceCONTROL_ESCAPETerminalRuleCall_0(), semanticObject.getSequence());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns CharacterEscapeSequence
	 *     Disjunction.Disjunction_0_1_0 returns CharacterEscapeSequence
	 *     Alternative returns CharacterEscapeSequence
	 *     Alternative.Sequence_1_0 returns CharacterEscapeSequence
	 *     Term returns CharacterEscapeSequence
	 *
	 * Constraint:
	 *     (sequence=CONTROL_ESCAPE quantifier=Quantifier?)
	 */
	protected void sequence_CharacterEscapeSequence_Term(ISerializationContext context, CharacterEscapeSequence semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns ControlLetterEscapeSequence
	 *     AtomEscape returns ControlLetterEscapeSequence
	 *     ControlLetterEscapeSequence returns ControlLetterEscapeSequence
	 *     CharacterClassElement returns ControlLetterEscapeSequence
	 *     CharacterClassElement.CharacterClassRange_1_0_0 returns ControlLetterEscapeSequence
	 *     CharacterClassAtom returns ControlLetterEscapeSequence
	 *     EscapedCharacterClassAtom returns ControlLetterEscapeSequence
	 *
	 * Constraint:
	 *     sequence=CONTROL_LETTER_ESCAPE
	 */
	protected void sequence_ControlLetterEscapeSequence(ISerializationContext context, ControlLetterEscapeSequence semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.CONTROL_LETTER_ESCAPE_SEQUENCE__SEQUENCE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.CONTROL_LETTER_ESCAPE_SEQUENCE__SEQUENCE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0(), semanticObject.getSequence());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns ControlLetterEscapeSequence
	 *     Disjunction.Disjunction_0_1_0 returns ControlLetterEscapeSequence
	 *     Alternative returns ControlLetterEscapeSequence
	 *     Alternative.Sequence_1_0 returns ControlLetterEscapeSequence
	 *     Term returns ControlLetterEscapeSequence
	 *
	 * Constraint:
	 *     (sequence=CONTROL_LETTER_ESCAPE quantifier=Quantifier?)
	 */
	protected void sequence_ControlLetterEscapeSequence_Term(ISerializationContext context, ControlLetterEscapeSequence semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns DecimalEscapeSequence
	 *     AtomEscape returns DecimalEscapeSequence
	 *     DecimalEscapeSequence returns DecimalEscapeSequence
	 *     CharacterClassElement returns DecimalEscapeSequence
	 *     CharacterClassElement.CharacterClassRange_1_0_0 returns DecimalEscapeSequence
	 *     CharacterClassAtom returns DecimalEscapeSequence
	 *     EscapedCharacterClassAtom returns DecimalEscapeSequence
	 *
	 * Constraint:
	 *     sequence=DECIMAL_ESCAPE
	 */
	protected void sequence_DecimalEscapeSequence(ISerializationContext context, DecimalEscapeSequence semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.DECIMAL_ESCAPE_SEQUENCE__SEQUENCE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.DECIMAL_ESCAPE_SEQUENCE__SEQUENCE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceDECIMAL_ESCAPETerminalRuleCall_0(), semanticObject.getSequence());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns DecimalEscapeSequence
	 *     Disjunction.Disjunction_0_1_0 returns DecimalEscapeSequence
	 *     Alternative returns DecimalEscapeSequence
	 *     Alternative.Sequence_1_0 returns DecimalEscapeSequence
	 *     Term returns DecimalEscapeSequence
	 *
	 * Constraint:
	 *     (sequence=DECIMAL_ESCAPE quantifier=Quantifier?)
	 */
	protected void sequence_DecimalEscapeSequence_Term(ISerializationContext context, DecimalEscapeSequence semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns Disjunction
	 *
	 * Constraint:
	 *     ((elements+=Disjunction_Disjunction_0_1_0 elements+=Alternative*) | elements+=Alternative+)?
	 */
	protected void sequence_Disjunction(ISerializationContext context, Disjunction semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Quantifier returns ExactQuantifier
	 *     ExactQuantifier returns ExactQuantifier
	 *
	 * Constraint:
	 *     (min=INT (max=INT | unboundedMax?=',')? nonGreedy?='?'?)
	 */
	protected void sequence_ExactQuantifier(ISerializationContext context, ExactQuantifier semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns Group
	 *     Group returns Group
	 *
	 * Constraint:
	 *     ((nonCapturing?='(?:' | (named?='(?<' name=RegExpIdentifierName))? pattern=Disjunction)
	 */
	protected void sequence_Group(ISerializationContext context, Group semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns Group
	 *     Disjunction.Disjunction_0_1_0 returns Group
	 *     Alternative returns Group
	 *     Alternative.Sequence_1_0 returns Group
	 *     Term returns Group
	 *
	 * Constraint:
	 *     ((nonCapturing?='(?:' | (named?='(?<' name=RegExpIdentifierName))? pattern=Disjunction quantifier=Quantifier?)
	 */
	protected void sequence_Group_Term(ISerializationContext context, Group semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns HexEscapeSequence
	 *     AtomEscape returns HexEscapeSequence
	 *     HexEscapeSequence returns HexEscapeSequence
	 *     CharacterClassElement returns HexEscapeSequence
	 *     CharacterClassElement.CharacterClassRange_1_0_0 returns HexEscapeSequence
	 *     CharacterClassAtom returns HexEscapeSequence
	 *     EscapedCharacterClassAtom returns HexEscapeSequence
	 *
	 * Constraint:
	 *     sequence=HEX_ESCAPE
	 */
	protected void sequence_HexEscapeSequence(ISerializationContext context, HexEscapeSequence semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.HEX_ESCAPE_SEQUENCE__SEQUENCE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.HEX_ESCAPE_SEQUENCE__SEQUENCE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getHexEscapeSequenceAccess().getSequenceHEX_ESCAPETerminalRuleCall_0(), semanticObject.getSequence());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns HexEscapeSequence
	 *     Disjunction.Disjunction_0_1_0 returns HexEscapeSequence
	 *     Alternative returns HexEscapeSequence
	 *     Alternative.Sequence_1_0 returns HexEscapeSequence
	 *     Term returns HexEscapeSequence
	 *
	 * Constraint:
	 *     (sequence=HEX_ESCAPE quantifier=Quantifier?)
	 */
	protected void sequence_HexEscapeSequence_Term(ISerializationContext context, HexEscapeSequence semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns IdentityEscapeSequence
	 *     AtomEscape returns IdentityEscapeSequence
	 *     IdentityEscapeSequence returns IdentityEscapeSequence
	 *     CharacterClassElement returns IdentityEscapeSequence
	 *     CharacterClassElement.CharacterClassRange_1_0_0 returns IdentityEscapeSequence
	 *     CharacterClassAtom returns IdentityEscapeSequence
	 *     EscapedCharacterClassAtom returns IdentityEscapeSequence
	 *
	 * Constraint:
	 *     sequence=IDENTITY_ESCAPE
	 */
	protected void sequence_IdentityEscapeSequence(ISerializationContext context, IdentityEscapeSequence semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.IDENTITY_ESCAPE_SEQUENCE__SEQUENCE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.IDENTITY_ESCAPE_SEQUENCE__SEQUENCE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceIDENTITY_ESCAPETerminalRuleCall_0(), semanticObject.getSequence());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns IdentityEscapeSequence
	 *     Disjunction.Disjunction_0_1_0 returns IdentityEscapeSequence
	 *     Alternative returns IdentityEscapeSequence
	 *     Alternative.Sequence_1_0 returns IdentityEscapeSequence
	 *     Term returns IdentityEscapeSequence
	 *
	 * Constraint:
	 *     (sequence=IDENTITY_ESCAPE quantifier=Quantifier?)
	 */
	protected void sequence_IdentityEscapeSequence_Term(ISerializationContext context, IdentityEscapeSequence semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns LineEnd
	 *     Disjunction.Disjunction_0_1_0 returns LineEnd
	 *     Alternative returns LineEnd
	 *     Alternative.Sequence_1_0 returns LineEnd
	 *     Term returns LineEnd
	 *     Assertion returns LineEnd
	 *     LineEnd returns LineEnd
	 *
	 * Constraint:
	 *     {LineEnd}
	 */
	protected void sequence_LineEnd(ISerializationContext context, LineEnd semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns LineStart
	 *     Disjunction.Disjunction_0_1_0 returns LineStart
	 *     Alternative returns LineStart
	 *     Alternative.Sequence_1_0 returns LineStart
	 *     Term returns LineStart
	 *     Assertion returns LineStart
	 *     LineStart returns LineStart
	 *
	 * Constraint:
	 *     {LineStart}
	 */
	protected void sequence_LineStart(ISerializationContext context, LineStart semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns PatternCharacter
	 *     PatternCharacter returns PatternCharacter
	 *
	 * Constraint:
	 *     (
	 *         value=PATTERN_CHARACTER_NO_DASH | 
	 *         value=UNICODE_LETTER | 
	 *         value=UNICODE_DIGIT | 
	 *         value='-' | 
	 *         value='_' | 
	 *         value=',' | 
	 *         value='=' | 
	 *         value=':' | 
	 *         value='!' | 
	 *         value='{' | 
	 *         value='}' | 
	 *         value=']' | 
	 *         value='<' | 
	 *         value='>'
	 *     )
	 */
	protected void sequence_PatternCharacter(ISerializationContext context, PatternCharacter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns PatternCharacter
	 *     Disjunction.Disjunction_0_1_0 returns PatternCharacter
	 *     Alternative returns PatternCharacter
	 *     Alternative.Sequence_1_0 returns PatternCharacter
	 *     Term returns PatternCharacter
	 *
	 * Constraint:
	 *     (
	 *         (
	 *             value=PATTERN_CHARACTER_NO_DASH | 
	 *             value=UNICODE_LETTER | 
	 *             value=UNICODE_DIGIT | 
	 *             value='-' | 
	 *             value='_' | 
	 *             value=',' | 
	 *             value='=' | 
	 *             value=':' | 
	 *             value='!' | 
	 *             value='{' | 
	 *             value='}' | 
	 *             value=']' | 
	 *             value='<' | 
	 *             value='>'
	 *         ) 
	 *         quantifier=Quantifier?
	 *     )
	 */
	protected void sequence_PatternCharacter_Term(ISerializationContext context, PatternCharacter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     RegularExpressionBody returns RegularExpressionBody
	 *
	 * Constraint:
	 *     pattern=Disjunction
	 */
	protected void sequence_RegularExpressionBody(ISerializationContext context, RegularExpressionBody semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.REGULAR_EXPRESSION_BODY__PATTERN) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.REGULAR_EXPRESSION_BODY__PATTERN));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getRegularExpressionBodyAccess().getPatternDisjunctionParserRuleCall_0(), semanticObject.getPattern());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     RegularExpressionFlags returns RegularExpressionFlags
	 *
	 * Constraint:
	 *     (flags+=UNICODE_LETTER | flags+=UNICODE_ESCAPE)*
	 */
	protected void sequence_RegularExpressionFlags(ISerializationContext context, RegularExpressionFlags semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     RegularExpressionLiteral returns RegularExpressionLiteral
	 *
	 * Constraint:
	 *     (body=RegularExpressionBody flags=RegularExpressionFlags)
	 */
	protected void sequence_RegularExpressionLiteral(ISerializationContext context, RegularExpressionLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.REGULAR_EXPRESSION_LITERAL__BODY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.REGULAR_EXPRESSION_LITERAL__BODY));
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.REGULAR_EXPRESSION_LITERAL__FLAGS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.REGULAR_EXPRESSION_LITERAL__FLAGS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getRegularExpressionLiteralAccess().getBodyRegularExpressionBodyParserRuleCall_1_0(), semanticObject.getBody());
		feeder.accept(grammarAccess.getRegularExpressionLiteralAccess().getFlagsRegularExpressionFlagsParserRuleCall_3_0(), semanticObject.getFlags());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Quantifier returns SimpleQuantifier
	 *     SimpleQuantifier returns SimpleQuantifier
	 *
	 * Constraint:
	 *     ((quantifier='+' | quantifier='*' | quantifier='?') nonGreedy?='?'?)
	 */
	protected void sequence_SimpleQuantifier(ISerializationContext context, SimpleQuantifier semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns UnicodeEscapeSequence
	 *     Disjunction.Disjunction_0_1_0 returns UnicodeEscapeSequence
	 *     Alternative returns UnicodeEscapeSequence
	 *     Alternative.Sequence_1_0 returns UnicodeEscapeSequence
	 *     Term returns UnicodeEscapeSequence
	 *
	 * Constraint:
	 *     (sequence=UNICODE_ESCAPE quantifier=Quantifier?)
	 */
	protected void sequence_Term_UnicodeEscapeSequence(ISerializationContext context, UnicodeEscapeSequence semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns Wildcard
	 *     Disjunction.Disjunction_0_1_0 returns Wildcard
	 *     Alternative returns Wildcard
	 *     Alternative.Sequence_1_0 returns Wildcard
	 *     Term returns Wildcard
	 *
	 * Constraint:
	 *     quantifier=Quantifier?
	 */
	protected void sequence_Term_Wildcard(ISerializationContext context, Wildcard semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns UnicodeEscapeSequence
	 *     AtomEscape returns UnicodeEscapeSequence
	 *     UnicodeEscapeSequence returns UnicodeEscapeSequence
	 *     CharacterClassElement returns UnicodeEscapeSequence
	 *     CharacterClassElement.CharacterClassRange_1_0_0 returns UnicodeEscapeSequence
	 *     CharacterClassAtom returns UnicodeEscapeSequence
	 *     EscapedCharacterClassAtom returns UnicodeEscapeSequence
	 *
	 * Constraint:
	 *     sequence=UNICODE_ESCAPE
	 */
	protected void sequence_UnicodeEscapeSequence(ISerializationContext context, UnicodeEscapeSequence semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegularExpressionPackage.Literals.UNICODE_ESCAPE_SEQUENCE__SEQUENCE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegularExpressionPackage.Literals.UNICODE_ESCAPE_SEQUENCE__SEQUENCE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceUNICODE_ESCAPETerminalRuleCall_0(), semanticObject.getSequence());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Atom returns Wildcard
	 *     Wildcard returns Wildcard
	 *
	 * Constraint:
	 *     {Wildcard}
	 */
	protected void sequence_Wildcard(ISerializationContext context, Wildcard semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Disjunction returns WordBoundary
	 *     Disjunction.Disjunction_0_1_0 returns WordBoundary
	 *     Alternative returns WordBoundary
	 *     Alternative.Sequence_1_0 returns WordBoundary
	 *     Term returns WordBoundary
	 *     Assertion returns WordBoundary
	 *     WordBoundary returns WordBoundary
	 *
	 * Constraint:
	 *     not?=NOT_WORD_BOUNDARY?
	 */
	protected void sequence_WordBoundary(ISerializationContext context, WordBoundary semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
