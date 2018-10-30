/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xsemantics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.xtext.util.PolymorphicDispatcher;

import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrayPadding;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.CatchVariable;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.LocalArgumentsVariable;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NewTarget;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xsemantics.runtime.RuleApplicationTrace;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xsemantics.runtime.internal.PatchedPolymorphicDispatcher;

/**
 * This class is solely used to analyze and demonstrate a bug in Xtext's PolymorphicDispatcher.
 *
 * @see https://github.com/eclipse/xtext-core/issues/238
 */
@SuppressWarnings({ "javadoc", "restriction", "unused" })
public class TestPolymorphicDispatcher extends PolymorphicDispatcher<Object> {

	/**
	 * Main method, uncomment the different method calls in the loop to run different test scenarios.
	 */
	public static void main(String[] args) {
		TestPolymorphicDispatcher test = new TestPolymorphicDispatcher();
		while (true) {
			// test.shuffleAndSortUsingTimSortWithOriginalCompare();
			// test.shuffleAndSortUsingTimSortWithPatchedCompare();
			// test.shuffleAndSortUsingTreeSetWithOriginalCompare();
			test.shuffleAndSortUsingTreeSetWithPatchedCompare();
		}
	}

	private void shuffleAndSortUsingTimSortWithOriginalCompare() {
		shuffleAndSortUsingTimSort(originalCompare());
	}

	private void shuffleAndSortUsingTimSortWithPatchedCompare() {
		shuffleAndSortUsingTimSort(patchedCompare());
	}

	private void shuffleAndSortUsingTreeSetWithOriginalCompare() {
		shuffleAndSortUsingTreeSet(originalCompare());
	}

	private void shuffleAndSortUsingTreeSetWithPatchedCompare() {
		shuffleAndSortUsingTreeSet(patchedCompare());
	}

	private void shuffleAndSortUsingTimSort(Comparator<MethodDesc> compare) {
		List<MethodDesc> copy = new ArrayList<>(methods);
		Collections.shuffle(copy);
		Collections.sort(copy, compare);
	}

	private void shuffleAndSortUsingTreeSet(Comparator<MethodDesc> compare) {
		SortedSet<MethodDesc> cachedDescriptors = new TreeSet<>(compare);

		List<MethodDesc> copy = new ArrayList<>(methods);
		Collections.shuffle(copy);
		cachedDescriptors.addAll(copy);

		if (cachedDescriptors.size() != methods.size()) {
			StringBuilder msg = new StringBuilder();
			msg.append("TreeSet swallowed ").append(methods.size() - cachedDescriptors.size()).append(" methods");
			throw new RuntimeException(msg.toString());
		}
	}

	/**
	 * Returns a comparator that compares method descriptors using {@link PolymorphicDispatcher#compare}.
	 */
	private Comparator<MethodDesc> originalCompare() {
		return new Comparator<MethodDesc>() {
			@Override
			public int compare(MethodDesc o1,
					MethodDesc o2) {
				final List<Class<?>> paramTypes1 = Arrays.asList(o1.getParameterTypes());
				final List<Class<?>> paramTypes2 = Arrays.asList(o2.getParameterTypes());

				// sort by number of parameters
				if (paramTypes1.size() > paramTypes2.size())
					return 1;
				if (paramTypes2.size() > paramTypes1.size())
					return -1;

				// sort by parameter types from left to right
				for (int i = 0; i < paramTypes1.size(); i++) {
					final Class<?> class1 = paramTypes1.get(i);
					final Class<?> class2 = paramTypes2.get(i);

					if (class1.equals(class2))
						continue;
					if (class1.isAssignableFrom(class2) || Void.class.equals(class2))
						return -1;
					if (class2.isAssignableFrom(class1) || Void.class.equals(class1))
						return 1;
				}

				// sort by declaring class (more specific comes first).
				if (!o1.getDeclaringClass().equals(o2.getDeclaringClass())) {
					if (o1.getDeclaringClass().isAssignableFrom(o2.getDeclaringClass()))
						return 1;
					if (o2.getDeclaringClass().isAssignableFrom(o1.getDeclaringClass()))
						return -1;
				}

				// sort by target skipped since we only have one target
				return 0;

				// final int compareTo = ((Integer) targets.indexOf(o2.target)).compareTo(targets.indexOf(o1.target));
				// return compareTo;
			}
		};
	}

	/**
	 * Returns a comparator that compares method descriptors using {@link PatchedPolymorphicDispatcher#compare}.
	 */
	private Comparator<MethodDesc> patchedCompare() {
		return new Comparator<MethodDesc>() {
			private final Comparator<MethodDesc> orig = originalCompare();

			@Override
			public int compare(MethodDesc o1, MethodDesc o2) {
				int compare = orig.compare(o1, o2);
				if (compare != 0) {
					return compare;
				}
				Class<?>[] p1 = o1.getParameterTypes();
				Class<?>[] p2 = o2.getParameterTypes();
				int to = Math.min(p1.length, p2.length);

				for (int i = 0; i < to; i++) {
					final String n1 = p1[i].getName();
					final String n2 = p2[i].getName();
					compare = n1.compareTo(n2);
					if (compare != 0) {
						return compare;
					}
				}
				return compare;
			}
		};
	}

