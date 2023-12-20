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
package org.eclipse.n4js.transpiler.es.assistants;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Block;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._LiteralOrComputedPropertyName;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4GetterDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ReturnStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ThisLiteral;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.transpiler.InformationRegistry;
import org.eclipse.n4js.transpiler.TransformationAssistant;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.ResourceNameComputer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.inject.Inject;

/**
 * Helper methods for creating output code related to reflection for classes, interfaces, and enums in N4JS.
 */
public class ReflectionAssistant extends TransformationAssistant {
	@Inject
	private ResourceNameComputer resourceNameComputer;

	/**
	 * Convenience method. Same as {@link #createN4TypeGetter(N4TypeDeclaration)}, but if creation of the 'n4type'
	 * reflection getter is successful, the getter will be added to the given classifier; otherwise, this method does
	 * nothing.
	 */
	public void addN4TypeGetter(N4TypeDeclaration typeDecl, N4ClassifierDeclaration addHere) {
		N4GetterDeclaration getter = createN4TypeGetter(typeDecl);
		if (getter != null) {
			addHere.getOwnedMembersRaw().add(getter);
		}
	}

	/**
	 * Returns the 'n4type' getter for obtaining reflection information of the given class, interface, or enum
	 * declaration or <code>null</code> if it cannot be created.
	 * <p>
	 * NOTE: Reflection is only supported for declarations that were given in the original source code, i.e. when method
	 * {@link InformationRegistry#getOriginalDefinedType(N4ClassifierDeclaration)} returns a non-null value. Otherwise,
	 * this method will return <code>null</code> as well.
	 *
	 * @return the 'n4type' getter for the given declaration or <code>null</code> iff the declaration does not have an
	 *         original defined type.
	 */
	public N4GetterDeclaration createN4TypeGetter(N4TypeDeclaration typeDecl) {
		Type originalType = getState().info.getOriginalDefinedType(typeDecl);
		if (originalType == null) {
			return null;
		}

		SymbolTableEntry typeSTE = findSymbolTableEntryForElement(typeDecl, true);
		ReflectionBuilder reflectionBuilder = new ReflectionBuilder(this, getState(), resourceNameComputer);
		JsonElement reflectInfo = reflectionBuilder.createReflectionInfo(typeDecl, typeSTE);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String origJsonString = gson.toJson(reflectInfo);
		String quotedJsonString = "'" + origJsonString.replaceAll("\'", "\\\\\'") + "'";

		SymbolTableEntryInternal methodName = null;
		if (typeDecl instanceof N4ClassDeclaration) {
			methodName = steFor_$getReflectionForClass();
		}
		if (typeDecl instanceof N4InterfaceDeclaration) {
			methodName = steFor_$getReflectionForInterface();
		}
		if (typeDecl instanceof N4EnumDeclaration) {
			methodName = steFor_$getReflectionForEnum();
		}

		N4GetterDeclaration getterDecl = _N4GetterDecl(
				_LiteralOrComputedPropertyName(N4JSLanguageConstants.N4TYPE_NAME),
				_Block(
						_ReturnStmnt(
								_CallExpr(
										_IdentRef(methodName),
										_ThisLiteral(),
										_StringLiteral(quotedJsonString, quotedJsonString)))));

		getterDecl.getDeclaredModifiers().add(N4Modifier.STATIC);

		return getterDecl;
	}

}
