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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.formatting2.FormattingUserPreferenceHelper;
import org.eclipse.n4js.ide.editor.contentassist.N4JSIdeContentProposalProvider.N4JSCandidateFilter;
import org.eclipse.n4js.ide.server.imports.ImportOrganizer.ImportRef;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportCallExpression;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.scoping.IContentAssistScopeProvider;
import org.eclipse.n4js.scoping.imports.PlainAccessOfAliasedImportDescription;
import org.eclipse.n4js.scoping.imports.PlainAccessOfNamespacedImportDescription;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher;
import org.eclipse.xtext.ide.editor.contentassist.IProposalConflictHelper;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalPriorities;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Creates proposals for content assist and also adds imports of the proposed element if necessary.
 */
public class ImportsAwareReferenceProposalCreator {

	private static final EReference identifierRef_id = N4JSPackage.eINSTANCE.getIdentifierRef_Id();
	private static final EReference parameterizedTypeRef_declaredType = TypeRefsPackage.eINSTANCE
			.getParameterizedTypeRef_DeclaredType();

	@Inject
	private IScopeProvider scopeProvider;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private IValueConverterService valueConverters;

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private IdeContentProposalPriorities proposalPriorities;

	@Inject
	private IPrefixMatcher prefixMatcher;

	@Inject
	private IProposalConflictHelper conflictHelper;

	@Inject
	private ImportRegionHelper importRegionHelper;

	@Inject
	private FormattingUserPreferenceHelper formattingUserPreferenceHelper;

	private static final class ProposalAcceptor implements IIdeContentProposalAcceptor {

		private final boolean onlyAcceptSingleProposal;
		private final CancelIndicator cancelIndicator;

		private final List<ContentAssistEntry> entries = new ArrayList<>();

		private ProposalAcceptor(boolean onlyAcceptSingleProposal, CancelIndicator cancelIndicator) {
			this.onlyAcceptSingleProposal = onlyAcceptSingleProposal;
			this.cancelIndicator = cancelIndicator;
		}

		@Override
		public void accept(ContentAssistEntry newEntry, int priority) {
			entries.add(newEntry);
		}

		public List<ContentAssistEntry> getEntries() {
			if (cancelIndicator.isCanceled()) {
				return Collections.emptyList();
			}
			return entries;
		}

		@Override
		public boolean canAcceptMoreProposals() {
			if (onlyAcceptSingleProposal && !entries.isEmpty()) {
				return false;
			}
			return !cancelIndicator.isCanceled();
		}
	}

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

