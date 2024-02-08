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

import static org.eclipse.n4js.transpiler.SymbolTableManagement.findSymbolTableEntryForElement;
import static org.eclipse.n4js.transpiler.SymbolTableManagement.getSymbolTableEntryOriginal;
import static org.eclipse.n4js.transpiler.SymbolTableManagement.rewireSymbolTable;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Argument;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ImportDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._NamedImportSpecifier;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._NamespaceImportSpecifier;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ReturnStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableStatement;
import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryIMOnly;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.Lists;

/**
 * Methods of this class provide elementary operations on a transpiler state, mainly on the intermediate model. The
 * intermediate model should only be changed through the operations defined by this class.
 * <p>
 * Main clients are AST transformations, but they should not invoke these operations directly, but instead use the
 * delegation methods in {@link Transformation}.
 */
public class TranspilerStateOperations {

	/**
	 * Creates a new namespace import for the given module and adds it to the intermediate model of the given transpiler
	 * state. The returned symbol table entry can be used to create references to the namespace, e.g. by passing it to
	 * {@link TranspilerBuilderBlocks#_IdentRef(SymbolTableEntry)}. The newly created import can be obtained by calling
	 * {@link SymbolTableEntryOriginal#getImportSpecifier()} on the returned symbol table entry.
	 * <p>
	 * IMPORTANT: this method does not check if an import for the given module exists already or if the given namespace
	 * name is unique (i.e. does not avoid name clashes!).
	 */
	public static SymbolTableEntryOriginal addNamespaceImport(TranspilerState state, TModule moduleToImport,
			String namespaceName) {

		// 1) create import declaration & specifier
		NamespaceImportSpecifier importSpec = _NamespaceImportSpecifier(namespaceName, true);
		ImportDeclaration importDecl = _ImportDecl(importSpec);
		// 2) create a temporary type to use as original target
		ModuleNamespaceVirtualType typeForNamespace = TypesFactory.eINSTANCE.createModuleNamespaceVirtualType();
		typeForNamespace.setName(namespaceName);
		state.resource.addTemporaryType(typeForNamespace); // make sure our temporary type is contained in a resource
		// 3) create a symbol table entry
		SymbolTableEntryOriginal steForNamespace = getSymbolTableEntryOriginal(state, typeForNamespace, true);
		steForNamespace.setImportSpecifier(importSpec);
		// 4) add import to intermediate model
		EList<ScriptElement> scriptElements = state.im.getScriptElements();
		if (scriptElements.isEmpty()) {
			scriptElements.add(importDecl);
		} else {
			insertBefore(scriptElements.get(0), importDecl);
		}
		// 5) update info registry
		state.info.setImportedModule(importDecl, moduleToImport);
		return steForNamespace;
	}

	/**
	 * Creates a new named import for the given element and adds it to the intermediate model of the given transpiler
	 * state. The returned symbol table entry can be used to create references to the imported element, e.g. by passing
	 * it to {@link TranspilerBuilderBlocks#_IdentRef(SymbolTableEntry)}. The newly created import can be obtained by
	 * calling {@link SymbolTableEntryOriginal#getImportSpecifier()} on the returned symbol table entry.
	 * <p>
	 * If a named import already exists for the given element, nothing will be changed in the intermediate model and its
	 * symbol table entry will be returned as described above. If the given element is of type
	 * {@link ModuleNamespaceVirtualType} an exception will be thrown (because only namespace imports can be created for
	 * those types).
	 * <p>
	 * IMPORTANT: this method does not check if the given namespace name is unique (i.e. does not avoid name clashes!).
	 */
	public static SymbolTableEntryOriginal addNamedImport(TranspilerState state, IdentifiableElement elementToImport,
			String aliasOrNull) {
		SymbolTableEntryOriginal steOfElementToImport = getSymbolTableEntryOriginal(state, elementToImport, true);
		addNamedImport(state, steOfElementToImport, aliasOrNull);
		return steOfElementToImport;
	}

