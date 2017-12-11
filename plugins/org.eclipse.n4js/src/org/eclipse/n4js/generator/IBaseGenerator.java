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
package org.eclipse.n4js.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IGenerator;

/**
 */
public interface IBaseGenerator extends IGenerator {
	/**
	 *
	 * @return true if the composite generator is applicable to the given resource and false otherwise.
	 */
	boolean isApplicableTo(Resource resource);
}
