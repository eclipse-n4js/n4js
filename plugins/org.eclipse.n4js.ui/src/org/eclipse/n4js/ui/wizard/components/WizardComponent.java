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
 * A wizard component is a bundle of UI and data binding which can be placed in a {@link WizardComponentContainer}.
 *
 * A wizard component can be specialized to only work with a certain type of model.
 *
 */
public abstract class WizardComponent {

	private final WizardComponentContainer container;

	/**
	 * Creates a new WizardComponent inside given container
	 */
	public WizardComponent(WizardComponentContainer container) {
		this.container = container;
	}

	/**
	 * Returns the DataBindingContext used in the component scope
	 */
	public DataBindingContext getDataBindingContext() {
		return container.getDataBindingContext();
	}

	/**
	 * Returns the component's container
	 */
	public WizardComponentContainer getContainer() {
		return container;
	}

	/**
	 * Returns the containers swt composite
	 */
	public Composite getParentComposite() {
		return container.getComposite();
	}

	/**
	 * Focuses the primary widget of this component
	 */
	public abstract void setFocus();

}
