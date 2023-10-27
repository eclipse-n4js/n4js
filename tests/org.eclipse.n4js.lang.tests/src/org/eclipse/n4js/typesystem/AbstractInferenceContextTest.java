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
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.bottomType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.topType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedType;
import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sort;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeCompareUtils;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@SuppressWarnings({ "unchecked", "hiding" })
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public abstract class AbstractInferenceContextTest extends AbstractTypesystemTest {

	@Inject
	private DeclMergingHelper declMergingHelper;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	protected static String DEFAULT_CODE = """

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

			""";

	// all the following variables will be set in the @Before method!

	protected Script script;
	protected RuleEnvironment _G;

	protected Type I;
	protected Type I1;
	protected Type I2;
	protected Type I3;
	protected Type IA;
	protected Type IB;
	protected Type IC;
	protected Type IG;
	protected Type IH;
	protected Type A;
	protected Type B;
	protected Type B1;
	protected Type B2;
	protected Type B3;
	protected Type C;
	protected Type C1;
	protected Type C2;
	protected Type C3;
	protected Type X;
	protected Type Y;
	protected Type Z;
	protected Type G;
	protected Type Gsub;
	protected Type G2;
	protected Type H;

	protected InferenceVariable alpha;
	protected InferenceVariable beta;
	protected InferenceVariable gamma;
	protected InferenceVariable delta;
	protected InferenceVariable epsilon;

	/** The N4JS code used as a basis for the tests. Subclasses may override. */
	protected String getCode() {
		return DEFAULT_CODE;
	}

	@Before
	public void before() {
		script = createScript(JavaScriptVariant.n4js, getCode());
		assertNoValidationErrors(script);

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

	protected Type selectType(String arg) {
		Type result = findFirst(script.getModule().getTypes(), t -> Objects.equals(t.getName(), arg));
		Assert.assertNotNull(result);
		return result;
	}

	protected TypeVariable createTypeVar(String name) {
		TypeVariable result = TypesFactory.eINSTANCE.createTypeVariable();
		result.setName(name);
		return result;
	}

	protected InferenceVariable createInfVar(String name) {
		InferenceVariable result = TypesFactory.eINSTANCE.createInferenceVariable();
		result.setName(name);
		return result;
	}

	/**
	 * Asserts that {@link InferenceContext} finds a solution for the given constraint system matching the given
	 * expected instantiations.
	 */
	protected void assertSolution(Script script, TypeConstraint[] constraints,
			Pair<InferenceVariable, TypeRef>... expectedInstantiations) {

		if (expectedInstantiations == null || expectedInstantiations.length == 0) {
			throw new IllegalArgumentException(
					"should provide one or more expected instantiations to #assertSolution()");
		}

		Map<InferenceVariable, TypeRef> solution = getSolutionFromInferenceContext(script, constraints,
				expectedInstantiations);

		if (solution == null) {
			Assert.fail("""
					expected a solution, but InferenceContext reported "no solution found"
					""" +
					asString(constraints));
			return;
		}

		boolean haveIncorrect = false;
		java.util.List<String> instantiationsAsString = new ArrayList<>();
		for (Pair<InferenceVariable, TypeRef> p : expectedInstantiations) {
			InferenceVariable infVar = p.getKey();
			TypeRef expected = p.getValue();
			if (expected != null) {
				TypeRef actual = solution.get(infVar);
				if (actual == null) {
					instantiationsAsString.add("    " + infVar.getName()
							+ " -> null (INCOMPLETE, instantiation of this inference variable)");
					haveIncorrect = true;
				} else if (!TypeCompareUtils.isEqual(expected, actual)) {
					instantiationsAsString.add("    " + infVar.getName() + " -> " + actual.getTypeRefAsString()
							+ " (INCORRECT, expected: " + expected.getTypeRefAsString() + ")");
					haveIncorrect = true;
				} else {
					instantiationsAsString
							.add("    " + infVar.getName() + " -> " + actual.getTypeRefAsString() + " (correct)");
				}
			}
		}

		if (haveIncorrect) {
			Assert.fail("""
					incorrect solution found by InferenceContext
					%s
					solution:
					%s
					""".formatted(asString(constraints), join(System.lineSeparator(), sort(instantiationsAsString))));
		}
	}

	/**
	 * Asserts that {@link InferenceContext} does *not* find a solution for the given constraint system.
	 */
	protected void assertNoSolution(Script script, TypeConstraint[] constraints,
			InferenceVariable... inferenceVariables) {

		if (inferenceVariables == null || inferenceVariables.length == 0) {
			throw new IllegalArgumentException("should provide one or more inference variables to #assertNoSolution()");
		}
		Map<InferenceVariable, TypeRef> solution = getSolutionFromInferenceContext(script, constraints,
				toList(map(Arrays.asList(inferenceVariables), iv -> Pair.of(iv, null))).toArray(new Pair[0]));

		if (solution != null) {
			Assert.fail("""
					expected no solution, but InferenceContext found a solution
					«constraints.asString»
					solution:
					«»
					""".formatted(asString(constraints),
					join(System.lineSeparator(), sort(
							map(solution.entrySet(), e -> "    " + e.getKey().getName() + " -> "
									+ e.getValue().getTypeRefAsString())))));
		}
	}

	private Map<InferenceVariable, TypeRef> getSolutionFromInferenceContext(Script script, TypeConstraint[] constraints,
			Pair<InferenceVariable, TypeRef>... expectedInstantiations) {

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		Set<InferenceVariable> infVars = toSet(map(Arrays.asList(expectedInstantiations), Pair::getKey));
		InferenceContext infCtx = new InferenceContext(ts, tsh, declMergingHelper, operationCanceledManager,
				CancelIndicator.NullImpl, G, infVars.toArray(new InferenceVariable[0]));

		for (TypeConstraint c : constraints) {
			infCtx.addConstraint(c.left, c.right, c.variance);
		}

		Map<InferenceVariable, TypeRef> solution = infCtx.solve();
		return solution;
	}

	private String asString(TypeConstraint[] constraints) {
		java.util.List<String> lines = new ArrayList<>();
		lines.add("constraints:");
		lines.addAll(toList(map(Arrays.asList(constraints), c -> "    " + c.toString())));
		return join(System.lineSeparator(), lines);
	}

	protected static TypeConstraint constraint(EObject left, String relation, EObject right) {
		TypeArgument leftTypeArg = null;
		TypeArgument rightTypeArg = null;
		Variance variance = null;
		if (left instanceof TypeArgument) {
			leftTypeArg = (TypeArgument) left;
		} else if (left instanceof Type) {
			leftTypeArg = TypeUtils.createTypeRef((Type) left);
		} else {
			throw new IllegalArgumentException("unsupported type of 'left': " + left.eClass().getName());
		}

		if (right instanceof TypeArgument) {
			rightTypeArg = (TypeArgument) right;
		} else if (right instanceof Type) {
			rightTypeArg = TypeUtils.createTypeRef((Type) right);
		} else {
			throw new IllegalArgumentException("unsupported type of 'left': " + right.eClass().getName());
		}

		switch (relation) {
		case "<:":
			variance = Variance.CO;
			break;
		case ":>":
			variance = Variance.CONTRA;
			break;
		case "=":
			variance = Variance.INV;
			break;
		default:
			throw new IllegalArgumentException("unknown relation string: " + relation);
		}

		return new TypeConstraint(leftTypeArg, rightTypeArg, variance);
	}

	protected Type top() {
		return topType(_G);
	}

	protected Type bottom() {
		return bottomType(_G);
	}

	protected Type any() {
		return anyType(_G);
	}

	protected Type undefined() {
		return undefinedType(_G);
	}
}
