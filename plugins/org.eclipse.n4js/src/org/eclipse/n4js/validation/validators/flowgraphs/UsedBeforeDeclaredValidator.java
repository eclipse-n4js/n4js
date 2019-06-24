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
package org.eclipse.n4js.validation.validators.flowgraphs;

import java.util.List;

import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.UsedBeforeDeclaredAnalyser;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.validators.N4JSFlowgraphValidator;

/**
 * This validator validates all variables that are used before their declaration.
 */
public class UsedBeforeDeclaredValidator implements FlowValidator {
	final private UsedBeforeDeclaredAnalyser ubda;

	/** Constructor. */
	public UsedBeforeDeclaredValidator(UsedBeforeDeclaredAnalyser usedBeforeDeclaredAnalyser) {
		this.ubda = usedBeforeDeclaredAnalyser;
	}

	@Override
	public FlowAnalyser getFlowAnalyser() {
		return ubda;
	}

	@Override
	public void checkResults(N4JSFlowgraphValidator fVali) {
		internalCheckUsedBeforeDeclared(fVali);
	}

	private void internalCheckUsedBeforeDeclared(N4JSFlowgraphValidator fVali) {
		List<IdentifierRef> usedBeforeDeclared = ubda.getUsedButNotDeclaredIdentifierRefs();

		for (IdentifierRef idRef : usedBeforeDeclared) {
			String varName = idRef.getId().getName();
			String msg = IssueCodes.getMessageForCFG_USED_BEFORE_DECLARED(varName);
			fVali.addIssue(msg, idRef, IssueCodes.CFG_USED_BEFORE_DECLARED);
		}
	}

}
