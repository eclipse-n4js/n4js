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
package org.eclipse.n4js.ui.proposals.imports;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.inject.Inject;

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.scoping.IContentAssistScopeProvider;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ui.contentassist.N4JSCandidateFilter;
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants;

/**
 * Custom proposal creator that is used to query a scope and produce completion proposals for the elements in that
 * scope.
 *
 * The proposals that are produced by this class will maintain the import section when a proposal is selected. This is
 * done by the {@link FQNImporter} which is set as the
 * {@link ConfigurableCompletionProposal#setTextApplier(org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal.IReplacementTextApplier)
 * text applier * }.
 *
 * @see org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider.ReferenceProposalCreator
 * @see org.eclipse.n4js.ui.contentassist.N4JSProposalProvider#lookupCrossReference
 */
public class ImportsAwareReferenceProposalCreator {

	@Inject
	private IScopeProvider scopeProvider;

	private IValueConverter<String> valueConverter;

	@Inject
	private FQNImporter.Factory fqnImporterFactory;

	@Inject
	private void setValueConverter(IValueConverterService service, N4JSGrammarAccess grammarAccess) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		IValueConverter<String> converter = (IValueConverter) ((IValueConverterService.Introspectable) service)
				.getConverter(grammarAccess
						.getTypeReferenceNameRule()
						.getName());
		this.valueConverter = converter;
	}

	/**
	 * Retrieves possible reference targets from scope, including erroneous solutions (e.g., not visible targets). This
	 * list is further filtered here. This is a general pattern: Do not change or modify scoping for special content
	 * assist requirements, instead filter here.
	 *
	 * @param proposalFactory
	 *            usually this will be an instance of
	 *            {@link AbstractJavaBasedContentProposalProvider.DefaultProposalCreator DefaultProposalCreator}.
	 * @param filter
	 *            by default an instance of {@link N4JSCandidateFilter} will be provided here.
	 */
	@SuppressWarnings("javadoc")
	public void lookupCrossReference(
			EObject model,
			EReference reference,
			ContentAssistContext context,
			ICompletionProposalAcceptor acceptor,
			Predicate<IEObjectDescription> filter,
			Function<IEObjectDescription, ICompletionProposal> proposalFactory) {
		if (model != null) {
			final IScope scope = ((IContentAssistScopeProvider) scopeProvider).getScopeForContentAssist(model,
					reference);

			// wrap the given proposalFactory to configure the created ICompletionProposal
			// with a FQNImporter as custom text applier
			final Function<IEObjectDescription, ICompletionProposal> wrappedProposalFactory = (input) -> {
				IEObjectDescription inputToUse = input;

				// Content assist at a location where only simple names are allowed:
				// We found a qualified name and we'd need an import to be allowed to use
				// that name. Consider only the simple name of the element from the index
				// and make sure that the import is inserted as soon as the proposal is applied
				QualifiedName inputQN = input.getName();
				int inputNameSegmentCount = inputQN.getSegmentCount();
				if (reference == N4JSPackage.Literals.IDENTIFIER_REF__ID && inputNameSegmentCount > 1) {
					inputToUse = new AliasedEObjectDescription(
							QualifiedName.create(inputQN.getLastSegment()),
							input);
				}
				// filter out non-importable things:
				// globally provided things should never be imported:
				if (inputNameSegmentCount == 2 && N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT
						.equals(inputQN.getFirstSegment())) {
					inputToUse = new AliasedEObjectDescription(
							QualifiedName.create(inputQN.getLastSegment()),
							input);
				} else // special handling for default imports:
				if (inputQN.getLastSegment().equals(N4JSLanguageConstants.EXPORT_DEFAULT_NAME)) {
					QualifiedName nameNoDefault = inputQN.skipLast(1);
					QualifiedName moduleName = nameNoDefault.getSegmentCount() > 1
							? QualifiedName.create(nameNoDefault.getLastSegment()) : nameNoDefault;
					inputToUse = new AliasedEObjectDescription(
							moduleName,
							input);
				}

				final ICompletionProposal result = proposalFactory.apply(inputToUse);
				if (result instanceof ConfigurableCompletionProposal) {
					final FQNImporter importer = fqnImporterFactory.create(
							model.eResource(),
							scope,
							valueConverter,
							filter,
							context.getViewer());
					((ConfigurableCompletionProposal) result).setTextApplier(importer);
				}
				return result;
			};

			// iterate over candidates, filter them, and create ICompletionProposals for them
			final Iterable<IEObjectDescription> candidates = scope.getAllElements();
			// don't use candidates.forEach since we want an early exit
			for (IEObjectDescription candidate : candidates) {
				if (!acceptor.canAcceptMoreProposals())
					return;
				if (filter.apply(candidate)) {
					final ICompletionProposal proposal = wrappedProposalFactory.apply(candidate);
					if (proposal instanceof ConfigurableCompletionProposal
							&& candidate.getName().getSegmentCount() > 1) {
						((ConfigurableCompletionProposal) proposal).setAdditionalData(FQNImporter.KEY_QUALIFIED_NAME,
								candidate.getQualifiedName());
					}
					acceptor.accept(proposal);
				}
			}
		}
	}
}
