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
package org.eclipse.n4js.xpect.tests;

import org.eclipse.n4js.N4JSStandaloneTestsModule;
import org.eclipse.n4js.xpect.common.LspCompatibleXpectTestFiles;
import org.eclipse.n4js.xpect.methods.AccessModifierXpectMethod;
import org.eclipse.n4js.xpect.methods.ElementKeywordXpectMethod;
import org.eclipse.n4js.xpect.methods.FindReferencesXpectMethod;
import org.eclipse.n4js.xpect.methods.FlowgraphsXpectMethod;
import org.eclipse.n4js.xpect.methods.FormatterXpectMethod;
import org.eclipse.n4js.xpect.methods.TypeXpectMethod;
import org.eclipse.n4js.xpect.methods.scoping.ScopeXpectMethod;
import org.eclipse.n4js.xpect.validation.suppression.N4JSSuppressIssuesSetup;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.xtext.lib.tests.LinkingTest;
import org.eclipse.xpect.xtext.lib.tests.ResourceDescriptionTest;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 * Xpect test class, also configured in fragment.xml, executes all Xpect tests found in model folder.
 */
@XpectSuiteClasses({
		FlowgraphsXpectMethod.class,
		AccessModifierXpectMethod.class,
		LinkingTest.class,
		TypeXpectMethod.class,
		ScopeXpectMethod.class,
		ResourceDescriptionTest.class,
		ValidationTest.class,
		FormatterXpectMethod.class,
		FindReferencesXpectMethod.class,
		ElementKeywordXpectMethod.class
})
@RunWith(XpectRunner.class)
@LspCompatibleXpectTestFiles(baseDir = "model")
@XpectImport({ N4JSStandaloneTestsModule.class, N4JSSuppressIssuesSetup.class })
public class N4JSXpectTest {
	//
}
