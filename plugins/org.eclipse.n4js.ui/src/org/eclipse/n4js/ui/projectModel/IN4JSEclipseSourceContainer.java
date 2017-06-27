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
package org.eclipse.n4js.ui.projectModel;

import org.eclipse.core.resources.IContainer;

import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

/**
 */
public interface IN4JSEclipseSourceContainer extends IN4JSSourceContainer {

	/**
	 * Returns the eclipse folder that corresponds to this source container.
	 */
	IContainer getContainer();

	@Override
	IN4JSEclipseProject getProject();

}
