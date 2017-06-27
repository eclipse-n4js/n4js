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
package org.eclipse.n4js.ui.wizard.classifiers;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ui.wizard.classes.N4JSClassWizardModel;
import org.eclipse.n4js.ui.wizard.model.AccessModifier;
import org.eclipse.n4js.ui.wizard.model.ClassifierReference;
import org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardModelValidator;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

/**
 * Base validator implementation for N4JS classifiers.
 */
public abstract class N4JSClassifierWizardModelValidator<M extends N4JSClassifierWizardModel>
		extends WorkspaceWizardModelValidator<M> {

	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private N4JSLanguageHelper languageHelper;

	private IResourceDescriptions descriptions;

	/**
	 * Error Messages for model validation of the {@link N4JSClassWizardModel}
	 */
	public static class ErrorMessages {

		// Class name errors
		private static final String CLASSIFIER_NAME_MUST_NOT_BE_EMPTY = "The %s name field must not be empty.";
		private static final String INVALID_CLASSIFIER_NAME = "Invalid %s name.";
		private static final String RESERVED_CLASSIFIER_NAME = "The %s name '%s' is a reserved N4JS identifier.";

		// Interfaces errors
		private static final String THE_INTERFACE_CANNOT_BE_FOUND = "The interface %s cannot be found.";

		// Definition file collision errors
		private static final String THE_NEW_SOURCE_MODULE_COLLIDES_WITH_THE_DEFINITION_FILE = "The new source module collides with the definition file: %s";
		private static final String THE_NEW_DEFINITION_MODULE_COLLIDES_WITH_THE_SOURCE_FILE = "The new definition module collides with the source file: %s";

		// File location errors
		private static final String FILE_LOCATION_OVERLAPS = "The specified file location overlaps with the file %s";
	}

	/**
	 * Class name specifier property constraints
	 */
	protected void validateClassifierName() throws ValidationException {

		// 1. The class name must not be empty
		if (getModel().getName().trim().length() < 1) {
			throw new ValidationException(
					String.format(ErrorMessages.CLASSIFIER_NAME_MUST_NOT_BE_EMPTY, getClassifierName()));
		}

		String className = getModel().getName();

		if (!N4JSLanguageUtils.isValidIdentifier(className)) {
			throw new ValidationException(format(ErrorMessages.INVALID_CLASSIFIER_NAME, getClassifierName()));
		}

		if (languageHelper.isReservedIdentifier(className)) {
			throw new ValidationException(
					format(ErrorMessages.RESERVED_CLASSIFIER_NAME, getClassifierName(), className));
		}
	}

	/**
	 * Module specifier specifier property constraints
	 */
	@Override
	protected void validateModuleSpecifier() throws ValidationException {

		String effectiveModuleSpecifier = getModel().getEffectiveModuleSpecifier();

		// Invoke super validation procedure on full effective module specifier
		doValidateModuleSpecifier(effectiveModuleSpecifier);

		/* Check for potential file collisions */
		if (isFileSpecifyingModuleSpecifier(effectiveModuleSpecifier)) {
			IProject moduleProject = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(getModel().getProject().toString());
			IPath effectiveModulePath = new Path(getModel().getEffectiveModuleSpecifier());

			IPath n4jsdPath = getModel().getSourceFolder()
					.append(effectiveModulePath.addFileExtension(N4JSGlobals.N4JSD_FILE_EXTENSION));
			IPath n4jsPath = getModel().getSourceFolder()
					.append(effectiveModulePath.addFileExtension(N4JSGlobals.N4JS_FILE_EXTENSION));

			if (getModel().isDefinitionFile() && moduleProject.exists(n4jsPath)) {
				throw new ValidationException(
						String.format(ErrorMessages.THE_NEW_DEFINITION_MODULE_COLLIDES_WITH_THE_SOURCE_FILE,
								moduleProject.getFullPath().append(n4jsPath)));
			} else if (!getModel().isDefinitionFile() && moduleProject.exists(n4jsdPath)) {
				throw new ValidationException(String
						.format(ErrorMessages.THE_NEW_SOURCE_MODULE_COLLIDES_WITH_THE_DEFINITION_FILE,
								moduleProject.getFullPath().append(n4jsdPath)));
			}
		}

	}

	/**
	 * Interfaces specifier property constraints
	 */
	protected void validateInterfaces() throws ValidationException {
		// ---------------------------------------
		// Interfaces property constraints
		// ---------------------------------------

		ArrayList<ClassifierReference> interfaces = new ArrayList<>(getModel().getInterfaces());

		for (int i = 0; i < interfaces.size(); i++) {
			ClassifierReference iface = interfaces.get(i);
			if (!isValidInterface(iface)) {
				throw new ValidationException(
						String.format(ErrorMessages.THE_INTERFACE_CANNOT_BE_FOUND, iface.getFullSpecifier())
								.toString());
			} else if (iface.uri == null) {
				IEObjectDescription interfaceDescription = getClassifierObjectDescriptionForFQN(
						iface.getFullSpecifier());
				if (interfaceDescription != null) {
					iface.uri = interfaceDescription.getEObjectURI();
				}
			}
		}

		getModel().setInterfaces(interfaces);
	}

	/**
	 * Sugar for getting the specific name of the classifier for UI purposes. With this we can distinguish on the UI
	 * between interfaces and classes.
	 */
	protected String getClassifierName() {
		return getModel().getClassifierName();
	}

	@Override
	protected void prepare() {
		// ---------------------------------------
		// Automatic regulative constraints
		// ---------------------------------------

		// Auto disable the N4JS annotation field when definition file (external) is unselected.
		if (!getModel().isDefinitionFile() && getModel().isN4jsAnnotated()) {
			getModel().setN4jsAnnotated(false);
		}
		// Auto disable the Internal annotation for the private and project access modifier
		if ((getModel().getAccessModifier() == AccessModifier.PRIVATE
				|| getModel().getAccessModifier() == AccessModifier.PROJECT) && getModel().isInternal()) {
			getModel().setInternal(false);
		}
		// Auto disable the N4JS annotation for the private access modifier
		if (getModel().getAccessModifier() == AccessModifier.PRIVATE) {
			getModel().setN4jsAnnotated(false);
		}

		// Remove interfaces duplicate entries
		ArrayList<ClassifierReference> interfaces = new ArrayList<>(getModel().getInterfaces());

		Set<String> duplicateFullSpecifiers = Sets.newHashSet();

		for (Iterator<ClassifierReference> itr = interfaces.iterator(); itr.hasNext();/**/) {
			ClassifierReference next = itr.next();
			if (!duplicateFullSpecifiers.add(next.getFullSpecifier())) {
				itr.remove();
			}
			// Also remove empty entries
			if (next.classifierName.isEmpty()) {
				itr.remove();
			}
		}

		getModel().setInterfaces(interfaces);
	}

	@Override
	protected void run() throws ValidationException {
		super.run();

		validateClassifierName();
		validateFileLocation();
		validateInterfaces();
	}

	private void validateFileLocation() throws ValidationException {
		IPath path = getModel().computeFileLocation();

		IContainer activeContainer = ResourcesPlugin.getWorkspace().getRoot();

		for (int i = 0; i < path.segmentCount(); i++) {
			String segment = path.segment(i);

			IResource member = activeContainer.findMember(segment);

			// If a segment does not exist and its not the last segment (module file)
			if (null == member && i < path.segmentCount() - 1) {
				// Abort validation as this is fine
				break;
			}
			// If a segment isn't the module file but a file exists at its path
			if (member instanceof IFile && i < path.segmentCount() - 1) {
				throw new ValidationException(
						String.format(ErrorMessages.FILE_LOCATION_OVERLAPS, member.getFullPath()));
			}

			if (member instanceof IContainer) {
				activeContainer = (IContainer) member;
			}
		}
	}

	private boolean isValidReferenceOfType(String absoluteSpecifier, EClass type) {
		if (descriptions == null) {
			ResourceSet set = n4jsCore.createResourceSet(Optional.fromNullable(null));
			this.descriptions = n4jsCore.getXtextIndex(set);
		}

		QualifiedName name = qualifiedNameConverter.toQualifiedName(absoluteSpecifier);
		Iterable<IEObjectDescription> foundObjects = descriptions.getExportedObjects(type, name, false);
		return foundObjects.iterator().hasNext();
	}

	/**
	 * Returns with {@code true} if the classifier resolved against the argument is a {@link TClass}, otherwise
	 * {@code false}.
	 */
	protected boolean isValidClass(ClassifierReference ref) {
		return isValidReferenceOfType(ref.getFullSpecifier(), TypesPackage.eINSTANCE.getTClass());
	}

	/**
	 * Returns with {@code true} if the classifier resolved against the argument is a {@link TInterface}, otherwise
	 * {@code false}.
	 */
	protected boolean isValidInterface(ClassifierReference ref) {
		return isValidReferenceOfType(ref.getFullSpecifier(), TypesPackage.eINSTANCE.getTInterface());
	}

	/**
	 * Returns true if the given module specifier is specifying a file.
	 *
	 * Returns false for empty specifiers.
	 *
	 * @param specifier
	 *            The module specifier
	 */
	protected boolean isFileSpecifyingModuleSpecifier(String specifier) {
		return specifier.length() > 0 && specifier.charAt(specifier.length() - 1) != IPath.SEPARATOR;
	}

	/**
	 * Returns with the index entry representing a classifier for the given classifier FQN argument.
	 *
	 * @param fqn
	 *            the fully qualified name of the classifier.
	 * @return the index entry representing the classifier.
	 */
	protected IEObjectDescription getClassifierObjectDescriptionForFQN(String fqn) {
		QualifiedName name = qualifiedNameConverter.toQualifiedName(fqn);
		Iterable<IEObjectDescription> foundObjects = descriptions.getExportedObjects(
				TypesPackage.eINSTANCE.getTClassifier(),
				name, false);

		return Iterables.getFirst(foundObjects, null);
	}
}
