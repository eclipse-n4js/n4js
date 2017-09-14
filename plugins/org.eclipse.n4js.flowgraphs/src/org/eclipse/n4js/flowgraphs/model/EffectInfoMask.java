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

//TODO GH-235
@SuppressWarnings("javadoc")
public class EffectInfoMask {

	final public boolean showAssigned;
	final public boolean showDeclares;
	final public boolean showReferences;
	final public boolean showSEReads;
	final public boolean showSEWrites;

	public EffectInfoMask(
			boolean showAssigned,
			boolean showDeclares,
			boolean showReferences,
			boolean showSEReads,
			boolean showSEWrites) {

		this.showAssigned = showAssigned;
		this.showDeclares = showDeclares;
		this.showReferences = showReferences;
		this.showSEReads = showSEReads;
		this.showSEWrites = showSEWrites;
	}

	public EffectInfoMask(boolean show) {
		this.showAssigned = show;
		this.showDeclares = show;
		this.showReferences = show;
		this.showSEReads = show;
		this.showSEWrites = show;
	}

	public boolean isShowAssigned() {
		return showAssigned;
	}

	public boolean isShowDeclares() {
		return showDeclares;
	}

	public boolean isShowReferences() {
		return showReferences;
	}

	public boolean isShowSEReads() {
		return showSEReads;
	}

	public boolean isShowSEWrites() {
		return showSEWrites;
	}

}
