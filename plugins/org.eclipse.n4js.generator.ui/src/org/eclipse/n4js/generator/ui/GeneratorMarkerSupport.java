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
package org.eclipse.n4js.generator.ui;

import static org.eclipse.core.resources.IResource.DEPTH_ZERO;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.generator.IGeneratorMarkerSupport;

import com.google.inject.Singleton;

/**
 * Concrete Implementation for markers delegating to Resource-Markers.
 */
@Singleton
public class GeneratorMarkerSupport implements IGeneratorMarkerSupport {

	private final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

	@Override
	public void createMarker(Resource res, String message, Severity severity) {

		final int severityEclipse;
		switch (severity) {
		case INFO:
			severityEclipse = IMarker.SEVERITY_INFO;
			break;
		case WARNING:
			severityEclipse = IMarker.SEVERITY_WARNING;
			break;
		default:
			severityEclipse = IMarker.SEVERITY_ERROR;
			break;
		}

		try {
			IMarker marker = toIFile(res).createMarker(MARKER__ORG_ECLIPSE_IDE_N4JS_UI_COMPILER_ERROR);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severityEclipse);
			marker.setAttribute(IMarker.LINE_NUMBER, 1);
		} catch (CoreException e) {
			GeneratorUiActivator.getInstance().getLog().log(e.getStatus());
			String resInfo = "";
			if (res != null) {
				if (res.getURI() != null) {
					resInfo = "on resource with uri=" + res.getURI();
				} else {
					resInfo = "on resource=" + res;
				}
			}
			throw new RuntimeException("Cannot create error marker with message='" + message + "' " + resInfo + ".", e);
		}
	}

	@Override
	public void deleteMarker(Resource res) {
		try {
			toIFile(res).deleteMarkers(MARKER__ORG_ECLIPSE_IDE_N4JS_UI_COMPILER_ERROR, true, DEPTH_ZERO);
		} catch (CoreException e) {
			GeneratorUiActivator.getInstance().getLog().log(e.getStatus());
			// Problems with marker must not affect the actual build.
		}
	}

	@Override
	public boolean hasMarker(Resource res) {
		try {
			return toIFile(res).findMarkers(MARKER__ORG_ECLIPSE_IDE_N4JS_UI_COMPILER_ERROR, true,
					DEPTH_ZERO).length > 0;
		} catch (CoreException e) {
			GeneratorUiActivator.getInstance().getLog().log(e.getStatus());
		}
		return false;
	}

	private IFile toIFile(Resource res) {
		return toIFile(res.getURI());
	}

	private IFile toIFile(URI uri) {
		return workspaceRoot.getFile(new Path(uri.toPlatformString(true)));
	}
}
