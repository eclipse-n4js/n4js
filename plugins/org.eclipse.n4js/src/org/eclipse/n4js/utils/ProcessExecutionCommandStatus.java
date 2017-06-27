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
package org.eclipse.n4js.utils;

import static org.eclipse.core.runtime.Status.OK_STATUS;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.core.runtime.IStatus;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.utils.process.ProcessExecutionCommand;
import org.eclipse.n4js.utils.process.ProcessResult;

/**
 * Helper for executing {@link ProcessExecutionCommand}s. Maps execution result into {@link IStatus}.
 */
// TODO good candidate for org.eclipse.n4js.utils bundle
// but in current form depends (transitively via StatusHelper)
// on org.eclipse.n4js.N4JSPluginId.N4JS_PLUGIN_ID
// can't move it without changing bunch of files and
// potentially, adjusting this API
@Singleton
public class ProcessExecutionCommandStatus {

	@Inject
	private StatusHelper statusHelper;

	/**
	 * Executes command given by the supplier and returns {@link IStatus} of the execution Provided message will be used
	 * to create error status if command execution fails.
	 */
	public IStatus execute(Supplier<ProcessExecutionCommand> commandSupplier, final String msg) {

		final ProcessResult per = commandSupplier.get().execute();

		if (per.isOK())
			return OK_STATUS;

		final Throwable cause = per.toThrowable(msg);
		if (null != cause)
			return statusHelper.createError(cause.getMessage(), cause);

		return statusHelper.createError(per.toString(), cause);
	}

	/**
	 * Sugar for {@link #execute(Supplier, String)}. Will pass provided parameter t to the function creating command.
	 */
	public <T> IStatus execute(final T t,
			final Function<T, ProcessExecutionCommand> operation,
			final String msg) {

		return execute(() -> operation.apply(t), msg);
	}

	/**
	 * Sugar for {@link #execute(Supplier, String)}. Will pass provided parameters t and u to the function creating
	 * command.
	 */
	public <T, U> IStatus execute(final T t, final U u,
			final BiFunction<T, U, ProcessExecutionCommand> operation,
			final String msg) {

		return execute(() -> operation.apply(t, u), msg);
	}

}
