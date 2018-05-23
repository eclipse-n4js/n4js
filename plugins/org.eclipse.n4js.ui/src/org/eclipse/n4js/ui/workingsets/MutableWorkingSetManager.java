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

/**
 * Configurable working set representation. Working sets can be added and removed from the current manager. Besides that
 * existing working sets can be edited as well.
 */
public interface MutableWorkingSetManager extends WorkingSetManager {

	/**
	 * Creates a new wizard for creating a new working set for the current working set manager.
	 *
	 * @return the wizard for creating a new working set instance.
	 */
	WorkingSetNewWizard createNewWizard();

	/**
	 * Creates a new wizard for editing an existing working set of the current working set manager.
	 *
	 * @return the wizard for editing an existing working set instance.
	 */
	WorkingSetEditWizard createEditWizard();

}
