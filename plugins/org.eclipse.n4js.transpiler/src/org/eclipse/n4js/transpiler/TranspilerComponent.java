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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.ReferencingElementExpression_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryIMOnly;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM;
import org.eclipse.n4js.transpiler.utils.TranspilerDebugUtils;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Abstract base class for {@link Transformation}s and {@link TransformationAssistant}s, containing convenience delegate
 * methods to access and modify the current transpiler state, mainly the intermediate model. It stores a transpiler
 * state (retrieved via injection) and delegates to
 * <ul>
 * <li>{@link TranspilerStateOperations}
 * <li>{@link SymbolTableManagement}
 * </ul>
 * <p>
 * Transpiler components may use dependency injection (i.e. instances will be created via an appropriate injector).
 */
public abstract class TranspilerComponent {

	@Inject
	private TranspilerState state;
	@Inject
	private PreparationStep preparationStep;

	/**
	 * Default constructor.
	 */
	public TranspilerComponent() {
		if (getClass().isAnnotationPresent(Singleton.class))
			throw new IllegalStateException("subclasses of TranspilerComponent must not be annotated with @Singleton");
	}

	/**
	 * Returns the transpiler state.
	 */
	protected TranspilerState getState() {
		if (state == null) {
			throw new IllegalStateException("transpiler state not available");
		}
		return state;
	}

	// ###############################################################################################################
	// DELEGATION METHODS TO TRANSPILER STATE OPERATIONS

	/** See {@link TranspilerStateOperations#addNamespaceImport(TranspilerState, TModule, String)}. */
	protected SymbolTableEntryOriginal addNamespaceImport(TModule moduleToImport, String namespaceName) {
		return TranspilerStateOperations.addNamespaceImport(state, moduleToImport, namespaceName);
	}

	/** See {@link TranspilerStateOperations#addNamedImport(TranspilerState, IdentifiableElement, String)}. */
	public SymbolTableEntryOriginal addNamedImport(IdentifiableElement elementToImport, String aliasOrNull) {
		return TranspilerStateOperations.addNamedImport(state, elementToImport, aliasOrNull);
	}

	/** See {@link TranspilerStateOperations#addNamedImport(TranspilerState, SymbolTableEntryOriginal, String)}. */
	public void addNamedImport(SymbolTableEntryOriginal steOfElementToImport, String aliasOrNull) {
		TranspilerStateOperations.addNamedImport(state, steOfElementToImport, aliasOrNull);
	}

	/** See {@link TranspilerStateOperations#addEmptyImport(TranspilerState, String)}. */
	public void addEmptyImport(String moduleSpecifier) {
		TranspilerStateOperations.addEmptyImport(state, moduleSpecifier);
	}

	/** See {@link TranspilerStateOperations#addOrGetTemporaryVariable(TranspilerState, String, EObject)}. */
	public SymbolTableEntryIMOnly addOrGetTemporaryVariable(String name, EObject nodeInIM) {
		return TranspilerStateOperations.addOrGetTemporaryVariable(state, name, nodeInIM);
	}

	@SuppressWarnings("javadoc")
	protected void setTarget(ParameterizedCallExpression callExpr, Expression newTarget) {
		TranspilerStateOperations.setTarget(state, callExpr, newTarget);
	}

	@SuppressWarnings("javadoc")
	protected void setTarget(ParameterizedPropertyAccessExpression_IM accExpr, Expression newTarget) {
		TranspilerStateOperations.setTarget(state, accExpr, newTarget);
	}

	@SuppressWarnings("javadoc")
	protected void addArgument(ParameterizedCallExpression callExpr, int index, Expression newArgument) {
		TranspilerStateOperations.addArgument(state, callExpr, index, newArgument);
	}

	@SuppressWarnings("javadoc")
	protected void remove(EObject elementInIntermediateModel) {
		TranspilerStateOperations.remove(state, elementInIntermediateModel);
	}

	@SuppressWarnings("javadoc")
	protected VariableStatement removeExport(ExportedVariableStatement varStmt) {
		return TranspilerStateOperations.removeExport(state, varStmt);
	}

	@SuppressWarnings("javadoc")
	protected void replace(N4ClassDeclaration classDecl, FunctionDeclaration funDecl) {
		TranspilerStateOperations.replace(state, classDecl, funDecl);
	}

