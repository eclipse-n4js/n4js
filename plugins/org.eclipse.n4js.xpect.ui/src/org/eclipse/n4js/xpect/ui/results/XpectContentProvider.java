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
package org.eclipse.n4js.xpect.ui.results;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.junit.runner.Description;

/**
 * Content provider for test viewer {@link N4IDEXpectView}
 */
class XpectContentProvider implements ITreeContentProvider {

	XpectContentProvider() {
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Description) {
			return ((Description) inputElement).getChildren().toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return ((Description) parentElement).getChildren().toArray();
	}

	@Override
	public Object getParent(Object element) {

		// freakin xpect initialization error
		if (element instanceof Description == false) {
			return null;
		}
		return ((Description) element).getTestClass();

	}

	@Override
	public boolean hasChildren(Object element) {
		return !((Description) element).getChildren().isEmpty();
	}
}
