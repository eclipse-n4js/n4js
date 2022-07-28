/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Utilities for {@link QualifiedName}s.
 */
public class QualifiedNameUtils {

	/**
	 * Null-safe appending of one or more segments.
	 */
	public static QualifiedName append(QualifiedName prefix, String... segments) {
		if (prefix == null || segments == null) {
			return null;
		}
		if (segments.length == 0) {
			return prefix;
		}
		List<String> allSegments = new ArrayList<>(prefix.getSegmentCount() + segments.length);
		allSegments.addAll(prefix.getSegments());
		for (String segment : segments) {
			if (segment == null) {
				return null;
			}
			allSegments.add(segment);
		}
		return QualifiedName.create(allSegments);
	}

	/**
	 * Null-safe appending of one or more segments.
	 */
	public static QualifiedName append(QualifiedName prefix, List<String> segments) {
		if (prefix == null || segments == null) {
			return null;
		}
		if (segments.isEmpty()) {
			return prefix;
		}
		List<String> allSegments = new ArrayList<>(prefix.getSegmentCount() + segments.size());
		allSegments.addAll(prefix.getSegments());
		for (String segment : segments) {
			if (segment == null) {
				return null;
			}
			allSegments.add(segment);
		}
		return QualifiedName.create(allSegments);
	}

	/**
	 * Prefixing a qualified name.
	 */
	public static QualifiedName prepend(String segment, QualifiedName suffix) {
		if (segment == null) {
			return suffix;
		}
		QualifiedName qn = QualifiedName.create(segment);
		if (suffix != null) {
			qn = qn.append(suffix);
		}
		return qn;
	}

	/**
	 * Null-safe concatenation of qualified names.
	 */
	public static QualifiedName concat(QualifiedName prefix, QualifiedName qn) {
		if (prefix == null && qn == null) {
			return null;
		}
		if (prefix == null) {
			return qn;
		}
		if (qn == null) {
			return prefix;
		}
		List<String> segments = new ArrayList<>(prefix.getSegments());
		segments.addAll(qn.getSegments());
		return QualifiedName.create(segments);
	}

	/**
	 * Null-safe pop operation of the last segment.
	 */
	public static QualifiedName pop(QualifiedName qn) {
		if (qn == null) {
			return null;
		}
		if (qn.getSegmentCount() < 1) {
			return null;
		}
		List<String> segments = new ArrayList<>(qn.getSegments());
		segments.remove(segments.size() - 1);
		return QualifiedName.create(segments);
	}

	/**
	 * Removes all segments for which the given predicate returns <code>true</code>.
	 */
	public static QualifiedName removeIf(QualifiedName qn, Predicate<? super String> predicate) {
		return modifySegments(qn, s -> predicate.test(s) ? null : s);
	}

	/**
	 * Changes each segment of the given {@link QualifiedName} using the given function.
	 *
	 * @param qn
	 *            the qualified name to encode.
	 * @param fn
	 *            transformation function; will be invoked for each segment. May return <code>null</code> to denote that
	 *            a segment is to be removed.
	 */
	public static QualifiedName modifySegments(QualifiedName qn, Function<String, String> fn) {
		if (qn == null || qn.isEmpty()) {
			return qn;
		}
		int len = qn.getSegmentCount();
		List<String> segsModified = new ArrayList<>(len);
		for (int i = 0; i < len; i++) {
			String seg = qn.getSegment(i);
			String segModified = fn.apply(seg);
			if (segModified != null) {
				segsModified.add(segModified);
			}
		}
		return QualifiedName.create(segsModified);
	}

	/** @return <code>true</code> iff the given qualified name denotes a globally available element. */
	public static boolean isGlobal(QualifiedName qn) {
		return qn != null && !qn.isEmpty()
				&& N4JSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT.equals(qn.getFirstSegment());
	}

	/** @return <code>true</code> iff the given qualified name denotes an ambient module. */
	public static boolean isAmbient(QualifiedName name) {
		return name != null && name.getSegmentCount() > 0
				&& name.getFirstSegment().equals(N4JSQualifiedNameProvider.AMBIENT_MODULE_SEGMENT);
	}

	/**
	 * Trims the {@link N4JSQualifiedNameProvider#MODULE_CONTENT_SEGMENT MODULE_CONTENT_SEGMENT} and all following
	 * segments from the given name. Returns <code>null</code> if it does not contain a {@code MODULE_CONTENT_SEGMENT}.
	 */
	public static QualifiedName trimModuleContent(QualifiedName qn) {
		if (qn == null || qn.isEmpty()) {
			return qn;
		}
		int segmentCount = qn.getSegmentCount();
		for (int i = 0; i < segmentCount; i++) {
			if (N4JSQualifiedNameProvider.MODULE_CONTENT_SEGMENT.equals(qn.getSegment(i))) {
				return qn.skipLast(segmentCount - i);
			}
		}
		return null;
	}

	/**
	 * Returns a name with all segments following the {@link N4JSQualifiedNameProvider#MODULE_CONTENT_SEGMENT
	 * MODULE_CONTENT_SEGMENT} in the given name. Returns <code>null</code> if it does not contain a
	 * {@code MODULE_CONTENT_SEGMENT}.
	 */
	public static QualifiedName getModuleContent(QualifiedName qn) {
		if (qn == null || qn.isEmpty()) {
			return qn;
		}
		int segmentCount = qn.getSegmentCount();
		for (int i = 0; i < segmentCount; i++) {
			if (N4JSQualifiedNameProvider.MODULE_CONTENT_SEGMENT.equals(qn.getSegment(i))) {
				return qn.skipFirst(i + 1);
			}
		}
		return null;
	}

	/** Like {@link #toHumanReadableString(QualifiedName, boolean)}, without skipping the last segment. */
	public static String toHumanReadableString(QualifiedName qn) {
		return toHumanReadableString(qn, false);
	}

	/**
	 * Returns a human-readable string representation for the given qualified name, optionally skipping the last
	 * segment.
	 *
	 * @param skipLast
	 *            if <code>true</code>, the last segment will be skipped, except it is the only segment emitted.
	 */
	public static String toHumanReadableString(QualifiedName qn, boolean skipLast) {
		StringBuilder sb = new StringBuilder();
		String delimiter = N4JSQualifiedNameConverter.DELIMITER;
		final int segCount = qn.getSegmentCount();
		for (int i = 0; i < segCount - (skipLast ? 1 : 0); i++) {
			String seg = qn.getSegment(i);
			if (i == 0 && seg.equals(N4JSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT)) {
				continue;
			}
			if (seg.equals(N4JSQualifiedNameProvider.MODULE_CONTENT_SEGMENT)) {
				delimiter = ".";
				continue;
			}
			if (sb.length() > 0) {
				sb.append(delimiter);
			}
			sb.append(seg);
		}
		if (skipLast && sb.length() == 0 && !qn.isEmpty()) {
			// skipping the last segment must not lead to an empty string
			return qn.getLastSegment();
		}
		return sb.toString();
	}
}
