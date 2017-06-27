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
package org.eclipse.n4js.runner.extension;

import org.eclipse.n4js.runner.IRunner;

/**
 * Implementation of {@link IRunnerDescriptor} intended for headless mode. Within Eclipse,
 * {@link EclipseRunnerDescriptor}s will be created automatically from extensions to the 'runners' extension point.
 */
public class RunnerDescriptorImpl implements IRunnerDescriptor {

	private final String id;
	private final String name;
	private final RuntimeEnvironment environment;
	private final IRunner runner;

	/**
	 * Create descriptor for the given runner. Only intended for headless mode!
	 */
	public RunnerDescriptorImpl(String id, String name, RuntimeEnvironment environment, IRunner runner) {
		this.id = id;
		this.name = name;
		this.environment = environment;
		this.runner = runner;
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
	public IRunner getRunner() {
		return runner;
	}
}
