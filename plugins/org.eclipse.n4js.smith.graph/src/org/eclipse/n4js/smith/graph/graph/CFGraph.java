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
package org.eclipse.n4js.smith.graph.graph;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.smith.graph.CFGraphProvider;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GC;
import org.eclipse.xtext.resource.DefaultLocationInFileProvider;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.util.ITextRegion;

/**
 * The {@link CFGraph} creates a control flow graph based on the nodes and edges provided by the
 * {@link CFGraphProvider}.
 */
public class CFGraph extends Graph<CFGraphProvider> {
	final ILocationInFileProvider locFileProvider;
	final XtextEditor editor;
	final StyledText styledText;
	final NavigableMap<ControlFlowElement, Node> nodeMap = new TreeMap<>(new CFEComparator());
	private CFGraphProvider gProvider;
	private N4JSFlowAnalyzer flowAnalyzer;

	/**
	 * Constructor
	 */
	public CFGraph() {
		locFileProvider = new DefaultLocationInFileProvider();
		editor = EditorUtils.getActiveXtextEditor();
		styledText = editor.getInternalSourceViewer().getTextWidget();
	}

	@Override
	public void build(CFGraphProvider provider, Object input) {
		clear();
		nodeMap.clear();
		gProvider = provider;
		flowAnalyzer = provider.getFlowAnalyses();

		Collection<ControlFlowElement> cfes = gProvider.getElements(input);
		for (ControlFlowElement cfe : cfes) {
			Node node = gProvider.getNode(cfe);
			nodes.add(node);
			nodeMap.put(cfe, node);

			List<Edge> succs = gProvider.getConnectedEdges(node, null);
			edges.addAll(succs);
		}
	}

	@Override
	public void layout(GC gc) {
		int lastLine = 0;
		int posInLine = 10;
		int lineCounter = 0;

		Set<Entry<ControlFlowElement, Node>> entries = nodeMap.entrySet();
		Iterator<Entry<ControlFlowElement, Node>> entriesIt = entries.iterator();

		while (entriesIt.hasNext()) {
			Entry<ControlFlowElement, Node> entry = entriesIt.next();
			ControlFlowElement cfe = entry.getKey();
			Node node = entry.getValue();

			int line = getLineEnd(cfe);
			boolean lineChange = lastLine != line;
			lastLine = line;
			if (lineChange) {
				posInLine = 10;
				lineCounter++;
			}
			node.x = posInLine;
			node.y = lineCounter * 100;
			node.trim(gc);
			posInLine += node.width + 50;
		}
	}

	/** @return offset of the source code region of the given {@link ControlFlowElement} */
	private int getOffset(ControlFlowElement cfe) {
		ITextRegion tr = locFileProvider.getFullTextRegion(cfe);
		return tr.getOffset();
	}

	/** @return offset end of the source code region of the given {@link ControlFlowElement} */
	private int getOffsetEnd(ControlFlowElement cfe) {
		ITextRegion tr = locFileProvider.getFullTextRegion(cfe);
		return tr.getOffset() + tr.getLength();
	}

	/** @return length of the source code region of the given {@link ControlFlowElement} */
	private int getLength(ControlFlowElement cfe) {
		ITextRegion tr = locFileProvider.getFullTextRegion(cfe);
		return tr.getLength();
	}

	/** @return line number of the source code region's end of the given {@link ControlFlowElement} */
	private int getLineEnd(ControlFlowElement cfe) {
		int line = styledText.getLineAtOffset(getOffsetEnd(cfe));
		return line;
	}

	/**
	 * Compare two {@link ControlFlowElement}s (CFEs). When used to sort a collection C, the resulting order is as
	 * follows:
	 * <ul>
	 * <li/>All CFEs of one source code line are grouped together
	 * <li/>The CFEs of one line are ordered in the direction of the control flow.
	 * <li/>In case two CFEs are parallel to each other (regarding the control flow), their order is not specified but
	 * stable in the sense, that it does not change between two calls.
	 * </ul>
	 */
	private class CFEComparator implements Comparator<ControlFlowElement> {
		@Override
		public int compare(ControlFlowElement cfe1, ControlFlowElement cfe2) {
			int line1 = getLineEnd(cfe1);
			int line2 = getLineEnd(cfe2);
			int offset = line1 - line2;

			if (offset == 0) {
				if (flowAnalyzer.isSuccessor(cfe1, cfe2))
					return -10;

				if (flowAnalyzer.isSuccessor(cfe2, cfe1))
					return 10;

				if (flowAnalyzer.isTransitiveSuccessor(cfe1, cfe2))
					return -1;

				if (flowAnalyzer.isTransitiveSuccessor(cfe2, cfe1))
					return 1;
			}

			if (offset == 0) {
				offset = getOffset(cfe1) - getOffset(cfe2);
			}
			if (offset == 0) {
				offset = getLength(cfe1) - getLength(cfe2);
			}

			if (offset == 0) {

				System.out.println("hashcode");
				offset = cfe1.hashCode() - cfe2.hashCode();
			}

			return offset;
		}
	}

}
