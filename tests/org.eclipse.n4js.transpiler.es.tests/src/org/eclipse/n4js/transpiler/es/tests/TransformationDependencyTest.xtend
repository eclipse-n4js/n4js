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
package org.eclipse.n4js.transpiler.es.tests

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency
import org.eclipse.n4js.transpiler.TransformationDependency.Excludes
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesAfter
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesBefore
import org.eclipse.n4js.transpiler.TransformationDependency.Requires
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresAfter
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore
import java.util.List
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TransformationDependencyTest extends AbstractTranspilerTest {


	@Test
	def void testEmptySequence() {
		assertErrMsgs(#[
			// empty
		], #[
			// no error messages
		]);
	}

	@Test
	def void testRequires_ok() {
		assertErrMsgs(#[
			T0,
			T1,
			TR
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			T0,
			TR,
			T1
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			TR,
			T0,
			T1
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			T0,
			T1,
			TRmany
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			T0,
			TRmany,
			T1
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			TRmany,
			T0,
			T1
		], #[
			// no error messages
		]);
	}

	@Test
	def void testRequires_missingRequiredTrafo() {
		assertErrMsgs(#[
			T0,
			TR
		], #[
			"TR @Requires: T1"
		]);

		assertErrMsgs(#[
			T0,
			TRmany
		], #[
			"TRmany @Requires: T1"
		]);

		assertErrMsgs(#[
			TRmany
		], #[
			"TRmany @Requires: T0, T1"
		]);
	}

	@Test
	def void testRequiresBefore_ok() {
		assertErrMsgs(#[
			T0,
			T1,
			TRB
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			T0,
			T1,
			TRBmany
		], #[
			// no error messages
		]);
	}

	@Test
	def void testRequiresBefore_missingRequiredTrafo() {
		assertErrMsgs(#[
			T0,
			TRB
		], #[
			"TRB @RequiresBefore: T1"
		]);

		assertErrMsgs(#[
			T0,
			TRBmany
		], #[
			"TRBmany @RequiresBefore: T1"
		]);

		assertErrMsgs(#[
			TRBmany
		], #[
			"TRBmany @RequiresBefore: T0, T1"
		]);
	}

	@Test
	def void testRequiresBefore_wrongOrder() {
		assertErrMsgs(#[
			T0,
			TRB,
			T1
		], #[
			"TRB @RequiresBefore: T1"
		]);

		assertErrMsgs(#[
			T1,
			TRBmany,
			T0
		], #[
			"TRBmany @RequiresBefore: T0"
		]);
	}

	@Test
	def void testRequiresAfter_ok() {
		assertErrMsgs(#[
			TRA,
			T0,
			T1
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			TRAmany,
			T0,
			T1
		], #[
			// no error messages
		]);
	}

	@Test
	def void testRequiresAfter_missingRequiredTrafo() {
		assertErrMsgs(#[
			TRA,
			T0
		], #[
			"TRA @RequiresAfter: T1"
		]);

		assertErrMsgs(#[
			TRAmany,
			T0
		], #[
			"TRAmany @RequiresAfter: T1"
		]);

		assertErrMsgs(#[
			TRAmany
		], #[
			"TRAmany @RequiresAfter: T0, T1"
		]);
	}

	@Test
	def void testRequiresAfter_wrongOrder() {
		assertErrMsgs(#[
			T1,
			TRA,
			T0
		], #[
			"TRA @RequiresAfter: T1"
		]);

		assertErrMsgs(#[
			T0,
			TRAmany,
			T1
		], #[
			"TRAmany @RequiresAfter: T0"
		]);
	}

	@Test
	def void testExcludes_ok() {
		assertErrMsgs(#[
			T0,
			TE
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			TEmany
		], #[
			// no error messages
		]);
	}

	@Test
	def void testExcludes_conflictBefore() {
		assertErrMsgs(#[
			T0,
			T1,
			TE
		], #[
			"TE @Excludes: T1"
		]);

		assertErrMsgs(#[
			T0,
			T1,
			TEmany
		], #[
			"TEmany @Excludes: T0, T1"
		]);
	}

	@Test
	def void testExcludes_conflictAfter() {
		assertErrMsgs(#[
			TE,
			T0,
			T1
		], #[
			"TE @Excludes: T1"
		]);

		assertErrMsgs(#[
			TEmany,
			T0,
			T1
		], #[
			"TEmany @Excludes: T0, T1"
		]);
	}

	@Test
	def void testExcludesBefore_ok() {
		assertErrMsgs(#[
			TEB,
			T0,
			T1
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			TEBmany,
			T0,
			T1
		], #[
			// no error messages
		]);
	}

	@Test
	def void testExcludesBefore_conflictBefore() {
		assertErrMsgs(#[
			T0,
			T1,
			TEB
		], #[
			"TEB @ExcludesBefore: T1"
		]);

		assertErrMsgs(#[
			T0,
			T1,
			TEBmany
		], #[
			"TEBmany @ExcludesBefore: T0, T1"
		]);
	}

	@Test
	def void testExcludesBefore_conflictAfter() {
		assertErrMsgs(#[
			TEB,
			T0,
			T1
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			TEBmany,
			T0,
			T1
		], #[
			// no error messages
		]);
	}

	@Test
	def void testExcludesAfter_ok() {
		assertErrMsgs(#[
			T0,
			T1,
			TEA
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			T0,
			T1,
			TEAmany
		], #[
			// no error messages
		]);
	}

	@Test
	def void testExcludesAfter_conflictBefore() {
		assertErrMsgs(#[
			T0,
			T1,
			TEA
		], #[
			// no error messages
		]);

		assertErrMsgs(#[
			T0,
			T1,
			TEAmany
		], #[
			// no error messages
		]);
	}

	@Test
	def void testExcludesAfter_conflictAfter() {
		assertErrMsgs(#[
			TEA,
			T0,
			T1
		], #[
			"TEA @ExcludesAfter: T1"
		]);

		assertErrMsgs(#[
			TEAmany,
			T0,
			T1
		], #[
			"TEAmany @ExcludesAfter: T0, T1"
		]);
	}


	private static class T0 extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	private static class T1 extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@Requires(T1)
	private static class TR extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@Requires(T0,T1)
	private static class TRmany extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@RequiresBefore(T1)
	private static class TRB extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@RequiresBefore(T0,T1)
	private static class TRBmany extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@RequiresAfter(T1)
	private static class TRA extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@RequiresAfter(T0,T1)
	private static class TRAmany extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@Excludes(T1)
	private static class TE extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@Excludes(T0,T1)
	private static class TEmany extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@ExcludesBefore(T1)
	private static class TEB extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@ExcludesBefore(T0,T1)
	private static class TEBmany extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@ExcludesAfter(T1)
	private static class TEA extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}
	@ExcludesAfter(T0,T1)
	private static class TEAmany extends Transformation {
		override assertPreConditions() {}
		override assertPostConditions() {}
		override analyze() {}
		override transform() {}
	}


	def void assertErrMsgs(Class<? extends Transformation>[] sequence, List<String> expectedErrMsgs) {
		val actualErrMsgs = TransformationDependency.check(sequence);
		assertEquals(
			"incorrect error messages when checking transformation dependencies",
			expectedErrMsgs,
			actualErrMsgs);
	}
}
