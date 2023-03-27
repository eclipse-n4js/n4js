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
import java.util.Map;

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
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.d.ts", """
									export class MyClass {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "index.d.ts"
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage";
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));

		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testNoTsconfigNoFileExtension() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.d.ts", """
									export class MyClass {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "index"
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage";
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testNoTsconfigClosure() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.d.ts", """
									import * as I from "./imported.d.ts";
									export class MyClass {
									}
								""",
						"imported.d.ts", """
									export class ImportedClass {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "index.d.ts"
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { ImportedClass } from "mypackage/imported";
									ImportedClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testEmptyMainTypesFiles() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.d.ts", """
									export class MyClass {
									}
								""",
						"tsconfig.json", """
									{
									    "files": ["index.d.ts")
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1"
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage/index"; // complete import
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testTsconfigFiles() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.d.ts", """
									// empty
								""",
						"lib.d.ts", """
									export class MyClass {
									}
								""",
						"tsconfig.json", """
									{
									    "files": ["lib.d.ts")
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1"
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage/lib"; // complete import
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testMissingTsconfigFiles() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.d.ts", """
									// empty
								""",
						"lib.d.ts", """
									export class MyClass {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"main": "index.d.ts",
										"version": "0.0.1"
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage/lib"; // complete import
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertIssuesInModules(Pair.of("client/src/module.n4js", List.of(
				"(Error, [0:25 - 0:40], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)",
				"(Error, [1:1 - 1:8], Couldn't resolve reference to IdentifiableElement 'MyClass'.)")));

		shutdownLspServer();
	}

	@Test
	public void testSameMainAndTsconfigFiles() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.d.ts", """
									export class MyClass {
									}
								""",
						"tsconfig.json", """
									{
									    "files": ["index.d.ts")
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "index.d.ts"
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage";
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testIncludeExplicitInTsconfigFiles() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"lib.d.ts", """
									export class MyClass {
									}
								""",
						"tsconfig.json", """
									{
									    "include": ["lib.d.ts")
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage/lib";
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testIncludeGlobInTsconfigFiles() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"lib.d.ts", """
									export class MyClass {
									}
								""",
						"tsconfig.json", """
									{
									    "include": ["*.d.ts")
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage/lib";
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

	@Test
	public void testExcludeInTsconfigFiles() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"lib.d.ts", """
									export class MyClass {
									}
								""",
						"tsconfig.json", """
									{
									    "include": ["*.d.ts"],
									    "exclude": ["lib.d.ts"]
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage/lib";
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertIssuesInModules(Pair.of("client/src/module.n4js", List.of(
				"(Error, [0:25 - 0:40], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)",
				"(Error, [1:1 - 1:8], Couldn't resolve reference to IdentifiableElement 'MyClass'.)")));

		shutdownLspServer();
	}

	@Test
	public void testNonStandardMain() {
		Map<String, Map<String, String>> testData = Map.of(
				CFG_NODE_MODULES + "@types/mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"module.d.ts", """
									export class MyClass {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "@types/mypackage",
										"version": "0.0.1",
										"main": "module.d.ts"
									}
								"""),
				CFG_NODE_MODULES + "mypackage", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"index.js", """
									export class MyClassJS {
									}
								""",
						PACKAGE_JSON, """
									{
										"name": "mypackage",
										"version": "0.0.1",
										"main": "index.js"
									}
								"""),
				"client", Map.of(
						"module", """
									import { MyClass } from "mypackage";
									MyClass;
								""",
						CFG_DEPENDENCIES, """
									mypackage, @types/mypackage
								"""));
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(testData);

		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
	}

}
