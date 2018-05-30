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
package org.eclipse.n4js.n4mf.resource

import com.google.inject.Inject
import org.eclipse.n4js.n4mf.ModuleLoader
import org.eclipse.n4js.n4mf.N4MFInjectorProvider
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test for the N4JS manifest merger.
 */
@RunWith(XtextRunner)
@InjectWith(N4MFInjectorProvider)
class ManifestMergerTest extends Assert {

	@Inject
	ManifestMerger merger;

	@Inject
	ParseHelper<ProjectDescription> parseHelper;

	@Test
	def void testMergerReferencesWithManyFeature() {
		val from = parseHelper.parse('''
			ProjectDependencies {
			    org.eclipse.n4js.mangelhaft,
			    eu.numberfour.mangelhaft.reporter.console
			}
			
			RequiredRuntimeLibraries {
			    runtime.fetch,
			    runtime.es6
			}
		''').eResource;

		val to = parseHelper.parse('''
			ProjectDependencies {
			    express
			}
			
			RequiredRuntimeLibraries {
			    runtime.nodejs
			}
		''').eResource;

		val result = merger.mergeContent(from, to);

		assertTrue(
			'Expected 3 project dependencies after the merge. Got ' + result.projectDependencies.size + ' instead.',
			result.projectDependencies.size === 3
		);
		assertTrue(
			'Expected 3 required runtime libraries after the merge. Got ' + result.requiredRuntimeLibraries.size +
				' instead.',
			result.requiredRuntimeLibraries.size === 3
		);
	}

	@Test
	def void testMergerReferencesWithExistingManyFeature() {
		val from = parseHelper.parse('''
			ProjectDependencies {
			    express
			}
		''').eResource;

		val to = parseHelper.parse('''
			ProjectDependencies {
			    express
			}
			
			RequiredRuntimeLibraries {
			    runtime.nodejs
			}
		''').eResource;

		val result = merger.mergeContent(from, to);

		assertTrue(
			'Expected 1 project dependency after the merge. Got ' + result.projectDependencies.size + ' instead.',
			result.projectDependencies.size === 1
		);
		assertTrue(
			'Expected 1 required runtime library after the merge. Got ' + result.requiredRuntimeLibraries.size +
				' instead.',
			result.requiredRuntimeLibraries.size === 1
		);
	}

	@Test
	def void testUpdateAttribute() {
		val from = parseHelper.parse('''ProjectId: from.id''');
		val to = parseHelper.parse('''ProjectId: to.id''');
		val result = merger.mergeContent(from.eResource, to.eResource);
		assertTrue('Expected to.id project ID. Was ' + result.projectId + '.instead.',
			result.projectId.equals('from.id'));

	}

	@Test
	def void testMergerIgnoresUnsetDefaultEnums() {
		val from = parseHelper.parse('''
			ProjectDependencies {
			    express
			}
		''').eResource;

		val to = parseHelper.parse('''
			ModuleLoader: «ModuleLoader.COMMONJS.toString.toLowerCase»
			
			RequiredRuntimeLibraries {
			    runtime.nodejs
			}
		''').eResource;

		val result = merger.mergeContent(from, to);

		assertTrue(
			'''Expected unchanged module loader after merge. Expected «ModuleLoader.COMMONJS». Got '«result.moduleLoader»' instead.''',
			result.moduleLoader === ModuleLoader.COMMONJS
		);
	}

}
