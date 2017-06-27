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
package org.eclipse.n4js.ui.wizard.interfaces;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.google.inject.Inject;

import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.wizard.classifiers.N4JSNewClassifierWizard;
import org.eclipse.n4js.ui.wizard.generator.WorkspaceWizardGenerator;

/**
 * Wizard for creating new N4JS interfaces.
 */
public class N4JSNewInterfaceWizard extends N4JSNewClassifierWizard<N4JSInterfaceWizardModel> {

	@Inject
	private N4JSInterfaceWizardModel model;

	@Inject
	private N4JSNewInterfaceWizardGenerator generator;

	@Inject
	private N4JSNewInterfaceWizardPage wizardPage;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection, boolean nested) {
		setDefaultPageImageDescriptor(ImageRef.NEW_INTERFACE_WIZBAN.asImageDescriptor().orNull());
		super.init(workbench, selection, nested);
	}

	@Override
	public IDialogSettings getDialogSettings() {
		return N4JSActivator.getInstance().getDialogSettings();
	}

	@Override
	protected N4JSInterfaceWizardModel getModel() {
		return model;
	}

	@Override
	protected WorkspaceWizardGenerator<N4JSInterfaceWizardModel> getGenerator() {
		return generator;
	}

	@Override
	protected N4JSNewInterfaceWizardPage getPage() {
		return wizardPage;
	}
}
