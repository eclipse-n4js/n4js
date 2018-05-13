/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.smith.ui;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.n4js.ui.handler.GeneratedJsFileLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.ViewPart;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.CharStreams;
import com.google.inject.Inject;

/**
 * @see <a href="http://sokra.github.io/source-map-visualization/">source-map-visualization</a>
 */
public class SourceMapView extends ViewPart {

	private final String SOURCEMAP_INDICATOR = "sourceMappingURL=";

	private final String GEN_EXT = "js";
	private final String MAP_EXT = "map";
	private final String N4JS_EXT = "n4js";

	private TabFolder tabsOrg;
	private Map<IFile, StyledText> textOrgs;
	private StyledText textGen;
	private StyledText textMappings;
	private StyledText textMapFile;
	private StyledText textMessages;

	private IEditorPart activeEditor;
	private IFile mapFile;
	private IFile genFile;
	private final Map<String, IFile> orgFiles = new LinkedHashMap<>();

	private Color[] colors;

	@Inject
	private GeneratedJsFileLocator fileLocator;

	private Font font;

	@Override
	public void createPartControl(Composite parent) {
		Display display = parent.getDisplay();
		font = new Font(parent.getDisplay(), "Courier New", 12, SWT.NORMAL);
		int diff = 60, shades = 3;
		int index = 0;
		colors = new Color[2 * 3 * shades];
		for (int r = 0; r < shades; r++) {
			for (int g = 0; g < shades; g++) {
				for (int b = 0; b < shades; b++) {
					if (r != g || r != b || g != b) {
						colors[index++] = new Color(display, 255 - diff * r, 255 - diff * g, 255 - diff * b);
					}
				}
			}
		}

		SashForm codeMapping = new SashForm(parent, SWT.VERTICAL);
		SashForm orgGen = new SashForm(codeMapping, SWT.HORIZONTAL);
		tabsOrg = new TabFolder(orgGen, SWT.BORDER);
		textOrgs = new LinkedHashMap<>();
		textGen = createText(orgGen, true);

		SashForm mappingAndMessages = new SashForm(codeMapping, SWT.HORIZONTAL);

		TabFolder tabFolder = new TabFolder(mappingAndMessages, SWT.BORDER);
		TabItem tabMappings = new TabItem(tabFolder, SWT.NULL);
		tabMappings.setText("Mappings");
		textMappings = createText(tabFolder, true);
		tabMappings.setControl(textMappings);
		TabItem tabMapFile = new TabItem(tabFolder, SWT.NULL);
		tabMapFile.setText("Map-File");
		textMapFile = createText(tabFolder, true);
		tabMapFile.setControl(textMapFile);

		textMessages = createText(mappingAndMessages, false);

		// add listener to track the active editor
		this.getSite().getPage().addPartListener(new ActiveEditorChangeListener(this::updateActiveEditor));

	}

	@Override
	public void dispose() {
		font.dispose();
		for (int i = 0; i < colors.length; i++) {
			colors[i].dispose();
		}
	}

