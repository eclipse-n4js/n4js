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
package org.eclipse.n4js.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Utilities for different URI types
 */
public class URIUtils {

	/** Workspace relative URI starts with this letter */
	private static final String L = "L/";
	/** Workspace relative URI starts with this letter */
	private static final String P = "P/";

	/** @return a {@link IFile} for the given absolute file {@link URI} */
	static public IFile convertFileUriToPlatformFile(org.eclipse.emf.common.util.URI fileUri) {
		if (fileUri.isFile()) {
			String fileString = fileUri.toFileString();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath iPath = org.eclipse.core.runtime.Path.fromOSString(fileString);
			if (iPath != null) {
				IFile iFile = root.getFileForLocation(iPath);
				return iFile;
			}
		}
		return null;
	}

	/** @return a {@link IContainer} for the given absolute file {@link URI} */
	static public IContainer convertFileUriToPlatformContainer(org.eclipse.emf.common.util.URI fileUri) {
		if (fileUri.isFile()) {
			String fileString = fileUri.toFileString();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath iPath = org.eclipse.core.runtime.Path.fromOSString(fileString);
			if (iPath != null) {
				IContainer iContainer = root.getContainerForLocation(iPath);
				return iContainer;
			}
		}
		return null;
	}

	/**
	 * Converts the given {@link IResource} to a {@link org.eclipse.emf.common.util.URI}. In case the given resource is
	 * a workspace resource, a <i>platform resource URI</i> is returned. In case the given resource is a file based
	 * resource (e.g. an external project), a <i>file URI</i> is returned.
	 *
	 * @returns the a {@link org.eclipse.emf.common.util.URI} location for the given resource
	 */
	static public org.eclipse.emf.common.util.URI convert(IResource iResource) {
		if (iResource == null) {
			return null;
		}

		String projectPath = iResource.toString();
		String fullPathString = iResource.getFullPath().toString();

		org.eclipse.emf.common.util.URI uri;
		if (projectPath.startsWith(P) || projectPath.startsWith(L)) {
			uri = org.eclipse.emf.common.util.URI.createPlatformResourceURI(fullPathString, true);
		} else {
			uri = org.eclipse.emf.common.util.URI.createFileURI(fullPathString);
		}
		return addEmptyAuthority(uri);
	}

	/** Converts the given IResource to a file emf Uri */
	static public org.eclipse.emf.common.util.URI toFileUri(IResource iResource) {
		URI rUri = convert(iResource);
		return toFileUri(rUri);
	}

	/**
	 * Tells if the given URI is of the form <code>platform:/resource/EclipseProjectName</code>.
	 */
	static public boolean isPlatformResourceUriPointingToProject(org.eclipse.emf.common.util.URI uri) {
		return uri.isPlatformResource() && uri.segmentCount() == 2; // n.b. "resource" counts as a segment
	}

