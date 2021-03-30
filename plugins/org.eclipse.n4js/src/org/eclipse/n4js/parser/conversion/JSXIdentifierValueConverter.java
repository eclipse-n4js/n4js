/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.parser.conversion;

import java.util.Iterator;

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractValueConverter;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.base.CharMatcher;

/**
 * Converts a JSXIdentifier to a semantic value.
 */
public class JSXIdentifierValueConverter extends AbstractValueConverter<String> {

	private static final CharMatcher disallowedChar = CharMatcher.anyOf(" /\\").precomputed();

	@Override
	public String toValue(String string, INode node) throws ValueConverterException {
		if (node != null) {
			Iterator<ILeafNode> leafNodes = node.getLeafNodes().iterator();
			while (leafNodes.hasNext()) {
				ILeafNode leading = leafNodes.next();
				// skip leading hidden nodes
				if (!leading.isHidden()) {
					ILeafNode first = leading;
					int firstOffset = first.getOffset();
					validate(string, firstOffset, first);
					while (leafNodes.hasNext()) {
						ILeafNode toBeChecked = leafNodes.next();
						validate(string, firstOffset, toBeChecked);
					}
				}
			}
		} else {
			int idx = disallowedChar.indexIn(string);
			if (idx != -1) {
				throw new N4JSValueConverterWithValueException(
						IssueCodes.getMessageForVCO_IDENT_ILLEGAL_CHAR_WITH_RESULT(string, string.charAt(idx), idx),
						IssueCodes.VCO_IDENT_ILLEGAL_CHAR_WITH_RESULT, node, string.substring(0, idx), null);
			}
		}
		return string;
	}

	/**
	 * @param leaf
	 *            the leaf node to validate.
	 */
	private void validate(String value, int firstOffset, ILeafNode leaf) {
		if (leaf.isHidden()) {
			int idx = leaf.getTotalOffset() - firstOffset;
			throw new N4JSValueConverterWithValueException(
					IssueCodes.getMessageForVCO_JSXIDENT_WHITESPACE_COMMENT(),
					IssueCodes.VCO_JSXIDENT_WHITESPACE_COMMENT, leaf, value.substring(0, idx), null);
		} else {
			int escapeSequence = leaf.getText().indexOf("\\");
			if (escapeSequence >= 0) {
				int idx = leaf.getTotalOffset() - firstOffset + escapeSequence;
				throw new N4JSValueConverterWithValueException(
						IssueCodes.getMessageForVCO_IDENT_ILLEGAL_CHAR_WITH_RESULT(value,
								leaf.getText().charAt(escapeSequence), idx),
						IssueCodes.VCO_IDENT_ILLEGAL_CHAR_WITH_RESULT, leaf, value.substring(0, idx), null);
			}
		}
	}

	@Override
	public String toString(String value) throws ValueConverterException {
		return value;
	}

}
