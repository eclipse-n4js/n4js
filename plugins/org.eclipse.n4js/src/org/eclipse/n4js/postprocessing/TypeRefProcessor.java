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
package org.eclipse.n4js.postprocessing;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.NestedTypeRefsSwitch;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.EcoreUtilN4;

import com.google.inject.Inject;

/**
 * Processor for converting the raw type references provided by the parser into valid type references that can be used
 * internally.
 * <p>
 * Most type references created by the parser are already valid and can be used directly; the only exceptions are:
 * <ul>
 * <li>references to the literal of an enum must be converted to an {@link EnumLiteralTypeRef}.
 * <li>{@link TypeRef#isAliasUnresolved() unresolved} references to type aliases must be converted to
 * {@link TypeRef#isAliasResolved() resolved} references to type aliases.
 * </ul>
 */
class TypeRefProcessor extends AbstractProcessor {

	@Inject
	private TypeSystemHelper tsh;

	@SuppressWarnings("unused")
	void handleTypeRefs(RuleEnvironment G, EObject astNode, ASTMetaInfoCache cache) {
		handleTypeRefsInAST(G, astNode);
		handleTypeRefsInTModule(G, astNode);
	}

	private void handleTypeRefsInAST(RuleEnvironment G, EObject astNode) {
		for (TypeReferenceNode<?> typeRefNode : N4JSASTUtils.getContainedTypeReferenceNodes(astNode)) {
			TypeRef typeRef = typeRefNode.getTypeRefInAST();
			TypeRef typeRefProcessed = processTypeArg(G, typeRef);
			if (typeRefProcessed != null) {
				if (typeRefProcessed == typeRef) {
					// temporary tweak to ensure correctness of code base:
					// if nothing was resolved, we could directly use 'typeRef' as the value of property
					// TypeReferenceNode#cachedProcessedTypeRef; however, then the return value of operation
					// TypeReferenceNode#getTypeRef() would sometimes be contained in the AST and sometimes not;
					// by always creating a copy here, we force the entire code base to be able to cope with the
					// fact that the return value of TypeReferenceNode#getTypeRef() might not be contained in the
					// AST (similarly as type aliases were used everywhere in the code, for testing)
					typeRefProcessed = TypeUtils.copy(typeRef);
				}
				TypeRef typeRefProcessedFinal = typeRefProcessed;
				EcoreUtilN4.doWithDeliver(false, () -> typeRefNode.setCachedProcessedTypeRef(typeRefProcessedFinal),
						typeRefNode);
			}
		}
	}

	private void handleTypeRefsInTModule(RuleEnvironment G, EObject astNode) {
		IdentifiableElement defType = null;

		if (astNode instanceof TypeDefiningElement) {
			defType = ((TypeDefiningElement) astNode).getDefinedType();
		} else if (astNode instanceof StructuralTypeRef) {
			defType = ((StructuralTypeRef) astNode).getStructuralType();
		} else if (astNode instanceof FunctionTypeExpression) {
			defType = ((FunctionTypeExpression) astNode).getDeclaredType();
		} else if (astNode instanceof VariableDeclaration) {
			defType = ((VariableDeclaration) astNode).getDefinedVariable();
		}

		if (defType != null) {
			handleTypeRefsInIdentifiableElement(G, defType);
		}
	}

	private void handleTypeRefsInIdentifiableElement(RuleEnvironment G, IdentifiableElement elem) {
		if (elem instanceof TypeAlias) {
			return; // do not resolve the 'actualTypeRef' property in type alias itself
		}
		List<TypeArgument> allNestedTypeArgs = new ArrayList<>(); // create list up-front to not confuse tree iterator
																	// when replacing nodes!
		TreeIterator<EObject> iter = elem.eAllContents();
		while (iter.hasNext()) {
			EObject obj = iter.next();
			if (obj instanceof TypeArgument) {
				allNestedTypeArgs.add((TypeArgument) obj);
				iter.prune();
			}
		}
		for (TypeArgument typeArg : allNestedTypeArgs) {
			TypeArgument typeArgProcessed = processTypeArg(G, typeArg);
			if (typeArgProcessed != null && typeArgProcessed != typeArg) {
				EReference containmentFeature = typeArg.eContainmentFeature();
				boolean isValidType = containmentFeature != null
						&& containmentFeature.getEReferenceType().isSuperTypeOf(typeArgProcessed.eClass());
				if (isValidType) {
					EcoreUtilN4.doWithDeliver(false, () -> EcoreUtil.replace(typeArg, typeArgProcessed),
							typeArg.eContainer());
				}
			}
		}
	}

	/** This overload implements the rule that when passing in a {@link TypeRef}, you get a {@code TypeRef} back. */
	private TypeRef processTypeArg(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) processTypeArg(G, (TypeArgument) typeRef);
	}

	/**
	 * Guarantee: type references are never converted to {@link Wildcard}s, i.e. when passing in a {@link TypeRef}, you
	 * get a {@code TypeRef} back.
	 */
	private TypeArgument processTypeArg(RuleEnvironment G, TypeArgument typeArg) {
		if (typeArg == null) {
			return null;
		}
		var processed = typeArg;

		processed = processEnumLiteralTypeRefs(G, processed);
		processed = processTypeAliases(G, processed);

		return processed;
	}

	private TypeArgument processEnumLiteralTypeRefs(RuleEnvironment G, TypeArgument typeArg) {
		// note: we also have to handle parameterized type refs that might be nested below some other TypeRef!
		return new ResolveParameterizedTypeRefPointingToTEnumLiteralSwitch(G).doSwitch(typeArg);
	}

	private TypeArgument processTypeAliases(RuleEnvironment G, TypeArgument typeArg) {
		// note: we also have to resolve type aliases that might be nested below a non-alias TypeRef!
		return tsh.resolveTypeAliases(G, typeArg);
	}

	private static class ResolveParameterizedTypeRefPointingToTEnumLiteralSwitch extends NestedTypeRefsSwitch {

		ResolveParameterizedTypeRefPointingToTEnumLiteralSwitch(RuleEnvironment G) {
			super(G);
		}

		@Override
		protected ResolveParameterizedTypeRefPointingToTEnumLiteralSwitch derive(RuleEnvironment G_NEW) {
			return new ResolveParameterizedTypeRefPointingToTEnumLiteralSwitch(G_NEW);
		}

		@Override
		protected TypeRef caseParameterizedTypeRef_processDeclaredType(ParameterizedTypeRef typeRef) {
			Type astQualifier = typeRef.getAstNamespaceLikeRefs() != null
					&& last(typeRef.getAstNamespaceLikeRefs()) != null
							? last(typeRef.getAstNamespaceLikeRefs()).getDeclaredType()
							: null;

			if (astQualifier instanceof TEnum) {
				String enumLiteralName = typeRef.getDeclaredTypeAsText();
				TEnumLiteral enumLiteral = findFirst(((TEnum) astQualifier).getLiterals(),
						lit -> Objects.equals(lit.getName(), enumLiteralName));
				if (enumLiteral != null) {
					EnumLiteralTypeRef litTypeRef = TypeRefsFactory.eINSTANCE.createEnumLiteralTypeRef();
					litTypeRef.setValue(enumLiteral);
					return litTypeRef;
				}
			}
			return typeRef;
		}
	}
}
