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
package org.eclipse.n4js.ui.preferences.external;

import java.util.LinkedList;
import java.util.List;

/**
 * Value object to be passed as parameter instead of series of booleans. Captures decisions to run specific maintenance
 * actions. Typically created based on user input and passed to appropriate handlers.
 */
public class MaintenanceActionsChoice {

	/** Simple constructor that stores provided values. */
	public MaintenanceActionsChoice(boolean decisionResetTypeDefinitions, boolean decisionCleanCache,
			boolean decisionReinstall, boolean decisionPurgeNpm, boolean decisionReload) {

		this.decisionResetTypeDefinitions = decisionResetTypeDefinitions;
		this.decisionCleanCache = decisionCleanCache;
		this.decisionReinstall = decisionReinstall;
		this.decisionPurgeNpm = decisionPurgeNpm;
		this.decisionReload = decisionReload;
	}

	/** Flag marks if reseting type definitions should be performed. */
	public final boolean decisionResetTypeDefinitions;
	/** Flag marks if npm cache clean should be performed. */
	public final boolean decisionCleanCache;
	/** Flag marks if reinstalling currently installed npms should be performed. */
	public final boolean decisionReinstall;
	/** Flag marks if all npms should be deleted should be performed. */
	public final boolean decisionPurgeNpm;
	/** Flag marks if libraries state should be reloaded from disk should be performed. */
	public final boolean decisionReload;

	@Override
	public String toString() {
		List<String> actionTexts = new LinkedList<>();
		if (decisionResetTypeDefinitions) {
			actionTexts.add(MaintenanceActionsButtonListener.ACTION_TYPE_DEFINITIONS_RESET);
		}
		if (decisionCleanCache) {
			actionTexts.add(MaintenanceActionsButtonListener.ACTION_NPM_CACHE_CLEAN);
		}
		if (decisionReinstall) {
			actionTexts.add(MaintenanceActionsButtonListener.ACTION_NPM_REINSTALL);
		}
		if (decisionPurgeNpm) {
			actionTexts.add(MaintenanceActionsButtonListener.ACTION_NPM_PACKAGES_DELETE);
		}
		if (decisionReload) {
			actionTexts.add(MaintenanceActionsButtonListener.ACTION_NPM_RELOAD);
		}
		return String.join(", ", actionTexts);
	}
}
