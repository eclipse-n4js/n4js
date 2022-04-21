/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts.astbuilders;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.n4js.dts.TypeScriptParser;
import org.eclipse.n4js.dts.TypeScriptParser.BlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.NumericLiteralContext;
import org.eclipse.n4js.dts.TypeScriptParser.ReservedWordContext;
import org.eclipse.n4js.dts.TypeScriptParser.StatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.StatementListContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeArgumentContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeArgumentListContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeArgumentsContext;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.utils.parser.conversion.ValueConverterUtils;
import org.eclipse.n4js.utils.parser.conversion.ValueConverterUtils.StringConverterResult;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Ints;

/**
 * Utilities to retrieve information from the parse tree
 */
public class ParserContextUtil {

	/** Like {@code N4JSGlobals#NAMESPACE_ACCESS_DELIMITER}, but for .d.ts files. */
	public static final String NAMESPACE_ACCESS_DELIMITER = ".";

	/** @return true iff the given rule is contained in an {@link TypeScriptParser#RULE_exportStatement} */
	public static boolean isExported(ParserRuleContext ctx) {
		ParserRuleContext exportedParentCtx = findParentContext(ctx, TypeScriptParser.RULE_exportStatement,
				TypeScriptParser.RULE_statement);
		return exportedParentCtx != null;
	}

	/** @return the statements in the given block or an empty list if not available. */
	public static List<StatementContext> getStatements(BlockContext block) {
		if (block != null) {
			StatementListContext statementList = block.statementList();
			if (statementList != null) {
				List<StatementContext> statement = statementList.statement();
				if (statement != null) {
					return statement;
				}
			}
		}
		return Collections.emptyList();
	}

	/** Sets the given accessibility, avoiding duplicate modifiers. */
	public static void setAccessibility(ModifiableElement elem, N4Modifier accessibility) {
		Objects.requireNonNull(accessibility);
		EList<N4Modifier> modifiers = elem.getDeclaredModifiers();
		Iterator<N4Modifier> iter = modifiers.iterator();
		while (iter.hasNext()) {
			if (N4JSASTUtils.ACCESSIBILITY_MODIFIERS.contains(iter.next())) {
				iter.remove();
			}
		}
		modifiers.add(accessibility);
	}

	/** Sets the given element's "declared this type" by adding a {@code @This()} annotation. */
	public static void setDeclThisType(AnnotableElement elem, TypeRef typeRef) {
		EObject parent = elem.eContainer();
		if (parent instanceof ExportDeclaration) {
			elem = (ExportDeclaration) parent;
		}
		Annotation ann = N4JSFactory.eINSTANCE.createAnnotation();
		ann.setName("This");
		TypeRefAnnotationArgument arg = N4JSFactory.eINSTANCE.createTypeRefAnnotationArgument();
		arg.setTypeRefNode(wrapInTypeRefNode(typeRef));
		ann.getArgs().add(arg);
		N4JSASTUtils.addAnnotation(elem, ann);
	}

	/** Sets the given element's "declared this type" by adding a {@code @This()} annotation. */
	public static void setDeclThisType(TAnnotableElement elem, TypeRef typeRef) {
		TAnnotation ann = TypesFactory.eINSTANCE.createTAnnotation();
		ann.setName("This");
		TAnnotationTypeRefArgument arg = TypesFactory.eINSTANCE.createTAnnotationTypeRefArgument();
		arg.setTypeRef(typeRef);
		ann.getArgs().add(arg);
		elem.getAnnotations().add(ann);
	}

	/**
	 * Convenience for {@link #addAndHandleExported(EObject, EReference, ExportableElement, boolean, boolean, boolean)}.
	 */
	public static void addAndHandleExported(EObject addHere, EReference eRef,
			ExportableElement elem, boolean makePrivateIfNotExported, ParserRuleContext ctx) {

		if (ctx == null) {
			return;
		}

		boolean isExported = ParserContextUtil.isExported(ctx);
		addAndHandleExported(addHere, eRef, elem, makePrivateIfNotExported, isExported, false);
	}

	/**
	 * Adds 'elem' to object 'addHere', setting its accessibility and wrapping it in an {@link ExportDeclaration}, if
	 * necessary.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addAndHandleExported(EObject addHere, EReference eRef,
			ExportableElement elem, boolean makePrivateIfNotExported, boolean isExported, boolean defaultExport) {

		if (elem == null) {
			return;
		}

		EObject toAdd;
		if (isExported) {
			if (elem instanceof ModifiableElement) {
				setAccessibility((ModifiableElement) elem, N4Modifier.PUBLIC);
			}

			ExportDeclaration ed = N4JSFactory.eINSTANCE.createExportDeclaration();
			ed.setDefaultExport(defaultExport);
			ed.setExportedElement(elem);

			toAdd = ed;
		} else {
			if (elem instanceof ModifiableElement) {
				if (makePrivateIfNotExported) {
					setAccessibility((ModifiableElement) elem, N4Modifier.PRIVATE);
				} else {
					setAccessibility((ModifiableElement) elem, N4Modifier.PUBLIC);
				}
			}

			toAdd = elem;
		}
		((List) addHere.eGet(eRef)).add(toAdd);
	}

	/**
	 * Traverses the parent relation upwards from the given context. In case a parent matches the given rule id, it is
	 * returned. In case a parent's rule id matches the given stopAtIds, null is returned. The given start context is
	 * not checked.
	 *
	 * @return parent context with the given id or null.
	 */
	public static ParserRuleContext findParentContext(ParserRuleContext ctx, int parentContextId,
			Integer... stopAtIds) {
		return findParentContext(ctx, parentContextId, false, stopAtIds);
	}

