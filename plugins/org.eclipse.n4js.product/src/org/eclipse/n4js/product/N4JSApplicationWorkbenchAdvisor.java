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

import static org.eclipse.n4js.product.N4JSPerspective.N4JS_PERSPECTIVE_ID;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.ide.application.IDEWorkbenchAdvisor;

/**
 * Class for configuring the N4JS IDE workbench.
 */
@SuppressWarnings("restriction")
public class N4JSApplicationWorkbenchAdvisor extends IDEWorkbenchAdvisor {

	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer configurer) {
		return new N4JSApplicationWorkbenchWindowAdvisor(this, configurer);
	}

	@Override
	public String getInitialWindowPerspectiveId() {
		return N4JS_PERSPECTIVE_ID;
	}

}
