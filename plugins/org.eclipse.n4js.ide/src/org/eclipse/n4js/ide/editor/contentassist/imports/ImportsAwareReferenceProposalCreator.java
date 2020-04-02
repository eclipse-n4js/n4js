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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.ide.editor.contentassist.N4JSIdeContentProposalProvider.N4JSCandidateFilter;
import org.eclipse.n4js.ide.editor.contentassist.imports.ReferenceResolutionFinder.IResolutionAcceptor;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IProposalConflictHelper;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalPriorities;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

/**
 * Creates proposals for content assist and also adds imports of the proposed element if necessary.
 */
public class ImportsAwareReferenceProposalCreator {

	@Inject
	private ReferenceResolutionFinder referenceResolutionFinder;

	@Inject
	private IProposalConflictHelper conflictHelper;

	@Inject
	private IdeContentProposalPriorities proposalPriorities;

	/**
	 * Retrieves possible reference targets from scope, including erroneous solutions (e.g., not visible targets). This
	 * list is further filtered here. This is a general pattern: Do not change or modify scoping for special content
	 * assist requirements, instead filter here.
	 *
	 * @param filter
	 *            by default an instance of {@link N4JSCandidateFilter} will be provided here.
	 */
	public void lookupCrossReference(
			EObject model,
			EReference reference,
			ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor,
			Predicate<IEObjectDescription> filter) {

		if (model == null) {
			return;
		}

		ReferenceDescriptor referenceDesc = new ReferenceDescriptor(context.getPrefix(), model, reference,
				context.getCurrentNode());
		Predicate<String> conflictChecker = (proposalToCheck) -> conflictHelper.existsConflict(proposalToCheck,
				context);

		referenceResolutionFinder.findResolutions(referenceDesc, false, false, conflictChecker, filter,
				new ResolutionToContentProposalAcceptor(acceptor, context));
	}

	/** An {@link IResolutionAcceptor} that forwards to a given {@link IIdeContentProposalAcceptor}. */
	private class ResolutionToContentProposalAcceptor implements IResolutionAcceptor {

		private final IIdeContentProposalAcceptor contentProposalAcceptor;
		private final ContentAssistContext context;

		ResolutionToContentProposalAcceptor(IIdeContentProposalAcceptor contentProposalAcceptor,
				ContentAssistContext context) {
			this.contentProposalAcceptor = contentProposalAcceptor;
			this.context = context;
		}

		@Override
		public void accept(ReferenceResolution resolution) {
			ContentAssistEntry entry = convertResolutionToContentAssistEntry(resolution, context);
			int priority = proposalPriorities.getCrossRefPriority(resolution.referencedElement, entry);
			contentProposalAcceptor.accept(entry, priority);
		}

		@Override
		public boolean canAcceptMoreProposals() {
			return contentProposalAcceptor.canAcceptMoreProposals();
		}
	}

	private static ContentAssistEntry convertResolutionToContentAssistEntry(ReferenceResolution resolution,
			ContentAssistContext context) {
		ContentAssistEntry cae = new ContentAssistEntry();
		cae.setPrefix(context.getPrefix());
		cae.setProposal(resolution.proposal);
		cae.setLabel(resolution.label);
		cae.setDescription(resolution.description);
		cae.getTextReplacements().addAll(resolution.textReplacements);
		cae.setSource(resolution.referencedElement);
		cae.setKind(getKind(resolution));
		return cae;
	}

	private static String getKind(ReferenceResolution resolution) {
		EClass eClass = resolution.referencedElement.getEClass();
		if (TypesPackage.eINSTANCE.getTClass() == eClass) {
			return ContentAssistEntry.KIND_CLASS;
		}
		if (TypesPackage.eINSTANCE.getTInterface() == eClass) {
			return ContentAssistEntry.KIND_INTERFACE;
		}
		if (TypesPackage.eINSTANCE.getTField() == eClass) {
			return ContentAssistEntry.KIND_FIELD;
		}
		if (TypesPackage.eINSTANCE.getTEnum() == eClass) {
			return ContentAssistEntry.KIND_ENUM;
		}
		if (TypesPackage.eINSTANCE.getTFunction() == eClass) {
			return ContentAssistEntry.KIND_FUNCTION;
		}
		if (TypesPackage.eINSTANCE.getTVariable() == eClass) {
			return ContentAssistEntry.KIND_VARIABLE;
		}
		if (N4JSPackage.eINSTANCE.getVariableDeclaration() == eClass) {
			return ContentAssistEntry.KIND_VARIABLE;
		}
		if (TypesPackage.eINSTANCE.getModuleNamespaceVirtualType() == eClass) {
			return ContentAssistEntry.KIND_COLOR;
		}
		return ContentAssistEntry.KIND_TEXT;
	}
}