	/**
	 * Traverses the parent relation upwards from the given context. In case a parent matches the given rule id, it is
	 * returned. In case a parent's rule id matches the given stopAtIds, null is returned. The given start context is
	 * checked iff checkStart is true.
	 *
	 * @return parent context with the given id or null.
	 */
	public static ParserRuleContext findParentContext(ParserRuleContext ctx, int parentContextId, boolean checkStart,
			Integer... stopAtIds) {

		if (checkStart && ctx.getRuleIndex() == parentContextId) {
			return ctx;
		}
		Set<Integer> stopAtIdsSet = stopAtIds == null ? Collections.emptySet() : Set.of(stopAtIds);

		while (ctx.parent != null) {
			if (ctx.getRuleIndex() == parentContextId) {
				return ctx;
			}
			if (stopAtIdsSet.contains(ctx.getRuleIndex())) {
				return null;
			}
			ctx = (ParserRuleContext) ctx.parent;
		}
		return null;
	}

	/**
	 * @return true iff from the given context the given parent can be reached before reaching the root or one of the
	 *         given excluded parents.
	 */
	public static boolean hasParentContext(ParserRuleContext ctx, int parentContextId,
			Integer... stopAtIds) {
		return hasParentContexts(ctx, new int[] { parentContextId }, stopAtIds);
	}

	/**
	 * @return true iff from the given context one of the given parents can be reached before reaching the root or one
	 *         of the given excluded parents.
	 */
	public static boolean hasParentContexts(ParserRuleContext ctx, int[] parentContextIds,
			Integer... stopAtIds) {

		Set<Integer> stopAtIdsSet = stopAtIds == null ? Collections.emptySet() : Set.of(stopAtIds);
		Set<Integer> parentIdsSet = parentContextIds == null
				? Collections.emptySet()
				: new HashSet<>(Ints.asList(parentContextIds));

		ctx = (ParserRuleContext) ctx.parent;
		while (ctx != null) {
			if (parentIdsSet.contains(ctx.getRuleIndex())) {
				return true;
			}
			if (stopAtIdsSet.contains(ctx.getRuleIndex())) {
				break;
			}
			ctx = (ParserRuleContext) ctx.parent;
		}
		return false;
	}

