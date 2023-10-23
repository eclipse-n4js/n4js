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
package org.eclipse.n4js.typesystem;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("unchecked")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class InferenceContext_RawTypeTest extends AbstractInferenceContextTest {

	@Test
	public void test_simple01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "=", rawTypeRef(G))
				},
				Pair.of(alpha, of(G, wildcard())));
	}

	@Test
	public void test_simple02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", rawTypeRef(G))
				},
				Pair.of(alpha, of(G, wildcard())));
	}

	@Test
	public void test_simple03() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", rawTypeRef(G))
				},
				Pair.of(alpha, of(G, wildcard())));
	}

	@Test
	public void test_combine_Raw_NonRaw01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "=", rawTypeRef(G)),
						constraint(alpha, "<:", of(G, B))
				},
				Pair.of(alpha, of(G, B)));
	}

	@Test
	public void test_combine_Raw_NonRaw02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", rawTypeRef(G)),
						constraint(alpha, "<:", of(G, B))
				},
				Pair.of(alpha, of(G, B)));
	}

	@Test
	public void test_combine_Raw_NonRaw03() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", rawTypeRef(G)),
						constraint(alpha, "<:", of(G, B))
				},
				Pair.of(alpha, of(G, B)));
	}

	@Test
	public void test_negative_simple01() {
		assertNoSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "=", rawTypeRef(IG)),
						constraint(alpha, "<:", rawTypeRef(IH))
				},
				alpha);
	}

	@Test
	public void test_negative_simple02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", rawTypeRef(IG)),
						constraint(alpha, "<:", rawTypeRef(IH))
				},
				Pair.of(alpha, intersection(of(IG, wildcard()), of(IH, wildcard()))));
	}

	@Test
	public void test_negative_simple02_withClasses() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", rawTypeRef(G)),
						constraint(alpha, "<:", rawTypeRef(H))
				},
				Pair.of(alpha, intersection(of(G, wildcard()), of(H, wildcard()))));
	}

	@Test
	public void test_negative_simple03() {
		assertNoSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", rawTypeRef(IG)),
						constraint(alpha, "<:", rawTypeRef(IH))
				},
				alpha);
	}

	@Test
	public void test_negative_combine_Raw_NonRaw() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", rawTypeRef(IG)),
						constraint(alpha, "<:", of(IH, B))
				},
				Pair.of(alpha, intersection(of(IG, wildcard()), of(IH, B))));
	}

	@Test
	public void test_negative_combine_Raw_NonRaw_withClasses() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", rawTypeRef(G)),
						constraint(alpha, "<:", of(H, B))
				},
				Pair.of(alpha, intersection(of(G, wildcard()), of(H, B))));
	}
}
