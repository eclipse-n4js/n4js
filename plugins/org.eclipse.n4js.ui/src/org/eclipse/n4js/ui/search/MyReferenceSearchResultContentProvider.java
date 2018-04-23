/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.search;

import org.eclipse.xtext.resource.IResourceDescription.Event;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.ui.editor.findrefs.ReferenceSearchResultContentProvider;

import com.google.inject.Inject;

/**
 * Workaround to fix GH-724. We do not remove tree nodes in find references result when a resource is changed.
 */
@SuppressWarnings("restriction")
public class MyReferenceSearchResultContentProvider extends ReferenceSearchResultContentProvider {

	/**
	 * Constructor
	 */
	@Inject
	public MyReferenceSearchResultContentProvider(IResourceDescriptions resourceDescriptions) {
		super(resourceDescriptions);
	}

	@Override
	public void descriptionsChanged(final Event event) {
		// Do nothing. Workaround to fix GH-724
	}
}
