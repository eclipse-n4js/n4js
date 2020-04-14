/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.assistants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.AnnotationDefinition.RetentionPolicy;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4FieldAccessor;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.transpiler.TranspilerComponent;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.DelegatingMember;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 *
 */
public class ReflectionBuilder {
	final TranspilerComponent transpilerComponent;
	final TranspilerState state;
	final ResourceNameComputer resourceNameComputer;

	ReflectionBuilder(TranspilerComponent transpilerComponent, TranspilerState state,
			ResourceNameComputer resourceNameComputer) {

		this.transpilerComponent = transpilerComponent;
		this.state = state;
		this.resourceNameComputer = resourceNameComputer;
	}

	JsonElement createReflectionInfo(N4TypeDeclaration typeDecl, SymbolTableEntry typeSTE) {

		// [
		// 'MyClass',
		// 'MyModule',
		// 'Test',
		// [
		// 'F.myStatic',
		// 'f.myField',
		// 'm.foo',
		// 'g.gg',
		// 'S.gg',
		// 'm:iFoo',
		// 'f:iField'
		// ],
		// {
		// 'm.foo': [
		// 'Final'
		// ]
		// },
		// [
		// 'MyModule/I'
		// ],
		// [
		// 'Final'
		// ]
		// ]

		Type type = state.info.getOriginalDefinedType(typeDecl);

		// the name is written to reflection output only for interfaces
		boolean isInterface = (typeDecl instanceof N4InterfaceDeclaration);

		Iterable<N4MemberDeclaration> allMembers = (typeDecl instanceof N4ClassifierDeclaration)
				? ((N4ClassifierDeclaration) typeDecl).getOwnedMembers()
				: Collections.emptyList();

		String origin = resourceNameComputer.generateProjectDescriptor(state.resource.getURI());
		String fqn = resourceNameComputer.getFullyQualifiedTypeName(type);
		String modulePath = fqn.substring(0, fqn.length() - typeSTE.getName().length() - 1);

		List<JsonElement> members = createAllMembers(typeDecl, allMembers);
		List<Pair<String, JsonElement>> memberAnnotations = createMemberAnnotations(allMembers);
		List<JsonElement> annotations = createRuntimeAnnotations(typeDecl);

		// reverse order to respect semantics of default parameters
		JsonElement optAnnotations = (annotations == null || annotations.isEmpty())
				? null
				: array(annotations);
		JsonElement optMemberAnnotations = (optAnnotations == null && memberAnnotations.isEmpty())
				? null
				: object(memberAnnotations);
		JsonElement optMembers = (optMemberAnnotations == null && members.isEmpty())
				? null
				: array(members);

		JsonElement reflectInfo = array(
				isInterface ? primitive(typeSTE.getName()) : null,
				primitive(modulePath),
				primitive(origin),
				optMembers,
				optMemberAnnotations,
				optAnnotations);

		return reflectInfo;
	}

	private JsonArray array(JsonElement... elements) {
		return array(Arrays.asList(elements));
	}

	private JsonArray array(List<JsonElement> elements) {
		JsonArray array = new JsonArray();
		for (JsonElement elem : elements) {
			if (elem != null) {
				array.add(elem);
			}
		}
		return array;
	}

	private JsonElement arrayOrSelf(JsonElement... elements) {
		if (elements.length == 1) {
			return elements[0];
		}
		return array(elements);
	}

	private JsonElement object(List<Pair<String, JsonElement>> pairs) {
		JsonObject object = new JsonObject();
		for (Pair<String, JsonElement> elem : pairs) {
			if (elem != null) {
				object.add(elem.getKey(), elem.getValue());
			}
		}
		return object;
	}

	private JsonElement primitive(String value) {
		value = value.replaceAll("\"", "\\\\\"");
		return new JsonPrimitive(value);
	}

	private List<JsonElement> createAllMembers(N4TypeDeclaration typeDecl, Iterable<N4MemberDeclaration> allMembers) {
		if (typeDecl instanceof N4ClassDeclaration) {
			return createClassMembers(allMembers);
		}
		if (typeDecl instanceof N4InterfaceDeclaration) {
			return createInterfaceMembers(allMembers);
		}
		if (typeDecl instanceof N4EnumDeclaration) {
			return Collections.emptyList();
		}
		throw new RuntimeException("Unknown type to create members");
	}

