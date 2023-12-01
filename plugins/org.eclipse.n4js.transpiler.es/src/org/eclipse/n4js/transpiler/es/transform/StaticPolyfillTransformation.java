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
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._DefaultImportSpecifier;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ImportDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._NamedImportSpecifier;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._NamespaceImportSpecifier;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._TypeReferenceNode;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isContainedInStaticPolyfillAware;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tooling.organizeImports.ScriptDependencyResolver;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.StaticPolyfillHelper;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Inject;

/**
 */
public class StaticPolyfillTransformation extends Transformation {

	@Inject
	private StaticPolyfillHelper staticPolyfillHelper;
	@Inject
	private TypeAssistant typeAssistant;

	/**
	 * While inserting members into classes, we add here the elements referenced in the initializer expressions, fpar
	 * default expressions and bodies.
	 */
	private final Set<SymbolTableEntryOriginal> referencedElements = new HashSet<>();
	/**
	 * Each referenced element for which we add an import gets its own alias. This set is used to keep track of those
	 * aliases (mainly when creating a new, unique alias).
	 */
	private final Set<String> referencedElementsAliases = new HashSet<>();

	@Override
	public void assertPreConditions() {
		// none
	}

	@Override
	public void assertPostConditions() {
		// none
	}

	@Override
	public void analyze() {
		// nothing
	}

	@Override
	public void transform() {
		boolean isAware = isContainedInStaticPolyfillAware(getState().resource.getScript());
		if (isAware) {
			N4JSResource fillingResource = staticPolyfillHelper.getStaticPolyfillResource(getState().resource);
			if (fillingResource != null) {
				referencedElements.clear();
				for (N4ClassDeclaration cd : collectNodes(getState().im, N4ClassDeclaration.class, false)) {
					doStaticPolyfilling(cd);
				}
				for (SymbolTableEntryOriginal steo : referencedElements) {
					addImportIfRequired(steo, fillingResource);
				}
			}
		}
	}

	private void doStaticPolyfilling(N4ClassDeclaration classDecl) {
		TClass defCls = getState().info.getOriginalDefinedType(classDecl);
		N4ClassDeclaration filler = staticPolyfillHelper.getStaticPolyfill(defCls);
		if (filler != null) {
			doStaticPolyfilling(classDecl, filler);
		}
	}

	private void doStaticPolyfilling(N4ClassDeclaration classFilled, N4ClassDeclaration classFiller) {
		// fill additionally implemented interfaces
		Set<TInterface> currentIfcs = new HashSet<>();
		for (TypeReferenceNode<ParameterizedTypeRef> trn : classFilled.getImplementedInterfaceRefs()) {
			TypeRef origTRef = getState().info.getOriginalProcessedTypeRef(trn);
			if (origTRef != null && origTRef.getDeclaredType() instanceof TInterface) {
				currentIfcs.add((TInterface) origTRef.getDeclaredType());
			}
		}
		for (TypeReferenceNode<ParameterizedTypeRef> trn : classFiller.getImplementedInterfaceRefs()) {
			Type origType = typeAssistant.getOriginalDeclaredType(trn);
			if (origType instanceof TInterface) {
				insertImplementedInterface(classFilled, (TInterface) origType, currentIfcs);
			}
		}
		// fill members
		for (N4MemberDeclaration member : classFiller.getOwnedMembers()) {
			insertMember(classFilled, member);
		}
	}

	private void insertImplementedInterface(N4ClassDefinition classFilled, TInterface ifcToBeInserted,
			Set<TInterface> currentIfcs) {
		if (!currentIfcs.contains(ifcToBeInserted)) { // avoid duplicates!
			classFilled.getImplementedInterfaceRefs()
					.add(_TypeReferenceNode(getState(), TypeUtils.createTypeRef(ifcToBeInserted)));
		}
	}

	/**
	 * Create and insert a copy of <code>memberToBeInserted</code> into <code>classFilled</code>.
	 *
	 * @param classFilled
	 *            the class declaration to be filled, must be contained in the intermediate model.
	 * @param memberToBeInserted
	 *            this member is expected to be contained in the AST of the filling resource, so this is neither
	 *            contained in the original AST (of the resource to compile) nor the intermediate model!
	 */
	private void insertMember(N4ClassDefinition classFilled, N4MemberDeclaration memberToBeInserted) {
		N4MemberDeclaration existing = findFirst(classFilled.getOwnedMembers(),
				member -> member.eClass() == memberToBeInserted.eClass()
						&& Objects.equals(member.getName(), memberToBeInserted.getName()));

		N4MemberDeclaration copy = copyAlienElement(memberToBeInserted);
		// store elements that are referenced from within this member
		// (e.g. from within the body, from initializer expressions of fields, from default expressions of fpars, etc.)
		EcoreUtil2.eAllOfType(classFilled, ReferencingElement_IM.class);
		for (ReferencingElement_IM refElem : EcoreUtil2.eAllOfType(classFilled, ReferencingElement_IM.class)) {
			if (refElem.getRewiredTarget() instanceof SymbolTableEntryOriginal) {
				referencedElements.add((SymbolTableEntryOriginal) refElem.getRewiredTarget());
			}
		}
		if (existing != null) {
			replace(existing, copy);
		} else {
			classFilled.getOwnedMembersRaw().add(copy);
		}
		getState().info.setOriginalDefinedMember(copy, memberToBeInserted.getDefinedTypeElement());
	}

