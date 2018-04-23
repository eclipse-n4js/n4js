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
package org.eclipse.n4js.tester.ui;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.tester.TestConfiguration;
import org.eclipse.n4js.tester.TesterFrontEnd;
import org.eclipse.n4js.tester.domain.TestCase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Converts a {@link TestConfiguration} to an {@link ILaunchConfiguration} and vice versa.
 */
@Singleton
public class TestConfigurationConverter {

	@Inject
	private TesterFrontEnd testerFrontEnd;

	/**
	 * Converts an {@link ILaunchConfiguration} to a {@link TestConfiguration}. Will throw a {@link WrappedException} in
	 * case of error.
	 *
	 * @see TestConfiguration#writePersistentValues(Map)
	 */
	public TestConfiguration toTestConfiguration(ILaunchConfiguration launchConfig) {
		try {
			final Map<String, Object> properties = launchConfig.getAttributes();
			// special treatment of name required:
			// name is already included in 'properties', but we have to make sure that the name is up-to-date
			// in case the name of the launch configuration has been changed after the launch configuration
			// was created via method #toLaunchConfiguration()
			properties.put(RunConfiguration.NAME, launchConfig.getName());
			TestConfiguration testConfig = testerFrontEnd.createConfiguration(properties);
			testConfig.setLaunchConfigurationTypeIdentifier(launchConfig.getType().getIdentifier());
			return testConfig;
		} catch (Exception e) {
			throw new WrappedException("could not convert Eclipse ILaunchConfiguration to N4JS TestConfiguration", e);
		}
	}

	/**
	 * Converts a {@link TestConfiguration} to an {@link ILaunchConfiguration}. Will throw a {@link WrappedException} in
	 * case of error.
	 *
	 * @param failed
	 *            may be null, only used in test view to execute failed tests
	 * @see TestConfiguration#readPersistentValues()
	 */
	public ILaunchConfiguration toLaunchConfiguration(ILaunchConfigurationType type, TestConfiguration testConfig,
			List<TestCase> failed) {
		try {
			final ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager()
					.getLaunchConfigurations(type);

			for (ILaunchConfiguration config : configs) {
				if (equals(testConfig, config))
					return config;
			}

			final IContainer container = null;
			final ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(container, testConfig.getName());

			workingCopy.setAttributes(testConfig.readPersistentValues());
			if (failed != null && !failed.isEmpty()) {
				workingCopy.setAttribute(TestConfiguration.TESTCASE_SELECTION,
						failed.stream().map(tc -> {
							String s = tc.getURI().toString();
							return s;
						}).collect(Collectors.toList()));
			}

			return workingCopy.doSave();
		} catch (Exception e) {
			throw new WrappedException("could not convert N4JS TestConfiguration to Eclipse ILaunchConfiguration", e);
		}
	}

	private boolean equals(TestConfiguration testConfig, ILaunchConfiguration launchConfig) throws CoreException {
		return testConfig.getName().equals(launchConfig.getName())
				&& testConfig.readPersistentValues().equals(launchConfig.getAttributes());
	}
}
