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
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.UriUtil;
import org.eclipse.xtext.validation.Issue;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
public class XBuildRequest {

	private URI baseDir;

	private Collection<URI> dirtyFiles = new ArrayList<>();

	private Collection<URI> deletedFiles = new ArrayList<>();

	private Collection<IResourceDescription.Delta> externalDeltas = new ArrayList<>();

	private XIndexState state = new XIndexState();

	private boolean writeStorageResources = false;

	private boolean indexOnly = false;

	private XtextResourceSet resourceSet;

	private CancelIndicator cancelIndicator = CancelIndicator.NullImpl;

	private final Map<URI, Collection<Issue>> resultIssues = new LinkedHashMap<>();

	private final Collection<URI> resultDeletedFiles = new ArrayList<>();

	private final Map<URI, URI> resultGeneratedFiles = new LinkedHashMap<>();

	/** Listener for validation events */
	public static interface AfterValidateListener {
		/** Called after a source file was validated with the given issues */
		void afterValidate(URI source, Collection<Issue> issues);
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

	private AfterValidateListener afterValidateListener;

	private AfterGenerateListener afterGenerateListener;

	private AfterDeleteListener afterDeleteListener;

	/**
	 * Setter for the base directory.
	 */
	public void setBaseDir(URI baseDir) {
		this.baseDir = baseDir;
	}

	/**
	 * Return the base dir.
	 */
	public URI getBaseDir() {
		if ((this.baseDir == null)) {
			String userDir = System.getProperty("user.dir");
			this.baseDir = UriUtil.createFolderURI(new File(userDir));
		}
		return this.baseDir;
	}

	/**
	 * Getter.
	 */
	public Collection<URI> getDirtyFiles() {
		return this.dirtyFiles;
	}

	/**
	 * Setter.
	 */
	public void setDirtyFiles(Collection<URI> dirtyFiles) {
		this.dirtyFiles = dirtyFiles;
	}

	/**
	 * Getter.
	 */
	public Collection<URI> getDeletedFiles() {
		return this.deletedFiles;
	}

	/**
	 * Setter.
	 */
	public void setDeletedFiles(Collection<URI> deletedFiles) {
		this.deletedFiles = deletedFiles;
	}

	/**
	 * Getter.
	 */
	public Collection<IResourceDescription.Delta> getExternalDeltas() {
		return this.externalDeltas;
	}

	/**
	 * Setter.
	 */
	public void setExternalDeltas(Collection<IResourceDescription.Delta> externalDeltas) {
		this.externalDeltas = externalDeltas;
	}

	/**
	 * Getter.
	 */
	public Map<URI, Collection<Issue>> getResultIssues() {
		return this.resultIssues;
	}

	/**
	 * Setter.
	 */
	public void setResultIssues(URI source, Collection<Issue> issues) {
		this.resultIssues.put(source, issues);
		this.afterValidate(source, issues);
	}

	/** Setter. */
	public void setAfterValidateListener(AfterValidateListener afterValidateListener) {
		this.afterValidateListener = afterValidateListener;
	}

	/** Called each time a new set of issues was added for a validated source file */
	public void afterValidate(URI source, Collection<Issue> issues) {
		if (afterValidateListener != null) {
			afterValidateListener.afterValidate(source, issues);
		}
	}

	/**
	 * Getter.
	 */
	public Map<URI, URI> getResultGeneratedFiles() {
		return this.resultGeneratedFiles;
	}

	/**
	 * Setter.
	 */
	public void setResultGeneratedFile(URI source, URI generated) {
		this.resultGeneratedFiles.put(source, generated);
		afterGenerate(source, generated);
	}

	/** Setter. */
	public void setAfterGenerateListener(AfterGenerateListener afterGenerateListener) {
		this.afterGenerateListener = afterGenerateListener;
	}

	/** Called each time a new set of issues was added for a validated source file */
	public void afterGenerate(URI source, URI generated) {
		if (afterGenerateListener != null) {
			afterGenerateListener.afterGenerate(source, generated);
		}
	}

	/**
	 * Getter.
	 */
	public Collection<URI> getResultDeleteFiles() {
		return this.resultDeletedFiles;
	}

	/**
	 * Setter.
	 */
	public void setResultDeleteFile(URI file) {
		this.resultDeletedFiles.add(file);
		afterDelete(file);
	}

	/** Setter. */
	public void setAfterDeleteListener(AfterDeleteListener afterDeleteListener) {
		this.afterDeleteListener = afterDeleteListener;
	}

	/** Called each time a file was deleted */
	public void afterDelete(URI file) {
		if (afterDeleteListener != null) {
			afterDeleteListener.afterDelete(file);
		}
	}

	/** @return true iff the builder should perform generation on the given source file */
	public boolean shouldGenerate(URI source) {
		return !containsErrors(source);
	}

	/**
	 * Getter.
	 */
	public XIndexState getState() {
		return this.state;
	}

	/**
	 * Setter.
	 */
	public void setState(XIndexState state) {
		this.state = state;
	}

	/**
	 * Getter.
	 */
	public boolean isWriteStorageResources() {
		return this.writeStorageResources;
	}

	/**
	 * Setter.
	 */
	public void setWriteStorageResources(boolean writeStorageResources) {
		this.writeStorageResources = writeStorageResources;
	}

	/**
	 * Getter.
	 */
	public boolean isIndexOnly() {
		return this.indexOnly;
	}

	/**
	 * Setter.
	 */
	public void setIndexOnly(boolean indexOnly) {
		this.indexOnly = indexOnly;
	}

	/**
	 * Getter.
	 */
	public XtextResourceSet getResourceSet() {
		return this.resourceSet;
	}

	/**
	 * Setter.
	 */
	public void setResourceSet(XtextResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	/**
	 * Getter.
	 */
	public CancelIndicator getCancelIndicator() {
		return this.cancelIndicator;
	}

	/**
	 * Setter.
	 */
	public void setCancelIndicator(CancelIndicator cancelIndicator) {
		this.cancelIndicator = cancelIndicator;
	}

	/** @return true iff the given source has issues of severity ERROR */
	protected boolean containsErrors(URI source) {
		Collection<Issue> issues = this.resultIssues.get(source);
		for (Issue issue : issues) {
			Severity severity = issue.getSeverity();
			if (severity != null) {
				switch (severity) {
				case ERROR:
					return true;
				case WARNING:
					break;
				case INFO:
					break;
				case IGNORE:
					break;
				}
			}
		}
		return false;
	}
}
