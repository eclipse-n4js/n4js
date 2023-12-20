/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._AdditiveExpression;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Parenthesis;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.AdditiveOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ParameterizedAccess;
import org.eclipse.n4js.n4JS.PrimaryExpression;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.Optional;
import org.eclipse.n4js.utils.parser.conversion.ValueConverterUtils;

import com.google.common.collect.Lists;

/**
 * Transforms ES2015 template literals to a corresponding ES5 equivalent.
 */
@Optional(GeneratorOption.TemplateStringLiterals)
public class TemplateStringTransformation extends Transformation {

	@Override
	public void assertPreConditions() {
		// true
	}

	@Override
	public void assertPostConditions() {
		// true
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		for (TemplateLiteral tl : collectNodes(getState().im, TemplateLiteral.class, true)) {
			transformTemplateLiteral(tl);
		}
	}

	private void transformTemplateLiteral(TemplateLiteral template) {
		EObject parent = template.eContainer();
		if (parent instanceof TaggedTemplateString) {
			transformTaggedTemplateLiteral((TaggedTemplateString) parent, template);
		} else {
			transformPlainTemplateLiteral(template);
		}
	}

	private void transformTaggedTemplateLiteral(TaggedTemplateString taggedTemplate, TemplateLiteral template) {
		// (1) collect string segments and expression segments
		List<StringLiteral> stringSegments = new ArrayList<>();
		List<Expression> expressionSegments = new ArrayList<>();
		for (Expression segment : Lists.newArrayList(template.getSegments())) { // avoid concurrent modification
			if (segment instanceof TemplateSegment) {
				TemplateSegment tSegm = (TemplateSegment) segment;
				stringSegments.add(_StringLiteral(tSegm.getValueAsString(), wrapAndQuote(tSegm.getValueAsString())));
			} else {
				expressionSegments.add(segment);
			}
		}
		// (2) transform the tagged template literal (invoke the tag function with the segments as arguments)
		Expression[] wrappedExprs = wrapIfRequired(expressionSegments);
		Expression[] args = new Expression[1 + expressionSegments.size()];
		args[0] = _ArrLit(stringSegments.toArray(new Expression[0]));
		for (int i = 0; i < wrappedExprs.length; i++) {
			args[i + 1] = wrappedExprs[i];
		}
		Expression replacement = _CallExpr(
				taggedTemplate.getTarget(),
				taggedTemplate.isOptionalChaining(),
				args);
		replace(taggedTemplate, replacement);
	}

	private void transformPlainTemplateLiteral(TemplateLiteral template) {
		// (1) transform contained template segments to string literals
		for (Expression segment : Lists.newArrayList(template.getSegments())) { // avoid concurrent modification
			if (segment instanceof TemplateSegment) {
				TemplateSegment tSegm = (TemplateSegment) segment;
				replace(segment, _StringLiteral(tSegm.getValueAsString(), wrapAndQuote(tSegm.getValueAsString())));
			} else {
				// the segment is an ordinary expression -> leave without change
			}
		}
		// (2) transform template literal itself
		Expression replacement;
		switch (template.getSegments().size()) {
		case 0:
			replacement = _StringLiteral("");
			break;
		case 1:
			replacement = template.getSegments().get(0);
			break;
		default:
			replacement = _Parenthesis(
					_AdditiveExpression(AdditiveOperator.ADD, wrapIfRequired(template.getSegments())));
		}
		replace(template, replacement);
	}

	private static Expression[] wrapIfRequired(List<Expression> expressions) {
		// required due to possible concurrent modification (see below)
		List<Expression> copy = new ArrayList<>();
		for (Expression expr : expressions) {
			boolean needParentheses = !(expr instanceof PrimaryExpression || expr instanceof ParameterizedAccess);
			if (needParentheses) {
				// this might change iterable 'expressions' (if an EMF containment reference was passed in)
				copy.add(_Parenthesis(expr));
			} else {
				copy.add(expr);
			}
		}
		return copy.toArray(new Expression[0]);
	}

	/**
	 * put raw into double quote and escape all existing double-quotes {@code '"' -> '\"' } and newlines
	 * {@code '\n' -> '\\n'}.
	 */
	private static String wrapAndQuote(String raw) {
		return '"' + ValueConverterUtils.convertToEscapedString(raw, true) + '"';
	}
}
