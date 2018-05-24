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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.n4js.transpiler.sourcemap.MappingEntry;
import org.eclipse.n4js.transpiler.sourcemap.SourceMap;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapFileLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.ViewPart;

import com.google.common.base.Charsets;
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

	private CTabFolder tabsOrg;
	private Map<File, StyledText> textOrgs;
	private StyledText textGen;
	private StyledText textMappings;
	private StyledText textMapFile;
	private StyledText textMessages;

	private IEditorPart activeEditor;

	private Color[] colors;

	private Font font;

	@Inject
	private SourceMapFileLocator sourceMapFileLocator;

	private SourceMap sourceMap;

	@Override
	public void createPartControl(Composite parent) {
		Display display = parent.getDisplay();
		font = new Font(parent.getDisplay(), "Courier New", 12, SWT.NORMAL);
		int diff = 60, shades = 3;
		int index = 0;
		// colors = new Color[2 * 3 * shades];
		// for (int r = 0; r < shades; r++) {
		// for (int g = 0; g < shades; g++) {
		// for (int b = 0; b < shades; b++) {
		// if (r != g || r != b || g != b) {
		// colors[index++] = new Color(display, 255 - diff * r, 255 - diff * g, 255 - diff * b);
		// }
		// }
		// }
		// }

		SashForm codeMapping = new SashForm(parent, SWT.VERTICAL);
		SashForm orgGen = new SashForm(codeMapping, SWT.HORIZONTAL);
		tabsOrg = new CTabFolder(orgGen, SWT.BORDER);

		textOrgs = new LinkedHashMap<>();
		textGen = createText(orgGen, true);
		textGen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int offset = textGen.getOffsetAtLocation(new Point(e.x, e.y));
				int line = textGen.getLineAtOffset(offset);
				int column = offset - textGen.getOffsetAtLine(line);
				selectSrcByGenPos(line, column);
			}
		});
		textGen.addCaretListener(new CaretListener() {

			@Override
			public void caretMoved(CaretEvent event) {
				int offset = event.caretOffset;
				int line = textGen.getLineAtOffset(offset);
				int column = offset - textGen.getOffsetAtLine(line);
				selectSrcByGenPos(line, column);
			}
		});

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
		// for (int i = 0; i < colors.length; i++) {
		// colors[i].dispose();
		// }
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

	void err(String s) {
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

		if (editorPart != activeEditor && editorPart != null) {
			activeEditor = editorPart;
			IFileEditorInput fei = (IFileEditorInput) activeEditor.getEditorInput();
			activeEditor = editorPart;

			reset();

			IFile editorFile = fei.getFile();
			String editorFileExt = editorFile.getFileExtension().toLowerCase();
			Path path = Paths.get(editorFile.getLocation().toFile().toURI());

			if (MAP_EXT.equals(editorFileExt)) {
				log("Found map file in editor, try to resolve original and generated code.");
				try {
					resolveFromMap(path);
				} catch (Exception ex) {
					err("Error reading map file " + editorFile + ": " + ex);
				}
			} else if (GEN_EXT.equals(editorFileExt)) {
				log("Found javascript file in editor, try to resolve map file and original code.");
				try {
					resolveFromGen(path);
				} catch (Exception ex) {
				}
			} else if (N4JS_EXT.equals(editorFileExt)) {
				log("Found n4js file in editor, try to resolve map file and generated code.");
				try {
					resolveFromSrc(path);
				} catch (Exception ex) {
					err("Error reading source file " + editorFile + ": " + ex);
				}
			}
		}
	}

	private void resolveFromSrc(Path path) throws Exception {
		File file = sourceMapFileLocator.resolveSourceMapFromSrc(path);
		if (file == null)
			return;
		resolveFromMap(file.toPath());
	}

	private void resolveFromGen(Path path) throws Exception {
		File file = sourceMapFileLocator.resolveSourceMapFromGen(path);
		if (file == null)
			return;
		resolveFromMap(file.toPath());
	}

	private void resolveFromMap(Path path) throws Exception {
		File mapFile = path.toFile();
		String s = readFile(mapFile);
		textMapFile.setText(s);

		sourceMap = SourceMap.loadAndResolve(path);

		File file = sourceMap.getResolvedFile().toFile();
		s = readFile(file);
		textGen.setText(s);

		for (Path srcPath : sourceMap.getResolvedSources()) {
			addOrg(srcPath.toFile());
		}
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
		CTabItem[] items = tabsOrg.getItems();
		for (CTabItem item : items) {
			item.dispose();
		}
		textOrgs.clear();
		sourceMap = null;

	}

	private void addOrg(File file) throws IOException {
		File canFile = file.getCanonicalFile();
		if (textOrgs.containsKey(canFile)) {
			return;
		}
		String code;
		try {
			code = readFile(canFile);
		} catch (Exception e) {
			log("Error loading original file " + file + ": " + e);
			return;
		}
		CTabItem tabItem = new CTabItem(tabsOrg, SWT.NONE);
		StyledText text = createText(tabsOrg, true);
		text.setText(code);
		tabItem.setControl(text);
		tabItem.setText(file.getName());
		textOrgs.put(canFile, text);
		tabsOrg.setSelection(tabsOrg.getItemCount() - 1);

	}

	private String readFile(File file) throws Exception {
		InputStream in = new FileInputStream(file);
		try (InputStreamReader reader = new InputStreamReader(in, Charsets.UTF_8)) {
			return CharStreams.toString(reader);
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	private void selectSrcByGenPos(int genLine, int genColumn) {
		if (sourceMap != null) {
			MappingEntry entry = sourceMap.findMappingForGenPosition(genLine, genColumn);
			if (entry != null) {
				Path srcPath = sourceMap.getResolvedSources().get(entry.srcIndex);
				StyledText text = textOrgs.get(srcPath.normalize().toFile());
				int delta = genColumn - entry.genColumn;
				int srcOffset = text.getOffsetAtLine(entry.srcLine)
						+ entry.srcColumn + delta;
				text.setSelection(srcOffset, srcOffset + 1);
			}
		}
	}

}
