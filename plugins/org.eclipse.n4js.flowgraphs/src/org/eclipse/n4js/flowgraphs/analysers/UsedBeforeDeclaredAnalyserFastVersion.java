/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.analysers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.analysis.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TVariable;

/**
 * This implementation of the "used before defined" analysis relies only one the {@link GraphVisitor}. This simplifies
 * the implementation and increases runtime performance. However, in some corner cases, this analysis is not reliable,
 * such as:
 *
 * <pre>
 * for (; r < 9;) { // missing warning at 'r'
 * 	var r = 2;
 * }
 * </pre>
 *
 * This is due to the merging behavior which merges visits the condition and merges afterwards.
 */
// TODO: not active/tested
public class UsedBeforeDeclaredAnalyserFastVersion extends GraphVisitor {
	final Map<VariableDeclaration, Set<IdentifierRef>> checkLists = new HashMap<>();

	/** Constructor */
	public UsedBeforeDeclaredAnalyserFastVersion() {
		super(TraverseDirection.Backward);
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (cfe instanceof VariableDeclaration) {
			checkLists.put((VariableDeclaration) cfe, new HashSet<>());
		}
		if (cfe instanceof IdentifierRef) {
			IdentifierRef ir = (IdentifierRef) cfe;
			IdentifiableElement id = SymbolFactory.getId(ir);
			if (id instanceof TVariable) {
				TVariable tvar = (TVariable) id;
				id = (VariableDeclaration) tvar.getAstElement();
			}
			Set<IdentifierRef> refs = checkLists.get(id);
			if (refs != null) {
				refs.add(ir);
			}
		}
	}

	/** @return all {@link IdentifierRef}s that are used before declared */
	public List<IdentifierRef> getUsedButNotDeclaredIdentifierRefs() {
		List<IdentifierRef> idRefs = new LinkedList<>();

		for (Set<IdentifierRef> refs : checkLists.values()) {
			idRefs.addAll(refs);
		}
		return idRefs;
	}

}
