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
package org.eclipse.n4js.generator.headless;

import java.util.Comparator;

import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 * Naive {@link IN4JSProject comparator}, used for sorting and comparing project dependencies by their
 * {@link IN4JSProject#getProjectId()}
 */
public class N4JSProjectComparator implements Comparator<IN4JSProject> {

	/** Stateless reusable instance. */
	public final static N4JSProjectComparator INSTANCE = new N4JSProjectComparator();

	@Override
	public int compare(IN4JSProject o1, IN4JSProject o2) {
		return o1.getProjectId().compareTo(o2.getProjectId());
	}

}
