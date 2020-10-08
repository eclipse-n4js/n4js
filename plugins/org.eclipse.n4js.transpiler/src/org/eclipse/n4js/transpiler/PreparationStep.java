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
package org.eclipse.n4js.transpiler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.generator.UnresolvedProxyInSubGeneratorException;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.JSXElementName;
import org.eclipse.n4js.n4JS.LocalArgumentsVariable;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.Variable;
import org.eclipse.n4js.n4idl.transpiler.utils.N4IDLTranspilerUtils;
import org.eclipse.n4js.n4idl.versioning.MigrationUtils;
import org.eclipse.n4js.n4idl.versioning.VersionHelper;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.transpiler.TranspilerState.STECache;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.ImFactory;
import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM;
import org.eclipse.n4js.transpiler.im.Script_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVersionable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.Arrays;
import org.eclipse.xtext.util.LineAndColumn;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

/**
 */
public class PreparationStep {

	private static final ImmutableMap<EClass, EClass> ECLASS_REPLACEMENT = ImmutableMap.<EClass, EClass> builder()
			.put(N4JSPackage.eINSTANCE.getScript(),
					ImPackage.eINSTANCE.getScript_IM())
			.put(N4JSPackage.eINSTANCE.getIdentifierRef(),
					ImPackage.eINSTANCE.getIdentifierRef_IM())
			.put(N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression(),
					ImPackage.eINSTANCE.getParameterizedPropertyAccessExpression_IM())
			.put(N4JSPackage.eINSTANCE.getVersionedIdentifierRef(),
					ImPackage.eINSTANCE.getVersionedIdentifierRef_IM())
			.put(TypeRefsPackage.eINSTANCE.getParameterizedTypeRef(),
					ImPackage.eINSTANCE.getParameterizedTypeRef_IM())
			.put(TypeRefsPackage.eINSTANCE.getParameterizedTypeRefStructural(),
					ImPackage.eINSTANCE.getParameterizedTypeRefStructural_IM())
			.put(TypeRefsPackage.eINSTANCE.getVersionedParameterizedTypeRef(),
					ImPackage.eINSTANCE.getVersionedParameterizedTypeRef_IM())
			.put(TypeRefsPackage.eINSTANCE.getVersionedParameterizedTypeRefStructural(),
					ImPackage.eINSTANCE.getVersionedParameterizedTypeRefStructural_IM())
			.build();

	private static final EReference[] REWIRED_REFERENCES = {
			N4JSPackage.eINSTANCE.getIdentifierRef_Id(),
			N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property(),
			TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType(),
	};

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Inject
	private VersionHelper versionHelper;

	/**
	 * Creates and initializes the transpiler state. In particular, this will create the intermediate model as a copy of
	 * the original AST with some modifications (esp. rewiring of cross-references to ensure intermediate model is
	 * self-contained).
	 *
	 * @param options
	 *            the {@link GeneratorOption generator options} to use during generation.
	 */
	public TranspilerState prepare(Script script, GeneratorOption[] options) {
		final N4JSResource resource = (N4JSResource) script.eResource();
		final IN4JSProject project = n4jsCore.findProject(resource.getURI()).orNull();
		final ContainerTypesHelper.MemberCollector memberCollector = containerTypesHelper.fromContext(resource);
		final Tracer tracer = new Tracer();
		final InformationRegistry info = new InformationRegistry();
		final STECache steCache = createIM(script, tracer, info);

		return new TranspilerState(resource, project, options, memberCollector, steCache.im, steCache, tracer, info);
	}

	private STECache createIM(Script script, Tracer tracer, InformationRegistry info) {
		final AST2IMCopier copier = new AST2IMCopier(script, tracer, info);
		final EObject result = copier.copy(script);
		final STECache steCache = new STECache((Script_IM) result);
		copier.steCache = steCache;

		copier.copyReferences();
		copier.createRemainingSymbolTableEntries();
		return steCache;
	}

