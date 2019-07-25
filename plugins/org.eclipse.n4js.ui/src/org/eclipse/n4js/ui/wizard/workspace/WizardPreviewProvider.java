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
package org.eclipse.n4js.ui.wizard.workspace;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.ui.wizard.generator.ContentBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.PreferenceStoreAccessor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * A preview window for wizards which shows a preview of the created class.
 */
@SuppressWarnings("restriction")
public class WizardPreviewProvider {

	private static final DefaultHighlightingConfiguration DEFAULT_HIGHLIGHTING_CONFIGURATION = new DefaultHighlightingConfiguration();
	private static Logger LOGGER = Logger.getLogger(WizardPreviewProvider.class);

	@Inject
	private EmbeddedEditorFactory editorFactory;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private PreferenceStoreAccessor preferenceStoreAccessor;

	/**
	 * Opens a new wizard with the give shell as parent.
	 *
	 * A wizard preview can be of style {@code SWT.LEFT} or {@code SWT.LEFT}
	 *
	 * @param parent
	 *            The parent shell
	 * @param style
	 *            The preview style
	 * @return The preview control
	 */
	public WizardPreview create(Composite parent, int style) {
		return new WizardPreview(parent, style);
	}

	/**
	 * A preview window which attaches to the side of a given shell.
	 */
	public class WizardPreview extends Composite {

		private Document editorDocument;
		private SourceViewer sourceViewer;

		// Info label
		private Label infoLabel;

		// Content blocks
		private ContentBlock[] contentBlocks;

		private final Color inactiveColor;

		private final Font editorFont = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
				.getFontRegistry().get("org.eclipse.jface.textfont");

		/**
		 * Creates a new wizard preview for a given shell.
		 *
		 */
		public WizardPreview(Composite parent, int style) {
			super(parent, style);
			this.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).extendedMargins(0, 0, 0, 0).create());

			createEditor();
			createInfoBar();

			inactiveColor = createInactiveColor();

