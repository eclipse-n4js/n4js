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
package org.eclipse.n4js.typesbuilder;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isNullOrEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.compileTime.CompileTimeEvaluator;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AnnotationArgument;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.LiteralAnnotationArgument;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.ModifierUtils;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument;
import org.eclipse.n4js.postprocessing.ComputedNameProcessor;
import org.eclipse.n4js.scoping.N4JSScopeProviderLocalOnly;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationStringArgument;
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.TameAutoClosable;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
class N4JSTypesBuilderHelper {

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;
	@Inject
	private CompileTimeEvaluator compileTimeEvaluator;
	@Inject
	private N4JSScopeProviderLocalOnly n4jsScopeProviderLocalOnly;

	private static Logger logger = Logger.getLogger(N4JSTypesBuilderHelper.class);

	protected <T extends AnnotableElement & ModifiableElement> void setTypeAccessModifier(
			AccessibleTypeElement classifier, T definition) {

		boolean isPlainJS = jsVariantHelper.isPlainJS(definition);
		// IDEBUG-861 assume public visibility if plain JS
		if (isPlainJS) {
			classifier.setDeclaredTypeAccessModifier(TypeAccessModifier.PUBLIC);
		} else {
			classifier.setDeclaredTypeAccessModifier(ModifierUtils.convertToTypeAccessModifier(
					definition.getDeclaredModifiers(), definition.getAllAnnotations()));
		}
	}

	void setProvidedByRuntime(AccessibleTypeElement declaredType, AnnotableElement annotableElement,
			@SuppressWarnings("unused") boolean preLinkingPhase) {

		declaredType.setDeclaredProvidedByRuntime(AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(
				annotableElement));
	}

	/**
	 * Adds references to a feature (via the closure), but copies the references in order to avoid problems with
	 * containment relations. The references are copied with proxies (see {@link TypeUtils#copyWithProxies(EObject)} in
	 * order to avoid resolving of proxies here. Null values (usually caused by a syntax error) are omitted, i.e.
	 * indices are not preserved.
	 */
	<T extends EObject> void addCopyOfReferences(List<? super T> target, List<T> values) {
		if (isNullOrEmpty(values)) {
			return;
		}
		if (exists(values, it -> it != null && it.eIsProxy())) {
			throw new IllegalStateException("There is a proxy in the list, cannot copy and set references");
		}
		target.addAll(toList(map(filterNull(values), it -> TypeUtils.copyWithProxies(it))));
	}

	/**
	 * Initializes the name of a TMember in the TModule based the member/property declaration in the AST. In case of
	 * computed property names, this method will keep the name in the TModule set to <code>null</code> and
	 * {@link ComputedNameProcessor} will later change it to the actual name during post-processing.
	 */
	void setMemberName(TMember tMember, PropertyNameOwner n4MemberOrPropertyAssignment) {
		// this will be 'null' in case of a computed property name
		tMember.setName(n4MemberOrPropertyAssignment.getName());
		LiteralOrComputedPropertyName declaredName = n4MemberOrPropertyAssignment.getDeclaredName();
		tMember.setHasComputedName(declaredName != null && declaredName.getKind() == PropertyNameKind.COMPUTED);
	}

	/**
	 * Translates AST related member access modifier (and annotation {@code @Interanl}) to type model related member
	 * access modifier.
	 */
	void setMemberAccessModifier(Consumer<MemberAccessModifier> memberAccessModifierAssignment,
			Collection<? extends N4Modifier> modifiers, List<Annotation> annotations) {
		memberAccessModifierAssignment.accept(ModifierUtils.convertToMemberAccessModifier(modifiers, annotations));
	}

	/** Returns newly created reference to built-in type <code>any</code>. */
	ParameterizedTypeRef createAnyTypeRef(EObject object) {
		ResourceSet rs = null;
		if (object != null && object.eResource() != null) {
			rs = object.eResource().getResourceSet();
		}
		if (rs != null) {
			return BuiltInTypeScope.get(rs).getAnyTypeRef();
		}
		return null;
	}

	/**
	 * Copies annotations from AST to Type model. Note that not all annotations are copied, only the ones listed in
	 * {@link AnnotationDefinition#ANNOTATIONS_IN_TYPEMODEL}. Also annotations contained in containing export
	 * declaration are copied, as this construct is not present in type model and its annotations are to be defined at
	 * the type.
	 * <p>
	 * Since this mechanism is changed anyway, no type check (whether annotation is allowed for given element type) is
	 * performed.
	 */
	void copyAnnotations(TAnnotableElement typeTarget, AnnotableElement ast,
			@SuppressWarnings("unused") boolean preLinkingPhase) {

		for (Annotation ann : ast.getAllAnnotations()) {
			if (AnnotationDefinition.isInTypeModel(ann.getName())) {
				TAnnotation ta = TypesFactory.eINSTANCE.createTAnnotation();
				ta.setName(ann.getName());

				for (AnnotationArgument arg : ann.getArgs()) {
					if (arg instanceof LiteralAnnotationArgument) {
						TAnnotationStringArgument targ = TypesFactory.eINSTANCE.createTAnnotationStringArgument();
						targ.setValue(((LiteralAnnotationArgument) arg).getLiteral().getValueAsString());
						ta.getArgs().add(targ);
					} else if (arg instanceof TypeRefAnnotationArgument) {
						TAnnotationTypeRefArgument targ = TypesFactory.eINSTANCE.createTAnnotationTypeRefArgument();
						TypeRefAnnotationArgument traa = (TypeRefAnnotationArgument) arg;
						targ.setTypeRef(TypeUtils.copyWithProxies(
								traa.getTypeRefNode() == null ? null : traa.getTypeRefNode().getTypeRefInAST()));
						ta.getArgs().add(targ);
					}
				}
				typeTarget.getAnnotations().add(ta);
			}
		}
	}

