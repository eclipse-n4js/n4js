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

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.valueOf;
import static org.eclipse.n4js.projectDescription.ProjectType.API;
import static org.eclipse.n4js.projectDescription.ProjectType.LIBRARY;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.xtext.ui.wizard.DefaultProjectInfo;

/**
 * Simple POJO model for storing the N4JS project setup configuration.
 */
public class N4JSProjectInfo extends DefaultProjectInfo {

	/** The name of the project location property name. Used by JFace data binding. */
	public static final String PROJECT_LOCATION_PROP_NAME = "projectLocation";

	/** The name of the project type property name. Used by JFace data binding. */
	public static final String PROJECT_TYPE_PROP_NAME = "projectType";

	/** Property to specify the selected working set */
	public static final String SELECTED_WORKING_SET_PROP_NAME = "selectedWorkingSet";

	/** Name of the implementation ID property. Used by SWT data binding. */
	public static final String IMPLEMENTATION_ID_PROP_NAME = "implementationId";

	/** Name of the implementation ID property. Used by SWT data binding. */
	public static final String IMPLEMENTED_PROJECTS_PROP_NAME = "implementedProjects";

	/** Tested Project of a test project */
	public static final String TESTED_PROJECT_PROP_NAME = "testedProjects";

	/** Property to specify whether a test project should have an additional normal source folder */
	public static final String ADDITIONAL_NORMAL_SOURCE_FOLDER_PROP_NAME = "additionalSourceFolder";

	/** Property to specify to project dependencies */
	public static final String PROJECT_DEPENDENCIES_PROP_NAME = "projectDependencies";

	/** Property to specify to project devDependencies */
	public static final String PROJECT_DEV_DEPENDENCIES_PROP_NAME = "projectDevDependencies";

	/** Property to specify the output folder */
	public static final String OUTPUT_FOLDER_PROP_NAME = "outputFolder";

	/** Property to specify the source folders */
	public static final String SOURCE_FOLDERS_PROP_NAME = "sourceFolders";

	/** Property to specify the external source folders */
	public static final String EXTERNAL_SOURCE_FOLDERS_PROP_NAME = "externalSourceFolders";

	/** Property to specify the test source folders */
	public static final String TEST_SOURCE_FOLDERS_PROP_NAME = "testSourceFolders";

	/** Property to specify whether a greeter file should be created */
	public static final String CREATE_GREETER_FILE_PROP_NAME = "createGreeterFile";

	/** Property to specify the vendor id */
	public static final String VENDOR_ID_PROP_NAME = "vendorId";

	/** The default value for the vendor id */
	public static final String DEFAULT_VENDOR_ID = "org.eclipse.n4js";

	/** The custom project location. {@code null} if there is not custom project location set. */
	private IPath projectLocation;

	/** The type of the N4JS project. By default: {@link ProjectType#LIBRARY system}. */
	private ProjectType projectType = LIBRARY;

	/** The vendor id of the project */
	private String vendorId = DEFAULT_VENDOR_ID;

	/** The optional implementation ID. */
	private String implementationId;

	/** The implemented API project IDs. */
	private List<String> implementedProjects = newArrayList();

	/** WorkingSets the new Project will be included to. */
	private IWorkingSet[] selectedWorkingSets;

	/** The tested project in case of a test project */
	private List<String> testedProjects = new ArrayList<>();

	/** Specifies whether a test project should have an additional normal source folder */
	private boolean additionalSourceFolder;

	/** Specifies whether a greeter file should be created */
	private boolean createGreeterFile;

	/** The list of project dependencies */
	private List<String> projectDependencies = new ArrayList<>();

	/** The list of project devDependencies */
	private List<String> projectDevDependencies = new ArrayList<>();

	/** The output folder of the project */
	private String outputFolder;

