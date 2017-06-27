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

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;

/**
 * Base implementation of a new working set creation wizard.
 */
public abstract class WorkingSetNewWizard extends Wizard {

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	/**
	 * Returns with the new working set created by the wizard. May return with an {@link Optional#absent() absent}
	 * instance if the creation was interrupted.
	 *
	 * @return the new working set created by the wizard.
	 */
	public abstract Optional<WorkingSet> getWorkingSet();

	/**
	 * Returns with the working set manager that is being currently edited by creating the new working set instance. By
	 * default returns with the {@link WorkingSetManagerBroker#getActiveManager() active manager} from the shared
	 * {@link WorkingSetManagerBroker} instance.
	 *
	 * @return the working set manager.
	 */
	protected WorkingSetManager getManager() {
		return workingSetManagerBroker.getActiveManager();
	}

	@Override
	public Image getDefaultPageImage() {
		return ImageRef.WORKING_SET_WIZBAN.asImage().orNull();
	}

}
