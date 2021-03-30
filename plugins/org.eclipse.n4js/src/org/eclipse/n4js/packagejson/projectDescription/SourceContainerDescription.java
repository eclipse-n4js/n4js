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
package org.eclipse.n4js.packagejson.projectDescription;

import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.utils.ImmutableDataClass;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * Lists one or more source folders inside the project with a common intended usage (e.g. main sources, tests).
 */
@SuppressWarnings("javadoc")
public class SourceContainerDescription extends ImmutableDataClass {

	private final SourceContainerType type;
	private final ImmutableList<String> paths;

	public SourceContainerDescription(SourceContainerType type, Iterable<String> paths) {
		this.type = type;
		this.paths = ImmutableList.copyOf(paths);
	}

	public SourceContainerType getType() {
		return type;
	}

	public List<String> getPaths() {
		return paths;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				paths,
				type);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		SourceContainerDescription other = (SourceContainerDescription) obj;
		return Objects.equals(paths, other.paths)
				&& type == other.type;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " { type: " + getType()
				+ ", paths: " + (paths.isEmpty() ? "[]" : "[ " + Joiner.on(", ").join(paths) + " ]")
				+ " }";
	}
}
