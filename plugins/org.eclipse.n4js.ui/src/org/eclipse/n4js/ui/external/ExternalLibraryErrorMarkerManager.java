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
package org.eclipse.n4js.ui.external;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.N4JSEclipseProject;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

/**
 * This class manages issues that refer to the external libraries.
 */
public class ExternalLibraryErrorMarkerManager {

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	/** Clears all error markers of external libraries */
	public void clearAllMarkers() {
		clearMarkers((String) null);
	}

	/** Clears error markers of the given external library */
	public void clearMarkers(N4JSExternalProject[] n4ExtPrjs) {
		for (N4JSExternalProject n4ExtPrj : n4ExtPrjs) {
			clearMarkers(n4ExtPrj.getName());
		}
	}

	/** Clears error markers of the given external library */
	public void clearMarkers(N4JSExternalProject n4ExtPrj) {
		clearMarkers(n4ExtPrj.getName());
	}

	/** Clears error markers of the given external library */
	public void clearMarkers(N4JSEclipseProject n4EclPrj) {
		clearMarkers(n4EclPrj.getProjectId());
	}

	/** Clears error markers of the given external library name */
	public void clearMarkers(String projectID) {
		Iterable<IN4JSProject> allProjects = n4jsCore.findAllProjects();
		for (IN4JSProject prj : allProjects) {
			if (!prj.isExternal() && prj.exists() && prj instanceof N4JSEclipseProject) {
				IProject iProject = ((N4JSEclipseProject) prj).getProject();

				try {
					IMarker[] currentMarkers = iProject.findMarkers(null, true, IResource.DEPTH_ZERO);
					for (IMarker marker : currentMarkers) {
						String codeKey = marker.getAttribute(Issue.CODE_KEY, "");
						String uriKey = marker.getAttribute(Issue.URI_KEY, "");

						boolean pleaseDelete = false;
						pleaseDelete |= IssueCodes.EXTERNAL_LIBRARY_ERRORS.equals(codeKey);
						pleaseDelete |= IssueCodes.EXTERNAL_LIBRARY_WARNINGS.equals(codeKey);
						pleaseDelete &= projectID == null || uriKey.equals(projectID);

						if (pleaseDelete) {
							try {
								marker.delete();
							} catch (CoreException e1) {
								// ignore
							}
						}
					}
				} catch (CoreException e1) {
					// ignore
				}
			}
		}
	}

	/** Adds error markers for the given resource and issues */
	public void setIssues(URI uri, Collection<Issue> issues) {
		N4JSEclipseProject oneWorkspaceProject = null;
		for (IN4JSProject prj : n4jsCore.findAllProjects()) {
			if (!prj.isExternal() && prj.exists() && prj instanceof N4JSEclipseProject) {
				oneWorkspaceProject = (N4JSEclipseProject) prj;
			}
		}
		if (oneWorkspaceProject == null) {
			return;
		}

		IN4JSProject extPrj = n4jsCore.findProject(uri).orNull();
		N4JSExternalProject externalProject = externalLibraryWorkspace.getProject(extPrj.getLocation());

		for (Issue issue : issues) {
			String locationName = "Dependency: " + externalProject.getName();
			locationName += ", File: " + uri.toString();
			locationName += ", Line: " + issue.getLineNumber();
			String uriKey = externalProject.getName();

			try {
				addMarker(oneWorkspaceProject.getProject(), issue, locationName, uriKey);
			} catch (CoreException e) {
				// ignore
			}
		}
	}

	/**
	 * Adds an error marker to a given {@link IResource}, in this case to a {@link IProject}.
	 * <p>
	 * Inspired by {@link MarkerCreator#createMarker(Issue, IResource, String)}
	 */
	private void addMarker(IProject projectForMarker, Issue issue, String locName, String uriKey) throws CoreException {
		IMarker marker = projectForMarker.createMarker("org.eclipse.n4js.n4mf.ui.n4mf.check.normal");

		marker.setAttribute(IMarker.LINE_NUMBER, 0);
		marker.setAttribute(IMarker.CHAR_START, 0);
		marker.setAttribute(IMarker.CHAR_END, 0);
		marker.setAttribute(IMarker.LOCATION, locName);
		marker.setAttribute(Issue.CODE_KEY, getCodeKey(issue));
		marker.setAttribute(IMarker.SEVERITY, getSeverity(issue));
		marker.setAttribute(IMarker.MESSAGE, issue.getMessage());
		marker.setAttribute(Issue.URI_KEY, uriKey);
		marker.setAttribute("FIXABLE_KEY", false);
	}

	private String getCodeKey(Issue issue) {
		switch (issue.getSeverity()) {
		case ERROR:
			return IssueCodes.EXTERNAL_LIBRARY_ERRORS;
		case WARNING:
			return IssueCodes.EXTERNAL_LIBRARY_WARNINGS;
		default:
			throw new IllegalArgumentException(String.valueOf(issue.getSeverity()));
		}
	}

	/** copied from {@link MarkerCreator} */
	private int getSeverity(Issue issue) {
		switch (issue.getSeverity()) {
		case ERROR:
			return IMarker.SEVERITY_ERROR;
		case WARNING:
			return IMarker.SEVERITY_WARNING;
		case INFO:
			return IMarker.SEVERITY_INFO;
		default:
			throw new IllegalArgumentException(String.valueOf(issue.getSeverity()));
		}
	}
}
