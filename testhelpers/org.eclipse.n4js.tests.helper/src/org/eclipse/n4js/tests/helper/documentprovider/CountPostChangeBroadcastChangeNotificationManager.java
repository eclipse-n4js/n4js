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
package org.eclipse.n4js.tests.helper.documentprovider;

import org.eclipse.core.internal.events.NotificationManager;
import org.eclipse.core.internal.events.ResourceChangeEvent;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.internal.watson.ElementTree;
import org.eclipse.core.resources.IResourceChangeEvent;

/**
 * This class counts the number of broadcastChange method calls.
 */
@SuppressWarnings("restriction")
public class CountPostChangeBroadcastChangeNotificationManager extends NotificationManager {
	Thread mainThread;
	int countPostChangeBroadcastTriggered;

	CountPostChangeBroadcastChangeNotificationManager(Workspace workspace, Thread mainThread) {
		super(workspace);
		this.mainThread = mainThread;
	}

	@Override
	public void broadcastChanges(ElementTree lastState, ResourceChangeEvent event, boolean lockTree) {
		// Count the post change events triggered on the main thread
		if (Thread.currentThread() == mainThread && event.getType() == IResourceChangeEvent.POST_CHANGE) {
			countPostChangeBroadcastTriggered++;
		}
		super.broadcastChanges(lastState, event, lockTree);
	}

	
	public int numberPostChangeTriggered() {
		return countPostChangeBroadcastTriggered;
	}
}
