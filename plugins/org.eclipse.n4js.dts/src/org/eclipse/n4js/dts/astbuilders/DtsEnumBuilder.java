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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_enumBody;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_enumMemberList;

import java.math.BigDecimal;
import java.util.Set;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.dts.TypeScriptParser.EnumDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.EnumMemberContext;
import org.eclipse.n4js.dts.TypeScriptParser.LiteralExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.NumericLiteralContext;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsEnumBuilder extends AbstractDtsSubBuilder<EnumDeclarationContext, N4EnumDeclaration> {

	/** Constructor */
	public DtsEnumBuilder(LazyLinkingResource resource) {
		super(resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_enumBody,
				RULE_enumMemberList);
	}

	@Override
	public void enterEnumDeclaration(EnumDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4EnumDeclaration();
		result.setName(ctx.Identifier().getText());
		result.getDeclaredModifiers().add(N4Modifier.EXTERNAL);
	}

	@Override
	public void exitEnumDeclaration(EnumDeclarationContext ctx) {
		boolean definesAllStringValues = true;
		for (N4EnumLiteral literal : result.getLiterals()) {
			Expression valueExpression = literal.getValueExpression();
			definesAllStringValues &= (valueExpression instanceof StringLiteral);
		}

		Annotation valueBasedAnnotation = N4JSFactory.eINSTANCE.createAnnotation();
		String name = definesAllStringValues ? AnnotationDefinition.STRING_BASED.name
				: AnnotationDefinition.NUMBER_BASED.name;
		valueBasedAnnotation.setName(name);
		result.getAllAnnotations().add(valueBasedAnnotation);
	}

	@Override
	public void enterEnumMember(EnumMemberContext ctx) {
		N4EnumLiteral literal = N4JSFactory.eINSTANCE.createN4EnumLiteral();
		literal.setName(ctx.propertyName().getText());
		if (ctx.singleExpression() instanceof LiteralExpressionContext) {
			Expression valueExpression = null;
			LiteralExpressionContext leCtx = (LiteralExpressionContext) ctx.singleExpression();
			if (leCtx.literal().StringLiteral() != null) {
				StringLiteral stringLiteral = N4JSFactory.eINSTANCE.createStringLiteral();
				stringLiteral.setRawValue(leCtx.literal().StringLiteral().getText());
				valueExpression = stringLiteral;

			} else if (leCtx.literal().numericLiteral() != null) {
				NumericLiteral numericLiteral = N4JSFactory.eINSTANCE.createNumericLiteral();
				numericLiteral.setValue(getBigDecimal(leCtx.literal().numericLiteral()));
				valueExpression = numericLiteral;
			}
			literal.setValueExpression(valueExpression);
		}
		result.getLiterals().add(literal);
	}

	private BigDecimal getBigDecimal(NumericLiteralContext ctx) {
		if (ctx.DecimalLiteral() != null) {
			int sgn = ctx.Minus() != null ? -1 : 1;
			String decimalStr = ctx.DecimalLiteral().getText();
			double decimalValue = Double.parseDouble(decimalStr);
			return new BigDecimal(sgn * decimalValue);
		}

		return null;
	}
}
