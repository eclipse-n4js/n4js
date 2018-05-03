package org.eclipse.n4js.json.conversion;

import java.math.BigDecimal;

import org.eclipse.n4js.conversion.DoubleValueConverter;
import org.eclipse.n4js.conversion.ScientificIntValueConverter;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService;

import com.google.inject.Inject;

public class JSONValueConverterService extends AbstractDeclarativeValueConverterService {
	
	@Inject
	private JsonSTRINGValueConverter stringValueConverter;
	
	@Inject
	private DoubleValueConverter doubleValueConverter;
	
	@Inject
	private ScientificIntValueConverter scientificIntValueConverter;
	
	/**
	 * @return the registered value converter for the rule {@code STRING}
	 */
	@ValueConverter(rule = "STRING")
	public IValueConverter<String> STRING() {
		return stringValueConverter;
	}
	
	/**
	 * @return the registered value converter for the rule {@code DOUBLE}
	 */
	@ValueConverter(rule = "DOUBLE")
	public IValueConverter<BigDecimal> Double() {
		return doubleValueConverter;
	}
	
	/**
	 * @return the registered value converter for the rule {@code SCIENTIFIC_INT}
	 */
	@ValueConverter(rule = "SCIENTIFIC_INT")
	public IValueConverter<BigDecimal> ScientificInt() {
		return scientificIntValueConverter;
	}
}
