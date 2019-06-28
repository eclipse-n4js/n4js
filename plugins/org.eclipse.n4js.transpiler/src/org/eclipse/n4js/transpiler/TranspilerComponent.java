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
import org.eclipse.n4js.transpiler.operations.SymbolTableManagement;
import org.eclipse.n4js.transpiler.operations.TranspilerStateOperations;
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
	protected void replace(N4EnumDeclaration enumDecl, VariableDeclaration varDecl) {
		TranspilerStateOperations.replace(state, enumDecl, varDecl);
	}

	@SuppressWarnings("javadoc")
	protected void replace(N4EnumDeclaration enumDecl, FunctionDeclaration funDecl) {
		TranspilerStateOperations.replace(state, enumDecl, funDecl);
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
	// ################################################################################################################
	// Constant Values

	//// arguments

	/** "arguments" - retrieve the internal symbol table entry for the symbol "arguments" */
	public SymbolTableEntryInternal steFor_arguments() {

		return getSymbolTableEntryInternal("arguments", true);
	}

	//// N4ApiNotImplementedError

	/**
	 * "N4ApiNotImplementedError" - retrieve the internal symbol table entry for the symbol "N4ApiNotImplementedError"
	 */
	public SymbolTableEntryInternal steFor_N4ApiNotImplementedError() {

		return getSymbolTableEntryInternal("N4ApiNotImplementedError", true);
	}

	//// $makeClass

	/** "$makeClass" - retrieve the internal symbol table entry for the symbol "$makeClass" */
	public SymbolTableEntryInternal steFor_$makeClass() {

		return getSymbolTableEntryInternal("$makeClass", true);
	}

	//// $methods

	/** "$methods" - retrieve the internal symbol table entry for the symbol "$methods" */
	public SymbolTableEntryInternal steFor_$methods() {

		return getSymbolTableEntryInternal("$methods", true);
	}

	//// $makeInterface

	/** "$makeInterface" - retrieve the internal symbol table entry for the symbol "$makeInterface" */
	public SymbolTableEntryInternal steFor_$makeInterface() {

		return getSymbolTableEntryInternal("$makeInterface", true);
	}

	//// $makeEnum

	/** "$makeEnum" - retrieve the internal symbol table entry for the symbol "$makeEnum" */
	public SymbolTableEntryInternal steFor_$makeEnum() {

		return getSymbolTableEntryInternal("$makeEnum", true);
	}

	//// N4Class

	/** "N4Class" - retrieve the internal symbol table entry for the symbol "N4Class" */
	public SymbolTableEntryInternal steFor_N4Class() {

		return getSymbolTableEntryInternal("N4Class", true);
	}

	//// N4Interface

	/** "N4Interface" - retrieve the internal symbol table entry for the symbol "N4Interface" */
	public SymbolTableEntryInternal steFor_N4Interface() {

		return getSymbolTableEntryInternal("N4Interface", true);
	}

	//// N4EnumType

	/** "N4EnumType" - retrieve the internal symbol table entry for the symbol "N4EnumType" */
	public SymbolTableEntryInternal steFor_N4EnumType() {

		return getSymbolTableEntryInternal("N4EnumType", true);
	}

	//// N4Method

	/** "N4Method" - retrieve the internal symbol table entry for the symbol "N4Method" */
	public SymbolTableEntryInternal steFor_N4Method() {

		return getSymbolTableEntryInternal("N4Method", true);
	}

	//// N4Accessor

	/** "N4Accessor" - retrieve the internal symbol table entry for the symbol "N4Accessor" */
	public SymbolTableEntryInternal steFor_N4Accessor() {

		return getSymbolTableEntryInternal("N4Accessor", true);
	}

	//// N4DataField

	/** "N4DataField" - retrieve the internal symbol table entry for the symbol "N4DataField" */
	public SymbolTableEntryInternal steFor_N4DataField() {

		return getSymbolTableEntryInternal("N4DataField", true);
	}

	//// N4Member

	/** "N4Member" - retrieve the internal symbol table entry for the symbol "N4Member" */
	public SymbolTableEntryInternal steFor_N4Member() {

		return getSymbolTableEntryInternal("N4Member", true);
	}

	//// N4Annotation

	/** "N4Annotation" - retrieve the internal symbol table entry for the symbol "N4Annotation" */
	public SymbolTableEntryInternal steFor_N4Annotation() {

		return getSymbolTableEntryInternal("N4Annotation", true);
	}

	//// $fieldInit

	/** "$fieldInit" - retrieve the internal symbol table entry for the symbol "$fieldInit" */
	public SymbolTableEntryInternal steFor_$fieldInit() {

		return getSymbolTableEntryInternal("$fieldInit", true);
	}

	//// __proto__

	/** "__proto__" - retrieve the internal symbol table entry for the symbol "__proto__" */
	public SymbolTableEntryInternal steFor___proto__() {

		return getSymbolTableEntryInternal("__proto__", true);
	}

	//// get

	/** "get" - retrieve the internal symbol table entry for the symbol "get" */
	public SymbolTableEntryInternal steFor_get() {

		return getSymbolTableEntryInternal("get", true);
	}

	//// set

	/** "set" - retrieve the internal symbol table entry for the symbol "set" */
	public SymbolTableEntryInternal steFor_set() {

		return getSymbolTableEntryInternal("set", true);
	}

	//// value

	/** "value" - retrieve the internal symbol table entry for the symbol "value" */
	public SymbolTableEntryInternal steFor_value() {

		return getSymbolTableEntryInternal("value", true);
	}

	//// $sliceToArrayForDestruct

	/**
	 * "$sliceToArrayForDestruct" - retrieve the internal symbol table entry for the symbol "$sliceToArrayForDestruct"
	 */
	public SymbolTableEntryInternal steFor_$sliceToArrayForDestruct() {

		return getSymbolTableEntryInternal("$sliceToArrayForDestruct", true);
	}

	//// $implements

	/** "$implements" - retrieve the internal symbol table entry for the symbol "$implements" */
	public SymbolTableEntryInternal steFor_$implements() {

		return getSymbolTableEntryInternal("$implements", true);
	}

	//// $instanceof

	/** "$instanceof" - retrieve the internal symbol table entry for the symbol "$instanceof" */
	public SymbolTableEntryInternal steFor_$instanceof() {

		return getSymbolTableEntryInternal("$instanceof", true);
	}

	//// Array

	/** "Array" - retrieve the internal symbol table entry for the symbol "Array" */
	public SymbolTableEntryInternal steFor_Array() {

		return getSymbolTableEntryInternal("Array", true);
	}

	//// prototype

	/** "prototype" - retrieve the internal symbol table entry for the symbol "prototype" */
	public SymbolTableEntryInternal steFor_prototype() {

		return getSymbolTableEntryInternal("prototype", true);
	}

	//// slice

	/** "slice" - retrieve the internal symbol table entry for the symbol "slice" */
	public SymbolTableEntryInternal steFor_slice() {

		return getSymbolTableEntryInternal("slice", true);
	}

	//// call

	/** "call" - retrieve the internal symbol table entry for the symbol "call" */
	public SymbolTableEntryInternal steFor_call() {

		return getSymbolTableEntryInternal("call", true);
	}

	//// System

	/** "System" - retrieve the internal symbol table entry for the symbol "System" */
	public SymbolTableEntryInternal steFor_System() {

		return getSymbolTableEntryInternal("System", true);
	}

	//// register

	/** "register" - retrieve the internal symbol table entry for the symbol "register" */
	public SymbolTableEntryInternal steFor_register() {

		return getSymbolTableEntryInternal("register", true);
	}

	//// $n4Export

	/** "$n4Export" - retrieve the internal symbol table entry for the symbol "$n4Export" */
	public SymbolTableEntryInternal steFor_$n4Export() {

		return getSymbolTableEntryInternal("$n4Export", true);
	}

	//// $spawn

	/** "$spawn" - retrieve the internal symbol table entry for the symbol "$spawn" */
	public SymbolTableEntryInternal steFor_$spawn() {

		return getSymbolTableEntryInternal("$spawn", true);
	}

	//// $n4promisifyFunction

	/** "$n4promisifyFunction" - retrieve the internal symbol table entry for the symbol "$n4promisifyFunction" */
	public SymbolTableEntryInternal steFor_$n4promisifyFunction() {

		return getSymbolTableEntryInternal("$n4promisifyFunction", true);
	}

	//// $n4promisifyMethod

	/** "$n4promisifyMethod" - retrieve the internal symbol table entry for the symbol "$n4promisifyMethod" */
	public SymbolTableEntryInternal steFor_$n4promisifyMethod() {

		return getSymbolTableEntryInternal("$n4promisifyMethod", true);
	}

	/** "global" - retrieve the internal symbol table entry for the symbol "global" */
	public SymbolTableEntryInternal steFor_global() {

		return getSymbolTableEntryInternal("global", true);
	}

	/** "self" - retrieve the internal symbol table entry for the symbol "self" */
	public SymbolTableEntryInternal steFor_self() {

		return getSymbolTableEntryInternal("self", true);
	}

	/** "module" - retrieve the internal symbol table entry for the symbol "module" */
	public SymbolTableEntryInternal steFor_module() {

		return getSymbolTableEntryInternal("module", true);
	}

	/** "exports" - retrieve the internal symbol table entry for the symbol "exports" */
	public SymbolTableEntryInternal steFor_exports() {

		return getSymbolTableEntryInternal("exports", true);
	}

	/** "Object" - retrieve the internal symbol table entry for the symbol "Object" */
	public SymbolTableEntryInternal steFor_Object() {

		return getSymbolTableEntryInternal("Object", true);
	}

	/** "undefined" - retrieve the internal symbol table entry for the symbol "undefined" */
	public SymbolTableEntryInternal steFor_undefined() {

		return getSymbolTableEntryInternal("undefined", true);
	}

	/** "require" - retrieve the internal symbol table entry for the symbol "require" */
	public SymbolTableEntryInternal steFor_require() {

		return getSymbolTableEntryInternal("require", true);
	}

	/** "assign" - retrieve the internal symbol table entry for the symbol "assign" */
	public SymbolTableEntryInternal steFor_assign() {

		return getSymbolTableEntryInternal("assign", true);
	}
}
