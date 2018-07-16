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
package org.eclipse.n4js.ui.wizard.generator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardModel;

/**
 * A wizard generator for workspace N4JS elements.
 *
 */
public interface WorkspaceWizardGenerator<M extends WorkspaceWizardModel> {
	/**
	 * Returns a preview of the generated code specified by the model.
	 *
	 * In the preview, the import statements are aliased in the case of an existing target module file.
	 *
	 * @param model
	 *            The model for which a preview should be generated
	 * @return The file content
	 */
	public ContentBlock[] generateContentPreview(M model);

	/**
	 * Writes the element specified by the model to its target module file.
	 *
	 * This may imply the creation of a new file or insertion into an existing file.
	 *
	 * @param model
	 *            The specifying model
	 * @param monitor
	 *            A monitor to report progress to
	 * @throw {@link WorkspaceWizardGeneratorException} when problems during the process occur.
	 */
	public void writeToFile(M model, IProgressMonitor monitor) throws WorkspaceWizardGeneratorException;

	/**
	 * Performs all necessary changes in the project description file to prepare the creation of the model's workspace
	 * element.
	 *
	 * @param model
	 *            The model to change the project description for.
	 *
	 * @throw {@link WorkspaceWizardGeneratorException} when problems during the process occur.
	 */
	public void performProjectDescriptionChanges(M model, IProgressMonitor monitor)
			throws WorkspaceWizardGeneratorException;
}
