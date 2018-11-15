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
package org.eclipse.n4js.ui.contentassist;

import static org.eclipse.xtext.xtext.ParameterConfigHelper.getAssignedArguments;
import static org.eclipse.xtext.xtext.ParameterConfigHelper.getParameterConfig;

import java.util.List;
import java.util.Set;

import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.contentassist.antlr.RequiredRuleNameComputer;

import com.google.inject.Singleton;

// TODO remove with Xtext 2.13
/**
 * Fixes two issues with the computation of the methods that are supposed to be called on the parser.
 *
 * <ol>
 * <li>The parser attempts to call methods for groups that contain only one element. These grammar constructs are
 * normalized in the parser generator. See {@link #getEnclosingSingleElementGroup(AbstractElement)}.</li>
 * <li>The super impl has a bug in {@link #getRequiredRuleNames(Param)} which would return the original method name
 * instead of the adjusted name.</li>
 * </ol>
 *
 */
@Singleton
public class PatchedRequiredRuleNameComputer extends RequiredRuleNameComputer {

	private static final String[][] EMPTY_ARRAY = new String[0][];

	@Override
	protected boolean isFiltered(Param param) {
		AbstractElement elementToParse = param.elementToParse;
		while (elementToParse != null) {
			if (isFiltered(elementToParse, param)) {
				return true;
			}
			elementToParse = getEnclosingSingleElementGroup(elementToParse);
		}
		return false;
	}

	@SuppressWarnings("restriction")
	@Override
	public String[][] getRequiredRuleNames(Param param) {
		if (isFiltered(param)) {
			return EMPTY_ARRAY;
		}
		AbstractElement elementToParse = param.elementToParse;
		String ruleName = param.ruleName;
		if (ruleName == null) {
			if (elementToParse instanceof RuleCall) {
				RuleCall call = (RuleCall) elementToParse;
				if (call.getRule() instanceof ParserRule) {
					String antlrRuleName = getRuleNames().getAntlrRuleName(call.getRule());
					if (!call.getArguments().isEmpty()) {
						Set<Parameter> context = param.getAssignedParametes();
						Set<Parameter> arguments = getAssignedArguments(call, context);
						int config = getParameterConfig(arguments);
						antlrRuleName = getRuleNames().getAntlrRuleName(call.getRule(), config);
					}
					return new String[][] { { antlrRuleName } };
				}
			}
			return EMPTY_ARRAY;
		}
		String adjustedRuleName = adjustRuleName(ruleName, param);
		if (!(GrammarUtil.isOptionalCardinality(elementToParse)
				|| GrammarUtil.isOneOrMoreCardinality(elementToParse))) {
			return new String[][] { { adjustedRuleName } };
		}
		if ((elementToParse.eContainer() instanceof Group)) {
			List<AbstractElement> tokens = getFilteredElements(((Group) elementToParse.eContainer()).getElements(),
					param);
			int idx = tokens.indexOf(elementToParse) + 1;
			if (idx != tokens.size()) {
				String secondRule = param.getBaseRuleName((AbstractElement) elementToParse.eContainer());
				secondRule = secondRule.substring(0, secondRule.lastIndexOf('_') + 1) + idx;
				String adjustedSecondRule = adjustRuleName(secondRule, param);
				if (GrammarUtil.isMultipleCardinality(elementToParse))
					return new String[][] { { adjustedRuleName }, { adjustedRuleName, adjustedSecondRule } };
				return new String[][] { { adjustedRuleName, adjustedSecondRule } };
			}
		}
		// Fix is here. Original method returns the original ruleName instead of the adjusted rule name.
		return new String[][] { { adjustedRuleName } };
	}
}
