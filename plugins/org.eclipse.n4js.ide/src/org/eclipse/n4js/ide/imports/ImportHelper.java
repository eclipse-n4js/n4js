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
package org.eclipse.n4js.ide.imports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.formatting2.FormattingUserPreferenceHelper;
import org.eclipse.n4js.ide.imports.ReferenceResolutionFinder.IResolutionAcceptor;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Provides high-level convenience methods on top of {@link ImportDescriptor}, {@link ReferenceResolution},
 * {@link ReferenceResolutionFinder}, etc.
 */
@Singleton
public class ImportHelper {

	private static final EReference identifierRef_id = N4JSPackage.eINSTANCE.getIdentifierRef_Id();
	private static final EReference parameterizedTypeRef_declaredType = TypeRefsPackage.eINSTANCE
			.getParameterizedTypeRef_DeclaredType();

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private ReferenceResolutionFinder referenceResolutionFinder;

	@Inject
	private ImportRegionHelper importRegionHelper;

	@Inject
	private FormattingUserPreferenceHelper formattingUserPreferenceHelper;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private IValueConverterService valueConverters;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Creates an {@link ImportDescriptor} for a given existing import in the AST.
	 *
	 * @param importDecl
	 *            the import declaration. May not be <code>null</code>.
	 * @param importSpec
	 *            the import specification in case of a named or namespace import; <code>null</code> denotes a bare
	 *            import.
	 * @param originalIndex
	 *            the original index to set in the newly created {@link ImportDescriptor}. This should usually reflect
	 *            the original order of imports in the AST.
	 * @return a newly created import descriptor.
	 */
	public ImportDescriptor createImportDescriptorFromAST(ImportDeclaration importDecl, ImportSpecifier importSpec,
			int originalIndex) {

		Objects.requireNonNull(importDecl);
		if (importSpec != null && !importSpec.isFlaggedUsedInCode()) {
			throw new IllegalArgumentException("must not create an ImportDescriptor for unused imports");
		}
		if ((importSpec == null && ImportSpecifiersUtil.isBrokenImport(importDecl))
				|| (importSpec != null && ImportSpecifiersUtil.isBrokenImport(importSpec))) {
			throw new IllegalArgumentException("must not create an ImportDescriptor for broken imports");
		}

		TModule module = importDecl.getModule();
		N4JSProjectName targetProjectName = new N4JSProjectName(module.getProjectName());
		QualifiedName targetModule = qualifiedNameConverter.toQualifiedName(module.getQualifiedName());

		String moduleSpecifier = importDecl.getModuleSpecifierAsText();
		if (importDecl.isBare()) {
			return ImportDescriptor.createBareImport(moduleSpecifier, targetProjectName, targetModule, originalIndex);
		} else {
			if (importSpec instanceof NamedImportSpecifier) {
				NamedImportSpecifier importSpecCasted = (NamedImportSpecifier) importSpec;
				if (importSpecCasted.isDefaultImport()) {
					String localName = importSpecCasted.getAlias();
					return ImportDescriptor.createDefaultImport(localName, moduleSpecifier, targetProjectName,
							targetModule, originalIndex);
				} else {
					String elementName = importSpecCasted.getImportedElementAsText();
					String alias = importSpecCasted.getAlias();
					return ImportDescriptor.createNamedImport(elementName, alias, moduleSpecifier, targetProjectName,
							targetModule, originalIndex);
				}
			} else if (importSpec instanceof NamespaceImportSpecifier) {
				String localNamespaceName = ((NamespaceImportSpecifier) importSpec).getAlias();
				boolean isDynamic = ((NamespaceImportSpecifier) importSpec).isDeclaredDynamic();
				return ImportDescriptor.createNamespaceImport(localNamespaceName, isDynamic, moduleSpecifier,
						targetProjectName, targetModule, originalIndex);
			} else if (importSpec != null) {
				throw new IllegalArgumentException(
						"unknown subclass of ImportSpecifier: " + importSpec.getClass().getSimpleName());
			} else {
				throw new IllegalArgumentException("importSpec may be null only if importDecl is a bare import");
			}
		}
	}