	/**
	 * Should be only called from {@link TranspilerComponent#copyAlienElement(EObject)}.
	 */
	/* package */ <T extends EObject> T copyForIM(TranspilerState state, T someASTNode) {
		final AST2IMCopier copier = new AST2IMCopier(state);
		final EObject result = copier.copy(someASTNode);
		copier.copyReferences();
		// copier.createRemainingSymbolTableEntries(); // not required here
		@SuppressWarnings("unchecked")
		final T resultCasted = (T) result;
		return resultCasted;
	}

	/**
	 * A custom implementation of {@link org.eclipse.emf.ecore.util.EcoreUtil.Copier} that copies the N4JS AST model to
	 * the N4JS transpiler intermediate representation.
	 */
	private final class AST2IMCopier extends EcoreUtil.Copier {

		private final Tracer tracer;
		private final InformationRegistry info;
		private final Script script;

		private Script_IM script_IM;
		private STECache steCache;

		/**
		 * Helper registry. Maps original TModule elements that were imported <b>via a named import</b> to original(!)
		 * import specifier causing the import.
		 */
		private final Map<IdentifiableElement, NamedImportSpecifier> importedElements = new HashMap<>();
		/**
		 * Helper registry. Maps original TModules that were imported <b>via a name-space import</b> to original(!)
		 * import specifier causing the import.
		 */
		private final Map<TModule, NamespaceImportSpecifier> importedModules = new HashMap<>();

		public AST2IMCopier(Script script, Tracer tracer, InformationRegistry info) {
			// note: we set useOriginalReferences==false, which means we won't copy references to remote elements that
			// have not been contained in the element to be copied (e.g. references to the TModule)
			super(true, false);
			if (!tracer.isEmpty()) {
				throw new IllegalArgumentException(
						"so far, IMCopier assumes given tracer to be empty; if that is not desired, please refactor");
			}
			this.script = script;
			this.tracer = tracer;
			this.info = info;
		}

		public AST2IMCopier(TranspilerState state) {
			super(true, false);
			this.script = (Script) state.tracer.getOriginalASTNode(state.im);
			this.tracer = state.tracer;
			this.info = state.info;
			this.script_IM = state.im;
			this.steCache = state.steCache;
		}

		private void initializeScript_IM(Script_IM sim) {
			if (script_IM != null) {
				throw new IllegalStateException("script copied twice");
			}
			script_IM = sim;
			script_IM.setSymbolTable(ImFactory.eINSTANCE.createSymbolTable());
		}

		@Override
		protected EObject createCopy(EObject eObject) {
			final EObject copy = super.createCopy(eObject);
			tracer.setOriginalASTNode_internal(copy, eObject);
			if (copy instanceof Script_IM) {
				initializeScript_IM((Script_IM) copy);
			} else if (copy instanceof ImportDeclaration) {
				info.setImportedModule_internal((ImportDeclaration) copy,
						((ImportDeclaration) eObject).getModule());
			} else if (copy instanceof ImportSpecifier) {
				// remember which TModule elements were imported via ImportSpecifiers
				if (copy instanceof NamedImportSpecifier)
					// cast to IM-specific class is safe due to ECLASS_REPLACEMENT
					handleCopyNamedImportSpecifier((NamedImportSpecifier) eObject);
				else if (copy instanceof NamespaceImportSpecifier)
					importedModules.put(((ImportDeclaration) eObject.eContainer()).getModule(),
							(NamespaceImportSpecifier) eObject);
				else
					throw new IllegalStateException("unsupported sub-class of ImportSpecifier: " + copy.eClass());
			} else if (copy instanceof N4TypeDeclaration) {
				info.setOriginalDefinedType_internal((N4TypeDeclaration) copy,
						((N4TypeDeclaration) eObject).getDefinedType());
			} else if (copy instanceof N4MemberDeclaration) {
				info.setOriginalDefinedMember_internal((N4MemberDeclaration) copy,
						((N4MemberDeclaration) eObject).getDefinedTypeElement());
			}
			return copy;
		}

		@Override
		protected EClass getTarget(EClass eClass) {
			final EClass replacement = ECLASS_REPLACEMENT.get(eClass);
			return replacement != null ? replacement : super.getTarget(eClass);
		}

