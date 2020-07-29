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
import org.eclipse.n4js.ide.xtext.server.LSPIssue;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.UriUtil;

import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.Multimap;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
public class XBuildRequest {

	private final String projectName;

	private URI baseDir;

	private Collection<URI> dirtyFiles = new ArrayList<>();

	private Collection<URI> deletedFiles = new ArrayList<>();

	private Collection<IResourceDescription.Delta> externalDeltas = new ArrayList<>();

	private ResourceDescriptionsData index;

	private XSource2GeneratedMapping fileMappings;

	private boolean doGenerate = true;

	private boolean doValidate = true;

	private boolean indexOnly = false;

	private boolean writeStorageResources = false;

	private XtextResourceSet resourceSet;

	private CancelIndicator cancelIndicator = CancelIndicator.NullImpl;

	/** Note that {@link Multimap} is not used here since we need to store empty lists, too. */
	private final Map<URI, Collection<LSPIssue>> resultIssues = new LinkedHashMap<>();

	private final Collection<URI> resultDeletedFiles = new ArrayList<>();

	private final Map<URI, URI> resultGeneratedFiles = new LinkedHashMap<>();

	/** Listener for validation events */
	public static interface AfterValidateListener {
		/** Called after a source file was validated with the given issues */
		void afterValidate(String projectName, URI source, Collection<? extends LSPIssue> issues);
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
		this.dirtyFiles = dirtyFiles;
	}

	/** Getter. */
	public Collection<URI> getDeletedFiles() {
		return this.deletedFiles;
	}

	/** Setter. */
	public void setDeletedFiles(Collection<URI> deletedFiles) {
		this.deletedFiles = deletedFiles;
	}

	/** Getter. */
	public Collection<IResourceDescription.Delta> getExternalDeltas() {
		return this.externalDeltas;
	}

	/** Setter. */
	public void setExternalDeltas(Collection<IResourceDescription.Delta> externalDeltas) {
		this.externalDeltas = externalDeltas;
	}

	/** Getter. */
	public Map<URI, Collection<LSPIssue>> getResultIssues() {
		return this.resultIssues;
	}

	/** Setter. */
	public void setResultIssues(String projectName, URI source, Collection<LSPIssue> issues) {
		this.resultIssues.put(source, issues);
		this.afterValidate(projectName, source, issues);
	}

	/** Setter. */
	public void setAfterValidateListener(AfterValidateListener afterValidateListener) {
		this.afterValidateListener = afterValidateListener;
	}

	/** Called each time a new set of issues was added for a validated source file */
	@SuppressWarnings("hiding")
	public void afterValidate(String projectName, URI source, Collection<LSPIssue> issues) {
		if (afterValidateListener != null) {
			afterValidateListener.afterValidate(projectName, source, issues);
		}
	}

	/** Getter. */
	public Map<URI, URI> getResultGeneratedFiles() {
		return this.resultGeneratedFiles;
	}

	/** Setter. */
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

	/** Getter. */
	public Collection<URI> getResultDeleteFiles() {
		return this.resultDeletedFiles;
	}

	/** Setter. */
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
	public XtextResourceSet getResourceSet() {
		return this.resourceSet;
	}

	/** Setter. */
	public void setResourceSet(XtextResourceSet resourceSet) {
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

	/** @return true iff the given source has issues of severity ERROR */
	public boolean containsValidationErrors(URI source) {
		Collection<? extends LSPIssue> issues = this.resultIssues.get(source);
		for (LSPIssue issue : issues) {
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
