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

import static org.eclipse.n4js.utils.N4JSLanguageUtils.lastSegmentOrDefaultHost;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.ide.editor.contentassist.N4JSIdeContentProposalProvider.N4JSCandidateFilter;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportRewriter.ImportChanges;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.scoping.IContentAssistScopeProvider;
import org.eclipse.n4js.scoping.imports.PlainAccessOfAliasedImportDescription;
import org.eclipse.n4js.scoping.imports.PlainAccessOfNamespacedImportDescription;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalCreator;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalPriorities;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.util.Arrays;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

/**
 */
@SuppressWarnings("unused")
public class ImportsAwareReferenceProposalCreator {

	@Inject
	private IScopeProvider scopeProvider;

	private IValueConverter<String> valueConverter;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ImportRewriter importRewriter;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private IdeContentProposalPriorities proposalPriorities;

	private static final EReference[] referencesSupportingImportedElements = {
			N4JSPackage.Literals.IDENTIFIER_REF__ID,
			TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE
	};

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
			IIdeContentProposalAcceptor acceptor,
			Predicate<IEObjectDescription> filter,
			IdeContentProposalCreator proposalFactory) {

		if (model != null) {
			final IContentAssistScopeProvider contentAssistScopeProvider = (IContentAssistScopeProvider) scopeProvider;
			final IScope scope = contentAssistScopeProvider.getScopeForContentAssist(model, reference);
			// iterate over candidates, filter them, and create ICompletionProposals for them
			final Iterable<IEObjectDescription> candidates = scope.getAllElements();
			final Set<URI> candidateURIs = new HashSet<>(); // note: shadowing for #getAllElements does not work

			for (IEObjectDescription candidate : candidates) {
				if (!acceptor.canAcceptMoreProposals()) {
					return;
				}

				if (filter.apply(candidate)) {
					final QualifiedName qfn = candidate.getQualifiedName();
					final int qfnSegmentCount = qfn.getSegmentCount();
					final String tmodule = (qfnSegmentCount >= 2) ? qfn.getSegment(qfnSegmentCount - 2) : null;

					final ContentAssistEntry proposal = getProposal(candidate,
							model,
							scope,
							reference,
							context,
							filter,
							proposalFactory);

					if (proposal != null && candidateURIs.add(candidate.getEObjectURI())) {
						acceptor.accept(proposal, proposalPriorities.getCrossRefPriority(candidate, proposal));
					}
				}
			}
		}
	}

	/**
	 * Creates initial proposal adjusted for the N4JS imports. Then passes that proposal to the provided delegate
	 * proposal factory. Obtained ICompletionProposal is configured with a FQNImporter as custom text. applier.
	 *
	 * @param candidate
	 *            for which proposal is created
	 * @param delegateProposalFactory
	 *            delegate proposal factory
	 * @return code completion proposal
	 */
	private ContentAssistEntry getProposal(IEObjectDescription candidate, EObject model,
			IScope scope,
			EReference reference,
			ContentAssistContext context,
			Predicate<IEObjectDescription> filter,
			IdeContentProposalCreator delegateProposalFactory) {

		Resource resource = model.eResource();
		IEObjectDescription inputToUse = getAliasedDescription(candidate, reference, context);

		QualifiedName name = inputToUse.getName();
		String shortQName = lastSegmentOrDefaultHost(name);

		ContentAssistEntry result = delegateProposalFactory.createProposal(name.toString(), context);

		if (result != null) {
			try {
				int version = N4JSResourceDescriptionStrategy.getVersion(inputToUse);
				QualifiedName qName = inputToUse.getQualifiedName();

				if (qName.equals(name)) {
					EObject eObj = inputToUse.getEObjectOrProxy(); // performance issue! TODO: remove it
					QualifiedName qnOfEObject = qualifiedNameProvider.getFullyQualifiedName(eObj);
					qName = qnOfEObject != null ? qnOfEObject : qName;
				}
				String description = getDescription(qName, name, version);
				String label = getLabel(qName, name, version);
				String kind = getKind(candidate);
				result.setLabel(label);
				result.setDescription(description);
				result.setKind(kind);
				result.setSource(candidate.getEObjectURI());

				Collection<ReplaceRegion> regions = getImportChanges(name.toString(), resource, scope, candidate,
						filter);
				if (regions != null && !regions.isEmpty()) {
					result.getTextReplacements().addAll(regions);
				}
			} catch (ValueConverterException e) {
				// text does not match the concrete syntax
				result = null;
			}
		}

		return result;
	}

	String getDescription(QualifiedName qualifiedName, QualifiedName shortName, int version) {
		String result = "";
		if (qualifiedName.getSegmentCount() > 1) {
			String lastSegment = qualifiedName.getLastSegment();

			String shortNameString = shortName.toString();
			String typeVersion = (version == 0) ? "" : N4IDLGlobals.VERSION_SEPARATOR + String.valueOf(version);
			if (shortNameString.endsWith(lastSegment)) {
				result = qualifiedNameConverter.toString(qualifiedName.skipLast(1));
			} else {
				result = "alias for " + lastSegment;
			}
			result += typeVersion;
		}
		return result;
	}

	String getLabel(QualifiedName qualifiedName, QualifiedName shortName, int version) {
		String shortNameString = shortName.toString();
		String result = shortNameString;
		if (qualifiedName.getSegmentCount() > 1) {
			String lastSegment = qualifiedName.getLastSegment();

			if (shortNameString.endsWith(lastSegment)) {
				String typeVersion = (version == 0) ? "" : N4IDLGlobals.VERSION_SEPARATOR + String.valueOf(version);
				result = lastSegment + typeVersion;
			} else {
				result = shortNameString;
			}
		}
		return result;
	}

	private String getKind(IEObjectDescription candidate) {
		EClass eClass = candidate.getEClass();
		String kind = ContentAssistEntry.KIND_TEXT;
		if (TypesPackage.eINSTANCE.getTClass() == eClass) {
			kind = ContentAssistEntry.KIND_CLASS;
		}
		if (TypesPackage.eINSTANCE.getTInterface() == eClass) {
			kind = ContentAssistEntry.KIND_INTERFACE;
		}
		if (TypesPackage.eINSTANCE.getTEnum() == eClass) {
			kind = ContentAssistEntry.KIND_ENUM;
		}
		if (TypesPackage.eINSTANCE.getTFunction() == eClass) {
			kind = ContentAssistEntry.KIND_FUNCTION;
		}
		if (TypesPackage.eINSTANCE.getTVariable() == eClass) {
			kind = ContentAssistEntry.KIND_VARIABLE;
		}
		return kind;
	}

	/**
	 * Creates proposal taking semantics of the N4JS imports into account.
	 *
	 * @param candidate
	 *            the original input for which we create proposal
	 * @param reference
	 *            the reference
	 * @param context
	 *            the context
	 * @return candidate proposal adjusted to the N4JS imports
	 */
	private IEObjectDescription getAliasedDescription(IEObjectDescription candidate, EReference reference,
			ContentAssistContext context) {

		// Content assist at a location where only simple names are allowed:
		// We found a qualified name and we'd need an import to be allowed to use
		// that name. Consider only the simple name of the element from the index
		// and make sure that the import is inserted as soon as the proposal is applied
		QualifiedName inputQN = candidate.getName();
		int inputNameSegmentCount = inputQN.getSegmentCount();
		if (inputNameSegmentCount > 1
				&& Arrays.contains(referencesSupportingImportedElements, reference))
			return new AliasedEObjectDescription(QualifiedName.create(inputQN.getLastSegment()), candidate);

		// filter out non-importable things:
		// globally provided things should never be imported:
		if (inputNameSegmentCount == 2 && N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT
				.equals(inputQN.getFirstSegment()))
			return new AliasedEObjectDescription(QualifiedName.create(inputQN.getLastSegment()), candidate);

		// special handling for default imports:
		if (inputQN.getLastSegment().equals(N4JSLanguageConstants.EXPORT_DEFAULT_NAME)) {
			if (TExportableElement.class.isAssignableFrom(candidate.getEClass().getInstanceClass())) {
				if (N4JSResourceDescriptionStrategy.getExportDefault(candidate)) {
					return new AliasedEObjectDescription(inputQN, candidate);
				}
			}
			// not accessed via namespace
			QualifiedName nameNoDefault = inputQN.skipLast(1);
			QualifiedName moduleName = nameNoDefault.getSegmentCount() > 1
					? QualifiedName.create(nameNoDefault.getLastSegment())
					: nameNoDefault;
			return new AliasedEObjectDescription(moduleName, candidate);
		}
		// no special handling, return original input
		return candidate;
	}

	private Collection<ReplaceRegion> getImportChanges(String syntacticReplacementString, Resource resource,
			IScope scope, IEObjectDescription candidate, Predicate<IEObjectDescription> filter) {

		// does it even happen? check logs if first and/or second check passes
		String actualSyntacticReplacementString = getActualReplacementString(syntacticReplacementString, scope,
				resource, filter);
		// there is an import statement - apply computed replacementString
		if (!syntacticReplacementString.equals(actualSyntacticReplacementString)) {
			QualifiedName shortQualifiedName = applyValueConverter(actualSyntacticReplacementString);
			if (shortQualifiedName.getSegmentCount() == 1) {
				return Collections.emptyList();
			}
		}

		QualifiedName qualifiedName = getCandidateName(candidate);
		QualifiedName originalQualifiedName = candidate.getQualifiedName();

		if (qualifiedName == null) {
			return Collections.emptyList();
		}

		// we could create an import statement if there is no conflict
		if (qualifiedName.getSegmentCount() == 1) {
			// type name is a simple name - no need to hassle with imports
			return Collections.emptyList();
		}

		// Globally available elements should not generate imports
		if (qualifiedName.getSegmentCount() == 2
				&& N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT.equals(qualifiedName.getFirstSegment())) {
			// type name is a simple name from global Namespace - no need to hassle with imports
			return Collections.emptyList();
		}

		String alias = null;
		String shortQName = lastSegmentOrDefaultHost(originalQualifiedName);
		IEObjectDescription descriptionFullQN = scope.getSingleElement(QualifiedName.create(shortQName));

		// element is already imported via namespace
		if (descriptionFullQN instanceof PlainAccessOfNamespacedImportDescription) {
			return Collections.emptyList();
		}

		// element is already imported via an alias
		if (descriptionFullQN instanceof PlainAccessOfAliasedImportDescription) {
			return Collections.emptyList();
		}

		if (descriptionFullQN != null) {
			// accessing default export via already imported namespace
			if (descriptionFullQN.getEObjectOrProxy() instanceof ModuleNamespaceVirtualType) {
				return Collections.emptyList();
			}

			// the simple name is already reachable, i.e. already in use - another import is present
			// try to use an alias
			alias = "Alias" + UtilN4.toUpperCaseFirst(shortQName);
		}

		ImportChanges importChanges = importRewriter.create("\n", resource);
		importChanges.addImport(qualifiedName, alias);
		Collection<ReplaceRegion> regions = importChanges.toReplaceRegions();
		return regions;
	}

	/** In case of main module, adjust the qualified name, e.g. index.Element -> react.Element */
	private QualifiedName getCandidateName(IEObjectDescription candidate) {
		QualifiedName qfn = candidate.getQualifiedName();
		int qfnSegmentCount = qfn.getSegmentCount();
		String tmodule = (qfnSegmentCount >= 2) ? qfn.getSegment(qfnSegmentCount - 2) : null;

		QualifiedName candidateName;
		IN4JSProject project = n4jsCore.findProject(candidate.getEObjectURI()).orNull();
		if (project != null && tmodule != null && tmodule.equals(project.getMainModule())) {
			N4JSProjectName projectName = project.getProjectName();
			N4JSProjectName definesPackage = project.getDefinesPackageName();
			if (definesPackage != null) {
				projectName = definesPackage;
			}
			String lastSegmentOfQFN = candidate.getQualifiedName().getLastSegment().toString();
			candidateName = QualifiedName.create(projectName.getRawName(), lastSegmentOfQFN);
		} else {
			candidateName = candidate.getQualifiedName();
		}
		return candidateName;
	}

	/**
	 * Convert the the given qualifiedName to a valid syntax in the n4js file.
	 */
	private String applyValueConverter(QualifiedName qualifiedName) {
		String result = qualifiedNameConverter.toString(qualifiedName);
		result = valueConverter.toString(result);
		return result;
	}

	/**
	 * Converts the concrete syntax to a qualified name.
	 */
	private QualifiedName applyValueConverter(String concreteSyntax) {
		final String semanticReplacementString = valueConverter.toValue(concreteSyntax, null);
		final QualifiedName qualifiedName = qualifiedNameConverter.toQualifiedName(semanticReplacementString);
		return qualifiedName;
	}

	/**
	 * Return the to-be-inserted string if an existing import is present.
	 */
	public String getActualReplacementString(String syntacticReplacementString, IScope scope, Resource resource,
			Predicate<IEObjectDescription> filter) {

		if (scope != null) {
			final QualifiedName qualifiedName = applyValueConverter(syntacticReplacementString);
			if (qualifiedName.getSegmentCount() == 1) {
				return syntacticReplacementString;
			}
			final IEObjectDescription element = scope.getSingleElement(qualifiedName);
			if (element != null) {
				EObject resolved = EcoreUtil.resolve(element.getEObjectOrProxy(), resource);
				if (!resolved.eIsProxy()) {
					IEObjectDescription description = findApplicableDescription(resolved, qualifiedName, true, scope,
							filter);
					if (description != null) {
						String multisegmentProposal = applyValueConverter(description.getName());
						return multisegmentProposal;
					}
				}
			}
		}
		return syntacticReplacementString;
	}

	/**
	 * Search for a description in the scope that points to the given element. A valid description is considered to
	 * fulfill the {@code filter} and the last segment of the given qualifiedName has to match
	 * {@code simpleNameMatch == true} or has to be different from the description's last name segment. If nothing can
	 * be found, {@code null} is returned.
	 */
	private IEObjectDescription findApplicableDescription(EObject objectOrProxy, QualifiedName qualifiedName,
			boolean simpleNameMatch, IScope scope, Predicate<IEObjectDescription> filter) {

		Iterable<IEObjectDescription> lookupElements = scope.getElements(objectOrProxy);
		for (IEObjectDescription lookupElement : lookupElements) {
			if (filter.apply(lookupElement)) {
				if (simpleNameMatch == qualifiedName.getLastSegment()
						.equals(lookupElement.getName().getLastSegment())) {
					return lookupElement;
				}
			}
		}
		return null;
	}
}
