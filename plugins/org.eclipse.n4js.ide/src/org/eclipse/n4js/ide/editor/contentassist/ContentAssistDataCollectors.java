/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.editor.contentassist;

import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;

/**
 * Data collectors to be used in the content assist implementation.
 *
 * Bound as singleton in the UI and IDE module.
 */
public final class ContentAssistDataCollectors {

	private final DataCollector root;
	private final DataCollector createProposals;
	private final DataCollector createContexts;
	private final DataCollector createProposalsInner;
	private final DataCollector getScope;
	private final DataCollector getAllElements;
	private final DataCollector forEachElement;
	private final DataCollector getResolution;

	/**
	 * Constructor
	 */
	public ContentAssistDataCollectors(DataCollector parent) {
		this.root = create("createCompletions", parent);
		this.createProposals = create("createProposals", root);
		this.createContexts = create("createContexts", createProposals);
		this.createProposalsInner = create("inner createProposals", createProposals);
		this.getScope = create("getScopeForContentAssist", createProposalsInner);
		this.getAllElements = create("getAllElements", dcCreateProposalsInner());
		this.forEachElement = create("forEachElement", dcCreateProposalsInner());
		this.getResolution = create("getResolution", forEachElement);
	}

	/**
	 * The data collector that covers the entire chain from document and offset to proposals for the platform.
	 */
	public DataCollector dcCreateCompletionsRoot() {
		return root;
	}

	/**
	 * The data collector for the computation of the proposals in their internal representation.
	 */
	public DataCollector dcCreateProposals() {
		return createProposals;
	}

	/**
	 * The data collector for the computation of the content assist context from the resource information.
	 */
	public DataCollector dcCreateContexts() {
		return createContexts;
	}

	/**
	 * The data collector for the computation of the internal proposals
	 */
	public DataCollector dcCreateProposalsInner() {
		return createProposalsInner;
	}

	/**
	 * The data collector for the computation of the internal proposals
	 */
	public DataCollector dcGetScope() {
		return getScope;
	}

	/**
	 * The data collector for the computation of the internal proposals
	 */
	public DataCollector dcGetAllElements() {
		return getAllElements;
	}

	/**
	 * The data collector for the computation of the internal proposals
	 */
	public DataCollector dcIterateAllElements() {
		return forEachElement;
	}

	/**
	 * The data collector for the computation of the internal proposals
	 */
	public DataCollector dcGetResolution() {
		return getResolution;
	}

	private static DataCollector create(String key, DataCollector parent) {
		return DataCollectors.INSTANCE.getOrCreateDataCollector(key, parent);
	}
}
