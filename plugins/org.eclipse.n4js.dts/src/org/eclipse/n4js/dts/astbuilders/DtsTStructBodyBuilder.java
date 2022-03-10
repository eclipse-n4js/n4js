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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_interfaceBody;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_interfaceMember;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_interfaceMemberList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.CallSignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.ConstructSignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.GetAccessorContext;
import org.eclipse.n4js.dts.TypeScriptParser.GetterContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceBodyContext;
import org.eclipse.n4js.dts.TypeScriptParser.MethodSignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterBlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertySignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.SetAccessorContext;
import org.eclipse.n4js.dts.TypeScriptParser.SetterContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeParametersContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefContext;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

import com.google.common.base.Strings;

/**
 * Builder to create {@link TStructMember}s from parse tree elements
 */
public class DtsTStructBodyBuilder
		extends AbstractDtsBuilderWithHelpers<InterfaceBodyContext, List<TStructMember>> {

	/** Constructor */
	public DtsTStructBodyBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_interfaceBody,
				RULE_interfaceMemberList,
				RULE_interfaceMember);
	}

	@Override
	public void enterInterfaceBody(InterfaceBodyContext ctx) {
		result = new ArrayList<>();
	}

	@Override
	public void enterCallSignature(CallSignatureContext ctx) {
		result.add(createTStructMethod(null, ctx));
	}

	@Override
	public void enterConstructSignature(ConstructSignatureContext ctx) {
		TStructMethod m = createTStructMethod(null, ctx.typeParameters(), ctx.parameterBlock(),
				ctx.colonSepTypeRef().typeRef());
		m.setName("new");
		result.add(m);
	}

	@Override
	public void enterPropertySignature(PropertySignatureContext ctx) {
		TStructField f = TypesFactory.eINSTANCE.createTStructField();
		setBasePropertiesOfTStructMember(f, ctx.propertyName());
		f.setTypeRef(orAnyPlus(newTypeRefBuilder().consume(ctx.colonSepTypeRef())));
		f.setOptional(ctx.QuestionMark() != null);
		result.add(f);
	}

	@Override
	public void enterGetAccessor(GetAccessorContext ctx) {
		GetterContext getterCtx = ctx.getter();
		if (getterCtx == null) {
			return;
		}
		TStructGetter g = TypesFactory.eINSTANCE.createTStructGetter();
		setBasePropertiesOfTStructMember(g, getterCtx.propertyName());
		g.setTypeRef(orAnyPlus(newTypeRefBuilder().consume(ctx.colonSepTypeRef())));
		result.add(g);
	}

	@Override
	public void enterSetAccessor(SetAccessorContext ctx) {
		SetterContext setterCtx = ctx.setter();
		if (setterCtx == null) {
			return;
		}
		TStructSetter g = TypesFactory.eINSTANCE.createTStructSetter();
		setBasePropertiesOfTStructMember(g, setterCtx.propertyName());
		TFormalParameter p = TypesFactory.eINSTANCE.createTFormalParameter();
		String fparName = ctx.Identifier() != null ? ctx.Identifier().getText() : null;
		p.setName(!Strings.isNullOrEmpty(fparName) ? fparName : "value");
		p.setTypeRef(orAnyPlus(newTypeRefBuilder().consume(ctx.colonSepTypeRef())));
		g.setFpar(p);
		result.add(g);
	}

	@Override
	public void enterMethodSignature(MethodSignatureContext ctx) {
		result.add(createTStructMethod(ctx.propertyName(), ctx.callSignature()));
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
		m.getFpars().addAll(newTFormalParametersBuilder().consumeWithDeclThisType(fpars, m));
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
}