	@SuppressWarnings("javadoc")
	protected void replace(N4InterfaceDeclaration ifcDecl, VariableDeclaration varDecl) {
		TranspilerStateOperations.replace(state, ifcDecl, varDecl);
	}

	@SuppressWarnings("javadoc")
	protected void replace(N4EnumDeclaration enumDecl, N4ClassDeclaration classDecl) {
		TranspilerStateOperations.replace(state, enumDecl, classDecl);
	}

	@SuppressWarnings("javadoc")
	protected void replace(N4MemberDeclaration memberDecl, N4MemberDeclaration replacement) {
		TranspilerStateOperations.replace(state, memberDecl, replacement);
	}

	@SuppressWarnings("javadoc")
	protected void replace(FunctionDeclaration funDecl, VariableDeclaration varDecl) {
		TranspilerStateOperations.replace(state, funDecl, varDecl);
	}

	@SuppressWarnings("javadoc")
	protected void replace(VariableStatement varStmnt, Statement... newStmnts) {
		TranspilerStateOperations.replace(state, varStmnt, newStmnts);
	}

	@SuppressWarnings("javadoc")
	protected void replace(VariableBinding varBinding, VariableDeclaration... varDecls) {
		TranspilerStateOperations.replace(state, varBinding, varDecls);
	}

	@SuppressWarnings("javadoc")
	protected void replace(Statement stmnt, ReturnStatement returnStmnt) {
		TranspilerStateOperations.replace(state, stmnt, returnStmnt);
	}

	@SuppressWarnings("javadoc")
	protected void replace(Expression exprOld, Expression exprNew) {
		TranspilerStateOperations.replace(state, exprOld, exprNew);
	}

	@SuppressWarnings("javadoc")
	protected void replace(ArrowFunction exprOld, ParameterizedCallExpression exprNew,
			FunctionExpression rewireTarget) {
		TranspilerStateOperations.replace(state, exprOld, exprNew, rewireTarget);
	}

	@SuppressWarnings("javadoc")
	protected void replaceAndRelocate(FormalParameter fPar_to_remove, VariableStatement varStmnt,
			VariableDeclaration varDecl_wireTo, Block newContainer) {
		TranspilerStateOperations.replaceAndRelocate(state, fPar_to_remove, varStmnt, varDecl_wireTo, newContainer);
	}

	@SuppressWarnings("javadoc")
	protected <T extends Expression> void wrapExistingExpression(T exprToWrap, Expression outerExpr_without_exprToWrap,
			Procedure1<? super T> inserterFunction) {
		TranspilerStateOperations.wrapExistingExpression(state, exprToWrap, outerExpr_without_exprToWrap,
				inserterFunction);
	}

	/** Delegates to {@link TranspilerStateOperations#insertBefore(TranspilerState, EObject, EObject...)}. */
	protected void insertBefore(EObject elementInIntermediateModel, EObject... newElements) {
		TranspilerStateOperations.insertBefore(state, elementInIntermediateModel, newElements);
	}

	/** Delegates to {@link TranspilerStateOperations#insertAfter(TranspilerState, EObject, EObject...)}. */
	protected void insertAfter(EObject elementInIntermediateModel, EObject... newElements) {
		TranspilerStateOperations.insertAfter(state, elementInIntermediateModel, newElements);
	}

	/** Delegates to {@link TranspilerStateOperations#copy(TranspilerState, EObject)}. */
	protected <T extends EObject> T copy(T elementInIM) {
		return TranspilerStateOperations.copy(state, elementInIM);
	}

	/**
	 * Copy some element <b>not</b> contained in the intermediate model in a way such that the copy can then be inserted
	 * into the intermediate model (but it won't be inserted by this method!). The element passed in may be an original
	 * AST node, an AST node in some remote resource (not the resource to compile), or some other element of a type that
	 * may appear in the IM (e.g. a type reference).
	 * <p>
	 * Currently, this is mainly used for static polyfills. Should be used with caution.
	 */
	public <T extends EObject> T copyAlienElement(T someElement) {
		if (TranspilerUtils.isIntermediateModelElement(someElement)) {
			throw new IllegalArgumentException(
					"method #copyAlienElement() not intended for copying IM elements - use method #copy() instead!");
		}
		// forwarding to PreparationStep is a bit dirty, but avoids code duplication for now; could be refactored
		return preparationStep.copyForIM(state, someElement);
	}

