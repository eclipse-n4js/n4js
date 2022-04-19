/**
 * Copyright (c) 2015, 2016 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.xtext.ide.server.build;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.UriUtil;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.ImmutableList;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
public class XBuildRequest {

	private final String projectName;

	private final URI baseDir;

	private final ImmutableList<URI> dirtyFiles;

	private final ImmutableList<URI> deletedFiles;

	private final ImmutableList<IResourceDescription.Delta> externalDeltas;

	private final ResourceDescriptionsData index;

	private final WorkspaceAwareResourceSet resourceSet;

	private final XSource2GeneratedMapping fileMappings;

	private final boolean doGenerate;

	private final boolean doValidate;

	private final boolean indexOnly;

	private final boolean writeStorageResources;

	private CancelIndicator cancelIndicator = CancelIndicator.NullImpl;

	/** Listener for affected resources */
	public static interface AffectedListener {
		/** Called if a resource is considered to be affected and will be built */
		void afterDetectedAsAffected(URI source);
	}

	/** Listener for validation events */
	public static interface AfterValidateListener {
		/** Called after a source file was validated with the given issues */
		void afterValidate(URI source, List<? extends Issue> issues);
	}

	/** Listener for generation events */
	public static interface AfterGenerateListener {
		/** Called after a generated file was created based on a source file */
		void afterGenerate(URI source, URI generated);
	}

	/** Listener for deletion events */
	public static interface AfterDeleteListener {
		/** Called after a file was deleted */
		void afterDelete(URI file);
	}

	/** Listener for the build done of a single file events */
	public static interface AfterBuildFileListener {
		/** Called after a file was built */
		void afterBuildFile(URI file);
	}

	/** Listener for the entire build */
	public static interface AfterBuildRequestListener {
		/** Called after the build request was done, if (and only if) it completed normally */
		void afterBuildRequest(XBuildRequest request, XBuildResult result);
	}

	private List<AfterValidateListener> afterValidateListeners;

	private List<AfterGenerateListener> afterGenerateListeners;

	private List<AfterDeleteListener> afterDeleteListeners;

	private List<AffectedListener> affectedListeners;

	private List<AfterBuildFileListener> afterBuildFileListeners;

	private List<AfterBuildRequestListener> afterBuildRequestListeners;

	/** Create a new instance. Use {@link IBuildRequestFactory} instead! */
	public XBuildRequest(String projectID, URI baseDir, Collection<URI> dirtyFiles, Collection<URI> deletedFiles,
			Collection<IResourceDescription.Delta> externalDeltas, ResourceDescriptionsData index,
			WorkspaceAwareResourceSet resourceSet, XSource2GeneratedMapping fileMappings,
			boolean doGenerate, boolean doValidate, boolean indexOnly, boolean writeStorageResources) {

		this.projectName = projectID;
		this.baseDir = baseDir == null
				? UriUtil.createFolderURI(new File(StandardSystemProperty.USER_DIR.value()))
				: baseDir;
		this.dirtyFiles = dirtyFiles == null ? ImmutableList.of() : ImmutableList.copyOf(dirtyFiles);
		this.deletedFiles = deletedFiles == null ? ImmutableList.of() : ImmutableList.copyOf(deletedFiles);
		this.externalDeltas = externalDeltas == null ? ImmutableList.of() : ImmutableList.copyOf(externalDeltas);
		this.index = index;
		this.resourceSet = resourceSet;
		this.fileMappings = fileMappings;
		this.doGenerate = doGenerate;
		this.doValidate = doValidate;
		this.indexOnly = indexOnly;
		this.writeStorageResources = writeStorageResources;
	}

	/** Returns the project name. */
	public String getProjectName() {
		return projectName;
	}

	/** Return the base directory. */
	public URI getBaseDir() {
		return this.baseDir;
	}

	/** Getter. */
	public List<URI> getDirtyFiles() {
		return this.dirtyFiles;
	}

	/** Getter. */
	public List<URI> getDeletedFiles() {
		return this.deletedFiles;
	}

	/** Getter. */
	public List<IResourceDescription.Delta> getExternalDeltas() {
		return this.externalDeltas;
	}

	/** Getter. */
	public ResourceDescriptionsData getIndex() {
		return index;
	}

	/** Getter. */
	public XSource2GeneratedMapping getFileMappings() {
		return fileMappings;
	}

	/** Combines {@link #isValidatorEnabled()} and {@link #isIndexOnly()}. */
	public boolean canValidate() {
		return isValidatorEnabled() && !isIndexOnly();
	}

	/** Getter. */
	public boolean isValidatorEnabled() {
		return this.doValidate;
	}

	/** Combines {@link #isGeneratorEnabled()},{@link #isValidatorEnabled()} and {@link #isIndexOnly()}. */
	public boolean canGenerate() {
		return isGeneratorEnabled() && isValidatorEnabled() && !isIndexOnly();
	}

	/** Getter. */
	public boolean isGeneratorEnabled() {
		return this.doGenerate;
	}

	/** Getter. */
	public boolean isWriteStorageResources() {
		return this.writeStorageResources;
	}

	/** Getter. */
	public boolean isIndexOnly() {
		return this.indexOnly;
	}

	/** Getter. */
	public WorkspaceAwareResourceSet getResourceSet() {
		return this.resourceSet;
	}

	/** Getter. */
	public CancelIndicator getCancelIndicator() {
		return this.cancelIndicator;
	}

	/** Setter. */
	public void setCancelIndicator(CancelIndicator cancelIndicator) {
		this.cancelIndicator = cancelIndicator;
	}

	/**
	 * Attach a validation listener to the requested build.
	 */
	public void addAfterValidateListener(AfterValidateListener listener) {
		if (afterValidateListeners == null) {
			afterValidateListeners = new ArrayList<>();
		}
		afterValidateListeners.add(Objects.requireNonNull(listener));
	}

	/** Called each time a new set of issues was added for a validated source file */
	public void afterValidate(URI source, List<? extends Issue> issues) {
		if (afterValidateListeners != null) {
			for (AfterValidateListener listener : afterValidateListeners) {
				listener.afterValidate(source, issues);
			}
		}
	}

	/**
	 * Attach a generate listener to the requested build.
	 */
	public void addAfterGenerateListener(AfterGenerateListener listener) {
		if (afterGenerateListeners == null) {
			afterGenerateListeners = new ArrayList<>();
		}
		afterGenerateListeners.add(Objects.requireNonNull(listener));
	}

	/** Called each time a new set of issues was added for a validated source file */
	public void afterGenerate(URI source, URI generated) {
		if (afterGenerateListeners != null) {
			for (AfterGenerateListener listener : afterGenerateListeners) {
				listener.afterGenerate(source, generated);
			}
		}
	}

	/**
	 * Attach a delete listener to the requested build.
	 */
	public void addAfterDeleteListener(AfterDeleteListener listener) {
		if (afterDeleteListeners == null) {
			afterDeleteListeners = new ArrayList<>();
		}
		afterDeleteListeners.add(Objects.requireNonNull(listener));
	}

	/** Called each time a file was deleted */
	public void afterDelete(URI file) {
		if (afterDeleteListeners != null) {
			for (AfterDeleteListener listener : afterDeleteListeners) {
				listener.afterDelete(file);
			}
		}
	}

	/**
	 * Attach an affected listener to the requested build.
	 */
	public void addAffectedListener(AffectedListener listener) {
		if (affectedListeners == null) {
			affectedListeners = new ArrayList<>();
		}
		affectedListeners.add(Objects.requireNonNull(listener));
	}

	/**
	 * Notify the request that we are going to work with the given URI.
	 */
	public void afterDetectedAsAffected(URI uri) {
		if (affectedListeners != null) {
			for (AffectedListener listener : affectedListeners) {
				listener.afterDetectedAsAffected(uri);
			}
		}
	}

	/**
	 * Attach an after build file listener to the requested build.
	 */
	public void addAfterBuildFileListener(AfterBuildFileListener listener) {
		if (afterBuildFileListeners == null) {
			afterBuildFileListeners = new ArrayList<>();
		}
		afterBuildFileListeners.add(Objects.requireNonNull(listener));
	}

	/**
	 * Notify the request that a file was built with the given URI.
	 */
	public void afterBuildFile(URI uri) {
		if (afterBuildFileListeners != null) {
			for (AfterBuildFileListener listener : afterBuildFileListeners) {
				listener.afterBuildFile(uri);
			}
		}
	}

	/**
	 * Attach a build listener to the requested build.
	 *
	 * Attention: The most recently added build listener will be notified first. This allows to further modify the build
	 * result while it is being passed along the listener chain.
	 */
	public void addAfterBuildRequestListener(AfterBuildRequestListener listener) {
		if (afterBuildRequestListeners == null) {
			afterBuildRequestListeners = new ArrayList<>();
		}
		afterBuildRequestListeners.add(0, Objects.requireNonNull(listener));
	}

	/** Called after the build was done */
	public void afterBuildRequest(XBuildResult buildResult) {
		if (afterBuildRequestListeners != null) {
			for (AfterBuildRequestListener listener : afterBuildRequestListeners) {
				listener.afterBuildRequest(this, buildResult);
			}
		}
	}
}
