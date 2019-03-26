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
package org.eclipse.n4js.ui.building;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.impl.QueuedBuildData;
import org.eclipse.xtext.builder.impl.ToBeBuilt;

// FIXME GH-1234: remove this class which is now obsolete after adjustments were moved to Xtext builder!
@SuppressWarnings({ "restriction", "javadoc" })
public class BuildDataWithRequestRebuild extends BuildData {

	private final Runnable needRebuild;

	public BuildDataWithRequestRebuild(String projectName, ResourceSet resourceSet, ToBeBuilt toBeBuilt,
			QueuedBuildData queuedBuildData, boolean indexingOnly, Runnable needRebuild) {
		super(projectName, resourceSet, toBeBuilt, queuedBuildData, indexingOnly);
		this.needRebuild = needRebuild;
	}

	public BuildDataWithRequestRebuild(String projectName, ResourceSet resourceSet, ToBeBuilt toBeBuilt,
			QueuedBuildData queuedBuildData, Runnable needRebuild) {
		super(projectName, resourceSet, toBeBuilt, queuedBuildData);
		this.needRebuild = needRebuild;
	}

	public void needRebuild() {
		needRebuild.run();
	}

}
