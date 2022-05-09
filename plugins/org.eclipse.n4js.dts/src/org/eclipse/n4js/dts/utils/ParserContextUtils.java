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
package org.eclipse.n4js.dts.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.dts.DtsParseTreeNodeInfo;
import org.eclipse.n4js.dts.TypeScriptParser;
import org.eclipse.n4js.dts.TypeScriptParser.BlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.DeclarationStatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.DeclareStatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.GlobalScopeAugmentationContext;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.NamespaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.NumericLiteralContext;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
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
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument;
import org.eclipse.n4js.ts.types.Type;
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
public class ParserContextUtils {

	/** Like {@code N4JSGlobals#NAMESPACE_ACCESS_DELIMITER}, but for .d.ts files. */
	public static final String NAMESPACE_ACCESS_DELIMITER = ".";

	/** @return the {@link DtsMode mode} of the .d.ts file represented by the given {@link ProgramContext context}. */
	public static DtsMode getDtsMode(ProgramContext ctx) {
		StatementListContext stmnts = ctx.statementList();
		if (stmnts != null && stmnts.statement() != null) {
			for (StatementContext stmnt : stmnts.statement()) {
				if (stmnt.importStatement() != null || stmnt.exportStatement() != null) {
					return DtsMode.MODULE;
				}
			}
		}
		return DtsMode.SCRIPT;
	}

	/** @return true iff the element represented by the given context should be exported on N4JS-side. */
	public static boolean isExported(@SuppressWarnings("unused") ParserRuleContext ctx) {
		// as it turns out, elements declared in a .d.ts file are always available from the outside, not matter whether
		// they are preceded by keyword 'declared' or 'export' or none of the two; thus, we always return 'true':
		return true;
		// ParserRuleContext exportedParentCtx = findParentContext(ctx, TypeScriptParser.RULE_exportStatement,
		// TypeScriptParser.RULE_statement);
		// return exportedParentCtx != null;
	}

	/** @return the global scope augmentations directly contained in the given module declaration. */
	public static List<GlobalScopeAugmentationContext> getGlobalScopeAugmentations(ModuleDeclarationContext ctx) {
		List<GlobalScopeAugmentationContext> result = new ArrayList<>();
		for (StatementContext stmntCtx : ParserContextUtils.getStatements(ctx.block())) {
			DeclareStatementContext declStmnt = stmntCtx.declareStatement();
			if (declStmnt != null) {
				DeclarationStatementContext declStmnt2 = declStmnt.declarationStatement();
				if (declStmnt2 != null) {
					GlobalScopeAugmentationContext gsa = declStmnt2.globalScopeAugmentation();
					if (gsa != null) {
						result.add(gsa);
					}
				}
			}
		}
		return result;
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

	/** Makes the given {@link Script} global by inserting a {@code @@Global} at the top. */
	public static void makeGlobal(Script script) {
		Annotation ann = N4JSFactory.eINSTANCE.createAnnotation();
		ann.setName(AnnotationDefinition.GLOBAL.name);
		script.getAnnotations().add(0, ann);
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

		boolean isExported = ParserContextUtils.isExported(ctx);
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

	/** @return a new {@code boolean} type reference. */
	public static ParameterizedTypeRef createBooleanTypeRef(LazyLinkingResource resource) {
		return createParameterizedTypeRef(resource, "boolean", false);
	}

	/** @return a new {@code any+} type reference. */
	public static ParameterizedTypeRef createAnyPlusTypeRef(LazyLinkingResource resource) {
		return createParameterizedTypeRef(resource, "any", true);
	}

	/** @return a new {@link ParameterizedTypeRef} pointing to the given declared type. */
	public static ParameterizedTypeRef createParameterizedTypeRef(LazyLinkingResource resource, String declTypeName,
			boolean dynamic) {
		return ParserContextUtils.createParameterizedTypeRef(resource, declTypeName, null, dynamic);
	}

	/** @return a new {@link ParameterizedTypeRef} pointing to the given declared type and type arguments. */
	public static ParameterizedTypeRef createParameterizedTypeRef(LazyLinkingResource resource, String declTypeName,
			Collection<? extends TypeArgument> typeArgs, boolean dynamic) {

		ParameterizedTypeRef ptr = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
		ptr.setDynamic(dynamic);
		ptr.setDeclaredTypeAsText(declTypeName);

		Type typeProxy = TypesFactory.eINSTANCE.createType();
		EReference eRef = TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType();
		ParserContextUtils.installProxy(resource, ptr, eRef, typeProxy, ptr.getDeclaredTypeAsText());
		ptr.setDeclaredType(typeProxy);

		if (typeArgs != null) {
			ptr.getDeclaredTypeArgs().addAll(typeArgs);
		}

		return ptr;
	}

	/**
	 * Of all equally named functions remove all but the one functions with the most parameters. The return type will be
	 * changed to any+ iff there exist at least two overloads with different return types. The parameters of the
	 * surviving function will be made optional. Also, a simple name and type inference for parameters works as follows:
	 * <ul>
	 * <li>Parameter names are kept as long as all parameters at the same position have the same name. Otherwise, an
	 * artificial name is used.
	 * <li>Parameter types are kept as long as all parameters at the same position have the same type name. Otherwise,
	 * any+ is used.
	 * </ul>
	 */
	public static void removeOverloadingFunctionDefs(LazyLinkingResource resource,
			Collection<? extends EObject> elements) {

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
				Map<Integer, String> fparNames = new HashMap<>();
				Map<Integer, String> fparTypes = new HashMap<>();
				String returnTypeName;

				// find the survivor
				Iterator<FunctionDefinition> iter = signatures.iterator();
				FunctionDefinition survivor = iter.next();
				for (int i = 0; i < survivor.getFpars().size(); i++) {
					FormalParameter fPar = survivor.getFpars().get(i);
					fparNames.put(i, fPar.getName());
					fparTypes.put(i, fPar.getDeclaredTypeRefInAST() == null ? null
							: fPar.getDeclaredTypeRefInAST().getTypeRefAsString());
				}
				returnTypeName = survivor.getDeclaredReturnTypeRefInAST() == null ? null
						: survivor.getDeclaredReturnTypeRefInAST().getTypeRefAsString();

				for (FunctionDefinition fd = iter.next(); fd != null; fd = iter.hasNext() ? iter.next() : null) {
					int survFPars = survivor.getFpars().size();
					int fdFPars = fd.getFpars().size();
					for (int i = 0; i < fdFPars; i++) {
						FormalParameter fPar = fd.getFpars().get(i);
						if (fparNames.containsKey(i)) {
							String oldName = fparNames.get(i);
							if (!Objects.equals(oldName, fPar.getName())) {
								fparNames.put(i, "arg" + i);
							}
							String oldTypeRef = fparTypes.get(i);
							if (!Objects.equals(oldTypeRef, fPar.getDeclaredTypeRefInAST() == null ? null
									: fPar.getDeclaredTypeRefInAST().getTypeRefAsString())) {
								fparTypes.put(i, null);
							}
						} else {
							fparNames.put(i, fPar.getName());
							fparTypes.put(i, fPar.getDeclaredTypeRefInAST() == null ? null
									: fPar.getDeclaredTypeRefInAST().getTypeRefAsString());
						}

						if (!Objects.equals(returnTypeName, fd.getDeclaredReturnTypeRefInAST() == null ? null
								: fd.getDeclaredReturnTypeRefInAST().getTypeRefAsString())) {
							returnTypeName = null;
						}
					}
					if (survFPars < fdFPars) {
						survivor = fd;
					} else if (survFPars == fdFPars && survivor.getDeclaredReturnTypeRefNode() == null) {
						survivor = fd;
					}
				}

				// remove all overloads but the survivor
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

				// change return type iff necessary
				if (survivor.getDeclaredReturnTypeRefNode() != null && returnTypeName == null) {
					survivor.getDeclaredReturnTypeRefNode().setTypeRefInAST(createAnyPlusTypeRef(resource));
				}

				// change parameter names and types iff necessary
				for (int i = 0; i < survivor.getFpars().size(); i++) {
					FormalParameter fPar = survivor.getFpars().get(i);
					String fparName = fparNames.get(i);
					fPar.setName(fparName);
					fPar.setHasInitializerAssignment(true);
					if (fparTypes.get(i) == null) {
						fPar.setDeclaredTypeRefNode(wrapInTypeRefNode(createAnyPlusTypeRef(resource)));
					}
				}
			}
		}
	}

