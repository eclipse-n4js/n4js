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

/**
 * A model that contains a list of interfaces
 *
 */
public interface InterfacesContainingModel {

	/**
	 * Set the interfaces
	 *
	 * Replaces existing interfaces.
	 *
	 * @param interfaces
	 *            Array of Interfaces
	 */
	public void setInterfaces(List<ClassifierReference> interfaces);

	/**
	 * Add a single interface to the model
	 *
	 * @param iface
	 *            The interface to add
	 */
	public void addInterfaces(ClassifierReference iface);

	/**
	 * Remove a single interface from the model
	 *
	 * @param iface
	 *            The interface to remove
	 */
	public void removeInterfaces(ClassifierReference iface);

	/**
	 * Get the interfaces
	 *
	 * @return A list of the contained interfaces
	 */
	public List<ClassifierReference> getInterfaces();
}