		@Override
		protected EClass getTarget(EObject eObject) {
			// special-handling for named import specifiers of versioned types
			if (eObject instanceof NamedImportSpecifier &&
					N4IDLTranspilerUtils.isVersionedImportSpecifier((NamedImportSpecifier) eObject)) {
				return ImPackage.eINSTANCE.getVersionedNamedImportSpecifier_IM();
			}
			return super.getTarget(eObject);
		}

		@Override
		protected void copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
			final boolean needsRewiring = Arrays.contains(REWIRED_REFERENCES, eReference);
			if (needsRewiring) {
				if (!(copyEObject instanceof ReferencingElement_IM)) {
					throw new IllegalStateException(
							"an EObject with a cross-reference that requires rewiring must have a copy of type ReferencingElement_IM;"
									+ "\nin object: " + eObject
									+ "\nin resource: " + eObject.eResource().getURI());
				}
				rewire(eObject, (ReferencingElement_IM) copyEObject);
				// note: suppress default behavior (references "id", "property", "declaredType" should stay at 'null')
			} else {
				super.copyReference(eReference, eObject, copyEObject);
			}
		}

		// TODO IDE-2010 consider improving performance of following method!
		private void createRemainingSymbolTableEntries() {
			// so far, we have created symbol table entries for all referenced elements on the fly;
			// now we will create entries for the remaining elements in the TModule
			final TreeIterator<EObject> iter1 = script.getModule().eAllContents();
			while (iter1.hasNext()) {
				final EObject obj = iter1.next();
				if (obj instanceof IdentifiableElement) {
					getSymbolTableEntry((IdentifiableElement) obj, true);
				}
			}
			// ... and for the variables (and fpars) that do not have a TVariable in the module (i.e. non-exported)
			final TreeIterator<EObject> iter2 = script.eAllContents();
			while (iter2.hasNext()) {
				final EObject obj = iter2.next();
				if (obj instanceof Variable) { // note: this also includes FormalParameters and CatchVariables
					final boolean isExported = obj instanceof ExportedVariableDeclaration;
					if (!isExported) {
						// don't do this for exported variable declarations, because we already have a SymbolTableEntry
						// pointing to the TVariable. Calling #getSymbolTableEntry() again with the declaration instead
						// of the TVariable would give us a second, duplicate symbol table entry!
						getSymbolTableEntry((Variable) obj, true);
					}
				}
			}
		}

		/**
		 * Handler for when a {@link NamedImportSpecifier} is encountered in the copying process.
		 */
		private void handleCopyNamedImportSpecifier(NamedImportSpecifier namedImportSpecifier) {
			TExportableElement importedElement = namedImportSpecifier.getImportedElement();

			if (importedElement instanceof Type) {
				// add all versions of the type to the importedElements map.
				Iterable<? extends Type> versions = versionHelper.findTypeVersions((Type) importedElement);
				versions.forEach(v -> {
					importedElements.put(v, namedImportSpecifier);
				});
			} else {
				// for non-type imports, there is no versions
				importedElements.put(importedElement,
						namedImportSpecifier);
			}
		}

