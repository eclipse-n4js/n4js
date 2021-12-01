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
package org.eclipse.n4js.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.n4js.common.unicode.services.UnicodeGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.service.AbstractElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class TypeExpressionsGrammarAccess extends AbstractElementFinder.AbstractGrammarElementFinder {
	
	public class TypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeRef");
		private final RuleCall cConditionalTypeRefParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//// ****************************************************************************************************
		//// N4JS versions of type references and expressions, also used by Types.xtext
		////
		//// References:
		////
		//// [ECM15]    ECMAScript 2015 Language Specification / ISO/IEC (ECMA-262, 6th Edition).
		////            International Standard.
		////            http://www.ecma-international.org/publications/ files/ECMA-ST/Ecma-262.pdf
		////
		//// ****************************************************************************************************
		///*
		// * cf. N4JSSec §4
		// * Depending on where the type references is used, not all possible variants are allowed.
		// * This is however checked by the validator in order to
		// * 1) provide better error messages
		// * 2) simplify grammar
		// *
		// * Constraints:
		// * UnionElementTypeRef: no AnyType, no Void, union itself must not be dynamic
		// * FParTypeRef: no Void
		// * ReturnTypeRef: everything, but no dynamic
		// * // in N4JS:
		// * VarTypeRef: no Void, i.e.
		// * AttributeTypeRef: no Void
		// */
		//TypeRef: ConditionalTypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//ConditionalTypeRef
		public RuleCall getConditionalTypeRefParserRuleCall() { return cConditionalTypeRefParserRuleCall; }
	}
	public class ConditionalTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ConditionalTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cUnionTypeExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cConditionalTypeRefTypeRefAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Keyword cExtendsKeyword_1_0_0_1 = (Keyword)cGroup_1_0_0.eContents().get(1);
		private final Assignment cUpperBoundAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cUpperBoundUnionTypeExpressionParserRuleCall_1_1_0 = (RuleCall)cUpperBoundAssignment_1_1.eContents().get(0);
		private final Keyword cQuestionMarkKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		private final Assignment cTrueTypeRefAssignment_1_3 = (Assignment)cGroup_1.eContents().get(3);
		private final RuleCall cTrueTypeRefConditionalTypeRefParserRuleCall_1_3_0 = (RuleCall)cTrueTypeRefAssignment_1_3.eContents().get(0);
		private final Keyword cColonKeyword_1_4 = (Keyword)cGroup_1.eContents().get(4);
		private final Assignment cFalseTypeRefAssignment_1_5 = (Assignment)cGroup_1.eContents().get(5);
		private final RuleCall cFalseTypeRefConditionalTypeRefParserRuleCall_1_5_0 = (RuleCall)cFalseTypeRefAssignment_1_5.eContents().get(0);
		
		//ConditionalTypeRef returns TypeRef:
		//    UnionTypeExpression (=> ({ConditionalTypeRef.typeRef=current} 'extends') upperBound=UnionTypeExpression '?' trueTypeRef=ConditionalTypeRef ':' falseTypeRef=ConditionalTypeRef)?;
		@Override public ParserRule getRule() { return rule; }
		
		//UnionTypeExpression (=> ({ConditionalTypeRef.typeRef=current} 'extends') upperBound=UnionTypeExpression '?' trueTypeRef=ConditionalTypeRef ':' falseTypeRef=ConditionalTypeRef)?
		public Group getGroup() { return cGroup; }
		
		//UnionTypeExpression
		public RuleCall getUnionTypeExpressionParserRuleCall_0() { return cUnionTypeExpressionParserRuleCall_0; }
		
		//(=> ({ConditionalTypeRef.typeRef=current} 'extends') upperBound=UnionTypeExpression '?' trueTypeRef=ConditionalTypeRef ':' falseTypeRef=ConditionalTypeRef)?
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({ConditionalTypeRef.typeRef=current} 'extends')
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{ConditionalTypeRef.typeRef=current} 'extends'
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{ConditionalTypeRef.typeRef=current}
		public Action getConditionalTypeRefTypeRefAction_1_0_0_0() { return cConditionalTypeRefTypeRefAction_1_0_0_0; }
		
		//'extends'
		public Keyword getExtendsKeyword_1_0_0_1() { return cExtendsKeyword_1_0_0_1; }
		
		//upperBound=UnionTypeExpression
		public Assignment getUpperBoundAssignment_1_1() { return cUpperBoundAssignment_1_1; }
		
		//UnionTypeExpression
		public RuleCall getUpperBoundUnionTypeExpressionParserRuleCall_1_1_0() { return cUpperBoundUnionTypeExpressionParserRuleCall_1_1_0; }
		
		//'?'
		public Keyword getQuestionMarkKeyword_1_2() { return cQuestionMarkKeyword_1_2; }
		
		//trueTypeRef=ConditionalTypeRef
		public Assignment getTrueTypeRefAssignment_1_3() { return cTrueTypeRefAssignment_1_3; }
		
		//ConditionalTypeRef
		public RuleCall getTrueTypeRefConditionalTypeRefParserRuleCall_1_3_0() { return cTrueTypeRefConditionalTypeRefParserRuleCall_1_3_0; }
		
		//':'
		public Keyword getColonKeyword_1_4() { return cColonKeyword_1_4; }
		
		//falseTypeRef=ConditionalTypeRef
		public Assignment getFalseTypeRefAssignment_1_5() { return cFalseTypeRefAssignment_1_5; }
		
		//ConditionalTypeRef
		public RuleCall getFalseTypeRefConditionalTypeRefParserRuleCall_1_5_0() { return cFalseTypeRefConditionalTypeRefParserRuleCall_1_5_0; }
	}
	public class UnionTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.UnionTypeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cIntersectionTypeExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cUnionTypeExpressionTypeRefsAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Keyword cVerticalLineKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cTypeRefsAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cTypeRefsIntersectionTypeExpressionParserRuleCall_1_1_1_0 = (RuleCall)cTypeRefsAssignment_1_1_1.eContents().get(0);
		
		//UnionTypeExpression returns TypeRef:
		//    IntersectionTypeExpression ({UnionTypeExpression.typeRefs+=current} ("|" typeRefs+=IntersectionTypeExpression)+)?;
		@Override public ParserRule getRule() { return rule; }
		
		//IntersectionTypeExpression ({UnionTypeExpression.typeRefs+=current} ("|" typeRefs+=IntersectionTypeExpression)+)?
		public Group getGroup() { return cGroup; }
		
		//IntersectionTypeExpression
		public RuleCall getIntersectionTypeExpressionParserRuleCall_0() { return cIntersectionTypeExpressionParserRuleCall_0; }
		
		//({UnionTypeExpression.typeRefs+=current} ("|" typeRefs+=IntersectionTypeExpression)+)?
		public Group getGroup_1() { return cGroup_1; }
		
		//{UnionTypeExpression.typeRefs+=current}
		public Action getUnionTypeExpressionTypeRefsAction_1_0() { return cUnionTypeExpressionTypeRefsAction_1_0; }
		
		//("|" typeRefs+=IntersectionTypeExpression)+
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//"|"
		public Keyword getVerticalLineKeyword_1_1_0() { return cVerticalLineKeyword_1_1_0; }
		
		//typeRefs+=IntersectionTypeExpression
		public Assignment getTypeRefsAssignment_1_1_1() { return cTypeRefsAssignment_1_1_1; }
		
		//IntersectionTypeExpression
		public RuleCall getTypeRefsIntersectionTypeExpressionParserRuleCall_1_1_1_0() { return cTypeRefsIntersectionTypeExpressionParserRuleCall_1_1_1_0; }
	}
	public class IntersectionTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.IntersectionTypeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cOperatorTypeRefParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cIntersectionTypeExpressionTypeRefsAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Keyword cAmpersandKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cTypeRefsAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cTypeRefsOperatorTypeRefParserRuleCall_1_1_1_0 = (RuleCall)cTypeRefsAssignment_1_1_1.eContents().get(0);
		
		//IntersectionTypeExpression returns TypeRef:
		//    OperatorTypeRef ({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=OperatorTypeRef)+)?;
		@Override public ParserRule getRule() { return rule; }
		
		//OperatorTypeRef ({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=OperatorTypeRef)+)?
		public Group getGroup() { return cGroup; }
		
		//OperatorTypeRef
		public RuleCall getOperatorTypeRefParserRuleCall_0() { return cOperatorTypeRefParserRuleCall_0; }
		
		//({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=OperatorTypeRef)+)?
		public Group getGroup_1() { return cGroup_1; }
		
		//{IntersectionTypeExpression.typeRefs+=current}
		public Action getIntersectionTypeExpressionTypeRefsAction_1_0() { return cIntersectionTypeExpressionTypeRefsAction_1_0; }
		
		//("&" typeRefs+=OperatorTypeRef)+
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//"&"
		public Keyword getAmpersandKeyword_1_1_0() { return cAmpersandKeyword_1_1_0; }
		
		//typeRefs+=OperatorTypeRef
		public Assignment getTypeRefsAssignment_1_1_1() { return cTypeRefsAssignment_1_1_1; }
		
		//OperatorTypeRef
		public RuleCall getTypeRefsOperatorTypeRefParserRuleCall_1_1_1_0() { return cTypeRefsOperatorTypeRefParserRuleCall_1_1_1_0; }
	}
	public class OperatorTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.OperatorTypeRef");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Action cOperatorTypeRefAction_0_0 = (Action)cGroup_0.eContents().get(0);
		private final Assignment cOpAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
		private final RuleCall cOpTypeOperatorEnumRuleCall_0_1_0 = (RuleCall)cOpAssignment_0_1.eContents().get(0);
		private final Assignment cTypeRefAssignment_0_2 = (Assignment)cGroup_0.eContents().get(2);
		private final RuleCall cTypeRefArrayTypeExpressionParserRuleCall_0_2_0 = (RuleCall)cTypeRefAssignment_0_2.eContents().get(0);
		private final RuleCall cArrayTypeExpressionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//OperatorTypeRef returns TypeRef:
		//      ({OperatorTypeRef} op=TypeOperator typeRef=ArrayTypeExpression)
		//    | ArrayTypeExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//  ({OperatorTypeRef} op=TypeOperator typeRef=ArrayTypeExpression)
		//| ArrayTypeExpression
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//({OperatorTypeRef} op=TypeOperator typeRef=ArrayTypeExpression)
		public Group getGroup_0() { return cGroup_0; }
		
		//{OperatorTypeRef}
		public Action getOperatorTypeRefAction_0_0() { return cOperatorTypeRefAction_0_0; }
		
		//op=TypeOperator
		public Assignment getOpAssignment_0_1() { return cOpAssignment_0_1; }
		
		//TypeOperator
		public RuleCall getOpTypeOperatorEnumRuleCall_0_1_0() { return cOpTypeOperatorEnumRuleCall_0_1_0; }
		
		//typeRef=ArrayTypeExpression
		public Assignment getTypeRefAssignment_0_2() { return cTypeRefAssignment_0_2; }
		
		//ArrayTypeExpression
		public RuleCall getTypeRefArrayTypeExpressionParserRuleCall_0_2_0() { return cTypeRefArrayTypeExpressionParserRuleCall_0_2_0; }
		
		//ArrayTypeExpression
		public RuleCall getArrayTypeExpressionParserRuleCall_1() { return cArrayTypeExpressionParserRuleCall_1; }
	}
	public class ArrayTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ArrayTypeExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Action cParameterizedTypeRefAction_0_0 = (Action)cGroup_0.eContents().get(0);
		private final Assignment cDeclaredTypeArgsAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
		private final RuleCall cDeclaredTypeArgsWildcardOldNotationWithoutBoundParserRuleCall_0_1_0 = (RuleCall)cDeclaredTypeArgsAssignment_0_1.eContents().get(0);
		private final Assignment cArrayTypeExpressionAssignment_0_2 = (Assignment)cGroup_0.eContents().get(2);
		private final Keyword cArrayTypeExpressionLeftSquareBracketKeyword_0_2_0 = (Keyword)cArrayTypeExpressionAssignment_0_2.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_0_3 = (Keyword)cGroup_0.eContents().get(3);
		private final Group cGroup_0_4 = (Group)cGroup_0.eContents().get(4);
		private final Group cGroup_0_4_0 = (Group)cGroup_0_4.eContents().get(0);
		private final Action cParameterizedTypeRefDeclaredTypeArgsAction_0_4_0_0 = (Action)cGroup_0_4_0.eContents().get(0);
		private final Assignment cArrayTypeExpressionAssignment_0_4_0_1 = (Assignment)cGroup_0_4_0.eContents().get(1);
		private final Keyword cArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0 = (Keyword)cArrayTypeExpressionAssignment_0_4_0_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_0_4_0_2 = (Keyword)cGroup_0_4_0.eContents().get(2);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Action cParameterizedTypeRefAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1_1 = (Keyword)cGroup_1.eContents().get(1);
		private final Assignment cDeclaredTypeArgsAssignment_1_2 = (Assignment)cGroup_1.eContents().get(2);
		private final RuleCall cDeclaredTypeArgsWildcardParserRuleCall_1_2_0 = (RuleCall)cDeclaredTypeArgsAssignment_1_2.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_1_3 = (Keyword)cGroup_1.eContents().get(3);
		private final Assignment cArrayTypeExpressionAssignment_1_4 = (Assignment)cGroup_1.eContents().get(4);
		private final Keyword cArrayTypeExpressionLeftSquareBracketKeyword_1_4_0 = (Keyword)cArrayTypeExpressionAssignment_1_4.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1_5 = (Keyword)cGroup_1.eContents().get(5);
		private final Group cGroup_1_6 = (Group)cGroup_1.eContents().get(6);
		private final Group cGroup_1_6_0 = (Group)cGroup_1_6.eContents().get(0);
		private final Action cParameterizedTypeRefDeclaredTypeArgsAction_1_6_0_0 = (Action)cGroup_1_6_0.eContents().get(0);
		private final Assignment cArrayTypeExpressionAssignment_1_6_0_1 = (Assignment)cGroup_1_6_0.eContents().get(1);
		private final Keyword cArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0 = (Keyword)cArrayTypeExpressionAssignment_1_6_0_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1_6_0_2 = (Keyword)cGroup_1_6_0.eContents().get(2);
		private final RuleCall cArrayNTypeExpressionParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final Group cGroup_3 = (Group)cAlternatives.eContents().get(3);
		private final RuleCall cPrimaryTypeExpressionParserRuleCall_3_0 = (RuleCall)cGroup_3.eContents().get(0);
		private final Group cGroup_3_1 = (Group)cGroup_3.eContents().get(1);
		private final Alternatives cAlternatives_3_1_0 = (Alternatives)cGroup_3_1.eContents().get(0);
		private final Group cGroup_3_1_0_0 = (Group)cAlternatives_3_1_0.eContents().get(0);
		private final Action cParameterizedTypeRefDeclaredTypeArgsAction_3_1_0_0_0 = (Action)cGroup_3_1_0_0.eContents().get(0);
		private final Assignment cArrayTypeExpressionAssignment_3_1_0_0_1 = (Assignment)cGroup_3_1_0_0.eContents().get(1);
		private final Keyword cArrayTypeExpressionLeftSquareBracketKeyword_3_1_0_0_1_0 = (Keyword)cArrayTypeExpressionAssignment_3_1_0_0_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_3_1_0_0_2 = (Keyword)cGroup_3_1_0_0.eContents().get(2);
		private final Group cGroup_3_1_0_1 = (Group)cAlternatives_3_1_0.eContents().get(1);
		private final Action cIndexAccessTypeRefTargetTypeRefAction_3_1_0_1_0 = (Action)cGroup_3_1_0_1.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_3_1_0_1_1 = (Keyword)cGroup_3_1_0_1.eContents().get(1);
		private final Assignment cIndexTypeRefAssignment_3_1_0_1_2 = (Assignment)cGroup_3_1_0_1.eContents().get(2);
		private final RuleCall cIndexTypeRefPrimaryTypeExpressionParserRuleCall_3_1_0_1_2_0 = (RuleCall)cIndexTypeRefAssignment_3_1_0_1_2.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_3_1_0_1_3 = (Keyword)cGroup_3_1_0_1.eContents().get(3);
		
		//ArrayTypeExpression returns TypeRef:
		//      ({ParameterizedTypeRef} declaredTypeArgs+=WildcardOldNotationWithoutBound arrayTypeExpression?='[' ']' =>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*)
		//    | ({ParameterizedTypeRef} '(' declaredTypeArgs+=Wildcard ')' arrayTypeExpression?='[' ']' =>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*)
		//    | ArrayNTypeExpression
		//    | PrimaryTypeExpression =>(
		//          ({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')
		//        | ({IndexAccessTypeRef.targetTypeRef=current} '[' indexTypeRef=PrimaryTypeExpression ']')
		//    )*;
		@Override public ParserRule getRule() { return rule; }
		
		//  ({ParameterizedTypeRef} declaredTypeArgs+=WildcardOldNotationWithoutBound arrayTypeExpression?='[' ']' =>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*)
		//| ({ParameterizedTypeRef} '(' declaredTypeArgs+=Wildcard ')' arrayTypeExpression?='[' ']' =>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*)
		//| ArrayNTypeExpression
		//| PrimaryTypeExpression =>(
		//      ({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')
		//    | ({IndexAccessTypeRef.targetTypeRef=current} '[' indexTypeRef=PrimaryTypeExpression ']')
		//)*
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//({ParameterizedTypeRef} declaredTypeArgs+=WildcardOldNotationWithoutBound arrayTypeExpression?='[' ']' =>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*)
		public Group getGroup_0() { return cGroup_0; }
		
		//{ParameterizedTypeRef}
		public Action getParameterizedTypeRefAction_0_0() { return cParameterizedTypeRefAction_0_0; }
		
		//declaredTypeArgs+=WildcardOldNotationWithoutBound
		public Assignment getDeclaredTypeArgsAssignment_0_1() { return cDeclaredTypeArgsAssignment_0_1; }
		
		//WildcardOldNotationWithoutBound
		public RuleCall getDeclaredTypeArgsWildcardOldNotationWithoutBoundParserRuleCall_0_1_0() { return cDeclaredTypeArgsWildcardOldNotationWithoutBoundParserRuleCall_0_1_0; }
		
		//arrayTypeExpression?='['
		public Assignment getArrayTypeExpressionAssignment_0_2() { return cArrayTypeExpressionAssignment_0_2; }
		
		//'['
		public Keyword getArrayTypeExpressionLeftSquareBracketKeyword_0_2_0() { return cArrayTypeExpressionLeftSquareBracketKeyword_0_2_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_0_3() { return cRightSquareBracketKeyword_0_3; }
		
		//=>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*
		public Group getGroup_0_4() { return cGroup_0_4; }
		
		//{ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']'
		public Group getGroup_0_4_0() { return cGroup_0_4_0; }
		
		//{ParameterizedTypeRef.declaredTypeArgs+=current}
		public Action getParameterizedTypeRefDeclaredTypeArgsAction_0_4_0_0() { return cParameterizedTypeRefDeclaredTypeArgsAction_0_4_0_0; }
		
		//arrayTypeExpression?='['
		public Assignment getArrayTypeExpressionAssignment_0_4_0_1() { return cArrayTypeExpressionAssignment_0_4_0_1; }
		
		//'['
		public Keyword getArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0() { return cArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_0_4_0_2() { return cRightSquareBracketKeyword_0_4_0_2; }
		
		//({ParameterizedTypeRef} '(' declaredTypeArgs+=Wildcard ')' arrayTypeExpression?='[' ']' =>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*)
		public Group getGroup_1() { return cGroup_1; }
		
		//{ParameterizedTypeRef}
		public Action getParameterizedTypeRefAction_1_0() { return cParameterizedTypeRefAction_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1_1() { return cLeftParenthesisKeyword_1_1; }
		
		//declaredTypeArgs+=Wildcard
		public Assignment getDeclaredTypeArgsAssignment_1_2() { return cDeclaredTypeArgsAssignment_1_2; }
		
		//Wildcard
		public RuleCall getDeclaredTypeArgsWildcardParserRuleCall_1_2_0() { return cDeclaredTypeArgsWildcardParserRuleCall_1_2_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_1_3() { return cRightParenthesisKeyword_1_3; }
		
		//arrayTypeExpression?='['
		public Assignment getArrayTypeExpressionAssignment_1_4() { return cArrayTypeExpressionAssignment_1_4; }
		
		//'['
		public Keyword getArrayTypeExpressionLeftSquareBracketKeyword_1_4_0() { return cArrayTypeExpressionLeftSquareBracketKeyword_1_4_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1_5() { return cRightSquareBracketKeyword_1_5; }
		
		//=>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*
		public Group getGroup_1_6() { return cGroup_1_6; }
		
		//{ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']'
		public Group getGroup_1_6_0() { return cGroup_1_6_0; }
		
		//{ParameterizedTypeRef.declaredTypeArgs+=current}
		public Action getParameterizedTypeRefDeclaredTypeArgsAction_1_6_0_0() { return cParameterizedTypeRefDeclaredTypeArgsAction_1_6_0_0; }
		
		//arrayTypeExpression?='['
		public Assignment getArrayTypeExpressionAssignment_1_6_0_1() { return cArrayTypeExpressionAssignment_1_6_0_1; }
		
		//'['
		public Keyword getArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0() { return cArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1_6_0_2() { return cRightSquareBracketKeyword_1_6_0_2; }
		
		//ArrayNTypeExpression
		public RuleCall getArrayNTypeExpressionParserRuleCall_2() { return cArrayNTypeExpressionParserRuleCall_2; }
		
		//PrimaryTypeExpression =>(
		//         ({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')
		//       | ({IndexAccessTypeRef.targetTypeRef=current} '[' indexTypeRef=PrimaryTypeExpression ']')
		//   )*
		public Group getGroup_3() { return cGroup_3; }
		
		//PrimaryTypeExpression
		public RuleCall getPrimaryTypeExpressionParserRuleCall_3_0() { return cPrimaryTypeExpressionParserRuleCall_3_0; }
		
		//=>(
		//         ({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')
		//       | ({IndexAccessTypeRef.targetTypeRef=current} '[' indexTypeRef=PrimaryTypeExpression ']')
		//   )*
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//  ({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')
		//| ({IndexAccessTypeRef.targetTypeRef=current} '[' indexTypeRef=PrimaryTypeExpression ']')
		public Alternatives getAlternatives_3_1_0() { return cAlternatives_3_1_0; }
		
		//({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')
		public Group getGroup_3_1_0_0() { return cGroup_3_1_0_0; }
		
		//{ParameterizedTypeRef.declaredTypeArgs+=current}
		public Action getParameterizedTypeRefDeclaredTypeArgsAction_3_1_0_0_0() { return cParameterizedTypeRefDeclaredTypeArgsAction_3_1_0_0_0; }
		
		//arrayTypeExpression?='['
		public Assignment getArrayTypeExpressionAssignment_3_1_0_0_1() { return cArrayTypeExpressionAssignment_3_1_0_0_1; }
		
		//'['
		public Keyword getArrayTypeExpressionLeftSquareBracketKeyword_3_1_0_0_1_0() { return cArrayTypeExpressionLeftSquareBracketKeyword_3_1_0_0_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_3_1_0_0_2() { return cRightSquareBracketKeyword_3_1_0_0_2; }
		
		//({IndexAccessTypeRef.targetTypeRef=current} '[' indexTypeRef=PrimaryTypeExpression ']')
		public Group getGroup_3_1_0_1() { return cGroup_3_1_0_1; }
		
		//{IndexAccessTypeRef.targetTypeRef=current}
		public Action getIndexAccessTypeRefTargetTypeRefAction_3_1_0_1_0() { return cIndexAccessTypeRefTargetTypeRefAction_3_1_0_1_0; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_3_1_0_1_1() { return cLeftSquareBracketKeyword_3_1_0_1_1; }
		
		//indexTypeRef=PrimaryTypeExpression
		public Assignment getIndexTypeRefAssignment_3_1_0_1_2() { return cIndexTypeRefAssignment_3_1_0_1_2; }
		
		//PrimaryTypeExpression
		public RuleCall getIndexTypeRefPrimaryTypeExpressionParserRuleCall_3_1_0_1_2_0() { return cIndexTypeRefPrimaryTypeExpressionParserRuleCall_3_1_0_1_2_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_3_1_0_1_3() { return cRightSquareBracketKeyword_3_1_0_1_3; }
	}
	public class PrimaryTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.PrimaryTypeExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cLiteralTypeRefParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cArrowFunctionTypeExpressionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cTypeRefWithModifiersParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cQueryTypeRefParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cInferTypeRefParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final Group cGroup_5 = (Group)cAlternatives.eContents().get(5);
		private final Keyword cLeftParenthesisKeyword_5_0 = (Keyword)cGroup_5.eContents().get(0);
		private final RuleCall cTypeRefParserRuleCall_5_1 = (RuleCall)cGroup_5.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_5_2 = (Keyword)cGroup_5.eContents().get(2);
		
		//PrimaryTypeExpression returns TypeRef:
		//    ( LiteralTypeRef
		//    | ArrowFunctionTypeExpression
		//    | TypeRefWithModifiers
		//    | QueryTypeRef
		//    | InferTypeRef
		//    | "(" TypeRef ")"
		//    );
		@Override public ParserRule getRule() { return rule; }
		
		//( LiteralTypeRef
		//| ArrowFunctionTypeExpression
		//| TypeRefWithModifiers
		//| QueryTypeRef
		//| InferTypeRef
		//| "(" TypeRef ")"
		//)
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//LiteralTypeRef
		public RuleCall getLiteralTypeRefParserRuleCall_0() { return cLiteralTypeRefParserRuleCall_0; }
		
		//ArrowFunctionTypeExpression
		public RuleCall getArrowFunctionTypeExpressionParserRuleCall_1() { return cArrowFunctionTypeExpressionParserRuleCall_1; }
		
		//TypeRefWithModifiers
		public RuleCall getTypeRefWithModifiersParserRuleCall_2() { return cTypeRefWithModifiersParserRuleCall_2; }
		
		//QueryTypeRef
		public RuleCall getQueryTypeRefParserRuleCall_3() { return cQueryTypeRefParserRuleCall_3; }
		
		//InferTypeRef
		public RuleCall getInferTypeRefParserRuleCall_4() { return cInferTypeRefParserRuleCall_4; }
		
		//"(" TypeRef ")"
		public Group getGroup_5() { return cGroup_5; }
		
		//"("
		public Keyword getLeftParenthesisKeyword_5_0() { return cLeftParenthesisKeyword_5_0; }
		
		//TypeRef
		public RuleCall getTypeRefParserRuleCall_5_1() { return cTypeRefParserRuleCall_5_1; }
		
		//")"
		public Keyword getRightParenthesisKeyword_5_2() { return cRightParenthesisKeyword_5_2; }
	}
	public class TypeRefWithModifiersElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeRefWithModifiers");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final RuleCall cParameterizedTypeRefParserRuleCall_0_0 = (RuleCall)cGroup_0.eContents().get(0);
		private final Assignment cDynamicAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
		private final Keyword cDynamicPlusSignKeyword_0_1_0 = (Keyword)cDynamicAssignment_0_1.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final RuleCall cThisTypeRefParserRuleCall_1_0 = (RuleCall)cGroup_1.eContents().get(0);
		private final Assignment cDynamicAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final Keyword cDynamicPlusSignKeyword_1_1_0 = (Keyword)cDynamicAssignment_1_1.eContents().get(0);
		private final RuleCall cTypeTypeRefParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cUnionTypeExpressionOLDParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cIntersectionTypeExpressionOLDParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cMappedTypeRefParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		
		//TypeRefWithModifiers returns TypeRef:
		//      (ParameterizedTypeRef => dynamic?='+'?)
		//    | (ThisTypeRef => dynamic?='+'?)
		//    | TypeTypeRef
		//    | UnionTypeExpressionOLD
		//    | IntersectionTypeExpressionOLD
		//    | MappedTypeRef // this covers sequences starting with: '{' '[' IdentifierName 'in' ...
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//  (ParameterizedTypeRef => dynamic?='+'?)
		//| (ThisTypeRef => dynamic?='+'?)
		//| TypeTypeRef
		//| UnionTypeExpressionOLD
		//| IntersectionTypeExpressionOLD
		//| MappedTypeRef
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//(ParameterizedTypeRef => dynamic?='+'?)
		public Group getGroup_0() { return cGroup_0; }
		
		//ParameterizedTypeRef
		public RuleCall getParameterizedTypeRefParserRuleCall_0_0() { return cParameterizedTypeRefParserRuleCall_0_0; }
		
		//=> dynamic?='+'?
		public Assignment getDynamicAssignment_0_1() { return cDynamicAssignment_0_1; }
		
		//'+'
		public Keyword getDynamicPlusSignKeyword_0_1_0() { return cDynamicPlusSignKeyword_0_1_0; }
		
		//(ThisTypeRef => dynamic?='+'?)
		public Group getGroup_1() { return cGroup_1; }
		
		//ThisTypeRef
		public RuleCall getThisTypeRefParserRuleCall_1_0() { return cThisTypeRefParserRuleCall_1_0; }
		
		//=> dynamic?='+'?
		public Assignment getDynamicAssignment_1_1() { return cDynamicAssignment_1_1; }
		
		//'+'
		public Keyword getDynamicPlusSignKeyword_1_1_0() { return cDynamicPlusSignKeyword_1_1_0; }
		
		//TypeTypeRef
		public RuleCall getTypeTypeRefParserRuleCall_2() { return cTypeTypeRefParserRuleCall_2; }
		
		//UnionTypeExpressionOLD
		public RuleCall getUnionTypeExpressionOLDParserRuleCall_3() { return cUnionTypeExpressionOLDParserRuleCall_3; }
		
		//IntersectionTypeExpressionOLD
		public RuleCall getIntersectionTypeExpressionOLDParserRuleCall_4() { return cIntersectionTypeExpressionOLDParserRuleCall_4; }
		
		//MappedTypeRef
		public RuleCall getMappedTypeRefParserRuleCall_5() { return cMappedTypeRefParserRuleCall_5; }
	}
	public class TypeArgInTypeTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeArgInTypeTypeRef");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cParameterizedTypeRefNominalParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cThisTypeRefNominalParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cWildcardOldNotationParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//TypeArgInTypeTypeRef returns TypeArgument:
		//      ParameterizedTypeRefNominal
		//    | ThisTypeRefNominal
		//    | WildcardOldNotation;
		@Override public ParserRule getRule() { return rule; }
		
		//  ParameterizedTypeRefNominal
		//| ThisTypeRefNominal
		//| WildcardOldNotation
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getParameterizedTypeRefNominalParserRuleCall_0() { return cParameterizedTypeRefNominalParserRuleCall_0; }
		
		//ThisTypeRefNominal
		public RuleCall getThisTypeRefNominalParserRuleCall_1() { return cThisTypeRefNominalParserRuleCall_1; }
		
		//WildcardOldNotation
		public RuleCall getWildcardOldNotationParserRuleCall_2() { return cWildcardOldNotationParserRuleCall_2; }
	}
	public class LiteralTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.LiteralTypeRef");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cBooleanLiteralTypeRefParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cNumericLiteralTypeRefParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cStringLiteralTypeRefParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//LiteralTypeRef returns LiteralTypeRef:
		//      BooleanLiteralTypeRef
		//    | NumericLiteralTypeRef
		//    | StringLiteralTypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//  BooleanLiteralTypeRef
		//| NumericLiteralTypeRef
		//| StringLiteralTypeRef
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//BooleanLiteralTypeRef
		public RuleCall getBooleanLiteralTypeRefParserRuleCall_0() { return cBooleanLiteralTypeRefParserRuleCall_0; }
		
		//NumericLiteralTypeRef
		public RuleCall getNumericLiteralTypeRefParserRuleCall_1() { return cNumericLiteralTypeRefParserRuleCall_1; }
		
		//StringLiteralTypeRef
		public RuleCall getStringLiteralTypeRefParserRuleCall_2() { return cStringLiteralTypeRefParserRuleCall_2; }
	}
	public class BooleanLiteralTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.BooleanLiteralTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cBooleanLiteralTypeRefAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cAstValueAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Alternatives cAstValueAlternatives_1_0 = (Alternatives)cAstValueAssignment_1.eContents().get(0);
		private final Keyword cAstValueTrueKeyword_1_0_0 = (Keyword)cAstValueAlternatives_1_0.eContents().get(0);
		private final Keyword cAstValueFalseKeyword_1_0_1 = (Keyword)cAstValueAlternatives_1_0.eContents().get(1);
		
		//    // note: EnumLiteralTypeRefs are not available in type expressions
		//BooleanLiteralTypeRef returns BooleanLiteralTypeRef:
		//    {BooleanLiteralTypeRef} astValue=('true' | 'false');
		@Override public ParserRule getRule() { return rule; }
		
		//{BooleanLiteralTypeRef} astValue=('true' | 'false')
		public Group getGroup() { return cGroup; }
		
		//{BooleanLiteralTypeRef}
		public Action getBooleanLiteralTypeRefAction_0() { return cBooleanLiteralTypeRefAction_0; }
		
		//astValue=('true' | 'false')
		public Assignment getAstValueAssignment_1() { return cAstValueAssignment_1; }
		
		//('true' | 'false')
		public Alternatives getAstValueAlternatives_1_0() { return cAstValueAlternatives_1_0; }
		
		//'true'
		public Keyword getAstValueTrueKeyword_1_0_0() { return cAstValueTrueKeyword_1_0_0; }
		
		//'false'
		public Keyword getAstValueFalseKeyword_1_0_1() { return cAstValueFalseKeyword_1_0_1; }
	}
	public class NumericLiteralTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.NumericLiteralTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Keyword cPlusSignKeyword_0_0 = (Keyword)cAlternatives_0.eContents().get(0);
		private final Assignment cAstNegatedAssignment_0_1 = (Assignment)cAlternatives_0.eContents().get(1);
		private final Keyword cAstNegatedHyphenMinusKeyword_0_1_0 = (Keyword)cAstNegatedAssignment_0_1.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cAstValueAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final RuleCall cAstValueINTTerminalRuleCall_1_0_0 = (RuleCall)cAstValueAssignment_1_0.eContents().get(0);
		private final Assignment cAstValueAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final RuleCall cAstValueDOUBLETerminalRuleCall_1_1_0 = (RuleCall)cAstValueAssignment_1_1.eContents().get(0);
		private final Assignment cAstValueAssignment_1_2 = (Assignment)cAlternatives_1.eContents().get(2);
		private final RuleCall cAstValueOCTAL_INTTerminalRuleCall_1_2_0 = (RuleCall)cAstValueAssignment_1_2.eContents().get(0);
		private final Assignment cAstValueAssignment_1_3 = (Assignment)cAlternatives_1.eContents().get(3);
		private final RuleCall cAstValueLEGACY_OCTAL_INTTerminalRuleCall_1_3_0 = (RuleCall)cAstValueAssignment_1_3.eContents().get(0);
		private final Assignment cAstValueAssignment_1_4 = (Assignment)cAlternatives_1.eContents().get(4);
		private final RuleCall cAstValueHEX_INTTerminalRuleCall_1_4_0 = (RuleCall)cAstValueAssignment_1_4.eContents().get(0);
		private final Assignment cAstValueAssignment_1_5 = (Assignment)cAlternatives_1.eContents().get(5);
		private final RuleCall cAstValueBINARY_INTTerminalRuleCall_1_5_0 = (RuleCall)cAstValueAssignment_1_5.eContents().get(0);
		private final Assignment cAstValueAssignment_1_6 = (Assignment)cAlternatives_1.eContents().get(6);
		private final RuleCall cAstValueSCIENTIFIC_INTTerminalRuleCall_1_6_0 = (RuleCall)cAstValueAssignment_1_6.eContents().get(0);
		
		//NumericLiteralTypeRef returns NumericLiteralTypeRef:
		//    ('+' | astNegated?='-')?
		//    (
		//          astValue=INT
		//        | astValue=DOUBLE
		//        | astValue=OCTAL_INT
		//        | astValue=LEGACY_OCTAL_INT
		//        | astValue=HEX_INT
		//        | astValue=BINARY_INT
		//        | astValue=SCIENTIFIC_INT
		//    );
		@Override public ParserRule getRule() { return rule; }
		
		//('+' | astNegated?='-')?
		//(
		//      astValue=INT
		//    | astValue=DOUBLE
		//    | astValue=OCTAL_INT
		//    | astValue=LEGACY_OCTAL_INT
		//    | astValue=HEX_INT
		//    | astValue=BINARY_INT
		//    | astValue=SCIENTIFIC_INT
		//)
		public Group getGroup() { return cGroup; }
		
		//('+' | astNegated?='-')?
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//'+'
		public Keyword getPlusSignKeyword_0_0() { return cPlusSignKeyword_0_0; }
		
		//astNegated?='-'
		public Assignment getAstNegatedAssignment_0_1() { return cAstNegatedAssignment_0_1; }
		
		//'-'
		public Keyword getAstNegatedHyphenMinusKeyword_0_1_0() { return cAstNegatedHyphenMinusKeyword_0_1_0; }
		
		//(
		//      astValue=INT
		//    | astValue=DOUBLE
		//    | astValue=OCTAL_INT
		//    | astValue=LEGACY_OCTAL_INT
		//    | astValue=HEX_INT
		//    | astValue=BINARY_INT
		//    | astValue=SCIENTIFIC_INT
		//)
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//astValue=INT
		public Assignment getAstValueAssignment_1_0() { return cAstValueAssignment_1_0; }
		
		//INT
		public RuleCall getAstValueINTTerminalRuleCall_1_0_0() { return cAstValueINTTerminalRuleCall_1_0_0; }
		
		//astValue=DOUBLE
		public Assignment getAstValueAssignment_1_1() { return cAstValueAssignment_1_1; }
		
		//DOUBLE
		public RuleCall getAstValueDOUBLETerminalRuleCall_1_1_0() { return cAstValueDOUBLETerminalRuleCall_1_1_0; }
		
		//astValue=OCTAL_INT
		public Assignment getAstValueAssignment_1_2() { return cAstValueAssignment_1_2; }
		
		//OCTAL_INT
		public RuleCall getAstValueOCTAL_INTTerminalRuleCall_1_2_0() { return cAstValueOCTAL_INTTerminalRuleCall_1_2_0; }
		
		//astValue=LEGACY_OCTAL_INT
		public Assignment getAstValueAssignment_1_3() { return cAstValueAssignment_1_3; }
		
		//LEGACY_OCTAL_INT
		public RuleCall getAstValueLEGACY_OCTAL_INTTerminalRuleCall_1_3_0() { return cAstValueLEGACY_OCTAL_INTTerminalRuleCall_1_3_0; }
		
		//astValue=HEX_INT
		public Assignment getAstValueAssignment_1_4() { return cAstValueAssignment_1_4; }
		
		//HEX_INT
		public RuleCall getAstValueHEX_INTTerminalRuleCall_1_4_0() { return cAstValueHEX_INTTerminalRuleCall_1_4_0; }
		
		//astValue=BINARY_INT
		public Assignment getAstValueAssignment_1_5() { return cAstValueAssignment_1_5; }
		
		//BINARY_INT
		public RuleCall getAstValueBINARY_INTTerminalRuleCall_1_5_0() { return cAstValueBINARY_INTTerminalRuleCall_1_5_0; }
		
		//astValue=SCIENTIFIC_INT
		public Assignment getAstValueAssignment_1_6() { return cAstValueAssignment_1_6; }
		
		//SCIENTIFIC_INT
		public RuleCall getAstValueSCIENTIFIC_INTTerminalRuleCall_1_6_0() { return cAstValueSCIENTIFIC_INTTerminalRuleCall_1_6_0; }
	}
	public class StringLiteralTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.StringLiteralTypeRef");
		private final Assignment cAstValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cAstValueSTRINGTerminalRuleCall_0 = (RuleCall)cAstValueAssignment.eContents().get(0);
		
		//StringLiteralTypeRef returns StringLiteralTypeRef:
		//    astValue=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//astValue=STRING
		public Assignment getAstValueAssignment() { return cAstValueAssignment; }
		
		//STRING
		public RuleCall getAstValueSTRINGTerminalRuleCall_0() { return cAstValueSTRINGTerminalRuleCall_0; }
	}
	public class ThisTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ThisTypeRef");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cThisTypeRefNominalParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cThisTypeRefStructuralParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//ThisTypeRef returns ThisTypeRef:
		//    ThisTypeRefNominal | ThisTypeRefStructural;
		@Override public ParserRule getRule() { return rule; }
		
		//ThisTypeRefNominal | ThisTypeRefStructural
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ThisTypeRefNominal
		public RuleCall getThisTypeRefNominalParserRuleCall_0() { return cThisTypeRefNominalParserRuleCall_0; }
		
		//ThisTypeRefStructural
		public RuleCall getThisTypeRefStructuralParserRuleCall_1() { return cThisTypeRefStructuralParserRuleCall_1; }
	}
	public class ThisTypeRefNominalElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ThisTypeRefNominal");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cThisTypeRefNominalAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cThisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//ThisTypeRefNominal returns ThisTypeRefNominal:
		//    {ThisTypeRefNominal} 'this';
		@Override public ParserRule getRule() { return rule; }
		
		//{ThisTypeRefNominal} 'this'
		public Group getGroup() { return cGroup; }
		
		//{ThisTypeRefNominal}
		public Action getThisTypeRefNominalAction_0() { return cThisTypeRefNominalAction_0; }
		
		//'this'
		public Keyword getThisKeyword_1() { return cThisKeyword_1; }
	}
	public class ThisTypeRefStructuralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ThisTypeRefStructural");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDefinedTypingStrategyAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0 = (RuleCall)cDefinedTypingStrategyAssignment_0.eContents().get(0);
		private final Keyword cThisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cWithKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final RuleCall cTStructMemberListParserRuleCall_2_1 = (RuleCall)cGroup_2.eContents().get(1);
		
		//ThisTypeRefStructural returns ThisTypeRefStructural:
		//    definedTypingStrategy=TypingStrategyUseSiteOperator
		//    'this'
		//    ('with' TStructMemberList)?;
		@Override public ParserRule getRule() { return rule; }
		
		//definedTypingStrategy=TypingStrategyUseSiteOperator
		//'this'
		//('with' TStructMemberList)?
		public Group getGroup() { return cGroup; }
		
		//definedTypingStrategy=TypingStrategyUseSiteOperator
		public Assignment getDefinedTypingStrategyAssignment_0() { return cDefinedTypingStrategyAssignment_0; }
		
		//TypingStrategyUseSiteOperator
		public RuleCall getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0() { return cDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0; }
		
		//'this'
		public Keyword getThisKeyword_1() { return cThisKeyword_1; }
		
		//('with' TStructMemberList)?
		public Group getGroup_2() { return cGroup_2; }
		
		//'with'
		public Keyword getWithKeyword_2_0() { return cWithKeyword_2_0; }
		
		//TStructMemberList
		public RuleCall getTStructMemberListParserRuleCall_2_1() { return cTStructMemberListParserRuleCall_2_1; }
	}
	public class ArrowFunctionTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ArrowFunctionTypeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cFunctionTypeExpressionAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final RuleCall cTAnonymousFormalParameterListWithDeclaredThisTypeParserRuleCall_0_0_2 = (RuleCall)cGroup_0_0.eContents().get(2);
		private final Keyword cRightParenthesisKeyword_0_0_3 = (Keyword)cGroup_0_0.eContents().get(3);
		private final RuleCall cArrowParserRuleCall_0_0_4 = (RuleCall)cGroup_0_0.eContents().get(4);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cReturnTypePredicateAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final RuleCall cReturnTypePredicateTypePredicateWithPrimaryParserRuleCall_1_0_0 = (RuleCall)cReturnTypePredicateAssignment_1_0.eContents().get(0);
		private final Assignment cReturnTypeRefAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final RuleCall cReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_1_0 = (RuleCall)cReturnTypeRefAssignment_1_1.eContents().get(0);
		
		//ArrowFunctionTypeExpression returns FunctionTypeExpression:
		//    =>({FunctionTypeExpression} '(' TAnonymousFormalParameterListWithDeclaredThisType ')' Arrow) (returnTypePredicate=TypePredicateWithPrimary | returnTypeRef=PrimaryTypeExpression);
		@Override public ParserRule getRule() { return rule; }
		
		//=>({FunctionTypeExpression} '(' TAnonymousFormalParameterListWithDeclaredThisType ')' Arrow) (returnTypePredicate=TypePredicateWithPrimary | returnTypeRef=PrimaryTypeExpression)
		public Group getGroup() { return cGroup; }
		
		//=>({FunctionTypeExpression} '(' TAnonymousFormalParameterListWithDeclaredThisType ')' Arrow)
		public Group getGroup_0() { return cGroup_0; }
		
		//{FunctionTypeExpression} '(' TAnonymousFormalParameterListWithDeclaredThisType ')' Arrow
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{FunctionTypeExpression}
		public Action getFunctionTypeExpressionAction_0_0_0() { return cFunctionTypeExpressionAction_0_0_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_0_0_1() { return cLeftParenthesisKeyword_0_0_1; }
		
		//TAnonymousFormalParameterListWithDeclaredThisType
		public RuleCall getTAnonymousFormalParameterListWithDeclaredThisTypeParserRuleCall_0_0_2() { return cTAnonymousFormalParameterListWithDeclaredThisTypeParserRuleCall_0_0_2; }
		
		//')'
		public Keyword getRightParenthesisKeyword_0_0_3() { return cRightParenthesisKeyword_0_0_3; }
		
		//Arrow
		public RuleCall getArrowParserRuleCall_0_0_4() { return cArrowParserRuleCall_0_0_4; }
		
		//(returnTypePredicate=TypePredicateWithPrimary | returnTypeRef=PrimaryTypeExpression)
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//returnTypePredicate=TypePredicateWithPrimary
		public Assignment getReturnTypePredicateAssignment_1_0() { return cReturnTypePredicateAssignment_1_0; }
		
		//TypePredicateWithPrimary
		public RuleCall getReturnTypePredicateTypePredicateWithPrimaryParserRuleCall_1_0_0() { return cReturnTypePredicateTypePredicateWithPrimaryParserRuleCall_1_0_0; }
		
		//returnTypeRef=PrimaryTypeExpression
		public Assignment getReturnTypeRefAssignment_1_1() { return cReturnTypeRefAssignment_1_1; }
		
		//PrimaryTypeExpression
		public RuleCall getReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_1_0() { return cReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_1_0; }
	}
	public class TAnonymousFormalParameterListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TAnonymousFormalParameterList");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Assignment cFparsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cFparsTAnonymousFormalParameterParserRuleCall_0_0 = (RuleCall)cFparsAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cCommaKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cFparsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cFparsTAnonymousFormalParameterParserRuleCall_1_1_0 = (RuleCall)cFparsAssignment_1_1.eContents().get(0);
		
		//// TODO extract FormalParameterContainer and use returns FormalParameterContainer instead of wildcard
		//fragment TAnonymousFormalParameterList* :
		//    (fpars+=TAnonymousFormalParameter (',' fpars+=TAnonymousFormalParameter)*)?
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//(fpars+=TAnonymousFormalParameter (',' fpars+=TAnonymousFormalParameter)*)?
		public Group getGroup() { return cGroup; }
		
		//fpars+=TAnonymousFormalParameter
		public Assignment getFparsAssignment_0() { return cFparsAssignment_0; }
		
		//TAnonymousFormalParameter
		public RuleCall getFparsTAnonymousFormalParameterParserRuleCall_0_0() { return cFparsTAnonymousFormalParameterParserRuleCall_0_0; }
		
		//(',' fpars+=TAnonymousFormalParameter)*
		public Group getGroup_1() { return cGroup_1; }
		
		//','
		public Keyword getCommaKeyword_1_0() { return cCommaKeyword_1_0; }
		
		//fpars+=TAnonymousFormalParameter
		public Assignment getFparsAssignment_1_1() { return cFparsAssignment_1_1; }
		
		//TAnonymousFormalParameter
		public RuleCall getFparsTAnonymousFormalParameterParserRuleCall_1_1_0() { return cFparsTAnonymousFormalParameterParserRuleCall_1_1_0; }
	}
	public class TAnonymousFormalParameterListWithDeclaredThisTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TAnonymousFormalParameterListWithDeclaredThisType");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cAlternatives_0.eContents().get(0);
		private final Keyword cThisKeyword_0_0_0 = (Keyword)cGroup_0_0.eContents().get(0);
		private final Keyword cColonKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cDeclaredThisTypeAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cDeclaredThisTypeTypeRefParserRuleCall_0_0_2_0 = (RuleCall)cDeclaredThisTypeAssignment_0_0_2.eContents().get(0);
		private final Assignment cFparsAssignment_0_1 = (Assignment)cAlternatives_0.eContents().get(1);
		private final RuleCall cFparsTAnonymousFormalParameterParserRuleCall_0_1_0 = (RuleCall)cFparsAssignment_0_1.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cCommaKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cFparsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cFparsTAnonymousFormalParameterParserRuleCall_1_1_0 = (RuleCall)cFparsAssignment_1_1.eContents().get(0);
		
		//fragment TAnonymousFormalParameterListWithDeclaredThisType* :
		//    (('this' ':' declaredThisType=TypeRef | fpars+=TAnonymousFormalParameter) (',' fpars+=TAnonymousFormalParameter)*)?
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//(('this' ':' declaredThisType=TypeRef | fpars+=TAnonymousFormalParameter) (',' fpars+=TAnonymousFormalParameter)*)?
		public Group getGroup() { return cGroup; }
		
		//('this' ':' declaredThisType=TypeRef | fpars+=TAnonymousFormalParameter)
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//'this' ':' declaredThisType=TypeRef
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//'this'
		public Keyword getThisKeyword_0_0_0() { return cThisKeyword_0_0_0; }
		
		//':'
		public Keyword getColonKeyword_0_0_1() { return cColonKeyword_0_0_1; }
		
		//declaredThisType=TypeRef
		public Assignment getDeclaredThisTypeAssignment_0_0_2() { return cDeclaredThisTypeAssignment_0_0_2; }
		
		//TypeRef
		public RuleCall getDeclaredThisTypeTypeRefParserRuleCall_0_0_2_0() { return cDeclaredThisTypeTypeRefParserRuleCall_0_0_2_0; }
		
		//fpars+=TAnonymousFormalParameter
		public Assignment getFparsAssignment_0_1() { return cFparsAssignment_0_1; }
		
		//TAnonymousFormalParameter
		public RuleCall getFparsTAnonymousFormalParameterParserRuleCall_0_1_0() { return cFparsTAnonymousFormalParameterParserRuleCall_0_1_0; }
		
		//(',' fpars+=TAnonymousFormalParameter)*
		public Group getGroup_1() { return cGroup_1; }
		
		//','
		public Keyword getCommaKeyword_1_0() { return cCommaKeyword_1_0; }
		
		//fpars+=TAnonymousFormalParameter
		public Assignment getFparsAssignment_1_1() { return cFparsAssignment_1_1; }
		
		//TAnonymousFormalParameter
		public RuleCall getFparsTAnonymousFormalParameterParserRuleCall_1_1_0() { return cFparsTAnonymousFormalParameterParserRuleCall_1_1_0; }
	}
	public class TAnonymousFormalParameterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TAnonymousFormalParameter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cVariadicAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cVariadicFullStopFullStopFullStopKeyword_0_0 = (Keyword)cVariadicAssignment_0.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Assignment cNameAssignment_1_0_0_0 = (Assignment)cGroup_1_0_0.eContents().get(0);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_0_0_0_0 = (RuleCall)cNameAssignment_1_0_0_0.eContents().get(0);
		private final Keyword cQuestionMarkKeyword_1_0_0_1 = (Keyword)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cColonSepTypeRefParserRuleCall_1_0_0_2 = (RuleCall)cGroup_1_0_0.eContents().get(2);
		private final Assignment cTypeRefAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final RuleCall cTypeRefTypeRefParserRuleCall_1_1_0 = (RuleCall)cTypeRefAssignment_1_1.eContents().get(0);
		private final RuleCall cDefaultFormalParameterParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		///**
		// * Used in type expressions, name is optional.
		// */
		//TAnonymousFormalParameter:
		//    variadic?='...'? (=>(name=BindingIdentifier<Yield=false> '?'? ->ColonSepTypeRef) | typeRef=TypeRef)
		//    DefaultFormalParameter
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//variadic?='...'? (=>(name=BindingIdentifier<Yield=false> '?'? ->ColonSepTypeRef) | typeRef=TypeRef)
		//DefaultFormalParameter
		public Group getGroup() { return cGroup; }
		
		//variadic?='...'?
		public Assignment getVariadicAssignment_0() { return cVariadicAssignment_0; }
		
		//'...'
		public Keyword getVariadicFullStopFullStopFullStopKeyword_0_0() { return cVariadicFullStopFullStopFullStopKeyword_0_0; }
		
		//(=>(name=BindingIdentifier<Yield=false> '?'? ->ColonSepTypeRef) | typeRef=TypeRef)
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//=>(name=BindingIdentifier<Yield=false> '?'? ->ColonSepTypeRef)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//name=BindingIdentifier<Yield=false> '?'? ->ColonSepTypeRef
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//name=BindingIdentifier<Yield=false>
		public Assignment getNameAssignment_1_0_0_0() { return cNameAssignment_1_0_0_0; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_0_0_0_0() { return cNameBindingIdentifierParserRuleCall_1_0_0_0_0; }
		
		//'?'?
		public Keyword getQuestionMarkKeyword_1_0_0_1() { return cQuestionMarkKeyword_1_0_0_1; }
		
		//->ColonSepTypeRef
		public RuleCall getColonSepTypeRefParserRuleCall_1_0_0_2() { return cColonSepTypeRefParserRuleCall_1_0_0_2; }
		
		//typeRef=TypeRef
		public Assignment getTypeRefAssignment_1_1() { return cTypeRefAssignment_1_1; }
		
		//TypeRef
		public RuleCall getTypeRefTypeRefParserRuleCall_1_1_0() { return cTypeRefTypeRefParserRuleCall_1_1_0; }
		
		//DefaultFormalParameter
		public RuleCall getDefaultFormalParameterParserRuleCall_2() { return cDefaultFormalParameterParserRuleCall_2; }
	}
	public class TFormalParameterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TFormalParameter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cVariadicAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cVariadicFullStopFullStopFullStopKeyword_0_0 = (Keyword)cVariadicAssignment_0.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cQuestionMarkKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final RuleCall cColonSepTypeRefParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		private final RuleCall cDefaultFormalParameterParserRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		
		///**
		// * Used in Types language only.
		// */
		//TFormalParameter:
		//    variadic?='...'? name=BindingIdentifier<Yield=false> '?'? ColonSepTypeRef
		//    DefaultFormalParameter
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//variadic?='...'? name=BindingIdentifier<Yield=false> '?'? ColonSepTypeRef
		//DefaultFormalParameter
		public Group getGroup() { return cGroup; }
		
		//variadic?='...'?
		public Assignment getVariadicAssignment_0() { return cVariadicAssignment_0; }
		
		//'...'
		public Keyword getVariadicFullStopFullStopFullStopKeyword_0_0() { return cVariadicFullStopFullStopFullStopKeyword_0_0; }
		
		//name=BindingIdentifier<Yield=false>
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_0() { return cNameBindingIdentifierParserRuleCall_1_0; }
		
		//'?'?
		public Keyword getQuestionMarkKeyword_2() { return cQuestionMarkKeyword_2; }
		
		//ColonSepTypeRef
		public RuleCall getColonSepTypeRefParserRuleCall_3() { return cColonSepTypeRefParserRuleCall_3; }
		
		//DefaultFormalParameter
		public RuleCall getDefaultFormalParameterParserRuleCall_4() { return cDefaultFormalParameterParserRuleCall_4; }
	}
	public class DefaultFormalParameterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.DefaultFormalParameter");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Assignment cHasInitializerAssignmentAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cHasInitializerAssignmentEqualsSignKeyword_0_0 = (Keyword)cHasInitializerAssignmentAssignment_0.eContents().get(0);
		private final Assignment cAstInitializerAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cAstInitializerTypeReferenceNameParserRuleCall_1_0 = (RuleCall)cAstInitializerAssignment_1.eContents().get(0);
		
		///**
		// * Default initializers in FunctionTypeExpressions or TFunctions
		// * are necessary to specify optional formal parameters. Hence, their
		// * initializer expression is rather uninteresting and limited by validations
		// * to 'undefined'. The shorthand form, that is omitting the initializer, is supported.
		// */
		//fragment DefaultFormalParameter*:
		//    (hasInitializerAssignment?='=' astInitializer=TypeReferenceName?)?
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//(hasInitializerAssignment?='=' astInitializer=TypeReferenceName?)?
		public Group getGroup() { return cGroup; }
		
		//hasInitializerAssignment?='='
		public Assignment getHasInitializerAssignmentAssignment_0() { return cHasInitializerAssignmentAssignment_0; }
		
		//'='
		public Keyword getHasInitializerAssignmentEqualsSignKeyword_0_0() { return cHasInitializerAssignmentEqualsSignKeyword_0_0; }
		
		//astInitializer=TypeReferenceName?
		public Assignment getAstInitializerAssignment_1() { return cAstInitializerAssignment_1; }
		
		//TypeReferenceName
		public RuleCall getAstInitializerTypeReferenceNameParserRuleCall_1_0() { return cAstInitializerTypeReferenceNameParserRuleCall_1_0; }
	}
	public class UnionTypeExpressionOLDElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.UnionTypeExpressionOLD");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cUnionTypeExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cUnionKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cTypeRefsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cTypeRefsTypeRefParserRuleCall_3_0 = (RuleCall)cTypeRefsAssignment_3.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cCommaKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cTypeRefsAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cTypeRefsTypeRefParserRuleCall_4_1_0 = (RuleCall)cTypeRefsAssignment_4_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_5 = (Keyword)cGroup.eContents().get(5);
		
		//UnionTypeExpressionOLD returns UnionTypeExpression:
		//    {UnionTypeExpression}
		//    'union' '{' typeRefs+=TypeRef (',' typeRefs+=TypeRef)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{UnionTypeExpression}
		//'union' '{' typeRefs+=TypeRef (',' typeRefs+=TypeRef)* '}'
		public Group getGroup() { return cGroup; }
		
		//{UnionTypeExpression}
		public Action getUnionTypeExpressionAction_0() { return cUnionTypeExpressionAction_0; }
		
		//'union'
		public Keyword getUnionKeyword_1() { return cUnionKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//typeRefs+=TypeRef
		public Assignment getTypeRefsAssignment_3() { return cTypeRefsAssignment_3; }
		
		//TypeRef
		public RuleCall getTypeRefsTypeRefParserRuleCall_3_0() { return cTypeRefsTypeRefParserRuleCall_3_0; }
		
		//(',' typeRefs+=TypeRef)*
		public Group getGroup_4() { return cGroup_4; }
		
		//','
		public Keyword getCommaKeyword_4_0() { return cCommaKeyword_4_0; }
		
		//typeRefs+=TypeRef
		public Assignment getTypeRefsAssignment_4_1() { return cTypeRefsAssignment_4_1; }
		
		//TypeRef
		public RuleCall getTypeRefsTypeRefParserRuleCall_4_1_0() { return cTypeRefsTypeRefParserRuleCall_4_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_5() { return cRightCurlyBracketKeyword_5; }
	}
	public class IntersectionTypeExpressionOLDElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.IntersectionTypeExpressionOLD");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cIntersectionTypeExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cIntersectionKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cTypeRefsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cTypeRefsTypeRefParserRuleCall_3_0 = (RuleCall)cTypeRefsAssignment_3.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cCommaKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cTypeRefsAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cTypeRefsTypeRefParserRuleCall_4_1_0 = (RuleCall)cTypeRefsAssignment_4_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_5 = (Keyword)cGroup.eContents().get(5);
		
		//IntersectionTypeExpressionOLD returns IntersectionTypeExpression:
		//    {IntersectionTypeExpression}
		//    'intersection' '{' typeRefs+=TypeRef (',' typeRefs+=TypeRef)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{IntersectionTypeExpression}
		//'intersection' '{' typeRefs+=TypeRef (',' typeRefs+=TypeRef)* '}'
		public Group getGroup() { return cGroup; }
		
		//{IntersectionTypeExpression}
		public Action getIntersectionTypeExpressionAction_0() { return cIntersectionTypeExpressionAction_0; }
		
		//'intersection'
		public Keyword getIntersectionKeyword_1() { return cIntersectionKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//typeRefs+=TypeRef
		public Assignment getTypeRefsAssignment_3() { return cTypeRefsAssignment_3; }
		
		//TypeRef
		public RuleCall getTypeRefsTypeRefParserRuleCall_3_0() { return cTypeRefsTypeRefParserRuleCall_3_0; }
		
		//(',' typeRefs+=TypeRef)*
		public Group getGroup_4() { return cGroup_4; }
		
		//','
		public Keyword getCommaKeyword_4_0() { return cCommaKeyword_4_0; }
		
		//typeRefs+=TypeRef
		public Assignment getTypeRefsAssignment_4_1() { return cTypeRefsAssignment_4_1; }
		
		//TypeRef
		public RuleCall getTypeRefsTypeRefParserRuleCall_4_1_0() { return cTypeRefsTypeRefParserRuleCall_4_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_5() { return cRightCurlyBracketKeyword_5; }
	}
	public class ParameterizedTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ParameterizedTypeRef");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cParameterizedTypeRefNominalParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cParameterizedTypeRefStructuralParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//ParameterizedTypeRef returns ParameterizedTypeRef:
		//    ParameterizedTypeRefNominal | ParameterizedTypeRefStructural;
		@Override public ParserRule getRule() { return rule; }
		
		//ParameterizedTypeRefNominal | ParameterizedTypeRefStructural
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getParameterizedTypeRefNominalParserRuleCall_0() { return cParameterizedTypeRefNominalParserRuleCall_0; }
		
		//ParameterizedTypeRefStructural
		public RuleCall getParameterizedTypeRefStructuralParserRuleCall_1() { return cParameterizedTypeRefStructuralParserRuleCall_1; }
	}
	public class ParameterizedTypeRefNominalElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ParameterizedTypeRefNominal");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cTypeReferenceParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final RuleCall cTypeArgumentsParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//ParameterizedTypeRefNominal returns ParameterizedTypeRef:
		//    TypeReference
		//    (-> TypeArguments)?
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//TypeReference
		//(-> TypeArguments)?
		public Group getGroup() { return cGroup; }
		
		//TypeReference
		public RuleCall getTypeReferenceParserRuleCall_0() { return cTypeReferenceParserRuleCall_0; }
		
		//(-> TypeArguments)?
		public RuleCall getTypeArgumentsParserRuleCall_1() { return cTypeArgumentsParserRuleCall_1; }
	}
	public class ParameterizedTypeRefStructuralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ParameterizedTypeRefStructural");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Action cParameterizedTypeRefStructuralAction_0_0 = (Action)cGroup_0.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Group cGroup_0_2 = (Group)cGroup_0.eContents().get(2);
		private final Assignment cAstStructuralMembersAssignment_0_2_0 = (Assignment)cGroup_0_2.eContents().get(0);
		private final RuleCall cAstStructuralMembersTStructMemberParserRuleCall_0_2_0_0 = (RuleCall)cAstStructuralMembersAssignment_0_2_0.eContents().get(0);
		private final Alternatives cAlternatives_0_2_1 = (Alternatives)cGroup_0_2.eContents().get(1);
		private final Keyword cSemicolonKeyword_0_2_1_0 = (Keyword)cAlternatives_0_2_1.eContents().get(0);
		private final Keyword cCommaKeyword_0_2_1_1 = (Keyword)cAlternatives_0_2_1.eContents().get(1);
		private final Keyword cRightCurlyBracketKeyword_0_3 = (Keyword)cGroup_0.eContents().get(3);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Assignment cDefinedTypingStrategyAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_1_0_0 = (RuleCall)cDefinedTypingStrategyAssignment_1_0.eContents().get(0);
		private final RuleCall cTypeReferenceParserRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		private final RuleCall cTypeArgumentsParserRuleCall_1_2 = (RuleCall)cGroup_1.eContents().get(2);
		private final Group cGroup_1_3 = (Group)cGroup_1.eContents().get(3);
		private final Keyword cWithKeyword_1_3_0 = (Keyword)cGroup_1_3.eContents().get(0);
		private final RuleCall cTStructMemberListParserRuleCall_1_3_1 = (RuleCall)cGroup_1_3.eContents().get(1);
		
		//ParameterizedTypeRefStructural returns ParameterizedTypeRefStructural:
		//    ( {ParameterizedTypeRefStructural} '{' (astStructuralMembers+=TStructMember (';'|',')?)*  '}' )
		//|    (
		//        definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
		//        (-> TypeArguments)?
		//        ('with' TStructMemberList)?
		//    )
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//    ( {ParameterizedTypeRefStructural} '{' (astStructuralMembers+=TStructMember (';'|',')?)*  '}' )
		//|    (
		//        definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
		//        (-> TypeArguments)?
		//        ('with' TStructMemberList)?
		//    )
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//( {ParameterizedTypeRefStructural} '{' (astStructuralMembers+=TStructMember (';'|',')?)*  '}' )
		public Group getGroup_0() { return cGroup_0; }
		
		//{ParameterizedTypeRefStructural}
		public Action getParameterizedTypeRefStructuralAction_0_0() { return cParameterizedTypeRefStructuralAction_0_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_0_1() { return cLeftCurlyBracketKeyword_0_1; }
		
		//(astStructuralMembers+=TStructMember (';'|',')?)*
		public Group getGroup_0_2() { return cGroup_0_2; }
		
		//astStructuralMembers+=TStructMember
		public Assignment getAstStructuralMembersAssignment_0_2_0() { return cAstStructuralMembersAssignment_0_2_0; }
		
		//TStructMember
		public RuleCall getAstStructuralMembersTStructMemberParserRuleCall_0_2_0_0() { return cAstStructuralMembersTStructMemberParserRuleCall_0_2_0_0; }
		
		//(';'|',')?
		public Alternatives getAlternatives_0_2_1() { return cAlternatives_0_2_1; }
		
		//';'
		public Keyword getSemicolonKeyword_0_2_1_0() { return cSemicolonKeyword_0_2_1_0; }
		
		//','
		public Keyword getCommaKeyword_0_2_1_1() { return cCommaKeyword_0_2_1_1; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_0_3() { return cRightCurlyBracketKeyword_0_3; }
		
		//(
		//    definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
		//    (-> TypeArguments)?
		//    ('with' TStructMemberList)?
		//)
		public Group getGroup_1() { return cGroup_1; }
		
		//definedTypingStrategy=TypingStrategyUseSiteOperator
		public Assignment getDefinedTypingStrategyAssignment_1_0() { return cDefinedTypingStrategyAssignment_1_0; }
		
		//TypingStrategyUseSiteOperator
		public RuleCall getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_1_0_0() { return cDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_1_0_0; }
		
		//TypeReference
		public RuleCall getTypeReferenceParserRuleCall_1_1() { return cTypeReferenceParserRuleCall_1_1; }
		
		//(-> TypeArguments)?
		public RuleCall getTypeArgumentsParserRuleCall_1_2() { return cTypeArgumentsParserRuleCall_1_2; }
		
		//('with' TStructMemberList)?
		public Group getGroup_1_3() { return cGroup_1_3; }
		
		//'with'
		public Keyword getWithKeyword_1_3_0() { return cWithKeyword_1_3_0; }
		
		//TStructMemberList
		public RuleCall getTStructMemberListParserRuleCall_1_3_1() { return cTStructMemberListParserRuleCall_1_3_1; }
	}
	public class MappedTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.MappedTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Keyword cPlusSignKeyword_1_0_0 = (Keyword)cGroup_1_0.eContents().get(0);
		private final Assignment cIncludeReadonlyAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final Keyword cIncludeReadonlyReadonlyKeyword_1_0_1_0 = (Keyword)cIncludeReadonlyAssignment_1_0_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Keyword cHyphenMinusKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cExcludeReadonlyAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final Keyword cExcludeReadonlyReadonlyKeyword_1_1_1_0 = (Keyword)cExcludeReadonlyAssignment_1_1_1.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cPropNameAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cPropNameIdentifierNameParserRuleCall_3_0 = (RuleCall)cPropNameAssignment_3.eContents().get(0);
		private final Keyword cInKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cPropNameTypeRefAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cPropNameTypeRefTypeRefParserRuleCall_5_0 = (RuleCall)cPropNameTypeRefAssignment_5.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Alternatives cAlternatives_7 = (Alternatives)cGroup.eContents().get(7);
		private final Group cGroup_7_0 = (Group)cAlternatives_7.eContents().get(0);
		private final Keyword cPlusSignKeyword_7_0_0 = (Keyword)cGroup_7_0.eContents().get(0);
		private final Assignment cIncludeOptionalAssignment_7_0_1 = (Assignment)cGroup_7_0.eContents().get(1);
		private final Keyword cIncludeOptionalQuestionMarkKeyword_7_0_1_0 = (Keyword)cIncludeOptionalAssignment_7_0_1.eContents().get(0);
		private final Group cGroup_7_1 = (Group)cAlternatives_7.eContents().get(1);
		private final Keyword cHyphenMinusKeyword_7_1_0 = (Keyword)cGroup_7_1.eContents().get(0);
		private final Assignment cExcludeOptionalAssignment_7_1_1 = (Assignment)cGroup_7_1.eContents().get(1);
		private final Keyword cExcludeOptionalQuestionMarkKeyword_7_1_1_0 = (Keyword)cExcludeOptionalAssignment_7_1_1.eContents().get(0);
		private final Group cGroup_8 = (Group)cGroup.eContents().get(8);
		private final Keyword cColonKeyword_8_0 = (Keyword)cGroup_8.eContents().get(0);
		private final Assignment cTemplateTypeRefAssignment_8_1 = (Assignment)cGroup_8.eContents().get(1);
		private final RuleCall cTemplateTypeRefTypeRefParserRuleCall_8_1_0 = (RuleCall)cTemplateTypeRefAssignment_8_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_9 = (Keyword)cGroup.eContents().get(9);
		private final Keyword cRightCurlyBracketKeyword_10 = (Keyword)cGroup.eContents().get(10);
		
		//MappedTypeRef:
		//    '{'
		//    ('+'? includeReadonly?='readonly' | '-' excludeReadonly?='readonly')?
		//    '[' propName=IdentifierName 'in' propNameTypeRef=TypeRef ']'
		//    ('+'? includeOptional?='?' | '-' excludeOptional?='?')?
		//    (':' templateTypeRef=TypeRef)? ';'?
		//    // FIXME consider allowing additional mapping signatures here, to be able to show better error messages
		//    // FIXME consider allowing TStructMemberList here, to be able to show better error messages
		//    '}'
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'{'
		//('+'? includeReadonly?='readonly' | '-' excludeReadonly?='readonly')?
		//'[' propName=IdentifierName 'in' propNameTypeRef=TypeRef ']'
		//('+'? includeOptional?='?' | '-' excludeOptional?='?')?
		//(':' templateTypeRef=TypeRef)? ';'?
		//// FIXME consider allowing additional mapping signatures here, to be able to show better error messages
		//// FIXME consider allowing TStructMemberList here, to be able to show better error messages
		//'}'
		public Group getGroup() { return cGroup; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_0() { return cLeftCurlyBracketKeyword_0; }
		
		//('+'? includeReadonly?='readonly' | '-' excludeReadonly?='readonly')?
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//'+'? includeReadonly?='readonly'
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//'+'?
		public Keyword getPlusSignKeyword_1_0_0() { return cPlusSignKeyword_1_0_0; }
		
		//includeReadonly?='readonly'
		public Assignment getIncludeReadonlyAssignment_1_0_1() { return cIncludeReadonlyAssignment_1_0_1; }
		
		//'readonly'
		public Keyword getIncludeReadonlyReadonlyKeyword_1_0_1_0() { return cIncludeReadonlyReadonlyKeyword_1_0_1_0; }
		
		//'-' excludeReadonly?='readonly'
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_1_1_0() { return cHyphenMinusKeyword_1_1_0; }
		
		//excludeReadonly?='readonly'
		public Assignment getExcludeReadonlyAssignment_1_1_1() { return cExcludeReadonlyAssignment_1_1_1; }
		
		//'readonly'
		public Keyword getExcludeReadonlyReadonlyKeyword_1_1_1_0() { return cExcludeReadonlyReadonlyKeyword_1_1_1_0; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_2() { return cLeftSquareBracketKeyword_2; }
		
		//propName=IdentifierName
		public Assignment getPropNameAssignment_3() { return cPropNameAssignment_3; }
		
		//IdentifierName
		public RuleCall getPropNameIdentifierNameParserRuleCall_3_0() { return cPropNameIdentifierNameParserRuleCall_3_0; }
		
		//'in'
		public Keyword getInKeyword_4() { return cInKeyword_4; }
		
		//propNameTypeRef=TypeRef
		public Assignment getPropNameTypeRefAssignment_5() { return cPropNameTypeRefAssignment_5; }
		
		//TypeRef
		public RuleCall getPropNameTypeRefTypeRefParserRuleCall_5_0() { return cPropNameTypeRefTypeRefParserRuleCall_5_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_6() { return cRightSquareBracketKeyword_6; }
		
		//('+'? includeOptional?='?' | '-' excludeOptional?='?')?
		public Alternatives getAlternatives_7() { return cAlternatives_7; }
		
		//'+'? includeOptional?='?'
		public Group getGroup_7_0() { return cGroup_7_0; }
		
		//'+'?
		public Keyword getPlusSignKeyword_7_0_0() { return cPlusSignKeyword_7_0_0; }
		
		//includeOptional?='?'
		public Assignment getIncludeOptionalAssignment_7_0_1() { return cIncludeOptionalAssignment_7_0_1; }
		
		//'?'
		public Keyword getIncludeOptionalQuestionMarkKeyword_7_0_1_0() { return cIncludeOptionalQuestionMarkKeyword_7_0_1_0; }
		
		//'-' excludeOptional?='?'
		public Group getGroup_7_1() { return cGroup_7_1; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_7_1_0() { return cHyphenMinusKeyword_7_1_0; }
		
		//excludeOptional?='?'
		public Assignment getExcludeOptionalAssignment_7_1_1() { return cExcludeOptionalAssignment_7_1_1; }
		
		//'?'
		public Keyword getExcludeOptionalQuestionMarkKeyword_7_1_1_0() { return cExcludeOptionalQuestionMarkKeyword_7_1_1_0; }
		
		//(':' templateTypeRef=TypeRef)?
		public Group getGroup_8() { return cGroup_8; }
		
		//':'
		public Keyword getColonKeyword_8_0() { return cColonKeyword_8_0; }
		
		//templateTypeRef=TypeRef
		public Assignment getTemplateTypeRefAssignment_8_1() { return cTemplateTypeRefAssignment_8_1; }
		
		//TypeRef
		public RuleCall getTemplateTypeRefTypeRefParserRuleCall_8_1_0() { return cTemplateTypeRefTypeRefParserRuleCall_8_1_0; }
		
		//';'?
		public Keyword getSemicolonKeyword_9() { return cSemicolonKeyword_9; }
		
		//// FIXME consider allowing additional mapping signatures here, to be able to show better error messages
		//// FIXME consider allowing TStructMemberList here, to be able to show better error messages
		//'}'
		public Keyword getRightCurlyBracketKeyword_10() { return cRightCurlyBracketKeyword_10; }
	}
	public class ArrayNTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ArrayNTypeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cArrayNTypeExpressionAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cArrayNTypeExpressionLeftSquareBracketKeyword_0_0 = (Keyword)cArrayNTypeExpressionAssignment_0.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cDeclaredTypeArgsAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final RuleCall cDeclaredTypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0 = (RuleCall)cDeclaredTypeArgsAssignment_1_0.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Assignment cDeclaredTypeArgsAssignment_1_1_0 = (Assignment)cGroup_1_1.eContents().get(0);
		private final RuleCall cDeclaredTypeArgsTypeArgumentParserRuleCall_1_1_0_0 = (RuleCall)cDeclaredTypeArgsAssignment_1_1_0.eContents().get(0);
		private final Group cGroup_1_1_1 = (Group)cGroup_1_1.eContents().get(1);
		private final Keyword cCommaKeyword_1_1_1_0 = (Keyword)cGroup_1_1_1.eContents().get(0);
		private final Assignment cDeclaredTypeArgsAssignment_1_1_1_1 = (Assignment)cGroup_1_1_1.eContents().get(1);
		private final RuleCall cDeclaredTypeArgsTypeArgumentParserRuleCall_1_1_1_1_0 = (RuleCall)cDeclaredTypeArgsAssignment_1_1_1_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1_1_2 = (Keyword)cGroup_1_1.eContents().get(2);
		
		//ArrayNTypeExpression returns ParameterizedTypeRef:
		//    arrayNTypeExpression?='['
		//    (
		//        declaredTypeArgs+=EmptyIterableTypeExpressionTail
		//    |    declaredTypeArgs+=TypeArgument (',' declaredTypeArgs+=TypeArgument)* ']'
		//    );
		@Override public ParserRule getRule() { return rule; }
		
		//arrayNTypeExpression?='['
		//(
		//    declaredTypeArgs+=EmptyIterableTypeExpressionTail
		//|    declaredTypeArgs+=TypeArgument (',' declaredTypeArgs+=TypeArgument)* ']'
		//)
		public Group getGroup() { return cGroup; }
		
		//arrayNTypeExpression?='['
		public Assignment getArrayNTypeExpressionAssignment_0() { return cArrayNTypeExpressionAssignment_0; }
		
		//'['
		public Keyword getArrayNTypeExpressionLeftSquareBracketKeyword_0_0() { return cArrayNTypeExpressionLeftSquareBracketKeyword_0_0; }
		
		//(
		//    declaredTypeArgs+=EmptyIterableTypeExpressionTail
		//|    declaredTypeArgs+=TypeArgument (',' declaredTypeArgs+=TypeArgument)* ']'
		//)
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//declaredTypeArgs+=EmptyIterableTypeExpressionTail
		public Assignment getDeclaredTypeArgsAssignment_1_0() { return cDeclaredTypeArgsAssignment_1_0; }
		
		//EmptyIterableTypeExpressionTail
		public RuleCall getDeclaredTypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0() { return cDeclaredTypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0; }
		
		//declaredTypeArgs+=TypeArgument (',' declaredTypeArgs+=TypeArgument)* ']'
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//declaredTypeArgs+=TypeArgument
		public Assignment getDeclaredTypeArgsAssignment_1_1_0() { return cDeclaredTypeArgsAssignment_1_1_0; }
		
		//TypeArgument
		public RuleCall getDeclaredTypeArgsTypeArgumentParserRuleCall_1_1_0_0() { return cDeclaredTypeArgsTypeArgumentParserRuleCall_1_1_0_0; }
		
		//(',' declaredTypeArgs+=TypeArgument)*
		public Group getGroup_1_1_1() { return cGroup_1_1_1; }
		
		//','
		public Keyword getCommaKeyword_1_1_1_0() { return cCommaKeyword_1_1_1_0; }
		
		//declaredTypeArgs+=TypeArgument
		public Assignment getDeclaredTypeArgsAssignment_1_1_1_1() { return cDeclaredTypeArgsAssignment_1_1_1_1; }
		
		//TypeArgument
		public RuleCall getDeclaredTypeArgsTypeArgumentParserRuleCall_1_1_1_1_0() { return cDeclaredTypeArgsTypeArgumentParserRuleCall_1_1_1_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1_1_2() { return cRightSquareBracketKeyword_1_1_2; }
	}
	public class EmptyIterableTypeExpressionTailElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.EmptyIterableTypeExpressionTail");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cWildcardAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//EmptyIterableTypeExpressionTail returns Wildcard:
		//    {Wildcard} ']';
		@Override public ParserRule getRule() { return rule; }
		
		//{Wildcard} ']'
		public Group getGroup() { return cGroup; }
		
		//{Wildcard}
		public Action getWildcardAction_0() { return cWildcardAction_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1() { return cRightSquareBracketKeyword_1; }
	}
	public class TypeReferenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeReference");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Assignment cAstNamespaceLikeRefsAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final RuleCall cAstNamespaceLikeRefsNamespaceLikeRefParserRuleCall_0_0_0 = (RuleCall)cAstNamespaceLikeRefsAssignment_0_0.eContents().get(0);
		private final Keyword cFullStopKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Assignment cDeclaredTypeAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final CrossReference cDeclaredTypeTypeCrossReference_1_0 = (CrossReference)cDeclaredTypeAssignment_1.eContents().get(0);
		private final RuleCall cDeclaredTypeTypeTypeReferenceNameParserRuleCall_1_0_1 = (RuleCall)cDeclaredTypeTypeCrossReference_1_0.eContents().get(1);
		
		//fragment TypeReference *:
		//    (astNamespaceLikeRefs+=NamespaceLikeRef '.')*
		//    => declaredType=[Type|TypeReferenceName]
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//(astNamespaceLikeRefs+=NamespaceLikeRef '.')*
		//=> declaredType=[Type|TypeReferenceName]
		public Group getGroup() { return cGroup; }
		
		//(astNamespaceLikeRefs+=NamespaceLikeRef '.')*
		public Group getGroup_0() { return cGroup_0; }
		
		//astNamespaceLikeRefs+=NamespaceLikeRef
		public Assignment getAstNamespaceLikeRefsAssignment_0_0() { return cAstNamespaceLikeRefsAssignment_0_0; }
		
		//NamespaceLikeRef
		public RuleCall getAstNamespaceLikeRefsNamespaceLikeRefParserRuleCall_0_0_0() { return cAstNamespaceLikeRefsNamespaceLikeRefParserRuleCall_0_0_0; }
		
		//'.'
		public Keyword getFullStopKeyword_0_1() { return cFullStopKeyword_0_1; }
		
		//=> declaredType=[Type|TypeReferenceName]
		public Assignment getDeclaredTypeAssignment_1() { return cDeclaredTypeAssignment_1; }
		
		//[Type|TypeReferenceName]
		public CrossReference getDeclaredTypeTypeCrossReference_1_0() { return cDeclaredTypeTypeCrossReference_1_0; }
		
		//TypeReferenceName
		public RuleCall getDeclaredTypeTypeTypeReferenceNameParserRuleCall_1_0_1() { return cDeclaredTypeTypeTypeReferenceNameParserRuleCall_1_0_1; }
	}
	public class NamespaceLikeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.NamespaceLikeRef");
		private final Assignment cDeclaredTypeAssignment = (Assignment)rule.eContents().get(1);
		private final CrossReference cDeclaredTypeTypeCrossReference_0 = (CrossReference)cDeclaredTypeAssignment.eContents().get(0);
		private final RuleCall cDeclaredTypeTypeTypeReferenceNameParserRuleCall_0_1 = (RuleCall)cDeclaredTypeTypeCrossReference_0.eContents().get(1);
		
		//NamespaceLikeRef:
		//    declaredType=[Type|TypeReferenceName]
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//declaredType=[Type|TypeReferenceName]
		public Assignment getDeclaredTypeAssignment() { return cDeclaredTypeAssignment; }
		
		//[Type|TypeReferenceName]
		public CrossReference getDeclaredTypeTypeCrossReference_0() { return cDeclaredTypeTypeCrossReference_0; }
		
		//TypeReferenceName
		public RuleCall getDeclaredTypeTypeTypeReferenceNameParserRuleCall_0_1() { return cDeclaredTypeTypeTypeReferenceNameParserRuleCall_0_1; }
	}
	public class TypeArgumentsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeArguments");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLessThanSignKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cDeclaredTypeArgsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cDeclaredTypeArgsTypeArgumentParserRuleCall_1_0 = (RuleCall)cDeclaredTypeArgsAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cCommaKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cDeclaredTypeArgsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cDeclaredTypeArgsTypeArgumentParserRuleCall_2_1_0 = (RuleCall)cDeclaredTypeArgsAssignment_2_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//fragment TypeArguments *:
		//    '<' declaredTypeArgs+=TypeArgument (',' declaredTypeArgs+=TypeArgument)* '>'
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'<' declaredTypeArgs+=TypeArgument (',' declaredTypeArgs+=TypeArgument)* '>'
		public Group getGroup() { return cGroup; }
		
		//'<'
		public Keyword getLessThanSignKeyword_0() { return cLessThanSignKeyword_0; }
		
		//declaredTypeArgs+=TypeArgument
		public Assignment getDeclaredTypeArgsAssignment_1() { return cDeclaredTypeArgsAssignment_1; }
		
		//TypeArgument
		public RuleCall getDeclaredTypeArgsTypeArgumentParserRuleCall_1_0() { return cDeclaredTypeArgsTypeArgumentParserRuleCall_1_0; }
		
		//(',' declaredTypeArgs+=TypeArgument)*
		public Group getGroup_2() { return cGroup_2; }
		
		//','
		public Keyword getCommaKeyword_2_0() { return cCommaKeyword_2_0; }
		
		//declaredTypeArgs+=TypeArgument
		public Assignment getDeclaredTypeArgsAssignment_2_1() { return cDeclaredTypeArgsAssignment_2_1; }
		
		//TypeArgument
		public RuleCall getDeclaredTypeArgsTypeArgumentParserRuleCall_2_1_0() { return cDeclaredTypeArgsTypeArgumentParserRuleCall_2_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3() { return cGreaterThanSignKeyword_3; }
	}
	public class TStructMemberListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TStructMemberList");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Assignment cAstStructuralMembersAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cAstStructuralMembersTStructMemberParserRuleCall_1_0_0 = (RuleCall)cAstStructuralMembersAssignment_1_0.eContents().get(0);
		private final Alternatives cAlternatives_1_1 = (Alternatives)cGroup_1.eContents().get(1);
		private final Keyword cSemicolonKeyword_1_1_0 = (Keyword)cAlternatives_1_1.eContents().get(0);
		private final Keyword cCommaKeyword_1_1_1 = (Keyword)cAlternatives_1_1.eContents().get(1);
		private final Keyword cRightCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//fragment TStructMemberList*:  '{' (astStructuralMembers+=TStructMember (';'|',')?)*  '}';
		@Override public ParserRule getRule() { return rule; }
		
		//'{' (astStructuralMembers+=TStructMember (';'|',')?)*  '}'
		public Group getGroup() { return cGroup; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_0() { return cLeftCurlyBracketKeyword_0; }
		
		//(astStructuralMembers+=TStructMember (';'|',')?)*
		public Group getGroup_1() { return cGroup_1; }
		
		//astStructuralMembers+=TStructMember
		public Assignment getAstStructuralMembersAssignment_1_0() { return cAstStructuralMembersAssignment_1_0; }
		
		//TStructMember
		public RuleCall getAstStructuralMembersTStructMemberParserRuleCall_1_0_0() { return cAstStructuralMembersTStructMemberParserRuleCall_1_0_0; }
		
		//(';'|',')?
		public Alternatives getAlternatives_1_1() { return cAlternatives_1_1; }
		
		//';'
		public Keyword getSemicolonKeyword_1_1_0() { return cSemicolonKeyword_1_1_0; }
		
		//','
		public Keyword getCommaKeyword_1_1_1() { return cCommaKeyword_1_1_1; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_2() { return cRightCurlyBracketKeyword_2; }
	}
	public class TStructMemberElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TStructMember");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cTStructGetterParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cTStructSetterParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cTStructMethodParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cTStructFieldParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cTStructIndexSignatureParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		
		///**
		// * All TMembers here are only used in ParameterizedTypeRefStructural references
		// * Most type references are optional.
		// */
		//TStructMember:
		//      TStructGetter
		//    | TStructSetter
		//    | TStructMethod
		//    | TStructField
		//    | TStructIndexSignature;
		@Override public ParserRule getRule() { return rule; }
		
		//  TStructGetter
		//| TStructSetter
		//| TStructMethod
		//| TStructField
		//| TStructIndexSignature
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//TStructGetter
		public RuleCall getTStructGetterParserRuleCall_0() { return cTStructGetterParserRuleCall_0; }
		
		//TStructSetter
		public RuleCall getTStructSetterParserRuleCall_1() { return cTStructSetterParserRuleCall_1; }
		
		//TStructMethod
		public RuleCall getTStructMethodParserRuleCall_2() { return cTStructMethodParserRuleCall_2; }
		
		//TStructField
		public RuleCall getTStructFieldParserRuleCall_3() { return cTStructFieldParserRuleCall_3; }
		
		//TStructIndexSignature
		public RuleCall getTStructIndexSignatureParserRuleCall_4() { return cTStructIndexSignatureParserRuleCall_4; }
	}
	public class TStructMethodElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TStructMethod");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cTStructMethodAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Group cGroup_0_0_1 = (Group)cGroup_0_0.eContents().get(1);
		private final Keyword cLessThanSignKeyword_0_0_1_0 = (Keyword)cGroup_0_0_1.eContents().get(0);
		private final Assignment cTypeVarsAssignment_0_0_1_1 = (Assignment)cGroup_0_0_1.eContents().get(1);
		private final RuleCall cTypeVarsTypeVariableParserRuleCall_0_0_1_1_0 = (RuleCall)cTypeVarsAssignment_0_0_1_1.eContents().get(0);
		private final Group cGroup_0_0_1_2 = (Group)cGroup_0_0_1.eContents().get(2);
		private final Keyword cCommaKeyword_0_0_1_2_0 = (Keyword)cGroup_0_0_1_2.eContents().get(0);
		private final Assignment cTypeVarsAssignment_0_0_1_2_1 = (Assignment)cGroup_0_0_1_2.eContents().get(1);
		private final RuleCall cTypeVarsTypeVariableParserRuleCall_0_0_1_2_1_0 = (RuleCall)cTypeVarsAssignment_0_0_1_2_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_0_0_1_3 = (Keyword)cGroup_0_0_1.eContents().get(3);
		private final Assignment cNameAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cNameIdentifierNameParserRuleCall_0_0_2_0 = (RuleCall)cNameAssignment_0_0_2.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_0_0_3 = (Keyword)cGroup_0_0.eContents().get(3);
		private final RuleCall cTAnonymousFormalParameterListParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		//TStructMethod:
		//    =>
		//    ({TStructMethod}
		//        ('<' typeVars+=TypeVariable (',' typeVars+=TypeVariable)* '>')?
		//        (name=IdentifierName)? '('
		//    )
		//    TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//=>
		//({TStructMethod}
		//    ('<' typeVars+=TypeVariable (',' typeVars+=TypeVariable)* '>')?
		//    (name=IdentifierName)? '('
		//)
		//TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?
		public Group getGroup() { return cGroup; }
		
		//=>
		//({TStructMethod}
		//    ('<' typeVars+=TypeVariable (',' typeVars+=TypeVariable)* '>')?
		//    (name=IdentifierName)? '('
		//)
		public Group getGroup_0() { return cGroup_0; }
		
		//{TStructMethod}
		//        ('<' typeVars+=TypeVariable (',' typeVars+=TypeVariable)* '>')?
		//        (name=IdentifierName)? '('
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{TStructMethod}
		public Action getTStructMethodAction_0_0_0() { return cTStructMethodAction_0_0_0; }
		
		//('<' typeVars+=TypeVariable (',' typeVars+=TypeVariable)* '>')?
		public Group getGroup_0_0_1() { return cGroup_0_0_1; }
		
		//'<'
		public Keyword getLessThanSignKeyword_0_0_1_0() { return cLessThanSignKeyword_0_0_1_0; }
		
		//typeVars+=TypeVariable
		public Assignment getTypeVarsAssignment_0_0_1_1() { return cTypeVarsAssignment_0_0_1_1; }
		
		//TypeVariable
		public RuleCall getTypeVarsTypeVariableParserRuleCall_0_0_1_1_0() { return cTypeVarsTypeVariableParserRuleCall_0_0_1_1_0; }
		
		//(',' typeVars+=TypeVariable)*
		public Group getGroup_0_0_1_2() { return cGroup_0_0_1_2; }
		
		//','
		public Keyword getCommaKeyword_0_0_1_2_0() { return cCommaKeyword_0_0_1_2_0; }
		
		//typeVars+=TypeVariable
		public Assignment getTypeVarsAssignment_0_0_1_2_1() { return cTypeVarsAssignment_0_0_1_2_1; }
		
		//TypeVariable
		public RuleCall getTypeVarsTypeVariableParserRuleCall_0_0_1_2_1_0() { return cTypeVarsTypeVariableParserRuleCall_0_0_1_2_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_0_0_1_3() { return cGreaterThanSignKeyword_0_0_1_3; }
		
		//(name=IdentifierName)?
		public Assignment getNameAssignment_0_0_2() { return cNameAssignment_0_0_2; }
		
		//IdentifierName
		public RuleCall getNameIdentifierNameParserRuleCall_0_0_2_0() { return cNameIdentifierNameParserRuleCall_0_0_2_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_0_0_3() { return cLeftParenthesisKeyword_0_0_3; }
		
		//TAnonymousFormalParameterList
		public RuleCall getTAnonymousFormalParameterListParserRuleCall_1() { return cTAnonymousFormalParameterListParserRuleCall_1; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2() { return cRightParenthesisKeyword_2; }
		
		//ColonSepReturnTypeRef?
		public RuleCall getColonSepReturnTypeRefParserRuleCall_3() { return cColonSepReturnTypeRefParserRuleCall_3; }
	}
	public class ColonSepTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ColonSepTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cColonKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cTypeRefAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTypeRefTypeRefParserRuleCall_1_0 = (RuleCall)cTypeRefAssignment_1.eContents().get(0);
		
		//fragment ColonSepTypeRef*:
		//    ':' typeRef=TypeRef
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//':' typeRef=TypeRef
		public Group getGroup() { return cGroup; }
		
		//':'
		public Keyword getColonKeyword_0() { return cColonKeyword_0; }
		
		//typeRef=TypeRef
		public Assignment getTypeRefAssignment_1() { return cTypeRefAssignment_1; }
		
		//TypeRef
		public RuleCall getTypeRefTypeRefParserRuleCall_1_0() { return cTypeRefTypeRefParserRuleCall_1_0; }
	}
	public class ColonSepReturnTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ColonSepReturnTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cColonKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cReturnTypePredicateAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final RuleCall cReturnTypePredicateTypePredicateParserRuleCall_1_0_0 = (RuleCall)cReturnTypePredicateAssignment_1_0.eContents().get(0);
		private final Assignment cReturnTypeRefAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final RuleCall cReturnTypeRefTypeRefParserRuleCall_1_1_0 = (RuleCall)cReturnTypeRefAssignment_1_1.eContents().get(0);
		
		//fragment ColonSepReturnTypeRef*:
		//    ':' (returnTypePredicate=TypePredicate | returnTypeRef=TypeRef)
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//':' (returnTypePredicate=TypePredicate | returnTypeRef=TypeRef)
		public Group getGroup() { return cGroup; }
		
		//':'
		public Keyword getColonKeyword_0() { return cColonKeyword_0; }
		
		//(returnTypePredicate=TypePredicate | returnTypeRef=TypeRef)
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//returnTypePredicate=TypePredicate
		public Assignment getReturnTypePredicateAssignment_1_0() { return cReturnTypePredicateAssignment_1_0; }
		
		//TypePredicate
		public RuleCall getReturnTypePredicateTypePredicateParserRuleCall_1_0_0() { return cReturnTypePredicateTypePredicateParserRuleCall_1_0_0; }
		
		//returnTypeRef=TypeRef
		public Assignment getReturnTypeRefAssignment_1_1() { return cReturnTypeRefAssignment_1_1; }
		
		//TypeRef
		public RuleCall getReturnTypeRefTypeRefParserRuleCall_1_1_0() { return cReturnTypeRefTypeRefParserRuleCall_1_1_0; }
	}
	public class TStructFieldElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TStructField");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cReadonlyKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIdentifierNameParserRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Assignment cOptionalAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cOptionalQuestionMarkKeyword_2_0 = (Keyword)cOptionalAssignment_2.eContents().get(0);
		private final RuleCall cColonSepTypeRefParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		//TStructField:
		//    'readonly'? name=IdentifierName (optional?='?')? ColonSepTypeRef?
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'readonly'? name=IdentifierName (optional?='?')? ColonSepTypeRef?
		public Group getGroup() { return cGroup; }
		
		//'readonly'?
		public Keyword getReadonlyKeyword_0() { return cReadonlyKeyword_0; }
		
		//name=IdentifierName
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//IdentifierName
		public RuleCall getNameIdentifierNameParserRuleCall_1_0() { return cNameIdentifierNameParserRuleCall_1_0; }
		
		//(optional?='?')?
		public Assignment getOptionalAssignment_2() { return cOptionalAssignment_2; }
		
		//'?'
		public Keyword getOptionalQuestionMarkKeyword_2_0() { return cOptionalQuestionMarkKeyword_2_0; }
		
		//ColonSepTypeRef?
		public RuleCall getColonSepTypeRefParserRuleCall_3() { return cColonSepTypeRefParserRuleCall_3; }
	}
	public class TStructGetterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TStructGetter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cTStructGetterAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cGetKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cNameAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cNameIdentifierNameParserRuleCall_0_0_2_0 = (RuleCall)cNameAssignment_0_0_2.eContents().get(0);
		private final Assignment cOptionalAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cOptionalQuestionMarkKeyword_1_0 = (Keyword)cOptionalAssignment_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final RuleCall cColonSepTypeRefParserRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		
		//TStructGetter:
		//    => ({TStructGetter}
		//    'get'
		//    name=IdentifierName)
		//    (optional?='?')?
		//    '(' ')' ColonSepTypeRef?
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({TStructGetter}
		//'get'
		//name=IdentifierName)
		//(optional?='?')?
		//'(' ')' ColonSepTypeRef?
		public Group getGroup() { return cGroup; }
		
		//=> ({TStructGetter}
		//'get'
		//name=IdentifierName)
		public Group getGroup_0() { return cGroup_0; }
		
		//{TStructGetter}
		//    'get'
		//    name=IdentifierName
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{TStructGetter}
		public Action getTStructGetterAction_0_0_0() { return cTStructGetterAction_0_0_0; }
		
		//'get'
		public Keyword getGetKeyword_0_0_1() { return cGetKeyword_0_0_1; }
		
		//name=IdentifierName
		public Assignment getNameAssignment_0_0_2() { return cNameAssignment_0_0_2; }
		
		//IdentifierName
		public RuleCall getNameIdentifierNameParserRuleCall_0_0_2_0() { return cNameIdentifierNameParserRuleCall_0_0_2_0; }
		
		//(optional?='?')?
		public Assignment getOptionalAssignment_1() { return cOptionalAssignment_1; }
		
		//'?'
		public Keyword getOptionalQuestionMarkKeyword_1_0() { return cOptionalQuestionMarkKeyword_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }
		
		//ColonSepTypeRef?
		public RuleCall getColonSepTypeRefParserRuleCall_4() { return cColonSepTypeRefParserRuleCall_4; }
	}
	public class TStructSetterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TStructSetter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cTStructSetterAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cSetKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cNameAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cNameIdentifierNameParserRuleCall_0_0_2_0 = (RuleCall)cNameAssignment_0_0_2.eContents().get(0);
		private final Assignment cOptionalAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cOptionalQuestionMarkKeyword_1_0 = (Keyword)cOptionalAssignment_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cFparAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cFparTAnonymousFormalParameterParserRuleCall_3_0 = (RuleCall)cFparAssignment_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//TStructSetter:
		//    => ({TStructSetter}
		//    'set'
		//    name=IdentifierName)
		//    (optional?='?')?
		//    '(' fpar=TAnonymousFormalParameter ')'
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({TStructSetter}
		//'set'
		//name=IdentifierName)
		//(optional?='?')?
		//'(' fpar=TAnonymousFormalParameter ')'
		public Group getGroup() { return cGroup; }
		
		//=> ({TStructSetter}
		//'set'
		//name=IdentifierName)
		public Group getGroup_0() { return cGroup_0; }
		
		//{TStructSetter}
		//    'set'
		//    name=IdentifierName
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{TStructSetter}
		public Action getTStructSetterAction_0_0_0() { return cTStructSetterAction_0_0_0; }
		
		//'set'
		public Keyword getSetKeyword_0_0_1() { return cSetKeyword_0_0_1; }
		
		//name=IdentifierName
		public Assignment getNameAssignment_0_0_2() { return cNameAssignment_0_0_2; }
		
		//IdentifierName
		public RuleCall getNameIdentifierNameParserRuleCall_0_0_2_0() { return cNameIdentifierNameParserRuleCall_0_0_2_0; }
		
		//(optional?='?')?
		public Assignment getOptionalAssignment_1() { return cOptionalAssignment_1; }
		
		//'?'
		public Keyword getOptionalQuestionMarkKeyword_1_0() { return cOptionalQuestionMarkKeyword_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }
		
		//fpar=TAnonymousFormalParameter
		public Assignment getFparAssignment_3() { return cFparAssignment_3; }
		
		//TAnonymousFormalParameter
		public RuleCall getFparTAnonymousFormalParameterParserRuleCall_3_0() { return cFparTAnonymousFormalParameterParserRuleCall_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_4() { return cRightParenthesisKeyword_4; }
	}
	public class TStructIndexSignatureElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TStructIndexSignature");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftSquareBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cKeyNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cKeyNameIdentifierNameParserRuleCall_1_0 = (RuleCall)cKeyNameAssignment_1.eContents().get(0);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cKeyTypeRefAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cKeyTypeRefTypeRefParserRuleCall_3_0 = (RuleCall)cKeyTypeRefAssignment_3.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Keyword cColonKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Assignment cValueTypeRefAssignment_6 = (Assignment)cGroup.eContents().get(6);
		private final RuleCall cValueTypeRefTypeRefParserRuleCall_6_0 = (RuleCall)cValueTypeRefAssignment_6.eContents().get(0);
		
		//TStructIndexSignature:
		//    '[' keyName=IdentifierName ':' keyTypeRef=TypeRef ']'
		//    ':'
		//    valueTypeRef=TypeRef
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'[' keyName=IdentifierName ':' keyTypeRef=TypeRef ']'
		//':'
		//valueTypeRef=TypeRef
		public Group getGroup() { return cGroup; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_0() { return cLeftSquareBracketKeyword_0; }
		
		//keyName=IdentifierName
		public Assignment getKeyNameAssignment_1() { return cKeyNameAssignment_1; }
		
		//IdentifierName
		public RuleCall getKeyNameIdentifierNameParserRuleCall_1_0() { return cKeyNameIdentifierNameParserRuleCall_1_0; }
		
		//':'
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//keyTypeRef=TypeRef
		public Assignment getKeyTypeRefAssignment_3() { return cKeyTypeRefAssignment_3; }
		
		//TypeRef
		public RuleCall getKeyTypeRefTypeRefParserRuleCall_3_0() { return cKeyTypeRefTypeRefParserRuleCall_3_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_4() { return cRightSquareBracketKeyword_4; }
		
		//':'
		public Keyword getColonKeyword_5() { return cColonKeyword_5; }
		
		//valueTypeRef=TypeRef
		public Assignment getValueTypeRefAssignment_6() { return cValueTypeRefAssignment_6; }
		
		//TypeRef
		public RuleCall getValueTypeRefTypeRefParserRuleCall_6_0() { return cValueTypeRefTypeRefParserRuleCall_6_0; }
	}
	public class TypingStrategyUseSiteOperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypingStrategyUseSiteOperator");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cTildeKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Keyword cTildeKeyword_1_0 = (Keyword)cAlternatives_1.eContents().get(0);
		private final RuleCall cSTRUCTMODSUFFIXTerminalRuleCall_1_1 = (RuleCall)cAlternatives_1.eContents().get(1);
		
		//TypingStrategyUseSiteOperator returns TypingStrategy:
		//    '~' ('~' | STRUCTMODSUFFIX)?;
		@Override public ParserRule getRule() { return rule; }
		
		//'~' ('~' | STRUCTMODSUFFIX)?
		public Group getGroup() { return cGroup; }
		
		//'~'
		public Keyword getTildeKeyword_0() { return cTildeKeyword_0; }
		
		//('~' | STRUCTMODSUFFIX)?
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//'~'
		public Keyword getTildeKeyword_1_0() { return cTildeKeyword_1_0; }
		
		//STRUCTMODSUFFIX
		public RuleCall getSTRUCTMODSUFFIXTerminalRuleCall_1_1() { return cSTRUCTMODSUFFIXTerminalRuleCall_1_1; }
	}
	public class TypingStrategyDefSiteOperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypingStrategyDefSiteOperator");
		private final Keyword cTildeKeyword = (Keyword)rule.eContents().get(1);
		
		//TypingStrategyDefSiteOperator returns TypingStrategy:
		//    '~';
		@Override public ParserRule getRule() { return rule; }
		
		//'~'
		public Keyword getTildeKeyword() { return cTildeKeyword; }
	}
	public class TypeTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cTypeTypeRefAction_0 = (Action)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Keyword cTypeKeyword_1_0 = (Keyword)cAlternatives_1.eContents().get(0);
		private final Assignment cConstructorRefAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final Keyword cConstructorRefConstructorKeyword_1_1_0 = (Keyword)cConstructorRefAssignment_1_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cTypeArgAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cTypeArgTypeArgInTypeTypeRefParserRuleCall_3_0 = (RuleCall)cTypeArgAssignment_3.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//TypeTypeRef returns TypeTypeRef:
		//    {TypeTypeRef}
		//    ('type' | constructorRef?='constructor')
		//    '{' typeArg=TypeArgInTypeTypeRef '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{TypeTypeRef}
		//('type' | constructorRef?='constructor')
		//'{' typeArg=TypeArgInTypeTypeRef '}'
		public Group getGroup() { return cGroup; }
		
		//{TypeTypeRef}
		public Action getTypeTypeRefAction_0() { return cTypeTypeRefAction_0; }
		
		//('type' | constructorRef?='constructor')
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//'type'
		public Keyword getTypeKeyword_1_0() { return cTypeKeyword_1_0; }
		
		//constructorRef?='constructor'
		public Assignment getConstructorRefAssignment_1_1() { return cConstructorRefAssignment_1_1; }
		
		//'constructor'
		public Keyword getConstructorRefConstructorKeyword_1_1_0() { return cConstructorRefConstructorKeyword_1_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//typeArg=TypeArgInTypeTypeRef
		public Assignment getTypeArgAssignment_3() { return cTypeArgAssignment_3; }
		
		//TypeArgInTypeTypeRef
		public RuleCall getTypeArgTypeArgInTypeTypeRefParserRuleCall_3_0() { return cTypeArgTypeArgInTypeTypeRefParserRuleCall_3_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class TypeReferenceNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeReferenceName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cVoidKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cThisKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cAwaitKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cPromisifyKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cTargetKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cDefaultKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final RuleCall cIDENTIFIERTerminalRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		
		//TypeReferenceName:
		//    'void' | 'This' | 'await' | 'Promisify' | 'target' | 'default' | IDENTIFIER
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'void' | 'This' | 'await' | 'Promisify' | 'target' | 'default' | IDENTIFIER
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'void'
		public Keyword getVoidKeyword_0() { return cVoidKeyword_0; }
		
		//'This'
		public Keyword getThisKeyword_1() { return cThisKeyword_1; }
		
		//'await'
		public Keyword getAwaitKeyword_2() { return cAwaitKeyword_2; }
		
		//'Promisify'
		public Keyword getPromisifyKeyword_3() { return cPromisifyKeyword_3; }
		
		//'target'
		public Keyword getTargetKeyword_4() { return cTargetKeyword_4; }
		
		//'default'
		public Keyword getDefaultKeyword_5() { return cDefaultKeyword_5; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_6() { return cIDENTIFIERTerminalRuleCall_6; }
	}
	public class TypeArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeArgument");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cWildcardParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cTypeRefParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//TypeArgument returns TypeArgument:
		//    Wildcard | TypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//Wildcard | TypeRef
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Wildcard
		public RuleCall getWildcardParserRuleCall_0() { return cWildcardParserRuleCall_0; }
		
		//TypeRef
		public RuleCall getTypeRefParserRuleCall_1() { return cTypeRefParserRuleCall_1; }
	}
	public class WildcardElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.Wildcard");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cWildcardOldNotationParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cWildcardNewNotationParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//Wildcard:
		//    WildcardOldNotation
		//|    WildcardNewNotation;
		@Override public ParserRule getRule() { return rule; }
		
		//    WildcardOldNotation
		//|    WildcardNewNotation
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//WildcardOldNotation
		public RuleCall getWildcardOldNotationParserRuleCall_0() { return cWildcardOldNotationParserRuleCall_0; }
		
		//WildcardNewNotation
		public RuleCall getWildcardNewNotationParserRuleCall_1() { return cWildcardNewNotationParserRuleCall_1; }
	}
	public class WildcardOldNotationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.WildcardOldNotation");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cWildcardAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cQuestionMarkKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Keyword cExtendsKeyword_1_0_0 = (Keyword)cGroup_1_0.eContents().get(0);
		private final Assignment cDeclaredUpperBoundAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cDeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0 = (RuleCall)cDeclaredUpperBoundAssignment_1_0_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Keyword cSuperKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cDeclaredLowerBoundAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cDeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0 = (RuleCall)cDeclaredLowerBoundAssignment_1_1_1.eContents().get(0);
		
		//WildcardOldNotation returns Wildcard:
		//    => ({Wildcard} '?') (('extends' declaredUpperBound=TypeRef) | ('super'
		//    declaredLowerBound=TypeRef))?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({Wildcard} '?') (('extends' declaredUpperBound=TypeRef) | ('super'
		//declaredLowerBound=TypeRef))?
		public Group getGroup() { return cGroup; }
		
		//=> ({Wildcard} '?')
		public Group getGroup_0() { return cGroup_0; }
		
		//{Wildcard} '?'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{Wildcard}
		public Action getWildcardAction_0_0_0() { return cWildcardAction_0_0_0; }
		
		//'?'
		public Keyword getQuestionMarkKeyword_0_0_1() { return cQuestionMarkKeyword_0_0_1; }
		
		//(('extends' declaredUpperBound=TypeRef) | ('super'
		//   declaredLowerBound=TypeRef))?
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//('extends' declaredUpperBound=TypeRef)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//'extends'
		public Keyword getExtendsKeyword_1_0_0() { return cExtendsKeyword_1_0_0; }
		
		//declaredUpperBound=TypeRef
		public Assignment getDeclaredUpperBoundAssignment_1_0_1() { return cDeclaredUpperBoundAssignment_1_0_1; }
		
		//TypeRef
		public RuleCall getDeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0() { return cDeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0; }
		
		//('super'
		//   declaredLowerBound=TypeRef)
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//'super'
		public Keyword getSuperKeyword_1_1_0() { return cSuperKeyword_1_1_0; }
		
		//declaredLowerBound=TypeRef
		public Assignment getDeclaredLowerBoundAssignment_1_1_1() { return cDeclaredLowerBoundAssignment_1_1_1; }
		
		//TypeRef
		public RuleCall getDeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0() { return cDeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0; }
	}
	public class WildcardOldNotationWithoutBoundElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.WildcardOldNotationWithoutBound");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cWildcardAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cQuestionMarkKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//WildcardOldNotationWithoutBound returns Wildcard:
		//    {Wildcard} '?';
		@Override public ParserRule getRule() { return rule; }
		
		//{Wildcard} '?'
		public Group getGroup() { return cGroup; }
		
		//{Wildcard}
		public Action getWildcardAction_0() { return cWildcardAction_0; }
		
		//'?'
		public Keyword getQuestionMarkKeyword_1() { return cQuestionMarkKeyword_1; }
	}
	public class WildcardNewNotationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.WildcardNewNotation");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Assignment cUsingInOutNotationAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final Keyword cUsingInOutNotationOutKeyword_0_0_0 = (Keyword)cUsingInOutNotationAssignment_0_0.eContents().get(0);
		private final Assignment cDeclaredUpperBoundAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
		private final RuleCall cDeclaredUpperBoundTypeRefParserRuleCall_0_1_0 = (RuleCall)cDeclaredUpperBoundAssignment_0_1.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Assignment cUsingInOutNotationAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final Keyword cUsingInOutNotationInKeyword_1_0_0 = (Keyword)cUsingInOutNotationAssignment_1_0.eContents().get(0);
		private final Assignment cDeclaredLowerBoundAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cDeclaredLowerBoundTypeRefParserRuleCall_1_1_0 = (RuleCall)cDeclaredLowerBoundAssignment_1_1.eContents().get(0);
		
		//WildcardNewNotation returns Wildcard:
		//    (usingInOutNotation?='out' declaredUpperBound=TypeRef)
		//|    (usingInOutNotation?='in' declaredLowerBound=TypeRef);
		@Override public ParserRule getRule() { return rule; }
		
		//    (usingInOutNotation?='out' declaredUpperBound=TypeRef)
		//|    (usingInOutNotation?='in' declaredLowerBound=TypeRef)
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//(usingInOutNotation?='out' declaredUpperBound=TypeRef)
		public Group getGroup_0() { return cGroup_0; }
		
		//usingInOutNotation?='out'
		public Assignment getUsingInOutNotationAssignment_0_0() { return cUsingInOutNotationAssignment_0_0; }
		
		//'out'
		public Keyword getUsingInOutNotationOutKeyword_0_0_0() { return cUsingInOutNotationOutKeyword_0_0_0; }
		
		//declaredUpperBound=TypeRef
		public Assignment getDeclaredUpperBoundAssignment_0_1() { return cDeclaredUpperBoundAssignment_0_1; }
		
		//TypeRef
		public RuleCall getDeclaredUpperBoundTypeRefParserRuleCall_0_1_0() { return cDeclaredUpperBoundTypeRefParserRuleCall_0_1_0; }
		
		//(usingInOutNotation?='in' declaredLowerBound=TypeRef)
		public Group getGroup_1() { return cGroup_1; }
		
		//usingInOutNotation?='in'
		public Assignment getUsingInOutNotationAssignment_1_0() { return cUsingInOutNotationAssignment_1_0; }
		
		//'in'
		public Keyword getUsingInOutNotationInKeyword_1_0_0() { return cUsingInOutNotationInKeyword_1_0_0; }
		
		//declaredLowerBound=TypeRef
		public Assignment getDeclaredLowerBoundAssignment_1_1() { return cDeclaredLowerBoundAssignment_1_1; }
		
		//TypeRef
		public RuleCall getDeclaredLowerBoundTypeRefParserRuleCall_1_1_0() { return cDeclaredLowerBoundTypeRefParserRuleCall_1_1_0; }
	}
	public class TypeVariableElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeVariable");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Assignment cDeclaredCovariantAssignment_0_0 = (Assignment)cAlternatives_0.eContents().get(0);
		private final Keyword cDeclaredCovariantOutKeyword_0_0_0 = (Keyword)cDeclaredCovariantAssignment_0_0.eContents().get(0);
		private final Assignment cDeclaredContravariantAssignment_0_1 = (Assignment)cAlternatives_0.eContents().get(1);
		private final Keyword cDeclaredContravariantInKeyword_0_1_0 = (Keyword)cDeclaredContravariantAssignment_0_1.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDENTIFIERTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cExtendsKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cDeclaredUpperBoundAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cDeclaredUpperBoundTypeRefParserRuleCall_2_1_0 = (RuleCall)cDeclaredUpperBoundAssignment_2_1.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cEqualsSignKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cDefaultArgumentAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cDefaultArgumentTypeRefParserRuleCall_3_1_0 = (RuleCall)cDefaultArgumentAssignment_3_1.eContents().get(0);
		
		//TypeVariable returns TypeVariable:
		//    (declaredCovariant?='out' | declaredContravariant?='in')?
		//    name=IDENTIFIER ('extends' declaredUpperBound=TypeRef)?
		//    // the following is disallowed by ASTStructureValidator for all uses of this grammar rule
		//    // (only added here to obtain a better error message)
		//    ('=' defaultArgument=TypeRef)?;
		@Override public ParserRule getRule() { return rule; }
		
		//(declaredCovariant?='out' | declaredContravariant?='in')?
		//name=IDENTIFIER ('extends' declaredUpperBound=TypeRef)?
		//// the following is disallowed by ASTStructureValidator for all uses of this grammar rule
		//// (only added here to obtain a better error message)
		//('=' defaultArgument=TypeRef)?
		public Group getGroup() { return cGroup; }
		
		//(declaredCovariant?='out' | declaredContravariant?='in')?
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//declaredCovariant?='out'
		public Assignment getDeclaredCovariantAssignment_0_0() { return cDeclaredCovariantAssignment_0_0; }
		
		//'out'
		public Keyword getDeclaredCovariantOutKeyword_0_0_0() { return cDeclaredCovariantOutKeyword_0_0_0; }
		
		//declaredContravariant?='in'
		public Assignment getDeclaredContravariantAssignment_0_1() { return cDeclaredContravariantAssignment_0_1; }
		
		//'in'
		public Keyword getDeclaredContravariantInKeyword_0_1_0() { return cDeclaredContravariantInKeyword_0_1_0; }
		
		//name=IDENTIFIER
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//IDENTIFIER
		public RuleCall getNameIDENTIFIERTerminalRuleCall_1_0() { return cNameIDENTIFIERTerminalRuleCall_1_0; }
		
		//('extends' declaredUpperBound=TypeRef)?
		public Group getGroup_2() { return cGroup_2; }
		
		//'extends'
		public Keyword getExtendsKeyword_2_0() { return cExtendsKeyword_2_0; }
		
		//declaredUpperBound=TypeRef
		public Assignment getDeclaredUpperBoundAssignment_2_1() { return cDeclaredUpperBoundAssignment_2_1; }
		
		//TypeRef
		public RuleCall getDeclaredUpperBoundTypeRefParserRuleCall_2_1_0() { return cDeclaredUpperBoundTypeRefParserRuleCall_2_1_0; }
		
		//// the following is disallowed by ASTStructureValidator for all uses of this grammar rule
		//// (only added here to obtain a better error message)
		//('=' defaultArgument=TypeRef)?
		public Group getGroup_3() { return cGroup_3; }
		
		//'='
		public Keyword getEqualsSignKeyword_3_0() { return cEqualsSignKeyword_3_0; }
		
		//defaultArgument=TypeRef
		public Assignment getDefaultArgumentAssignment_3_1() { return cDefaultArgumentAssignment_3_1; }
		
		//TypeRef
		public RuleCall getDefaultArgumentTypeRefParserRuleCall_3_1_0() { return cDefaultArgumentTypeRefParserRuleCall_3_1_0; }
	}
	public class QueryTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.QueryTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cTypeofKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cElementAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final CrossReference cElementIdentifiableElementCrossReference_1_0 = (CrossReference)cElementAssignment_1.eContents().get(0);
		private final RuleCall cElementIdentifiableElementIdentifierNameParserRuleCall_1_0_1 = (RuleCall)cElementIdentifiableElementCrossReference_1_0.eContents().get(1);
		
		//QueryTypeRef:
		//    'typeof' element=[IdentifiableElement|IdentifierName]
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'typeof' element=[IdentifiableElement|IdentifierName]
		public Group getGroup() { return cGroup; }
		
		//'typeof'
		public Keyword getTypeofKeyword_0() { return cTypeofKeyword_0; }
		
		//element=[IdentifiableElement|IdentifierName]
		public Assignment getElementAssignment_1() { return cElementAssignment_1; }
		
		//[IdentifiableElement|IdentifierName]
		public CrossReference getElementIdentifiableElementCrossReference_1_0() { return cElementIdentifiableElementCrossReference_1_0; }
		
		//IdentifierName
		public RuleCall getElementIdentifiableElementIdentifierNameParserRuleCall_1_0_1() { return cElementIdentifiableElementIdentifierNameParserRuleCall_1_0_1; }
	}
	public class InferTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.InferTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cInferKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cTypeVarNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTypeVarNameIDENTIFIERTerminalRuleCall_1_0 = (RuleCall)cTypeVarNameAssignment_1.eContents().get(0);
		
		//InferTypeRef:
		//    'infer' typeVarName=IDENTIFIER
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'infer' typeVarName=IDENTIFIER
		public Group getGroup() { return cGroup; }
		
		//'infer'
		public Keyword getInferKeyword_0() { return cInferKeyword_0; }
		
		//typeVarName=IDENTIFIER
		public Assignment getTypeVarNameAssignment_1() { return cTypeVarNameAssignment_1; }
		
		//IDENTIFIER
		public RuleCall getTypeVarNameIDENTIFIERTerminalRuleCall_1_0() { return cTypeVarNameIDENTIFIERTerminalRuleCall_1_0; }
	}
	public class TypePredicateElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypePredicate");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Alternatives cAlternatives_0_0_0 = (Alternatives)cGroup_0_0.eContents().get(0);
		private final Assignment cReferringToThisAssignment_0_0_0_0 = (Assignment)cAlternatives_0_0_0.eContents().get(0);
		private final Keyword cReferringToThisThisKeyword_0_0_0_0_0 = (Keyword)cReferringToThisAssignment_0_0_0_0.eContents().get(0);
		private final Assignment cFparAssignment_0_0_0_1 = (Assignment)cAlternatives_0_0_0.eContents().get(1);
		private final CrossReference cFparIdentifiableElementCrossReference_0_0_0_1_0 = (CrossReference)cFparAssignment_0_0_0_1.eContents().get(0);
		private final RuleCall cFparIdentifiableElementBindingIdentifierParserRuleCall_0_0_0_1_0_1 = (RuleCall)cFparIdentifiableElementCrossReference_0_0_0_1_0.eContents().get(1);
		private final Keyword cIsKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cTypeRefAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTypeRefTypeRefParserRuleCall_1_0 = (RuleCall)cTypeRefAssignment_1.eContents().get(0);
		
		//TypePredicate:
		//    =>((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is') typeRef=TypeRef
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//=>((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is') typeRef=TypeRef
		public Group getGroup() { return cGroup; }
		
		//=>((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is')
		public Group getGroup_0() { return cGroup_0; }
		
		//(referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//(referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>])
		public Alternatives getAlternatives_0_0_0() { return cAlternatives_0_0_0; }
		
		//referringToThis?='this'
		public Assignment getReferringToThisAssignment_0_0_0_0() { return cReferringToThisAssignment_0_0_0_0; }
		
		//'this'
		public Keyword getReferringToThisThisKeyword_0_0_0_0_0() { return cReferringToThisThisKeyword_0_0_0_0_0; }
		
		//fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]
		public Assignment getFparAssignment_0_0_0_1() { return cFparAssignment_0_0_0_1; }
		
		//[IdentifiableElement|BindingIdentifier<Yield=false>]
		public CrossReference getFparIdentifiableElementCrossReference_0_0_0_1_0() { return cFparIdentifiableElementCrossReference_0_0_0_1_0; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getFparIdentifiableElementBindingIdentifierParserRuleCall_0_0_0_1_0_1() { return cFparIdentifiableElementBindingIdentifierParserRuleCall_0_0_0_1_0_1; }
		
		//'is'
		public Keyword getIsKeyword_0_0_1() { return cIsKeyword_0_0_1; }
		
		//typeRef=TypeRef
		public Assignment getTypeRefAssignment_1() { return cTypeRefAssignment_1; }
		
		//TypeRef
		public RuleCall getTypeRefTypeRefParserRuleCall_1_0() { return cTypeRefTypeRefParserRuleCall_1_0; }
	}
	public class TypePredicateWithPrimaryElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypePredicateWithPrimary");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Alternatives cAlternatives_0_0_0 = (Alternatives)cGroup_0_0.eContents().get(0);
		private final Assignment cReferringToThisAssignment_0_0_0_0 = (Assignment)cAlternatives_0_0_0.eContents().get(0);
		private final Keyword cReferringToThisThisKeyword_0_0_0_0_0 = (Keyword)cReferringToThisAssignment_0_0_0_0.eContents().get(0);
		private final Assignment cFparAssignment_0_0_0_1 = (Assignment)cAlternatives_0_0_0.eContents().get(1);
		private final CrossReference cFparIdentifiableElementCrossReference_0_0_0_1_0 = (CrossReference)cFparAssignment_0_0_0_1.eContents().get(0);
		private final RuleCall cFparIdentifiableElementBindingIdentifierParserRuleCall_0_0_0_1_0_1 = (RuleCall)cFparIdentifiableElementCrossReference_0_0_0_1_0.eContents().get(1);
		private final Keyword cIsKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cTypeRefAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTypeRefPrimaryTypeExpressionParserRuleCall_1_0 = (RuleCall)cTypeRefAssignment_1.eContents().get(0);
		
		//TypePredicateWithPrimary returns TypePredicate:
		//    =>((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is') typeRef=PrimaryTypeExpression
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//=>((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is') typeRef=PrimaryTypeExpression
		public Group getGroup() { return cGroup; }
		
		//=>((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is')
		public Group getGroup_0() { return cGroup_0; }
		
		//(referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//(referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>])
		public Alternatives getAlternatives_0_0_0() { return cAlternatives_0_0_0; }
		
		//referringToThis?='this'
		public Assignment getReferringToThisAssignment_0_0_0_0() { return cReferringToThisAssignment_0_0_0_0; }
		
		//'this'
		public Keyword getReferringToThisThisKeyword_0_0_0_0_0() { return cReferringToThisThisKeyword_0_0_0_0_0; }
		
		//fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]
		public Assignment getFparAssignment_0_0_0_1() { return cFparAssignment_0_0_0_1; }
		
		//[IdentifiableElement|BindingIdentifier<Yield=false>]
		public CrossReference getFparIdentifiableElementCrossReference_0_0_0_1_0() { return cFparIdentifiableElementCrossReference_0_0_0_1_0; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getFparIdentifiableElementBindingIdentifierParserRuleCall_0_0_0_1_0_1() { return cFparIdentifiableElementBindingIdentifierParserRuleCall_0_0_0_1_0_1; }
		
		//'is'
		public Keyword getIsKeyword_0_0_1() { return cIsKeyword_0_0_1; }
		
		//typeRef=PrimaryTypeExpression
		public Assignment getTypeRefAssignment_1() { return cTypeRefAssignment_1; }
		
		//PrimaryTypeExpression
		public RuleCall getTypeRefPrimaryTypeExpressionParserRuleCall_1_0() { return cTypeRefPrimaryTypeExpressionParserRuleCall_1_0; }
	}
	public class BindingIdentifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.BindingIdentifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cIDENTIFIERTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Keyword cYieldKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final RuleCall cN4KeywordParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		///*
		// * [ECM11] (7.6, pp. 17)
		// * Identifier :: IdentifierName but not ReservedWord
		// * ReservedWord :: Keyword | FutureReservedWord | NullLiteral | BooleanLiteral
		// */
		//BindingIdentifier <Yield>:
		//    IDENTIFIER
		//    // yield as identifier as of [ECM15] (11.6.2, pp. 165)
		//    | <!Yield> 'yield'
		//    | N4Keyword
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//IDENTIFIER
		//// yield as identifier as of [ECM15] (11.6.2, pp. 165)
		//| <!Yield> 'yield'
		//| N4Keyword
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_0() { return cIDENTIFIERTerminalRuleCall_0; }
		
		//<!Yield> 'yield'
		public Group getGroup_1() { return cGroup_1; }
		
		//'yield'
		public Keyword getYieldKeyword_1_0() { return cYieldKeyword_1_0; }
		
		//N4Keyword
		public RuleCall getN4KeywordParserRuleCall_2() { return cN4KeywordParserRuleCall_2; }
	}
	public class IdentifierNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.IdentifierName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cIDENTIFIERTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cReservedWordParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cN4KeywordParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//IdentifierName:
		//    IDENTIFIER | ReservedWord | N4Keyword
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//IDENTIFIER | ReservedWord | N4Keyword
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_0() { return cIDENTIFIERTerminalRuleCall_0; }
		
		//ReservedWord
		public RuleCall getReservedWordParserRuleCall_1() { return cReservedWordParserRuleCall_1; }
		
		//N4Keyword
		public RuleCall getN4KeywordParserRuleCall_2() { return cN4KeywordParserRuleCall_2; }
	}
	public class ReservedWordElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ReservedWord");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cBreakKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cCaseKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cCatchKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cClassKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cConstKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cContinueKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final Keyword cDebuggerKeyword_6 = (Keyword)cAlternatives.eContents().get(6);
		private final Keyword cDefaultKeyword_7 = (Keyword)cAlternatives.eContents().get(7);
		private final Keyword cDeleteKeyword_8 = (Keyword)cAlternatives.eContents().get(8);
		private final Keyword cDoKeyword_9 = (Keyword)cAlternatives.eContents().get(9);
		private final Keyword cElseKeyword_10 = (Keyword)cAlternatives.eContents().get(10);
		private final Keyword cExportKeyword_11 = (Keyword)cAlternatives.eContents().get(11);
		private final Keyword cExtendsKeyword_12 = (Keyword)cAlternatives.eContents().get(12);
		private final Keyword cFinallyKeyword_13 = (Keyword)cAlternatives.eContents().get(13);
		private final Keyword cForKeyword_14 = (Keyword)cAlternatives.eContents().get(14);
		private final Keyword cFunctionKeyword_15 = (Keyword)cAlternatives.eContents().get(15);
		private final Keyword cIfKeyword_16 = (Keyword)cAlternatives.eContents().get(16);
		private final Keyword cImportKeyword_17 = (Keyword)cAlternatives.eContents().get(17);
		private final Keyword cInKeyword_18 = (Keyword)cAlternatives.eContents().get(18);
		private final Keyword cInstanceofKeyword_19 = (Keyword)cAlternatives.eContents().get(19);
		private final Keyword cNewKeyword_20 = (Keyword)cAlternatives.eContents().get(20);
		private final Keyword cReturnKeyword_21 = (Keyword)cAlternatives.eContents().get(21);
		private final Keyword cSuperKeyword_22 = (Keyword)cAlternatives.eContents().get(22);
		private final Keyword cSwitchKeyword_23 = (Keyword)cAlternatives.eContents().get(23);
		private final Keyword cThisKeyword_24 = (Keyword)cAlternatives.eContents().get(24);
		private final Keyword cThrowKeyword_25 = (Keyword)cAlternatives.eContents().get(25);
		private final Keyword cTryKeyword_26 = (Keyword)cAlternatives.eContents().get(26);
		private final Keyword cTypeofKeyword_27 = (Keyword)cAlternatives.eContents().get(27);
		private final Keyword cVarKeyword_28 = (Keyword)cAlternatives.eContents().get(28);
		private final Keyword cVoidKeyword_29 = (Keyword)cAlternatives.eContents().get(29);
		private final Keyword cWhileKeyword_30 = (Keyword)cAlternatives.eContents().get(30);
		private final Keyword cWithKeyword_31 = (Keyword)cAlternatives.eContents().get(31);
		private final Keyword cYieldKeyword_32 = (Keyword)cAlternatives.eContents().get(32);
		private final Keyword cNullKeyword_33 = (Keyword)cAlternatives.eContents().get(33);
		private final Keyword cTrueKeyword_34 = (Keyword)cAlternatives.eContents().get(34);
		private final Keyword cFalseKeyword_35 = (Keyword)cAlternatives.eContents().get(35);
		private final Keyword cEnumKeyword_36 = (Keyword)cAlternatives.eContents().get(36);
		
		//ReservedWord:
		//    // Keywords as of [ECM15] (11.6.2, pp. 165)
		//    'break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete'
		//    | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import'
		//    | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try'
		//    | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield'
		//    // null literal
		//    | 'null'
		//    // boolean literal
		//    | 'true' | 'false'
		//    // Future Reserved Word as of [ECM15] (11.6.2.2, pp. 166)
		//    // | 'await' /* reserved word only if parse goal is module - compromise: allow as identifier and validate */
		//    | 'enum';
		@Override public ParserRule getRule() { return rule; }
		
		//// Keywords as of [ECM15] (11.6.2, pp. 165)
		//'break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete'
		//| 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import'
		//| 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try'
		//| 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield'
		//// null literal
		//| 'null'
		//// boolean literal
		//| 'true' | 'false'
		//// Future Reserved Word as of [ECM15] (11.6.2.2, pp. 166)
		//// | 'await' /* reserved word only if parse goal is module - compromise: allow as identifier and validate */
		//| 'enum'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//// Keywords as of [ECM15] (11.6.2, pp. 165)
		//'break'
		public Keyword getBreakKeyword_0() { return cBreakKeyword_0; }
		
		//'case'
		public Keyword getCaseKeyword_1() { return cCaseKeyword_1; }
		
		//'catch'
		public Keyword getCatchKeyword_2() { return cCatchKeyword_2; }
		
		//'class'
		public Keyword getClassKeyword_3() { return cClassKeyword_3; }
		
		//'const'
		public Keyword getConstKeyword_4() { return cConstKeyword_4; }
		
		//'continue'
		public Keyword getContinueKeyword_5() { return cContinueKeyword_5; }
		
		//'debugger'
		public Keyword getDebuggerKeyword_6() { return cDebuggerKeyword_6; }
		
		//'default'
		public Keyword getDefaultKeyword_7() { return cDefaultKeyword_7; }
		
		//'delete'
		public Keyword getDeleteKeyword_8() { return cDeleteKeyword_8; }
		
		//'do'
		public Keyword getDoKeyword_9() { return cDoKeyword_9; }
		
		//'else'
		public Keyword getElseKeyword_10() { return cElseKeyword_10; }
		
		//'export'
		public Keyword getExportKeyword_11() { return cExportKeyword_11; }
		
		//'extends'
		public Keyword getExtendsKeyword_12() { return cExtendsKeyword_12; }
		
		//'finally'
		public Keyword getFinallyKeyword_13() { return cFinallyKeyword_13; }
		
		//'for'
		public Keyword getForKeyword_14() { return cForKeyword_14; }
		
		//'function'
		public Keyword getFunctionKeyword_15() { return cFunctionKeyword_15; }
		
		//'if'
		public Keyword getIfKeyword_16() { return cIfKeyword_16; }
		
		//'import'
		public Keyword getImportKeyword_17() { return cImportKeyword_17; }
		
		//'in'
		public Keyword getInKeyword_18() { return cInKeyword_18; }
		
		//'instanceof'
		public Keyword getInstanceofKeyword_19() { return cInstanceofKeyword_19; }
		
		//'new'
		public Keyword getNewKeyword_20() { return cNewKeyword_20; }
		
		//'return'
		public Keyword getReturnKeyword_21() { return cReturnKeyword_21; }
		
		//'super'
		public Keyword getSuperKeyword_22() { return cSuperKeyword_22; }
		
		//'switch'
		public Keyword getSwitchKeyword_23() { return cSwitchKeyword_23; }
		
		//'this'
		public Keyword getThisKeyword_24() { return cThisKeyword_24; }
		
		//'throw'
		public Keyword getThrowKeyword_25() { return cThrowKeyword_25; }
		
		//'try'
		public Keyword getTryKeyword_26() { return cTryKeyword_26; }
		
		//'typeof'
		public Keyword getTypeofKeyword_27() { return cTypeofKeyword_27; }
		
		//'var'
		public Keyword getVarKeyword_28() { return cVarKeyword_28; }
		
		//'void'
		public Keyword getVoidKeyword_29() { return cVoidKeyword_29; }
		
		//'while'
		public Keyword getWhileKeyword_30() { return cWhileKeyword_30; }
		
		//'with'
		public Keyword getWithKeyword_31() { return cWithKeyword_31; }
		
		//'yield'
		public Keyword getYieldKeyword_32() { return cYieldKeyword_32; }
		
		//'null'
		public Keyword getNullKeyword_33() { return cNullKeyword_33; }
		
		//'true'
		public Keyword getTrueKeyword_34() { return cTrueKeyword_34; }
		
		//'false'
		public Keyword getFalseKeyword_35() { return cFalseKeyword_35; }
		
		//'enum'
		public Keyword getEnumKeyword_36() { return cEnumKeyword_36; }
	}
	public class N4KeywordElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.N4Keyword");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cGetKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cSetKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cLetKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cProjectKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cExternalKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cAbstractKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final Keyword cStaticKeyword_6 = (Keyword)cAlternatives.eContents().get(6);
		private final Keyword cAsKeyword_7 = (Keyword)cAlternatives.eContents().get(7);
		private final Keyword cFromKeyword_8 = (Keyword)cAlternatives.eContents().get(8);
		private final Keyword cConstructorKeyword_9 = (Keyword)cAlternatives.eContents().get(9);
		private final Keyword cOfKeyword_10 = (Keyword)cAlternatives.eContents().get(10);
		private final Keyword cTargetKeyword_11 = (Keyword)cAlternatives.eContents().get(11);
		private final Keyword cTypeKeyword_12 = (Keyword)cAlternatives.eContents().get(12);
		private final Keyword cUnionKeyword_13 = (Keyword)cAlternatives.eContents().get(13);
		private final Keyword cIntersectionKeyword_14 = (Keyword)cAlternatives.eContents().get(14);
