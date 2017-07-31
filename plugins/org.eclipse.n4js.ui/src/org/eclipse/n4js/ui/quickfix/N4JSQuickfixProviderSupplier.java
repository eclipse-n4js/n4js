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

import static org.eclipse.n4js.ui.internal.N4JSActivator.ORG_ECLIPSE_N4JS_N4JS;

import org.eclipse.n4js.ui.internal.N4JSActivator;
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
		return N4JSActivator.getInstance().getInjector(ORG_ECLIPSE_N4JS_N4JS);
	}

}
