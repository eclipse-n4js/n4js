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
package org.eclipse.n4js.runner.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.runner.RunConfiguration;
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

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Base implementation for the main tab of Eclipse launch configurations for N4JS runners and testers.
 */
public abstract class AbstractLaunchConfigurationMainTab extends AbstractLaunchConfigurationTab {

	@Inject
	private IN4JSCore in4jsCore;

	/** Default name of this tab. */
	protected static final String MAIN_TAB_NAME = "Main";

	/** Text widget for the resource to run, test, etc. */
	protected Text txtResource;

	/** Text widget for the method to run; maybe empty */
	protected Text txtTestMethod;
	/** Text widget for the implementation ID to use. */
	protected Text txtImplementationId;

	/**
	 * Returns the "Resource to Run", using the workspace relative location retrieved via the attribute in
	 * {@link RunConfiguration#USER_SELECTION} If attribute is not present, an empty string is returned. A concrete test
	 * method are removed, use getTestMethod instead.
	 */
	public static String getResourceRunAsText(ILaunchConfiguration configuration) throws CoreException {
		String uriStr;
		uriStr = configuration.getAttribute(RunConfiguration.USER_SELECTION, "");
		final URI uri = uriStr.trim().length() > 0 ? URI.createURI(uriStr) : null;
		final String wsRelativePath = uri != null ? uri.toPlatformString(true) : null;
		return wsRelativePath != null ? wsRelativePath : "";
	}

	/**
	 * Returns the name of the class and method if the USER_SELECTION URI refers to a method. Otherwise an empty string
	 * is returned.
	 */
	public String getTestMethod(ILaunchConfiguration configuration) {
		try {
			String uriStr = configuration.getAttribute(RunConfiguration.USER_SELECTION, "");
			if (uriStr == null) {
				return "";
			}
			final URI uri = uriStr.trim().length() > 0 ? URI.createURI(uriStr) : null;
			if (uri == null) {
				return "";
			}
			final URI trimmedUri = uri.hasFragment() ? uri.trimFragment() : uri;
			final IN4JSProject project = in4jsCore.findProject(trimmedUri).orNull();
			if (project != null) {
				ResourceSet resSet = in4jsCore.createResourceSet(Optional.of(project));
				if (resSet != null) {
					EObject eobject = resSet.getEObject(uri, true);
					if (eobject instanceof N4MethodDeclaration) {
						N4MethodDeclaration method = (N4MethodDeclaration) eobject;
						String name = ((N4MethodDeclaration) eobject).getName();
						if (method.getOwner() instanceof N4ClassDeclaration) {
							return ((N4ClassDeclaration) method.getOwner()).getName() + "." + name;
						}
						return name;
					}
				}
			}
		} catch (Exception ex) {
			// be robust here
		}
		return "";

	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	@Override
	public String getName() {
		return MAIN_TAB_NAME;
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Font font = parent.getFont();
		Composite comp = createComposite(parent, font, 1, 1, GridData.FILL_BOTH);
		createResourceGroup(comp);
		createImplementationIdGroup(comp);
		setControl(comp);
	}

	/**
	 * Creates widget group containing the resource to launch, etc.
	 */
	protected void createResourceGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(getResourceLabel());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);
		group.setFont(parent.getFont());

