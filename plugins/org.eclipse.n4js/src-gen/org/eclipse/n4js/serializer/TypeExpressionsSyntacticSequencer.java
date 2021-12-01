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
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class TypeExpressionsSyntacticSequencer extends AbstractSyntacticSequencer {

	protected TypeExpressionsGrammarAccess grammarAccess;
	protected AbstractElementAlias match_MappedTypeRef_PlusSignKeyword_1_0_0_q;
	protected AbstractElementAlias match_MappedTypeRef_PlusSignKeyword_7_0_0_q;
	protected AbstractElementAlias match_MappedTypeRef_SemicolonKeyword_9_q;
	protected AbstractElementAlias match_NumericLiteralTypeRef_PlusSignKeyword_0_0_q;
	protected AbstractElementAlias match_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q;
	protected AbstractElementAlias match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_a;
	protected AbstractElementAlias match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_p;
	protected AbstractElementAlias match_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q;
	protected AbstractElementAlias match_TFormalParameter_QuestionMarkKeyword_2_q;
	protected AbstractElementAlias match_TStructField_ReadonlyKeyword_0_q;
	protected AbstractElementAlias match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (TypeExpressionsGrammarAccess) access;
		match_MappedTypeRef_PlusSignKeyword_1_0_0_q = new TokenAlias(false, true, grammarAccess.getMappedTypeRefAccess().getPlusSignKeyword_1_0_0());
		match_MappedTypeRef_PlusSignKeyword_7_0_0_q = new TokenAlias(false, true, grammarAccess.getMappedTypeRefAccess().getPlusSignKeyword_7_0_0());
		match_MappedTypeRef_SemicolonKeyword_9_q = new TokenAlias(false, true, grammarAccess.getMappedTypeRefAccess().getSemicolonKeyword_9());
		match_NumericLiteralTypeRef_PlusSignKeyword_0_0_q = new TokenAlias(false, true, grammarAccess.getNumericLiteralTypeRefAccess().getPlusSignKeyword_0_0());
		match_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q = new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getParameterizedTypeRefStructuralAccess().getCommaKeyword_0_2_1_1()), new TokenAlias(false, false, grammarAccess.getParameterizedTypeRefStructuralAccess().getSemicolonKeyword_0_2_1_0()));
		match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_a = new TokenAlias(true, true, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0());
		match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_p = new TokenAlias(true, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0());
		match_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q = new TokenAlias(false, true, grammarAccess.getTAnonymousFormalParameterAccess().getQuestionMarkKeyword_1_0_0_1());
		match_TFormalParameter_QuestionMarkKeyword_2_q = new TokenAlias(false, true, grammarAccess.getTFormalParameterAccess().getQuestionMarkKeyword_2());
		match_TStructField_ReadonlyKeyword_0_q = new TokenAlias(false, true, grammarAccess.getTStructFieldAccess().getReadonlyKeyword_0());
		match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q = new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1()), new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0()));
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
			if (match_MappedTypeRef_PlusSignKeyword_1_0_0_q.equals(syntax))
				emit_MappedTypeRef_PlusSignKeyword_1_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_MappedTypeRef_PlusSignKeyword_7_0_0_q.equals(syntax))
				emit_MappedTypeRef_PlusSignKeyword_7_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_MappedTypeRef_SemicolonKeyword_9_q.equals(syntax))
				emit_MappedTypeRef_SemicolonKeyword_9_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_NumericLiteralTypeRef_PlusSignKeyword_0_0_q.equals(syntax))
				emit_NumericLiteralTypeRef_PlusSignKeyword_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q.equals(syntax))
				emit_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_a.equals(syntax))
				emit_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_p.equals(syntax))
				emit_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q.equals(syntax))
				emit_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TFormalParameter_QuestionMarkKeyword_2_q.equals(syntax))
				emit_TFormalParameter_QuestionMarkKeyword_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TStructField_ReadonlyKeyword_0_q.equals(syntax))
				emit_TStructField_ReadonlyKeyword_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q.equals(syntax))
				emit_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     '+'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '('* '{' (ambiguity) includeReadonly?='readonly'
	 *     (rule start) '{' (ambiguity) includeReadonly?='readonly'
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
	 *     (rule start) '('* (ambiguity) astValue=BINARY_INT
	 *     (rule start) '('* (ambiguity) astValue=DOUBLE
	 *     (rule start) '('* (ambiguity) astValue=HEX_INT
	 *     (rule start) '('* (ambiguity) astValue=INT
	 *     (rule start) '('* (ambiguity) astValue=LEGACY_OCTAL_INT
	 *     (rule start) '('* (ambiguity) astValue=OCTAL_INT
	 *     (rule start) '('* (ambiguity) astValue=SCIENTIFIC_INT
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
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '(' ')' Arrow returnTypePredicate=TypePredicateWithPrimary
	 *     (rule start) (ambiguity) '(' ')' Arrow returnTypeRef=PrimaryTypeExpression
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
	 *     (rule start) (ambiguity) 'typeof' element=[IdentifiableElement|IdentifierName]
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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 5641a2d2d (early support for constructor type expressions)
=======
>>>>>>> f7a1b4792 (constructor type expressions can be 'abstract')
<<<<<<< HEAD
	 *     (rule start) (ambiguity) namespaceLikeRefs+=NamespaceLikeRef
=======
=======
=======
	 *     (rule start) (ambiguity) dtsAbstract?='abstract'
>>>>>>> 448326d89 (constructor type expressions can be 'abstract')
	 *     (rule start) (ambiguity) dtsConstructor?='new'
>>>>>>> 9195fcc1c (early support for constructor type expressions)
	 *     (rule start) (ambiguity) op=TypeOperator
	 *     (rule start) (ambiguity) {ConditionalTypeRef.typeRef=}
	 *     (rule start) (ambiguity) {IndexAccessTypeRef.targetTypeRef=}
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
>>>>>>> 400b52473 (early support for DTS type references, except mapped types)
	 *     (rule start) (ambiguity) {IntersectionTypeExpression.typeRefs+=}
	 *     (rule start) (ambiguity) {ParameterizedTypeRef.declaredTypeArgs+=}
	 *     (rule start) (ambiguity) {UnionTypeExpression.typeRefs+=}
	 */
	protected void emit_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '(' declaredTypeArgs+=Wildcard
	 *     (rule start) (ambiguity) declaredTypeArgs+=WildcardOldNotationWithoutBound
	 *     (rule start) (ambiguity) op=TypeOperator
	 *     (rule start) (ambiguity) {ConditionalTypeRef.typeRef=}
	 *     (rule start) (ambiguity) {IndexAccessTypeRef.targetTypeRef=}
	 *     (rule start) (ambiguity) {IntersectionTypeExpression.typeRefs+=}
	 *     (rule start) (ambiguity) {ParameterizedTypeRef.declaredTypeArgs+=}
	 *     (rule start) (ambiguity) {UnionTypeExpression.typeRefs+=}
	 */
	protected void emit_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
	protected void emit_TStructField_ReadonlyKeyword_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
	
}
