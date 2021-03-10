/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.tests.ide.contentassist

import org.eclipse.xtext.testing.AbstractLanguageServerTest
import org.junit.Test
import org.eclipse.lsp4j.CompletionItem

// FIXME: GH-2045: Tests in this bundle seem to use plain Xtext test infrastructure that differs from ours.
// This results (sometimes) in wrong results. E.g.: The result of testNameValuePairs_01 is incomplete.
class JSONCompletionTest extends AbstractLanguageServerTest {
	new() {
		super("json")
	}
	
	override protected _toExpectation(CompletionItem it) {
		return '''[«kind?.name», «insertTextFormat?.name?:'PlainText'»] «super._toExpectation(it)»'''
	}

	@Test def void testKeywords() {
		testCompletion [ 
			model = '{ "unknownAttribute" : }'
			column = model.length - 1
			expectedCompletionItems = '''
				[Keyword, PlainText] false -> false [[0, 23] .. [0, 23]]
				[Keyword, PlainText] null -> null [[0, 23] .. [0, 23]]
				[Keyword, PlainText] true -> true [[0, 23] .. [0, 23]]
			'''
		]
	}
	
	@Test def void testNameValuePairs_01() {
		testCompletion [ 
			model = '{}'
			column = 1
			expectedCompletionItems = '''
				[Class, Snippet] dependencies (Dependencies of this npm) -> "dependencies": {
				    $1
				}$0 [[0, 1] .. [0, 1]]
				[Class, Snippet] devDependencies (Development dependencies of this npm) -> "devDependencies": {
				    $1
				}$0 [[0, 1] .. [0, 1]]
				[Property, Snippet] main (Main module. Path is relative to package root) -> "main": "$1"$0 [[0, 1] .. [0, 1]]
				[Class, Snippet] n4js (N4JS section) -> "n4js": {
				    $1
				}$0 [[0, 1] .. [0, 1]]
				[Property, Snippet] name (Npm name) -> "name": "$1"$0 [[0, 1] .. [0, 1]]
				[Property, Snippet] version (Npm semver version) -> "version": "$1"$0 [[0, 1] .. [0, 1]]
				[Value, Snippet] workspaces (Array of projects names or glob that are members of the yarn workspace) -> "workspaces": [
				    $1
				]$0 [[0, 1] .. [0, 1]]
			'''
		]
	}
	
	@Test def void testNameValuePairs_02() {
		testCompletion [ 
			model = '''
				{
					"main": "index.js",
					"dependencies": {},
					
				}
			'''
			column = 1
			line = 3
			expectedCompletionItems = '''
				[Class, Snippet] devDependencies (Development dependencies of this npm) -> "devDependencies": {
				    $1
				}$0 [[3, 1] .. [3, 1]]
				[Class, Snippet] n4js (N4JS section) -> "n4js": {
				    $1
				}$0 [[3, 1] .. [3, 1]]
				[Property, Snippet] name (Npm name) -> "name": "$1"$0 [[3, 1] .. [3, 1]]
				[Property, Snippet] version (Npm semver version) -> "version": "$1"$0 [[3, 1] .. [3, 1]]
				[Value, Snippet] workspaces (Array of projects names or glob that are members of the yarn workspace) -> "workspaces": [
				    $1
				]$0 [[3, 1] .. [3, 1]]
			'''
		]
	}
	
	@Test def void testNameValuePairs_03() {
		testCompletion [ 
			model = '''
				{
					"main": "index.js",
					"dependencies": {},
					"n4js": {
						
					}
				}
			'''
			column = 2
			line = 4
			expectedCompletionItems = '''
				[Property, Snippet] definesPackage -> "definesPackage": "$1"$0 [[4, 2] .. [4, 2]]
				[Property, Snippet] extendedRuntimeEnvironment -> "extendedRuntimeEnvironment": "$1"$0 [[4, 2] .. [4, 2]]
				[Property, Snippet] implementationId -> "implementationId": "$1"$0 [[4, 2] .. [4, 2]]
				[Value, Snippet] implementedProjects -> "implementedProjects": [
				    $1
				]$0 [[4, 2] .. [4, 2]]
				[Property, Snippet] mainModule (Main module specifier. Starts from source folder(s)) -> "mainModule": "$1"$0 [[4, 2] .. [4, 2]]
				[Class, Snippet] moduleFilters -> "moduleFilters": {
				    $1
				}$0 [[4, 2] .. [4, 2]]
				[Class, Snippet] noValidate -> "noValidate": {
				    $1
				}$0 [[4, 2] .. [4, 2]]
				[Property, Snippet] output (Output folder. Default is '.') -> "output": "$1"$0 [[4, 2] .. [4, 2]]
				[Property, Snippet] projectType (project type) -> "projectType": "$1"$0 [[4, 2] .. [4, 2]]
				[Value, Snippet] providedRuntimeLibraries -> "providedRuntimeLibraries": [
				    $1
				]$0 [[4, 2] .. [4, 2]]
				[Value, Snippet] requiredRuntimeLibraries -> "requiredRuntimeLibraries": [
				    $1
				]$0 [[4, 2] .. [4, 2]]
				[Class, Snippet] sources (Source folders) -> "sources": {
				    $1
				}$0 [[4, 2] .. [4, 2]]
				[Value, Snippet] testedProjects (Projects that are tested by this project) -> "testedProjects": [
				    $1
				]$0 [[4, 2] .. [4, 2]]
				[Property, Snippet] vendorId -> "vendorId": "$1"$0 [[4, 2] .. [4, 2]]
				[Property, Snippet] vendorName -> "vendorName": "$1"$0 [[4, 2] .. [4, 2]]
			'''
		]
	}
	
