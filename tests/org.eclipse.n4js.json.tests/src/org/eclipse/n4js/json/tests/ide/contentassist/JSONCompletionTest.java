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
package org.eclipse.n4js.json.tests.ide.contentassist;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.xtext.testing.AbstractLanguageServerTest;
import org.junit.Test;

// FIXME: GH-2045: Tests in this bundle seem to use plain Xtext test infrastructure that differs from ours.
// This results (sometimes) in wrong results. E.g.: The result of testNameValuePairs_01 is incomplete.
@SuppressWarnings("javadoc")
public class JSONCompletionTest extends AbstractLanguageServerTest {

	public JSONCompletionTest() {
		super("json");
	}

	@Override
	protected String _toExpectation(CompletionItem ci) {
		String kindStr = ci.getKind() == null ? "" : ci.getKind().name();
		String insertTextFormatStr = ci.getInsertTextFormat() == null || ci.getInsertTextFormat().name() == null
				? "PlainText"
				: ci.getInsertTextFormat().name();
		String expct = super._toExpectation(ci);
		return "[%s, %s] %s".formatted(kindStr, insertTextFormatStr, expct);
	}

	@Test
	public void testKeywords() {
		testCompletion((tcc) -> {
			tcc.setModel("{ \"unknownAttribute\" : }");
			tcc.setColumn(tcc.getModel().length() - 1);
			tcc.setExpectedCompletionItems("""
					[Keyword, PlainText] false -> false [[0, 23] .. [0, 23]]
					[Keyword, PlainText] null -> null [[0, 23] .. [0, 23]]
					[Keyword, PlainText] true -> true [[0, 23] .. [0, 23]]
					""");
		});
	}

	@Test
	public void testNameValuePairs_01() {
		testCompletion((tcc) -> {
			tcc.setModel("{}");
			tcc.setColumn(1);
			tcc.setExpectedCompletionItems(
					"""
							[Class, Snippet] dependencies (Dependencies of this npm) -> "dependencies": {
							    $1
							}$0 [[0, 1] .. [0, 1]]
							[Class, Snippet] devDependencies (Development dependencies of this npm) -> "devDependencies": {
							    $1
							}$0 [[0, 1] .. [0, 1]]
							[Class, Snippet] exports (Entry point definitions) -> "exports": {
							    $1
							}$0 [[0, 1] .. [0, 1]]
							[Property, Snippet] main (Main module. Path is relative to package root) -> "main": "$1"$0 [[0, 1] .. [0, 1]]
							[Property, Snippet] module (Like "main" but provides a different file with esm code) -> "module": "$1"$0 [[0, 1] .. [0, 1]]
							[Class, Snippet] n4js (N4JS section) -> "n4js": {
							    $1
							}$0 [[0, 1] .. [0, 1]]
							[Property, Snippet] name (Npm name) -> "name": "$1"$0 [[0, 1] .. [0, 1]]
							[Property, Snippet] type (The module format used for .js files in this project. Either 'commonjs' or 'module'.) -> "type": "$1"$0 [[0, 1] .. [0, 1]]
							[Property, Snippet] types (Type module. (TypesScript) Path is relative to package root. Enabled only when using project import.) -> "types": "$1"$0 [[0, 1] .. [0, 1]]
							[Class, Snippet] typesVersions (Defines source paths that contain type definition modules for specific TypeScript versions.) -> "typesVersions": {
							    $1
							}$0 [[0, 1] .. [0, 1]]
							[Property, Snippet] typings (Synonymous with 'types'.) -> "typings": "$1"$0 [[0, 1] .. [0, 1]]
							[Property, Snippet] version (Npm semver version) -> "version": "$1"$0 [[0, 1] .. [0, 1]]
							[Value, Snippet] workspaces (Array of projects names or glob that are members of the yarn workspace) -> "workspaces": [
							    $1
							]$0 [[0, 1] .. [0, 1]]
							""");
		});
	}

