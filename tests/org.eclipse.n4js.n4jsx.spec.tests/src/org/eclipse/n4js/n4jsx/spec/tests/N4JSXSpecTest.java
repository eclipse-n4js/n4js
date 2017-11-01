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
package org.eclipse.n4js.n4jsx.spec.tests;

import org.eclipse.n4js.N4JSStandaloneTestsModule;
import org.eclipse.n4js.xpect.methods.AccessModifierXpectMethod;
import org.eclipse.n4js.xpect.methods.FindReferencesXpectMethod;
import org.eclipse.n4js.xpect.methods.FormatterXpectMethod;
import org.eclipse.n4js.xpect.methods.ReturnXpectMethod;
import org.eclipse.n4js.xpect.methods.TypeXpectMethod;
import org.eclipse.n4js.xpect.methods.scoping.ScopeXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OutputXpectMethod;
import org.eclipse.n4js.xpect.validation.suppression.SuppressIssuesSetup;
import org.junit.runner.RunWith;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.runner.XpectTestFiles.FileRoot;
import org.eclipse.xpect.xtext.lib.tests.LinkingTest;
import org.eclipse.xpect.xtext.lib.tests.ResourceDescriptionTest;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;

/**
 * Xpect test class, to be executed as plain JUnit test.
 */
@XpectSuiteClasses({
		AccessModifierXpectMethod.class,
		LinkingTest.class,
		ReturnXpectMethod.class,
		TypeXpectMethod.class,
		ScopeXpectMethod.class,
		ResourceDescriptionTest.class,
		ValidationTest.class,
		FormatterXpectMethod.class,
		FindReferencesXpectMethod.class,
		OutputXpectMethod.class
})
@RunWith(XpectRunner.class)
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "xpectTests")
@XpectImport({N4JSStandaloneTestsModule.class, SuppressIssuesSetup.class})
public class N4JSXSpecTest {
	//
}