	// ###############################################################################################################
	// DELEGATION METHODS TO SYMBOL TABLE MANAGEMENT

	@SuppressWarnings("javadoc")
	protected SymbolTableEntryOriginal createSymbolTableEntryOriginal(IdentifiableElement originalTarget) {
		return SymbolTableManagement.createSymbolTableEntryOriginal(state, originalTarget);
	}

	@SuppressWarnings("javadoc")
	protected SymbolTableEntryIMOnly createSymbolTableEntryIMOnly(NamedElement elementInIntermediateModel) {
		return SymbolTableManagement.createSymbolTableEntryIMOnly(state, elementInIntermediateModel);
	}

	@SuppressWarnings("javadoc")
	protected SymbolTableEntryInternal createSymbolTableEntryInternal(String name) {
		return SymbolTableManagement.createSymbolTableEntryInternal(state, name);
	}

	@SuppressWarnings("javadoc")
	protected SymbolTableEntryOriginal getSymbolTableEntryOriginal(IdentifiableElement originalTarget, boolean create) {
		return SymbolTableManagement.getSymbolTableEntryOriginal(state, originalTarget, create);
	}

	@SuppressWarnings("javadoc")
	protected SymbolTableEntryOriginal getSymbolTableEntryForMember(TClassifier type, String memberName,
			boolean writeAccess, boolean staticAccess, boolean create) {
		return SymbolTableManagement.getSymbolTableEntryForMember(state, type, memberName, writeAccess,
				staticAccess, create);
	}

	@SuppressWarnings("javadoc")
	protected SymbolTableEntry findSymbolTableEntryForElement(NamedElement elementInIntermediateModel, boolean create) {
		return SymbolTableManagement.findSymbolTableEntryForElement(state, elementInIntermediateModel, create);
	}

	@SuppressWarnings("javadoc")
	protected SymbolTableEntryInternal getSymbolTableEntryInternal(String name, boolean create) {
		return SymbolTableManagement.getSymbolTableEntryInternal(state, name, create);
	}

	@SuppressWarnings("javadoc")
	protected SymbolTableEntryOriginal findSymbolTableEntryForNamespaceImport(NamespaceImportSpecifier importspec) {
		return SymbolTableManagement.findSymbolTableEntryForNamespaceImport(state, importspec);
	}

	@SuppressWarnings("javadoc")
	protected SymbolTableEntryOriginal findSymbolTableEntryForNamedImport(NamedImportSpecifier importspec) {
		return SymbolTableManagement.findSymbolTableEntryForNamedImport(state, importspec);
	}

	@SuppressWarnings("javadoc")
	protected Collection<SymbolTableEntryOriginal> findSymbolTableEntriesForVersionedTypeImport(
			VersionedNamedImportSpecifier_IM importspec) {
		return SymbolTableManagement.findSymbolTableEntriesForVersionedTypeImport(state, importspec);
	}

	@SuppressWarnings("javadoc")
	protected void rename(SymbolTableEntry entry, String newName) {
		TranspilerStateOperations.rename(state, entry, newName);
	}

	@SuppressWarnings("javadoc")
	public void addOriginal(SymbolTableEntryOriginal steOriginal) {
		SymbolTableManagement.addOriginal(state, steOriginal);
	}

	// ###############################################################################################################
	// DELEGATION METHODS TO TRANSPILER UTILITIES

	@SuppressWarnings("javadoc")
	public <T extends EObject> List<T> collectNodes(EObject root, Class<T> cls, boolean searchForNestedNodes) {
		return TranspilerUtils.collectNodes(root, cls, searchForNestedNodes);
	}

	@SuppressWarnings("javadoc")
	protected boolean hasNonTrivialInitExpression(N4FieldDeclaration fieldDecl) {
		return TranspilerUtils.hasNonTrivialInitExpression(state.G, fieldDecl);
	}

	// ###############################################################################################################
	// GENERAL HELPER METHODS

