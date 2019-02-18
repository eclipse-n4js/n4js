/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.ui.shared.internal.SharedModule
 *	in bundle org.eclipse.xtext.ui.shared
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui;

import org.eclipse.n4js.ui.search.MyReferenceSearchResultContentProvider;
import org.eclipse.xtext.ui.editor.findrefs.ReferenceSearchResultContentProvider;
import org.eclipse.xtext.ui.editor.findrefs.ReferenceSearchResultLabelProvider;
import org.eclipse.xtext.ui.editor.findrefs.ReferenceSearchViewPage;
import org.eclipse.xtext.ui.editor.findrefs.ReferenceSearchViewSorter;

import com.google.inject.AbstractModule;
import com.google.inject.PrivateModule;

/**
 * This overriding module binds ReferenceSearchResultContentProvider to a custom MyReferenceSearchResultContentProvider
 * that aims to fix GH-724.
 */
@SuppressWarnings("restriction")
public class MyReferenceSearchResultContentProviderCustomModule extends AbstractModule {

	@Override
	protected void configure() {
		// As a workaround to fix GH-724, this is copied from org.eclipse.xtext.ui.shared.internal.SharedModule
		// and the default must be kept in sync with the corresponding code there!!
		binder().install(new PrivateModule() {
			@Override
			protected void configure() {
				bind(ReferenceSearchViewPage.class);
				bind(ReferenceSearchResultLabelProvider.class);
				// Workaround to fix GH-724.
				bind(ReferenceSearchResultContentProvider.class).to(MyReferenceSearchResultContentProvider.class);
				bind(ReferenceSearchViewSorter.class);

				expose(ReferenceSearchViewPage.class);
			}
		});
	}
}
