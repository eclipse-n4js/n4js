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
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import org.eclipse.xsemantics.runtime.RuleEnvironment
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.InferenceVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.ts.utils.TypeCompareUtils
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.constraints.InferenceContext
import org.eclipse.n4js.typesystem.constraints.TypeConstraint
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.util.CancelIndicator
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public abstract class AbstractInferenceContextTest extends AbstractTypeSystemHelperTests {

	@Inject private N4JSTypeSystem ts;
	@Inject private TypeSystemHelper tsh;
	@Inject private OperationCanceledManager operationCanceledManager;

	protected static val DEFAULT_CODE = '''

		interface I {}
		interface I1 {}
		interface I2 {}
		interface I3 {}

		interface IA extends I {}
		interface IB extends I {}
		interface IC extends I {}

		interface IG<T> {}
		interface IH<T> {}

		class A implements IA, I, I1, I2, I3 {}
		class B extends A implements IB {}
		class B1 extends A implements IC {}
		class B2 extends A implements IC {}
		class B3 extends A implements IC {}
		class C extends B implements IC {}
		class C1 extends B implements IC {}
		class C2 extends B implements IC {}
		class C3 extends B implements IC {}

		class X {}
		class Y {}
		class Z {}

		class G<T> {}
		class Gsub<T> extends G<T> {}
		class G2<T,S> {}
		class H<T> {}

		function <T> foo(p1: Array<T>, p2: G<T> ) {}

		var a: A;
		var b: B;
		var b1: B1;
		var b2: B2;
		var b3: B3;
		var c: C;
		var c1: C1;
		var c2: C2;
		var c3: C3;
		var ga: G<A>;

	''';


	// all the following variables will be set in the @Before method!

	protected var Script script;
	protected var RuleEnvironment _G;

	protected var Type I;
	protected var Type I1;
	protected var Type I2;
	protected var Type I3;
	protected var Type IA;
	protected var Type IB;
	protected var Type IC;
	protected var Type IG;
	protected var Type IH;
	protected var Type A;
	protected var Type B;
	protected var Type B1;
	protected var Type B2;
	protected var Type B3;
	protected var Type C;
	protected var Type C1;
	protected var Type C2;
	protected var Type C3;
	protected var Type X;
	protected var Type Y;
	protected var Type Z;
	protected var Type G;
	protected var Type Gsub;
	protected var Type G2;
	protected var Type H;

	protected var InferenceVariable alpha;
	protected var InferenceVariable beta;
	protected var InferenceVariable gamma;
	protected var InferenceVariable delta;
	protected var InferenceVariable epsilon;


	/** The N4JS code used as a basis for the tests. Subclasses may override. */
	def protected String getCode() {
		DEFAULT_CODE
	}

	@Before
	def void before() {
		script = assembler.setupScript(getCode(), JavaScriptVariant.n4js, 0);
		//val expr = script.eAllContents.filter(ParameterizedCallExpression).head;

		_G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		I = selectType("I");
		I1 = selectType("I1");
		I2 = selectType("I2");
		I3 = selectType("I3");
		IA = selectType("IA");
		IB = selectType("IB");
		IC = selectType("IC");
		IG = selectType("IG");
		IH = selectType("IH");
		A = selectType("A");
		B = selectType("B");
		B1 = selectType("B1");
		B2 = selectType("B2");
		B3 = selectType("B3");
		C = selectType("C");
		C1 = selectType("C1");
		C2 = selectType("C2");
		C3 = selectType("C3");
		X = selectType("X");
		Y = selectType("Y");
		Z = selectType("Z");
		G = selectType("G");
		Gsub = selectType("Gsub");
		G2 = selectType("G2");
		H = selectType("H");

		alpha = createInfVar("\u03B1"); // "α"
		beta = createInfVar("\u03B2");
		gamma = createInfVar("\u03B3");
		delta = createInfVar("\u03B4");
		epsilon = createInfVar("\u03B5");
	}



	protected def Type selectType(String arg) {
		val result = script.module.topLevelTypes.findFirst[name==arg];
		Assert.assertNotNull(result);
		return result;
	}

	protected def TypeVariable createTypeVar(String name) {
		val result = TypesFactory.eINSTANCE.createTypeVariable();
		result.name = name;
		return result;
	}
	protected def InferenceVariable createInfVar(String name) {
		val result = TypesFactory.eINSTANCE.createInferenceVariable();
		result.name = name;
		return result;
	}

	def protected void assertSolution(Script script, TypeConstraint[] constraints, Pair<InferenceVariable,TypeRef>... expectedInstantiations) {
		if(expectedInstantiations===null || expectedInstantiations.empty) {
			throw new IllegalArgumentException("should provide one or more expected instantiations to #assertNoSolution()");
		}

		val solution = getSolutionFromInferenceContext(script, constraints, expectedInstantiations);
		Assert.assertNotNull("expected a solution, but InferenceContext reported \"no solution found\"", solution);

		expectedInstantiations.forEach[p|
			val infVar = p.key;
			val expected = p.value;
			if(expected!==null) {
				val actual = solution.get(infVar);
				Assert.assertNotNull("incomplete solution; instantiation of inference variable "+infVar.name+" is null", actual);
				assertEqualTypeArgument(expected,actual);
			}
		]
	}
	def protected void assertNoSolution(Script script, TypeConstraint[] constraints, InferenceVariable... inferenceVariables) {
		if(inferenceVariables===null || inferenceVariables.empty) {
			throw new IllegalArgumentException("should provide one or more inference variables to #assertNoSolution()");
		}
		val solution = getSolutionFromInferenceContext(script, constraints, inferenceVariables.map[it->null]);
		Assert.assertNull("expected no solution, but InferenceContext found a solution", solution);
	}
	def private Map<InferenceVariable,TypeRef> getSolutionFromInferenceContext(
		Script script, TypeConstraint[] constraints, Pair<InferenceVariable,TypeRef>... expectedInstantiations) {
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script)
		val infVars = expectedInstantiations.map[key].toSet
		val InferenceContext infCtx = new InferenceContext(ts,tsh,operationCanceledManager,CancelIndicator.NullImpl,G,infVars)
		constraints.forEach[infCtx.addConstraint(left,right,variance)]

		val solution = infCtx.solve
		return solution;
	}

	def protected void assertEqualTypeArgument(TypeArgument expected, TypeArgument actual) {
		if(expected!==null) {
			Assert.assertNotNull("expected TypeArgument was non-null, actual was null",actual);
			Assert.assertTrue(
				'''
				semi-semantic equality check failed for:
				    expected: «expected?.typeRefAsString»
				    actual  : «actual?.typeRefAsString»
				''',
				TypeCompareUtils.isEqual(expected,actual));
//			Assert.assertSame("same EClass",expected.eClass,actual.eClass);
//			if(expected instanceof TypeRef) {
//				Assert.assertSame("same declared type",expected.declaredType,(actual as TypeRef).declaredType);
//			}
//			if(expected instanceof ComposedTypeRef) {
//				val actualCasted = actual as ComposedTypeRef;
//				Assert.assertEquals("different number of types in ComposedTypeRef",expected.typeRefs.size,actualCasted.typeRefs.size);
//				for(var idx=0;idx<expected.typeRefs.size;idx++) {
//					assertEqualTypeArgument(expected.typeRefs.get(idx),actualCasted.typeRefs.get(idx));
//				}
//			}
//			// TODO more assertions
		}
		else {
			Assert.assertNull("expected TypeArgument was null, actual was non-null",actual);
		}
	}

	def protected static TypeConstraint constraint(EObject left, String relation, EObject right) {
		val TypeArgument leftTypeArg = switch(left) {
			TypeArgument: left
			Type: TypeUtils.createTypeRef(left)
			default: throw new IllegalArgumentException("unsupported type of 'left': "+left.eClass.name)
		}
		val TypeArgument rightTypeArg = switch(right) {
			TypeArgument: right
			Type: TypeUtils.createTypeRef(right)
			default: throw new IllegalArgumentException("unsupported type of 'left': "+left.eClass.name)
		}
		val Variance variance = switch(relation) {
			case "<:": Variance.CO
			case ":>": Variance.CONTRA
			case "=": Variance.INV
			default: throw new IllegalArgumentException("unknown relation string: "+relation)
		};
		return new TypeConstraint(leftTypeArg,rightTypeArg,variance);
	}


	def protected Type any() {
		return _G.anyType;
	}


	// for readability of test cases we enforce using different TypeRef creation methods for generic and non-generic types:
	def protected static TypeRef ref(Type type) {
		if(type.generic)
			throw new IllegalArgumentException("type may not be generic; use methods #of() to create TypeRefs for generic types");
		return TypeUtils.createTypeRef(type);
	}
	def protected static TypeRef rawTypeRef(Type type) {
		if(!type.generic)
			throw new IllegalArgumentException("type must generic; use method #ref() to create TypeRefs for non-generic types");
		return TypeUtils.createTypeRef(type);
	}
	def protected static TypeRef of(Type type, Type... types) {
		return of(type,types.map[ref]);
	}
	def protected static TypeRef of(Type type, TypeArgument... typeArgs) {
		if(!type.generic)
			throw new IllegalArgumentException("type must generic; use method #ref() to create TypeRefs for non-generic types");
		if(type.typeVars.size !== typeArgs.size)
			throw new IllegalArgumentException("incorrect number of type arguments provided; required: "+type.typeVars.size+", got: "+typeArgs.size);
		return TypeUtils.createTypeRef(type,typeArgs);
	}


	def protected static UnionTypeExpression union(Type... types) {
		return TypeUtils.createNonSimplifiedUnionType(types.map[it.ref]);
	}
	def protected static IntersectionTypeExpression intersection(Type... types) {
		return TypeUtils.createNonSimplifiedIntersectionType(types.map[it.ref]);
	}
	def protected static UnionTypeExpression union(TypeRef... typeRefs) {
		return TypeUtils.createNonSimplifiedUnionType(typeRefs);
	}
	def protected static IntersectionTypeExpression intersection(TypeRef... typeRefs) {
		return TypeUtils.createNonSimplifiedIntersectionType(typeRefs);
	}
	def protected Wildcard wildcard() {
		return TypeRefsFactory.eINSTANCE.createWildcard;
	}
	def protected Wildcard wildcardExtends(Type type) {
		val w = TypeRefsFactory.eINSTANCE.createWildcard;
		w.declaredUpperBound = type.ref;
		return w;
	}
	def protected Wildcard wildcardExtends(TypeRef typeRef) {
		val w = TypeRefsFactory.eINSTANCE.createWildcard;
		w.declaredUpperBound = typeRef;
		return w;
	}
	def protected Wildcard wildcardSuper(Type type) {
		val w = TypeRefsFactory.eINSTANCE.createWildcard;
		w.declaredLowerBound = type.ref;
		return w;
	}
	def protected Wildcard wildcardSuper(TypeRef typeRef) {
		val w = TypeRefsFactory.eINSTANCE.createWildcard;
		w.declaredLowerBound = typeRef;
		return w;
	}
	def protected FunctionTypeExpression functionType(Type returnType, Type... fparTypes) {
		return functionType(#[], returnType, fparTypes);
	}
	def protected FunctionTypeExpression functionType(TypeRef returnTypeRef, TypeRef... fparTypeRefs) {
		return functionType(#[], returnTypeRef, fparTypeRefs);
	}
	def protected FunctionTypeExpression functionType(TypeVariable[] typeVars, Type returnType, Type... fparTypes) {
		return functionType(typeVars, returnType.ref, fparTypes.map[ref]);
	}
	def protected FunctionTypeExpression functionType(TypeVariable[] typeVars, TypeRef returnTypeRef, TypeRef... fparTypeRefs) {
		val fpars = fparTypeRefs.map[typeRef|
			val fpar = TypesFactory.eINSTANCE.createTFormalParameter;
			fpar.typeRef = typeRef;
			return fpar;
		];
		return TypeUtils.createFunctionTypeExpression(null, typeVars, fpars, returnTypeRef);
	}
}
