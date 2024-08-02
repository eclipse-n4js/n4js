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
package org.eclipse.n4js.ide.server.hover;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.postprocessing.ASTMetaInfoUtils;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;

import com.google.inject.Inject;

/**
 */
public class N4JSElementSignatureProvider {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private N4JSElementKeywordProvider keywordProvider;

	/***/
	public String get(EObject obj) {
		if (obj instanceof LiteralOrComputedPropertyName) {
			return get(obj.eContainer());
		}
		String label = getLabel(obj);
		UtilN4.sanitizeForHTML(label);
		return label;
	}

	String getLabel(EObject obj) {

		if (obj instanceof TMember) {
			return getKeyword(obj) + ((TMember) obj).getMemberAsString();

		} else if (obj instanceof TVariable) {
			return ((TVariable) obj).getVariableAsString();

		} else if (obj instanceof TFunction) {
			String keyword = getKeyword(obj);
			String label = ((TFunction) obj).getFunctionAsString();
			label = label.replaceFirst("function ", keyword);
			return label;

		} else if (obj instanceof TFormalParameter) {
			return ((TFormalParameter) obj).getFormalParameterAsString();

		} else if (obj instanceof FunctionTypeExprOrRef) {
			FunctionTypeExprOrRef ftref = (FunctionTypeExprOrRef) obj;
			String keyword = getKeyword(ftref.getFunctionType());
			String label = ftref.getAsFunctionString();
			label = label.replaceFirst("function ", keyword);
			return label;

		} else if (obj instanceof ParameterizedTypeRef) {
			ParameterizedTypeRef ptr = (ParameterizedTypeRef) obj;
			String keyword = getKeyword(ptr.getDeclaredType());
			return keyword + ptr.getTypeRefAsString();

		} else if (obj instanceof IdentifierRef) {
			IdentifierRef ir = (IdentifierRef) obj;
			IdentifiableElement ie = ir.getId();
			if (ie instanceof TFunction) {
				TypeRef typeRef = getTypeRefSubstituted(ir);
				if (typeRef instanceof FunctionTypeExprOrRef) {
					return getLabel(typeRef);
				}
			}
			return getLabel(ie);

		} else if (obj instanceof VariableDeclaration) {
			VariableDeclaration vd = (VariableDeclaration) obj;
			String keyword = vd.getDefinedVariable().isConst() ? "const " : "var ";
			return keyword + getName(obj) + getType(obj);

		} else if (obj instanceof GenericDeclaration) {
			GenericDeclaration gd = (GenericDeclaration) obj;
			return getLabel(gd.getDefinedType());

		} else if (obj instanceof N4TypeVariable) {
			N4TypeVariable tv = (N4TypeVariable) obj;
			return getKeyword(obj) + tv.getDefinedTypeVariable().getTypeAsString();

		} else if (obj instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) obj;

			TypeRef typeRef = getTypeRefSubstituted(ppae);
			if (typeRef instanceof FunctionTypeExprOrRef) {
				return getLabel(typeRef);
			}

			IdentifiableElement property = ppae.getProperty();
			String keyword;
			if (property instanceof TVariable) {
				TVariable tv = (TVariable) property;
				keyword = tv.isConst() ? "const " : "var ";
			} else {
				keyword = getKeyword(property);
			}
			return keyword + getName(ppae) + getType(ppae);

		} else if (obj instanceof ParameterizedCallExpression) {
			ParameterizedCallExpression pce = (ParameterizedCallExpression) obj;
			Expression target = pce.getTarget();
			String targs = "";
			if (!pce.getTypeArgs().isEmpty()) {
				targs += "<";
				targs += Strings.join(", ", ta -> getLabel(ta.getTypeRef()), pce.getTypeArgs());
				targs += ">";
			}
			String name = getName(target);
			name = (name == null ? "?" : name);
			String fpars = Strings.join(", ", a -> getLabel(a), pce.getArguments());
			TypeRef typeRef = ts.type(RuleEnvironmentExtensions.newRuleEnvironment(obj), pce);
			String retType = typeRef.getTypeRefAsStringWithAliasExpansion();
			return "%s%s(%s) : %s".formatted(targs, name, fpars, retType);

		} else if (obj instanceof Argument) {
			Argument arg = (Argument) obj;
			String spreadMarker = arg.isSpread() ? "…" : "";
			return spreadMarker + getName(obj) + getType(arg);

		} else if (obj instanceof FormalParameter) {
			FormalParameter fp = (FormalParameter) obj;
			String optinonalMarker = fp.isHasInitializerAssignment() ? "=…" : "";
			return getKeyword(obj) + getName(obj) + getType(obj) + optinonalMarker;

		} else if (obj instanceof LiteralOrComputedPropertyName) {
			if (obj.eContainer() instanceof TypableElement) {
				return getLabel(obj.eContainer());
			} else {
				return getName(obj);
			}

		} else if (obj instanceof Type) {
			return getKeyword(obj) + ((Type) obj).getTypeAsString();

		} else if (obj instanceof TypeRef) {
			return ((TypeRef) obj).getTypeRefAsStringWithAliasExpansion();

		} else if (getName(obj) != null) {
			return getKeyword(obj) + getName(obj) + getType(obj);

		} else if (N4JSASTUtils.getCorrespondingTypeModelElement(obj) != null) {
			return getLabel(N4JSASTUtils.getCorrespondingTypeModelElement(obj));
		}

		return null;
	}

	private TypeRef getTypeRefSubstituted(TypableElement obj) {
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(obj);
		TypeRef typeRef = ts.type(G, obj);
		if (obj.eContainer() instanceof ParameterizedCallExpression) {
			ParameterizedCallExpression pce = (ParameterizedCallExpression) obj.eContainer();
			List<TypeRef> typeArgs = ASTMetaInfoUtils.getInferredTypeArgs(pce);
			if (typeRef instanceof FunctionTypeExprOrRef) {
				FunctionTypeExprOrRef ftre = (FunctionTypeExprOrRef) typeRef;
				RuleEnvironmentExtensions.addTypeMappings(G, ftre.getTypeVars(), typeArgs);
				typeRef = ts.substTypeVariables(G, ftre);
			}
		}
		return typeRef;
	}

	private String getKeyword(EObject obj) {
		String keyword = keywordProvider.keyword(obj);
		if (keyword != null) {
			return keyword + " ";
		}
		return "";
	}

	private String getName(EObject obj) {
		if (obj instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) obj;
			return ppae.getPropertyAsText();

		} else if (obj instanceof FormalParameter) {
			FormalParameter fp = (FormalParameter) obj;
			return fp.getName();

		} else if (obj instanceof FunctionExpression) {
			return ((FunctionExpression) obj).getName();

		} else if (obj instanceof LiteralOrComputedPropertyName) {
			return ((LiteralOrComputedPropertyName) obj).getName();

		} else if (obj instanceof NamedElement) {
			return ((NamedElement) obj).getName();

		} else if (obj instanceof IdentifiableElement) {
			return ((IdentifiableElement) obj).getName();
		}

		return null;
	}

	private String getType(EObject obj) {
		String typeRefStr = "";
		if (obj instanceof TypableElement) {
			TypeRef typeRef = ts.type(RuleEnvironmentExtensions.newRuleEnvironment(obj), (TypableElement) obj);
			typeRefStr = ": " + typeRef.getTypeRefAsStringWithAliasExpansion();
		}
		return typeRefStr;
	}

}
