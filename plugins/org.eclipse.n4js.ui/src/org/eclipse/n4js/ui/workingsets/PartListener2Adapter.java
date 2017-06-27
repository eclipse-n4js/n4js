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

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

/**
 * This adapter class provides default implementations for the methods described by the {@link IPartListener2}
 * interface.
 *
 * <p>
 * Classes that wish to deal with {@link IWorkbenchPartReference} events can extend this class and override only the
 * methods which they are interested in.
 */
public class PartListener2Adapter implements IPartListener2 {

	@Override
	public void partActivated(final IWorkbenchPartReference partRef) {
		// Does nothing by default.
	}

	@Override
	public void partBroughtToTop(final IWorkbenchPartReference partRef) {
		// Does nothing by default.
	}

	@Override
	public void partClosed(final IWorkbenchPartReference partRef) {
		// Does nothing by default.
	}

	@Override
	public void partDeactivated(final IWorkbenchPartReference partRef) {
		// Does nothing by default.
	}

	@Override
	public void partOpened(final IWorkbenchPartReference partRef) {
		// Does nothing by default.
	}

	@Override
	public void partHidden(final IWorkbenchPartReference partRef) {
		// Does nothing by default.
	}

	@Override
	public void partVisible(final IWorkbenchPartReference partRef) {
		// Does nothing by default.
	}

	@Override
	public void partInputChanged(final IWorkbenchPartReference partRef) {
		// Does nothing by default.
	}

}
