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
package org.eclipse.n4js.ui.preferences;

import org.eclipse.n4js.generator.CompilerDescriptor;
import org.eclipse.n4js.generator.CompilerProperties;
import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;

import com.google.inject.Inject;

/**
 * Inspired by org.eclipse.xtext.ui.editor.syntaxcoloring.PreferenceStoreAccessor.
 */
public class N4JSPreferenceStoreAccessor extends AbstractN4JSPreferenceStoreAccessor<CompilerDescriptor> {
	/**
	 * @param scopedAccessor
	 *            the class to use to access the preference store
	 */
	@Inject
	public N4JSPreferenceStoreAccessor(PreferenceStoreAccessImpl scopedAccessor) {
		super(scopedAccessor);
	}

	@Override
	protected CompilerProperties[] getComponentPropertiesValues() {
		return CompilerProperties.values();
	}
}
