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
 * A model which is named. (e.g. class name, interface name)
 *
 */
public interface NamedModel {

	/** Get the name */
	public String getName();

	/** Set the name */
	public void setName(String name);

}
