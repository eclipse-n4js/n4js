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
package org.eclipse.n4js.ui.binaries;

import static org.eclipse.n4js.ui.binaries.IllegalBinaryStateDialog.createCustomAreaWithLink;
import static org.eclipse.swt.SWT.FILL;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.binaries.BinaryStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.statushandlers.DefaultDetailsArea;
import org.eclipse.ui.internal.statushandlers.InternalDialog;
import org.eclipse.ui.internal.statushandlers.WorkbenchStatusDialogManagerImpl;
import org.eclipse.ui.statushandlers.AbstractStatusAreaProvider;
import org.eclipse.ui.statushandlers.StatusAdapter;

/**
 * Status area provider for the {@link N4StatusHandler status handler} used in the N4JS IDE application.
 */
@SuppressWarnings("restriction")
public class N4StatusAreaProvider extends AbstractStatusAreaProvider {

	private final WorkbenchStatusDialogManagerImpl manager;

	/**
	 * Creates a new area provider with the given dialog state.
	 *
	 * @param manager
	 *            a map of dialog state used for the {@link InternalDialog} class.
	 */
	public N4StatusAreaProvider(final WorkbenchStatusDialogManagerImpl manager) {
		this.manager = manager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Control createSupportArea(final Composite parent, final StatusAdapter statusAdapter) {

		final IStatus status = statusAdapter.getStatus();

		if (status instanceof BinaryStatus) {
			final Binary binary = ((BinaryStatus) status).getBinary();
			final Composite control = new Composite(parent, SWT.NONE);
			control.setLayout(GridLayoutFactory.swtDefaults().margins(10, 10).create());
			control.setLayoutData(new GridData(FILL, FILL, true, true));
			return createCustomAreaWithLink(control, manager.getDialog(), binary);
		}

		return new DefaultDetailsArea(manager.getDialogState()).createSupportArea(parent, statusAdapter);
	}

}
