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
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.conversion.impl.QualifiedNameValueConverter;
import org.eclipse.xtext.xtext.RuleNames;

import com.google.inject.Inject;

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
		boolean res = (grammarElement instanceof RuleCall
				&& delegateRule == ((RuleCall) grammarElement).getRule());
		if (!res && grammarElement instanceof Keyword) {
			// support namespace.default
			String elementValue = ((Keyword) grammarElement).getValue();
			return N4JSLanguageConstants.EXPORT_DEFAULT_NAME.equals(elementValue);
		}
		return res;
	}

	@Override
	protected String delegateToString(String segment) {
		// support namespace.default
		return N4JSLanguageConstants.EXPORT_DEFAULT_NAME.equals(segment) ? segment : super.delegateToString(segment);
	}

}
