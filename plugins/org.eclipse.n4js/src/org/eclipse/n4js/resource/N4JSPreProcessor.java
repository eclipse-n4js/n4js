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
package org.eclipse.n4js.resource;

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toIterable;

import java.math.BigDecimal;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.BooleanLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StringLiteralTypeRef;
import org.eclipse.n4js.validation.ASTStructureValidator;

import com.google.inject.Singleton;

/**
 * This class performs some early pre-processing of the AST. This happens after {@link N4JSLinker lazy linking} and
 * before {@link ASTStructureValidator AST structure validation}.
 */
@Singleton
@SuppressWarnings("unused")
final class N4JSPreProcessor {

	/**
	 * Performs an early processing of the AST, e.g. initialization of transient helper values. <b>This method assumes
	 * that it is allowed to change the AST!</b> Thus, it should be invoked from an "exec without cache clear" handler,
	 * see {@code OnChangeEvictingCache#execWithoutCacheClear(N4JSResource,IUnitOfWork)}.
	 */
	public void process(Script script, N4JSResource resource) {
		ResourceSet resourceSet = resource.getResourceSet();
		if (resourceSet == null) {
			// null-safe exit - required in smoke test where Resources detached from a ResourceSet are used.
			return;
		}
		BuiltInTypeScope builtInTypes = BuiltInTypeScope.get(resourceSet);
		for (EObject node : toIterable(resource.getScript().eAllContents())) {
			processNode(node, resource, builtInTypes);
		}
	}

	private void processNode(EObject astNode, N4JSResource resource, BuiltInTypeScope builtInTypes) {
		if (astNode instanceof ParameterizedTypeRef) {
			processNode((ParameterizedTypeRef) astNode, resource, builtInTypes);
		} else if (astNode instanceof BooleanLiteralTypeRef) {
			processNode((BooleanLiteralTypeRef) astNode, resource, builtInTypes);
		} else if (astNode instanceof NumericLiteralTypeRef) {
			processNode((NumericLiteralTypeRef) astNode, resource, builtInTypes);
		} else if (astNode instanceof StringLiteralTypeRef) {
			processNode((StringLiteralTypeRef) astNode, resource, builtInTypes);
		} else if (astNode instanceof EnumLiteralTypeRef) {
			processNode((EnumLiteralTypeRef) astNode, resource, builtInTypes);
		} else {
			// by default, do nothing
		}
	}

	/**
	 * Support for array type syntax:
	 *
	 * <pre>
	 * let arr: string[];
	 * </pre>
	 *
	 * and arrrayN syntax:
	 *
	 * <pre>
	 * let tup: [string, int];
	 * </pre>
	 *
	 * Note that both {@code string[]} and {@code [string]} results in an {@code Array<string>}
	 */
	private void processNode(ParameterizedTypeRef typeRef, N4JSResource resource, BuiltInTypeScope builtInTypes) {
		if (typeRef.isArrayTypeExpression()) {
			typeRef.setDeclaredType(builtInTypes.getArrayType());
		} else if (typeRef.isArrayNTypeExpression()) {
			int n = typeRef.getDeclaredTypeArgs().size();
			if (n < 2) {
				typeRef.setDeclaredType(builtInTypes.getArrayType());
			} else if (n <= BuiltInTypeScope.ITERABLE_N__MAX_LEN) {
				typeRef.setDeclaredType(builtInTypes.getArrayNType(n));
			} else {
				// error (a validation will create an issue)
				// NOTE: it would be nice to create an InterableN with a union as last type argument
				// containing those element types that exceed the ITERABLE_N__MAX_LEN; however, this
				// would require AST rewriting, which isn't allowed.
				typeRef.setDeclaredType(builtInTypes.getArrayNType(BuiltInTypeScope.ITERABLE_N__MAX_LEN));
			}
		}
	}

	private void processNode(BooleanLiteralTypeRef typeRef, N4JSResource resource, BuiltInTypeScope builtInTypes) {
		typeRef.setValue(Objects.equals(typeRef.getAstValue(), "true"));
	}

	private void processNode(NumericLiteralTypeRef typeRef, N4JSResource resource, BuiltInTypeScope builtInTypes) {
		BigDecimal valueRaw = (BigDecimal) typeRef.getAstValue(); // validity of this cast is enforced by the grammar
		if (valueRaw != null) {
			valueRaw = valueRaw.stripTrailingZeros();
			if (typeRef.isAstNegated()) {
				valueRaw = valueRaw.negate();
			}
			typeRef.setValue(valueRaw);
		} else {
			// syntax error
			typeRef.setValue(BigDecimal.ZERO);
		}
	}

	private void processNode(StringLiteralTypeRef typeRef, N4JSResource resource, BuiltInTypeScope builtInTypes) {
		typeRef.setValue((String) typeRef.getAstValue()); // validity of this cast is enforced by the grammar
	}

	private void processNode(EnumLiteralTypeRef typeRef, N4JSResource resource, BuiltInTypeScope builtInTypes) {
		// setting the value of an EnumLiteralTypeRef requires scoping and can therefore not be done here;
		// see N4JSScopeProvider#getScopeByShortcut() and TypeRefProcessor#processEnumLiteralTypeRefs()
	}
}
