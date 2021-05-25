/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.imports;

import static org.eclipse.n4js.utils.N4JSLanguageUtils.lastSegmentOrDefaultHost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistDataCollectors;
import org.eclipse.n4js.n4JS.ImportCallExpression;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.scoping.IContentAssistScopeProvider;
import org.eclipse.n4js.scoping.imports.PlainAccessOfAliasedImportDescription;
import org.eclipse.n4js.scoping.imports.PlainAccessOfNamespacedImportDescription;
import org.eclipse.n4js.scoping.members.WrongTypingStrategyDescription;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * For a {@link ReferenceDescriptor reference in the N4JS source code} (which may be resolved or unresolved), this class
 * provides helper functionality to search all possible {@link ReferenceResolution resolutions}, i.e. all possible
 * target elements the reference may refer to.
 * <p>
 * Do not confuse this with "find references" functionality:
 * <ul>
 * <li>"find references" searches all valid, resolved references in the code base that point to a given identifiable
 * element.
 * <li>this class searches all identifiable elements in the code base that may serve as a valid target for a given,
 * resolved or unresolved, reference.
 * </ul>
 * This functionality is used by
 * <ul>
 * <li>content assist (to compute all content assist proposals),
 * <li>quick fix "add missing import" (to compute the new import to be added),
 * <li>organize imports (to compute new imports to be added for all unresolved references in the file).
 * </ul>
 */
@Singleton
public class ReferenceResolutionFinder {

	public static boolean USE_NEW_APPROACH = true;

	@Inject
	private IScopeProvider scopeProvider;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private IPrefixMatcher prefixMatcher;

	@Inject
	private ContentAssistDataCollectors contentAssistDataCollectors;

	/**
	 * An acceptor receiving valid {@link ReferenceResolution}s for a given {@link ReferenceDescriptor reference}. See
	 * {@link ReferenceResolutionFinder#findResolutions(N4JSWorkspaceConfigSnapshot, ReferenceDescriptor, boolean, boolean, Predicate, Predicate, IResolutionAcceptor)
	 * #searchResolutions()} for details.
	 */
	public interface IResolutionAcceptor {

		/** Invoked when a valid {@link ReferenceResolution} is found. */
		void accept(ReferenceResolution resolution);

		/** Tells if this acceptor is able and willing to accept more {@link ReferenceResolution}s. */
		boolean canAcceptMoreProposals();
	}

	/**
	 * Searches all valid resolutions of the given reference. Note: two higher-level convenience methods are available
	 * in {@link ImportHelper}.
	 *
	 * @param reference
	 *            the reference to resolve.
	 * @param requireFullMatch
	 *            if <code>true</code>, a candidate's name must be <em>equal</em> to the given <code>reference</code>'s
	 *            {@link ReferenceDescriptor#text text}; otherwise it is sufficient if the name <em>starts with</em> the
	 *            text.
	 * @param isUnresolvedReference
	 *            if true, the given <code>reference</code> is assumed to be an unresolved reference (which means
	 *            certain collision checks can be omitted); otherwise nothing will be assumed, i.e. the reference may be
	 *            resolved or unresolved.
	 * @param conflictChecker
	 *            allows for additional checks to be performed on a potential resolution's
	 *            {@link ReferenceResolutionCandidate#shortName shortName}, before it is deemed valid and sent to the
	 *            acceptor.
	 * @param filter
	 *            a filter to decide with candidate {@link IEObjectDescription}s to include in the search. Should return
	 *            <code>true</code> to include the given candidate in the search.
	 * @param acceptor
	 *            an {@link IResolutionAcceptor} that will be invoked with all valid resolutions.
	 */
	public void findResolutions(
			N4JSWorkspaceConfigSnapshot wc,
			ReferenceDescriptor reference,
			boolean requireFullMatch,
			boolean isUnresolvedReference,
			Predicate<String> conflictChecker,
			Predicate<IEObjectDescription> filter,
			IResolutionAcceptor acceptor) {

		IScope scope = getScopeForContentAssist(reference);

		// iterate over candidates, filter them, and create ICompletionProposals for them

		boolean needCollisionCheck = !isUnresolvedReference;

		List<IEObjectDescription> candidates = new ArrayList<>(512);
		Set<QualifiedName> candidateNames = needCollisionCheck ? new HashSet<>() : null;
		collectAllElements(scope, candidates, candidateNames, acceptor);

		try (Measurement m = contentAssistDataCollectors.dcIterateAllElements().getMeasurement()) {
			Set<URI> candidateURIs = new HashSet<>(); // note: shadowing for #getAllElements does not work
			for (IEObjectDescription candidate : candidates) {
				if (!acceptor.canAcceptMoreProposals()) {
					return;
				}
				if (!filter.apply(candidate)) {
					continue;
				}

				final N4JSProjectConfigSnapshot candidateProject = wc.findProjectContaining(candidate.getEObjectURI());
				// note: must go on with resolution computation even if 'candidateProject' is 'null', because this will
				// happen in some valid cases (e.g. built-in types)

				final Optional<IScope> scopeForCollisionCheck = needCollisionCheck
						? Optional.of(scope)
						: Optional.absent();
				final Optional<Set<QualifiedName>> allElementsForCollisionCheck = needCollisionCheck
						? Optional.of(candidateNames)
						: Optional.absent();
				final ReferenceResolution resolution = getResolution(reference.text, reference.parseTreeNode,
						requireFullMatch, candidate, candidateProject, scopeForCollisionCheck,
						allElementsForCollisionCheck, conflictChecker);
				if (resolution != null && candidateURIs.add(candidate.getEObjectURI())) {
					acceptor.accept(resolution);
				}
			}
		}
	}