	/**
	 * Creates a new named import for the given STE and adds it to the intermediate model of the given transpiler state.
	 * The passed-in symbol table entry can be used to create references to the imported element, e.g. by passing it to
	 * {@link TranspilerBuilderBlocks#_IdentRef(SymbolTableEntry)}. The newly created import can be obtained by calling
	 * {@link SymbolTableEntryOriginal#getImportSpecifier()} on the passed-in symbol table entry.
	 * <p>
	 * If a named import already exists for the given element, nothing will be changed in the intermediate model. If the
	 * original target of the given symbol table entry is of type {@link ModuleNamespaceVirtualType} an exception will
	 * be thrown (because only namespace imports can be created for those types).
	 * <p>
	 * IMPORTANT: this method does not check if the given namespace name is unique (i.e. does not avoid name clashes!).
	 */
	public static void addNamedImport(TranspilerState state, SymbolTableEntryOriginal steOfElementToImport,
			String aliasOrNull) {
		// check for valid type of element to be imported (i.e. the original target)
		IdentifiableElement originalTarget = steOfElementToImport.getOriginalTarget();
		if (originalTarget instanceof ModuleNamespaceVirtualType) {
			throw new IllegalArgumentException("cannot create named import for a ModuleNamespaceVirtualType");
		}
		// check for existing import
		ImportSpecifier existingImportSpec = steOfElementToImport.getImportSpecifier();
		if (existingImportSpec != null) {
			// import already exists, nothing to be done
			return;
		}

		// 1) create import declaration & specifier
		NamedImportSpecifier importSpec = _NamedImportSpecifier(steOfElementToImport.getExportedName(), aliasOrNull,
				true);
		ImportDeclaration importDecl = _ImportDecl(importSpec);
		// 2) add import to intermediate model
		EList<ScriptElement> scriptElements = state.im.getScriptElements();
		if (scriptElements.isEmpty()) {
			scriptElements.add(importDecl);
		} else {
			insertBefore(scriptElements.get(0), importDecl);
		}
		// 3) link symbol table entry to its newly created import specifier
		steOfElementToImport.setImportSpecifier(importSpec);
		// 4) update info registry
		TModule moduleOfOriginalTarget = originalTarget.getContainingModule();
		state.info.setImportedModule(importDecl, moduleOfOriginalTarget);
	}

	/**
	 * Creates a new named import for the given STE and adds it to the intermediate model of the given transpiler state.
	 * Note that this method does not perform a binding to an existing target module.
	 * <p>
	 * IMPORTANT: this method does not check if the given element name or alias is unique (i.e. does not avoid name
	 * clashes!).
	 */
	public static ImportDeclaration addNamedImport(TranspilerState state, String elementNameToImport,
			String aliasOrNull, String moduleSpecifierName) {

		// 1) create import declaration & specifier
		NamedImportSpecifier importSpec = _NamedImportSpecifier(elementNameToImport, aliasOrNull, true);
		ImportDeclaration importDecl = _ImportDecl(importSpec);
		importDecl.setModuleSpecifierAsText(moduleSpecifierName);

		// 2) add import to intermediate model
		EList<ScriptElement> scriptElements = state.im.getScriptElements();
		if (scriptElements.isEmpty()) {
			scriptElements.add(importDecl);
		} else {
			insertBefore(scriptElements.get(0), importDecl);
		}

		return importDecl;
	}

	/**
	 * Adds an "empty" import to the intermediate model, i.e. an import of the form:
	 *
	 * <pre>
	 * import "&lt;moduleSpecifier>";
	 * </pre>
	 */
	public static void addEmptyImport(TranspilerState state, String moduleSpecifier) {
		// 1) create import declaration
		ImportDeclaration importDecl = _ImportDecl();
		importDecl.setModuleSpecifierAsText(moduleSpecifier);
		// 2) add import to intermediate model
		EList<ScriptElement> scriptElements = state.im.getScriptElements();
		if (scriptElements.isEmpty()) {
			scriptElements.add(importDecl);
		} else {
			insertBefore(scriptElements.get(0), importDecl);
		}
	}

