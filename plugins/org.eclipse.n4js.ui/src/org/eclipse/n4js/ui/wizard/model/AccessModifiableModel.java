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
 * A wizard model containing access modifier information
 */
public interface AccessModifiableModel {

	/**
	 * The saved access modifier
	 */
	public AccessModifier getAccessModifier();

	/**
	 * @param accessModifier
	 *            The new access modifier
	 */
	public void setAccessModifier(AccessModifier accessModifier);

	/**
	 * The state of the internal property
	 */
	public boolean isInternal();

	/**
	 * @param internal
	 *            The new internal property state
	 */
	public void setInternal(boolean internal);
}