	/**
	 * @param addHere
	 *            elements will be added here. This is required even if <code>addHereByName</code> is given, to preserve
	 *            the correct order of elements.
	 * @param addHereNames
	 *            iff non-<code>null</code>, elements will also be added here, indexed by name.
	 * @param acceptor
	 *            no resolutions will be passed to the acceptor by this method, only used for cancellation handling.
	 */
	private void collectAllElements(IScope scope, List<IEObjectDescription> addHere, Set<QualifiedName> addHereNames,
			IResolutionAcceptor acceptor) {
		try (Measurement m = contentAssistDataCollectors.dcGetAllElements().getMeasurement()) {
			if (!acceptor.canAcceptMoreProposals()) {
				return;
			}
			Iterator<IEObjectDescription> iter = scope.getAllElements().iterator();
			// note: checking #canAcceptMoreProposals() in next line is required to quickly react to cancellation
			while (acceptor.canAcceptMoreProposals() && iter.hasNext()) {
				IEObjectDescription curr = iter.next();
				if (!isRelevantDescription(curr)) {
					continue;
				}
				addHere.add(curr);
				if (addHereNames != null) {
					addHereNames.add(curr.getName());
				}
			}
		}
	}

	private IScope getScopeForContentAssist(ReferenceDescriptor reference) {
		try (Measurement m = contentAssistDataCollectors.dcGetScope().getMeasurement()) {
			IContentAssistScopeProvider contentAssistScopeProvider = (IContentAssistScopeProvider) scopeProvider;
			return contentAssistScopeProvider.getScopeForContentAssist(reference.astNode, reference.eReference);
		}
	}

