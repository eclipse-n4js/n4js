/**
 * Copyright (c) 2018 Jens von Pilgrim.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jens von Pilgrim - Initial API and implementation
 */
package org.eclipse.n4js.smith.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.n4js.transpiler.sourcemap.MappingEntry;
import org.eclipse.n4js.transpiler.sourcemap.SourceMap;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapFileLocator;
import org.eclipse.n4js.validation.helper.FunctionValidationHelper.TripleConsumer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
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

	private static final String GEN_EXT = "js";
	private static final String MAP_EXT = "map";
	private static final String N4JS_EXT = "n4js";
	private static final int FILENAME_LENGTH = 36;

	private CTabFolder tabsOrg;
	private LinkedHashMap<File, StyledText> textOrgs;
	private StyledText textGen;
	private StyledText textMappings;
	private StyledText textMapFile;
	private StyledText textMessages;

	private IEditorPart activeEditor;

	private Color colorBgMapped;
	private Color colorBgMarked;
	private Font font;

	@Inject
	private SourceMapFileLocator sourceMapFileLocator;

	/**
	 * The created source map which is used for mapping gen to sources.
	 */
	private SourceMap sourceMap;

	/**
	 * Map to find the index (i.e. the line number in the mapping view text) of an mapping entry. This map is recreated
	 * when the mappings are loaded.
	 */
	private final Map<MappingEntry, Integer> mappingEntriesByIndex = new HashMap<>();
	/**
	 * List to find mapping by index (of the array) when selecting a mapping in the text.
	 */
	private final List<MappingEntry> mappingEntriesAsList = new ArrayList<>();

	private final Map<StyledText, Point> textMarkers = new HashMap<>();
	private ActiveEditorChangeListener activeEditorChangeListener;

	@Override
	public void createPartControl(Composite parent) {
		Display display = parent.getDisplay();
		font = new Font(parent.getDisplay(), "Courier New", 12, SWT.NORMAL);

		colorBgMapped = new Color(display, 255, 248, 217); // light yellow
		colorBgMarked = new Color(display, 255, 183, 219); // light red

		SashForm sashFilesAndMapping = new SashForm(parent, SWT.VERTICAL);
		SashForm sashGenAndOrgFiles = new SashForm(sashFilesAndMapping, SWT.HORIZONTAL);
		textGen = createText(sashGenAndOrgFiles, true);
		addTextSelectionListerners(textGen,
				(t, genLine, genColumn) -> {
					if (sourceMap != null) {
						MappingEntry entry = sourceMap.findMappingForGenPosition(genLine, genColumn);
						markMapping(t, entry);
					}
				});

		tabsOrg = new CTabFolder(sashGenAndOrgFiles, SWT.BORDER);
		textOrgs = new LinkedHashMap<>();

		TabFolder tabFolder = new TabFolder(sashFilesAndMapping, SWT.BORDER);
		TabItem tabMappings = new TabItem(tabFolder, SWT.NULL);
		tabMappings.setText("Mappings");
		textMappings = createText(tabFolder, true);
		addTextSelectionListerners(textMappings, (t, line, c) -> {
			if (line >= mappingEntriesByIndex.size()) {
				return;
			}
			MappingEntry entry = mappingEntriesAsList.get(line);
			markMapping(t, entry);
		});
		tabMappings.setControl(textMappings);
		TabItem tabMapFile = new TabItem(tabFolder, SWT.NULL);
		tabMapFile.setText("Map-File");
		textMapFile = createText(tabFolder, true);
		tabMapFile.setControl(textMapFile);

		TabItem tabMessages = new TabItem(tabFolder, SWT.NULL);
		tabMessages.setText("Messages");
		textMessages = createText(tabFolder, false);
		tabMessages.setControl(textMessages);

		// add listener to track the active editor
		activeEditorChangeListener = new ActiveEditorChangeListener(this::updateActiveEditor);
		this.getSite().getPage().addPartListener(activeEditorChangeListener);

	}

	/**
	 * Adds a mouse and caret listener to the given text widget. The selector function is called with the text widget
	 * and the text coordinates of the event.
	 */
	private void addTextSelectionListerners(StyledText text,
			TripleConsumer<StyledText, Integer, Integer> selectorFunction) {
		text.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					if (text.isFocusControl()) {
						int offset = text.getOffsetAtPoint(new Point(e.x, e.y));
						if (offset >= 0) {
							int line = text.getLineAtOffset(offset);
							int column = offset - text.getOffsetAtLine(line);
							selectorFunction.accept(text, line, column);
						}
					}
				} catch (IllegalArgumentException ex) {
					// we ignore exceptions due to wrong locations
				}
			}
		});
		text.addCaretListener(new CaretListener() {

			@Override
			public void caretMoved(CaretEvent event) {
				if (text.isFocusControl()) {
					int offset = event.caretOffset;
					int line = text.getLineAtOffset(offset);
					int column = offset - text.getOffsetAtLine(line);
					selectorFunction.accept(text, line, column);
				}
			}
		});

	}

	@Override
	public void dispose() {
		if (activeEditorChangeListener != null) { // fixes #1100
			this.getSite().getPage().removePartListener(activeEditorChangeListener);
			activeEditorChangeListener = null;
		}
		font.dispose();
		colorBgMapped.dispose();
		colorBgMarked.dispose();
	}

	private StyledText createText(Composite parent, boolean nonPropFont) {
		StyledText text = new StyledText(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		if (nonPropFont) {
			text.setFont(font);
		}
		return text;
	}

	private void log(String s) {
		textMessages.append("\n" + s);
	}

	private void updateActiveEditor(IEditorPart editorPart) {

		if (editorPart != activeEditor && editorPart != null
				&& editorPart.getEditorInput() instanceof IFileEditorInput) {
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
					log("Error reading map file " + editorFile + ": " + ex);
				}
			} else if (GEN_EXT.equals(editorFileExt)) {
				log("Found javascript file in editor, try to resolve map file and original code.");
				try {
					resolveFromGen(path);
				} catch (Exception ex) {
					log("Error resolving from generated file: " + ex);
				}
			} else if (N4JS_EXT.equals(editorFileExt)) {
				log("Found n4js file in editor, try to resolve map file and generated code.");
				try {
					resolveFromSrc(path);
				} catch (Exception ex) {
					ex.printStackTrace();
					log("Error reading source file " + editorFile + ": " + ex);
				}
			}
		}
	}

	/**
	 * Tries to find the map file from source, then delegates to {@link #resolveFromMap(Path)} to initialize the view.
	 */
	private void resolveFromSrc(Path path) throws Exception {
		File file = sourceMapFileLocator.resolveSourceMapFromSrc(path);
		if (file == null)
			return;
		resolveFromMap(file.toPath());
	}

	/**
	 * Tries to find the map file from gen, then delegates to {@link #resolveFromMap(Path)} to initialize the view.
	 */
	private void resolveFromGen(Path path) throws Exception {
		File file = sourceMapFileLocator.resolveSourceMapFromGen(path);
		if (file == null)
			return;
		resolveFromMap(file.toPath());
	}

	/**
	 * Loads the map file from given path and initializes the view.
	 */
	private void resolveFromMap(Path path) throws Exception {
		File mapFile = path.toFile();
		String s = readFile(mapFile);
		textMapFile.setText(s);

		sourceMap = SourceMap.loadAndResolve(path);

		StringBuffer strb = new StringBuffer();

		String genShort = sourceMap.getResolvedFile().getFileName().toString();
		if (genShort.length() > FILENAME_LENGTH) {
			genShort = "…" + genShort.substring(genShort.length() - FILENAME_LENGTH - 1);
		}

		for (MappingEntry mappingEntry : sourceMap.getGenMappings()) {
			mappingEntriesByIndex.put(mappingEntry, mappingEntriesByIndex.size());
			mappingEntriesAsList.add(mappingEntry);
			if (strb.length() > 0) {
				strb.append("\n");
			}
			String srcShort = sourceMap.sources.get(mappingEntry.srcIndex);
			if (srcShort.length() > FILENAME_LENGTH) {
				srcShort = "…" + srcShort.substring(srcShort.length() - FILENAME_LENGTH - 1);
			}
			strb.append(String.format("%1$-3s:%2$03d:%3$03d -> %4$-3s:%5$03d:%6$03d)",
					genShort,
					mappingEntry.genLine + 1, mappingEntry.genColumn + 1,
					srcShort,
					mappingEntry.srcLine + 1, mappingEntry.srcColumn + 1));
		}
		textMappings.setText(strb.toString());

		File file = sourceMap.getResolvedFile().toFile();
		s = readFile(file);
		textGen.setText(s);

		for (Path srcPath : sourceMap.getResolvedSources()) {
			addOrg(srcPath.toFile());
		}

		for (MappingEntry mappingEntry : sourceMap.getGenMappings()) {
			highlightMapping(mappingEntry);
		}
	}

	private void highlightMapping(MappingEntry entry) {
		styleMappedTextGen(null, entry, false);
		styleMappedTextOrg(null, entry, false);
	}

	/**
	 * Called when new mapping are to be loaded, e.g. when active editor has changed. This method must be called before
	 * all resolve methods.
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
		mappingEntriesByIndex.clear();
		mappingEntriesAsList.clear();
		textMarkers.clear();

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
		final StyledText text = createText(tabsOrg, true);
		text.setText(code);
		tabItem.setControl(text);
		tabItem.setText(file.getName());
		textOrgs.put(canFile, text);
		tabsOrg.setSelection(tabsOrg.getItemCount() - 1);
		addTextSelectionListerners(text, (t, srcLine, srcColumn) -> {
			if (sourceMap != null) {
				int srcIndex = 0;
				for (Entry<File, StyledText> e : textOrgs.entrySet()) {
					if (e.getValue() == t) {
						break;
					}
					srcIndex++;
				}
				if (srcIndex >= textOrgs.size()) { // text sending event not found
					return;
				}
				MappingEntry entry = sourceMap.findMappingForSrcPosition(srcIndex, srcLine, srcColumn);
				markMapping(text, entry);
			}
		});

	}

	private String readFile(File file) throws Exception {
		InputStream in = new FileInputStream(file);
		try (InputStreamReader reader = new InputStreamReader(in, Charsets.UTF_8)) {
			return CharStreams.toString(reader);
		}
	}

	@Override
	public void setFocus() {
		// nothing todo
	}

	private void markMapping(StyledText textEmittingEvent, MappingEntry entry) {
		for (Entry<StyledText, Point> e : textMarkers.entrySet()) {
			doStyleText(e.getKey(), e.getKey() == textMappings ? null : colorBgMapped, e.getValue().x, e.getValue().y);
		}
		textMarkers.clear();

		styleMappedTextGen(textEmittingEvent, entry, true);
		styleMappedTextOrg(textEmittingEvent, entry, true);
		styleMapping(textEmittingEvent, entry, true);
	}

	private void styleMappedTextOrg(StyledText textEmittingEvent, MappingEntry entry, boolean mark) {
		if (entry != null) {
			Path srcPath = sourceMap.getResolvedSources().get(entry.srcIndex);
			StyledText text = textOrgs.get(srcPath.normalize().toFile());
			int srcOffset = text.getOffsetAtLine(entry.srcLine)
					+ entry.srcColumn;
			int srcOffsetEnd = srcOffset;
			int length = sourceMap.computeLengthSrc(entry);
			if (length >= 0) {
				srcOffsetEnd += length;
			} else {
				if (entry.srcLine + 1 >= text.getLineCount()) { // last line
					srcOffsetEnd = text.getCharCount();
				} else {
					srcOffsetEnd = text.getOffsetAtLine(entry.srcLine + 1) - 1;
				}
			}
			styleText(text, srcOffset, srcOffsetEnd, mark);
			if (mark && text != textEmittingEvent) {
				text.setSelection(srcOffset);
			}
		} else {
			StyledText text = (StyledText) tabsOrg.getSelection().getControl();
			styleText(text, 0, 0, mark);
		}
	}

	private void styleMappedTextGen(StyledText textEmittingEvent, MappingEntry entry, boolean mark) {
		if (entry != null) {
			int genOffset = textGen.getOffsetAtLine(entry.genLine) + entry.genColumn;
			int genOffsetEnd = genOffset;
			int length = sourceMap.computeLengthGen(entry);
			if (length >= 0) {
				genOffsetEnd += length;
			} else {
				genOffsetEnd = textGen.getOffsetAtLine(entry.genLine + 1) - 1;
			}
			styleText(textGen, genOffset, genOffsetEnd, mark);
			if (mark && textGen != textEmittingEvent) {
				textGen.setSelection(genOffset);
			}
		} else {
			styleText(textGen, 0, 0, mark);
		}
	}

	private void styleMapping(StyledText textEmittingEvent, MappingEntry entry, boolean mark) {
		if (entry != null) {
			int mapIndex = mappingEntriesByIndex.get(entry);
			if (mapIndex >= 0) {
				int mapOffset = textMappings.getOffsetAtLine(mapIndex);
				int mapOffsetEnd = mapIndex < mappingEntriesByIndex.size() - 1
						? textMappings.getOffsetAtLine(mapIndex + 1) - 1
						: textMappings.getText().length() - 1;
				styleText(textMappings, mapOffset, mapOffsetEnd, mark);
				if (mark && textGen != textEmittingEvent) {
					textMappings.setSelection(mapOffset);
				}
				return;
			}
		}
		styleText(textMappings, 0, 0, mark);
	}

	private void styleText(StyledText text, int start, int end, boolean mark) {
		Color color = mark ? colorBgMarked : colorBgMapped;
		if (start <= end) {
			doStyleText(text, color, start, end);
			if (mark) {
				textMarkers.put(text, new Point(start, end));
			}
		}
	}

	private static void doStyleText(StyledText text, Color colorBackground, int start, int end) {
		final int max = text.getCharCount();
		if (end >= max) {
			end = max - 1;
		}
		if (start < 0 || end < 0 || start > end || end - start == 0) {
			return;
		}
		StyleRange styleRange = new StyleRange();
		styleRange.start = start;
		styleRange.length = end - start;
		styleRange.background = colorBackground;
		text.setStyleRange(styleRange);
	}

}
