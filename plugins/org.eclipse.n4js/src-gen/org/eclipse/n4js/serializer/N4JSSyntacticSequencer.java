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
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class N4JSSyntacticSequencer extends AbstractSyntacticSequencer {

	protected N4JSGrammarAccess grammarAccess;
	protected AbstractElementAlias match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_0_2_q;
	protected AbstractElementAlias match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_1_6_q;
	protected AbstractElementAlias match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_2_1_q;
	protected AbstractElementAlias match_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q;
	protected AbstractElementAlias match_AnnotationNoAtSign___LeftParenthesisKeyword_1_0_RightParenthesisKeyword_1_2__q;
	protected AbstractElementAlias match_ArrayBindingPattern_CommaKeyword_3_2_0_q;
	protected AbstractElementAlias match_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__;
	protected AbstractElementAlias match_DoStatement_SemiParserRuleCall_6_q;
	protected AbstractElementAlias match_ExportClause_CommaKeyword_1_2_q;
	protected AbstractElementAlias match_FunctionDeclaration_SemiParserRuleCall_1_q;
	protected AbstractElementAlias match_ImportSpecifiersExceptDefault_CommaKeyword_1_1_2_q;
	protected AbstractElementAlias match_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1;
	protected AbstractElementAlias match_N4CallableConstructorDeclaration_SemicolonKeyword_1_q;
	protected AbstractElementAlias match_N4GetterDeclaration_SemicolonKeyword_2_q;
	protected AbstractElementAlias match_N4MethodDeclaration_SemicolonKeyword_1_q;
	protected AbstractElementAlias match_N4SetterDeclaration_SemicolonKeyword_6_q;
	protected AbstractElementAlias match_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q;
	protected AbstractElementAlias match_ObjectLiteral_CommaKeyword_2_2_q;
	protected AbstractElementAlias match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_a;
	protected AbstractElementAlias match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p;
	protected AbstractElementAlias match_PropertyMethodDeclaration_SemicolonKeyword_1_q;
	protected AbstractElementAlias match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (N4JSGrammarAccess) access;
		match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_0_2_q = new TokenAlias(false, true, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_0_2());
		match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_1_6_q = new TokenAlias(false, true, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_1_6());
		match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_2_1_q = new TokenAlias(false, true, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_2_1());
		match_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q = new TokenAlias(false, true, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSemicolonKeyword_1_3_1());
		match_AnnotationNoAtSign___LeftParenthesisKeyword_1_0_RightParenthesisKeyword_1_2__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getAnnotationNoAtSignAccess().getLeftParenthesisKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getAnnotationNoAtSignAccess().getRightParenthesisKeyword_1_2()));
		match_ArrayBindingPattern_CommaKeyword_3_2_0_q = new TokenAlias(false, true, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_2_0());
		match_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__ = new AlternativeAlias(false, false, new GroupAlias(false, false, new TokenAlias(false, false, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_1()), new TokenAlias(false, false, grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionKeyword_3()), new TokenAlias(false, false, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_5())), new TokenAlias(false, false, grammarAccess.getArrowFunctionTypeExpressionAccess().getLeftParenthesisKeyword_0_0_1()));
		match_DoStatement_SemiParserRuleCall_6_q = new TokenAlias(false, true, grammarAccess.getDoStatementAccess().getSemiParserRuleCall_6());
		match_ExportClause_CommaKeyword_1_2_q = new TokenAlias(false, true, grammarAccess.getExportClauseAccess().getCommaKeyword_1_2());
		match_FunctionDeclaration_SemiParserRuleCall_1_q = new TokenAlias(false, true, grammarAccess.getFunctionDeclarationAccess().getSemiParserRuleCall_1());
		match_ImportSpecifiersExceptDefault_CommaKeyword_1_1_2_q = new TokenAlias(false, true, grammarAccess.getImportSpecifiersExceptDefaultAccess().getCommaKeyword_1_1_2());
		match_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getInterfaceExtendsListAccess().getExtendsKeyword_0_0()), new TokenAlias(false, false, grammarAccess.getInterfaceExtendsListAccess().getImplementsKeyword_0_1()));
		match_N4CallableConstructorDeclaration_SemicolonKeyword_1_q = new TokenAlias(false, true, grammarAccess.getN4CallableConstructorDeclarationAccess().getSemicolonKeyword_1());
		match_N4GetterDeclaration_SemicolonKeyword_2_q = new TokenAlias(false, true, grammarAccess.getN4GetterDeclarationAccess().getSemicolonKeyword_2());
		match_N4MethodDeclaration_SemicolonKeyword_1_q = new TokenAlias(false, true, grammarAccess.getN4MethodDeclarationAccess().getSemicolonKeyword_1());
		match_N4SetterDeclaration_SemicolonKeyword_6_q = new TokenAlias(false, true, grammarAccess.getN4SetterDeclarationAccess().getSemicolonKeyword_6());
		match_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q = new TokenAlias(false, true, grammarAccess.getNoLineTerminatorAccess().getNO_LINE_TERMINATORTerminalRuleCall());
		match_ObjectLiteral_CommaKeyword_2_2_q = new TokenAlias(false, true, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_2());
		match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_a = new TokenAlias(true, true, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_3_0());
		match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p = new TokenAlias(true, false, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_3_0());
		match_PropertyMethodDeclaration_SemicolonKeyword_1_q = new TokenAlias(false, true, grammarAccess.getPropertyMethodDeclarationAccess().getSemicolonKeyword_1());
		match_TStructMemberList___CommaKeyword_1_1_1_or_SemicolonKeyword_1_1_0__q = new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1()), new TokenAlias(false, false, grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (ruleCall.getRule() == grammarAccess.getNO_LINE_TERMINATORRule())
			return getNO_LINE_TERMINATORToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getSemiRule())
			return getSemiToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getTemplateExpressionEndRule())
			return getTemplateExpressionEndToken(semanticObject, ruleCall, node);
		return "";
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
			else if (match_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_2_1_q.equals(syntax))
				emit_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_2_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q.equals(syntax))
				emit_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AnnotationNoAtSign___LeftParenthesisKeyword_1_0_RightParenthesisKeyword_1_2__q.equals(syntax))
				emit_AnnotationNoAtSign___LeftParenthesisKeyword_1_0_RightParenthesisKeyword_1_2__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ArrayBindingPattern_CommaKeyword_3_2_0_q.equals(syntax))
				emit_ArrayBindingPattern_CommaKeyword_3_2_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__.equals(syntax))
				emit_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_DoStatement_SemiParserRuleCall_6_q.equals(syntax))
				emit_DoStatement_SemiParserRuleCall_6_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ExportClause_CommaKeyword_1_2_q.equals(syntax))
				emit_ExportClause_CommaKeyword_1_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_FunctionDeclaration_SemiParserRuleCall_1_q.equals(syntax))
				emit_FunctionDeclaration_SemiParserRuleCall_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ImportSpecifiersExceptDefault_CommaKeyword_1_1_2_q.equals(syntax))
				emit_ImportSpecifiersExceptDefault_CommaKeyword_1_1_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1.equals(syntax))
				emit_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_N4CallableConstructorDeclaration_SemicolonKeyword_1_q.equals(syntax))
				emit_N4CallableConstructorDeclaration_SemicolonKeyword_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_N4GetterDeclaration_SemicolonKeyword_2_q.equals(syntax))
				emit_N4GetterDeclaration_SemicolonKeyword_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_N4MethodDeclaration_SemicolonKeyword_1_q.equals(syntax))
				emit_N4MethodDeclaration_SemicolonKeyword_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_N4SetterDeclaration_SemicolonKeyword_6_q.equals(syntax))
				emit_N4SetterDeclaration_SemicolonKeyword_6_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q.equals(syntax))
				emit_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ObjectLiteral_CommaKeyword_2_2_q.equals(syntax))
				emit_ObjectLiteral_CommaKeyword_2_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_a.equals(syntax))
				emit_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p.equals(syntax))
				emit_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_PropertyMethodDeclaration_SemicolonKeyword_1_q.equals(syntax))
				emit_PropertyMethodDeclaration_SemicolonKeyword_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
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
	 *     declaredTypeRef=TypeRef (ambiguity) (rule end)
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
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 *     returnTypeRef=TypeRef (ambiguity) (rule end)
	 */
	protected void emit_AnnotatedN4MemberDeclaration_SemicolonKeyword_1_2_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 */
	protected void emit_AnnotatedPropertyAssignment_SemicolonKeyword_1_3_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('(' ')')?
	 *
	 * This ambiguous syntax occurs at:
	 *     name=AnnotationName (ambiguity) (rule end)
	 */
	protected void emit_AnnotationNoAtSign___LeftParenthesisKeyword_1_0_RightParenthesisKeyword_1_2__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
	 *     ('{' 'function' '(') | '('
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '('* (ambiguity) fpars+=TAnonymousFormalParameter
	 *     (rule start) (ambiguity) fpars+=TAnonymousFormalParameter
	 */
	protected void emit_ArrowFunctionTypeExpression_FunctionTypeExpressionOLD_LeftParenthesisKeyword_0_0_1_or___LeftCurlyBracketKeyword_1_FunctionKeyword_3_LeftParenthesisKeyword_5__(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
	 *     Semi?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) 'function' '(' ')' (ambiguity) (rule start)
	 *     body=Block (ambiguity) (rule end)
	 *     declaredAsync?='async' NO_LINE_TERMINATOR? 'function' '(' ')' (ambiguity) (rule end)
	 *     declaredModifiers+=N4Modifier 'function' '(' ')' (ambiguity) (rule end)
	 *     declaredVersion=VERSION '(' ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 *     generator?='*' '(' ')' (ambiguity) (rule end)
	 *     name=BindingIdentifier '(' ')' (ambiguity) (rule end)
	 *     returnTypeRef=TypeRef (ambiguity) (rule end)
	 *     typeVars+=TypeVariable '>' '(' ')' (ambiguity) (rule end)
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
	 *     (rule start) 'interface' (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominal
	 *     declaredModifiers+=N4Modifier 'interface' (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominal
	 *     declaredVersion=VERSION (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominal
	 *     name=BindingIdentifier (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominal
	 *     typeVars+=TypeVariable '>' (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominal
	 *     typingStrategy=TypingStrategyDefSiteOperator (ambiguity) superInterfaceRefs+=ParameterizedTypeRefNominal
	 */
	protected void emit_InterfaceExtendsList_ExtendsKeyword_0_0_or_ImplementsKeyword_0_1(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 *     returnTypeRef=TypeRef (ambiguity) (rule end)
	 */
	protected void emit_N4CallableConstructorDeclaration_SemicolonKeyword_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
	 *     declaredTypeRef=TypeRef (ambiguity) (rule end)
	 */
	protected void emit_N4GetterDeclaration_SemicolonKeyword_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 *     returnTypeRef=TypeRef (ambiguity) (rule end)
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
	 *     declaredAsync?='async' (ambiguity) '(' ')' ':' returnTypeRef=TypeRef
	 *     declaredAsync?='async' (ambiguity) '(' ')' '=>' body=ExpressionDisguisedAsBlock
	 *     declaredAsync?='async' (ambiguity) '(' ')' '=>' hasBracesAroundBody?='{'
	 *     declaredAsync?='async' (ambiguity) '(' fpars+=FormalParameter
	 *     declaredAsync?='async' (ambiguity) 'function' '(' ')' ':' returnTypeRef=TypeRef
	 *     declaredAsync?='async' (ambiguity) 'function' '(' ')' (rule end)
	 *     declaredAsync?='async' (ambiguity) 'function' '(' ')' Semi? (rule end)
	 *     declaredAsync?='async' (ambiguity) 'function' '(' ')' body=Block
	 *     declaredAsync?='async' (ambiguity) 'function' '(' fpars+=FormalParameter
	 *     declaredAsync?='async' (ambiguity) 'function' '<' typeVars+=TypeVariable
	 *     declaredAsync?='async' (ambiguity) 'function' declaredVersion=VERSION
	 *     declaredAsync?='async' (ambiguity) 'function' generator?='*'
	 *     declaredAsync?='async' (ambiguity) 'function' name=BindingIdentifier
	 *     declaredAsync?='async' (ambiguity) declaredName=LiteralOrComputedPropertyName
	 */
	protected void emit_NoLineTerminator_NO_LINE_TERMINATORTerminalRuleCall_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
	 *     (rule start) (ambiguity) arrayTypeLiteral?='['
	 *     (rule start) (ambiguity) astNamespace=[ModuleNamespaceVirtualType|TypeReferenceName]
	 *     (rule start) (ambiguity) constructorRef?='constructor'
	 *     (rule start) (ambiguity) declaredType=[Type|TypeReferenceName]
	 *     (rule start) (ambiguity) definedTypingStrategy=TypingStrategyUseSiteOperator
	 *     (rule start) (ambiguity) {IntersectionTypeExpression.typeRefs+=}
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
	 *     (rule start) (ambiguity) {UnionTypeExpression.typeRefs+=}
	 */
	protected void emit_PrimaryTypeExpression_LeftParenthesisKeyword_3_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ';'?
	 *
	 * This ambiguous syntax occurs at:
	 *     body=Block (ambiguity) (rule end)
	 *     declaredName=LiteralOrComputedPropertyName '(' ')' (ambiguity) (rule end)
	 *     fpars+=FormalParameter ')' (ambiguity) (rule end)
	 */
	protected void emit_PropertyMethodDeclaration_SemicolonKeyword_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
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
