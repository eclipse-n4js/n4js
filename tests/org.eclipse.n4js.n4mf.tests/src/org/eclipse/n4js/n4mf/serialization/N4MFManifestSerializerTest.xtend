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
package org.eclipse.n4js.n4mf.serialization

import com.google.inject.Inject
import org.eclipse.n4js.n4mf.N4MFInjectorProvider
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.n4mf.resource.ManifestMerger
import org.eclipse.n4js.n4mf.ModuleLoader

/**
 * Basic unit tests with regard to {@link N4MFManifestSerializer}.
 */
@RunWith(XtextRunner)
@InjectWith(N4MFInjectorProvider)
class N4MFManifestSerializerTest extends Assert {
	
	@Inject private extension ParseHelper<ProjectDescription> parseHelper;
	@Inject private N4MFManifestSerializer serializer;
	@Inject private ManifestMerger merger;
	
	/** 
	 * Parses a minimum manifest file and serializes it.
	 * Asserts that the serialization result equals the initial manifest code.
	 */
	@Test
	public def void testMinimalManifest() {
		val manifestCode = '''
		ProjectId: B
		ProjectType: library
		ProjectVersion: 0.0.1-abc
		VendorId: org.eclipse.n4js
		ModuleLoader: commonjs
		'''
		
		val description = manifestCode.parse;
		
		assertEquals("The serialized project description equals the original manifest.",
			manifestCode, serializer.serialize(description))
	}
	
	/** 
	 * Parses a medium-sized manifest file and serializes it.
	 * Asserts that the serialization result equals the initial manifest code.
	 */
	@Test
	public def void testMediumManifest() {
		val manifestCode = '''
		ProjectId: B
		ProjectType: library
		ProjectVersion: 0.0.1-abc
		VendorId: org.eclipse.n4js
		VendorName: "a b c"
		MainModule: "a/b/c"
		ExtendedRuntimeEnvironment: n4js-es5
		ProvidedRuntimeLibraries {
			n4js-runtime-mangelhaft,
			n4js-runtime-fetch
		}
		ImplementationId: abc.d
		ImplementedProjects {
			A,
			C
		}
		InitModules {
			"*/aaa" in "src",
			"1830abc"
		}
		Output: "src-out"
		Libraries {
			"a",
			"folder"
		}
		Sources {
			external {
				"src-ext"
			}
			source {
				"src",
				"src2"
			}
			test {
				"src-test",
				"second-test"
			}
		}
		TestedProjects {
			A,
			C 0.0.1,
			D 0.1,
			E 0.1.1,
			F 0.1.1-a,
			G [0.1.1-a, 0.2),
			H [0.1.1-a, 2],
			L (0.1.1-a],
			I (0.1.1-a, 12-a],
			J [0.1.1-a, 1)
		}
		ModuleLoader: commonjs
		'''
		
		val description = manifestCode.parse;
		
		assertEquals("The serialized project description equals the original manifest.",
			manifestCode, serializer.serialize(description))
	}
	
	/** 
	 * Parses a large manifest file that configures a maximum of manifest options and serializes it.
	 * Asserts that the serialization result equals the initial manifest code.
	 */
	@Test
	public def void testLargeManifest() {
		val manifestCode = '''
		ProjectId: B
		ProjectType: library
		ProjectVersion: 0.0.1-abc
		VendorId: org.eclipse.n4js
		VendorName: "a b c"
		MainModule: "a/b/c"
		ExtendedRuntimeEnvironment: n4js-es5
		ProvidedRuntimeLibraries {
			n4js-runtime-mangelhaft,
			n4js-runtime-fetch
		}
		RequiredRuntimeLibraries {
			n4js-runtime-es2015
		}
		ProjectDependencies {
			A,
			C 0,
			D 0.1,
			E 0.1.1,
			F 0.1.1-a,
			G [0.1.1-a, 0.2),
			H [0.1.1-a, 2],
			H 0.1.1-a,
			I (0.1.1-a, 12-a],
			J [0.1.1-a, 1)
		}
		ImplementationId: abc.d
		ImplementedProjects {
			A,
			C
		}
		InitModules {
			"*/aaa" in "src",
			"1830abc"
		}
		ExecModule: "abc" in "src"
		Output: "src-out"
		Libraries {
			"a",
			"folder"
		}
		Resources {
			"a",
			"b",
			"folder"
		}
		Sources {
			external {
				"src-ext"
			}
			source {
				"src",
				"src2"
			}
			test {
				"src-test",
				"second-test"
			}
		}
		ModuleFilters {
			noModuleWrap {
				"src/1",
				"src/2" in "src2"
			}
			noValidate {
				"src-ext",
				"src-test/*" in "src-test"
			}
		}
		TestedProjects {
			A,
			C 0.0.1,
			D 0.1,
			E 0.1.1,
			F 0.1.1-a,
			G [0.1.1-a, 0.2),
			H [0.1.1-a, 2],
			L (0.1.1-a],
			I (0.1.1-a, 12-a],
			J [0.1.1-a, 1)
		}
		ModuleLoader: commonjs
		''';
		
		val description = manifestCode.parse;
		
		assertEquals("The serialized project description equals the original manifest.",
			manifestCode, serializer.serialize(description))
	}
	
	/** Merges two manifest (fragments) and checks for the serialized result of the merge. */
	@Test
	public def testSerializeMergeResult() {
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
		
		val expectation = '''
		ProjectType: application
		RequiredRuntimeLibraries {
			runtime.nodejs,
			runtime.fetch,
			runtime.es6
		}
		ProjectDependencies {
			express,
			org.eclipse.n4js.mangelhaft,
			eu.numberfour.mangelhaft.reporter.console
		}
		'''
		
		assertEquals("The serialized project description equals the expected result of a manifest merge.",
			expectation, serializer.serialize(result))
	}
	
	/** Merges two manifest (fragments) and checks for the serialized result of the merge. */
	@Test
	public def testSerializeManifestMergeResult2() {
		val from = parseHelper.parse('''
			ProjectType: library
			ProjectDependencies {
			    express,
			    react
			}
		''').eResource;

		val to = parseHelper.parse('''
			ModuleLoader: «ModuleLoader.COMMONJS.toString.toLowerCase»
			
			RequiredRuntimeLibraries {
			    runtime.nodejs
			}
		''').eResource;

		val result = merger.mergeContent(from, to);
		
		val expectation = '''
		ProjectType: library
		RequiredRuntimeLibraries {
			runtime.nodejs
		}
		ProjectDependencies {
			express,
			react
		}
		ModuleLoader: commonjs
		'''
		
		assertEquals("The serialized project description equals the expected result of a manifest merge.",
			expectation, serializer.serialize(result))
	}
}