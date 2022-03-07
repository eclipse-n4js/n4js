/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts.astbuilders;

import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyAccessExpressionContext;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Adds helper methods to {@link AbstractDtsBuilder} intended for use across different concrete builders.
 * <p>
 * By convention, all methods in this class should be declared {@code protected final}.
 */
public abstract class AbstractDtsBuilderWithHelpers<T extends ParserRuleContext, R>
		extends AbstractDtsBuilder<T, R> {

	/** Constructor */
	public AbstractDtsBuilderWithHelpers(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	/**
	 * Creates a property access expression, intended for cases when no {@link PropertyAccessExpressionContext} is
	 * available (otherwise use {@link DtsExpressionBuilder}).
	 */
	protected final ParameterizedPropertyAccessExpression createParameterizedPropertyAccessExpression(Expression target,
			IdentifierNameContext propertyCtx) {
		if (propertyCtx == null) {
			return null;
		}
		String propertyAsText = propertyCtx.getText();
		if (propertyAsText == null || propertyAsText.isBlank()) {
			return null;
		}
		ParameterizedPropertyAccessExpression ppae = N4JSFactory.eINSTANCE
				.createParameterizedPropertyAccessExpression();
		ppae.setTarget(target);
		ppae.setPropertyAsText(propertyAsText);

		IdentifiableElement ieProxy = TypesFactory.eINSTANCE.createIdentifiableElement();
		EReference eRef = N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property();
		ParserContextUtil.installProxy(resource, ppae, eRef, ieProxy, propertyAsText);
		ppae.setProperty(ieProxy);

		return ppae;
	}

	/** @return the given type reference, if non-<code>null</code>; otherwise a new {@code any+} type reference. */
	protected final TypeReferenceNode<TypeRef> orAnyPlus(TypeReferenceNode<TypeRef> typeRefNode) {
		if (typeRefNode != null) {
			return typeRefNode;
		}
		return ParserContextUtil.wrapInTypeRefNode(createAnyPlusTypeRef());
	}

	/** @return the given type reference, if non-<code>null</code>; otherwise a new {@code any+} type reference. */
	protected final TypeRef orAnyPlus(TypeRef typeRef) {
		if (typeRef != null) {
			return typeRef;
		}
		return createAnyPlusTypeRef();
	}

	/** @return a new {@code any+} type reference. */
	protected final ParameterizedTypeRef createAnyPlusTypeRef() {
		return createParameterizedTypeRef("any", true);
	}

	/** @return a new {@link ParameterizedTypeRef} pointing to the given declared type. */
	protected final ParameterizedTypeRef createParameterizedTypeRef(String declTypeName, boolean dynamic) {
		ParameterizedTypeRef ptr = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
		ptr.setDynamic(dynamic);
		ptr.setDeclaredTypeAsText(declTypeName);

		Type typeProxy = TypesFactory.eINSTANCE.createType();
		EReference eRef = TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType();
		ParserContextUtil.installProxy(resource, ptr, eRef, typeProxy, ptr.getDeclaredTypeAsText());
		ptr.setDeclaredType(typeProxy);

		return ptr;
	}
}
