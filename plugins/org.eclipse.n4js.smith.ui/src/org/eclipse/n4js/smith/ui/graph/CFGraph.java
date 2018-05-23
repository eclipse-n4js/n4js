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
package org.eclipse.n4js.smith.ui.graph;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.smith.ui.CFGraphProvider;
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
	final NavigableMap<CFNode, ControlFlowElement> nodeMap = new TreeMap<>(new CFEComparator());
	private CFGraphProvider gProvider;
	private boolean layoutDone;

	/**
	 * Constructor
	 */
	public CFGraph() {
		locFileProvider = new DefaultLocationInFileProvider();
		editor = EditorUtils.getActiveXtextEditor();
		styledText = editor.getInternalSourceViewer().getTextWidget();
		layoutDone = false;
	}

	@Override
	public void build(CFGraphProvider provider, Object input) {
		clear();
		nodeMap.clear();
		gProvider = provider;

		Collection<ControlFlowElement> cfes = gProvider.getElements(input);
		for (ControlFlowElement cfe : cfes) {

			if (cfe instanceof Script) {
				continue;
			}

			if (FGUtils.isCFContainer(cfe)) {
				CFNode entryNode = gProvider.getEntryNode(cfe);
				CFNode exitNode = gProvider.getExitNode(cfe);
				nodeMap.put(entryNode, entryNode.getControlFlowElement());
				nodeMap.put(exitNode, exitNode.getControlFlowElement());
				nodes.add(entryNode);
				nodes.add(exitNode);

				List<Edge> succs = gProvider.getConnectedEdges(entryNode, null);
				edges.addAll(succs);

			} else {
				CFNode node = gProvider.getNode(cfe);
				nodes.add(node);
				nodeMap.put(node, cfe);

				List<Edge> succs = gProvider.getConnectedEdges(node, null);
				edges.addAll(succs);
			}
		}

	}

	@Override
	public void layout(GC gc) {
		if (layoutDone)
			return;

		int lastLine = 0;
		int posInLine = 70;
		int lineCounter = 0;
		int entryLineCounter = 0;

		Set<Entry<CFNode, ControlFlowElement>> entries = nodeMap.entrySet();
		Iterator<Entry<CFNode, ControlFlowElement>> entriesIt = entries.iterator();

		CFNode lastNode = null;
		while (entriesIt.hasNext()) {
			Entry<CFNode, ControlFlowElement> entry = entriesIt.next();
			CFNode node = entry.getKey();
			ControlFlowElement cfe = entry.getValue();

			int line = getLineEnd(cfe);
			boolean lineChange = lastLine != line;
			if (lineChange) {
				posInLine = 70;

				boolean lastIsEntry = lastNode != null && lastNode.isEntry;
				boolean lastIsExit = lastNode != null && lastNode.isExit;
				boolean isContainer = node.isEntry || node.isExit;
				if (!lastIsEntry && !lastIsExit && !isContainer) {
					lineCounter++; // normal lines
				}
				if (lastIsExit || node.isEntry) {
					lineCounter += 2; // lines between two functions
				}
				if (node.isExit && entryLineCounter == lineCounter) {
					lineCounter++; // line if function consists of one line only
				}
			}

			node.x = node.isEntry || node.isExit ? 0 : posInLine;
			node.y = lineCounter * 100;

			node.trim(gc);
			posInLine += node.width + 50;

			lastNode = node;
			lastLine = line;
			if (node.isEntry) {
				entryLineCounter = lineCounter;
			}
		}
		layoutDone = true;
	}

	/** @return offset end of the source code region of the given {@link ControlFlowElement} */
	private int getOffsetStart(ControlFlowElement cfe) {
		ITextRegion tr = locFileProvider.getFullTextRegion(cfe);
		return tr.getOffset();
	}

	/** @return offset end of the source code region of the given {@link ControlFlowElement} */
	private int getOffsetEnd(ControlFlowElement cfe) {
		ITextRegion tr = locFileProvider.getFullTextRegion(cfe);
		return tr.getOffset() + tr.getLength();
	}

	/** @return line number of the source code region's end of the given {@link ControlFlowElement} */
	private int getLineStart(ControlFlowElement cfe) {
		int line = styledText.getLineAtOffset(getOffsetStart(cfe));
		return line;
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
	 * </ul>
	 */
	private class CFEComparator implements Comparator<CFNode> {
		@Override
		public int compare(CFNode cfn1, CFNode cfn2) {
			ControlFlowElement cfe1 = cfn1.getControlFlowElement();
			ControlFlowElement cfe2 = cfn2.getControlFlowElement();

			int line1 = cfn1.isEntry ? getLineStart(cfe1) : getLineEnd(cfe1);
			int line2 = cfn2.isEntry ? getLineStart(cfe2) : getLineEnd(cfe2);
			int offset = line1 - line2;

			if (offset == 0) {
				offset = cfn1.nodeIdx - cfn2.nodeIdx;
			}

			return offset;
		}
	}

}
