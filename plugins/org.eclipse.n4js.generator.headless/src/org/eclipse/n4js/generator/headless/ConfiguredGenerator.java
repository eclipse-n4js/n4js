/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.generator.headless;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;

/**
 * Wrapper around {@link IGenerator} and {@link JavaIoFileSystemAccess}, that are configured and ready to use.
 * Convenience for the user to just trigger generation.
 */
class ConfiguredGenerator {

	private final IGenerator generator;
	private final IFileSystemAccess fsa;

	ConfiguredGenerator(IGenerator generator, IFileSystemAccess fsa) {
		this.generator = generator;
		this.fsa = fsa;
	}

	/** delegates to {@link IGenerator#doGenerate} */
	void generate(Resource input) {
		generator.doGenerate(input, fsa);
	}

}