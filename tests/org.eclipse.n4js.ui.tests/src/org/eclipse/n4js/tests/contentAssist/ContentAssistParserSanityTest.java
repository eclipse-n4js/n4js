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
package org.eclipse.n4js.tests.contentAssist;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.ide.contentassist.antlr.N4JSParser;
import org.eclipse.n4js.ide.contentassist.antlr.internal.InternalN4JSParser;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;
import org.eclipse.xtext.xtext.FlattenedGrammarAccess;
import org.eclipse.xtext.xtext.RuleFilter;
import org.eclipse.xtext.xtext.RuleNames;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Injector;

/**
 * A test to ensure that all the methods that are generated into the the N4JS content assist parser are actually present
 * in the InternalN4JSParser.
 */
@SuppressWarnings("restriction")
@RunWith(Parameterized.class)
public class ContentAssistParserSanityTest {

	/**
	 * The name of the method that need to be present in the InternalN4JSParser class.
	 */
	@Parameter(0)
	public String methodName;

	/**
	 * All rules that are supposed to be present in the generated parser class
	 */
	@Parameter(1)
	public Set<String> flattenedRuleNames;

	static class DummyParser extends N4JSParser {
		@Override
		protected String getRuleName(AbstractElement element) {
			return super.getRuleName(element);
		}
	}

	/**
	 * Returns test data.
	 */
	@Parameters(name = "{0}")
	public static Collection<Object[]> methodNames() {
		try {
			N4JSInjectorProvider injectorProvider = new N4JSInjectorProvider();
			Injector injector = injectorProvider.getInjector();
			DummyParser dummyParser = new DummyParser();
			N4JSParser.NameMappings nameMappings = injector.getInstance(N4JSParser.NameMappings.class);
			dummyParser.setNameMappings(nameMappings);
			N4JSGrammarAccess grammarAccess = injector.getInstance(N4JSGrammarAccess.class);
			RuleNames ruleNames = RuleNames.getRuleNames(grammarAccess.getGrammar(), false);
			RuleFilter ruleFilter = new RuleFilter();
			ruleFilter.setDiscardUnreachableRules(true);
			FlattenedGrammarAccess flattened = new FlattenedGrammarAccess(ruleNames, ruleFilter);
			Grammar flattenedGrammar = flattened.getFlattenedGrammar();
			dummyParser.setGrammarAccess(grammarAccess);
			injectorProvider.restoreRegistry();
			dummyParser.getRuleName(null);
			Map<AbstractElement, String> actualNameMappings = new ReflectExtensions().get(nameMappings, "mappings");
			List<String> methodNames = Lists.newArrayList(actualNameMappings.values());
			Collections.sort(methodNames);
			Set<String> flattenedRuleNames = Sets
					.newHashSet(Lists.transform(flattenedGrammar.getRules(), r -> r.getName()));
			return Lists.transform(methodNames, (s) -> new Object[] { s, flattenedRuleNames });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Tests if the method is actually present in the parser.
	 */
	@Test
	public void testMethodExists() throws Exception {
		Class<?> type = InternalN4JSParser.class;
		try {
			Method method = type.getDeclaredMethod(methodName);
			// will throw if missing but just to make it more obvious
			Assert.assertNotNull("method is not null", method);
		} catch (NoSuchMethodException e) {
			String withoutPrefix = methodName.substring("rule__".length());
			String withoutSuffix = withoutPrefix.substring(0, withoutPrefix.indexOf('_'));
			String flattenedRuleName = "rule" + withoutSuffix;
			// this is a dirty hack, but good enough for now to ensure that it breaks in case of incompatible grammar
			// changes
			switch (flattenedRuleName) {
			case "ruleVariableBinding":
				Assert.assertTrue("methodName " + methodName + " points to a never called rule",
						flattenedRuleNames.containsAll(Arrays.asList("norm1_VariableBinding", "norm2_VariableBinding",
								"norm3_VariableBinding", "norm4_VariableBinding", "norm6_VariableBinding")));
				Assert.assertFalse("methodName " + methodName + " points to a never called rule",
						flattenedRuleNames.contains("norm5_VariableBinding"));
				Assert.assertFalse("methodName " + methodName + " points to a never called rule",
						flattenedRuleNames.contains("norm7_VariableBinding"));
				Assert.assertFalse("methodName " + methodName + " points to a never called rule",
						flattenedRuleNames.contains("norm8_VariableBinding"));
				break;
			case "ruleFunctionBody":
				Assert.assertTrue("methodName " + methodName + " points to a never called rule",
						flattenedRuleNames.containsAll(Arrays.asList("norm1_FunctionBody", "norm2_FunctionBody",
								"norm3_FunctionBody")));
				Assert.assertFalse("methodName " + methodName + " points to a never called rule",
						flattenedRuleNames.contains("norm4_ruleFunctionBody"));
				break;
			default:
				Assert.assertFalse("methodName " + methodName + " points to a never called rule",
						flattenedRuleNames.contains(flattenedRuleName));
			}
		}
	}

}
