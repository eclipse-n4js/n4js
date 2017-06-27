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
package org.eclipse.n4js.jsdoc2spec.ui.adoc;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.n4js.jsdoc2spec.ui.PreferenceKeys;
import org.eclipse.n4js.jsdoc2spec.ui.SpecPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Wizard page that contains all widgets to configure the adoc computation.
 */
public class SpecConfigAdocPage extends SpecPage {
	MyCheckboxListener chkboxListener = new MyCheckboxListener();
	Button chkGenAdoc;

	Text txtDocRootDirName;

	/**
	 * Constructor
	 */
	SpecConfigAdocPage(String pageName) {
		super(pageName);
		setPageComplete(false);
		setMessage("Which tasks do you want to perform?");
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, SWT.NONE);

		composite.setLayout(new GridLayout(3, false));
		GridData gridData = new GridData();

		Label lblDocRoot = new Label(composite, SWT.NONE);
		lblDocRoot.setText("Target Folder (contains 'api-gen'):");

		txtDocRootDirName = new Text(composite, SWT.BORDER);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		txtDocRootDirName.setLayoutData(gridData);
		txtDocRootDirName.addListener(SWT.Modify, chkboxListener);
		txtDocRootDirName.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event e) {
				saveProperty(txtDocRootDirName.getText());
			}
		});
		Button btnDocRootDir = new Button(composite, SWT.PUSH);
		btnDocRootDir.setText("...");
		btnDocRootDir.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				selectDocRoot(e);
			}
		});

		Label lblCopyrightFileInfo = new Label(composite, SWT.NONE);
		GridData gridDataCRH = new GridData();
		gridDataCRH.horizontalSpan = 3;
		gridDataCRH.horizontalAlignment = SWT.FILL;
		lblCopyrightFileInfo.setLayoutData(gridDataCRH);
		lblCopyrightFileInfo.setText(
				"Note: In case the target folder contains the file copyrightheader.adoc, its contents will be preprended to every generated file.");

		Label lblSeparator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridDataSep = new GridData();
		gridDataSep.horizontalSpan = 3;
		gridDataSep.horizontalAlignment = SWT.FILL;
		lblSeparator.setLayoutData(gridDataSep);

		GridData gridDataChkb = new GridData();
		gridDataChkb.horizontalSpan = 3;
		gridDataChkb.grabExcessHorizontalSpace = true;
		gridDataChkb.horizontalAlignment = SWT.FILL;

		chkGenAdoc = new Button(composite, SWT.CHECK);
		chkGenAdoc.setLayoutData(gridDataChkb);
		chkGenAdoc.setText("Generate adoc files");
		chkGenAdoc.addListener(SWT.Selection, chkboxListener);
		chkGenAdoc.setSelection(true);

		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PreferenceKeys.EXPORTER_PREF_NODE);
		String value = prefs.get(PreferenceKeys.EXPORT_ADOC_ROOT_DIR, "");
		txtDocRootDirName.setText(value);
		checkPageComplete();

		setControl(composite);
	}

	ConfigAdoc getConfig() {
		ConfigAdoc ca = new ConfigAdoc(txtDocRootDirName.getText(), chkGenAdoc.getSelection());
		return ca;
	}

	private void selectDocRoot(Event e) {
		switch (e.type) {
		case SWT.Selection:
			DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.OPEN | SWT.MULTI);
			dialog.setText("Select the documentation root directory");
			String result = dialog.open();
			if (result != null && !result.isEmpty()) {
				txtDocRootDirName.setText(result);
				saveProperty(result);
				checkPageComplete();
			}
			break;
		}
	}

	private void saveProperty(String result) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PreferenceKeys.EXPORTER_PREF_NODE);
		prefs.put(PreferenceKeys.EXPORT_ADOC_ROOT_DIR, result);
	}

	private void checkPageComplete() {
		boolean pageComplete = txtDocRootDirName.getText() != null && !txtDocRootDirName.getText().isEmpty();
		setPageComplete(pageComplete);
		getWizard().getContainer().updateButtons();
	}

	private class MyCheckboxListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			checkPageComplete();
		}
	}

}