		/**
		 * Here we use the target of the original cross reference "id", "property", or "declaredType" to set the rewired
		 * target (i.e. symbol table entry) of the corresponding IM-model reference "id_IM", "property_IM", or
		 * "declaredType_IM".
		 */
		private void rewire(EObject eObject, ReferencingElement_IM copyEObject) {
			final IdentifiableElement originalTarget = getOriginalTargetOfNodeToRewire(eObject);
			final SymbolTableEntry rewiredTarget;
			if (!originalTarget.eIsProxy()) {
				// standard case: original target is a valid IdentifiableElement
				rewiredTarget = getSymbolTableEntry(originalTarget, true);
				copyEObject.setRewiredTarget(rewiredTarget);
			} else {
				// special case: unresolved proxy
				// -> this is usually an error, except in the following special cases:
				if (eObject instanceof ParameterizedPropertyAccessExpression) {
					// property access to an any+ type
					// -> because we know the transpiler is never invoked for resources that contain errors, we can
					// simply assume that we have the any+ case without actually checking the type of the receiver
					final String propName = getPropertyAsString((ParameterizedPropertyAccessExpression) eObject);
					((ParameterizedPropertyAccessExpression_IM) copyEObject).setAnyPlusAccess(true);
					((ParameterizedPropertyAccessExpression_IM) copyEObject).setNameOfAnyPlusProperty(propName);
				} else if (isDynamicNamespaceReference(eObject)) {
					// type references via dynamic namespace imports can still be transpiled
					// (no additional properties to set in ParameterizedTypeRef_IM)
				} else if (eObject instanceof IdentifierRef && eObject.eContainer() instanceof JSXElementName) {
					// name of a JSX element, e.g. the "div" in something like: <div prop='value'></div>
					String tagName = ((IdentifierRef) eObject).getIdAsText();
					((IdentifierRef_IM) copyEObject).setIdAsText(tagName);
				} else if (MigrationUtils.isMigrateCall(eObject.eContainer())) {
					// unresolved migrate-calls can still be transpiled
				} else {
					ICompositeNode node = NodeModelUtils.findActualNodeFor(eObject);
					LineAndColumn pos = node != null ? NodeModelUtils.getLineAndColumn(node, node.getOffset()) : null;
					throw new UnresolvedProxyInSubGeneratorException(eObject.eResource(), Optional.fromNullable(pos));
				}
			}
		}

		private boolean isDynamicNamespaceReference(EObject eObject) {
			if (eObject instanceof ParameterizedTypeRef) {
				ParameterizedTypeRef ptr = (ParameterizedTypeRef) eObject;
				ModuleNamespaceVirtualType astNamespace = ptr.getAstNamespace();
				if (astNamespace != null) {
					return astNamespace.isDeclaredDynamic();
				}
			}
			return false;
		}

		private SymbolTableEntryOriginal getSymbolTableEntry(IdentifiableElement elem, boolean create) {
			final SymbolTableEntryOriginal e = steCache.mapOriginal.get(elem);
			if (e != null)
				return e;
			if (create) {
				String versionedName = N4IDLTranspilerUtils.getVersionedInternalName(elem);
				return createSymbolTableEntry(versionedName, elem);
			}
			return null;
		}

		/**
		 * Creates a new {@link SymbolTableEntryOriginal} for the given {@link IdentifiableElement} using the given
		 * name.
		 */
		private SymbolTableEntryOriginal createSymbolTableEntry(String name, IdentifiableElement elem) {
			final SymbolTableEntryOriginal entry = ImFactory.eINSTANCE.createSymbolTableEntryOriginal();
			entry.setName(name);
			entry.setOriginalTarget(elem);
			// compute properties 'elementsOfThisName' and 'importSpecifier' from 'elem'
			if (elem instanceof Variable) {
				// special case of non-exported variables:
				// 'elem' is a variable (or fpar) in the original AST (note: never in a remote resource!) and we
				// have to point to its copy in the intermediate model via property 'elementsOfThisName'
				final EObject copy = getCopyOf(elem);
				entry.getElementsOfThisName().add((Variable) copy);
			} else {
				final EObject astElement = getASTElementIfInSameResource(elem);
				if (astElement instanceof NamespaceImportSpecifier) {
					// special case of exported namespace imports
					final EObject copy = getCopyOf(astElement);
					entry.setImportSpecifier((NamespaceImportSpecifier) copy);
				} else {
					// standard case:
					if (astElement instanceof NamedElement) {
						final EObject copy = getCopyOf(astElement);
						entry.getElementsOfThisName().add((NamedElement) copy);
					}
				}
			}
			final ImportSpecifier impSpec = getImportSpecifierForImportedElement(elem);
			if (impSpec != null) {
				final EObject copy = getCopyOf(impSpec);
				entry.setImportSpecifier((ImportSpecifier) copy);
				// Rewrite local name if alias is present.
				if (impSpec instanceof NamedImportSpecifier) {
					String alias = ((NamedImportSpecifier) impSpec).getAlias();
					if (null != alias) {
						entry.setName(alias); // exported name is visible via operation entry#exportedName())
					}
					if (N4IDLTranspilerUtils.refersToVersionedType(entry)) {
						// In this block, we may now assume that 'entry' is actually of type {@link
						// VersionedNamedImportSpecifier_IM} (cf. {@link #getTarget(EObject)}).

						// Add referenced type version to the list of imported type versions of the import specifier
						// copy. This is only executed once per type version, as the returned STE of this method is
						// cached.
						((VersionedNamedImportSpecifier_IM) copy).getImportedTypeVersions().add(entry);

						if (null != alias) {
							// Make sure to compute the versioned internal name based on the alias
							entry.setName(N4IDLTranspilerUtils.getVersionedInternalAlias(entry.getName(),
									(TVersionable) entry.getOriginalTarget()));
						}
					}
				}
			}
			// add 'entry' to the symbol table
			// add 'entry' to our registry
			SymbolTableManagement.addOriginal(steCache, entry);

			return entry;
		}

