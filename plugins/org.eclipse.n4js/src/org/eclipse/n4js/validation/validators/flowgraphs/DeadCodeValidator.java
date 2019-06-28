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

import java.util.Set;

import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.DeadCodeAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.DeadCodeAnalyser.DeadCodeRegion;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;
import org.eclipse.n4js.validation.validators.N4JSFlowgraphValidator;

import com.google.common.base.Strings;

/**
 * This validator validates dead code.
 */
public class DeadCodeValidator implements FlowValidator {
	final private DeadCodeAnalyser dca;
	final private N4JSElementKeywordProvider keywordProvider;

	/** Constructor. */
	public DeadCodeValidator(DeadCodeAnalyser deadCodeAnalyser, N4JSElementKeywordProvider keywordProvider) {
		this.keywordProvider = keywordProvider;
		dca = deadCodeAnalyser;
	}

	@Override
	public FlowAnalyser getFlowAnalyser() {
		return dca;
	}

	@Override
	public void checkResults(N4JSFlowgraphValidator fVali) {
		internalCheckDeadCode(fVali);
	}

	// Req.107
	private void internalCheckDeadCode(N4JSFlowgraphValidator fVali) {
		Set<DeadCodeRegion> deadCodeRegions = dca.getDeadCodeRegions();

		for (DeadCodeRegion dcRegion : deadCodeRegions) {
			String stmtDescription = getStatementDescription(dcRegion);
			String errCode = IssueCodes.FUN_DEAD_CODE;
			String msg = IssueCodes.getMessageForFUN_DEAD_CODE();
			if (stmtDescription != null) {
				msg = IssueCodes.getMessageForFUN_DEAD_CODE_WITH_PREDECESSOR(stmtDescription);
				errCode = IssueCodes.FUN_DEAD_CODE_WITH_PREDECESSOR;
			}
			fVali.addIssue(msg, dcRegion.getContainer(), dcRegion.getOffset(), dcRegion.getLength(), errCode);
		}
	}

	private String getStatementDescription(DeadCodeRegion deadCodeRegion) {
		ControlFlowElement reachablePred = deadCodeRegion.getReachablePredecessor();
		if (reachablePred == null) {
			return null;
		}

		boolean addKeyword = false;
		addKeyword |= reachablePred instanceof ReturnStatement;
		addKeyword |= reachablePred instanceof BreakStatement;
		addKeyword |= reachablePred instanceof ContinueStatement;
		addKeyword |= reachablePred instanceof ThrowStatement;

		if (addKeyword) {
			String keyword = keywordProvider.keyword(reachablePred);
			if (Strings.isNullOrEmpty(keyword)) {
				keyword = reachablePred.eClass().getName();
			}
			return keyword;
		}
		return null;
	}

}
