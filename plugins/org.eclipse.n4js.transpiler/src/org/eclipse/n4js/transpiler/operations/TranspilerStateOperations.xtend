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
package org.eclipse.n4js.transpiler.operations

import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EObjectContainmentEList
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TranspilerBuilderBlocks
import org.eclipse.n4js.transpiler.TranspilerState
import org.eclipse.n4js.transpiler.im.ImPackage
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.transpiler.utils.TranspilerUtils
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.transpiler.operations.SymbolTableManagement.*

/**
 * Methods of this class provide elementary operations on a transpiler state, mainly on the intermediate model. The
 * intermediate model should only be changed through the operations defined by this class.
 * <p>
 * Main clients are AST transformations, but they should not invoke these operations directly, but instead use the
 * delegation methods in {@link Transformation}.
 */
class TranspilerStateOperations {

	/**
	 * Creates a new namespace import for the given module and adds it to the intermediate model of the given transpiler
	 * state. The returned symbol table entry can be used to create references to the namespace, e.g. by passing it to
	 * {@link TranspilerBuilderBlocks#_IdentRef(SymbolTableEntry)}. The newly created import can be obtained by calling
	 * {@link SymbolTableEntryOriginal#getImportSpecifier()} on the returned symbol table entry.
	 * <p>
	 * IMPORTANT: this method does not check if an import for the given module exists already or if the given namespace
	 * name is unique (i.e. does not avoid name clashes!).
	 */
	def public static SymbolTableEntryOriginal addNamespaceImport(TranspilerState state, TModule moduleToImport,
		String namespaceName) {

		// 1) create import declaration & specifier
		val importSpec = _NamespaceImportSpecifier(namespaceName, true);
		val importDecl = _ImportDecl(importSpec);
		// 2) create a temporary type to use as original target
		val typeForNamespace = TypesFactory.eINSTANCE.createModuleNamespaceVirtualType();
		typeForNamespace.name = namespaceName;
		state.resource.addTemporaryType(typeForNamespace); // make sure our temporary type is contained in a resource
		// 3) create a symbol table entry
		val steForNamespace = getSymbolTableEntryOriginal(state, typeForNamespace, true);
		steForNamespace.importSpecifier = importSpec;
		// 4) add import to intermediate model
		val scriptElements = state.im.scriptElements;
		if(scriptElements.empty) {
			scriptElements.add(importDecl);
		} else {
			insertBefore(state, scriptElements.get(0), importDecl);
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
	def public static SymbolTableEntryOriginal addNamedImport(TranspilerState state, IdentifiableElement elementToImport, String aliasOrNull) {
		val steOfElementToImport = getSymbolTableEntryOriginal(state, elementToImport, true);
		addNamedImport(state, steOfElementToImport, aliasOrNull);
		return steOfElementToImport;
	}

	/**
	 * Creates a new named import for the given STE and adds it to the intermediate model of the given transpiler
	 * state. The passed-in symbol table entry can be used to create references to the imported element, e.g. by passing
	 * it to {@link TranspilerBuilderBlocks#_IdentRef(SymbolTableEntry)}. The newly created import can be obtained by
	 * calling {@link SymbolTableEntryOriginal#getImportSpecifier()} on the passed-in symbol table entry.
	 * <p>
	 * If a named import already exists for the given element, nothing will be changed in the intermediate model. If the
	 * original target of the given symbol table entry is of type {@link ModuleNamespaceVirtualType} an exception will
	 * be thrown (because only namespace imports can be created for those types).
	 * <p>
	 * IMPORTANT: this method does not check if the given namespace name is unique (i.e. does not avoid name clashes!).
	 */
	def public static void addNamedImport(TranspilerState state, SymbolTableEntryOriginal steOfElementToImport, String aliasOrNull) {
		// check for valid type of element to be imported (i.e. the original target)
		val originalTarget = steOfElementToImport.originalTarget;
		if(originalTarget instanceof ModuleNamespaceVirtualType) {
			throw new IllegalArgumentException("cannot create named import for a ModuleNamespaceVirtualType");
		}
		// check for existing import
		val existingImportSpec = steOfElementToImport.importSpecifier;
		if(existingImportSpec!==null) {
			// import already exists, nothing to be done
			return;
		}

		// 1) create import declaration & specifier
		val importSpec = _NamedImportSpecifier(steOfElementToImport.exportedName, aliasOrNull, true);
		val importDecl = _ImportDecl(importSpec);
		// 2) add import to intermediate model
		val scriptElements = state.im.scriptElements;
		if(scriptElements.empty) {
			scriptElements.add(importDecl);
		} else {
			insertBefore(state, scriptElements.get(0), importDecl);
		}
		// 3) link symbol table entry to its newly created import specifier
		steOfElementToImport.importSpecifier = importSpec;
		// 4) update info registry
		val moduleOfOriginalTarget = originalTarget.containingModule;
		state.info.setImportedModule(importDecl, moduleOfOriginalTarget);
	}

	/**
	 * Adds an "empty" import to the intermediate model, i.e. an import of the form:
	 * <pre>
	 * import "&lt;moduleSpecifier>";
	 * </pre>
	 */
	def public static void addEmptyImport(TranspilerState state, String moduleSpecifier) {
		// 1) create import declaration
		val importDecl = _ImportDecl() => [
			moduleSpecifierAsText = moduleSpecifier
		];
		// 2) add import to intermediate model
		val scriptElements = state.im.scriptElements;
		if(scriptElements.empty) {
			scriptElements.add(importDecl);
		} else {
			insertBefore(state, scriptElements.get(0), importDecl);
		}
	}

	def public static void setTarget(TranspilerState state, ParameterizedCallExpression callExpr, Expression newTarget) {
		val oldTarget = callExpr.target;
		if(oldTarget!==null) {
			state.replaceWithoutRewire(oldTarget, newTarget);
		} else {
			callExpr.target = newTarget;
		}
	}

	def public static void setTarget(TranspilerState state, ParameterizedPropertyAccessExpression_IM accExpr, Expression newTarget) {
		val oldTarget = accExpr.target;
		if(oldTarget!==null) {
			state.replaceWithoutRewire(oldTarget, newTarget);
		} else {
			accExpr.target = newTarget;
		}
	}

	def public static void addArgument(TranspilerState state, ParameterizedCallExpression callExpr, int index, Expression newArgument) {
		callExpr.arguments.add(index, _Argument(newArgument));
	}

	def public static void remove(TranspilerState state, EObject elementInIM) {
		state.replaceWithoutRewire(elementInIM) // i.e. replace with nothing (will update tracer)
		if(elementInIM instanceof ReferencingElement_IM) {
			elementInIM.rewiredTarget = null; // important here: will remove elementInIM from its symbol table entry's 'referencingElements' list!
			// note: this update of the symbol table is incomplete; elementInIM may be the root of an entire subtree
			// of the IM, so we would have to iterate over all successors
		}
	}

	/**
	 * Removes the export-container (ExportDeclaration) by creating a new VariableStatement {@code varStmt}, moving all content from {@code exVarStmnt}
	 * into it and replacing the ExportDeclaration with the newly created {@code varStmt}
	 * @return newly created {@code varStmt} (already part of the intermediate model).
	 */
	def public static VariableStatement removeExport(TranspilerState state, ExportedVariableStatement exVarStmnt) {

		if(!TranspilerUtils.isIntermediateModelElement(exVarStmnt)) {
			throw new IllegalArgumentException("not an element in the intermediate model: " + exVarStmnt);
		}

		val exportDecl = exVarStmnt.eContainer as ExportDeclaration

		// convert to VariableStatement:
		val varStmnt = TranspilerBuilderBlocks._VariableStatement() => [
			varDeclsOrBindings += exVarStmnt.varDeclsOrBindings
			varStmtKeyword = exVarStmnt.varStmtKeyword
		];

		state.replaceWithoutRewire(exportDecl,varStmnt);

		return varStmnt
	}


	def public static void replace(TranspilerState state, Statement stmnt, ReturnStatement returnStmnt) {
		state.replaceWithoutRewire(stmnt, returnStmnt);
	}

	def public static void replace(TranspilerState state, N4ClassDeclaration classDecl, FunctionDeclaration funDecl) {
		state.replaceWithoutRewire(classDecl, funDecl);
		state.rewireSymbolTable(classDecl, funDecl);
	}

	/**
	 * Replace an interface declaration by a variable declaration. The variable declaration will be wrapped in a
	 * newly created [Exported]VariableStatement.
	 */
	def public static void replace(TranspilerState state, N4InterfaceDeclaration ifcDecl, VariableDeclaration varDecl) {
		val isExported = ifcDecl.eContainer instanceof ExportDeclaration;
		val varStmnt = _VariableStatement(isExported, varDecl);
		state.replaceWithoutRewire(ifcDecl, varStmnt);
		state.rewireSymbolTable(ifcDecl, varDecl);
	}

	/**
	 * Replace an enum declaration by a variable declaration. The variable declaration will be wrapped in a
	 * newly created [Exported]VariableStatement.
	 */
	def public static void replace(TranspilerState state, N4EnumDeclaration enumDecl, VariableDeclaration varDecl) {
		val isExported = enumDecl.eContainer instanceof ExportDeclaration;
		val varStmnt = _VariableStatement(isExported, varDecl);
		state.replaceWithoutRewire(enumDecl, varStmnt);
		state.rewireSymbolTable(enumDecl, varDecl);
	}

	/**
	 * Replace an enum declaration by a function declaration.
	 */
	def public static void replace(TranspilerState state, N4EnumDeclaration enumDecl, FunctionDeclaration funDecl) {
		state.replaceWithoutRewire(enumDecl, funDecl);
		state.rewireSymbolTable(enumDecl, funDecl);
	}

	def public static void replace(TranspilerState state, FunctionDeclaration funDecl, VariableDeclaration varDecl) {
		val isExported = funDecl.eContainer instanceof ExportDeclaration;
		val varStmnt = _VariableStatement(isExported, varDecl);
		state.replaceWithoutRewire(funDecl, varStmnt);
		state.rewireSymbolTable(funDecl,varDecl);
		// need to rewire the local arguments variable, to enable renaming:
		val varValue = varDecl.expression;
		if(varValue instanceof FunctionExpression) {
			state.rewireSymbolTable(funDecl.localArgumentsVariable, varValue.localArgumentsVariable);
		} else {
			throw new IllegalArgumentException(
				"when replacing a function declaration by a variable declaration, " +
				"we expect the variable to be initialized with a function expression");
		}
	}
	
	def public static void replace(TranspilerState state, FunctionDeclaration functionDecl, ExpressionStatement stmt) {
		state.replaceWithoutRewire(functionDecl, stmt);
	}

	def public static void replace(TranspilerState state, N4MemberDeclaration memberDecl, N4MemberDeclaration replacement) {
		state.replaceWithoutRewire(memberDecl, replacement);
		state.rewireSymbolTable(memberDecl, replacement);
	}

	def public static void replace(TranspilerState state, VariableStatement varStmnt, Statement... newStmnts) {
		state.replaceWithoutRewire(varStmnt, newStmnts);
	}

	def public static void replace(TranspilerState state, VariableBinding varBinding, VariableDeclaration... varDecls) {
		state.replaceWithoutRewire(varBinding, varDecls);
	}

	def public static void replace(TranspilerState state, Expression exprOld, Expression exprNew) {
		state.replaceWithoutRewire(exprOld, exprNew);
	}

	def public static void replace(TranspilerState state, ArrowFunction exprOld, ParameterizedCallExpression exprNew, FunctionExpression rewireTarget) {
		state.replaceWithoutRewire(exprOld, exprNew);
		state.rewireSymbolTable(exprOld, rewireTarget);
	}


	/** Replace formal parameter with a variableStmt. Rewire the fpar to the VariableDeclaration. Relocate the Stmt  */
	def public static void replaceAndRelocate(TranspilerState state, FormalParameter fPar_to_remove, VariableStatement varStmnt,
		VariableDeclaration varDecl_wireTo, Block newContainer ) {
		if(varDecl_wireTo.eContainer!==varStmnt) {
			throw new IllegalArgumentException("varDecl must be contained in varStmnt");
		}
		state.replaceAndRelocateWithoutRewire_internal(fPar_to_remove, varStmnt, newContainer.statements, 0);

		state.rewireSymbolTable(fPar_to_remove, varDecl_wireTo);
	}

	def public static <T extends Expression> void wrapExistingExpression(TranspilerState state,
		T exprToWrap, Expression outerExpr_without_exprToWrap, (T)=>void inserterFunction
	) {
		state.insertOrReplace_internal(exprToWrap, #[outerExpr_without_exprToWrap], true, false);
		inserterFunction.apply(exprToWrap)
	}

	/* append( pos < 0 or > current size ), prepend(pos==0) or insert at {@code pos} the object {@code insertThis}
	 * to {@code newContainer}. Also delete {@code removeThis} from the IM.  Does not rewire. But keeps trace.*/
	def private static void replaceAndRelocateWithoutRewire_internal(TranspilerState state, EObject removeThis, EObject insertThis,
		EList<?> newContainer, int pos ) {


		val eRefRemove = checkedContainmentFeature(removeThis);

		if( insertThis.eContainer !== null ) throw new IllegalArgumentException("The new element must not be contained anywhere."+
			" insertThis="+insertThis+" is currently contained in "+insertThis.eContainer);

		if( newContainer instanceof EObjectContainmentEList<?> ){
			val newContainerCasted = newContainer as EObjectContainmentEList<EObject>;

			//// Tracing:
			state.tracer.copyTrace(removeThis, insertThis);
			state.tracer.discardIntermediateModelNode(removeThis);

			////////////////////////////////////
			//// remove:
			if( eRefRemove.upperBound == 1 ) { // single-value
				if( eRefRemove.isUnsettable )
					removeThis.eContainer.eUnset(eRefRemove)
				else
				 	removeThis.eContainer.eSet(eRefRemove,null)
			} else { // multivalue
				val l = removeThis.eContainer.eGet(eRefRemove) as List<EObject>; // c.f. type check above
				var idx = l.indexOf(removeThis);
				l.remove(idx);
			}

			////////////////////////////////////
			//// insert:
			val idx = if( pos < 0 || pos >= newContainer.size ) {
				// append
				newContainer.size
			} else {
				// insert
				pos
			};
			newContainerCasted.add(idx,insertThis)

		} else {
			throw new IllegalArgumentException("designated new container-list must be a subtype of type EObjectContainmentList")
		}
	}

	/**
	 * {@code elementInIntermediateModel} is going away (ie, should be garbage-collected) and therefore we clear all
	 * references to it from tracing. The {@code replacements} IM nodes take over whatever AST node was previously
	 * traced-back-to via the element going away.
	 */
	def private static void replaceWithoutRewire(TranspilerState state, EObject elementInIntermediateModel, EObject... replacements) {
		state.tracer.copyTrace(elementInIntermediateModel, replacements);
		state.tracer.discardIntermediateModelNode(elementInIntermediateModel);
		state.insertOrReplace_internal(elementInIntermediateModel, replacements, true, false);
	}


	def public static void insertBefore(TranspilerState state, EObject elementInIntermediateModel, EObject... newElements) {
		state.insertOrReplace_internal(elementInIntermediateModel, newElements, false, false);
	}

	def public static void insertAfter(TranspilerState state, EObject elementInIntermediateModel, EObject... newElements) {
		state.insertOrReplace_internal(elementInIntermediateModel, newElements, false, true);
	}

	def private static void insertOrReplace_internal(TranspilerState state, EObject elementInIntermediateModel,
		EObject[] newElements, boolean replace, boolean after
	) {
		if(newElements.empty && ! replace) {
			return; // nothing to be inserted
		}
		val eRef = checkedContainmentFeature(elementInIntermediateModel);
		val eRefType = eRef.getEReferenceType;
		val replElemsOfWrongType = newElements.filter[!eRefType.isSuperTypeOf(it.eClass)];
		if(!replElemsOfWrongType.empty) {
			throw new IllegalArgumentException("one or more elements are of wrong type, expected: "
				+ eRef.EReferenceType.name + ", actual: " + replElemsOfWrongType.map[eClass.name].join(', '));
		}
		if( eRef.upperBound == 1 ) {
			// single valued
			if( newElements.length > 1 )
				throw new IllegalArgumentException("the single-valued reference "+eRef.name+" in class "+eRef.EContainingClass
						+  " is not able to hold "+newElements.length +" elements.");
			if( newElements.length == 1 )
			{
				if( !replace )
					throw new IllegalArgumentException("Cannot insert another element into a single-valued containment reference "+eRef.name+" in class "+eRef.EContainingClass );
				elementInIntermediateModel.eContainer.eSet(eRef,newElements.get(0));
			} else {
				if( !replace )
					throw new IllegalArgumentException("Inserting zero elements with replace==false is pointless.");
				// no element, so remove
				if( eRef.isUnsettable )
					elementInIntermediateModel.eContainer.eUnset(eRef)
				else
				 	elementInIntermediateModel.eContainer.eSet(eRef,null)
			}

		} else {
			// multi-valued
			val l = elementInIntermediateModel.eContainer.eGet(eRef) as List<EObject>; // c.f. type check above
			var idx = l.indexOf(elementInIntermediateModel);
			if(replace) {
				l.remove(idx);
			} else {
				// note: before/after only applicable if !replace
				if(after) {
					idx++; // always safe to increment, because we know there exists an element at index 'idx'
				}
			}
			l.addAll(idx, newElements);
		}
	}

	/** Retrieves the EReference of the Container. Throws Exceptions if a) not part of the IM or b) not contained anywhere*/
	def private static EReference checkedContainmentFeature(EObject elementInIntermediateModel) {
		if(!TranspilerUtils.isIntermediateModelElement(elementInIntermediateModel)) {
			throw new IllegalArgumentException("not an element in the intermediate model: " + elementInIntermediateModel);
		}
		val eRef = elementInIntermediateModel.eContainmentFeature;
		if(eRef===null) {
			throw new IllegalArgumentException("element is not contained anywhere");
		}
		return eRef;
	}

	/**
	 * Rename the given symbol table entry and all named elements in the intermediate model that are using this name.
	 * During AST transformations in the transpiler, the 'name' property of a symbol table entry should never be changed
	 * directly, but this operation should be used instead.
	 * <p>
	 * <b>WARNING:</b> renaming is currently only implemented partially and used only in a single, very specific use
	 * case; if renaming is required in the future, then the implementation of this method has to be complemented!
	 */
	def public static void rename(TranspilerState state, SymbolTableEntry entry, String newName) {
		SymbolTableManagement.rename(state, entry, newName );
	}

	/**
	 * Copy a subtree of the intermediate model.
	 */
	def public static <T extends EObject> T copy(TranspilerState state, T elementInIM) {
		// create a copy with a special copier to take care of reference ReferencingElement_IM#rewiredTarget
	    val copier = new IM2IMCopier();
	    val result = copier.copy(elementInIM);
	    copier.copyReferences();
	    // copy tracing information
	    state.tracer.copyTrace(elementInIM, result); // note: copying trace for all nodes would be more fine grained
	    return result as T;
	}

	private static final class IM2IMCopier extends EcoreUtil.Copier {
		private static final EReference eRef__ReferencingElement_IM__rewiredTarget = ImPackage.eINSTANCE.referencingElement_IM_RewiredTarget;

		override protected copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
			if(eReference===eRef__ReferencingElement_IM__rewiredTarget) {
				(copyEObject as ReferencingElement_IM).rewiredTarget = (eObject as ReferencingElement_IM).rewiredTarget;
			} else {
				super.copyReference(eReference, eObject, copyEObject);
			}
		}

	}
}
