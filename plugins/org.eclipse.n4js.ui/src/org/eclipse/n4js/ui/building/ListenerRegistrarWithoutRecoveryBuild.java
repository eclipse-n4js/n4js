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

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.xtext.builder.impl.ProjectOpenedOrClosedListener;
import org.eclipse.xtext.ui.shared.contribution.IEagerContribution;
import org.eclipse.xtext.ui.shared.internal.ListenerRegistrar;

import com.google.inject.Inject;

/**
 * Same as {@link ListenerRegistrar} but avoid the recovery build while the injector is still being created.
 */
@SuppressWarnings("restriction")
public class ListenerRegistrarWithoutRecoveryBuild implements IEagerContribution {

	@Inject
	private ProjectOpenedOrClosedListener listener;
	@Inject
	private IWorkspace workspace;

	@Override
	public void initialize() {
		workspace.addResourceChangeListener(listener);
	}

	@Override
	public void discard() {
		workspace.removeResourceChangeListener(listener);
	}

}
