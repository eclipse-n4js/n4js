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
package org.eclipse.n4js.tests.n4JS.extensions;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.scoping.utils.SourceElementExtensions;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class SourceElementExtensionsBenchmarkTest extends Assert {

	static int ITERATIONS = 1;

	static String BENCHMARK = """
			import * from "pr0_0pa0";
			/**
			 * Class pr0_0pa0/Class0
			*/
			module.exports = Class({
			    has : {

			        Class0 field0: {},
			        Class15 field1: {},
			        Class23 field2: {},
			        Class39 field3: {},
			        Class7 field4: {}
			    },
			    methods: {

			        method0: function() {
			            var dummy;
			            var localFieldAccess0: Class0;
			            dummy = localFieldAccess0.field0;
			            var localFieldAccess1: Class15;
			            dummy = localFieldAccess1.field71;
			            var localFieldAccess2: Class23;
			            dummy = localFieldAccess2.field112;
			            var localMethodAccess0: Class0;
			            dummy = localMethodAccess0.method0();
			            var localMethodAccess1: Class15;
			            dummy = localMethodAccess1.method71();
			            if (dummy===null) { dummy=null; }
			            return null;
			        },
			        method1: function() {
			            var dummy;
			            var localFieldAccess0: Class39;
			            dummy = localFieldAccess0.field193;
			            var localFieldAccess1: Class7;
			            dummy = localFieldAccess1.field34;
			            var localFieldAccess2: Class22;
			            dummy = localFieldAccess2.field105;
			            var localMethodAccess0: Class23;
			            dummy = localMethodAccess0.method112();
			            var localMethodAccess1: Class39;
			            dummy = localMethodAccess1.method193();
			            if (dummy===null) { dummy=null; }
			            return null;
			        },
			        method2: function() {
			            var dummy;
			            var localFieldAccess0: Class31;
			            dummy = localFieldAccess0.field151;
			            var localFieldAccess1: Class46;
			            dummy = localFieldAccess1.field227;
			            var localFieldAccess2: Class14;
			            dummy = localFieldAccess2.field68;
			            var localMethodAccess0: Class7;
			            dummy = localMethodAccess0.method34();
			            var localMethodAccess1: Class22;
			            dummy = localMethodAccess1.method105();
			            if (dummy===null) { dummy=null; }
			            return null;
			        },
			        method3: function() {
			            var dummy;
			            var localFieldAccess0: Class29;
			            dummy = localFieldAccess0.field144;
			            var localFieldAccess1: Class38;
			            dummy = localFieldAccess1.field185;
			            var localFieldAccess2: Class53;
			            dummy = localFieldAccess2.field261;
			            var localMethodAccess0: Class31;
			            dummy = localMethodAccess0.method151();
			            var localMethodAccess1: Class46;
			            dummy = localMethodAccess1.method227();
			            if (dummy===null) { dummy=null; }
			            return null;
			        },
			        method4: function() {
			            var dummy;
			            var localFieldAccess0: Class21;
			            dummy = localFieldAccess0.field102;
			            var localFieldAccess1: Class37;
			            dummy = localFieldAccess1.field183;
			            var localFieldAccess2: Class45;
			            dummy = localFieldAccess2.field224;
			            var localMethodAccess0: Class14;
			            dummy = localMethodAccess0.method68();
			            var localMethodAccess1: Class29;
			            dummy = localMethodAccess1.method144();
			            if (dummy===null) { dummy=null; }
			            return null;
			        }
			    }
			});
			""";

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	SourceElementExtensions srcElemExt;

	Script script;
	List<FunctionDefinition> functions;

	@Before
	public void parseBenchmarkFile() {
		try {
			script = parseHelper.parse(BENCHMARK);
			functions = toList(filter(script.eAllContents(), FunctionDefinition.class));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void discardBenchmarkFile() {
		script = null;
		functions = null;
	}

	@Test
	public void testNewImplementation() {
		for (int i = 1; i <= ITERATIONS; i++) {
			Iterable<Object> lists = flatten(map(functions, it -> srcElemExt.collectVisibleIdentifiableElements(it)));
			assertEquals(30, size(lists));
		}
	}
}
