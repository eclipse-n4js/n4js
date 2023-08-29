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
package org.eclipse.n4js.tests

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.XtextParametrizedRunner
import org.eclipse.n4js.XtextParametrizedRunner.ParametersProvider
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.AbstractElement
import org.eclipse.xtext.GrammarUtil
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.testing.InjectWith
import org.junit.Assert
import org.junit.Assume
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Check that there is no predicate on the parser rule Expression.
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextParametrizedRunner)
@FinalFieldsConstructor
class GreedyPredicateTest {

	/**
	 * Many of these can only be avoided if we swap the position of identifiable and type, e.g.
	 * instead of {@code var string s = ''} we would need to use {@code var s: string = ''}.
	 *
	 * TODO IDE-1580 re-activate watch dog for the following rules (if possible)
	 *
	 * TODO Should be possible for some of these as soon as the deprecated type annotation notation is removed from the grammar.
	 */
	val static final GREEDY_RULES = #[
		'ArrowExpression',
		'BindingElementFragment',
		'VariableBinding',
		'ExportedVariableBinding',
		'CatchVariable',
		'AnnotatedPropertyAssignment',
		'PropertyMethodDeclaration',
		'PropertyNameValuePair',
		'BindingProperty',
		'BindingElementImpl',
		'AnnotatedN4MemberDeclaration',
		'N4MemberDeclaration',
		'N4MethodDeclaration',
		'AnnotationArgument'
	];

	@Inject
	N4JSGrammarAccess grammarAccess

	val String ruleName
	val ParserRule ruleToCheck

	var inPredicate = false
	var ParserRule ruleToAvoid
	val Set<RuleCall> allVisited = newLinkedHashSet()
	val Set<RuleCall> currentlyVisiting = newLinkedHashSet()

	@ParametersProvider(name='{0}')
	static def Provider<Iterable<Object[]>> getRules() {
		return new Provider<Iterable<Object[]>>() {
			@Inject
			N4JSGrammarAccess grammarAccess
			override get() {
				GrammarUtil.allParserRules(grammarAccess.grammar).filter[r|!GREEDY_RULES.contains(r.name)].map[ val Object[] arr = #[name, it] return arr ]
			}
		}
	}

	@Test
	def void testExpressionEnclosedInPredicate() {
		Assume.assumeTrue(ruleToCheck !== grammarAccess.forStatementRule)
		Assume.assumeTrue(ruleToCheck !== grammarAccess.shiftExpressionRule)

		ruleToAvoid = grammarAccess.expressionRule
		ruleToCheck.alternatives.visit
	}

	@Test
	def void testSemiEnclosedInPredicate() {
		Assume.assumeTrue(ruleToCheck !== grammarAccess.forStatementRule)
		Assume.assumeTrue(ruleToCheck !== grammarAccess.shiftExpressionRule)

		ruleToAvoid = grammarAccess.expressionRule
		ruleToCheck.alternatives.visit
	}

	@Test
	def void testRegExEnclosedInPredicate() {
		Assume.assumeTrue(ruleToCheck !== grammarAccess.forStatementRule)
		Assume.assumeTrue(ruleToCheck !== grammarAccess.shiftExpressionRule)
		Assume.assumeTrue(ruleToCheck !== grammarAccess.annotationListRule)

		ruleToAvoid = grammarAccess.REGEX_LITERALRule
		ruleToCheck.alternatives.visit
	}

	@Test
	def void testTemplateExpressionEnclosedInPredicate_01() {
		Assume.assumeTrue(ruleToCheck !== grammarAccess.forStatementRule)
		Assume.assumeTrue(ruleToCheck !== grammarAccess.shiftExpressionRule)

		ruleToAvoid = grammarAccess.templateMiddleLiteralRule
		ruleToCheck.alternatives.visit
	}

	@Test
	def void testTemplateExpressionEnclosedInPredicate_02() {
		Assume.assumeTrue(ruleToCheck !== grammarAccess.forStatementRule)
		Assume.assumeTrue(ruleToCheck !== grammarAccess.shiftExpressionRule)

		ruleToAvoid = grammarAccess.templateExpressionEndRule
		ruleToCheck.alternatives.visit
	}

	def dispatch void visit(EObject object) {
		// ignore
	}
	def dispatch void visit(AbstractElement element) {
		if (inPredicate && element.predicated) {
			return
		}
		if (element.firstSetPredicated) {
			return
		}
		maintainPredicatedFlag(element.predicated) [
			element.eContents.forEach[ visit ]
		]
	}
	def void maintainPredicatedFlag(boolean predicated, ()=>void runMe) {
		val wasInPredicate = inPredicate
		inPredicate = inPredicate || predicated
		try {
			runMe.apply
		} finally {
			inPredicate = wasInPredicate
		}
	}
	def dispatch void visit(RuleCall call) {
		if (call.firstSetPredicated) {
			return
		}
		if (call.predicated != inPredicate) {
			if (allVisited.add(call)) {
				try {
					currentlyVisiting.add(call)
					if (inPredicate && call.rule === ruleToAvoid) {
						Assert.fail('''Predicate in rule «ruleName» encloses «ruleToAvoid.name» via «currentlyVisiting.map[rule.name].toSet.join('\n-> ')»''')
					}
					// we have to follow the call graph if this call is predicated and we are not yet predicated or we are predicated and this call is not predicated
					maintainPredicatedFlag(call.predicated) [
						if (inPredicate) {
							call.rule.visit
						}
					]
				} finally {
					currentlyVisiting.remove(call)
				}
			}
		}
	}
	def dispatch void visit(ParserRule rule) {
		rule.alternatives.visit
	}


}