	/** Different source folders of the project */
	private List<String> sourceFolders = new ArrayList<>();
	private List<String> externalSourceFolders = new ArrayList<>();
	private List<String> testSourceFolders = new ArrayList<>();

	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	/**
	 * @param listener
	 *            listener to be called on every change of any property
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @param listener
	 *            remove listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 *            bean name of the property
	 * @param newValue
	 *            new value of the property
	 * @param oldValue
	 *            old value of the property
	 */
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		this.changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * Returns with the project type as a lower camel case formatted string.
	 *
	 * This can be used directly for the N4JS package.json.
	 *
	 * @return the project type for the package.json file.
	 */
	public String getProjectTypeForProjectDescription() {
		if (API.equals(projectType)) {
			return API.getLiteral();
		}
		return UPPER_UNDERSCORE.to(LOWER_CAMEL, valueOf(projectType));
	}

	/**
	 * Returns with the project location path. Can be {@code null}.
	 *
	 * @return the project location path.
	 */
	public IPath getProjectLocation() {
		return projectLocation;
	}

	/**
	 * Counterpart of {@link #getProjectLocation()}. Set the project location as the given argument.
	 *
	 * @param projectLocation
	 *            the project location to set.
	 */
	public void setProjectLocation(IPath projectLocation) {
		firePropertyChange(PROJECT_LOCATION_PROP_NAME, this.projectLocation, this.projectLocation = projectLocation);
	}

	/**
	 * Returns with the {@link ProjectType project type}.
	 *
	 * @return the project type.
	 */
	public ProjectType getProjectType() {
		return projectType;
	}

	/**
	 * Counterpart of the {@link #getProjectType()}. Sets the project type for the desired argument value.
	 *
	 * @param projectType
	 *            the project type.
	 */
	public void setProjectType(ProjectType projectType) {
		firePropertyChange(PROJECT_TYPE_PROP_NAME, this.projectType, this.projectType = projectType);
	}

	/**
	 * Returns the selected working set
	 */
	public IWorkingSet[] getSelectedWorkingSets() {
		return selectedWorkingSets;
	}

	/**
	 * Sets the selected working set
	 */
	public void setSelectedWorkingSets(IWorkingSet[] selectedWorkingSets) {
		firePropertyChange(SELECTED_WORKING_SET_PROP_NAME, this.selectedWorkingSets,
				this.selectedWorkingSets = selectedWorkingSets);
	}

	/**
	 * Returns with the implementation ID. Optional, can be {@code null}.
	 *
	 * @return the implementation ID, or {@code null} if not specified.
	 */
	public String getImplementationId() {
		return implementationId;
	}

	/**
	 * Counterpart of the {@link #getImplementationId()}.
	 *
	 * @param implementationId
	 *            the desired implementation ID value. Optional, can be {@code null}.
	 */
	public void setImplementationId(String implementationId) {
		firePropertyChange(IMPLEMENTATION_ID_PROP_NAME, this.implementationId,
				this.implementationId = implementationId);
	}

	/**
	 * Returns with a list of API projects IDs to implement.
	 *
	 * @return a list of API project project IDs.
	 */
	public List<String> getImplementedProjects() {
		return implementedProjects;
	}

	/**
	 * Counterpart of {@link #getImplementedProjects()}.
	 *
	 * @param implementedProjects
	 *            the list of implemented API project IDs to set.
	 */
	public void setImplementedProjects(List<String> implementedProjects) {
		firePropertyChange(IMPLEMENTED_PROJECTS_PROP_NAME, this.implementedProjects,
				this.implementedProjects = implementedProjects);
	}

	/**
	 * Returns the tested project for a test project
	 */
	public List<String> getTestedProjects() {
		return this.testedProjects;
	}

	/**
	 * Sets the tested project for a test project
	 */
	public void setTestedProjects(List<String> testedProjects) {
		firePropertyChange(TESTED_PROJECT_PROP_NAME, this.testedProjects, this.testedProjects = testedProjects);
	}

	/**
	 * Returns whether a test project should have an additional normal source folder.
	 *
	 * Does not hold any useful information if project type is not test.
	 */
	public boolean getAdditionalSourceFolder() {
		return additionalSourceFolder;
	}

