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
package org.eclipse.n4js.transpiler.es.assistants

import com.google.gson.GsonBuilder
import com.google.inject.Inject
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.transpiler.InformationRegistry
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.utils.ResourceNameComputer

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Helper methods for creating output code related to reflection for classes, interfaces, and enums in N4JS.
 */
class ReflectionAssistant extends TransformationAssistant {
	@Inject private ResourceNameComputer resourceNameComputer;

	/**
	 * Convenience method. Same as {@link #createN4TypeGetter(N4TypeDeclaration)}, but if creation of the
	 * 'n4type' reflection getter is successful, the getter will be added to the given classifier; otherwise,
	 * this method does nothing.
	 */
	def public void addN4TypeGetter(N4TypeDeclaration typeDecl, N4ClassifierDeclaration addHere) {
		val getter = createN4TypeGetter(typeDecl);
		if (getter !== null) {
			addHere.ownedMembersRaw += getter;
		}
	}

	/**
	 * Returns the 'n4type' getter for obtaining reflection information of the given class, interface, or enum declaration
	 * or <code>null</code> if it cannot be created.
	 * <p>
	 * NOTE: Reflection is only supported for declarations that were given in the original source code, i.e. when method
	 * {@link InformationRegistry#getOriginalDefinedType(N4TypeDeclaration) getOriginalDefinedType()}
	 * returns a non-null value. Otherwise, this method will return <code>null</code> as well.
	 * 
	 * @return the 'n4type' getter for the given declaration or <code>null</code> iff the declaration does not have
	 *         an original defined type.
	 */
	def public N4GetterDeclaration createN4TypeGetter(N4TypeDeclaration typeDecl) {
		val originalType = state.info.getOriginalDefinedType(typeDecl);
		if (originalType === null) {
			return null;
		}

		val typeSTE = findSymbolTableEntryForElement(typeDecl, true);
		val reflectionBuilder = new ReflectionBuilder(state, resourceNameComputer);
		val reflectInfo = reflectionBuilder.createReflectionInfo(typeDecl, typeSTE);
		val gson = new GsonBuilder().disableHtmlEscaping().create();
		val origJsonString = gson.toJson(reflectInfo);
		val quotedJsonString = "'" + origJsonString.replaceAll("\'", "\\\\\'") + "'";
		
		val methodName = switch (typeDecl) {
			N4ClassDeclaration: steFor_$getReflectionForClass()
			N4InterfaceDeclaration: steFor_$getReflectionForInterface()
			N4EnumDeclaration: steFor_$getReflectionForEnum()
		}

		return _N4GetterDecl(
			_LiteralOrComputedPropertyName(N4JSLanguageConstants.N4TYPE_NAME),
			_Block(
				_ReturnStmnt(
					_CallExpr(
						_IdentRef(methodName),
						_StringLiteral(quotedJsonString, quotedJsonString)
					)
				)
			)
		) => [
			declaredModifiers += N4Modifier.STATIC
		];
	}

}