	/**
	 * Returns the symbol table entry to a temporary variable with the given name, intended for use at the location of
	 * "nodeInIM" in the intermediate model. If no such variable exists yet, a new variable statement and declaration
	 * will be created.
	 * <p>
	 * When newly created, the temporary declarations will be added to the body of the closest ancestor
	 * function/accessor (or on the top level if no such ancestor exists), even if a temporary variable of the same name
	 * already exists in an outer variable environment (i.e. an outer function/accessor or on top level if inside a
	 * function/accessor).
	 */
	public static SymbolTableEntryIMOnly addOrGetTemporaryVariable(TranspilerState state, String name,
			EObject nodeInIM) {
		FunctionOrFieldAccessor contextFunctionOrAccessor = getContextFunctionOrAccessor(nodeInIM);
		VariableEnvironmentElement context = contextFunctionOrAccessor != null ? contextFunctionOrAccessor : state.im;
		SymbolTableEntryIMOnly tempVarSTE = state.temporaryVariables.get(Pair.of(context, name));
		if (tempVarSTE != null) {
			return tempVarSTE;
		}
		// need to create a new temporary variable below context
		VariableStatement tempVarStmnt = addOrGetTemporaryVariableStatement(state, context);
		VariableDeclaration tempVarDecl = _VariableDeclaration(name);
		tempVarStmnt.getVarDeclsOrBindings().add(tempVarDecl);
		SymbolTableEntryIMOnly tempVarSTENew = (SymbolTableEntryIMOnly) findSymbolTableEntryForElement(state,
				tempVarDecl, true);
		state.temporaryVariables.put(Pair.of(context, name), tempVarSTENew);
		return tempVarSTENew;
	}

	private static FunctionOrFieldAccessor getContextFunctionOrAccessor(EObject nodeInIM) {
		if (nodeInIM == null) {
			return null;
		}
		if (nodeInIM instanceof FunctionOrFieldAccessor) {
			return (FunctionOrFieldAccessor) nodeInIM;
		}
		EObject parent = nodeInIM.eContainer();
		if (parent instanceof FormalParameter
				&& parent.eContainer() instanceof FunctionOrFieldAccessor
				&& ((FormalParameter) parent).getInitializer() == nodeInIM) {
			// special case: since the expression of a default parameter cannot access a function"s local variables,
			// the directly containing function of a default parameter is not a valid context function for temporary
			// variables used in the default parameter"s initializer expression.
			EObject parentOfContainingFunctionOrAccessor = parent.eContainer().eContainer();
			return getContextFunctionOrAccessor(parentOfContainingFunctionOrAccessor);
		}
		return getContextFunctionOrAccessor(parent);
	}

