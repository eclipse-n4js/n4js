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
package org.eclipse.n4js.n4jsx.ui.labeling

import com.google.inject.Inject
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider

/**
 * Provides labels for EObjects.
 *
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class N4JSXLabelProvider extends N4JSLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}
}
