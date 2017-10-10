/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.smith.dash.ui.graph;

import org.eclipse.jface.viewers.LabelProvider;

class VisualisationLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		return ((ListEntry) element).label;
	}
}