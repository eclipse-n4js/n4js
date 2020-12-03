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

import java.math.BigDecimal;

import org.eclipse.n4js.ts.conversions.IdentifierDelegateValueConverter;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService;
import org.eclipse.xtext.conversion.impl.KeywordAlternativeConverter;
import org.eclipse.xtext.conversion.impl.STRINGValueConverter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Registers special value converters for N4JS.
 */
@Singleton
public class ValueConverters extends AbstractDeclarativeValueConverterService {

	@Inject
	private BinaryIntValueConverter binaryIntValueConverter;

	@Inject
	private HexIntValueConverter hexIntValueConverter;

	@Inject
	private OctalIntValueConverter octalIntValueConverter;

	@Inject
	private LegacyOctalIntValueConverter legacyOctalIntValueConverter;

	@Inject
	private ScientificIntValueConverter scientificIntValueConverter;

	@Inject
	private DoubleValueConverter doubleValueConverter;

	@Inject
	private IdentifierValueConverter identifierValueConverter;
	@Inject
	private IdentifierDelegateValueConverter bindingIdentifierValueConverter;
	@Inject
	private IdentifierDelegateValueConverter identifierNameValueConverter;

	@Inject
	private KeywordAlternativeConverter identifierOrThisValueConverter;

	@Inject
	private KeywordAlternativeConverter typeReferenceNameValueConverter;

	@Inject
	private RegExLiteralConverter regExLiteralConverter;

	@Inject
	private HashbangValueConverter hashbangValueConverter;

	@Inject
	private STRINGValueConverter stringValueConverter;
	@Inject
	private KeywordAlternativeConverter stringLiteralAsNameConverter;

	@Inject
	private NoSubstitutionTemplateSegmentValueConverter noSubstitutionTemplateConverter;
	@Inject
	private TemplateEndValueConverter templateEndValueConverter;
	@Inject
	private TemplateHeadValueConverter templateHeadValueConverter;
	@Inject
	private TemplateMiddleValueConverter templateMiddleValueConverter;

	@Inject
	private ModuleSpecifierValueConverter moduleSpecifierValueConverter;

	@Inject
	private VersionRequestValueConverter versionRequestValueConverter;

	@Inject
	private JSXIdentifierValueConverter jsxIdentifierValueConverter;

	/**
	 * @return the registered value converter for the rule {@code NO_SUBSTITUTION_TEMPLATE_LITERAL}
	 */
	@ValueConverter(rule = "NO_SUBSTITUTION_TEMPLATE_LITERAL")
	public IValueConverter<String> noSubstitutionTemplateConverter() {
		return noSubstitutionTemplateConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code TEMPLATE_HEAD}
	 */
	@ValueConverter(rule = "TEMPLATE_HEAD")
	public IValueConverter<String> templateHeadValueConverter() {
		return templateHeadValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code TemplateTailLiteral}
	 */
	@ValueConverter(rule = "TemplateTailLiteral")
	public IValueConverter<String> templateEndValueConverter() {
		return templateEndValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code TemplateMiddleLiteral}
	 */
	@ValueConverter(rule = "TemplateMiddleLiteral")
	public IValueConverter<String> templateMiddleValueConverter() {
		return templateMiddleValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code BINARY_INT}
	 */
	@ValueConverter(rule = "BINARY_INT")
	public IValueConverter<BigDecimal> BinaryInt() {
		return binaryIntValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code HEX_INT}
	 */
	@ValueConverter(rule = "HEX_INT")
	public IValueConverter<BigDecimal> HexInt() {
		return hexIntValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code LEGACY_OCTAL_INT}
	 */
	@ValueConverter(rule = "LEGACY_OCTAL_INT")
	public IValueConverter<BigDecimal> LegacyOctalInt() {
		return legacyOctalIntValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code OCTAL_INT}
	 */
	@ValueConverter(rule = "OCTAL_INT")
	public IValueConverter<BigDecimal> OctalInt() {
		return octalIntValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code SCIENTIFIC_INT}
	 */
	@ValueConverter(rule = "SCIENTIFIC_INT")
	public IValueConverter<BigDecimal> ScientificInt() {
		return scientificIntValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code DOUBLE}
	 */
	@ValueConverter(rule = "DOUBLE")
	public IValueConverter<BigDecimal> Double() {
		return doubleValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code IDENTIFIER}
	 */
	@ValueConverter(rule = "IDENTIFIER")
	public IValueConverter<String> Identifier() {
		return identifierValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code BindingIdentifier}
	 */
	@ValueConverter(rule = "BindingIdentifier")
	public IValueConverter<String> BindingIdentifier() {
		return bindingIdentifierValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code IdentifierName}
	 */
	@ValueConverter(rule = "IdentifierName")
	public IValueConverter<String> IdentifierName() {
		return identifierNameValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code IdentifierOrThis}
	 */
	@ValueConverter(rule = "IdentifierOrThis")
	public IValueConverter<String> IdentifierOrThis() {
		return identifierOrThisValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code TypeReferenceName}
	 */
	@ValueConverter(rule = "TypeReferenceName")
	public IValueConverter<String> TypeReferenceName() {
		return typeReferenceNameValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code JSXIdentifier}
	 */
	@ValueConverter(rule = "JSXIdentifier")
	public IValueConverter<String> JSXIdentifier() {
		return jsxIdentifierValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code REGEX_LITERAL}
	 */
	@ValueConverter(rule = "REGEX_LITERAL")
	public IValueConverter<String> RegExLiteral() {
		return regExLiteralConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code STRING}
	 */
	@ValueConverter(rule = "STRING")
	public IValueConverter<String> STRING() {
		return stringValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code STRING}
	 */
	@ValueConverter(rule = "HASH_BANG")
	public IValueConverter<String> HASH_BANG() {
		return hashbangValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code StringLiteralAsName}
	 */
	@ValueConverter(rule = "StringLiteralAsName")
	public IValueConverter<String> StringLiteralAsName() {
		return stringLiteralAsNameConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code STRING}
	 */
	@ValueConverter(rule = "ModuleSpecifier")
	public IValueConverter<String> ModuleSpecifier() {
		return moduleSpecifierValueConverter;
	}

	/**
	 * @return the registered value converter for the rule {@code VERSION_REQUEST}
	 */
	@ValueConverter(rule = "VERSION")
	public IValueConverter<BigDecimal> VERSION() {
		return versionRequestValueConverter;
	}
}