	private StyledText createText(Composite parent, boolean nonPropFont) {
		StyledText text = new StyledText(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		text.addLineStyleListener(new LineStyleListener() {
			@Override
			public void lineGetStyle(LineStyleEvent e) {
				e.bulletIndex = text.getLineAtOffset(e.lineOffset);
				StyleRange style = new StyleRange();
				style.metrics = new GlyphMetrics(0, 0, Integer.toString(text.getLineCount() + 1).length() * 12);
				e.bullet = new Bullet(ST.BULLET_NUMBER, style);
			}
		});
		if (nonPropFont) {
			text.setFont(font);
		}
		return text;
	}

	void log(String s) {
		textMessages.append("\n" + s);
	}

	boolean markText(StyledText text, int colIndex, int line, int colStart, int colEnd) {
		final int lineCount = text.getLineCount();
		final int max = text.getCharCount();
		if (lineCount < line) {
			return false;
		}
		final int lineOffset = text.getOffsetAtLine(line);
		final int start = lineOffset + colStart;
		if (start >= max) {
			return false;
		}
		int end;
		if (colEnd < 0) {
			end = line + 1 < lineCount ? text.getOffsetAtLine(line + 1) - 1 : max - 1;
		} else {
			if (colEnd < colStart) {
				return false;
			}
			end = lineOffset + colEnd;
		}
		if (end >= max) {
			return false;
		}
		StyleRange styleRange = new StyleRange();
		styleRange.start = start;
		styleRange.length = end - start;
		// styleRange.fontStyle = SWT.BOLD;
		styleRange.background = colors[colIndex];
		text.setStyleRange(styleRange);

		return true;
	}

	private void updateActiveEditor(IEditorPart editorPart) {

		if (editorPart != activeEditor) {
			IFileEditorInput fei = (IFileEditorInput) activeEditor.getEditorInput();
			activeEditor = editorPart;

			reset();

			IFile editorFile = fei.getFile();
			String editorFileExt = editorFile.getFileExtension().toLowerCase();

			if (MAP_EXT.equals(editorFileExt)) {
				log("Found map file in editor, try to resolve original and generated code.");
				mapFile = editorFile;
				try {
					String s = readFile(mapFile);
					textMapFile.setText(s);
					resolveFromMapFile(s);
				} catch (Exception ex) {
					log("Error reading map file " + mapFile + ": " + ex);
				}
			} else if (GEN_EXT.equals(editorFileExt)) {
				log("Found javascript file in editor, try to resolve map file and original code.");
				genFile = editorFile;
				try {
					String genCode = readFile(genFile);
					textGen.setText(genCode);
					resolveFromGen(genCode);
				} catch (Exception ex) {
					log("Error reading javascript file " + genFile + ": " + ex);
				}
			} else if (N4JS_EXT.equals(editorFileExt)) {
				log("Found n4js file in editor, try to resolve map file and generated code.");
				addOrg(editorFile);
				try {
					resolveFromN4JS(editorFile);
				} catch (Exception ex) {
					log("Error reading javascript file " + genFile + ": " + ex);
				}
			}

			List<IFile> orgFiles = new ArrayList<>();
			IFile genFile = null;
			textMapFile.setText("Mappings file not loaded");
			textMappings.setText("No mappings loaded.");

			final Optional<IFile> generatedSource = fileLocator.tryGetGeneratedSourceForN4jsFile(orgFile);
			if (generatedSource.isPresent()) {
				genFile = generatedSource.get();
				textGen.setText("Loading " + genFile);
			} else {
				textGen.setText("Generated file not found.");
			}

			boolean error = false;

			try {
				textOrg.setText(readFile(orgFile));
			} catch (Exception ex) {
				textOrg.setText("Error loading " + orgFile + "\n" + ex);
				error = true;
			}
			String genCode = null;
			if (genFile != null && genFile.exists()) {
				try {
					genCode = readFile(genFile);
					textGen.setText(genCode);
				} catch (Exception ex) {
					textGen.setText("Error loading " + genFile + "\n" + ex);
					error = true;
				}

				if (genCode != null) {
					int beginIndex = genCode.lastIndexOf(SOURCEMAP_INDICATOR);
					if (beginIndex >= 0) {
						int endIndex = genCode.indexOf('\n', beginIndex);
						if (endIndex < 0)
							endIndex = genCode.length();
						String mapFileName = genCode.substring(beginIndex + SOURCEMAP_INDICATOR.length(), endIndex)
								.trim();
						mapFile = (IFile) genFile.getParent().findMember(mapFileName);
						textMapFile.setText("Loading " + mapFile);
					}
				}
				if (mapFile == null) {
					String genFileName = genFile.getName();
					mapFile = (IFile) genFile.getParent().findMember(genFileName + ".map");
					if (!mapFile.exists()) {
						String ext = genFile.getFileExtension();
						String mapFileName = genFileName.substring(0, genFileName.length() - ext.length()) + ".map";
						mapFile = (IFile) genFile.getParent().findMember(mapFileName);
					}
				}
			}
			if (mapFile != null && mapFile.exists()) {
				try {
					String mapCode = readFile(mapFile);
					textMapFile.setText(mapCode);
				} catch (Exception ex) {
					textMapFile.setText("Error loading " + mapFile + "\n" + ex);
					error = true;
				}
			}

		}
		markText();
	}

	/**
	 * @param editorFile
	 */
	private void resolveFromN4JS(IFile editorFile) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param genCode
	 */
	private void resolveFromGen(String genCode) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param s
	 */
	private void resolveFromMapFile(String s) {

	}

	/**
	 *
	 */
	private void reset() {
		textGen.setText("");
		textMapFile.setText("");
		textMappings.setText("");
		textMessages.setText("");
		for (StyledText t : textOrgs.values()) {
			t.dispose();
		}
		TabItem[] items = tabsOrg.getItems();
		for (TabItem item : items) {
			item.dispose();
		}
		textOrgs.clear();

		mapFile = null;
		genFile = null;
		orgFiles.clear();

	}

	private void addOrg(IFile file) {
		String code;
		try {
			code = readFile(file);
		} catch (Exception e) {
			log("Error loading original file " + file + ": " + e);
			return;
		}
		TabItem tabItem = new TabItem(tabsOrg, SWT.NULL);
		tabItem.setText(file.getName());
		StyledText text = createText(tabsOrg, true);
		tabItem.setControl(text);
		text.setText(code);
		textOrgs.put(file, text);
	}

	private String readFile(IFile file) throws Exception {
		InputStream in = file.getContents();
		try (InputStreamReader reader = new InputStreamReader(in, Charsets.UTF_8)) {
			return CharStreams.toString(reader);
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
