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
package org.eclipse.n4js.smith.dash.ui.views;

import org.eclipse.n4js.smith.dash.ui.ViewBuilder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * A view showing a graph of the AST and type model (i.e. TModule).
 */
public class DashboardView5 extends ViewPart {

	private static final String KEY = "InferenceContext";

	@Override
	public void createPartControl(final Composite parent) {
		ViewBuilder.createView(parent, KEY);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
