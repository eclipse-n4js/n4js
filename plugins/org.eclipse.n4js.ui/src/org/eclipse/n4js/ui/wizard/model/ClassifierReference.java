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
package org.eclipse.n4js.ui.wizard.model;

import java.util.List;
import java.util.StringJoiner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.naming.QualifiedName;

import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;

/**
 * Helper type to represent classifier references in a wizard. ClassifierReferences may be resolved or unresolved. *
 * <p>
 * Unresolved classifier references do not have an URI. They are meant to mainly exist in an intermediate step between
 * user input and validation. The validation process should add the corresponding uri or leave it empty if the specifier
 * is invalid. Invalid specifiers are reported as a validation error.
 * </p>
 *
 */
public class ClassifierReference {
	/**
	 * Absolute specifier of the module containing the classifier
	 */
	public String classifierModuleSpecifier;

	/**
	 * Name of the classifier
	 */
	public String classifierName;

	/**
	 * Platform uri of the classifier declaration.
	 */
	public URI uri;

	/**
	 * Creates a unresolved classifier reference.
	 *
	 * @param classifierModuleSpecifier
	 *            Absolute specifier of the classifier
	 */
	public ClassifierReference(String classifierModuleSpecifier, String classifierName) {
		this.classifierModuleSpecifier = classifierModuleSpecifier;
		this.classifierName = classifierName;
		this.uri = null;
	}

	/**
	 * Creates a resolved classifier reference. This means a classifier with a valid uri of its declaration.
	 *
	 * @param qualifiedName
	 *            QualifiedName of the classifier
	 * @param uri
	 *            Platform uri of the classifier declaration
	 */
	public ClassifierReference(QualifiedName qualifiedName, URI uri) {
		this.classifierName = qualifiedName.getLastSegment();
		List<String> frontSegments = qualifiedName.getSegments();
		if (frontSegments.size() > 0) {
			StringJoiner joiner = new StringJoiner(N4JSQualifiedNameConverter.DELIMITER);
			for (String segment : frontSegments.subList(0, frontSegments.size() - 1)) {
				joiner.add(segment);
			}
			this.classifierModuleSpecifier = joiner.toString();
		} else {
			this.classifierModuleSpecifier = "";
		}
		this.uri = uri;

	}

	/**
	 * Return the full classifier specifier. For existing classifiers this is a string representation of its qualified
	 * name.
	 *
	 * @return The full classifier specifier
	 */
	public String getFullSpecifier() {
		if (!this.classifierName.isEmpty())
			return (this.classifierModuleSpecifier.isEmpty() ? ""
					: this.classifierModuleSpecifier + N4JSQualifiedNameConverter.DELIMITER) + this.classifierName;
		return "";
	}

	/**
	 * Return true if this ClassifierReference is a complete classifier reference data structure.
	 *
	 * This method helps to determine if the reference is in a valid or an intermediate state where it still needs to be
	 * processed by the model validator.
	 *
	 * <p>
	 * Note: No existence/visibility validation takes place as this method only assures the data structure is complete.
	 * </p>
	 *
	 * @return True on completeness, false otherwise
	 */
	public boolean isComplete() {
		if (!this.classifierName.isEmpty() && !this.classifierModuleSpecifier.isEmpty() && this.uri != null) {
			return true;
		}
		return false;
	}

}
