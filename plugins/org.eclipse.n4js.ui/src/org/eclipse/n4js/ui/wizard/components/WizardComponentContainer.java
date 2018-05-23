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
package org.eclipse.n4js.ui.wizard.components;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;

/**
 * A container for wizard components.
 *
 * Wizard component containers are SWT composites with three column grids. A component is meant to use one row of such a
 * layout.
 *
 */
public interface WizardComponentContainer {

	/**
	 * Returns the global data binding context
	 */
	public DataBindingContext getDataBindingContext();

	/**
	 * Returns the SWT composite which is associated with the container
	 *
	 * <p>
	 * This is always a column with a 3 column grid layout.
	 * </p>
	 */
	public Composite getComposite();

}
