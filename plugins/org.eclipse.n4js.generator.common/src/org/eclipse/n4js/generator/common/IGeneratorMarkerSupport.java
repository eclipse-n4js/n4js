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
package org.eclipse.n4js.generator.common;

import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.ImplementedBy;

/**
 * Generic Marker support for UI and non-UI cases.
 */
@ImplementedBy(IGeneratorMarkerSupport.NullGeneratorMarkersupport.class)
public interface IGeneratorMarkerSupport {

	/** UI-independent values for marker severity. */
	@SuppressWarnings("javadoc")
	public static enum Severity {
		INFO, WARNING, ERROR
	}

	/**
	 * Default Null-implementation
	 */
	public static class NullGeneratorMarkersupport implements IGeneratorMarkerSupport {

		@Override
		public void createMarker(Resource res, String message, Severity severity) {
			// n.t.d.
		}

		@Override
		public void deleteMarker(Resource res) {
			// n.t.d.
		}

		@Override
		public boolean hasMarker(Resource res) {
			return false;
		}

		@Override
		public boolean isOperationCanceledException(Throwable th) {
			return false;
		}
	}

	/** Marker type as used in plugin.xml of *.n4js.ui plug-in. */
	public static final String MARKER__ORG_ECLIPSE_IDE_N4JS_UI_COMPILER_ERROR = "org.eclipse.n4js.ui.compiler.error";

	/** Create an marker */
	public void createMarker(Resource res, String message, Severity severity);

	/** Delete an existing marker - if present. */
	public void deleteMarker(Resource res);

	/** @return true if marker is present. */
	public boolean hasMarker(Resource res);

	/**
	 * Tells whether the given throwable is of a type that is thrown when cancellation occurs AND is a subtype of
	 * {@link RuntimeException} (usually, all operation-canceled exceptions should be runtime exceptions, but this
	 * method will guarantee that).
	 */
	public boolean isOperationCanceledException(Throwable th);
}
