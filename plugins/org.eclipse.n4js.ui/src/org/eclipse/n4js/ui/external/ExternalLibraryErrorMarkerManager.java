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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.generator.IWorkspaceMarkerSupport;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.N4JSEclipseProject;
import org.eclipse.n4js.validation.IssueCodes;
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

	@Inject
	private IWorkspaceMarkerSupport workspaceMarkerSupport;

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
		clearMarkers(n4EclPrj.getProjectName());
	}

	/** Clears error markers of the given external library name */
	public void clearMarkers(String projectName) {
		Iterable<IN4JSProject> allProjects = n4jsCore.findAllProjects();
		for (IN4JSProject prj : allProjects) {
			if (!prj.isExternal() && prj.exists() && prj instanceof N4JSEclipseProject) {
				IProject iProject = ((N4JSEclipseProject) prj).getProject();

				workspaceMarkerSupport.deleteMarkersWithUriKey(iProject, projectName,
						IssueCodes.EXTERNAL_LIBRARY_ERRORS,
						IssueCodes.EXTERNAL_LIBRARY_WARNINGS);
			}
		}
	}

	/** Adds error markers for the given resource and issues */
	public void setIssues(URI uri, Collection<Issue> issues) {
		IResource resource = null;
		if (uri.isFile()) {
			String fileString = uri.toFileString();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath location = org.eclipse.core.runtime.Path.fromOSString(fileString);
			resource = root.getFileForLocation(location);
		}
		setIssues(uri, issues, null);
	}

	public void setIssues(URI uri, Collection<Issue> issues, IResource resource) {
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
		if (externalProject == null) {
			return;
		}

		for (Issue issue : issues) {
			String prjFileLocation = extPrj.getLocation().toFileString();
			String resFileLocation = uri.toFileString();
			resFileLocation = resFileLocation.substring(prjFileLocation.length());

			String locationName = "Dependency: " + externalProject.getName();
			locationName += ", File: " + resFileLocation;
			locationName += ", Line: " + issue.getLineNumber();
			String uriKey = externalProject.getName();

			if (resource == null) {
				resource = oneWorkspaceProject.getProject();
			}
			String code = getCodeKey(issue);
			String msg = getMessage(issue);

			switch (issue.getSeverity()) {
			case ERROR:
				workspaceMarkerSupport.createError(resource, code, locationName, msg, uriKey, false);
				break;
			case WARNING:
				workspaceMarkerSupport.createWarning(resource, code, locationName, msg, uriKey, false);
				break;
			default:
				throw new IllegalArgumentException(String.valueOf(issue.getSeverity()));
			}

		}
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

	private String getMessage(Issue issue) {
		switch (issue.getSeverity()) {
		case ERROR:
			return IssueCodes.getMessageForEXTERNAL_LIBRARY_ERRORS(issue.getMessage());
		case WARNING:
			return IssueCodes.getMessageForEXTERNAL_LIBRARY_WARNINGS(issue.getMessage());
		default:
			throw new IllegalArgumentException(String.valueOf(issue.getSeverity()));
		}
	}

}
