/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.preferences.external;

import static org.eclipse.jface.dialogs.IDialogConstants.CANCEL_ID;
import static org.eclipse.jface.dialogs.IDialogConstants.CANCEL_LABEL;
import static org.eclipse.jface.dialogs.IDialogConstants.OK_ID;
import static org.eclipse.jface.dialogs.IDialogConstants.OK_LABEL;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.SHADOW_ETCHED_IN;
import static org.eclipse.swt.SWT.TOP;

import java.util.function.Consumer;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Custom dialog for installing npm dependencies. Allows user to specify package name and version constraint. Uses
 * custom input validators.
 */
public class InstallNpmDependencyDialog extends TitleAreaDialog {
	private static final String EMPTY = "";
	private static final String LN_DASH = "\n - ";
	private static final String PACKAGE_NAME = "Package name";
	private static final String VERSION_OPTIONAL = "Version (optional)";
	private static final String PROPERTIES_OF_NPM_DEPENDENCY = "Properties of npm dependency.";
	private static final String PROVIDE_PROPERTIES_OF_NPM_PACKAGE_TO_INSTALL = "Provide properties of npm package to install.";
	private static final String REVIEW_ISSUES = "Please review following issues:";
	private static final String VERSION_ISSUES = "Version issues: ";

	private final IInputValidator packageNameValidator;
	private final IInputValidator packageVersionValidator;
	private String errPackageName = null;
	private String errVersion = null;

	private String version;
	private String packageName;

	/** Creates dialog with custom validators. */
	public InstallNpmDependencyDialog(Shell parentShell, IInputValidator packageNameValidator,
			IInputValidator packageVersionValidator) {
		super(parentShell);
		this.packageNameValidator = packageNameValidator;
		this.packageVersionValidator = packageVersionValidator;
	}

	/**
	 * Returns string representation of the package name specified by the user.
	 *
	 * @return validated name or {@code null}
	 */
	public String getPackageName() {
		if (hasErrors())
			return null;

		return packageName;
	}

	/**
	 * Returns string representation of the version constrained specified by the user.
	 *
	 * @return validated name or {@code null}
	 */
	public String getVersionConstraint() {
		if (hasErrors())
			return null;

		return version;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	public void create() {
		super.create();
		setTitle(PROPERTIES_OF_NPM_DEPENDENCY);
		setMessage(PROVIDE_PROPERTIES_OF_NPM_PACKAGE_TO_INSTALL, IMessageProvider.INFORMATION);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, OK_ID, OK_LABEL, true);
		createButton(parent, CANCEL_ID, CANCEL_LABEL, false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		final Group customDialogArea = new Group(parent, SHADOW_ETCHED_IN);
		customDialogArea.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).create());
		customDialogArea.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).align(FILL, TOP).create());

		createNameArea(customDialogArea, PACKAGE_NAME, this::handlePackageNameInput);
		createVersionArea(customDialogArea, VERSION_OPTIONAL, this::handleVersionInput);

		return customDialogArea;
	}

	private void createVersionArea(final Group parent, String versionLabel, Consumer<String> textHandler) {
		final Composite area = createVersionArea(parent, versionLabel);
		final Composite textArea = createVersionInputArea(area);

		final Text txtUpperVersion = getSimpleTextArea(textArea);
		txtUpperVersion.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Text textWidget = (Text) e.getSource();
				textHandler.accept(textWidget.getText());
			}
		});
	}

	private Text getSimpleTextArea(Composite parent) {
		final Text text = new Text(parent, BORDER);
		text.setLayoutData(new GridData(FILL, CENTER, true, false, 1, 1));
		return text;
	}

	private Composite createVersionArea(final Composite parent, final String label) {
		final Group area = new Group(parent, SHADOW_ETCHED_IN);
		area.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).create());
		area.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		area.setText(label);
		return area;
	}

	private Composite createVersionInputArea(final Composite parent) {
		final Composite textArea = new Composite(parent, NONE);
		textArea.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).create());
		textArea.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(FILL, CENTER).create());
		return textArea;
	}

	private void createNameArea(Composite parent, String areaName, Consumer<String> textHandler) {
		final Group area = new Group(parent, SHADOW_ETCHED_IN);
		area.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).create());
		area.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		area.setText(areaName);

		final Text txtPackageName = getSimpleTextArea(area);
		txtPackageName.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Text textWidget = (Text) e.getSource();
				textHandler.accept(textWidget.getText());
			}
		});
	}

	/**
	 * Toggles enabled/disabled state of the OK button based state of the error messages.
	 */
	private void toggleOK(boolean enabled) {
		Control button = getButton(OK_ID);
		if (button != null) {
			button.setEnabled(enabled);
		}
	}

	private void handlePackageNameInput(final String userText) {
		errPackageName = packageNameValidator.isValid(userText);
		packageName = userText;
		updateErrors();
	}

	private boolean hasErrors() {
		return errPackageName != null
				|| errVersion != null;
	}

	private final void updateErrors() {
		final boolean hasNoErrors = !hasErrors();
		toggleOK(hasNoErrors);
		if (hasNoErrors) {
			setErrorMessage(null);
		} else {
			StringBuilder sb = new StringBuilder();
			if (errPackageName != null)
				sb.append(LN_DASH).append(errPackageName);

			if (errVersion != null)
				sb.append(LN_DASH).append(errVersion);

			setErrorMessage(REVIEW_ISSUES + sb);
		}
	}

	private void handleVersionInput(final String userText) {
		errVersion = null;

		// allow no value or just whitespace (which we ignore)
		String preprocessed = userText == null ? EMPTY : userText.trim();
		// if there is actual content do real parsing
		if (!preprocessed.isEmpty()) {
			String validateResult = validate(preprocessed);
			if (validateResult != null) {
				errVersion = VERSION_ISSUES + validateResult;
			}
		}

		this.version = preprocessed;
		updateErrors();
	}

	private String validate(final String data) {
		String result = null;
		// allow no value or just whitespace (which we ignore)
		String preprocessed = data == null ? EMPTY : data.trim();
		// if there is actual content do real parsing
		if (!preprocessed.isEmpty()) {
			result = packageVersionValidator.isValid(preprocessed);
		}

		return result;
	}

}
