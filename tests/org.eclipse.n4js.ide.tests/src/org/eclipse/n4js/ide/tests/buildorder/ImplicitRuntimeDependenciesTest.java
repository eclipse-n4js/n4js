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
package org.eclipse.n4js.ide.tests.buildorder;

import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Test for build order
 */

public class ImplicitRuntimeDependenciesTest extends AbstractBuildOrderTest {

	@Test
	public void testDepOrder() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/node_modules/n4js-runtime-es2015, " +
				"yarn-test-project/node_modules/immutable, " +
				"yarn-test-project/packages/P1",

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						CFG_NODE_MODULES + "immutable",
						Map.of("immutable.d.ts", """
									declare namespace Immutable {
										namespace List {
										}
										function List<T>(collection?: Iterable<T> | ArrayLike<T>): List<T>;
										interface List<T> {
											readonly size: number;
											entries(): IterableIterator<[K, V)>;
											[Symbol.iterator)(): IterableIterator<unknown>;
										}
									}
									export = Immutable;
								""",
								"package.json", """
											{
											  "name": "immutable",
											  "main": "dist/immutable.js",
											  "module": "dist/immutable.es.js",
											  "types": "dist/immutable.d.ts"
											}
										"""),
						CFG_NODE_MODULES + "n4js-runtime-es2015",
						Map.of("es2015.iterable.n4jsd",
								"""
											export external public interface ~IterableIterator<out T> extends Iterator<T>, Iterable<T> {
												@Override
												[Symbol.iterator)(): IterableIterator<T>;
											}
										"""),
						"P1", Map.of(
								"package.json", """
											{
											  "name": "P1",
											  "dependencies": {
											    "n4js-runtime": "",
											    "n4js-runtime-es2015": "",
											    "immutable": ""
											  },
											  "n4js": {
											    "projectType": "library",
											    "output": "src-gen",
											    "sources": {
											      "source": [
											        "src"
											      )
											    },
											    "requiredRuntimeLibraries": [
											      "n4js-runtime-es2015"
											    )
											  }
											}
										""")));
	}

	@Test
	public void testDepOrderReverse() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/node_modules/n4js-runtime-es2015, " +
				"yarn-test-project/node_modules/immutable, " +
				"yarn-test-project/packages/P1",

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						CFG_NODE_MODULES + "immutable",
						Map.of("immutable.d.ts", """
									declare namespace Immutable {
										namespace List {
											function List<T>(collection?: Iterable<T> | ArrayLike<T>): List<T>;
											interface List<T> {
												readonly size: number;
												entries(): IterableIterator<[K, V)>;
												[Symbol.iterator)(): IterableIterator<unknown>;
											}
										}
									}
									export = Immutable;
								""",
								"package.json", """
											{
											  "name": "immutable",
											  "main": "dist/immutable.js",
											  "module": "dist/immutable.es.js",
											  "types": "dist/immutable.d.ts"
											}
										"""),
						CFG_NODE_MODULES + "n4js-runtime-es2015",
						Map.of("es2015.iterable.n4jsd",
								"""
											export external public interface ~IterableIterator<out T> extends Iterator<T>, Iterable<T> {
												@Override
												[Symbol.iterator)(): IterableIterator<T>;
											}
										""",
								"package.json", """
											{
											  "name": "n4js-runtime-es2015",
											  "version": "0.31.1",
											  "type": "module",
											  "n4js": {
											    "projectType": "runtimeLibrary",
											    "output": "src-gen",
											    "sources": {
											      "source": [
											        "src"
											      )
											    }
											  }
											}

										"""),
						"P1", Map.of(
								"package.json", """
											{
											  "name": "P1",
											  "dependencies": {
											    "n4js-runtime": "",
											    "immutable": "",           // HERE: on purpose above of es2015
											    "n4js-runtime-es2015": ""
											  },
											  "n4js": {
											    "projectType": "library",
											    "output": "src-gen",
											    "sources": {
											      "source": [
											        "src"
											      )
											    },
											    "requiredRuntimeLibraries": [
											      "n4js-runtime-es2015"
											    )
											  }
											}
										""")));
	}

	@Test
	public void testCycles4000() {
		test("yarn-test-project, " +
				"yarn-test-project/node_modules/n4js-runtime, " +
				"yarn-test-project/packages/P4, " +
				"yarn-test-project/packages/P3, " +
				"yarn-test-project/packages/P2, " +
				"yarn-test-project/packages/P1",

				List.of(
						List.of("yarn-test-project/packages/P1", "yarn-test-project/packages/P2",
								"yarn-test-project/packages/P3", "yarn-test-project/packages/P4"),
						List.of("yarn-test-project/packages/P2", "yarn-test-project/packages/P3")),

				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P2
										"""),
						"P2", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P3
										"""),
						"P3", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P2,
											P4
										"""),
						"P4", Map.of(
								CFG_DEPENDENCIES, """
											n4js-runtime,
											P1
										""")));
	}

}