<<<<<<< HEAD
		private final Keyword cThisKeyword_15 = (Keyword)cAlternatives.eContents().get(15);
		private final Keyword cPromisifyKeyword_16 = (Keyword)cAlternatives.eContents().get(16);
		private final Keyword cAwaitKeyword_17 = (Keyword)cAlternatives.eContents().get(17);
		private final Keyword cAsyncKeyword_18 = (Keyword)cAlternatives.eContents().get(18);
		private final Keyword cImplementsKeyword_19 = (Keyword)cAlternatives.eContents().get(19);
		private final Keyword cInterfaceKeyword_20 = (Keyword)cAlternatives.eContents().get(20);
		private final Keyword cPrivateKeyword_21 = (Keyword)cAlternatives.eContents().get(21);
		private final Keyword cProtectedKeyword_22 = (Keyword)cAlternatives.eContents().get(22);
		private final Keyword cPublicKeyword_23 = (Keyword)cAlternatives.eContents().get(23);
		private final Keyword cOutKeyword_24 = (Keyword)cAlternatives.eContents().get(24);
		private final Keyword cNamespaceKeyword_25 = (Keyword)cAlternatives.eContents().get(25);
=======
		private final Keyword cPromisifyKeyword_15 = (Keyword)cAlternatives.eContents().get(15);
		private final Keyword cAwaitKeyword_16 = (Keyword)cAlternatives.eContents().get(16);
		private final Keyword cAsyncKeyword_17 = (Keyword)cAlternatives.eContents().get(17);
		private final Keyword cImplementsKeyword_18 = (Keyword)cAlternatives.eContents().get(18);
		private final Keyword cInterfaceKeyword_19 = (Keyword)cAlternatives.eContents().get(19);
		private final Keyword cPrivateKeyword_20 = (Keyword)cAlternatives.eContents().get(20);
		private final Keyword cProtectedKeyword_21 = (Keyword)cAlternatives.eContents().get(21);
		private final Keyword cPublicKeyword_22 = (Keyword)cAlternatives.eContents().get(22);
		private final Keyword cOutKeyword_23 = (Keyword)cAlternatives.eContents().get(23);
