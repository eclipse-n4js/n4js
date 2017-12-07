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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.analyses.FastFlowVisitor;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TVariable;

/**
 * Analysis to detect uses of {@link IdentifierRef}s that are located in the control flow before their corresponding
 * variables are declared.
 */
public class UsedBeforeDeclaredAnalyser extends FastFlowVisitor {

	static class CVLocationDataEntry extends ActivationLocation {
		final ControlFlowElement cfe;
		final List<IdentifierRef> idRefs = new LinkedList<>();

		CVLocationDataEntry(ControlFlowElement cfe) {
			this.cfe = cfe;
		}

		@Override
		public Object getKey() {
			return cfe;
		}
	}

	/** @return all {@link IdentifierRef}s that are used before declared */
	public List<IdentifierRef> getUsedButNotDeclaredIdentifierRefs() {
		List<IdentifierRef> idRefs = new LinkedList<>();
		for (ActivationLocation actLoc : getAllActivationLocations()) {
			CVLocationDataEntry userData = (CVLocationDataEntry) actLoc;
			idRefs.addAll(userData.idRefs);
		}
		return idRefs;
	}

	@Override
	protected void visitNext(FastFlowBranch currentBranch, ControlFlowElement cfe) {

		if (cfe instanceof VariableDeclaration) {
			CVLocationDataEntry userData = (CVLocationDataEntry) currentBranch.getActivationLocation(cfe);
			if (userData != null) {
				userData.idRefs.clear();
			} else {
				currentBranch.activate(new CVLocationDataEntry(cfe));
			}

		} else if (cfe instanceof IdentifierRef) {
			IdentifierRef ir = (IdentifierRef) cfe;
			IdentifiableElement id = ir.getId();
			if (id instanceof TVariable) {
				TVariable tvar = (TVariable) id;
				id = (VariableDeclaration) tvar.getAstElement();
			}
			CVLocationDataEntry userData = (CVLocationDataEntry) currentBranch.getActivationLocation(id);
			if (userData != null) {
				userData.idRefs.add(ir);
			}
		}

	}

}
