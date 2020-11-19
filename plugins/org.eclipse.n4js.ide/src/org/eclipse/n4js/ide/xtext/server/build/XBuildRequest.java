/**
 * Copyright (c) 2015, 2016 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

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
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
public class XBuildRequest {

	private final String projectName;

	private URI baseDir;

	private ImmutableCollection<URI> dirtyFiles = ImmutableList.of();

	private ImmutableCollection<URI> deletedFiles = ImmutableList.of();

	private ImmutableCollection<IResourceDescription.Delta> externalDeltas = ImmutableList.of();

	private ResourceDescriptionsData index;

	private XSource2GeneratedMapping fileMappings;

	private boolean doGenerate = true;

	private boolean doValidate = true;

	private boolean indexOnly = false;

	private boolean writeStorageResources = false;

	private WorkspaceAwareResourceSet resourceSet;

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

	/** Listener for the entire build */
	public static interface AfterBuildListener {
		/** Called after the build was done, if (and only if) it completed normally */
		void afterBuild(XBuildRequest request, XBuildResult result);
	}

	private List<AfterValidateListener> afterValidateListeners;

	private List<AfterGenerateListener> afterGenerateListeners;

	private List<AfterDeleteListener> afterDeleteListeners;

	private List<AffectedListener> affectedListeners;

	private List<AfterBuildListener> afterBuildListeners;

	/** Create a new instance. Use {@link IBuildRequestFactory} instead! */
	public XBuildRequest(String projectName) {
		this.projectName = projectName;
	}

	/** Returns the project name. */
	public String getProjectName() {
		return projectName;
	}

	/** Setter for the base directory. */
	public void setBaseDir(URI baseDir) {
		this.baseDir = baseDir;
	}

	/** Return the base directory. */
	public URI getBaseDir() {
		if (this.baseDir == null) {
			String userDir = StandardSystemProperty.USER_DIR.value();
			this.baseDir = UriUtil.createFolderURI(new File(userDir));
		}
		return this.baseDir;
	}

	/** Getter. */
	public Collection<URI> getDirtyFiles() {
		return this.dirtyFiles;
	}

	/** Setter. */
	public void setDirtyFiles(Collection<URI> dirtyFiles) {
		this.dirtyFiles = ImmutableList.copyOf(dirtyFiles);
	}

	/** Getter. */
	public Collection<URI> getDeletedFiles() {
		return this.deletedFiles;
	}

	/** Setter. */
	public void setDeletedFiles(Collection<URI> deletedFiles) {
		this.deletedFiles = ImmutableList.copyOf(deletedFiles);
	}

	/** Getter. */
	public Collection<IResourceDescription.Delta> getExternalDeltas() {
		return this.externalDeltas;
	}

	/** Setter. */
	public void setExternalDeltas(Collection<IResourceDescription.Delta> externalDeltas) {
		this.externalDeltas = ImmutableList.copyOf(externalDeltas);
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
	 * Attach a build listener to the requested build.
	 *
	 * Attention: The most recently added build listener will be notified first. This allows to further modify the build
	 * result while it is being passed along the listener chain.
	 */
	public void addAfterBuildListener(AfterBuildListener listener) {
		if (afterBuildListeners == null) {
			afterBuildListeners = new ArrayList<>();
		}
		afterBuildListeners.add(0, Objects.requireNonNull(listener));
	}

	/** Called after the build was done */
	public void afterBuild(XBuildResult buildResult) {
		if (afterBuildListeners != null) {
			for (AfterBuildListener listener : afterBuildListeners) {
				listener.afterBuild(this, buildResult);
			}
		}
	}

	/** Getter. */
	public ResourceDescriptionsData getIndex() {
		return index;
	}

	/** Setter. */
	public void setIndex(ResourceDescriptionsData index) {
		this.index = index;
	}

	/** Getter. */
	public XSource2GeneratedMapping getFileMappings() {
		return fileMappings;
	}

	/** Setter. */
	public void setFileMappings(XSource2GeneratedMapping fileMappings) {
		this.fileMappings = fileMappings;
	}

	/** Combines {@link #isValidatorEnabled()} and {@link #isIndexOnly()}. */
	public boolean canValidate() {
		return isValidatorEnabled() && !isIndexOnly();
	}

	/** Getter. */
	public boolean isValidatorEnabled() {
		return this.doValidate;
	}

	/** Setter. */
	public void setValidatorEnabled(boolean doValidate) {
		this.doValidate = doValidate;
	}

	/** Combines {@link #isGeneratorEnabled()},{@link #isValidatorEnabled()} and {@link #isIndexOnly()}. */
	public boolean canGenerate() {
		return isGeneratorEnabled() && isValidatorEnabled() && !isIndexOnly();
	}

	/** Getter. */
	public boolean isGeneratorEnabled() {
		return this.doGenerate;
	}

	/** Setter. */
	public void setGeneratorEnabled(boolean doGenerate) {
		this.doGenerate = doGenerate;
	}

	/** Getter. */
	public boolean isWriteStorageResources() {
		return this.writeStorageResources;
	}

	/** Setter. */
	public void setWriteStorageResources(boolean writeStorageResources) {
		this.writeStorageResources = writeStorageResources;
	}

	/** Getter. */
	public boolean isIndexOnly() {
		return this.indexOnly;
	}

	/** Setter. */
	public void setIndexOnly(boolean indexOnly) {
		this.indexOnly = indexOnly;
	}

	/** Getter. */
	public WorkspaceAwareResourceSet getResourceSet() {
		return this.resourceSet;
	}

	/** Setter. */
	public void setResourceSet(WorkspaceAwareResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	/** Getter. */
	public CancelIndicator getCancelIndicator() {
		return this.cancelIndicator;
	}

	/** Setter. */
	public void setCancelIndicator(CancelIndicator cancelIndicator) {
		this.cancelIndicator = cancelIndicator;
	}

}
