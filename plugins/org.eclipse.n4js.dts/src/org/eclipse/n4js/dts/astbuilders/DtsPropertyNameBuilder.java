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

import java.util.List;

import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyNameContext;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link LiteralOrComputedPropertyName} from parse tree elements
 */
public class DtsPropertyNameBuilder extends AbstractDtsSubBuilder<PropertyNameContext, LiteralOrComputedPropertyName> {

	private final DtsExpressionBuilder expressionBuilder = new DtsExpressionBuilder(tokenStream, resource);

	/** Constructor */
	public DtsPropertyNameBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	public void enterPropertyName(PropertyNameContext name) {
		if (name == null) {
			return;
		}
		result = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
		List<IdentifierNameContext> identifierName = name.identifierName();
		if (name.OpenBracket() == null) {
			if (identifierName != null && identifierName.size() == 1) {
				result.setKind(PropertyNameKind.IDENTIFIER);
				result.setLiteralName(identifierName.get(0).Identifier().getText());
			} else if (name.numericLiteral() != null) {
				result.setKind(PropertyNameKind.NUMBER);
				result.setLiteralName(name.numericLiteral().getText());
			} else if (name.StringLiteral() != null) {
				result.setKind(PropertyNameKind.STRING);
				result.setLiteralName(ParserContextUtil.trimAndUnescapeStringLiteral(name.StringLiteral()));
			}
		} else {
			result.setKind(PropertyNameKind.COMPUTED);
			if (identifierName != null && !identifierName.isEmpty()) {
				Expression expr = expressionBuilder.consume(identifierName.get(0));
				for (int idx = 1; idx < identifierName.size(); idx++) {
					expr = ParserContextUtil.createParameterizedPropertyAccessExpression(resource, expr,
							identifierName.get(idx));
				}
			} else if (name.StringLiteral() != null) {
				result.setExpression(ParserContextUtil.createStringLiteral(name.StringLiteral()));
			}
		}
	}
}
