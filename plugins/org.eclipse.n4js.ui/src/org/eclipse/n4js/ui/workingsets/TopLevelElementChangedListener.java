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

/**
 * Listener that will be notified when the top level element configuration changes in the navigator.
 */
public interface TopLevelElementChangedListener {

	/**
	 * Receives a notification after the top level element configuration has been changed in the navigator.
	 *
	 * @param workingSetTopLevel
	 *            the new value. {@code true} if working sets were selected as top level elements, otherwise
	 *            {@code false}.
	 */
	void topLevelElementChanged(final boolean workingSetTopLevel);

}
