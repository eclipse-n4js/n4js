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
package org.eclipse.n4js.transpiler.dts.print;

import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.isLegalIdentifier;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.dts.utils.DtsUtils;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TTypedElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.common.collect.Lists;

/**
 * Emits an N4JS type reference to TypeScript.
 */
/* package */ class PrettyPrinterTypeRef {

	private final PrettyPrinterDts delegate;
	private final TranspilerState state;

	/** Creates a new {@link PrettyPrinterTypeRef}. */
	public PrettyPrinterTypeRef(PrettyPrinterDts delegate, TranspilerState state) {
		this.delegate = delegate;
		this.state = state;
	}

	public void processReturnType(FunctionDefinition funDef, String suffix) {
		TypeReferenceNode<?> declaredReturnTypeRefNode = funDef.getDeclaredReturnTypeRefNode();
		TypeRef returnTypeRef = declaredReturnTypeRefNode != null
				? state.info.getOriginalProcessedTypeRef(declaredReturnTypeRefNode)
				: null;

		if (returnTypeRef == null) {
			// implicit return type in TypeScript is 'any', so we have to explicitly emit 'void' here:
			returnTypeRef = RuleEnvironmentExtensions.voidTypeRef(state.G);
		}

		// handle outer return type of asynchronous and/or generator functions
		if ((funDef.isGenerator() || funDef.isAsync())
				&& !N4JSLanguageUtils.hasExpectedSpecialReturnType(returnTypeRef, funDef, state.builtInTypeScope)) {
			EObject astNode = state.tracer.getOriginalASTNode(funDef);
			TFunction tFunction = astNode instanceof FunctionDefinition
					? ((FunctionDefinition) astNode).getDefinedFunction()
					: null;
			TypeRef outerReturnTypeRef = tFunction != null ? tFunction.getReturnTypeRef() : null;
			if (outerReturnTypeRef != null) {
				returnTypeRef = outerReturnTypeRef;
			} else {
				throw new IllegalStateException("TODO");
			}
		}

		write(": ");
		processTypeRef(returnTypeRef, suffix);
	}

	/** Emit a type reference. */
	public void processTypeRefNode(TypeReferenceNode<?> typeRefNode, String suffix) {
		TypeRef typeRef = state.info.getOriginalProcessedTypeRef(typeRefNode);
		processTypeRef(typeRef, suffix);
	}

	private void processTypeRef(TypeRef typeRef, String suffix) {
		if (typeRef == null) {
			return; // do not emit 'suffix' in this case
		}
		processTypeRef(typeRef);
		write(suffix);
	}

	private void processTypeRef(TypeRef typeRef) {
		if (typeRef == null) {
			return;
		}

		// if type aliases are used in the n4js[d] source code, we want the alias to show up in the .d.ts output, so we
		// have to use the original alias type reference instead of the resolved alias type reference here:
		if (typeRef.isAliasResolved()) {
			TypeRef originalAliasTypeRef = typeRef.getOriginalAliasTypeRef();
			if (originalAliasTypeRef != null) {
				typeRef = originalAliasTypeRef;
			}
		}

		if (typeRef instanceof ComposedTypeRef) {
			processComposedTypeRef((ComposedTypeRef) typeRef);
		} else if (typeRef instanceof FunctionTypeExprOrRef) {
			processFunctionTypeExprOrRef((FunctionTypeExprOrRef) typeRef);
		} else if (typeRef instanceof ParameterizedTypeRef) {
			processParameterizedTypeRef((ParameterizedTypeRef) typeRef);
		} else if (typeRef instanceof ExistentialTypeRef) {
			// TODO
			write("any");
		} else if (typeRef instanceof ThisTypeRef) {
			// TODO
			write("any");
		} else if (typeRef instanceof TypeTypeRef) {
			// TODO
			write("any");
		} else if (typeRef instanceof UnknownTypeRef) {
			// TODO
			write("any");
		} else {
			throw new IllegalStateException("unknown subclass of " + ComposedTypeRef.class.getSimpleName() + ": "
					+ typeRef.getClass().getSimpleName());
		}
	}

	private void processComposedTypeRef(ComposedTypeRef typeRef) {
		char op;
		if (typeRef instanceof UnionTypeExpression) {
			op = '|';
		} else if (typeRef instanceof IntersectionTypeExpression) {
			op = '&';
		} else {
			throw new IllegalStateException("unknown subclass of " + ComposedTypeRef.class.getSimpleName() + ": "
					+ typeRef.getClass().getSimpleName());
		}
		List<TypeRef> typeRefs = typeRef.getTypeRefs();
		process(typeRefs, this::processMemberTypeRef, " " + op + " ");
	}

	/** Process the member of a composed type reference. */
	private void processMemberTypeRef(TypeRef memberTypeRef) {
		boolean requiresParentheses = memberTypeRef instanceof ComposedTypeRef
				|| memberTypeRef instanceof FunctionTypeExprOrRef;
		if (requiresParentheses) {
			write('(');
		}
		processTypeRef(memberTypeRef);
		if (requiresParentheses) {
			write(')');
		}
	}

	private void processFunctionTypeExprOrRef(FunctionTypeExprOrRef typeRef) {
		EList<TFormalParameter> fpars = typeRef.getFpars();
		TypeRef returnTypeRef = typeRef.getReturnTypeRef();
		write('(');
		processTFormalParameters(fpars);
		write(")=>");
		if (returnTypeRef != null) {
			processTypeRef(returnTypeRef);
		} else {
			write("void");
		}
	}

	private void processParameterizedTypeRef(ParameterizedTypeRef typeRef) {
		if (typeRef instanceof FunctionTypeRef) {
			processFunctionTypeExprOrRef((FunctionTypeRef) typeRef);
			return;
		}

		Type declType = typeRef.getDeclaredType();
		boolean hasStructMembers = typeRef instanceof ParameterizedTypeRefStructural
				&& !typeRef.getStructuralMembers().isEmpty();
		boolean showDeclaredType = !hasStructMembers
				|| declType != RuleEnvironmentExtensions.objectType(state.G);

		if (showDeclaredType && hasStructMembers) {
			write('(');
		}

		if (showDeclaredType) {
			if (DtsUtils.isDtsExportableDependency(declType, state)) {
				// FIXME is there a better way? (maybe via a symbol table entry as in
				// PrettyPrinterSwitch#caseIdentifierRef())
				String referenceStr = declType != null
						? DtsUtils.getReferenceToTypeIfLocallyAvailable(declType, state)
						: null;
				if (referenceStr != null) {
					write(referenceStr);
					processTypeArguments(typeRef);
				} else {
					write("any");
				}
			} else {
				write("any");
			}
		}

		if (hasStructMembers) {
			List<TStructMember> members = typeRef.getStructuralMembers();
			if (showDeclaredType) {
				write(" & ");
			}
			write("{");
			process(members, this::processTMember, "; "); // ',' would also be allowed as separator
			write("}");
		}

		if (showDeclaredType && hasStructMembers) {
			write(')');
		}
	}

	private void processTypeArguments(ParameterizedTypeRef typeRef) {
		processTypeArguments(typeRef, "<", ">");
	}

	private void processTypeArguments(ParameterizedTypeRef typeRef, String prefix, String suffix) {
		List<TypeArgument> typeArgs = typeRef.getTypeArgs();
		if (typeArgs.isEmpty()) {
			return;
		}

		// special handling for certain types
		Type declType = typeRef.getDeclaredType();
		if (declType == RuleEnvironmentExtensions.promiseType(state.G) && typeArgs.size() > 1) {
			// Promise in N4JS has more than one type parameter, whereas in TypeScript it has only one
			typeArgs = Lists.newArrayList(typeArgs.get(0));
		}

		write(prefix);
		process(typeArgs, this::processTypeArgument, ", ");
		write(suffix);
	}

	private void processTypeArgument(TypeArgument typeArg) {
		if (typeArg instanceof Wildcard) {
			Wildcard wildcard = (Wildcard) typeArg;
			TypeRef upperBound = wildcard.getDeclaredOrImplicitUpperBound();
			if (upperBound != null) {
				processTypeArgument(upperBound);
			} else {
				write("any"); // TypeScript does not support lower bounds
			}
			return;
		}

		TypeRef typeRef = (TypeRef) typeArg;

		// type 'undefined' used as type argument in N4JS, corresponds to 'void' in TypeScript:
		if (typeRef.getDeclaredType() == RuleEnvironmentExtensions.undefinedType(state.G)) {
			// DISABLED (it leads to compile problems if an upper bound is in effect, because 'void' won't be a subtype
			// of the upper bound, but 'undefined' will be; since we cannot easily find out whether an upper bound is in
			// effect at this point in the code, we disable this special replacement with 'void' for now:

			// write("void");
			// return;
		}

		processTypeRef(typeRef);
	}

	private void processTFormalParameters(Iterable<? extends TFormalParameter> fpars) {
		process(fpars, this::processTFormalParameter, ", ");
	}

	private void processTFormalParameter(TFormalParameter fpar) {
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
			write('?');
		}
		TypeRef typeRef = fpar.getTypeRef();
		if (typeRef != null) {
			write(": ");
			processTypeRef(typeRef);
			if (fpar.isVariadic()) {
				write("[]"); // TypeScript expect an array type
			}
		}
	}

	private void processTMember(TMember member) {
		if (member instanceof TField) {
			processTField((TField) member);
		} else if (member instanceof TGetter) {
			processTGetter((TGetter) member);
		} else if (member instanceof TSetter) {
			processTSetter((TSetter) member);
		} else if (member instanceof TMethod) {
			processTMethod((TMethod) member);
		} else {
			throw new IllegalStateException("unknown subclass of " + TMember.class.getSimpleName() + ": "
					+ member.getClass().getSimpleName());
		}
	}

	private void processTField(TField field) {
		writeQuotedIfNonIdentifier(field.getName());
		processDeclaredTypeRef(field, "");
	}

	private void processTGetter(TGetter getter) {
		write("get ");
		writeQuotedIfNonIdentifier(getter.getName());
		write("(): ");
		TypeRef typeRef = getter.getTypeRef();
		if (typeRef != null) {
			processTypeRef(typeRef);
		} else {
			write("any");
		}
	}

	private void processTSetter(TSetter setter) {
		write("set ");
		writeQuotedIfNonIdentifier(setter.getName());
		write("(");
		processTFormalParameters(Collections.singletonList(setter.getFpar()));
		write(")");
	}

	private void processTMethod(TMethod method) {
		writeQuotedIfNonIdentifier(method.getName());
		write("(");
		processTFormalParameters(method.getFpars());
		write("): ");
		TypeRef returnTypeRef = method.getReturnTypeRef();
		if (returnTypeRef != null) {
			processTypeRef(returnTypeRef);
		} else {
			// TypeScript's default return type is 'any', so we need to emit 'void' in this case!
			write("void");
		}
	}

	// ###############################################################################################################
	// UTILITY AND CONVENIENCE METHODS

	private void processDeclaredTypeRef(TTypedElement elem, String suffix) {
		TypeRef declaredTypeRef = elem.getTypeRef();
		if (declaredTypeRef != null) {
			write(": ");
			processTypeRef(declaredTypeRef, suffix);
		}
	}

	private <T extends EObject> void process(Iterable<? extends T> nodesInIM, Consumer<? super T> processor,
			String separator) {
		final Iterator<? extends T> iter = nodesInIM.iterator();
		while (iter.hasNext()) {
			processor.accept(iter.next());
			if (separator != null && iter.hasNext()) {
				write(separator);
			}
		}
	}

	private void write(char c) {
		delegate.write(c);
	}

	private void write(CharSequence csq) {
		delegate.write(csq);
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
}
