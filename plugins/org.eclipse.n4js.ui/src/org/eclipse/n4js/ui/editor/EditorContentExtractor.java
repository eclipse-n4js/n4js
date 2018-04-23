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
package org.eclipse.n4js.ui.editor;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Lists.newLinkedList;
import static java.lang.System.arraycopy;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.ui.PlatformUI.getWorkbench;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextPresentationReconciler;
import org.eclipse.xtext.ui.editor.model.ITokenTypeToPartitionTypeMapper;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.util.ITextRegion;

import com.google.common.base.Optional;
import com.google.common.collect.Range;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Class for extracting AST semantic elements from a {@link XtextEditor Xtext based editor} as its text representation
 * without opening the editor itself.
 */
public class EditorContentExtractor {

	private static final Logger LOGGER = Logger.getLogger(EditorContentExtractor.class);
	private static final String TEXT_FONT_ID = "org.eclipse.jface.textfont";

	@Inject
	private IN4JSCore core;

	@Inject
	private XtextDocumentProvider docProvider;

	@Inject
	private XtextPresentationReconciler reconciler;

	@Inject
	private ITokenTypeToPartitionTypeMapper partitionTypeMapper;

	@Inject
	private Provider<IPresentationDamager> damagerProvider;

	@Inject
	private Provider<IPresentationRepairer> repairerProvider;

	@Inject
	private ILocationInFileProvider locationInFileProvider;

	/**
	 * Optionally returns with the semantic AST node element (given as the element URI) as a {@link StyledTextDescriptor
	 * styled text descriptor}. If the element cannot be resolved or the styled text cannot be computed this method
	 * returns with and {@link Optional#absent() absent} instance but never {@code null}.
	 *
	 * @param uri
	 *            the URI of the semantic element in the AST.
	 * @return a styled text descriptor representing the extracted code for the semantic AST node given with its unique
	 *         URI.
	 */
	public Optional<StyledTextDescriptor> getDescriptorForSemanticElement(final URI uri) {
		final Optional<TModule> optModule = loadTModuleFromURI(uri);
		if (!optModule.isPresent()) {
			return absent();
		}
		TModule module = optModule.get();

		final URI moduleUri = module.eResource().getURI();
		final IFile file = getWorkspace().getRoot().getFile(new Path(moduleUri.toPlatformString(true)));
		if (null == file || !file.exists()) {
			return absent();
		}

		final FileEditorInput editorInput = new FileEditorInput(file);
		try {
			docProvider.connect(editorInput);
		} catch (final CoreException e) {
			LOGGER.error("Error while connecting editor input with document provider: " + e);
			return absent();
		}

		final IDocument doc = docProvider.getDocument(editorInput);
		if (null == doc) {
			return absent();
		}

		final XtextResource xtextResource = (XtextResource) module.eResource();
		final ResourceSet resourceSet = xtextResource.getResourceSet();
		final EObject object = resourceSet.getEObject(uri, true);
		if (null == object) {
			return absent();
		}

		final ITextRegion textRegion = locationInFileProvider.getFullTextRegion(object);
		if (null == textRegion) {
			return absent();
		}

		try {

			final int lineOfOffset = doc.getLineOfOffset(textRegion.getOffset());
			final int lineOffset = doc.getLineOffset(lineOfOffset);
			final int offset = lineOffset;
			final int length = textRegion.getLength() + (textRegion.getOffset() - lineOffset);
			final String text = doc.get(offset, length);

			final IPresentationRepairer repairer = repairerProvider.get();
			final IPresentationDamager damager = damagerProvider.get();
			for (final String contentType : partitionTypeMapper.getSupportedPartitionTypes()) {
				reconciler.setRepairer(repairer, contentType);
				repairer.setDocument(doc);
				reconciler.setDamager(damager, contentType);
				damager.setDocument(doc);
			}

			final Region region = new Region(offset, length);
			final TextPresentation textPresentation = reconciler.createRepairDescription(region, doc);

			final Iterator<?> rangeItr = textPresentation.getAllStyleRangeIterator();
			final Collection<StyleRange> ranges = newLinkedList();
			while (rangeItr.hasNext()) {
				final Object next = rangeItr.next();
				if (next instanceof StyleRange) {
					ranges.add((StyleRange) next);
				}
			}

			final Range<Integer> textRange = Range.closed(offset, offset + length);
			for (final Iterator<StyleRange> itr = ranges.iterator(); itr.hasNext(); /* nothing */) {
				final StyleRange range = itr.next();
				if (!textRange.contains(range.start) || !textRange.contains(range.start + range.length)) {
					itr.remove();
				} else {
					range.start = range.start - offset;
				}
			}

			return fromNullable(new StyledTextDescriptorImpl(text, ranges));

		} catch (final BadLocationException e) {
			LOGGER.error("Error while trying to extract text from document.", e);
			return absent();
		}

	}

	private Optional<TModule> loadTModuleFromURI(final URI uri) {
		if (null == uri) {
			return absent();
		}

		final URI trimmedUri = uri.hasFragment() ? uri.trimFragment() : uri;
		final IN4JSProject project = core.findProject(trimmedUri).orNull();
		if (project == null) {
			return absent();
		}
		final ResourceSet resSet = core.createResourceSet(Optional.of(project));
		final IResourceDescriptions index = core.getXtextIndex(resSet);
		final IResourceDescription resDesc = index.getResourceDescription(trimmedUri);

		if (null == resDesc) {
			return absent();
		}

		final TModule module = core.loadModuleFromIndex(resSet, resDesc, false);
		if (null == module || null == module.eResource() || null == module.eResource().getResourceSet()) {
			return absent();
		}
		return Optional.of(module);
	}

	/**
	 * Implementation of a {@link StyledTextDescriptor styled text descriptor}.
	 */
	private static final class StyledTextDescriptorImpl implements StyledTextDescriptor {

		private final String text;
		private final StyleRange[] ranges;
		private final Font font;

		private StyledTextDescriptorImpl(final String text, final Iterable<StyleRange> ranges) {
			this.text = text;
			this.ranges = toArray(ranges, StyleRange.class);
			this.font = getTextFont();
		}

		@Override
		public String getText() {
			return text;
		}

		@Override
		public StyleRange[] getRanges() {
			if (null == ranges) {
				return null;
			}
			final StyleRange[] copy = new StyleRange[ranges.length];
			arraycopy(ranges, 0, copy, 0, ranges.length);
			return copy;
		}

		@Override
		public Font getFont() {
			return font;
		}

		private Font getTextFont() {
			final ITheme theme = getWorkbench().getThemeManager().getCurrentTheme();
			return theme.getFontRegistry().get(TEXT_FONT_ID);
		}

	}

}
