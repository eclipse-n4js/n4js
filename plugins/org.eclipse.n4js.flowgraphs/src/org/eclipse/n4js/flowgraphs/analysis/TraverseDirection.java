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
package org.eclipse.n4js.flowgraphs.analysis;

/**
 * Specifies the traverse direction of a {@link GraphVisitorInternal} instance.
 */
public enum TraverseDirection {
	/** Forward edge-direction begins from the entry node of a given container. */
	Forward,
	/** Backward edge-direction begins from the exit node of a given container. */
	Backward
}
