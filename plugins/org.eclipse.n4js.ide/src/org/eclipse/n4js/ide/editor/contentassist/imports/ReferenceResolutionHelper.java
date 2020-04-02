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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.formatting2.FormattingUserPreferenceHelper;
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
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
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
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * For a reference in the N4JS source code (which may be resolved or unresolved), this class provides helper methods to
 * search all possible {@link ReferenceResolution resolutions}, i.e. all possible target elements the reference may
 * refer to.
 * <p>
 * This functionality is used by
 * <ul>
 * <li>content assist (to compute all content assist proposals),
 * <li>quick fix "adding missing import" (to compute the new import to be added),
 * <li>organize imports (to compute new imports to be added for all unresolved references in the file).
 * </ul>
 */
public class ReferenceResolutionHelper {

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
	private IPrefixMatcher prefixMatcher;

	@Inject
	private ImportRegionHelper importRegionHelper;

	@Inject
	private FormattingUserPreferenceHelper formattingUserPreferenceHelper;

	public interface IResolutionAcceptor {

		void accept(ReferenceResolution resolution);

		boolean canAcceptMoreProposals();
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

	private static class Reference {
		final String prefix;
		final EObject model;
		final EReference eReference;
		final INode currentNode;

		Reference(String prefix, EObject model, EReference eReference, INode currentNode) {
			this.prefix = prefix;
			this.model = model;
			this.eReference = eReference;
			this.currentNode = currentNode;
		}
	}

	public List<ReferenceResolution> lookupAllUnresolvedCrossReferences(
			Script script,
			CancelIndicator cancelIndicator) {

		triggerProxyResolution(script, cancelIndicator);

		List<ReferenceResolution> result = new ArrayList<>();
		Set<String> donePrefixes = new HashSet<>();
		Iterator<EObject> iter = script.eAllContents();
		while (iter.hasNext()) {
			EObject curr = iter.next();
			Reference reference = getUnresolvedReferenceForASTNode(curr);
			if (reference != null && donePrefixes.add(reference.prefix)) {
				ResolutionAcceptor acceptor = new ResolutionAcceptor(true, cancelIndicator);
				searchResolutions(reference, true, true, Predicates.alwaysFalse(), Predicates.alwaysTrue(), acceptor);
				List<ReferenceResolution> resolutions = acceptor.getResolutions();
				if (!resolutions.isEmpty()) {
					result.add(resolutions.get(0));
				}
			}
		}
		return result;
	}

	public List<ReferenceResolution> lookupSingleUnresolvedCrossReference(
			EObject model,
			boolean onlyAcceptSingleProposal,
			CancelIndicator cancelIndicator) {

		triggerProxyResolution(model, cancelIndicator);
		Reference reference = getUnresolvedReferenceForASTNode(model);
		if (reference == null) {
			return Collections.emptyList();
		}
		ResolutionAcceptor acceptor = new ResolutionAcceptor(onlyAcceptSingleProposal, cancelIndicator);
		searchResolutions(reference, true, true, Predicates.alwaysFalse(), Predicates.alwaysTrue(), acceptor);
		return acceptor.getResolutions();
	}

	private void triggerProxyResolution(EObject eObject, CancelIndicator cancelIndicator) {
		Resource resource = eObject.eResource();
		if (resource instanceof LazyLinkingResource) {
			((LazyLinkingResource) resource).resolveLazyCrossReferences(cancelIndicator);
		}
	}

	/** IMPORTANT: this method assumes proxy resolution has taken place in containing resource! */
	private Reference getUnresolvedReferenceForASTNode(EObject astNode) {
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

		return new Reference(prefix, astNode, eReference, currentNode);
	}

	public void lookupSingleCrossReference(
			EObject model,
			EReference eReference,
			String prefix, INode currentNode,
			Predicate<String> conflictChecker,
			IResolutionAcceptor acceptor,
			Predicate<IEObjectDescription> filter) {

		if (model == null) {
			return;
		}

		Reference reference = new Reference(prefix, model, eReference, currentNode);
		searchResolutions(reference, false, false, conflictChecker, filter, acceptor);
	}

