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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import javax.swing.text.html.HTML.Tag
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassExpression
import org.eclipse.n4js.n4JS.N4FieldAccessor
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.BootstrapCallAssistant
import org.eclipse.n4js.transpiler.es.assistants.ClassConstructorAssistant
import org.eclipse.n4js.transpiler.im.DelegatingMember
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.ts.types.TField

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.transpiler.utils.TranspilerUtils.*
import java.util.List

/**
 * Transforms {@link N4ClassDeclaration}s into a constructor function and a <code>$makeClass</code> call.
 * <p>
 * Dependencies:
 * <ul>
 * <li>requiresBefore {@link MemberPatchingTransformation}:
 *     additional members must be in place before they are transformed within this transformation.
 * <li>requiresBefore {@link SuperLiteralTransformation}:
 *     requires the tag {@link Tag#explicitSuperCall}.
 * </ul>
 */
@RequiresBefore(MemberPatchingTransformation,SuperLiteralTransformation)
class ClassDeclarationTransformation extends Transformation {

	@Inject private ClassConstructorAssistant classConstructorAssistant;
	@Inject private BootstrapCallAssistant bootstrapCallAssistant;
	@Inject private TypeAssistant typeAssistant;


	override assertPreConditions() {
		typeAssistant.assertClassifierPreConditions();
		assertFalse("class expressions are not supported yet",
			state.im.eAllContents.exists[it instanceof N4ClassExpression]);
		assertFalse("only top-level classes are supported, for now",
			collectNodes(state.im, N4ClassDeclaration, false).exists[!typeAssistant.isTopLevel(it)]);
	}

	override assertPostConditions() {
		 assertFalse("there should not be any N4ClassDeclarations in the intermediate model",
			 state.im.eAllContents.exists[it instanceof N4ClassDeclaration]);
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, N4ClassDeclaration, false).forEach[transformClassDecl];
	}

	def private void transformClassDecl(N4ClassDeclaration classDecl) {
		val classSTE = classDecl.findSymbolTableEntryForElement(false);
		val superClassSTE = typeAssistant.getSuperClassSTE(classDecl);

		val ctorDecl = classConstructorAssistant.createCtorDecl(classDecl, superClassSTE);
		val makeClassCall = bootstrapCallAssistant.createMakeClassCall(classDecl, superClassSTE);
		val staticInits = createStaticFieldInitializations(classSTE, classDecl);

		state.tracer.copyTrace(classDecl, ctorDecl);
		state.tracer.copyTrace(classDecl, makeClassCall);
		// note: tracing for staticInits was already set in #createStaticInitialisers() and related methods!

		replace(classDecl, ctorDecl);
		insertAfter(ctorDecl.orContainingExportDeclaration, makeClassCall);

		insertAfter( makeClassCall, staticInits );

		state.info.markAsToHoist(ctorDecl);
	}

	// ################################################################################################################
	// STATIC INITIALIZERS

	/**
	 * Creates a new list of statements to initialize the static fields of the given {@code classDecl}.
	 * 
	 * Clients of this method may modify the returned list.
	 */
	def protected List<Statement> createStaticFieldInitializations(SymbolTableEntry steClass, N4ClassDeclaration classDecl) {
		// apply only to static members
		return classDecl.ownedMembers.filter[isStatic].map[createStaticInitialiserCode(steClass)].filterNull.toList;
	}

	def private dispatch Statement createStaticInitialiserCode(N4FieldDeclaration fieldDecl, SymbolTableEntry steClass) {

		if( ! fieldDecl.isStatic || fieldDecl.expression === null) return null;

		val tField = state.info.getOriginalDefinedMember(fieldDecl) as TField;

		val exprStmnt = _ExprStmnt( _AssignmentExpr( _PropertyAccessExpr=>[
				it.target = __NSSafe_IdentRef(steClass);
				it.property_IM = getSymbolTableEntryOriginal(
					tField,
					true
				);
			],  fieldDecl.expression // REUSE existing expression
		 ));
		state.tracer.copyTrace(fieldDecl, exprStmnt);

		return exprStmnt;

	}
	def private dispatch Statement createStaticInitialiserCode(N4FieldAccessor fieldDecl, SymbolTableEntry steClass) {
		// n.t.d.
	}
	def private dispatch Statement createStaticInitialiserCode(N4MethodDeclaration methodDecl, SymbolTableEntry steClass) {
		// n.t.d.
	}
	def private dispatch Statement createStaticInitialiserCode(DelegatingMember accDecl, SymbolTableEntry steClass) {
		// n.t.d.
	}
}
