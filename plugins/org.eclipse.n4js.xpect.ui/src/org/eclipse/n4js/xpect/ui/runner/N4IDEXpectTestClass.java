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
package org.eclipse.n4js.xpect.ui.runner;

import org.eclipse.n4js.xpect.methods.AccessModifierXpectMethod;
import org.eclipse.n4js.xpect.methods.FindReferencesXpectMethod;
import org.eclipse.n4js.xpect.methods.FlowgraphsXpectMethod;
import org.eclipse.n4js.xpect.methods.FormatterXpectMethod;
import org.eclipse.n4js.xpect.methods.LinkingXpectMethod;
import org.eclipse.n4js.xpect.methods.TypeXpectMethod;
import org.eclipse.n4js.xpect.methods.scoping.ScopeXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.HyperlinkXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OrganizeImportXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OutlineXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OutputXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.ProposalXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.contentassist.ContentAssistXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.quickfix.QuickFixXpectMethod;
import org.eclipse.n4js.xpect.ui.refactoring.RenameRefactoringXpectMethod;
import org.eclipse.n4js.xpect.ui.runner.N4IDEXpectTestFilesCollector.N4IDEXpectTestURIProvider;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.IXpectURIProvider;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.xtext.lib.tests.ResourceDescriptionTest;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 * Main and only entry point for executing xpect in the product. Uses custom setup {@link N4IDEXpectFileSetup}.
 * Additionally custom files collector {@link N4IDEXpectTestFilesCollector} configures custom {@link IXpectURIProvider}
 * - {@link N4IDEXpectTestURIProvider} that will be used by {@link XpectRunner}
 */
@XpectSuiteClasses({
		AccessModifierXpectMethod.class,
		FindReferencesXpectMethod.class,
		FormatterXpectMethod.class,
		LinkingXpectMethod.class,
		TypeXpectMethod.class,
		ValidationTest.class,
		ScopeXpectMethod.class,
		HyperlinkXpectMethod.class,
		OrganizeImportXpectMethod.class,
		OutlineXpectMethod.class,
		OutputXpectMethod.class,
		ProposalXpectMethod.class,
		ContentAssistXpectMethod.class,
		QuickFixXpectMethod.class,
		ResourceDescriptionTest.class,
		FlowgraphsXpectMethod.class,
		RenameRefactoringXpectMethod.class
})

@RunWith(XpectRunner.class)
@XpectImport({ N4IDEXpectFileSetup.class })
@N4IDEXpectTestFilesCollector
public class N4IDEXpectTestClass {
	// noop
}
