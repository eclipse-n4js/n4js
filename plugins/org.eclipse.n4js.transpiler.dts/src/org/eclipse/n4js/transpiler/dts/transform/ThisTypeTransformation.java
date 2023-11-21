/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._FormalParameter;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4MethodDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._TypeReferenceNode;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.setThisBinding;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * This transformation replaces some ThisTypeRefs by concrete type references iff they occur in static methods or
 * constructors. This is true for the following cases:
 * <ul>
 * <li/>replace spec parameter (this-type) of constructors by concrete type
 * <li/>add (missing) spec constructor iff superclass defines one
 * <li/>replace return this-type of static methods by concrete type
 * <li/>add method override for all static methods returning this-types to replace return type by concrete type
 * </ul>
 */
public class ThisTypeTransformation extends Transformation {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private TypeSystemHelper tsh;

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		transformThisTypeRefs();
	}

	private void transformThisTypeRefs() {
		List<N4ClassDeclaration> classes = collectNodes(getState().im, true, N4ClassDeclaration.class);

		for (N4ClassDeclaration clazz : classes) {
			N4ClassDeclaration clazzOrig = (N4ClassDeclaration) getState().tracer.getOriginalASTNode(clazz);

			List<N4MethodDeclaration> methods = collectNodes(clazz, false, N4MethodDeclaration.class);
			Set<String> methodNamesCurClass = new HashSet<>();
			N4MethodDeclaration ctor = null;
			for (N4MethodDeclaration method : methods) {
				if (method.isConstructor()) {
					ctor = method;
				} else if (method.isStatic()) {
					methodNamesCurClass.add(method.getName());
					// special handling for return type 'this' of static methods
					TypeRef retTR = getState().info.getOriginalProcessedTypeRef(method.getDeclaredReturnTypeRefNode());
					if (retTR instanceof ThisTypeRef) {
						N4MethodDeclaration methodOrig = getState().tracer.getOriginalASTNodeOfSameType(method, false);
						var typeRef = tsh.bindAndSubstituteThisTypeRef(getState().G, methodOrig, retTR);
						typeRef = ts.upperBoundWithReopen(getState().G, typeRef);
						if (typeRef.isGeneric()) {
							typeRef = TypeUtils.copyIfContained(typeRef);
							int newTypeArgsCount = typeRef.getDeclaredType().getTypeVars().size();
							typeRef.getDeclaredTypeArgs().clear(); // only 'any' allowed here by TypeScript
							for (var i = 0; i < newTypeArgsCount; i++) {
								typeRef.getDeclaredTypeArgs().add(TypeRefsFactory.eINSTANCE.createWildcard());
							}
						}
						getState().info.setOriginalProcessedTypeRef(method.getDeclaredReturnTypeRefNode(), typeRef);
					}
				}
			}

			if (ctor != null) {
				// special handling for constructor parameter @Spec ~i~this
				FormalParameter specFPar = getSpecFPar(ctor);
				if (specFPar != null) {
					FormalParameter specFParOrig = getState().tracer.getOriginalASTNodeOfSameType(specFPar, false);
					if (specFParOrig != null) {
						var typeRef = getState().info.getOriginalProcessedTypeRef(specFPar.getDeclaredTypeRefNode());
						typeRef = tsh.bindAndSubstituteThisTypeRef(getState().G, specFParOrig, typeRef);
						typeRef = ts.upperBoundWithReopen(getState().G, typeRef);
						getState().info.setOriginalProcessedTypeRef(specFPar.getDeclaredTypeRefNode(), typeRef);
					}
				}

			} else {
				// search spec constructor in superclass and put its signature here and replace return 'this' type by
				// actual type
				TMethod specConstructorOrig = findSpecConstructorInSuperclass(clazzOrig);
				if (specConstructorOrig != null) {
					addOverwritingSpecConstructor(specConstructorOrig, clazz);
				}
			}

			Collection<TMethod> staticMethodsReturingThisInSuperclasses = findStaticMethodsReturingThisInSuperclasses(
					clazzOrig);
			for (TMethod method : staticMethodsReturingThisInSuperclasses) {
				if (!methodNamesCurClass.contains(method.getName())) {
					// put signature here and replace return 'this' type by actual type
					addOverwritingStaticMethod(method, clazz);
				}
			}
		}
	}

	private Collection<TMethod> findStaticMethodsReturingThisInSuperclasses(N4ClassDeclaration clazzOrig) {
		Map<String, TMethod> name2method = new HashMap<>();
		TClass curClass = clazzOrig.getDefinedTypeAsClass();
		while (curClass.getSuperClass() != null) {
			curClass = curClass.getSuperClass();
			for (TMember member : curClass.getOwnedMembers()) {
				if (member instanceof TMethod) {
					TMethod tMethod = (TMethod) member;
					if (member.isStatic() && tMethod.getReturnTypeRef() instanceof ThisTypeRef) {
						name2method.putIfAbsent(member.getName(), tMethod);
					}
				}
			}
		}

		return name2method.values();
	}

	private TMethod findSpecConstructorInSuperclass(N4ClassDeclaration clazzOrig) {
		TClass curClass = clazzOrig.getDefinedTypeAsClass();
		do {
			if (curClass.getSuperClass() == null) {
				return null;
			}
			curClass = curClass.getSuperClass();
			TMethod ctor = curClass.getOwnedCtor();

			if (isSpecConstructor(ctor)) {
				return ctor;
			} else if (ctor != null) {
				return null;
			}
		} while (true);
	}

	@SuppressWarnings("unused")
	private N4MethodDeclaration getConstructor(N4ClassDeclaration clazz) {
		EList<N4MethodDeclaration> methods = clazz.getOwnedMethods();
		for (N4MethodDeclaration method : methods) {
			if (method.isConstructor()) {
				return method;
			}
		}
		return null;
	}

	private FormalParameter getSpecFPar(N4MethodDeclaration method) {
		if (method == null) {
			return null;
		}
		if (!method.isConstructor()) {
			return null;
		}
		if (method.getFpars().isEmpty()) {
			return null;
		}
		for (FormalParameter fpar : method.getFpars()) {
			if (AnnotationDefinition.SPEC.hasAnnotation(fpar)) {
				return fpar;
			}
		}
		return null;
	}

	private boolean isSpecConstructor(TMethod method) {
		if (method == null) {
			return false;
		}
		if (!method.isConstructor()) {
			return false;
		}
		if (method.getFpars().isEmpty()) {
			return false;
		}
		for (TFormalParameter fpar : method.getFpars()) {
			if (AnnotationDefinition.SPEC.hasAnnotation(fpar)) {
				return true;
			}
		}
		return false;
	}

	private N4MethodDeclaration addOverwritingSpecConstructor(TMethod constructor, N4ClassDeclaration clazz) {
		N4ClassDeclaration clazzOrig = (N4ClassDeclaration) getState().tracer.getOriginalASTNode(clazz);
		N4MethodDeclaration newConstructor = _N4MethodDecl("constructor");
		clazz.getOwnedMembersRaw().add(newConstructor);
		for (TFormalParameter fpar : constructor.getFpars()) {
			FormalParameter newFPar = _FormalParameter(fpar.getName());
			newConstructor.getFpars().add(newFPar);
			TypeRef typeRef = fpar.getTypeRef();
			if (AnnotationDefinition.SPEC.hasAnnotation(fpar)) {
				TypeRef thisTypeRef = TypeUtils.createTypeRefWithParamsAsArgs(clazzOrig.getDefinedType());
				typeRef = getConcreteTypeRef(fpar.getTypeRef(), thisTypeRef);
			}

			newFPar.setDeclaredTypeRefNode(_TypeReferenceNode(getState(), typeRef));
			getState().info.setOriginalProcessedTypeRef(newFPar.getDeclaredTypeRefNode(), typeRef);
		}
		return newConstructor;
	}

	private N4MethodDeclaration addOverwritingStaticMethod(TMethod method, N4ClassDeclaration clazz) {
		N4ClassDeclaration clazzOrig = (N4ClassDeclaration) getState().tracer.getOriginalASTNode(clazz);
		N4MethodDeclaration newMethod = _N4MethodDecl(method.getName());
		newMethod.getDeclaredModifiers().add(N4Modifier.STATIC);
		clazz.getOwnedMembersRaw().add(newMethod);
		for (TFormalParameter fpar : method.getFpars()) {
			FormalParameter newFPar = _FormalParameter(fpar.getName());
			newMethod.getFpars().add(newFPar);
			TypeRef typeRef = fpar.getTypeRef();
			newFPar.setDeclaredTypeRefNode(_TypeReferenceNode(getState(), typeRef));
			getState().info.setOriginalProcessedTypeRef(newFPar.getDeclaredTypeRefNode(), typeRef);
		}

		Preconditions.checkState(method.getReturnTypeRef() instanceof ThisTypeRef);

		TypeRef thisTypeRef = TypeUtils.createTypeRef(clazzOrig.getDefinedType(), clazzOrig.getTypingStrategy(), true);
		TypeRef typeRef = getConcreteTypeRef(method.getReturnTypeRef(), thisTypeRef);
		newMethod.setDeclaredReturnTypeRefNode(_TypeReferenceNode(getState(), typeRef));
		getState().info.setOriginalProcessedTypeRef(newMethod.getDeclaredReturnTypeRefNode(), typeRef);

		return newMethod;
	}

	private TypeRef getConcreteTypeRef(TypeRef typeRef, TypeRef thisTypeRef) {
		RuleEnvironment localG = wrap(getState().G);
		setThisBinding(localG, thisTypeRef);
		var concreteTypeRef = ts.substTypeVariables(localG, typeRef);
		concreteTypeRef = ts.upperBoundWithReopen(getState().G, concreteTypeRef);
		return concreteTypeRef;
	}
}
