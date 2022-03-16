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

import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.GetAccessorContext;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyAccessExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyMemberBaseContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyMemberContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.SetAccessorContext;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
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

	/** Builds a {@link N4GetterDeclaration} from a {@link GetAccessorContext} */
	protected final N4GetterDeclaration createGetAccessor(GetAccessorContext ctx) {

		if (ctx.getter() == null || ctx.getter().propertyName() == null) {
			return null;
		}

		N4GetterDeclaration getter = N4JSFactory.eINSTANCE.createN4GetterDeclaration();
		getter.setDeclaredName(newPropertyNameBuilder().consume(ctx.getter().propertyName()));

		TypeRef typeRef = newTypeRefBuilder().consume(ctx.colonSepTypeRef());
		getter.setDeclaredTypeRefNode(ParserContextUtil.wrapInTypeRefNode(typeRef));

		if (ctx.parent instanceof PropertyMemberContext) { // true for classes
			PropertyMemberContext pmctx = (PropertyMemberContext) ctx.parent;
			if (pmctx.propertyMemberBase() != null) {
				PropertyMemberBaseContext pmb = pmctx.propertyMemberBase();
				if (pmb.Static() != null) {
					getter.getDeclaredModifiers().add(N4Modifier.STATIC);
				}
			}
		}

		getter.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		return getter;
	}

	/** Builds a {@link N4SetterDeclaration} from a {@link SetAccessorContext} */
	protected final N4SetterDeclaration createSetAccessor(SetAccessorContext ctx) {

		if (ctx.setter() == null || ctx.setter().propertyName() == null) {
			return null;
		}

		N4SetterDeclaration setter = N4JSFactory.eINSTANCE.createN4SetterDeclaration();
		setter.setDeclaredName(newPropertyNameBuilder().consume(ctx.setter().propertyName()));

		FormalParameter fpar = N4JSFactory.eINSTANCE.createFormalParameter();
		setter.setFpar(fpar);
		TypeRef typeRef = newTypeRefBuilder().consume(ctx.colonSepTypeRef());
		fpar.setDeclaredTypeRefNode(ParserContextUtil.wrapInTypeRefNode(typeRef));
		if (ctx.Identifier() != null) {
			fpar.setName(ctx.Identifier().getText());
		} else if (ctx.bindingPattern() != null) {
			fpar.setBindingPattern(newBindingPatternBuilder().consume(ctx.bindingPattern()));
		}

		if (ctx.parent instanceof PropertyMemberContext) { // true for classes
			PropertyMemberContext pmctx = (PropertyMemberContext) ctx.parent;
			if (pmctx.propertyMemberBase() != null) {
				PropertyMemberBaseContext pmb = pmctx.propertyMemberBase();
				if (pmb.Static() != null) {
					setter.getDeclaredModifiers().add(N4Modifier.STATIC);
				}
			}
		}

		setter.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		return setter;
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

	/**
	 * In places where N4JS does not support all forms of property names (e.g. number/string literals, computed names),
	 * this method should be used to obtain the simple name.
	 * <p>
	 * TODO implement all forms of property names everywhere
	 */
	protected final String getSimpleNameFromPropertyName(PropertyNameContext propNameCtx) {
		if (propNameCtx != null) {
			LiteralOrComputedPropertyName locpn = newPropertyNameBuilder().consume(propNameCtx);
			if (locpn != null) {
				return locpn.getName();
			}
		}
		return null;
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

	/** @return a new {@code boolean} type reference. */
	protected final ParameterizedTypeRef createBooleanTypeRef() {
		return createParameterizedTypeRef("boolean", false);
	}

	/** @return a new {@code any+} type reference. */
	protected final ParameterizedTypeRef createAnyPlusTypeRef() {
		return createParameterizedTypeRef("any", true);
	}

	/** @return a new {@link ParameterizedTypeRef} pointing to the given declared type. */
	protected final ParameterizedTypeRef createParameterizedTypeRef(String declTypeName, boolean dynamic) {
		return createParameterizedTypeRef(declTypeName, null, dynamic);
	}

	/** @return a new {@link ParameterizedTypeRef} pointing to the given declared type and type arguments. */
	protected final ParameterizedTypeRef createParameterizedTypeRef(String declTypeName,
			Collection<? extends TypeArgument> typeArgs, boolean dynamic) {

		ParameterizedTypeRef ptr = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
		ptr.setDynamic(dynamic);
		ptr.setDeclaredTypeAsText(declTypeName);

		Type typeProxy = TypesFactory.eINSTANCE.createType();
		EReference eRef = TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType();
		ParserContextUtil.installProxy(resource, ptr, eRef, typeProxy, ptr.getDeclaredTypeAsText());
		ptr.setDeclaredType(typeProxy);

		if (typeArgs != null) {
			ptr.getDeclaredTypeArgs().addAll(typeArgs);
		}

		return ptr;
	}
}
