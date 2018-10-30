/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem;

import org.eclipse.n4js.n4idl.versioning.N4IDLVersionResolver;
import org.eclipse.n4js.utils.ContainerTypesHelper;

import com.google.inject.Inject;

public abstract class AbstractJudgment {

	@Inject
	protected InternalTypeSystemNEW ts; // FIXME reconsider if we need to use N4JSTypeSystem here!!!!
	@Inject
	protected TypeSystemHelper typeSystemHelper;
	@Inject
	protected ContainerTypesHelper containerTypesHelper;
	@Inject
	protected N4IDLVersionResolver n4idlVersionResolver;

	public static final class JResult<T> {

		private final boolean success;
		private final T value;
		private final String failureMessage;
		private final boolean custom;
		private final JResult<?> cause;

		private JResult(T value) {
			this.success = true;
			this.value = value;
			this.failureMessage = null;
			this.custom = false;
			this.cause = null;
		}

		private JResult(String failureMessage, boolean custom, JResult<?> cause) {
			this.success = false;
			this.value = null;
			this.failureMessage = failureMessage;
			this.custom = custom;
			this.cause = cause;
		}

		public boolean isSuccess() {
			return success;
		}

		public boolean isFailure() {
			return !success;
		}

		public T getValue() {
			return value;
		}

		public String getFailureMessage() {
			return failureMessage;
		}

		public boolean isCustom() {
			return custom;
		}

		public JResult<?> getCause() {
			return cause;
		}

		public boolean isOrIsCausedByCustom() {
			return getCustomFailure() != null;
		}

		public String getPreferredFailureMessage() {
			JResult<?> customFailure = getCustomFailure();
			String customMessage = customFailure != null ? customFailure.getFailureMessage() : null;
			return customMessage != null ? customMessage : failureMessage;
		}

		public JResult<?> getCustomFailure() {
			if (!success && custom) {
				return this;
			}
			if (cause != null) {
				return cause.getCustomFailure();
			}
			return null;
		}

		public JResult<T> trimCauses() {
			return isSuccess() ? this : new JResult<>(failureMessage, custom, null);
		}

		public static JResult<Boolean> success() {
			return success(Boolean.TRUE);
		}

		public static <T> JResult<T> success(T value) {
			return new JResult(value);
		}

		public static <T> JResult<T> failure(String failureMessage, boolean custom, JResult<?> cause) {
			return new JResult(failureMessage, custom, cause);
		}

		public static <T> JResult<T> failure(String failureMessage, JResult<?> template) {
			if (!template.isFailure()) {
				throw new IllegalArgumentException("template is not a failure");
			}
			if (template.getFailureMessage() == null) {
				return new JResult<>(failureMessage, template.custom, template.cause);
			}
			return new JResult<>(failureMessage, false, template);
		}
	}
}