	/**
	 * Returns a resolution if the given candidate is a valid target element for the given reference text (full name or
	 * just prefix, depending on <code>requireFullMatch</code>); otherwise <code>null</code> is returned.
	 *
	 * @param candidate
	 *            the {@link IEObjectDescription} representing the potential target element of the resolution.
	 * @param candidateProjectOrNull
	 *            the containing project of the <code>candidate</code> or <code>null</code> if not available /
	 *            applicable (e.g. in case the candidate represents a built-in type).
	 * @param scopeForCollisionCheck
	 *            a scope that will be used for a collision check. If the reference being resolved is known to be an
	 *            unresolved reference and <code>requireFullMatch</code> is set to <code>true</code>, then this
	 *            collision check can safely be omitted.
	 * @return the resolution of <code>null</code> if the candidate is not a valid match for the reference.
	 */
	private ReferenceResolution getResolution(String text, INode parseTreeNode, boolean requireFullMatch,
			IEObjectDescription candidate, N4JSProjectConfigSnapshot candidateProjectOrNull,
			Optional<IScope> scopeForCollisionCheck, Optional<Set<QualifiedName>> allElementNamesForCollisionCheck,
			Predicate<String> conflictChecker) {

		try (Measurement m1 = contentAssistDataCollectors.dcGetResolution().getMeasurement()) {
			ReferenceResolutionCandidate rrc = new ReferenceResolutionCandidate(candidate, candidateProjectOrNull,
					scopeForCollisionCheck, allElementNamesForCollisionCheck, text, requireFullMatch, parseTreeNode,
					conflictChecker);

			if (!rrc.isValid) {
				return null;
			}

			try (Measurement m2 = contentAssistDataCollectors.dcGetResolution().getMeasurement()) {
				int version = N4JSResourceDescriptionStrategy.getVersion(candidate);

				String proposal = getProposal(rrc);
				String label = getLabel(rrc, version);
				String description = getDescription(rrc);
				ImportDescriptor importToBeAdded = getImportToBeAdded(rrc);

				return new ReferenceResolution(candidate, proposal, label, description, importToBeAdded);

			} catch (ValueConverterException e) {
				// text does not match the concrete syntax
			}

			return null;
		}
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

	private ImportDescriptor getImportToBeAdded(ReferenceResolutionCandidate rrc) {
		NameAndAlias requestedImport = rrc.addedImportNameAndAlias;
		if (requestedImport == null) {
			return null;
		}

		QualifiedName qualifiedName = requestedImport.name;
		String optionalAlias = requestedImport.alias;

		N4JSProjectName projectName = rrc.candidateProjectOrNull != null
				? rrc.candidateProjectOrNull.getN4JSProjectName()
				: null;
		if (projectName == null) {
			return null;
		}

		QualifiedName moduleName = qualifiedName.skipLast(1);
		String moduleSpecifier = qualifiedNameConverter.toString(moduleName);

		ImportDescriptor importDesc;
		if (N4JSLanguageUtils.isDefaultExport(qualifiedName)) {
			String localName = optionalAlias != null ? optionalAlias
					: N4JSLanguageUtils.lastSegmentOrDefaultHost(qualifiedName);
			importDesc = ImportDescriptor.createDefaultImport(localName, moduleSpecifier, projectName, moduleName,
					Integer.MAX_VALUE);
		} else {
			importDesc = ImportDescriptor.createNamedImport(qualifiedName.getLastSegment(), optionalAlias,
					moduleSpecifier, projectName, moduleName, Integer.MAX_VALUE);
		}

		return importDesc;
	}

	private static enum CandidateAccessType {
		direct, alias, namespace
	}

	private class ReferenceResolutionCandidate {
		final IEObjectDescription candidate;
		final IEObjectDescription candidateViaScopeShortName;
		/** The project containing the candidate. Might be <code>null</code>, e.g. in case of built-in types. */
		final N4JSProjectConfigSnapshot candidateProjectOrNull;
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

		ReferenceResolutionCandidate(IEObjectDescription candidate, N4JSProjectConfigSnapshot candidateProjectOrNull,
				Optional<IScope> scopeForCollisionCheck, Optional<Set<QualifiedName>> allElementNamesForCollisionCheck,
				String text, boolean requireFullMatch, INode parseTreeNode, Predicate<String> conflictChecker) {

			try (Measurement m = contentAssistDataCollectors.dcCreateReferenceResolutionCandidate1().getMeasurement()) {
				if (!requireFullMatch && !scopeForCollisionCheck.isPresent()) {
					throw new IllegalArgumentException(
							"collision check should only be omitted if a full match is required");
				}

				this.candidate = candidate;
				this.candidateProjectOrNull = candidateProjectOrNull;
				this.shortName = getShortName();
				this.qualifiedName = getQualifiedName();
				this.parentImportElement = getParentImportElement(parseTreeNode);
				this.parentImportModuleName = getParentImportModuleName();
			}
			try (Measurement m = contentAssistDataCollectors.dcDetectProposalConflicts().getMeasurement()) {
				this.candidateViaScopeShortName = getCorrectCandidateViaScope(scopeForCollisionCheck,
						allElementNamesForCollisionCheck);
			}
			try (Measurement m = contentAssistDataCollectors.dcCreateReferenceResolutionCandidate2().getMeasurement()) {
				this.isScopedCandidateEqual = isEqualCandidateName(candidateViaScopeShortName, qualifiedName);
				this.isScopedCandidateCollisioning = isScopedCandidateCollisioning();
				this.accessType = getAccessType();
				this.aliasName = getAliasName();
				this.namespaceName = getNamespaceName();
				this.addedImportNameAndAlias = getImportChanges();
				this.isValid = isValid(text, requireFullMatch, conflictChecker);
			}
		}

		private String getShortName() {
			QualifiedName qName = candidate.getQualifiedName();
			return lastSegmentOrDefaultHost(qName);
		}

		/** @return true iff this candidate is valid and should be shown as a proposal */
		private boolean isValid(String text, boolean requireFullMatch, Predicate<String> conflictChecker) {
			boolean validName = false;
			validName |= isMatch(shortName, text, requireFullMatch);
			validName |= isAlias() && isMatch(aliasName, text, requireFullMatch);
			validName |= isNamespace()
					&& prefixMatcher.isCandidateMatchingPrefix(namespaceName.getLastSegment(), text);

			boolean valid = validName;
			valid &= !Strings.isNullOrEmpty(shortName);
			valid &= !conflictChecker.apply(shortName);
			valid &= parentImportModuleName == null || qualifiedName.toString("/").startsWith(parentImportModuleName);

			return valid;
		}

		private boolean isMatch(String name, String text, boolean requireFullMatch) {
			if (requireFullMatch) {
				return text.equals(name);
			}
			return prefixMatcher.isCandidateMatchingPrefix(name, text);
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

		private IEObjectDescription getCorrectCandidateViaScope(Optional<IScope> scopeForCollisionCheck,
				Optional<Set<QualifiedName>> allElementNamesForCollisionCheck) {
			if (scopeForCollisionCheck.isPresent()) {
				IScope scope = scopeForCollisionCheck.get();
				Set<QualifiedName> allElementNames = allElementNamesForCollisionCheck.get();
				IEObjectDescription candidateViaScope = getCandidateViaScope(scope, allElementNames);
				candidateViaScope = specialcaseNamespaceShadowsOwnElement(scope, candidateViaScope);
				return candidateViaScope;
			}
			return null;
		}

		private IEObjectDescription getCandidateViaScope(IScope scope, Set<QualifiedName> allElementNames) {
			QualifiedName shortNameQN = QualifiedName.create(shortName);
			if (USE_NEW_APPROACH) {
				// because 'scope.getElements()' is slow-ish and we are invoked for each element in
				// 'scope.getAllElements()' (see #collectAllElements() above), we use this guard:
				if (!allElementNames.contains(shortNameQN)) {
					return null;
				}
			}
			List<IEObjectDescription> elements = Lists.newArrayList(Iterables.filter(
					scope.getElements(shortNameQN), ReferenceResolutionFinder::isRelevantDescription));
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

		private EObject getParentImportElement(INode parseTreeNode) {
			while (parseTreeNode != null) {
				EObject semanticElement = parseTreeNode.getSemanticElement();
				if (semanticElement instanceof ImportCallExpression
						|| semanticElement instanceof ImportDeclaration) {

					return semanticElement;
				}
				parseTreeNode = parseTreeNode.getParent();
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

			return new NameAndAlias(importName, alias);
		}

		/** Return fully qualified name (= module specifier + element name) of the element to be imported. */
		private QualifiedName getImportName() {
			QualifiedName qfn = candidate.getQualifiedName();
			int qfnSegmentCount = qfn.getSegmentCount();
			String tmodule = (qfnSegmentCount >= 2) ? qfn.getSegment(qfnSegmentCount - 2) : null;
			ProjectType projectType = candidateProjectOrNull != null ? candidateProjectOrNull.getType() : null;

			QualifiedName candidateName;
			if (candidateProjectOrNull != null && tmodule != null
					&& tmodule.equals(candidateProjectOrNull.getMainModule())) {
				// use project import when importing from a main module (e.g. index.Element -> react.Element)
				N4JSProjectName projectName = getNameOfDefinedOrGivenProject(candidateProjectOrNull);
				String lastSegmentOfQFN = candidate.getQualifiedName().getLastSegment().toString();
				candidateName = projectName.toQualifiedName().append(lastSegmentOfQFN);

			} else if (candidateProjectOrNull != null
					&& (projectType == ProjectType.PLAINJS || projectType == ProjectType.DEFINITION)) {
				// use complete module specifier when importing from PLAINJS or DEFINITION project
				// (i.e. prepend project name)
				N4JSProjectName projectName = getNameOfDefinedOrGivenProject(candidateProjectOrNull);
				candidateName = projectName.toQualifiedName().append(candidate.getQualifiedName());

			} else {
				// standard case: use plain module specifier
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

	/**
	 * Returns <code>true</code> iff the given description is relevant for the purpose of reference resolution finding
	 * in this class.
	 * <p>
	 * In principle, this is the case iff {@link N4JSLanguageUtils#isActualElementInScope(IEObjectDescription)} returns
	 * <code>true</code>. However, because some special handling exists above for certain subclasses of
	 * {@link IEObjectDescriptionWithError} we have to return <code>true</code> for those subclasses as well.
	 */
	private static boolean isRelevantDescription(IEObjectDescription desc) {
		return desc instanceof PlainAccessOfAliasedImportDescription
				|| desc instanceof PlainAccessOfNamespacedImportDescription
				|| desc instanceof WrongTypingStrategyDescription
				|| N4JSLanguageUtils.isActualElementInScope(desc);
	}

	private static N4JSProjectName getNameOfDefinedOrGivenProject(N4JSProjectConfigSnapshot project) {
		if (project.getType() == ProjectType.DEFINITION) {
			N4JSProjectName definedProjectName = project.getDefinesPackage();
			if (definedProjectName != null) {
				return definedProjectName;
			}
		}
		return project.getN4JSProjectName();
	}

	private static class NameAndAlias {
		private final QualifiedName name;
		private final String alias;

		public NameAndAlias(QualifiedName qualifiedName, String alias) {
			this.name = qualifiedName;
			this.alias = alias;
		}
	}
}
