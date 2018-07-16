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
package org.eclipse.n4js.json.ui.labeling;

import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.label.DefaultDescriptionLabelProvider;

/**
 * Provides labels for {@link IEObjectDescription}s that represent JSON model
 * elements in the index.
 * 
 * For now, this does nothing as the content of JSON resources is not exposed to
 * the index.
 */
public class JSONDescriptionLabelProvider extends DefaultDescriptionLabelProvider {
	// no custom behavior
}
