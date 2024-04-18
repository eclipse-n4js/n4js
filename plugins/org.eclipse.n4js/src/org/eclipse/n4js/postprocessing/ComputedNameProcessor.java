/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.postprocessing;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.PropertySpread;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.inject.Singleton;

/**
 * Processing of {@link LiteralOrComputedPropertyName}s that have a computed property name, mainly setting property
 * {@link LiteralOrComputedPropertyName#getComputedName() 'computedName'}.
 * <p>
 * For details, see
 * {@link ComputedNameProcessor#processComputedPropertyName( LiteralOrComputedPropertyName, ASTMetaInfoCache )}.
 */
@Singleton
public class ComputedNameProcessor {

	/**
	 * If the given 'nameDecl' has a computed property name, this method will
	 * <ol>
	 * <li>obtain its expression's compile-time value from the cache (the actual evaluation of the expression happened
	 * in {@link CompileTimeExpressionProcessor}),
	 * <li>store this name in 'nameDecl' (for later use), and
	 * <li>store this name in the corresponding TModule element (if such a TModule element exists).
	 * </ol>
	 * <p>
	 * In case the compile-time value of the expression is invalid (i.e. the expression is not a valid compile-time
	 * expression) no actual name will be stored in 'nameDecl' and the corresponding TModule element will be removed
	 * from the TModule, entirely (if such a TModule element exists).
	 */
	public void processComputedPropertyName(LiteralOrComputedPropertyName nameDecl, ASTMetaInfoCache cache) {
		if (nameDecl.hasComputedPropertyName()) {
			// obtain compile-time value of expression
			CompileTimeValue value = cache.getCompileTimeValue(nameDecl.getExpression());
			// derive a property name from the value
			String name = N4JSLanguageUtils.derivePropertyNameFromCompileTimeValue(value);
			if (name != null) {
				// cache the computed name in the LiteralOrComputedPropertyName AST node
				EcoreUtilN4.doWithDeliver(false, () -> {
					nameDecl.setComputedName(name);
					nameDecl.setComputedSymbol(value instanceof CompileTimeValue.ValueSymbol);
				}, nameDecl);
				// set the computed name in the types model element
				EObject owner = nameDecl.eContainer();
				EObject typeElem = N4JSASTUtils.getCorrespondingTypeModelElement(owner);
				if (typeElem instanceof IdentifiableElement) {
					EcoreUtilN4.doWithDeliver(false, () -> {
						((IdentifiableElement) typeElem).setName(name);
					}, typeElem);
				}
			} else {
				// invalid name expression (i.e. not a constant expression)
				// -> remove the types model element from the TModule
				// (note: we have to do this for consistency with how the types builder handles elements that are
				// unnamed (usually due to a broken AST): in those cases, the types builder does not create a TModule
				// element)
				EObject owner = nameDecl.eContainer();
				discardTypeModelElement(owner);
			}
		}
	}

	/**
	 * Discards the types model element corresponding to the given AST node. Throws exception if given AST node does not
	 * have a corresponding types model element.
	 */
	private void discardTypeModelElement(EObject astNode) {
		EObject elem = N4JSASTUtils.getCorrespondingTypeModelElement(astNode);

		EcoreUtilN4.doWithDeliver(false, () -> {
			if (astNode instanceof TypeDefiningElement) {
				((TypeDefiningElement) astNode).setDefinedType(null);
			} else if (astNode instanceof N4FieldDeclaration) {
				((N4FieldDeclaration) astNode).setDefinedField(null);
			} else if (astNode instanceof N4GetterDeclaration) {
				((N4GetterDeclaration) astNode).setDefinedGetter(null);
			} else if (astNode instanceof N4SetterDeclaration) {
				((N4SetterDeclaration) astNode).setDefinedSetter(null);
			} else if (astNode instanceof PropertyNameValuePair) {
				((PropertyNameValuePair) astNode).setDefinedField(null);
			} else if (astNode instanceof PropertyGetterDeclaration) {
				((PropertyGetterDeclaration) astNode).setDefinedGetter(null);
			} else if (astNode instanceof PropertySetterDeclaration) {
				((PropertySetterDeclaration) astNode).setDefinedSetter(null);
				// note: PropertyMethodDeclaration is a TypeDefiningElement (handled above)
			} else if (astNode instanceof PropertySpread) {
				// nothing to discard in this case
			} else {
				throw new UnsupportedOperationException("switch case missing for: " + astNode);
			}
		}, astNode);

		if (elem instanceof SyntaxRelatedTElement) {
			((SyntaxRelatedTElement) elem).setAstElement(null);
		}
		if (elem != null) {
			EcoreUtilN4.doWithDeliver(false, () -> {
				EcoreUtil.remove(elem);
			}, elem.eContainer());
		}
	}
}