		txtResource = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		txtResource.setLayoutData(gd);
		txtResource.setFont(parent.getFont());
		txtResource.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		Button btnSearch = createPushButton(group, "Search...", null); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String resourceStr = searchResource();
				if (resourceStr != null)
					txtResource.setText(resourceStr);
			}
		});

		txtTestMethod = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		txtTestMethod.setLayoutData(gd);
		txtTestMethod.setFont(parent.getFont());
		txtTestMethod.setEditable(false);

	}

	/**
	 * Creates widget group containing the resource to launch, etc.
	 */
	protected void createImplementationIdGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText("Implementation ID");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		group.setFont(parent.getFont());

		txtImplementationId = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		txtImplementationId.setLayoutData(gd);
		txtImplementationId.setFont(parent.getFont());
		txtImplementationId.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	/**
	 * Label for the text widget containing the resource to launch. Will also be used in some error messages; use
	 * something like "File to Run" or "Resource to Test".
	 */
	protected abstract String getResourceLabel();

	/**
	 * Returns the key in a {@link RunConfiguration} holding the main entry point of the launch. E.g.
	 * {@link RunConfiguration#USER_SELECTION}.
	 */
	protected abstract String getResourceRunConfigKey();

	/**
	 * Mask of resource types accepted by this tab as the resource to launch.
	 */
	protected abstract int getAcceptedResourceTypes();

	/**
	 * Open a resource chooser to select a resource as the main entry point for the launch.
	 */
	protected String searchResource() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(getShell(), root,
				getAcceptedResourceTypes());
		dialog.setTitle("Search " + getResourceLabel());
		if (dialog.open() == Window.OK) {
			Object[] files = dialog.getResult();
			IFile file = (IFile) files[0];
			return file.getFullPath().toString();
		}
		return null;
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// FIXME IDE-1393 move to subclass?
		configuration.setAttribute(RunConfiguration.RUNNER_ID, RunnerUiUtils.getRunnerId(configuration, true));
		configuration.setAttribute(RunConfiguration.IMPLEMENTATION_ID, (String) null);
	}

	/**
	 * From the configuration to the UI widgets.
	 *
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			final String wsRelativePath = getResourceRunAsText(configuration);
			txtResource.setText(wsRelativePath);
			String testMethod = getTestMethod(configuration);
			txtTestMethod.setText(testMethod);

			final String implId = configuration.getAttribute(RunConfiguration.IMPLEMENTATION_ID, "");
			txtImplementationId.setText(implId);
		} catch (CoreException e) {
			setErrorMessage(e.getMessage());
		}
	}

	/**
	 * From the UI widgets to the configuration.
	 *
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		final String wsRelativePath = txtResource.getText();
		final URI uri = wsRelativePath.trim().length() > 0 ? URI.createPlatformResourceURI(wsRelativePath, true) : null;
		final String uriStr = uri != null ? uri.toString() : null;
		configuration.setAttribute(getResourceRunConfigKey(), uriStr);

		final String implementationId = txtImplementationId.getText();
		final String actualImplId = implementationId.trim().length() > 0 ? implementationId.trim() : null;
		configuration.setAttribute(RunConfiguration.IMPLEMENTATION_ID, actualImplId);
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setMessage(null);
		setErrorMessage(null);

		final String uriStr;
		try {
			uriStr = launchConfig.getAttribute(getResourceRunConfigKey(), "");
		} catch (CoreException e1) {
			setErrorMessage("cannot read resource URI from launch configuration");
			return false;
		}
		if (uriStr.trim().isEmpty()) {
			setErrorMessage("launch configuration must specify a " + getResourceLabel().toLowerCase());
			return false;
		}
		final URI resourceUri = URI.createURI(uriStr, true);

		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = root.findMember(resourceUri.toPlatformString(true));
		if (resource == null) {
			setErrorMessage("unable to locate resource in workspace: " + resourceUri.toPlatformString(true));
			return false;
		}

		if (!checkResourceIsInAvailableProject(resourceUri)) {
			return false;
		}
		if (!checkResourceIsOfAcceptedType(resource)) {
			return false;
		}
		if (!checkSubclassSpecificConstraints(launchConfig, resource, resourceUri)) {
			return false;
		}

		return true;
	}

	/**
	 * Built-in default check.
	 */
	protected boolean checkResourceIsInAvailableProject(URI resourceUri) {
		final Optional<? extends IN4JSProject> oProject = in4jsCore.findProject(resourceUri);
		if (!oProject.isPresent()) {
			setErrorMessage("cannot locate N4JS project for " + resourceUri.toPlatformString(true));
			return false;
		}
		return true;
	}

	/**
	 * Built-in default check.
	 */
	protected boolean checkResourceIsOfAcceptedType(IResource resource) {
		final boolean isOfAcceptedOfType = (getAcceptedResourceTypes() & resource.getType()) != 0;
		if (!isOfAcceptedOfType) {
			setErrorMessage("resource is of wrong type"); // TODO better error message
			return false;
		}
		return true;
	}

	/**
	 * Subclasses should implement to perform more validity checks. Note that this base class is already checking
	 * several basic things (see implementation of {@link #isValid(ILaunchConfiguration)} for details).
	 */
	protected abstract boolean checkSubclassSpecificConstraints(ILaunchConfiguration launchConfig, IResource resource,
			URI resourceUri);

	@SuppressWarnings("javadoc")
	protected static Composite createComposite(Composite parent, Font font, int columns, int hspan, int fill) {
		Composite g = new Composite(parent, SWT.NONE);
		g.setLayout(new GridLayout(columns, false));
		g.setFont(font);
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}
}
