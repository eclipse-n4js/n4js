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
package org.eclipse.n4js.ui.internal;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.generator.IWorkspaceMarkerSupport;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Singleton;

/**
 * Concrete Implementation for workspace markers.
 */
@Singleton
public class WorkspaceMarkerSupport implements IWorkspaceMarkerSupport {
	private static final Logger LOGGER = Logger.getLogger(WorkspaceMarkerSupport.class);

	@Override
	public void createWarning(IResource res, String codeKey, String location, String message, String uriKey,
			boolean fixable) {

		createMarker(res, codeKey, location, message, uriKey, fixable, IMarker.SEVERITY_WARNING);
	}

	@Override
	public void createError(IResource res, String codeKey, String location, String message, String uriKey,
			boolean fixable) {

		createMarker(res, codeKey, location, message, uriKey, fixable, IMarker.SEVERITY_ERROR);
	}

	private void createMarker(IResource res, String codeKey, String location, String message, String uriKey,
			boolean fixable, int severity) {

		try {
			IMarker marker = res.createMarker(MARKER__ORG_ECLIPSE_IDE_N4JS_UI_WORKSPACE_ERROR);
			marker.setAttribute(IMarker.LINE_NUMBER, 0);
			marker.setAttribute(IMarker.CHAR_START, 0);
			marker.setAttribute(IMarker.CHAR_END, 0);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(Issue.CODE_KEY, codeKey);
			marker.setAttribute(IMarker.LOCATION, location);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(Issue.URI_KEY, uriKey);
			marker.setAttribute("FIXABLE_KEY", fixable);
		} catch (CoreException e) {
			LOGGER.error(e.getStatus());
			String resInfo = "";
			if (res.getLocationURI() != null) {
				resInfo = "on resource with uri=" + res.getLocationURI();
			} else {
				resInfo = "on resource=" + res;
			}
			throw new RuntimeException("Cannot create error marker with message='" + message + "' " + resInfo + ".", e);
		}
	}

	@Override
	public boolean hasMarkers(IResource res, String codeKey) {
		return !getAllMarker(res, null, codeKey).isEmpty();
	}

	@Override
	public void deleteMarkers(IResource res, String... codeKeys) {
		deleteMarkersWithUriKey(res, null, codeKeys);
	}

	@Override
	public void deleteMarkersWithUriKey(IResource res, String uriKey, String... codeKeys) {
		for (IMarker marker : getAllMarker(res, uriKey, codeKeys)) {
			try {
				marker.delete();
			} catch (CoreException e) {
				LOGGER.error(e.getStatus());
				// Problems with marker must not affect the actual build.
			}
		}
	}

	private List<IMarker> getAllMarker(IResource res, String uriKey, String... codeKeys) {
		List<IMarker> allMarkers = new LinkedList<>();
		try {
			IMarker[] allResourceMarkers = res.findMarkers(null, true, 0);

			for (IMarker marker : allResourceMarkers) {
				String mCodeKey = marker.getAttribute(Issue.CODE_KEY, "");
				String mUriKey = marker.getAttribute(Issue.URI_KEY, "");

				boolean select = false;
				for (String codeKey : codeKeys) {
					select |= mCodeKey.equals(codeKey);
				}
				select &= uriKey == null || uriKey.equals(mUriKey);

				if (select) {
					allMarkers.add(marker);
				}
			}
		} catch (CoreException e) {
			LOGGER.error(e.getStatus());
			// Problems with marker must not affect the actual build.
		}
		return allMarkers;
	}

}
