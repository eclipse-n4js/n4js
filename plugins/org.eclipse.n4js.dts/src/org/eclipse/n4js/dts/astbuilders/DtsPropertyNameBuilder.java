/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts.astbuilders;

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_propertyName;

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.dts.TypeScriptParser.ComputedPropertyContext;
import org.eclipse.n4js.dts.TypeScriptParser.ComputedPropertyNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierPropertyContext;
import org.eclipse.n4js.dts.TypeScriptParser.NumericPropertyContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.StringPropertyContext;
import org.eclipse.n4js.dts.utils.ParserContextUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.StringLiteral;

/**
 * Builder to create {@link LiteralOrComputedPropertyName} from parse tree elements
 */
public class DtsPropertyNameBuilder
		extends AbstractDtsBuilderWithHelpers<PropertyNameContext, LiteralOrComputedPropertyName> {

	/** Constructor */
	public DtsPropertyNameBuilder(AbstractDtsBuilder<?, ?> parent) {
		super(parent);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_propertyName);
	}

	@Override
	public void enterStringProperty(StringPropertyContext ctx) {
		if (ctx == null) {
			return;
		}
		result = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();

		result.setKind(PropertyNameKind.STRING);
		result.setLiteralName(ParserContextUtils.trimAndUnescapeStringLiteral(ctx.StringLiteral()));
	}

	@Override
	public void enterNumericProperty(NumericPropertyContext ctx) {
		if (ctx == null) {
			return;
		}
		result = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();

		result.setKind(PropertyNameKind.NUMBER);
		result.setLiteralName(ctx.getText());
	}

	@Override
	public void enterComputedProperty(ComputedPropertyContext ctx) {
		if (ctx == null) {
			return;
		}
		result = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();

		ComputedPropertyNameContext cpn = ctx.computedPropertyName();
		List<IdentifierNameContext> identifierName = cpn.identifierName();
		result.setKind(PropertyNameKind.COMPUTED);
		if (identifierName != null && !identifierName.isEmpty()) {
			Expression expr = newExpressionBuilder().consume(identifierName.get(0));
			for (int idx = 1; idx < identifierName.size(); idx++) {
				expr = createParameterizedPropertyAccessExpression(expr, identifierName.get(idx));
			}
			result.setExpression(expr);
		} else if (cpn.StringLiteral() != null) {
			StringLiteral stringLiteral = ParserContextUtils.createStringLiteral(cpn.StringLiteral());
			result.setExpression(stringLiteral);
		}
	}

	@Override
	public void enterIdentifierProperty(IdentifierPropertyContext ctx) {
		if (ctx == null) {
			return;
		}
		result = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();

		IdentifierNameContext identifierName = ctx.identifierName();
		result.setKind(PropertyNameKind.IDENTIFIER);
		if (identifierName.Identifier() != null) {
			result.setLiteralName(identifierName.Identifier().getText());
		} else if (identifierName.reservedWord() != null) {
			result.setLiteralName(identifierName.reservedWord().getText());
		}
	}
}
