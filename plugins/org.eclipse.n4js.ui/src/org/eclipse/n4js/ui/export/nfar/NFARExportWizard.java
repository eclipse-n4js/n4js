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
package org.eclipse.n4js.ui.export.nfar;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 */
public class NFARExportWizard extends Wizard implements IExportWizard {
	private IStructuredSelection selection;

	private NFARExportWizardPage mainPage;

	// This is evil but we cannot do something about it since the wizard page accepts a constructor parameter
	@Inject
	private Injector inject;

	@Inject
	private void injectDialogSettings(IDialogSettings settings) {
		IDialogSettings section = settings.getSection("NFARExportWizard");//$NON-NLS-1$
		if (section == null) {
			section = settings.addNewSection("NFARExportWizard");//$NON-NLS-1$
		}
		setDialogSettings(section);
	}

	/*
	 * (non-Javadoc) Method declared on IWizard.
	 */
	@Override
	public void addPages() {
		super.addPages();
		mainPage = new NFARExportWizardPage(selection);
		inject.injectMembers(mainPage);
		addPage(mainPage);
	}

	/*
	 * (non-Javadoc) Method declared on IWorkbenchWizard.
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		this.selection = currentSelection;

		setWindowTitle(NFARExportMessages.WizardTitle_export);
		setDefaultPageImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				"org.eclipse.ui.ide", "$nl$/icons/full/wizban/exportzip_wiz.png"));//$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	/*
	 * (non-Javadoc) Method declared on IWizard.
	 */
	@Override
	public boolean performFinish() {
		return mainPage.finish();
	}
}
