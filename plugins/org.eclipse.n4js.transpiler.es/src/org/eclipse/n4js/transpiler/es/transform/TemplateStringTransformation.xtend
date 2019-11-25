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
package org.eclipse.n4js.transpiler.es.transform

import com.google.common.collect.Lists
import java.util.ArrayList
import org.eclipse.n4js.conversion.ValueConverterUtils
import org.eclipse.n4js.n4JS.AdditiveOperator
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ParameterizedAccess
import org.eclipse.n4js.n4JS.PrimaryExpression
import org.eclipse.n4js.n4JS.TaggedTemplateString
import org.eclipse.n4js.n4JS.TemplateLiteral
import org.eclipse.n4js.n4JS.TemplateSegment
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.Optional

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Transforms ES2015 template literals to a corresponding ES5 equivalent.
 */
@Optional(TemplateStringLiterals)
class TemplateStringTransformation extends Transformation {


	override assertPreConditions() {
		// true
	}

	override assertPostConditions() {
		// true
	}


	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, TemplateLiteral, true).forEach[transformTemplateLiteral];
	}

	def private void transformTemplateLiteral(TemplateLiteral template) {
		val parent = template.eContainer;
		if (parent instanceof TaggedTemplateString) {
			transformTaggedTemplateLiteral(parent, template);
		} else {
			transformPlainTemplateLiteral(template);
		}
	}

	def private void transformTaggedTemplateLiteral(TaggedTemplateString taggedTemplate, TemplateLiteral template) {
		// (1) collect string segments and expression segments
		val stringSegments = newArrayList;
		val expressionSegments = newArrayList;
		for(segment : Lists.newArrayList(template.segments)) { // avoid concurrent modification
			if(segment instanceof TemplateSegment) {
				stringSegments += _StringLiteral(segment.valueAsString, segment.valueAsString.wrapAndQuote);
			} else {
				expressionSegments += segment;
			}
		}
		// (2) transform the tagged template literal (invoke the tag function with the segments as arguments)
		val replacement = _CallExpr(
			taggedTemplate.target,
			taggedTemplate.optionalChaining,
			#[ _ArrLit(stringSegments) ] + expressionSegments.wrapIfRequired
		);
		replace(taggedTemplate, replacement);
	}

	def private void transformPlainTemplateLiteral(TemplateLiteral template) {
		// (1) transform contained template segments to string literals
		for(segment : Lists.newArrayList(template.segments)) { // avoid concurrent modification
			if(segment instanceof TemplateSegment) {
				replace(segment, _StringLiteral(segment.valueAsString, segment.valueAsString.wrapAndQuote));
			} else {
				// the segment is an ordinary expression -> leave without change
			}
		}
		// (2) transform template literal itself
		val replacement = switch(template.segments.size) {
			case 0:
				_StringLiteral("")
			case 1:
				template.segments.get(0)
			default:
				_Parenthesis(
					_AdditiveExpression(AdditiveOperator.ADD, template.segments.wrapIfRequired)
				)
		};
		replace(template, replacement);
	}

	def private static Iterable<Expression> wrapIfRequired(Iterable<Expression> expressions) {
		val copy = new ArrayList(expressions.toList); // required due to possible concurrent modification (see below)
		return copy.map[expr|
			val boolean needParentheses = !(expr instanceof PrimaryExpression || expr instanceof ParameterizedAccess);
			if(needParentheses) {
				return _Parenthesis(expr) // this might change iterable 'expressions' (if an EMF containment reference was passed in)
			} else {
				return expr
			}
		];
	}

	/** put raw into double quote and escape all existing double-quotes {@code '"' -> '\"' } and newlines {@code '\n' -> '\\n'}. */
	def private static String wrapAndQuote(String raw){
		'"' + ValueConverterUtils.convertToEscapedString(raw, true) + '"'
	}
}
