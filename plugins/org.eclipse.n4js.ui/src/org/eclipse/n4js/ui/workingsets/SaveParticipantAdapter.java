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

import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.runtime.CoreException;

/**
 * This adapter class provides default implementations for the methods described by the {@link ISaveParticipant}
 * interface.
 *
 * <p>
 * Classes that wish to deal with {@link ISaveContext} notifications can extend this class and override only the methods
 * which they are interested in.
 */
class SaveParticipantAdapter implements ISaveParticipant {

	@Override
	public void doneSaving(final ISaveContext context) {
		// Nothing by default.
	}

	@Override
	public void prepareToSave(final ISaveContext context) throws CoreException {
		// Nothing by default.
	}

	@Override
	public void rollback(final ISaveContext context) {
		// Nothing by default.
	}

	@Override
	public void saving(final ISaveContext context) throws CoreException {
		// Nothing by default.
	}

}