	@Test
	public void testNameValuePairs_02() {
		testCompletion((tcc) -> {
			tcc.setModel("""
					{
						"main": "index.js",
						"dependencies": {},

					}
					""");
			tcc.setColumn(0);
			tcc.setLine(3);
			tcc.setExpectedCompletionItems(
					"""
							[Class, Snippet] dependencies (Dependencies of this npm) -> "dependencies": {
							    $1
							}$0 [[3, 0] .. [3, 0]]
							[Class, Snippet] devDependencies (Development dependencies of this npm) -> "devDependencies": {
							    $1
							}$0 [[3, 0] .. [3, 0]]
							[Class, Snippet] exports (Entry point definitions) -> "exports": {
							    $1
							}$0 [[3, 0] .. [3, 0]]
							[Property, Snippet] main (Main module. Path is relative to package root) -> "main": "$1"$0 [[3, 0] .. [3, 0]]
							[Property, Snippet] module (Like "main" but provides a different file with esm code) -> "module": "$1"$0 [[3, 0] .. [3, 0]]
							[Class, Snippet] n4js (N4JS section) -> "n4js": {
							    $1
							}$0 [[3, 0] .. [3, 0]]
							[Property, Snippet] name (Npm name) -> "name": "$1"$0 [[3, 0] .. [3, 0]]
							[Property, Snippet] type (The module format used for .js files in this project. Either 'commonjs' or 'module'.) -> "type": "$1"$0 [[3, 0] .. [3, 0]]
							[Property, Snippet] types (Type module. (TypesScript) Path is relative to package root. Enabled only when using project import.) -> "types": "$1"$0 [[3, 0] .. [3, 0]]
							[Class, Snippet] typesVersions (Defines source paths that contain type definition modules for specific TypeScript versions.) -> "typesVersions": {
							    $1
							}$0 [[3, 0] .. [3, 0]]
							[Property, Snippet] typings (Synonymous with 'types'.) -> "typings": "$1"$0 [[3, 0] .. [3, 0]]
							[Property, Snippet] version (Npm semver version) -> "version": "$1"$0 [[3, 0] .. [3, 0]]
							[Value, Snippet] workspaces (Array of projects names or glob that are members of the yarn workspace) -> "workspaces": [
							    $1
							]$0 [[3, 0] .. [3, 0]]
							""");
		});
	}

	@Test
	public void testNameValuePairs_03() {
		testCompletion((tcc) -> {
			tcc.setModel("""
					{
						"main": "index.js",
						"dependencies": {},
						"n4js": {

						}
					}
					""");
			tcc.setColumn(0);
			tcc.setLine(4);
			tcc.setExpectedCompletionItems(
					"""
							[Property, Snippet] definesPackage -> "definesPackage": "$1"$0 [[4, 0] .. [4, 0]]
							[Property, Snippet] extendedRuntimeEnvironment -> "extendedRuntimeEnvironment": "$1"$0 [[4, 0] .. [4, 0]]
							[Class, Snippet] generator (Configurations for the generator) -> "generator": {
							    $1
							}$0 [[4, 0] .. [4, 0]]
							[Property, Snippet] implementationId -> "implementationId": "$1"$0 [[4, 0] .. [4, 0]]
							[Value, Snippet] implementedProjects -> "implementedProjects": [
							    $1
							]$0 [[4, 0] .. [4, 0]]
							[Property, Snippet] mainModule (Main module specifier. Starts from source folder(s)) -> "mainModule": "$1"$0 [[4, 0] .. [4, 0]]
							[Class, Snippet] moduleFilters -> "moduleFilters": {
							    $1
							}$0 [[4, 0] .. [4, 0]]
							[Class, Snippet] noValidate -> "noValidate": {
							    $1
							}$0 [[4, 0] .. [4, 0]]
							[Property, Snippet] output (Output folder. Default is '.') -> "output": "$1"$0 [[4, 0] .. [4, 0]]
							[Property, Snippet] projectType (project type) -> "projectType": "$1"$0 [[4, 0] .. [4, 0]]
							[Value, Snippet] providedRuntimeLibraries -> "providedRuntimeLibraries": [
							    $1
							]$0 [[4, 0] .. [4, 0]]
							[Value, Snippet] requiredRuntimeLibraries -> "requiredRuntimeLibraries": [
							    $1
							]$0 [[4, 0] .. [4, 0]]
							[Class, Snippet] sources (Source folders) -> "sources": {
							    $1
							}$0 [[4, 0] .. [4, 0]]
							[Value, Snippet] testedProjects (Projects that are tested by this project) -> "testedProjects": [
							    $1
							]$0 [[4, 0] .. [4, 0]]
							[Property, Snippet] vendorId -> "vendorId": "$1"$0 [[4, 0] .. [4, 0]]
							[Property, Snippet] vendorName -> "vendorName": "$1"$0 [[4, 0] .. [4, 0]]
							""");
		});
	}