	/** If context is absent, then the temporary variable statement will be created on the top level. */
	private static VariableStatement addOrGetTemporaryVariableStatement(TranspilerState state,
			VariableEnvironmentElement context) {
		VariableStatement tempVarStmnt = state.temporaryVariableStatements.get(context);
		if (tempVarStmnt != null) {
			return tempVarStmnt;
		}
		// need to create a new temporary variable statement
		VariableStatement tempVarStmntNew = _VariableStatement(VariableStatementKeyword.LET);
		state.temporaryVariableStatements.put(context, tempVarStmntNew);
		if (context instanceof FunctionOrFieldAccessor) {
			// add to body of function/accessor
			if (context instanceof ArrowFunction) {
				ArrowFunction af = (ArrowFunction) context;
				if (!af.isHasBracesAroundBody()) {
					// to allow for declarations inside the body, we have to turn single-expression arrow functions into
					// ordinary arrow functions
					if (af.isSingleExprImplicitReturn()) {
						ExpressionStatement singleExprStmnt = (ExpressionStatement) af.getBody().getStatements().get(0); // we
																															// know
																															// this,
																															// because
																															// #isSingleExprImplicitReturn()
																															// returned
																															// true
						replace(state, singleExprStmnt, _ReturnStmnt(singleExprStmnt.getExpression()));
					}
					af.setHasBracesAroundBody(true);
				}
			}
			((FunctionOrFieldAccessor) context).getBody().getStatements().add(0, tempVarStmntNew);
		} else if (context instanceof Script) {
			Script script = (Script) context;
			// add on top level before the first non-empty, non-import statement
			Iterator<ScriptElement> iter = script.getScriptElements().iterator();
			ScriptElement elem;
			do {
				elem = (iter.hasNext()) ? iter.next() : null;
			} while (elem instanceof EmptyStatement || elem instanceof ImportDeclaration);
			if (elem != null) {
				insertBefore(elem, tempVarStmntNew);
			} else {
				script.getScriptElements().add(tempVarStmntNew);
			}
		}
		return tempVarStmntNew;
	}

	/***/
	public static void setTarget(TranspilerState state, ParameterizedCallExpression callExpr, Expression newTarget) {
		Expression oldTarget = callExpr.getTarget();
		if (oldTarget != null) {
			replaceWithoutRewire(state, oldTarget, newTarget);
		} else {
			callExpr.setTarget(newTarget);
		}
	}

	/***/
	public static void setTarget(TranspilerState state, ParameterizedPropertyAccessExpression_IM accExpr,
			Expression newTarget) {
		Expression oldTarget = accExpr.getTarget();
		if (oldTarget != null) {
			replaceWithoutRewire(state, oldTarget, newTarget);
		} else {
			accExpr.setTarget(newTarget);
		}
	}

	/***/
	public static void addArgument(ParameterizedCallExpression callExpr, int index, Expression newArgument) {
		callExpr.getArguments().add(index, _Argument(newArgument));
	}

	/***/
	public static void removeAll(TranspilerState state, Iterable<? extends EObject> elementsInIM) {
		for (EObject elementInIM : Lists.newArrayList(elementsInIM)) {
			remove(state, elementInIM);
		}
	}

	/***/
	public static void remove(TranspilerState state, EObject elementInIM) {
		replaceWithoutRewire(state, elementInIM); // i.e. replace with nothing (will update tracer)
		if (elementInIM instanceof ReferencingElement_IM) {
			((ReferencingElement_IM) elementInIM).setRewiredTarget(null); // important here: will remove elementInIM
																			// from its symbol table entry"s
																			// "referencingElements" list!
			// note: this update of the symbol table is incomplete; elementInIM may be the root of an entire subtree
			// of the IM, so we would have to iterate over all successors
		}
	}

	/**
	 * Removes the export-container (ExportDeclaration) by creating a new VariableStatement {@code varStmt}, moving all
	 * content from {@code exVarStmnt} into it and replacing the ExportDeclaration with the newly created
	 * {@code varStmt}
	 */
	public static void removeExport(TranspilerState state, VariableStatement exVarStmnt) {

		if (!TranspilerUtils.isIntermediateModelElement(exVarStmnt)) {
			throw new IllegalArgumentException("not an element in the intermediate model: " + exVarStmnt);
		}

		ExportDeclaration exportDecl = (ExportDeclaration) exVarStmnt.eContainer();

		replaceWithoutRewire(state, exportDecl, exVarStmnt);
	}

	/***/
	public static void replace(TranspilerState state, Statement stmnt, ReturnStatement returnStmnt) {
		replaceWithoutRewire(state, stmnt, returnStmnt);
	}

	/***/
	public static void replace(TranspilerState state, N4ClassDeclaration classDecl, FunctionDeclaration funDecl) {
		replaceWithoutRewire(state, classDecl, funDecl);
		rewireSymbolTable(state, classDecl, funDecl);
	}

