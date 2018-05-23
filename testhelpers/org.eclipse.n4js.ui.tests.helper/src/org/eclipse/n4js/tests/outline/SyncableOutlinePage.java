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
package org.eclipse.n4js.tests.outline;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeoutException;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineRefreshJob;

/**
 * This horrible hack allows to wait for outline tree updates without blocking the display thread.
 *
 *
 *         Copied and adapted from
 *         /org.eclipse.xtext.ui.tests/tests/org/eclipse/xtext/ui/tests/editor/outline/SyncableOutlinePage.java
 */
@SuppressWarnings("javadoc")
public class SyncableOutlinePage {
	private volatile DisplaySafeSyncer syncer = new DisplaySafeSyncer();
	private final OutlinePage page;

	SyncableOutlinePage(OutlinePage page) {
		this.page = page;
	}

	public void resetSyncer() throws InterruptedException {
		try {
			Method method = OutlinePage.class.getDeclaredMethod("getRefreshJob");
			method.setAccessible(true);
			OutlineRefreshJob refreshPageJob = (OutlineRefreshJob) method.invoke(page);
			refreshPageJob.join();
			syncer.start();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void waitForUpdate(long timeout) throws TimeoutException, InterruptedException {
		syncer.awaitSignal(timeout);
	}

	protected void treeUpdated() {
		try {
			Method method = OutlinePage.class.getDeclaredMethod("treeUpdated");
			method.setAccessible(true);
			method.invoke(page);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		syncer.signal();
	}

	public TreeViewer getTreeViewer() {
		return page.getTreeViewer();
	}
}
