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
package org.eclipse.n4js.transpiler.es.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency;
import org.eclipse.n4js.transpiler.TransformationDependency.Excludes;
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesAfter;
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesBefore;
import org.eclipse.n4js.transpiler.TransformationDependency.Requires;
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresAfter;
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class TransformationDependencyTest extends AbstractTranspilerTest {

	@Test
	public void testEmptySequence() {
		assertErrMsgs(List.of(
		// empty
		), List.of(
		// no error messages
		));
	}

	@Test
	public void testRequires_ok() {
		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TR.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				T0.class,
				TR.class,
				T1.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				TR.class,
				T0.class,
				T1.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TRmany.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				T0.class,
				TRmany.class,
				T1.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				TRmany.class,
				T0.class,
				T1.class), List.of(
		// no error messages
		));
	}

	@Test
	public void testRequires_missingRequiredTrafo() {
		assertErrMsgs(List.of(
				T0.class,
				TR.class),
				List.of(
						"TR @Requires: T1"));

		assertErrMsgs(List.of(
				T0.class,
				TRmany.class),
				List.of(
						"TRmany @Requires: T1"));

		assertErrMsgs(List.of(
				TRmany.class),
				List.of(
						"TRmany @Requires: T0, T1"));
	}

	@Test
	public void testRequiresBefore_ok() {
		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TRB.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TRBmany.class), List.of(
		// no error messages
		));
	}

	@Test
	public void testRequiresBefore_missingRequiredTrafo() {
		assertErrMsgs(List.of(
				T0.class,
				TRB.class),
				List.of(
						"TRB @RequiresBefore: T1"));

		assertErrMsgs(List.of(
				T0.class,
				TRBmany.class),
				List.of(
						"TRBmany @RequiresBefore: T1"));

		assertErrMsgs(List.of(
				TRBmany.class),
				List.of(
						"TRBmany @RequiresBefore: T0, T1"));
	}

	@Test
	public void testRequiresBefore_wrongOrder() {
		assertErrMsgs(List.of(
				T0.class,
				TRB.class,
				T1.class),
				List.of(
						"TRB @RequiresBefore: T1"));

		assertErrMsgs(List.of(
				T1.class,
				TRBmany.class,
				T0.class),
				List.of(
						"TRBmany @RequiresBefore: T0"));
	}

	@Test
	public void testRequiresAfter_ok() {
		assertErrMsgs(List.of(
				TRA.class,
				T0.class,
				T1.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				TRAmany.class,
				T0.class,
				T1.class), List.of(
		// no error messages
		));
	}

	@Test
	public void testRequiresAfter_missingRequiredTrafo() {
		assertErrMsgs(List.of(
				TRA.class,
				T0.class),
				List.of(
						"TRA @RequiresAfter: T1"));

		assertErrMsgs(List.of(
				TRAmany.class,
				T0.class),
				List.of(
						"TRAmany @RequiresAfter: T1"));

		assertErrMsgs(List.of(
				TRAmany.class),
				List.of(
						"TRAmany @RequiresAfter: T0, T1"));
	}

	@Test
	public void testRequiresAfter_wrongOrder() {
		assertErrMsgs(List.of(
				T1.class,
				TRA.class,
				T0.class),
				List.of(
						"TRA @RequiresAfter: T1"));

		assertErrMsgs(List.of(
				T0.class,
				TRAmany.class,
				T1.class),
				List.of(
						"TRAmany @RequiresAfter: T0"));
	}

	@Test
	public void testExcludes_ok() {
		assertErrMsgs(List.of(
				T0.class,
				TE.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				TEmany.class), List.of(
		// no error messages
		));
	}

	@Test
	public void testExcludes_conflictBefore() {
		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TE.class),
				List.of(
						"TE @Excludes: T1"));

		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TEmany.class),
				List.of(
						"TEmany @Excludes: T0, T1"));
	}

	@Test
	public void testExcludes_conflictAfter() {
		assertErrMsgs(List.of(
				TE.class,
				T0.class,
				T1.class),
				List.of(
						"TE @Excludes: T1"));

		assertErrMsgs(List.of(
				TEmany.class,
				T0.class,
				T1.class),
				List.of(
						"TEmany @Excludes: T0, T1"));
	}

	@Test
	public void testExcludesBefore_ok() {
		assertErrMsgs(List.of(
				TEB.class,
				T0.class,
				T1.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				TEBmany.class,
				T0.class,
				T1.class), List.of(
		// no error messages
		));
	}

	@Test
	public void testExcludesBefore_conflictBefore() {
		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TEB.class),
				List.of(
						"TEB @ExcludesBefore: T1"));

		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TEBmany.class),
				List.of(
						"TEBmany @ExcludesBefore: T0, T1"));
	}

	@Test
	public void testExcludesBefore_conflictAfter() {
		assertErrMsgs(List.of(
				TEB.class,
				T0.class,
				T1.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				TEBmany.class,
				T0.class,
				T1.class), List.of(
		// no error messages
		));
	}

	@Test
	public void testExcludesAfter_ok() {
		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TEA.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TEAmany.class), List.of(
		// no error messages
		));
	}

	@Test
	public void testExcludesAfter_conflictBefore() {
		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TEA.class), List.of(
		// no error messages
		));

		assertErrMsgs(List.of(
				T0.class,
				T1.class,
				TEAmany.class), List.of(
		// no error messages
		));
	}

	@Test
	public void testExcludesAfter_conflictAfter() {
		assertErrMsgs(List.of(
				TEA.class,
				T0.class,
				T1.class),
				List.of(
						"TEA @ExcludesAfter: T1"));

		assertErrMsgs(List.of(
				TEAmany.class,
				T0.class,
				T1.class),
				List.of(
						"TEAmany @ExcludesAfter: T0, T1"));
	}

	private static class T0 extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	private static class T1 extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@Requires(T1.class)
	private static class TR extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@Requires({ T0.class, T1.class })
	private static class TRmany extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@RequiresBefore(T1.class)
	private static class TRB extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@RequiresBefore({ T0.class, T1.class })
	private static class TRBmany extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@RequiresAfter(T1.class)
	private static class TRA extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@RequiresAfter({ T0.class, T1.class })
	private static class TRAmany extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@Excludes(T1.class)
	private static class TE extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@Excludes({ T0.class, T1.class })
	private static class TEmany extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@ExcludesBefore(T1.class)
	private static class TEB extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@ExcludesBefore({ T0.class, T1.class })
	private static class TEBmany extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@ExcludesAfter(T1.class)
	private static class TEA extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	@ExcludesAfter({ T0.class, T1.class })
	private static class TEAmany extends Transformation {
		@Override
		public void assertPreConditions() {
			/* empty */}

		@Override
		public void assertPostConditions() {
			/* empty */}

		@Override
		public void analyze() {
			/* empty */}

		@Override
		public void transform() {
			/* empty */}
	}

	public void assertErrMsgs(List<Class<? extends Transformation>> sequence, List<String> expectedErrMsgs) {
		@SuppressWarnings("unchecked")
		List<String> actualErrMsgs = TransformationDependency.check(sequence.toArray(new Class[0]));
		assertEquals(
				"incorrect error messages when checking transformation dependencies",
				expectedErrMsgs,
				actualErrMsgs);
	}
}
