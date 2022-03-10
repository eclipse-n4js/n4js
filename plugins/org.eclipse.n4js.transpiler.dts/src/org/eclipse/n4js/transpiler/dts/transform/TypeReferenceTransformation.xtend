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
package org.eclipse.n4js.transpiler.dts.transform

import com.google.common.base.Strings
import com.google.common.collect.Lists
import com.google.inject.Inject
import java.util.Collections
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Objects
import java.util.function.Consumer
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.scoping.builtin.N4Scheme
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TranspilerState
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.dts.utils.DtsUtils
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TTypedElement
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind
import org.eclipse.n4js.workspace.WorkspaceAccess
import org.eclipse.xtext.EcoreUtil2

import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.isLegalIdentifier

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * For each {@link TypeReferenceNode_IM} in the intermediate model, this transformation will
 * <ol>
 * <li>produce a string representation and store it in property {@link TypeReferenceNode_IM#getCodeToEmit() codeToEmit}, and
 * <li>record all types actually referenced by that string in property {@link TypeReferenceNode_IM#getRewiredReferences() rewiredReferences}.
 * </ol>
 */
class TypeReferenceTransformation extends Transformation {

	private TypeReferenceNode_IM<?> currTypeRefNode = null;
	private StringBuilder currStringBuilder = null;

	private final Map<Type, String> referenceCache = new HashMap();

	@Inject
	private TypeAssistant typeAssistant;
	
	@Inject
	private WorkspaceAccess workspaceAccess;

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, TypeReferenceNode, false).forEach[ typeRefNode |
			// if you get a ClassCastException here, it means that not all TypeReferenceNodes were
			// converted to TypeReferenceNode_IMs, which is an error
			val typeRefNodeCasted = typeRefNode as TypeReferenceNode_IM<?>;

			try {
				currTypeRefNode = typeRefNodeCasted;
				currStringBuilder = new StringBuilder();
				convertTypeRefNode(typeRefNodeCasted);
				if (currStringBuilder.length === 0) {
					// we expect #convertTypeRefNode() to always produce some output code
					// (if the type reference cannot be converted to TypeScript, 'any' or something similar should be emitted)
					throw new IllegalStateException("converting the type reference of a TypeReferenceNode produced empty output code");
				}
				typeRefNodeCasted.codeToEmit = currStringBuilder.toString();
			} finally {
				currTypeRefNode = null;
				currStringBuilder = null;
			}
		];
	}

	def private void convertTypeRefNode(TypeReferenceNode_IM<?> typeRefNode) {
		var typeRef = state.info.getOriginalProcessedTypeRef(typeRefNode);

		// special handling for return types
		val isReturnType = typeRefNode.eContainmentFeature === N4JSPackage.Literals.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE;
		if (isReturnType) {
			val funDef = typeRefNode.eContainer as FunctionDefinition;

			// handle outer return type of asynchronous and/or generator functions
			if ((funDef.isGenerator() || funDef.isAsync())
					&& !N4JSLanguageUtils.hasExpectedSpecialReturnType(typeRef, funDef, state.builtInTypeScope)) {
				
				val outerReturnTypeRef = typeAssistant.getReturnTypeRef(state, funDef);
				typeRef = outerReturnTypeRef;
			}
		}

		val declType = typeRef.getDeclaredType();
		if (state.G.isArrayN(declType)) {
			val isExtendsClass = typeRefNode.eContainmentFeature === N4JSPackage.Literals.N4_CLASS_DEFINITION__SUPER_CLASS_REF;
			if (isExtendsClass) {
				val referenceStr = getReferenceToType(declType, state);
				write(referenceStr);
				convertTypeArguments(typeRef as ParameterizedTypeRef);
				return;
			}
		}
		
		convertTypeRef(typeRef);
	}
	
	def private void convertDeclaredTypeRef(TTypedElement elem) {
		val declaredTypeRef = elem.getTypeRef();
		if (declaredTypeRef !== null) {
			write(": ");
			convertTypeRef(declaredTypeRef);
		}
	}

	def private void convertTypeRef(TypeRef typeRefRaw) {
		if (typeRefRaw === null) {
			return;
		}

		var typeRef = typeRefRaw;

		// if type aliases are used in the n4js[d] source code, we want the alias to show up in the .d.ts output, so we
		// have to use the original alias type reference instead of the resolved alias type reference here:
		if (typeRef.isAliasResolved()) {
			val originalAliasTypeRef = typeRef.getOriginalAliasTypeRef();
			if (originalAliasTypeRef !== null) {
				typeRef = originalAliasTypeRef;
			}
		}

		if (typeRef instanceof ComposedTypeRef) {
			convertComposedTypeRef(typeRef);
		} else if (typeRef instanceof FunctionTypeExprOrRef) {
			convertFunctionTypeExprOrRef(typeRef);
		} else if (typeRef instanceof ParameterizedTypeRef) {
			convertParameterizedTypeRef(typeRef);
		} else if (typeRef instanceof ThisTypeRef) {
			convertThisTypeRef(typeRef);
		} else if (typeRef instanceof ExistentialTypeRef) {
			write("any"); // unsupported type reference
		} else if (typeRef instanceof TypeTypeRef) {
			write("any"); // unsupported type reference
		} else if (typeRef instanceof LiteralTypeRef) {
			convertLiteralTypeRef(typeRef);
		} else if (typeRef instanceof UnknownTypeRef) {
			write("any"); // unsupported type reference
		} else {
			throw new IllegalStateException("unknown subclass of " + TypeRef.simpleName + ": " + typeRef.getClass.simpleName);
		}
	}

	def private void convertComposedTypeRef(ComposedTypeRef typeRef) {
		var char op;
		if (typeRef instanceof UnionTypeExpression) {
			op = '|';
		} else if (typeRef instanceof IntersectionTypeExpression) {
			op = '&';
		} else {
			throw new IllegalStateException("unknown subclass of " + ComposedTypeRef.simpleName + ": " + typeRef.getClass.simpleName);
		}
		val typeRefs = typeRef.getTypeRefs();
		write(typeRefs, [convertMemberTypeRef], " " + op + " ");
	}

	/** Convert the member of a composed type reference. */
	def private void convertMemberTypeRef(TypeRef memberTypeRef) {
		val requiresParentheses = memberTypeRef instanceof ComposedTypeRef
				|| memberTypeRef instanceof FunctionTypeExprOrRef;
		if (requiresParentheses) {
			write('(');
		}
		convertTypeRef(memberTypeRef);
		if (requiresParentheses) {
			write(')');
		}
	}

	def private void convertFunctionTypeExprOrRef(FunctionTypeExprOrRef typeRef) {
		val fpars = typeRef.getFpars();
		val returnTypeRef = typeRef.getReturnTypeRef();
		write('(');
		convertTFormalParameters(fpars);
		write(")=>");
		if (returnTypeRef !== null) {
			convertTypeRef(returnTypeRef);
		} else {
			// TypeScript's default return type is 'any', so we need to emit 'void' in this case!
			write("void");
		}
	}

	def private void convertParameterizedTypeRef(ParameterizedTypeRef typeRef) {
		if (typeRef instanceof FunctionTypeRef) {
			convertFunctionTypeExprOrRef(typeRef);
			return;
		}
		val declType = typeRef.getDeclaredType();
		
		if (state.G.isArrayN(declType)) {
			convertTypeArguments(typeRef, "[", "]");
			return;
		}
		
		val hasStructMembers = typeRef instanceof ParameterizedTypeRefStructural
				&& !typeRef.getStructuralMembers().isEmpty();
		val showDeclaredType = !hasStructMembers
				|| declType !== state.G.objectType;

		if (showDeclaredType && hasStructMembers) {
			write('(');
		}

		if (showDeclaredType) {
			val referenceStr = if (declType !== null) {
				getReferenceToType(declType, state);
			};
			if (referenceStr !== null) {
				val prependType = getStructuralTypeReplacements(typeRef);
				write(prependType);

				write(referenceStr);
				convertTypeArguments(typeRef);

				write(prependType.isNullOrEmpty ? "" : ">");

			} else {
				write("any");
			}
		}

		if (hasStructMembers) {
			val members = typeRef.getStructuralMembers();
			if (showDeclaredType) {
				write(" & ");
			}
			write("{");
			write(members, [convertTMember], "; "); // ',' would also be allowed as separator
			write("}");
		}

		if (showDeclaredType && hasStructMembers) {
			write(')');
		}
	}
	
	def private void convertThisTypeRef(ThisTypeRef typeRef) {
		val prependType = getStructuralTypeReplacements(typeRef);
		write(prependType);
		
		write("this");
		
		write(prependType.isNullOrEmpty ? "" : ">");
	}

	def private void convertLiteralTypeRef(LiteralTypeRef typeRef) {
		if (typeRef instanceof EnumLiteralTypeRef) {
			val enumType = typeRef.enumType;
			val referenceStr = if (enumType !== null) getReferenceToType(enumType, state);
			if (referenceStr !== null) {
				write(referenceStr);
				val enumLiteralName = typeRef.value?.name;
				val enumKind = N4JSLanguageUtils.getEnumKind(enumType);
				if (enumLiteralName !== null
						&& (enumKind === EnumKind.NumberBased || enumKind === EnumKind.StringBased)) {
					write('.');
					write(enumLiteralName);
				}
			} else {
				write("any");
			}
		} else {
			write(typeRef.getTypeRefAsString());
		}
	}	

	def private String getStructuralTypeReplacements(TypeRef typeRef) {
		return switch (typeRef.getTypingStrategy()) {
					case TypingStrategy.STRUCTURAL_FIELDS: "StructuralFields<"
					case TypingStrategy.STRUCTURAL_READ_ONLY_FIELDS: "StructuralReadOnly<"
					case TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS: "StructuralWriteOnly<"
					case TypingStrategy.STRUCTURAL_FIELD_INITIALIZER: "StructuralInititializers<"
					default: ""
				};
	}

	def private void convertTypeArguments(ParameterizedTypeRef typeRef) {
		convertTypeArguments(typeRef, "<", ">");
	}

	def private void convertTypeArguments(ParameterizedTypeRef typeRef, String prefix, String suffix) {
		var List<TypeArgument> typeArgs = typeRef.getDeclaredTypeArgs();
		if (typeArgs.isEmpty()) {
			return;
		}

		// special handling for certain types
		val declType = typeRef.getDeclaredType();
		if (declType === state.G.promiseType && typeArgs.size() > 1) {
			// Promise in N4JS has more than one type parameter, whereas in TypeScript it has only one
			typeArgs = Lists.newArrayList(typeArgs.get(0));
		}

		write(prefix);
		write(typeArgs, [convertTypeArgument], ", ");
		write(suffix);
	}

	def private void convertTypeArgument(TypeArgument typeArg) {
		if (typeArg instanceof Wildcard) {
			val upperBound = typeArg.getDeclaredOrImplicitUpperBound();
			if (upperBound !== null) {
				convertTypeArgument(upperBound);
			} else {
				write("any"); // TypeScript does not support lower bounds
			}
			return;
		}

		val typeRef = typeArg as TypeRef;

		// type 'undefined' used as type argument in N4JS, corresponds to 'void' in TypeScript:
		if (typeRef.getDeclaredType() == state.G.undefinedType) {
			// DISABLED (it leads to compile problems if an upper bound is in effect, because 'void' won't be a subtype
			// of the upper bound, but 'undefined' will be; since we cannot easily find out whether an upper bound is in
			// effect at this point in the code, we disable this special replacement with 'void' for now:

			// write("void");
			// return;
		}

		convertTypeRef(typeRef);
	}

	def private void convertTFormalParameters(Iterable<? extends TFormalParameter> fpars) {
		write(fpars, [convertTFormalParameter], ", ");
	}

	def private void convertTFormalParameter(TFormalParameter fpar) {
		if (fpar.isVariadic()) {
			write("...");
		}
		val name = fpar.getName();
		if (name !== null) {
			write(name);
		} else {
			// Note: the name is mandatory in TypeScript; however, we create synthetic names for fpars in the types
			// builder, so we should never reach this point:
			throw new IllegalStateException("encountered a TFormalParameter without a (synthetic) name");
		}
		if (!fpar.isVariadic() && fpar.isOptional()) {
			write('?');
		}
		val typeRef = fpar.getTypeRef();
		if (typeRef !== null) {
			write(": ");
			convertTypeRef(typeRef);
			if (fpar.isVariadic()) {
				write("[]"); // TypeScript expect an array type
			}
		}
	}

	def private dispatch void convertTMember(TMember member) {
		throw new IllegalStateException("unknown subclass of " + TMember.simpleName + ": " + member.getClass.simpleName);
	}

	def private dispatch void convertTMember(TField field) {
		writeQuotedIfNonIdentifier(field.getName());
		convertDeclaredTypeRef(field);
	}

	def private dispatch void convertTMember(TGetter getter) {
		write("get ");
		writeQuotedIfNonIdentifier(getter.getName());
		write("(): ");
		val typeRef = getter.getTypeRef();
		if (typeRef !== null) {
			convertTypeRef(typeRef);
		} else {
			write("any");
		}
	}

	def private dispatch void convertTMember(TSetter setter) {
		write("set ");
		writeQuotedIfNonIdentifier(setter.getName());
		write("(");
		convertTFormalParameters(Collections.singletonList(setter.getFpar()));
		write(")");
	}

	def private dispatch void convertTMember(TMethod method) {
		writeQuotedIfNonIdentifier(method.getName());
		write("(");
		convertTFormalParameters(method.getFpars());
		write("): ");
		val returnTypeRef = method.getReturnTypeRef();
		if (returnTypeRef !== null) {
			convertTypeRef(returnTypeRef);
		} else {
			// TypeScript's default return type is 'any', so we need to emit 'void' in this case!
			write("void");
		}
	}

	def private <T extends EObject> void write(Iterable<? extends T> nodesInIM, Consumer<? super T> processor, String separator) {
		val iter = nodesInIM.iterator();
		while (iter.hasNext()) {
			processor.accept(iter.next());
			if (separator !== null && iter.hasNext()) {
				write(separator);
			}
		}
	}

	def private void write(CharSequence csq) {
		currStringBuilder.append(csq);
	}

	def private void writeQuotedIfNonIdentifier(String csq) {
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
	def private String getReferenceToType(Type type, TranspilerState state) {
		// note: map 'referenceCache' may contain 'null' as value!
		if (referenceCache.containsKey(type)) {
			return referenceCache.get(type);
		}
		val reference = computeReferenceToType(type, state);
		referenceCache.put(type, reference);
		return reference;
	}

	def private String computeReferenceToType(Type type, TranspilerState state) {
		val isLocal = isFromSameFileOrStaticPolyfill(type);

		if (!isLocal) {

			if (!DtsUtils.isDtsExportableReference(type, state)) {
				// the type is from a project not available on the .d.ts side, so we cut off the .d.ts export at this reference
				return null;
			}

			val isBuiltInOrGlobal = isBuiltInOrGlobal(type);
			if (isBuiltInOrGlobal) {
				// simple case: the type reference points to a built-in type OR a type from a global module
				// -> can simply use its name in output code, because they are global and available everywhere
				if (type == state.G.intType) {
					return "number";
				} else if (type == state.G.iteratorEntryType) {
					return "IteratorReturnResult";
				}
				val containingModule = type.containingModule;
				if (containingModule instanceof TModule
						&& containingModule.simpleName == "IntlClasses"
						&& (containingModule as TModule).packageName == N4JSGlobals.N4JS_RUNTIME_ECMA402.rawName) {
					return "Intl." + type.name;
				}
				return type.name;
			}

		}

		val ste = getSymbolTableEntryOriginal(type, true);
		if (ste === null) {
			return null;
		}

		// is the type already available?
		var isAvailable = isLocal || ste.importSpecifier !== null;
		if (!isAvailable) {
			// no, so try to import it!

// FIXME reconsider use of directlyExported!!!
			if (type.directlyExported && isFromSameProjectOrDirectDependency(type)) {
				// note: no need to check accessibility modifiers in addition to #isExported(), because on TypeScript-side we bump up the accessibility

				val alias = hasNameConflict(ste, ste.name)? findConflictFreeName(ste) : null;

				// add a new named import for this type
				addNamedImport(ste, alias);
				
				
				for (idElem : state.steCache.mapOriginal.keySet) {
					idElem.name
				}

				val is = ste.importSpecifier;
				ste.name = switch (is) {
					NamedImportSpecifier: Strings.isNullOrEmpty(is.alias) ? is.importedElementAsText : is.alias
					NamespaceImportSpecifier: is.alias
				}
				isAvailable = ste.importSpecifier !== null;
			}
		}

		// is the type now available?
		if (isAvailable) {
			// yes, so ...

			// 1) record that 'currTypeRefNode' is actually referring to 'type'
			recordReferenceToType(currTypeRefNode, ste);

			// 2) compute output code that correctly refers to 'type' from within current file (depending on how the type was imported)
			var referenceStr = "";
			val importSpec = ste.getImportSpecifier();
			if (importSpec instanceof NamespaceImportSpecifier) {
				val namespaceName = importSpec.getAlias();
				referenceStr = namespaceName + "." + ste.getName();
			} else {
				referenceStr = ste.getName();
			}

			return referenceStr;
		}

		// we tried our best, but this type is not available in the current file AND cannot be made available
		return null;
	}
	
	def private boolean hasNameConflict(SymbolTableEntryOriginal ste, String name) {
		return state.steCache.mapOriginal.keySet.exists[
			it != ste.originalTarget && Objects.equals(it.name, name)
		];
	}
	
	def private String findConflictFreeName(SymbolTableEntryOriginal ste) {
		var i = 2;
		while (hasNameConflict(ste, ste.name + i)) {
			i++;
		}
		return ste.name + i;
	}

	def private boolean isFromSameFileOrStaticPolyfill(Type type) {
		val typeResource = type.eResource;
		if (typeResource === state.resource) {
			return true;
		}
		val module = state.resource.module;
		if (module !== null && module.isStaticPolyfillAware) {
			val typeModule = type.containingModule;
			return typeModule !== null
				&& typeModule.isStaticPolyfillModule
				&& typeModule.qualifiedName == module.qualifiedName;
		}
		return false;
	}

	/** Tells whether the given type is located in the local project or a project we directly depend on. */
	def private boolean isFromSameProjectOrDirectDependency(Type type) {
		val containingProject = workspaceAccess.findProjectContaining(type);
		return containingProject !== null
			&& (containingProject === state.project
				|| state.project.dependencies.contains(containingProject.name));
	}

	def private static boolean isBuiltInOrGlobal(Type type) {
		if (N4Scheme.isFromResourceWithN4Scheme(type)) {
			return true;
		}
		val module = EcoreUtil2.getContainerOfType(type, TModule);
		return module !== null && AnnotationDefinition.GLOBAL.hasAnnotation(module);
	}
}
