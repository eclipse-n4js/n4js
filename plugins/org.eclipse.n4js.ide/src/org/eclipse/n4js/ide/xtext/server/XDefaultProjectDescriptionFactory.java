/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.resource.impl.ProjectDescription;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.11
 */
public class XDefaultProjectDescriptionFactory implements XIProjectDescriptionFactory {

	@Override
	public ProjectDescription getProjectDescription(XIProjectConfig config) {
		ProjectDescription result = new ProjectDescription();
		result.setName(config.getName());
		result.getDependencies().addAll(config.getDependencies());
		return result;
	}

}
