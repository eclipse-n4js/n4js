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
import org.eclipse.n4js.services.N4JSGrammarAccess;
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
public class N4JSSyntacticSequencer extends AbstractSyntacticSequencer {

	protected N4JSGrammarAccess grammarAccess;
	protected AbstractElementAlias match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_0_2_q;
	protected AbstractElementAlias match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_1_6_q;
	protected AbstractElementAlias match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_3_1_q;
	protected AbstractElementAlias match_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q;
	protected AbstractElementAlias match_ArrayBindingPattern_CommaKeyword_3_2_0_q;
	protected AbstractElementAlias match_DoStatement_SemiParserRuleCall_6_q;
	protected AbstractElementAlias match_ExportClause_CommaKeyword_1_2_q;
	protected AbstractElementAlias match_FieldDeclarationImpl_ReadonlyKeyword_0_q;
	protected AbstractElementAlias match_FunctionDeclaration_SemiParserRuleCall_1_q;
	protected AbstractElementAlias match_ImportSpecifiersExceptDefault_CommaKeyword_1_1_2_q;
	protected AbstractElementAlias match_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1;
	protected AbstractElementAlias match_MappedTypeRef_PlusSignKeyword_1_0_0_q;
	protected AbstractElementAlias match_MappedTypeRef_PlusSignKeyword_7_0_0_q;
	protected AbstractElementAlias match_MappedTypeRef_SemicolonKeyword_9_q;
	protected AbstractElementAlias match_N4CallSignatureDeclaration_SemicolonKeyword_2_q;
	protected AbstractElementAlias match_N4GetterDeclaration_SemicolonKeyword_2_q;
	protected AbstractElementAlias match_N4IndexSignatureDeclaration_SemicolonKeyword_5_q;
	protected AbstractElementAlias match_N4MethodDeclaration_SemicolonKeyword_1_q;
	protected AbstractElementAlias match_N4SetterDeclaration_SemicolonKeyword_6_q;
	protected AbstractElementAlias match_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q;
	protected AbstractElementAlias match_NoWhiteSpace_NO_WHITE_SPACETerminalRuleCall_q;
	protected AbstractElementAlias match_NumericLiteralTypeRef_PlusSignKeyword_0_0_q;
	protected AbstractElementAlias match_ObjectLiteral_CommaKeyword_2_2_q;
	protected AbstractElementAlias match_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q;
	protected AbstractElementAlias match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_a;
	protected AbstractElementAlias match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_p;
	protected AbstractElementAlias match_PropertyMethodDeclaration_SemicolonKeyword_1_q;
	protected AbstractElementAlias match_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q;
	protected AbstractElementAlias match_TFormalParameter_QuestionMarkKeyword_2_q;
	protected AbstractElementAlias match_TStructField_ReadonlyKeyword_0_q;
	protected AbstractElementAlias match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (N4JSGrammarAccess) access;
		match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_0_2_q = new TokenAlias(false, true, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_0_2());
		match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_1_6_q = new TokenAlias(false, true, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_1_6());
		match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_3_1_q = new TokenAlias(false, true, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_3_1());
		match_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q = new TokenAlias(false, true, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSemicolonKeyword_1_3_1());
		match_ArrayBindingPattern_CommaKeyword_3_2_0_q = new TokenAlias(false, true, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_2_0());
		match_DoStatement_SemiParserRuleCall_6_q = new TokenAlias(false, true, grammarAccess.getDoStatementAccess().getSemiParserRuleCall_6());
		match_ExportClause_CommaKeyword_1_2_q = new TokenAlias(false, true, grammarAccess.getExportClauseAccess().getCommaKeyword_1_2());
		match_FieldDeclarationImpl_ReadonlyKeyword_0_q = new TokenAlias(false, true, grammarAccess.getFieldDeclarationImplAccess().getReadonlyKeyword_0());
		match_FunctionDeclaration_SemiParserRuleCall_1_q = new TokenAlias(false, true, grammarAccess.getFunctionDeclarationAccess().getSemiParserRuleCall_1());
		match_ImportSpecifiersExceptDefault_CommaKeyword_1_1_2_q = new TokenAlias(false, true, grammarAccess.getImportSpecifiersExceptDefaultAccess().getCommaKeyword_1_1_2());
		match_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getInterfaceExtendsListAccess().getExtendsKeyword_0_0()), new TokenAlias(false, false, grammarAccess.getInterfaceExtendsListAccess().getImplementsKeyword_0_1()));
		match_MappedTypeRef_PlusSignKeyword_1_0_0_q = new TokenAlias(false, true, grammarAccess.getMappedTypeRefAccess().getPlusSignKeyword_1_0_0());
		match_MappedTypeRef_PlusSignKeyword_7_0_0_q = new TokenAlias(false, true, grammarAccess.getMappedTypeRefAccess().getPlusSignKeyword_7_0_0());
		match_MappedTypeRef_SemicolonKeyword_9_q = new TokenAlias(false, true, grammarAccess.getMappedTypeRefAccess().getSemicolonKeyword_9());
		match_N4CallSignatureDeclaration_SemicolonKeyword_2_q = new TokenAlias(false, true, grammarAccess.getN4CallSignatureDeclarationAccess().getSemicolonKeyword_2());
		match_N4GetterDeclaration_SemicolonKeyword_2_q = new TokenAlias(false, true, grammarAccess.getN4GetterDeclarationAccess().getSemicolonKeyword_2());
		match_N4IndexSignatureDeclaration_SemicolonKeyword_5_q = new TokenAlias(false, true, grammarAccess.getN4IndexSignatureDeclarationAccess().getSemicolonKeyword_5());
		match_N4MethodDeclaration_SemicolonKeyword_1_q = new TokenAlias(false, true, grammarAccess.getN4MethodDeclarationAccess().getSemicolonKeyword_1());
		match_N4SetterDeclaration_SemicolonKeyword_6_q = new TokenAlias(false, true, grammarAccess.getN4SetterDeclarationAccess().getSemicolonKeyword_6());
		match_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q = new TokenAlias(false, true, grammarAccess.getNoLineTerminatorAccess().getNO_LINE_TERMINATORTerminalRuleCall());
		match_NoWhiteSpace_NO_WHITE_SPACETerminalRuleCall_q = new TokenAlias(false, true, grammarAccess.getNoWhiteSpaceAccess().getNO_WHITE_SPACETerminalRuleCall());
		match_NumericLiteralTypeRef_PlusSignKeyword_0_0_q = new TokenAlias(false, true, grammarAccess.getNumericLiteralTypeRefAccess().getPlusSignKeyword_0_0());
		match_ObjectLiteral_CommaKeyword_2_2_q = new TokenAlias(false, true, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_2());
		match_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q = new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getParameterizedTypeRefStructuralAccess().getCommaKeyword_0_2_1_1()), new TokenAlias(false, false, grammarAccess.getParameterizedTypeRefStructuralAccess().getSemicolonKeyword_0_2_1_0()));
		match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_a = new TokenAlias(true, true, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0());
		match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_p = new TokenAlias(true, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_6_0());
		match_PropertyMethodDeclaration_SemicolonKeyword_1_q = new TokenAlias(false, true, grammarAccess.getPropertyMethodDeclarationAccess().getSemicolonKeyword_1());
		match_TAnonymousFormalParameter_QuestionMarkKeyword_1_0_0_1_q = new TokenAlias(false, true, grammarAccess.getTAnonymousFormalParameterAccess().getQuestionMarkKeyword_1_0_0_1());
		match_TFormalParameter_QuestionMarkKeyword_2_q = new TokenAlias(false, true, grammarAccess.getTFormalParameterAccess().getQuestionMarkKeyword_2());
		match_TStructField_ReadonlyKeyword_0_q = new TokenAlias(false, true, grammarAccess.getTStructFieldAccess().getReadonlyKeyword_0());
		match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q = new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1()), new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (ruleCall.getRule() == grammarAccess.getArrowRule())
			return getArrowToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getNO_LINE_TERMINATORRule())
			return getNO_LINE_TERMINATORToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getNO_WHITE_SPACERule())
			return getNO_WHITE_SPACEToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getSemiRule())
			return getSemiToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getTemplateExpressionEndRule())
			return getTemplateExpressionEndToken(semanticObject, ruleCall, node);
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
	
	/**
	 * terminal NO_LINE_TERMINATOR:
	 * 	'//5' ;
	 */
	protected String getNO_LINE_TERMINATORToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "//5";
	}
	
	/**
	 * terminal NO_WHITE_SPACE:
	 * 	'//6' ;
	 */
	protected String getNO_WHITE_SPACEToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "//6";
	}
	
	/**
	 * Semi: ';';
	 */
	protected String getSemiToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return ";";
	}
	
	/**
	 * TemplateExpressionEnd:
	 * 	'}'
	 * ;
	 */
	protected String getTemplateExpressionEndToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "}";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_0_2_q.equals(syntax))
				emit_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_0_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_1_6_q.equals(syntax))
				emit_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_1_6_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_3_1_q.equals(syntax))
				emit_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_3_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q.equals(syntax))
				emit_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ArrayBindingPattern_CommaKeyword_3_2_0_q.equals(syntax))
				emit_ArrayBindingPattern_CommaKeyword_3_2_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_DoStatement_SemiParserRuleCall_6_q.equals(syntax))
				emit_DoStatement_SemiParserRuleCall_6_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ExportClause_CommaKeyword_1_2_q.equals(syntax))
				emit_ExportClause_CommaKeyword_1_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_FieldDeclarationImpl_ReadonlyKeyword_0_q.equals(syntax))
				emit_FieldDeclarationImpl_ReadonlyKeyword_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_FunctionDeclaration_SemiParserRuleCall_1_q.equals(syntax))
				emit_FunctionDeclaration_SemiParserRuleCall_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ImportSpecifiersExceptDefault_CommaKeyword_1_1_2_q.equals(syntax))
				emit_ImportSpecifiersExceptDefault_CommaKeyword_1_1_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1.equals(syntax))
				emit_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_MappedTypeRef_PlusSignKeyword_1_0_0_q.equals(syntax))
				emit_MappedTypeRef_PlusSignKeyword_1_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_MappedTypeRef_PlusSignKeyword_7_0_0_q.equals(syntax))
				emit_MappedTypeRef_PlusSignKeyword_7_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_MappedTypeRef_SemicolonKeyword_9_q.equals(syntax))
				emit_MappedTypeRef_SemicolonKeyword_9_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_N4CallSignatureDeclaration_SemicolonKeyword_2_q.equals(syntax))
				emit_N4CallSignatureDeclaration_SemicolonKeyword_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_N4GetterDeclaration_SemicolonKeyword_2_q.equals(syntax))
				emit_N4GetterDeclaration_SemicolonKeyword_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_N4IndexSignatureDeclaration_SemicolonKeyword_5_q.equals(syntax))
				emit_N4IndexSignatureDeclaration_SemicolonKeyword_5_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_N4MethodDeclaration_SemicolonKeyword_1_q.equals(syntax))
				emit_N4MethodDeclaration_SemicolonKeyword_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_N4SetterDeclaration_SemicolonKeyword_6_q.equals(syntax))
				emit_N4SetterDeclaration_SemicolonKeyword_6_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q.equals(syntax))
				emit_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_NoWhiteSpace_NO_WHITE_SPACETerminalRuleCall_q.equals(syntax))
				emit_NoWhiteSpace_NO_WHITE_SPACETerminalRuleCall_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_NumericLiteralTypeRef_PlusSignKeyword_0_0_q.equals(syntax))
				emit_NumericLiteralTypeRef_PlusSignKeyword_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ObjectLiteral_CommaKeyword_2_2_q.equals(syntax))
				emit_ObjectLiteral_CommaKeyword_2_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q.equals(syntax))
				emit_ParameterizedTypeRefStructural___CommaKeyword_0_2_1_1_or_SemicolonKeyword_0_2_1_0__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_a.equals(syntax))
				emit_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_p.equals(syntax))
				emit_PrimaryTypeExpression_LeftParenthesisKeyword_6_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PropertyMethodDeclaration_SemicolonKeyword_1_q.equals(syntax))
				emit_PropertyMethodDeclaration_SemicolonKeyword_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
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
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     declaredOptional?='?' '(' ')' (ambiguity) (rule end)
	 *     declaredTypeRefNode=TypeReferenceNode (ambiguity) (rule end)
	 */
	protected void emit_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_0_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     fpar=FormalParameter ')' (ambiguity) (rule end)
	 */
	protected void emit_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_1_6_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     declaredReturnTypePredicate=TypePredicateDeclaration (ambiguity) (rule end)
	 *     declaredReturnTypeRefNode=TypeReferenceNode (ambiguity) (rule end)
	 *     dtsDeclaredOptional?='?' '(' ')' (ambiguity) (rule end)
	 *     dtsDeclaredThisTypeNode=TypeReferenceNode ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 *     typeVars+=N4TypeVariable '>' '(' ')' (ambiguity) (rule end)
	 *     {N4MethodDeclaration.annotationList=} '(' ')' (ambiguity) (rule end)
	 */
	protected void emit_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_3_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     dtsDeclaredThisTypeNode=TypeReferenceNode ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 */
	protected void emit_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     elements+=BindingRestElement (ambiguity) ']' (rule end)
	 */
	protected void emit_ArrayBindingPattern_CommaKeyword_3_2_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     Semi?
	 *
	 * This ambiguous syntax occurs at:
	 *     expression=Expression ')' (ambiguity) (rule end)
	 */
	protected void emit_DoStatement_SemiParserRuleCall_6_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     namedExports+=ExportSpecifier (ambiguity) '}' 'from' reexportedFrom=[TModule|ModuleSpecifier]
	 *     namedExports+=ExportSpecifier (ambiguity) '}' Semi (rule end)
	 */
	protected void emit_ExportClause_CommaKeyword_1_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'readonly'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) declaredModifiers+=N4Modifier
	 *     (rule start) (ambiguity) declaredName=LiteralOrComputedPropertyName
	 *     {N4FieldDeclaration.annotationList=} (ambiguity) declaredModifiers+=N4Modifier
	 *     {N4FieldDeclaration.annotationList=} (ambiguity) declaredName=LiteralOrComputedPropertyName
	 */
	protected void emit_FieldDeclarationImpl_ReadonlyKeyword_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     Semi?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) 'function' '(' ')' (ambiguity) (rule start)
	 *     body=Block (ambiguity) (rule end)
	 *     declaredAsync?='async' NO_LINE_TERMINATOR? 'function' '(' ')' (ambiguity) (rule end)
	 *     declaredModifiers+=N4Modifier 'function' '(' ')' (ambiguity) (rule end)
	 *     declaredReturnTypePredicate=TypePredicateDeclaration (ambiguity) (rule end)
	 *     declaredReturnTypeRefNode=TypeReferenceNode (ambiguity) (rule end)
	 *     dtsDeclaredThisTypeNode=TypeReferenceNode ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 *     generator?='*' '(' ')' (ambiguity) (rule end)
	 *     name=BindingIdentifier '(' ')' (ambiguity) (rule end)
	 *     typeVars+=N4TypeVariable '>' '(' ')' (ambiguity) (rule end)
	 */
	protected void emit_FunctionDeclaration_SemiParserRuleCall_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     importSpecifiers+=NamedImportSpecifier (ambiguity) '}' importFrom?='from'
	 */
	protected void emit_ImportSpecifiersExceptDefault_CommaKeyword_1_1_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'extends' | 'implements'
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) 'interface' (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominalNode
	 *     declaredModifiers+=N4Modifier 'interface' (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominalNode
	 *     name=BindingIdentifier (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominalNode
	 *     typeVars+=N4TypeVariable '>' (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominalNode
	 *     typingStrategy=TypingStrategyDefSiteOperator (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominalNode
	 */
	protected void emit_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
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
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '(' ')' (ambiguity) (rule start)
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     declaredReturnTypePredicate=TypePredicateDeclaration (ambiguity) (rule end)
	 *     declaredReturnTypeRefNode=TypeReferenceNode (ambiguity) (rule end)
	 *     dtsDeclaredOptional?='?' '(' ')' (ambiguity) (rule end)
	 *     dtsDeclaredThisTypeNode=TypeReferenceNode ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 *     typeVars+=N4TypeVariable '>' '(' ')' (ambiguity) (rule end)
	 *     {N4MethodDeclaration.annotationList=} '(' ')' (ambiguity) (rule end)
	 */
	protected void emit_N4CallSignatureDeclaration_SemicolonKeyword_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     declaredOptional?='?' '(' ')' (ambiguity) (rule end)
	 *     declaredTypeRefNode=TypeReferenceNode (ambiguity) (rule end)
	 */
	protected void emit_N4GetterDeclaration_SemicolonKeyword_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     declaredValueTypeRefNode=TypeReferenceNode (ambiguity) (rule end)
	 */
	protected void emit_N4IndexSignatureDeclaration_SemicolonKeyword_5_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     declaredReturnTypePredicate=TypePredicateDeclaration (ambiguity) (rule end)
	 *     declaredReturnTypeRefNode=TypeReferenceNode (ambiguity) (rule end)
	 *     dtsDeclaredOptional?='?' '(' ')' (ambiguity) (rule end)
	 *     dtsDeclaredThisTypeNode=TypeReferenceNode ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 *     typeVars+=N4TypeVariable '>' '(' ')' (ambiguity) (rule end)
	 */
	protected void emit_N4MethodDeclaration_SemicolonKeyword_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     fpar=FormalParameter ')' (ambiguity) (rule end)
	 */
	protected void emit_N4SetterDeclaration_SemicolonKeyword_6_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     NO_LINE_TERMINATOR?
	 *
	 * This ambiguous syntax occurs at:
	 *     declaredAsync?='async' (ambiguity) '(' ')' ':' declaredReturnTypePredicate=TypePredicateDeclaration
	 *     declaredAsync?='async' (ambiguity) '(' ')' ':' declaredReturnTypeRefNode=TypeReferenceNode
	 *     declaredAsync?='async' (ambiguity) '(' ')' Arrow body=ExpressionDisguisedAsBlock
	 *     declaredAsync?='async' (ambiguity) '(' ')' Arrow hasBracesAroundBody?='{'
	 *     declaredAsync?='async' (ambiguity) '(' 'this' ':' dtsDeclaredThisTypeNode=TypeReferenceNode
	 *     declaredAsync?='async' (ambiguity) '(' fpars+=FormalParameter
	 *     declaredAsync?='async' (ambiguity) 'function' '(' ')' ':' declaredReturnTypePredicate=TypePredicateDeclaration
	 *     declaredAsync?='async' (ambiguity) 'function' '(' ')' ':' declaredReturnTypeRefNode=TypeReferenceNode
	 *     declaredAsync?='async' (ambiguity) 'function' '(' ')' (rule end)
	 *     declaredAsync?='async' (ambiguity) 'function' '(' ')' Semi? (rule end)
	 *     declaredAsync?='async' (ambiguity) 'function' '(' ')' body=Block
	 *     declaredAsync?='async' (ambiguity) 'function' '(' 'this' ':' dtsDeclaredThisTypeNode=TypeReferenceNode
	 *     declaredAsync?='async' (ambiguity) 'function' '(' fpars+=FormalParameter
	 *     declaredAsync?='async' (ambiguity) 'function' '<' typeVars+=N4TypeVariable
	 *     declaredAsync?='async' (ambiguity) 'function' generator?='*'
	 *     declaredAsync?='async' (ambiguity) 'function' name=BindingIdentifier
	 *     declaredAsync?='async' (ambiguity) declaredName=LiteralOrComputedPropertyName
	 *     declaredAsync?='async' (ambiguity) generator?='*'
	 */
	protected void emit_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     NO_WHITE_SPACE?
	 *
	 * This ambiguous syntax occurs at:
	 *     name=AnnotationName (ambiguity) '(' args+=AnnotationArgument
	 */
	protected void emit_NoWhiteSpace_NO_WHITE_SPACETerminalRuleCall_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     propertyAssignments+=PropertyAssignment (ambiguity) '}' (rule end)
	 */
	protected void emit_ObjectLiteral_CommaKeyword_2_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     dtsDeclaredThisTypeNode=TypeReferenceNode ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 */
	protected void emit_PropertyMethodDeclaration_SemicolonKeyword_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
