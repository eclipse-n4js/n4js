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
 * A Graph is mainly a container for {@link Node}s and {@link Edge}s. In addition, this class is responsible for
 * creating the nodes and edges based on a given {@link GraphProvider} (see {@link #build(CFGraphProvider, Object)}) and
 * for layout, see method {@link #layout(GC)}.
 */
public class CFGraph extends Graph<CFGraphProvider> {
	ILocationInFileProvider locFileProvider;
	XtextEditor editor;
	StyledText styledText;

	NavigableMap<ControlFlowElement, Node> nodeMap = new TreeMap<>(new CFEComparator());

	/**
	 *
	 */
	public CFGraph() {
		locFileProvider = new DefaultLocationInFileProvider();
		editor = EditorUtils.getActiveXtextEditor();
		styledText = editor.getInternalSourceViewer().getTextWidget();
	}

	@Override
	public void build(CFGraphProvider provider, Object input) {
		clear();
		Collection<ControlFlowElement> cfes = provider.getElements(input);
		for (ControlFlowElement cfe : cfes) {
			Node node = provider.getNode(cfe);
			nodes.add(node);
			nodeMap.put(cfe, node);

			List<Edge> succs = provider.getConnectedEdges(node, null);
			edges.addAll(succs);
		}
	}

	@Override
	public void layout(GC gc) {
		int lastLine = 0;
		int posInLine = 10;
		int lineCounter = 0;
		Set<Entry<ControlFlowElement, Node>> keys = nodeMap.entrySet();
		Iterator<Entry<ControlFlowElement, Node>> keysIt = keys.iterator();
		while (keysIt.hasNext()) {
			Entry<ControlFlowElement, Node> entry = keysIt.next();
			ControlFlowElement cfe = entry.getKey();
			Node node = entry.getValue();
			int line = getLine(cfe);
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

	private int getLine(ControlFlowElement entry) {
		ITextRegion tr = locFileProvider.getSignificantTextRegion(entry);
		int line = styledText.getLineAtOffset(tr.getOffset());
		return line;
	}

	private class CFEComparator implements Comparator<ControlFlowElement> {
		@Override
		public int compare(ControlFlowElement cfe1, ControlFlowElement cfe2) {
			ITextRegion tr1 = locFileProvider.getSignificantTextRegion(cfe1);
			ITextRegion tr2 = locFileProvider.getSignificantTextRegion(cfe2);
			int offset = tr1.getOffset() - tr2.getOffset();
			if (offset == 0) {
				offset = tr1.getLength() - tr2.getLength();
			}
			return offset;
		}
	}
}
