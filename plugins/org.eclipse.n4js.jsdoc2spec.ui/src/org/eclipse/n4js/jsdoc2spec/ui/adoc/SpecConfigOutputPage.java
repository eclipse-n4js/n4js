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

import org.eclipse.n4js.jsdoc2spec.ui.PreferenceKeys;
import org.eclipse.n4js.jsdoc2spec.ui.SpecPage;

/**
 * Wizard page that contains all widgets to configure the html computation.
 */
public class SpecConfigOutputPage extends SpecPage {
	PerformTasksListener chkboxListener = new PerformTasksListener();
	Button chkWriteAdocFiles;

	Text txtAsciispecBinaryDir;

	/**
	 * Constructor
	 */
	SpecConfigOutputPage(String pageName) {
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
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;

		new Label(composite, SWT.NONE).setText("Asciispec Binary:");
		txtAsciispecBinaryDir = new Text(composite, SWT.BORDER);
		txtAsciispecBinaryDir.setLayoutData(gridData);
		txtAsciispecBinaryDir.addListener(SWT.Modify, chkboxListener);
		txtAsciispecBinaryDir.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event e) {
				saveAsciispecBinDir(txtAsciispecBinaryDir.getText());
			}
		});
		Button btnFileChooser = new Button(composite, SWT.PUSH);
		btnFileChooser.setText("...");
		btnFileChooser.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				selectAsciispec(e);
			}
		});

		GridData gridDataSep = new GridData();
		Label lblSeparator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		gridDataSep.horizontalSpan = 3;
		gridDataSep.horizontalAlignment = SWT.FILL;
		lblSeparator.setLayoutData(gridDataSep);

		GridData gridDataChkb = new GridData();
		gridDataChkb.horizontalSpan = 3;
		gridDataChkb.grabExcessHorizontalSpace = true;
		gridDataChkb.horizontalAlignment = SWT.FILL;

		chkWriteAdocFiles = new Button(composite, SWT.CHECK);
		chkWriteAdocFiles.setLayoutData(gridDataChkb);
		chkWriteAdocFiles.setText("Write adoc files");
		chkWriteAdocFiles.addListener(SWT.Selection, chkboxListener);

		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PreferenceKeys.EXPORTER_PREF_NODE);
		String value = prefs.get(PreferenceKeys.EXPORT_ADOC_ASCIISPEC_BIN_DIR, "");
		txtAsciispecBinaryDir.setText(value);

		setControl(composite);
		checkTasks();
	}

	/**
	 * Enables the check box for writing adoc files.
	 */
	void setChkBoxWriteAdocEnabled(boolean writeAdocFilesEnabled) {
		chkWriteAdocFiles.setSelection(writeAdocFilesEnabled);
		chkWriteAdocFiles.setEnabled(writeAdocFilesEnabled);
		checkTasks();
	}

	ConfigOutput getConfig() {
		ConfigOutput ec = new ConfigOutput(chkWriteAdocFiles.getSelection());
		return ec;
	}

	private void selectAsciispec(Event e) {
		switch (e.type) {
		case SWT.Selection:
			DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.OPEN | SWT.MULTI);
			dialog.setText("Select the Asciispec binary directory");
			String result = dialog.open();
			if (result != null && !result.isEmpty()) {
				txtAsciispecBinaryDir.setText(result);
				saveAsciispecBinDir(result);
			}
			break;
		}
	}

	private void saveAsciispecBinDir(String result) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PreferenceKeys.EXPORTER_PREF_NODE);
		prefs.put(PreferenceKeys.EXPORT_ADOC_ASCIISPEC_BIN_DIR, result);
	}

	private void checkTasks() {
		boolean doSomething = false;
		doSomething |= chkWriteAdocFiles.getSelection();
		doSomething &= txtAsciispecBinaryDir.getText() != null && !txtAsciispecBinaryDir.getText().isEmpty();

		setPageComplete(doSomething);
		getWizard().getContainer().updateButtons();
	}

	private class PerformTasksListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			checkTasks();
		}
	}

}
