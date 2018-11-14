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
package org.eclipse.n4js.ui.wizard.project;

import static com.google.common.base.CharMatcher.breakingWhitespace;
import static com.google.common.base.CharMatcher.is;
import static com.google.common.base.CharMatcher.javaLetter;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.jface.databinding.viewers.ViewersObservables.observeSingleSelection;
import static org.eclipse.jface.layout.GridDataFactory.fillDefaults;
import static org.eclipse.n4js.projectDescription.ProjectType.API;
import static org.eclipse.n4js.projectDescription.ProjectType.LIBRARY;
import static org.eclipse.n4js.projectDescription.ProjectType.TEST;
import static org.eclipse.n4js.ui.wizard.project.N4JSProjectInfo.IMPLEMENTATION_ID_PROP_NAME;
import static org.eclipse.n4js.ui.wizard.project.N4JSProjectInfo.IMPLEMENTED_PROJECTS_PROP_NAME;
import static org.eclipse.n4js.ui.wizard.project.N4JSProjectInfo.PROJECT_TYPE_PROP_NAME;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.MULTI;
import static org.eclipse.swt.SWT.Modify;
import static org.eclipse.swt.SWT.READ_ONLY;
import static org.eclipse.xtext.util.Strings.toFirstUpper;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.packagejson.PackageJsonResourceDescriptionExtension;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
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
import org.eclipse.ui.dialogs.ExtensibleWizardNewProjectCreationPage;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.inject.Injector;

/**
 * Wizard page for configuring a new N4JS project.
 */
public class N4JSNewProjectWizardCreationPage extends ExtensibleWizardNewProjectCreationPage {

	private final N4JSProjectInfo projectInfo;

	// RegEx pattern for valid vendor IDs. See terminal ID rule in the N4MF grammar.
	private static final Pattern VENDOR_ID_PATTERN = Pattern.compile("\\^?[A-Za-z\\_][A-Za-z_\\-\\.0-9]*");