<<<<<<< HEAD
>>>>>>> 8b8567bc8 (early support for a few first constructs)
=======
		private final Keyword cDeclareKeyword_24 = (Keyword)cAlternatives.eContents().get(24);
		private final Keyword cIsKeyword_25 = (Keyword)cAlternatives.eContents().get(25);
		private final Keyword cKeyofKeyword_26 = (Keyword)cAlternatives.eContents().get(26);
		private final Keyword cUniqueKeyword_27 = (Keyword)cAlternatives.eContents().get(27);
<<<<<<< HEAD
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
=======
		private final Keyword cInferKeyword_28 = (Keyword)cAlternatives.eContents().get(28);
>>>>>>> c3d10a43c (early support for infer declarations)
		
		//N4Keyword:
		//    'get' | 'set'
		//    | 'let'
		//    | 'project'
		//    | 'external' | 'abstract' | 'static'
		//    | 'as' | 'from' | 'constructor' | 'of' | 'target'
		//    | 'type' | 'union' | 'intersection'
		//    | 'Promisify'
		//    // future reserved keyword in [ECM15] only in modules, we add additional validation
		//    | 'await'
		//    // async is not a reserved keyword, i.e. it can be used as a variable name
		//    | 'async'
		//    // future reserved keywords in [ECM15], restricted via static semantic in [ECM15]
		//    | 'implements' | 'interface'
		//    | 'private' | 'protected' | 'public' // package not used in N4JS
		//    // definition-site variance
		//    | 'out'
