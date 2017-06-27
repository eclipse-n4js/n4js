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
package org.eclipse.n4js.n4mf.ui.wizard;

import static com.google.common.base.CharMatcher.BREAKING_WHITESPACE;
import static com.google.common.base.CharMatcher.JAVA_LETTER;
import static com.google.common.base.CharMatcher.is;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.n4js.n4mf.ProjectType.API;
import static org.eclipse.n4js.n4mf.ProjectType.LIBRARY;
import static org.eclipse.n4js.n4mf.ProjectType.TEST;
import static org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy.getProjectId;
import static org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy.getProjectType;
import static org.eclipse.n4js.n4mf.ui.internal.N4MFActivator.ORG_ECLIPSE_N4JS_N4MF_N4MF;
import static org.eclipse.n4js.n4mf.ui.wizard.N4MFProjectInfo.IMPLEMENTATION_ID_PROP_NAME;
import static org.eclipse.n4js.n4mf.ui.wizard.N4MFProjectInfo.IMPLEMENTED_PROJECTS_PROP_NAME;
import static org.eclipse.n4js.n4mf.ui.wizard.N4MFProjectInfo.PROJECT_TYPE_PROP_NAME;
import static org.eclipse.jface.databinding.viewers.ViewersObservables.observeSingleSelection;
import static org.eclipse.jface.layout.GridDataFactory.fillDefaults;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.MULTI;
import static org.eclipse.swt.SWT.Modify;
import static org.eclipse.swt.SWT.READ_ONLY;
import static org.eclipse.xtext.util.Strings.toFirstUpper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.inject.Injector;

import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.ui.internal.N4MFActivator;

/**
 * Wizard page for configuring a new N4JS project.
 */
public class N4MFWizardNewProjectCreationPage extends WizardNewProjectCreationPage {

	private final N4MFProjectInfo projectInfo;

	// RegEx pattern for valid vendor IDs. See terminal ID rule in the N4MF grammar.
	private static final Pattern VENDOR_ID_PATTERN = Pattern.compile("\\^?[A-Za-z\\_][A-Za-z_\\-\\.0-9]*");

	/**
	 * Creates a new wizard page to set up and create a new N4JS project with the given project info model.
	 *
	 * @param projectInfo
	 *            the project info model that will be used to initialize the new N4JS project.
	 */
	public N4MFWizardNewProjectCreationPage(final N4MFProjectInfo projectInfo) {
		super(N4MFWizardNewProjectCreationPage.class.getName());
		this.projectInfo = projectInfo;
		setTitle("N4JS Project");
		setDescription("Create a new N4JS project.");
	}

	@Override
	public boolean canFlipToNextPage() {
		// Only allow page flipping for test projects
		return isPageComplete() && TEST.equals(projectInfo.getProjectType());
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent); // We need to create the UI controls from the parent class.

