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
package org.eclipse.n4js.ts.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.n4js.common.unicode.services.UnicodeGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class TypeExpressionsGrammarAccess extends AbstractGrammarElementFinder {
	
	public class TypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeRef");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cIntersectionTypeExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cUnionTypeExpressionTypeRefsAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Keyword cVerticalLineKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cTypeRefsAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cTypeRefsIntersectionTypeExpressionParserRuleCall_1_1_1_0 = (RuleCall)cTypeRefsAssignment_1_1_1.eContents().get(0);
		
		//// ****************************************************************************************************
		//// N4JS versions of type references and expressions, also used by Types.xtext
		////
		//// References:
		////
		//// [ECM15]	ECMAScript 2015 Language Specification / ISO/IEC (ECMA-262, 6th Edition).
		////			International Standard.
		////			http://www.ecma-international.org/publications/ files/ECMA-ST/Ecma-262.pdf
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
		// */ TypeRef:
		//	IntersectionTypeExpression ({UnionTypeExpression.typeRefs+=current} ("|" typeRefs+=IntersectionTypeExpression)+)?;
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.IntersectionTypeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cArrayTypeExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cIntersectionTypeExpressionTypeRefsAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Keyword cAmpersandKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cTypeRefsAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cTypeRefsArrayTypeExpressionParserRuleCall_1_1_1_0 = (RuleCall)cTypeRefsAssignment_1_1_1.eContents().get(0);
		
		//IntersectionTypeExpression TypeRef:
		//	ArrayTypeExpression ({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=ArrayTypeExpression)+)?;
		@Override public ParserRule getRule() { return rule; }
		
		//ArrayTypeExpression ({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=ArrayTypeExpression)+)?
		public Group getGroup() { return cGroup; }
		
		//ArrayTypeExpression
		public RuleCall getArrayTypeExpressionParserRuleCall_0() { return cArrayTypeExpressionParserRuleCall_0; }
		
		//({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=ArrayTypeExpression)+)?
		public Group getGroup_1() { return cGroup_1; }
		
		//{IntersectionTypeExpression.typeRefs+=current}
		public Action getIntersectionTypeExpressionTypeRefsAction_1_0() { return cIntersectionTypeExpressionTypeRefsAction_1_0; }
		
		//("&" typeRefs+=ArrayTypeExpression)+
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//"&"
		public Keyword getAmpersandKeyword_1_1_0() { return cAmpersandKeyword_1_1_0; }
		
		//typeRefs+=ArrayTypeExpression
		public Assignment getTypeRefsAssignment_1_1_1() { return cTypeRefsAssignment_1_1_1; }
		
		//ArrayTypeExpression
		public RuleCall getTypeRefsArrayTypeExpressionParserRuleCall_1_1_1_0() { return cTypeRefsArrayTypeExpressionParserRuleCall_1_1_1_0; }
	}
	public class ArrayTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ArrayTypeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cPrimaryTypeExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cParameterizedTypeRefTypeArgsAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cArrayTypeExpressionAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final Keyword cArrayTypeExpressionLeftSquareBracketKeyword_1_0_1_0 = (Keyword)cArrayTypeExpressionAssignment_1_0_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1_0_2 = (Keyword)cGroup_1_0.eContents().get(2);
		
		//ArrayTypeExpression TypeRef:
		//	PrimaryTypeExpression => ({ParameterizedTypeRef.typeArgs+=current} arrayTypeExpression?='[' ']')*;
		@Override public ParserRule getRule() { return rule; }
		
		//PrimaryTypeExpression => ({ParameterizedTypeRef.typeArgs+=current} arrayTypeExpression?='[' ']')*
		public Group getGroup() { return cGroup; }
		
		//PrimaryTypeExpression
		public RuleCall getPrimaryTypeExpressionParserRuleCall_0() { return cPrimaryTypeExpressionParserRuleCall_0; }
		
		//=> ({ParameterizedTypeRef.typeArgs+=current} arrayTypeExpression?='[' ']')*
		public Group getGroup_1() { return cGroup_1; }
		
		//{ParameterizedTypeRef.typeArgs+=current} arrayTypeExpression?='[' ']'
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{ParameterizedTypeRef.typeArgs+=current}
		public Action getParameterizedTypeRefTypeArgsAction_1_0_0() { return cParameterizedTypeRefTypeArgsAction_1_0_0; }
		
		//arrayTypeExpression?='['
		public Assignment getArrayTypeExpressionAssignment_1_0_1() { return cArrayTypeExpressionAssignment_1_0_1; }
		
		//'['
		public Keyword getArrayTypeExpressionLeftSquareBracketKeyword_1_0_1_0() { return cArrayTypeExpressionLeftSquareBracketKeyword_1_0_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1_0_2() { return cRightSquareBracketKeyword_1_0_2; }
	}
	public class PrimaryTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.PrimaryTypeExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cArrowFunctionTypeExpressionParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cIterableTypeExpressionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cTypeRefWithModifiersParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final Group cGroup_3 = (Group)cAlternatives.eContents().get(3);
		private final Keyword cLeftParenthesisKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final RuleCall cTypeRefParserRuleCall_3_1 = (RuleCall)cGroup_3.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_3_2 = (Keyword)cGroup_3.eContents().get(2);
		
		//PrimaryTypeExpression TypeRef:
		//	ArrowFunctionTypeExpression
		//	| IterableTypeExpression
		//	| TypeRefWithModifiers
		//	| "(" TypeRef ")";
		@Override public ParserRule getRule() { return rule; }
		
		//ArrowFunctionTypeExpression | IterableTypeExpression | TypeRefWithModifiers | "(" TypeRef ")"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ArrowFunctionTypeExpression
		public RuleCall getArrowFunctionTypeExpressionParserRuleCall_0() { return cArrowFunctionTypeExpressionParserRuleCall_0; }
		
		//IterableTypeExpression
		public RuleCall getIterableTypeExpressionParserRuleCall_1() { return cIterableTypeExpressionParserRuleCall_1; }
		
		//TypeRefWithModifiers
		public RuleCall getTypeRefWithModifiersParserRuleCall_2() { return cTypeRefWithModifiersParserRuleCall_2; }
		
		//"(" TypeRef ")"
		public Group getGroup_3() { return cGroup_3; }
		
		//"("
		public Keyword getLeftParenthesisKeyword_3_0() { return cLeftParenthesisKeyword_3_0; }
		
		//TypeRef
		public RuleCall getTypeRefParserRuleCall_3_1() { return cTypeRefParserRuleCall_3_1; }
		
		//")"
		public Keyword getRightParenthesisKeyword_3_2() { return cRightParenthesisKeyword_3_2; }
	}
	public class TypeRefWithModifiersElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cTypeRefWithoutModifiersParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Assignment cFollowedByQuestionMarkAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cFollowedByQuestionMarkQuestionMarkKeyword_1_0 = (Keyword)cFollowedByQuestionMarkAssignment_1.eContents().get(0);
		
		//TypeRefWithModifiers StaticBaseTypeRef:
		//	TypeRefWithoutModifiers => followedByQuestionMark?='?'?;
		@Override public ParserRule getRule() { return rule; }
		
		//TypeRefWithoutModifiers => followedByQuestionMark?='?'?
		public Group getGroup() { return cGroup; }
		
		//TypeRefWithoutModifiers
		public RuleCall getTypeRefWithoutModifiersParserRuleCall_0() { return cTypeRefWithoutModifiersParserRuleCall_0; }
		
		//=> followedByQuestionMark?='?'?
		public Assignment getFollowedByQuestionMarkAssignment_1() { return cFollowedByQuestionMarkAssignment_1; }
		
		//'?'
		public Keyword getFollowedByQuestionMarkQuestionMarkKeyword_1_0() { return cFollowedByQuestionMarkQuestionMarkKeyword_1_0; }
	}
	public class TypeRefWithoutModifiersElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeRefWithoutModifiers");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Alternatives cAlternatives_0_0 = (Alternatives)cGroup_0.eContents().get(0);
		private final RuleCall cParameterizedTypeRefParserRuleCall_0_0_0 = (RuleCall)cAlternatives_0_0.eContents().get(0);
		private final RuleCall cThisTypeRefParserRuleCall_0_0_1 = (RuleCall)cAlternatives_0_0.eContents().get(1);
		private final Assignment cDynamicAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
		private final Keyword cDynamicPlusSignKeyword_0_1_0 = (Keyword)cDynamicAssignment_0_1.eContents().get(0);
		private final RuleCall cTypeTypeRefParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cFunctionTypeExpressionOLDParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cUnionTypeExpressionOLDParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cIntersectionTypeExpressionOLDParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		
		//TypeRefWithoutModifiers StaticBaseTypeRef:
		//	(ParameterizedTypeRef | ThisTypeRef) => dynamic?='+'? | TypeTypeRef
		//	| FunctionTypeExpressionOLD
		//	| UnionTypeExpressionOLD
		//	| IntersectionTypeExpressionOLD;
		@Override public ParserRule getRule() { return rule; }
		
		//(ParameterizedTypeRef | ThisTypeRef) => dynamic?='+'? | TypeTypeRef | FunctionTypeExpressionOLD | UnionTypeExpressionOLD
		//| IntersectionTypeExpressionOLD
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//(ParameterizedTypeRef | ThisTypeRef) => dynamic?='+'?
		public Group getGroup_0() { return cGroup_0; }
		
		//ParameterizedTypeRef | ThisTypeRef
		public Alternatives getAlternatives_0_0() { return cAlternatives_0_0; }
		
		//ParameterizedTypeRef
		public RuleCall getParameterizedTypeRefParserRuleCall_0_0_0() { return cParameterizedTypeRefParserRuleCall_0_0_0; }
		
		//ThisTypeRef
		public RuleCall getThisTypeRefParserRuleCall_0_0_1() { return cThisTypeRefParserRuleCall_0_0_1; }
		
		//=> dynamic?='+'?
		public Assignment getDynamicAssignment_0_1() { return cDynamicAssignment_0_1; }
		
		//'+'
		public Keyword getDynamicPlusSignKeyword_0_1_0() { return cDynamicPlusSignKeyword_0_1_0; }
		
		//TypeTypeRef
		public RuleCall getTypeTypeRefParserRuleCall_1() { return cTypeTypeRefParserRuleCall_1; }
		
		//FunctionTypeExpressionOLD
		public RuleCall getFunctionTypeExpressionOLDParserRuleCall_2() { return cFunctionTypeExpressionOLDParserRuleCall_2; }
		
		//UnionTypeExpressionOLD
		public RuleCall getUnionTypeExpressionOLDParserRuleCall_3() { return cUnionTypeExpressionOLDParserRuleCall_3; }
		
		//IntersectionTypeExpressionOLD
		public RuleCall getIntersectionTypeExpressionOLDParserRuleCall_4() { return cIntersectionTypeExpressionOLDParserRuleCall_4; }
	}
	public class TypeRefFunctionTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeRefFunctionTypeExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cParameterizedTypeRefParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cIterableTypeExpressionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cTypeTypeRefParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cUnionTypeExpressionOLDParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cIntersectionTypeExpressionOLDParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		
		//TypeRefFunctionTypeExpression StaticBaseTypeRef:
		//	ParameterizedTypeRef
		//	| IterableTypeExpression
		//	| TypeTypeRef
		//	| UnionTypeExpressionOLD
		//	| IntersectionTypeExpressionOLD;
		@Override public ParserRule getRule() { return rule; }
		
		//ParameterizedTypeRef | IterableTypeExpression | TypeTypeRef | UnionTypeExpressionOLD | IntersectionTypeExpressionOLD
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ParameterizedTypeRef
		public RuleCall getParameterizedTypeRefParserRuleCall_0() { return cParameterizedTypeRefParserRuleCall_0; }
		
		//IterableTypeExpression
		public RuleCall getIterableTypeExpressionParserRuleCall_1() { return cIterableTypeExpressionParserRuleCall_1; }
		
		//TypeTypeRef
		public RuleCall getTypeTypeRefParserRuleCall_2() { return cTypeTypeRefParserRuleCall_2; }
		
		//UnionTypeExpressionOLD
		public RuleCall getUnionTypeExpressionOLDParserRuleCall_3() { return cUnionTypeExpressionOLDParserRuleCall_3; }
		
		//IntersectionTypeExpressionOLD
		public RuleCall getIntersectionTypeExpressionOLDParserRuleCall_4() { return cIntersectionTypeExpressionOLDParserRuleCall_4; }
	}
	public class TypeArgInTypeTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeArgInTypeTypeRef");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cParameterizedTypeRefNominalParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cThisTypeRefNominalParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cWildcardParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//TypeArgInTypeTypeRef TypeArgument:
		//	ParameterizedTypeRefNominal
		//	| ThisTypeRefNominal
		//	| Wildcard;
		@Override public ParserRule getRule() { return rule; }
		
		//ParameterizedTypeRefNominal | ThisTypeRefNominal | Wildcard
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getParameterizedTypeRefNominalParserRuleCall_0() { return cParameterizedTypeRefNominalParserRuleCall_0; }
		
		//ThisTypeRefNominal
		public RuleCall getThisTypeRefNominalParserRuleCall_1() { return cThisTypeRefNominalParserRuleCall_1; }
		
		//Wildcard
		public RuleCall getWildcardParserRuleCall_2() { return cWildcardParserRuleCall_2; }
	}
	public class ThisTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ThisTypeRef");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cThisTypeRefNominalParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cThisTypeRefStructuralParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//ThisTypeRef:
		//	ThisTypeRefNominal | ThisTypeRefStructural;
		@Override public ParserRule getRule() { return rule; }
		
		//ThisTypeRefNominal | ThisTypeRefStructural
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ThisTypeRefNominal
		public RuleCall getThisTypeRefNominalParserRuleCall_0() { return cThisTypeRefNominalParserRuleCall_0; }
		
		//ThisTypeRefStructural
		public RuleCall getThisTypeRefStructuralParserRuleCall_1() { return cThisTypeRefStructuralParserRuleCall_1; }
	}
	public class ThisTypeRefNominalElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ThisTypeRefNominal");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cThisTypeRefNominalAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cThisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//ThisTypeRefNominal:
		//	{ThisTypeRefNominal} 'this';
		@Override public ParserRule getRule() { return rule; }
		
		//{ThisTypeRefNominal} 'this'
		public Group getGroup() { return cGroup; }
		
		//{ThisTypeRefNominal}
		public Action getThisTypeRefNominalAction_0() { return cThisTypeRefNominalAction_0; }
		
		//'this'
		public Keyword getThisKeyword_1() { return cThisKeyword_1; }
	}
	public class ThisTypeRefStructuralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ThisTypeRefStructural");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDefinedTypingStrategyAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0 = (RuleCall)cDefinedTypingStrategyAssignment_0.eContents().get(0);
		private final Keyword cThisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cWithKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final RuleCall cTStructMemberListParserRuleCall_2_1 = (RuleCall)cGroup_2.eContents().get(1);
		
		//ThisTypeRefStructural:
		//	definedTypingStrategy=TypingStrategyUseSiteOperator
		//	'this' ('with' TStructMemberList)?;
		@Override public ParserRule getRule() { return rule; }
		
		//definedTypingStrategy=TypingStrategyUseSiteOperator 'this' ('with' TStructMemberList)?
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
	public class FunctionTypeExpressionOLDElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.FunctionTypeExpressionOLD");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cFunctionTypeExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cCommercialAtKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Keyword cThisKeyword_2_1 = (Keyword)cGroup_2.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_2_2 = (Keyword)cGroup_2.eContents().get(2);
		private final Assignment cDeclaredThisTypeAssignment_2_3 = (Assignment)cGroup_2.eContents().get(3);
		private final RuleCall cDeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0 = (RuleCall)cDeclaredThisTypeAssignment_2_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_2_4 = (Keyword)cGroup_2.eContents().get(4);
		private final Keyword cFunctionKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cLessThanSignKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cOwnedTypeVarsAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cOwnedTypeVarsTypeVariableParserRuleCall_4_1_0 = (RuleCall)cOwnedTypeVarsAssignment_4_1.eContents().get(0);
		private final Group cGroup_4_2 = (Group)cGroup_4.eContents().get(2);
		private final Keyword cCommaKeyword_4_2_0 = (Keyword)cGroup_4_2.eContents().get(0);
		private final Assignment cOwnedTypeVarsAssignment_4_2_1 = (Assignment)cGroup_4_2.eContents().get(1);
		private final RuleCall cOwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0 = (RuleCall)cOwnedTypeVarsAssignment_4_2_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_4_3 = (Keyword)cGroup_4.eContents().get(3);
		private final Keyword cLeftParenthesisKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final RuleCall cTAnonymousFormalParameterListParserRuleCall_6 = (RuleCall)cGroup.eContents().get(6);
		private final Keyword cRightParenthesisKeyword_7 = (Keyword)cGroup.eContents().get(7);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_8 = (RuleCall)cGroup.eContents().get(8);
		private final Keyword cRightCurlyBracketKeyword_9 = (Keyword)cGroup.eContents().get(9);
		
		//FunctionTypeExpressionOLD FunctionTypeExpression:
		//	{FunctionTypeExpression}
		//	'{' ('@' 'This' '(' declaredThisType=TypeRefFunctionTypeExpression ')')?
		//	'function' ('<' ownedTypeVars+=TypeVariable (',' ownedTypeVars+=TypeVariable)* '>')?
		//	'(' TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{FunctionTypeExpression} '{' ('@' 'This' '(' declaredThisType=TypeRefFunctionTypeExpression ')')? 'function' ('<'
		//ownedTypeVars+=TypeVariable (',' ownedTypeVars+=TypeVariable)* '>')? '(' TAnonymousFormalParameterList ')'
		//ColonSepReturnTypeRef? '}'
		public Group getGroup() { return cGroup; }
		
		//{FunctionTypeExpression}
		public Action getFunctionTypeExpressionAction_0() { return cFunctionTypeExpressionAction_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//('@' 'This' '(' declaredThisType=TypeRefFunctionTypeExpression ')')?
		public Group getGroup_2() { return cGroup_2; }
		
		//'@'
		public Keyword getCommercialAtKeyword_2_0() { return cCommercialAtKeyword_2_0; }
		
		//'This'
		public Keyword getThisKeyword_2_1() { return cThisKeyword_2_1; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2_2() { return cLeftParenthesisKeyword_2_2; }
		
		//declaredThisType=TypeRefFunctionTypeExpression
		public Assignment getDeclaredThisTypeAssignment_2_3() { return cDeclaredThisTypeAssignment_2_3; }
		
		//TypeRefFunctionTypeExpression
		public RuleCall getDeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0() { return cDeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2_4() { return cRightParenthesisKeyword_2_4; }
		
		//'function'
		public Keyword getFunctionKeyword_3() { return cFunctionKeyword_3; }
		
		//('<' ownedTypeVars+=TypeVariable (',' ownedTypeVars+=TypeVariable)* '>')?
		public Group getGroup_4() { return cGroup_4; }
		
		//'<'
		public Keyword getLessThanSignKeyword_4_0() { return cLessThanSignKeyword_4_0; }
		
		//ownedTypeVars+=TypeVariable
		public Assignment getOwnedTypeVarsAssignment_4_1() { return cOwnedTypeVarsAssignment_4_1; }
		
		//TypeVariable
		public RuleCall getOwnedTypeVarsTypeVariableParserRuleCall_4_1_0() { return cOwnedTypeVarsTypeVariableParserRuleCall_4_1_0; }
		
		//(',' ownedTypeVars+=TypeVariable)*
		public Group getGroup_4_2() { return cGroup_4_2; }
		
		//','
		public Keyword getCommaKeyword_4_2_0() { return cCommaKeyword_4_2_0; }
		
		//ownedTypeVars+=TypeVariable
		public Assignment getOwnedTypeVarsAssignment_4_2_1() { return cOwnedTypeVarsAssignment_4_2_1; }
		
		//TypeVariable
		public RuleCall getOwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0() { return cOwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_4_3() { return cGreaterThanSignKeyword_4_3; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_5() { return cLeftParenthesisKeyword_5; }
		
		//TAnonymousFormalParameterList
		public RuleCall getTAnonymousFormalParameterListParserRuleCall_6() { return cTAnonymousFormalParameterListParserRuleCall_6; }
		
		//')'
		public Keyword getRightParenthesisKeyword_7() { return cRightParenthesisKeyword_7; }
		
		//ColonSepReturnTypeRef?
		public RuleCall getColonSepReturnTypeRefParserRuleCall_8() { return cColonSepReturnTypeRefParserRuleCall_8; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_9() { return cRightCurlyBracketKeyword_9; }
	}
	public class ArrowFunctionTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ArrowFunctionTypeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cFunctionTypeExpressionAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final RuleCall cTAnonymousFormalParameterListParserRuleCall_0_0_2 = (RuleCall)cGroup_0_0.eContents().get(2);
		private final Keyword cRightParenthesisKeyword_0_0_3 = (Keyword)cGroup_0_0.eContents().get(3);
		private final Keyword cEqualsSignGreaterThanSignKeyword_0_0_4 = (Keyword)cGroup_0_0.eContents().get(4);
		private final Assignment cReturnTypeRefAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0 = (RuleCall)cReturnTypeRefAssignment_1.eContents().get(0);
		
		//ArrowFunctionTypeExpression FunctionTypeExpression:
		//	=> ({FunctionTypeExpression} '(' TAnonymousFormalParameterList ')' '=>') returnTypeRef=PrimaryTypeExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({FunctionTypeExpression} '(' TAnonymousFormalParameterList ')' '=>') returnTypeRef=PrimaryTypeExpression
		public Group getGroup() { return cGroup; }
		
		//=> ({FunctionTypeExpression} '(' TAnonymousFormalParameterList ')' '=>')
		public Group getGroup_0() { return cGroup_0; }
		
		//{FunctionTypeExpression} '(' TAnonymousFormalParameterList ')' '=>'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{FunctionTypeExpression}
		public Action getFunctionTypeExpressionAction_0_0_0() { return cFunctionTypeExpressionAction_0_0_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_0_0_1() { return cLeftParenthesisKeyword_0_0_1; }
		
		//TAnonymousFormalParameterList
		public RuleCall getTAnonymousFormalParameterListParserRuleCall_0_0_2() { return cTAnonymousFormalParameterListParserRuleCall_0_0_2; }
		
		//')'
		public Keyword getRightParenthesisKeyword_0_0_3() { return cRightParenthesisKeyword_0_0_3; }
		
		//'=>'
		public Keyword getEqualsSignGreaterThanSignKeyword_0_0_4() { return cEqualsSignGreaterThanSignKeyword_0_0_4; }
		
		//returnTypeRef=PrimaryTypeExpression
		public Assignment getReturnTypeRefAssignment_1() { return cReturnTypeRefAssignment_1; }
		
		//PrimaryTypeExpression
		public RuleCall getReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0() { return cReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0; }
	}
	public class TAnonymousFormalParameterListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TAnonymousFormalParameterList");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Assignment cFparsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cFparsTAnonymousFormalParameterParserRuleCall_0_0 = (RuleCall)cFparsAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cCommaKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cFparsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cFparsTAnonymousFormalParameterParserRuleCall_1_1_0 = (RuleCall)cFparsAssignment_1_1.eContents().get(0);
		
		//// TODO extract FormalParameterContainer and use returns FormalParameterContainer instead of wildcard
		//fragment TAnonymousFormalParameterList *:
		//	(fpars+=TAnonymousFormalParameter (',' fpars+=TAnonymousFormalParameter)*)?;
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
	public class TAnonymousFormalParameterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TAnonymousFormalParameter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cVariadicAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cVariadicFullStopFullStopFullStopKeyword_0_0 = (Keyword)cVariadicAssignment_0.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Assignment cNameAssignment_1_0_0_0 = (Assignment)cGroup_1_0_0.eContents().get(0);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_0_0_0_0 = (RuleCall)cNameAssignment_1_0_0_0.eContents().get(0);
		private final RuleCall cColonSepTypeRefParserRuleCall_1_0_0_1 = (RuleCall)cGroup_1_0_0.eContents().get(1);
		private final Assignment cTypeRefAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final RuleCall cTypeRefTypeRefParserRuleCall_1_1_0 = (RuleCall)cTypeRefAssignment_1_1.eContents().get(0);
		private final RuleCall cDefaultFormalParameterParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		///**
		// * Used in type expressions, name is optional.
		// */ TAnonymousFormalParameter:
		//	variadic?='...'? (=> (name=BindingIdentifier<Yield=false> -> ColonSepTypeRef) | typeRef=TypeRef)
		//	DefaultFormalParameter;
		@Override public ParserRule getRule() { return rule; }
		
		//variadic?='...'? (=> (name=BindingIdentifier<Yield=false> -> ColonSepTypeRef) | typeRef=TypeRef) DefaultFormalParameter
		public Group getGroup() { return cGroup; }
		
		//variadic?='...'?
		public Assignment getVariadicAssignment_0() { return cVariadicAssignment_0; }
		
		//'...'
		public Keyword getVariadicFullStopFullStopFullStopKeyword_0_0() { return cVariadicFullStopFullStopFullStopKeyword_0_0; }
		
		//=> (name=BindingIdentifier<Yield=false> -> ColonSepTypeRef) | typeRef=TypeRef
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//=> (name=BindingIdentifier<Yield=false> -> ColonSepTypeRef)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//name=BindingIdentifier<Yield=false> -> ColonSepTypeRef
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//name=BindingIdentifier<Yield=false>
		public Assignment getNameAssignment_1_0_0_0() { return cNameAssignment_1_0_0_0; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_0_0_0_0() { return cNameBindingIdentifierParserRuleCall_1_0_0_0_0; }
		
		//-> ColonSepTypeRef
		public RuleCall getColonSepTypeRefParserRuleCall_1_0_0_1() { return cColonSepTypeRefParserRuleCall_1_0_0_1; }
		
		//typeRef=TypeRef
		public Assignment getTypeRefAssignment_1_1() { return cTypeRefAssignment_1_1; }
		
		//TypeRef
		public RuleCall getTypeRefTypeRefParserRuleCall_1_1_0() { return cTypeRefTypeRefParserRuleCall_1_1_0; }
		
		//DefaultFormalParameter
		public RuleCall getDefaultFormalParameterParserRuleCall_2() { return cDefaultFormalParameterParserRuleCall_2; }
	}
	public class TFormalParameterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TFormalParameter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cVariadicAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cVariadicFullStopFullStopFullStopKeyword_0_0 = (Keyword)cVariadicAssignment_0.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final RuleCall cColonSepTypeRefParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final RuleCall cDefaultFormalParameterParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		///**
		// * Used in Types language only.
		// */ TFormalParameter:
		//	variadic?='...'? name=BindingIdentifier<Yield=false> ColonSepTypeRef
		//	DefaultFormalParameter;
		@Override public ParserRule getRule() { return rule; }
		
		//variadic?='...'? name=BindingIdentifier<Yield=false> ColonSepTypeRef DefaultFormalParameter
		public Group getGroup() { return cGroup; }
		
		//variadic?='...'?
		public Assignment getVariadicAssignment_0() { return cVariadicAssignment_0; }
		
		//'...'
		public Keyword getVariadicFullStopFullStopFullStopKeyword_0_0() { return cVariadicFullStopFullStopFullStopKeyword_0_0; }
		
		//name=BindingIdentifier<Yield=false>
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_0() { return cNameBindingIdentifierParserRuleCall_1_0; }
		
		//ColonSepTypeRef
		public RuleCall getColonSepTypeRefParserRuleCall_2() { return cColonSepTypeRefParserRuleCall_2; }
		
		//DefaultFormalParameter
		public RuleCall getDefaultFormalParameterParserRuleCall_3() { return cDefaultFormalParameterParserRuleCall_3; }
	}
	public class DefaultFormalParameterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.DefaultFormalParameter");
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
		// */ fragment DefaultFormalParameter *:
		//	(hasInitializerAssignment?='=' astInitializer=TypeReferenceName?)?;
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.UnionTypeExpressionOLD");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cUnionTypeExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cUnionKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cTypeRefsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cTypeRefsTypeRefWithoutModifiersParserRuleCall_3_0 = (RuleCall)cTypeRefsAssignment_3.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cCommaKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cTypeRefsAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cTypeRefsTypeRefWithoutModifiersParserRuleCall_4_1_0 = (RuleCall)cTypeRefsAssignment_4_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_5 = (Keyword)cGroup.eContents().get(5);
		
		//UnionTypeExpressionOLD UnionTypeExpression:
		//	{UnionTypeExpression}
		//	'union' '{' typeRefs+=TypeRefWithoutModifiers (',' typeRefs+=TypeRefWithoutModifiers)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{UnionTypeExpression} 'union' '{' typeRefs+=TypeRefWithoutModifiers (',' typeRefs+=TypeRefWithoutModifiers)* '}'
		public Group getGroup() { return cGroup; }
		
		//{UnionTypeExpression}
		public Action getUnionTypeExpressionAction_0() { return cUnionTypeExpressionAction_0; }
		
		//'union'
		public Keyword getUnionKeyword_1() { return cUnionKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//typeRefs+=TypeRefWithoutModifiers
		public Assignment getTypeRefsAssignment_3() { return cTypeRefsAssignment_3; }
		
		//TypeRefWithoutModifiers
		public RuleCall getTypeRefsTypeRefWithoutModifiersParserRuleCall_3_0() { return cTypeRefsTypeRefWithoutModifiersParserRuleCall_3_0; }
		
		//(',' typeRefs+=TypeRefWithoutModifiers)*
		public Group getGroup_4() { return cGroup_4; }
		
		//','
		public Keyword getCommaKeyword_4_0() { return cCommaKeyword_4_0; }
		
		//typeRefs+=TypeRefWithoutModifiers
		public Assignment getTypeRefsAssignment_4_1() { return cTypeRefsAssignment_4_1; }
		
		//TypeRefWithoutModifiers
		public RuleCall getTypeRefsTypeRefWithoutModifiersParserRuleCall_4_1_0() { return cTypeRefsTypeRefWithoutModifiersParserRuleCall_4_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_5() { return cRightCurlyBracketKeyword_5; }
	}
	public class IntersectionTypeExpressionOLDElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.IntersectionTypeExpressionOLD");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cIntersectionTypeExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cIntersectionKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cTypeRefsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cTypeRefsTypeRefWithoutModifiersParserRuleCall_3_0 = (RuleCall)cTypeRefsAssignment_3.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cCommaKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cTypeRefsAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cTypeRefsTypeRefWithoutModifiersParserRuleCall_4_1_0 = (RuleCall)cTypeRefsAssignment_4_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_5 = (Keyword)cGroup.eContents().get(5);
		
		//IntersectionTypeExpressionOLD IntersectionTypeExpression:
		//	{IntersectionTypeExpression}
		//	'intersection' '{' typeRefs+=TypeRefWithoutModifiers (',' typeRefs+=TypeRefWithoutModifiers)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{IntersectionTypeExpression} 'intersection' '{' typeRefs+=TypeRefWithoutModifiers (','
		//typeRefs+=TypeRefWithoutModifiers)* '}'
		public Group getGroup() { return cGroup; }
		
		//{IntersectionTypeExpression}
		public Action getIntersectionTypeExpressionAction_0() { return cIntersectionTypeExpressionAction_0; }
		
		//'intersection'
		public Keyword getIntersectionKeyword_1() { return cIntersectionKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//typeRefs+=TypeRefWithoutModifiers
		public Assignment getTypeRefsAssignment_3() { return cTypeRefsAssignment_3; }
		
		//TypeRefWithoutModifiers
		public RuleCall getTypeRefsTypeRefWithoutModifiersParserRuleCall_3_0() { return cTypeRefsTypeRefWithoutModifiersParserRuleCall_3_0; }
		
		//(',' typeRefs+=TypeRefWithoutModifiers)*
		public Group getGroup_4() { return cGroup_4; }
		
		//','
		public Keyword getCommaKeyword_4_0() { return cCommaKeyword_4_0; }
		
		//typeRefs+=TypeRefWithoutModifiers
		public Assignment getTypeRefsAssignment_4_1() { return cTypeRefsAssignment_4_1; }
		
		//TypeRefWithoutModifiers
		public RuleCall getTypeRefsTypeRefWithoutModifiersParserRuleCall_4_1_0() { return cTypeRefsTypeRefWithoutModifiersParserRuleCall_4_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_5() { return cRightCurlyBracketKeyword_5; }
	}
	public class ParameterizedTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRef");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cParameterizedTypeRefNominalParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cParameterizedTypeRefStructuralParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//ParameterizedTypeRef:
		//	ParameterizedTypeRefNominal | ParameterizedTypeRefStructural;
		@Override public ParserRule getRule() { return rule; }
		
		//ParameterizedTypeRefNominal | ParameterizedTypeRefStructural
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getParameterizedTypeRefNominalParserRuleCall_0() { return cParameterizedTypeRefNominalParserRuleCall_0; }
		
		//ParameterizedTypeRefStructural
		public RuleCall getParameterizedTypeRefStructuralParserRuleCall_1() { return cParameterizedTypeRefStructuralParserRuleCall_1; }
	}
	public class ParameterizedTypeRefNominalElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final RuleCall cTypeReferenceParserRuleCall_0_0 = (RuleCall)cAlternatives_0.eContents().get(0);
		private final Group cGroup_0_1 = (Group)cAlternatives_0.eContents().get(1);
		private final Action cVersionedParameterizedTypeRefAction_0_1_0 = (Action)cGroup_0_1.eContents().get(0);
		private final RuleCall cTypeReferenceParserRuleCall_0_1_1 = (RuleCall)cGroup_0_1.eContents().get(1);
		private final RuleCall cVersionRequestParserRuleCall_0_1_2 = (RuleCall)cGroup_0_1.eContents().get(2);
		private final RuleCall cTypeArgumentsParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//ParameterizedTypeRefNominal ParameterizedTypeRef:
		//	(TypeReference
		//	| {VersionedParameterizedTypeRef} TypeReference VersionRequest) -> TypeArguments?;
		@Override public ParserRule getRule() { return rule; }
		
		//(TypeReference | {VersionedParameterizedTypeRef} TypeReference VersionRequest) -> TypeArguments?
		public Group getGroup() { return cGroup; }
		
		//TypeReference | {VersionedParameterizedTypeRef} TypeReference VersionRequest
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//TypeReference
		public RuleCall getTypeReferenceParserRuleCall_0_0() { return cTypeReferenceParserRuleCall_0_0; }
		
		//{VersionedParameterizedTypeRef} TypeReference VersionRequest
		public Group getGroup_0_1() { return cGroup_0_1; }
		
		//{VersionedParameterizedTypeRef}
		public Action getVersionedParameterizedTypeRefAction_0_1_0() { return cVersionedParameterizedTypeRefAction_0_1_0; }
		
		//TypeReference
		public RuleCall getTypeReferenceParserRuleCall_0_1_1() { return cTypeReferenceParserRuleCall_0_1_1; }
		
		//VersionRequest
		public RuleCall getVersionRequestParserRuleCall_0_1_2() { return cVersionRequestParserRuleCall_0_1_2; }
		
		//-> TypeArguments?
		public RuleCall getTypeArgumentsParserRuleCall_1() { return cTypeArgumentsParserRuleCall_1; }
	}
	public class ParameterizedTypeRefStructuralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefStructural");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cAlternatives_0.eContents().get(0);
		private final Assignment cDefinedTypingStrategyAssignment_0_0_0 = (Assignment)cGroup_0_0.eContents().get(0);
		private final RuleCall cDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0_0_0 = (RuleCall)cDefinedTypingStrategyAssignment_0_0_0.eContents().get(0);
		private final RuleCall cTypeReferenceParserRuleCall_0_0_1 = (RuleCall)cGroup_0_0.eContents().get(1);
		private final Group cGroup_0_1 = (Group)cAlternatives_0.eContents().get(1);
		private final Action cVersionedParameterizedTypeRefStructuralAction_0_1_0 = (Action)cGroup_0_1.eContents().get(0);
		private final Assignment cDefinedTypingStrategyAssignment_0_1_1 = (Assignment)cGroup_0_1.eContents().get(1);
		private final RuleCall cDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_1_1_0 = (RuleCall)cDefinedTypingStrategyAssignment_0_1_1.eContents().get(0);
		private final RuleCall cTypeReferenceParserRuleCall_0_1_2 = (RuleCall)cGroup_0_1.eContents().get(2);
		private final RuleCall cVersionRequestParserRuleCall_0_1_3 = (RuleCall)cGroup_0_1.eContents().get(3);
		private final RuleCall cTypeArgumentsParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cWithKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final RuleCall cTStructMemberListParserRuleCall_2_1 = (RuleCall)cGroup_2.eContents().get(1);
		
		//ParameterizedTypeRefStructural:
		//	(definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
		//	| {VersionedParameterizedTypeRefStructural} definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
		//	VersionRequest) -> TypeArguments? ('with' TStructMemberList)?;
		@Override public ParserRule getRule() { return rule; }
		
		//(definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference | {VersionedParameterizedTypeRefStructural}
		//definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference VersionRequest) -> TypeArguments? ('with'
		//TStructMemberList)?
		public Group getGroup() { return cGroup; }
		
		//definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference | {VersionedParameterizedTypeRefStructural}
		//definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference VersionRequest
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//definedTypingStrategy=TypingStrategyUseSiteOperator
		public Assignment getDefinedTypingStrategyAssignment_0_0_0() { return cDefinedTypingStrategyAssignment_0_0_0; }
		
		//TypingStrategyUseSiteOperator
		public RuleCall getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0_0_0() { return cDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0_0_0; }
		
		//TypeReference
		public RuleCall getTypeReferenceParserRuleCall_0_0_1() { return cTypeReferenceParserRuleCall_0_0_1; }
		
		//{VersionedParameterizedTypeRefStructural} definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
		//VersionRequest
		public Group getGroup_0_1() { return cGroup_0_1; }
		
		//{VersionedParameterizedTypeRefStructural}
		public Action getVersionedParameterizedTypeRefStructuralAction_0_1_0() { return cVersionedParameterizedTypeRefStructuralAction_0_1_0; }
		
		//definedTypingStrategy=TypingStrategyUseSiteOperator
		public Assignment getDefinedTypingStrategyAssignment_0_1_1() { return cDefinedTypingStrategyAssignment_0_1_1; }
		
		//TypingStrategyUseSiteOperator
		public RuleCall getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_1_1_0() { return cDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_1_1_0; }
		
		//TypeReference
		public RuleCall getTypeReferenceParserRuleCall_0_1_2() { return cTypeReferenceParserRuleCall_0_1_2; }
		
		//VersionRequest
		public RuleCall getVersionRequestParserRuleCall_0_1_3() { return cVersionRequestParserRuleCall_0_1_3; }
		
		//-> TypeArguments?
		public RuleCall getTypeArgumentsParserRuleCall_1() { return cTypeArgumentsParserRuleCall_1; }
		
		//('with' TStructMemberList)?
		public Group getGroup_2() { return cGroup_2; }
		
		//'with'
		public Keyword getWithKeyword_2_0() { return cWithKeyword_2_0; }
		
		//TStructMemberList
		public RuleCall getTStructMemberListParserRuleCall_2_1() { return cTStructMemberListParserRuleCall_2_1; }
	}
	public class IterableTypeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.IterableTypeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cIterableTypeExpressionAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cIterableTypeExpressionLeftSquareBracketKeyword_0_0 = (Keyword)cIterableTypeExpressionAssignment_0.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cTypeArgsAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final RuleCall cTypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0 = (RuleCall)cTypeArgsAssignment_1_0.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Assignment cTypeArgsAssignment_1_1_0 = (Assignment)cGroup_1_1.eContents().get(0);
		private final RuleCall cTypeArgsTypeArgumentParserRuleCall_1_1_0_0 = (RuleCall)cTypeArgsAssignment_1_1_0.eContents().get(0);
		private final Group cGroup_1_1_1 = (Group)cGroup_1_1.eContents().get(1);
		private final Keyword cCommaKeyword_1_1_1_0 = (Keyword)cGroup_1_1_1.eContents().get(0);
		private final Assignment cTypeArgsAssignment_1_1_1_1 = (Assignment)cGroup_1_1_1.eContents().get(1);
		private final RuleCall cTypeArgsTypeArgumentParserRuleCall_1_1_1_1_0 = (RuleCall)cTypeArgsAssignment_1_1_1_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1_1_2 = (Keyword)cGroup_1_1.eContents().get(2);
		
		//IterableTypeExpression ParameterizedTypeRef:
		//	iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail
		//	| typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* ']');
		@Override public ParserRule getRule() { return rule; }
		
		//iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail | typeArgs+=TypeArgument (','
		//typeArgs+=TypeArgument)* ']')
		public Group getGroup() { return cGroup; }
		
		//iterableTypeExpression?='['
		public Assignment getIterableTypeExpressionAssignment_0() { return cIterableTypeExpressionAssignment_0; }
		
		//'['
		public Keyword getIterableTypeExpressionLeftSquareBracketKeyword_0_0() { return cIterableTypeExpressionLeftSquareBracketKeyword_0_0; }
		
		//typeArgs+=EmptyIterableTypeExpressionTail | typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* ']'
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//typeArgs+=EmptyIterableTypeExpressionTail
		public Assignment getTypeArgsAssignment_1_0() { return cTypeArgsAssignment_1_0; }
		
		//EmptyIterableTypeExpressionTail
		public RuleCall getTypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0() { return cTypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0; }
		
		//typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* ']'
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//typeArgs+=TypeArgument
		public Assignment getTypeArgsAssignment_1_1_0() { return cTypeArgsAssignment_1_1_0; }
		
		//TypeArgument
		public RuleCall getTypeArgsTypeArgumentParserRuleCall_1_1_0_0() { return cTypeArgsTypeArgumentParserRuleCall_1_1_0_0; }
		
		//(',' typeArgs+=TypeArgument)*
		public Group getGroup_1_1_1() { return cGroup_1_1_1; }
		
		//','
		public Keyword getCommaKeyword_1_1_1_0() { return cCommaKeyword_1_1_1_0; }
		
		//typeArgs+=TypeArgument
		public Assignment getTypeArgsAssignment_1_1_1_1() { return cTypeArgsAssignment_1_1_1_1; }
		
		//TypeArgument
		public RuleCall getTypeArgsTypeArgumentParserRuleCall_1_1_1_1_0() { return cTypeArgsTypeArgumentParserRuleCall_1_1_1_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1_1_2() { return cRightSquareBracketKeyword_1_1_2; }
	}
	public class EmptyIterableTypeExpressionTailElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.EmptyIterableTypeExpressionTail");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cWildcardAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//EmptyIterableTypeExpressionTail Wildcard:
		//	{Wildcard} ']';
		@Override public ParserRule getRule() { return rule; }
		
		//{Wildcard} ']'
		public Group getGroup() { return cGroup; }
		
		//{Wildcard}
		public Action getWildcardAction_0() { return cWildcardAction_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_1() { return cRightSquareBracketKeyword_1; }
	}
	public class VersionRequestElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.VersionRequest");
		private final Assignment cRequestedVersionAssignment = (Assignment)rule.eContents().get(0);
		private final RuleCall cRequestedVersionVERSIONTerminalRuleCall_0 = (RuleCall)cRequestedVersionAssignment.eContents().get(0);
		
		//fragment VersionRequest *:
		//	requestedVersion=VERSION;
		@Override public ParserRule getRule() { return rule; }
		
		//requestedVersion=VERSION
		public Assignment getRequestedVersionAssignment() { return cRequestedVersionAssignment; }
		
		//VERSION
		public RuleCall getRequestedVersionVERSIONTerminalRuleCall_0() { return cRequestedVersionVERSIONTerminalRuleCall_0; }
	}
	public class TypeReferenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeReference");
		private final Assignment cDeclaredTypeAssignment = (Assignment)rule.eContents().get(0);
		private final CrossReference cDeclaredTypeTypeCrossReference_0 = (CrossReference)cDeclaredTypeAssignment.eContents().get(0);
		private final RuleCall cDeclaredTypeTypeTypeReferenceNameParserRuleCall_0_1 = (RuleCall)cDeclaredTypeTypeCrossReference_0.eContents().get(1);
		
		//fragment TypeReference *:
		//	declaredType=[Type|TypeReferenceName];
		@Override public ParserRule getRule() { return rule; }
		
		//declaredType=[Type|TypeReferenceName]
		public Assignment getDeclaredTypeAssignment() { return cDeclaredTypeAssignment; }
		
		//[Type|TypeReferenceName]
		public CrossReference getDeclaredTypeTypeCrossReference_0() { return cDeclaredTypeTypeCrossReference_0; }
		
		//TypeReferenceName
		public RuleCall getDeclaredTypeTypeTypeReferenceNameParserRuleCall_0_1() { return cDeclaredTypeTypeTypeReferenceNameParserRuleCall_0_1; }
	}
	public class TypeArgumentsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeArguments");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLessThanSignKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cTypeArgsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTypeArgsTypeArgumentParserRuleCall_1_0 = (RuleCall)cTypeArgsAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cCommaKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cTypeArgsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cTypeArgsTypeArgumentParserRuleCall_2_1_0 = (RuleCall)cTypeArgsAssignment_2_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//fragment TypeArguments *:
		//	'<' typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* '>';
		@Override public ParserRule getRule() { return rule; }
		
		//'<' typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* '>'
		public Group getGroup() { return cGroup; }
		
		//'<'
		public Keyword getLessThanSignKeyword_0() { return cLessThanSignKeyword_0; }
		
		//typeArgs+=TypeArgument
		public Assignment getTypeArgsAssignment_1() { return cTypeArgsAssignment_1; }
		
		//TypeArgument
		public RuleCall getTypeArgsTypeArgumentParserRuleCall_1_0() { return cTypeArgsTypeArgumentParserRuleCall_1_0; }
		
		//(',' typeArgs+=TypeArgument)*
		public Group getGroup_2() { return cGroup_2; }
		
		//','
		public Keyword getCommaKeyword_2_0() { return cCommaKeyword_2_0; }
		
		//typeArgs+=TypeArgument
		public Assignment getTypeArgsAssignment_2_1() { return cTypeArgsAssignment_2_1; }
		
		//TypeArgument
		public RuleCall getTypeArgsTypeArgumentParserRuleCall_2_1_0() { return cTypeArgsTypeArgumentParserRuleCall_2_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3() { return cGreaterThanSignKeyword_3; }
	}
	public class TStructMemberListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TStructMemberList");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Assignment cAstStructuralMembersAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cAstStructuralMembersTStructMemberParserRuleCall_1_0_0 = (RuleCall)cAstStructuralMembersAssignment_1_0.eContents().get(0);
		private final Alternatives cAlternatives_1_1 = (Alternatives)cGroup_1.eContents().get(1);
		private final Keyword cSemicolonKeyword_1_1_0 = (Keyword)cAlternatives_1_1.eContents().get(0);
		private final Keyword cCommaKeyword_1_1_1 = (Keyword)cAlternatives_1_1.eContents().get(1);
		private final Keyword cRightCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//fragment TStructMemberList *:
		//	'{' (astStructuralMembers+=TStructMember (';' | ',')?)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//'{' (astStructuralMembers+=TStructMember (';' | ',')?)* '}'
		public Group getGroup() { return cGroup; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_0() { return cLeftCurlyBracketKeyword_0; }
		
		//(astStructuralMembers+=TStructMember (';' | ',')?)*
		public Group getGroup_1() { return cGroup_1; }
		
		//astStructuralMembers+=TStructMember
		public Assignment getAstStructuralMembersAssignment_1_0() { return cAstStructuralMembersAssignment_1_0; }
		
		//TStructMember
		public RuleCall getAstStructuralMembersTStructMemberParserRuleCall_1_0_0() { return cAstStructuralMembersTStructMemberParserRuleCall_1_0_0; }
		
		//(';' | ',')?
		public Alternatives getAlternatives_1_1() { return cAlternatives_1_1; }
		
		//';'
		public Keyword getSemicolonKeyword_1_1_0() { return cSemicolonKeyword_1_1_0; }
		
		//','
		public Keyword getCommaKeyword_1_1_1() { return cCommaKeyword_1_1_1; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_2() { return cRightCurlyBracketKeyword_2; }
	}
	public class TStructMemberElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TStructMember");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cTStructGetterParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cTStructSetterParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cTStructMethodParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cTStructFieldParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		
		///**
		// * All TMembers here are only used in ParameterizedTypeRefStructural references
		// * Most type references are optional. However, in the types language (n4ts), these
		// * references are NOT optional.
		// */ TStructMember:
		//	TStructGetter
		//	| TStructSetter
		//	| TStructMethod
		//	| TStructField;
		@Override public ParserRule getRule() { return rule; }
		
		//TStructGetter | TStructSetter | TStructMethod | TStructField
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//TStructGetter
		public RuleCall getTStructGetterParserRuleCall_0() { return cTStructGetterParserRuleCall_0; }
		
		//TStructSetter
		public RuleCall getTStructSetterParserRuleCall_1() { return cTStructSetterParserRuleCall_1; }
		
		//TStructMethod
		public RuleCall getTStructMethodParserRuleCall_2() { return cTStructMethodParserRuleCall_2; }
		
		//TStructField
		public RuleCall getTStructFieldParserRuleCall_3() { return cTStructFieldParserRuleCall_3; }
	}
	public class TStructMethodElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TStructMethod");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cTStructMethodAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_0_0_1 = (RuleCall)cGroup_0_0.eContents().get(1);
		private final Assignment cNameAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cNameIdentifierNameParserRuleCall_0_0_2_0 = (RuleCall)cNameAssignment_0_0_2.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_0_0_3 = (Keyword)cGroup_0_0.eContents().get(3);
		private final RuleCall cTAnonymousFormalParameterListParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		//TStructMethod:
		//	=>
		//	({TStructMethod} TypeVariables?
		//	name=IdentifierName '(') TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({TStructMethod} TypeVariables? name=IdentifierName '(') TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?
		public Group getGroup() { return cGroup; }
		
		//=> ({TStructMethod} TypeVariables? name=IdentifierName '(')
		public Group getGroup_0() { return cGroup_0; }
		
		//{TStructMethod} TypeVariables? name=IdentifierName '('
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{TStructMethod}
		public Action getTStructMethodAction_0_0_0() { return cTStructMethodAction_0_0_0; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_0_0_1() { return cTypeVariablesParserRuleCall_0_0_1; }
		
		//name=IdentifierName
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
	public class TypeVariablesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeVariables");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLessThanSignKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cTypeVarsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTypeVarsTypeVariableParserRuleCall_1_0 = (RuleCall)cTypeVarsAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cCommaKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cTypeVarsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cTypeVarsTypeVariableParserRuleCall_2_1_0 = (RuleCall)cTypeVarsAssignment_2_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//// TODO extract TypeVariableContainer to be used here
		//fragment TypeVariables *:
		//	'<' typeVars+=TypeVariable (',' typeVars+=TypeVariable)* '>';
		@Override public ParserRule getRule() { return rule; }
		
		//'<' typeVars+=TypeVariable (',' typeVars+=TypeVariable)* '>'
		public Group getGroup() { return cGroup; }
		
		//'<'
		public Keyword getLessThanSignKeyword_0() { return cLessThanSignKeyword_0; }
		
		//typeVars+=TypeVariable
		public Assignment getTypeVarsAssignment_1() { return cTypeVarsAssignment_1; }
		
		//TypeVariable
		public RuleCall getTypeVarsTypeVariableParserRuleCall_1_0() { return cTypeVarsTypeVariableParserRuleCall_1_0; }
		
		//(',' typeVars+=TypeVariable)*
		public Group getGroup_2() { return cGroup_2; }
		
		//','
		public Keyword getCommaKeyword_2_0() { return cCommaKeyword_2_0; }
		
		//typeVars+=TypeVariable
		public Assignment getTypeVarsAssignment_2_1() { return cTypeVarsAssignment_2_1; }
		
		//TypeVariable
		public RuleCall getTypeVarsTypeVariableParserRuleCall_2_1_0() { return cTypeVarsTypeVariableParserRuleCall_2_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3() { return cGreaterThanSignKeyword_3; }
	}
	public class ColonSepDeclaredTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ColonSepDeclaredTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cColonKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cDeclaredTypeRefAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cDeclaredTypeRefTypeRefParserRuleCall_1_0 = (RuleCall)cDeclaredTypeRefAssignment_1.eContents().get(0);
		
		//fragment ColonSepDeclaredTypeRef *:
		//	':' declaredTypeRef=TypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//':' declaredTypeRef=TypeRef
		public Group getGroup() { return cGroup; }
		
		//':'
		public Keyword getColonKeyword_0() { return cColonKeyword_0; }
		
		//declaredTypeRef=TypeRef
		public Assignment getDeclaredTypeRefAssignment_1() { return cDeclaredTypeRefAssignment_1; }
		
		//TypeRef
		public RuleCall getDeclaredTypeRefTypeRefParserRuleCall_1_0() { return cDeclaredTypeRefTypeRefParserRuleCall_1_0; }
	}
	public class ColonSepTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ColonSepTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cColonKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cTypeRefAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTypeRefTypeRefParserRuleCall_1_0 = (RuleCall)cTypeRefAssignment_1.eContents().get(0);
		
		//fragment ColonSepTypeRef *:
		//	':' typeRef=TypeRef;
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ColonSepReturnTypeRef");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cColonKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cReturnTypeRefAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cReturnTypeRefTypeRefParserRuleCall_1_0 = (RuleCall)cReturnTypeRefAssignment_1.eContents().get(0);
		
		//fragment ColonSepReturnTypeRef *:
		//	':' returnTypeRef=TypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//':' returnTypeRef=TypeRef
		public Group getGroup() { return cGroup; }
		
		//':'
		public Keyword getColonKeyword_0() { return cColonKeyword_0; }
		
		//returnTypeRef=TypeRef
		public Assignment getReturnTypeRefAssignment_1() { return cReturnTypeRefAssignment_1; }
		
		//TypeRef
		public RuleCall getReturnTypeRefTypeRefParserRuleCall_1_0() { return cReturnTypeRefTypeRefParserRuleCall_1_0; }
	}
	public class TStructFieldElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TStructField");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cNameIdentifierNameParserRuleCall_0_0 = (RuleCall)cNameAssignment_0.eContents().get(0);
		private final Assignment cOptionalAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cOptionalQuestionMarkKeyword_1_0 = (Keyword)cOptionalAssignment_1.eContents().get(0);
		private final RuleCall cColonSepTypeRefParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		//TStructField:
		//	name=IdentifierName optional?='?'? ColonSepTypeRef?;
		@Override public ParserRule getRule() { return rule; }
		
		//name=IdentifierName optional?='?'? ColonSepTypeRef?
		public Group getGroup() { return cGroup; }
		
		//name=IdentifierName
		public Assignment getNameAssignment_0() { return cNameAssignment_0; }
		
		//IdentifierName
		public RuleCall getNameIdentifierNameParserRuleCall_0_0() { return cNameIdentifierNameParserRuleCall_0_0; }
		
		//optional?='?'?
		public Assignment getOptionalAssignment_1() { return cOptionalAssignment_1; }
		
		//'?'
		public Keyword getOptionalQuestionMarkKeyword_1_0() { return cOptionalQuestionMarkKeyword_1_0; }
		
		//ColonSepTypeRef?
		public RuleCall getColonSepTypeRefParserRuleCall_2() { return cColonSepTypeRefParserRuleCall_2; }
	}
	public class TStructGetterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TStructGetter");
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
		private final RuleCall cColonSepDeclaredTypeRefParserRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		
		//TStructGetter:
		//	=> ({TStructGetter}
		//	'get'
		//	name=IdentifierName) optional?='?'?
		//	'(' ')' ColonSepDeclaredTypeRef?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({TStructGetter} 'get' name=IdentifierName) optional?='?'? '(' ')' ColonSepDeclaredTypeRef?
		public Group getGroup() { return cGroup; }
		
		//=> ({TStructGetter} 'get' name=IdentifierName)
		public Group getGroup_0() { return cGroup_0; }
		
		//{TStructGetter} 'get' name=IdentifierName
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{TStructGetter}
		public Action getTStructGetterAction_0_0_0() { return cTStructGetterAction_0_0_0; }
		
		//'get'
		public Keyword getGetKeyword_0_0_1() { return cGetKeyword_0_0_1; }
		
		//name=IdentifierName
		public Assignment getNameAssignment_0_0_2() { return cNameAssignment_0_0_2; }
		
		//IdentifierName
		public RuleCall getNameIdentifierNameParserRuleCall_0_0_2_0() { return cNameIdentifierNameParserRuleCall_0_0_2_0; }
		
		//optional?='?'?
		public Assignment getOptionalAssignment_1() { return cOptionalAssignment_1; }
		
		//'?'
		public Keyword getOptionalQuestionMarkKeyword_1_0() { return cOptionalQuestionMarkKeyword_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }
		
		//ColonSepDeclaredTypeRef?
		public RuleCall getColonSepDeclaredTypeRefParserRuleCall_4() { return cColonSepDeclaredTypeRefParserRuleCall_4; }
	}
	public class TStructSetterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TStructSetter");
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
		//	=> ({TStructSetter}
		//	'set'
		//	name=IdentifierName) optional?='?'?
		//	'(' fpar=TAnonymousFormalParameter ')';
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({TStructSetter} 'set' name=IdentifierName) optional?='?'? '(' fpar=TAnonymousFormalParameter ')'
		public Group getGroup() { return cGroup; }
		
		//=> ({TStructSetter} 'set' name=IdentifierName)
		public Group getGroup_0() { return cGroup_0; }
		
		//{TStructSetter} 'set' name=IdentifierName
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{TStructSetter}
		public Action getTStructSetterAction_0_0_0() { return cTStructSetterAction_0_0_0; }
		
		//'set'
		public Keyword getSetKeyword_0_0_1() { return cSetKeyword_0_0_1; }
		
		//name=IdentifierName
		public Assignment getNameAssignment_0_0_2() { return cNameAssignment_0_0_2; }
		
		//IdentifierName
		public RuleCall getNameIdentifierNameParserRuleCall_0_0_2_0() { return cNameIdentifierNameParserRuleCall_0_0_2_0; }
		
		//optional?='?'?
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
	public class TypingStrategyUseSiteOperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypingStrategyUseSiteOperator");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cTildeKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Keyword cTildeKeyword_1_0 = (Keyword)cAlternatives_1.eContents().get(0);
		private final RuleCall cSTRUCTMODSUFFIXTerminalRuleCall_1_1 = (RuleCall)cAlternatives_1.eContents().get(1);
		
		//TypingStrategyUseSiteOperator TypingStrategy:
		//	'~' ('~' | STRUCTMODSUFFIX)?;
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypingStrategyDefSiteOperator");
		private final Keyword cTildeKeyword = (Keyword)rule.eContents().get(1);
		
		//TypingStrategyDefSiteOperator TypingStrategy:
		//	'~';
		@Override public ParserRule getRule() { return rule; }
		
		//'~'
		public Keyword getTildeKeyword() { return cTildeKeyword; }
	}
	public class TypeTypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeTypeRef");
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
		
		//TypeTypeRef:
		//	{TypeTypeRef} ('type' | constructorRef?='constructor')
		//	'{' typeArg=TypeArgInTypeTypeRef '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{TypeTypeRef} ('type' | constructorRef?='constructor') '{' typeArg=TypeArgInTypeTypeRef '}'
		public Group getGroup() { return cGroup; }
		
		//{TypeTypeRef}
		public Action getTypeTypeRefAction_0() { return cTypeTypeRefAction_0; }
		
		//'type' | constructorRef?='constructor'
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeReferenceName");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cIDENTIFIERTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final RuleCall cIDENTIFIERTerminalRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//TypeReferenceName:
		//	IDENTIFIER ('.' IDENTIFIER)*;
		@Override public ParserRule getRule() { return rule; }
		
		//IDENTIFIER ('.' IDENTIFIER)*
		public Group getGroup() { return cGroup; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_0() { return cIDENTIFIERTerminalRuleCall_0; }
		
		//('.' IDENTIFIER)*
		public Group getGroup_1() { return cGroup_1; }
		
		//'.'
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_1_1() { return cIDENTIFIERTerminalRuleCall_1_1; }
	}
	public class TypeArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeArgument");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cWildcardParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cWildcardNewNotationParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cTypeRefParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//TypeArgument:
		//	Wildcard | WildcardNewNotation | TypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//Wildcard | WildcardNewNotation | TypeRef
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Wildcard
		public RuleCall getWildcardParserRuleCall_0() { return cWildcardParserRuleCall_0; }
		
		//WildcardNewNotation
		public RuleCall getWildcardNewNotationParserRuleCall_1() { return cWildcardNewNotationParserRuleCall_1; }
		
		//TypeRef
		public RuleCall getTypeRefParserRuleCall_2() { return cTypeRefParserRuleCall_2; }
	}
	public class WildcardElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.Wildcard");
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
		
		//Wildcard:
		//	=> ({Wildcard} '?') ('extends' declaredUpperBound=TypeRef | 'super'
		//	declaredLowerBound=TypeRef)?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({Wildcard} '?') ('extends' declaredUpperBound=TypeRef | 'super' declaredLowerBound=TypeRef)?
		public Group getGroup() { return cGroup; }
		
		//=> ({Wildcard} '?')
		public Group getGroup_0() { return cGroup_0; }
		
		//{Wildcard} '?'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{Wildcard}
		public Action getWildcardAction_0_0_0() { return cWildcardAction_0_0_0; }
		
		//'?'
		public Keyword getQuestionMarkKeyword_0_0_1() { return cQuestionMarkKeyword_0_0_1; }
		
		//('extends' declaredUpperBound=TypeRef | 'super' declaredLowerBound=TypeRef)?
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//'extends' declaredUpperBound=TypeRef
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//'extends'
		public Keyword getExtendsKeyword_1_0_0() { return cExtendsKeyword_1_0_0; }
		
		//declaredUpperBound=TypeRef
		public Assignment getDeclaredUpperBoundAssignment_1_0_1() { return cDeclaredUpperBoundAssignment_1_0_1; }
		
		//TypeRef
		public RuleCall getDeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0() { return cDeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0; }
		
		//'super' declaredLowerBound=TypeRef
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//'super'
		public Keyword getSuperKeyword_1_1_0() { return cSuperKeyword_1_1_0; }
		
		//declaredLowerBound=TypeRef
		public Assignment getDeclaredLowerBoundAssignment_1_1_1() { return cDeclaredLowerBoundAssignment_1_1_1; }
		
		//TypeRef
		public RuleCall getDeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0() { return cDeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0; }
	}
	public class WildcardNewNotationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.WildcardNewNotation");
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
		
		//WildcardNewNotation Wildcard:
		//	usingInOutNotation?='out' declaredUpperBound=TypeRef | usingInOutNotation?='in' declaredLowerBound=TypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//usingInOutNotation?='out' declaredUpperBound=TypeRef | usingInOutNotation?='in' declaredLowerBound=TypeRef
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//usingInOutNotation?='out' declaredUpperBound=TypeRef
		public Group getGroup_0() { return cGroup_0; }
		
		//usingInOutNotation?='out'
		public Assignment getUsingInOutNotationAssignment_0_0() { return cUsingInOutNotationAssignment_0_0; }
		
		//'out'
		public Keyword getUsingInOutNotationOutKeyword_0_0_0() { return cUsingInOutNotationOutKeyword_0_0_0; }
		
		//declaredUpperBound=TypeRef
		public Assignment getDeclaredUpperBoundAssignment_0_1() { return cDeclaredUpperBoundAssignment_0_1; }
		
		//TypeRef
		public RuleCall getDeclaredUpperBoundTypeRefParserRuleCall_0_1_0() { return cDeclaredUpperBoundTypeRefParserRuleCall_0_1_0; }
		
		//usingInOutNotation?='in' declaredLowerBound=TypeRef
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeVariable");
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
		
		//TypeVariable:
		//	(declaredCovariant?='out' | declaredContravariant?='in')?
		//	name=IDENTIFIER ('extends' declaredUpperBound=TypeRef)?;
		@Override public ParserRule getRule() { return rule; }
		
		//(declaredCovariant?='out' | declaredContravariant?='in')? name=IDENTIFIER ('extends' declaredUpperBound=TypeRef)?
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
	}
	public class BindingIdentifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cIDENTIFIERTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Keyword cYieldKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final RuleCall cN4KeywordParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		///*
		// * [ECM11] (7.6, pp. 17)
		// * Identifier :: IdentifierName but not ReservedWord
		// * ReservedWord :: Keyword | FutureReservedWord | NullLiteral | BooleanLiteral
		// */ BindingIdentifier <Yield>:
		//	IDENTIFIER
		//	// yield as identifier as of [ECM15] (11.6.2, pp. 165)
		//	| <!Yield> 'yield'
		//	| N4Keyword;
		@Override public ParserRule getRule() { return rule; }
		
		//IDENTIFIER // yield as identifier as of [ECM15] (11.6.2, pp. 165)
		//| <!Yield> 'yield' | N4Keyword
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cIDENTIFIERTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cReservedWordParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cN4KeywordParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//IdentifierName:
		//	IDENTIFIER | ReservedWord | N4Keyword;
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ReservedWord");
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
		
		//ReservedWord: // Keywords as of [ECM15] (11.6.2, pp. 165)
		//	'break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' |
		//	'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' |
		//	'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' // null literal
		//	| 'null' // boolean literal
		//	| 'true' | 'false' // Future Reserved Word as of [ECM15] (11.6.2.2, pp. 166)
		//	// | 'await' /* reserved word only if parse goal is module - compromise: allow as identifier and validate */
		//	| 'enum';
		@Override public ParserRule getRule() { return rule; }
		
		//// Keywords as of [ECM15] (11.6.2, pp. 165)
		//'break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' |
		//'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' |
		//'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' // null literal
		//| 'null' // boolean literal
		//| 'true' | 'false' // Future Reserved Word as of [ECM15] (11.6.2.2, pp. 166)
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.N4Keyword");
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
		
		//N4Keyword:
		//	'get' | 'set'
		//	| 'let'
		//	| 'project'
		//	| 'external' | 'abstract' | 'static'
		//	| 'as' | 'from' | 'constructor' | 'of' | 'target'
		//	| 'type' | 'union' | 'intersection'
		//	| 'This' | 'Promisify'
		//	// future reserved keyword in [ECM15] only in modules, we add additional validation
		//	| 'await'
		//	// async is not a reserved keyword, i.e. it can be used as a variable name
		//	| 'async'
		//	// future reserved keywords in [ECM15], restricted via static semantic in [ECM15]
		//	| 'implements' | 'interface'
		//	| 'private' | 'protected' | 'public' // package not used in N4JS
		//	// definition-site variance
		//	| 'out';
		@Override public ParserRule getRule() { return rule; }
		
		//'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target'
		//| 'type' | 'union' | 'intersection' | 'This' | 'Promisify' // future reserved keyword in [ECM15] only in modules, we add additional validation
		//| 'await' // async is not a reserved keyword, i.e. it can be used as a variable name
		//| 'async' // future reserved keywords in [ECM15], restricted via static semantic in [ECM15]
		//| 'implements' | 'interface' | 'private' | 'protected' | 'public' // package not used in N4JS
		//// definition-site variance
		//| 'out'
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
		
		//'This'
		public Keyword getThisKeyword_15() { return cThisKeyword_15; }
		
		//'Promisify'
		public Keyword getPromisifyKeyword_16() { return cPromisifyKeyword_16; }
		
		//'await'
		public Keyword getAwaitKeyword_17() { return cAwaitKeyword_17; }
		
		//'async'
		public Keyword getAsyncKeyword_18() { return cAsyncKeyword_18; }
		
		//'implements'
		public Keyword getImplementsKeyword_19() { return cImplementsKeyword_19; }
		
		//'interface'
		public Keyword getInterfaceKeyword_20() { return cInterfaceKeyword_20; }
		
		//'private'
		public Keyword getPrivateKeyword_21() { return cPrivateKeyword_21; }
		
		//'protected'
		public Keyword getProtectedKeyword_22() { return cProtectedKeyword_22; }
		
		//'public'
		public Keyword getPublicKeyword_23() { return cPublicKeyword_23; }
		
		//'out'
		public Keyword getOutKeyword_24() { return cOutKeyword_24; }
	}
	
	
	private final TypeRefElements pTypeRef;
	private final IntersectionTypeExpressionElements pIntersectionTypeExpression;
	private final ArrayTypeExpressionElements pArrayTypeExpression;
	private final PrimaryTypeExpressionElements pPrimaryTypeExpression;
	private final TypeRefWithModifiersElements pTypeRefWithModifiers;
	private final TypeRefWithoutModifiersElements pTypeRefWithoutModifiers;
	private final TypeRefFunctionTypeExpressionElements pTypeRefFunctionTypeExpression;
	private final TypeArgInTypeTypeRefElements pTypeArgInTypeTypeRef;
	private final ThisTypeRefElements pThisTypeRef;
	private final ThisTypeRefNominalElements pThisTypeRefNominal;
	private final ThisTypeRefStructuralElements pThisTypeRefStructural;
	private final FunctionTypeExpressionOLDElements pFunctionTypeExpressionOLD;
	private final ArrowFunctionTypeExpressionElements pArrowFunctionTypeExpression;
	private final TAnonymousFormalParameterListElements pTAnonymousFormalParameterList;
	private final TAnonymousFormalParameterElements pTAnonymousFormalParameter;
	private final TFormalParameterElements pTFormalParameter;
	private final DefaultFormalParameterElements pDefaultFormalParameter;
	private final UnionTypeExpressionOLDElements pUnionTypeExpressionOLD;
	private final IntersectionTypeExpressionOLDElements pIntersectionTypeExpressionOLD;
	private final ParameterizedTypeRefElements pParameterizedTypeRef;
	private final ParameterizedTypeRefNominalElements pParameterizedTypeRefNominal;
	private final ParameterizedTypeRefStructuralElements pParameterizedTypeRefStructural;
	private final IterableTypeExpressionElements pIterableTypeExpression;
	private final EmptyIterableTypeExpressionTailElements pEmptyIterableTypeExpressionTail;
	private final VersionRequestElements pVersionRequest;
	private final TypeReferenceElements pTypeReference;
	private final TypeArgumentsElements pTypeArguments;
	private final TStructMemberListElements pTStructMemberList;
	private final TStructMemberElements pTStructMember;
	private final TStructMethodElements pTStructMethod;
	private final TypeVariablesElements pTypeVariables;
	private final ColonSepDeclaredTypeRefElements pColonSepDeclaredTypeRef;
	private final ColonSepTypeRefElements pColonSepTypeRef;
	private final ColonSepReturnTypeRefElements pColonSepReturnTypeRef;
	private final TStructFieldElements pTStructField;
	private final TStructGetterElements pTStructGetter;
	private final TStructSetterElements pTStructSetter;
	private final TypingStrategyUseSiteOperatorElements pTypingStrategyUseSiteOperator;
	private final TypingStrategyDefSiteOperatorElements pTypingStrategyDefSiteOperator;
	private final TerminalRule tSTRUCTMODSUFFIX;
	private final TypeTypeRefElements pTypeTypeRef;
	private final TypeReferenceNameElements pTypeReferenceName;
	private final TypeArgumentElements pTypeArgument;
	private final WildcardElements pWildcard;
	private final WildcardNewNotationElements pWildcardNewNotation;
	private final TypeVariableElements pTypeVariable;
	private final BindingIdentifierElements pBindingIdentifier;
	private final IdentifierNameElements pIdentifierName;
	private final ReservedWordElements pReservedWord;
	private final N4KeywordElements pN4Keyword;
	private final TerminalRule tIDENTIFIER;
	private final TerminalRule tINT;
	private final TerminalRule tML_COMMENT;
	private final TerminalRule tSL_COMMENT;
	private final TerminalRule tEOL;
	private final TerminalRule tWS;
	private final TerminalRule tUNICODE_ESCAPE_FRAGMENT;
	private final TerminalRule tIDENTIFIER_START;
	private final TerminalRule tIDENTIFIER_PART;
	private final TerminalRule tDOT_DOT;
	private final TerminalRule tVERSION;
	
	private final Grammar grammar;
	
	private final UnicodeGrammarAccess gaUnicode;

	@Inject
	public TypeExpressionsGrammarAccess(GrammarProvider grammarProvider,
			UnicodeGrammarAccess gaUnicode) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaUnicode = gaUnicode;
		this.pTypeRef = new TypeRefElements();
		this.pIntersectionTypeExpression = new IntersectionTypeExpressionElements();
		this.pArrayTypeExpression = new ArrayTypeExpressionElements();
		this.pPrimaryTypeExpression = new PrimaryTypeExpressionElements();
		this.pTypeRefWithModifiers = new TypeRefWithModifiersElements();
		this.pTypeRefWithoutModifiers = new TypeRefWithoutModifiersElements();
		this.pTypeRefFunctionTypeExpression = new TypeRefFunctionTypeExpressionElements();
		this.pTypeArgInTypeTypeRef = new TypeArgInTypeTypeRefElements();
		this.pThisTypeRef = new ThisTypeRefElements();
		this.pThisTypeRefNominal = new ThisTypeRefNominalElements();
		this.pThisTypeRefStructural = new ThisTypeRefStructuralElements();
		this.pFunctionTypeExpressionOLD = new FunctionTypeExpressionOLDElements();
		this.pArrowFunctionTypeExpression = new ArrowFunctionTypeExpressionElements();
		this.pTAnonymousFormalParameterList = new TAnonymousFormalParameterListElements();
		this.pTAnonymousFormalParameter = new TAnonymousFormalParameterElements();
		this.pTFormalParameter = new TFormalParameterElements();
		this.pDefaultFormalParameter = new DefaultFormalParameterElements();
		this.pUnionTypeExpressionOLD = new UnionTypeExpressionOLDElements();
		this.pIntersectionTypeExpressionOLD = new IntersectionTypeExpressionOLDElements();
		this.pParameterizedTypeRef = new ParameterizedTypeRefElements();
		this.pParameterizedTypeRefNominal = new ParameterizedTypeRefNominalElements();
		this.pParameterizedTypeRefStructural = new ParameterizedTypeRefStructuralElements();
		this.pIterableTypeExpression = new IterableTypeExpressionElements();
		this.pEmptyIterableTypeExpressionTail = new EmptyIterableTypeExpressionTailElements();
		this.pVersionRequest = new VersionRequestElements();
		this.pTypeReference = new TypeReferenceElements();
		this.pTypeArguments = new TypeArgumentsElements();
		this.pTStructMemberList = new TStructMemberListElements();
		this.pTStructMember = new TStructMemberElements();
		this.pTStructMethod = new TStructMethodElements();
		this.pTypeVariables = new TypeVariablesElements();
		this.pColonSepDeclaredTypeRef = new ColonSepDeclaredTypeRefElements();
		this.pColonSepTypeRef = new ColonSepTypeRefElements();
		this.pColonSepReturnTypeRef = new ColonSepReturnTypeRefElements();
		this.pTStructField = new TStructFieldElements();
		this.pTStructGetter = new TStructGetterElements();
		this.pTStructSetter = new TStructSetterElements();
		this.pTypingStrategyUseSiteOperator = new TypingStrategyUseSiteOperatorElements();
		this.pTypingStrategyDefSiteOperator = new TypingStrategyDefSiteOperatorElements();
		this.tSTRUCTMODSUFFIX = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.STRUCTMODSUFFIX");
		this.pTypeTypeRef = new TypeTypeRefElements();
		this.pTypeReferenceName = new TypeReferenceNameElements();
		this.pTypeArgument = new TypeArgumentElements();
		this.pWildcard = new WildcardElements();
		this.pWildcardNewNotation = new WildcardNewNotationElements();
		this.pTypeVariable = new TypeVariableElements();
		this.pBindingIdentifier = new BindingIdentifierElements();
		this.pIdentifierName = new IdentifierNameElements();
		this.pReservedWord = new ReservedWordElements();
		this.pN4Keyword = new N4KeywordElements();
		this.tIDENTIFIER = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER");
		this.tINT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.INT");
		this.tML_COMMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.ML_COMMENT");
		this.tSL_COMMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.SL_COMMENT");
		this.tEOL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.EOL");
		this.tWS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.WS");
		this.tUNICODE_ESCAPE_FRAGMENT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.UNICODE_ESCAPE_FRAGMENT");
		this.tIDENTIFIER_START = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER_START");
		this.tIDENTIFIER_PART = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER_PART");
		this.tDOT_DOT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.DOT_DOT");
		this.tVERSION = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.VERSION");
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.ts.TypeExpressions".equals(grammar.getName())) {
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
	//// [ECM15]	ECMAScript 2015 Language Specification / ISO/IEC (ECMA-262, 6th Edition).
	////			International Standard.
	////			http://www.ecma-international.org/publications/ files/ECMA-ST/Ecma-262.pdf
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
	// */ TypeRef:
	//	IntersectionTypeExpression ({UnionTypeExpression.typeRefs+=current} ("|" typeRefs+=IntersectionTypeExpression)+)?;
	public TypeRefElements getTypeRefAccess() {
		return pTypeRef;
	}
	
	public ParserRule getTypeRefRule() {
		return getTypeRefAccess().getRule();
	}
	
	//IntersectionTypeExpression TypeRef:
	//	ArrayTypeExpression ({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=ArrayTypeExpression)+)?;
	public IntersectionTypeExpressionElements getIntersectionTypeExpressionAccess() {
		return pIntersectionTypeExpression;
	}
	
	public ParserRule getIntersectionTypeExpressionRule() {
		return getIntersectionTypeExpressionAccess().getRule();
	}
	
	//ArrayTypeExpression TypeRef:
	//	PrimaryTypeExpression => ({ParameterizedTypeRef.typeArgs+=current} arrayTypeExpression?='[' ']')*;
	public ArrayTypeExpressionElements getArrayTypeExpressionAccess() {
		return pArrayTypeExpression;
	}
	
	public ParserRule getArrayTypeExpressionRule() {
		return getArrayTypeExpressionAccess().getRule();
	}
	
	//PrimaryTypeExpression TypeRef:
	//	ArrowFunctionTypeExpression
	//	| IterableTypeExpression
	//	| TypeRefWithModifiers
	//	| "(" TypeRef ")";
	public PrimaryTypeExpressionElements getPrimaryTypeExpressionAccess() {
		return pPrimaryTypeExpression;
	}
	
	public ParserRule getPrimaryTypeExpressionRule() {
		return getPrimaryTypeExpressionAccess().getRule();
	}
	
	//TypeRefWithModifiers StaticBaseTypeRef:
	//	TypeRefWithoutModifiers => followedByQuestionMark?='?'?;
	public TypeRefWithModifiersElements getTypeRefWithModifiersAccess() {
		return pTypeRefWithModifiers;
	}
	
	public ParserRule getTypeRefWithModifiersRule() {
		return getTypeRefWithModifiersAccess().getRule();
	}
	
	//TypeRefWithoutModifiers StaticBaseTypeRef:
	//	(ParameterizedTypeRef | ThisTypeRef) => dynamic?='+'? | TypeTypeRef
	//	| FunctionTypeExpressionOLD
	//	| UnionTypeExpressionOLD
	//	| IntersectionTypeExpressionOLD;
	public TypeRefWithoutModifiersElements getTypeRefWithoutModifiersAccess() {
		return pTypeRefWithoutModifiers;
	}
	
	public ParserRule getTypeRefWithoutModifiersRule() {
		return getTypeRefWithoutModifiersAccess().getRule();
	}
	
	//TypeRefFunctionTypeExpression StaticBaseTypeRef:
	//	ParameterizedTypeRef
	//	| IterableTypeExpression
	//	| TypeTypeRef
	//	| UnionTypeExpressionOLD
	//	| IntersectionTypeExpressionOLD;
	public TypeRefFunctionTypeExpressionElements getTypeRefFunctionTypeExpressionAccess() {
		return pTypeRefFunctionTypeExpression;
	}
	
	public ParserRule getTypeRefFunctionTypeExpressionRule() {
		return getTypeRefFunctionTypeExpressionAccess().getRule();
	}
	
	//TypeArgInTypeTypeRef TypeArgument:
	//	ParameterizedTypeRefNominal
	//	| ThisTypeRefNominal
	//	| Wildcard;
	public TypeArgInTypeTypeRefElements getTypeArgInTypeTypeRefAccess() {
		return pTypeArgInTypeTypeRef;
	}
	
	public ParserRule getTypeArgInTypeTypeRefRule() {
		return getTypeArgInTypeTypeRefAccess().getRule();
	}
	
	//ThisTypeRef:
	//	ThisTypeRefNominal | ThisTypeRefStructural;
	public ThisTypeRefElements getThisTypeRefAccess() {
		return pThisTypeRef;
	}
	
	public ParserRule getThisTypeRefRule() {
		return getThisTypeRefAccess().getRule();
	}
	
	//ThisTypeRefNominal:
	//	{ThisTypeRefNominal} 'this';
	public ThisTypeRefNominalElements getThisTypeRefNominalAccess() {
		return pThisTypeRefNominal;
	}
	
	public ParserRule getThisTypeRefNominalRule() {
		return getThisTypeRefNominalAccess().getRule();
	}
	
	//ThisTypeRefStructural:
	//	definedTypingStrategy=TypingStrategyUseSiteOperator
	//	'this' ('with' TStructMemberList)?;
	public ThisTypeRefStructuralElements getThisTypeRefStructuralAccess() {
		return pThisTypeRefStructural;
	}
	
	public ParserRule getThisTypeRefStructuralRule() {
		return getThisTypeRefStructuralAccess().getRule();
	}
	
	//FunctionTypeExpressionOLD FunctionTypeExpression:
	//	{FunctionTypeExpression}
	//	'{' ('@' 'This' '(' declaredThisType=TypeRefFunctionTypeExpression ')')?
	//	'function' ('<' ownedTypeVars+=TypeVariable (',' ownedTypeVars+=TypeVariable)* '>')?
	//	'(' TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?
	//	'}';
	public FunctionTypeExpressionOLDElements getFunctionTypeExpressionOLDAccess() {
		return pFunctionTypeExpressionOLD;
	}
	
	public ParserRule getFunctionTypeExpressionOLDRule() {
		return getFunctionTypeExpressionOLDAccess().getRule();
	}
	
	//ArrowFunctionTypeExpression FunctionTypeExpression:
	//	=> ({FunctionTypeExpression} '(' TAnonymousFormalParameterList ')' '=>') returnTypeRef=PrimaryTypeExpression;
	public ArrowFunctionTypeExpressionElements getArrowFunctionTypeExpressionAccess() {
		return pArrowFunctionTypeExpression;
	}
	
	public ParserRule getArrowFunctionTypeExpressionRule() {
		return getArrowFunctionTypeExpressionAccess().getRule();
	}
	
	//// TODO extract FormalParameterContainer and use returns FormalParameterContainer instead of wildcard
	//fragment TAnonymousFormalParameterList *:
	//	(fpars+=TAnonymousFormalParameter (',' fpars+=TAnonymousFormalParameter)*)?;
	public TAnonymousFormalParameterListElements getTAnonymousFormalParameterListAccess() {
		return pTAnonymousFormalParameterList;
	}
	
	public ParserRule getTAnonymousFormalParameterListRule() {
		return getTAnonymousFormalParameterListAccess().getRule();
	}
	
	///**
	// * Used in type expressions, name is optional.
	// */ TAnonymousFormalParameter:
	//	variadic?='...'? (=> (name=BindingIdentifier<Yield=false> -> ColonSepTypeRef) | typeRef=TypeRef)
	//	DefaultFormalParameter;
	public TAnonymousFormalParameterElements getTAnonymousFormalParameterAccess() {
		return pTAnonymousFormalParameter;
	}
	
	public ParserRule getTAnonymousFormalParameterRule() {
		return getTAnonymousFormalParameterAccess().getRule();
	}
	
	///**
	// * Used in Types language only.
	// */ TFormalParameter:
	//	variadic?='...'? name=BindingIdentifier<Yield=false> ColonSepTypeRef
	//	DefaultFormalParameter;
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
	// */ fragment DefaultFormalParameter *:
	//	(hasInitializerAssignment?='=' astInitializer=TypeReferenceName?)?;
	public DefaultFormalParameterElements getDefaultFormalParameterAccess() {
		return pDefaultFormalParameter;
	}
	
	public ParserRule getDefaultFormalParameterRule() {
		return getDefaultFormalParameterAccess().getRule();
	}
	
	//UnionTypeExpressionOLD UnionTypeExpression:
	//	{UnionTypeExpression}
	//	'union' '{' typeRefs+=TypeRefWithoutModifiers (',' typeRefs+=TypeRefWithoutModifiers)* '}';
	public UnionTypeExpressionOLDElements getUnionTypeExpressionOLDAccess() {
		return pUnionTypeExpressionOLD;
	}
	
	public ParserRule getUnionTypeExpressionOLDRule() {
		return getUnionTypeExpressionOLDAccess().getRule();
	}
	
	//IntersectionTypeExpressionOLD IntersectionTypeExpression:
	//	{IntersectionTypeExpression}
	//	'intersection' '{' typeRefs+=TypeRefWithoutModifiers (',' typeRefs+=TypeRefWithoutModifiers)* '}';
	public IntersectionTypeExpressionOLDElements getIntersectionTypeExpressionOLDAccess() {
		return pIntersectionTypeExpressionOLD;
	}
	
	public ParserRule getIntersectionTypeExpressionOLDRule() {
		return getIntersectionTypeExpressionOLDAccess().getRule();
	}
	
	//ParameterizedTypeRef:
	//	ParameterizedTypeRefNominal | ParameterizedTypeRefStructural;
	public ParameterizedTypeRefElements getParameterizedTypeRefAccess() {
		return pParameterizedTypeRef;
	}
	
	public ParserRule getParameterizedTypeRefRule() {
		return getParameterizedTypeRefAccess().getRule();
	}
	
	//ParameterizedTypeRefNominal ParameterizedTypeRef:
	//	(TypeReference
	//	| {VersionedParameterizedTypeRef} TypeReference VersionRequest) -> TypeArguments?;
	public ParameterizedTypeRefNominalElements getParameterizedTypeRefNominalAccess() {
		return pParameterizedTypeRefNominal;
	}
	
	public ParserRule getParameterizedTypeRefNominalRule() {
		return getParameterizedTypeRefNominalAccess().getRule();
	}
	
	//ParameterizedTypeRefStructural:
	//	(definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
	//	| {VersionedParameterizedTypeRefStructural} definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
	//	VersionRequest) -> TypeArguments? ('with' TStructMemberList)?;
	public ParameterizedTypeRefStructuralElements getParameterizedTypeRefStructuralAccess() {
		return pParameterizedTypeRefStructural;
	}
	
	public ParserRule getParameterizedTypeRefStructuralRule() {
		return getParameterizedTypeRefStructuralAccess().getRule();
	}
	
	//IterableTypeExpression ParameterizedTypeRef:
	//	iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail
	//	| typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* ']');
	public IterableTypeExpressionElements getIterableTypeExpressionAccess() {
		return pIterableTypeExpression;
	}
	
	public ParserRule getIterableTypeExpressionRule() {
		return getIterableTypeExpressionAccess().getRule();
	}
	
	//EmptyIterableTypeExpressionTail Wildcard:
	//	{Wildcard} ']';
	public EmptyIterableTypeExpressionTailElements getEmptyIterableTypeExpressionTailAccess() {
		return pEmptyIterableTypeExpressionTail;
	}
	
	public ParserRule getEmptyIterableTypeExpressionTailRule() {
		return getEmptyIterableTypeExpressionTailAccess().getRule();
	}
	
	//fragment VersionRequest *:
	//	requestedVersion=VERSION;
	public VersionRequestElements getVersionRequestAccess() {
		return pVersionRequest;
	}
	
	public ParserRule getVersionRequestRule() {
		return getVersionRequestAccess().getRule();
	}
	
	//fragment TypeReference *:
	//	declaredType=[Type|TypeReferenceName];
	public TypeReferenceElements getTypeReferenceAccess() {
		return pTypeReference;
	}
	
	public ParserRule getTypeReferenceRule() {
		return getTypeReferenceAccess().getRule();
	}
	
	//fragment TypeArguments *:
	//	'<' typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* '>';
	public TypeArgumentsElements getTypeArgumentsAccess() {
		return pTypeArguments;
	}
	
	public ParserRule getTypeArgumentsRule() {
		return getTypeArgumentsAccess().getRule();
	}
	
	//fragment TStructMemberList *:
	//	'{' (astStructuralMembers+=TStructMember (';' | ',')?)* '}';
	public TStructMemberListElements getTStructMemberListAccess() {
		return pTStructMemberList;
	}
	
	public ParserRule getTStructMemberListRule() {
		return getTStructMemberListAccess().getRule();
	}
	
	///**
	// * All TMembers here are only used in ParameterizedTypeRefStructural references
	// * Most type references are optional. However, in the types language (n4ts), these
	// * references are NOT optional.
	// */ TStructMember:
	//	TStructGetter
	//	| TStructSetter
	//	| TStructMethod
	//	| TStructField;
	public TStructMemberElements getTStructMemberAccess() {
		return pTStructMember;
	}
	
	public ParserRule getTStructMemberRule() {
		return getTStructMemberAccess().getRule();
	}
	
	//TStructMethod:
	//	=>
	//	({TStructMethod} TypeVariables?
	//	name=IdentifierName '(') TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?;
	public TStructMethodElements getTStructMethodAccess() {
		return pTStructMethod;
	}
	
	public ParserRule getTStructMethodRule() {
		return getTStructMethodAccess().getRule();
	}
	
	//// TODO extract TypeVariableContainer to be used here
	//fragment TypeVariables *:
	//	'<' typeVars+=TypeVariable (',' typeVars+=TypeVariable)* '>';
	public TypeVariablesElements getTypeVariablesAccess() {
		return pTypeVariables;
	}
	
	public ParserRule getTypeVariablesRule() {
		return getTypeVariablesAccess().getRule();
	}
	
	//fragment ColonSepDeclaredTypeRef *:
	//	':' declaredTypeRef=TypeRef;
	public ColonSepDeclaredTypeRefElements getColonSepDeclaredTypeRefAccess() {
		return pColonSepDeclaredTypeRef;
	}
	
	public ParserRule getColonSepDeclaredTypeRefRule() {
		return getColonSepDeclaredTypeRefAccess().getRule();
	}
	
	//fragment ColonSepTypeRef *:
	//	':' typeRef=TypeRef;
	public ColonSepTypeRefElements getColonSepTypeRefAccess() {
		return pColonSepTypeRef;
	}
	
	public ParserRule getColonSepTypeRefRule() {
		return getColonSepTypeRefAccess().getRule();
	}
	
	//fragment ColonSepReturnTypeRef *:
	//	':' returnTypeRef=TypeRef;
	public ColonSepReturnTypeRefElements getColonSepReturnTypeRefAccess() {
		return pColonSepReturnTypeRef;
	}
	
	public ParserRule getColonSepReturnTypeRefRule() {
		return getColonSepReturnTypeRefAccess().getRule();
	}
	
	//TStructField:
	//	name=IdentifierName optional?='?'? ColonSepTypeRef?;
	public TStructFieldElements getTStructFieldAccess() {
		return pTStructField;
	}
	
	public ParserRule getTStructFieldRule() {
		return getTStructFieldAccess().getRule();
	}
	
	//TStructGetter:
	//	=> ({TStructGetter}
	//	'get'
	//	name=IdentifierName) optional?='?'?
	//	'(' ')' ColonSepDeclaredTypeRef?;
	public TStructGetterElements getTStructGetterAccess() {
		return pTStructGetter;
	}
	
	public ParserRule getTStructGetterRule() {
		return getTStructGetterAccess().getRule();
	}
	
	//TStructSetter:
	//	=> ({TStructSetter}
	//	'set'
	//	name=IdentifierName) optional?='?'?
	//	'(' fpar=TAnonymousFormalParameter ')';
	public TStructSetterElements getTStructSetterAccess() {
		return pTStructSetter;
	}
	
	public ParserRule getTStructSetterRule() {
		return getTStructSetterAccess().getRule();
	}
	
	//TypingStrategyUseSiteOperator TypingStrategy:
	//	'~' ('~' | STRUCTMODSUFFIX)?;
	public TypingStrategyUseSiteOperatorElements getTypingStrategyUseSiteOperatorAccess() {
		return pTypingStrategyUseSiteOperator;
	}
	
	public ParserRule getTypingStrategyUseSiteOperatorRule() {
		return getTypingStrategyUseSiteOperatorAccess().getRule();
	}
	
	//TypingStrategyDefSiteOperator TypingStrategy:
	//	'~';
	public TypingStrategyDefSiteOperatorElements getTypingStrategyDefSiteOperatorAccess() {
		return pTypingStrategyDefSiteOperator;
	}
	
	public ParserRule getTypingStrategyDefSiteOperatorRule() {
		return getTypingStrategyDefSiteOperatorAccess().getRule();
	}
	
	//terminal STRUCTMODSUFFIX:
	//	('r' | 'i' | 'w' | '\\u2205') '~';
	public TerminalRule getSTRUCTMODSUFFIXRule() {
		return tSTRUCTMODSUFFIX;
	}
	
	//TypeTypeRef:
	//	{TypeTypeRef} ('type' | constructorRef?='constructor')
	//	'{' typeArg=TypeArgInTypeTypeRef '}';
	public TypeTypeRefElements getTypeTypeRefAccess() {
		return pTypeTypeRef;
	}
	
	public ParserRule getTypeTypeRefRule() {
		return getTypeTypeRefAccess().getRule();
	}
	
	//TypeReferenceName:
	//	IDENTIFIER ('.' IDENTIFIER)*;
	public TypeReferenceNameElements getTypeReferenceNameAccess() {
		return pTypeReferenceName;
	}
	
	public ParserRule getTypeReferenceNameRule() {
		return getTypeReferenceNameAccess().getRule();
	}
	
	//TypeArgument:
	//	Wildcard | WildcardNewNotation | TypeRef;
	public TypeArgumentElements getTypeArgumentAccess() {
		return pTypeArgument;
	}
	
	public ParserRule getTypeArgumentRule() {
		return getTypeArgumentAccess().getRule();
	}
	
	//Wildcard:
	//	=> ({Wildcard} '?') ('extends' declaredUpperBound=TypeRef | 'super'
	//	declaredLowerBound=TypeRef)?;
	public WildcardElements getWildcardAccess() {
		return pWildcard;
	}
	
	public ParserRule getWildcardRule() {
		return getWildcardAccess().getRule();
	}
	
	//WildcardNewNotation Wildcard:
	//	usingInOutNotation?='out' declaredUpperBound=TypeRef | usingInOutNotation?='in' declaredLowerBound=TypeRef;
	public WildcardNewNotationElements getWildcardNewNotationAccess() {
		return pWildcardNewNotation;
	}
	
	public ParserRule getWildcardNewNotationRule() {
		return getWildcardNewNotationAccess().getRule();
	}
	
	//TypeVariable:
	//	(declaredCovariant?='out' | declaredContravariant?='in')?
	//	name=IDENTIFIER ('extends' declaredUpperBound=TypeRef)?;
	public TypeVariableElements getTypeVariableAccess() {
		return pTypeVariable;
	}
	
	public ParserRule getTypeVariableRule() {
		return getTypeVariableAccess().getRule();
	}
	
	///*
	// * [ECM11] (7.6, pp. 17)
	// * Identifier :: IdentifierName but not ReservedWord
	// * ReservedWord :: Keyword | FutureReservedWord | NullLiteral | BooleanLiteral
	// */ BindingIdentifier <Yield>:
	//	IDENTIFIER
	//	// yield as identifier as of [ECM15] (11.6.2, pp. 165)
	//	| <!Yield> 'yield'
	//	| N4Keyword;
	public BindingIdentifierElements getBindingIdentifierAccess() {
		return pBindingIdentifier;
	}
	
	public ParserRule getBindingIdentifierRule() {
		return getBindingIdentifierAccess().getRule();
	}
	
	//IdentifierName:
	//	IDENTIFIER | ReservedWord | N4Keyword;
	public IdentifierNameElements getIdentifierNameAccess() {
		return pIdentifierName;
	}
	
	public ParserRule getIdentifierNameRule() {
		return getIdentifierNameAccess().getRule();
	}
	
	//ReservedWord: // Keywords as of [ECM15] (11.6.2, pp. 165)
	//	'break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' |
	//	'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' |
	//	'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' // null literal
	//	| 'null' // boolean literal
	//	| 'true' | 'false' // Future Reserved Word as of [ECM15] (11.6.2.2, pp. 166)
	//	// | 'await' /* reserved word only if parse goal is module - compromise: allow as identifier and validate */
	//	| 'enum';
	public ReservedWordElements getReservedWordAccess() {
		return pReservedWord;
	}
	
	public ParserRule getReservedWordRule() {
		return getReservedWordAccess().getRule();
	}
	
	//N4Keyword:
	//	'get' | 'set'
	//	| 'let'
	//	| 'project'
	//	| 'external' | 'abstract' | 'static'
	//	| 'as' | 'from' | 'constructor' | 'of' | 'target'
	//	| 'type' | 'union' | 'intersection'
	//	| 'This' | 'Promisify'
	//	// future reserved keyword in [ECM15] only in modules, we add additional validation
	//	| 'await'
	//	// async is not a reserved keyword, i.e. it can be used as a variable name
	//	| 'async'
	//	// future reserved keywords in [ECM15], restricted via static semantic in [ECM15]
	//	| 'implements' | 'interface'
	//	| 'private' | 'protected' | 'public' // package not used in N4JS
	//	// definition-site variance
	//	| 'out';
	public N4KeywordElements getN4KeywordAccess() {
		return pN4Keyword;
	}
	
	public ParserRule getN4KeywordRule() {
		return getN4KeywordAccess().getRule();
	}
	
	//terminal IDENTIFIER:
	//	IDENTIFIER_START IDENTIFIER_PART*;
	public TerminalRule getIDENTIFIERRule() {
		return tIDENTIFIER;
	}
	
	//terminal INT returns ecore::EBigDecimal:
	//	DECIMAL_INTEGER_LITERAL_FRAGMENT;
	public TerminalRule getINTRule() {
		return tINT;
	}
	
	//terminal ML_COMMENT:
	//	ML_COMMENT_FRAGMENT;
	public TerminalRule getML_COMMENTRule() {
		return tML_COMMENT;
	}
	
	//terminal SL_COMMENT:
	//	'//' !LINE_TERMINATOR_FRAGMENT*;
	public TerminalRule getSL_COMMENTRule() {
		return tSL_COMMENT;
	}
	
	//terminal EOL:
	//	LINE_TERMINATOR_SEQUENCE_FRAGMENT;
	public TerminalRule getEOLRule() {
		return tEOL;
	}
	
	//terminal WS:
	//	WHITESPACE_FRAGMENT+;
	public TerminalRule getWSRule() {
		return tWS;
	}
	
	//terminal fragment UNICODE_ESCAPE_FRAGMENT:
	//	'\\' ('u' (HEX_DIGIT (HEX_DIGIT (HEX_DIGIT HEX_DIGIT?)?)?
	//	| '{' HEX_DIGIT* '}'?)?)?;
	public TerminalRule getUNICODE_ESCAPE_FRAGMENTRule() {
		return tUNICODE_ESCAPE_FRAGMENT;
	}
	
	//terminal fragment IDENTIFIER_START:
	//	UNICODE_LETTER_FRAGMENT
	//	| '$'
	//	| '_'
	//	| UNICODE_ESCAPE_FRAGMENT;
	public TerminalRule getIDENTIFIER_STARTRule() {
		return tIDENTIFIER_START;
	}
	
	//terminal fragment IDENTIFIER_PART:
	//	UNICODE_LETTER_FRAGMENT
	//	| UNICODE_ESCAPE_FRAGMENT
	//	| '$'
	//	| UNICODE_COMBINING_MARK_FRAGMENT
	//	| UNICODE_DIGIT_FRAGMENT
	//	| UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT
	//	| ZWNJ
	//	| ZWJ;
	public TerminalRule getIDENTIFIER_PARTRule() {
		return tIDENTIFIER_PART;
	}
	
	//terminal DOT_DOT:
	//	'..';
	public TerminalRule getDOT_DOTRule() {
		return tDOT_DOT;
	}
	
	//terminal VERSION returns ecore::EBigDecimal:
	//	'#' WS* INT;
	public TerminalRule getVERSIONRule() {
		return tVERSION;
	}
	
	//terminal fragment HEX_DIGIT:
	//	DECIMAL_DIGIT_FRAGMENT | 'a'..'f' | 'A'..'F';
	public TerminalRule getHEX_DIGITRule() {
		return gaUnicode.getHEX_DIGITRule();
	}
	
	//terminal fragment DECIMAL_INTEGER_LITERAL_FRAGMENT:
	//	'0'
	//	| '1'..'9' DECIMAL_DIGIT_FRAGMENT*;
	public TerminalRule getDECIMAL_INTEGER_LITERAL_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_INTEGER_LITERAL_FRAGMENTRule();
	}
	
	//terminal fragment DECIMAL_DIGIT_FRAGMENT:
	//	'0'..'9';
	public TerminalRule getDECIMAL_DIGIT_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment ZWJ:
	//	'\\u200D';
	public TerminalRule getZWJRule() {
		return gaUnicode.getZWJRule();
	}
	
	//terminal fragment ZWNJ:
	//	'\\u200C';
	public TerminalRule getZWNJRule() {
		return gaUnicode.getZWNJRule();
	}
	
	//terminal fragment BOM:
	//	'\\uFEFF';
	public TerminalRule getBOMRule() {
		return gaUnicode.getBOMRule();
	}
	
	//terminal fragment WHITESPACE_FRAGMENT:
	//	'\\u0009' | '\\u000B' | '\\u000C' | '\\u0020' | '\\u00A0' | BOM | UNICODE_SPACE_SEPARATOR_FRAGMENT;
	public TerminalRule getWHITESPACE_FRAGMENTRule() {
		return gaUnicode.getWHITESPACE_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_FRAGMENT:
	//	'\\u000A' | '\\u000D' | '\\u2028' | '\\u2029';
	public TerminalRule getLINE_TERMINATOR_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_SEQUENCE_FRAGMENT:
	//	'\\u000A' | '\\u000D' '\\u000A'? | '\\u2028' | '\\u2029';
	public TerminalRule getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule();
	}
	
	//terminal fragment SL_COMMENT_FRAGMENT:
	//	'//' !LINE_TERMINATOR_FRAGMENT*;
	public TerminalRule getSL_COMMENT_FRAGMENTRule() {
		return gaUnicode.getSL_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment ML_COMMENT_FRAGMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENT_FRAGMENTRule() {
		return gaUnicode.getML_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_COMBINING_MARK_FRAGMENT: // any character in the Unicode categories
	//// ―Non-spacing mark (Mn)
	//// ―Combining spacing mark (Mc)
	//	'\\u0300'..'\\u036F' | '\\u0483'..'\\u0487' | '\\u0591'..'\\u05BD' | '\\u05BF' | '\\u05C1'..'\\u05C2' | '\\u05C4'..'\\u05C5' |
	//	'\\u05C7' | '\\u0610'..'\\u061A' | '\\u064B'..'\\u065F' | '\\u0670' | '\\u06D6'..'\\u06DC' | '\\u06DF'..'\\u06E4' |
	//	'\\u06E7'..'\\u06E8' | '\\u06EA'..'\\u06ED' | '\\u0711' | '\\u0730'..'\\u074A' | '\\u07A6'..'\\u07B0' | '\\u07EB'..'\\u07F3' |
	//	'\\u0816'..'\\u0819' | '\\u081B'..'\\u0823' | '\\u0825'..'\\u0827' | '\\u0829'..'\\u082D' | '\\u0859'..'\\u085B' |
	//	'\\u08E3'..'\\u0903' | '\\u093A'..'\\u093C' | '\\u093E'..'\\u094F' | '\\u0951'..'\\u0957' | '\\u0962'..'\\u0963' |
	//	'\\u0981'..'\\u0983' | '\\u09BC' | '\\u09BE'..'\\u09C4' | '\\u09C7'..'\\u09C8' | '\\u09CB'..'\\u09CD' | '\\u09D7' |
	//	'\\u09E2'..'\\u09E3' | '\\u0A01'..'\\u0A03' | '\\u0A3C' | '\\u0A3E'..'\\u0A42' | '\\u0A47'..'\\u0A48' | '\\u0A4B'..'\\u0A4D' |
	//	'\\u0A51' | '\\u0A70'..'\\u0A71' | '\\u0A75' | '\\u0A81'..'\\u0A83' | '\\u0ABC' | '\\u0ABE'..'\\u0AC5' | '\\u0AC7'..'\\u0AC9' |
	//	'\\u0ACB'..'\\u0ACD' | '\\u0AE2'..'\\u0AE3' | '\\u0B01'..'\\u0B03' | '\\u0B3C' | '\\u0B3E'..'\\u0B44' | '\\u0B47'..'\\u0B48' |
	//	'\\u0B4B'..'\\u0B4D' | '\\u0B56'..'\\u0B57' | '\\u0B62'..'\\u0B63' | '\\u0B82' | '\\u0BBE'..'\\u0BC2' | '\\u0BC6'..'\\u0BC8' |
	//	'\\u0BCA'..'\\u0BCD' | '\\u0BD7' | '\\u0C00'..'\\u0C03' | '\\u0C3E'..'\\u0C44' | '\\u0C46'..'\\u0C48' | '\\u0C4A'..'\\u0C4D' |
	//	'\\u0C55'..'\\u0C56' | '\\u0C62'..'\\u0C63' | '\\u0C81'..'\\u0C83' | '\\u0CBC' | '\\u0CBE'..'\\u0CC4' | '\\u0CC6'..'\\u0CC8' |
	//	'\\u0CCA'..'\\u0CCD' | '\\u0CD5'..'\\u0CD6' | '\\u0CE2'..'\\u0CE3' | '\\u0D01'..'\\u0D03' | '\\u0D3E'..'\\u0D44' |
	//	'\\u0D46'..'\\u0D48' | '\\u0D4A'..'\\u0D4D' | '\\u0D57' | '\\u0D62'..'\\u0D63' | '\\u0D82'..'\\u0D83' | '\\u0DCA' |
	//	'\\u0DCF'..'\\u0DD4' | '\\u0DD6' | '\\u0DD8'..'\\u0DDF' | '\\u0DF2'..'\\u0DF3' | '\\u0E31' | '\\u0E34'..'\\u0E3A' |
	//	'\\u0E47'..'\\u0E4E' | '\\u0EB1' | '\\u0EB4'..'\\u0EB9' | '\\u0EBB'..'\\u0EBC' | '\\u0EC8'..'\\u0ECD' | '\\u0F18'..'\\u0F19' |
	//	'\\u0F35' | '\\u0F37' | '\\u0F39' | '\\u0F3E'..'\\u0F3F' | '\\u0F71'..'\\u0F84' | '\\u0F86'..'\\u0F87' | '\\u0F8D'..'\\u0F97' |
	//	'\\u0F99'..'\\u0FBC' | '\\u0FC6' | '\\u102B'..'\\u103E' | '\\u1056'..'\\u1059' | '\\u105E'..'\\u1060' | '\\u1062'..'\\u1064' |
	//	'\\u1067'..'\\u106D' | '\\u1071'..'\\u1074' | '\\u1082'..'\\u108D' | '\\u108F' | '\\u109A'..'\\u109D' | '\\u135D'..'\\u135F' |
	//	'\\u1712'..'\\u1714' | '\\u1732'..'\\u1734' | '\\u1752'..'\\u1753' | '\\u1772'..'\\u1773' | '\\u17B4'..'\\u17D3' | '\\u17DD' |
	//	'\\u180B'..'\\u180D' | '\\u18A9' | '\\u1920'..'\\u192B' | '\\u1930'..'\\u193B' | '\\u1A17'..'\\u1A1B' | '\\u1A55'..'\\u1A5E' |
	//	'\\u1A60'..'\\u1A7C' | '\\u1A7F' | '\\u1AB0'..'\\u1ABD' | '\\u1B00'..'\\u1B04' | '\\u1B34'..'\\u1B44' | '\\u1B6B'..'\\u1B73' |
	//	'\\u1B80'..'\\u1B82' | '\\u1BA1'..'\\u1BAD' | '\\u1BE6'..'\\u1BF3' | '\\u1C24'..'\\u1C37' | '\\u1CD0'..'\\u1CD2' |
	//	'\\u1CD4'..'\\u1CE8' | '\\u1CED' | '\\u1CF2'..'\\u1CF4' | '\\u1CF8'..'\\u1CF9' | '\\u1DC0'..'\\u1DF5' | '\\u1DFC'..'\\u1DFF' |
	//	'\\u20D0'..'\\u20DC' | '\\u20E1' | '\\u20E5'..'\\u20F0' | '\\u2CEF'..'\\u2CF1' | '\\u2D7F' | '\\u2DE0'..'\\u2DFF' |
	//	'\\u302A'..'\\u302F' | '\\u3099'..'\\u309A' | '\\uA66F' | '\\uA674'..'\\uA67D' | '\\uA69E'..'\\uA69F' | '\\uA6F0'..'\\uA6F1' |
	//	'\\uA802' | '\\uA806' | '\\uA80B' | '\\uA823'..'\\uA827' | '\\uA880'..'\\uA881' | '\\uA8B4'..'\\uA8C4' | '\\uA8E0'..'\\uA8F1' |
	//	'\\uA926'..'\\uA92D' | '\\uA947'..'\\uA953' | '\\uA980'..'\\uA983' | '\\uA9B3'..'\\uA9C0' | '\\uA9E5' | '\\uAA29'..'\\uAA36' |
	//	'\\uAA43' | '\\uAA4C'..'\\uAA4D' | '\\uAA7B'..'\\uAA7D' | '\\uAAB0' | '\\uAAB2'..'\\uAAB4' | '\\uAAB7'..'\\uAAB8' |
	//	'\\uAABE'..'\\uAABF' | '\\uAAC1' | '\\uAAEB'..'\\uAAEF' | '\\uAAF5'..'\\uAAF6' | '\\uABE3'..'\\uABEA' | '\\uABEC'..'\\uABED' |
	//	'\\uFB1E' | '\\uFE00'..'\\uFE0F' | '\\uFE20'..'\\uFE2F';
	public TerminalRule getUNICODE_COMBINING_MARK_FRAGMENTRule() {
		return gaUnicode.getUNICODE_COMBINING_MARK_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_DIGIT_FRAGMENT: // any character in the Unicode categories
	//// ―Decimal number (Nd)
	//	'\\u0030'..'\\u0039' | '\\u0660'..'\\u0669' | '\\u06F0'..'\\u06F9' | '\\u07C0'..'\\u07C9' | '\\u0966'..'\\u096F' |
	//	'\\u09E6'..'\\u09EF' | '\\u0A66'..'\\u0A6F' | '\\u0AE6'..'\\u0AEF' | '\\u0B66'..'\\u0B6F' | '\\u0BE6'..'\\u0BEF' |
	//	'\\u0C66'..'\\u0C6F' | '\\u0CE6'..'\\u0CEF' | '\\u0D66'..'\\u0D6F' | '\\u0DE6'..'\\u0DEF' | '\\u0E50'..'\\u0E59' |
	//	'\\u0ED0'..'\\u0ED9' | '\\u0F20'..'\\u0F29' | '\\u1040'..'\\u1049' | '\\u1090'..'\\u1099' | '\\u17E0'..'\\u17E9' |
	//	'\\u1810'..'\\u1819' | '\\u1946'..'\\u194F' | '\\u19D0'..'\\u19D9' | '\\u1A80'..'\\u1A89' | '\\u1A90'..'\\u1A99' |
	//	'\\u1B50'..'\\u1B59' | '\\u1BB0'..'\\u1BB9' | '\\u1C40'..'\\u1C49' | '\\u1C50'..'\\u1C59' | '\\uA620'..'\\uA629' |
	//	'\\uA8D0'..'\\uA8D9' | '\\uA900'..'\\uA909' | '\\uA9D0'..'\\uA9D9' | '\\uA9F0'..'\\uA9F9' | '\\uAA50'..'\\uAA59' |
	//	'\\uABF0'..'\\uABF9' | '\\uFF10'..'\\uFF19';
	public TerminalRule getUNICODE_DIGIT_FRAGMENTRule() {
		return gaUnicode.getUNICODE_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT: // any character in the Unicode categories
	//// ―Connector punctuation (Pc)
	//	'\\u005F' | '\\u203F'..'\\u2040' | '\\u2054' | '\\uFE33'..'\\uFE34' | '\\uFE4D'..'\\uFE4F' | '\\uFF3F';
	public TerminalRule getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule() {
		return gaUnicode.getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_LETTER_FRAGMENT: // any character in the Unicode categories
	//// ―Uppercase letter (Lu)
	//// ―Lowercase letter (Ll)
	//// ―Titlecase letter (Lt)
	//// ―Modifier letter (Lm)
	//// ―Other letter (Lo)
	//// ―Letter number (Nl)
	//	'\\u0041'..'\\u005A' | '\\u0061'..'\\u007A' | '\\u00AA' | '\\u00B5' | '\\u00BA' | '\\u00C0'..'\\u00D6' | '\\u00D8'..'\\u00F6' |
	//	'\\u00F8'..'\\u02C1' | '\\u02C6'..'\\u02D1' | '\\u02E0'..'\\u02E4' | '\\u02EC' | '\\u02EE' | '\\u0370'..'\\u0374' |
	//	'\\u0376'..'\\u0377' | '\\u037A'..'\\u037D' | '\\u037F' | '\\u0386' | '\\u0388'..'\\u038A' | '\\u038C' | '\\u038E'..'\\u03A1' |
	//	'\\u03A3'..'\\u03F5' | '\\u03F7'..'\\u0481' | '\\u048A'..'\\u052F' | '\\u0531'..'\\u0556' | '\\u0559' | '\\u0561'..'\\u0587' |
	//	'\\u05D0'..'\\u05EA' | '\\u05F0'..'\\u05F2' | '\\u0620'..'\\u064A' | '\\u066E'..'\\u066F' | '\\u0671'..'\\u06D3' | '\\u06D5' |
	//	'\\u06E5'..'\\u06E6' | '\\u06EE'..'\\u06EF' | '\\u06FA'..'\\u06FC' | '\\u06FF' | '\\u0710' | '\\u0712'..'\\u072F' |
	//	'\\u074D'..'\\u07A5' | '\\u07B1' | '\\u07CA'..'\\u07EA' | '\\u07F4'..'\\u07F5' | '\\u07FA' | '\\u0800'..'\\u0815' | '\\u081A' |
	//	'\\u0824' | '\\u0828' | '\\u0840'..'\\u0858' | '\\u08A0'..'\\u08B4' | '\\u0904'..'\\u0939' | '\\u093D' | '\\u0950' |
	//	'\\u0958'..'\\u0961' | '\\u0971'..'\\u0980' | '\\u0985'..'\\u098C' | '\\u098F'..'\\u0990' | '\\u0993'..'\\u09A8' |
	//	'\\u09AA'..'\\u09B0' | '\\u09B2' | '\\u09B6'..'\\u09B9' | '\\u09BD' | '\\u09CE' | '\\u09DC'..'\\u09DD' | '\\u09DF'..'\\u09E1' |
	//	'\\u09F0'..'\\u09F1' | '\\u0A05'..'\\u0A0A' | '\\u0A0F'..'\\u0A10' | '\\u0A13'..'\\u0A28' | '\\u0A2A'..'\\u0A30' |
	//	'\\u0A32'..'\\u0A33' | '\\u0A35'..'\\u0A36' | '\\u0A38'..'\\u0A39' | '\\u0A59'..'\\u0A5C' | '\\u0A5E' | '\\u0A72'..'\\u0A74' |
	//	'\\u0A85'..'\\u0A8D' | '\\u0A8F'..'\\u0A91' | '\\u0A93'..'\\u0AA8' | '\\u0AAA'..'\\u0AB0' | '\\u0AB2'..'\\u0AB3' |
	//	'\\u0AB5'..'\\u0AB9' | '\\u0ABD' | '\\u0AD0' | '\\u0AE0'..'\\u0AE1' | '\\u0AF9' | '\\u0B05'..'\\u0B0C' | '\\u0B0F'..'\\u0B10' |
	//	'\\u0B13'..'\\u0B28' | '\\u0B2A'..'\\u0B30' | '\\u0B32'..'\\u0B33' | '\\u0B35'..'\\u0B39' | '\\u0B3D' | '\\u0B5C'..'\\u0B5D' |
	//	'\\u0B5F'..'\\u0B61' | '\\u0B71' | '\\u0B83' | '\\u0B85'..'\\u0B8A' | '\\u0B8E'..'\\u0B90' | '\\u0B92'..'\\u0B95' |
	//	'\\u0B99'..'\\u0B9A' | '\\u0B9C' | '\\u0B9E'..'\\u0B9F' | '\\u0BA3'..'\\u0BA4' | '\\u0BA8'..'\\u0BAA' | '\\u0BAE'..'\\u0BB9' |
	//	'\\u0BD0' | '\\u0C05'..'\\u0C0C' | '\\u0C0E'..'\\u0C10' | '\\u0C12'..'\\u0C28' | '\\u0C2A'..'\\u0C39' | '\\u0C3D' |
	//	'\\u0C58'..'\\u0C5A' | '\\u0C60'..'\\u0C61' | '\\u0C85'..'\\u0C8C' | '\\u0C8E'..'\\u0C90' | '\\u0C92'..'\\u0CA8' |
	//	'\\u0CAA'..'\\u0CB3' | '\\u0CB5'..'\\u0CB9' | '\\u0CBD' | '\\u0CDE' | '\\u0CE0'..'\\u0CE1' | '\\u0CF1'..'\\u0CF2' |
	//	'\\u0D05'..'\\u0D0C' | '\\u0D0E'..'\\u0D10' | '\\u0D12'..'\\u0D3A' | '\\u0D3D' | '\\u0D4E' | '\\u0D5F'..'\\u0D61' |
	//	'\\u0D7A'..'\\u0D7F' | '\\u0D85'..'\\u0D96' | '\\u0D9A'..'\\u0DB1' | '\\u0DB3'..'\\u0DBB' | '\\u0DBD' | '\\u0DC0'..'\\u0DC6' |
	//	'\\u0E01'..'\\u0E30' | '\\u0E32'..'\\u0E33' | '\\u0E40'..'\\u0E46' | '\\u0E81'..'\\u0E82' | '\\u0E84' | '\\u0E87'..'\\u0E88' |
	//	'\\u0E8A' | '\\u0E8D' | '\\u0E94'..'\\u0E97' | '\\u0E99'..'\\u0E9F' | '\\u0EA1'..'\\u0EA3' | '\\u0EA5' | '\\u0EA7' |
	//	'\\u0EAA'..'\\u0EAB' | '\\u0EAD'..'\\u0EB0' | '\\u0EB2'..'\\u0EB3' | '\\u0EBD' | '\\u0EC0'..'\\u0EC4' | '\\u0EC6' |
	//	'\\u0EDC'..'\\u0EDF' | '\\u0F00' | '\\u0F40'..'\\u0F47' | '\\u0F49'..'\\u0F6C' | '\\u0F88'..'\\u0F8C' | '\\u1000'..'\\u102A' |
	//	'\\u103F' | '\\u1050'..'\\u1055' | '\\u105A'..'\\u105D' | '\\u1061' | '\\u1065'..'\\u1066' | '\\u106E'..'\\u1070' |
	//	'\\u1075'..'\\u1081' | '\\u108E' | '\\u10A0'..'\\u10C5' | '\\u10C7' | '\\u10CD' | '\\u10D0'..'\\u10FA' | '\\u10FC'..'\\u1248' |
	//	'\\u124A'..'\\u124D' | '\\u1250'..'\\u1256' | '\\u1258' | '\\u125A'..'\\u125D' | '\\u1260'..'\\u1288' | '\\u128A'..'\\u128D' |
	//	'\\u1290'..'\\u12B0' | '\\u12B2'..'\\u12B5' | '\\u12B8'..'\\u12BE' | '\\u12C0' | '\\u12C2'..'\\u12C5' | '\\u12C8'..'\\u12D6' |
	//	'\\u12D8'..'\\u1310' | '\\u1312'..'\\u1315' | '\\u1318'..'\\u135A' | '\\u1380'..'\\u138F' | '\\u13A0'..'\\u13F5' |
	//	'\\u13F8'..'\\u13FD' | '\\u1401'..'\\u166C' | '\\u166F'..'\\u167F' | '\\u1681'..'\\u169A' | '\\u16A0'..'\\u16EA' |
	//	'\\u16EE'..'\\u16F8' | '\\u1700'..'\\u170C' | '\\u170E'..'\\u1711' | '\\u1720'..'\\u1731' | '\\u1740'..'\\u1751' |
	//	'\\u1760'..'\\u176C' | '\\u176E'..'\\u1770' | '\\u1780'..'\\u17B3' | '\\u17D7' | '\\u17DC' | '\\u1820'..'\\u1877' |
	//	'\\u1880'..'\\u18A8' | '\\u18AA' | '\\u18B0'..'\\u18F5' | '\\u1900'..'\\u191E' | '\\u1950'..'\\u196D' | '\\u1970'..'\\u1974' |
	//	'\\u1980'..'\\u19AB' | '\\u19B0'..'\\u19C9' | '\\u1A00'..'\\u1A16' | '\\u1A20'..'\\u1A54' | '\\u1AA7' | '\\u1B05'..'\\u1B33' |
	//	'\\u1B45'..'\\u1B4B' | '\\u1B83'..'\\u1BA0' | '\\u1BAE'..'\\u1BAF' | '\\u1BBA'..'\\u1BE5' | '\\u1C00'..'\\u1C23' |
	//	'\\u1C4D'..'\\u1C4F' | '\\u1C5A'..'\\u1C7D' | '\\u1CE9'..'\\u1CEC' | '\\u1CEE'..'\\u1CF1' | '\\u1CF5'..'\\u1CF6' |
	//	'\\u1D00'..'\\u1DBF' | '\\u1E00'..'\\u1F15' | '\\u1F18'..'\\u1F1D' | '\\u1F20'..'\\u1F45' | '\\u1F48'..'\\u1F4D' |
	//	'\\u1F50'..'\\u1F57' | '\\u1F59' | '\\u1F5B' | '\\u1F5D' | '\\u1F5F'..'\\u1F7D' | '\\u1F80'..'\\u1FB4' | '\\u1FB6'..'\\u1FBC' |
	//	'\\u1FBE' | '\\u1FC2'..'\\u1FC4' | '\\u1FC6'..'\\u1FCC' | '\\u1FD0'..'\\u1FD3' | '\\u1FD6'..'\\u1FDB' | '\\u1FE0'..'\\u1FEC' |
	//	'\\u1FF2'..'\\u1FF4' | '\\u1FF6'..'\\u1FFC' | '\\u2071' | '\\u207F' | '\\u2090'..'\\u209C' | '\\u2102' | '\\u2107' |
	//	'\\u210A'..'\\u2113' | '\\u2115' | '\\u2119'..'\\u211D' | '\\u2124' | '\\u2126' | '\\u2128' | '\\u212A'..'\\u212D' |
	//	'\\u212F'..'\\u2139' | '\\u213C'..'\\u213F' | '\\u2145'..'\\u2149' | '\\u214E' | '\\u2160'..'\\u2188' | '\\u2C00'..'\\u2C2E' |
	//	'\\u2C30'..'\\u2C5E' | '\\u2C60'..'\\u2CE4' | '\\u2CEB'..'\\u2CEE' | '\\u2CF2'..'\\u2CF3' | '\\u2D00'..'\\u2D25' | '\\u2D27' |
	//	'\\u2D2D' | '\\u2D30'..'\\u2D67' | '\\u2D6F' | '\\u2D80'..'\\u2D96' | '\\u2DA0'..'\\u2DA6' | '\\u2DA8'..'\\u2DAE' |
	//	'\\u2DB0'..'\\u2DB6' | '\\u2DB8'..'\\u2DBE' | '\\u2DC0'..'\\u2DC6' | '\\u2DC8'..'\\u2DCE' | '\\u2DD0'..'\\u2DD6' |
	//	'\\u2DD8'..'\\u2DDE' | '\\u2E2F' | '\\u3005'..'\\u3007' | '\\u3021'..'\\u3029' | '\\u3031'..'\\u3035' | '\\u3038'..'\\u303C' |
	//	'\\u3041'..'\\u3096' | '\\u309D'..'\\u309F' | '\\u30A1'..'\\u30FA' | '\\u30FC'..'\\u30FF' | '\\u3105'..'\\u312D' |
	//	'\\u3131'..'\\u318E' | '\\u31A0'..'\\u31BA' | '\\u31F0'..'\\u31FF' | '\\u3400'..'\\u4DB5' | '\\u4E00'..'\\u9FD5' |
	//	'\\uA000'..'\\uA48C' | '\\uA4D0'..'\\uA4FD' | '\\uA500'..'\\uA60C' | '\\uA610'..'\\uA61F' | '\\uA62A'..'\\uA62B' |
	//	'\\uA640'..'\\uA66E' | '\\uA67F'..'\\uA69D' | '\\uA6A0'..'\\uA6EF' | '\\uA717'..'\\uA71F' | '\\uA722'..'\\uA788' |
	//	'\\uA78B'..'\\uA7AD' | '\\uA7B0'..'\\uA7B7' | '\\uA7F7'..'\\uA801' | '\\uA803'..'\\uA805' | '\\uA807'..'\\uA80A' |
	//	'\\uA80C'..'\\uA822' | '\\uA840'..'\\uA873' | '\\uA882'..'\\uA8B3' | '\\uA8F2'..'\\uA8F7' | '\\uA8FB' | '\\uA8FD' |
	//	'\\uA90A'..'\\uA925' | '\\uA930'..'\\uA946' | '\\uA960'..'\\uA97C' | '\\uA984'..'\\uA9B2' | '\\uA9CF' | '\\uA9E0'..'\\uA9E4' |
	//	'\\uA9E6'..'\\uA9EF' | '\\uA9FA'..'\\uA9FE' | '\\uAA00'..'\\uAA28' | '\\uAA40'..'\\uAA42' | '\\uAA44'..'\\uAA4B' |
	//	'\\uAA60'..'\\uAA76' | '\\uAA7A' | '\\uAA7E'..'\\uAAAF' | '\\uAAB1' | '\\uAAB5'..'\\uAAB6' | '\\uAAB9'..'\\uAABD' | '\\uAAC0' |
	//	'\\uAAC2' | '\\uAADB'..'\\uAADD' | '\\uAAE0'..'\\uAAEA' | '\\uAAF2'..'\\uAAF4' | '\\uAB01'..'\\uAB06' | '\\uAB09'..'\\uAB0E' |
	//	'\\uAB11'..'\\uAB16' | '\\uAB20'..'\\uAB26' | '\\uAB28'..'\\uAB2E' | '\\uAB30'..'\\uAB5A' | '\\uAB5C'..'\\uAB65' |
	//	'\\uAB70'..'\\uABE2' | '\\uAC00'..'\\uD7A3' | '\\uD7B0'..'\\uD7C6' | '\\uD7CB'..'\\uD7FB' | '\\uF900'..'\\uFA6D' |
	//	'\\uFA70'..'\\uFAD9' | '\\uFB00'..'\\uFB06' | '\\uFB13'..'\\uFB17' | '\\uFB1D' | '\\uFB1F'..'\\uFB28' | '\\uFB2A'..'\\uFB36' |
	//	'\\uFB38'..'\\uFB3C' | '\\uFB3E' | '\\uFB40'..'\\uFB41' | '\\uFB43'..'\\uFB44' | '\\uFB46'..'\\uFBB1' | '\\uFBD3'..'\\uFD3D' |
	//	'\\uFD50'..'\\uFD8F' | '\\uFD92'..'\\uFDC7' | '\\uFDF0'..'\\uFDFB' | '\\uFE70'..'\\uFE74' | '\\uFE76'..'\\uFEFC' |
	//	'\\uFF21'..'\\uFF3A' | '\\uFF41'..'\\uFF5A' | '\\uFF66'..'\\uFFBE' | '\\uFFC2'..'\\uFFC7' | '\\uFFCA'..'\\uFFCF' |
	//	'\\uFFD2'..'\\uFFD7' | '\\uFFDA'..'\\uFFDC';
	public TerminalRule getUNICODE_LETTER_FRAGMENTRule() {
		return gaUnicode.getUNICODE_LETTER_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_SPACE_SEPARATOR_FRAGMENT: // any character in the Unicode categories
	//// ―space separator (Zs)
	//	'\\u0020' | '\\u00A0' | '\\u1680' | '\\u2000'..'\\u200A' | '\\u202F' | '\\u205F' | '\\u3000';
	public TerminalRule getUNICODE_SPACE_SEPARATOR_FRAGMENTRule() {
		return gaUnicode.getUNICODE_SPACE_SEPARATOR_FRAGMENTRule();
	}
	
	//terminal fragment ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaUnicode.getANY_OTHERRule();
	}
}
