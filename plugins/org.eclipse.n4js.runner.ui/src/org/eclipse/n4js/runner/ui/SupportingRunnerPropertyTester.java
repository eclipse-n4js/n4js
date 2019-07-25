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
package org.eclipse.n4js.runner.ui;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.log4j.Logger.getLogger;
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.runner.RuntimeEnvironmentsHelper;
import org.eclipse.n4js.runner.exceptions.DependencyCycleDetectedException;
import org.eclipse.n4js.runner.exceptions.InsolvableRuntimeEnvironmentException;
import org.eclipse.n4js.runner.extension.IRunnerDescriptor;
import org.eclipse.n4js.runner.extension.RunnerRegistry;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.ui.handler.GeneratedJsFileLocator;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Check if given receiver is supported by registered runners.
 */
public class SupportingRunnerPropertyTester extends PropertyTester {

	private static final Logger LOGGER = getLogger(SupportingRunnerPropertyTester.class);

	/** namespace of the property tested */
	public static final String PROPERTY_NAMESPACE = "org.eclipse.n4js.runner.ui";
	/** property tested */
	public static final String PROPERTY_IS_SUPPORTING_RUNNER = "isSupportingRunner";

	@Inject
	private RuntimeEnvironmentsHelper hRuntimeEnvironments;

	@Inject
	private IN4JSCore in4jsCore;

	@Inject
	private RunnerRegistry runnerRegistry;

	@Inject
	private GeneratedJsFileLocator generatedFileLocator;

	/** constructor will take care of injecting internal fields */
	public SupportingRunnerPropertyTester() {
		/* get Guice injector configured with all n4js stuff */
		N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS).injectMembers(this);
	}

	/**
	 * Check if given receiver object is supported by given runner. Runner is specified by first arguments in args
	 * parameter which is expected to be runner key value.
	 *
	 * @param receiver
	 *            is either {@link FileEditorInput} or {@link IFile} to be check against runner.
	 * @param property
	 *            used to verify callers are invoking correct tester.
	 * @param args
	 *            expected to contain {@link String} representing runner key of the runner to test.
	 * @param expectedValue
	 *            not really used
	 *
	 */
	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {

		if (!PROPERTY_IS_SUPPORTING_RUNNER.equals(property)) {
			LOGGER.debug("invoked wrong property to test : " + property);
			return false;
		}

		// Input is neither a file nor file editor input.
		final Optional<IFile> file = getFileFromInput(receiver);
		if (!file.isPresent()) {
			return false;
		}

		// Cannot locate generated file, what to run?
		if (!generatedFileLocator.tryGetGeneratedSourceForN4jsFile(file.get()).isPresent()) {
			return false;
		}

		final Object arg0 = args[0];
		if (!(arg0 instanceof String)) {
			LOGGER.debug("invalid runner key value, should be String");
			return false;
		}
		final String runnerId = arg0.toString();
		final IRunnerDescriptor runnerDesc;
		try {
			runnerDesc = runnerRegistry.getDescriptor(runnerId);
		} catch (Exception e) {
			LOGGER.debug("invalid runner key value, no runner found for id: " + runnerId);
			return false;
		}

		final List<RuntimeEnvironment> compatibleRuntimeEnvironmets = newArrayList();
		try {
			compatibleRuntimeEnvironmets.addAll(findCompatibleRuntimeEnvironments(file.get()));
		} catch (final DependencyCycleDetectedException | InsolvableRuntimeEnvironmentException e) {
			LOGGER.info(e.getMessage());
			return false;
		}

		// TODO IDE-1351 remove the following hack once the library manager is in place
		if ("org.eclipse.n4js.runner.nodejs.NODEJS".equals(runnerDesc.getId())) {
			// the node-runner supports running without a custom runtime environment in the workspace
			final boolean haveCustomNodeRuntimeEnvironment = hRuntimeEnvironments
					.findRuntimeEnvironmentProject(RuntimeEnvironment.NODEJS).isPresent();
			if (!haveCustomNodeRuntimeEnvironment)
				return true;
			// otherwise:
			// there is a custom Node.js runtime environment -> continue with the ordinary checks ...
		}

		// TODO IDE-1393 connect testers with extension point
		if (!compatibleRuntimeEnvironmets.isEmpty() &&
				"RE_NodeJS_Mangelhaft".equals(compatibleRuntimeEnvironmets.get(0).getProjectName().getRawName())) {
			return true;
		}

		final boolean runnerToTestIsCompatible = compatibleRuntimeEnvironmets.contains(runnerDesc.getEnvironment());
		if (!runnerToTestIsCompatible) {
			LOGGER.debug("Runner with id '" + runnerId + "' does not support running selected file.");
			return false;
		}
		return true;
	}

	/**
	 * @see RuntimeEnvironmentsHelper#findCompatibleRuntimeEnvironments(IN4JSProject)
	 */
	private Set<RuntimeEnvironment> findCompatibleRuntimeEnvironments(final IFile file) {
		final String pathName = file.getFullPath().toString();
		final URI uri = createPlatformResourceURI(pathName, true);
		return findCompatibleRuntimeEnvironments(uri);
	}

	private Optional<IFile> getFileFromInput(final Object input) {
		if (input instanceof IFile) {
			return fromNullable((IFile) input);
		} else if (input instanceof IFileEditorInput) {
			return fromNullable(((IFileEditorInput) input).getFile());
		} else {
			LOGGER.debug("Unsupported input selection : " + input.getClass().getName());
			return absent();
		}
	}

	/**
	 * If project cannot be obtained for given URI empty list is returned.
	 *
	 * @see RuntimeEnvironmentsHelper#findCompatibleRuntimeEnvironments(IN4JSProject)
	 */
	private Set<RuntimeEnvironment> findCompatibleRuntimeEnvironments(final URI uri) {

		final Optional<? extends IN4JSProject> project = this.in4jsCore.findProject(uri);

		if (project.isPresent() == false) {
			LOGGER.debug("Cannot obtain " + IN4JSProject.class.getName() + " for "
					+ uri.toFileString());
			LOGGER.error("Cannot resolve runner as no project corresponding to the selection found.");
			return Collections.emptySet();
		}

		return hRuntimeEnvironments.findCompatibleRuntimeEnvironments(project.get());
	}
}