	/**
	 * In cases where the Type will be transformed into an IdentifierRef this method ensures, that in cases of
	 * NamespaceImports the name-space will be prefixed.
	 *
	 * @param entry
	 *            SymboltableEntry of the imported thing.
	 * @return one of {@link IdentifierRef_IM} or {@link ParameterizedPropertyAccessExpression_IM}
	 */
	public ReferencingElementExpression_IM __NSSafe_IdentRef(SymbolTableEntry entry) {
		if (entry instanceof SymbolTableEntryOriginal) {
			final ImportSpecifier impSpec = ((SymbolTableEntryOriginal) entry).getImportSpecifier();
			if (impSpec instanceof NamespaceImportSpecifier) {
				SymbolTableEntry steNS = findSymbolTableEntryForNamespaceImport((NamespaceImportSpecifier) impSpec);
				return TranspilerBuilderBlocks._PropertyAccessExpr(steNS, entry);
			}
		}
		return TranspilerBuilderBlocks._IdentRef(entry);
	}

	/**
	 * In cases where the target is an STE of something which is possibly imported through a name-space. Will delegate
	 * to {@link #__NSSafe_IdentRef(SymbolTableEntry)} in the construction of the PropertyAccess.
	 *
	 * @param target
	 *            SymboltableEntry of the imported thing. Might be imported with NamespaceImport.
	 */
	public ParameterizedPropertyAccessExpression_IM __NSSafe_PropertyAccessExpr(SymbolTableEntry target,
			SymbolTableEntry... properties) {
		return TranspilerBuilderBlocks._PropertyAccessExpr(__NSSafe_IdentRef(target), properties);
	}

	// ###############################################################################################################
	// CONVENIENCE METHODS FOR ASSERTIONS IN #assertPreConditions() AND #assertPostConditions()

	/**
	 * Asserts {@code value} to be <code>true</code> and throws an {@link AssertionError} otherwise.
	 */
	protected void assertTrue(String message, boolean value) {
		TranspilerDebugUtils.assertTrue(message, value);
	}

	/**
	 * Asserts {@code value} to be <code>false</code> and throws an {@link AssertionError} otherwise.
	 */
	protected void assertFalse(String message, boolean value) {
		TranspilerDebugUtils.assertFalse(message, value);
	}

	/**
	 * Asserts {@code value} to be <code>null</code> and throws an {@link AssertionError} otherwise.
	 */
	protected void assertNull(String message, Object value) {
		TranspilerDebugUtils.assertNull(message, value);
	}

	/**
	 * Asserts {@code value} to be non-<code>null</code> and throws an {@link AssertionError} otherwise.
	 */
	protected void assertNotNull(String message, Object value) {
		TranspilerDebugUtils.assertNotNull(message, value);
	}

	// ################################################################################################################
	// UTILITY STUFF

	/**
	 * Returns a new reference to 'undefined'.
	 */
	protected IdentifierRef_IM undefinedRef() {
		return TranspilerBuilderBlocks._IdentRef(getSymbolTableEntryForMember(
				RuleEnvironmentExtensions.globalObjectType(state.G), "undefined", false, false, true));
	}

	// ################################################################################################################
	// Constant Values

	/** Returns the internal symbol table entry for the symbol "arguments". */
	public SymbolTableEntryInternal steFor_arguments() {
		return getSymbolTableEntryInternal("arguments", true);
	}

	/** Returns the internal symbol table entry for the symbol "N4ApiNotImplementedError". */
	public SymbolTableEntryInternal steFor_N4ApiNotImplementedError() {
		return getSymbolTableEntryInternal("N4ApiNotImplementedError", true);
	}

	/** Returns the internal symbol table entry for the symbol "$fieldDefaults". */
	public SymbolTableEntryInternal steFor_$fieldDefaults() {
		return getSymbolTableEntryInternal("$fieldDefaults", true);
	}

	/** Returns the internal symbol table entry for the symbol "$members". */
	public SymbolTableEntryInternal steFor_$members() {
		return getSymbolTableEntryInternal("$members", true);
	}

	/** Returns the internal symbol table entry for the symbol "$getReflectionForClass". */
	public SymbolTableEntryInternal steFor_$getReflectionForClass() {
		return getSymbolTableEntryInternal("$getReflectionForClass", true);
	}

