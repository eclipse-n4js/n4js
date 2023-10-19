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
package org.eclipse.n4js.tests.scoping;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class TypeVisibilityCheckerTest {

	static String SAME_VENDOR = "V";
	static String SAME_PROJECT = "P";
	static String OTHER_VENDOR = "V2";
	static String OTHER_PROJECT = "P2";

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	TypeVisibilityChecker visibilityChecker;

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	private Script withVendorAndProject(Script script, String vendorID, String projectID) {
		TModule module = (TModule) script.eResource().getContents().get(1);
		module.setProjectID(projectID);
		module.setVendorID(vendorID);
		return script;
	}

	private void assertIsVisible(CharSequence type, String myVendor, String myProject) {
		doAssertVisibility(type, myVendor, myProject, true);
	}

	private void assertIsNotVisible(CharSequence type, String myVendor, String myProject) {
		doAssertVisibility(type, myVendor, myProject, false);
	}

	private void doAssertVisibility(CharSequence type, String myVendor, String myProject, boolean expectation) {
		XtextResourceSet rs = resourceSetProvider.get();
		try {

			URI uri1 = URI.createURI("a.n4js");
			Script script = withVendorAndProject(parseHelper.parse(type, uri1, rs), SAME_VENDOR, SAME_PROJECT);
			Type parsedType = head(((TModule) last(script.eResource().getContents())).getTypesAndFunctions());
			URI uri2 = URI.createURI("b.n4js?" + myVendor + "|" + myProject);
			Script myScript = withVendorAndProject(parseHelper.parse("class A{}", uri2, rs), myVendor, myProject);

			Assert.assertEquals(expectation, visibilityChecker.isVisible(myScript.eResource(), parsedType).visibility);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Public API type visible from same project / same vendor
	 */
	@Test
	public void testPublicType_01() {
		assertIsVisible("export public class X {}", SAME_VENDOR, SAME_PROJECT);
	}

	/**
	 * Public API type visible from diff project / same vendor
	 */
	@Test
	public void testPublicType_02() {
		assertIsVisible("export public class X {}", SAME_VENDOR, OTHER_PROJECT);
	}

	/**
	 * Public API type visible from diff project / diff vendor
	 */
	@Test
	public void testPublicType_03() {
		assertIsVisible("export public class X {}", OTHER_VENDOR, OTHER_PROJECT);
	}

	/**
	 * Error condition: Public API type visible from same project / diff vendor
	 */
	@Test
	public void testPublicType_04() {
		assertIsVisible("export public class X {}", OTHER_VENDOR, SAME_PROJECT);
	}

	/**
	 * Public type visible from same project / same vendor
	 */
	@Test
	public void testPublicInternalType_01() {
		assertIsVisible("export @Internal public class X {}", SAME_VENDOR, SAME_PROJECT);
	}

	/**
	 * Public type visible from diff project / same vendor
	 */
	@Test
	public void testPublicInternalType_02() {
		assertIsVisible("export @Internal public class X {}", SAME_VENDOR, OTHER_PROJECT);
	}

	/**
	 * Public type not visible from diff project / diff vendor
	 */
	@Test
	public void testPublicInternalType_03() {
		assertIsNotVisible("export @Internal public class X {}", OTHER_VENDOR, OTHER_PROJECT);
	}

	/**
	 * Error condition: Public type not visible from same project / diff vendor
	 */
	@Test
	public void testPublicInternalType_04() {
		assertIsNotVisible("export @Internal public class X {}", OTHER_VENDOR, SAME_PROJECT);
	}

	/**
	 * Error condition: Project API type visible from same project / same vendor
	 */
	@Test
	public void testExportedProjectType_01() {
		assertIsVisible("export project class X {}", SAME_VENDOR, SAME_PROJECT);
	}

	/**
	 * Error condition: Project API type not visible from diff project / same vendor
	 */
	@Test
	public void testExportedProjectType_02() {
		assertIsNotVisible("export project class X {}", SAME_VENDOR, OTHER_PROJECT);
	}

	/**
	 * Error condition: Project API type not visible from diff project / diff vendor
	 */
	@Test
	public void testExportedProjectType_03() {
		assertIsNotVisible("export project class X {}", OTHER_VENDOR, OTHER_PROJECT);
	}

	/**
	 * Error condition: Project API type not visible from same project / diff vendor
	 */
	@Test
	public void testExportedProjectType_04() {
		assertIsNotVisible("export project class X {}", OTHER_VENDOR, SAME_PROJECT);
	}
}
