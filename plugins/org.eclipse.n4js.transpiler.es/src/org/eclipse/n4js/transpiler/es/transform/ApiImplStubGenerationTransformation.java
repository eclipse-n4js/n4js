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

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._AnnotationList;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._EnumDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._EnumLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ExportDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._FunDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4ClassDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4InterfaceDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4MemberDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4MethodDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._NewExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ThrowStmnt;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AnnotableScriptElement;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.tooling.compare.ProjectComparisonEntry;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.es.assistants.DelegationAssistant;
import org.eclipse.n4js.transpiler.im.DelegatingMember;
import org.eclipse.n4js.transpiler.im.Script_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.utils.ConcreteMembersOrderedForTranspiler;
import org.eclipse.n4js.transpiler.utils.MissingApiMembersForTranspiler;
import org.eclipse.n4js.transpiler.utils.ScriptApiTracker;
import org.eclipse.n4js.transpiler.utils.ScriptApiTracker.ProjectComparisonAdapter;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.util.AccessorTuple;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;

import com.google.inject.Inject;

/**
 * Generation of code for missing implementations in projects implementing a specific API.
 */
@RequiresBefore(MemberPatchingTransformation.class)
public class ApiImplStubGenerationTransformation extends Transformation {

	@Inject
	private DelegationAssistant delegationAssistant;
	@Inject
	private TypeAssistant typeAssistant;
	@Inject
	private ScriptApiTracker scriptApiTracker;
	@Inject
	private ContainerTypesHelper containerTypesHelper;
	@Inject
	private N4JSElementKeywordProvider n4jsElementKeywordProvider;

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// perform API/Impl compare for the resource to compile
		scriptApiTracker.initApiCompare(getState().resource.getScript());
		// (reminder: code in #analyze() will be invoked once per resource to compile, so this is what we want)
	}

	@Override
	public void transform() {
		// add stubs for missing members in EXISTING classes and interfaces
		for (N4ClassifierDeclaration cd : collectNodes(getState().im, N4ClassifierDeclaration.class, false)) {
			addMissingMembers(cd);
		}
		// add missing types (classes, interfaces, enums) and other top-level elements (declared functions, variables)
		addMissingTopLevelElements();
	}

	private void addMissingMembers(N4ClassifierDeclaration classifierDecl) {
		TClassifier type = getState().info.getOriginalDefinedType(classifierDecl);
		MissingApiMembersForTranspiler mamft = createMAMFT(type);

		// add API/Impl method stubs
		for (TMethod m : mamft.missingApiMethods) {
			N4MemberDeclaration member = createApiImplStub(classifierDecl, m);
			classifierDecl.getOwnedMembersRaw().add(member);
		}
		// add API/Impl field accessor stubs
		for (AccessorTuple accTuple : mamft.missingApiAccessorTuples) {
			if (accTuple.getGetter() != null) {
				TGetter g = accTuple.getGetter();
				N4MemberDeclaration member = createApiImplStub(classifierDecl, g);
				classifierDecl.getOwnedMembersRaw().add(member);
			}
			if (accTuple.getSetter() != null) {
				TSetter s = accTuple.getSetter();
				N4MemberDeclaration member = createApiImplStub(classifierDecl, s);
				classifierDecl.getOwnedMembersRaw().add(member);
			}
		}

		// add delegates to inherited fields/getters/setters shadowed by an owned setter XOR getter
		// NOTE: Partial shadowing in general is disallowed by validation. However, in incomplete
		// API-impl situation we still support this feature here to propagate generated stubs for
		// test reporting-purposes.
		for (AccessorTuple accTuple : mamft.missingApiAccessorTuples) {
			if (accTuple.getInheritedGetter() != null && accTuple.getGetter() == null && accTuple.getSetter() != null) {
				// an owned setter is shadowing an inherited getter -> delegate to the inherited getter
				DelegatingMember delegator = delegationAssistant.createDelegatingMember(type,
						accTuple.getInheritedGetter());
				classifierDecl.getOwnedMembersRaw().add(delegator);
			}
			if (accTuple.getInheritedSetter() != null && accTuple.getGetter() != null && accTuple.getSetter() == null) {
				// an owned getter is shadowing an inherited setter -> delegate to the inherited setter
				DelegatingMember delegator = delegationAssistant.createDelegatingMember(type,
						accTuple.getInheritedSetter());
				classifierDecl.getOwnedMembersRaw().add(delegator);
			}
		}

	}

	private void addMissingTopLevelElements() {
		Script script = getState().resource.getScript();

		scriptApiTracker.initApiCompare(script);
		ProjectComparisonAdapter comparison = ScriptApiTracker.firstProjectComparisonAdapter(script.eResource())
				.orElse(null);
		if (null == comparison) {
			return;
		}

		for (ProjectComparisonEntry pce : comparison.getEntryFor(script.getModule()).allChildren().toList()) {
			if (null == pce.getElementImpl(0)) {// no implementation
				EObject x = pce.getElementAPI();
				if (x instanceof TMethod) {
					/* do nothing */
				}
				if (x instanceof TFunction) {
					missing((TFunction) x);
				}
				if (x instanceof TClass) {
					missing((TClass) x);
				}
				if (x instanceof TInterface) {
					missing((TInterface) x);
				}
				if (x instanceof TEnum) {
					missing((TEnum) x);
				}
				if (x instanceof TVariable) {
					missing((TVariable) x);
				}
			}
		}
	}

	private void missing(TInterface tinter) {
		N4InterfaceDeclaration stub0 = _N4InterfaceDeclaration(tinter.getName());

		// annotations
		stub0.setAnnotationList(_AnnotationList(
				toList(map(tinter.getAnnotations(), a -> AnnotationDefinition.find(a.getName())))));

		// export
		ScriptElement stub = (ScriptElement) wrapExported(tinter.isDirectlyExported(), stub0);

		// members
		// (in an interface stub we need member stubs for static AND non-static members)
		MemberList<TMember> members = getState().memberCollector.members(tinter, false, false);
		for (TMember m : members) {
			if (!(m instanceof TField)) {
				stub0.getOwnedMembersRaw().add(createApiImplStub(stub0, m));
			}
		}

		appendToScript(stub);

		getState().info.setOriginalDefinedType(stub0, tinter);

		createSymbolTableEntryIMOnly(stub0);
	}

	private void missing(TClass tclass) {
		N4ClassDeclaration stub0 = _N4ClassDeclaration(tclass.getName());

		// at least a ctor throwing an exception is required here.
		stub0.getOwnedMembersRaw().add(_N4MethodDecl("constructor",
				_ThrowStmnt(_NewExpr(
						_IdentRef(N4ApiNotImplementedErrorSTE()),
						_StringLiteral("Class " + tclass.getName() + " is not implemented yet.")))));

		// annotations
		stub0.setAnnotationList(_AnnotationList(
				toList(map(tclass.getAnnotations(), a -> AnnotationDefinition.find(a.getName())))));

		// export
		ScriptElement stub = (ScriptElement) wrapExported(tclass.isDirectlyExported(), stub0);

		// members
		// (in a class stub we need member stubs ONLY for static members)
		MemberList<TMember> members = getState().memberCollector.members(tclass, false, false);
		for (TMember m : members) {
			if (!(m instanceof TField) && m.isStatic()) {
				stub0.getOwnedMembersRaw().add(createApiImplStub(stub0, m));
			}
		}

		appendToScript(stub);

		getState().info.setOriginalDefinedType(stub0, tclass);

		createSymbolTableEntryIMOnly(stub0);
	}

	private void missing(TEnum tenum) {
		N4EnumDeclaration stub0 = _EnumDeclaration(tenum.getName(),
				toList(map(tenum.getLiterals(), l -> _EnumLiteral(l.getName(), l.getName()))));

		// exported
		ScriptElement stub = (ScriptElement) wrapExported(tenum.isDirectlyExported(), stub0);

		// number-/string-based
		EnumKind enumKind = N4JSLanguageUtils.getEnumKind(tenum);
		switch (enumKind) {
		case Normal: {
			// do nothing
			break;
		}
		case NumberBased: {
			((AnnotableScriptElement) stub)
					.setAnnotationList(_AnnotationList(List.of(AnnotationDefinition.NUMBER_BASED)));
			break;
		}
		case StringBased: {
			((AnnotableScriptElement) stub)
					.setAnnotationList(_AnnotationList(List.of(AnnotationDefinition.STRING_BASED)));
			break;
		}
		}

		appendToScript(stub);

		getState().info.setOriginalDefinedType(stub0, tenum);

		createSymbolTableEntryIMOnly(stub0);
	}

	private void appendToScript(ScriptElement stub) {
		Script_IM script = getState().im;
		if (script.getScriptElements().isEmpty()) {
			script.getScriptElements().add(stub);
		} else {
			insertAfter(last(script.getScriptElements()), stub);
		}
	}

	private void missing(TVariable tvar) {
		missingFuncOrVar(tvar, tvar.isDirectlyExported(), "variable");
	}

	private void missing(TFunction func) {
		missingFuncOrVar(func, func.isDirectlyExported(), "function");
	}

	private SymbolTableEntryInternal N4ApiNotImplementedErrorSTE() {
		return steFor_N4ApiNotImplementedError();
	}

	private void missingFuncOrVar(IdentifiableElement func, boolean exported, String description) {
		SymbolTableEntryOriginal funcSTE = getSymbolTableEntryOriginal(func, true); // createSymbolTableEntry(fun);

		FunctionDeclaration funcDecl = _FunDecl(funcSTE.getName(), _ThrowStmnt(_NewExpr(
				_IdentRef(N4ApiNotImplementedErrorSTE()),
				_StringLiteral(description + " " + funcSTE.getName() + " is not implemented yet."))));
		EObject stub = wrapExported(exported, funcDecl);

		insertAfter(last(getState().im.getScriptElements()), stub);
	}

	private EObject wrapExported(boolean exported, ExportableElement toExportOrNotToExport) {
		return (exported) ? _ExportDeclaration(toExportOrNotToExport) : toExportOrNotToExport;
	}

	/**
	 * Creates a member that servers as the stub for a missing member on implementation side, corresponding to the given
	 * member <code>apiMember</code> on API side.
	 */
	private N4MemberDeclaration createApiImplStub(N4ClassifierDeclaration classifierDecl, TMember apiMember) {
		// here we create:
		//
		// public m() { // or a getter or setter
		// throw new N4ApiNotImplementedError("API for method C.m not implemented yet.");
		// }
		//
		SymbolTableEntryInternal N4ApiNotImplementedErrorSTE = steFor_N4ApiNotImplementedError();
		String typeName = classifierDecl.getName();
		String memberKeyword = n4jsElementKeywordProvider.keyword(apiMember);
		String memberName = apiMember.getName();
		return _N4MemberDecl(apiMember,
				_ThrowStmnt(
						_NewExpr(_IdentRef(N4ApiNotImplementedErrorSTE), _StringLiteral(
								"API for " + memberKeyword + " " + typeName + "." + memberName
										+ " not implemented yet."))));
	}

	// note: the following uses logic from old transpiler (MissingApiMembersForTranspiler, ScriptApiTracker)
	private MissingApiMembersForTranspiler createMAMFT(TClassifier classifier) {
		ConcreteMembersOrderedForTranspiler cmoft = typeAssistant.getOrCreateCMOFT(classifier);
		return MissingApiMembersForTranspiler.create(containerTypesHelper, scriptApiTracker,
				classifier, cmoft, getState().resource.getScript());
	}
}
