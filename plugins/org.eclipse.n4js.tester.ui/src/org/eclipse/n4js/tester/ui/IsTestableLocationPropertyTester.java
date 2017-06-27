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
package org.eclipse.n4js.tester.ui;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.common.util.URI;

import com.google.inject.Inject;

import org.eclipse.n4js.tester.TestDiscoveryHelper;
import org.eclipse.n4js.ui.internal.N4JSActivator;

/**
 * A {@link PropertyTester} that checks if the receiver (usually a selected element in the workbench) contains N4JS
 * tests, i.e. classes with methods annotated with &#64;Test. For details, see
 * {@link TestDiscoveryHelper#isTestable(URI)}.
 */
public class IsTestableLocationPropertyTester extends PropertyTester {

	/** name space of the property tested */
	public static final String PROPERTY_NAMESPACE = "org.eclipse.n4js.tester.ui";
	/** property tested */
	public static final String PROPERTY_CONTAINS_TESTS = "isTestable";

	@Inject
	private TestDiscoveryHelper testDiscoveryHelper;

	/** constructor will take care of injecting internal fields */
	public IsTestableLocationPropertyTester() {
		// copied from org.eclipse.n4js.runner.ui.SupportingRunnerPropertyTester.SupportingRunnerPropertyTester():
		N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS).injectMembers(this);
		// TODO use executable extension factory instead
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final URI location = TestDiscoveryUIUtils.getLocationForSelectedObject(receiver);
		if (location == null)
			return false;
		return testDiscoveryHelper.isTestable(location);
	}
}
