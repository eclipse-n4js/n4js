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

/**
 * A wizard model that contains information about being (in) a definition file.
 */
public interface DefinitionFileModel {

	/** Get the name */
	public boolean isDefinitionFile();

	/** Set the name */
	public void setDefinitionFile(boolean definitionFile);

}
