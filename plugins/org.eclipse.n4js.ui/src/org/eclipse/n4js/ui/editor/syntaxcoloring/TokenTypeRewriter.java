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
package org.eclipse.n4js.ui.editor.syntaxcoloring;

import java.util.Map;

import org.antlr.runtime.CommonToken;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Runtime hook to rewrite token types based on consumed grammar elements. The mapping is populated once in the
 * constructor. Rewriting is done in {@link #rewrite(CommonToken, AbstractElement)}.
 *
 * TODO use N4JSConstants instead of the duplicated sets.
 */
@Singleton
public class TokenTypeRewriter implements PseudoTokens {

	/** Public for testing */
	public static class N4JSKeywords {
		/** Public for testing */
		public static final ImmutableSet<String> es5Keywords = ImmutableSet.of(
				"break",
				"case",
				"catch",
				"continue",
				"debugger",
				"default",
				"delete",
				"do",
				"else",
				"finally",
				"for",
				"function",
				"if",
				"in",
				"instanceof",
				"new",
				"return",
				"switch",
				"this",
				"throw",
				"try",
				"typeof",
				"var",
				"void",
				"while",
				"with");
		/** Public for testing */
		public static final ImmutableSet<String> es6Keywords = ImmutableSet.of(
				"class",
				"const",
				"export",
				"import",
				"yield");
		/** Public for testing */
		public static final ImmutableSet<String> futureKeywords = ImmutableSet.of(
				"enum");
		/** Public for testing */
		public static final ImmutableSet<String> nullLiteral = ImmutableSet.of(
				"null");
		/** Public for testing */
		public static final ImmutableSet<String> booleanLiteral = ImmutableSet.of(
				"true", "false");
		/** Public for testing */
		public static final ImmutableSet<String> n4jsKeyword = ImmutableSet.of(
				"abstract",
				"as",
				"from",
				"extends",
				"implements",
				"interface",
				"let",
				"of",
				"static",
				"super",
				"set",
				"get",
				"external",
				"project",
				"private",
				"protected",
				"public",
				"target",
				"async",
				"await", // future reserved keyword in ECMAScript 2015, but only in modules
				"out");
		/** Public for testing */
		public static final ImmutableSet<String> keywordFromTypeRef = ImmutableSet.of(
				"type",
				"union",
				"intersection",
				"constructor");
		/** Public for testing */
		public static final ImmutableSet<String> hardcodedAnnotationAndSemanticKeywords = ImmutableSet.of(
				"This",
				"Promisify");

		/**
		 * Returns the token type for the given keyword.
		 */
		public Integer getTokenType(Keyword kw) {
			String text = kw.getValue();
			Integer type = getTokenType(text, es5Keywords, ES5_KW_TOKEN, null);
			type = getTokenType(text, es6Keywords, ES6_KW_TOKEN, type);
			type = getTokenType(text, futureKeywords, FUTURE_KW_TOKEN, type);
			type = getTokenType(text, nullLiteral, NULL_TOKEN, type);
			type = getTokenType(text, booleanLiteral, BOOLEAN_TOKEN, type);
			type = getTokenType(text, n4jsKeyword, N4JS_KW_TOKEN, type);
			return type;
		}

		private Integer getTokenType(String text, ImmutableSet<String> set, int type, Integer refinedType) {
			if (refinedType != null) {
				if (set.contains(text)) {
					throw new RuntimeException("duplicate keyword declaration: " + text);
				}
				return refinedType;
			}
			if (set.contains(text)) {
				return type;
			}
			return null;
		}
	}

	private final Map<AbstractElement, Integer> mapping;

	/**
	 * Creates a new instance.
	 */
	@Inject
	public TokenTypeRewriter(N4JSGrammarAccess ga) {
		ImmutableMap.Builder<AbstractElement, Integer> builder = ImmutableMap.builder();
		rewriteRegExLiterals(ga, builder);
		rewriteTemplateLiterals(ga, builder);
		rewriteAnnotationReferences(ga, builder);
		rewriteTypeReferences(ga, builder);
		rewriteIdentifiers(ga, builder);
		rewriteTypeVariables(ga, builder);
		rewriteNumberLiterals(ga, builder);
		rewriteKeywords(ga, builder);
		this.mapping = builder.build();
	}

	private static void rewriteKeywords(N4JSGrammarAccess ga,
			ImmutableMap.Builder<AbstractElement, Integer> builder) {
		N4JSKeywords keywords = new N4JSKeywords();
		for (ParserRule rule : GrammarUtil.allParserRules(ga.getGrammar())) {
			if (!GrammarUtil.isDatatypeRule(rule)) {
				rewriteKeywords(rule, keywords, builder);
			}
		}
		for (EnumRule rule : GrammarUtil.allEnumRules(ga.getGrammar())) {
			rewriteKeywords(rule, keywords, builder);
		}
	}

	private static void rewriteKeywords(AbstractRule rule, N4JSKeywords keywords,
			ImmutableMap.Builder<AbstractElement, Integer> builder) {
		for (EObject obj : EcoreUtil2.eAllContents(rule.getAlternatives())) {
			if (obj instanceof Keyword) {
				Keyword keyword = (Keyword) obj;
				Integer type = keywords.getTokenType(keyword);
				if (type != null) {
					if (keyword.eContainer() instanceof EnumLiteralDeclaration) {
						builder.put((AbstractElement) keyword.eContainer(), type);
					} else {
						builder.put(keyword, type);
					}
				}
			}
		}
	}

	private static void rewriteTemplateLiterals(N4JSGrammarAccess ga,
			ImmutableMap.Builder<AbstractElement, Integer> builder) {
		builder.put(ga.getTemplateExpressionEndAccess().getRightCurlyBracketKeyword(),
				InternalN4JSParser.RULE_TEMPLATE_CONTINUATION);
	}

	private static void rewriteTypeVariables(N4JSGrammarAccess ga,
			ImmutableMap.Builder<AbstractElement, Integer> builder) {
		builder.put(ga.getN4TypeVariableAccess().getNameAssignment_1(), TYPE_VARIABLE_TOKEN);
	}

	private static void rewriteRegExLiterals(N4JSGrammarAccess ga,
			ImmutableMap.Builder<AbstractElement, Integer> builder) {
		builder.put(ga.getREGEX_LITERALAccess().getSolidusKeyword_0_0(), InternalN4JSParser.RULE_REGEX_START);
		builder.put(ga.getREGEX_LITERALAccess().getSolidusEqualsSignKeyword_0_1(), InternalN4JSParser.RULE_REGEX_START);
	}

	private static void rewriteAnnotationReferences(N4JSGrammarAccess ga,
			ImmutableMap.Builder<AbstractElement, Integer> builder) {
		builder.put(ga.getAnnotationNoAtSignAccess().getNameAssignment_0(),
				InternalN4JSParser.CommercialAtCommercialAt);
		builder.put(ga.getFunctionTypeExpressionOLDAccess().getThisKeyword_2_1(),
				InternalN4JSParser.CommercialAtCommercialAt);
		builder.put(ga.getPromisifyExpressionAccess().getPromisifyKeyword_0_0_2(),
				InternalN4JSParser.CommercialAtCommercialAt);
	}

	private static void rewriteTypeReferences(N4JSGrammarAccess ga,
			ImmutableMap.Builder<AbstractElement, Integer> builder) {
		for (ParserRule rule : GrammarUtil.allParserRules(ga.getGrammar())) {
			for (EObject obj : EcoreUtil2.eAllContents(rule.getAlternatives())) {
				if (obj instanceof Assignment) {
					Assignment assignment = (Assignment) obj;
					AbstractElement terminal = assignment.getTerminal();
					if (terminal instanceof RuleCall) {
						AbstractRule calledRule = ((RuleCall) terminal).getRule();
						EClassifier classifier = calledRule.getType().getClassifier();
						if (classifier instanceof EClass
								&& TypeRefsPackage.Literals.TYPE_REF.isSuperTypeOf((EClass) classifier)) {
							builder.put(assignment, TYPE_REF_TOKEN);
						}
					}
				}
			}
		}
	}

	private static void rewriteIdentifiers(N4JSGrammarAccess ga,
			ImmutableMap.Builder<AbstractElement, Integer> builder) {
		ImmutableSet<AbstractRule> identifierRules = ImmutableSet.of(
				ga.getBindingIdentifierRule(),
				ga.getIdentifierNameRule(),
				ga.getIDENTIFIERRule());
		for (ParserRule rule : GrammarUtil.allParserRules(ga.getGrammar())) {
			for (EObject obj : EcoreUtil2.eAllContents(rule.getAlternatives())) {
				if (obj instanceof Assignment) {
					Assignment assignment = (Assignment) obj;
					AbstractElement terminal = assignment.getTerminal();
					int type = InternalN4JSParser.RULE_IDENTIFIER;
					if (terminal instanceof CrossReference) {
						terminal = ((CrossReference) terminal).getTerminal();
						type = IDENTIFIER_REF_TOKEN;
					}
					if (terminal instanceof RuleCall) {
						AbstractRule calledRule = ((RuleCall) terminal).getRule();
						if (identifierRules.contains(calledRule)) {
							builder.put(assignment, type);
						}
					}
				}
			}
		}
	}

	private static void rewriteNumberLiterals(N4JSGrammarAccess ga,
			ImmutableMap.Builder<AbstractElement, Integer> builder) {
		for (ParserRule rule : GrammarUtil.allParserRules(ga.getGrammar())) {
			for (EObject obj : EcoreUtil2.eAllContents(rule.getAlternatives())) {
				if (obj instanceof Assignment) {
					Assignment assignment = (Assignment) obj;
					AbstractElement terminal = assignment.getTerminal();
					if (terminal instanceof RuleCall) {
						AbstractRule calledRule = ((RuleCall) terminal).getRule();
						EClassifier classifier = calledRule.getType().getClassifier();
						if (classifier == EcorePackage.Literals.EBIG_DECIMAL) {
							builder.put(assignment, NUMBER_LITERAL_TOKEN);
						}
					}
				}
			}
		}
	}

	/**
	 * Overrides the token type if the given element was configured as a special token type. Returns {@code null} if no
	 * rewrite should happen.
	 *
	 * @param token
	 *            the token to rewrite. Optional, if {@code null} no rewrite will be performed.
	 * @param element
	 *            the abstract element.
	 * @return returns with the rewritten token type or {@code null} if no rewrite were performed.
	 */
	public Integer rewrite(CommonToken token, AbstractElement element) {
		Integer mappedType = mapping.get(element);
		if (mappedType != null) {
			if (null != token) {
				token.setType(mappedType);
			}
		}
		return mappedType;
	}

}