<<<<<<< HEAD
		//    // namespace keyword
		//    | 'namespace'
=======
		//    // .d.ts keywords
<<<<<<< HEAD
		//    | 'declare' | 'is' | 'keyof' | 'unique' // | 'readonly'
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
=======
		//    | 'declare' | 'is' | 'keyof' | 'unique' | 'infer' // | 'readonly'
>>>>>>> c3d10a43c (early support for infer declarations)
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'get' | 'set'
		//| 'let'
		//| 'project'
		//| 'external' | 'abstract' | 'static'
		//| 'as' | 'from' | 'constructor' | 'of' | 'target'
		//| 'type' | 'union' | 'intersection'
		//| 'Promisify'
		//// future reserved keyword in [ECM15] only in modules, we add additional validation
		//| 'await'
		//// async is not a reserved keyword, i.e. it can be used as a variable name
		//| 'async'
		//// future reserved keywords in [ECM15], restricted via static semantic in [ECM15]
		//| 'implements' | 'interface'
		//| 'private' | 'protected' | 'public' // package not used in N4JS
		//// definition-site variance
		//| 'out'
<<<<<<< HEAD
		//// namespace keyword
		//| 'namespace'
=======
		//// .d.ts keywords
<<<<<<< HEAD
		//| 'declare' | 'is' | 'keyof' | 'unique'
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
=======
		//| 'declare' | 'is' | 'keyof' | 'unique' | 'infer'