		lookupSingleCrossReference(
				model, reference,
				context.getPrefix(), context.getCurrentNode(),
				(proposalToCheck) -> conflictHelper.existsConflict(proposalToCheck, context),
				acceptor, filter);
	}

	public List<ContentAssistEntryWithRef> lookupAllUnresolvedCrossReferences(Script script,
			CancelIndicator cancelIndicator) {
		List<ContentAssistEntryWithRef> result = new ArrayList<>();
		Iterator<EObject> iter = script.eAllContents();
		while (iter.hasNext()) {
			EObject curr = iter.next();
			List<ContentAssistEntry> entries = lookupSingleUnresolvedCrossReference(curr, true, cancelIndicator);
			if (entries.size() == 1) {
				ContentAssistEntry entry = entries.get(0);
				if (entry instanceof ContentAssistEntryWithRef) {
					result.add((ContentAssistEntryWithRef) entry);
				}
			}
		}
		return result;
	}

	public List<ContentAssistEntry> lookupSingleUnresolvedCrossReference(
			EObject model,
			boolean onlyAcceptSingleProposal,
			CancelIndicator cancelIndicator) {

		EReference reference;
		String prefix;
		if (model instanceof IdentifierRef) {
			reference = identifierRef_id;
			prefix = ((IdentifierRef) model).getIdAsText();
		} else if (model instanceof ParameterizedTypeRef) {
			reference = parameterizedTypeRef_declaredType;
			prefix = ((ParameterizedTypeRef) model).getDeclaredTypeAsText();
		} else {
			return Collections.emptyList(); // unsupported kind of AST node
		}

		return lookupSingleUnresolvedCrossReference(model, reference, prefix, onlyAcceptSingleProposal,
				cancelIndicator);
	}

	private List<ContentAssistEntry> lookupSingleUnresolvedCrossReference(
			EObject model,
			EReference reference,
			String prefix,
			boolean onlyAcceptSingleProposal,
			CancelIndicator cancelIndicator) {

		Object targetObj = model.eGet(reference, false);
		if (!(targetObj instanceof EObject)) {
			return Collections.emptyList(); // unsupported kind of reference (e.g. many-valued)
		}
		if (!((EObject) targetObj).eIsProxy()) {
			return Collections.emptyList(); // reference isn't unresolved
		}

		INode currentNode = NodeModelUtils.findActualNodeFor(model);
		ProposalAcceptor acceptor = new ProposalAcceptor(onlyAcceptSingleProposal, cancelIndicator);
		lookupSingleCrossReference(model, reference, prefix, currentNode, Predicates.alwaysFalse(), acceptor,
				Predicates.alwaysTrue());
		return acceptor.getEntries();
	}

	private void lookupSingleCrossReference(
			EObject model,
			EReference reference,
			String prefix, INode currentNode, Predicate<String> conflictChecker,
			IIdeContentProposalAcceptor acceptor,
			Predicate<IEObjectDescription> filter) {

		if (model == null) {
			return;
		}

		final IContentAssistScopeProvider contentAssistScopeProvider = (IContentAssistScopeProvider) scopeProvider;
		final IScope scope = contentAssistScopeProvider.getScopeForContentAssist(model, reference);
		// iterate over candidates, filter them, and create ICompletionProposals for them
		final Iterable<IEObjectDescription> candidates = scope.getAllElements();
		final Set<URI> candidateURIs = new HashSet<>(); // note: shadowing for #getAllElements does not work

		for (IEObjectDescription candidate : candidates) {
			if (!acceptor.canAcceptMoreProposals() || !filter.apply(candidate)) {
				return;
			}

			final ContentAssistEntry proposal = getProposal(candidate, model, scope, prefix, currentNode,
					conflictChecker);
			if (proposal != null && candidateURIs.add(candidate.getEObjectURI())) {
				int prio = proposalPriorities.getCrossRefPriority(candidate, proposal);
				acceptor.accept(proposal, prio);
			}
		}
	}

	/**
	 * Creates proposal that can contain an N4JS import.
	 *
	 * @param candidate
	 *            for which proposal is created
	 * @return code completion proposal
	 */
	private ContentAssistEntry getProposal(IEObjectDescription candidate, EObject model, IScope scope,
			String prefix, INode currentNode, Predicate<String> conflictChecker) {

		CAECandidate caec = new CAECandidate(candidate, scope, prefix, currentNode, conflictChecker);

		if (!caec.isValid) {
			return null;
		}

		try {
			ContentAssistEntryWithRef cae = new ContentAssistEntryWithRef();
			int version = N4JSResourceDescriptionStrategy.getVersion(candidate);

			String proposal = getProposal(caec);
			String label = getLabel(caec, version);
			String description = getDescription(caec);
			String kind = getKind(caec);

			cae.setProposal(proposal);
			cae.setPrefix(prefix);
			cae.setLabel(label);
			cae.setDescription(description);
			cae.setKind(kind);
			cae.setSource(candidate);

			addImportIfNecessary(caec, model.eResource(), cae);

			return cae;

		} catch (ValueConverterException e) {
			// text does not match the concrete syntax
		}

		return null;
	}

	private String getProposal(CAECandidate caec) {
		switch (caec.accessType) {
		case alias:
			return caec.aliasName;
		case namespace:
			return caec.namespaceName.toString();
		default:
			// noop
		}
		if (caec.newImportHasAlias()) {
			return caec.addedImportNameAndAlias.alias;
		}
		return caec.shortName;
	}

	private String getLabel(CAECandidate caec, int version) {
		String typeVersion = (version == 0) ? "" : N4IDLGlobals.VERSION_SEPARATOR + String.valueOf(version);
		if (caec.isAlias()) {
			return caec.aliasName + typeVersion;
		}
		if (caec.isNamespace()) {
			return caec.namespaceName + typeVersion;
		}
		if (caec.newImportHasAlias()) {
			return caec.shortName + typeVersion;
		}

		return caec.shortName + typeVersion;
	}

	private String getDescription(CAECandidate caec) {
		if (caec.isAlias()) {
			return "alias for " + qualifiedNameConverter.toString(caec.qualifiedName);
		}
		if (caec.isNamespace()) {
			return qualifiedNameConverter.toString(caec.qualifiedName);
		}
		if (caec.newImportHasAlias()) {
			String descr = "via new alias " + caec.addedImportNameAndAlias.alias;
			descr += " for " + qualifiedNameConverter.toString(caec.qualifiedName);
			descr += "\n\n";
			descr += "Introduces the new alias '" + caec.addedImportNameAndAlias.alias;
			descr += "' for element " + qualifiedNameConverter.toString(caec.qualifiedName);
			return descr;
		}

		QualifiedName caecQN = caec.qualifiedName;
		QualifiedName descrQN = (caecQN.getSegmentCount() > 1) ? caecQN.skipLast(1) : caecQN;
		return qualifiedNameConverter.toString(descrQN);
	}

	private String getKind(CAECandidate caec) {
		EClass eClass = caec.candidate.getEClass();
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

	private void addImportIfNecessary(CAECandidate caec, Resource resource, ContentAssistEntryWithRef proposal) {

		NameAndAlias requestedImport = caec.addedImportNameAndAlias;
		if (requestedImport == null) {
			return;
		}

		Script script = N4JSResource.getScript(resource);
		if (script == null) {
			return;
		}

		int insertionOffset = importRegionHelper.findInsertionOffset(script);
		String spacing = formattingUserPreferenceHelper.getSpacingPreference(resource);
		String lineDelimiter = "\n";

		QualifiedName qualifiedName = requestedImport.getName();
		String optionalAlias = requestedImport.getAlias();
		String projectName = requestedImport.getProjectName();

		QualifiedName moduleName = qualifiedName.skipLast(1);

		// create ImportRef
		ImportRef importRef = new ImportRef(qualifiedName.getLastSegment(), optionalAlias, projectName, moduleName);
		proposal.getImportRefs().add(importRef);

		// create text replacement
		String syntacticModuleName = syntacticModuleName(moduleName);

		String importSpec = (insertionOffset != 0 ? lineDelimiter : "") + "import ";

		if (!N4JSLanguageUtils.isDefaultExport(qualifiedName)) { // not an 'default' export
			importSpec = importSpec + "{" + spacing + qualifiedName.getLastSegment();
			if (optionalAlias != null) {
				importSpec = importSpec + " as ";
				importSpec = importSpec + optionalAlias;
			}
			importSpec = importSpec + spacing + "}";
		} else { // import default exported element
			if (optionalAlias == null) {
				importSpec = importSpec + N4JSLanguageUtils.lastSegmentOrDefaultHost(qualifiedName);
			} else {
				importSpec = importSpec + optionalAlias;
			}
		}

		String insertedCode = importSpec + " from " + syntacticModuleName + ";"
				+ (insertionOffset != 0 ? "" : lineDelimiter);
		ITextRegion region = new TextRegion(insertionOffset, 0);
		proposal.getTextReplacements().add(new XReplaceRegion(region, insertedCode));
	}

	/** compute the syntactic string representation of the moduleName */
	private String syntacticModuleName(QualifiedName moduleName) {
		String syntacticModuleName = valueConverters.toString(
				qualifiedNameConverter.toString(moduleName),
				grammarAccess.getModuleSpecifierRule().getName());
		return syntacticModuleName;
	}

	private static enum CandidateAccessType {
		direct, alias, namespace
	}

	private class CAECandidate {
		final IEObjectDescription candidate;
		final IEObjectDescription candidateViaScopeShortName;
		final IN4JSProject candidateProject;
		final boolean isScopedCandidateEqual;
		final boolean isScopedCandidateCollisioning;
		final boolean isValid;
		final EObject parentImportElement;
		final String parentImportModuleName;
		final QualifiedName qualifiedName;
		final String shortName;
		final QualifiedName namespaceName;
		final String aliasName;
		final CandidateAccessType accessType;
		final NameAndAlias addedImportNameAndAlias;

		CAECandidate(IEObjectDescription candidate, IScope scope, String prefix, INode currentNode,
				Predicate<String> conflictChecker) {
			this.candidate = candidate;
			this.shortName = getShortName();
			this.qualifiedName = getQualifiedName();
			this.parentImportElement = getParentImportElement(currentNode);
			this.parentImportModuleName = getParentImportModuleName();
			this.candidateViaScopeShortName = getCorrectCandidateViaScope(scope);
			this.candidateProject = n4jsCore.findProject(candidate.getEObjectURI()).orNull();
			this.isScopedCandidateEqual = isEqualCandidateName(candidateViaScopeShortName, qualifiedName);
			this.isScopedCandidateCollisioning = isScopedCandidateCollisioning();
			this.accessType = getAccessType();
			this.aliasName = getAliasName();
			this.namespaceName = getNamespaceName();
			this.addedImportNameAndAlias = getImportChanges();
			this.isValid = isValid(prefix, conflictChecker);
		}

		private String getShortName() {
			QualifiedName qName = candidate.getQualifiedName();
			return lastSegmentOrDefaultHost(qName);
		}

		/** @return true iff this candidate is valid and should be shown as a proposal */
		private boolean isValid(String prefix, Predicate<String> conflictChecker) {
			boolean validName = false;
			validName |= prefixMatcher.isCandidateMatchingPrefix(shortName, prefix);
			validName |= isAlias() && prefixMatcher.isCandidateMatchingPrefix(aliasName, prefix);
			validName |= isNamespace()
					&& prefixMatcher.isCandidateMatchingPrefix(namespaceName.getLastSegment(), prefix);

			boolean valid = validName;
			valid &= !Strings.isNullOrEmpty(shortName);
			valid &= !conflictChecker.apply(shortName);
			valid &= parentImportModuleName == null || qualifiedName.toString("/").startsWith(parentImportModuleName);

			return valid;
		}

		private QualifiedName getQualifiedName() {
			QualifiedName qName = candidate.getQualifiedName();

			if (qName.toString().equals(shortName)) {
				QualifiedName qnOfEObject = getCompleteQualifiedName(candidate);
				if (qnOfEObject != null) {
					return qnOfEObject;
				}
			}

			return qName;
		}

		private IEObjectDescription getCorrectCandidateViaScope(IScope scope) {
			IEObjectDescription candidateViaScope = getCandidateViaScope(scope);
			candidateViaScope = specialcaseNamespaceShadowsOwnElement(scope, candidateViaScope);
			return candidateViaScope;
		}

		private IEObjectDescription getCandidateViaScope(IScope scope) {
			// performance issue: scope.getElements
			List<IEObjectDescription> elements = Lists.newArrayList(scope.getElements(QualifiedName.create(shortName)));
			if (elements.isEmpty()) {
				return null;
			}
			if (elements.size() > 1) {
				for (IEObjectDescription element : elements) {
					if (isEqualCandidateName(element, qualifiedName)) {
						return element;
					}
				}
			}
			if (!elements.isEmpty()) {
				return elements.get(0);
			}

			return null;
		}

		private IEObjectDescription specialcaseNamespaceShadowsOwnElement(IScope scope,
				IEObjectDescription candidateViaScope) {

			if (candidateViaScope == null) {
				return candidateViaScope;
			}

			if (candidate.getEObjectOrProxy() instanceof ModuleNamespaceVirtualType) {
				return candidateViaScope;
			}

			EObject eObject = candidateViaScope.getEObjectOrProxy();
			if (!(candidateViaScope.getEObjectOrProxy() instanceof ModuleNamespaceVirtualType)) {
				return candidateViaScope;
			}

			ModuleNamespaceVirtualType mnvt = (ModuleNamespaceVirtualType) eObject;
			TModule module = mnvt.getModule();
			if (module == null) {
				return candidateViaScope;
			}

			String moduleQN = module.getQualifiedName();
			String candidateNamespaceName = candidateViaScope.getName().toString();
			if (!candidateNamespaceName.equals(shortName)) {
				return candidateViaScope;
			}

			QualifiedName qualifiedNameViaModule = QualifiedName.create(moduleQN).append(shortName);
			IEObjectDescription shadowedCandidateViaScope = scope.getSingleElement(qualifiedNameViaModule);
			if (shadowedCandidateViaScope == null) {
				return candidateViaScope;
			}

			QualifiedName qualifiedNameViaNamespace = QualifiedName.create(candidateNamespaceName).append(shortName);
			if (!qualifiedName.equals(qualifiedNameViaModule)) {
				return candidateViaScope;
			}

			// handle special case:
			return new PlainAccessOfNamespacedImportDescription(shadowedCandidateViaScope, qualifiedNameViaNamespace);
		}

		/** @return the complete qualified name using {@link IQualifiedNameProvider} */
		private QualifiedName getCompleteQualifiedName(IEObjectDescription objDescr) {
			if (objDescr == null) {
				return null;
			}
			EObject eObjectOrProxy = objDescr.getEObjectOrProxy();
			if (eObjectOrProxy == null) {
				return null;
			}
			QualifiedName qnOfEObject = qualifiedNameProvider.getFullyQualifiedName(eObjectOrProxy);
			return qnOfEObject;
		}

		/** @return true iff the {@link QualifiedName} of the given objDescr equals the given qName */
		private boolean isEqualCandidateName(IEObjectDescription objDescr, QualifiedName qName) {
			QualifiedName qnOfEObject = getCompleteQualifiedName(objDescr);
			if (qnOfEObject == null) {
				return false;
			}
			return qnOfEObject.equals(qName);
		}

		/**
		 * @return true iff {@link #candidate} and {@link #candidateViaScopeShortName} are different but accessible via
		 *         the same short name
		 */
		private boolean isScopedCandidateCollisioning() {
			if (isScopedCandidateEqual) {
				return false;
			}
			if (candidateViaScopeShortName instanceof PlainAccessOfAliasedImportDescription) {
				String candidateAlias = ((PlainAccessOfAliasedImportDescription) candidateViaScopeShortName).getAlias();
				if (!shortName.equals(candidateAlias)) {
					return false;
				}
			}
			if (candidateViaScopeShortName instanceof PlainAccessOfNamespacedImportDescription) {
				QualifiedName candidateNamespaceName = ((PlainAccessOfNamespacedImportDescription) candidateViaScopeShortName)
						.getNamespacedName();
				if (!shortName.equals(candidateNamespaceName.toString())) {
					return false;
				}
			}
			return true;
		}

		private CandidateAccessType getAccessType() {
			if (isScopedCandidateEqual) {
				if (candidateViaScopeShortName instanceof PlainAccessOfAliasedImportDescription) {
					return CandidateAccessType.alias;
				}
				if (candidateViaScopeShortName instanceof PlainAccessOfNamespacedImportDescription) {
					return CandidateAccessType.namespace;
				}
			}
			if (candidate instanceof AliasedEObjectDescription) {
				return CandidateAccessType.alias;
			}
			return CandidateAccessType.direct;
		}

		private String getAliasName() {
			if (accessType == CandidateAccessType.alias) {
				if (candidate instanceof AliasedEObjectDescription) {
					return candidate.getName().toString();
				}
				return ((PlainAccessOfAliasedImportDescription) candidateViaScopeShortName).getAlias();
			}
			return null;
		}

		private QualifiedName getNamespaceName() {
			if (accessType == CandidateAccessType.namespace) {
				return ((PlainAccessOfNamespacedImportDescription) candidateViaScopeShortName).getNamespacedName();
			}
			return null;
		}

		private EObject getParentImportElement(INode currentNode) {
			while (currentNode != null) {
				EObject semanticElement = currentNode.getSemanticElement();
				if (semanticElement instanceof ImportCallExpression
						|| semanticElement instanceof ImportDeclaration) {

					return semanticElement;
				}
				currentNode = currentNode.getParent();
			}
			return null;
		}

		private String getParentImportModuleName() {
			if (parentImportElement instanceof ImportCallExpression) {
				// how could that be done?
				return null;
			}
			if (parentImportElement instanceof ImportDeclaration) {
				// TODO GH-1704: could also be done via scoping
				ImportDeclaration impDecl = (ImportDeclaration) parentImportElement;
				return impDecl.getModuleSpecifierAsText();
			}

			return null;
		}

		private NameAndAlias getImportChanges() {
			if (parentImportElement != null) {
				return null;
			}

			if (accessType != CandidateAccessType.direct) {
				return null;
			}

			QualifiedName importName = getImportName();

			if (importName == null) {
				return null;
			}

			// we could create an import statement if there is no conflict
			if (importName.getSegmentCount() == 1) {
				// type name is a simple name - no need to hassle with imports
				return null;
			}

			// Globally available elements should not generate imports
			if (importName.getSegmentCount() == 2
					&& N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT.equals(importName.getFirstSegment())) {
				// type name is a simple name from global Namespace - no need to hassle with imports
				return null;
			}

			String alias = null;

			if (candidateViaScopeShortName != null && isScopedCandidateCollisioning) {
				// accessing default export via already imported namespace
				if (candidateViaScopeShortName.getEObjectOrProxy() instanceof ModuleNamespaceVirtualType) {
					// return null;
				}

				// the simple name is already reachable, i.e. already in use - another import is present
				// try to use an alias
				alias = "Alias_" + UtilN4.toUpperCaseFirst(qualifiedName.toString().replace(".", "_"));
			}

			String projectName = candidateProject != null ? candidateProject.getProjectName().toString() : null;

			return new NameAndAlias(importName, alias, projectName);
		}

		/** In case of main module, adjust the qualified name, e.g. index.Element -> react.Element */
		private QualifiedName getImportName() {
			QualifiedName qfn = candidate.getQualifiedName();
			int qfnSegmentCount = qfn.getSegmentCount();
			String tmodule = (qfnSegmentCount >= 2) ? qfn.getSegment(qfnSegmentCount - 2) : null;

			QualifiedName candidateName;
			if (candidateProject != null && tmodule != null && tmodule.equals(candidateProject.getMainModule())) {
				N4JSProjectName projectName = candidateProject.getProjectName();
				N4JSProjectName definesPackage = candidateProject.getDefinesPackageName();
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

		public boolean hasNewImport() {
			return addedImportNameAndAlias != null;
		}

		public boolean newImportHasAlias() {
			return hasNewImport() && !Strings.isNullOrEmpty(addedImportNameAndAlias.alias);
		}

		public boolean isAlias() {
			return accessType == CandidateAccessType.alias;
		}

		public boolean isNamespace() {
			return accessType == CandidateAccessType.namespace;
		}
	}
}