	/**
	 * Replace an interface declaration by a variable declaration. The variable declaration will be wrapped in a newly
	 * created [Exported]VariableStatement.
	 */
	public static void replace(TranspilerState state, N4InterfaceDeclaration ifcDecl, VariableDeclaration varDecl) {
		VariableStatement varStmnt = _VariableStatement(VariableStatementKeyword.CONST, varDecl);
		replaceWithoutRewire(state, ifcDecl, varStmnt);
		rewireSymbolTable(state, ifcDecl, varDecl);
	}

	/***/
	public static void replace(TranspilerState state, N4EnumDeclaration enumDecl, N4ClassDeclaration classDecl) {
		replaceWithoutRewire(state, enumDecl, classDecl);
		rewireSymbolTable(state, enumDecl, classDecl);
	}

	/***/
	public static void replace(TranspilerState state, FunctionDeclaration funDecl, VariableDeclaration varDecl) {
		VariableStatement varStmnt = _VariableStatement(varDecl);
		replaceWithoutRewire(state, funDecl, varStmnt);
		rewireSymbolTable(state, funDecl, varDecl);
		// need to rewire the local arguments variable, to enable renaming:
		Expression varValue = varDecl.getExpression();
		if (varValue instanceof FunctionExpression) {
			rewireSymbolTable(state, funDecl.getImplicitArgumentsVariable(),
					((FunctionExpression) varValue).getImplicitArgumentsVariable());
		} else {
			throw new IllegalArgumentException(
					"when replacing a function declaration by a variable declaration, " +
							"we expect the variable to be initialized with a function expression");
		}
	}

	/***/
	public static void replace(TranspilerState state, FunctionDeclaration functionDecl, ExpressionStatement stmt) {
		replaceWithoutRewire(state, functionDecl, stmt);
	}

	/***/
	public static void replace(TranspilerState state, N4MemberDeclaration memberDecl, N4MemberDeclaration replacement) {
		replaceWithoutRewire(state, memberDecl, replacement);
		rewireSymbolTable(state, memberDecl, replacement);
	}

	/***/
	public static void replace(TranspilerState state, VariableStatement varStmnt, Statement... newStmnts) {
		replaceWithoutRewire(state, varStmnt, newStmnts);
	}

	/***/
	public static void replace(TranspilerState state, VariableBinding varBinding, VariableDeclaration... varDecls) {
		replaceWithoutRewire(state, varBinding, varDecls);
	}

	/***/
	public static void replace(TranspilerState state, Expression exprOld, Expression exprNew) {
		replaceWithoutRewire(state, exprOld, exprNew);
	}

	/***/
	public static void replace(TranspilerState state, ArrowFunction exprOld, ParameterizedCallExpression exprNew,
			FunctionExpression rewireTarget) {
		replaceWithoutRewire(state, exprOld, exprNew);
		rewireSymbolTable(state, exprOld, rewireTarget);
	}

	/** Replace formal parameter with a variableStmt. Rewire the fpar to the VariableDeclaration. Relocate the Stmt */
	public static void replaceAndRelocate(TranspilerState state, FormalParameter fPar_to_remove,
			VariableStatement varStmnt,
			VariableDeclaration varDecl_wireTo, Block newContainer) {
		if (varDecl_wireTo.eContainer() != varStmnt) {
			throw new IllegalArgumentException("varDecl must be contained in varStmnt");
		}
		replaceAndRelocateWithoutRewire_internal(state, fPar_to_remove, varStmnt, newContainer.getStatements(), 0);

		rewireSymbolTable(state, fPar_to_remove, varDecl_wireTo);
	}

	/***/
	public static <T extends Expression> void wrapExistingExpression(T exprToWrap,
			Expression outerExpr_without_exprToWrap, Consumer<T> inserterFunction) {

		insertOrReplace_internal(exprToWrap, List.of(outerExpr_without_exprToWrap), true, false);
		inserterFunction.accept(exprToWrap);
	}

