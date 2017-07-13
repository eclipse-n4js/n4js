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
package org.eclipse.n4js.typesystem

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class InferenceContext_RawTypeTest extends AbstractInferenceContextTest {

	@Test
	def void test_simple01() {
		script.assertSolution(
			#[
				constraint(alpha,'=',G.rawTypeRef)
			],
			alpha -> G.of(wildcard())
		)
	}

	@Test
	def void test_simple02() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',G.rawTypeRef)
			],
			alpha -> G.of(wildcard())
		)
	}

	@Test
	def void test_simple03() {
		script.assertSolution(
			#[
				constraint(alpha,':>',G.rawTypeRef)
			],
			alpha -> G.of(wildcard())
		)
	}

	@Test
	def void test_combine_Raw_NonRaw01() {
		script.assertSolution(
			#[
				constraint(alpha,'=',G.rawTypeRef),
				constraint(alpha,'<:',G.of(B))
			],
			alpha -> G.of(B)
		)
	}

	@Test
	def void test_combine_Raw_NonRaw02() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',G.rawTypeRef),
				constraint(alpha,'<:',G.of(B))
			],
			alpha -> G.of(B)
		)
	}

	@Test
	def void test_combine_Raw_NonRaw03() {
		script.assertSolution(
			#[
				constraint(alpha,':>',G.rawTypeRef),
				constraint(alpha,'<:',G.of(B))
			],
			alpha -> G.of(B)
		)
	}

	@Test
	def void test_negative_simple01() {
		script.assertNoSolution(
			#[
				constraint(alpha,'=',IG.rawTypeRef),
				constraint(alpha,'<:',IH.rawTypeRef)
			],
			alpha
		)
	}
	@Test
	def void test_negative_simple02() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',IG.rawTypeRef),
				constraint(alpha,'<:',IH.rawTypeRef)
			],
			alpha -> intersection(IG.of(wildcard()),IH.of(wildcard()))
		)
	}

	@Test
	def void test_negative_simple02_withClasses() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',G.rawTypeRef),
				constraint(alpha,'<:',H.rawTypeRef)
			],
			alpha -> intersection(G.of(wildcard()), H.of(wildcard()))
		)
	}

	@Test
	def void test_negative_simple03() {
		script.assertNoSolution(
			#[
				constraint(alpha,':>',IG.rawTypeRef),
				constraint(alpha,'<:',IH.rawTypeRef)
			],
			alpha
		)
	}

	@Test
	def void test_negative_combine_Raw_NonRaw() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',IG.rawTypeRef),
				constraint(alpha,'<:',IH.of(B))
			],
			alpha -> intersection(IG.of(wildcard()), IH.of(B))
		)
	}

	@Test
	def void test_negative_combine_Raw_NonRaw_withClasses() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',G.rawTypeRef),
				constraint(alpha,'<:',H.of(B))
			],
			alpha -> intersection(G.of(wildcard()), H.of(B))
		)
	}
}
