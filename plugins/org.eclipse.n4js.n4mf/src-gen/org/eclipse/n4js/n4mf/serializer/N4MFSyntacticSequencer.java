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
package org.eclipse.n4js.n4mf.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class N4MFSyntacticSequencer extends AbstractSyntacticSequencer {

	protected N4MFGrammarAccess grammarAccess;
	protected AbstractElementAlias match_ProjectDescription___RightCurlyBracketKeyword_17_3_SourcesKeyword_17_0_LeftCurlyBracketKeyword_17_1__q;
	protected AbstractElementAlias match_ProjectDescription___RightCurlyBracketKeyword_18_3_ModuleFiltersKeyword_18_0_LeftCurlyBracketKeyword_18_1__q;
	protected AbstractElementAlias match_VersionConstraint_RightParenthesisKeyword_0_2_1_q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (N4MFGrammarAccess) access;
		match_ProjectDescription___RightCurlyBracketKeyword_17_3_SourcesKeyword_17_0_LeftCurlyBracketKeyword_17_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_17_3()), new TokenAlias(false, false, grammarAccess.getProjectDescriptionAccess().getSourcesKeyword_17_0()), new TokenAlias(false, false, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_17_1()));
		match_ProjectDescription___RightCurlyBracketKeyword_18_3_ModuleFiltersKeyword_18_0_LeftCurlyBracketKeyword_18_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_18_3()), new TokenAlias(false, false, grammarAccess.getProjectDescriptionAccess().getModuleFiltersKeyword_18_0()), new TokenAlias(false, false, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_18_1()));
		match_VersionConstraint_RightParenthesisKeyword_0_2_1_q = new TokenAlias(false, true, grammarAccess.getVersionConstraintAccess().getRightParenthesisKeyword_0_2_1());
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
			if (match_ProjectDescription___RightCurlyBracketKeyword_17_3_SourcesKeyword_17_0_LeftCurlyBracketKeyword_17_1__q.equals(syntax))
				emit_ProjectDescription___RightCurlyBracketKeyword_17_3_SourcesKeyword_17_0_LeftCurlyBracketKeyword_17_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ProjectDescription___RightCurlyBracketKeyword_18_3_ModuleFiltersKeyword_18_0_LeftCurlyBracketKeyword_18_1__q.equals(syntax))
				emit_ProjectDescription___RightCurlyBracketKeyword_18_3_ModuleFiltersKeyword_18_0_LeftCurlyBracketKeyword_18_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_VersionConstraint_RightParenthesisKeyword_0_2_1_q.equals(syntax))
				emit_VersionConstraint_RightParenthesisKeyword_0_2_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     ('}' 'Sources' '{')?
	 *
	 * This ambiguous syntax occurs at:
	 *     sourceFragment+=SourceFragment (ambiguity) sourceFragment+=SourceFragment
	 */
	protected void emit_ProjectDescription___RightCurlyBracketKeyword_17_3_SourcesKeyword_17_0_LeftCurlyBracketKeyword_17_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ('}' 'ModuleFilters' '{')?
	 *
	 * This ambiguous syntax occurs at:
	 *     moduleFilters+=ModuleFilter (ambiguity) moduleFilters+=ModuleFilter
	 */
	protected void emit_ProjectDescription___RightCurlyBracketKeyword_18_3_ModuleFiltersKeyword_18_0_LeftCurlyBracketKeyword_18_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     ')'?
	 *
	 * This ambiguous syntax occurs at:
	 *     lowerVersion=DeclaredVersion (ambiguity) (rule end)
	 */
	protected void emit_VersionConstraint_RightParenthesisKeyword_0_2_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