	private void searchResolutions(
			Reference reference,
			boolean requireFullMatch,
			boolean isUnresolvedReference,
			Predicate<String> conflictChecker,
			Predicate<IEObjectDescription> filter,
			IResolutionAcceptor acceptor) {

		final Resource resource = reference.model.eResource();
		final IContentAssistScopeProvider contentAssistScopeProvider = (IContentAssistScopeProvider) scopeProvider;
		final IScope scope = contentAssistScopeProvider.getScopeForContentAssist(reference.model, reference.eReference);
		// iterate over candidates, filter them, and create ICompletionProposals for them
		final Iterable<IEObjectDescription> candidates = scope.getAllElements();
		final Set<URI> candidateURIs = new HashSet<>(); // note: shadowing for #getAllElements does not work

		for (IEObjectDescription candidate : candidates) {
			if (!acceptor.canAcceptMoreProposals()) {
				return;
			}
			if (!filter.apply(candidate)) {
				continue;
			}

			final Optional<IScope> scopeForCollisionCheck = isUnresolvedReference ? Optional.absent()
					: Optional.of(scope);
			final ReferenceResolution resolution = getResolution(resource, reference.prefix, reference.currentNode,
					requireFullMatch, candidate, scopeForCollisionCheck, conflictChecker);
			if (resolution != null && candidateURIs.add(candidate.getEObjectURI())) {
				acceptor.accept(resolution);
			}
		}
	}

	/**
	 * Returns a resolution if the given candidate is a valid target element for the given prefix; otherwise
	 * <code>null</code> is returned.
	 *
	 * @param candidate
	 *            the {@link IEObjectDescription} representing the potential target element of the resolution.
	 * @param scopeForCollisionCheck
	 *            a scope that will be used for a collision check. If the reference being resolved is known to be an
	 *            unresolved reference and <code>requireFullMatch</code> is set to <code>true</code>, then this
	 *            collision check can safely be omitted.
	 * @return the resolution of <code>null</code> if the candidate is not a valid match for the reference.
	 */
	private ReferenceResolution getResolution(Resource resource, String prefix, INode currentNode,
			boolean requireFullMatch, IEObjectDescription candidate, Optional<IScope> scopeForCollisionCheck,
			Predicate<String> conflictChecker) {

		ReferenceResolutionCandidate rrc = new ReferenceResolutionCandidate(candidate, scopeForCollisionCheck, prefix,
				requireFullMatch, currentNode, conflictChecker);

		if (!rrc.isValid) {
			return null;
		}

		try {
			int version = N4JSResourceDescriptionStrategy.getVersion(candidate);

			String proposal = getProposal(rrc);
			String label = getLabel(rrc, version);
			String description = getDescription(rrc);
			ImportRef importToBeAdded = getImportRef(rrc);
			Collection<ReplaceRegion> textReplacements = getTextReplacements(resource, importToBeAdded);

			return new ReferenceResolution(candidate, proposal, label, description, importToBeAdded, textReplacements);

		} catch (ValueConverterException e) {
			// text does not match the concrete syntax
		}

		return null;
	}

	private String getProposal(ReferenceResolutionCandidate rrc) {
		switch (rrc.accessType) {
		case alias:
			return rrc.aliasName;
		case namespace:
			return rrc.namespaceName.toString();
		default:
			// noop
		}
		if (rrc.newImportHasAlias()) {
			return rrc.addedImportNameAndAlias.alias;
		}
		return rrc.shortName;
	}

	private String getLabel(ReferenceResolutionCandidate rrc, int version) {
		String typeVersion = (version == 0) ? "" : N4IDLGlobals.VERSION_SEPARATOR + String.valueOf(version);
		if (rrc.isAlias()) {
			return rrc.aliasName + typeVersion;
		}
		if (rrc.isNamespace()) {
			return rrc.namespaceName + typeVersion;
		}
		if (rrc.newImportHasAlias()) {
			return rrc.shortName + typeVersion;
		}

		return rrc.shortName + typeVersion;
	}

	private String getDescription(ReferenceResolutionCandidate rrc) {
		if (rrc.isAlias()) {
			return "alias for " + qualifiedNameConverter.toString(rrc.qualifiedName);
		}
		if (rrc.isNamespace()) {
			return qualifiedNameConverter.toString(rrc.qualifiedName);
		}
		if (rrc.newImportHasAlias()) {
			String descr = "via new alias " + rrc.addedImportNameAndAlias.alias;
			descr += " for " + qualifiedNameConverter.toString(rrc.qualifiedName);
			descr += "\n\n";
			descr += "Introduces the new alias '" + rrc.addedImportNameAndAlias.alias;
			descr += "' for element " + qualifiedNameConverter.toString(rrc.qualifiedName);
			return descr;
		}

		QualifiedName rrcQN = rrc.qualifiedName;
		QualifiedName descrQN = (rrcQN.getSegmentCount() > 1) ? rrcQN.skipLast(1) : rrcQN;
		return qualifiedNameConverter.toString(descrQN);
	}

