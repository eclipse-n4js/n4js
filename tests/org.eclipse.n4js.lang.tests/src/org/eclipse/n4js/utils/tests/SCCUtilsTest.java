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
package org.eclipse.n4js.utils.tests;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.ts.types.RuntimeDependency;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.utils.SCCUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Low-level tests for {@link SCCUtils}.
 */
public class SCCUtilsTest {

	private TModule A;
	private TModule B;
	private TModule C;
	private TModule D;
	private TModule E;
	private TModule F;
	private TModule G;
	private TModule H;

	@Before
	public void createModules() {
		A = createModule("A");
		B = createModule("B");
		C = createModule("C");
		D = createModule("D");
		E = createModule("E");
		F = createModule("F");
		G = createModule("G");
		H = createModule("H");
	}

	@Test
	public void test01() {
		dependsOn(A, B);
		dependsOn(B, C);
		dependsOn(C, D);
		dependsOn(D);

		assertSCCs(List.of(A, B, C, D), List.of(
				List.of(D),
				List.of(C),
				List.of(B),
				List.of(A)));
	}

	@Test
	public void test02() {
		dependsOn(A, B);
		dependsOn(B, C);
		dependsOn(C, D);
		dependsOn(D, A);

		assertSCCs(List.of(A, B, C, D), List.of(
				List.of(D, C, B, A)));
	}

	@Test
	public void test03() {
		dependsOn(A, B);
		dependsOn(B, C);
		dependsOn(C, D, A);
		dependsOn(D, B);

		assertSCCs(List.of(A, B, C, D), List.of(
				List.of(D, C, B, A)));
	}

	@Test
	public void test04() {
		dependsOn(A, B);
		dependsOn(B, C);
		dependsOn(C, D, E);
		dependsOn(D, A);
		dependsOn(E, C);

		assertSCCs(List.of(A, B, C, D, E), List.of(
				List.of(E, D, C, B, A)));
	}

	@Test
	public void test05() {
		dependsOn(A, C, D);
		dependsOn(B, A);
		dependsOn(C, B);
		dependsOn(D, E);
		dependsOn(E);

		assertSCCs(List.of(A, B, C, D, E), List.of(
				List.of(E),
				List.of(D),
				List.of(B, C, A)));
	}

	// from Tarjan 1972, p. 158
	@Test
	public void test06() {
		dependsOn(A, B);
		dependsOn(B, C, H);
		dependsOn(C, D, G);
		dependsOn(D, E);
		dependsOn(E, C, F);
		dependsOn(F);
		dependsOn(G, D, F);
		dependsOn(H, A, G);

		assertSCCs(List.of(A, B, C, D, E, F, G, H), List.of(
				List.of(F),
				List.of(G, E, D, C),
				List.of(H, B, A)));
	}

	@Test
	public void testBackwardSubCycles01() {
		dependsOn(A, B);
		dependsOn(B, C);
		dependsOn(C, D);
		dependsOn(D, G, E);
		dependsOn(E, F);
		dependsOn(F, A);
		dependsOn(G, B);

		assertSCCs(List.of(A, B, C, D, E, F, G), List.of(
				List.of(F, E, G, D, C, B, A)));
	}

	@Test
	public void testBackwardSubCycles02() {
		// d_A1: A -> B
		// d_B1: B -> C
		// d_B2: B -> A
		// d_C1: C -> B
		dependsOn(A, B);
		dependsOn(B, C, A);
		dependsOn(C, B);

		assertSCCs(List.of(A, B, C), List.of(
				List.of(C, B, A)));

		// change order of dependencies in B
		dependsOn(B, A, C);

		assertSCCs(List.of(A, B, C), List.of(
				List.of(C, B, A)));
	}

	private void assertSCCs(Iterable<TModule> modules, Iterable<? extends Iterable<TModule>> expectedSCCs) {
		List<List<TModule>> actualSCCs = findSCCs(modules);
		if (!Objects.equals(expectedSCCs, actualSCCs)) {
			Assert.fail("""
					unexpected SCCs:
					EXPECTED:
					%s
					ACTUAL:
					%s
					""".formatted(convertSCCsToString(expectedSCCs), convertSCCsToString(actualSCCs)));
		}
	}

	private List<List<TModule>> findSCCs(Iterable<TModule> modules) {
		return SCCUtils.findSCCs(modules.iterator(), m -> map(m.getDependenciesRuntime(), dep -> dep.getTarget()));
	}

	private String convertSCCsToString(Iterable<? extends Iterable<TModule>> sccs) {
		return join("\n", map(sccs,
				iterSccs -> "(" + join(", ", map(iterSccs, m -> m.getSimpleName())) + ")"));
	}

	private TModule createModule(String name) {
		TModule module = TypesFactory.eINSTANCE.createTModule();
		module.setSimpleName(name);
		return module;
	}

	private void dependsOn(TModule module, TModule... dependencies) {
		module.getDependenciesRuntime().clear();
		for (TModule dep : dependencies) {
			RuntimeDependency newDep = TypesFactory.eINSTANCE.createRuntimeDependency();
			newDep.setTarget(dep);
			newDep.setLoadtimeForInheritance(false);
			module.getDependenciesRuntime().add(newDep);
		}
	}
}