	/** Returns newly created reference to built-in type <code>any</code>. */
	TClassifier getObjectType(EObject object) {
		ResourceSet rs = null;
		if (object != null && object.eResource() != null) {
			rs = object.eResource().getResourceSet();
		}
		if (rs != null) {
			return BuiltInTypeScope.get(rs).getObjectType();
		}
		return null;
	}

	/** @see TClassifier#isDeclaredCovariantConstructor() */
	boolean isDeclaredCovariantConstructor(N4ClassifierDeclaration classifierDecl) {
		if (AnnotationDefinition.COVARIANT_CONSTRUCTOR.hasAnnotation(classifierDecl)) {
			return true;
		}
		N4MemberDeclaration ctor = findFirst(classifierDecl.getOwnedMembers(), m -> m.isConstructor());
		return ctor != null && AnnotationDefinition.COVARIANT_CONSTRUCTOR.hasAnnotation(ctor);
	}

	/** Handle optional "@This" annotation and set its argument as declared this type in types model element. */
	protected void setDeclaredThisTypeFromAnnotation(TFunction functionType, FunctionDefinition functionDef,
			boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			functionType.setDeclaredThisType(TypeUtils.copyWithProxies(
					internalGetDeclaredThisTypeFromAnnotation(functionDef)));
		}
	}

	/** Handle optional "@This" annotation and set its argument as declared this type in types model element. */
	protected void setDeclaredThisTypeFromAnnotation(FieldAccessor accessorType,
			org.eclipse.n4js.n4JS.FieldAccessor accessorDecl, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			accessorType.setDeclaredThisType(TypeUtils.copyWithProxies(
					internalGetDeclaredThisTypeFromAnnotation(accessorDecl)));
		}
	}

	private TypeRef internalGetDeclaredThisTypeFromAnnotation(AnnotableElement element) {
		Annotation annThis = AnnotationDefinition.THIS.getAnnotation(element);
		if (annThis != null && annThis.getArgs() != null) {
			TypeRefAnnotationArgument traa = head(filter(annThis.getArgs(), TypeRefAnnotationArgument.class));
			if (traa != null && traa.getTypeRefNode() != null) {
				return traa.getTypeRefNode().getTypeRefInAST();
			}
		}
		return null;
	}

	/**
	 * Used during
	 * {@link N4JSTypesBuilder#relinkTModuleToSource(org.eclipse.xtext.resource.DerivedStateAwareResource, boolean)
	 * relinking}, to ensure consistency of named elements between newly loaded AST and original TModule.
	 */
	protected void ensureEqualName(NamedElement astNode, IdentifiableElement moduleElement) {
		ensureEqualName(astNode, moduleElement.getName());
	}

	protected void ensureEqualName(NamedElement astNode, String nameInModule) {
		String nameInAST = astNode == null ? null : astNode.getName();
		if (astNode != null && nameInAST != null) {
			// note: no check if no name available in AST (don't fiddle with computed property names, etc.)
			if (!nameInAST.equals(nameInModule)) {
				String msg = "inconsistency between newly loaded AST and to-be-linked TModule: "
						+ "nameInAST=" + nameInAST + ", "
						+ "nameInModule=" + nameInModule + ", "
						+ "in: " + (astNode.eResource() == null ? null : astNode.eResource().getURI().toString());
				logger.error(msg);
				throw new IllegalStateException(msg);
			}
		}
	}

	ModuleNamespaceVirtualType addNewModuleNamespaceVirtualType(TModule target, String name, TModule wrappedModule,
			boolean dynamic, TypeDefiningElement astNode) {
		ModuleNamespaceVirtualType type = TypesFactory.eINSTANCE.createModuleNamespaceVirtualType();
		type.setName(name);
		type.setModule(wrappedModule);
		type.setDeclaredDynamic(dynamic);

		type.setAstElement(astNode);
		astNode.setDefinedType(type);

		target.getInternalTypes().add(type);

		return type;
	}

	boolean hasValidName(PropertyNameOwner pno) {
		if (pno.getName() == null && pno.hasComputedPropertyName()) {
			if (N4JSLanguageUtils.isProcessedAsCompileTimeExpression(pno.getDeclaredName().getExpression())) {
				return false;
			}

			LiteralOrComputedPropertyName litOrComp = pno.getDeclaredName();
			RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(pno);
			try (TameAutoClosable tac = n4jsScopeProviderLocalOnly.newCrossFileResolutionSuppressor()) {
				CompileTimeValue value = compileTimeEvaluator.evaluateCompileTimeExpression(G,
						litOrComp.getExpression());
				String name = N4JSLanguageUtils.derivePropertyNameFromCompileTimeValue(value);
				if (name == null) {
					return false;
				}
			}
		}
		return true;
	}

	boolean validPNO(PropertyNameOwner pno) {
		return pno.getName() != null || pno.hasComputedPropertyName();
	}
}
