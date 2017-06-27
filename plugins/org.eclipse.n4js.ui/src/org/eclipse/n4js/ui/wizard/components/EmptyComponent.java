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

/**
 * An empty placeholder component
 *
 */
public class EmptyComponent extends WizardComponent {
	/**
	 * Creates a new empty component.
	 */
	public EmptyComponent(WizardComponentContainer container) {
		super(container);
		WizardComponentUtils.emptyGridCell(container.getComposite());
		WizardComponentUtils.emptyGridCell(container.getComposite());
		WizardComponentUtils.emptyGridCell(container.getComposite());
	}

	@Override
	public void setFocus() {
		// Nothing to focus
	}

}
