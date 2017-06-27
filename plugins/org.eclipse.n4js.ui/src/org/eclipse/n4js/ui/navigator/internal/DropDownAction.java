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
package org.eclipse.n4js.ui.navigator.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;

import com.google.common.base.Strings;

/**
 * Abstract drop down action implementation.
 */
public abstract class DropDownAction extends Action implements IMenuCreator {

	private Menu menu;

	/**
	 * Creates a new drop down action with text and without image descriptor.
	 *
	 * @param text
	 *            the text of the action. Can be {@code null}, in such cases empty string will be used as the text of
	 *            the action.
	 */
	protected DropDownAction(final String text) {
		this(text, null);
	}

	/**
	 * Creates a new drop down action with image descriptor and without text.
	 *
	 * @param imageDescriptor
	 *            the image descriptor for the action. Can be {@code null}, when {@code null}, no image will be shown
	 *            for the action in the UI.
	 */
	protected DropDownAction(final ImageDescriptor imageDescriptor) {
		this("", imageDescriptor);
	}

	/**
	 * Creates a new drop down action with the given action text and image descriptor.
	 *
	 * @param text
	 *            the text of the action. Can be {@code null}, in such cases empty string will be used as the text of
	 *            the action.
	 * @param imageDescriptor
	 *            the image descriptor for the action. Can be {@code null}, when {@code null}, no image will be shown
	 *            for the action in the UI.
	 */
	protected DropDownAction(final String text, final ImageDescriptor imageDescriptor) {
		setMenuCreator(this);
		if (null != imageDescriptor) {
			setImageDescriptor(imageDescriptor);
		}
		if (!Strings.isNullOrEmpty(text)) {
			setText(text);
		}
	}

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

	@Override
	public void runWithEvent(final Event event) {
		if (event.widget instanceof ToolItem) {
			final ToolItem toolItem = (ToolItem) event.widget;
			final Control control = toolItem.getParent();
			@SuppressWarnings("hiding")
			final Menu menu = getMenu(control);
			final Rectangle bounds = toolItem.getBounds();
			final Point topLeft = new Point(bounds.x, bounds.y + bounds.height);
			menu.setLocation(control.toDisplay(topLeft));
			menu.setVisible(true);
		}
	}

	@Override
	public Menu getMenu(final Control parent) {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
		menu = new Menu(parent);
		createMenuItems(menu);
		return menu;
	}

	@Override
	public Menu getMenu(final Menu parent) {
		return null;
	}

	@Override
	public String getId() {
		return this.getClass().getName();
	}

	/**
	 * Creates the menu items for the drop down action by hooking up the created items to the parent menu argument.
	 *
	 * @param parent
	 *            the parent menu of the new items that has to be created.
	 */
	protected abstract void createMenuItems(Menu parent);

	/**
	 * Creates a new separator by hooking it into the given {@code parent} menu argument.
	 *
	 * @param parent
	 *            the parent menu.
	 * @return the new separator instance.
	 */
	protected MenuItem createSeparator(final Menu parent) {
		return new MenuItem(parent, SWT.SEPARATOR);
	}

}
