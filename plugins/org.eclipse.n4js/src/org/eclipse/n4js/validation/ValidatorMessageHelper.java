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
package org.eclipse.n4js.validation;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.tail;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.take;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Singleton;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.inject.Inject;

/**
 */
@Singleton
@SuppressWarnings("javadoc")
public class ValidatorMessageHelper {

	@Inject
	protected N4JSElementKeywordProvider keywordProvider;

	/**
	 * Returns type of member and short qualified name, and, if this is similar to another member, including the line
	 * number.
	 */
	public String descriptionDifferentFrom(TMember member, TMember differentMembers) {
		return descriptionDifferentFrom(member, List.of(differentMembers));
	}

	/**
	 * Returns type of member and short qualified name, and, if this is similar one of the other members, including the
	 * line number.
	 */
	public String descriptionDifferentFrom(TMember member, Iterable<TMember> otherMembers) {
		if (toSet(map(otherMembers, m -> m.getContainingType().getName()))
				.contains(member.getContainingType().getName())) {
			EObject ast = (EObject) member.eGet(TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT, false);
			if (null != ast && !ast.eIsProxy()) { // do not trigger automatic resolving here, since it will possibly
													// invalidate TMember-references in our caller
				ICompositeNode node = NodeModelUtils.getNode(ast);
				if (node != null) {
					int memberLine = node.getStartLine();
					return "%s (line %s)".formatted(description(member), memberLine);
				}
			}
		}
		return description(member);
	}

	/**
	 * Returns "static " if member is static, "const " for const fields and an empty strign otherwise; used in
	 * description methods.
	 */
	private String staticOrConstKeyword(TMember member) {
		if (member != null && member.isStatic()) {
			// exception for 'const' which is tagged static but meaning is implied by 'const' keyword
			if (member instanceof TField) {
				if (member.isConst()) {
					return "const ";
				}
			}
			return "static ";
		} else {
			return "";
		}
	}

	/**
	 * Returns "static " or "const "-prefixed keyword if member is static, used in description methods.
	 */
	private String prefixedKeyword(TMember member) {
		return staticOrConstKeyword(member) + keywordProvider.keyword(member);
	}