		private final EObject getASTElementIfInSameResource(IdentifiableElement elem) {
			if (elem instanceof SyntaxRelatedTElement && elem.eResource() == script.eResource())
				return ((SyntaxRelatedTElement) elem).getAstElement();
			else
				return null;
		}

		private final ImportSpecifier getImportSpecifierForImportedElement(IdentifiableElement element) {
			// case #1: named import
			ImportSpecifier impSpec = importedElements.get(element);
			if (impSpec == null) {
				// case #2: namespace import
				final TModule module = EcoreUtil2.getContainerOfType(element, TModule.class);
				if (module != null) {
					impSpec = importedModules.get(module);
				}
			}
			return impSpec;
		}

		private String getPropertyAsString(ParameterizedPropertyAccessExpression propAccessExpr) {
			final StringBuilder sb = new StringBuilder();
			for (INode node : NodeModelUtils.findNodesForFeature(propAccessExpr,
					N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property())) {
				sb.append(NodeModelUtils.getTokenText(node));
			}
			return sb.toString();
		}

		private final EObject getCopyOf(EObject obj) {
			// to not require an additional registry here, we use the tracer for this
			final Iterator<EObject> iterator = tracer.getIntermediateModelElements(obj).iterator();
			final EObject copy = iterator.hasNext() ? iterator.next() : null;

			// SPECIAL CASE here: local arguments-variables are created in scoping but if they are not really
			// referenced, then the copy-phase did not create a copy for them.
			if (copy == null && obj instanceof LocalArgumentsVariable) {
				// in this case, we create the copy of the LocalArgumentsVariable here:
				final LocalArgumentsVariable lav = ((FunctionOrFieldAccessor) getCopyOf(obj.eContainer()))
						.getLocalArgumentsVariable(); // this will create it and add it to the intermediate model
				tracer.setOriginalASTNode_internal(lav, obj);
				return lav;
			}

			// note in previous line:
			// 1) we know there's exactly 1 IM element while we are inside IMCopier
			// 2) if this is only called from #copyReference(), then we know the result can never be null, due to the
			// two phases of EcoreUtil.Copier (our super-class)
			if (copy == null)
				throw new IllegalStateException("copy of given object has not been created yet: " + obj);
			return copy;
		}
	}

	/**
	 * For a given AST node in the original AST that refers to some other element and requires re-wiring of its cross
	 * reference, this method will return the <b>original</b> target of the cross reference to rewire.
	 */
	private static final IdentifiableElement getOriginalTargetOfNodeToRewire(EObject nodeInOriginalAST) {
		if (nodeInOriginalAST instanceof IdentifierRef)
			return ((IdentifierRef) nodeInOriginalAST).getId();
		if (nodeInOriginalAST instanceof ParameterizedPropertyAccessExpression)
			return ((ParameterizedPropertyAccessExpression) nodeInOriginalAST).getProperty();
		if (nodeInOriginalAST instanceof ParameterizedTypeRef)
			return ((ParameterizedTypeRef) nodeInOriginalAST).getDeclaredType();
		throw new IllegalArgumentException("not an AST node that requires rewiring: " + nodeInOriginalAST);
	}
}
