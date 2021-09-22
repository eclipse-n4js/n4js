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
package org.eclipse.n4js.tests;

import static org.eclipse.xtext.EcoreUtil2.eAllContentsAsList;
import static org.eclipse.xtext.EcoreUtil2.typeSelect;

import java.util.Collection;
import java.util.List;

import org.eclipse.n4js.N4JSStandaloneSetup;
import org.eclipse.n4js.TypeExpressionsStandaloneSetup;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.services.TypeExpressionsGrammarAccess;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.inject.Inject;

/**
 * Small helper to identifiy potential programming problems in the grammar.
 */
public class GrammarLinter {

	@Inject
	private N4JSGrammarAccess grammarAccess;
	private TypeExpressionsGrammarAccess typesGrammarAccess;

	/***/
	public static void main(String[] args) {
		GrammarLinter linter = new N4JSStandaloneSetup().createInjectorAndDoEMFRegistration().getInstance(
				GrammarLinter.class);
		linter.typesGrammarAccess = new TypeExpressionsStandaloneSetup().createInjector().getInstance(
				TypeExpressionsGrammarAccess.class);
		linter.diagnose();
	}

	/***/
	public void diagnose() {
		printKeywordsOnlyInDatatypeRules();
		printKeywordsInTypesButNotInN4JS();
	}

	private void printKeywordsInTypesButNotInN4JS() {
		Grammar n4js = grammarAccess.getGrammar();
		Grammar types = typesGrammarAccess.getGrammar();
		ListMultimap<String, Keyword> n4jsKeywords = getAllKeywords(n4js);
		ListMultimap<String, Keyword> typesKeywords = getAllKeywords(types);

		typesKeywords.keySet().removeAll(n4jsKeywords.keySet());

		System.out.println("Keywords which do not occur in n4js rules: ");
		for (String keyword : typesKeywords.keySet()) {
			System.out.println("  " + keyword);
		}
		System.out.println();
	}

	private void printKeywordsOnlyInDatatypeRules() {
		Grammar grammar = grammarAccess.getGrammar();
		ListMultimap<String, Keyword> allKeywords = getAllKeywords(grammar);
		System.out.println("Keywords which do not occur in production rules: ");
		outer: for (Collection<Keyword> chunk : allKeywords.asMap().values()) {
			for (Keyword keyword : chunk) {
				AbstractRule rule = EcoreUtil2.getContainerOfType(keyword, AbstractRule.class);
				if (!GrammarUtil.isDatatypeRule(rule)) {
					continue outer;
				}
			}
			System.out.println("  " + ((List<Keyword>) chunk).get(0).getValue());
		}
		System.out.println();
	}

	private static ListMultimap<String, Keyword> getAllKeywords(Grammar g) {
		ListMultimap<String, Keyword> keywords = ArrayListMultimap.create();
		List<ParserRule> rules = GrammarUtil.allParserRules(g);
		for (ParserRule parserRule : rules) {
			List<Keyword> list = typeSelect(eAllContentsAsList(parserRule), Keyword.class);
			for (Keyword keyword : list) {
				keywords.put(keyword.getValue(), keyword);
			}
		}
		List<EnumRule> enumRules = GrammarUtil.allEnumRules(g);
		for (EnumRule enumRule : enumRules) {
			List<Keyword> list = typeSelect(eAllContentsAsList(enumRule), Keyword.class);
			for (Keyword keyword : list) {
				keywords.put(keyword.getValue(), keyword);
			}
		}
		return keywords;
	}

}