	public String description(final EObject classifier) {
		if (classifier instanceof TClassifier) {
			return _description((TClassifier) classifier);
		} else if (classifier instanceof IdentifierRef) {
			return _description((IdentifierRef) classifier);
		} else if (classifier instanceof TMember) {
			return _description((TMember) classifier);
		} else if (classifier instanceof FunctionDefinition) {
			return _description((FunctionDefinition) classifier);
		} else if (classifier instanceof IdentifiableElement) {
			return _description((IdentifiableElement) classifier);
		} else if (classifier instanceof NamedElement) {
			return _description((NamedElement) classifier);
		} else if (classifier != null) {
			return _description(classifier);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(classifier).toString());
		}
	}

	/**
	 * Returns with the keyword and the name of the function definition.
	 */
	private String _description(FunctionDefinition definition) {
		return keywordProvider.keyword(definition) + " " + definition.getName();
	}

	/**
	 * Returns keyword and name of a named element; this method is seldom called, usually more concrete methods are used
	 * instead.
	 */
	private String _description(NamedElement namedElement) {
		return keywordProvider.keyword(namedElement) + " " + namedElement.getName();
	}

	/**
	 * Returns keyword and name of an identifiable element; this method is seldom called, usually more concrete methods
	 * are used instead.
	 */
	private String _description(IdentifiableElement identifiableElement) {
		if (identifiableElement == null) {
			return "unknown ";
		}
		return keywordProvider.keyword(identifiableElement) + " " + identifiableElement.getName();
	}

	/**
	 * Returns type of member and short qualified name.
	 */
	private String _description(TMember member) {
		if (member.isConstructor()) {
			ContainerType<?> container = member.getContainingType();
			String preposition = (container instanceof TInterface) ? "in" : "of";
			String cdescr = container == null ? "" : description(container);
			return keywordProvider.keyword(member) + " " + preposition + " " + cdescr;
		}
		return prefixedKeyword(member) + " " + shortQualifiedName(member);
	}

	/**
	 * Returns type of classifier and short qualified name.
	 */
	private String _description(TClassifier classifier) {
		return keywordProvider.keyword(classifier) + " " + classifier.getName();
	}

	private String _description(IdentifierRef identifierRef) {
		return "identifier " + identifierRef.getIdAsText();
	}

	private String _description(@SuppressWarnings("unused") EObject eObject) {
		return "<unnamed>";
	}

	/**
	 * Returns type of member and simple name.
	 */
	public String shortDescription(TMember member) {
		return prefixedKeyword(member) + " " + member.getName();
	}

	/**
	 * Returns type of member and simple name.
	 */
	public String shortDescription(TFunction func) {
		return keywordProvider.keyword(func) + " " + func.getName();
	}

	/**
	 * Returns type of member and simple name.
	 */
	public String shortDescription(TVariable tvar) {
		return keywordProvider.keyword(tvar) + " " + tvar.getName();
	}

	/**
	 * Returns descriptions, comma separated, last one added with "and".
	 */
	public String descriptions(Iterable<? extends TMember> members) {
		TMember last = last(members);
		if (last == null) {
			return "";
		}
		if (last == head(members)) {
			return description(last);
		}
		return join(filter(members, m -> m != last), "", ", ", "", m -> description(m)) + " and " + description(last);
	}

	public String shortQualifiedName(TMember member) {
		return member.getContainingType().getName() + "." + member.getName();
	}

	public String shortQualifiedName(TFunction func) {
		return func.getName();
	}

	public String shortQualifiedName(TVariable tvar) {
		return tvar.getName();
	}

	/**
	 * Returns description of "interface" and short qualified name for all interfaces.
	 */
	public String names(Iterable<? extends IdentifiableElement> namedElements) {
		String result = "";
		List<? extends IdentifiableElement> list = toList(namedElements);
		for (int i = 0; i < list.size(); i++) {
			result += list.get(i).getName();
			if (i < list.size() - 2) {
				result += ",";
			} else {
				result += " and ";
			}
		}
		return result;
	}

	/**
	 * Returns type of member and name of the member with line number in brackets.
	 */
	public String descriptionWithLine(TMember member) {
		String memberLine = getMemberLine(member.getAstElement());
		return prefixedKeyword(member) + " " + member.getName() + memberLine;
	}

	private String getMemberLine(EObject element) {
		if (element != null) {
			ICompositeNode node = NodeModelUtils.getNode(element);
			if (node != null) {
				return " (line %s)".formatted(node.getStartLine());
			}
		}
		return "";
	}

	/**
	 * Returns type of ast element and line number in brackets.
	 */
	public String descriptionWithLine(EObject eAST) {
		String memberLine = getMemberLine(eAST);
		String result = keywordProvider.keyword(eAST) + memberLine;
		return result.trim();
	}

	/**
	 * Returns type and name of ast element and line number in brackets.
	 */
	public String descriptionWithLine(EObject eAST, String name) {
		String memberLine = getMemberLine(eAST);
		return keywordProvider.keyword(eAST) + " " + name + memberLine;
	}

	/**
	 * Returns type and name of the EObject.
	 */
	public String description(EObject eAST, String name) {
		return keywordProvider.keyword(eAST) + " " + name;
	}

	/**
	 * Returns verb of redefinition, that is either "implemented", "consumed", or "overridden", or "inherited".
	 *
	 */
	public String redefinitionString(TClassifier currentClassifier, TMember overriding, TMember overridden) {
		ContainerType<?> overriddenContainer = overridden.getContainingType();
		if (overridden.isAbstract()) { // members of interfaces are always abstract
			return "implemented";
		}
		if (overriddenContainer instanceof TInterface) {
			return "implemented";
		}
		if (overriding.getContainingType() == currentClassifier) {
			return "overridden";
		}
		return "inherited";
	}

	/**
	 * Returns verb of inheritance, that is either "consumed", or "inherited".
	 *
	 */
	public String inheritanceString(TMember inheritedMember) {
		ContainerType<?> container = inheritedMember.getContainingType();
		if (container instanceof TInterface) {
			return "consumed";
		}
		return "inherited";
	}

	/**
	 * Returns message provided by type system, or empty string if no message is provided. The returned message is
	 * trimmed, that is, preceeding "failed: " is removed and whitespaces are trimmed.
	 */
	public String trimTypesystemMessage(Result tsresult) {
		String msg = tsresult == null ? null : tsresult.getFailureMessage();
		if (msg == null) {
			return "";
		}
		if (msg.startsWith("failed:")) {
			return msg.substring("failed:".length()).trim();
		}
		return msg.trim();
	}

	public String fullFunctionSignature(TFunction tfunction) {
		StringBuilder strb = new StringBuilder();

		// tfunction.annotations only includes annotations with {@link RetentionPolicy.TYPE} or {@link
		// RetentionPolicy.RUNTIME}
		for (TAnnotation a : filter(tfunction.getAnnotations(),
				it -> !Objects.equals(it.getName(), AnnotationDefinition.INTERNAL.name))) {
			strb.append(a.getAnnotationAsString());
			strb.append(" ");
		}
		if (tfunction instanceof TMethod) {
			TMethod tm = (TMethod) tfunction;
			if (tm.getMemberAccessModifier() != null) {
				strb.append(keywordProvider.keyword(tm.getMemberAccessModifier()));
				strb.append(" ");
			}
			if (tm.isAbstract()) {
				strb.append("abstract ");
			}
		}
		if (!(tfunction instanceof TMethod)) {
			if (isAsyncOrPromise(tfunction)) {
				strb.append("async ");
			}
			strb.append("function ");
		}
		if (isGenerator(tfunction)) {
			strb.append("* ");
		}
		if (tfunction.isGeneric()) {
			strb.append("<" + join(map(tfunction.getTypeVars(), tv -> tv.getTypeAsString()), ",") + "> ");
		}

		if (tfunction instanceof TMethod) {
			if (isAsyncOrPromise(tfunction))
				strb.append("async ");
		}
		strb.append(tfunction.getName() + "("
				+ join(map(tfunction.getFpars(), fp -> fp.getFormalParameterAsString()), ", ") + ")");
		strb.append(": ");
		appendPromisedReturnType(strb, tfunction);
		return strb.toString();
	}

	/**
	 * Returns nicely worded enumeration of the given items.
	 *
	 * Examples <code>
	 * "A" -> "A"
	 * "A", "B" -> "A or B"
	 * "A", "B", "C" -> "A, B or C"
	 * </code>
	 */
	public String orList(List<String> items) {
		// if there is nothing to enumerate
		if (items.isEmpty()) {
			return "";
		}
		if (items.size() == 1) {
			return head(items);
		}

		// now: (items.size() >= 2) {
		return new StringBuilder()
				// add first element
				.append(head(items))
				// add middle elements with prepended "or" (possible empty sublist)
				.append(join(take(tail(items), items.size() - 2), ", ", ", ", "", it -> it))
				// add last element with prepended "or"
				.append(" or " + last(items))
				.toString();
	}

	/**
	 * Returns true if a type ref has been appended.
	 */
	public boolean appendPromisedReturnType(StringBuilder strb, TFunction tfunction) {
		boolean async = isAsyncOrPromise(tfunction);
		boolean generator = isGenerator(tfunction);
		if (async || generator) {
			// use the return type that is declared in the AST, rather than the actual return type
			EObject astElem = tfunction.getAstElement();
			if (astElem instanceof FunctionDeclaration) {
				TypeRef retType = ((FunctionDeclaration) astElem).getDeclaredReturnTypeRef();
				if (retType != null) {
					strb.append(retType.getTypeRefAsString());
					return true;
				}
			}
			if (astElem instanceof FunctionDefinition) {
				TypeRef retType = ((FunctionDefinition) astElem).getDeclaredReturnTypeRef();
				if (retType != null) {
					strb.append(retType.getTypeRefAsString());
					return true;
				}
			}

			ParameterizedTypeRef ptr = (ParameterizedTypeRef) tfunction.getReturnTypeRef();
			TypeArgument asyncReturnType = ptr.getDeclaredTypeArgs().get(0);
			if (asyncReturnType != null) {
				if (TypeUtils.isUndefined(asyncReturnType)) {
					strb.append("void");
				} else {
					strb.append(asyncReturnType.getTypeRefAsString());
				}
				return true;
			}
		} else {
			if (tfunction.getReturnTypeRef() != null) {
				strb.append(tfunction.getReturnTypeRef().getTypeRefAsString());
				return true;
			}
		}
		return false;
	}

	private boolean isAsyncOrPromise(TFunction tfunction) {
		if (!tfunction.isDeclaredAsync()) {
			return false;
		}

		ResourceSet rs = tfunction.eResource() == null ? null : tfunction.eResource().getResourceSet();
		if (rs == null) { // should not happen, but in case of messages the tfunction may be a temp. instance
			return false;
		}
		return TypeUtils.isPromise(tfunction.getReturnTypeRef(), BuiltInTypeScope.get(rs));
	}

	private boolean isGenerator(TFunction tfunction) {
		if (!tfunction.isDeclaredGenerator()) {
			return false;
		}

		ResourceSet rs = tfunction.eResource() == null ? null : tfunction.eResource().getResourceSet();
		if (rs == null) { // should not happen, but in case of messages the tfunction may be a temp. instance
			return false;
		}
		return TypeUtils.isGeneratorOrAsyncGenerator(tfunction.getReturnTypeRef(), BuiltInTypeScope.get(rs));
	}

}
