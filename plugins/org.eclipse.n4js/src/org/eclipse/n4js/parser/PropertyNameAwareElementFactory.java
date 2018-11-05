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
package org.eclipse.n4js.parser;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.conversion.N4JSValueConverterException;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.ValueConverterWithValueException;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.DefaultEcoreElementFactory;
import org.eclipse.xtext.xtext.RuleNames;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * <p>
 * This keeps track on the concrete syntax that was used to parse a property assign.
 * </p>
 */
@SuppressWarnings("restriction")
@Singleton
public class PropertyNameAwareElementFactory extends DefaultEcoreElementFactory {

	private String stringRuleName;
	private String identifierRuleName;
	private String numberRuleName;

	@Inject
	private void readRuleNames(N4JSGrammarAccess grammarAccess, RuleNames ruleNames) {
		stringRuleName = ruleNames.getQualifiedName(grammarAccess.getSTRINGRule());
		identifierRuleName = ruleNames.getQualifiedName(grammarAccess.getIdentifierNameRule());
		numberRuleName = ruleNames.getQualifiedName(grammarAccess.getNumericLiteralAsStringRule());
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * If we parsed a {@link PropertyAssignment}, the set operation also initializes the {@link PropertyNameKind}.
	 * </p>
	 */
	@Override
	public void set(EObject object, String feature, Object value, String ruleName, INode node)
			throws N4JSValueConverterException {
		final EStructuralFeature structuralFeature = object.eClass().getEStructuralFeature(feature);
		if (structuralFeature == null)
			throw new IllegalArgumentException(object.eClass().getName() + "." + feature + " does not exist");

		try {
			final Object tokenValue = getTokenValue(value, ruleName, node);
			checkNullForPrimitiveFeatures(structuralFeature, tokenValue, node);
			object.eSet(structuralFeature, tokenValue);

			// this call is an extra to the super class method
			setPropertyNameKind(object, feature, ruleName);

			setRawStringValue(object, feature, value);

		} catch (ValueConverterWithValueException e) {
			final Object tokenValue = e.getValue();
			checkNullForPrimitiveFeatures(structuralFeature, tokenValue, node);
			object.eSet(structuralFeature, tokenValue);

			// this call is an extra to the super class method
			setPropertyNameKind(object, feature, ruleName);

			setRawStringValue(object, feature, value);

			throw e;
		} catch (ValueConverterException e) {
			throw e;
		} catch (NullPointerException e) {
			throw new N4JSValueConverterException(IssueCodes.getMessageForVCO_NPE(), IssueCodes.VCO_NPE, node, e);
		} catch (Exception e) {
			throw new ValueConverterException(null, node, e);
		}
	}

	private void setRawStringValue(EObject object, String feature, Object value) {
		EClass eClass = object.eClass();
		if (eClass == N4JSPackage.Literals.STRING_LITERAL
				&& N4JSPackage.Literals.STRING_LITERAL__VALUE.getName().equals(feature)) {
			StringLiteral casted = (StringLiteral) object;
			casted.setRawValue((String) getTokenAsStringIfPossible(value));
		} else if (eClass == N4JSPackage.Literals.TEMPLATE_SEGMENT
				&& N4JSPackage.Literals.TEMPLATE_SEGMENT__VALUE.getName().equals(feature)) {
			TemplateSegment casted = (TemplateSegment) object;
			String rawValueFromSource = (String) getTokenAsStringIfPossible(value);
			String rawValueNormalized = normalizeTemplateSegmentRawValue(rawValueFromSource);
			casted.setRawValue(rawValueNormalized);
		}
	}

	private void setPropertyNameKind(EObject object, String feature, String ruleName) {
		if (object instanceof LiteralOrComputedPropertyName) {
			final LiteralOrComputedPropertyName nameDecl = (LiteralOrComputedPropertyName) object;
			if ("literalName".equals(feature)) {
				if (identifierRuleName.equals(ruleName)) {
					nameDecl.setKind(PropertyNameKind.IDENTIFIER);
				} else if (stringRuleName.equals(ruleName)) {
					nameDecl.setKind(PropertyNameKind.STRING);
				} else if (numberRuleName.equals(ruleName)) {
					nameDecl.setKind(PropertyNameKind.NUMBER);
				} else {
					throw new IllegalArgumentException(ruleName);
				}
			} else if ("expression".equals(feature)) {
				nameDecl.setKind(PropertyNameKind.COMPUTED);
			}
		}
	}

	// only a copy of super class method
	private Object getTokenValue(Object tokenOrValue, String ruleName, INode node) throws N4JSValueConverterException {
		Object value = getTokenAsStringIfPossible(tokenOrValue);
		if ((value == null || value instanceof CharSequence) && ruleName != null) {
			value = getConverterService().toValue(value == null ? null : value.toString(), ruleName, node);
		}
		return value;
	}

	// only a copy of super class method
	private void checkNullForPrimitiveFeatures(EStructuralFeature structuralFeature, Object tokenValue, INode node) {
		if (tokenValue == null && structuralFeature.getEType().getInstanceClass().isPrimitive()) {
			throw new N4JSValueConverterException(
					IssueCodes.getMessageForVCO_NULL_FEATURE(structuralFeature.getName()), IssueCodes.VCO_NULL_FEATURE,
					node, null);
		}
	}

	/**
	 * When obtaining a template segment's raw source code, e.g. via the node model, the obtained string contains the
	 * leading "`" for head segments, the trailing "${" for head and middle segments, and the trailing "`" for tail
	 * segments. However, the leading "}" of middle and tail segments is missing. This method resolves this
	 * inconsistency by adding the missing "}" in the appropriate cases (also for consistency with the raw value of
	 * string literals, which always contains the leading and trailing quotes as used in the source code).
	 * <p>
	 * As an example, see the sample at {@link TemplateSegment#getRawValue()}: the leading "}" at the beginning of the
	 * second and third template segments of that example is added by this method and is not present in the node model.
	 */
	private static String normalizeTemplateSegmentRawValue(String rawSourceCode) {
		// some cases in which we have to prepend a "}":
		// "bbb${", "ccc`", "${" (empty middle segment!), "`" (empty trailing segment!)
		if (rawSourceCode != null && (!rawSourceCode.startsWith("`") || rawSourceCode.length() == 1)) {
			return "}" + rawSourceCode;
		}
		return rawSourceCode;
	}
}
