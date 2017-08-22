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
package org.eclipse.n4js.generator.common;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.util.CancelIndicator;

/**
 * This interface has to be implemented by all concrete N4JS transpilers.
 */
public interface ISubGenerator extends IGenerator {

	/**
	 * @return {@link CompilerDescriptor} instance
	 */
	public CompilerDescriptor getCompilerDescriptor();

	/**
	 * @param compilerDescriptor
	 *            compiler descriptor
	 */
	public void setCompilerDescriptor(CompilerDescriptor compilerDescriptor);

	/**
	 * Returns true if for the given resource something should be compiled
	 *
	 * @param input
	 *            the resource potentially to be compiled
	 */
	public boolean shouldBeCompiled(Resource input, CancelIndicator monitor);

	/**
	 * Only used for testing.
	 *
	 * @param root
	 *            the root AST element
	 * @return the compiled result as String
	 */
	public String getCompileResultAsText(Script root, GeneratorOption[] options);
}
