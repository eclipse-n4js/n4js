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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.util.CancelIndicator;

import org.eclipse.n4js.CancelIndicatorBaseExtractor;

/**
 */
public class CancelIndicatorUiExtractor extends CancelIndicatorBaseExtractor {

	/**
	 * A wrapper around Eclipse-favored progress monitors to comply with Xtext-favored cancel indicators.
	 */
	public static class ProgressBasedCancelIndicator implements CancelIndicator {

		private final IProgressMonitor wrapped;

		@SuppressWarnings("javadoc")
		public ProgressBasedCancelIndicator(IProgressMonitor wrapped) {
			this.wrapped = wrapped;
		}

		@Override
		public boolean isCanceled() {
			return wrapped.isCanceled();
		}

	}

	@Override
	public CancelIndicator extractCancelIndicator(Object fsa) {
		// why use reflection?
		// see IDE-1739
		// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=477068
		// and the javadoc for ICancelIndicatorExtractor
		Class<?> clazz = fsa.getClass();
		try {
			Method m = clazz.getMethod("getMonitor", new Class<?>[0]);
			try {
				IProgressMonitor monitor = (IProgressMonitor) (m.invoke(fsa));
				return new ProgressBasedCancelIndicator(monitor);
			} catch (IllegalAccessException exc) {
				return CancelIndicator.NullImpl;
			} catch (InvocationTargetException exc) {
				return CancelIndicator.NullImpl;
			}
		} catch (NoSuchMethodException exc) {
			return CancelIndicator.NullImpl;
		}
	}

}
