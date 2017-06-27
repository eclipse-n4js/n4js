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
package org.eclipse.n4js.ui.building;

import static com.google.common.base.Throwables.getStackTraceAsString;
import static com.google.common.collect.FluentIterable.from;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.Arrays.asList;

import java.io.PrintStream;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.debug.IBuildLogger;

import com.google.inject.BindingAnnotation;

import org.eclipse.n4js.ui.N4JSClusteringBuilderConfiguration;

/**
 * {@link IBuildLogger Build logger} for dumping information about the {@link IBuilderState builder state}. This builder
 * state logger instance is registered into the {@link N4JSClusteringBuilderConfiguration} via the {@link BuilderState}
 * annotation, hence can be injected as named instance.
 */
@SuppressWarnings("restriction")
public class BuilderStateLogger implements IBuildLogger {

	@Override
	public void log(final Object o) {

		final IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
		final BuilderStateConsole console = from(asList(manager.getConsoles())).filter(BuilderStateConsole.class)
				.first()
				.orNull();

		if (console != null) {
			if (o instanceof Throwable) {
				console.println(getStackTraceAsString((Throwable) o));
			} else {
				console.println(String.valueOf(o));
			}
			manager.showConsoleView(console);
		}

	}

	/**
	 * Console factory for the {@code org.eclipse.ui.console.consoleFactories} extension point.
	 */
	public static class Factory implements IConsoleFactory {

		@Override
		public void openConsole() {
			final IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
			manager.addConsoles(new IConsole[] { new BuilderStateConsole() });
		}

	}

	/**
	 * Console for logging the {@link IBuilderState builder state} during the build.
	 */
	private static final class BuilderStateConsole extends IOConsole {

		private final PrintStream out;

		private BuilderStateConsole() {
			super("Builder State", BuilderStateConsole.class.getName(), null, true);
			this.out = new PrintStream(newOutputStream(), true);
		}

		private void println(final String message) {
			out.println(message);
		}

	}

	/**
	 * Annotation for distinguishing between any {@link IBuildLogger} and {@link BuilderStateLogger} instances via
	 * injection.
	 */
	@BindingAnnotation
	@Target({ FIELD, PARAMETER, METHOD })
	@Retention(RUNTIME)
	public @interface BuilderState {
		// Nothing
	}

}
