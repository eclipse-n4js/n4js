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
package org.eclipse.n4js.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.services.TypeExpressionsGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AlternativeAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class TypeExpressionsSyntacticSequencer extends AbstractSyntacticSequencer {

	protected TypeExpressionsGrammarAccess grammarAccess;
	protected AbstractElementAlias match_ArrayNTypeExpression_CommaKeyword_1_1_2_q;
	protected AbstractElementAlias match_ArrowFunctionTypeExpression_CommaKeyword_0_0_2_3_q;
	protected AbstractElementAlias match_IntersectionTypeExpression_AmpersandKeyword_0_q;
	protected AbstractElementAlias match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__a;
	protected AbstractElementAlias match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__p;
	protected AbstractElementAlias match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__a;
	protected AbstractElementAlias match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__p;
	protected AbstractElementAlias match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__a;
	protected AbstractElementAlias match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__p;
	protected AbstractElementAlias match_MappedTypeRef_PlusSignKeyword_1_0_0_q;
	protected AbstractElementAlias match_MappedTypeRef_PlusSignKeyword_7_0_0_q;
	protected AbstractElementAlias match_MappedTypeRef_SemicolonKeyword_9_q;
	protected AbstractElementAlias match_NumericLiteralTypeRef_PlusSignKeyword_0_0_q;
	protected AbstractElementAlias match_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q;
	protected AbstractElementAlias match_TAnonymousFormalParameterListWithDeclaredThisType_CommaKeyword_2_q;
	protected AbstractElementAlias match_TAnonymousFormalParameterList_CommaKeyword_2_q;
	protected AbstractElementAlias match_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q;
	protected AbstractElementAlias match_TFormalParameter_QuestionMarkKeyword_2_q;
	protected AbstractElementAlias match_TStructField_ReadonlyKeyword_0_1_0_0_q;
	protected AbstractElementAlias match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q;
	protected AbstractElementAlias match_TStructMethod_CommaKeyword_0_0_1_3_q;
	protected AbstractElementAlias match_UnionTypeExpression_VerticalLineKeyword_0_q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (TypeExpressionsGrammarAccess) access;
		match_ArrayNTypeExpression_CommaKeyword_1_1_2_q = new TokenAlias(false, true, grammarAccess.getArrayNTypeExpressionAccess().getCommaKeyword_1_1_2());
		match_ArrowFunctionTypeExpression_CommaKeyword_0_0_2_3_q = new TokenAlias(false, true, grammarAccess.getArrowFunctionTypeExpressionAccess().getCommaKeyword_0_0_2_3());
		match_IntersectionTypeExpression_AmpersandKeyword_0_q = new TokenAlias(false, true, grammarAccess.getIntersectionTypeExpressionAccess().getAmpersandKeyword_0());
		match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__a = new GroupAlias(true, true, new TokenAlias(false, true, grammarAccess.getIntersectionTypeExpressionAccess().getAmpersandKeyword_0()), new TokenAlias(false, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0()), new TokenAlias(false, true, grammarAccess.getUnionTypeExpressionAccess().getVerticalLineKeyword_0()));
		match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__p = new GroupAlias(true, false, new TokenAlias(false, true, grammarAccess.getIntersectionTypeExpressionAccess().getAmpersandKeyword_0()), new TokenAlias(false, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0()), new TokenAlias(false, true, grammarAccess.getUnionTypeExpressionAccess().getVerticalLineKeyword_0()));
		match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__a = new GroupAlias(true, true, new TokenAlias(false, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0()), new TokenAlias(false, true, grammarAccess.getUnionTypeExpressionAccess().getVerticalLineKeyword_0()), new TokenAlias(false, true, grammarAccess.getIntersectionTypeExpressionAccess().getAmpersandKeyword_0()));
		match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__p = new GroupAlias(true, false, new TokenAlias(false, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0()), new TokenAlias(false, true, grammarAccess.getUnionTypeExpressionAccess().getVerticalLineKeyword_0()), new TokenAlias(false, true, grammarAccess.getIntersectionTypeExpressionAccess().getAmpersandKeyword_0()));
		match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__a = new GroupAlias(true, true, new TokenAlias(false, true, grammarAccess.getUnionTypeExpressionAccess().getVerticalLineKeyword_0()), new TokenAlias(false, true, grammarAccess.getIntersectionTypeExpressionAccess().getAmpersandKeyword_0()), new TokenAlias(false, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0()));
		match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__p = new GroupAlias(true, false, new TokenAlias(false, true, grammarAccess.getUnionTypeExpressionAccess().getVerticalLineKeyword_0()), new TokenAlias(false, true, grammarAccess.getIntersectionTypeExpressionAccess().getAmpersandKeyword_0()), new TokenAlias(false, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0()));
		match_MappedTypeRef_PlusSignKeyword_1_0_0_q = new TokenAlias(false, true, grammarAccess.getMappedTypeRefAccess().getPlusSignKeyword_1_0_0());
		match_MappedTypeRef_PlusSignKeyword_7_0_0_q = new TokenAlias(false, true, grammarAccess.getMappedTypeRefAccess().getPlusSignKeyword_7_0_0());
		match_MappedTypeRef_SemicolonKeyword_9_q = new TokenAlias(false, true, grammarAccess.getMappedTypeRefAccess().getSemicolonKeyword_9());
		match_NumericLiteralTypeRef_PlusSignKeyword_0_0_q = new TokenAlias(false, true, grammarAccess.getNumericLiteralTypeRefAccess().getPlusSignKeyword_0_0());
		match_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q = new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getParameterizedTypeRefStructuralAccess().getCommaKeyword_0_2_1_1()), new TokenAlias(false, false, grammarAccess.getParameterizedTypeRefStructuralAccess().getSemicolonKeyword_0_2_1_0()));
		match_TAnonymousFormalParameterListWithDeclaredThisType_CommaKeyword_2_q = new TokenAlias(false, true, grammarAccess.getTAnonymousFormalParameterListWithDeclaredThisTypeAccess().getCommaKeyword_2());
		match_TAnonymousFormalParameterList_CommaKeyword_2_q = new TokenAlias(false, true, grammarAccess.getTAnonymousFormalParameterListAccess().getCommaKeyword_2());
		match_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q = new TokenAlias(false, true, grammarAccess.getTAnonymousFormalParameterAccess().getQuestionMarkKeyword_1_0_0_1());
		match_TFormalParameter_QuestionMarkKeyword_2_q = new TokenAlias(false, true, grammarAccess.getTFormalParameterAccess().getQuestionMarkKeyword_2());
		match_TStructField_ReadonlyKeyword_0_1_0_0_q = new TokenAlias(false, true, grammarAccess.getTStructFieldAccess().getReadonlyKeyword_0_1_0_0());
		match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q = new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1()), new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0()));
		match_TStructMethod_CommaKeyword_0_0_1_3_q = new TokenAlias(false, true, grammarAccess.getTStructMethodAccess().getCommaKeyword_0_0_1_3());
		match_UnionTypeExpression_VerticalLineKeyword_0_q = new TokenAlias(false, true, grammarAccess.getUnionTypeExpressionAccess().getVerticalLineKeyword_0());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (ruleCall.getRule() == grammarAccess.getArrowRule())
			return getArrowToken(semanticObject, ruleCall, node);
		return "";
	}
	
	/**
	 * Arrow hidden(): 	'=' '>'
	 * ;
	 */
	protected String getArrowToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "= >";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_ArrayNTypeExpression_CommaKeyword_1_1_2_q.equals(syntax))
				emit_ArrayNTypeExpression_CommaKeyword_1_1_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ArrowFunctionTypeExpression_CommaKeyword_0_0_2_3_q.equals(syntax))
				emit_ArrowFunctionTypeExpression_CommaKeyword_0_0_2_3_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_IntersectionTypeExpression_AmpersandKeyword_0_q.equals(syntax))
				emit_IntersectionTypeExpression_AmpersandKeyword_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__a.equals(syntax))
				emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__p.equals(syntax))
				emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__a.equals(syntax))
				emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__p.equals(syntax))
				emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__a.equals(syntax))
				emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__p.equals(syntax))
				emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_MappedTypeRef_PlusSignKeyword_1_0_0_q.equals(syntax))
				emit_MappedTypeRef_PlusSignKeyword_1_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_MappedTypeRef_PlusSignKeyword_7_0_0_q.equals(syntax))
				emit_MappedTypeRef_PlusSignKeyword_7_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_MappedTypeRef_SemicolonKeyword_9_q.equals(syntax))
				emit_MappedTypeRef_SemicolonKeyword_9_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_NumericLiteralTypeRef_PlusSignKeyword_0_0_q.equals(syntax))
				emit_NumericLiteralTypeRef_PlusSignKeyword_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q.equals(syntax))
				emit_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TAnonymousFormalParameterListWithDeclaredThisType_CommaKeyword_2_q.equals(syntax))
				emit_TAnonymousFormalParameterListWithDeclaredThisType_CommaKeyword_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TAnonymousFormalParameterList_CommaKeyword_2_q.equals(syntax))
				emit_TAnonymousFormalParameterList_CommaKeyword_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q.equals(syntax))
				emit_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TFormalParameter_QuestionMarkKeyword_2_q.equals(syntax))
				emit_TFormalParameter_QuestionMarkKeyword_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TStructField_ReadonlyKeyword_0_1_0_0_q.equals(syntax))
				emit_TStructField_ReadonlyKeyword_0_1_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q.equals(syntax))
				emit_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TStructMethod_CommaKeyword_0_0_1_3_q.equals(syntax))
				emit_TStructMethod_CommaKeyword_0_0_1_3_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_UnionTypeExpression_VerticalLineKeyword_0_q.equals(syntax))
				emit_UnionTypeExpression_VerticalLineKeyword_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     declaredTypeArgs+=TypeArgument (ambiguity) ']' (rule end)
	 */
	protected void emit_ArrayNTypeExpression_CommaKeyword_1_1_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     ownedTypeVars+=TypeVariable (ambiguity) '>' '(' ')' Arrow returnTypePredicate=TypePredicateWithOperatorTypeRef
	 *     ownedTypeVars+=TypeVariable (ambiguity) '>' '(' ')' Arrow returnTypeRef=OperatorTypeRef
	 *     ownedTypeVars+=TypeVariable (ambiguity) '>' '(' 'this' ':' declaredThisType=TypeRef
	 *     ownedTypeVars+=TypeVariable (ambiguity) '>' '(' fpars+=TAnonymousFormalParameter
	 */
	protected void emit_ArrowFunctionTypeExpression_CommaKeyword_0_0_2_3_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '&'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '(' ')' Arrow returnTypePredicate=TypePredicateWithOperatorTypeRef
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '(' ')' Arrow returnTypeRef=OperatorTypeRef
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '(' 'this' ':' declaredThisType=TypeRef
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '(' declaredTypeArgs+=Wildcard
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '(' fpars+=TAnonymousFormalParameter
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=BINARY_INT
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=DOUBLE
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=HEX_INT
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=INT
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=LEGACY_OCTAL_INT
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=OCTAL_INT
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=SCIENTIFIC_INT
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '<' ownedTypeVars+=TypeVariable
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* 'infer' typeVarName=IDENTIFIER
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* 'intersection' '{' typeRefs+=TypeRef
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* 'this' (rule start)
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* 'this' dynamic?='+'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* 'type' '{' typeArg=TypeArgInTypeTypeRef
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* 'typeof' expr=PropertyAccessExpressionInTypeRef
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* 'union' '{' typeRefs+=TypeRef
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '{' '+'? includeReadonly?='readonly'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '{' '-' excludeReadonly?='readonly'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '{' '[' propName=IdentifierName
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '{' '}' (rule start)
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '{' '}' dynamic?='+'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* '{' astStructuralMembers+=TStructMember
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* arrayNTypeExpression?='['
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* astNamespaceLikeRefs+=NamespaceLikeRef
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* astNegated?='-'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* astValue='false'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* astValue='true'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* astValue=STRING
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* constructorRef?='constructor'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* declaredType=[Type|TypeReferenceName]
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* declaredTypeArgs+=WildcardOldNotationWithoutBound
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* definedTypingStrategy=TypingStrategyUseSiteOperator
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* dtsAbstract?='abstract'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* dtsConstructor?='new'
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* op=TypeOperator
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* {IndexAccessTypeRef.targetTypeRef=}
	 *     (rule start) '|'? (ambiguity) ('(' '|'? (ambiguity))* {ParameterizedTypeRef.declaredTypeArgs+=}
	 *     (rule start) (ambiguity) '(' ('|'? (ambiguity) '(')* {ConditionalTypeRef.typeRef=}
	 *     (rule start) (ambiguity) '(' ('|'? (ambiguity) '(')* {UnionTypeExpression.typeRefs+=}
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '(' ')' Arrow returnTypePredicate=TypePredicateWithOperatorTypeRef
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '(' ')' Arrow returnTypeRef=OperatorTypeRef
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '(' 'this' ':' declaredThisType=TypeRef
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '(' declaredTypeArgs+=Wildcard
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '(' fpars+=TAnonymousFormalParameter
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=BINARY_INT
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=DOUBLE
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=HEX_INT
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=INT
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=LEGACY_OCTAL_INT
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=OCTAL_INT
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '+'? astValue=SCIENTIFIC_INT
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '<' ownedTypeVars+=TypeVariable
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* 'infer' typeVarName=IDENTIFIER
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* 'intersection' '{' typeRefs+=TypeRef
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* 'this' (rule start)
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* 'this' dynamic?='+'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* 'type' '{' typeArg=TypeArgInTypeTypeRef
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* 'typeof' expr=PropertyAccessExpressionInTypeRef
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* 'union' '{' typeRefs+=TypeRef
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '{' '+'? includeReadonly?='readonly'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '{' '-' excludeReadonly?='readonly'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '{' '[' propName=IdentifierName
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '{' '}' (rule start)
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '{' '}' dynamic?='+'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* '{' astStructuralMembers+=TStructMember
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* arrayNTypeExpression?='['
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* astNamespaceLikeRefs+=NamespaceLikeRef
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* astNegated?='-'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* astValue='false'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* astValue='true'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* astValue=STRING
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* constructorRef?='constructor'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* declaredType=[Type|TypeReferenceName]
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* declaredTypeArgs+=WildcardOldNotationWithoutBound
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* definedTypingStrategy=TypingStrategyUseSiteOperator
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* dtsAbstract?='abstract'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* dtsConstructor?='new'
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* op=TypeOperator
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* {IndexAccessTypeRef.targetTypeRef=}
	 *     (rule start) (ambiguity) ('(' '|'? (ambiguity))* {ParameterizedTypeRef.declaredTypeArgs+=}
	 */
	protected void emit_IntersectionTypeExpression_AmpersandKeyword_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('&'? '(' '|'?)*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '(' '|'? (ambiguity) {IntersectionTypeExpression.typeRefs+=}
	 *     (rule start) '|'? (ambiguity) {IntersectionTypeExpression.typeRefs+=}
	 *     (rule start) (ambiguity) {IntersectionTypeExpression.typeRefs+=}
	 */
	protected void emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('&'? '(' '|'?)+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) {IntersectionTypeExpression.typeRefs+=}
	 */
	protected void emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q__p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('(' '|'? '&'?)*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '&'? (ambiguity) '(' ')' Arrow returnTypePredicate=TypePredicateWithOperatorTypeRef
	 *     (rule start) '&'? (ambiguity) '(' ')' Arrow returnTypeRef=OperatorTypeRef
	 *     (rule start) '&'? (ambiguity) '(' 'this' ':' declaredThisType=TypeRef
	 *     (rule start) '&'? (ambiguity) '(' declaredTypeArgs+=Wildcard
	 *     (rule start) '&'? (ambiguity) '(' fpars+=TAnonymousFormalParameter
	 *     (rule start) '&'? (ambiguity) '+'? astValue=BINARY_INT
	 *     (rule start) '&'? (ambiguity) '+'? astValue=DOUBLE
	 *     (rule start) '&'? (ambiguity) '+'? astValue=HEX_INT
	 *     (rule start) '&'? (ambiguity) '+'? astValue=INT
	 *     (rule start) '&'? (ambiguity) '+'? astValue=LEGACY_OCTAL_INT
	 *     (rule start) '&'? (ambiguity) '+'? astValue=OCTAL_INT
	 *     (rule start) '&'? (ambiguity) '+'? astValue=SCIENTIFIC_INT
	 *     (rule start) '&'? (ambiguity) '<' ownedTypeVars+=TypeVariable
	 *     (rule start) '&'? (ambiguity) 'infer' typeVarName=IDENTIFIER
	 *     (rule start) '&'? (ambiguity) 'intersection' '{' typeRefs+=TypeRef
	 *     (rule start) '&'? (ambiguity) 'this' (rule start)
	 *     (rule start) '&'? (ambiguity) 'this' dynamic?='+'
	 *     (rule start) '&'? (ambiguity) 'type' '{' typeArg=TypeArgInTypeTypeRef
	 *     (rule start) '&'? (ambiguity) 'typeof' expr=PropertyAccessExpressionInTypeRef
	 *     (rule start) '&'? (ambiguity) 'union' '{' typeRefs+=TypeRef
	 *     (rule start) '&'? (ambiguity) '{' '+'? includeReadonly?='readonly'
	 *     (rule start) '&'? (ambiguity) '{' '-' excludeReadonly?='readonly'
	 *     (rule start) '&'? (ambiguity) '{' '[' propName=IdentifierName
	 *     (rule start) '&'? (ambiguity) '{' '}' (rule start)
	 *     (rule start) '&'? (ambiguity) '{' '}' dynamic?='+'
	 *     (rule start) '&'? (ambiguity) '{' astStructuralMembers+=TStructMember
	 *     (rule start) '&'? (ambiguity) arrayNTypeExpression?='['
	 *     (rule start) '&'? (ambiguity) astNamespaceLikeRefs+=NamespaceLikeRef
	 *     (rule start) '&'? (ambiguity) astNegated?='-'
	 *     (rule start) '&'? (ambiguity) astValue='false'
	 *     (rule start) '&'? (ambiguity) astValue='true'
	 *     (rule start) '&'? (ambiguity) astValue=STRING
	 *     (rule start) '&'? (ambiguity) constructorRef?='constructor'
	 *     (rule start) '&'? (ambiguity) declaredType=[Type|TypeReferenceName]
	 *     (rule start) '&'? (ambiguity) declaredTypeArgs+=WildcardOldNotationWithoutBound
	 *     (rule start) '&'? (ambiguity) definedTypingStrategy=TypingStrategyUseSiteOperator
	 *     (rule start) '&'? (ambiguity) dtsAbstract?='abstract'
	 *     (rule start) '&'? (ambiguity) dtsConstructor?='new'
	 *     (rule start) '&'? (ambiguity) op=TypeOperator
	 *     (rule start) '&'? (ambiguity) {IndexAccessTypeRef.targetTypeRef=}
	 *     (rule start) '&'? (ambiguity) {ParameterizedTypeRef.declaredTypeArgs+=}
	 *     (rule start) '|'? '&'? (ambiguity) '(' ')' Arrow returnTypePredicate=TypePredicateWithOperatorTypeRef
	 *     (rule start) '|'? '&'? (ambiguity) '(' ')' Arrow returnTypeRef=OperatorTypeRef
	 *     (rule start) '|'? '&'? (ambiguity) '(' 'this' ':' declaredThisType=TypeRef
	 *     (rule start) '|'? '&'? (ambiguity) '(' declaredTypeArgs+=Wildcard
	 *     (rule start) '|'? '&'? (ambiguity) '(' fpars+=TAnonymousFormalParameter
	 *     (rule start) '|'? '&'? (ambiguity) '+'? astValue=BINARY_INT
	 *     (rule start) '|'? '&'? (ambiguity) '+'? astValue=DOUBLE
	 *     (rule start) '|'? '&'? (ambiguity) '+'? astValue=HEX_INT
	 *     (rule start) '|'? '&'? (ambiguity) '+'? astValue=INT
	 *     (rule start) '|'? '&'? (ambiguity) '+'? astValue=LEGACY_OCTAL_INT
	 *     (rule start) '|'? '&'? (ambiguity) '+'? astValue=OCTAL_INT
	 *     (rule start) '|'? '&'? (ambiguity) '+'? astValue=SCIENTIFIC_INT
	 *     (rule start) '|'? '&'? (ambiguity) '<' ownedTypeVars+=TypeVariable
	 *     (rule start) '|'? '&'? (ambiguity) 'infer' typeVarName=IDENTIFIER
	 *     (rule start) '|'? '&'? (ambiguity) 'intersection' '{' typeRefs+=TypeRef
	 *     (rule start) '|'? '&'? (ambiguity) 'this' (rule start)
	 *     (rule start) '|'? '&'? (ambiguity) 'this' dynamic?='+'
	 *     (rule start) '|'? '&'? (ambiguity) 'type' '{' typeArg=TypeArgInTypeTypeRef
	 *     (rule start) '|'? '&'? (ambiguity) 'typeof' expr=PropertyAccessExpressionInTypeRef
	 *     (rule start) '|'? '&'? (ambiguity) 'union' '{' typeRefs+=TypeRef
	 *     (rule start) '|'? '&'? (ambiguity) '{' '+'? includeReadonly?='readonly'
	 *     (rule start) '|'? '&'? (ambiguity) '{' '-' excludeReadonly?='readonly'
	 *     (rule start) '|'? '&'? (ambiguity) '{' '[' propName=IdentifierName
	 *     (rule start) '|'? '&'? (ambiguity) '{' '}' (rule start)
	 *     (rule start) '|'? '&'? (ambiguity) '{' '}' dynamic?='+'
	 *     (rule start) '|'? '&'? (ambiguity) '{' astStructuralMembers+=TStructMember
	 *     (rule start) '|'? '&'? (ambiguity) arrayNTypeExpression?='['
	 *     (rule start) '|'? '&'? (ambiguity) astNamespaceLikeRefs+=NamespaceLikeRef
	 *     (rule start) '|'? '&'? (ambiguity) astNegated?='-'
	 *     (rule start) '|'? '&'? (ambiguity) astValue='false'
	 *     (rule start) '|'? '&'? (ambiguity) astValue='true'
	 *     (rule start) '|'? '&'? (ambiguity) astValue=STRING
	 *     (rule start) '|'? '&'? (ambiguity) constructorRef?='constructor'
	 *     (rule start) '|'? '&'? (ambiguity) declaredType=[Type|TypeReferenceName]
	 *     (rule start) '|'? '&'? (ambiguity) declaredTypeArgs+=WildcardOldNotationWithoutBound
	 *     (rule start) '|'? '&'? (ambiguity) definedTypingStrategy=TypingStrategyUseSiteOperator
	 *     (rule start) '|'? '&'? (ambiguity) dtsAbstract?='abstract'
	 *     (rule start) '|'? '&'? (ambiguity) dtsConstructor?='new'
	 *     (rule start) '|'? '&'? (ambiguity) op=TypeOperator
	 *     (rule start) '|'? '&'? (ambiguity) {IndexAccessTypeRef.targetTypeRef=}
	 *     (rule start) '|'? '&'? (ambiguity) {ParameterizedTypeRef.declaredTypeArgs+=}
	 *     (rule start) (ambiguity) '(' ')' Arrow returnTypePredicate=TypePredicateWithOperatorTypeRef
	 *     (rule start) (ambiguity) '(' ')' Arrow returnTypeRef=OperatorTypeRef
	 *     (rule start) (ambiguity) '(' 'this' ':' declaredThisType=TypeRef
	 *     (rule start) (ambiguity) '(' declaredTypeArgs+=Wildcard
	 *     (rule start) (ambiguity) '(' fpars+=TAnonymousFormalParameter
	 *     (rule start) (ambiguity) '+'? astValue=BINARY_INT
	 *     (rule start) (ambiguity) '+'? astValue=DOUBLE
	 *     (rule start) (ambiguity) '+'? astValue=HEX_INT
	 *     (rule start) (ambiguity) '+'? astValue=INT
	 *     (rule start) (ambiguity) '+'? astValue=LEGACY_OCTAL_INT
	 *     (rule start) (ambiguity) '+'? astValue=OCTAL_INT
	 *     (rule start) (ambiguity) '+'? astValue=SCIENTIFIC_INT
	 *     (rule start) (ambiguity) '<' ownedTypeVars+=TypeVariable
	 *     (rule start) (ambiguity) 'infer' typeVarName=IDENTIFIER
	 *     (rule start) (ambiguity) 'intersection' '{' typeRefs+=TypeRef
	 *     (rule start) (ambiguity) 'this' (rule start)
	 *     (rule start) (ambiguity) 'this' dynamic?='+'
	 *     (rule start) (ambiguity) 'type' '{' typeArg=TypeArgInTypeTypeRef
	 *     (rule start) (ambiguity) 'typeof' expr=PropertyAccessExpressionInTypeRef
	 *     (rule start) (ambiguity) 'union' '{' typeRefs+=TypeRef
	 *     (rule start) (ambiguity) '{' '+'? includeReadonly?='readonly'
	 *     (rule start) (ambiguity) '{' '-' excludeReadonly?='readonly'
	 *     (rule start) (ambiguity) '{' '[' propName=IdentifierName
	 *     (rule start) (ambiguity) '{' '}' (rule start)
	 *     (rule start) (ambiguity) '{' '}' dynamic?='+'
	 *     (rule start) (ambiguity) '{' astStructuralMembers+=TStructMember
	 *     (rule start) (ambiguity) arrayNTypeExpression?='['
	 *     (rule start) (ambiguity) astNamespaceLikeRefs+=NamespaceLikeRef
	 *     (rule start) (ambiguity) astNegated?='-'
	 *     (rule start) (ambiguity) astValue='false'
	 *     (rule start) (ambiguity) astValue='true'
	 *     (rule start) (ambiguity) astValue=STRING
	 *     (rule start) (ambiguity) constructorRef?='constructor'
	 *     (rule start) (ambiguity) declaredType=[Type|TypeReferenceName]
	 *     (rule start) (ambiguity) declaredTypeArgs+=WildcardOldNotationWithoutBound
	 *     (rule start) (ambiguity) definedTypingStrategy=TypingStrategyUseSiteOperator
	 *     (rule start) (ambiguity) dtsAbstract?='abstract'
	 *     (rule start) (ambiguity) dtsConstructor?='new'
	 *     (rule start) (ambiguity) op=TypeOperator
	 *     (rule start) (ambiguity) {IndexAccessTypeRef.targetTypeRef=}
	 *     (rule start) (ambiguity) {ParameterizedTypeRef.declaredTypeArgs+=}
	 */
	protected void emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('(' '|'? '&'?)+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '(' declaredTypeArgs+=Wildcard
	 *     (rule start) (ambiguity) declaredTypeArgs+=WildcardOldNotationWithoutBound
	 *     (rule start) (ambiguity) op=TypeOperator
	 *     (rule start) (ambiguity) {IndexAccessTypeRef.targetTypeRef=}
	 *     (rule start) (ambiguity) {ParameterizedTypeRef.declaredTypeArgs+=}
	 */
	protected void emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___LeftParenthesisKeyword_6_0_VerticalLineKeyword_0_q_AmpersandKeyword_0_q__p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('|'? '&'? '(')*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '&'? '(' (ambiguity) {ConditionalTypeRef.typeRef=}
	 *     (rule start) '&'? '(' (ambiguity) {UnionTypeExpression.typeRefs+=}
	 *     (rule start) '(' (ambiguity) {ConditionalTypeRef.typeRef=}
	 *     (rule start) '(' (ambiguity) {UnionTypeExpression.typeRefs+=}
	 *     (rule start) (ambiguity) {ConditionalTypeRef.typeRef=}
	 *     (rule start) (ambiguity) {UnionTypeExpression.typeRefs+=}
	 */
	protected void emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('|'? '&'? '(')+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) {ConditionalTypeRef.typeRef=}
	 *     (rule start) (ambiguity) {UnionTypeExpression.typeRefs+=}
	 */
	protected void emit_IntersectionTypeExpression_PrimaryTypeExpression_UnionTypeExpression___VerticalLineKeyword_0_q_AmpersandKeyword_0_q_LeftParenthesisKeyword_6_0__p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '+'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '&'? ('(' '|'? '&'?)* '{' (ambiguity) includeReadonly?='readonly'
	 *     (rule start) '{' (ambiguity) includeReadonly?='readonly'
	 *     (rule start) '|'? '&'? ('(' '|'? '&'?)* '{' (ambiguity) includeReadonly?='readonly'
	 *     (rule start) ('(' '|'? '&'?)* '{' (ambiguity) includeReadonly?='readonly'
	 */
	protected void emit_MappedTypeRef_PlusSignKeyword_1_0_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '+'?
	 *
	 * This ambiguous syntax occurs at:
	 *     propNameTypeRef=TypeRef ']' (ambiguity) includeOptional?='?'
	 */
	protected void emit_MappedTypeRef_PlusSignKeyword_7_0_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     excludeOptional?='?' (ambiguity) '}' (rule end)
	 *     includeOptional?='?' (ambiguity) '}' (rule end)
	 *     propNameTypeRef=TypeRef ']' (ambiguity) '}' (rule end)
	 *     templateTypeRef=TypeRef (ambiguity) '}' (rule end)
	 */
	protected void emit_MappedTypeRef_SemicolonKeyword_9_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '+'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '&'? ('(' '|'? '&'?)* (ambiguity) astValue=BINARY_INT
	 *     (rule start) '&'? ('(' '|'? '&'?)* (ambiguity) astValue=DOUBLE
	 *     (rule start) '&'? ('(' '|'? '&'?)* (ambiguity) astValue=HEX_INT
	 *     (rule start) '&'? ('(' '|'? '&'?)* (ambiguity) astValue=INT
	 *     (rule start) '&'? ('(' '|'? '&'?)* (ambiguity) astValue=LEGACY_OCTAL_INT
	 *     (rule start) '&'? ('(' '|'? '&'?)* (ambiguity) astValue=OCTAL_INT
	 *     (rule start) '&'? ('(' '|'? '&'?)* (ambiguity) astValue=SCIENTIFIC_INT
	 *     (rule start) '|'? '&'? ('(' '|'? '&'?)* (ambiguity) astValue=BINARY_INT
	 *     (rule start) '|'? '&'? ('(' '|'? '&'?)* (ambiguity) astValue=DOUBLE
	 *     (rule start) '|'? '&'? ('(' '|'? '&'?)* (ambiguity) astValue=HEX_INT
	 *     (rule start) '|'? '&'? ('(' '|'? '&'?)* (ambiguity) astValue=INT
	 *     (rule start) '|'? '&'? ('(' '|'? '&'?)* (ambiguity) astValue=LEGACY_OCTAL_INT
	 *     (rule start) '|'? '&'? ('(' '|'? '&'?)* (ambiguity) astValue=OCTAL_INT
	 *     (rule start) '|'? '&'? ('(' '|'? '&'?)* (ambiguity) astValue=SCIENTIFIC_INT
	 *     (rule start) ('(' '|'? '&'?)* (ambiguity) astValue=BINARY_INT
	 *     (rule start) ('(' '|'? '&'?)* (ambiguity) astValue=DOUBLE
	 *     (rule start) ('(' '|'? '&'?)* (ambiguity) astValue=HEX_INT
	 *     (rule start) ('(' '|'? '&'?)* (ambiguity) astValue=INT
	 *     (rule start) ('(' '|'? '&'?)* (ambiguity) astValue=LEGACY_OCTAL_INT
	 *     (rule start) ('(' '|'? '&'?)* (ambiguity) astValue=OCTAL_INT
	 *     (rule start) ('(' '|'? '&'?)* (ambiguity) astValue=SCIENTIFIC_INT
	 *     (rule start) (ambiguity) astValue=BINARY_INT
	 *     (rule start) (ambiguity) astValue=DOUBLE
	 *     (rule start) (ambiguity) astValue=HEX_INT
	 *     (rule start) (ambiguity) astValue=INT
	 *     (rule start) (ambiguity) astValue=LEGACY_OCTAL_INT
	 *     (rule start) (ambiguity) astValue=OCTAL_INT
	 *     (rule start) (ambiguity) astValue=SCIENTIFIC_INT
	 */
	protected void emit_NumericLiteralTypeRef_PlusSignKeyword_0_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     (';' | ',')?
	 *
	 * This ambiguous syntax occurs at:
	 *     astStructuralMembers+=TStructMember (ambiguity) '}' (rule end)
	 *     astStructuralMembers+=TStructMember (ambiguity) '}' dynamic?='+'
	 *     astStructuralMembers+=TStructMember (ambiguity) astStructuralMembers+=TStructMember
	 */
	protected void emit_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     declaredThisType=TypeRef (ambiguity) ')' Arrow returnTypePredicate=TypePredicateWithOperatorTypeRef
	 *     declaredThisType=TypeRef (ambiguity) ')' Arrow returnTypeRef=OperatorTypeRef
	 *     fpars+=TAnonymousFormalParameter (ambiguity) ')' Arrow returnTypePredicate=TypePredicateWithOperatorTypeRef
	 *     fpars+=TAnonymousFormalParameter (ambiguity) ')' Arrow returnTypeRef=OperatorTypeRef
	 */
	protected void emit_TAnonymousFormalParameterListWithDeclaredThisType_CommaKeyword_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     fpars+=TAnonymousFormalParameter (ambiguity) ')' ':' returnTypePredicate=TypePredicate
	 *     fpars+=TAnonymousFormalParameter (ambiguity) ')' ':' returnTypeRef=TypeRef
	 *     fpars+=TAnonymousFormalParameter (ambiguity) ')' (rule end)
	 */
	protected void emit_TAnonymousFormalParameterList_CommaKeyword_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '?'?
	 *
	 * This ambiguous syntax occurs at:
	 *     name=BindingIdentifier (ambiguity) ':' typeRef=TypeRef
	 */
	protected void emit_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '?'?
	 *
	 * This ambiguous syntax occurs at:
	 *     name=BindingIdentifier (ambiguity) ':' typeRef=TypeRef
	 */
	protected void emit_TFormalParameter_QuestionMarkKeyword_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'readonly'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '[' dtsComputedNameExpression=ExpressionInTypeRef
	 *     (rule start) (ambiguity) name=IdentifierName
	 *     (rule start) (ambiguity) name=NumericLiteralAsString
	 *     (rule start) (ambiguity) name=STRING
	 */
	protected void emit_TStructField_ReadonlyKeyword_0_1_0_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     (';' | ',')?
	 *
	 * This ambiguous syntax occurs at:
	 *     astStructuralMembers+=TStructMember (ambiguity) '}' (rule end)
	 *     astStructuralMembers+=TStructMember (ambiguity) '}' dynamic?='+'
	 *     astStructuralMembers+=TStructMember (ambiguity) astStructuralMembers+=TStructMember
	 */
	protected void emit_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     typeVars+=TypeVariable (ambiguity) '>' '(' ')' ':' returnTypePredicate=TypePredicate
	 *     typeVars+=TypeVariable (ambiguity) '>' '(' ')' ':' returnTypeRef=TypeRef
	 *     typeVars+=TypeVariable (ambiguity) '>' '(' ')' (rule end)
	 *     typeVars+=TypeVariable (ambiguity) '>' '(' fpars+=TAnonymousFormalParameter
	 *     typeVars+=TypeVariable (ambiguity) '>' '[' dtsComputedNameExpression=ExpressionInTypeRef
	 *     typeVars+=TypeVariable (ambiguity) '>' name=IdentifierName
	 *     typeVars+=TypeVariable (ambiguity) '>' name=NumericLiteralAsString
	 *     typeVars+=TypeVariable (ambiguity) '>' name=STRING
	 */
	protected void emit_TStructMethod_CommaKeyword_0_0_1_3_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '|'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '(' (ambiguity) ('&'? '(' (ambiguity))* {IntersectionTypeExpression.typeRefs+=}
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '(' ')' Arrow returnTypePredicate=TypePredicateWithOperatorTypeRef
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '(' ')' Arrow returnTypeRef=OperatorTypeRef
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '(' 'this' ':' declaredThisType=TypeRef
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '(' declaredTypeArgs+=Wildcard
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '(' fpars+=TAnonymousFormalParameter
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '+'? astValue=BINARY_INT
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '+'? astValue=DOUBLE
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '+'? astValue=HEX_INT
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '+'? astValue=INT
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '+'? astValue=LEGACY_OCTAL_INT
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '+'? astValue=OCTAL_INT
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '+'? astValue=SCIENTIFIC_INT
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '<' ownedTypeVars+=TypeVariable
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* 'infer' typeVarName=IDENTIFIER
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* 'intersection' '{' typeRefs+=TypeRef
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* 'this' (rule start)
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* 'this' dynamic?='+'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* 'type' '{' typeArg=TypeArgInTypeTypeRef
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* 'typeof' expr=PropertyAccessExpressionInTypeRef
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* 'union' '{' typeRefs+=TypeRef
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '{' '+'? includeReadonly?='readonly'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '{' '-' excludeReadonly?='readonly'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '{' '[' propName=IdentifierName
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '{' '}' (rule start)
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '{' '}' dynamic?='+'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* '{' astStructuralMembers+=TStructMember
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* arrayNTypeExpression?='['
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* astNamespaceLikeRefs+=NamespaceLikeRef
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* astNegated?='-'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* astValue='false'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* astValue='true'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* astValue=STRING
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* constructorRef?='constructor'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* declaredType=[Type|TypeReferenceName]
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* declaredTypeArgs+=WildcardOldNotationWithoutBound
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* definedTypingStrategy=TypingStrategyUseSiteOperator
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* dtsAbstract?='abstract'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* dtsConstructor?='new'
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* op=TypeOperator
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* {IndexAccessTypeRef.targetTypeRef=}
	 *     (rule start) (ambiguity) '&'? ('(' (ambiguity) '&'?)* {ParameterizedTypeRef.declaredTypeArgs+=}
	 *     (rule start) (ambiguity) ('&'? '(' (ambiguity))* {IntersectionTypeExpression.typeRefs+=}
	 */
	protected void emit_UnionTypeExpression_VerticalLineKeyword_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
