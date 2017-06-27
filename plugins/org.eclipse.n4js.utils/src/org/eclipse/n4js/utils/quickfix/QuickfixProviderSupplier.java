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
package org.eclipse.n4js.utils.quickfix;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;

import com.google.common.base.Supplier;

/**
 * Supplies a {@link DefaultQuickfixProvider quickfix provider} instance.
 */
public interface QuickfixProviderSupplier extends Supplier<DefaultQuickfixProvider> {

	/**
	 * Unique ID of the {@code quickfixProviderSupplier} extension point.
	 */
	String EXTENSION_POINT_ID = "org.eclipse.n4js.utils.quickfixProviderSupplier";

	/**
	 * The {@link IConfigurationElement configuration element} property name that will be used to instantiate the
	 * concrete {@link QuickfixProviderSupplier supplier} instance.
	 */
	String CLAZZ_PROPERTY_NAME = "class";

	/**
	 * Returns with the supplier {@link DefaultQuickfixProvider quickfix provider} instance.
	 *
	 * @return the quick fix provider instance.
	 */
	@Override
	DefaultQuickfixProvider get();

}
