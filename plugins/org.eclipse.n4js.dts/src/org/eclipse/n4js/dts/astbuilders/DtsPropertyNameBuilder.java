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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_computedPropertyName;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_identifierName;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_numericLiteral;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_propertyName;

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.ComputedPropertyNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.NumericLiteralContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyNameContext;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link LiteralOrComputedPropertyName} from parse tree elements
 */
public class DtsPropertyNameBuilder
		extends AbstractDtsBuilderWithHelpers<PropertyNameContext, LiteralOrComputedPropertyName> {

	/** Constructor */
	public DtsPropertyNameBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_propertyName,
				RULE_numericLiteral,
				RULE_computedPropertyName,
				RULE_identifierName);
	}

	@Override
	public void enterPropertyName(PropertyNameContext ctx) {
		if (ctx == null) {
			return;
		}
		result = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
		if (ctx.StringLiteral() != null) {
			result.setKind(PropertyNameKind.STRING);
			result.setLiteralName(ParserContextUtil.trimAndUnescapeStringLiteral(ctx.StringLiteral()));
		}
	}

	@Override
	public void enterComputedPropertyName(ComputedPropertyNameContext ctx) {
		List<IdentifierNameContext> identifierName = ctx.identifierName();
		result.setKind(PropertyNameKind.COMPUTED);
		if (identifierName != null && !identifierName.isEmpty()) {
			Expression expr = newExpressionBuilder().consume(identifierName.get(0));
			for (int idx = 1; idx < identifierName.size(); idx++) {
				expr = createParameterizedPropertyAccessExpression(expr, identifierName.get(idx));
			}
		} else if (ctx.StringLiteral() != null) {
			result.setExpression(ParserContextUtil.createStringLiteral(ctx.StringLiteral()));
		}
	}

	@Override
	public void enterNumericLiteral(NumericLiteralContext ctx) {
		result.setKind(PropertyNameKind.NUMBER);
		result.setLiteralName(ctx.getText());
	}

	@Override
	public void enterIdentifierName(IdentifierNameContext ctx) {
		result.setKind(PropertyNameKind.IDENTIFIER);
		if (ctx.Identifier() != null) {
			result.setLiteralName(ctx.Identifier().getText());
		} else if (ctx.reservedWord() != null) {
			result.setLiteralName(ctx.reservedWord().getText());
		}
	}
}
