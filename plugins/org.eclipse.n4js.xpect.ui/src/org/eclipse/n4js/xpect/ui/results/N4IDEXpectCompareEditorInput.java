/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.ui.results;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.ResourceNode;
import org.eclipse.compare.internal.ICompareUIConstants;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.util.Exceptions;
import org.junit.ComparisonFailure;

import com.google.common.base.Preconditions;

/**
 * Configures input for compare editor used to display diff view of expected/actual result in test execution. Based on
 * Xpect ui compare editor.
 */
@SuppressWarnings("restriction")
public class N4IDEXpectCompareEditorInput extends CompareEditorInput {

	@SuppressWarnings("javadoc")
	protected static class CompareItem implements IStreamContentAccessor, ITypedElement, IModificationDate {
		private final String contents, name;

		CompareItem(String name, String contents) {
			this.name = name;
			this.contents = contents;
		}

		@Override
		public InputStream getContents() throws CoreException {
			return new ByteArrayInputStream(contents.getBytes());
		}

		@Override
		public Image getImage() {
			return null;
		}

		@Override
		public long getModificationDate() {
			return 0;
		}

		@Override
		public String getName() {
			return name;
		}

		public String getString() {
			return contents;
		}

		@Override
		public String getType() {
			return ITypedElement.TEXT_TYPE;
		}
	}

	@SuppressWarnings("javadoc")
	protected static class EditableCompareItem extends CompareItem implements IEditableContent {
		private final IFile file;

		EditableCompareItem(String name, String contents, IFile file) {
			super(name, contents);
			this.file = file;
		}

		@Override
		public boolean isEditable() {
			return file != null;
		}

		@Override
		public ITypedElement replace(ITypedElement dest, ITypedElement src) {
			return null;
		}

		@Override
		public void setContent(byte[] newContent) {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(newContent);
			try {
				file.setContents(inputStream, true, true, new NullProgressMonitor());
			} catch (CoreException e) {
				LOG.error(e);
			}
		}

	}

	private final static Logger LOG = Logger.getLogger(N4IDEXpectCompareEditorInput.class);

	@SuppressWarnings("javadoc")
	protected static CompareConfiguration createConfiguration(IFile file) {
		CompareConfiguration configuration = new CompareConfiguration();
		configuration.setLeftEditable(true);
		configuration.setLeftLabel("Expected Test Result" + (file != null ? " - " + file.getName() : ""));
		configuration.setRightLabel("Actual Test Result");
		configuration.setAncestorLabel("File on Disk");
		configuration.setProperty(ICompareUIConstants.PROP_ANCESTOR_VISIBLE, Boolean.FALSE);
		return configuration;
	}

	private final IFile file;
	private final ComparisonFailure comparisonFailure;

	/**
	 */
	public N4IDEXpectCompareEditorInput(IFile file,
			ComparisonFailure cf) {
		super(createConfiguration(file));
		Preconditions.checkNotNull(file);
		this.file = file;
		this.comparisonFailure = cf;
	}

	@Override
	public String getTitle() {
		return file.getName();
	}

	@Override
	protected Object prepareInput(IProgressMonitor pm) {
		try {
			ResourceNode ancestor = new ResourceNode(file);
			String ancestorContent = getContent(ancestor);
			String leftContent, rightContent;

			leftContent = this.comparisonFailure.getExpected();
			rightContent = this.comparisonFailure.getActual();
			if (!leftContent.equals(ancestorContent))
				getCompareConfiguration().setProperty(ICompareUIConstants.PROP_ANCESTOR_VISIBLE, Boolean.TRUE);
			CompareItem left = new EditableCompareItem("Left", leftContent, file);
			CompareItem right = new CompareItem("Right", rightContent);
			return new DiffNode(null, Differencer.CHANGE | Differencer.DIRECTION_MASK, ancestor, left, right);
		} catch (Throwable t) {
			LOG.error(t.getMessage(), t);
			Exceptions.throwUncheckedException(t);
			return null;
		}
	}

	private String getContent(ResourceNode ancestor) {
		try {
			return new String(ancestor.getContent(), file.getCharset());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

}
