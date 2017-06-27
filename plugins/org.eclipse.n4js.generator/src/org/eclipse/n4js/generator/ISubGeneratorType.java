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

import org.eclipse.n4js.generator.common.ISubGenerator;

/**
 * This interface forces implementing Subgenerators to provide api that allow dynamic creation of instance.
 */
public interface ISubGeneratorType {

	/**
	 * return bundle ID of the SubGEnerator
	 */
	public String getBundleId();

	/**
	 * get class name for SubGenerator
	 */
	public String getGeneratorClassName();

	/**
	 * get Class of actual SubGenerator
	 */
	public Class<? extends ISubGenerator> getSubGeneratorClass();
}
