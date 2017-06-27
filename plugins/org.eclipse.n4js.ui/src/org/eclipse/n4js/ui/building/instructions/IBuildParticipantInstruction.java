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
package org.eclipse.n4js.ui.building.instructions;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

/**
 */
public interface IBuildParticipantInstruction extends Adapter {

	/**
	 * The null-object pattern.
	 */
	IBuildParticipantInstruction NOOP = new NoopInstruction();

	/**
	 * Used to indicate that the build is done.
	 */
	void finish(List<Delta> deltas, IProgressMonitor progressMonitor) throws CoreException;

	/**
	 * Request the processing of the given delta.
	 */
	void process(Delta delta, ResourceSet resourceSet, IProgressMonitor progressMonitor) throws CoreException;

	/**
	 * @return <code>true</code> if the project requires a rebuild.
	 */
	boolean isRebuild();

}
