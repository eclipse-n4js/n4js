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
package org.eclipse.n4js.flowgraphs.ui.tests;

import org.eclipse.n4js.xpect.common.N4JSXpectRunner;
import org.eclipse.n4js.xpect.methods.FindReferencesXpectMethod;
import org.eclipse.n4js.xpect.methods.FlowgraphsXpectMethod;
import org.eclipse.n4js.xpect.methods.TypeXpectMethod;
import org.eclipse.n4js.xpect.methods.scoping.ScopeXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OutputXpectMethod;
import org.eclipse.n4js.xpect.validation.suppression.N4JSSuppressIssuesSetup;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.xtext.lib.tests.LinkingTest;
import org.eclipse.xpect.xtext.lib.tests.ResourceDescriptionTest;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 * Plugin linking test
 */
@XpectSuiteClasses({
		FlowgraphsXpectMethod.class,
		LinkingTest.class,
		TypeXpectMethod.class,
		ScopeXpectMethod.class,
		ResourceDescriptionTest.class,
		ValidationTest.class,
		OutputXpectMethod.class,
		FindReferencesXpectMethod.class
})
@RunWith(N4JSXpectRunner.class)
@XpectTestFiles(baseDir = "model")
@XpectImport({ N4JSSuppressIssuesSetup.class })
public class N4JSXFlowgraphPluginTest {
	//
}
