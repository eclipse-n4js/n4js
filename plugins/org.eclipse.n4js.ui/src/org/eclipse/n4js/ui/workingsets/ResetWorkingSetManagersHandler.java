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
package org.eclipse.n4js.ui.workingsets;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.google.inject.Inject;

/**
 * Handler for resetting the state of all working set managers.
 */
public class ResetWorkingSetManagersHandler extends AbstractHandler {

	/**
	 * The unique ID of the command that is associated with this handler.
	 */
	public static final String COMMAND_ID = ResetWorkingSetManagersHandler.class.getName();

	@Inject
	private WorkingSetManagerBrokerImpl workingSetManagerBroker;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		workingSetManagerBroker.resetState();
		return null;
	}

}
