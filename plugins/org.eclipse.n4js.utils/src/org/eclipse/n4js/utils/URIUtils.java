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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Utilities for different URI types
 */
public class URIUtils {

	/** Character used to denote special segments in {@link URI}s. */
	public static final char SPECIAL_SEGMENT_MARKER = '$';

	/** URI segment used for virtual resources. */
	public static final String VIRTUAL_RESOURCE_SEGMENT = SPECIAL_SEGMENT_MARKER + "VIRTUAL" + SPECIAL_SEGMENT_MARKER;

	private static final Pattern VIRTUAL_RESOURCE_NAME_SPLIT_PATTERN = Pattern.compile(Pattern.quote("/"));

	static final Map<String, Set<String>> extensionPrefixes = Map.of("ts", Set.of("d"));

	/** Fix {@link URI#fileExtension()} w.r.t. unsupported d.ts file extension that includes dots */
	static public String fileExtension(org.eclipse.emf.common.util.URI uri) {
		if (uri == null) {
			return null;
		}
		String fileExtension = uri.fileExtension();
		if (fileExtension != null && extensionPrefixes.containsKey(fileExtension)) {
			URI trimmedUri = uri.trimFileExtension();
			String fileExtensionPrefix = trimmedUri.fileExtension();
			if (fileExtensionPrefix != null && extensionPrefixes.get(fileExtension).contains(fileExtensionPrefix)) {
				return fileExtensionPrefix + "." + fileExtension;
			}
		}
		return fileExtension;
	}

