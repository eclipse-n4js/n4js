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
package org.eclipse.n4js.runner.tests

import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.Singleton

import static org.eclipse.n4js.n4mf.ProjectType.*

/**
 * Class for providing N4JS manifest builder instances.
 */
@Singleton
class ManifestBuilderProvider implements Provider<ManifestBuilder> {

	@Inject
	Provider<ManifestBuilder> delegate

	@Override
	override get() {
		delegate.get
	}

	/**
	 * Returns with a new N4JS manifest builder instance which is configured for system project type by default.
	 * @return the new manifest builder instance for default system project type.
	 */
	def newBuilder() {
		get
	}

	/**
	 * Returns with a new N4JS manifest builder with runtime environment project type.
	 * @return the new manifest builder for runtime environment project type.
	 */
	def newBuilderForRE() {
		get.withType(RUNTIME_ENVIRONMENT)
	}

	/**
	 * Returns with a new N4JS manifest builder with runtime library project type.
	 * @return the new manifest builder for runtime library project type.
	 */
	def newBuilderForRL() {
		get.withType(RUNTIME_LIBRARY)
	}

}
