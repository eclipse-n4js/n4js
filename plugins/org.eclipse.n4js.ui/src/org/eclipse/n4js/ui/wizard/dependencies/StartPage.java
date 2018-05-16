/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.wizard.dependencies;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Simple start page. Provides user with description of the performed actions. Additionally serves as starting point of
 * long running operation executed when next page is displayed. This makes user experience much better for large
 * workspaces.
 */
public class StartPage extends WizardPage {
	private static final String NAME = "Start Page";
	private static final String DESCRIPTION = "Setup External Libraries : Start Page";

	private static final String OPERATIONS_DESCRIPTION = "This wizard setups external libraries for the whole workspace.\n"
			+ "In general process is as follows:\n"
			+ "  1.  turn off autobuild (if needed)\n"
			+ "  2.  clean npm cache (optional)\n"
			+ "  3.  reset type definitions\n"
			+ "  4.  for every missing dependency\n"
			+ "  4a.   run npm install\n"
			+ "  4b.   add manifest (if needed)\n"
			+ "  4c.   add type definitions (if needed)\n"
			+ "  5.  run build on all installed libraries\n"
			+ "  6.  turn on autobuild (if it was on in step 1)\n";

	/** Simple constructor. */
	public StartPage() {
		super(NAME);
		setTitle(NAME);
		setDescription(DESCRIPTION);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(container, SWT.NONE).setText(OPERATIONS_DESCRIPTION);

		// required to avoid exception thrown in {@code org.eclipse.jface.wizard.Wizard.createPageControls(Composite)}
		setControl(container);
		setPageComplete(true);

	}
}
