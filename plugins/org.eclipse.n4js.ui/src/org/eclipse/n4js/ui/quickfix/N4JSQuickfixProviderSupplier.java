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
package org.eclipse.n4js.ui.quickfix;

import org.eclipse.n4js.ui.utils.N4JSInjectorSupplier;
import org.eclipse.n4js.utils.ui.quickfix.QuickfixProviderSupplier;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;

import com.google.inject.Injector;

/**
 * Supplies the {@link N4JSQuickfixProvider quickfix provider} service for the N4JS language.
 */
public class N4JSQuickfixProviderSupplier implements QuickfixProviderSupplier {

	@Override
	public DefaultQuickfixProvider get() {
		return getInjector().getInstance(N4JSQuickfixProvider.class);
	}

	private Injector getInjector() {
		return new N4JSInjectorSupplier().get();
	}

}
