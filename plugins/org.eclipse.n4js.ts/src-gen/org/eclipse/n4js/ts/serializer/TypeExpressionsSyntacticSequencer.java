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
package org.eclipse.n4js.ts.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess;
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
	protected AbstractElementAlias match_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__;
	protected AbstractElementAlias match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_a;
	protected AbstractElementAlias match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p;
	protected AbstractElementAlias match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (TypeExpressionsGrammarAccess) access;
		match_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__ = new AlternativeAlias(false, false, new GroupAlias(false, false, new TokenAlias(false, false, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_1()), new TokenAlias(false, false, grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionKeyword_3()), new TokenAlias(false, false, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_5())), new TokenAlias(false, false, grammarAccess.getArrowFunctionTypeExpressionAccess().getLeftParenthesisKeyword_0_0_1()));
		match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_a = new TokenAlias(true, true, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_3_0());
		match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p = new TokenAlias(true, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_3_0());
		match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q = new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1()), new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__.equals(syntax))
				emit_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_a.equals(syntax))
				emit_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p.equals(syntax))
				emit_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q.equals(syntax))
				emit_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     ('{' 'function' '(') | '('
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '('* (ambiguity) fpars+=TAnonymousFormalParameter
	 */
	protected void emit_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '(' ')' '=>' returnTypeRef=PrimaryTypeExpression
	 *     (rule start) (ambiguity) 'intersection' '{' typeRefs+=TypeRefWithoutModifiers
	 *     (rule start) (ambiguity) 'this' (rule start)
	 *     (rule start) (ambiguity) 'this' dynamic?='+'
	 *     (rule start) (ambiguity) 'this' followedByQuestionMark?='?'
	 *     (rule start) (ambiguity) 'type' '{' typeArg=TypeArgInTypeTypeRef
	 *     (rule start) (ambiguity) 'union' '{' typeRefs+=TypeRefWithoutModifiers
	 *     (rule start) (ambiguity) '{' '@' 'This' '(' declaredThisType=TypeRefFunctionTypeExpression
	 *     (rule start) (ambiguity) '{' 'function' '(' ')' ':' returnTypeRef=TypeRef
	 *     (rule start) (ambiguity) '{' 'function' '(' ')' '}' (rule start)
	 *     (rule start) (ambiguity) '{' 'function' '(' ')' '}' followedByQuestionMark?='?'
	 *     (rule start) (ambiguity) '{' 'function' '<' ownedTypeVars+=TypeVariable
	 *     (rule start) (ambiguity) (('{' 'function' '(') | '(') fpars+=TAnonymousFormalParameter
	 *     (rule start) (ambiguity) constructorRef?='constructor'
	 *     (rule start) (ambiguity) declaredType=[Type|TypeReferenceName]
	 *     (rule start) (ambiguity) definedTypingStrategy=TypingStrategyUseSiteOperator
	 *     (rule start) (ambiguity) iterableTypeExpression?='['
	 *     (rule start) (ambiguity) {IntersectionTypeExpression.typeRefs+=}
	 *     (rule start) (ambiguity) {ParameterizedTypeRef.typeArgs+=}
	 *     (rule start) (ambiguity) {UnionTypeExpression.typeRefs+=}
	 */
	protected void emit_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) {IntersectionTypeExpression.typeRefs+=}
	 *     (rule start) (ambiguity) {ParameterizedTypeRef.typeArgs+=}
	 *     (rule start) (ambiguity) {UnionTypeExpression.typeRefs+=}
	 */
	protected void emit_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     (';' | ',')?
	 *
	 * This ambiguous syntax occurs at:
	 *     astStructuralMembers+=TStructMember (ambiguity) '}' (rule end)
	 *     astStructuralMembers+=TStructMember (ambiguity) '}' dynamic?='+'
	 *     astStructuralMembers+=TStructMember (ambiguity) '}' followedByQuestionMark?='?'
	 *     astStructuralMembers+=TStructMember (ambiguity) astStructuralMembers+=TStructMember
	 */
	protected void emit_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
