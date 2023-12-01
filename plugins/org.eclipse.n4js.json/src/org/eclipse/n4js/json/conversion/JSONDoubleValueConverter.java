package org.eclipse.n4js.json.conversion;

import java.math.BigDecimal;

import org.eclipse.n4js.json.validation.JSONIssueCodes;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * A value converter that properly converts floating point JSON numbers to {@link BigDecimal}.
 */
public class JSONDoubleValueConverter extends AbstractLexerBasedConverter<BigDecimal> {

	@Override
	protected String toEscapedString(BigDecimal value) {
		return value.toString();
	}

	@Override
	protected void assertValidValue(BigDecimal value) {
		super.assertValidValue(value);
		if (value.signum() == -1)
			throw new ValueConverterException(JSONIssueCodes.JSON_INVALID_DOUBLE_VALUE.getMessage(),
					null, null);
	}

	@Override
	public BigDecimal toValue(String string, INode node) {
		if (Strings.isEmpty(string))
			throw new ValueConverterException(JSONIssueCodes.JSON_INVALID_DOUBLE_VALUE.getMessage(),
					node, null);
		try {
			return new BigDecimal(string);
		} catch (NumberFormatException e) {
			throw new ValueConverterException(JSONIssueCodes.JSON_INVALID_DOUBLE_VALUE.getMessage(),
					node, null);
		}
	}

}