	/**
	 * Creates a new wizard page to set up and create a new N4JS project with the given project info model.
	 *
	 * @param projectInfo
	 *            the project info model that will be used to initialize the new N4JS project.
	 */
	public N4JSNewProjectWizardCreationPage(final N4JSProjectInfo projectInfo) {
		super(N4JSNewProjectWizardCreationPage.class.getName());
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

		ComboViewer projectTypeCombo = new ComboViewer(control, READ_ONLY);
		projectTypeCombo.setLabelProvider(new ProjectTypeLabelProvider());
		projectTypeCombo.setContentProvider(ArrayContentProvider.getInstance());
		projectTypeCombo.getControl().setLayoutData(fillDefaults().grab(true, false).create());
		projectTypeCombo.setInput(ProjectType.values());

		Composite projectTypePropertyControls = new Composite(control, NONE);
		StackLayout changingStackLayout = new StackLayout();
		projectTypePropertyControls.setLayout(changingStackLayout);
		projectTypePropertyControls.setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).create());

		Composite defaultOptions = initDefaultOptionsUI(dbc, projectTypePropertyControls);
		Composite libraryProjectOptionsGroup = initLibraryOptionsUI(dbc, projectTypePropertyControls);
		Composite testProjectOptionsGroup = initTestProjectUI(dbc, projectTypePropertyControls);

		initProjectTypeBinding(dbc, projectTypeCombo);

		// Configure stack layout to show advanced options
		projectTypeCombo.addPostSelectionChangedListener(e -> {
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
			projectTypePropertyControls.layout(true);
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

	@Override
	public String getProjectName() {
		return ProjectDescriptionUtils.getPlainProjectName(super.getProjectName());
	}

	@Override
	public IProject getProjectHandle() {
		// make sure the validated project handle considers the eclipse representation
		// of scoped projects
		final String eclipseProjectName = ProjectDescriptionUtils
				.convertN4JSProjectNameToEclipseProjectName(getProjectName());
		return ResourcesPlugin.getWorkspace().getRoot().getProject(
				eclipseProjectName);
	}

	/**
	 * Returns the full project name including the scope (e.g. {@code @scope/*}) prefix.
	 */
	protected String getProjectNameWithScope() {
		return super.getProjectName();
	}

	@Override
	@SuppressWarnings("restriction")
	protected void setLocationForSelection() {
		// use scoped project name for project location
		locationArea.updateProjectName(getProjectNameWithScope());
	}

	@SuppressWarnings("unchecked")
	private void createVendorIdControls(DataBindingContext dbc, Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(GridLayoutFactory.swtDefaults().numColumns(2).create());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Label vendorIdLabel = new Label(composite, SWT.NONE);
		vendorIdLabel.setText("Vendor id:");

		Text vendorIdText = new Text(composite, SWT.BORDER);
		vendorIdText.setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).create());

		projectInfo.addPropertyChangeListener(event -> {
			if (event.getPropertyName().equals(N4JSProjectInfo.VENDOR_ID_PROP_NAME)) {
				setPageComplete(validatePage());
			}
		});

		dbc.bindValue(WidgetProperties.text(Modify).observe(vendorIdText),
				BeanProperties.value(N4JSProjectInfo.class, N4JSProjectInfo.VENDOR_ID_PROP_NAME).observe(projectInfo));

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
		libraryProjectOptionsGroup
				.setLayout(GridLayoutFactory.fillDefaults().margins(12, 5).numColumns(2).equalWidth(false).create());

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
		apiViewer.setInput(getAvailableApiProjectNames());

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
		final boolean valid = validateProjectNameNonEmpty()
				&& super.validatePage()
				&& validateIsExistingProjectPath()
				&& validateProjectName()
				&& validateVendorId()
				&& validateImplementationId();

		if (valid) {
			// clear error messages
			setErrorMessage(null);
			// update model only if validation passed successfully
			updateModel();
		}

		return valid;
	}

	/** Returns {@code true} iff the specified project name is not considered empty. */
	private boolean validateProjectNameNonEmpty() {
		final String fullProjectName = getProjectNameWithScope();
		final String scopeName = ProjectDescriptionUtils.getScopeName(fullProjectName);
		final String plainProjectName = ProjectDescriptionUtils.getPlainProjectName(fullProjectName);

		if (fullProjectName.isEmpty()) {
			setErrorMessage(null);
			setMessage("Project name must be specified");
			return false;
		}

		if (scopeName != null && (scopeName.isEmpty() || scopeName.equals("@"))) {
			setErrorMessage("The scope segment of the project name must not be empty.");
			return false;
		}

		if (plainProjectName != null && plainProjectName.isEmpty()) {
			setErrorMessage("The name segment of the project name must not be empty.");
			return false;
		}

		return true;
	}

	/** Returns {@code true} iff the specified project name is valid. */
	private boolean validateProjectName() {
		final String projectName = getProjectNameWithScope();
		final String plainProjectName = getProjectName();
		final String scopeName = ProjectDescriptionUtils.getScopeName(projectName);

		if (scopeName != null && !ProjectDescriptionUtils.isValidScopeName(scopeName)) {
			setErrorMessage(
					"Invalid project name: \"" + scopeName + "\" is not a valid scope segment.");
			return false;
		}

		if (!ProjectDescriptionUtils.isValidPlainProjectName(plainProjectName)) {
			setErrorMessage(
					"Invalid project name: \"" + plainProjectName
							+ "\" is not a valid name segment.");
			return false;
		}

		if (breakingWhitespace().matchesAnyOf(projectName)) {
			setErrorMessage("Project name should not contain any whitespace characters.");
			return false;
		}

		if (isNullOrEmpty(projectName)) {
			setErrorMessage("Project name should be specified.");
			return false;
		}

		return true;
	}

	/** Returns {@code true} iff the vendor ID is valid. */
	private boolean validateVendorId() {
		final String vendorId = projectInfo.getVendorId();
		if (isNullOrEmpty(vendorId)) {
			setErrorMessage("Vendor id must not be empty.");
			return false;
		}

		if (!VENDOR_ID_PATTERN.matcher(vendorId).matches()) {
			setErrorMessage("Invalid vendor id.");
			return false;
		}
		return true;
	}

	/** Returns {@code true} iff the implementation ID is valid. */
	private boolean validateImplementationId() {
		if (LIBRARY.equals(projectInfo.getProjectType())) {
			final String implementationId = projectInfo.getImplementationId();

			// Implementation ID is optional
			if (!isNullOrEmpty(implementationId)) {
				final char leadingChar = implementationId.charAt(0);
				if (!is('_').or(javaLetter()).matches(leadingChar)) {
					setErrorMessage("Implementation ID should start either an upper or a lower case character "
							+ "from the Latin alphabet or with the underscore character.");
					return false;
				}
				final List<String> implementedApis = projectInfo.getImplementedProjects();
				if (null == implementedApis || implementedApis.isEmpty()) {
					setErrorMessage(
							"One or more API project should be selected for implementation when the implementation ID is specified.");
					return false;
				}

				if (breakingWhitespace().matchesAnyOf(implementationId)) {
					setErrorMessage("Implementation ID should not contain any whitespace characters.");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Accessible reader from outside. Call before using the project info.
	 */
	public void updateSelectedWorkingSets() {
		projectInfo.setSelectedWorkingSets(getSelectedWorkingSets());
	}

	private void updateModel() {
		// use eclipse project name in model
		final String eclipseProjectName = ProjectDescriptionUtils
				.convertN4JSProjectNameToEclipseProjectName(getProjectNameWithScope());
		projectInfo.setProjectName(eclipseProjectName);

		final boolean isScoped = getProjectNameWithScope() != getProjectName();
		if (useDefaults() && isScoped) {
			projectInfo.setProjectLocation(getLocationPath().append(getProjectNameWithScope()));
		} else if (useDefaults()) {
			projectInfo.setProjectLocation(null);
		} else {
			projectInfo.setProjectLocation(getLocationPath());
		}
	}

	@SuppressWarnings("unchecked")
	private void initDefaultCreateGreeterBindings(DataBindingContext dbc, Button createGreeterFileButton) {
		// Bind the "create greeter file"-checkbox
		dbc.bindValue(WidgetProperties.selection().observe(createGreeterFileButton),
				BeanProperties.value(N4JSProjectInfo.class, N4JSProjectInfo.CREATE_GREETER_FILE_PROP_NAME)
						.observe(projectInfo));
	}

	@SuppressWarnings("unchecked")
	private void initTestProjectBinding(DataBindingContext dbc, Button addNormalSourceFolderButton,
			Button createTestGreeterFileButton) {
		// Bind the "normal source folder"-checkbox
		dbc.bindValue(WidgetProperties.selection().observe(addNormalSourceFolderButton),
				PojoProperties.value(N4JSProjectInfo.class, N4JSProjectInfo.ADDITIONAL_NORMAL_SOURCE_FOLDER_PROP_NAME)
						.observe(projectInfo));

		// Bind the "Create greeter file"-checkbox
		dbc.bindValue(WidgetProperties.selection().observe(createTestGreeterFileButton),
				BeanProperties.value(N4JSProjectInfo.class, N4JSProjectInfo.CREATE_GREETER_FILE_PROP_NAME)
						.observe(projectInfo));
	}

	@SuppressWarnings("unchecked")
	private void initProjectTypeBinding(final DataBindingContext dbc, final ComboViewer projectType) {
		dbc.bindValue(observeSingleSelection(projectType),
				PojoProperties.value(N4JSProjectInfo.class, PROJECT_TYPE_PROP_NAME).observe(projectInfo));
	}

	@SuppressWarnings("unchecked")
	private void initImplementationIdBinding(final DataBindingContext dbc, final Text text) {
		dbc.bindValue(WidgetProperties.text(Modify).observe(text),
				PojoProperties.value(N4JSProjectInfo.class, IMPLEMENTATION_ID_PROP_NAME).observe(projectInfo));
	}

	@SuppressWarnings("unchecked")
	private void initApiViewerBinding(DataBindingContext dbc, ListViewer apiViewer) {
		dbc.bindList(
				ViewersObservables.observeMultiSelection(apiViewer),
				PojoProperties.list(N4JSProjectInfo.class, IMPLEMENTED_PROJECTS_PROP_NAME).observe(projectInfo));
	}

	/**
	 * Checks whether the specified project path points to an existing project and sets an according error message.
	 *
	 * Returns <code>true</code> otherwise.
	 *
	 * This method assumes that {@link #getProjectName()} returns a valid project name.
	 */
	private boolean validateIsExistingProjectPath() {
		IPath projectLocation = getLocationPath();
		final String projectName = getProjectName();

		// if workspace is project location (default location)
		if (projectLocation.equals(Platform.getLocation())) {
			// add project name since #getLocationPath does not return the full project location
			projectLocation = projectLocation.append(projectName);
		}

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		boolean workspaceProjectExists = root.getProject(projectName).exists();

		// check for an existing project description file
		IPath projectDescriptionPath = projectLocation.append(IN4JSProject.PACKAGE_JSON);
		File existingProjectDescriptionFile = new File(projectDescriptionPath.toString());

		// check for an existing file with the path of the project folder
		File existingFileAtProjectDirectory = new File(projectLocation.toString());
		boolean projectDirectoryIsExistingFile = existingFileAtProjectDirectory.isFile();
		boolean isExistingNonWorkspaceProject = existingProjectDescriptionFile.exists() && !workspaceProjectExists;

		if (projectDirectoryIsExistingFile) {
			// set error message if there is already at the specified project location
			setErrorMessage("There already exists a file at the location '" + projectLocation.toString() + "'.");
			return false;
		} else if (isExistingNonWorkspaceProject) {
			// set error message if the specified directory already represents an N4JS project
			setErrorMessage(
					"There already exists an N4JS project at the specified location. Please use 'File > Import...' to add it to the workspace.");
			return false;
		} else {
			// otherwise the project location does not exist yet
			return true;
		}
	}

	private Collection<String> getAvailableApiProjectNames() {
		final Collection<String> distinctIds = from(
				getResourceDescriptions().getExportedObjectsByType(JSONPackage.Literals.JSON_DOCUMENT))
						.filter(desc -> API.equals(PackageJsonResourceDescriptionExtension.getProjectType(desc)))
						.transform(desc -> PackageJsonResourceDescriptionExtension.getProjectName(desc))
						.filter(id -> null != id)
						.toSet();
		final List<String> ids = newArrayList(distinctIds);
		Collections.sort(ids);
		return ids;
	}

	private IResourceDescriptions getResourceDescriptions() {
		return getInjector().getInstance(IResourceDescriptions.class);
	}

	private Injector getInjector() {
		return N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
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
