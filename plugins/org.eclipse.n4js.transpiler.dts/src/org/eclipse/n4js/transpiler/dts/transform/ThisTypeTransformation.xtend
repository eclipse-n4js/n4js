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
package org.eclipse.n4js.transpiler.dts.transform

import com.google.common.base.Preconditions
import com.google.inject.Inject
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * This transformation replaces some ThisTypeRefs by concrete type references
 * iff they occur in static methods or constructors. This is true for the following cases:
 * <ul>
 * <li/>replace spec parameter (this-type) of constructors by concrete type
 * <li/>add (missing) spec constructor iff superclass defines one 
 * <li/>replace return this-type of static methods by concrete type
 * <li/>add method override for all static methods returning this-types to replace return type by concrete type
 * </ul>
 */
class ThisTypeTransformation extends Transformation {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private TypeSystemHelper tsh;


	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		transformThisTypeRefs();
	}

	def private void transformThisTypeRefs() {
		val classes = collectNodes(state.im, true, N4ClassDeclaration);
		
		for (clazz : classes) {
			val clazzOrig = state.tracer.getOriginalASTNode(clazz) as N4ClassDeclaration;
			
			val methods = collectNodes(clazz, false, N4MethodDeclaration);
			val methodNamesCurClass = new HashSet();
			var N4MethodDeclaration ctor = null;
			for (method : methods) {
				if (method.isConstructor) {
					ctor = method;
				} else if (method.isStatic) {
					methodNamesCurClass += method.name;
					// special handling for return type 'this' of static methods
					val retTR = state.info.getOriginalProcessedTypeRef(method.declaredReturnTypeRefNode);
					if (retTR instanceof ThisTypeRef) {
						val methodOrig = state.tracer.getOriginalASTNodeOfSameType(method, false);
						var typeRef = tsh.bindAndSubstituteThisTypeRef(state.G, methodOrig, retTR);
						typeRef = ts.upperBoundWithReopen(state.G, typeRef);
						if (typeRef.generic) {
							typeRef = TypeUtils.copyIfContained(typeRef);
							val newTypeArgsCount = typeRef.declaredType.typeVars.size;
							typeRef.declaredTypeArgs.clear; // only 'any' allowed here by TypeScript
							for (var i = 0; i < newTypeArgsCount; i++) {
								typeRef.declaredTypeArgs.add(TypeRefsFactory.eINSTANCE.createWildcard);
							}
						}
						state.info.setOriginalProcessedTypeRef(method.declaredReturnTypeRefNode, typeRef);
					}
				}
			}
			
			if (ctor !== null) {
				// special handling for constructor parameter @Spec ~i~this 
				val specFPar = getSpecFPar(ctor);
				if (specFPar !== null) {
					val specFParOrig = state.tracer.getOriginalASTNodeOfSameType(specFPar, false);
					if (specFParOrig !== null) {
						var typeRef = state.info.getOriginalProcessedTypeRef(specFPar.declaredTypeRefNode);
						typeRef = tsh.bindAndSubstituteThisTypeRef(state.G, specFParOrig, typeRef);
						typeRef = ts.upperBoundWithReopen(state.G, typeRef);
						state.info.setOriginalProcessedTypeRef(specFPar.declaredTypeRefNode, typeRef);
					}
				}

			} else {
				// search spec constructor in superclass and put its signature here and replace return 'this' type by actual type
				val specConstructorOrig = findSpecConstructorInSuperclass(clazzOrig);
				if (specConstructorOrig !== null) {
					addOverwritingSpecConstructor(specConstructorOrig, clazz);
				}
			}
			
			val staticMethodsReturingThisInSuperclasses = findStaticMethodsReturingThisInSuperclasses(clazzOrig);
			for (method : staticMethodsReturingThisInSuperclasses) {
				if (!methodNamesCurClass.contains(method.name)) {
					// put signature here and replace return 'this' type by actual type
					addOverwritingStaticMethod(method, clazz);
				}
			}
		}
	}
	
	def TMethod[] findStaticMethodsReturingThisInSuperclasses(N4ClassDeclaration clazzOrig) {
		val name2method = new HashMap<String, TMethod>();
		var curClass = clazzOrig.definedTypeAsClass;
		while (curClass.superClass !== null) {
			curClass = curClass.superClass;
			for (member : curClass.ownedMembers) {
				if (member instanceof TMethod) {
					if (member.isStatic && member.returnTypeRef instanceof ThisTypeRef) {
						name2method.putIfAbsent(member.name, member);
					}
				}
			}
		}

		return name2method.values;
	}
	
	def TMethod findSpecConstructorInSuperclass(N4ClassDeclaration clazzOrig) {
		var curClass = clazzOrig.definedTypeAsClass;
		do {
			if (curClass.superClass === null) {
				return null;
			}
			curClass = curClass.superClass;
			val ctor = curClass.ownedCtor
			
			if (isSpecConstructor(ctor)) {
				return ctor;
			} else if (ctor !== null) {
				return null;
			}
		} while (true);
	}
	
	def N4MethodDeclaration getConstructor(N4ClassDeclaration clazz) {
		val methods = clazz.ownedMethods;
		for (method : methods) {
			if (method.isConstructor) {
				return method;
			}
		}
		return null;
	}
	
	def FormalParameter getSpecFPar(N4MethodDeclaration method) {
		if (method === null) {
			return null;
		}
		if (!method.isConstructor) {
			return null;
		}
		if (method.fpars.empty) {
			return null;
		}
		for (fpar : method.fpars) {
			if (AnnotationDefinition.SPEC.hasAnnotation(fpar)) {
				return fpar;
			}
		}
		return null;
	}
	
	def boolean isSpecConstructor(TMethod method) {
		if (method === null) {
			return false;
		}
		if (!method.isConstructor) {
			return false;
		}
		if (method.fpars.empty) {
			return false;
		}
		for (fpar : method.fpars) {
			if (AnnotationDefinition.SPEC.hasAnnotation(fpar)) {
				return true;
			}
		}
		return false;
	}

	def N4MethodDeclaration addOverwritingSpecConstructor(TMethod constructor, N4ClassDeclaration clazz) {
		val clazzOrig = state.tracer.getOriginalASTNode(clazz) as N4ClassDeclaration;
		val newConstructor = _N4MethodDecl("constructor", new ArrayList());
		clazz.ownedMembersRaw += newConstructor;
		for (fpar: constructor.fpars) {
			val newFPar = _FormalParameter(fpar.name);
			newConstructor.fpars += newFPar;
			var typeRef = fpar.typeRef;
			if (AnnotationDefinition.SPEC.hasAnnotation(fpar)) {
				val thisTypeRef = TypeUtils.createTypeRefWithParamsAsArgs(clazzOrig.definedType);
				typeRef = getConcreteTypeRef(fpar.typeRef, thisTypeRef);
			}
			
			newFPar.declaredTypeRefNode = _TypeReferenceNode(state, typeRef);
			state.info.setOriginalProcessedTypeRef(newFPar.declaredTypeRefNode, typeRef);
		}
		return newConstructor;
	}

	def N4MethodDeclaration addOverwritingStaticMethod(TMethod method, N4ClassDeclaration clazz) {
		val clazzOrig = state.tracer.getOriginalASTNode(clazz) as N4ClassDeclaration;
		val newMethod = _N4MethodDecl(method.name);
		newMethod.declaredModifiers += N4Modifier.STATIC;
		clazz.ownedMembersRaw += newMethod;
		for (fpar: method.fpars) {
			val newFPar = _FormalParameter(fpar.name);
			newMethod.fpars += newFPar;
			var typeRef = fpar.typeRef;
			newFPar.declaredTypeRefNode = _TypeReferenceNode(state, typeRef);
			state.info.setOriginalProcessedTypeRef(newFPar.declaredTypeRefNode, typeRef);
		}
		

		Preconditions.checkState(method.returnTypeRef instanceof ThisTypeRef);
		
		val thisTypeRef = TypeUtils.createTypeRef(clazzOrig.definedType, clazzOrig.typingStrategy, true);
		val typeRef = getConcreteTypeRef(method.returnTypeRef, thisTypeRef);
		newMethod.declaredReturnTypeRefNode = _TypeReferenceNode(state, typeRef);
		state.info.setOriginalProcessedTypeRef(newMethod.declaredReturnTypeRefNode, typeRef);
		
		return newMethod;
	}
	
	def private TypeRef getConcreteTypeRef(TypeRef typeRef, TypeRef thisTypeRef) {
		val localG = state.G.wrap;
		setThisBinding(localG, thisTypeRef);
		var concreteTypeRef = ts.substTypeVariables(localG, typeRef);
		concreteTypeRef = ts.upperBoundWithReopen(state.G, concreteTypeRef);
		return concreteTypeRef;
	}
}
