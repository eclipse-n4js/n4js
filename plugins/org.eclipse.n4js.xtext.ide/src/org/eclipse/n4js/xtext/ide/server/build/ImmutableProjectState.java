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
package org.eclipse.n4js.xtext.ide.server.build;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

/**
 * Represents a consistent snapshot of the project state after a build was run.
 */
@SuppressWarnings("deprecation")
public class ImmutableProjectState {
	/** Type index */
	private final ResourceDescriptionsData indexData;
	/** File Mappings */
	private final XSource2GeneratedMapping fileMappings;
	/** Hashes to indicate file changes */
	private final ImmutableMap<URI, HashedFileContent> fileHashes;
	/** Validation issues related to a URI */
	private final ImmutableListMultimap<URI, LSPIssue> validationIssues;
	/** Dependencies and whether they existed at the time this project state was computed. */
	private final ImmutableMap<String, Boolean> dependencies;

	/**
	 * Factory method to make it explicit that we create copies of the passed arguments.
	 */
	public static ImmutableProjectState copyFrom(
			ResourceDescriptionsData indexData,
			XSource2GeneratedMapping fileMappings,
			Map<? extends URI, ? extends HashedFileContent> fileHashes,
			ListMultimap<? extends URI, ? extends LSPIssue> validationIssues,
			Map<String, Boolean> dependencies) {
		return new ImmutableProjectState(indexData.copy(), fileMappings.copy(), ImmutableMap.copyOf(fileHashes),
				ImmutableListMultimap.copyOf(validationIssues), ImmutableMap.copyOf(dependencies));
	}

	/**
	 * Returns an empty project state. No index data, no issues, no file mappings, nothing.
	 */
	public static ImmutableProjectState empty() {
		return new ImmutableProjectState(new ResourceDescriptionsData(Collections.emptyList()),
				new XSource2GeneratedMapping(), ImmutableMap.of(), ImmutableListMultimap.of(),
				ImmutableMap.of());
	}

	/**
	 * Use with caution.
	 */
	static ImmutableProjectState withoutCopy(
			ResourceDescriptionsData indexData,
			XSource2GeneratedMapping fileMappings,
			ImmutableMap<URI, HashedFileContent> fileHashes,
			ImmutableListMultimap<URI, LSPIssue> validationIssues,
			ImmutableMap<String, Boolean> dependencies) {
		return new ImmutableProjectState(indexData, fileMappings, fileHashes, validationIssues, dependencies);
	}

	/** Non-copying constructor */
	private ImmutableProjectState(
			ResourceDescriptionsData indexData,
			XSource2GeneratedMapping fileMappings,
			ImmutableMap<URI, HashedFileContent> fileHashes,
			ImmutableListMultimap<URI, LSPIssue> validationIssues,
			ImmutableMap<String, Boolean> dependencies) {

		this.indexData = Objects.requireNonNull(indexData);
		this.fileMappings = Objects.requireNonNull(fileMappings);
		this.fileHashes = Objects.requireNonNull(fileHashes);
		this.validationIssues = Objects.requireNonNull(validationIssues);
		this.dependencies = Objects.requireNonNull(dependencies);
	}

	/**
	 * Return the index state of the snapshot.
	 */
	public IResourceDescriptions getResourceDescriptions() {
		return indexData;
	}

	ResourceDescriptionsData internalGetResourceDescriptions() {
		return indexData;
	}

	/**
	 * Return the file mapping information of the snapshot.
	 */
	// TODO introduce immutable variant of XSource2GeneratedMapping
	public XSource2GeneratedMapping getFileMappings() {
		return fileMappings;
	}

	/**
	 * Return the file hashes of the snapshot.
	 */
	public ImmutableMap<URI, HashedFileContent> getFileHashes() {
		return fileHashes;
	}

	/**
	 * Return the validation issues of the snapshot.
	 */
	public ImmutableListMultimap<URI, LSPIssue> getValidationIssues() {
		return validationIssues;
	}

	/**
	 * Return the project dependencies of the snapshot, as a map from project name to a boolean telling whether that
	 * project existed at the time this project state was computed.
	 */
	public ImmutableMap<String, Boolean> getDependencies() {
		return dependencies;
	}
}
