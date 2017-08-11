/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.model;

abstract public class AbstractEdge implements GraphElement {
	public final Node start;
	public final Node end;

	public AbstractEdge(Node start, Node end) {
		this.start = start;
		this.end = end;
	}

}