	private List<JsonElement> createClassMembers(Iterable<N4MemberDeclaration> allMembers) {
		List<JsonElement> memberStrings = new ArrayList<>();
		for (N4MemberDeclaration member : allMembers) {
			if (state.info.isHiddenFromReflection(member)) {
				continue;
			}
			// create only some member strings, since others are detected from constructor and prototype
			boolean serialize = false;
			serialize |= !member.isStatic() && member instanceof N4FieldDeclaration;
			serialize |= state.info.isConsumedFromInterface(member);
			serialize |= member.getName().startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX);
			if (serialize) {
				memberStrings.add(primitive(createMemberString(member)));
			}
		}
		return memberStrings;
	}

	private List<JsonElement> createInterfaceMembers(Iterable<N4MemberDeclaration> allMembers) {
		List<JsonElement> memberStrings = new ArrayList<>();
		for (N4MemberDeclaration member : allMembers) {
			// do not datafields, default members, and #hasInstance
			boolean serialize = true;
			serialize &= !hasDefault(member);
			serialize &= !member.getName().equals(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX + "hasInstance");
			serialize &= !(member instanceof N4FieldDeclaration);

			if (serialize) {
				memberStrings.add(primitive(createMemberString(member)));
			}
		}
		return memberStrings;
	}

	private boolean hasDefault(N4MemberDeclaration member) {
		if (member instanceof N4FieldDeclaration) {
			return ((N4FieldDeclaration) member).getExpression() != null;
		}
		if (member instanceof N4MethodDeclaration) {
			return ((N4MethodDeclaration) member).getBody() != null;
		}
		if (member instanceof N4FieldAccessor) {
			return ((N4FieldAccessor) member).getBody() != null;
		}
		return false;
	}

	private List<Pair<String, JsonElement>> createMemberAnnotations(Iterable<N4MemberDeclaration> allMembers) {
		List<Pair<String, JsonElement>> memberAnnotationPairs = new ArrayList<>();
		for (N4MemberDeclaration member : allMembers) {
			TAnnotableElement tAnnotableElem = getTAnnotableElement(member);
			List<JsonElement> annotations = createRuntimeAnnotations2(tAnnotableElem);
			if (annotations != null && !annotations.isEmpty()) {
				String memberString = createMemberString(member);
				memberAnnotationPairs.add(Pair.of(memberString, array(annotations)));
			}
		}
		return memberAnnotationPairs;
	}

	private String createMemberString(N4MemberDeclaration member) {
		String kindLC = null;
		if (member instanceof N4FieldDeclaration) {
			kindLC = "f";
		} else if (member instanceof N4MethodDeclaration) {
			kindLC = "m";
		} else if (member instanceof N4GetterDeclaration) {
			kindLC = "g";
		} else if (member instanceof N4SetterDeclaration) {
			kindLC = "s";
		} else {
			throw new RuntimeException("Unknown member type");
		}
		String kind = (member.isStatic()) ? kindLC.toUpperCase() : kindLC;
		String consumed = (state.info.isConsumedFromInterface(member)) ? ":" : ".";
		return kind + consumed + member.getName();
	}

	private List<JsonElement> createRuntimeAnnotations(AnnotableElement annElem) {
		TAnnotableElement tAnnElem = getTAnnotableElement(annElem);
		return createRuntimeAnnotations2(tAnnElem);
	}

	private TAnnotableElement getTAnnotableElement(AnnotableElement annElem) {
		if (annElem instanceof DelegatingMember) {
			return (TAnnotableElement) ((DelegatingMember) annElem).getDelegationTarget().getOriginalTarget();
		}
		if (annElem instanceof N4TypeDeclaration) {
			return state.info.getOriginalDefinedType((N4TypeDeclaration) annElem);
		}
		if (annElem instanceof N4MemberDeclaration) {
			return state.info.getOriginalDefinedMember((N4MemberDeclaration) annElem);
		}
		if (annElem instanceof TypeDefiningElement) {
			// going via original AST as a fall back
			EObject original = state.tracer.getOriginalASTNode(annElem);
			if (original instanceof TypeDefiningElement) {
				return ((TypeDefiningElement) original).getDefinedType();
			}
		}
		return null;
	}

	private List<JsonElement> createRuntimeAnnotations2(TAnnotableElement tAnnElem) {
		if (tAnnElem == null) {
			return null; // note that this covers the case of a N4ClassifierDeclaration without a type in the TModule
		}

		List<JsonElement> runtimeAnnotations = new ArrayList<>();
		for (TAnnotation annotation : tAnnElem.getAnnotations()) {
			RetentionPolicy retention = AnnotationDefinition.find(annotation.getName()).retention;
			if (retention == RetentionPolicy.RUNTIME || retention == RetentionPolicy.RUNTIME_TYPEFIELD) {
				runtimeAnnotations.add(createRuntimeAnnotation2(annotation));
			}
		}
		if (runtimeAnnotations.isEmpty()) {
			return null;
		}

		return runtimeAnnotations;
	}

	private JsonElement createRuntimeAnnotation2(TAnnotation ann) {
		List<JsonElement> args = ann.getArgs().stream()
				.map(arg -> primitive(arg.getArgAsString()))
				.collect(Collectors.toList());

		if (args.isEmpty()) {
			return primitive(ann.getName());
		} else {
			return arrayOrSelf(
					primitive(ann.getName()),
					array(args));
		}
	}

}
