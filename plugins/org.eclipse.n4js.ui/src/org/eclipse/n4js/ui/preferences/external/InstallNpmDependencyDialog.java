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
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.END;
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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.eclipse.n4js.external.version.VersionConstraintFormatUtil;
import org.eclipse.n4js.ui.utils.DelegatingSelectionAdapter;

/**
 * Custom dialog for installing npm dependencies. Allows user to specify package name and version constraint. Uses
 * custom input validators.
 */
public class InstallNpmDependencyDialog extends TitleAreaDialog {
	private static final String EMPTY = "";
	private static final String LN_DASH = "\n - ";
	private static final String INCLUSIVE = "Inclusive";
	private static final String PACKAGE_NAME = "Package name";
	private static final String MINIMUM_VERSION_OPTIONAL = "Minimum version (optional)";
	private static final String MAXIMUM_VERSION_OPTIONAL = "Maximum version (optional)";
	private static final String PROPERTIES_OF_NPM_DEPENDENCY = "Properties of npm dependency.";
	private static final String PROVIDE_PROPERTIES_OF_NPM_PACKAGE_TO_INSTALL = "Provide properties of npm package to install.";

	private static final String REVIEW_ISSUES = "Please review following issues:";
	private static final String LOWER_VERSION_ISSUES = "Lower version issues: ";
	private static final String UPPER_VERSION_ISSUES = "Upper version issues: ";
	private static final String NO_MINIMUM_VERSION_MAXIMUM_IS_SPECIFIED = LOWER_VERSION_ISSUES
			+ "Cannot have no minimum version if maximum is specified.";
	private static final String MINIMUM_VERSION_IS_MISSING = UPPER_VERSION_ISSUES
			+ "Minimum version is missing.";

	private final IInputValidator packageNameValidator;
	private final IInputValidator packageVersionValidator;
	private String errPackageName = null;
	private String errLowerVersion = null;
	private String errUpperVersion = null;

	private String upperVersion;
	private String lowerVersion;
	private String packageName;
	private boolean isLowerExcluded;
	private boolean isUpperExcluded;

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

		if (upperVersion == null || upperVersion.isEmpty())
			return VersionConstraintFormatUtil.npmVersionFormat(lowerVersion);
		else
			return VersionConstraintFormatUtil.npmRangeFormat(
					lowerVersion, isLowerExcluded,
					upperVersion, isUpperExcluded);

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
		createVersionArea(customDialogArea, MINIMUM_VERSION_OPTIONAL, this::handleLowerVersionInput,
				this::setLowerExcluded);
		createVersionArea(customDialogArea, MAXIMUM_VERSION_OPTIONAL, this::handleUpperVersionInput,
				this::setUpperExcluded);

		return customDialogArea;
	}

	private void createVersionArea(final Group parent, String versionLabel, Consumer<String> textHandler,
			Consumer<Boolean> flagHandler) {
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

		createVersionInclsivnessArea(area, flagHandler);
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

	/**
	 * Creates area with two radio buttons for version being exclusive / inclusive. State of the buttons is mutually
	 * exclusive i.e. only one can be enabled (ensured by SWT handling of the radio button group).
	 *
	 *
	 * @param parent
	 *            the parent in which group is created with buttons is created
	 */
	private void createVersionInclsivnessArea(final Composite parent, Consumer<Boolean> setData) {
		final Group radioArea = new Group(parent, SHADOW_ETCHED_IN);
		radioArea.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).create());
		radioArea.setLayoutData(GridDataFactory.fillDefaults().grab(false, false).align(END, TOP).create());

		final Button inclusive = new Button(radioArea, CHECK);
		inclusive.setText(INCLUSIVE);
		inclusive.setSelection(true);

		// ignore events just get value from button
		inclusive.addSelectionListener(new DelegatingSelectionAdapter((SelectionEvent event) -> {
			setData.accept(inclusive.getSelection());
		}, (SelectionEvent event) -> {
			setData.accept(inclusive.getSelection());
		}));

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
				|| errLowerVersion != null
				|| errUpperVersion != null;
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

			if (errLowerVersion != null)
				sb.append(LN_DASH).append(errLowerVersion);

			if (errUpperVersion != null)
				sb.append(LN_DASH).append(errUpperVersion);

			setErrorMessage(REVIEW_ISSUES + sb);
		}
	}

	private void handleLowerVersionInput(final String userText) {
		errLowerVersion = null;

		// allow no value or just whitespace (which we ignore)
		String pereprocessed = userText == null ? EMPTY : userText.trim();
		// if there is actual content do real parsing
		if (!pereprocessed.isEmpty()) {
			if (MINIMUM_VERSION_IS_MISSING.equals(errUpperVersion)) {
				errUpperVersion = null;
			}
			String validateResult = validate(pereprocessed);
			if (validateResult != null) {
				errLowerVersion = LOWER_VERSION_ISSUES + validateResult;
			}
		} else {
			// if lower is missing, upper has to be missing
			final String upper = upperVersion == null ? EMPTY : upperVersion.trim();
			if (!upper.isEmpty()) {
				errLowerVersion = NO_MINIMUM_VERSION_MAXIMUM_IS_SPECIFIED;
			}
		}

		this.lowerVersion = pereprocessed;
		updateErrors();
	}

	private void handleUpperVersionInput(final String userText) {
		errUpperVersion = null;

		// allow no value or just whitespace (which we ignore)
		String pereprocessed = userText == null ? EMPTY : userText.trim();
		// if there is actual content do real parsing
		if (!pereprocessed.isEmpty()) {
			String validateResult = validate(pereprocessed);
			if (validateResult != null) {
				errUpperVersion = UPPER_VERSION_ISSUES + validateResult;
			} else {
				// if upper version is valid check interaction with lower
				final String lower = lowerVersion == null ? EMPTY : lowerVersion.trim();
				if (lower.isEmpty()) {
					errUpperVersion = MINIMUM_VERSION_IS_MISSING;
				}
			}
		}

		this.upperVersion = pereprocessed;
		updateErrors();
	}

	private String validate(final String data) {
		String result = null;
		// allow no value or just whitespace (which we ignore)
		String pereprocessed = data == null ? EMPTY : data.trim();
		// if there is actual content do real parsing
		if (!pereprocessed.isEmpty()) {
			result = packageVersionValidator.isValid(pereprocessed);
		}

		return result;
	}

	private void setLowerExcluded(boolean value) {
		isLowerExcluded = value;
	}

	private void setUpperExcluded(boolean value) {
		isUpperExcluded = value;
	}

}
