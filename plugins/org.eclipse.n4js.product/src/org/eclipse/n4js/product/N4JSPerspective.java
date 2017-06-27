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
package org.eclipse.n4js.product;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * {@link IPerspectiveFactory Perspective factory} implementation for creating the N4JS IDE specific perspective.
 */
public class N4JSPerspective implements IPerspectiveFactory {

	/**
	 * Unique ID of the N4JS IDE perspective. <br>
	 * {@value}
	 */
	public static final String N4JS_PERSPECTIVE_ID = "org.eclipse.n4js.product.N4JSPerspective"; //$NON-NLS-1$

	@Override
	public void createInitialLayout(final IPageLayout layout) {
		// layout initialization is done via plugin.xml
	}

}
