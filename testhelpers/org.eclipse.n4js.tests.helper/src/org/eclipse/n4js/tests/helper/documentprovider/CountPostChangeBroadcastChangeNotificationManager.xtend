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
package org.eclipse.n4js.tests.helper.documentprovider

import org.eclipse.core.internal.events.NotificationManager
import org.eclipse.core.internal.events.ResourceChangeEvent
import org.eclipse.core.internal.resources.Workspace
import org.eclipse.core.internal.watson.ElementTree
import org.eclipse.core.resources.IResourceChangeEvent

/**
  * This class counts the number of broadcastChange method calls.
  */
class CountPostChangeBroadcastChangeNotificationManager extends NotificationManager {

	int countPostChangeBroadcastTriggered;

	new(Workspace workspace) {
		super(workspace)
	}

	override void broadcastChanges(ElementTree lastState, ResourceChangeEvent event, boolean lockTree) {
		if (event.type == IResourceChangeEvent.POST_CHANGE)
			countPostChangeBroadcastTriggered++
		super.broadcastChanges(lastState, event, lockTree)
	}

	def numberPostChangeTriggered() {
		return countPostChangeBroadcastTriggered;
	}
}
 