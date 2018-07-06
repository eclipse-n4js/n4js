/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.semver;

import org.eclipse.n4js.semver.validation.SEMVERIssueCodes;
import org.eclipse.n4js.semver.validation.SEMVERIssueSeveritiesProvider;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.validation.IssueSeveritiesProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class SEMVERRuntimeModule extends AbstractSEMVERRuntimeModule {

	/**
	 * A custom serializer class.
	 */
	@Override
	public Class<? extends ISerializer> bindISerializer() {
		return SEMVERSerializer.class;
	}

	/** Bind custom SEMVER issue severities provider that operates based on {@link SEMVERIssueCodes}. */
	public Class<? extends IssueSeveritiesProvider> bindIssueSeveritiesProvider() {
		return SEMVERIssueSeveritiesProvider.class;
	}
}