	/**
	 * Convert an {@link ImportDescriptor} to a {@link ReplaceRegion}.
	 */
	public ReplaceRegion getReplacementForImport(Script script, ImportDescriptor importDesc) {
		int insertionOffset = importRegionHelper.findInsertionOffset(script);
		String spacing = formattingUserPreferenceHelper.getSpacingPreference(script.eResource());
		String lineDelimiter = "\n";

		String insertedCode = (insertionOffset != 0 ? lineDelimiter : "")
				+ importDesc.toCode(spacing, valueConverters, grammarAccess)
				+ (insertionOffset != 0 ? "" : lineDelimiter);
		ITextRegion region = new TextRegion(insertionOffset, 0);
		XReplaceRegion textReplacement = new XReplaceRegion(region, insertedCode);

		return textReplacement;
	}

	/**
	 * Searches the entire AST for unresolved references and searches possible resolutions for each. Returns the
	 * resolutions of those unresolved references that are unambiguous, i.e. that have only a single possible
	 * resolution.
	 *
	 * @see ReferenceResolutionFinder#findResolutions(N4JSWorkspaceConfigSnapshot, ReferenceDescriptor, boolean,
	 *      boolean, Predicate, Predicate, IResolutionAcceptor)
	 */
	public List<ReferenceResolution> findResolutionsForAllUnresolvedReferences(Script script,
			CancelIndicator cancelIndicator) {

		triggerProxyResolution(script, cancelIndicator);

		N4JSWorkspaceConfigSnapshot wc = workspaceAccess.getWorkspaceConfig(script).get();
		List<ReferenceResolution> result = new ArrayList<>();
		Set<String> donePrefixes = new HashSet<>();
		Iterator<EObject> iter = script.eAllContents();
		while (iter.hasNext()) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			EObject curr = iter.next();
			ReferenceDescriptor reference = getUnresolvedReferenceForASTNode(curr);
			if (reference != null && donePrefixes.add(reference.text)) {
				ResolutionAcceptor acceptor = new ResolutionAcceptor(2, cancelIndicator);
				referenceResolutionFinder.findResolutions(wc, reference, true, true, Predicates.alwaysFalse(),
						Predicates.alwaysTrue(), acceptor);
				// only add a found resolution if we have exactly one resolution (i.e. if reference is unambiguous)
				List<ReferenceResolution> resolutions = acceptor.getResolutions();
				if (resolutions.size() == 1) {
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
	 * @return valid resolutions for the unresolved reference. May be empty but never <code>null</code>.
	 */
	public List<ReferenceResolution> findResolutionsForUnresolvedReference(EObject astNode,
			CancelIndicator cancelIndicator) {

		triggerProxyResolution(astNode, cancelIndicator);

		N4JSWorkspaceConfigSnapshot wc = workspaceAccess.getWorkspaceConfig(astNode).get();

		ReferenceDescriptor reference = getUnresolvedReferenceForASTNode(astNode);
		if (reference == null) {
			return Collections.emptyList();
		}
		operationCanceledManager.checkCanceled(cancelIndicator);
		ResolutionAcceptor acceptor = new ResolutionAcceptor(-1, cancelIndicator);
		referenceResolutionFinder.findResolutions(wc, reference, true, true, Predicates.alwaysFalse(),
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
		if (eReference == null || prefix == null) {
			return null; // broken AST
		}

		Object targetObj = astNode.eGet(eReference, false);
		if (!(targetObj instanceof EObject)) {
			return null; // unsupported kind of reference (e.g. many-valued)
		}
		if (!((EObject) targetObj).eIsProxy()) {
			return null; // reference isn't unresolvable
		}

		INode currentNode = NodeModelUtils.findActualNodeFor(astNode);
		if (currentNode == null) {
			return null; // broken AST
		}

		return new ReferenceDescriptor(prefix, astNode, eReference, currentNode);
	}

	private static final class ResolutionAcceptor implements IResolutionAcceptor {

		private final int maxAcceptedProposals;
		private final CancelIndicator cancelIndicator;

		private final List<ReferenceResolution> resolutions = new ArrayList<>();

		private ResolutionAcceptor(int maxAcceptedProposals, CancelIndicator cancelIndicator) {
			this.maxAcceptedProposals = maxAcceptedProposals >= 0 ? maxAcceptedProposals : Integer.MAX_VALUE;
			this.cancelIndicator = cancelIndicator;
		}

		@Override
		public void accept(ReferenceResolution newEntry) {
			resolutions.add(newEntry);
		}

		public List<ReferenceResolution> getResolutions() {
			return resolutions;
		}

		@Override
		public boolean canAcceptMoreProposals() {
			return resolutions.size() < maxAcceptedProposals && !cancelIndicator.isCanceled();
		}
	}
}
