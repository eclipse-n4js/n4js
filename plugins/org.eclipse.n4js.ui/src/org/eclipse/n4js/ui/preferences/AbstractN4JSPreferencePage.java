/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Originally copied from org.eclipse.xtext.ui.preferences.PropertyAndPreferencePage
 * 		in bundle org.eclipse.xtext.ui
 * 		available under the terms of the Eclipse Public License 2.0
 * 		Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 *
 *  which has been itself copied from org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage
 *      in bundle org.eclipse.jdt.ui
 *      available under the terms of the Eclipse Public License 2.0
 *      Copyright (c) 2000, 2011 IBM Corporation and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 *
 */
package org.eclipse.n4js.ui.preferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ControlEnableState;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.generator.CompilerProperties;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.utils.ComponentDescriptor;
import org.eclipse.n4js.utils.IComponentProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage;
import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;
import org.eclipse.xtext.ui.editor.tasks.dialogfields.DialogField;
import org.eclipse.xtext.ui.editor.tasks.dialogfields.IDialogFieldListener;
import org.eclipse.xtext.ui.editor.tasks.dialogfields.LayoutUtil;
import org.eclipse.xtext.ui.editor.tasks.dialogfields.SelectionButtonDialogField;
import org.eclipse.xtext.ui.preferences.ProjectSelectionDialog;
import org.eclipse.xtext.ui.preferences.StatusInfo;
import org.eclipse.xtext.util.Triple;

