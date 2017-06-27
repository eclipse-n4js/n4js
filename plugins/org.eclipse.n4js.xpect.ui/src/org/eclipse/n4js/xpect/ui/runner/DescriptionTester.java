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
package org.eclipse.n4js.xpect.ui.runner;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.junit.runner.Description;

/**
 * Performs property check on receiver. If it is instanceof {@link IStructuredSelection} holding instanceof
 * {@link Description} it will perform link {@link Description#isTest()} or {@link Description#isSuite()} check. Check
 * will fail in all other cases.
 */
public class DescriptionTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {

		if (receiver instanceof IStructuredSelection == false) {
			return false;
		}

		Object element = ((IStructuredSelection) receiver).getFirstElement();

		if (element == null || element instanceof Description == false) {
			return false;
		}

		Description description = (Description) element;

		if (property.equals("isTest")) {
			return description.isTest();
		}

		if (property.equals("isSuite")) {
			return description.isSuite();
		}

		return false;
	}
}
