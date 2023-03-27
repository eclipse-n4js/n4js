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
package org.eclipse.n4js.ide.dts.tests;

import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

// Tests:
//  - closure build
//    - direct/indirect/non-included
//    - properties: tsconfig#files, tsconfig#include, tsconfig#include+exclude, package.json#main, package.json#types
//    - import from client by project/direct import
//  - normal build

/**
 *
 */
@SuppressWarnings("javadoc")
public class TsConfigRespectTest extends AbstractIdeTest {

	@Test
	public void testNoTsconfig() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "index.d.ts"
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage";
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));

		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testNoTsconfigNoFileExtension() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "index"
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage";
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testNoTsconfigClosure() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.d.ts", """
									import * as I from "./imported.d.ts";
									export class MyClass {
									}
								"""),
						Pair.of("imported.d.ts", """
									export class ImportedClass {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "index.d.ts"
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { ImportedClass } from "mypackage/imported";
									ImportedClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testEmptyMainTypesFiles() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of("tsconfig.json", """
									{
									    "files": ["index.d.ts")
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1"
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage/index"; // complete import
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testTsconfigFiles() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.d.ts", """
									// empty
								"""),
						Pair.of("lib.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of("tsconfig.json", """
									{
									    "files": ["lib.d.ts")
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1"
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage/lib"; // complete import
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testMissingTsconfigFiles() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.d.ts", """
									// empty
								"""),
						Pair.of("lib.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"main": "index.d.ts",
										"version": "0.0.1"
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage/lib"; // complete import
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertIssuesInModules(Pair.of("client/src/module.n4js", List.of(
				"(Error, [0:25 - 0:40], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)",
				"(Error, [1:1 - 1:8], Couldn't resolve reference to IdentifiableElement 'MyClass'.)")));

		shutdownLspServer();
	}

	@Test
	public void testSameMainAndTsconfigFiles() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of("tsconfig.json", """
									{
									    "files": ["index.d.ts")
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "index.d.ts"
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage";
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testIncludeExplicitInTsconfigFiles() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("lib.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of("tsconfig.json", """
									{
									    "include": ["lib.d.ts")
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage/lib";
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testIncludeGlobInTsconfigFiles() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("lib.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of("tsconfig.json", """
									{
									    "include": ["*.d.ts")
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage/lib";
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testExcludeInTsconfigFiles() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("lib.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of("tsconfig.json", """
									{
									    "include": ["*.d.ts"],
									    "exclude": ["lib.d.ts"]
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage/lib";
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertIssuesInModules(Pair.of("client/src/module.n4js", List.of(
				"(Error, [0:25 - 0:40], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)",
				"(Error, [1:1 - 1:8], Couldn't resolve reference to IdentifiableElement 'MyClass'.)")));

		shutdownLspServer();
	}

	@Test
	public void testNonStandardMain() {
		List<Pair<String, List<Pair<String, String>>>> testData = List.of(
				Pair.of(CFG_NODE_MODULES + "@types/mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("module.d.ts", """
									export class MyClass {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "module.d.ts"
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "mypackage", List.of(
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of("index.js", """
									export class MyClassJS {
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""))),
				Pair.of("client", List.of(
						Pair.of("module", """
									import { MyClass } from "mypackage";
									MyClass;
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""))));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

}