	/*
	 * append( pos < 0 or > current size ), prepend(pos==0) or insert at {@code pos} the object {@code insertThis} to
	 * {@code newContainer}. Also delete {@code removeThis} from the IM. Does not rewire. But keeps trace.
	 */
	private static void replaceAndRelocateWithoutRewire_internal(TranspilerState state, EObject removeThis,
			EObject insertThis,
			EList<?> newContainer, int pos) {

		EReference eRefRemove = checkedContainmentFeature(removeThis);

		if (insertThis.eContainer() != null)
			throw new IllegalArgumentException("The new element must not be contained anywhere." +
					" insertThis=" + insertThis + " is currently contained in " + insertThis.eContainer());

		if (newContainer instanceof EObjectContainmentEList<?>) {
			@SuppressWarnings("unchecked")
			EObjectContainmentEList<EObject> newContainerCasted = (EObjectContainmentEList<EObject>) newContainer;

			//// Tracing:
			state.tracer.copyTrace(removeThis, insertThis);
			state.tracer.discardIntermediateModelNode(removeThis);

			////////////////////////////////////
			//// remove:
			if (eRefRemove.getUpperBound() == 1) { // single-value
				if (eRefRemove.isUnsettable())
					removeThis.eContainer().eUnset(eRefRemove);
				else
					removeThis.eContainer().eSet(eRefRemove, null);
			} else { // multivalue
				@SuppressWarnings("unchecked")
				List<EObject> l = (List<EObject>) removeThis.eContainer().eGet(eRefRemove); // c.f. type check above
				int idx = l.indexOf(removeThis);
				l.remove(idx);
			}

			////////////////////////////////////
			//// insert:
			int idx = pos; // insert

			if (pos < 0 || pos >= newContainer.size()) {
				// append
				idx = newContainer.size();
			}
			newContainerCasted.add(idx, insertThis);

		} else {
			throw new IllegalArgumentException(
					"designated new container-list must be a subtype of type EObjectContainmentList");
		}
	}

	/**
	 * {@code elementInIntermediateModel} is going away (ie, should be garbage-collected) and therefore we clear all
	 * references to it from tracing. The {@code replacements} IM nodes take over whatever AST node was previously
	 * traced-back-to via the element going away.
	 */
	private static void replaceWithoutRewire(TranspilerState state, EObject elementInIntermediateModel,
			EObject... replacements) {

		state.tracer.copyTrace(elementInIntermediateModel, replacements);
		state.tracer.discardIntermediateModelNode(elementInIntermediateModel);
		insertOrReplace_internal(elementInIntermediateModel, Arrays.asList(replacements), true, false);
	}

	/***/
	public static void insertBefore(EObject elementInIntermediateModel, EObject... newElements) {
		insertOrReplace_internal(elementInIntermediateModel, Arrays.asList(newElements), false, false);
	}

	/***/
	public static void insertAfter(EObject elementInIntermediateModel, EObject... newElements) {
		insertOrReplace_internal(elementInIntermediateModel, Arrays.asList(newElements), false, true);
	}