	@Test def void testNameValuePairs_04() {
		testCompletion [ 
			model = '''
				{
					"main": "index.js",
					"dependencies": {},
					"n4js": {
						"defin"
					}
				}
			'''
			column = 8
			line = 4
			expectedCompletionItems = '''
				[Property, Snippet] "definesPackage" -> "definesPackage": "$1"$0 [[4, 2] .. [4, 8]]
			'''
		]
	}
	
	@Test def void testNameValuePairs_05() {
		testCompletion [ 
			model = '''
				{
					"main": "index.js",
					"dependencies": {},
					"n4js": {
						"defin"
					}
				}
			'''
			column = 7
			line = 4
			expectedCompletionItems = '''
				[Property, Snippet] "definesPackage" -> "definesPackage": "$1"$0 [[4, 2] .. [4, 7]]
			'''
		]
	}
	
	@Test def void testInObjectLiteral_01() {
		testCompletion [ 
			model = '''
				{
					"dependencies": {
						
					}
				}
			'''
			column = 2
			line = 2
			expectedCompletionItems = '''
				[Value, Snippet] <array> (Generic name array pair) -> "${1:name}": [
				    $2
				]$0 [[2, 2] .. [2, 2]]
				[Class, Snippet] <object> (Generic name object pair) -> "${1:name}": {
				    $2
				}$0 [[2, 2] .. [2, 2]]
				[Property, Snippet] <value> (Generic name value pair) -> "${1:name}": "$2"$0 [[2, 2] .. [2, 2]]
			'''
		]
	}
	
	@Test def void testInArray_01() {
		testCompletion [ 
			model = '''
				{
					"dependencies": [
						
					]
				}
			'''
			column = 2
			line = 2
			expectedCompletionItems = '''
				[Keyword, PlainText] false -> false [[2, 2] .. [2, 2]]
				[Keyword, PlainText] null -> null [[2, 2] .. [2, 2]]
				[Keyword, PlainText] true -> true [[2, 2] .. [2, 2]]
			'''
		]
	}
	
	@Test def void testInDependencies_01() {
		testCompletion [ 
			model = '''
				{
					"dependencies": {
						"unknown": 
					}
				}
			'''
			column = '		"unknown": '.length
			line = 2
			expectedCompletionItems = '''
				[Value, PlainText] "*" -> "*" [[2, 13] .. [2, 13]]
				[Keyword, PlainText] false -> false [[2, 13] .. [2, 13]]
				[Keyword, PlainText] null -> null [[2, 13] .. [2, 13]]
				[Keyword, PlainText] true -> true [[2, 13] .. [2, 13]]
			'''
		]
	}
	
	@Test def void testProjectType_01() {
		testCompletion [ 
			model = '''
				{
					"n4js": {
						"projectType": "api"
					}
				}
			'''
			column = '		"projectType": "api": '.length - 4
			line = 2
			expectedCompletionItems = '''
				[Keyword, PlainText] "api" -> "api" [[2, 17] .. [2, 20]]
				[Keyword, PlainText] "application" -> "application" [[2, 17] .. [2, 20]]
			'''
		]
	}
	
	@Test def void testProjectType_02() {
		testCompletion [ 
			model = '''
				{
					"n4js": {
						"projectType": 
					}
				}
			'''
			column = '		"projectType": '.length
			line = 2
			expectedCompletionItems = '''
				[Keyword, PlainText] "api" -> "api" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] "application" -> "application" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] "definition" -> "definition" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] "library" -> "library" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] "plainjs" -> "plainjs" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] "processor" -> "processor" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] "runtimeEnvironment" -> "runtimeEnvironment" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] "runtimeLibrary" -> "runtimeLibrary" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] "test" -> "test" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] "validation" -> "validation" [[2, 17] .. [2, 17]]
				[Keyword, PlainText] false -> false [[2, 17] .. [2, 17]]
				[Keyword, PlainText] null -> null [[2, 17] .. [2, 17]]
				[Keyword, PlainText] true -> true [[2, 17] .. [2, 17]]
			'''
		]
	}
}
