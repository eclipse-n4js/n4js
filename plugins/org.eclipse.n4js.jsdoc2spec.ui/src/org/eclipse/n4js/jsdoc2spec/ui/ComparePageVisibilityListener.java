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
package org.eclipse.n4js.jsdoc2spec.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.n4js.jsdoc2spec.ui.SpecPage.VisibilityChangedListener;

/**
 * This listener initialized the compare view.
 */
public class ComparePageVisibilityListener implements VisibilityChangedListener {
	final SpecChangeSetProvider csp;
	final SpecComparePage comparePage;

	/**
	 * Constructor
	 */
	public ComparePageVisibilityListener(SpecChangeSetProvider csp, SpecComparePage comparePage) {
		this.csp = csp;
		this.comparePage = comparePage;
	}

	@Override
	public void isVisibleChanged(boolean visible) {
		try {
			comparePage.setSpecChanges(new ArrayList<>(csp.getSpecChangeSet()));
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				e = (Exception) ((InvocationTargetException) e).getTargetException();
			}
			comparePage.setErrorMessage("Error updating specs" + e.getMessage());
			comparePage.setSpecChanges(new ArrayList<>());
		}
	}

}