	/** @return the actual value of the given numeric literal. */
	public static BigDecimal parseNumericLiteral(NumericLiteralContext numLitCtx, boolean ignoreNegation) {
		if (numLitCtx == null) {
			return null;
		}
		String text = trimAndNormalize(numLitCtx.getText());
		if (text == null) {
			return null;
		}
		if (ignoreNegation) {
			if (text.startsWith("-")) {
				text = text.substring(1);
			}
		}
		try {
			if (numLitCtx.BinaryIntegerLiteral() != null) {
				return new BigDecimal(new BigInteger(text.substring(2), 2));
			} else if (numLitCtx.OctalIntegerLiteral() != null) {
				return new BigDecimal(new BigInteger(text, 8));
			} else if (numLitCtx.OctalIntegerLiteral2() != null) {
				return new BigDecimal(new BigInteger(text.substring(2), 8));
			} else if (numLitCtx.HexIntegerLiteral() != null) {
				return new BigDecimal(new BigInteger(text.substring(2), 16));
			} else if (numLitCtx.DecimalLiteral() != null) {
				return new BigDecimal(text);
			} else {
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * @return the name of the identifiable element being referred to by the given {@link IdentifierNameContext}. Blank
	 *         strings are normalized to <code>null</code>.
	 */
	public static String getIdentifierName(IdentifierNameContext identifierNameCtx) {
		if (identifierNameCtx == null) {
			return null;
		}
		ReservedWordContext reservedWord = identifierNameCtx.reservedWord();
		if (reservedWord != null) {
			return trimAndNormalize(reservedWord.getText());
		}
		return getIdentifierName(identifierNameCtx.Identifier());
	}

	/**
	 * @return the name of the identifiable element being referred to by the given identifier. Blank strings are
	 *         normalized to <code>null</code>.
	 */
	public static String getIdentifierName(TerminalNode identifier) {
		if (identifier == null) {
			return null;
		}
		return trimAndNormalize(identifier.getText());
	}

	/** @return the newly created string literal. Null safe. */
	public static StringLiteral createStringLiteral(TerminalNode stringLiteral) {
		if (stringLiteral == null) {
			return null;
		}
		StringLiteral sl = N4JSFactory.eINSTANCE.createStringLiteral();
		sl.setRawValue(stringLiteral.getText());
		sl.setValue(trimAndUnescapeStringLiteral(stringLiteral));
		return sl;
	}

	/** @return the unquoted and unescaped string. Null safe. */
	public static String trimAndUnescapeStringLiteral(TerminalNode stringLiteral) {
		String str = stringLiteral != null ? trimAndNormalize(stringLiteral.getText()) : null;
		if (str == null || str.length() < 2) {
			return "";
		}
		// trim quotes
		str = str.substring(1, str.length() - 1);
		// resolve escape sequences
		StringConverterResult converted = ValueConverterUtils.convertFromEscapedString(str, true, false, false, null);
		return converted.getValue();
	}

	/** @return the type argument contexts of the given context. Null safe. */
	public static List<TypeArgumentContext> getTypeArgsFromTypeArgCtx(TypeArgumentsContext typeArgsCtx) {
		if (typeArgsCtx != null) {
			TypeArgumentListContext typeArgList = typeArgsCtx.typeArgumentList();
			if (typeArgList != null) {
				List<TypeArgumentContext> typeArgument = typeArgList.typeArgument();
				if (typeArgument != null && !typeArgument.isEmpty()) {
					return typeArgument;
				}
			}
		}
		return Collections.emptyList();
	}

	/** @return a new {@link TypeReferenceNode} wrapping the given type reference. Null safe. */
	public static <T extends TypeRef> TypeReferenceNode<T> wrapInTypeRefNode(T typeRef) {
		if (typeRef == null) {
			return null;
		}
		TypeReferenceNode<T> result = N4JSFactory.eINSTANCE.createTypeReferenceNode();
		result.setTypeRefInAST(typeRef);
		return result;
	}

	/** Installs proxy information that is later used for linking */
	public static void installProxy(LazyLinkingResource resource, EObject container, EReference eRef, EObject proxy,
			String crossRefStr) {

		int fragmentNumber = resource.addLazyProxyInformation(container, eRef, new PseudoLeafNode(crossRefStr));
		URI encodedLink = resource.getURI().appendFragment("|" + fragmentNumber);
		((InternalEObject) proxy).eSetProxyURI(encodedLink);
	}

	/**
	 * Trims the given string. Null-safe and blank strings are normalized to <code>null</code>.
	 */
	private static String trimAndNormalize(String str) {
		String trimmed = str != null ? str.trim() : null;
		return trimmed != null && trimmed.length() > 0 ? trimmed : null;
	}

	/**
	 * Of all equally named functions remove all but the one functions with the most parameters. The parameters of the
	 * surviving function will be made optional.
	 */
	public static void removeOverloadingFunctionDefs(Collection<? extends EObject> elements) {
		Multimap<String, FunctionDefinition> functionsByName = HashMultimap.create();
		for (EObject elem : elements) {
			if (elem instanceof ExportDeclaration) {
				ExportDeclaration expDecl = (ExportDeclaration) elem;
				elem = expDecl.getExportedElement();
			}
			if (elem instanceof FunctionDefinition) {
				FunctionDefinition fd = (FunctionDefinition) elem;
				functionsByName.put(fd.getName(), fd);
			}
		}

		for (String fName : functionsByName.keySet()) {
			Collection<FunctionDefinition> signatures = functionsByName.get(fName);
			if (signatures.size() > 1) {
				// find the survivor
				Iterator<FunctionDefinition> iter = signatures.iterator();
				FunctionDefinition survivor = iter.next();
				for (FunctionDefinition fd = iter.next(); fd != null; fd = iter.hasNext() ? iter.next() : null) {
					int fparCountSurvivor = survivor.getFpars() == null ? 0 : survivor.getFpars().size();
					int fparCountFd = fd.getFpars() == null ? 0 : fd.getFpars().size();
					if (fparCountFd > fparCountSurvivor) {
						survivor = fd;
					} else if (fparCountFd > 0 && fparCountFd == fparCountSurvivor) {
						FormalParameter lastFParSurvivor = survivor.getFpars().get(survivor.getFpars().size() - 1);
						FormalParameter lastFParFd = fd.getFpars().get(fd.getFpars().size() - 1);
						if (!lastFParSurvivor.isVariadic() && lastFParFd.isVariadic()) {
							survivor = fd;
						}
					}
				}

				// remove all but the survivor
				for (FunctionDefinition fd : functionsByName.get(fName)) {
					if (fd == survivor) {
						continue;
					}
					EObject elemToRemove = fd;
					if (elemToRemove.eContainer() instanceof ExportDeclaration) {
						elemToRemove = elemToRemove.eContainer();
					}
					elements.remove(elemToRemove);
				}

				// make parameters optional
				for (FormalParameter fpar : survivor.getFpars()) {
					fpar.setHasInitializerAssignment(true);
				}
			}
		}
	}
}
