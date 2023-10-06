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
package org.eclipse.n4js.transpiler.es.tests;

import java.util.regex.Pattern;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AT_IDE_2004_YieldTest extends AbstractTranspilerTest {

	/**
	 * This test checks extra parenthesis around the yield expression. c.f. IDE-2004
	 */
	@Test
	public void test_CompileArrowToYield_check_extra_parenthesis() throws Throwable {

		String script = " var x = async()=>{ return 45; } ";

		// Prepare ResourceSet to contain exportedScript:
		ResourceSet resSet = installExportedScript();

		Script scriptNode = parseHelper.parse(script, toTestProjectURI("A.n4js"), resSet);
		resolveLazyRefs(scriptNode);

		// \\h is a horizontal white-space
		Pattern pattern = Pattern.compile("\\(\\h*yield\\h*45\\h*\\)");

		// Changed behavior in GHOLD-93 to not create a yield for simple return statements.
		assertCompileResultDoesNotMatch(scriptNode, pattern);
	}

}
