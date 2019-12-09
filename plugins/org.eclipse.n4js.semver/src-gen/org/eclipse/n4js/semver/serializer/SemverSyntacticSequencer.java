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
package org.eclipse.n4js.semver.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.semver.services.SemverGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class SemverSyntacticSequencer extends AbstractSyntacticSequencer {

	protected SemverGrammarAccess grammarAccess;
	protected AbstractElementAlias match_NPMVersionRequirement_WSTerminalRuleCall_0_0_q;
	protected AbstractElementAlias match_NPMVersionRequirement_WSTerminalRuleCall_1_1_q;
	protected AbstractElementAlias match_SimpleVersion_WSTerminalRuleCall_0_1_q;
	protected AbstractElementAlias match_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_0_q;
	protected AbstractElementAlias match_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_2_q;
	protected AbstractElementAlias match_VersionRangeSetRequirement_WSTerminalRuleCall_1_2_q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (SemverGrammarAccess) access;
		match_NPMVersionRequirement_WSTerminalRuleCall_0_0_q = new TokenAlias(false, true, grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0());
		match_NPMVersionRequirement_WSTerminalRuleCall_1_1_q = new TokenAlias(false, true, grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1());
		match_SimpleVersion_WSTerminalRuleCall_0_1_q = new TokenAlias(false, true, grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1());
		match_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_0_q = new TokenAlias(false, true, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0());
		match_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_2_q = new TokenAlias(false, true, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2());
		match_VersionRangeSetRequirement_WSTerminalRuleCall_1_2_q = new TokenAlias(false, true, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (ruleCall.getRule() == grammarAccess.getFILE_TAGRule())
			return getFILE_TAGToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getLETTER_VRule())
			return getLETTER_VToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getSEMVER_TAGRule())
			return getSEMVER_TAGToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getWILDCARDRule())
			return getWILDCARDToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getWSRule())
			return getWSToken(semanticObject, ruleCall, node);
		return "";
	}
	
	/**
	 * FILE_TAG:
	 * 	LETTER_F LETTER_I LETTER_L LETTER_E ':'
	 * ;
	 */
	protected String getFILE_TAGToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "f i l e :";
	}
	
	/**
	 * terminal LETTER_V :
	 * 	'v' | 'V'
	 * ;
	 */
	protected String getLETTER_VToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "v";
	}
	
	/**
	 * SEMVER_TAG:
	 * 	LETTER_S LETTER_E LETTER_M LETTER_V LETTER_E LETTER_R ':'
	 * ;
	 */
	protected String getSEMVER_TAGToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "s e m v r :";
	}
	
	/**
	 * WILDCARD:
	 * 	LETTER_X | ASTERIX
	 * ;
	 */
	protected String getWILDCARDToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "x";
	}
	
	/**
	 * terminal WS:
	 * 	WHITESPACE_FRAGMENT+;
	 */
	protected String getWSToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "\t";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_NPMVersionRequirement_WSTerminalRuleCall_0_0_q.equals(syntax))
				emit_NPMVersionRequirement_WSTerminalRuleCall_0_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_NPMVersionRequirement_WSTerminalRuleCall_1_1_q.equals(syntax))
				emit_NPMVersionRequirement_WSTerminalRuleCall_1_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_SimpleVersion_WSTerminalRuleCall_0_1_q.equals(syntax))
				emit_SimpleVersion_WSTerminalRuleCall_0_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_0_q.equals(syntax))
				emit_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_2_q.equals(syntax))
				emit_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_VersionRangeSetRequirement_WSTerminalRuleCall_1_2_q.equals(syntax))
				emit_VersionRangeSetRequirement_WSTerminalRuleCall_1_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     WS?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) (rule start)
	 *     (rule start) (ambiguity) ranges+=VersionRange
	 */
	protected void emit_NPMVersionRequirement_WSTerminalRuleCall_0_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     WS?
	 *
	 * This ambiguous syntax occurs at:
	 *     commitISH=ALPHA_NUMERIC_CHARS (ambiguity) (rule end)
	 *     githubUrl=URL_NO_VX (ambiguity) (rule end)
	 *     localPath=PATH (ambiguity) (rule end)
	 *     tagName=TAG (ambiguity) (rule end)
	 *     url=URL (ambiguity) (rule end)
	 *     versionSpecifier=URLVersionSpecifier (ambiguity) (rule end)
	 */
	protected void emit_NPMVersionRequirement_WSTerminalRuleCall_1_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     WS?
	 *
	 * This ambiguous syntax occurs at:
	 *     comparators+=VersionComparator (ambiguity) comparators+=VersionComparator
	 *     comparators+=VersionComparator (ambiguity) number=VersionNumber
	 *     comparators+=VersionComparator (ambiguity) withLetterV?=LETTER_V
	 */
	protected void emit_SimpleVersion_WSTerminalRuleCall_0_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     WS?
	 *
	 * This ambiguous syntax occurs at:
	 *     ranges+=VersionRange (ambiguity) '||' WS? ranges+=VersionRange
	 */
	protected void emit_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     WS?
	 *
	 * This ambiguous syntax occurs at:
	 *     ranges+=VersionRange WS? '||' (ambiguity) ranges+=VersionRange
	 */
	protected void emit_VersionRangeSetRequirement_WSTerminalRuleCall_1_1_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     WS?
	 *
	 * This ambiguous syntax occurs at:
	 *     ranges+=VersionRange (ambiguity) (rule end)
	 */
	protected void emit_VersionRangeSetRequirement_WSTerminalRuleCall_1_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
