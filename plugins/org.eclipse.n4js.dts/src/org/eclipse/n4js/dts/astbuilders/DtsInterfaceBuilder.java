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

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.CallSignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.ConstructSignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.GetAccessorContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceExtendsClauseContext;
import org.eclipse.n4js.dts.TypeScriptParser.MethodSignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterBlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertySignatureContext;
import org.eclipse.n4js.dts.TypeScriptParser.SetAccessorContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeParametersContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefContext;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsInterfaceBuilder extends AbstractDtsBuilder<InterfaceDeclarationContext, N4InterfaceDeclaration> {
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(tokenStream, resource);
	private final DtsTypeVariablesBuilder typeVariablesBuilder = new DtsTypeVariablesBuilder(tokenStream, resource);
	private final DtsFormalParametersBuilder formalParametersBuilder = new DtsFormalParametersBuilder(tokenStream,
			resource);
	private final DtsPropertyNameBuilder propertyNameBuilder = new DtsPropertyNameBuilder(tokenStream, resource);

	/** Constructor */
	public DtsInterfaceBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
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
	public void enterInterfaceDeclaration(InterfaceDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4InterfaceDeclaration();
		result.setName(ctx.identifierName().getText());
		result.getDeclaredModifiers().add(N4Modifier.EXTERNAL);

		List<N4TypeVariable> typeVars = typeVariablesBuilder.consume(ctx.typeParameters());
		result.getTypeVars().addAll(typeVars);

		InterfaceExtendsClauseContext extendsClause = ctx.interfaceExtendsClause();
		if (extendsClause != null && extendsClause.classOrInterfaceTypeList() != null
				&& extendsClause.classOrInterfaceTypeList().parameterizedTypeRef() != null
				&& !extendsClause.classOrInterfaceTypeList().parameterizedTypeRef().isEmpty()) {

			ParameterizedTypeRefContext extendsTypeRefCtx = extendsClause.classOrInterfaceTypeList()
					.parameterizedTypeRef().get(0);

			TypeReferenceNode<ParameterizedTypeRef> typeRefNode = typeRefBuilder.consume(extendsTypeRefCtx);
			result.getSuperInterfaceRefs().add(typeRefNode);
		}

		walker.enqueue(ctx.interfaceBody());
	}

	@Override
	public void enterPropertySignature(PropertySignatureContext ctx) {
		N4FieldDeclaration fd = N4JSFactory.eINSTANCE.createN4FieldDeclaration();
		fd.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		fd.setDeclaredName(propertyNameBuilder.consume(ctx.propertyName()));
		fd.setDeclaredOptional(ctx.QuestionMark() != null);

		if (ctx.ReadOnly() != null) {
			// "In interfaces, only methods may be declared final." (CLF_NO_FINAL_INTERFACE_MEMBER)
			// consider to create a getter instead of a field
		}

		TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(ctx.colonSepTypeRef());
		fd.setDeclaredTypeRefNode(trn);

		addLocationInfo(fd, ctx);
		result.getOwnedMembersRaw().add(fd);
	}

	@Override
	public void enterCallSignature(CallSignatureContext ctx) {
		N4MethodDeclaration md = createMethodDeclaration(null, ctx);
		addLocationInfo(md, ctx);
		result.getOwnedMembersRaw().add(md);
	}

	@Override
	public void enterConstructSignature(ConstructSignatureContext ctx) {
		N4MethodDeclaration md = createMethodDeclaration(null, ctx.typeParameters(), ctx.parameterBlock(),
				ctx.colonSepTypeRef().typeRef());
		LiteralOrComputedPropertyName locpn = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
		locpn.setKind(PropertyNameKind.IDENTIFIER);
		locpn.setLiteralName("new");
		md.setDeclaredName(locpn);

		addLocationInfo(md, ctx);
		result.getOwnedMembersRaw().add(md);
	}

	@Override
	public void enterMethodSignature(MethodSignatureContext ctx) {
		N4MethodDeclaration md = createMethodDeclaration(ctx.propertyName(), ctx.callSignature());
		addLocationInfo(md, ctx);
		result.getOwnedMembersRaw().add(md);
	}

	private N4MethodDeclaration createMethodDeclaration(PropertyNameContext name, CallSignatureContext callSignature) {
		return createMethodDeclaration(name, callSignature.typeParameters(), callSignature.parameterBlock(),
				callSignature.typeRef());
	}

	private N4MethodDeclaration createMethodDeclaration(PropertyNameContext name, TypeParametersContext typeParams,
			ParameterBlockContext fpars, TypeRefContext returnTypeRef) {

		N4MethodDeclaration md = N4JSFactory.eINSTANCE.createN4MethodDeclaration();
		md.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		md.setDeclaredName(propertyNameBuilder.consume(name));

		if (typeParams != null) {
			List<N4TypeVariable> typeVars = typeVariablesBuilder.consume(typeParams);
			md.getTypeVars().addAll(typeVars);
		}
		if (fpars != null) {
			List<FormalParameter> fPars = formalParametersBuilder.consume(fpars);
			md.getFpars().addAll(fPars);
		}
		if (returnTypeRef != null) {
			TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(returnTypeRef);
			md.setDeclaredReturnTypeRefNode(trn);
		}

		return md;
	}

	@Override
	public void enterGetAccessor(GetAccessorContext ctx) {
		N4GetterDeclaration getter = DtsClassBuilder.createGetAccessor(ctx, propertyNameBuilder, typeRefBuilder);
		if (getter != null) {
			addLocationInfo(getter, ctx);
			result.getOwnedMembersRaw().add(getter);
		}
	}

	@Override
	public void enterSetAccessor(SetAccessorContext ctx) {
		N4SetterDeclaration setter = DtsClassBuilder.createSetAccessor(ctx, this, propertyNameBuilder, typeRefBuilder);
		if (setter != null) {
			addLocationInfo(setter, ctx);
			result.getOwnedMembersRaw().add(setter);
		}
	}
}