			// Apply the editor theme by assigning a CSS class
			this.sourceViewer.getTextWidget().setData("org.eclipse.e4.ui.css.CssClassName", "MPart active");
		}

		@Override
		public void dispose() {
			inactiveColor.dispose();
			super.dispose();
		}

		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			if (!enabled) {
				unhighlightAll();
			}
		}

		/**
		 * Sets the content blocks of this preview.
		 */
		public void setContent(ContentBlock[] blocks) {
			// Return if the control hasn't been created yet.
			if (null == editorDocument) {
				return;
			}

			// Only update changed content blocks
			// to avoid flickering in the source viewer
			List<ContentBlock> blockList = new ArrayList<>(Arrays.asList(blocks));

			// The offset from which the document content needs to be replaced.
			int replaceStart = 0;

			if (null != this.contentBlocks) {
				int index = 0;
				for (Iterator<ContentBlock> itr = blockList.iterator(); itr.hasNext(); /**/) {
					// Assure that there is an existing content block with the same index
					if (this.contentBlocks.length > index) {
						ContentBlock currentBlock = itr.next();
						// If they're unequal break the loop
						if (!this.contentBlocks[index].content.equals(currentBlock.content)) {
							break;
						}
						// Otherwise keep the current block and only update the document
						// from the offset of the next block.
						replaceStart += currentBlock.content.length();
						index++;
						itr.remove();
					}
				}
			}
			this.contentBlocks = blocks;

			StringBuilder joinedContent = new StringBuilder();
			blockList.stream().forEach((block) -> joinedContent.append(block.content));

			try {
				editorDocument.replace(replaceStart, getContent().length() - replaceStart, joinedContent.toString());
			} catch (Exception e) {
				LOGGER.warn("Failed to insert changed blocks", e);
			}

			sourceViewer.invalidateTextPresentation();
			updateHighlighting();
		}

		/**
		 * Returns the current content of the preview.
		 *
		 * Returns {@code null} if the controls hasn't been created yet.
		 *
		 */
		public String getContent() {
			if (null != editorDocument) {
				return editorDocument.get();
			}
			return null;
		}

		/**
		 * Reveals the given content block.
		 *
		 * Note that the content block reference must be set as content before. See {@link #setContent(ContentBlock[])}.
		 */
		public void revealContentBlock(ContentBlock block) {
			int accumulatedOffset = 0;
			ContentBlock blockToShow = null;
			for (ContentBlock contentBlock : contentBlocks) {
				if (block == contentBlock) {
					blockToShow = block;
					break;
				} else {
					accumulatedOffset += contentBlock.content.length();
				}
			}
			if (null != blockToShow) {
				sourceViewer.revealRange(accumulatedOffset, blockToShow.content.length());
			}
		}

		/**
		 * Sets the new info bar content
		 *
		 * @param info
		 *            The new info
		 */
		public void setInfo(String info) {
			this.infoLabel.setText(info);
		}

		private Color createInactiveColor() {
			TextStyle commentTextStyle = new TextStyle();
			preferenceStoreAccessor.populateTextStyle(HighlightingStyles.COMMENT_ID, commentTextStyle,
					DEFAULT_HIGHLIGHTING_CONFIGURATION.commentTextStyle());
			return new Color(getDisplay(), commentTextStyle.getColor());
		}

		private void createInfoBar() {
			Composite infoComposite = new Composite(this, SWT.BORDER);
			infoComposite
					.setLayoutData(GridDataFactory.fillDefaults()
							.grab(true, false)
							.create());

			infoComposite.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).create());

			infoLabel = new Label(infoComposite, SWT.NONE);
			infoLabel.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		}

		private void createEditor() {
			EmbeddedEditor editor = editorFactory.newEditor(this::createTempResource).withParent(this);

			editor.getViewer().getControl().setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());

			// Initialize the document
			editor.createPartialEditor(true);

			editorDocument = editor.getDocument();
			sourceViewer = editor.getViewer();

			configureSourceViewer(sourceViewer);

			// Clear content
			editorDocument.set("");
		}

		/** Creates temp N4JS resource, with non-existing URI to prevent conflicts with existing workspace resources. */
		private XtextResource createTempResource() {
			// Use a non-existing invalid URI to prevent conflicts with existing workspace resources, e.g.
			// "/42TempProject98248/temp.n4js"
			String partialUri = "/" + (new Random()).nextInt(Integer.MAX_VALUE) + "TempProject"
					+ Instant.now().getNano() + "/temp.n4js";
			return (XtextResource) n4jsCore.createResourceSet(Optional.absent())
					.createResource(URI.createPlatformResourceURI(partialUri, true));
		}

		private void configureSourceViewer(SourceViewer viewer) {
			viewer.setEditable(false);

			viewer.addTextListener(new ITextListener() {
				@Override
				public void textChanged(TextEvent event) {
					updateHighlighting();
					sourceViewer.getTextWidget().setFont(editorFont);
				}
			});

		}

		/**
		 * Unhighlights all text in the editor.
		 */
		private void unhighlightAll() {
			for (StyleRange range : sourceViewer.getTextWidget().getStyleRanges()) {
				if (range.foreground != inactiveColor) {
					range.foreground = inactiveColor;
					range.fontStyle = SWT.NORMAL;
					sourceViewer.getTextWidget().setStyleRange(range);
				}
			}
		}

		/**
		 * Updates the syntax highlighting.
		 *
		 * Disables highlighting for inactive blocks, or for all blocks if the editor is disabled.
		 */
		private void updateHighlighting() {
			if (!getEnabled()) {
				unhighlightAll();
				return;
			}
			if (contentBlocks == null) {
				return;
			}

			int accumulatedOffset = 0;
			for (ContentBlock block : contentBlocks) {
				if (!block.highlighted) {
					StyleRange range = new StyleRange(accumulatedOffset, block.content.length(), inactiveColor, null);
					sourceViewer.getTextWidget().setStyleRange(range);
				}
				accumulatedOffset += block.content.length();
			}
		}
	}
}
