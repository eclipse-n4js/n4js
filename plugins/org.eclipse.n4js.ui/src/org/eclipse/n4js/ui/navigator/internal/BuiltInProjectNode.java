/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.navigator.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.swt.graphics.Image;

/**
 * Node for representing an external N4JS library in the Project Explorer view.
 */
/* default */ class BuiltInProjectNode extends NodeAdapter {

	private static final Image EXT_LIB_IMG = ImageRef.EXTERNAL_LIB_PROJECT.asImage().orNull();

	private final IN4JSProject project;

	/**
	 * Creates a new node for an N4JS external library.
	 *
	 * @param parent
	 *            the parent of the current node.
	 * @param project
	 *            the wrapped external project.
	 */
	/* default */ BuiltInProjectNode(final Node parent, final IN4JSProject project) {
		super(parent);
		checkNotNull(project, "project");
		checkArgument(project.exists(), "Project '" + project.getProjectName() + "' does not exist.");
		this.project = project;
	}

	@Override
	public Image getImage() {
		return EXT_LIB_IMG;
	}

	@Override
	public String getText() {
		return project.getProjectName().getRawName();
	}

	@Override
	public Object[] getChildren() {

		final ResourceNode manifestNode = getManifestResourceNode();

		final List<ResourceNode> childrenList = new LinkedList<>();
		for (IN4JSSourceContainer srcContainer : project.getSourceContainers()) {
			SafeURI<?> location = srcContainer.getLocation();
			String label = srcContainer.getRelativeLocation();
			ResourceNode resourceNode = ResourceNode.create(this, location, label);
			if (resourceNode != null) {
				childrenList.add(resourceNode);
			}
		}
		final ResourceNode[] children = childrenList.toArray(new ResourceNode[0]);

		return null != manifestNode ? Arrays2.add(children, manifestNode) : children;

	}

	/** Helper method to fake the manifest node if it is not in a source folder. Otherwise does nothing. */
	private ResourceNode getManifestResourceNode() {
		ResourceNode manifestNode = null;
		// Does nothing if the project root is a source container as well.
		if (!from(project.getSourceContainers()).transform(src -> src.getRelativeLocation()).contains("")) {
			final SafeURI<?> manifestLocation = project.getProjectDescriptionLocation();
			if (manifestLocation != null && manifestLocation.isFile()) {
				manifestNode = ResourceNode.create(this, manifestLocation);
			}
		}
		return manifestNode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project.getProjectName() == null) ? 0 : project.getProjectName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BuiltInProjectNode)) {
			return false;
		}
		BuiltInProjectNode other = (BuiltInProjectNode) obj;
		if (project.getProjectName() == null) {
			if (other.project.getProjectName() != null) {
				return false;
			}
		} else if (!project.getProjectName().equals(other.project.getProjectName())) {
			return false;
		}
		return true;
	}

}
