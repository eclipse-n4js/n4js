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

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.XtextParametrizedRunner;
import org.eclipse.n4js.XtextParametrizedRunner.ParametersProvider;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.testing.InjectWith;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Check that there is no predicate on the parser rule Expression.
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextParametrizedRunner.class)
public class GreedyPredicateTest {

	/**
	 * Many of these can only be avoided if we swap the position of identifiable and type, e.g. instead of
	 * {@code var string s = ''} we would need to use {@code var s: string = ''}.
	 *
	 * TODO IDE-1580 re-activate watch dog for the following rules (if possible)
	 *
	 * TODO Should be possible for some of these as soon as the deprecated type annotation notation is removed from the
	 * grammar.
	 */
	static final Set<String> GREEDY_RULES = Set.of(
			"ArrowExpression",
			"BindingElementFragment",
			"VariableBinding",
			"ExportedVariableBinding",
			"CatchVariable",
			"AnnotatedPropertyAssignment",
			"PropertyMethodDeclaration",
			"PropertyNameValuePair",
			"BindingProperty",
			"BindingElementImpl",
			"AnnotatedN4MemberDeclaration",
			"N4MemberDeclaration",
			"N4MethodDeclaration",
			"AnnotationArgument");

	@Inject
	N4JSGrammarAccess grammarAccess;

	final String ruleName;
	final ParserRule ruleToCheck;
	final Set<RuleCall> allVisited = new LinkedHashSet<>();
	final Set<RuleCall> currentlyVisiting = new LinkedHashSet<>();

	boolean inPredicate = false;
	ParserRule ruleToAvoid;

	@ParametersProvider(name = "{0}")
	public static Provider<Iterable<Object[]>> getRules() {
		return new Provider<>() {
			@Inject
			N4JSGrammarAccess grammarAccess;

			@Override
			public Iterable<Object[]> get() {
				List<ParserRule> allParserRules = GrammarUtil.allParserRules(grammarAccess.getGrammar());
				return map(
						filter(allParserRules, r -> !GREEDY_RULES.contains(r.getName())),
						(it) -> new Object[] { it.getName(), it });
			}
		};
	}

	public GreedyPredicateTest(String ruleName, ParserRule ruleToCheck) {
		this.ruleName = ruleName;
		this.ruleToCheck = ruleToCheck;
	}

	@Test
	public void testExpressionEnclosedInPredicate() {
		Assume.assumeTrue(ruleToCheck != grammarAccess.getForStatementRule());
		Assume.assumeTrue(ruleToCheck != grammarAccess.getShiftExpressionRule());

		ruleToAvoid = grammarAccess.getExpressionRule();
		visit(ruleToCheck.getAlternatives());
	}

	@Test
	public void testSemiEnclosedInPredicate() {
		Assume.assumeTrue(ruleToCheck != grammarAccess.getForStatementRule());
		Assume.assumeTrue(ruleToCheck != grammarAccess.getShiftExpressionRule());

		ruleToAvoid = grammarAccess.getExpressionRule();
		visit(ruleToCheck.getAlternatives());
	}

	@Test
	public void testRegExEnclosedInPredicate() {
		Assume.assumeTrue(ruleToCheck != grammarAccess.getForStatementRule());
		Assume.assumeTrue(ruleToCheck != grammarAccess.getShiftExpressionRule());
		Assume.assumeTrue(ruleToCheck != grammarAccess.getAnnotationListRule());

		ruleToAvoid = grammarAccess.getREGEX_LITERALRule();
		visit(ruleToCheck.getAlternatives());
	}

	@Test
	public void testTemplateExpressionEnclosedInPredicate_01() {
		Assume.assumeTrue(ruleToCheck != grammarAccess.getForStatementRule());
		Assume.assumeTrue(ruleToCheck != grammarAccess.getShiftExpressionRule());

		ruleToAvoid = grammarAccess.getTemplateMiddleLiteralRule();
		visit(ruleToCheck.getAlternatives());
	}

	@Test
	public void testTemplateExpressionEnclosedInPredicate_02() {
		Assume.assumeTrue(ruleToCheck != grammarAccess.getForStatementRule());
		Assume.assumeTrue(ruleToCheck != grammarAccess.getShiftExpressionRule());

		ruleToAvoid = grammarAccess.getTemplateExpressionEndRule();
		visit(ruleToCheck.getAlternatives());
	}

	void visit(EObject object) {
		if (object instanceof AbstractElement) {
			visit((AbstractElement) object);
		} else if (object instanceof RuleCall) {
			visit((RuleCall) object);
		} else if (object instanceof ParserRule) {
			visit((ParserRule) object);
		}
	}

	void visit(AbstractElement element) {
		if (inPredicate && element.isPredicated()) {
			return;
		}
		if (element.isFirstSetPredicated()) {
			return;
		}
		maintainPredicatedFlag(element.isPredicated(), () -> {
			for (EObject obj : element.eContents()) {
				visit(obj);
			}
		});
	}

	public void maintainPredicatedFlag(boolean predicated, Runnable runMe) {
		boolean wasInPredicate = inPredicate;
		inPredicate = inPredicate || predicated;
		try {
			runMe.run();
		} finally {
			inPredicate = wasInPredicate;
		}
	}

	void visit(RuleCall call) {
		if (call.isFirstSetPredicated()) {
			return;
		}
		if (call.isPredicated() != inPredicate) {
			if (allVisited.add(call)) {
				try {
					currentlyVisiting.add(call);
					if (inPredicate && call.getRule() == ruleToAvoid) {

						String names = join("\n-> ", map(currentlyVisiting, r -> r.getRule().getName()));
						Assert.fail("Predicate in rule " + ruleName + " encloses " + ruleToAvoid.getName() + " via "
								+ names);
					}
					// we have to follow the call graph if this call is predicated and we are not yet predicated or we
					// are predicated and this call is not predicated
					maintainPredicatedFlag(call.isPredicated(), () -> {
						if (inPredicate) {
							visit(call.getRule());
						}
					});
				} finally {
					currentlyVisiting.remove(call);
				}
			}
		}

	}

	void visit(ParserRule rule) {
		visit(rule.getAlternatives());
	}

}