	/**
	 * Sets whether a test project should have an additional normal source folder.
	 */
	public void setAdditionalSourceFolder(boolean additionalSourceFolder) {
		firePropertyChange(ADDITIONAL_NORMAL_SOURCE_FOLDER_PROP_NAME, this.additionalSourceFolder,
				this.additionalSourceFolder = additionalSourceFolder);
	}

	/**
	 * Returns the project dependencies of the project.
	 *
	 * Note: The return value is a mutable reference.
	 */
	public List<String> getProjectDependencies() {
		return projectDependencies;
	}

	/**
	 * Sets the project dependencies of the project.
	 */
	public void setProjectDependencies(List<String> projectDependencies) {
		firePropertyChange(PROJECT_DEPENDENCIES_PROP_NAME, this.projectDependencies,
				this.projectDependencies = projectDependencies);
	}

	/**
	 * Returns the project devDependencies of the project.
	 *
	 * Note: The return value is a mutable reference.
	 */
	public List<String> getProjectDevDependencies() {
		return projectDevDependencies;
	}

	/**
	 * Sets the project devDependencies of the project.
	 */
	public void setProjectDevDependencies(List<String> projectDevDependencies) {
		firePropertyChange(PROJECT_DEPENDENCIES_PROP_NAME, this.projectDevDependencies,
				this.projectDevDependencies = projectDevDependencies);
	}

	/**
	 * Returns the output folder of the project.
	 */
	public String getOutputFolder() {
		return outputFolder;
	}

	/**
	 * Sets the output folder of the project.
	 */
	public void setOutputFolder(String outputFolder) {
		firePropertyChange(OUTPUT_FOLDER_PROP_NAME, this.outputFolder, this.outputFolder = outputFolder);
	}

	/**
	 * Returns the source folders of the project.
	 *
	 * Note: The return value is a mutable reference.
	 */
	public List<String> getSourceFolders() {
		return sourceFolders;
	}

	/**
	 * Sets the source folders of the project
	 */
	public void setSourceFolders(List<String> sourceFolders) {
		firePropertyChange(SOURCE_FOLDERS_PROP_NAME, this.sourceFolders, this.sourceFolders = sourceFolders);
	}

	/**
	 * Returns the external source folders of the project.
	 *
	 * Note: The return value is a mutable reference.
	 *
	 */
	public List<String> getExternalSourceFolders() {
		return externalSourceFolders;
	}

	/**
	 * Sets the source folders of the project.
	 *
	 */
	public void setExternalSourceFolders(List<String> externalSourceFolders) {
		firePropertyChange(EXTERNAL_SOURCE_FOLDERS_PROP_NAME, this.externalSourceFolders,
				this.externalSourceFolders = externalSourceFolders);
	}

	/**
	 * Returns the test source folders of the project.
	 *
	 * Note: The return value is a mutable reference.
	 */
	public List<String> getTestSourceFolders() {
		return testSourceFolders;
	}

	/**
	 * Sets the test source folders of the project.
	 */
	public void setTestSourceFolders(List<String> testSourceFolders) {
		firePropertyChange(TEST_SOURCE_FOLDERS_PROP_NAME, this.testSourceFolders,
				this.testSourceFolders = testSourceFolders);
	}

	/**
	 * Returns whether a test project greeter file should be created.
	 *
	 */
	public boolean getCreateGreeterFile() {
		return createGreeterFile;
	}

	/**
	 * Sets whether a test project greeter file should be created.
	 */
	public void setCreateGreeterFile(boolean createTestGreeterFile) {
		firePropertyChange(CREATE_GREETER_FILE_PROP_NAME, this.createGreeterFile,
				this.createGreeterFile = createTestGreeterFile);
	}

	/**
	 * Returns the vendor id the the project.
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * Sets the vendor id of the project.
	 *
	 */
	public void setVendorId(String vendorId) {
		firePropertyChange(VENDOR_ID_PROP_NAME, this.vendorId, this.vendorId = vendorId);
	}

}
