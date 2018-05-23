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
package org.eclipse.n4js.xpect.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

/**
 * Create Main Tab
 */
public class LaunchConfigurationMainTab extends AbstractLaunchConfigurationTab {

	private static final String MAIN_TAB_NAME = "Main";
	private Text fileText;
	private Button fileButton;

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 **/
	@Override
	public void createControl(Composite parent) {
		Font font = parent.getFont();
		Composite comp = createComposite(parent, font, 1, 1, GridData.FILL_BOTH);
		createFileGroup(comp);
		setControl(comp);

	}

	private void createFileGroup(Composite parent) {
		Group fileGroup = new Group(parent, SWT.NONE);
		fileGroup.setText("File");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		fileGroup.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		fileGroup.setLayout(layout);
		fileGroup.setFont(parent.getFont());

		fileText = new Text(fileGroup, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		fileText.setFont(parent.getFont());
		fileText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		fileButton = createPushButton(fileGroup, "Search...", null); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseFiles();
			}
		});
	}

	/***
	 * Open a resource chooser to select a file
	 **/
	protected void browseFiles() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(getShell(), root, IResource.FILE);
		dialog.setTitle("Search File");
		if (dialog.open() == Window.OK) {
			Object[] files = dialog.getResult();
			IFile file = (IFile) files[0];
			fileText.setText(file.getFullPath().toString());
		}

	}

	/**
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 **/
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// nop
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse .debug.core.ILaunchConfiguration)
	 **/
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {

		try {
			String path = null;
			path = configuration.getAttribute(XpectRunConfiguration.XT_FILE_TO_RUN, "");
			if (path != null) {
				fileText.setText(path);
			}
		} catch (CoreException e) {
			setErrorMessage(e.getMessage());
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 **/
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String file = fileText.getText().trim();
		if (file.length() == 0) {
			file = null;
		}
		configuration.setAttribute(XpectRunConfiguration.XT_FILE_TO_RUN, file);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 **/
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		setMessage(null);
		// TODO fix LaunchConfigurationMainTab validation
		// String text = fileText.getText();
		// if (text.length() > 0) {
		// IPath path = new Path(text);
		// if (ResourcesPlugin.getWorkspace().getRoot().findMember(path) ==
		// null) {
		// setErrorMessage("Specified file does not exist");
		// return false;
		// }
		// } else {
		// setMessage("Specify an file");
		// }
		return true;
	}

	@SuppressWarnings("javadoc")
	public static Composite createComposite(Composite parent, Font font, int columns, int hspan, int fill) {
		Composite g = new Composite(parent, SWT.NONE);
		g.setLayout(new GridLayout(columns, false));
		g.setFont(font);
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 **/
	@Override
	public String getName() {
		return MAIN_TAB_NAME;
	}

}
