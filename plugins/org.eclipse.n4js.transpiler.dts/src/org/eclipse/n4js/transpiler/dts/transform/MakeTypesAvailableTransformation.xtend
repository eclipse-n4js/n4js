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

import com.google.common.collect.Lists
import com.google.inject.Inject
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import java.util.function.Consumer
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.dts.utils.DtsUtils
import org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
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
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TTypedElement
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.workspace.WorkspaceAccess

import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.isLegalIdentifier

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * For all types referenced from a type reference in the intermediate model, this transformation is responsible for
 * making them available in the current module (if not available already) by adding an additional import.
 */
class MakeTypesAvailableTransformation extends Transformation {

	private StringBuilder sb = null;

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
	makeAllTypesAvailable(typeRefNodeCasted)
];


		collectNodes(state.im, TypeReferenceNode, false).forEach[ typeRefNode |
			// if you get a ClassCastException here, it means that not all TypeReferenceNodes were
			// converted to TypeReferenceNode_IMs, which is an error
			val typeRefNodeCasted = typeRefNode as TypeReferenceNode_IM<?>;

			try {
				sb = new StringBuilder();
				convertTypeRefNode(typeRefNodeCasted);
				typeRefNodeCasted.typeRefAsCode = if (sb.length > 0) sb.toString();
			} finally {
				sb = null;
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
				val astNode = state.tracer.getOriginalASTNode(funDef);
				val tFunction = if (astNode instanceof FunctionDefinition) astNode.definedFunction;
				val outerReturnTypeRef = tFunction?.returnTypeRef;
				if (outerReturnTypeRef === null) {
					// FIXME we should not have to rely on the TModule here!
					throw new IllegalStateException("unable to obtain outer return type of generator/async function from TModule");
				}
				typeRef = outerReturnTypeRef;
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

		if (!DtsUtils.isSupportedTypeRef(typeRef)) {
			// unsupported type reference
			write("any");
		} else if (typeRef instanceof ComposedTypeRef) {
			convertComposedTypeRef(typeRef);
		} else if (typeRef instanceof FunctionTypeExprOrRef) {
			convertFunctionTypeExprOrRef(typeRef);
		} else if (typeRef instanceof ParameterizedTypeRef) {
			convertParameterizedTypeRef(typeRef);
		} else if (typeRef instanceof ExistentialTypeRef) {
			// should have been covered by case "unsupported type reference" above!
			throw new IllegalStateException("code not adjusted after DtsUtils#isSupportedTypeRef() was changed");
		} else if (typeRef instanceof ThisTypeRef) {
			// should have been covered by case "unsupported type reference" above!
			throw new IllegalStateException("code not adjusted after DtsUtils#isSupportedTypeRef() was changed");
		} else if (typeRef instanceof TypeTypeRef) {
			// should have been covered by case "unsupported type reference" above!
			throw new IllegalStateException("code not adjusted after DtsUtils#isSupportedTypeRef() was changed");
		} else if (typeRef instanceof UnknownTypeRef) {
			// should have been covered by case "unsupported type reference" above!
			throw new IllegalStateException("code not adjusted after DtsUtils#isSupportedTypeRef() was changed");
		} else {
			throw new IllegalStateException("unknown subclass of " + ComposedTypeRef.simpleName + ": " + typeRef.getClass.simpleName);
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
		val hasStructMembers = typeRef instanceof ParameterizedTypeRefStructural
				&& !typeRef.getStructuralMembers().isEmpty();
		val showDeclaredType = !hasStructMembers
				|| declType !== state.G.objectType;

		if (showDeclaredType && hasStructMembers) {
			write('(');
		}

		if (showDeclaredType) {
			if (DtsUtils.isDtsExportableDependency(declType, state)) {
				// FIXME is there a better way? (maybe via a symbol table entry as in
				// PrettyPrinterSwitch#caseIdentifierRef())
				val referenceStr = if (declType !== null) {
					DtsUtils.getReferenceToTypeIfLocallyAvailable(declType, typeRef.getDefinedTypingStrategy(), state)
				};
				if (referenceStr !== null) {
					write(referenceStr);
					convertTypeArguments(typeRef);
				} else {
					write("any");
				}
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

	def private void convertTypeArguments(ParameterizedTypeRef typeRef) {
		convertTypeArguments(typeRef, "<", ">");
	}

	def private void convertTypeArguments(ParameterizedTypeRef typeRef, String prefix, String suffix) {
		var List<TypeArgument> typeArgs = typeRef.getTypeArgs();
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
		sb.append(csq);
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



// -----

	def private void makeAllTypesAvailable(TypeReferenceNode_IM<?> typeRefNode) {
		val typeRef = state.info.getOriginalProcessedTypeRef(typeRefNode);
		if (typeRef !== null) {
			makeAllTypesAvailable(typeRefNode, typeRef, new HashSet());
		}
	}

	def private void makeAllTypesAvailable(TypeReferenceNode_IM<?> typeRefNode, TypeRef typeRef, Set<TypeRef> visited) {
		if (visited.contains(typeRef)) {
			return; // FIXME infinite recursion???
		}
		visited.add(typeRef);
		TypeUtils.forAllTypeRefs(typeRef, ParameterizedTypeRef, true, true, [mustBeIgnored], [ ptr |
			val declType = ptr.declaredType;
			if (declType !== null) {
				makeTypeAvailable(typeRefNode, declType);
			}
			for (typeArg : ptr.typeArgs) {
				if (typeArg instanceof Wildcard) {
					val upperBound = typeArg.getDeclaredOrImplicitUpperBound();
					if (upperBound !== null) {
						makeAllTypesAvailable(typeRefNode, upperBound, visited);
					}
				}
			}
			return true; // always continue
		], null);
	}

	def private void makeTypeAvailable(TypeReferenceNode_IM<?> typeRefNode, Type type) {
		if (!DtsUtils.isDtsExportableDependency(type, state)) {
			// the type is from a project not available on the .d.ts side, so the .d.ts export will be
			// cut off at this reference and thus we need not (and cannot) make this type available
			return;
		}
		val isAlreadyAvailable = DtsUtils.getReferenceToTypeIfLocallyAvailable(type, null, state) !== null;
		if (isAlreadyAvailable) {
			// the type is already available, but we have to make sure its import (if any) won't be removed as unused
			// even if all other usages will be removed (e.g. if they are in expressions/statements):
			val ste = getSymbolTableEntryOriginal(type, false);
			if (ste !== null) {
				recordReferenceToType(typeRefNode, ste);
			}
			return;
		}
		// the type is not yet available
		if (type.exported) {
			// no need to check accessibility modifiers in addition to #isExported(), because on TypeScript-side we bump up the accessibility
			val declTypeProject = workspaceAccess.findProjectContaining(type);
			if (declTypeProject !== null) {
				if (declTypeProject === state.project
					|| state.project.dependencies.contains(declTypeProject.name)) {
					// the type reference points to an exported type in the same project or a project we directly depend on
					// --> we can add an import for this type
					val ste = addNamedImport(type, null); // FIXME use alias if name already in use!
					recordReferenceToType(typeRefNode, ste);
					return;
				}
			}
		}
		// we tried our best, but this type cannot be made available
		// --> the PrettyPrinterTypeRef will replace it with 'any'
	}

	def private boolean mustBeIgnored(EObject obj) {
		if (obj instanceof TypeRef) {
			return !DtsUtils.isSupportedTypeRef(obj);
		}
		return false;
	}
}