	/*
	 * ===============================================================================================================
	 *
	 * Constructor and initialization of test data.
	 *
	 * ===============================================================================================================
	 */

	private final List<MethodDesc> methods;

	/**
	 * Creates a new instance and initializes the list of methods under consideration.
	 */
	public TestPolymorphicDispatcher() {
		super(Collections.singletonList(new Object()), method -> false);

		this.methods = getMethods();
	}

	private List<MethodDesc> getMethods() {
		List<MethodDesc> result = new ArrayList<>();

		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, AdditiveExpression.class)));
		result.add(createMethodDesc(this,
				findMethod(getClass(), "typeImpl", RuleEnvironment.class, RuleApplicationTrace.class, Argument.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ArrayElement.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ArrayLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ArrayPadding.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, AssignmentExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, AwaitExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, BinaryBitwiseExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, BinaryLogicalExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, BooleanLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, CastExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, CatchVariable.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, CommaExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ConditionalExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, EqualityExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, Expression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, FormalParameter.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, FunctionExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, GetterDeclaration.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, IdentifierRef.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, IndexedAccessExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, LocalArgumentsVariable.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ModuleNamespaceVirtualType.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, MultiplicativeExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, N4ClassExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, N4EnumLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, N4FieldDeclaration.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, NewExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, NewTarget.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, NullLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, NumericLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ObjectLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ParameterizedCallExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ParameterizedPropertyAccessExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ParenExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, PostfixExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, PromisifyExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, PropertyNameValuePair.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, RegularExpressionLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, RelationalExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, SetterDeclaration.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ShiftExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, StringLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, SuperLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, TaggedTemplateString.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, TemplateLiteral.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, TemplateSegment.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, TEnumLiteral.class)));
		result.add(createMethodDesc(this,
				findMethod(getClass(), "typeImpl", RuleEnvironment.class, RuleApplicationTrace.class, TField.class)));
		result.add(createMethodDesc(this,
				findMethod(getClass(), "typeImpl", RuleEnvironment.class, RuleApplicationTrace.class, TGetter.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, ThisLiteral.class)));
		result.add(createMethodDesc(this,
				findMethod(getClass(), "typeImpl", RuleEnvironment.class, RuleApplicationTrace.class, TSetter.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, TVariable.class)));
		result.add(createMethodDesc(this,
				findMethod(getClass(), "typeImpl", RuleEnvironment.class, RuleApplicationTrace.class, Type.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, TypeDefiningElement.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, UnaryExpression.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, VariableDeclaration.class)));
		result.add(createMethodDesc(this, findMethod(getClass(), "typeImpl", RuleEnvironment.class,
				RuleApplicationTrace.class, YieldExpression.class)));

		return result;
	}

	private Method findMethod(Class<?> current, String name, Class<?>... parameterTypes) {
		if (current == Object.class)
			throw new IllegalArgumentException("Cannot find method");
		try {
			return current.getDeclaredMethod(name, parameterTypes);
		} catch (java.lang.NoSuchMethodException | SecurityException e) {
			return findMethod(current.getSuperclass(), name, parameterTypes);
		}
	}

	/*
	 * ===============================================================================================================
	 *
	 * Test methods with signatures copied from N4JS type system.
	 *
	 * ===============================================================================================================
	 */

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, AdditiveExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, Argument x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ArrayElement x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ArrayLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ArrayPadding x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, AssignmentExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, AwaitExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, BinaryBitwiseExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, BinaryLogicalExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, BooleanLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, CastExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, CatchVariable x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, CommaExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ConditionalExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, EqualityExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, Expression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, FormalParameter x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, FunctionExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, GetterDeclaration x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, IdentifierRef x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, IndexedAccessExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, LocalArgumentsVariable x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ModuleNamespaceVirtualType x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, MultiplicativeExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, N4ClassExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, N4EnumLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, N4FieldDeclaration x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, NewExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, NewTarget x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, NullLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, NumericLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ObjectLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ParameterizedCallExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ParameterizedPropertyAccessExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ParenExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, PostfixExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, PromisifyExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, PropertyNameValuePair x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, RegularExpressionLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, RelationalExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, SetterDeclaration x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ShiftExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, StringLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, SuperLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, TaggedTemplateString x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, TemplateLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, TemplateSegment x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, TEnumLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, TField x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, TGetter x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, ThisLiteral x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, TSetter x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, TVariable x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, Type x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, TypeDefiningElement x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, UnaryExpression x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, VariableDeclaration x) {
		// empty
	}

	public void typeImpl(RuleEnvironment e, RuleApplicationTrace t, YieldExpression x) {
		// empty
	}
}
