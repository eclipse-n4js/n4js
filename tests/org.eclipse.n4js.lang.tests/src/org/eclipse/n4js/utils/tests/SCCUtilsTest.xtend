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
package org.eclipse.n4js.utils.tests

import java.util.List
import java.util.Objects
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.utils.SCCUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Low-level tests for {@link SCCUtils}.
 */
class SCCUtilsTest {

	private TModule A;
	private TModule B;
	private TModule C;
	private TModule D;
	private TModule E;
	private TModule F;
	private TModule G;
	private TModule H;

	@Before
	def void createModules() {
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
	def void test01() {
		A.dependsOn(B);
		B.dependsOn(C);
		C.dependsOn(D);
		D.dependsOn();

		assertSCCs(#[A, B, C, D], #[
			#[D],
			#[C],
			#[B],
			#[A]
		]);
	}

	@Test
	def void test02() {
		A.dependsOn(B);
		B.dependsOn(C);
		C.dependsOn(D);
		D.dependsOn(A);

		assertSCCs(#[A, B, C, D], #[
			#[D, C, B, A]
		]);
	}

	@Test
	def void test03() {
		A.dependsOn(B);
		B.dependsOn(C);
		C.dependsOn(D, A);
		D.dependsOn(B);

		assertSCCs(#[A, B, C, D], #[
			#[D, C, B, A]
		]);
	}

	@Test
	def void test04() {
		A.dependsOn(B);
		B.dependsOn(C);
		C.dependsOn(D, E);
		D.dependsOn(A);
		E.dependsOn(C);

		assertSCCs(#[A, B, C, D, E], #[
			#[E, D, C, B, A]
		]);
	}

	@Test
	def void test05() {
		A.dependsOn(C, D);
		B.dependsOn(A);
		C.dependsOn(B);
		D.dependsOn(E);
		E.dependsOn();

		assertSCCs(#[A, B, C, D, E], #[
			#[E],
			#[D],
			#[B, C, A]
		]);
	}

	// from Tarjan 1972, p. 158
	@Test
	def void test06() {
		A.dependsOn(B);
		B.dependsOn(C, H);
		C.dependsOn(D, G);
		D.dependsOn(E);
		E.dependsOn(C, F);
		F.dependsOn();
		G.dependsOn(D, F);
		H.dependsOn(A, G);

		assertSCCs(#[A, B, C, D, E, F, G, H], #[
			#[F],
			#[G, E, D, C],
			#[H, B, A]
		]);
	}

	@Test
	def void testBackwardSubCycles01() {
		A.dependsOn(B);
		B.dependsOn(C);
		C.dependsOn(D);
		D.dependsOn(G, E);
		E.dependsOn(F);
		F.dependsOn(A);
		G.dependsOn(B);

		assertSCCs(#[A, B, C, D, E, F, G], #[
			#[F, E, G, D, C, B, A]
		]);
	}

	@Test
	def void testBackwardSubCycles02() {
		// d_A1: A -> B
		// d_B1: B -> C
		// d_B2: B -> A
		// d_C1: C -> B
		A.dependsOn(B);
		B.dependsOn(C, A);
		C.dependsOn(B);

		assertSCCs(#[A, B, C], #[
			#[C, B, A]
		]);

		// change order of dependencies in B
		B.dependsOn(A, C);

		assertSCCs(#[A, B, C], #[
			#[C, B, A]
		]);
	}

	def private void assertSCCs(Iterable<TModule> modules, Iterable<? extends Iterable<TModule>> expectedSCCs) {
		val actualSCCs = findSCCs(modules);
		if (!Objects.equals(expectedSCCs, actualSCCs)) {
			Assert.fail('''
				unexpected SCCs:
				EXPECTED:
				«convertSCCsToString(expectedSCCs)»
				ACTUAL:
				«convertSCCsToString(actualSCCs)»
			''')
		}
	}

	def private List<List<TModule>> findSCCs(Iterable<TModule> modules) {
		return SCCUtils.findSCCs(modules.iterator, [ m |
			m.dependenciesRunTime.map[target]
		]);
	}

	def private String convertSCCsToString(Iterable<? extends Iterable<TModule>> sccs) {
		return sccs
			.map["(" + it.map[simpleName].join(", ") + ")"]
			.join("\n");
	}

	def private TModule createModule(String name) {
		return TypesFactory.eINSTANCE.createTModule => [
			simpleName = name
		];
	}

	def private void dependsOn(TModule module, TModule... dependencies) {
		module.dependenciesRunTime.clear();
		for (dep : dependencies) {
			module.dependenciesRunTime += TypesFactory.eINSTANCE.createRunTimeDependency => [
				target = dep;
				loadTimeForInheritance = false;
			];
		}
	}
}
