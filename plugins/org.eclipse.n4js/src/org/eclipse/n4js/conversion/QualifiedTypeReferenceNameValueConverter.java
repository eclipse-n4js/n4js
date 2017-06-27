/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.conversion;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.conversion.impl.QualifiedNameValueConverter;
import org.eclipse.xtext.xtext.RuleNames;

import com.google.inject.Inject;

import org.eclipse.n4js.services.N4JSGrammarAccess;

/**
 */
@SuppressWarnings("restriction")
public class QualifiedTypeReferenceNameValueConverter extends QualifiedNameValueConverter {

	private String delegateRuleName;
	private TerminalRule delegateRule;

	@Inject
	private void setDelegateRuleName(N4JSGrammarAccess grammarAccess, RuleNames ruleNames) {
		delegateRule = grammarAccess.getIDENTIFIERRule();
		this.delegateRuleName = ruleNames.getQualifiedName(delegateRule);
	}

	@Override
	protected String getDelegateRuleName() {
		return delegateRuleName;
	}

	@Override
	protected boolean isDelegateRuleCall(EObject grammarElement) {
		return (grammarElement instanceof RuleCall
				&& delegateRule == ((RuleCall) grammarElement).getRule());
	}

}