	/**  */
	public static void transformPromisifiables(EList<ScriptElement> scriptElements) {
		Map<String, FunctionDefinition> functionsTop = new HashMap<>();
		List<FunctionDefinition> functionsPrms = new ArrayList<>();
		for (EObject elem : scriptElements) {
			if (elem instanceof ExportDeclaration) {
				elem = ((ExportDeclaration) elem).getExportedElement();
			}
			if (elem instanceof FunctionDefinition) {
				FunctionDefinition fd = (FunctionDefinition) elem;
				if (Objects.equals("__promisify__", fd.getName())) {
					// fd.remove();
					functionsPrms.add(fd);
				} else {
					functionsTop.put(fd.getName(), fd);
				}
			}
		}

		for (FunctionDefinition promFd : functionsPrms) {
			DtsParseTreeNodeInfo dtsParseTreeNodeInfo = DtsParseTreeNodeInfo.get(promFd);
			if (dtsParseTreeNodeInfo != null) {
				ParserRuleContext ctx = dtsParseTreeNodeInfo.getParserRuleContext();
				if (ctx != null) {
					NamespaceDeclarationContext nsDeclCtx = (NamespaceDeclarationContext) findParentContext(ctx,
							TypeScriptParser.RULE_namespaceDeclaration);
					String nsName = null;
					if (nsDeclCtx != null) {
						nsName = nsDeclCtx.namespaceName() != null
								? nsDeclCtx.namespaceName().getText()
								: null;
					} else {
						ModuleDeclarationContext mDeclCtx = (ModuleDeclarationContext) findParentContext(ctx,
								TypeScriptParser.RULE_moduleDeclaration);

						if (mDeclCtx != null) {
							nsName = mDeclCtx.moduleName() != null && mDeclCtx.moduleName().Identifier() != null
									? mDeclCtx.moduleName().Identifier().getText()
									: null;
						}
					}
					if (nsName != null && functionsTop.containsKey(nsName)) {
						FunctionDefinition fd = functionsTop.get(nsName);
						Annotation promisifiable = N4JSFactory.eINSTANCE.createAnnotation();
						promisifiable.setName(AnnotationDefinition.PROMISIFIABLE.name);
						N4JSASTUtils.addAnnotation(fd, promisifiable);
					}
				}
			}
		}
	}
}