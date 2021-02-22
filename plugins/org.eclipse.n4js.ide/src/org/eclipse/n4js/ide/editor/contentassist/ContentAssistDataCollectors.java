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

import static org.eclipse.n4js.smith.DataCollectors.INSTANCE;

import org.eclipse.n4js.ide.imports.ReferenceResolution;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * Data collectors to be used in the content assist implementation.
 *
 * Bound as singleton in the UI and IDE module.
 */
public final class ContentAssistDataCollectors {

	private final DataCollector root;
	private final DataCollector createProposals;
	private final DataCollector createContexts;
	private final DataCollector parseContexts;
	private final DataCollector parseFollowElements;
	private final DataCollector createProposalsInner;
	private final DataCollector getScope;
	private final DataCollector getAllElements;
	private final DataCollector forEachElement;
	private final DataCollector getResolution;
	private final DataCollector createReferenceResolutionCandidate1;
	private final DataCollector checkConflict;
	private final DataCollector createReferenceResolutionCandidate2;
	private final DataCollector createReferenceResolution;

	/**
	 * Constructor
	 */
	public ContentAssistDataCollectors(DataCollector root) {
		this.root = root;
		this.createProposals = create(root, "createProposals");
		this.createContexts = create(createProposals, "createContexts");
		this.parseContexts = createSerial(createContexts, "parseContext");
		this.parseFollowElements = createSerial(createContexts, "parseFollowElements");
		this.createProposalsInner = create(createProposals, "inner createProposals");
		this.getScope = create(createProposalsInner, "getScopeForContentAssist");
		this.getAllElements = create(dcCreateProposalsInner(), "scope.getAllElements");
		this.forEachElement = create(dcCreateProposalsInner(), "for(description in scope.allElements)");
		this.getResolution = create(forEachElement, "getResolution");
		this.createReferenceResolutionCandidate1 = create(getResolution, "createReferenceResolutionCandidate1");
		this.checkConflict = create(getResolution, "checkConflict");
		this.createReferenceResolutionCandidate2 = create(getResolution, "createReferenceResolutionCandidate2");
		this.createReferenceResolution = create(getResolution, "createReferenceResolution");
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
	 * The data collector for the computation of the content assist context from the resource information.
	 */
	public DataCollector dcParseContexts() {
		return parseContexts;
	}

	/**
	 * The data collector for the computation of the content assist context from the resource information.
	 */
	public DataCollector dcParseFollowElements() {
		return parseFollowElements;
	}

	/**
	 * The data collector for the computation of the internal proposals
	 */
	public DataCollector dcCreateProposalsInner() {
		return createProposalsInner;
	}

	/**
	 * The data collector for the computation of the scope for content assist.
	 */
	public DataCollector dcGetScope() {
		return getScope;
	}

	/**
	 * The data collector for calling {@link IScope#getAllElements()}.
	 */
	public DataCollector dcGetAllElements() {
		return getAllElements;
	}

	/**
	 * The data collector for the traversal of the {@link IEObjectDescription scope contents}.
	 */
	public DataCollector dcIterateAllElements() {
		return forEachElement;
	}

	/**
	 * The data collector for the computation of the resolutions.
	 */
	public DataCollector dcGetResolution() {
		return getResolution;
	}

	/**
	 * The data collector for the first computations when creating a ReferenceResolutionCandidate.
	 */
	public DataCollector dcCreateReferenceResolutionCandidate1() {
		return createReferenceResolutionCandidate1;
	}

	/**
	 * The data collector for the detection of conflicting proposals.
	 */
	public DataCollector dcDetectProposalConflicts() {
		return checkConflict;
	}

	/**
	 * The data collector for the last computations when creating a ReferenceResolutionCandidate.
	 */
	public DataCollector dcCreateReferenceResolutionCandidate2() {
		return createReferenceResolutionCandidate2;
	}

	/**
	 * The data collector for the creation of {@link ReferenceResolution}.
	 */
	public DataCollector dcCreateReferenceResolution() {
		return createReferenceResolution;
	}

	private static DataCollector create(DataCollector parent, String key) {
		return INSTANCE.getOrCreateDataCollector(key, parent);
	}

	private static DataCollector createSerial(DataCollector parent, String key) {
		return INSTANCE.getOrCreateSerialDataCollector(key, parent);
	}
}
