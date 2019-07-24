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
package org.eclipse.n4js.external;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.n4js.projectModel.locations.FileURI;

import com.google.inject.Singleton;

/**
 * The {@link HlcExternalIndexSynchronizer} does not perform any synchronizations.
 */
@Singleton
public class HlcExternalIndexSynchronizer extends ExternalIndexSynchronizer {

	@Override
	public void synchronizeNpms(IProgressMonitor monitor) {
		return;
	}

	@Override
	public void synchronizeNpms(IProgressMonitor monitor, Collection<LibraryChange> forcedChangeSet) {
		return;
	}

	@Override
	public void reindexAllExternalProjects(IProgressMonitor monitor) {
		return;
	}

	@Override
	public boolean isInIndex(FileURI projectLocation) {
		// we assume all projects to be in the index in the headless case
		return true;
	}

}