		final Composite control = (Composite) getControl();
		control.setLayout(GridLayoutFactory.fillDefaults().create());
		control.setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).create());

		final DataBindingContext dbc = new DataBindingContext();
		control.addDisposeListener(e -> dbc.dispose());

		createVendorIdControls(dbc, control);

		final ComboViewer projectType = new ComboViewer(control, READ_ONLY);
		projectType.setLabelProvider(new ProjectTypeLabelProvider());
		projectType.setContentProvider(ArrayContentProvider.getInstance());
		projectType.getControl().setLayoutData(fillDefaults().grab(true, false).create());
		projectType.setInput(ProjectType.values());

		// A composite to hold the changing UI component (additional library project options / additional test project
		// options)
		final Composite changingComposite = new Composite(control, NONE);
		StackLayout changingStackLayout = new StackLayout();
		changingComposite.setLayout(changingStackLayout);
		changingComposite.setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).create());

		Composite defaultOptions = initDefaultOptionsUI(dbc, changingComposite);
		Composite libraryProjectOptionsGroup = initLibraryOptionsUI(dbc, changingComposite);
		Composite testProjectOptionsGroup = initTestProjectUI(dbc, changingComposite);

		initProjectTypeBinding(dbc, projectType);

		// Configure stack layout to show advanced options
		projectType.addPostSelectionChangedListener(e -> {
			switch (projectInfo.getProjectType()) {
			case LIBRARY:
				changingStackLayout.topControl = libraryProjectOptionsGroup;
				break;
			case TEST:
				changingStackLayout.topControl = testProjectOptionsGroup;
				break;
			default:
				changingStackLayout.topControl = defaultOptions;
			}
			changingComposite.layout(true);
			setPageComplete(validatePage());
		});

		// IDs from: org.eclipse.jdt.internal.ui.workingsets.IWorkingSetIDs.class
		createWorkingSetGroup(
				(Composite) getControl(),
				null,
				new String[] { "org.eclipse.ui.resourceWorkingSetPage",
						"org.eclipse.jdt.ui.JavaWorkingSetPage",
						"org.eclipse.jdt.internal.ui.OthersWorkingSet"
				}); // $NON-NLS-1$
		Dialog.applyDialogFont(getControl());

		dbc.updateTargets();

		setControl(control);
	}

	@SuppressWarnings("unchecked")
	private void createVendorIdControls(DataBindingContext dbc, Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(GridLayoutFactory.swtDefaults().numColumns(2).create());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Label vendorIdLabel = new Label(composite, SWT.NONE);
		vendorIdLabel.setText("Vendor id:");

		final Text vendorIdText = new Text(composite, SWT.BORDER);
		vendorIdText.setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).create());

		projectInfo.addPropertyChangeListener(event -> {
			if (event.getPropertyName().equals(N4MFProjectInfo.VENDOR_ID_PROP_NAME)) {
				setPageComplete(validatePage());
			}
		});

		dbc.bindValue(WidgetProperties.text(Modify).observe(vendorIdText),
				BeanProperties.value(N4MFProjectInfo.class, N4MFProjectInfo.VENDOR_ID_PROP_NAME).observe(projectInfo));

	}

	private Composite initDefaultOptionsUI(DataBindingContext dbc, Composite parent) {
		// A group for default options
		final Group defaultOptions = new Group(parent, NONE);
		defaultOptions.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).create());

		final Button createGreeterFileButton = new Button(defaultOptions, CHECK);
		createGreeterFileButton.setText("Create a greeter file");

		initDefaultCreateGreeterBindings(dbc, createGreeterFileButton);

		return defaultOptions;
	}

	private Composite initLibraryOptionsUI(DataBindingContext dbc, Composite parent) {
		// Additional library project options
		final Group libraryProjectOptionsGroup = new Group(parent, NONE);
		libraryProjectOptionsGroup.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).create());

		emptyPlaceholder(libraryProjectOptionsGroup);

		final Button createGreeterFileButton = new Button(libraryProjectOptionsGroup, CHECK);
		createGreeterFileButton.setText("Create a greeter file");
		createGreeterFileButton.setLayoutData(GridDataFactory.fillDefaults().create());

		new Label(libraryProjectOptionsGroup, SWT.NONE).setText("Implementation ID:");
		final Text implementationIdText = new Text(libraryProjectOptionsGroup, BORDER);
		implementationIdText.setLayoutData(fillDefaults().align(FILL, SWT.CENTER).grab(true, false).create());

		final Label implementedProjectsLabel = new Label(libraryProjectOptionsGroup, SWT.NONE);
		implementedProjectsLabel.setText("Implemented projects:");
		implementedProjectsLabel
				.setLayoutData(GridDataFactory.fillDefaults().grab(false, true).align(SWT.LEFT, SWT.TOP).create());

		final ListViewer apiViewer = new ListViewer(libraryProjectOptionsGroup, BORDER | MULTI);
		apiViewer.getControl().setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).span(1, 1).create());
		apiViewer.setContentProvider(ArrayContentProvider.getInstance());
		apiViewer.setInput(getAvailableApiProjectIds());

		initApiViewerBinding(dbc, apiViewer);
		initImplementationIdBinding(dbc, implementationIdText);
		initDefaultCreateGreeterBindings(dbc, createGreeterFileButton);

		// Invalidate on change
		apiViewer.addSelectionChangedListener(e -> {
			setPageComplete(validatePage());
		});
		// Invalidate on change
		implementationIdText.addModifyListener(e -> {
			setPageComplete(validatePage());
		});

		return libraryProjectOptionsGroup;
	}

	/** Create an empty placeholder control in parent */
	private static Control emptyPlaceholder(Composite parent) {
		return new Label(parent, NONE);
	}

	private Composite initTestProjectUI(DataBindingContext dbc, Composite parent) {
		// Additional test project options
		final Group testProjectOptionsGroup = new Group(parent, NONE);
		testProjectOptionsGroup.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).create());

		final Button createTestGreeterFileButton = new Button(testProjectOptionsGroup, CHECK);
		createTestGreeterFileButton.setText("Create a test project greeter file");

		final Button addNormalSourceFolderButton = new Button(testProjectOptionsGroup, CHECK);
		addNormalSourceFolderButton.setText("Also create a non-test source folder");

		Label nextPageHint = new Label(testProjectOptionsGroup, NONE);
		nextPageHint.setText("The projects which should be tested can be selected on the next page");
		nextPageHint.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));

		initTestProjectBinding(dbc, addNormalSourceFolderButton, createTestGreeterFileButton);

		return testProjectOptionsGroup;
	}

	@Override
	protected boolean validatePage() {
		final boolean valid = super.validatePage();

		if (valid) {

			String errorMsg = null;
			final String projectName = getProjectName();
			final String vendorId = projectInfo.getVendorId();

			if (LIBRARY.equals(projectInfo.getProjectType())) {

				final String implementationId = projectInfo.getImplementationId();

				// Implementation ID is optional
				if (!isNullOrEmpty(implementationId)) {

					final List<String> implementedApis = projectInfo.getImplementedProjects();
					if (null == implementedApis || implementedApis.isEmpty()) {
						errorMsg = "One or more API project should be selected for implementation when the implementation ID is specified.";
					}

					if (BREAKING_WHITESPACE.matchesAnyOf(implementationId)) {
						errorMsg = "Implementation ID should not contain any whitespace characters.";
					}

					final char leadincChar = implementationId.charAt(0);
					if (!is('_').or(JAVA_LETTER).matches(leadincChar)) {
						errorMsg = "Implementation ID should start either an upper or a lower case character "
								+ "from the Latin alphabet or with the underscore character.";
					}
				}
			}

			if (!VENDOR_ID_PATTERN.matcher(vendorId).matches()) {
				errorMsg = "Invalid vendor id.";
			}
			if (isNullOrEmpty(vendorId)) {
				errorMsg = "Vendor id must not be empty.";
			}

			if (isNullOrEmpty(projectName)) {
				errorMsg = "Project name should be specified.";
			}

			if (BREAKING_WHITESPACE.matchesAnyOf(projectName)) {
				errorMsg = "Project name should not contain any whitespace characters.";
			}

			final char leadincChar = projectName.charAt(0);
			if (!is('_').or(JAVA_LETTER).matches(leadincChar)) {
				errorMsg = "Project name should start either an upper or a lower case character "
						+ "from the Latin alphabet or with the underscore character.";
			}

			setErrorMessage(errorMsg);
			if (null == errorMsg) {
				updateModel();
			}
			return null == errorMsg;
		}

		return valid;
	}

	/**
	 * Accessible reader from outside. Call before using the project info.
	 */
	public void updateSelectedWorkingSets() {
		projectInfo.setSelectedWorkingSets(getSelectedWorkingSets());
	}

	private void updateModel() {
		projectInfo.setProjectName(getProjectName());
		if (useDefaults()) {
			projectInfo.setProjectLocation(null);
		} else {
			projectInfo.setProjectLocation(getLocationPath());
		}
	}

	@SuppressWarnings("unchecked")
	private void initDefaultCreateGreeterBindings(DataBindingContext dbc, Button createGreeterFileButton) {
		// Bind the "create greeter file"-checkbox
		dbc.bindValue(WidgetProperties.selection().observe(createGreeterFileButton),
				BeanProperties.value(N4MFProjectInfo.class, N4MFProjectInfo.CREATE_GREETER_FILE_PROP_NAME)
						.observe(projectInfo));
	}

	@SuppressWarnings("unchecked")
	private void initTestProjectBinding(DataBindingContext dbc, Button addNormalSourceFolderButton,
			Button createTestGreeterFileButton) {
		// Bind the "normal source folder"-checkbox
		dbc.bindValue(WidgetProperties.selection().observe(addNormalSourceFolderButton),
				PojoProperties.value(N4MFProjectInfo.class, N4MFProjectInfo.ADDITIONAL_NORMAL_SOURCE_FOLDER_PROP_NAME)
						.observe(projectInfo));

		// Bind the "Create greeter file"-checkbox
		dbc.bindValue(WidgetProperties.selection().observe(createTestGreeterFileButton),
				BeanProperties.value(N4MFProjectInfo.class, N4MFProjectInfo.CREATE_GREETER_FILE_PROP_NAME)
						.observe(projectInfo));
	}

	@SuppressWarnings("unchecked")
	private void initProjectTypeBinding(final DataBindingContext dbc, final ComboViewer projectType) {
		dbc.bindValue(observeSingleSelection(projectType),
				PojoProperties.value(N4MFProjectInfo.class, PROJECT_TYPE_PROP_NAME).observe(projectInfo));
	}

	@SuppressWarnings("unchecked")
	private void initImplementationIdBinding(final DataBindingContext dbc, final Text text) {
		dbc.bindValue(WidgetProperties.text(Modify).observe(text),
				PojoProperties.value(N4MFProjectInfo.class, IMPLEMENTATION_ID_PROP_NAME).observe(projectInfo));
	}

	@SuppressWarnings("unchecked")
	private void initApiViewerBinding(DataBindingContext dbc, ListViewer apiViewer) {
		dbc.bindList(
				ViewersObservables.observeMultiSelection(apiViewer),
				PojoProperties.list(N4MFProjectInfo.class, IMPLEMENTED_PROJECTS_PROP_NAME).observe(projectInfo));
	}

	private Collection<String> getAvailableApiProjectIds() {
		final Collection<String> distinctIds = from(
				getResourceDescriptions().getExportedObjectsByType(N4mfPackage.eINSTANCE.getProjectDescription()))
						.filter(desc -> API.equals(getProjectType(desc))).transform(desc -> getProjectId(desc))
						.filter(id -> null != id).toSet();
		final List<String> ids = newArrayList(distinctIds);
		Collections.sort(ids);
		return ids;
	}

	private IResourceDescriptions getResourceDescriptions() {
		return getInjector().getInstance(IResourceDescriptions.class);
	}

	private Injector getInjector() {
		return N4MFActivator.getInstance().getInjector(ORG_ECLIPSE_N4JS_N4MF_N4MF);
	}

	/**
	 * Label provider for converting the {@link ProjectType} literal into a human readable string.
	 */
	private static final class ProjectTypeLabelProvider extends LabelProvider {
		@Override
		public String getText(final Object element) {
			if (API.equals(element)) {
				return API.getLiteral();
			}
			return getDefaultText(element);
		}

		private String getDefaultText(final Object element) {
			return toFirstUpper(nullToEmpty(super.getText(element)).replaceAll("_", " ").toLowerCase());
		}
	}

}
