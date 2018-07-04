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
package org.eclipse.n4js.generator;

import org.eclipse.core.resources.IResource;

import com.google.inject.ImplementedBy;

/**
 * Generic Marker support for UI and non-UI cases.
 */
@ImplementedBy(IWorkspaceMarkerSupport.NullWorkspaceMarkersupport.class)
public interface IWorkspaceMarkerSupport {

	/** UI-independent values for marker severity. */
	@SuppressWarnings("javadoc")
	public static enum Severity {
		INFO, WARNING, ERROR
	}

	/**
	 * Default Null-implementation
	 */
	public static class NullWorkspaceMarkersupport implements IWorkspaceMarkerSupport {

		@Override
		public void createWarning(IResource res, String codeKey, String location, String message, String uriKey,
				boolean fixable) {
			// n.t.d.
		}

		@Override
		public void createError(IResource res, String codeKey, String location, String message, String uriKey,
				boolean fixable) {
			// n.t.d.
		}

		@Override
		public void deleteMarkers(IResource res, String... codeKeys) {
			// n.t.d.
		}

		@Override
		public void deleteMarkersWithUriKey(IResource res, String uriKey, String... codeKeys) {
			// n.t.d.
		}

		@Override
		public boolean hasMarkers(IResource res, String codeKey) {
			return false;
		}
	}

	/** Marker type as used in plugin.xml of *.n4js.ui plug-in. */
	public static final String MARKER__ORG_ECLIPSE_IDE_N4JS_UI_WORKSPACE_ERROR = "org.eclipse.n4js.ui.workspace.error";

	/** Create an error marker */
	public void createWarning(
			IResource res,
			String codeKey,
			String location,
			String message,
			String uriKey,
			boolean fixable);

	/** Create an error marker */
	public void createError(
			IResource res,
			String codeKey,
			String location,
			String message,
			String uriKey,
			boolean fixable);

	/** Delete an existing marker - if present. */
	public void deleteMarkers(IResource res, String... codeKeys);

	/** Delete an existing marker - if present. */
	public void deleteMarkersWithUriKey(IResource res, String uriKey, String... codeKeys);

	/** @return true if marker is present. */
	public boolean hasMarkers(IResource res, String codeKey);
}
