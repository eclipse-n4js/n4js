/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform;

import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.isLegalIdentifier;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.intType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isArrayN;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iteratorEntryType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.promiseType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.dts.utils.DtsUtils;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TTypedElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * For each {@link TypeReferenceNode_IM} in the intermediate model, this transformation will
 * <ol>
 * <li>produce a string representation and store it in property {@link TypeReferenceNode_IM#getCodeToEmit() codeToEmit},
 * and
 * <li>record all types actually referenced by that string in property
 * {@link TypeReferenceNode_IM#getRewiredReferences() rewiredReferences}.
 * </ol>
 */
public class TypeReferenceTransformation extends Transformation {

	private TypeReferenceNode_IM<?> currTypeRefNode = null;
	private StringBuilder currStringBuilder = null;

	private final Map<Type, String> referenceCache = new HashMap<>();

	@Inject
	private TypeAssistant typeAssistant;

	@Inject
	private WorkspaceAccess workspaceAccess;

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
		// ignore
	}

	@Override
	public void transform() {
		for (TypeReferenceNode<?> typeRefNode : collectNodes(getState().im, TypeReferenceNode.class, false)) {
			// if you get a ClassCastException here, it means that not all TypeReferenceNodes were
			// converted to TypeReferenceNode_IMs, which is an error
			TypeReferenceNode_IM<?> typeRefNodeCasted = (TypeReferenceNode_IM<?>) typeRefNode;

			try {
				currTypeRefNode = typeRefNodeCasted;
				currStringBuilder = new StringBuilder();
				convertTypeRefNode(typeRefNodeCasted);
				if (currStringBuilder.length() == 0) {
					// we expect #convertTypeRefNode() to always produce some output code
					// (if the type reference cannot be converted to TypeScript, 'any' or something similar should be
					// emitted)
					throw new IllegalStateException(
							"converting the type reference of a TypeReferenceNode produced empty output code");
				}
				typeRefNodeCasted.setCodeToEmit(currStringBuilder.toString());
			} finally {
				currTypeRefNode = null;
				currStringBuilder = null;
			}
		}
	}

	private void convertTypeRefNode(TypeReferenceNode_IM<?> typeRefNode) {
		var typeRef = getState().info.getOriginalProcessedTypeRef(typeRefNode);

		// special handling for return types
		boolean isReturnType = typeRefNode
				.eContainmentFeature() == N4JSPackage.Literals.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE;
		if (isReturnType) {
			FunctionDefinition funDef = (FunctionDefinition) typeRefNode.eContainer();

			// handle outer return type of asynchronous and/or generator functions
			if ((funDef.isGenerator() || funDef.isAsync())
					&& !N4JSLanguageUtils.hasExpectedSpecialReturnType(typeRef, funDef, getState().builtInTypeScope)) {

				TypeRef outerReturnTypeRef = typeAssistant.getReturnTypeRef(getState(), funDef);
				typeRef = outerReturnTypeRef;
			}
		}

		Type declType = typeRef.getDeclaredType();
		boolean isExtendsClass = typeRefNode
				.eContainmentFeature() == N4JSPackage.Literals.N4_CLASS_DEFINITION__SUPER_CLASS_REF;
		if (isExtendsClass && isArrayN(getState().G, declType)) {
			// special case: ArrayN in extends clause
			String referenceStr = getReferenceToType(declType, getState());
			write(referenceStr);
			convertTypeArguments((ParameterizedTypeRef) typeRef);
		} else {
			// standard case
			convertTypeRef(typeRef);
		}

		if (isReturnType) {
			FunctionDefinition funDef = (FunctionDefinition) typeRefNode.eContainer();
			FunctionDefinition funDefInAST = getState().tracer.getOriginalASTNodeOfSameType(funDef, false);
			if (funDefInAST != null) {
				if (funDefInAST.isReturnValueOptional()) {
					writeSuffixForReturnTypeOfFunctionWithOptionalReturnValue(true);
				}
			}
		}
	}

	private void convertDeclaredTypeRef(TTypedElement elem) {
		TypeRef declaredTypeRef = elem.getTypeRef();
		if (declaredTypeRef != null) {
			write(": ");
			convertTypeRef(declaredTypeRef);
		}
	}

	private void convertTypeRef(TypeRef typeRefRaw) {
		if (typeRefRaw == null) {
			return;
		}

		var typeRef = typeRefRaw;

		// if type aliases are used in the n4js[d] source code, we want the alias to show up in the .d.ts output, so we
		// have to use the original alias type reference instead of the resolved alias type reference here:
		if (typeRef.isAliasResolved()) {
			TypeRef originalAliasTypeRef = typeRef.getOriginalAliasTypeRef();
			if (originalAliasTypeRef != null) {
				typeRef = originalAliasTypeRef;
			}
		}

		if (typeRef instanceof ComposedTypeRef) {
			convertComposedTypeRef((ComposedTypeRef) typeRef);
		} else if (typeRef instanceof FunctionTypeExprOrRef) {
			convertFunctionTypeExprOrRef((FunctionTypeExprOrRef) typeRef);
		} else if (typeRef instanceof ParameterizedTypeRef) {
			convertParameterizedTypeRef((ParameterizedTypeRef) typeRef);
		} else if (typeRef instanceof ThisTypeRef) {
			convertThisTypeRef((ThisTypeRef) typeRef);
		} else if (typeRef instanceof ExistentialTypeRef) {
			write("any"); // unsupported type reference
		} else if (typeRef instanceof TypeTypeRef) {
			write("any"); // unsupported type reference
		} else if (typeRef instanceof LiteralTypeRef) {
			convertLiteralTypeRef((LiteralTypeRef) typeRef);
		} else if (typeRef instanceof UnknownTypeRef) {
			write("any"); // unsupported type reference
		} else {
			throw new IllegalStateException(
					"unknown subclass of " + TypeRef.class.getSimpleName() + ": " + typeRef.getClass().getSimpleName());
		}
	}

	private void convertComposedTypeRef(ComposedTypeRef typeRef) {
		char op;
		if (typeRef instanceof UnionTypeExpression) {
			op = '|';
		} else if (typeRef instanceof IntersectionTypeExpression) {
			op = '&';
		} else {
			throw new IllegalStateException("unknown subclass of " + ComposedTypeRef.class.getSimpleName() + ": "
					+ typeRef.getClass().getSimpleName());
		}
		EList<TypeRef> typeRefs = typeRef.getTypeRefs();
		write(typeRefs, tr -> convertMemberTypeRef(tr), " " + op + " ");
	}

	/** Convert the member of a composed type reference. */
	private void convertMemberTypeRef(TypeRef memberTypeRef) {
		boolean requiresParentheses = memberTypeRef instanceof ComposedTypeRef
				|| memberTypeRef instanceof FunctionTypeExprOrRef;
		if (requiresParentheses) {
			write("(");
		}
		convertTypeRef(memberTypeRef);
		if (requiresParentheses) {
			write(")");
		}
	}

	private void convertFunctionTypeExprOrRef(FunctionTypeExprOrRef typeRef) {
		EList<TFormalParameter> fpars = typeRef.getFpars();
		TypeRef returnTypeRef = typeRef.getReturnTypeRef();
		write("(");
		convertTFormalParameters(fpars);
		write(")=>");
		if (returnTypeRef != null) {
			convertTypeRef(returnTypeRef);
			if (typeRef.isReturnValueOptional()) {
				writeSuffixForReturnTypeOfFunctionWithOptionalReturnValue(false);
			}
		} else {
			// TypeScript's default return type is 'any', so we need to emit 'void' in this case!
			write("void");
		}
	}

	private void convertParameterizedTypeRef(ParameterizedTypeRef typeRef) {
		if (typeRef instanceof FunctionTypeRef) {
			convertFunctionTypeExprOrRef((FunctionTypeRef) typeRef);
			return;
		}
		Type declType = typeRef.getDeclaredType();

		if (isArrayN(getState().G, declType)) {
			convertTypeArguments(typeRef, "[", "]");
			return;
		}

		boolean hasStructMembers = typeRef instanceof ParameterizedTypeRefStructural
				&& !typeRef.getStructuralMembers().isEmpty();
		boolean showDeclaredType = !hasStructMembers
				|| declType != objectType(getState().G);

		if (showDeclaredType && hasStructMembers) {
			write("(");
		}

		if (showDeclaredType) {
			String referenceStr = (declType == null) ? null : getReferenceToType(declType, getState());
			if (referenceStr != null) {
				String prependType = getStructuralTypeReplacements(typeRef);
				write(prependType);

				write(referenceStr);
				convertTypeArguments(typeRef);

				write(Strings.isNullOrEmpty(prependType) ? "" : ">");

			} else {
				write("any");
			}
		}

		if (hasStructMembers) {
			EList<TStructMember> members = typeRef.getStructuralMembers();
			if (showDeclaredType) {
				write(" & ");
			}
			write("{");
			write(members, m -> convertTMember(m), "; "); // ',' would also be allowed as separator
			write("}");
		}

		if (showDeclaredType && hasStructMembers) {
			write(")");
		}
	}

	private void convertThisTypeRef(ThisTypeRef typeRef) {
		String prependType = getStructuralTypeReplacements(typeRef);
		write(prependType);

		write("this");

		write(Strings.isNullOrEmpty(prependType) ? "" : ">");
	}

	private void convertLiteralTypeRef(LiteralTypeRef typeRef) {
		if (typeRef instanceof EnumLiteralTypeRef) {
			TEnum enumType = ((EnumLiteralTypeRef) typeRef).getEnumType();
			String referenceStr = (enumType == null) ? null : getReferenceToType(enumType, getState());
			if (referenceStr != null) {
				write(referenceStr);
				TEnumLiteral value = ((EnumLiteralTypeRef) typeRef).getValue();
				String enumLiteralName = value == null ? null : value.getName();
				EnumKind enumKind = N4JSLanguageUtils.getEnumKind(enumType);
				if (enumLiteralName != null
						&& (enumKind == EnumKind.NumberBased || enumKind == EnumKind.StringBased)) {
					write(".");
					write(enumLiteralName);
				}
			} else {
				write("any");
			}
		} else {
			write(typeRef.getTypeRefAsString());
		}
	}

	private String getStructuralTypeReplacements(TypeRef typeRef) {
		switch (typeRef.getTypingStrategy()) {
		case STRUCTURAL_FIELDS:
			return "StructuralFields<";
		case STRUCTURAL_READ_ONLY_FIELDS:
			return "StructuralReadOnly<";
		case STRUCTURAL_WRITE_ONLY_FIELDS:
			return "StructuralWriteOnly<";
		case STRUCTURAL_FIELD_INITIALIZER:
			return "StructuralInititializers<";
		default:
			return "";
		}
	}

	private void convertTypeArguments(ParameterizedTypeRef typeRef) {
		convertTypeArguments(typeRef, "<", ">");
	}

	private void convertTypeArguments(ParameterizedTypeRef typeRef, String prefix, String suffix) {
		List<TypeArgument> typeArgs = typeRef.getDeclaredTypeArgs();
		if (typeArgs.isEmpty()) {
			return;
		}

		// special handling for certain types
		Type declType = typeRef.getDeclaredType();
		if (declType == promiseType(getState().G) && typeArgs.size() > 1) {
			// Promise in N4JS has more than one type parameter, whereas in TypeScript it has only one
			typeArgs = Lists.newArrayList(typeArgs.get(0));
		}

		write(prefix);
		write(typeArgs, ta -> convertTypeArgument(ta), ", ");
		write(suffix);
	}

	private void convertTypeArgument(TypeArgument typeArg) {
		if (typeArg instanceof Wildcard) {
			TypeRef upperBound = ((Wildcard) typeArg).getDeclaredOrImplicitUpperBound();
			if (upperBound != null) {
				convertTypeArgument(upperBound);
			} else {
				write("any"); // TypeScript does not support lower bounds
			}
			return;
		}

		TypeRef typeRef = (TypeRef) typeArg;

		// type 'undefined' used as type argument in N4JS, corresponds to 'void' in TypeScript:
		if (typeRef.getDeclaredType() == undefinedType(getState().G)) {
			// DISABLED (it leads to compile problems if an upper bound is in effect, because 'void' won't be a subtype
			// of the upper bound, but 'undefined' will be; since we cannot easily find out whether an upper bound is in
			// effect at this point in the code, we disable this special replacement with 'void' for now:

			// write("void");
			// return;
		}

		convertTypeRef(typeRef);
	}

	private void convertTFormalParameters(Iterable<? extends TFormalParameter> fpars) {
		write(fpars, fp -> convertTFormalParameter(fp), ", ");
	}

	private void convertTFormalParameter(TFormalParameter fpar) {
		if (fpar.isVariadic()) {
			write("...");
		}
		String name = fpar.getName();
		if (name != null) {
			write(name);
		} else {
			// Note: the name is mandatory in TypeScript; however, we create synthetic names for fpars in the types
			// builder, so we should never reach this point:
			throw new IllegalStateException("encountered a TFormalParameter without a (synthetic) name");
		}
		if (!fpar.isVariadic() && fpar.isOptional()) {
			write("?");
		}
		TypeRef typeRef = fpar.getTypeRef();
		if (typeRef != null) {
			write(": ");
			convertTypeRef(typeRef);
			if (fpar.isVariadic()) {
				write("[]"); // TypeScript expect an array type
			}
		}
	}

	private void convertTMember(TMember member) {
		if (member instanceof TField) {
			convertTMember((TField) member);
		} else if (member instanceof TGetter) {
			convertTMember((TGetter) member);
		} else if (member instanceof TSetter) {
			convertTMember((TSetter) member);
		} else if (member instanceof TMethod) {
			convertTMember((TMethod) member);
		} else {
			throw new IllegalStateException(
					"unknown subclass of " + TMember.class.getSimpleName() + ": " + member.getClass().getSimpleName());
		}
	}

	private void convertTMember(TField field) {
		writeQuotedIfNonIdentifier(field.getName());
		convertDeclaredTypeRef(field);
	}

	private void convertTMember(TGetter getter) {
		write("get ");
		writeQuotedIfNonIdentifier(getter.getName());
		write("(): ");
		TypeRef typeRef = getter.getTypeRef();
		if (typeRef != null) {
			convertTypeRef(typeRef);
		} else {
			write("any");
		}
	}

	private void convertTMember(TSetter setter) {
		write("set ");
		writeQuotedIfNonIdentifier(setter.getName());
		write("(");
		convertTFormalParameters(Collections.singletonList(setter.getFpar()));
		write(")");
	}

	private void convertTMember(TMethod method) {
		writeQuotedIfNonIdentifier(method.getName());
		write("(");
		convertTFormalParameters(method.getFpars());
		write("): ");
		TypeRef returnTypeRef = method.getReturnTypeRef();
		if (returnTypeRef != null) {
			convertTypeRef(returnTypeRef);
		} else {
			// TypeScript's default return type is 'any', so we need to emit 'void' in this case!
			write("void");
		}
	}

	/**
	 * @param isReturnTypeOfActualFunction
	 *            the caller should pass in {@code true} iff the return type to emit is for an "actual function", i.e. a
	 *            declared function, a method, or a function expression (as opposed to the return type of a pure
	 *            function type expression inside a type annotation).
	 */
	private void writeSuffixForReturnTypeOfFunctionWithOptionalReturnValue(boolean isReturnTypeOfActualFunction) {
		// note: due to limitations in N4JS, the return type of a function with an optional return value cannot be a
		// ComposedTypeRef,
		// so we do not need to bother with parentheses around the actual return type reference
		if (isReturnTypeOfActualFunction) {
			write("|undefined");
		} else {
			write("|void");
		}
	}

	private <T extends EObject> void write(Iterable<? extends T> nodesInIM, Consumer<? super T> processor,
			String separator) {

		Iterator<? extends T> iter = nodesInIM.iterator();
		while (iter.hasNext()) {
			processor.accept(iter.next());
			if (separator != null && iter.hasNext()) {
				write(separator);
			}
		}
	}

	private void write(CharSequence csq) {
		currStringBuilder.append(csq);
	}

	private void writeQuotedIfNonIdentifier(String csq) {
		if (!isLegalIdentifier(csq)) {
			write("'");
			write(csq);
			write("'");
		} else {
			write(csq);
		}
	}

	/**
	 * Returns the textual reference that can be used in the local file to refer to the given type (adding an import for
	 * the given type, if necessary), or <code>null</code> if the type cannot be referred to from the local file.
	 * <p>
	 * The returned string is usually simply the local name of the given type, but includes, if required, also the name
	 * of a namespace and "." as separator.
	 */
	private String getReferenceToType(Type type, TranspilerState state) {
		// note: map 'referenceCache' may contain 'null' as value!
		if (referenceCache.containsKey(type)) {
			return referenceCache.get(type);
		}
		String reference = computeReferenceToType(type, state);
		referenceCache.put(type, reference);
		return reference;
	}

	private String computeReferenceToType(Type type, TranspilerState state) {
		boolean isLocal = isFromSameFileOrStaticPolyfill(type);

		if (!isLocal) {
			if (!DtsUtils.isDtsExportableReference(type, state)) {
				// the type is from a project not available on the .d.ts side, so we cut off the .d.ts export at this
				// reference
				return null;
			}

			boolean isBuiltInOrGlobal = isBuiltInOrGlobal(type);
			if (isBuiltInOrGlobal) {
				// simple case: the type reference points to a built-in type OR a type from a global module
				// -> can simply use its name in output code, because they are global and available everywhere
				if (type == intType(state.G)) {
					return "number";
				} else if (type == iteratorEntryType(state.G)) {
					return "IteratorReturnResult";
				}
				TModule containingModule = type.getContainingModule();
				if (containingModule != null
						&& Objects.equals(containingModule.getSimpleName(), "IntlClasses")
						&& Objects.equals(containingModule.getPackageName(),
								N4JSGlobals.N4JS_RUNTIME_ECMA402.getRawName())) {
					return "Intl." + type.getName();
				}
				return type.getName();
			}
		}

		SymbolTableEntryOriginal ste = getSymbolTableEntryOriginal(type, true);
		if (ste == null) {
			return null;
		}

		// is the type already available?
		var isAvailable = isLocal || ste.getImportSpecifier() != null;
		if (!isAvailable) {
			// no, so try to import it!

			if (type.isExported() && isFromSameProjectOrDirectDependency(type)) {
				// note: no need to check accessibility modifiers in addition to #isExported(), because on
				// TypeScript-side we bump up the accessibility

				String alias = hasNameConflict(ste, ste.getName()) ? findConflictFreeName(ste) : null;

				// add a new named import for this type
				addNamedImport(ste, alias);

				// Try to delete following 3 lines
				// for (IdentifiableElement idElem : state.steCache.mapOriginal.keySet()) {
				// idElem.getName(); // What is this for?
				// }

				ImportSpecifier is = ste.getImportSpecifier();
				if (is instanceof NamedImportSpecifier) {
					NamedImportSpecifier nis = (NamedImportSpecifier) is;
					ste.setName(
							Strings.isNullOrEmpty(nis.getAlias()) ? nis.getImportedElementAsText() : nis.getAlias());
				}
				if (is instanceof NamespaceImportSpecifier) {
					ste.setName(((NamespaceImportSpecifier) is).getAlias());
				}
				isAvailable = ste.getImportSpecifier() != null;
			}
		}

		// is the type now available?
		if (isAvailable) {
			// yes, so ...

			// 1) record that 'currTypeRefNode' is actually referring to 'type'
			recordReferenceToType(currTypeRefNode, ste);

			// 2) compute output code that correctly refers to 'type' from within current file (depending on how the
			// type was imported)
			var referenceStr = "";
			ImportSpecifier importSpec = ste.getImportSpecifier();
			if (importSpec instanceof NamespaceImportSpecifier) {
				String namespaceName = ((NamespaceImportSpecifier) importSpec).getAlias();
				referenceStr = namespaceName + "." + ste.getName();
			} else {
				referenceStr = ste.getName();
			}

			return referenceStr;
		}

		// we tried our best, but this type is not available in the current file AND cannot be made available
		return null;
	}

	private boolean hasNameConflict(SymbolTableEntryOriginal ste, String name) {
		for (IdentifiableElement id : getState().steCache.mapOriginal.keySet()) {
			if (id != ste.getOriginalTarget() && Objects.equals(id.getName(), name)) {
				return true;
			}
		}
		return false;
	}

	private String findConflictFreeName(SymbolTableEntryOriginal ste) {
		var i = 2;
		while (hasNameConflict(ste, ste.getName() + i)) {
			i++;
		}
		return ste.getName() + i;
	}

	private boolean isFromSameFileOrStaticPolyfill(Type type) {
		Resource typeResource = type.eResource();
		if (typeResource == getState().resource) {
			return true;
		}
		TModule module = getState().resource.getModule();
		if (module != null && module.isStaticPolyfillAware()) {
			TModule typeModule = type.getContainingModule();
			return typeModule != null
					&& typeModule.isStaticPolyfillModule()
					&& Objects.equals(typeModule.getQualifiedName(), module.getQualifiedName());
		}
		return false;
	}

	/** True if the given type is located in the local project or a project we directly depend on. */
	private boolean isFromSameProjectOrDirectDependency(Type type) {
		N4JSProjectConfigSnapshot containingProject = workspaceAccess.findProjectContaining(type);
		return containingProject != null
				&& (containingProject == getState().project
						|| getState().project.getDependencies().contains(containingProject.getName()));
	}

	private static boolean isBuiltInOrGlobal(Type type) {
		if (N4Scheme.isFromResourceWithN4Scheme(type)) {
			return true;
		}
		TModule module = EcoreUtil2.getContainerOfType(type, TModule.class);
		return module != null && AnnotationDefinition.GLOBAL.hasAnnotation(module);
	}
}