import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 */
@SuppressWarnings("restriction")
public abstract class AbstractN4JSPreferencePage<DESCR_TYPE extends ComponentDescriptor> extends
		AbstractPreferencePage
		implements Comparator<Triple<String, String, DESCR_TYPE>> {

	/** project in case of project specific preferences, set in {@link #setElement(IAdaptable)} */
	protected IProject project;
	/** flag indicating that project specific preferences have been changed */
	protected boolean projectSpecificChanged = false;

	/** copied from PropertyAndPreferencePage */
	private Composite parentComposite;
	/** copied from PropertyAndPreferencePage */
	private SelectionButtonDialogField useProjectSettings;
	/** copied from PropertyAndPreferencePage */
	protected ControlEnableState blockEnableState;
	/** copied from PropertyAndPreferencePage */
	private Control configurationBlockControl;
	/** copied from PropertyAndPreferencePage */
	private Link changeWorkspaceSettings;
	/** copied from PropertyAndPreferencePage */
	protected final IStatus blockStatus;
	/** copied from PropertyAndPreferencePage */
	protected Map<Object, Object> pageData;
	/** copied from PropertyAndPreferencePage */
	public static final String DATA_NO_LINK = "N4JSBuilderPreferencePage.nolink";
	/** copied from OptionsConfigurationBlock */
	public static final String IS_PROJECT_SPECIFIC = "is_project_specific";
	/** copied from OptionsConfigurationBlock */
	private Map<String, String> disabledProjectSettings;
	private ComponentDetailsFieldEditor field = null;
	private Map<String, ValueDifference<String>> entriesDiffering = null;
	@Inject
	@Named(Constants.LANGUAGE_NAME)
	private String languageName;
	@Inject
	private IDialogSettings dialogSettings;

	@Inject
	private PreferenceStoreAccessImpl preferenceStoreAccessImpl;
	/**
	 * This property has been copied and adapted from org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock.
	 */
	protected static final String REBUILD_COUNT_KEY = "preferences_build_requested";

	final List<Triple<String, String, DESCR_TYPE>> components;

	/**
	 * Creates the page with given components, project is set to nul by default.
	 */
	public AbstractN4JSPreferencePage(
			ArrayList<Triple<String, String, DESCR_TYPE>> components) {
		this.components = components;
		blockStatus = new StatusInfo();
		blockEnableState = null;
		project = null;
		pageData = null;
		entriesDiffering = null;
	}

	/**
	 * Returns properties of configured component.
	 */
	protected abstract IComponentProperties<DESCR_TYPE>[] getComponentPropertiesValues();

	/**
	 * This method has been inspired by
	 * org.eclipse.xtext.ui.editor.syntaxcoloring.SyntaxColoringPreferencePage#refreshAttributes.
	 */
	protected abstract void refreshAttributes();

	/**
	 * The field have to use OUTPUT_PREFERENCE_TAG as name as the chain of field editor make up the full qualified name
	 * of the field editors contained by this field editor
	 */
	private ComponentDetailsFieldEditor doCreateField(IPreferenceStore preferenceStore) {
		return new ComponentDetailsFieldEditor(
				CompilerProperties.OUTPUT_PREFERENCE_TAG,
				"Registered compilers", getFieldEditorParent(), preferenceStore, components) {

			@Override
			protected IComponentProperties<?>[] getComponentProperties() {
				return getComponentPropertiesValues();
			}

		};
	}

	/** copied from PropertyAndPreferencePage */
	@Override
	protected Label createDescriptionLabel(Composite parent) {
		parentComposite = parent;
		if (isProjectPreferencePage()) {
			Composite composite = new Composite(parent, SWT.NONE);
			composite.setFont(parent.getFont());
			GridLayout layout = new GridLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.numColumns = 2;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			IDialogFieldListener listener = new IDialogFieldListener() {
				@Override
				public void dialogFieldChanged(DialogField dialogField) {
					boolean enabled = ((SelectionButtonDialogField) dialogField).isSelected();
					enableProjectSpecificSettings(enabled);
					projectSpecificChanged = true;

					if (enabled && getData() != null) {
						applyData(getData());
					}
				}
			};

			useProjectSettings = new SelectionButtonDialogField(SWT.CHECK);
			useProjectSettings.setDialogFieldListener(listener);
			useProjectSettings
					.setLabelText(
							org.eclipse.xtext.ui.preferences.Messages.PropertyAndPreferencePage_useprojectsettings_label);
			useProjectSettings.doFillIntoGrid(composite, 1);
			LayoutUtil.setHorizontalGrabbing(useProjectSettings.getSelectionButton(null));

			if (offerLink()) {
				changeWorkspaceSettings = createLink(composite,
						org.eclipse.xtext.ui.preferences.Messages.PropertyAndPreferencePage_useworkspacesettings_change);
				changeWorkspaceSettings.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
			} else {
				LayoutUtil.setHorizontalSpan(useProjectSettings.getSelectionButton(null), 2);
			}

			Label horizontalLine = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
			horizontalLine.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1));
			horizontalLine.setFont(composite.getFont());
		} else if (supportsProjectSpecificOptions() && offerLink()) {
			changeWorkspaceSettings = createLink(
					parent,
					org.eclipse.xtext.ui.preferences.Messages.PropertyAndPreferencePage_showprojectspecificsettings_label);
			changeWorkspaceSettings.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));
		}

		return super.createDescriptionLabel(parent);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		composite.setFont(parent.getFont());

		GridData data = new GridData(GridData.FILL, GridData.FILL, true, true);

		configurationBlockControl = super.createContents(composite);
		configurationBlockControl.setLayoutData(data);

		if (isProjectPreferencePage()) {
			boolean useProjectSpecificSettings = hasProjectSpecificOptions();
			enableProjectSpecificSettings(useProjectSpecificSettings);
		}

		Dialog.applyDialogFont(composite);
		return composite;
	}

	/** copied from PropertyAndPreferencePage */
	@SuppressWarnings("unchecked")
	@Override
	public void applyData(Object data) {
		if (data instanceof Map) {
			pageData = (Map<Object, Object>) data;
		}
		if (changeWorkspaceSettings != null) {
			if (!offerLink()) {
				changeWorkspaceSettings.dispose();
				parentComposite.layout(true, true);
			}
		}
	}

	@Override
	protected void createFieldEditors() {
		refreshAttributes();
		IPreferenceStore preferenceStore = preferenceStoreAccessImpl.getWritablePreferenceStore(getProject());
		field = doCreateField(preferenceStore);
		addField(field);
	}

	/**
	 * Process changes, e.g., checks whether a re-build is necessary. Default implementation does nothing, has to be
	 * overridden by subclasses.
	 *
	 * @param container
	 *            the container used by subclasses, e.g., to register a build job
	 */
	protected boolean processChanges(IWorkbenchPreferenceContainer container) {
		return true;
	}

	@Override
	public boolean performOk() {
		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		if (!processChanges(container)) {
			return false;
		}
		boolean retVal = super.performOk();

		if (retVal && isProjectPreferencePage()) {
			try {
				IPreferenceStore preferenceStore = preferenceStoreAccessImpl.getWritablePreferenceStore(getProject());
				if (preferenceStore instanceof IPersistentPreferenceStore) {
					((IPersistentPreferenceStore) preferenceStore).save();
				}
			} catch (Exception e) {
				System.err.println(e);
				retVal = false;
			}
		}
		return retVal;
	}

	/** copied from PropertyAndPreferencePage */
	private static void applyToStatusLine(DialogPage page, IStatus status) {
		String message = status.getMessage();
		if (message != null && message.length() == 0) {
			message = null;
		}
		switch (status.getSeverity()) {
		case IStatus.OK:
			page.setMessage(message, IMessageProvider.NONE);
			page.setErrorMessage(null);
			break;
		case IStatus.WARNING:
			page.setMessage(message, IMessageProvider.WARNING);
			page.setErrorMessage(null);
			break;
		case IStatus.INFO:
			page.setMessage(message, IMessageProvider.INFORMATION);
			page.setErrorMessage(null);
			break;
		default:
			page.setMessage(null);
			page.setErrorMessage(message);
			break;
		}
	}

	/**
	 * If the preference store is persistable, it will serialized here.
	 *
	 * This method has been copied and adapted from org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock.
	 */
	protected void savePreferences() {
		try {
			if (getPreferenceStore() instanceof IPersistentPreferenceStore) {
				((IPersistentPreferenceStore) getPreferenceStore()).save();
			}
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, N4JSActivator.getInstance().getBundle().getSymbolicName(),
					"Unexpected internal error: ", e); //$NON-NLS-1$
			N4JSActivator.getInstance().getLog().log(status);
		}
	}

	/**
	 * @return a map keyed by the preference store key (e.g. outlet.es5.autobuilding for compiler (resp. output
	 *         configuration) with name 'es5' and property 'autobuilding') containing old and new value. Only keys whose
	 *         values has been changed are included. This map contains the changes of all registered compilers
	 */
	public Map<String, ValueDifference<String>> getPreferenceChanges() {
		if (entriesDiffering == null) {
			for (Triple<String, String, DESCR_TYPE> compiler : components) {
				DESCR_TYPE compilerDescriptor = compiler.getThird();
				String outputName = compiler.getFirst();
				boolean initialLoad = false;

				@SuppressWarnings("unchecked")
				DESCR_TYPE currentlyStoredCompilerDescriptor = (DESCR_TYPE) compilerDescriptor
						.getCurrentlyStoredComponentDescriptor();
				if (currentlyStoredCompilerDescriptor == null) {
					currentlyStoredCompilerDescriptor = compilerDescriptor; // default configuration
					initialLoad = true;
				}

				Map<String, String> originalSettings = currentlyStoredCompilerDescriptor.fillMap(outputName);

				List<IPreferenceStore> preferenceStores = field.getPreferenceStores();

				// populate
				// use a copy here has we don't want to change the default values provided by the registered compilers
				@SuppressWarnings("unchecked")
				DESCR_TYPE newCompilerDescriptor = (DESCR_TYPE) compilerDescriptor.copy();

				for (IPreferenceStore preferenceStore : preferenceStores) {

					setDescriptorValuesFromPreferences(preferenceStore, outputName, newCompilerDescriptor);
				}

				Map<String, String> currentSettings = newCompilerDescriptor.fillMap(outputName);

				Map<String, ValueDifference<String>> changes = getPreferenceChanges(originalSettings, currentSettings);

				if (!initialLoad) {
					compilerDescriptor.setChanges(changes);
				}
				compilerDescriptor.setCurrentlyStoredComponentDescriptor(newCompilerDescriptor);
			}
			entriesDiffering = new HashMap<>();
			for (Triple<String, String, DESCR_TYPE> compiler : components) {
				entriesDiffering.putAll(compiler.getThird().getChanges());
			}
		}
		return entriesDiffering;
	}

	private void setDescriptorValuesFromPreferences(IPreferenceStore preferenceStore, String outputName,
			DESCR_TYPE newCompilerDescriptor) {
		for (IComponentProperties<DESCR_TYPE> prop : getComponentPropertiesValues()) {
			if (preferenceStore.contains(prop.getKey(outputName))) {
				if (prop.getType() == Boolean.class) {
					prop.setValueInCompilerDescriptor(newCompilerDescriptor, outputName,
							preferenceStore.getBoolean(prop.getKey(outputName)));
				} else { // String
					prop.setValueInCompilerDescriptor(newCompilerDescriptor, outputName,
							preferenceStore.getString(prop.getKey(outputName)));
				}
			}
		}
	}

	/**
	 * @param originalSettings
	 *            the settings before applying the values of the form page
	 * @param currentSettings
	 *            the settings after collecting the values of the form page
	 * @return a map keyed by the preference store key (e.g. outlet.es5.autobuilding for compiler (resp. output
	 *         configuration) with name 'es5' and property 'autobuilding') containing old and new value. Only keys whose
	 *         values has been changed are included.
	 */
	private Map<String, ValueDifference<String>> getPreferenceChanges(Map<String, String> originalSettings,
			Map<String, String> currentSettings) {
		MapDifference<String, String> mapDifference = Maps.difference(currentSettings, originalSettings);
		return mapDifference.entriesDiffering();
	}

	@Override
	public IAdaptable getElement() {
		return project;
	}

	/*
	 * Sets project via extensible adapter pattern.
	 */
	@Override
	public void setElement(IAdaptable element) {
		project = (IProject) element.getAdapter(IResource.class);
		setDescription(null); // no description for property page
	}

	/**
	 * This method has been copied from org.eclipse.xtext.ui.preferences.PropertyAndPreferencePage.
	 *
	 * @return the project in workspace associated with this preference page or null if global preference
	 */
	protected IProject getProject() {
		return project;
	}

	/** copied from PropertyAndPreferencePage */
	protected boolean isProjectPreferencePage() {
		return project != null;
	}

	/** copied from PropertyAndPreferencePage */
	protected void enableProjectSpecificSettings(boolean useProjectSpecificSettings) {
		useProjectSettings.setSelection(useProjectSpecificSettings);
		enablePreferenceContent(useProjectSpecificSettings);
		updateLinkVisibility();
		doStatusChanged();
		useProjectSpecificSettings(useProjectSpecificSettings);
	}

	/** copied from OptionsConfigurationBlock */
	private void useProjectSpecificSettings(boolean enable) {
		boolean hasProjectSpecificOption = disabledProjectSettings == null;
		if (enable != hasProjectSpecificOption && project != null) {
			IPreferenceStore preferenceStore = preferenceStoreAccessImpl.getWritablePreferenceStore(getProject());
			if (enable) {
				for (Triple<String, String, DESCR_TYPE> compiler : components) {
					for (CompilerProperties prop : CompilerProperties.values()) {
						String curr = prop.getKey(compiler.getFirst());
						String val = disabledProjectSettings.get(curr);
						preferenceStore.putValue(curr, val);
					}
				}
				disabledProjectSettings = null;
				getPreferenceStore().setValue(IS_PROJECT_SPECIFIC, true);
				try {
					getProject().setPersistentProperty(new QualifiedName(qualifiedName(), IS_PROJECT_SPECIFIC),
							String.valueOf(true));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			} else {
				disabledProjectSettings = Maps.newHashMap();
				for (Triple<String, String, DESCR_TYPE> compiler : components) {
					for (CompilerProperties prop : CompilerProperties.values()) {
						String curr = prop.getKey(compiler.getFirst());
						String oldSetting = preferenceStore.getString(curr);
						disabledProjectSettings.put(curr, oldSetting);
						preferenceStore.setToDefault(curr);
					}
				}
				getPreferenceStore().setToDefault(IS_PROJECT_SPECIFIC);
				try {
					getProject().setPersistentProperty(new QualifiedName(qualifiedName(), IS_PROJECT_SPECIFIC),
							String.valueOf(false));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** copied from PropertyAndPreferencePage */
	protected void enablePreferenceContent(boolean enable) {
		if (enable) {
			if (blockEnableState != null) {
				blockEnableState.restore();
				blockEnableState = null;
			}
		} else {
			if (blockEnableState == null) {
				blockEnableState = ControlEnableState.disable(configurationBlockControl);
			}
		}
	}

	/** copied from PropertyAndPreferencePage */
	private void updateLinkVisibility() {
		if (changeWorkspaceSettings == null || changeWorkspaceSettings.isDisposed()) {
			return;
		}

		if (isProjectPreferencePage()) {
			changeWorkspaceSettings.setEnabled(!useProjectSettings());
		}
	}

	/** copied from PropertyAndPreferencePage */
	protected void doStatusChanged() {
		if (!isProjectPreferencePage() || useProjectSettings()) {
			updateStatus(blockStatus);
		} else {
			updateStatus(new StatusInfo());
		}
	}

	/** copied from PropertyAndPreferencePage */
	protected boolean useProjectSettings() {
		return isProjectPreferencePage() && useProjectSettings != null && useProjectSettings.isSelected();
	}

	/** copied from PropertyAndPreferencePage */
	private void updateStatus(IStatus status) {
		setValid(!status.matches(IStatus.ERROR));
		applyToStatusLine(this, status);
	}

	/** copied from PropertyAndPreferencePage */
	protected Map<Object, Object> getData() {
		return pageData;
	}

	/** copied from PropertyAndPreferencePage */
	protected boolean offerLink() {
		return pageData == null || !Boolean.TRUE.equals(pageData.get(DATA_NO_LINK));
	}

	/** copied from PropertyAndPreferencePage */
	protected boolean supportsProjectSpecificOptions() {
		return true;
	}

	/** copied from PropertyAndPreferencePage */
	private Link createLink(Composite composite, String text) {
		Link link = new Link(composite, SWT.NONE);
		link.setFont(composite.getFont());
		link.setText("<A>" + text + "</A>"); //$NON-NLS-1$//$NON-NLS-2$
		link.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doLinkActivated();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				doLinkActivated();
			}
		});
		return link;
	}

	/** copied from PropertyAndPreferencePage */
	final void doLinkActivated() {
		Map<Object, Object> data = getData();
		if (data == null) {
			data = new HashMap<>();
		}
		data.put(DATA_NO_LINK, Boolean.TRUE);

		if (isProjectPreferencePage()) {
			openWorkspacePreferences(data);
		} else {
			Set<IProject> projectsWithSpecifics = Sets.newHashSet();
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (IProject proj : projects) {
				if (XtextProjectHelper.hasNature(proj) && hasProjectSpecificOptions()) {
					projectsWithSpecifics.add(proj);
				}
			}
			ProjectSelectionDialog dialog = new ProjectSelectionDialog(getShell(), projectsWithSpecifics,
					dialogSettings);
			if (dialog.open() == Window.OK) {
				IProject proj = (IProject) dialog.getFirstResult();
				openProjectProperties(proj, data);
			}
		}
	}

	/** copied from PropertyAndPreferencePage */
	protected final void openWorkspacePreferences(Object data) {
		String id = getPreferencePageID();
		PreferencesUtil.createPreferenceDialogOn(getShell(), id, new String[] { id }, data).open();
	}

	/** copied from OptionsConfigurationBlock */
	public boolean hasProjectSpecificOptions() {
		return getPreferenceStore().getBoolean(IS_PROJECT_SPECIFIC);
	}

	/** copied from PropertyAndPreferencePage */
	protected final void openProjectProperties(IProject proj, Object data) {
		String id = getPropertyPageID();
		if (id != null) {
			PreferencesUtil.createPropertyDialogOn(getShell(), proj, id, new String[] { id }, data).open();
		}
	}

	/** copied from BuilderPreferencePage */
	protected String getPreferencePageID() {
		return languageName + ".compiler.preferencePage";
	}

	/** copied from BuilderPreferencePage */
	protected String getPropertyPageID() {
		return languageName + ".compiler.propertyPage";
	}

	@Override
	public int compare(Triple<String, String, DESCR_TYPE> left, Triple<String, String, DESCR_TYPE> right) {
		return left.getSecond().compareTo(right.getSecond());
	}

}
