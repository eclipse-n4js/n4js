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
package org.eclipse.n4js.parser.conversion;

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.nodemodel.INode;

/**
 */
public class NoSubstitutionTemplateSegmentValueConverter extends AbstractTemplateSegmentValueConverter {

	@Override
	protected String getRightDelimiter() {
		return "`";
	}

	@Override
	protected String getLeftDelimiter() {
		return "`";
	}

	/**
	 * Creates a new value converter exception.
	 */
	@Override
	protected N4JSValueConverterWithValueException newN4JSValueConverterException(INode node, String value) {
		return new N4JSValueConverterWithValueException(IssueCodes.VCO_TEMPLATE_QUOTE.getMessage(),
				IssueCodes.VCO_TEMPLATE_QUOTE.name(), node, value, null);
	}

}
