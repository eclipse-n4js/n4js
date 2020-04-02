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
package org.eclipse.n4js.ide.editor.contentassist.imports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ide.editor.contentassist.imports.ReferenceResolutionFinder.IResolutionAcceptor;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.inject.Inject;

/**
 * Provides some high-level convenience methods on top of {@link ReferenceResolutionFinder}.
 */
public class ReferenceResolutionHelper {

	private static final EReference identifierRef_id = N4JSPackage.eINSTANCE.getIdentifierRef_Id();
	private static final EReference parameterizedTypeRef_declaredType = TypeRefsPackage.eINSTANCE
			.getParameterizedTypeRef_DeclaredType();

	@Inject
	private ReferenceResolutionFinder referenceResolutionFinder;

	/**
	 * Searches the entire AST for unresolved references and searches possible resolutions for each. Returns the
	 * resolutions of those unresolved references that are unambiguous, i.e. that have only a single possible
	 * resolution.
	 *
	 * @see ReferenceResolutionFinder#findResolutions(ReferenceDescriptor, boolean, boolean, Predicate, Predicate,
	 *      IResolutionAcceptor)
	 */
	public List<ReferenceResolution> findResolutionsForAllUnresolvedReferences(Script script,
			CancelIndicator cancelIndicator) {

		triggerProxyResolution(script, cancelIndicator);

		List<ReferenceResolution> result = new ArrayList<>();
		Set<String> donePrefixes = new HashSet<>();
		Iterator<EObject> iter = script.eAllContents();
		while (iter.hasNext()) {
			EObject curr = iter.next();
			ReferenceDescriptor reference = getUnresolvedReferenceForASTNode(curr);
			if (reference != null && donePrefixes.add(reference.prefix)) {
				ResolutionAcceptor acceptor = new ResolutionAcceptor(true, cancelIndicator);
				referenceResolutionFinder.findResolutions(reference, true, true, Predicates.alwaysFalse(),
						Predicates.alwaysTrue(), acceptor);
				List<ReferenceResolution> resolutions = acceptor.getResolutions();
				if (!resolutions.isEmpty()) {
					result.add(resolutions.get(0));
				}
			}
		}
		return result;
	}

	/**
	 * Iff the given AST node contains an unresolved reference, this method returns all possible resolutions of this
	 * reference. Returns an empty list if the given AST node does not contain an unresolved reference or if no
	 * resolutions are found.
	 *
	 * @param astNode
	 *            the AST node possibly containing an unresolved reference.
	 * @param onlyAcceptSingleProposal
	 *            iff <code>true</code>, this method also returns an empty list if there are more than a single
	 *            resolution, i.e. if the reference is ambiguous.
	 * @return valid resolutions for the unresolved reference. May be empty but never <code>null</code>.
	 */
	public List<ReferenceResolution> findResolutionsForUnresolvedReference(EObject astNode,
			boolean onlyAcceptSingleProposal, CancelIndicator cancelIndicator) {

		triggerProxyResolution(astNode, cancelIndicator);
		ReferenceDescriptor reference = getUnresolvedReferenceForASTNode(astNode);
		if (reference == null) {
			return Collections.emptyList();
		}
		ResolutionAcceptor acceptor = new ResolutionAcceptor(onlyAcceptSingleProposal, cancelIndicator);
		referenceResolutionFinder.findResolutions(reference, true, true, Predicates.alwaysFalse(),
				Predicates.alwaysTrue(), acceptor);
		return acceptor.getResolutions();
	}

	private void triggerProxyResolution(EObject eObject, CancelIndicator cancelIndicator) {
		Resource resource = eObject.eResource();
		if (resource instanceof LazyLinkingResource) {
			((LazyLinkingResource) resource).resolveLazyCrossReferences(cancelIndicator);
		}
	}

	/** IMPORTANT: this method assumes proxy resolution has taken place in containing resource! */
	private ReferenceDescriptor getUnresolvedReferenceForASTNode(EObject astNode) {
		EReference eReference;
		String prefix;
		if (astNode instanceof IdentifierRef) {
			eReference = identifierRef_id;
			prefix = ((IdentifierRef) astNode).getIdAsText();
		} else if (astNode instanceof ParameterizedTypeRef) {
			eReference = parameterizedTypeRef_declaredType;
			prefix = ((ParameterizedTypeRef) astNode).getDeclaredTypeAsText();
		} else {
			return null; // unsupported kind of AST node
		}

		Object targetObj = astNode.eGet(eReference, false);
		if (!(targetObj instanceof EObject)) {
			return null; // unsupported kind of reference (e.g. many-valued)
		}
		if (!((EObject) targetObj).eIsProxy()) {
			return null; // reference isn't unresolvable
		}

		INode currentNode = NodeModelUtils.findActualNodeFor(astNode);

		return new ReferenceDescriptor(prefix, astNode, eReference, currentNode);
	}

	private static final class ResolutionAcceptor implements IResolutionAcceptor {

		private final boolean onlyAcceptSingleProposal;
		private final CancelIndicator cancelIndicator;

		private final List<ReferenceResolution> resolutions = new ArrayList<>();
		private boolean valid = true;

		private ResolutionAcceptor(boolean onlyAcceptSingleProposal, CancelIndicator cancelIndicator) {
			this.onlyAcceptSingleProposal = onlyAcceptSingleProposal;
			this.cancelIndicator = cancelIndicator;
		}

		@Override
		public void accept(ReferenceResolution newEntry) {
			if (onlyAcceptSingleProposal && !resolutions.isEmpty()) {
				valid = false;
			}
			resolutions.add(newEntry);
		}

		public List<ReferenceResolution> getResolutions() {
			if (!valid || cancelIndicator.isCanceled()) {
				return Collections.emptyList();
			}
			return resolutions;
		}

		@Override
		public boolean canAcceptMoreProposals() {
			return valid && !cancelIndicator.isCanceled();
		}
	}
}
