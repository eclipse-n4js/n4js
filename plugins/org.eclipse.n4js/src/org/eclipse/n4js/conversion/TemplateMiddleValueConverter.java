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

import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;

import org.eclipse.n4js.validation.IssueCodes;

/**
 */
public class TemplateMiddleValueConverter extends AbstractTemplateSegmentValueConverter {

	@Override
	protected String getRightDelimiter() {
		return "${";
	}

	@Override
	protected String getLeftDelimiter() {
		return "";
	}

	@Override
	protected N4JSValueConverterWithValueException newN4JSValueConverterException(INode node, String value) {
		return new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_TEMPLATE_QUOTE(),
				IssueCodes.VCO_TEMPLATE_MIDDLE, node, value, null);
	}

	@Override
	public void setRule(AbstractRule rule) {
		// use the nested terminal rule
		super.setRule(((RuleCall) rule.getAlternatives()).getRule());
	}
}
