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

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * Implementation of {@link SelectionAdapter} that uses function references to delegate selection events handling.
 */
public class DelegatingSelectionAdapter extends SelectionAdapter {

	private final Consumer<SelectionEvent> selectedHandler;
	private final Consumer<SelectionEvent> defaultSelectedHandler;

	/**
	 * Provided handlers are used in methods for handling events in widget selections. If provided handlers are null,
	 * handling will be delegated to the super class.
	 *
	 * @param selectedHandler
	 *            handler invoked for calls to {@link SelectionAdapter#widgetSelected(SelectionEvent)}
	 * @param defaultSelectedHandler
	 *            handler invoked for calls to {@link SelectionAdapter#widgetDefaultSelected(SelectionEvent)}
	 */
	public DelegatingSelectionAdapter(Consumer<SelectionEvent> selectedHandler,
			Consumer<SelectionEvent> defaultSelectedHandler) {
		super();
		this.selectedHandler = selectedHandler != null
				? selectedHandler
				: super::widgetSelected;
		this.defaultSelectedHandler = defaultSelectedHandler != null
				? defaultSelectedHandler
				: super::widgetDefaultSelected;
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {
		selectedHandler.accept(e);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		defaultSelectedHandler.accept(e);
	}

	/** Convenience factory for {@link DelegatingSelectionAdapter} with custom selection handler */
	public static DelegatingSelectionAdapter createSelectionListener(final Consumer<SelectionEvent> handler) {
		return new DelegatingSelectionAdapter(handler, null);
	}

	/** Convenience factory for {@link DelegatingSelectionAdapter} with custom default selection handler */
	public static DelegatingSelectionAdapter createDefaultSelectionListener(final Consumer<SelectionEvent> handler) {
		return new DelegatingSelectionAdapter(null, handler);
	}
}