	private static void insertOrReplace_internal(EObject elementInIntermediateModel,
			List<EObject> newElements, boolean replace, boolean after) {
		if (newElements.isEmpty() && !replace) {
			return; // nothing to be inserted
		}
		EReference eRef = checkedContainmentFeature(elementInIntermediateModel);
		EClass eRefType = eRef.getEReferenceType();
		List<EObject> replElemsOfWrongType = toList(
				filter(newElements, elem -> !eRefType.isSuperTypeOf(elem.eClass())));
		if (!replElemsOfWrongType.isEmpty()) {
			throw new IllegalArgumentException("one or more elements are of wrong type, expected: "
					+ eRef.getEReferenceType().getName() + ", actual: "
					+ join(", ", map(replElemsOfWrongType, eobj -> eobj.eClass().getName())));
		}
		if (eRef.getUpperBound() == 1) {
			// single valued
			if (newElements.size() > 1) {
				throw new IllegalArgumentException(
						"the single-valued reference " + eRef.getName() + " in class " + eRef.getEContainingClass()
								+ " is not able to hold " + newElements.size() + " elements.");
			}
			if (newElements.size() == 1) {
				if (!replace) {
					throw new IllegalArgumentException(
							"Cannot insert another element into a single-valued containment reference " + eRef.getName()
									+ " in class " + eRef.getEContainingClass());
				}
				elementInIntermediateModel.eContainer().eSet(eRef, newElements.get(0));
			} else {
				if (!replace) {
					throw new IllegalArgumentException("Inserting zero elements with replace==false is pointless.");
				}
				// no element, so remove
				if (eRef.isUnsettable()) {
					elementInIntermediateModel.eContainer().eUnset(eRef);
				} else {
					elementInIntermediateModel.eContainer().eSet(eRef, null);
				}
			}

		} else {
			// multi-valued
			@SuppressWarnings("unchecked")
			// c.f. type check above
			List<EObject> l = (List<EObject>) elementInIntermediateModel.eContainer().eGet(eRef);
			int idx = l.indexOf(elementInIntermediateModel);
			if (replace) {
				l.remove(idx);
			} else {
				// note: before/after only applicable if !replace
				if (after) {
					idx++; // always safe to increment, because we know there exists an element at index "idx"
				}
			}
			l.addAll(idx, newElements);
		}
	}

	/**
	 * Retrieves the EReference of the Container. Throws Exceptions if a) not part of the IM or b) not contained
	 * anywhere
	 */
	private static EReference checkedContainmentFeature(EObject elementInIntermediateModel) {
		if (!TranspilerUtils.isIntermediateModelElement(elementInIntermediateModel)) {
			throw new IllegalArgumentException(
					"not an element in the intermediate model: " + elementInIntermediateModel);
		}
		EReference eRef = elementInIntermediateModel.eContainmentFeature();
		if (eRef == null) {
			throw new IllegalArgumentException("element is not contained anywhere");
		}
		return eRef;
	}

	/**
	 * Rename the given symbol table entry and all named elements in the intermediate model that are using this name.
	 * During AST transformations in the transpiler, the "name" property of a symbol table entry should never be changed
	 * directly, but this operation should be used instead.
	 * <p>
	 * <b>WARNING:</b> renaming is currently only implemented partially and used only in a single, very specific use
	 * case; if renaming is required in the future, then the implementation of this method has to be complemented!
	 */
	public static void rename(SymbolTableEntry entry, String newName) {
		SymbolTableManagement.rename(entry, newName);
	}

	/**
	 * Copy a subtree of the intermediate model.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T copy(TranspilerState state, T elementInIM) {
		// create a copy with a special copier to take care of reference ReferencingElement_IM#rewiredTarget
		IM2IMCopier copier = new IM2IMCopier();
		EObject result = copier.copy(elementInIM);
		copier.copyReferences();
		// copy tracing information
		state.tracer.copyTrace(elementInIM, result); // note: copying trace for all nodes would be more fine grained
		return (T) result;
	}

	private static final class IM2IMCopier extends EcoreUtil.Copier {
		private static final EReference eRef__ReferencingElement_IM__rewiredTarget = ImPackage.eINSTANCE
				.getReferencingElement_IM_RewiredTarget();

		@Override
		protected void copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
			if (eReference == eRef__ReferencingElement_IM__rewiredTarget) {
				((ReferencingElement_IM) copyEObject)
						.setRewiredTarget(((ReferencingElement_IM) eObject).getRewiredTarget());
			} else {
				super.copyReference(eReference, eObject, copyEObject);
			}
		}

	}
}
