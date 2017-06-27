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
package org.eclipse.n4js.ui.wizard.classifiers

import org.eclipse.n4js.ui.wizard.model.AccessModifier
import org.eclipse.n4js.ui.wizard.model.ClassifierReference
import org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardModel
import org.eclipse.n4js.utils.beans.PropertyChangeSupport
import java.util.List
import org.eclipse.core.runtime.IPath

import static org.eclipse.n4js.N4JSGlobals.*

/**
 * Wizard model for N4JS classifiers.
 */
@PropertyChangeSupport
abstract class N4JSClassifierWizardModel extends WorkspaceWizardModel {

	var String name = '';
	var AccessModifier accessModifier = AccessModifier.PUBLIC;
	var boolean definitionFile;
	var boolean internal;
	var boolean n4jsAnnotated;
	var List<ClassifierReference> interfaces = newArrayList();

	/**
	 * Computes the file location in which the class is going to be created according to the current model.
	 *
	 * @return path of the location.
	 */
	public def IPath computeFileLocation() {
		val ^extension = if (definitionFile) N4JSD_FILE_EXTENSION else N4JS_FILE_EXTENSION;
		return this.project
			.append(sourceFolder)
			.append(effectiveModuleSpecifier)
			.addFileExtension(^extension);
	}

	/**
	 * Dependent on the set module specifier this method returns its identity or the module specifier concatenated with
	 * the class name. (set module specifiers ending with a '/' character are interpreted as base path)
	 *
	 * @return the effective module specifier.
	 */
	public def String getEffectiveModuleSpecifier() {
		val effectiveModuleSpecifier = super.getModuleSpecifier();
		// If separator at the end or empty specifier auto attach class name.
		if (effectiveModuleSpecifier.empty || (effectiveModuleSpecifier.lastIndexOf(IPath.SEPARATOR) == effectiveModuleSpecifier.length - 1)) {
			return effectiveModuleSpecifier + name;
		}
		return effectiveModuleSpecifier;
	}

	/**
	 * Returns with the human readable name of the classifier. For instance interface or class.
	 */
	public def abstract String getClassifierName();

}
