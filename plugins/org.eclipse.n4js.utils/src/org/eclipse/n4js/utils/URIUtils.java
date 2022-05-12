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
import java.util.Comparator;
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

	/** Comparator for {@link URI}s. See {@link URIUtils#compare(URI, URI)}. */
	public static class URIComparator implements Comparator<URI> {

		@Override
		public int compare(URI uri1, URI uri2) {
			return URIUtils.compare(uri1, uri2);
		}

	}

	/** Fix {@link URI#fileExtension()} w.r.t. unsupported d.ts file extension that includes dots */
	static public String fileExtension(URI uri) {
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
	static public URI trimFileExtension(URI uri) {
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
	static public boolean equals(URI uri1, URI uri2) {
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
	static public int hashCode(URI uri) {
		return toString(uri).hashCode();
	}

	/**
	 * Adjusts paths that contain symlinks.
	 *
	 * @return true iff the given {@link URI}s are equal
	 */
	static public String toString(URI uri) {
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
	static public URI normalize(URI uri) {
		return addEmptyAuthority(URI.createURI(toString(uri)));
	}

	/**
	 * Compares the given {@link URI}s.
	 * <p>
	 * Does not normalize them, so many semantically equivalent URIs will yield a non-zero return value.
	 */
	static public int compare(URI uri1, URI uri2) {
		if (uri1 == uri2) {
			return 0;
		} else if (uri1 == null) {
			return -1;
		} else if (uri2 == null) {
			return 1;
		}
		int cmp = doCompare(uri1.scheme(), uri2.scheme());
		if (cmp != 0) {
			return cmp;
		}
		// note: authority includes userInfo, host, and port
		cmp = doCompare(uri1.authority(), uri2.authority());
		if (cmp != 0) {
			return cmp;
		}
		cmp = doCompare(uri1.device(), uri2.device());
		if (cmp != 0) {
			return cmp;
		}
		String[] segs1 = uri1.segments();
		String[] segs2 = uri2.segments();
		int len = Math.min(segs1.length, segs2.length);
		for (int i = 0; i < len; i++) {
			cmp = doCompare(segs1[i], segs2[1]);
			if (cmp != 0) {
				return cmp;
			}
		}
		cmp = Integer.compare(segs1.length, segs2.length);
		if (cmp != 0) {
			return cmp;
		}
		cmp = doCompare(uri1.query(), uri2.query());
		if (cmp != 0) {
			return cmp;
		}
		cmp = doCompare(uri1.fragment(), uri2.fragment());
		if (cmp != 0) {
			return cmp;
		}
		return 0;
	}

	static private int doCompare(String s1, String s2) {
		if (s1 == s2) {
			return 0;
		} else if (s1 == null) {
			return -1;
		} else if (s2 == null) {
			return 1;
		}
		return s1.compareTo(s2);
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
	 * Converts the given URI to a {@link File} iff it is a {@link URI#isFile() file URI}; otherwise returns
	 * <code>null</code>.
	 * <p>
	 * Same as {@link URI#toFileString()}, but returns a {@link File} instance instead of a string.
	 */
	static public File toFile(URI uri) {
		return uri != null && uri.isFile() ? new File(uri.toFileString()) : null;
	}

	/**
	 * Converts the given URI to a {@link Path} iff it is a {@link URI#isFile() file URI}; otherwise returns
	 * <code>null</code>.
	 * <p>
	 * Same as {@link URI#toFileString()}, but returns a {@link Path} instance instead of a string.
	 * <p>
	 * <b>Attention: This fails on platform Windows:</b><br>
	 * <code>Paths.get(URI#toFileString());</code><br>
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
	 * Extracts the base URI of a {@link #isVirtualResourceURI(URI) virtual resource URI}, i.e. the URI up to, excluding
	 * the {@link #VIRTUAL_RESOURCE_SEGMENT virtual resource segment}, equivalent to the URI of the host resource.
	 * <p>
	 * Returns the given URI itself if it isn't a virtual resource URI.
	 */
	public static URI getBaseOfVirtualResourceURI(URI uri) {
		String[] segs = uri.segments();
		int len = segs.length;
		for (int i = 0; i < len; i++) {
			if (URIUtils.VIRTUAL_RESOURCE_SEGMENT.equals(segs[i])) {
				URI result = uri.trimSegments(len - i);
				return result;
			}
		}
		return uri;
	}

	/**
	 * Extracts the path of a {@link #isVirtualResourceURI(URI) virtual resource URI}, i.e. the segments following the
	 * {@link #VIRTUAL_RESOURCE_SEGMENT}.
	 * <p>
	 * Returns <code>null</code> iff the given URI isn't a virtual resource URI.
	 */
	public static String[] getPathOfVirtualResource(URI uri, boolean decode) {
		String[] segs = uri.segments();
		int len = segs.length;
		for (int i = 0; i < len; i++) {
			if (URIUtils.VIRTUAL_RESOURCE_SEGMENT.equals(segs[i])) {
				String[] segsRemaining = Arrays.copyOfRange(segs, ++i, len);
				if (decode) {
					for (int j = 0; j < segsRemaining.length; j++) {
						segsRemaining[j] = URI.decode(segsRemaining[j]);
					}
				}
				return segsRemaining;
			}
		}
		return null;
	}
}
