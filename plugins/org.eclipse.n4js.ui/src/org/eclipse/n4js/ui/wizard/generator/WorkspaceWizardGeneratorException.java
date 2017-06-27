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

/**
 * An exception to indicate problems during the generation process.
 */
public class WorkspaceWizardGeneratorException extends RuntimeException {
	/**
	 * Creates a new {@link WorkspaceWizardGeneratorException} with the given message.
	 */
	public WorkspaceWizardGeneratorException(String message) {
		super(message);
	}
}
