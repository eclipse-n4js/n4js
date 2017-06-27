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
package org.eclipse.n4js.ui.utils;

import java.util.function.Consumer;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * Implementation of {@link SelectionAdapter} that uses function references to delegate selection events handling.
 */
public class DelegatingSelectionChangedListener implements ISelectionChangedListener {

	private final Consumer<SelectionChangedEvent> selectionChangedHandler;

	/**
	 * Provided handlers are used in methods for handling events in widget selections. If provided handlers are null,
	 * handling will be delegated to the super class.
	 *
	 * @param selectionChangedHandler
	 *            handler invoked for calls to {@link SelectionAdapter#widgetSelected(SelectionEvent)}
	 */
	public DelegatingSelectionChangedListener(Consumer<SelectionChangedEvent> selectionChangedHandler) {
		super();
		this.selectionChangedHandler = selectionChangedHandler != null
				? selectionChangedHandler
				: this::noopHandler;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		selectionChangedHandler.accept(event);
	}

	private void noopHandler(@SuppressWarnings("unused") SelectionChangedEvent event) {
		// NOOP
	}
}
