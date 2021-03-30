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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.NullDereferenceAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.NullDereferenceResult;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardAssertion;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardType;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.utils.FindReferenceHelper;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.validators.N4JSFlowgraphValidator;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.EcoreUtil2;

/**
 * This validator validates all dereference of null or undefined.
 */
public class NullUndefinedValidator implements FlowValidator {
	final private NullDereferenceAnalyser nda;
	final private FindReferenceHelper findReferenceHelper;
	final private WorkspaceAccess workspaceAccess;

	/** Constructor */
	public NullUndefinedValidator(NullDereferenceAnalyser nullDereferenceAnalyser, WorkspaceAccess workspaceAccess,
			FindReferenceHelper findReferenceHelper) {

		this.findReferenceHelper = findReferenceHelper;
		this.workspaceAccess = workspaceAccess;
		this.nda = nullDereferenceAnalyser;
	}

	@Override
	public FlowAnalyser getFlowAnalyser() {
		return nda;
	}

	@Override
	public void checkResults(N4JSFlowgraphValidator fVali) {
		internalCheckNullDereference(fVali);
	}

	private void internalCheckNullDereference(N4JSFlowgraphValidator fVali) {
		Iterable<NullDereferenceResult> nullDerefs = nda.getNullDereferences();
		for (NullDereferenceResult ndr : nullDerefs) {
			String varName = ndr.checkedSymbol.getName();

			boolean isLeakingToClosure = isLeakingToClosure(ndr);
			boolean isInTestFolder = isInTestFolder(ndr.checkedSymbol.getASTLocation());
			if (isInTestFolder && isLeakingToClosure) {
				continue; // ignore these warnings in test related source
			}

			String isOrMaybe = getAssertionString(ndr, isLeakingToClosure);
			String nullOrUndefined = getNullOrUndefinedString(ndr);
			String reason = getReason(ndr);
			String msg = IssueCodes.getMessageForDFG_NULL_DEREFERENCE(varName, isOrMaybe, nullOrUndefined, reason);
			fVali.addIssue(msg, ndr.cfe, IssueCodes.DFG_NULL_DEREFERENCE); // deactivated during tests
		}
	}

	private String getAssertionString(NullDereferenceResult ndr, boolean isLeakingToClosure) {
		if (ndr.assertion == GuardAssertion.AlwaysHolds && !isLeakingToClosure) {
			return "is";
		}
		return "may be";
	}

	private String getNullOrUndefinedString(NullDereferenceResult ndr) {
		String problemType = "";
		for (GuardType guardType : ndr.types) {
			problemType += !problemType.isEmpty() ? " or " : "";
			switch (guardType) {
			case IsNull:
				problemType += "null";
				break;
			case IsUndefined:
				problemType += "undefined";
				break;
			default:
				problemType += "unknown";
			}
		}
		return problemType;
	}

	private String getReason(NullDereferenceResult ndr) {
		if (ndr.failedAlias != null && !ndr.checkedSymbol.is(ndr.failedAlias)) {
			return " due to previous variable " + ndr.failedAlias.getName();
		}
		return "";
	}

	private boolean isLeakingToClosure(NullDereferenceResult ndr) {
		EObject decl = ndr.checkedSymbol.getDeclaration();
		List<EObject> refs = findReferenceHelper.findReferencesInResource(decl, decl.eResource());
		List<EObject> writeRefs = new LinkedList<>();
		writeRefs.add(ndr.cfe);

		for (EObject ref : refs) {
			if (N4JSASTUtils.isWriteAccess(ref)) {
				writeRefs.add(ref);
			}
		}

		Iterator<EObject> writeRefsIter = writeRefs.iterator();
		if (!writeRefsIter.hasNext()) {
			return false;
		}

		EObject ref = writeRefsIter.next();
		EObject parentScope = getParentScope(ref);
		while (writeRefsIter.hasNext()) {
			ref = writeRefsIter.next();
			if (parentScope != getParentScope(ref)) {
				return true;
			}
		}

		return false;
	}

	private EObject getParentScope(EObject eobj) {
		Iterable<EObject> containers = EcoreUtil2.getAllContainers(eobj);
		for (EObject container : containers) {
			boolean isScopeParent = false;
			isScopeParent |= container instanceof FunctionDefinition;
			isScopeParent |= container instanceof FunctionExpression;
			if (isScopeParent) {
				return container;
			}
		}
		return null;
	}

	private boolean isInTestFolder(EObject eobj) {
		Resource resource = eobj.eResource();
		URI location = resource.getURI();
		final N4JSSourceFolderSnapshot c = workspaceAccess.findSourceFolderContaining(resource, location);
		return c != null && c.isTest();
	}

}