	@Test
	public void testNameValuePairs_04() {
		testCompletion((tcc) -> {
			tcc.setModel("""
					{
						"main": "index.js",
						"dependencies": {},
						"n4js": {
							"defin"
						}
					}
					""");
			tcc.setColumn(8);
			tcc.setLine(4);
			tcc.setExpectedCompletionItems("""
					[Property, Snippet] "definesPackage" -> "definesPackage": "$1"$0 [[4, 2] .. [4, 8]]
					""");
		});
	}

	@Test
	public void testNameValuePairs_05() {
		testCompletion((tcc) -> {
			tcc.setModel("""
					{
						"main": "index.js",
						"dependencies": {},
						"n4js": {
							"defin"
						}
					}
					""");
			tcc.setColumn(7);
			tcc.setLine(4);
			tcc.setExpectedCompletionItems("""
					[Property, Snippet] "definesPackage" -> "definesPackage": "$1"$0 [[4, 2] .. [4, 7]]
					""");
		});
	}

	@Test
	public void testInObjectLiteral_01() {
		testCompletion((tcc) -> {
			tcc.setModel("""
					{
						"dependencies": {

						}
					}
					""");
			tcc.setColumn(0);
			tcc.setLine(2);
			tcc.setExpectedCompletionItems("""
					[Value, Snippet] <array> (Generic name array pair) -> "${1:name}": [
					    $2
					]$0 [[2, 0] .. [2, 0]]
					[Class, Snippet] <object> (Generic name object pair) -> "${1:name}": {
					    $2
					}$0 [[2, 0] .. [2, 0]]
					[Property, Snippet] <value> (Generic name value pair) -> "${1:name}": "$2"$0 [[2, 0] .. [2, 0]]
					""");
		});
	}

	@Test
	public void testInArray_01() {
		testCompletion((tcc) -> {
			tcc.setModel("""
					{
						"dependencies": [

						]
					}
					""");
			tcc.setColumn(0);
			tcc.setLine(2);
			tcc.setExpectedCompletionItems("""
					[Keyword, PlainText] false -> false [[2, 0] .. [2, 0]]
					[Keyword, PlainText] null -> null [[2, 0] .. [2, 0]]
					[Keyword, PlainText] true -> true [[2, 0] .. [2, 0]]
					""");
		});
	}

	@Test
	public void testInDependencies_01() {
		testCompletion((tcc) -> {
			tcc.setModel("""
					{
						"dependencies": {
							"unknown":
						}
					}
					""");
			tcc.setColumn("		\"unknown\":".length());
			tcc.setLine(2);
			tcc.setExpectedCompletionItems("""
					[Value, PlainText] "*" -> "*" [[2, 12] .. [2, 12]]
					[Keyword, PlainText] false -> false [[2, 12] .. [2, 12]]
					[Keyword, PlainText] null -> null [[2, 12] .. [2, 12]]
					[Keyword, PlainText] true -> true [[2, 12] .. [2, 12]]
					""");
		});
	}

	@Test
	public void testProjectType_01() {
		testCompletion((tcc) -> {
			tcc.setModel("""
					{
						"n4js": {
							"projectType": "api"
						}
					}
					""");
			tcc.setColumn("		\"projectType\": \"api\":".length() - 4);
			tcc.setLine(2);
			tcc.setExpectedCompletionItems("""
					[Keyword, PlainText] "api" -> "api" [[2, 17] .. [2, 19]]
					[Keyword, PlainText] "application" -> "application" [[2, 17] .. [2, 19]]
					""");
		});
	}

	@Test
	public void testProjectType_02() {
		testCompletion((tcc) -> {
			tcc.setModel("""
					{
						"n4js": {
							"projectType":
						}
					}
					""");
			tcc.setColumn("		\"projectType\":".length());
			tcc.setLine(2);
			tcc.setExpectedCompletionItems("""
					[Keyword, PlainText] "api" -> "api" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] "application" -> "application" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] "definition" -> "definition" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] "library" -> "library" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] "plainjs" -> "plainjs" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] "processor" -> "processor" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] "runtimeEnvironment" -> "runtimeEnvironment" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] "runtimeLibrary" -> "runtimeLibrary" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] "test" -> "test" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] "validation" -> "validation" [[2, 16] .. [2, 16]]
					[Keyword, PlainText] false -> false [[2, 16] .. [2, 16]]
					[Keyword, PlainText] null -> null [[2, 16] .. [2, 16]]
					[Keyword, PlainText] true -> true [[2, 16] .. [2, 16]]
					""");
		});
	}
}
