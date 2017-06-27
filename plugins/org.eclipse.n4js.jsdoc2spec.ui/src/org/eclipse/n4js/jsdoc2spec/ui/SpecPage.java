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

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;

/**
 * Provides a listener acceptor for its visibility property.
 */
public abstract class SpecPage extends WizardPage {
	/**
	 * The interface provides the {@link #isVisibleChanged(boolean)} method.
	 */
	static public interface VisibilityChangedListener {
		/**
		 * Whenever the {@link DialogPage#setVisible(boolean)} is invoked, the event is triggered. Also, this method is
		 * invoked in its own async thread to interact with SWT widgets.
		 */
		public void isVisibleChanged(boolean visible);
	}

	private VisibilityChangedListener visibilityListener;

	/**
	 * Inherited super constructor.
	 */
	protected SpecPage(String pageName) {
		super(pageName);
	}

	/**
	 * Sets the listener.
	 */
	public void setChangeListener(VisibilityChangedListener listener) {
		this.visibilityListener = listener;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visibilityListener == null)
			return;

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				visibilityListener.isVisibleChanged(visible);
			}
		});
	}

	@Override
	public IWizardPage getPreviousPage() {
		return getWizard().getPreviousPage(this);
	}
}