>>>>>>> c3d10a43c (early support for infer declarations)
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'get'
		public Keyword getGetKeyword_0() { return cGetKeyword_0; }
		
		//'set'
		public Keyword getSetKeyword_1() { return cSetKeyword_1; }
		
		//'let'
		public Keyword getLetKeyword_2() { return cLetKeyword_2; }
		
		//'project'
		public Keyword getProjectKeyword_3() { return cProjectKeyword_3; }
		
		//'external'
		public Keyword getExternalKeyword_4() { return cExternalKeyword_4; }
		
		//'abstract'
		public Keyword getAbstractKeyword_5() { return cAbstractKeyword_5; }
		
		//'static'
		public Keyword getStaticKeyword_6() { return cStaticKeyword_6; }
		
		//'as'
		public Keyword getAsKeyword_7() { return cAsKeyword_7; }
		
		//'from'
		public Keyword getFromKeyword_8() { return cFromKeyword_8; }
		
		//'constructor'
		public Keyword getConstructorKeyword_9() { return cConstructorKeyword_9; }
		
		//'of'
		public Keyword getOfKeyword_10() { return cOfKeyword_10; }
		
		//'target'
		public Keyword getTargetKeyword_11() { return cTargetKeyword_11; }
		
		//'type'
		public Keyword getTypeKeyword_12() { return cTypeKeyword_12; }
		
		//'union'
		public Keyword getUnionKeyword_13() { return cUnionKeyword_13; }
		
		//'intersection'
		public Keyword getIntersectionKeyword_14() { return cIntersectionKeyword_14; }
		
		//'Promisify'
		public Keyword getPromisifyKeyword_15() { return cPromisifyKeyword_15; }
		
		//'await'
		public Keyword getAwaitKeyword_16() { return cAwaitKeyword_16; }
		
		//'async'
		public Keyword getAsyncKeyword_17() { return cAsyncKeyword_17; }
		
		//'implements'
		public Keyword getImplementsKeyword_18() { return cImplementsKeyword_18; }
		
		//'interface'
		public Keyword getInterfaceKeyword_19() { return cInterfaceKeyword_19; }
		
		//'private'
		public Keyword getPrivateKeyword_20() { return cPrivateKeyword_20; }
		
		//'protected'
		public Keyword getProtectedKeyword_21() { return cProtectedKeyword_21; }
		
		//'public'
		public Keyword getPublicKeyword_22() { return cPublicKeyword_22; }
		
		//'out'
