/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.realworld

/**
 * This practical implementation for the test expectation of #MultipleSingletonPluginTest makes use of xtend features.
 */
class MultiSingletonExpectation {

//  this is the old test expectation (when this was an Eclipse-based pluginUI test)
/*
	static def String get() '''
		Found multiple instances for 10 singleton classes:
		Singleton 'org.eclipse.n4js.common.unicode.services.UnicodeGrammarAccess' has 4 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
			- Regex-Injector
			- Types-Injector
		Singleton 'org.eclipse.n4js.ts.scoping.builtin.BasicResourceSetProvider' has 2 instances that have the following injectors:
			- N4JS-Injector
			- Types-Injector
		Singleton 'org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess' has 2 instances that have the following injectors:
			- N4JS-Injector
			- Types-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedContextFinder' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedContextPDAProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedContextTypePDAProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedGrammarConstraintProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedGrammarPDAProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedSemanticSequencerNfaProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedSyntacticSequencerPDAProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
	'''
*/

	// WARNING: this test expectation includes some cases that are probably problematic! (esp. QueuedExecutorService, ConcurrentIndex)
	// TODO GH-2061 investigate the new duplicates (compare to old test expectation above)

	static def String get() '''
		Found multiple instances for 19 singleton classes:
		Singleton 'org.eclipse.n4js.common.unicode.services.UnicodeGrammarAccess' has 4 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
			- Regex-Injector
			- Types-Injector
		Singleton 'org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar' has 2 instances that have the following injectors:
			- N4JS-Injector
			- Types-Injector
		Singleton 'org.eclipse.n4js.ts.scoping.builtin.ConfiguredResourceSetProvider' has 2 instances that have the following injectors:
			- N4JS-Injector
			- Types-Injector
		Singleton 'org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInSchemeProvider' has 2 instances that have the following injectors:
			- N4JS-Injector
			- Types-Injector
		Singleton 'org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess' has 2 instances that have the following injectors:
			- N4JS-Injector
			- Types-Injector
		Singleton 'org.eclipse.n4js.workspace.WorkspaceAccess' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.ide.server.ProjectStatePersisterConfig' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.ide.server.QueuedExecutorService' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.ide.server.build.ConcurrentIndex' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.ide.server.build.ProjectStatePersister' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.ide.server.build.XURIBasedFileSystemAccessFactory' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.ide.server.issues.PublishingIssueAcceptor' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedContextFinder' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedContextPDAProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedContextTypePDAProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedGrammarConstraintProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedGrammarPDAProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedSemanticSequencerNfaProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
		Singleton 'org.eclipse.n4js.xtext.serializer.SynchronizedSyntacticSequencerPDAProvider' has 2 instances that have the following injectors:
			- JSON-Injector
			- N4JS-Injector
	'''
}
