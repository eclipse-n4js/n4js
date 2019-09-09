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
package org.eclipse.n4js.tests.defaultPreferences;

import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.junit.Test;

/**
 * Checks all default preferences defined in org.eclipse.n4js.product/plugin_customization.ini
 */
public class CheckDefaultPreferencesPluginUITest extends AbstractBuilderParticipantTest {

	/** Checks default preferences bundle by bundle */
	@Test
	public void testDefaultPreferences() {

		SharedDefaultPreferences.testDefaultPreferences();

	}

}