	/** Returns the internal symbol table entry for the symbol "$getReflectionForInterface". */
	public SymbolTableEntryInternal steFor_$getReflectionForInterface() {
		return getSymbolTableEntryInternal("$getReflectionForInterface", true);
	}

	/** Returns the internal symbol table entry for the symbol "$getReflectionForEnum". */
	public SymbolTableEntryInternal steFor_$getReflectionForEnum() {
		return getSymbolTableEntryInternal("$getReflectionForEnum", true);
	}

	/** Returns the internal symbol table entry for the symbol "$defineFields". */
	public SymbolTableEntryInternal steFor_$defineFields() {
		return getSymbolTableEntryInternal("$defineFields", true);
	}

	/** Returns the internal symbol table entry for the symbol "$initFieldsFromInterfaces". */
	public SymbolTableEntryInternal steFor_$initFieldsFromInterfaces() {
		return getSymbolTableEntryInternal("$initFieldsFromInterfaces", true);
	}

	/** Returns the internal symbol table entry for the symbol "prototype". */
	public SymbolTableEntryInternal steFor_prototype() {
		return getSymbolTableEntryInternal("prototype", true);
	}

	/** Returns the internal symbol table entry for the symbol "get". */
	public SymbolTableEntryInternal steFor_get() {
		return getSymbolTableEntryInternal("get", true);
	}

	/** Returns the internal symbol table entry for the symbol "set". */
	public SymbolTableEntryInternal steFor_set() {
		return getSymbolTableEntryInternal("set", true);
	}

	/** Returns the internal symbol table entry for the symbol "value". */
	public SymbolTableEntryInternal steFor_value() {
		return getSymbolTableEntryInternal("value", true);
	}

	/** Returns the internal symbol table entry for the symbol "$sliceToArrayForDestruct". */
	public SymbolTableEntryInternal steFor_$sliceToArrayForDestruct() {
		return getSymbolTableEntryInternal("$sliceToArrayForDestruct", true);
	}

	/** Returns the internal symbol table entry for the symbol "$n4promisifyFunction". */
	public SymbolTableEntryInternal steFor_$n4promisifyFunction() {
		return getSymbolTableEntryInternal("$n4promisifyFunction", true);
	}

	/** Returns the internal symbol table entry for the symbol "$n4promisifyMethod". */
	public SymbolTableEntryInternal steFor_$n4promisifyMethod() {
		return getSymbolTableEntryInternal("$n4promisifyMethod", true);
	}

	/** Returns the symbol table entry for built-in type "Object". */
	public SymbolTableEntryOriginal steFor_Object() {
		return getSymbolTableEntryOriginal(RuleEnvironmentExtensions.objectType(state.G), true);
	}

	/** Returns the symbol table entry for member "assign" in "Object". */
	public SymbolTableEntry steFor_Object_assign() {
		// note: Object#assign() is ES6 and thus not defined in our built-in type "Object", so
		// we cannot use #getSymbolTableEntryForMember() here!
		return getSymbolTableEntryInternal("assign", true);
	}

	/** Returns the symbol table entry for member "defineProperty" in "Object". */
	public SymbolTableEntryOriginal steFor_Object_defineProperty() {
		return getSymbolTableEntryForMember(RuleEnvironmentExtensions.objectType(state.G),
				"defineProperty", false, true, true);
	}

	/** Returns the symbol table entry for built-in type "Array". */
	public SymbolTableEntryOriginal steFor_Array() {
		return getSymbolTableEntryOriginal(RuleEnvironmentExtensions.arrayType(state.G), true);
	}

	/** Returns the symbol table entry for member "slice" in "Array". */
	public SymbolTableEntryOriginal steFor_Array_slice() {
		return getSymbolTableEntryForMember(RuleEnvironmentExtensions.arrayType(state.G),
				"slice", false, false, true);
	}

	/** Returns the symbol table entry for built-in type "Function". */
	public SymbolTableEntryOriginal steFor_Function() {
		return getSymbolTableEntryOriginal(RuleEnvironmentExtensions.functionType(state.G), true);
	}

	/** Returns the symbol table entry for member "call" in "Function". */
	public SymbolTableEntryOriginal steFor_Function_call() {
		return getSymbolTableEntryForMember(RuleEnvironmentExtensions.functionType(state.G),
				"call", false, false, true);
	}
}