	private ImportRef getImportRef(ReferenceResolutionCandidate rrc) {
		NameAndAlias requestedImport = rrc.addedImportNameAndAlias;
		if (requestedImport == null) {
			return null;
		}

		QualifiedName qualifiedName = requestedImport.getName();
		String optionalAlias = requestedImport.getAlias();
		String projectName = requestedImport.getProjectName();

		QualifiedName moduleName = qualifiedName.skipLast(1);
		String moduleSpecifier = qualifiedNameConverter.toString(moduleName);

		ImportRef importRef;
		if (N4JSLanguageUtils.isDefaultExport(qualifiedName)) {
			String localName = optionalAlias != null ? optionalAlias
					: N4JSLanguageUtils.lastSegmentOrDefaultHost(qualifiedName);
			importRef = ImportRef.createDefaultImport(localName, moduleSpecifier, projectName, moduleName,
					Integer.MAX_VALUE);
		} else {
			importRef = ImportRef.createNamedImport(qualifiedName.getLastSegment(), optionalAlias, moduleSpecifier,
					projectName, moduleName, Integer.MAX_VALUE);
		}

		return importRef;
	}

	private List<ReplaceRegion> getTextReplacements(Resource resource, ImportRef importRef) {
		if (importRef == null) {
			return Collections.emptyList();
		}

		Script script = N4JSResource.getScript(resource);
		if (script == null) {
			return null;
		}

		int insertionOffset = importRegionHelper.findInsertionOffset(script);
		String spacing = formattingUserPreferenceHelper.getSpacingPreference(resource);
		String lineDelimiter = "\n";

		String insertedCode = (insertionOffset != 0 ? lineDelimiter : "")
				+ importRef.toCode(spacing, valueConverters, grammarAccess)
				+ (insertionOffset != 0 ? "" : lineDelimiter);
		ITextRegion region = new TextRegion(insertionOffset, 0);
		XReplaceRegion textReplacement = new XReplaceRegion(region, insertedCode);

		return Collections.singletonList(textReplacement);
	}

	private static enum CandidateAccessType {
		direct, alias, namespace
	}

	private class ReferenceResolutionCandidate {
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

		ReferenceResolutionCandidate(IEObjectDescription candidate, Optional<IScope> scopeForCollisionCheck,
				String prefix, boolean requireFullMatch, INode currentNode, Predicate<String> conflictChecker) {
			if (!requireFullMatch && !scopeForCollisionCheck.isPresent()) {
				throw new IllegalArgumentException(
						"collision check should only be omitted if a full match is required");
			}
			this.candidate = candidate;
			this.candidateProject = n4jsCore.findProject(candidate.getEObjectURI()).orNull();
			this.shortName = getShortName();
			this.qualifiedName = getQualifiedName();
			this.parentImportElement = getParentImportElement(currentNode);
			this.parentImportModuleName = getParentImportModuleName();
			this.candidateViaScopeShortName = getCorrectCandidateViaScope(scopeForCollisionCheck);
			this.isScopedCandidateEqual = isEqualCandidateName(candidateViaScopeShortName, qualifiedName);
			this.isScopedCandidateCollisioning = isScopedCandidateCollisioning();
			this.accessType = getAccessType();
			this.aliasName = getAliasName();
			this.namespaceName = getNamespaceName();
			this.addedImportNameAndAlias = getImportChanges();
			this.isValid = isValid(prefix, requireFullMatch, conflictChecker);
		}

		private String getShortName() {
			QualifiedName qName = candidate.getQualifiedName();
			return lastSegmentOrDefaultHost(qName);
		}

		/** @return true iff this candidate is valid and should be shown as a proposal */
		private boolean isValid(String prefix, boolean requireFullMatch, Predicate<String> conflictChecker) {
			boolean validName = false;
			validName |= isMatch(shortName, prefix, requireFullMatch);
			validName |= isAlias() && isMatch(aliasName, prefix, requireFullMatch);
			validName |= isNamespace()
					&& prefixMatcher.isCandidateMatchingPrefix(namespaceName.getLastSegment(), prefix);

			boolean valid = validName;
			valid &= !Strings.isNullOrEmpty(shortName);
			valid &= !conflictChecker.apply(shortName);
			valid &= parentImportModuleName == null || qualifiedName.toString("/").startsWith(parentImportModuleName);

			return valid;
		}

		private boolean isMatch(String name, String prefix, boolean requireFullMatch) {
			if (requireFullMatch) {
				return prefix.equals(name);
			}
			return prefixMatcher.isCandidateMatchingPrefix(name, prefix);
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

		private IEObjectDescription getCorrectCandidateViaScope(Optional<IScope> scopeForCollisionCheck) {
			if (scopeForCollisionCheck.isPresent()) {
				IScope scope = scopeForCollisionCheck.get();
				IEObjectDescription candidateViaScope = getCandidateViaScope(scope);
				candidateViaScope = specialcaseNamespaceShadowsOwnElement(scope, candidateViaScope);
				return candidateViaScope;
			}
			return null;
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
