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
package org.eclipse.n4js.tester.extension;

import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.tester.ITester;

/**
 * Implementation of {@link ITesterDescriptor} intended for headless mode. Within Eclipse,
 * {@link EclipseTesterDescriptor}s will be created automatically from extensions to the 'testers' extension point.
 */
public class TesterDescriptorImpl implements ITesterDescriptor {

	private final String id;
	private final String name;
	private final RuntimeEnvironment environment;
	private final ITester tester;

	/**
	 * Create descriptor for the given tester. Only intended for headless mode!
	 */
	public TesterDescriptorImpl(String id, String name, RuntimeEnvironment environment, ITester tester) {
		this.id = id;
		this.name = name;
		this.environment = environment;
		this.tester = tester;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public RuntimeEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public ITester getTester() {
		return tester;
	}
}