<<<<<<< HEAD
		public Keyword getOutKeyword_24() { return cOutKeyword_24; }
		
		//'namespace'
		public Keyword getNamespaceKeyword_25() { return cNamespaceKeyword_25; }
=======
		public Keyword getOutKeyword_23() { return cOutKeyword_23; }
<<<<<<< HEAD
>>>>>>> 8b8567bc8 (early support for a few first constructs)
=======
		
		//'declare'
		public Keyword getDeclareKeyword_24() { return cDeclareKeyword_24; }
		
		//'is'
		public Keyword getIsKeyword_25() { return cIsKeyword_25; }
		
		//'keyof'
		public Keyword getKeyofKeyword_26() { return cKeyofKeyword_26; }
		
		//'unique'
		public Keyword getUniqueKeyword_27() { return cUniqueKeyword_27; }
<<<<<<< HEAD
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
=======
		
		//'infer'
		public Keyword getInferKeyword_28() { return cInferKeyword_28; }
>>>>>>> c3d10a43c (early support for infer declarations)
	}
	public class ArrowElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.Arrow");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cEqualsSignKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//Arrow hidden(): // "hidden()" works due to LazyTokenStream#doSetHiddenTokens()
		//    '=' '>'
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//// "hidden()" works due to LazyTokenStream#doSetHiddenTokens()
		//   '=' '>'
		public Group getGroup() { return cGroup; }
		
		//// "hidden()" works due to LazyTokenStream#doSetHiddenTokens()
		//   '='
		public Keyword getEqualsSignKeyword_0() { return cEqualsSignKeyword_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_1() { return cGreaterThanSignKeyword_1; }
	}
	
	public class TypeOperatorElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.TypeOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cKeyofEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cKeyofKeyofKeyword_0_0 = (Keyword)cKeyofEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cUniqueEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cUniqueUniqueKeyword_1_0 = (Keyword)cUniqueEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cReadonlyEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cReadonlyReadonlyKeyword_2_0 = (Keyword)cReadonlyEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum TypeOperator: keyof | unique | readonly;
		public EnumRule getRule() { return rule; }
		
		//keyof | unique | readonly
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//keyof
		public EnumLiteralDeclaration getKeyofEnumLiteralDeclaration_0() { return cKeyofEnumLiteralDeclaration_0; }
		
		public Keyword getKeyofKeyofKeyword_0_0() { return cKeyofKeyofKeyword_0_0; }
		
		//unique
		public EnumLiteralDeclaration getUniqueEnumLiteralDeclaration_1() { return cUniqueEnumLiteralDeclaration_1; }
		
		public Keyword getUniqueUniqueKeyword_1_0() { return cUniqueUniqueKeyword_1_0; }
		
		//readonly
		public EnumLiteralDeclaration getReadonlyEnumLiteralDeclaration_2() { return cReadonlyEnumLiteralDeclaration_2; }
		
		public Keyword getReadonlyReadonlyKeyword_2_0() { return cReadonlyReadonlyKeyword_2_0; }
	}
	
	private final TypeRefElements pTypeRef;
	private final ConditionalTypeRefElements pConditionalTypeRef;
	private final UnionTypeExpressionElements pUnionTypeExpression;
	private final IntersectionTypeExpressionElements pIntersectionTypeExpression;
	private final OperatorTypeRefElements pOperatorTypeRef;
	private final TypeOperatorElements eTypeOperator;
	private final ArrayTypeExpressionElements pArrayTypeExpression;
	private final PrimaryTypeExpressionElements pPrimaryTypeExpression;
	private final TypeRefWithModifiersElements pTypeRefWithModifiers;
	private final TypeArgInTypeTypeRefElements pTypeArgInTypeTypeRef;
	private final LiteralTypeRefElements pLiteralTypeRef;
	private final BooleanLiteralTypeRefElements pBooleanLiteralTypeRef;
	private final NumericLiteralTypeRefElements pNumericLiteralTypeRef;
	private final StringLiteralTypeRefElements pStringLiteralTypeRef;
	private final ThisTypeRefElements pThisTypeRef;
	private final ThisTypeRefNominalElements pThisTypeRefNominal;
	private final ThisTypeRefStructuralElements pThisTypeRefStructural;
	private final ArrowFunctionTypeExpressionElements pArrowFunctionTypeExpression;
	private final TAnonymousFormalParameterListElements pTAnonymousFormalParameterList;
	private final TAnonymousFormalParameterListWithDeclaredThisTypeElements pTAnonymousFormalParameterListWithDeclaredThisType;
	private final TAnonymousFormalParameterElements pTAnonymousFormalParameter;
	private final TFormalParameterElements pTFormalParameter;
	private final DefaultFormalParameterElements pDefaultFormalParameter;
	private final UnionTypeExpressionOLDElements pUnionTypeExpressionOLD;
	private final IntersectionTypeExpressionOLDElements pIntersectionTypeExpressionOLD;
	private final ParameterizedTypeRefElements pParameterizedTypeRef;
	private final ParameterizedTypeRefNominalElements pParameterizedTypeRefNominal;
	private final ParameterizedTypeRefStructuralElements pParameterizedTypeRefStructural;
	private final MappedTypeRefElements pMappedTypeRef;
	private final ArrayNTypeExpressionElements pArrayNTypeExpression;
	private final EmptyIterableTypeExpressionTailElements pEmptyIterableTypeExpressionTail;
	private final TypeReferenceElements pTypeReference;
	private final NamespaceLikeRefElements pNamespaceLikeRef;
	private final TypeArgumentsElements pTypeArguments;
	private final TStructMemberListElements pTStructMemberList;
	private final TStructMemberElements pTStructMember;
	private final TStructMethodElements pTStructMethod;
	private final ColonSepTypeRefElements pColonSepTypeRef;
	private final ColonSepReturnTypeRefElements pColonSepReturnTypeRef;
	private final TStructFieldElements pTStructField;
	private final TStructGetterElements pTStructGetter;
	private final TStructSetterElements pTStructSetter;
	private final TStructIndexSignatureElements pTStructIndexSignature;
	private final TypingStrategyUseSiteOperatorElements pTypingStrategyUseSiteOperator;
	private final TypingStrategyDefSiteOperatorElements pTypingStrategyDefSiteOperator;
	private final TerminalRule tSTRUCTMODSUFFIX;
	private final TypeTypeRefElements pTypeTypeRef;
	private final TypeReferenceNameElements pTypeReferenceName;
	private final TypeArgumentElements pTypeArgument;
	private final WildcardElements pWildcard;
	private final WildcardOldNotationElements pWildcardOldNotation;
	private final WildcardOldNotationWithoutBoundElements pWildcardOldNotationWithoutBound;
	private final WildcardNewNotationElements pWildcardNewNotation;
	private final TypeVariableElements pTypeVariable;
	private final QueryTypeRefElements pQueryTypeRef;
	private final InferTypeRefElements pInferTypeRef;
	private final TypePredicateElements pTypePredicate;
	private final TypePredicateWithPrimaryElements pTypePredicateWithPrimary;
	private final BindingIdentifierElements pBindingIdentifier;
	private final IdentifierNameElements pIdentifierName;
	private final ReservedWordElements pReservedWord;
	private final N4KeywordElements pN4Keyword;
	private final ArrowElements pArrow;
	private final TerminalRule tIDENTIFIER;
	private final TerminalRule tINT;
	private final TerminalRule tDOUBLE;
	private final TerminalRule tBINARY_INT;
	private final TerminalRule tOCTAL_INT;
	private final TerminalRule tLEGACY_OCTAL_INT;
	private final TerminalRule tHEX_INT;
	private final TerminalRule tINT_SUFFIX;
	private final TerminalRule tSCIENTIFIC_INT;
	private final TerminalRule tEXPONENT_PART;
	private final TerminalRule tSIGNED_INT;
	private final TerminalRule tSTRING;
	private final TerminalRule tDOUBLE_STRING_CHAR;
	private final TerminalRule tSINGLE_STRING_CHAR;
	private final TerminalRule tML_COMMENT;
	private final TerminalRule tSL_COMMENT;
	private final TerminalRule tEOL;
	private final TerminalRule tWS;
	private final TerminalRule tUNICODE_ESCAPE_FRAGMENT;
	private final TerminalRule tIDENTIFIER_START;
	private final TerminalRule tIDENTIFIER_PART;
	private final TerminalRule tDOT_DOT;
	
	private final Grammar grammar;
	
	private final UnicodeGrammarAccess gaUnicode;

	@Inject
	public TypeExpressionsGrammarAccess(GrammarProvider grammarProvider,
			UnicodeGrammarAccess gaUnicode) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaUnicode = gaUnicode;
		this.pTypeRef = new TypeRefElements();
		this.pConditionalTypeRef = new ConditionalTypeRefElements();
		this.pUnionTypeExpression = new UnionTypeExpressionElements();
		this.pIntersectionTypeExpression = new IntersectionTypeExpressionElements();
		this.pOperatorTypeRef = new OperatorTypeRefElements();
		this.eTypeOperator = new TypeOperatorElements();
		this.pArrayTypeExpression = new ArrayTypeExpressionElements();
		this.pPrimaryTypeExpression = new PrimaryTypeExpressionElements();
		this.pTypeRefWithModifiers = new TypeRefWithModifiersElements();
		this.pTypeArgInTypeTypeRef = new TypeArgInTypeTypeRefElements();
		this.pLiteralTypeRef = new LiteralTypeRefElements();
		this.pBooleanLiteralTypeRef = new BooleanLiteralTypeRefElements();
		this.pNumericLiteralTypeRef = new NumericLiteralTypeRefElements();
		this.pStringLiteralTypeRef = new StringLiteralTypeRefElements();
		this.pThisTypeRef = new ThisTypeRefElements();
		this.pThisTypeRefNominal = new ThisTypeRefNominalElements();
		this.pThisTypeRefStructural = new ThisTypeRefStructuralElements();
		this.pArrowFunctionTypeExpression = new ArrowFunctionTypeExpressionElements();
		this.pTAnonymousFormalParameterList = new TAnonymousFormalParameterListElements();
		this.pTAnonymousFormalParameterListWithDeclaredThisType = new TAnonymousFormalParameterListWithDeclaredThisTypeElements();
		this.pTAnonymousFormalParameter = new TAnonymousFormalParameterElements();
		this.pTFormalParameter = new TFormalParameterElements();
		this.pDefaultFormalParameter = new DefaultFormalParameterElements();
		this.pUnionTypeExpressionOLD = new UnionTypeExpressionOLDElements();
		this.pIntersectionTypeExpressionOLD = new IntersectionTypeExpressionOLDElements();
		this.pParameterizedTypeRef = new ParameterizedTypeRefElements();
		this.pParameterizedTypeRefNominal = new ParameterizedTypeRefNominalElements();
		this.pParameterizedTypeRefStructural = new ParameterizedTypeRefStructuralElements();
		this.pMappedTypeRef = new MappedTypeRefElements();
		this.pArrayNTypeExpression = new ArrayNTypeExpressionElements();
		this.pEmptyIterableTypeExpressionTail = new EmptyIterableTypeExpressionTailElements();
		this.pTypeReference = new TypeReferenceElements();
		this.pNamespaceLikeRef = new NamespaceLikeRefElements();
		this.pTypeArguments = new TypeArgumentsElements();
		this.pTStructMemberList = new TStructMemberListElements();
		this.pTStructMember = new TStructMemberElements();
		this.pTStructMethod = new TStructMethodElements();
		this.pColonSepTypeRef = new ColonSepTypeRefElements();
		this.pColonSepReturnTypeRef = new ColonSepReturnTypeRefElements();
		this.pTStructField = new TStructFieldElements();
		this.pTStructGetter = new TStructGetterElements();
		this.pTStructSetter = new TStructSetterElements();
		this.pTStructIndexSignature = new TStructIndexSignatureElements();
		this.pTypingStrategyUseSiteOperator = new TypingStrategyUseSiteOperatorElements();
		this.pTypingStrategyDefSiteOperator = new TypingStrategyDefSiteOperatorElements();
		this.tSTRUCTMODSUFFIX = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.STRUCTMODSUFFIX");
		this.pTypeTypeRef = new TypeTypeRefElements();
		this.pTypeReferenceName = new TypeReferenceNameElements();
		this.pTypeArgument = new TypeArgumentElements();
		this.pWildcard = new WildcardElements();
		this.pWildcardOldNotation = new WildcardOldNotationElements();
		this.pWildcardOldNotationWithoutBound = new WildcardOldNotationWithoutBoundElements();
		this.pWildcardNewNotation = new WildcardNewNotationElements();
		this.pTypeVariable = new TypeVariableElements();
		this.pQueryTypeRef = new QueryTypeRefElements();
		this.pInferTypeRef = new InferTypeRefElements();
		this.pTypePredicate = new TypePredicateElements();
		this.pTypePredicateWithPrimary = new TypePredicateWithPrimaryElements();
		this.pBindingIdentifier = new BindingIdentifierElements();
		this.pIdentifierName = new IdentifierNameElements();
		this.pReservedWord = new ReservedWordElements();
		this.pN4Keyword = new N4KeywordElements();
		this.pArrow = new ArrowElements();
		this.tIDENTIFIER = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.IDENTIFIER");
		this.tINT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.INT");
		this.tDOUBLE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.DOUBLE");
		this.tBINARY_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.BINARY_INT");
		this.tOCTAL_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.OCTAL_INT");
		this.tLEGACY_OCTAL_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.LEGACY_OCTAL_INT");
		this.tHEX_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.HEX_INT");
		this.tINT_SUFFIX = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.INT_SUFFIX");
		this.tSCIENTIFIC_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.SCIENTIFIC_INT");
		this.tEXPONENT_PART = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.EXPONENT_PART");
		this.tSIGNED_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.SIGNED_INT");
		this.tSTRING = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.STRING");
		this.tDOUBLE_STRING_CHAR = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.DOUBLE_STRING_CHAR");
		this.tSINGLE_STRING_CHAR = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.SINGLE_STRING_CHAR");
		this.tML_COMMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.ML_COMMENT");
		this.tSL_COMMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.SL_COMMENT");
		this.tEOL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.EOL");
		this.tWS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.WS");
		this.tUNICODE_ESCAPE_FRAGMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.UNICODE_ESCAPE_FRAGMENT");
		this.tIDENTIFIER_START = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.IDENTIFIER_START");
		this.tIDENTIFIER_PART = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.IDENTIFIER_PART");
		this.tDOT_DOT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.TypeExpressions.DOT_DOT");
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.TypeExpressions".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public UnicodeGrammarAccess getUnicodeGrammarAccess() {
		return gaUnicode;
	}

	
	//// ****************************************************************************************************
	//// N4JS versions of type references and expressions, also used by Types.xtext
	////
	//// References:
	////
	//// [ECM15]    ECMAScript 2015 Language Specification / ISO/IEC (ECMA-262, 6th Edition).
	////            International Standard.
	////            http://www.ecma-international.org/publications/ files/ECMA-ST/Ecma-262.pdf
	////
	//// ****************************************************************************************************
	///*
	// * cf. N4JSSec §4
	// * Depending on where the type references is used, not all possible variants are allowed.
	// * This is however checked by the validator in order to
	// * 1) provide better error messages
	// * 2) simplify grammar
	// *
	// * Constraints:
	// * UnionElementTypeRef: no AnyType, no Void, union itself must not be dynamic
	// * FParTypeRef: no Void
	// * ReturnTypeRef: everything, but no dynamic
	// * // in N4JS:
	// * VarTypeRef: no Void, i.e.
	// * AttributeTypeRef: no Void
	// */
	//TypeRef: ConditionalTypeRef;
	public TypeRefElements getTypeRefAccess() {
		return pTypeRef;
	}
	
	public ParserRule getTypeRefRule() {
		return getTypeRefAccess().getRule();
	}
	
	//ConditionalTypeRef returns TypeRef:
	//    UnionTypeExpression (=> ({ConditionalTypeRef.typeRef=current} 'extends') upperBound=UnionTypeExpression '?' trueTypeRef=ConditionalTypeRef ':' falseTypeRef=ConditionalTypeRef)?;
	public ConditionalTypeRefElements getConditionalTypeRefAccess() {
		return pConditionalTypeRef;
	}
	
	public ParserRule getConditionalTypeRefRule() {
		return getConditionalTypeRefAccess().getRule();
	}
	
	//UnionTypeExpression returns TypeRef:
	//    IntersectionTypeExpression ({UnionTypeExpression.typeRefs+=current} ("|" typeRefs+=IntersectionTypeExpression)+)?;
	public UnionTypeExpressionElements getUnionTypeExpressionAccess() {
		return pUnionTypeExpression;
	}
	
	public ParserRule getUnionTypeExpressionRule() {
		return getUnionTypeExpressionAccess().getRule();
	}
	
	//IntersectionTypeExpression returns TypeRef:
	//    OperatorTypeRef ({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=OperatorTypeRef)+)?;
	public IntersectionTypeExpressionElements getIntersectionTypeExpressionAccess() {
		return pIntersectionTypeExpression;
	}
	
	public ParserRule getIntersectionTypeExpressionRule() {
		return getIntersectionTypeExpressionAccess().getRule();
	}
	
	//OperatorTypeRef returns TypeRef:
	//      ({OperatorTypeRef} op=TypeOperator typeRef=ArrayTypeExpression)
	//    | ArrayTypeExpression;
	public OperatorTypeRefElements getOperatorTypeRefAccess() {
		return pOperatorTypeRef;
	}
	
	public ParserRule getOperatorTypeRefRule() {
		return getOperatorTypeRefAccess().getRule();
	}
	
	//enum TypeOperator: keyof | unique | readonly;
	public TypeOperatorElements getTypeOperatorAccess() {
		return eTypeOperator;
	}
	
	public EnumRule getTypeOperatorRule() {
		return getTypeOperatorAccess().getRule();
	}
	
	//ArrayTypeExpression returns TypeRef:
	//      ({ParameterizedTypeRef} declaredTypeArgs+=WildcardOldNotationWithoutBound arrayTypeExpression?='[' ']' =>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*)
	//    | ({ParameterizedTypeRef} '(' declaredTypeArgs+=Wildcard ')' arrayTypeExpression?='[' ']' =>({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')*)
	//    | ArrayNTypeExpression
	//    | PrimaryTypeExpression =>(
	//          ({ParameterizedTypeRef.declaredTypeArgs+=current} arrayTypeExpression?='[' ']')
	//        | ({IndexAccessTypeRef.targetTypeRef=current} '[' indexTypeRef=PrimaryTypeExpression ']')
	//    )*;
	public ArrayTypeExpressionElements getArrayTypeExpressionAccess() {
		return pArrayTypeExpression;
	}
	
	public ParserRule getArrayTypeExpressionRule() {
		return getArrayTypeExpressionAccess().getRule();
	}
	
	//PrimaryTypeExpression returns TypeRef:
	//    ( LiteralTypeRef
	//    | ArrowFunctionTypeExpression
	//    | TypeRefWithModifiers
	//    | QueryTypeRef
	//    | InferTypeRef
	//    | "(" TypeRef ")"
	//    );
	public PrimaryTypeExpressionElements getPrimaryTypeExpressionAccess() {
		return pPrimaryTypeExpression;
	}
	
	public ParserRule getPrimaryTypeExpressionRule() {
		return getPrimaryTypeExpressionAccess().getRule();
	}
	
	//TypeRefWithModifiers returns TypeRef:
	//      (ParameterizedTypeRef => dynamic?='+'?)
	//    | (ThisTypeRef => dynamic?='+'?)
	//    | TypeTypeRef
	//    | UnionTypeExpressionOLD
	//    | IntersectionTypeExpressionOLD
	//    | MappedTypeRef // this covers sequences starting with: '{' '[' IdentifierName 'in' ...
	//;
	public TypeRefWithModifiersElements getTypeRefWithModifiersAccess() {
		return pTypeRefWithModifiers;
	}
	
	public ParserRule getTypeRefWithModifiersRule() {
		return getTypeRefWithModifiersAccess().getRule();
	}
	
	//TypeArgInTypeTypeRef returns TypeArgument:
	//      ParameterizedTypeRefNominal
	//    | ThisTypeRefNominal
	//    | WildcardOldNotation;
	public TypeArgInTypeTypeRefElements getTypeArgInTypeTypeRefAccess() {
		return pTypeArgInTypeTypeRef;
	}
	
	public ParserRule getTypeArgInTypeTypeRefRule() {
		return getTypeArgInTypeTypeRefAccess().getRule();
	}
	
	//LiteralTypeRef returns LiteralTypeRef:
	//      BooleanLiteralTypeRef
	//    | NumericLiteralTypeRef
	//    | StringLiteralTypeRef;
	public LiteralTypeRefElements getLiteralTypeRefAccess() {
		return pLiteralTypeRef;
	}
	
	public ParserRule getLiteralTypeRefRule() {
		return getLiteralTypeRefAccess().getRule();
	}
	
	//    // note: EnumLiteralTypeRefs are not available in type expressions
	//BooleanLiteralTypeRef returns BooleanLiteralTypeRef:
	//    {BooleanLiteralTypeRef} astValue=('true' | 'false');
	public BooleanLiteralTypeRefElements getBooleanLiteralTypeRefAccess() {
		return pBooleanLiteralTypeRef;
	}
	
	public ParserRule getBooleanLiteralTypeRefRule() {
		return getBooleanLiteralTypeRefAccess().getRule();
	}
	
	//NumericLiteralTypeRef returns NumericLiteralTypeRef:
	//    ('+' | astNegated?='-')?
	//    (
	//          astValue=INT
	//        | astValue=DOUBLE
	//        | astValue=OCTAL_INT
	//        | astValue=LEGACY_OCTAL_INT
	//        | astValue=HEX_INT
	//        | astValue=BINARY_INT
	//        | astValue=SCIENTIFIC_INT
	//    );
	public NumericLiteralTypeRefElements getNumericLiteralTypeRefAccess() {
		return pNumericLiteralTypeRef;
	}
	
	public ParserRule getNumericLiteralTypeRefRule() {
		return getNumericLiteralTypeRefAccess().getRule();
	}
	
	//StringLiteralTypeRef returns StringLiteralTypeRef:
	//    astValue=STRING;
	public StringLiteralTypeRefElements getStringLiteralTypeRefAccess() {
		return pStringLiteralTypeRef;
	}
	
	public ParserRule getStringLiteralTypeRefRule() {
		return getStringLiteralTypeRefAccess().getRule();
	}
	
	//ThisTypeRef returns ThisTypeRef:
	//    ThisTypeRefNominal | ThisTypeRefStructural;
	public ThisTypeRefElements getThisTypeRefAccess() {
		return pThisTypeRef;
	}
	
	public ParserRule getThisTypeRefRule() {
		return getThisTypeRefAccess().getRule();
	}
	
	//ThisTypeRefNominal returns ThisTypeRefNominal:
	//    {ThisTypeRefNominal} 'this';
	public ThisTypeRefNominalElements getThisTypeRefNominalAccess() {
		return pThisTypeRefNominal;
	}
	
	public ParserRule getThisTypeRefNominalRule() {
		return getThisTypeRefNominalAccess().getRule();
	}
	
	//ThisTypeRefStructural returns ThisTypeRefStructural:
	//    definedTypingStrategy=TypingStrategyUseSiteOperator
	//    'this'
	//    ('with' TStructMemberList)?;
	public ThisTypeRefStructuralElements getThisTypeRefStructuralAccess() {
		return pThisTypeRefStructural;
	}
	
	public ParserRule getThisTypeRefStructuralRule() {
		return getThisTypeRefStructuralAccess().getRule();
	}
	
	//ArrowFunctionTypeExpression returns FunctionTypeExpression:
	//    =>({FunctionTypeExpression} '(' TAnonymousFormalParameterListWithDeclaredThisType ')' Arrow) (returnTypePredicate=TypePredicateWithPrimary | returnTypeRef=PrimaryTypeExpression);
	public ArrowFunctionTypeExpressionElements getArrowFunctionTypeExpressionAccess() {
		return pArrowFunctionTypeExpression;
	}
	
	public ParserRule getArrowFunctionTypeExpressionRule() {
		return getArrowFunctionTypeExpressionAccess().getRule();
	}
	
	//// TODO extract FormalParameterContainer and use returns FormalParameterContainer instead of wildcard
	//fragment TAnonymousFormalParameterList* :
	//    (fpars+=TAnonymousFormalParameter (',' fpars+=TAnonymousFormalParameter)*)?
	//;
	public TAnonymousFormalParameterListElements getTAnonymousFormalParameterListAccess() {
		return pTAnonymousFormalParameterList;
	}
	
	public ParserRule getTAnonymousFormalParameterListRule() {
		return getTAnonymousFormalParameterListAccess().getRule();
	}
	
	//fragment TAnonymousFormalParameterListWithDeclaredThisType* :
	//    (('this' ':' declaredThisType=TypeRef | fpars+=TAnonymousFormalParameter) (',' fpars+=TAnonymousFormalParameter)*)?
	//;
	public TAnonymousFormalParameterListWithDeclaredThisTypeElements getTAnonymousFormalParameterListWithDeclaredThisTypeAccess() {
		return pTAnonymousFormalParameterListWithDeclaredThisType;
	}
	
	public ParserRule getTAnonymousFormalParameterListWithDeclaredThisTypeRule() {
		return getTAnonymousFormalParameterListWithDeclaredThisTypeAccess().getRule();
	}
	
	///**
	// * Used in type expressions, name is optional.
	// */
	//TAnonymousFormalParameter:
	//    variadic?='...'? (=>(name=BindingIdentifier<Yield=false> '?'? ->ColonSepTypeRef) | typeRef=TypeRef)
	//    DefaultFormalParameter
	//;
	public TAnonymousFormalParameterElements getTAnonymousFormalParameterAccess() {
		return pTAnonymousFormalParameter;
	}
	
	public ParserRule getTAnonymousFormalParameterRule() {
		return getTAnonymousFormalParameterAccess().getRule();
	}
	
	///**
	// * Used in Types language only.
	// */
	//TFormalParameter:
	//    variadic?='...'? name=BindingIdentifier<Yield=false> '?'? ColonSepTypeRef
	//    DefaultFormalParameter
	//;
	public TFormalParameterElements getTFormalParameterAccess() {
		return pTFormalParameter;
	}
	
	public ParserRule getTFormalParameterRule() {
		return getTFormalParameterAccess().getRule();
	}
	
	///**
	// * Default initializers in FunctionTypeExpressions or TFunctions
	// * are necessary to specify optional formal parameters. Hence, their
	// * initializer expression is rather uninteresting and limited by validations
	// * to 'undefined'. The shorthand form, that is omitting the initializer, is supported.
	// */
	//fragment DefaultFormalParameter*:
	//    (hasInitializerAssignment?='=' astInitializer=TypeReferenceName?)?
	//;
	public DefaultFormalParameterElements getDefaultFormalParameterAccess() {
		return pDefaultFormalParameter;
	}
	
	public ParserRule getDefaultFormalParameterRule() {
		return getDefaultFormalParameterAccess().getRule();
	}
	
	//UnionTypeExpressionOLD returns UnionTypeExpression:
	//    {UnionTypeExpression}
	//    'union' '{' typeRefs+=TypeRef (',' typeRefs+=TypeRef)* '}';
	public UnionTypeExpressionOLDElements getUnionTypeExpressionOLDAccess() {
		return pUnionTypeExpressionOLD;
	}
	
	public ParserRule getUnionTypeExpressionOLDRule() {
		return getUnionTypeExpressionOLDAccess().getRule();
	}
	
	//IntersectionTypeExpressionOLD returns IntersectionTypeExpression:
	//    {IntersectionTypeExpression}
	//    'intersection' '{' typeRefs+=TypeRef (',' typeRefs+=TypeRef)* '}';
	public IntersectionTypeExpressionOLDElements getIntersectionTypeExpressionOLDAccess() {
		return pIntersectionTypeExpressionOLD;
	}
	
	public ParserRule getIntersectionTypeExpressionOLDRule() {
		return getIntersectionTypeExpressionOLDAccess().getRule();
	}
	
	//ParameterizedTypeRef returns ParameterizedTypeRef:
	//    ParameterizedTypeRefNominal | ParameterizedTypeRefStructural;
	public ParameterizedTypeRefElements getParameterizedTypeRefAccess() {
		return pParameterizedTypeRef;
	}
	
	public ParserRule getParameterizedTypeRefRule() {
		return getParameterizedTypeRefAccess().getRule();
	}
	
	//ParameterizedTypeRefNominal returns ParameterizedTypeRef:
	//    TypeReference
	//    (-> TypeArguments)?
	//;
	public ParameterizedTypeRefNominalElements getParameterizedTypeRefNominalAccess() {
		return pParameterizedTypeRefNominal;
	}
	
	public ParserRule getParameterizedTypeRefNominalRule() {
		return getParameterizedTypeRefNominalAccess().getRule();
	}
	
	//ParameterizedTypeRefStructural returns ParameterizedTypeRefStructural:
	//    ( {ParameterizedTypeRefStructural} '{' (astStructuralMembers+=TStructMember (';'|',')?)*  '}' )
	//|    (
	//        definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
	//        (-> TypeArguments)?
	//        ('with' TStructMemberList)?
	//    )
	//;
	public ParameterizedTypeRefStructuralElements getParameterizedTypeRefStructuralAccess() {
		return pParameterizedTypeRefStructural;
	}
	
	public ParserRule getParameterizedTypeRefStructuralRule() {
		return getParameterizedTypeRefStructuralAccess().getRule();
	}
	
	//MappedTypeRef:
	//    '{'
	//    ('+'? includeReadonly?='readonly' | '-' excludeReadonly?='readonly')?
	//    '[' propName=IdentifierName 'in' propNameTypeRef=TypeRef ']'
	//    ('+'? includeOptional?='?' | '-' excludeOptional?='?')?
	//    (':' templateTypeRef=TypeRef)? ';'?
	//    // FIXME consider allowing additional mapping signatures here, to be able to show better error messages
	//    // FIXME consider allowing TStructMemberList here, to be able to show better error messages
	//    '}'
	//;
	public MappedTypeRefElements getMappedTypeRefAccess() {
		return pMappedTypeRef;
	}
	
	public ParserRule getMappedTypeRefRule() {
		return getMappedTypeRefAccess().getRule();
	}
	
	//ArrayNTypeExpression returns ParameterizedTypeRef:
	//    arrayNTypeExpression?='['
	//    (
	//        declaredTypeArgs+=EmptyIterableTypeExpressionTail
	//    |    declaredTypeArgs+=TypeArgument (',' declaredTypeArgs+=TypeArgument)* ']'
	//    );
	public ArrayNTypeExpressionElements getArrayNTypeExpressionAccess() {
		return pArrayNTypeExpression;
	}
	
	public ParserRule getArrayNTypeExpressionRule() {
		return getArrayNTypeExpressionAccess().getRule();
	}
	
	//EmptyIterableTypeExpressionTail returns Wildcard:
	//    {Wildcard} ']';
	public EmptyIterableTypeExpressionTailElements getEmptyIterableTypeExpressionTailAccess() {
		return pEmptyIterableTypeExpressionTail;
	}
	
	public ParserRule getEmptyIterableTypeExpressionTailRule() {
		return getEmptyIterableTypeExpressionTailAccess().getRule();
	}
	
	//fragment TypeReference *:
	//    (astNamespaceLikeRefs+=NamespaceLikeRef '.')*
	//    => declaredType=[Type|TypeReferenceName]
	//;
	public TypeReferenceElements getTypeReferenceAccess() {
		return pTypeReference;
	}
	
	public ParserRule getTypeReferenceRule() {
		return getTypeReferenceAccess().getRule();
	}
	
	//NamespaceLikeRef:
	//    declaredType=[Type|TypeReferenceName]
	//;
	public NamespaceLikeRefElements getNamespaceLikeRefAccess() {
		return pNamespaceLikeRef;
	}
	
	public ParserRule getNamespaceLikeRefRule() {
		return getNamespaceLikeRefAccess().getRule();
	}
	
	//fragment TypeArguments *:
	//    '<' declaredTypeArgs+=TypeArgument (',' declaredTypeArgs+=TypeArgument)* '>'
	//;
	public TypeArgumentsElements getTypeArgumentsAccess() {
		return pTypeArguments;
	}
	
	public ParserRule getTypeArgumentsRule() {
		return getTypeArgumentsAccess().getRule();
	}
	
	//fragment TStructMemberList*:  '{' (astStructuralMembers+=TStructMember (';'|',')?)*  '}';
	public TStructMemberListElements getTStructMemberListAccess() {
		return pTStructMemberList;
	}
	
	public ParserRule getTStructMemberListRule() {
		return getTStructMemberListAccess().getRule();
	}
	
	///**
	// * All TMembers here are only used in ParameterizedTypeRefStructural references
	// * Most type references are optional.
	// */
	//TStructMember:
	//      TStructGetter
	//    | TStructSetter
	//    | TStructMethod
	//    | TStructField
	//    | TStructIndexSignature;
	public TStructMemberElements getTStructMemberAccess() {
		return pTStructMember;
	}
	
	public ParserRule getTStructMemberRule() {
		return getTStructMemberAccess().getRule();
	}
	
	//TStructMethod:
	//    =>
	//    ({TStructMethod}
	//        ('<' typeVars+=TypeVariable (',' typeVars+=TypeVariable)* '>')?
	//        (name=IdentifierName)? '('
	//    )
	//    TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?
	//;
	public TStructMethodElements getTStructMethodAccess() {
		return pTStructMethod;
	}
	
	public ParserRule getTStructMethodRule() {
		return getTStructMethodAccess().getRule();
	}
	
	//fragment ColonSepTypeRef*:
	//    ':' typeRef=TypeRef
	//;
	public ColonSepTypeRefElements getColonSepTypeRefAccess() {
		return pColonSepTypeRef;
	}
	
	public ParserRule getColonSepTypeRefRule() {
		return getColonSepTypeRefAccess().getRule();
	}
	
	//fragment ColonSepReturnTypeRef*:
	//    ':' (returnTypePredicate=TypePredicate | returnTypeRef=TypeRef)
	//;
	public ColonSepReturnTypeRefElements getColonSepReturnTypeRefAccess() {
		return pColonSepReturnTypeRef;
	}
	
	public ParserRule getColonSepReturnTypeRefRule() {
		return getColonSepReturnTypeRefAccess().getRule();
	}
	
	//TStructField:
	//    'readonly'? name=IdentifierName (optional?='?')? ColonSepTypeRef?
	//;
	public TStructFieldElements getTStructFieldAccess() {
		return pTStructField;
	}
	
	public ParserRule getTStructFieldRule() {
		return getTStructFieldAccess().getRule();
	}
	
	//TStructGetter:
	//    => ({TStructGetter}
	//    'get'
	//    name=IdentifierName)
	//    (optional?='?')?
	//    '(' ')' ColonSepTypeRef?
	//;
	public TStructGetterElements getTStructGetterAccess() {
		return pTStructGetter;
	}
	
	public ParserRule getTStructGetterRule() {
		return getTStructGetterAccess().getRule();
	}
	
	//TStructSetter:
	//    => ({TStructSetter}
	//    'set'
	//    name=IdentifierName)
	//    (optional?='?')?
	//    '(' fpar=TAnonymousFormalParameter ')'
	//;
	public TStructSetterElements getTStructSetterAccess() {
		return pTStructSetter;
	}
	
	public ParserRule getTStructSetterRule() {
		return getTStructSetterAccess().getRule();
	}
	
	//TStructIndexSignature:
	//    '[' keyName=IdentifierName ':' keyTypeRef=TypeRef ']'
	//    ':'
	//    valueTypeRef=TypeRef
	//;
	public TStructIndexSignatureElements getTStructIndexSignatureAccess() {
		return pTStructIndexSignature;
	}
	
	public ParserRule getTStructIndexSignatureRule() {
		return getTStructIndexSignatureAccess().getRule();
	}
	
	//TypingStrategyUseSiteOperator returns TypingStrategy:
	//    '~' ('~' | STRUCTMODSUFFIX)?;
	public TypingStrategyUseSiteOperatorElements getTypingStrategyUseSiteOperatorAccess() {
		return pTypingStrategyUseSiteOperator;
	}
	
	public ParserRule getTypingStrategyUseSiteOperatorRule() {
		return getTypingStrategyUseSiteOperatorAccess().getRule();
	}
	
	//TypingStrategyDefSiteOperator returns TypingStrategy:
	//    '~';
	public TypingStrategyDefSiteOperatorElements getTypingStrategyDefSiteOperatorAccess() {
		return pTypingStrategyDefSiteOperator;
	}
	
	public ParserRule getTypingStrategyDefSiteOperatorRule() {
		return getTypingStrategyDefSiteOperatorAccess().getRule();
	}
	
	//terminal STRUCTMODSUFFIX:
	//    ('r' | 'i' | 'w' | '\u2205') '~'
	//;
	public TerminalRule getSTRUCTMODSUFFIXRule() {
		return tSTRUCTMODSUFFIX;
	}
	
	//TypeTypeRef returns TypeTypeRef:
	//    {TypeTypeRef}
	//    ('type' | constructorRef?='constructor')
	//    '{' typeArg=TypeArgInTypeTypeRef '}';
	public TypeTypeRefElements getTypeTypeRefAccess() {
		return pTypeTypeRef;
	}
	
	public ParserRule getTypeTypeRefRule() {
		return getTypeTypeRefAccess().getRule();
	}
	
	//TypeReferenceName:
	//    'void' | 'This' | 'await' | 'Promisify' | 'target' | 'default' | IDENTIFIER
	//;
	public TypeReferenceNameElements getTypeReferenceNameAccess() {
		return pTypeReferenceName;
	}
	
	public ParserRule getTypeReferenceNameRule() {
		return getTypeReferenceNameAccess().getRule();
	}
	
	//TypeArgument returns TypeArgument:
	//    Wildcard | TypeRef;
	public TypeArgumentElements getTypeArgumentAccess() {
		return pTypeArgument;
	}
	
	public ParserRule getTypeArgumentRule() {
		return getTypeArgumentAccess().getRule();
	}
	
	//Wildcard:
	//    WildcardOldNotation
	//|    WildcardNewNotation;
	public WildcardElements getWildcardAccess() {
		return pWildcard;
	}
	
	public ParserRule getWildcardRule() {
		return getWildcardAccess().getRule();
	}
	
	//WildcardOldNotation returns Wildcard:
	//    => ({Wildcard} '?') (('extends' declaredUpperBound=TypeRef) | ('super'
	//    declaredLowerBound=TypeRef))?;
	public WildcardOldNotationElements getWildcardOldNotationAccess() {
		return pWildcardOldNotation;
	}
	
	public ParserRule getWildcardOldNotationRule() {
		return getWildcardOldNotationAccess().getRule();
	}
	
	//WildcardOldNotationWithoutBound returns Wildcard:
	//    {Wildcard} '?';
	public WildcardOldNotationWithoutBoundElements getWildcardOldNotationWithoutBoundAccess() {
		return pWildcardOldNotationWithoutBound;
	}
	
	public ParserRule getWildcardOldNotationWithoutBoundRule() {
		return getWildcardOldNotationWithoutBoundAccess().getRule();
	}
	
	//WildcardNewNotation returns Wildcard:
	//    (usingInOutNotation?='out' declaredUpperBound=TypeRef)
	//|    (usingInOutNotation?='in' declaredLowerBound=TypeRef);
	public WildcardNewNotationElements getWildcardNewNotationAccess() {
		return pWildcardNewNotation;
	}
	
	public ParserRule getWildcardNewNotationRule() {
		return getWildcardNewNotationAccess().getRule();
	}
	
	//TypeVariable returns TypeVariable:
	//    (declaredCovariant?='out' | declaredContravariant?='in')?
	//    name=IDENTIFIER ('extends' declaredUpperBound=TypeRef)?
	//    // the following is disallowed by ASTStructureValidator for all uses of this grammar rule
	//    // (only added here to obtain a better error message)
	//    ('=' defaultArgument=TypeRef)?;
	public TypeVariableElements getTypeVariableAccess() {
		return pTypeVariable;
	}
	
	public ParserRule getTypeVariableRule() {
		return getTypeVariableAccess().getRule();
	}
	
	//QueryTypeRef:
	//    'typeof' element=[IdentifiableElement|IdentifierName]
	//;
	public QueryTypeRefElements getQueryTypeRefAccess() {
		return pQueryTypeRef;
	}
	
	public ParserRule getQueryTypeRefRule() {
		return getQueryTypeRefAccess().getRule();
	}
	
	//InferTypeRef:
	//    'infer' typeVarName=IDENTIFIER
	//;
	public InferTypeRefElements getInferTypeRefAccess() {
		return pInferTypeRef;
	}
	
	public ParserRule getInferTypeRefRule() {
		return getInferTypeRefAccess().getRule();
	}
	
	//TypePredicate:
	//    =>((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is') typeRef=TypeRef
	//;
	public TypePredicateElements getTypePredicateAccess() {
		return pTypePredicate;
	}
	
	public ParserRule getTypePredicateRule() {
		return getTypePredicateAccess().getRule();
	}
	
	//TypePredicateWithPrimary returns TypePredicate:
	//    =>((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier<Yield=false>]) 'is') typeRef=PrimaryTypeExpression
	//;
	public TypePredicateWithPrimaryElements getTypePredicateWithPrimaryAccess() {
		return pTypePredicateWithPrimary;
	}
	
	public ParserRule getTypePredicateWithPrimaryRule() {
		return getTypePredicateWithPrimaryAccess().getRule();
	}
	
	///*
	// * [ECM11] (7.6, pp. 17)
	// * Identifier :: IdentifierName but not ReservedWord
	// * ReservedWord :: Keyword | FutureReservedWord | NullLiteral | BooleanLiteral
	// */
	//BindingIdentifier <Yield>:
	//    IDENTIFIER
	//    // yield as identifier as of [ECM15] (11.6.2, pp. 165)
	//    | <!Yield> 'yield'
	//    | N4Keyword
	//;
	public BindingIdentifierElements getBindingIdentifierAccess() {
		return pBindingIdentifier;
	}
	
	public ParserRule getBindingIdentifierRule() {
		return getBindingIdentifierAccess().getRule();
	}
	
	//IdentifierName:
	//    IDENTIFIER | ReservedWord | N4Keyword
	//;
	public IdentifierNameElements getIdentifierNameAccess() {
		return pIdentifierName;
	}
	
	public ParserRule getIdentifierNameRule() {
		return getIdentifierNameAccess().getRule();
	}
	
	//ReservedWord:
	//    // Keywords as of [ECM15] (11.6.2, pp. 165)
	//    'break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete'
	//    | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import'
	//    | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try'
	//    | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield'
	//    // null literal
	//    | 'null'
	//    // boolean literal
	//    | 'true' | 'false'
	//    // Future Reserved Word as of [ECM15] (11.6.2.2, pp. 166)
	//    // | 'await' /* reserved word only if parse goal is module - compromise: allow as identifier and validate */
	//    | 'enum';
	public ReservedWordElements getReservedWordAccess() {
		return pReservedWord;
	}
	
	public ParserRule getReservedWordRule() {
		return getReservedWordAccess().getRule();
	}
	
	//N4Keyword:
	//    'get' | 'set'
	//    | 'let'
	//    | 'project'
	//    | 'external' | 'abstract' | 'static'
	//    | 'as' | 'from' | 'constructor' | 'of' | 'target'
	//    | 'type' | 'union' | 'intersection'
	//    | 'Promisify'
	//    // future reserved keyword in [ECM15] only in modules, we add additional validation
	//    | 'await'
	//    // async is not a reserved keyword, i.e. it can be used as a variable name
	//    | 'async'
	//    // future reserved keywords in [ECM15], restricted via static semantic in [ECM15]
	//    | 'implements' | 'interface'
	//    | 'private' | 'protected' | 'public' // package not used in N4JS
	//    // definition-site variance
	//    | 'out'