	/**
	 * Given the location of an N4JS project on disk, this method returns a
	 * {@link org.eclipse.emf.common.util.URI#isFile() file URI}, as used internally to uniquely identify N4JS projects.
	 * <p>
	 * Since this methods always returns file URIs, it is only intended for use in the headless case. In the UI case,
	 * URIs for identifying projects will be created by Eclipse.
	 * <p>
	 * For details on N4JS project name handling, see {@code ProjectDescriptionUtils#isProjectNameWithScope(String)}.
	 */
	public static org.eclipse.emf.common.util.URI deriveProjectURIFromFileLocation(File file) {
		try {
			org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI
					.createURI(file.getAbsoluteFile().toURI().toURL().toString());
			// by convention IN4JSProject URI does not end with '/'
			// i.e. last segment must not be empty
			String last = createURI.lastSegment();
			if (last != null && last.isEmpty()) {
				createURI = createURI.trimSegments(1);
			}
			return createURI;
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/** @returns the a {@link org.eclipse.emf.common.util.URI} location for the given {@link java.net.URI} */
	static public org.eclipse.emf.common.util.URI toFileUri(java.net.URI jnUri) {
		if (jnUri == null) {
			return null;
		}

		File file = new File(jnUri);
		String path = file.getAbsolutePath();
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createFileURI(path);
		return addEmptyAuthority(uri);
	}

	/**
	 * Converts the given URI to a {@link File} iff it is a {@link org.eclipse.emf.common.util.URI#isFile() file URI};
	 * otherwise returns <code>null</code>.
	 * <p>
	 * Same as {@link org.eclipse.emf.common.util.URI#toFileString()}, but returns a {@link File} instance instead of a
	 * string.
	 */
	static public File toFile(org.eclipse.emf.common.util.URI uri) {
		return uri != null && uri.isFile() ? new File(uri.toFileString()) : null;
	}

	/**
	 * Compensates for the missing {@link URI#equals(Object)} implementation in {@link URI}. Adjusts paths that contain
	 * symlinks.
	 *
	 * @return true iff the given {@link URI}s are equal
	 */
	static public boolean equals(org.eclipse.emf.common.util.URI uri1, org.eclipse.emf.common.util.URI uri2) {
		if (uri1 == uri2) {
			return true;
		}
		String string1 = toString(uri1);
		String string2 = toString(uri2);
		return string1.equals(string2);
	}

	/**
	 * Adjusts paths that contain symlinks.
	 *
	 * @return a hash code of the given {@link URI}s
	 */
	static public int hashCode(org.eclipse.emf.common.util.URI uri) {
		return toString(uri).hashCode();
	}

	/**
	 * Adjusts paths that contain symlinks.
	 *
	 * @return true iff the given {@link URI}s are equal
	 */
	static public String toString(org.eclipse.emf.common.util.URI uri) {
		String result = null;

		if (uri.isFile()) {
			String fileString = uri.toFileString();
			File file = new File(fileString);
			Path path = file.toPath();
			try {
				String newResult = path.toRealPath().toFile().toURI().toString();
				if (newResult.endsWith("/")) {
					newResult = newResult.substring(0, newResult.length() - 1);
				}
				result = newResult;
			} catch (IOException e) {
				// conversion unsuccessful, return original
			}
		}

		if (result == null) {
			result = uri.toString();
		}

		return result;
	}

	/** Creates new URI from the provided one, with symlinks resolved. */
	static public org.eclipse.emf.common.util.URI normalize(org.eclipse.emf.common.util.URI uri) {
		return addEmptyAuthority(URI.createURI(toString(uri)));
	}

	/** @return a complete URI for a given project */
	public static URI toFileUri(IProject project) {
		String pathStr = project.getLocation().toString();
		return toFileUri(pathStr);
	}

	/** @return absolute file URI for the given path. */
	static public URI toFileUri(Path path) {
		return toFileUri(path.toFile());
	}

	/** @return absolute file URI for the given file. */
	static public URI toFileUri(File file) {
		return toFileUri(file.getAbsolutePath());
	}

	/** @return a complete URI for a given file path string */
	public static URI toFileUri(String filePath) {
		URI uri = (filePath.startsWith("file:")) ? URI.createURI(filePath) : URI.createFileURI(filePath);

		return addEmptyAuthority(uri);
	}

	/** @return a complete URI for a given emf resource */
	public static URI toFileUri(Resource resource) {
		URI rUri = resource.getURI();
		return toFileUri(rUri);
	}

	/** Converts any emf URI to a file URI */
	public static URI toFileUri(URI rUri) {
		URI fileUri = rUri;
		if (!rUri.isFile()) {
			fileUri = CommonPlugin.resolve(rUri);
		}
		return addEmptyAuthority(fileUri);
	}

	/** Converts any emf file URI to an accessible platform local URI. Otherwise returns given URI. */
	public static URI tryToPlatformUri(URI fileUri) {
		if (fileUri.isFile()) {
			java.net.URI jnUri = java.net.URI.create(fileUri.toString());
			IFile[] platformFiles = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(jnUri);
			if (platformFiles.length > 0 && platformFiles[0].isAccessible()) {
				IFile localTargetFile = platformFiles[0];
				URI uri = URIUtils.convert(localTargetFile);
				if (fileUri.hasFragment()) {
					uri = uri.appendFragment(fileUri.fragment());
				}
				return uri;
			}
		}
		return fileUri;
	}

	/** Adds empty authority to the given URI. Necessary for windows platform. */
	public static URI addEmptyAuthority(URI uri) {
		if (uri.isFile() && !uri.hasAuthority() && !uri.isRelative()) {
			uri = URI.createHierarchicalURI(uri.scheme(), "", uri.device(), uri.segments(), uri.query(),
					uri.fragment());
		}
		return uri;
	}

}
