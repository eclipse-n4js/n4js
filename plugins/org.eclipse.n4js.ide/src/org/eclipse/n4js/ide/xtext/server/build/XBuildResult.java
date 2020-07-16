/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.util.List;

import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The result of the build. Encapsulates the new index state and the list of changes.
 */
public class XBuildResult {
	private final ResourceDescriptionsData index;
	private final XSource2GeneratedMapping fileMappings;
	private final List<IResourceDescription.Delta> affectedResources;

	/** Constructor. */
	public XBuildResult(ResourceDescriptionsData index, XSource2GeneratedMapping fileMappings,
			List<IResourceDescription.Delta> affectedResources) {

		super();
		this.index = index;
		this.fileMappings = fileMappings;
		this.affectedResources = affectedResources;
	}

	/** Getter. */
	public ResourceDescriptionsData getIndex() {
		return this.index;
	}

	/** Getter. */
	public XSource2GeneratedMapping getFileMappings() {
		return this.fileMappings;
	}

	/** @return all deltas of resources that have been built. */
	public List<IResourceDescription.Delta> getAffectedResources() {
		return this.affectedResources;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((this.index == null) ? 0 : this.index.hashCode());
		result = prime * result + ((this.fileMappings == null) ? 0 : this.fileMappings.hashCode());
		return prime * result + ((this.affectedResources == null) ? 0 : this.affectedResources.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XBuildResult other = (XBuildResult) obj;
		if (this.index == null) {
			if (other.index != null)
				return false;
		} else if (!this.index.equals(other.index))
			return false;
		if (this.fileMappings == null) {
			if (other.fileMappings != null)
				return false;
		} else if (!this.fileMappings.equals(other.fileMappings))
			return false;
		if (this.affectedResources == null) {
			if (other.affectedResources != null)
				return false;
		} else if (!this.affectedResources.equals(other.affectedResources))
			return false;
		return true;
	}

	@Override
	public String toString() {
		ToStringBuilder b = new ToStringBuilder(this);
		b.add("index", this.index);
		b.add("fileMappings", this.fileMappings);
		b.add("affectedResources", this.affectedResources);
		return b.toString();
	}

}