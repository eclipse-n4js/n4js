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
package org.eclipse.n4js.ui.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.internal.ui.viewsupport.TreeHierarchyLayoutProblemsDecorator;
import org.eclipse.jdt.ui.JavaElementImageDescriptor;

import com.google.common.annotations.VisibleForTesting;

import org.eclipse.n4js.ui.workingsets.WorkingSet;

/**
 * Enables decoration for the customized {@link WorkingSet working sets} in the navigator.
 */
@SuppressWarnings("restriction")
public class N4JSProjectExplorerProblemsDecorator extends TreeHierarchyLayoutProblemsDecorator {

	/**
	 * Flag to render the build path error adornment.
	 */
	public static final int BUILDPATH_ERROR = JavaElementImageDescriptor.BUILDPATH_ERROR;

	/**
	 * Flag to render the error adornment.
	 */
	public static final int ERROR = JavaElementImageDescriptor.ERROR;

	/**
	 * Flag to render the warning adornment.
	 */
	public static final int WARNING = JavaElementImageDescriptor.WARNING;

	/**
	 * Flag to render no adornments at all.
	 */
	public static final int NO_ADORNMENT = 0;

	/**
	 * Increased visibility for testing purposes.
	 *
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	@VisibleForTesting
	public int computeAdornmentFlags(final Object obj) {
		if (!(obj instanceof WorkingSet)) {
			return super.computeAdornmentFlags(obj);
		}

		final WorkingSet workingSet = (WorkingSet) obj;
		final IAdaptable[] elements = workingSet.getElements();
		int result = 0;
		for (int i = 0; i < elements.length; i++) {
			final IAdaptable element = elements[i];
			final int flags = super.computeAdornmentFlags(element);
			if ((flags & BUILDPATH_ERROR) != 0) {
				return BUILDPATH_ERROR;
			}
			result |= flags;
		}
		if ((result & ERROR) != 0) {
			return ERROR;
		} else if ((result & WARNING) != 0) {
			return WARNING;
		} else {
			return NO_ADORNMENT;
		}
	}

}