	private void addImportIfRequired(SymbolTableEntryOriginal ste, N4JSResource fillingResource) {
		if (ste.getImportSpecifier() == null) {
			IdentifiableElement originalTarget = ste.getOriginalTarget();
			boolean isNested = originalTarget instanceof TMember || originalTarget instanceof TEnumLiteral;
			if (!isNested
					|| N4JSGlobals.DTS_FILE_EXTENSION == URIUtils.fileExtension(originalTarget.eResource().getURI())) {
				boolean isLocal = originalTarget.eResource() == getState().resource;
				if (!isLocal
						&& ScriptDependencyResolver.shouldBeImported(fillingResource.getModule(), originalTarget)) {
					addImport(ste, fillingResource);
				}
			}
		}
	}

	/**
	 * Add an import for the element represented by the given SymbolTableEntry in the intermediate model.
	 */
	private void addImport(SymbolTableEntryOriginal ste, N4JSResource fillingResource) {
		IdentifiableElement importedElement = ste.getOriginalTarget();
		boolean isNamespace = importedElement instanceof ModuleNamespaceVirtualType;

		// search original import specification (in original AST of fillingResource)
		Iterable<?> impSpecs = flatten(map(filter(
				fillingResource.getScript().getScriptElements(), ImportDeclaration.class),
				id -> id.getImportSpecifiers()));
		ImportSpecifier impSpec_original = (isNamespace)
				? findFirst(filter(impSpecs, NamespaceImportSpecifier.class),
						nis -> nis.getDefinedType() == importedElement)
				: findFirst(filter(impSpecs, NamedImportSpecifier.class),
						nis -> nis.getImportedElement() == importedElement);
		EObject impDecl_original = impSpec_original == null ? null : impSpec_original.eContainer();

		// if all this was successful, go ahead and add the import ...
		if (impDecl_original instanceof ImportDeclaration) {
			ImportDeclaration impDeclCasted = (ImportDeclaration) impDecl_original;
			String aliasName = getAlias(impSpec_original) != null
					? getAlias(impSpec_original)
					: ste.getExportedName() != null
							? ste.getExportedName()
							: "unnamed";
			String alias = chooseNewUniqueAlias(aliasName);
			ImportSpecifier impSpec;
			if (isNamespace) {
				impSpec = _NamespaceImportSpecifier(alias, true);
			} else if (impSpec_original instanceof DefaultImportSpecifier) {
				impSpec = _DefaultImportSpecifier(alias, true);
			} else {
				impSpec = _NamedImportSpecifier(ste.getExportedName(), alias, true);
			}

			// obtain module from which we import importedElement
			TModule remoteModule = (isNamespace) ?
			// warning: in case of namespaces, importedElement resides in the TModule of the fillingResource!
			// -> so we cannot just get the containing TModule in this case
					((ModuleNamespaceVirtualType) importedElement).getModule()
					:
					// standard case: just find the containing TModule of importedElement
					impDeclCasted.getModule();

			ImportDeclaration impDecl = _ImportDecl(impSpec);
			impDecl.setModuleSpecifierForm(impDeclCasted.getModuleSpecifierForm());
			getState().im.getScriptElements().add(0, impDecl);
			ste.setName(alias);
			ste.setImportSpecifier(impSpec);
			getState().tracer.setOriginalASTNode(impDecl, impDeclCasted);
			getState().tracer.setOriginalASTNode(impSpec, impSpec_original);
			// store the imported module in information registry
			// (required my ModuleWrappingTransformation)
			getState().info.setImportedModule(impDecl, remoteModule);
		}
	}

	private String chooseNewUniqueAlias(String baseName) {
		String alias;
		int cnt = 0;
		do {
			String cntStr = (cnt == 0) ? "" : Integer.toString(cnt);
			alias = baseName + cntStr + "$polyfilled";
			cnt++;
		} while (referencedElementsAliases.contains(alias));
		referencedElementsAliases.add(alias);
		return alias;
	}

	private static final String getAlias(ImportSpecifier impSpec) {
		if (impSpec instanceof NamedImportSpecifier) {
			return ((NamedImportSpecifier) impSpec).getAlias();
		}

		if (impSpec instanceof NamespaceImportSpecifier) {
			return ((NamespaceImportSpecifier) impSpec).getAlias();
		}
		return null;
	}
}