	/** Fix {@link URI#trimFileExtension()} w.r.t. unsupported d.ts file extension that includes dots */
	static public URI trimFileExtension(org.eclipse.emf.common.util.URI uri) {
		if (uri == null) {
			return null;
		}
		URI trimmedUri = uri.trimFileExtension();
		String fileExtension = uri.fileExtension();
		if (fileExtension != null && extensionPrefixes.containsKey(fileExtension)) {
			String fileExtensionPrefix = trimmedUri.fileExtension();
			if (fileExtensionPrefix != null && extensionPrefixes.get(fileExtension).contains(fileExtensionPrefix)) {
				return trimmedUri.trimFileExtension();
			}

		}
		return trimmedUri;
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

	/** Adds empty authority to the given URI. Necessary for windows platform. */
	public static URI addEmptyAuthority(URI uri) {
		if (uri.isFile() && !uri.hasAuthority() && !uri.isRelative()) {
			uri = URI.createHierarchicalURI(uri.scheme(), "", uri.device(), uri.segments(), uri.query(),
					uri.fragment());
		}
		return uri;
	}

	/**
	 * Converts the given URI to a {@link File} iff it is a {@link org.eclipse.emf.common.util.URI#isFile() file URI};
	 * otherwise returns <code>null</code>.
	 * <p>
	 * Same as {@link org.eclipse.emf.common.util.URI#toFileString()}, but returns a {@link File} instance instead of a
	 * string.
	 */
	static public File toFile(URI uri) {
		return uri != null && uri.isFile() ? new File(uri.toFileString()) : null;
	}

	/**
	 * Converts the given URI to a {@link Path} iff it is a {@link org.eclipse.emf.common.util.URI#isFile() file URI};
	 * otherwise returns <code>null</code>.
	 * <p>
	 * Same as {@link org.eclipse.emf.common.util.URI#toFileString()}, but returns a {@link Path} instance instead of a
	 * string.
	 * <p>
	 * <b>Attention: This fails on platform Windows:</b><br>
	 * <code>Paths.get(org.eclipse.emf.common.util.URI#toFileString());</code><br>
	 * The reason is that {@link URI#toFileString()} returns a string like "\\c:\\dir" which cannot be parsed by
	 * {@link Paths#get(java.net.URI)} due to the starting "\\".
	 */
	static public Path toPath(URI uri) {
		return toFile(uri).toPath();
	}

	/**
	 * Returns from the given map the value of the key that is the longest prefix of the given URI or <code>null</code>
	 * if none of the given map's keys is a prefix of the given URI. Assumes keys to be URIs <em>without</em> a trailing
	 * path separator.
	 * <p>
	 * TODO: consider using a {@link SortedMap} for this purpose to avoid multiple <code>Map#get()</code>-calls (then
	 * also improve the similar implementation in {@code FileBasedWorkspace#findProjectWith(FileURI)}).
	 */
	public static <T> T findInMapByNestedURI(Map<URI, T> map, URI nestedURI) {
		URI currPrefix = trimTrailingPathSeparator(nestedURI);
		do {
			T match = map.get(currPrefix);
			if (match != null) {
				return match;
			}
			currPrefix = currPrefix.segmentCount() > 0 ? currPrefix.trimSegments(1) : null;
		} while (currPrefix != null);

		return null;
	}

	/**
	 * Returns the URI formed by removing the {@link URI#hasTrailingPathSeparator() trailing path separator} of the
	 * given URI, if it has one. Otherwise returns the given URI unchanged.
	 */
	public static URI trimTrailingPathSeparator(URI uri) {
		uri = uri.trimFragment();
		return uri.hasTrailingPathSeparator() ? uri.trimSegments(1) : uri;
	}

	/**
	 * Creates an URI for a {@link #VIRTUAL_RESOURCE_SEGMENT virtual resource}.
	 *
	 * @param hostResourceURI
	 *            URI of the host resource.
	 * @param virtualResourceName
	 *            name of the virtual resource. May denote a path using <code>/</code> as separator, *not* the platform
	 *            specific separator. Will be {@link URI#encodeSegment(String, boolean) URI-encoded}.
	 */
	public static URI createVirtualResourceURI(URI hostResourceURI, String virtualResourceName) {
		String[] segments = VIRTUAL_RESOURCE_NAME_SPLIT_PATTERN.split(virtualResourceName);
		for (int i = 0; i < segments.length; i++) {
			segments[i] = URI.encodeSegment(segments[i], false);
		}
		return createVirtualResourceURI(hostResourceURI, segments);
	}

	/**
	 * Creates an URI for a {@link #VIRTUAL_RESOURCE_SEGMENT virtual resource}.
	 *
	 * @param hostResourceURI
	 *            URI of the host resource.
	 * @param virtualResourcePathSegments
	 *            path and name segments. Will not be encoded (caller has to do that).
	 */
	public static URI createVirtualResourceURI(URI hostResourceURI, String[] virtualResourcePathSegments) {
		URI virtualURI = hostResourceURI.appendSegment(VIRTUAL_RESOURCE_SEGMENT);
		virtualURI = virtualURI.appendSegments(virtualResourcePathSegments);
		return virtualURI;
	}

	/** @return <code>true</code> iff the given URI is a {@link #VIRTUAL_RESOURCE_SEGMENT virtual resource URI}. */
	public static boolean isVirtualResourceURI(URI uri) {
		String[] segs = uri.segments();
		int len = segs.length;
		for (int i = 0; i < len; i++) {
			if (URIUtils.VIRTUAL_RESOURCE_SEGMENT.equals(segs[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Extracts the path of a {@link #VIRTUAL_RESOURCE_SEGMENT virtual resource} from its URI. The returned segments
	 * will still be {@link URI#encodeSegment(String, boolean) URI-encoded}; caller has to decode them (if desired).
	 */
	public static String[] getPathOfVirtualResource(URI uri) {
		String[] segs = uri.segments();
		int len = segs.length;
		for (int i = 0; i < len; i++) {
			if (URIUtils.VIRTUAL_RESOURCE_SEGMENT.equals(segs[i])) {
				String[] segsRemaining = Arrays.copyOfRange(segs, ++i, len);
				return segsRemaining;
			}
		}
		return null;
	}
}
