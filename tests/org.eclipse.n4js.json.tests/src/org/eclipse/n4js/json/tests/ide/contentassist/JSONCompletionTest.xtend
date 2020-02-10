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

class JSONCompletionTest extends AbstractLanguageServerTest {
	new() {
		super("json")
	}

	@Test def void testKeywords() {
		testCompletion [ 
			model = '{ "unknownAttribute" : }'
			column = model.length - 1
			expectedCompletionItems = '''
				false -> false [[0, 23] .. [0, 23]]
				null -> null [[0, 23] .. [0, 23]]
				true -> true [[0, 23] .. [0, 23]]
			'''
		]
	}
	
	@Test def void testNameValuePairs_01() {
		testCompletion [ 
			model = '{}'
			column = 1
			expectedCompletionItems = '''
				dependencies (Dependencies of this npm) -> "dependencies": {
				    $1
				}$0 [[0, 1] .. [0, 1]]
				devDependencies (Development dependencies of this npm) -> "devDependencies": {
				    $1
				}$0 [[0, 1] .. [0, 1]]
				main (Main module. Path is relative to package root) -> "main": "$1"$0 [[0, 1] .. [0, 1]]
				n4js (N4JS section) -> "n4js": {
				    $1
				}$0 [[0, 1] .. [0, 1]]
				name (Npm name) -> "name": "$1"$0 [[0, 1] .. [0, 1]]
				version (Npm semver version) -> "version": "$1"$0 [[0, 1] .. [0, 1]]
				workspaces (Projects that are members of the yarn workspace) -> "workspaces": [
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
				devDependencies (Development dependencies of this npm) -> "devDependencies": {
				    $1
				}$0 [[3, 1] .. [3, 1]]
				n4js (N4JS section) -> "n4js": {
				    $1
				}$0 [[3, 1] .. [3, 1]]
				name (Npm name) -> "name": "$1"$0 [[3, 1] .. [3, 1]]
				version (Npm semver version) -> "version": "$1"$0 [[3, 1] .. [3, 1]]
				workspaces (Projects that are members of the yarn workspace) -> "workspaces": [
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
				definesPackage -> "definesPackage": "$1"$0 [[4, 2] .. [4, 2]]
				extendedRuntimeEnvironment -> "extendedRuntimeEnvironment": "$1"$0 [[4, 2] .. [4, 2]]
				implementationId -> "implementationId": "$1"$0 [[4, 2] .. [4, 2]]
				implementedProjects -> "implementedProjects": [
				    $1
				]$0 [[4, 2] .. [4, 2]]
				mainModule (Main module specifier. Starts from source folder(s)) -> "mainModule": "$1"$0 [[4, 2] .. [4, 2]]
				moduleFilters -> "moduleFilters": {
				    $1
				}$0 [[4, 2] .. [4, 2]]
				noValidate -> "noValidate": {
				    $1
				}$0 [[4, 2] .. [4, 2]]
				output (Output folder. Default is '.') -> "output": "$1"$0 [[4, 2] .. [4, 2]]
				projectType (plainjs) -> "projectType": "$1"$0 [[4, 2] .. [4, 2]]
				providedRuntimeLibraries -> "providedRuntimeLibraries": [
				    $1
				]$0 [[4, 2] .. [4, 2]]
				requiredRuntimeLibraries -> "requiredRuntimeLibraries": [
				    $1
				]$0 [[4, 2] .. [4, 2]]
				sources (Source folders) -> "sources": {
				    $1
				}$0 [[4, 2] .. [4, 2]]
				testedProjects (Projects that are tested by this project) -> "testedProjects": [
				    $1
				]$0 [[4, 2] .. [4, 2]]
				vendorId -> "vendorId": "$1"$0 [[4, 2] .. [4, 2]]
				vendorName -> "vendorName": "$1"$0 [[4, 2] .. [4, 2]]
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
				"definesPackage" -> "definesPackage": "$1"$0 [[4, 2] .. [4, 8]]
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
				"definesPackage" -> "definesPackage": "$1"$0 [[4, 2] .. [4, 7]]
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
				<array> (Generic name array pair) -> "${1:name}": [
				    $2
				]$0 [[2, 2] .. [2, 2]]
				<object> (Generic name object pair) -> "${1:name}": {
				    $2
				}$0 [[2, 2] .. [2, 2]]
				<value> (Generic name value pair) -> "${1:name}": "$2"$0 [[2, 2] .. [2, 2]]
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
				false -> false [[2, 2] .. [2, 2]]
				null -> null [[2, 2] .. [2, 2]]
				true -> true [[2, 2] .. [2, 2]]
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
				"*" -> "*" [[2, 13] .. [2, 13]]
				false -> false [[2, 13] .. [2, 13]]
				null -> null [[2, 13] .. [2, 13]]
				true -> true [[2, 13] .. [2, 13]]
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
				"api" -> "api" [[2, 17] .. [2, 20]]
				"application" -> "application" [[2, 17] .. [2, 20]]
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
				"api" -> "api" [[2, 17] .. [2, 17]]
				"application" -> "application" [[2, 17] .. [2, 17]]
				"definition" -> "definition" [[2, 17] .. [2, 17]]
				"library" -> "library" [[2, 17] .. [2, 17]]
				"plainjs" -> "plainjs" [[2, 17] .. [2, 17]]
				"processor" -> "processor" [[2, 17] .. [2, 17]]
				"runtimeEnvironment" -> "runtimeEnvironment" [[2, 17] .. [2, 17]]
				"runtimeLibrary" -> "runtimeLibrary" [[2, 17] .. [2, 17]]
				"test" -> "test" [[2, 17] .. [2, 17]]
				"validation" -> "validation" [[2, 17] .. [2, 17]]
				false -> false [[2, 17] .. [2, 17]]
				null -> null [[2, 17] .. [2, 17]]
				true -> true [[2, 17] .. [2, 17]]
			'''
		]
	}
}
