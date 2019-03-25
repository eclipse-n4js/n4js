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

import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.TOP;
import static org.eclipse.swt.SWT.WRAP;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.BinariesProvider;
import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * Preference page for configuring {@code Node.js} and {@code npm} binaries.
 */
public class BinariesPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Unique ID of the preference page.
	 */
	public static final String ID = BinariesPreferencePage.class.getName();

	private static final long VALIDATION_DELAY_MS = 300L;

	private static final int DESCRIPTION_H_HINT = 400;

	@Inject
	private BinariesPreferenceStore store;

	@Inject
	private BinariesProvider binariesProvider;

	@Override
	public void init(final IWorkbench workbench) {
		noDefaultAndApplyButton();
	}

	@Override
	protected Control createContents(final Composite parent) {

		Composite body = new Composite(parent, NONE);
		body.setLayout(GridLayoutFactory.fillDefaults().create());
		body.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).align(FILL, FILL).create());

		final Iterable<Binary> binaries = binariesProvider.getRegisteredBinaries();

		for (final Binary binary : binaries) {
			final Group binaryGroup = new Group(body, SWT.SHADOW_ETCHED_IN);
			binaryGroup.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(true).create());
			binaryGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(FILL, TOP).create());
			recursiveAddBinaryGroup(binaryGroup, binary);
		}

		refreshErrorMessage();

		return body;
	}

	/**
	 * Adds group with controls for a given binary to the given parent. Recursive call, for a given binary it will
	 * invoke itself on its {@link Binary#getChildren()}.
	 *
	 * @param parent
	 *            parent to which group will be added.
	 * @param binary
	 *            for which group will be added.
	 */
	private void recursiveAddBinaryGroup(Composite parent, final Binary binary) {
		final Group binaryGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
		binaryGroup.setLayout(GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).create());
		binaryGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(FILL, TOP).create());
		binaryGroup.setText(binary.getLabel());
		final DirectoryFieldEditor editor = new DirectoryFieldEditor("", "Path:", binaryGroup);
		final URI path = store.getPath(binary);
		if (null != path) {
			final File file = new File(path);
			editor.setStringValue(file.getAbsolutePath());
		}
		editor.setPropertyChangeListener(event -> {
			if (null != event) {
				if (FieldEditor.VALUE.equals(event.getProperty())) {
					updateStoreState(binary, event.getNewValue());
				}
			}
		});
		final Text text = editor.getTextControl(binaryGroup);
		final ModifyListener modifyListener = new ModifyListener() {

			private Timer timer;

			@Override
			public void modifyText(ModifyEvent e) {
				if (null != timer) {
					timer.cancel();
					timer = null;
				}
				timer = new Timer("'" + binary.getLabel() + "' binary validation thread");
				final String newValue = text.getText();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						updateStoreState(binary, newValue);
					}

				}, VALIDATION_DELAY_MS);
			}
		};
		text.addModifyListener(modifyListener);
		text.addDisposeListener(e -> text.removeModifyListener(modifyListener));

		final String description = binary.getDescription();
		if (null != description) {
			final Label descriptionLabel = new Label(binaryGroup, WRAP);
			descriptionLabel.setText(description);
			final GridData gridData = new GridData(FILL, TOP, true, false, 3, 1);
			gridData.widthHint = DESCRIPTION_H_HINT;
			descriptionLabel.setLayoutData(gridData);
		}

		for (Binary child : binary.getChildren()) {
			recursiveAddBinaryGroup(parent, child);
		}
	}

	private void updateStoreState(final Binary binary, final Object newValue) {
		if (newValue instanceof String && !Strings.isNullOrEmpty((String) newValue)) {
			final File file = new File(String.valueOf(newValue));
			store.setPath(binary, file.toURI());
		} else {
			store.setPath(binary, null);
		}
		store.save();
		refreshErrorMessage();
	}

	private void refreshErrorMessage() {
		final List<String> binariesErrors = binariesProvider.validateBinaries();
		UIUtils.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				final Control control = getControl();
				if (null != control && !control.isDisposed()) {
					if (!binariesErrors.isEmpty()) {
						setErrorMessage(Joiner.on("\n").join(binariesErrors));
					} else {
						setErrorMessage(null);
					}
				}
			}

		});
	}

}
