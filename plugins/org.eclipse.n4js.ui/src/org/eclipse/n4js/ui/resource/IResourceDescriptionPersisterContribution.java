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
package org.eclipse.n4js.ui.resource;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.xtext.resource.IResourceDescriptions;

/**
 * {@link IResourceDescriptions Resource descriptions} (Xtext index) persister contribution that are invoked from the
 * {@link ContributingResourceDescriptionPersister N4JS index persister}.
 */
public interface IResourceDescriptionPersisterContribution {

	/**
	 * Unique ID of the extension point.
	 */
	String EXTENSION_POINT_ID = "org.eclipse.n4js.ui.resourceDescriptionsPersisterContribution";

	/**
	 * The {@link IConfigurationElement configuration element} property name that will be used to instantiate the
	 * concrete implementation.
	 */
	String CLAZZ_PROPERTY_NAME = "class";

	/**
	 * Called before the base persisted state provider service (registered to the builder state) schedules a recovery
	 * build in case of missing or corrupted {@link IResourceDescriptions resource descriptions} (Xtext index).
	 */
	void scheduleRecoveryBuild();

}
