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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.ArrayTypeExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.ArrowFunctionTypeExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.CallSignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.ColonSepTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ConditionalTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ConstructSignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.GetAccessorContext;
import org.eclipse.n4js.dts.TypeScriptParser.GetterContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceBodyContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceMemberContext;
import org.eclipse.n4js.dts.TypeScriptParser.IntersectionTypeExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.MethodSignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.ObjectLiteralTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.OperatorTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterBlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.PrimaryTypeExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertySignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.SetAccessorContext;
import org.eclipse.n4js.dts.TypeScriptParser.SetterContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeArgumentContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeArgumentsContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeParametersContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefWithModifiersContext;
import org.eclipse.n4js.dts.TypeScriptParser.UnionTypeExpressionContext;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsFormalParametersBuilder.DtsTFormalParametersBuilder;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsTypeVariablesBuilder.DtsTypeVariablesBuilder;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

import com.google.common.base.Strings;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsTypeRefBuilder extends AbstractDtsBuilderWithHelpers<TypeRefContext, TypeRef> {

	/** Constructor */
	public DtsTypeRefBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return Collections.emptySet();
	}

	/** @return a {@link TypeReferenceNode} from the given context. Consumes the given context and all its children. */
	public TypeRef consume(ColonSepTypeRefContext ctx) {
		if (ctx == null) {
			return null;
		}
		return consume(ctx.typeRef());
	}

	public TypeRef consume(UnionTypeExpressionContext ctx) {
		return doConsume(ctx);
	}

	public TypeRef consume(IntersectionTypeExpressionContext ctx) {
		return doConsume(ctx);
	}

	public TypeRef consume(OperatorTypeRefContext ctx) {
		return doConsume(ctx);
	}

	/** @return a wrapped {@link ParameterizedTypeRef}, created from the given context. */
	public ParameterizedTypeRef consume(ParameterizedTypeRefContext ctx) {
		return (ParameterizedTypeRef) doConsume(ctx);
	}

	@Override
	public void enterTypeRef(TypeRefContext ctx) {
		if (ctx.conditionalTypeRef() != null) {
			enterConditionalTypeRef(ctx.conditionalTypeRef());
		}
	}

	@Override
	public void enterConditionalTypeRef(ConditionalTypeRefContext ctx) {
		if (ctx.unionTypeExpression().size() == 1) {
			enterUnionTypeExpression(ctx.unionTypeExpression(0));
		}
		// FIXME
	}

	@Override
	public void enterUnionTypeExpression(UnionTypeExpressionContext ctx) {
		int n = ctx.intersectionTypeExpression().size();
		if (n == 1) {
			enterIntersectionTypeExpression(ctx.intersectionTypeExpression(0));
			return;
		} else if (n >= 2) {
			UnionTypeExpression ute = TypeRefsFactory.eINSTANCE.createUnionTypeExpression();
			for (IntersectionTypeExpressionContext childCtx : ctx.intersectionTypeExpression()) {
				TypeRef childTypeRef = newTypeRefBuilder().consume(childCtx);
				if (childTypeRef != null) {
					ute.getTypeRefs().add(childTypeRef);
				}
			}
			result = ute;
		}
	}

	@Override
	public void enterIntersectionTypeExpression(IntersectionTypeExpressionContext ctx) {
		int n = ctx.operatorTypeRef().size();
		if (n == 1) {
			enterOperatorTypeRef(ctx.operatorTypeRef(0));
			return;
		} else if (n >= 2) {
			IntersectionTypeExpression ite = TypeRefsFactory.eINSTANCE.createIntersectionTypeExpression();
			for (OperatorTypeRefContext childCtx : ctx.operatorTypeRef()) {
				TypeRef childTypeRef = newTypeRefBuilder().consume(childCtx);
				if (childTypeRef != null) {
					ite.getTypeRefs().add(childTypeRef);
				}
			}
			result = ite;
		}
	}

	@Override
	public void enterOperatorTypeRef(OperatorTypeRefContext ctx) {
		enterArrayTypeExpression(ctx.arrayTypeExpression());
		// FIXME
	}

	@Override
	public void enterArrayTypeExpression(ArrayTypeExpressionContext ctx) {
		enterPrimaryTypeExpression(ctx.primaryTypeExpression());
		// FIXME
	}

	@Override
	public void enterPrimaryTypeExpression(PrimaryTypeExpressionContext ctx) {
		if (ctx.literalType() != null) {
			// FIXME
		} else if (ctx.arrowFunctionTypeExpression() != null) {
			enterArrowFunctionTypeExpression(ctx.arrowFunctionTypeExpression());
		} else if (ctx.tupleTypeExpression() != null) {
			// FIXME
		} else if (ctx.queryTypeRef() != null) {
			// FIXME
		} else if (ctx.importTypeRef() != null) {
			// FIXME
		} else if (ctx.inferTypeRef() != null) {
			// FIXME
		} else if (ctx.typeRefWithModifiers() != null) {
			enterTypeRefWithModifiers(ctx.typeRefWithModifiers());
		} else if (ctx.OpenParen() != null && ctx.typeRef() != null) {
			// parentheses
			enterTypeRef(ctx.typeRef());
		}
	}

	@Override
	public void enterArrowFunctionTypeExpression(ArrowFunctionTypeExpressionContext ctx) {
		if (ctx.parameterBlock() == null) {
			return;
		}
		FunctionTypeExpression fte = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression();
		fte.getTypeVars().addAll(newTypeVariablesBuilder().consume(ctx.typeParameters()));
		fte.getFpars().addAll(newTFormalParametersBuilder().consume(ctx.parameterBlock()));
		if (ctx.unionTypeExpression() != null) {
			fte.setReturnTypeRef(orAnyPlus(newTypeRefBuilder().consume(ctx.unionTypeExpression())));
		} else if (ctx.typePredicateWithOperatorTypeRef() != null) {
			fte.setReturnTypeRef(createBooleanTypeRef());
		}
		result = fte;
	}

	@Override
	public void enterTypeRefWithModifiers(TypeRefWithModifiersContext ctx) {
		if (ctx.parameterizedTypeRef() != null) {
			enterParameterizedTypeRef(ctx.parameterizedTypeRef());
		} else if (ctx.objectLiteralTypeRef() != null) {
			enterObjectLiteralTypeRef(ctx.objectLiteralTypeRef());
		} else if (ctx.thisTypeRef() != null) {
			// FIXME
		}
	}

	@Override
	public void enterParameterizedTypeRef(ParameterizedTypeRefContext ctx) {
		String declTypeName = ctx.typeName() != null ? ctx.typeName().getText() : null;
		ParameterizedTypeRef ptr = createParameterizedTypeRef(declTypeName, ctx.typeArguments(), false);
		addLocationInfo(ptr, ctx);
		result = ptr;
	}

	@Override
	public void enterObjectLiteralTypeRef(ObjectLiteralTypeRefContext ctx) {
		ParameterizedTypeRefStructural ptr = (ParameterizedTypeRefStructural) createParameterizedTypeRef("Object",
				(TypeArgumentsContext) null, true);
		ptr.getAstStructuralMembers().addAll(createStructuralMembers(ctx.interfaceBody()));
		addLocationInfo(ptr, ctx);
		result = ptr;
	}

	private ParameterizedTypeRef createParameterizedTypeRef(String declTypeName, TypeArgumentsContext typeArgsCtx,
			boolean structural) {
		return createParameterizedTypeRef(declTypeName, ParserContextUtil.getTypeArgsFromTypeArgCtx(typeArgsCtx),
				structural);
	}

	/**
	 * @param declTypeName
	 *            name of the declared type; may contain '.' when accessing types in namespaces (of namespace imports or
	 *            namespace declarations).
	 */
	private ParameterizedTypeRef createParameterizedTypeRef(String declTypeName, Iterable<TypeArgumentContext> typeArgs,
			boolean structural) {

		if (declTypeName == null) {
			return null;
		}

		ParameterizedTypeRef ptr = structural
				? TypeRefsFactory.eINSTANCE.createParameterizedTypeRefStructural()
				: TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();

		String[] segs = declTypeName.split(Pattern.quote(ParserContextUtil.NAMESPACE_ACCESS_DELIMITER));
		for (int i = 0; i < segs.length - 1; i++) {
			String currSeg = segs[i];
			NamespaceLikeRef nslRef = TypeRefsFactory.eINSTANCE.createNamespaceLikeRef();
			nslRef.setDeclaredTypeAsText(currSeg);

			Type nsProxy = TypesFactory.eINSTANCE.createType();
			EReference eRef = TypeRefsPackage.eINSTANCE.getNamespaceLikeRef_DeclaredType();
			ParserContextUtil.installProxy(resource, nslRef, eRef, nsProxy, currSeg);
			nslRef.setDeclaredType(nsProxy);

			ptr.getAstNamespaceLikeRefs().add(nslRef);
		}
		String lastSeg = segs[segs.length - 1];
		ptr.setDeclaredTypeAsText(lastSeg);

		Type typeProxy = TypesFactory.eINSTANCE.createType();
		EReference eRef = TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType();
		ParserContextUtil.installProxy(resource, ptr, eRef, typeProxy, lastSeg);
		ptr.setDeclaredType(typeProxy);

		if (typeArgs != null) {
			for (TypeArgumentContext typeArgCtx : typeArgs) {
				TypeRef typeArg = newTypeRefBuilder().consume(typeArgCtx.typeRef());
				if (typeArg != null) {
					ptr.getDeclaredTypeArgs().add(typeArg);
				}
			}
		}

		return ptr;
	}

	private List<TStructMember> createStructuralMembers(InterfaceBodyContext ifcBodyCtx) {
		List<InterfaceMemberContext> members = ifcBodyCtx != null && ifcBodyCtx.interfaceMemberList() != null
				? ifcBodyCtx.interfaceMemberList().interfaceMember()
				: null;
		return createStructuralMembers(members);

	}

	private List<TStructMember> createStructuralMembers(Iterable<InterfaceMemberContext> members) {
		@SuppressWarnings("hiding")
		List<TStructMember> result = new ArrayList<>();
		if (members != null) {
			for (InterfaceMemberContext memberCtx : members) {
				TStructMember member = createStructuralMember(memberCtx);
				if (member != null) {
					addLocationInfo(member, memberCtx);
					result.add(member);
				}
			}
		}
		return result;
	}

	private TStructMember createStructuralMember(InterfaceMemberContext ctx) {
		if (ctx.callSignature() != null) {
			return createCallSignature(ctx.callSignature());
		} else if (ctx.constructSignature() != null) {
			return createConstructSignature(ctx.constructSignature());
		} else if (ctx.propertySignature() != null) {
			return createTStructField(ctx.propertySignature());
		} else if (ctx.getAccessor() != null) {
			return createTStructGetter(ctx.getAccessor());
		} else if (ctx.setAccessor() != null) {
			return createTStructSetter(ctx.setAccessor());
		} else if (ctx.methodSignature() != null) {
			return createTStructMethod(ctx.methodSignature());
		}
		return null;
	}

	private TStructMethod createCallSignature(CallSignatureContext ctx) {
		return createTStructMethod(null, ctx);
	}

	private TStructMethod createConstructSignature(ConstructSignatureContext ctx) {
		TStructMethod m = createTStructMethod(null, ctx.typeParameters(), ctx.parameterBlock(),
				ctx.colonSepTypeRef().typeRef());
		m.setName("new");
		return m;
	}

	private TStructField createTStructField(PropertySignatureContext ctx) {
		TStructField f = TypesFactory.eINSTANCE.createTStructField();
		setBasePropertiesOfTStructMember(f, ctx.propertyName());
		f.setTypeRef(orAnyPlus(newTypeRefBuilder().consume(ctx.colonSepTypeRef())));
		f.setOptional(ctx.QuestionMark() != null);
		return f;
	}

	private TStructGetter createTStructGetter(GetAccessorContext ctx) {
		GetterContext getterCtx = ctx.getter();
		if (getterCtx == null) {
			return null;
		}
		TStructGetter g = TypesFactory.eINSTANCE.createTStructGetter();
		setBasePropertiesOfTStructMember(g, getterCtx.propertyName());
		g.setTypeRef(orAnyPlus(newTypeRefBuilder().consume(ctx.colonSepTypeRef())));
		return g;
	}

	private TStructSetter createTStructSetter(SetAccessorContext ctx) {
		SetterContext setterCtx = ctx.setter();
		if (setterCtx == null) {
			return null;
		}
		TStructSetter g = TypesFactory.eINSTANCE.createTStructSetter();
		setBasePropertiesOfTStructMember(g, setterCtx.propertyName());
		TFormalParameter p = TypesFactory.eINSTANCE.createTFormalParameter();
		String fparName = ctx.Identifier() != null ? ctx.Identifier().getText() : null;
		p.setName(!Strings.isNullOrEmpty(fparName) ? fparName : "value");
		p.setTypeRef(orAnyPlus(newTypeRefBuilder().consume(ctx.colonSepTypeRef())));
		g.setFpar(p);
		return g;
	}

	private TStructMethod createTStructMethod(MethodSignatureContext ctx) {
		return createTStructMethod(ctx.propertyName(), ctx.callSignature());
	}

	private TStructMethod createTStructMethod(PropertyNameContext name, CallSignatureContext callSignature) {
		if (callSignature == null) {
			return null;
		}
		return createTStructMethod(name, callSignature.typeParameters(), callSignature.parameterBlock(),
				callSignature.typeRef());
	}

	private TStructMethod createTStructMethod(PropertyNameContext name, TypeParametersContext typeParams,
			ParameterBlockContext fpars, TypeRefContext returnTypeRefCtx) {

		TStructMethod m = TypesFactory.eINSTANCE.createTStructMethod();
		setBasePropertiesOfTStructMember(m, name);
		m.getTypeVars().addAll(newTypeVariablesBuilder().consume(typeParams));
		m.getFpars().addAll(newTFormalParametersBuilder().consume(fpars));
		m.setReturnTypeRef(orAnyPlus(newTypeRefBuilder().consume(returnTypeRefCtx)));
		return m;

	}

	private void setBasePropertiesOfTStructMember(TMemberWithAccessModifier tMember, PropertyNameContext nameCtx) {
		// TODO TStructMembers in N4JS do not yet support all forms of property names!
		String name = getSimpleNameFromPropertyName(nameCtx);
		if (name != null) {
			tMember.setName(name);
		}
		tMember.setDeclaredMemberAccessModifier(MemberAccessModifier.PUBLIC);
	}

	private DtsTypeVariablesBuilder newTypeVariablesBuilder() {
		return new DtsTypeVariablesBuilder(tokenStream, resource);
	}

	private DtsTFormalParametersBuilder newTFormalParametersBuilder() {
		return new DtsTFormalParametersBuilder(tokenStream, resource);
	}

	private DtsTypeRefBuilder newTypeRefBuilder() {
		return new DtsTypeRefBuilder(tokenStream, resource);
	}
}
