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
package org.eclipse.n4js.tests.compare;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.ApiImplCompareTestHelper;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.tooling.compare.ProjectCompareHelper;
import org.eclipse.n4js.tooling.compare.ProjectCompareResult.Status;
import org.eclipse.n4js.tooling.compare.ProjectComparison;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Inject;

@Ignore // Api/Impl concept is doomed
@SuppressWarnings("unchecked")
public class ApiImplCompareTest extends ConvertedIdeTest {

	protected static N4JSPackageName PROJECT_ID_API = new N4JSPackageName("org.eclipse.n4js.sample.api");
	protected static N4JSPackageName PROJECT_ID_IMPL = new N4JSPackageName("org.eclipse.n4js.sample.n4js");
	protected static N4JSPackageName PROJECT_ID_UTILS = new N4JSPackageName("org.eclipse.n4js.sample.utils");

	@Inject
	private ProjectCompareHelper projectCompareHelper;
	@Inject
	private ApiImplCompareTestHelper apiImplHelper;

	@Test
	public void testBasicCases() {
		importProband(new File("probands", "ApiImplCompare"));
		assertNoIssues();

		List<String> errMsgs = new ArrayList<>();
		ProjectComparison comparison = projectCompareHelper.createComparison(true, errMsgs);
		assertEquals(0, errMsgs.size());

		// checking class Clazz
		apiImplHelper.assertCorrectChildEntries(comparison, "x/y/M", "Clazz",
				// format:
				// childName -> expected comparison status -> expected comparison description

				Pair.of(Pair.of("fieldOK", Status.EQUAL), null),
				Pair.of(Pair.of("fieldOK_ClazzOther", Status.EQUAL), null),
				Pair.of(Pair.of("fieldOK_B", Status.EQUAL), null),
				Pair.of(Pair.of("fieldGONE", Status.ERROR), "missing implementation"),
				Pair.of(Pair.of("fieldWrongType", Status.ERROR), "number is not a subtype of string"),
				Pair.of(Pair.of("fieldOnlyInImplementation", Status.COMPLIANT), null),
				Pair.of(Pair.of("fieldVisibilityReduced", Status.ERROR), "reduced visibility"),

				Pair.of(Pair.of("methodOK", Status.EQUAL), null),
				Pair.of(Pair.of("methodImplicitVoid", Status.EQUAL), null),
				Pair.of(Pair.of("methodFewerPars", Status.COMPLIANT), null),
				Pair.of(Pair.of("methodCompliant", Status.COMPLIANT), null),
				Pair.of(Pair.of("methodOnlyInImplementation", Status.COMPLIANT), null),
				Pair.of(Pair.of("methodGetType", Status.EQUAL), null),
				Pair.of(Pair.of("methodReducedVisibility", Status.ERROR), "reduced visibility"),
				Pair.of(Pair.of("methodWithSurplusOptionalFparsOnApiSide", Status.COMPLIANT), null),
				Pair.of(Pair.of("methodWithSurplusOptionalFparsOnImplSide", Status.COMPLIANT), null));

		// checking enum Color

		apiImplHelper.assertCorrectChildEntries(comparison, "x/y/M", "Color",

				// format:
				// childName -> expected comparison status -> expected comparison description

				Pair.of(Pair.of("RED", Status.EQUAL), null),
				Pair.of(Pair.of("BLACK", Status.COMPLIANT), null),
				Pair.of(Pair.of("GREEN", Status.EQUAL), null),
				Pair.of(Pair.of("BLUE", Status.EQUAL), null));

		// checking interface I

		apiImplHelper.assertCorrectChildEntries(comparison, "x/y/M", "I",
				Pair.of(Pair.of("methodOK", Status.EQUAL), null),
				Pair.of(Pair.of("methodDefaultImpl", Status.EQUAL), null),
				Pair.of(Pair.of("methodMissingDefaultImpl", Status.ERROR),
						"no body in implementation but @ProvidesDefaultImplementation in API"));

		apiImplHelper.assertCorrectChildEntries(comparison, "x/y/M", "DB",
				Pair.of(Pair.of("method", Status.COMPLIANT), null),
				Pair.of(Pair.of("mFromSuperClassInImpl", Status.EQUAL), null),
				Pair.of(Pair.of("mFromInterfaceInImpl", Status.EQUAL), null));

		apiImplHelper.assertCorrectChildEntries(comparison, "x/y/M", "DC",
				Pair.of(Pair.of("method", Status.COMPLIANT), null));

		apiImplHelper.assertCorrectChildEntries(comparison, "x/y/M", "DD",
				Pair.of(Pair.of("method", Status.EQUAL), null));

		apiImplHelper.assertCorrectTypeEntry(comparison, "x/y/M",
				Pair.of(Pair.of("TypeVariableTest1", Status.ERROR), "the number of type variables doesn't match"),
				Pair.of(Pair.of("TypeVariableTest2", Status.EQUAL), null),
				Pair.of(Pair.of("TypeVariableTest21", Status.ERROR),
						"the upper bound of type variable Other isn't compatible with the API"),
				Pair.of(Pair.of("TypeVariableTest3", Status.EQUAL), null));
	}
}
