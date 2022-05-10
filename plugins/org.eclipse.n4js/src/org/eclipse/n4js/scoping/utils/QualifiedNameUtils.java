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

import org.eclipse.xtext.naming.QualifiedName;

/**
 * Utilities for {@link QualifiedName}s.
 */
public class QualifiedNameUtils {

	/**
	 * Null-safe appending of a segment. If segment is null, null is returned. If prefix is null, a new qualified name
	 * (with non-null segment) is created.
	 */
	public static QualifiedName append(QualifiedName prefix, String segment) {
		if (segment == null) {
			return null;
		}
		if (prefix == null) {
			return QualifiedName.create(segment);
		}
		return prefix.append(segment);
	}

	/**
	 * Null-safe appending of one or more segments.
	 */
	public static QualifiedName append(QualifiedName prefix, List<String> segments) {
		if (segments == null) {
			return null;
		}
		if (segments.isEmpty()) {
			return prefix;
		}
		if (prefix == null) {
			return QualifiedName.create(segments);
		}
		List<String> allSegments = new ArrayList<>(prefix.getSegmentCount() + segments.size());
		allSegments.addAll(prefix.getSegments());
		allSegments.addAll(segments);
		return QualifiedName.create(allSegments);
	}

	/**
	 * Prefixing a qualified name. If the prefix segment is null, the suffix is returned, else if the suffix is null a
	 * QN with only one segment is returned. Otherwise segment gets prefixed to suffix.
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
	 * Changes each segment of the given {@link QualifiedName} using the given function.
	 *
	 * @param qn
	 *            the qualified name to encode.
	 * @param fn
	 *            transformation function; will be invoked for each segment.
	 */
	public static QualifiedName modifySegments(QualifiedName qn, Function<String, String> fn) {
		if (qn == null || qn.isEmpty()) {
			return qn;
		}
		int len = qn.getSegmentCount();
		String[] segsModified = new String[len];
		for (int i = 0; i < len; i++) {
			String seg = qn.getSegment(i);
			String segModified = fn.apply(seg);
			segsModified[i] = segModified;
		}
		return QualifiedName.create(segsModified);
	}
}
