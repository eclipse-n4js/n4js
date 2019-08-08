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
package org.eclipse.n4js.ui.wizard.workspace;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * An abstract wizard model validator for {@link WorkspaceWizardModel}s
 *
 * Subclasses may implement additional validation logic by overriding {@link #prepare()} and {@link #validate()}.
 *
 * As the {@link #setModel(WorkspaceWizardModel)} method remains exposed when subclassing, subclasses need to handle the
 * case of a too generic model themselves. This means whenever a validator isn't meant to also validate
 * {@link WorkspaceWizardModel}s, it needs to manually prohibit the use of this method.
 *
 */
public abstract class WorkspaceWizardModelValidator<M extends WorkspaceWizardModel> {

	/** The validation result property constant */
	public static final String VALIDATION_RESULT = "validationResult";
	/** The validity of the project property */
	public static final String PROJECT_PROPERTY_VALID = "projectValid";
	/** The validity of the source folder property */
	public static final String SOURCE_FOLDER_PROPERTY_VALID = "sourceFolderValid";

	private static final ValidationResult NO_MODEL_VALIDATION_RESULT = new ValidationResult("No model set");

	/**
	 * Error Messages for model validation of the {@link WorkspaceWizardModel}
	 */
	@SuppressWarnings("javadoc")
	protected static class ErrorMessages {

		// General errors
		public static final String INVALID_STATE_VALIDATION_ERROR = "Invalid state validation error";

		// Project errors
		public static final String PROJECT_DOES_NOT_EXIST = "The given project does not exist";
		public static final String INVALID_PROJECT = "Not a valid project";
		public static final String PROJECT_MUST_NOT_BE_EMPTY = "The project field must not be empty";

		// Source folder errors
		public static final String SOURCE_FOLDER_MUST_NOT_BE_EMPTY = "The source folder field must not be empty";
		public static final String SOURCE_FOLDER_IS_NOT_A_VALID_FOLDER_NAME = "The source folder is not a valid folder name";
		public static final String SOURCE_FOLDER_DOES_NOT_EXIST = "The source folder does not exist";

		// Module specifier errors
		public static final String MODULE_SPECIFIER_MUST_NOT_BE_EMPTY = "The module specifier field must not be empty";
		public static final String INVALID_MODULE_SPECIFIER_MUST_NOT_BEGIN_WITH = "Invalid module specifier. Must not begin with a \"/\" ";
		public static final String INVALID_MODULE_SPECIFIER_EMPTY_PATH_SEGMENT = "Invalid module specifier. Empty path segment:";
		public static final String INVALID_MODULE_SPECIFIER_INVALID_SEGMENT = "Invalid module specifier. Invalid segment. ";
	}

	/**
	 * Helper type for a validation results.
	 */
	public static class ValidationResult {

		/** A success status. Valid and without any message. */
		public static final ValidationResult SUCCESS = new ValidationResult();

		/**
		 * True if model content is valid
		 */
		public final boolean valid;
		/**
		 * Contains error message if result is negative
		 */
		public final String errorMessage;

		/**
		 * Initiate a successful validation result. (No errors)
		 */
		public ValidationResult() {
			this.valid = true;
			this.errorMessage = "";
		}

		/**
		 * Initiate a invalid validation result
		 *
		 * @param errorMessage
		 *            Error message to report
		 */
		public ValidationResult(String errorMessage) {
			this.valid = false;
			this.errorMessage = errorMessage;
		}

	}

	/**
	 * An exception to be thrown by validating logic in case of a validation error.
	 */
	protected static class ValidationException extends Exception {

		private final String propertyName = "";

		/**
		 * Creates a new ValidationException with given error message and model property name
		 *
		 * @param message
		 *            The error message
		 */
		public ValidationException(String message) {
			super(message);
		}

		/**
		 * Creates a new ValidationException with given error message and model property name.
		 *
		 * @param message
		 *            The error message
		 * @param propertyName
		 *            The model property name
		 */
		public ValidationException(String message, String propertyName) {
			super(message);
		}

		/** The name of the property of the model for which the validation failed */
		public String getPropertyName() {
			return propertyName;
		}

	}

	private ValidationResult validationResult;

	private boolean projectValid = false;
	private boolean sourceFolderValid = false;

	M model;

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * PropertyChangeListenerSupport
	 */
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
	 * @return The model currently validated
	 */
	protected M getModel() {
		return model;
	}

	/**
	 * Set the model to validate
	 *
	 * @param model
	 *            The new model to validate
	 */
	public void setModel(M model) {
		this.model = model;

		// Reset state and validate
		this.setSourceFolderValid(false);
		this.setProjectValid(false);

		this.validate();
	}

	/**
	 * Run the validator.
	 *
	 * @return The validation result
	 */
	public final ValidationResult validate() {
		if (this.model == null) {
			setValidationResult(NO_MODEL_VALIDATION_RESULT);
			return NO_MODEL_VALIDATION_RESULT;
		}

		boolean success = true;

		// Preprocess the model
		prepare();

		// Run the validation methods
		try {
			run();
		} catch (ValidationException e) {
			setValidationResult(new ValidationResult(e.getMessage()));
			success = false;
		} catch (Exception e) {
			// 'Throwthrough' all other exceptions to explicitly abort validation
			throw e;
		}

		if (success) {
			setValidationResult(ValidationResult.SUCCESS);
		}

		return validationResult;
	}

	/**
	 * This method is invoked on every validation. It is used to delegate validation to specific validation methods. It
	 * can be overridden by subclasses to add custom validation logic.
	 *
	 * @throws ValidationException
	 *             Exception to be thrown by validating methods on validation issues
	 */
	protected void run() throws ValidationException {
		validateProject();
		validateSourceFolder();
		validateModuleSpecifier();
	}

	/**
	 * This method is invoked before every validation. It can be used to automatically preprocess the model.
	 */
	abstract protected void prepare();

	/**
	 * @return The last validation result
	 */
	public ValidationResult getValidationResult() {
		return validationResult;
	}

	/**
	 * @return True if the project property is valid
	 */
	public boolean getProjectValid() {
		return projectValid;
	}

	/**
	 *
	 * @param projectValid
	 *            The new validity of the project property
	 */
	private void setProjectValid(boolean projectValid) {
		this.firePropertyChange(PROJECT_PROPERTY_VALID, this.projectValid, this.projectValid = projectValid);
	}

	/**
	 * @return True if the source folder property is valid
	 */
	public boolean getSourceFolderValid() {
		return sourceFolderValid;
	}

	/**
	 *
	 * @param sourceFolderValid
	 *            The new validity of the source folder property
	 */
	private void setSourceFolderValid(boolean sourceFolderValid) {
		this.firePropertyChange(SOURCE_FOLDER_PROPERTY_VALID, this.sourceFolderValid,
				this.sourceFolderValid = sourceFolderValid);
	}

	private void setValidationResult(ValidationResult validationResult) {
		this.firePropertyChange(VALIDATION_RESULT, this.validationResult, this.validationResult = validationResult);
	}

	/**
	 * Project property constraints
	 */
	private void validateProject() throws ValidationException {
		this.setProjectValid(false);

		// 1. It must not be empty
		if (getModel().getProject().toString().trim().isEmpty()) {
			throw new ValidationException(ErrorMessages.PROJECT_MUST_NOT_BE_EMPTY,
					WorkspaceWizardModel.PROJECT_PROPERTY);
		}

		// 2. It is a path of a valid project in the current workspace
		URI projectURI = URI.createPlatformResourceURI(getModel().getProject().toString(), true);
		Optional<? extends IN4JSProject> n4jsProject = n4jsCore.findProject(projectURI);
		if (!n4jsProject.isPresent()) {
			throw new ValidationException(ErrorMessages.INVALID_PROJECT, WorkspaceWizardModel.PROJECT_PROPERTY);
		} else if (!n4jsProject.get().exists()) {
			throw new ValidationException(ErrorMessages.PROJECT_DOES_NOT_EXIST, WorkspaceWizardModel.PROJECT_PROPERTY);
		} else {
			// The path points to a resource inside the project
			if (!n4jsProject.get().getLocation().toURI().equals(projectURI)) {
				throw new ValidationException(ErrorMessages.INVALID_PROJECT + n4jsProject.get().getLocation(),
						WorkspaceWizardModel.PROJECT_PROPERTY);
			}
		}

		this.setProjectValid(true);
	}

	private void validateSourceFolder() throws ValidationException {
		this.setSourceFolderValid(false);

		// 1. The source folder property must not be empty
		String sourceFolder = getModel().getSourceFolder().removeTrailingSeparator().toString();

		if (sourceFolder.trim().isEmpty()) {
			throw new ValidationException(ErrorMessages.SOURCE_FOLDER_MUST_NOT_BE_EMPTY,
					WorkspaceWizardModel.SOURCE_FOLDER_PROPERTY);
		}

		// 2. All segments of the source folder path must be valid folder names
		if (!WorkspaceWizardValidatorUtils.isValidFolderPath(getModel().getSourceFolder())) {
			throw new ValidationException(
					ErrorMessages.SOURCE_FOLDER_IS_NOT_A_VALID_FOLDER_NAME,
					WorkspaceWizardModel.SOURCE_FOLDER_PROPERTY);
		}

		// 3. The source folder must be a valid {@link IN4JSSourceContainer}
		// The source container must exist, and not be of type external or library

		URI projectUri = URI.createPlatformResourceURI(getModel().getProject().segment(0), true);
		IN4JSProject project = n4jsCore.findProject(projectUri).orNull();

		if (null == project) {
			throw new ValidationException(ErrorMessages.INVALID_STATE_VALIDATION_ERROR);
		}

		if (project.getSourceContainers().stream()
				.filter(src -> (src.isSource() || src.isTest())) // Filter source type
				.filter(src -> src.getRelativeLocation().equals(sourceFolder)) // Filter name
				.count() == 0)
			throw new ValidationException(ErrorMessages.SOURCE_FOLDER_DOES_NOT_EXIST,
					WorkspaceWizardModel.SOURCE_FOLDER_PROPERTY);

		this.setSourceFolderValid(true);

	}

	/**
	 * Validates the module specifier
	 */
	protected void validateModuleSpecifier() throws ValidationException {
		doValidateModuleSpecifier(getModel().getModuleSpecifier());
	}

	/**
	 * Runs validation procedure for a given module specifier.
	 *
	 * @throws ValidationException
	 *             if an validation issue is detected
	 */
	protected void doValidateModuleSpecifier(String moduleSpecifier) throws ValidationException {

		// 1. The module specifier property must not be empty
		if (moduleSpecifier.trim().isEmpty()) {
			throw new ValidationException(ErrorMessages.MODULE_SPECIFIER_MUST_NOT_BE_EMPTY,
					WorkspaceWizardModel.MODULE_SPECIFIER_PROPERTY);
		}

		// 2. The module specifier is properly formed
		String[] moduleSpecifierSegments = moduleSpecifier.split("/", -1);
		for (int i = 0; i < moduleSpecifierSegments.length; i++) {

			String segment = moduleSpecifierSegments[i];
			boolean last = i == moduleSpecifierSegments.length - 1;
			boolean first = i == 0;
			boolean empty = segment.trim().isEmpty();

			// First segment is empty that means the specifier begins with a '/'
			if (first && empty) {
				throw new ValidationException(ErrorMessages.INVALID_MODULE_SPECIFIER_MUST_NOT_BEGIN_WITH,
						WorkspaceWizardModel.MODULE_SPECIFIER_PROPERTY);
			}
			// Segment is empty and not the last one
			if (empty && !last) {
				throw new ValidationException(ErrorMessages.INVALID_MODULE_SPECIFIER_EMPTY_PATH_SEGMENT,
						WorkspaceWizardModel.MODULE_SPECIFIER_PROPERTY);
			}
			// The segment is an invalid folder name, not the last segment and not empty
			if (!WorkspaceWizardValidatorUtils.isValidFolderName(segment) && !(empty && last)) {
				throw new ValidationException(ErrorMessages.INVALID_MODULE_SPECIFIER_INVALID_SEGMENT,
						WorkspaceWizardModel.MODULE_SPECIFIER_PROPERTY);
			}
		}

	}
}
