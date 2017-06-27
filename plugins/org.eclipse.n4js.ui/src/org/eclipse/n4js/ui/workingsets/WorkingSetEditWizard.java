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
package org.eclipse.n4js.ui.workingsets;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import com.google.common.base.Optional;

/**
 * Base wizard implementation for editing an existing working set.
 */
public abstract class WorkingSetEditWizard extends WorkingSetNewWizard implements IWorkbenchWizard {

	private WorkingSet editedWorkingSet;

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		if (selection != null && !selection.isEmpty()) {
			final Object firstElement = selection.getFirstElement();
			if (firstElement instanceof WorkingSet) {
				editedWorkingSet = (WorkingSet) firstElement;
			}
		}
	}

	/**
	 * Returns with the edited working set if it was initialized from the actual selection.
	 *
	 * @return the edited working set. Could be {@link Optional#absent() absent}.
	 */
	protected Optional<WorkingSet> getEditedWorkingSet() {
		return Optional.fromNullable(editedWorkingSet);
	}

}
