/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.n4idl.assistants

import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TN4Classifier

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*
import org.eclipse.n4js.ts.types.TypingStrategy

/**
 * Transformation assistant for transforming N4IDL class and interface declarations.
 */
class N4IDLClassifierTransformationAssistant extends TransformationAssistant {
	
	/**
	 * Creates the static initializer statement for the given {@code classifierSTE} which
	 * initializes the {@link N4IDLGlobals#N4_SUPER_INTERFACE_STATIC_FIELD} field based on 
	 * the given {@param classifierDeclaration}.
	 * 
	 * This allows runtime access to the directly implemented interfaces of an N4IDL class or interface.
	 */
	public def Statement createImplementedInterfaceStaticInitializer(SymbolTableEntry classifierSTE, N4ClassifierDeclaration classifierDeclaration) {
		// [<superInterfaces>]
		val Expression superInterfaceFieldValue = _ArrLit(classifierDeclaration.getNonStructuralSuperClassifiers
			.map[ref | (ref as ParameterizedTypeRef_IM).rewiredTarget]
			.map[t | _ArrayElement(_IdentRef(t))])
		
		// A.$n4SuperInterfaces
		val staticFieldAccess =_PropertyAccessExpr(_IdentRef(classifierSTE), getSymbolTableEntryInternal(N4IDLGlobals.N4_SUPER_INTERFACE_STATIC_FIELD, true))
		
		// A.$n4SuperInterface = [<superInterfaces>]
		return _ExprStmnt(_AssignmentExpr(staticFieldAccess, superInterfaceFieldValue));
	}
	
	/**
	 * Returns an iterable of super classifiers (only TN4Classifiers) that are not declared to
	 * be def-site structural (.typeingStrategy == NOMINAL). 
	 */
	private def Iterable<ParameterizedTypeRef> getNonStructuralSuperClassifiers(N4ClassifierDeclaration declaration) {
		declaration.implementedOrExtendedInterfaceRefs
			.filter[ref | ref.declaredType instanceof TN4Classifier]
			.filter[ref | (ref.declaredType as TN4Classifier).typingStrategy == TypingStrategy.NOMINAL];
	} 
}