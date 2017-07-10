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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ui.contentassist.antlr.N4JSParser;
import org.eclipse.n4js.ui.contentassist.antlr.internal.InternalN4JSParser;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Lists;
import com.google.inject.Injector;

/**
 * A test to ensure that all the methods that are generated into the the N4JS content assist parser are actually present
 * in the InternalN4JSParser.
 */
@RunWith(Parameterized.class)
public class ContentAssistParserSanityTest {

	/**
	 * The name of the method that need to be present in the InternalN4JSParser class.
	 */
	@Parameter
	public String methodName;

	static class DummyParser extends N4JSParser {
		@Override
		protected String getRuleName(AbstractElement element) {
			return super.getRuleName(element);
		}
	}

	/**
	 * Returns test data.
	 */
	@Parameters
	public static Collection<String> methodNames() {
		try {
			N4JSInjectorProvider injectorProvider = new N4JSInjectorProvider();
			Injector injector = injectorProvider.getInjector();
			DummyParser dummyParser = new DummyParser();
			N4JSGrammarAccess grammarAccess = injector.getInstance(N4JSGrammarAccess.class);
			dummyParser.setGrammarAccess(grammarAccess);
			injectorProvider.restoreRegistry();
			dummyParser.getRuleName(null);
			Map<AbstractElement, String> nameMappings = new ReflectExtensions().get(dummyParser, "nameMappings");
			List<String> methodNames = Lists.newArrayList(nameMappings.values());
			Collections.sort(methodNames);
			return methodNames;
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
		Method method = type.getDeclaredMethod(methodName);
		// will throw if missing but just to make it more obvious
		Assert.assertNotNull("method is not null", method);
	}

}