<<<<<<< HEAD
	//    // namespace keyword
	//    | 'namespace'
=======
	//    // .d.ts keywords
<<<<<<< HEAD
	//    | 'declare' | 'is' | 'keyof' | 'unique' // | 'readonly'
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
=======
	//    | 'declare' | 'is' | 'keyof' | 'unique' | 'infer' // | 'readonly'
>>>>>>> c3d10a43c (early support for infer declarations)
	//;
	public N4KeywordElements getN4KeywordAccess() {
		return pN4Keyword;
	}
	
	public ParserRule getN4KeywordRule() {
		return getN4KeywordAccess().getRule();
	}
	
	//Arrow hidden(): // "hidden()" works due to LazyTokenStream#doSetHiddenTokens()
	//    '=' '>'
	//;
	public ArrowElements getArrowAccess() {
		return pArrow;
	}
	
	public ParserRule getArrowRule() {
		return getArrowAccess().getRule();
	}
	
	//terminal IDENTIFIER:
	//    IDENTIFIER_START IDENTIFIER_PART*;
	public TerminalRule getIDENTIFIERRule() {
		return tIDENTIFIER;
	}
	
	///**
	// * The terminal rules to represent number literals are listed below.
	// *
	// * They implement the constraint
	// * 'The source character immediately following a NumericLiteral must not be an IdentifierStart or DecimalDigit.'
	// * in the value converter. That is, the terminals consume a trailing identifier and
	// * later on, a meaningful error will be attached.
	// */
	//terminal INT returns ecore::EBigDecimal: DECIMAL_INTEGER_LITERAL_FRAGMENT;
	public TerminalRule getINTRule() {
		return tINT;
	}
	
	//terminal DOUBLE returns ecore::EBigDecimal:
	//    '.' DECIMAL_DIGIT_FRAGMENT+ EXPONENT_PART?
	//    | DECIMAL_INTEGER_LITERAL_FRAGMENT '.' DECIMAL_DIGIT_FRAGMENT* EXPONENT_PART?
	//;
	public TerminalRule getDOUBLERule() {
		return tDOUBLE;
	}
	
	//terminal BINARY_INT returns ecore::EBigDecimal: '0' ('b' | 'B') INT_SUFFIX;
	public TerminalRule getBINARY_INTRule() {
		return tBINARY_INT;
	}
	
	//terminal OCTAL_INT returns ecore::EBigDecimal: '0' ('o' | 'O') INT_SUFFIX;
	public TerminalRule getOCTAL_INTRule() {
		return tOCTAL_INT;
	}
	
	//terminal LEGACY_OCTAL_INT returns ecore::EBigDecimal: '0' DECIMAL_DIGIT_FRAGMENT INT_SUFFIX;
	public TerminalRule getLEGACY_OCTAL_INTRule() {
		return tLEGACY_OCTAL_INT;
	}
	
	//terminal HEX_INT returns ecore::EBigDecimal: '0' ('x' | 'X') INT_SUFFIX;
	public TerminalRule getHEX_INTRule() {
		return tHEX_INT;
	}
	
	///**
	// * This terminal fragment includes the decimal digits '0'..'9' and also all other identifier part chars
	// * to have a relaxed grammar and better error messages from the value converter.
	// */
	//terminal fragment INT_SUFFIX: IDENTIFIER_PART*;
	public TerminalRule getINT_SUFFIXRule() {
		return tINT_SUFFIX;
	}
	
	//terminal SCIENTIFIC_INT returns ecore::EBigDecimal:
	//    DECIMAL_INTEGER_LITERAL_FRAGMENT EXPONENT_PART
	//;
	public TerminalRule getSCIENTIFIC_INTRule() {
		return tSCIENTIFIC_INT;
	}
	
	//terminal fragment EXPONENT_PART:
	//      ('e' | 'E') SIGNED_INT
	//    | IDENTIFIER
	//;
	public TerminalRule getEXPONENT_PARTRule() {
		return tEXPONENT_PART;
	}
	
	//terminal fragment SIGNED_INT:
	//    ('+' | '-') DECIMAL_DIGIT_FRAGMENT+ IDENTIFIER?
	//;
	public TerminalRule getSIGNED_INTRule() {
		return tSIGNED_INT;
	}
	
	///* This terminal rule is not as strict as the ECMA spec because we want to
	// * provide better error messages than the lexer does.
	// * Therefore, an unclosed string literal is consumed to the end of line
	// * and validated in the JSStringValueConverter afterwards.
	// */
	//terminal STRING:
	//      '"' DOUBLE_STRING_CHAR* '"'?
	//    | "'" SINGLE_STRING_CHAR* "'"?
	//;
	public TerminalRule getSTRINGRule() {
		return tSTRING;
	}
	
	//terminal fragment DOUBLE_STRING_CHAR:
	//      !(LINE_TERMINATOR_FRAGMENT | '"' | '\\')
	//    | '\\' (LINE_TERMINATOR_SEQUENCE_FRAGMENT | !LINE_TERMINATOR_FRAGMENT)?
	//;
	public TerminalRule getDOUBLE_STRING_CHARRule() {
		return tDOUBLE_STRING_CHAR;
	}
	
	//terminal fragment SINGLE_STRING_CHAR:
	//      !(LINE_TERMINATOR_FRAGMENT | "'" | '\\')
	//    | '\\' (LINE_TERMINATOR_SEQUENCE_FRAGMENT | !LINE_TERMINATOR_FRAGMENT)?
	//;
	public TerminalRule getSINGLE_STRING_CHARRule() {
		return tSINGLE_STRING_CHAR;
	}
	
	//terminal ML_COMMENT:
	//    ML_COMMENT_FRAGMENT;
	public TerminalRule getML_COMMENTRule() {
		return tML_COMMENT;
	}
	
	//terminal SL_COMMENT:
	//    '//' (!LINE_TERMINATOR_FRAGMENT)*;
	public TerminalRule getSL_COMMENTRule() {
		return tSL_COMMENT;
	}
	
	//terminal EOL:
	//    LINE_TERMINATOR_SEQUENCE_FRAGMENT;
	public TerminalRule getEOLRule() {
		return tEOL;
	}
	
	//terminal WS:
	//    WHITESPACE_FRAGMENT+;
	public TerminalRule getWSRule() {
		return tWS;
	}
	
	//terminal fragment UNICODE_ESCAPE_FRAGMENT:
	//    '\\' ('u' (
	//        HEX_DIGIT (HEX_DIGIT (HEX_DIGIT HEX_DIGIT?)?)?
	//      | '{' HEX_DIGIT* '}'?
	//    )?)?;
	public TerminalRule getUNICODE_ESCAPE_FRAGMENTRule() {
		return tUNICODE_ESCAPE_FRAGMENT;
	}
	
	//terminal fragment IDENTIFIER_START:
	//      UNICODE_LETTER_FRAGMENT
	//    | '$'
	//    | '_'
	//    | UNICODE_ESCAPE_FRAGMENT;
	public TerminalRule getIDENTIFIER_STARTRule() {
		return tIDENTIFIER_START;
	}
	
	//terminal fragment IDENTIFIER_PART:
	//      UNICODE_LETTER_FRAGMENT
	//    | UNICODE_ESCAPE_FRAGMENT
	//    | '$'
	//    | UNICODE_COMBINING_MARK_FRAGMENT
	//    | UNICODE_DIGIT_FRAGMENT
	//    | UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT
	//    | ZWNJ
	//    | ZWJ;
	public TerminalRule getIDENTIFIER_PARTRule() {
		return tIDENTIFIER_PART;
	}
	
	//// Intentionally unused to get rid of bogus lexer errors when
	//// the input contains an incomplete variadic?='...' keyword, e.g. ..)
	//terminal DOT_DOT:
	//    '..'
	//;
	public TerminalRule getDOT_DOTRule() {
		return tDOT_DOT;
	}
	
	//terminal fragment HEX_DIGIT:
	//    (DECIMAL_DIGIT_FRAGMENT|'a'..'f'|'A'..'F')
	//;
	public TerminalRule getHEX_DIGITRule() {
		return gaUnicode.getHEX_DIGITRule();
	}
	
	//terminal fragment DECIMAL_INTEGER_LITERAL_FRAGMENT:
	//      '0'
	//    | '1'..'9' DECIMAL_DIGIT_FRAGMENT*
	//;
	public TerminalRule getDECIMAL_INTEGER_LITERAL_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_INTEGER_LITERAL_FRAGMENTRule();
	}
	
	//terminal fragment DECIMAL_DIGIT_FRAGMENT:
	//    '0'..'9'
	//;
	public TerminalRule getDECIMAL_DIGIT_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment ZWJ:
	//    '\u200D'
	//;
	public TerminalRule getZWJRule() {
		return gaUnicode.getZWJRule();
	}
	
	//terminal fragment ZWNJ:
	//    '\u200C'
	//;
	public TerminalRule getZWNJRule() {
		return gaUnicode.getZWNJRule();
	}
	
	//terminal fragment BOM:
	//    '\uFEFF'
	//;
	public TerminalRule getBOMRule() {
		return gaUnicode.getBOMRule();
	}
	
	//terminal fragment WHITESPACE_FRAGMENT:
	//    '\u0009' | '\u000B' | '\u000C' | '\u0020' | '\u00A0' | BOM | UNICODE_SPACE_SEPARATOR_FRAGMENT
	//;
	public TerminalRule getWHITESPACE_FRAGMENTRule() {
		return gaUnicode.getWHITESPACE_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_FRAGMENT:
	//    '\n' | '\r' | '\u2028' | '\u2029'
	//;
	public TerminalRule getLINE_TERMINATOR_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_SEQUENCE_FRAGMENT:
	//    '\n' | '\r' '\n'? | '\u2028' | '\u2029'
	//;
	public TerminalRule getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule();
	}
	
	//terminal fragment SL_COMMENT_FRAGMENT:
	//    '//' (!LINE_TERMINATOR_FRAGMENT)*
	//;
	public TerminalRule getSL_COMMENT_FRAGMENTRule() {
		return gaUnicode.getSL_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment ML_COMMENT_FRAGMENT:
	//    '/*' -> '*/'
	//;
	public TerminalRule getML_COMMENT_FRAGMENTRule() {
		return gaUnicode.getML_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_COMBINING_MARK_FRAGMENT:
	//    // any character in the Unicode categories
	//    // ―Non-spacing mark (Mn)
	//    // ―Combining spacing mark (Mc)
	//      '\u0300'..'\u036F'
	//    | '\u0483'..'\u0487'
	//    | '\u0591'..'\u05BD'
	//    | '\u05BF'
	//    | '\u05C1'..'\u05C2'
	//    | '\u05C4'..'\u05C5'
	//    | '\u05C7'
	//    | '\u0610'..'\u061A'
	//    | '\u064B'..'\u065F'
	//    | '\u0670'
	//    | '\u06D6'..'\u06DC'
	//    | '\u06DF'..'\u06E4'
	//    | '\u06E7'..'\u06E8'
	//    | '\u06EA'..'\u06ED'
	//    | '\u0711'
	//    | '\u0730'..'\u074A'
	//    | '\u07A6'..'\u07B0'
	//    | '\u07EB'..'\u07F3'
	//    | '\u0816'..'\u0819'
	//    | '\u081B'..'\u0823'
	//    | '\u0825'..'\u0827'
	//    | '\u0829'..'\u082D'
	//    | '\u0859'..'\u085B'
	//    | '\u08E3'..'\u0903'
	//    | '\u093A'..'\u093C'
	//    | '\u093E'..'\u094F'
	//    | '\u0951'..'\u0957'
	//    | '\u0962'..'\u0963'
	//    | '\u0981'..'\u0983'
	//    | '\u09BC'
	//    | '\u09BE'..'\u09C4'
	//    | '\u09C7'..'\u09C8'
	//    | '\u09CB'..'\u09CD'
	//    | '\u09D7'
	//    | '\u09E2'..'\u09E3'
	//    | '\u0A01'..'\u0A03'
	//    | '\u0A3C'
	//    | '\u0A3E'..'\u0A42'
	//    | '\u0A47'..'\u0A48'
	//    | '\u0A4B'..'\u0A4D'
	//    | '\u0A51'
	//    | '\u0A70'..'\u0A71'
	//    | '\u0A75'
	//    | '\u0A81'..'\u0A83'
	//    | '\u0ABC'
	//    | '\u0ABE'..'\u0AC5'
	//    | '\u0AC7'..'\u0AC9'
	//    | '\u0ACB'..'\u0ACD'
	//    | '\u0AE2'..'\u0AE3'
	//    | '\u0B01'..'\u0B03'
	//    | '\u0B3C'
	//    | '\u0B3E'..'\u0B44'
	//    | '\u0B47'..'\u0B48'
	//    | '\u0B4B'..'\u0B4D'
	//    | '\u0B56'..'\u0B57'
	//    | '\u0B62'..'\u0B63'
	//    | '\u0B82'
	//    | '\u0BBE'..'\u0BC2'
	//    | '\u0BC6'..'\u0BC8'
	//    | '\u0BCA'..'\u0BCD'
	//    | '\u0BD7'
	//    | '\u0C00'..'\u0C03'
	//    | '\u0C3E'..'\u0C44'
	//    | '\u0C46'..'\u0C48'
	//    | '\u0C4A'..'\u0C4D'
	//    | '\u0C55'..'\u0C56'
	//    | '\u0C62'..'\u0C63'
	//    | '\u0C81'..'\u0C83'
	//    | '\u0CBC'
	//    | '\u0CBE'..'\u0CC4'
	//    | '\u0CC6'..'\u0CC8'
	//    | '\u0CCA'..'\u0CCD'
	//    | '\u0CD5'..'\u0CD6'
	//    | '\u0CE2'..'\u0CE3'
	//    | '\u0D01'..'\u0D03'
	//    | '\u0D3E'..'\u0D44'
	//    | '\u0D46'..'\u0D48'
	//    | '\u0D4A'..'\u0D4D'
	//    | '\u0D57'
	//    | '\u0D62'..'\u0D63'
	//    | '\u0D82'..'\u0D83'
	//    | '\u0DCA'
	//    | '\u0DCF'..'\u0DD4'
	//    | '\u0DD6'
	//    | '\u0DD8'..'\u0DDF'
	//    | '\u0DF2'..'\u0DF3'
	//    | '\u0E31'
	//    | '\u0E34'..'\u0E3A'
	//    | '\u0E47'..'\u0E4E'
	//    | '\u0EB1'
	//    | '\u0EB4'..'\u0EB9'
	//    | '\u0EBB'..'\u0EBC'
	//    | '\u0EC8'..'\u0ECD'
	//    | '\u0F18'..'\u0F19'
	//    | '\u0F35'
	//    | '\u0F37'
	//    | '\u0F39'
	//    | '\u0F3E'..'\u0F3F'
	//    | '\u0F71'..'\u0F84'
	//    | '\u0F86'..'\u0F87'
	//    | '\u0F8D'..'\u0F97'
	//    | '\u0F99'..'\u0FBC'
	//    | '\u0FC6'
	//    | '\u102B'..'\u103E'
	//    | '\u1056'..'\u1059'
	//    | '\u105E'..'\u1060'
	//    | '\u1062'..'\u1064'
	//    | '\u1067'..'\u106D'
	//    | '\u1071'..'\u1074'
	//    | '\u1082'..'\u108D'
	//    | '\u108F'
	//    | '\u109A'..'\u109D'
	//    | '\u135D'..'\u135F'
	//    | '\u1712'..'\u1714'
	//    | '\u1732'..'\u1734'
	//    | '\u1752'..'\u1753'
	//    | '\u1772'..'\u1773'
	//    | '\u17B4'..'\u17D3'
	//    | '\u17DD'
	//    | '\u180B'..'\u180D'
	//    | '\u18A9'
	//    | '\u1920'..'\u192B'
	//    | '\u1930'..'\u193B'
	//    | '\u1A17'..'\u1A1B'
	//    | '\u1A55'..'\u1A5E'
	//    | '\u1A60'..'\u1A7C'
	//    | '\u1A7F'
	//    | '\u1AB0'..'\u1ABD'
	//    | '\u1B00'..'\u1B04'
	//    | '\u1B34'..'\u1B44'
	//    | '\u1B6B'..'\u1B73'
	//    | '\u1B80'..'\u1B82'
	//    | '\u1BA1'..'\u1BAD'
	//    | '\u1BE6'..'\u1BF3'
	//    | '\u1C24'..'\u1C37'
	//    | '\u1CD0'..'\u1CD2'
	//    | '\u1CD4'..'\u1CE8'
	//    | '\u1CED'
	//    | '\u1CF2'..'\u1CF4'
	//    | '\u1CF8'..'\u1CF9'
	//    | '\u1DC0'..'\u1DF5'
	//    | '\u1DFC'..'\u1DFF'
	//    | '\u20D0'..'\u20DC'
	//    | '\u20E1'
	//    | '\u20E5'..'\u20F0'
	//    | '\u2CEF'..'\u2CF1'
	//    | '\u2D7F'
	//    | '\u2DE0'..'\u2DFF'
	//    | '\u302A'..'\u302F'
	//    | '\u3099'..'\u309A'
	//    | '\uA66F'
	//    | '\uA674'..'\uA67D'
	//    | '\uA69E'..'\uA69F'
	//    | '\uA6F0'..'\uA6F1'
	//    | '\uA802'
	//    | '\uA806'
	//    | '\uA80B'
	//    | '\uA823'..'\uA827'
	//    | '\uA880'..'\uA881'
	//    | '\uA8B4'..'\uA8C4'
	//    | '\uA8E0'..'\uA8F1'
	//    | '\uA926'..'\uA92D'
	//    | '\uA947'..'\uA953'
	//    | '\uA980'..'\uA983'
	//    | '\uA9B3'..'\uA9C0'
	//    | '\uA9E5'
	//    | '\uAA29'..'\uAA36'
	//    | '\uAA43'
	//    | '\uAA4C'..'\uAA4D'
	//    | '\uAA7B'..'\uAA7D'
	//    | '\uAAB0'
	//    | '\uAAB2'..'\uAAB4'
	//    | '\uAAB7'..'\uAAB8'
	//    | '\uAABE'..'\uAABF'
	//    | '\uAAC1'
	//    | '\uAAEB'..'\uAAEF'
	//    | '\uAAF5'..'\uAAF6'
	//    | '\uABE3'..'\uABEA'
	//    | '\uABEC'..'\uABED'
	//    | '\uFB1E'
	//    | '\uFE00'..'\uFE0F'
	//    | '\uFE20'..'\uFE2F'
	//;
	public TerminalRule getUNICODE_COMBINING_MARK_FRAGMENTRule() {
		return gaUnicode.getUNICODE_COMBINING_MARK_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_DIGIT_FRAGMENT:
	//    // any character in the Unicode categories
	//    // ―Decimal number (Nd)
	//      '\u0030'..'\u0039'
	//    | '\u0660'..'\u0669'
	//    | '\u06F0'..'\u06F9'
	//    | '\u07C0'..'\u07C9'
	//    | '\u0966'..'\u096F'
	//    | '\u09E6'..'\u09EF'
	//    | '\u0A66'..'\u0A6F'
	//    | '\u0AE6'..'\u0AEF'
	//    | '\u0B66'..'\u0B6F'
	//    | '\u0BE6'..'\u0BEF'
	//    | '\u0C66'..'\u0C6F'
	//    | '\u0CE6'..'\u0CEF'
	//    | '\u0D66'..'\u0D6F'
	//    | '\u0DE6'..'\u0DEF'
	//    | '\u0E50'..'\u0E59'
	//    | '\u0ED0'..'\u0ED9'
	//    | '\u0F20'..'\u0F29'
	//    | '\u1040'..'\u1049'
	//    | '\u1090'..'\u1099'
	//    | '\u17E0'..'\u17E9'
	//    | '\u1810'..'\u1819'
	//    | '\u1946'..'\u194F'
	//    | '\u19D0'..'\u19D9'
	//    | '\u1A80'..'\u1A89'
	//    | '\u1A90'..'\u1A99'
	//    | '\u1B50'..'\u1B59'
	//    | '\u1BB0'..'\u1BB9'
	//    | '\u1C40'..'\u1C49'
	//    | '\u1C50'..'\u1C59'
	//    | '\uA620'..'\uA629'
	//    | '\uA8D0'..'\uA8D9'
	//    | '\uA900'..'\uA909'
	//    | '\uA9D0'..'\uA9D9'
	//    | '\uA9F0'..'\uA9F9'
	//    | '\uAA50'..'\uAA59'
	//    | '\uABF0'..'\uABF9'
	//    | '\uFF10'..'\uFF19'
	//;
	public TerminalRule getUNICODE_DIGIT_FRAGMENTRule() {
		return gaUnicode.getUNICODE_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT:
	//    // any character in the Unicode categories
	//    // ―Connector punctuation (Pc)
	//      '\u005F'
	//    | '\u203F'..'\u2040'
	//    | '\u2054'
	//    | '\uFE33'..'\uFE34'
	//    | '\uFE4D'..'\uFE4F'
	//    | '\uFF3F'
	//;
	public TerminalRule getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule() {
		return gaUnicode.getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_LETTER_FRAGMENT:
	//    // any character in the Unicode categories
	//    // ―Uppercase letter (Lu)
	//    // ―Lowercase letter (Ll)
	//    // ―Titlecase letter (Lt)
	//    // ―Modifier letter (Lm)
	//    // ―Other letter (Lo)
	//    // ―Letter number (Nl)
	//      '\u0041'..'\u005A'
	//    | '\u0061'..'\u007A'
	//    | '\u00AA'
	//    | '\u00B5'
	//    | '\u00BA'
	//    | '\u00C0'..'\u00D6'
	//    | '\u00D8'..'\u00F6'
	//    | '\u00F8'..'\u02C1'
	//    | '\u02C6'..'\u02D1'
	//    | '\u02E0'..'\u02E4'
	//    | '\u02EC'
	//    | '\u02EE'
	//    | '\u0370'..'\u0374'
	//    | '\u0376'..'\u0377'
	//    | '\u037A'..'\u037D'
	//    | '\u037F'
	//    | '\u0386'
	//    | '\u0388'..'\u038A'
	//    | '\u038C'
	//    | '\u038E'..'\u03A1'
	//    | '\u03A3'..'\u03F5'
	//    | '\u03F7'..'\u0481'
	//    | '\u048A'..'\u052F'
	//    | '\u0531'..'\u0556'
	//    | '\u0559'
	//    | '\u0561'..'\u0587'
	//    | '\u05D0'..'\u05EA'
	//    | '\u05F0'..'\u05F2'
	//    | '\u0620'..'\u064A'
	//    | '\u066E'..'\u066F'
	//    | '\u0671'..'\u06D3'
	//    | '\u06D5'
	//    | '\u06E5'..'\u06E6'
	//    | '\u06EE'..'\u06EF'
	//    | '\u06FA'..'\u06FC'
	//    | '\u06FF'
	//    | '\u0710'
	//    | '\u0712'..'\u072F'
	//    | '\u074D'..'\u07A5'
	//    | '\u07B1'
	//    | '\u07CA'..'\u07EA'
	//    | '\u07F4'..'\u07F5'
	//    | '\u07FA'
	//    | '\u0800'..'\u0815'
	//    | '\u081A'
	//    | '\u0824'
	//    | '\u0828'
	//    | '\u0840'..'\u0858'
	//    | '\u08A0'..'\u08B4'
	//    | '\u0904'..'\u0939'
	//    | '\u093D'
	//    | '\u0950'
	//    | '\u0958'..'\u0961'
	//    | '\u0971'..'\u0980'
	//    | '\u0985'..'\u098C'
	//    | '\u098F'..'\u0990'
	//    | '\u0993'..'\u09A8'
	//    | '\u09AA'..'\u09B0'
	//    | '\u09B2'
	//    | '\u09B6'..'\u09B9'
	//    | '\u09BD'
	//    | '\u09CE'
	//    | '\u09DC'..'\u09DD'
	//    | '\u09DF'..'\u09E1'
	//    | '\u09F0'..'\u09F1'
	//    | '\u0A05'..'\u0A0A'
	//    | '\u0A0F'..'\u0A10'
	//    | '\u0A13'..'\u0A28'
	//    | '\u0A2A'..'\u0A30'
	//    | '\u0A32'..'\u0A33'
	//    | '\u0A35'..'\u0A36'
	//    | '\u0A38'..'\u0A39'
	//    | '\u0A59'..'\u0A5C'
	//    | '\u0A5E'
	//    | '\u0A72'..'\u0A74'
	//    | '\u0A85'..'\u0A8D'
	//    | '\u0A8F'..'\u0A91'
	//    | '\u0A93'..'\u0AA8'
	//    | '\u0AAA'..'\u0AB0'
	//    | '\u0AB2'..'\u0AB3'
	//    | '\u0AB5'..'\u0AB9'
	//    | '\u0ABD'
	//    | '\u0AD0'
	//    | '\u0AE0'..'\u0AE1'
	//    | '\u0AF9'
	//    | '\u0B05'..'\u0B0C'
	//    | '\u0B0F'..'\u0B10'
	//    | '\u0B13'..'\u0B28'
	//    | '\u0B2A'..'\u0B30'
	//    | '\u0B32'..'\u0B33'
	//    | '\u0B35'..'\u0B39'
	//    | '\u0B3D'
	//    | '\u0B5C'..'\u0B5D'
	//    | '\u0B5F'..'\u0B61'
	//    | '\u0B71'
	//    | '\u0B83'
	//    | '\u0B85'..'\u0B8A'
	//    | '\u0B8E'..'\u0B90'
	//    | '\u0B92'..'\u0B95'
	//    | '\u0B99'..'\u0B9A'
	//    | '\u0B9C'
	//    | '\u0B9E'..'\u0B9F'
	//    | '\u0BA3'..'\u0BA4'
	//    | '\u0BA8'..'\u0BAA'
	//    | '\u0BAE'..'\u0BB9'
	//    | '\u0BD0'
	//    | '\u0C05'..'\u0C0C'
	//    | '\u0C0E'..'\u0C10'
	//    | '\u0C12'..'\u0C28'
	//    | '\u0C2A'..'\u0C39'
	//    | '\u0C3D'
	//    | '\u0C58'..'\u0C5A'
	//    | '\u0C60'..'\u0C61'
	//    | '\u0C85'..'\u0C8C'
	//    | '\u0C8E'..'\u0C90'
	//    | '\u0C92'..'\u0CA8'
	//    | '\u0CAA'..'\u0CB3'
	//    | '\u0CB5'..'\u0CB9'
	//    | '\u0CBD'
	//    | '\u0CDE'
	//    | '\u0CE0'..'\u0CE1'
	//    | '\u0CF1'..'\u0CF2'
	//    | '\u0D05'..'\u0D0C'
	//    | '\u0D0E'..'\u0D10'
	//    | '\u0D12'..'\u0D3A'
	//    | '\u0D3D'
	//    | '\u0D4E'
	//    | '\u0D5F'..'\u0D61'
	//    | '\u0D7A'..'\u0D7F'
	//    | '\u0D85'..'\u0D96'
	//    | '\u0D9A'..'\u0DB1'
	//    | '\u0DB3'..'\u0DBB'
	//    | '\u0DBD'
	//    | '\u0DC0'..'\u0DC6'
	//    | '\u0E01'..'\u0E30'
	//    | '\u0E32'..'\u0E33'
	//    | '\u0E40'..'\u0E46'
	//    | '\u0E81'..'\u0E82'
	//    | '\u0E84'
	//    | '\u0E87'..'\u0E88'
	//    | '\u0E8A'
	//    | '\u0E8D'
	//    | '\u0E94'..'\u0E97'
	//    | '\u0E99'..'\u0E9F'
	//    | '\u0EA1'..'\u0EA3'
	//    | '\u0EA5'
	//    | '\u0EA7'
	//    | '\u0EAA'..'\u0EAB'
	//    | '\u0EAD'..'\u0EB0'
	//    | '\u0EB2'..'\u0EB3'
	//    | '\u0EBD'
	//    | '\u0EC0'..'\u0EC4'
	//    | '\u0EC6'
	//    | '\u0EDC'..'\u0EDF'
	//    | '\u0F00'
	//    | '\u0F40'..'\u0F47'
	//    | '\u0F49'..'\u0F6C'
	//    | '\u0F88'..'\u0F8C'
	//    | '\u1000'..'\u102A'
	//    | '\u103F'
	//    | '\u1050'..'\u1055'
	//    | '\u105A'..'\u105D'
	//    | '\u1061'
	//    | '\u1065'..'\u1066'
	//    | '\u106E'..'\u1070'
	//    | '\u1075'..'\u1081'
	//    | '\u108E'
	//    | '\u10A0'..'\u10C5'
	//    | '\u10C7'
	//    | '\u10CD'
	//    | '\u10D0'..'\u10FA'
	//    | '\u10FC'..'\u1248'
	//    | '\u124A'..'\u124D'
	//    | '\u1250'..'\u1256'
	//    | '\u1258'
	//    | '\u125A'..'\u125D'
	//    | '\u1260'..'\u1288'
	//    | '\u128A'..'\u128D'
	//    | '\u1290'..'\u12B0'
	//    | '\u12B2'..'\u12B5'
	//    | '\u12B8'..'\u12BE'
	//    | '\u12C0'
	//    | '\u12C2'..'\u12C5'
	//    | '\u12C8'..'\u12D6'
	//    | '\u12D8'..'\u1310'
	//    | '\u1312'..'\u1315'
	//    | '\u1318'..'\u135A'
	//    | '\u1380'..'\u138F'
	//    | '\u13A0'..'\u13F5'
	//    | '\u13F8'..'\u13FD'
	//    | '\u1401'..'\u166C'
	//    | '\u166F'..'\u167F'
	//    | '\u1681'..'\u169A'
	//    | '\u16A0'..'\u16EA'
	//    | '\u16EE'..'\u16F8'
	//    | '\u1700'..'\u170C'
	//    | '\u170E'..'\u1711'
	//    | '\u1720'..'\u1731'
	//    | '\u1740'..'\u1751'
	//    | '\u1760'..'\u176C'
	//    | '\u176E'..'\u1770'
	//    | '\u1780'..'\u17B3'
	//    | '\u17D7'
	//    | '\u17DC'
	//    | '\u1820'..'\u1877'
	//    | '\u1880'..'\u18A8'
	//    | '\u18AA'
	//    | '\u18B0'..'\u18F5'
	//    | '\u1900'..'\u191E'
	//    | '\u1950'..'\u196D'
	//    | '\u1970'..'\u1974'
	//    | '\u1980'..'\u19AB'
	//    | '\u19B0'..'\u19C9'
	//    | '\u1A00'..'\u1A16'
	//    | '\u1A20'..'\u1A54'
	//    | '\u1AA7'
	//    | '\u1B05'..'\u1B33'
	//    | '\u1B45'..'\u1B4B'
	//    | '\u1B83'..'\u1BA0'
	//    | '\u1BAE'..'\u1BAF'
	//    | '\u1BBA'..'\u1BE5'
	//    | '\u1C00'..'\u1C23'
	//    | '\u1C4D'..'\u1C4F'
	//    | '\u1C5A'..'\u1C7D'
	//    | '\u1CE9'..'\u1CEC'
	//    | '\u1CEE'..'\u1CF1'
	//    | '\u1CF5'..'\u1CF6'
	//    | '\u1D00'..'\u1DBF'
	//    | '\u1E00'..'\u1F15'
	//    | '\u1F18'..'\u1F1D'
	//    | '\u1F20'..'\u1F45'
	//    | '\u1F48'..'\u1F4D'
	//    | '\u1F50'..'\u1F57'
	//    | '\u1F59'
	//    | '\u1F5B'
	//    | '\u1F5D'
	//    | '\u1F5F'..'\u1F7D'
	//    | '\u1F80'..'\u1FB4'
	//    | '\u1FB6'..'\u1FBC'
	//    | '\u1FBE'
	//    | '\u1FC2'..'\u1FC4'
	//    | '\u1FC6'..'\u1FCC'
	//    | '\u1FD0'..'\u1FD3'
	//    | '\u1FD6'..'\u1FDB'
	//    | '\u1FE0'..'\u1FEC'
	//    | '\u1FF2'..'\u1FF4'
	//    | '\u1FF6'..'\u1FFC'
	//    | '\u2071'
	//    | '\u207F'
	//    | '\u2090'..'\u209C'
	//    | '\u2102'
	//    | '\u2107'
	//    | '\u210A'..'\u2113'
	//    | '\u2115'
	//    | '\u2119'..'\u211D'
	//    | '\u2124'
	//    | '\u2126'
	//    | '\u2128'
	//    | '\u212A'..'\u212D'
	//    | '\u212F'..'\u2139'
	//    | '\u213C'..'\u213F'
	//    | '\u2145'..'\u2149'
	//    | '\u214E'
	//    | '\u2160'..'\u2188'
	//    | '\u2C00'..'\u2C2E'
	//    | '\u2C30'..'\u2C5E'
	//    | '\u2C60'..'\u2CE4'
	//    | '\u2CEB'..'\u2CEE'
	//    | '\u2CF2'..'\u2CF3'
	//    | '\u2D00'..'\u2D25'
	//    | '\u2D27'
	//    | '\u2D2D'
	//    | '\u2D30'..'\u2D67'
	//    | '\u2D6F'
	//    | '\u2D80'..'\u2D96'
	//    | '\u2DA0'..'\u2DA6'
	//    | '\u2DA8'..'\u2DAE'
	//    | '\u2DB0'..'\u2DB6'
	//    | '\u2DB8'..'\u2DBE'
	//    | '\u2DC0'..'\u2DC6'
	//    | '\u2DC8'..'\u2DCE'
	//    | '\u2DD0'..'\u2DD6'
	//    | '\u2DD8'..'\u2DDE'
	//    | '\u2E2F'
	//    | '\u3005'..'\u3007'
	//    | '\u3021'..'\u3029'
	//    | '\u3031'..'\u3035'
	//    | '\u3038'..'\u303C'
	//    | '\u3041'..'\u3096'
	//    | '\u309D'..'\u309F'
	//    | '\u30A1'..'\u30FA'
	//    | '\u30FC'..'\u30FF'
	//    | '\u3105'..'\u312D'
	//    | '\u3131'..'\u318E'
	//    | '\u31A0'..'\u31BA'
	//    | '\u31F0'..'\u31FF'
	//    | '\u3400'..'\u4DB5'
	//    | '\u4E00'..'\u9FD5'
	//    | '\uA000'..'\uA48C'
	//    | '\uA4D0'..'\uA4FD'
	//    | '\uA500'..'\uA60C'
	//    | '\uA610'..'\uA61F'
	//    | '\uA62A'..'\uA62B'
	//    | '\uA640'..'\uA66E'
	//    | '\uA67F'..'\uA69D'
	//    | '\uA6A0'..'\uA6EF'
	//    | '\uA717'..'\uA71F'
	//    | '\uA722'..'\uA788'
	//    | '\uA78B'..'\uA7AD'
	//    | '\uA7B0'..'\uA7B7'
	//    | '\uA7F7'..'\uA801'
	//    | '\uA803'..'\uA805'
	//    | '\uA807'..'\uA80A'
	//    | '\uA80C'..'\uA822'
	//    | '\uA840'..'\uA873'
	//    | '\uA882'..'\uA8B3'
	//    | '\uA8F2'..'\uA8F7'
	//    | '\uA8FB'
	//    | '\uA8FD'
	//    | '\uA90A'..'\uA925'
	//    | '\uA930'..'\uA946'
	//    | '\uA960'..'\uA97C'
	//    | '\uA984'..'\uA9B2'
	//    | '\uA9CF'
	//    | '\uA9E0'..'\uA9E4'
	//    | '\uA9E6'..'\uA9EF'
	//    | '\uA9FA'..'\uA9FE'
	//    | '\uAA00'..'\uAA28'
	//    | '\uAA40'..'\uAA42'
	//    | '\uAA44'..'\uAA4B'
	//    | '\uAA60'..'\uAA76'
	//    | '\uAA7A'
	//    | '\uAA7E'..'\uAAAF'
	//    | '\uAAB1'
	//    | '\uAAB5'..'\uAAB6'
	//    | '\uAAB9'..'\uAABD'
	//    | '\uAAC0'
	//    | '\uAAC2'
	//    | '\uAADB'..'\uAADD'
	//    | '\uAAE0'..'\uAAEA'
	//    | '\uAAF2'..'\uAAF4'
	//    | '\uAB01'..'\uAB06'
	//    | '\uAB09'..'\uAB0E'
	//    | '\uAB11'..'\uAB16'
	//    | '\uAB20'..'\uAB26'
	//    | '\uAB28'..'\uAB2E'
	//    | '\uAB30'..'\uAB5A'
	//    | '\uAB5C'..'\uAB65'
	//    | '\uAB70'..'\uABE2'
	//    | '\uAC00'..'\uD7A3'
	//    | '\uD7B0'..'\uD7C6'
	//    | '\uD7CB'..'\uD7FB'
	//    | '\uF900'..'\uFA6D'
	//    | '\uFA70'..'\uFAD9'
	//    | '\uFB00'..'\uFB06'
	//    | '\uFB13'..'\uFB17'
	//    | '\uFB1D'
	//    | '\uFB1F'..'\uFB28'
	//    | '\uFB2A'..'\uFB36'
	//    | '\uFB38'..'\uFB3C'
	//    | '\uFB3E'
	//    | '\uFB40'..'\uFB41'
	//    | '\uFB43'..'\uFB44'
	//    | '\uFB46'..'\uFBB1'
	//    | '\uFBD3'..'\uFD3D'
	//    | '\uFD50'..'\uFD8F'
	//    | '\uFD92'..'\uFDC7'
	//    | '\uFDF0'..'\uFDFB'
	//    | '\uFE70'..'\uFE74'
	//    | '\uFE76'..'\uFEFC'
	//    | '\uFF21'..'\uFF3A'
	//    | '\uFF41'..'\uFF5A'
	//    | '\uFF66'..'\uFFBE'
	//    | '\uFFC2'..'\uFFC7'
	//    | '\uFFCA'..'\uFFCF'
	//    | '\uFFD2'..'\uFFD7'
	//    | '\uFFDA'..'\uFFDC'
	//;
	public TerminalRule getUNICODE_LETTER_FRAGMENTRule() {
		return gaUnicode.getUNICODE_LETTER_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_SPACE_SEPARATOR_FRAGMENT:
	//    // any character in the Unicode categories
	//    // ―space separator (Zs)
	//      '\u0020'
	//    | '\u00A0'
	//    | '\u1680'
	//    | '\u2000'..'\u200A'
	//    | '\u202F'
	//    | '\u205F'
	//    | '\u3000'
	//;
	public TerminalRule getUNICODE_SPACE_SEPARATOR_FRAGMENTRule() {
		return gaUnicode.getUNICODE_SPACE_SEPARATOR_FRAGMENTRule();
	}
	
	//terminal fragment ANY_OTHER:
	//    .
	//;
	public TerminalRule getANY_OTHERRule() {
		return gaUnicode.getANY_OTHERRule();
	}
}
