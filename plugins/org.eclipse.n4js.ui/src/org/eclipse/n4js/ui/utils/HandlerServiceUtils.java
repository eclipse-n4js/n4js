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
package org.eclipse.n4js.ui.utils;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.core.expressions.IEvaluationContext.UNDEFINED_VARIABLE;
import static org.eclipse.ui.ISources.ACTIVE_EDITOR_NAME;
import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

import com.google.common.base.Optional;

/**
 * Utility class for the {@link IHandlerService}.
 * <p>
 * This class can be used to retrieve workbench specific variables even if an {@link ExecutionEvent execution event} is
 * not available.
 */
public abstract class HandlerServiceUtils {

	/**
	 * Optionally returns with the current state of the workbench only and if only the
	 * {@link PlatformUI#isWorkbenchRunning() workbench is running}.
	 *
	 * @return the current state of the running workbench. Returns with an {@link Optional#absent() absent} if the
	 *         workbench is not running or the {@link IHandlerService} is not available.
	 */
	public static Optional<IEvaluationContext> getCurrentWorkbenchState() {
		if (!isWorkbenchRunning()) {
			return absent();
		}
		final Object service = getWorkbench().getService(IHandlerService.class);
		return service instanceof IHandlerService ? fromNullable(((IHandlerService) service).getCurrentState())
				: absent();
	}

	/**
	 * Optionally returns with the currently active {@link IEditorPart editor} if there is an active editor.
	 *
	 * @return the active editor. Could be missing but never {@code null}.
	 */
	public static Optional<IEditorPart> getActiveEditor() {
		return getActiveEditor(IEditorPart.class);
	}

	/**
	 * Sugar for getting the active {@link IEditorPart editor part} in a type safe way.
	 *
	 * @return the active editor. Could be missing but never {@code null}.
	 *
	 * @see #getActiveEditor()
	 */
	public static <T extends IEditorPart> Optional<T> getActiveEditor(final Class<T> editorClass) {
		return getVariable(ACTIVE_EDITOR_NAME, editorClass);
	}

	private static <T> Optional<T> getVariable(final String variableName, final Class<T> expectedClass) {

		if (isNullOrEmpty(variableName) || null == expectedClass) {
			return absent();
		}

		final Optional<IEvaluationContext> state = getCurrentWorkbenchState();
		if (!state.isPresent()) {
			return absent();
		}

		final Object variable = state.get().getVariable(variableName);
		if (null == variable || UNDEFINED_VARIABLE == variable) {
			return absent();
		}

		if (expectedClass.isAssignableFrom(variable.getClass())) {
			return fromNullable(expectedClass.cast(variable));
		}

		return absent();
	}

}
