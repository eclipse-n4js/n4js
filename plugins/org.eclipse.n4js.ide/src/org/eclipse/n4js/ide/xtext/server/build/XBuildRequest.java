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

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.UriUtil;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
public class XBuildRequest {
	/**
	 * Callback after a resource was validated.
	 */
	public interface XIPostValidationCallback {
		/**
		 * @return whether the build can proceed, <code>false</code> if the build should be interrupted
		 */
		boolean afterValidate(URI validated, Iterable<Issue> issues);
	}

	private static class XDefaultValidationCallback implements XBuildRequest.XIPostValidationCallback {
		private static final Logger LOG = Logger.getLogger(XDefaultValidationCallback.class);

		@Override
		public boolean afterValidate(URI validated, Iterable<Issue> issues) {
			for (Issue issue : issues) {
				Severity severity = issue.getSeverity();
				if (severity != null) {
					switch (severity) {
					case ERROR:
						XBuildRequest.XDefaultValidationCallback.LOG.error(issue.toString());
						return false;
					case WARNING:
						XBuildRequest.XDefaultValidationCallback.LOG.warn(issue.toString());
						break;
					case INFO:
						XBuildRequest.XDefaultValidationCallback.LOG.info(issue.toString());
						break;
					case IGNORE:
						XBuildRequest.XDefaultValidationCallback.LOG.debug(issue.toString());
						break;
					}
				}
			}
			return true;
		}
	}

	private URI baseDir;

	private Collection<URI> dirtyFiles = new ArrayList<>();

	private Collection<URI> deletedFiles = new ArrayList<>();

	private Collection<IResourceDescription.Delta> externalDeltas = new ArrayList<>();

	/**
	 * Callback after validation, return <code>false</code> will stop the build.
	 */
	private XBuildRequest.XIPostValidationCallback afterValidate = new XBuildRequest.XDefaultValidationCallback();

	private Procedure2<? super URI, ? super URI> afterGenerateFile = (source, generated) -> {
		/* nothing to do */
	};

	private Procedure1<? super URI> afterDeleteFile = (file) -> {
		/* nothing to do */
	};

	private XIndexState state = new XIndexState();

	private boolean writeStorageResources = false;

	private boolean indexOnly = false;

	private XtextResourceSet resourceSet;

	private CancelIndicator cancelIndicator = CancelIndicator.NullImpl;

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
	public XBuildRequest.XIPostValidationCallback getAfterValidate() {
		return this.afterValidate;
	}

	/**
	 * Setter.
	 */
	public void setAfterValidate(XBuildRequest.XIPostValidationCallback afterValidate) {
		this.afterValidate = afterValidate;
	}

	/**
	 * Getter.
	 */
	public Procedure2<? super URI, ? super URI> getAfterGenerateFile() {
		return this.afterGenerateFile;
	}

	/**
	 * Setter.
	 */
	public void setAfterGenerateFile(Procedure2<? super URI, ? super URI> afterGenerateFile) {
		this.afterGenerateFile = afterGenerateFile;
	}

	/**
	 * Getter.
	 */
	public Procedure1<? super URI> getAfterDeleteFile() {
		return this.afterDeleteFile;
	}

	/**
	 * Setter.
	 */
	public void setAfterDeleteFile(Procedure1<? super URI> afterDeleteFile) {
		this.afterDeleteFile = afterDeleteFile;
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
}
