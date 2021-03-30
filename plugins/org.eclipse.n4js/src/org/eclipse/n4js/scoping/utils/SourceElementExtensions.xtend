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
package org.eclipse.n4js.scoping.utils

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration
import org.eclipse.n4js.n4JS.N4TypeVariable
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.n4JS.VariableEnvironmentElement
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.xtext.util.IResourceScopeCache

import static extension org.eclipse.n4js.n4JS.N4JSASTUtils.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Extensions for source element, in particular for statements.
 * Because this extension requires the type inferencer, it is part of the n4js bundle rather than the n4js.model bundle.
 */
@Singleton
class SourceElementExtensions {

	@Inject extension IResourceScopeCache cache

	/**
	 * Collects all elements visible from the given element. This will include the element itself, if it is either a
	 * {@link FunctionExpression} or a {@link IdentifiableElement}. Note that a {@code N4Class} is not variable environment,
	 * so you cannot scope elements in its content. All the children of given element will also be searched for visible
	 * identifiable elements.
	 *
	 * this element will be walked through
	 * @param element the given element
	 * @return the list of EObjects visible
	 */
	def List<IdentifiableElement> collectVisibleIdentifiableElements(VariableEnvironmentElement element) {
		cache.get('collectVisibleIdentifiableElements'->element, element.eResource) [|
			val result = <IdentifiableElement>newArrayList
			switch (element) {
				IdentifiableElement:
					result += (element as IdentifiableElement)
				FunctionExpression case element.name !== null:
					element.collectVisibleTypedElement(result)
			}
			doCollectVisibleIdentifiableElements(element, element, true, result)
			result
		]
	}

	/** collect only the local arguments variable - if available.
	 * @param element - an possible provider of an arguments variable (function,function-expression, getter/setter, mehtod)
	 * @return list with single entry of an arguments variable or empty list.
	 */
	def List<IdentifiableElement> collectLocalArguments(VariableEnvironmentElement element) {
		cache.get('collectLocalArguments'->element, element.eResource) [|
			val result = <IdentifiableElement>newArrayList
			if( element instanceof FunctionOrFieldAccessor ) {
				if(!(element instanceof ArrowFunction)) {
					result.add( element.localArgumentsVariable );
				}
			}
			result
		]
	}
	
	/** @return the defined type of the given element or the declared type of the corresponding polyfilled class */
	def Type getTypeOrPolyfilledType(TypeDefiningElement tde) {
		if (tde instanceof N4ClassDeclaration) {
			if (tde.isPolyfill
				||	tde.isStaticPolyfill) { // in polyfill? delegate to filled type and its type variables
				val filledType = tde.definedTypeAsClass?.superClassRef?.declaredType;
				return filledType;
			}
		}
		return tde.definedType;
	}

	def private <T extends TypeDefiningElement> collectVisibleTypedElement(T element,
		List<? super IdentifiableElement> addHere) {
		element.collectVisibleIdentifiableElement(addHere, [T e|e.definedType])
	}

	def private <T extends ExportedVariableDeclaration> collectVisibleVariable(T element,
		List<? super IdentifiableElement> addHere) {
		element.collectVisibleIdentifiableElement(addHere, [T e|e.definedVariable])
	}

	/**
	 * Determines the defined for the given element, if there is no one, it is tried to infer it via
	 * {@link N4JSTypeSystem#tau(EObject)}. If a type could be inferred or was already there it
	 * will be collected.
	 */
	def private <T extends TypableElement, U extends IdentifiableElement> collectVisibleIdentifiableElement(T element,
		List<? super IdentifiableElement> addHere, (T)=>U calculateType) {

		if(calculateType!==null) {
			val type = calculateType.apply(element)
			if (type !== null) {
				addHere.add(type)
			}
		}
	}

	/**
	 * Walk through all contents list of the given element. If the entry in the list is a N4ClassDefinition,
	 * a N4EnumDeclaration, a ExportedVariableDeclaration or a FunctionDefinition its children are walked, too.
	 * If the entry in the list is a IdentifiableElement this is also collected. If the entry in the list is a
	 * before mentioned element or a Expression the iterator is pruned as it is clear that below these elements no
	 * further visible identifiable elements can be found.
	 */
	def private void doCollectVisibleIdentifiableElements(VariableEnvironmentElement start, EObject element,
		boolean includeBlockScopedElements, List<? super IdentifiableElement> addHere) {
		val allContents = element.eAllContents
		while (allContents.hasNext) {
			val next = allContents.next
			switch (next) {
				N4ClassDeclaration: {
					val polyfilledOrOriginalType = next.getTypeOrPolyfilledType();
					val nonNullClassDecl = if (polyfilledOrOriginalType instanceof TClass) {
						val n4ClassDecl = polyfilledOrOriginalType?.astElement as N4ClassDeclaration;
						if (n4ClassDecl === null)
							next
						else
							n4ClassDecl;
					} else
						next;

					nonNullClassDecl.collectVisibleTypedElement(addHere)
					allContents.prune
				}
				N4InterfaceDeclaration: {
					next.collectVisibleTypedElement(addHere)
					allContents.prune
				}
				N4EnumDeclaration: {
					next.collectVisibleTypedElement(addHere)
					allContents.prune
				}
				N4TypeAliasDeclaration: {
					next.collectVisibleTypedElement(addHere)
					allContents.prune
				}
				FunctionDeclaration: {
					next.collectVisibleTypedElement(addHere)
					allContents.prune
				}
				ExportedVariableDeclaration case next.name !== null: {
					// this special case is required (in addition to case for IdentifiableElement below), to make sure
					// the TModule element is added to 'addHere', not the AST node (as is done for non-exported or
					// non-top-level variables)
					next.collectVisibleVariable(addHere)
					allContents.prune
				}
				IdentifiableElement case !(next instanceof N4TypeVariable): {
					// exclude type variables here, they can only be referred to as types
					if(next.isBlockScoped) {
						// let, const
						if(includeBlockScopedElements) {
							addHere.add(next)
						}
					} else {
						// var
						if(next.belongsToScope(start)) {
							addHere.add(next)
						}
					}
					allContents.prune
				}
				Block: {
					// continue inside block, but without adding block-scoped elements
					doCollectVisibleIdentifiableElements(start, next, false, addHere)
					allContents.prune
				}
				VariableEnvironmentElement: { // ForStatement, WithStatement, CatchBlock (note: CatchBlock is not a Block!)
					// a new, nested variable environment starts at this point
					// -> continue with children, but without adding block-scoped elements
					doCollectVisibleIdentifiableElements(start, next, false, addHere)
					allContents.prune
				}
				Expression: {
					// optimization:
					// variable declarations are statements and expression don't ever contain statements
					allContents.prune
				}
				TypeRef: { // do not collect elements of type refs, such as fields in structural types. cf. GHOLD-130
					allContents.prune
				}
			}
		}
	}

	def private boolean belongsToScope(IdentifiableElement elem, VariableEnvironmentElement scope) {
		return elem.getScope === scope;
	}
}
