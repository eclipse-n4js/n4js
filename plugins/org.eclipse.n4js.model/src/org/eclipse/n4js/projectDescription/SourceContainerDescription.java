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
package org.eclipse.n4js.projectDescription;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableList;

/**
 * Lists one or more source folders inside the project with a common intended usage (e.g. main sources, tests).
 */
@SuppressWarnings("javadoc")
public class SourceContainerDescription {

	private final SourceContainerType sourceContainerType;
	private final ImmutableList<String> paths;

	public SourceContainerDescription(SourceContainerType sourceContainerType, Iterable<String> paths) {
		this.sourceContainerType = sourceContainerType;
		this.paths = ImmutableList.copyOf(paths);
	}

	public SourceContainerType getSourceContainerType() {
		return sourceContainerType;
	}

	public List<String> getPaths() {
		return paths;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				paths,
				sourceContainerType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SourceContainerDescription))
			return false;
		SourceContainerDescription other = (SourceContainerDescription) obj;
		return Objects.equals(paths, other.paths)
				&& sourceContainerType == other.sourceContainerType;
	}
}
