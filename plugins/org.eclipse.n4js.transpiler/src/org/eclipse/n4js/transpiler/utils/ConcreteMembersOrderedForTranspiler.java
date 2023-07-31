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
package org.eclipse.n4js.transpiler.utils;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.util.AccessorTuple;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.ts.types.util.NameStaticPair;

/**
 */
public class ConcreteMembersOrderedForTranspiler {

	/* package */ final List<TMember> concreteInheritedMembers;
	/** members, maybe defined in interfaces mixed in */
	public final List<TMember> ownedAndMixedInConcreteMembers;
	/** members from shapes in the type hierarchy. Includes transitive members. */
	public final Set<TMember> inlinedMembersFromShapes;
	/** accessors not overridden by fields */
	public final List<AccessorTuple> concreteAccessorTuples;
	/** fields overriding accessors */
	public final MemberList<TField> fieldsOverridingAccessors;
	/** fields mixed in AND not contained in fieldsOverridingAccessors */
	public final MemberList<TField> fieldsPurelyMixedInNotOverridingAccessor;

	/**
	 * Returns a tuple of collections used by transpiler to generate interface or class members.
	 */
	public static ConcreteMembersOrderedForTranspiler create(TranspilerState state, TClassifier type) {

		List<TMember> concreteInheritedMembers = (type instanceof TClass)
				? state.memberCollector.inheritedMembers((TClass) type)
				: emptyList();

		List<TMember> ownedAndMixedInConcreteMembers = state.memberCollector
				.computeOwnedAndMixedInConcreteMembers(type);

		Set<TMember> inlinedMembersFromShapes = new LinkedHashSet<>(state.memberCollector
				.computeInlinedMembersFromShapes(type));

		List<AccessorTuple> concreteAccessorTuples = getConcreteFieldAccessors(ownedAndMixedInConcreteMembers,
				concreteInheritedMembers);

		MemberList<TField> fieldsOverridingAccessors = getFieldsOverridingAccessor(ownedAndMixedInConcreteMembers,
				concreteInheritedMembers);

		// compute the list of mixed in fields, which do not override any Accessor (handled separately)
		MemberList<TField> fieldsPurelyMixedInNotOverridingAccessor = new MemberList<>();
		fieldsPurelyMixedInNotOverridingAccessor.addAll(
				ownedAndMixedInConcreteMembers.stream()
						.filter(it -> it instanceof TField
								&& it.getContainingType() != type) // must stem from different type
						.map(it -> (TField) it)
						.filter(it -> !fieldsOverridingAccessors.contains(it)) // remove the ones overriding get/set
						.collect(Collectors.toList()));

		return new ConcreteMembersOrderedForTranspiler(
				concreteInheritedMembers,
				ownedAndMixedInConcreteMembers,
				inlinedMembersFromShapes,
				concreteAccessorTuples,
				fieldsOverridingAccessors,
				fieldsPurelyMixedInNotOverridingAccessor);
	}

	private ConcreteMembersOrderedForTranspiler(
			List<TMember> concreteInheritedMembers, List<TMember> ownedAndMixedInConcreteMembers,
			Set<TMember> inlinedMembersFromShapes,
			List<AccessorTuple> concreteAccessorTuples, MemberList<TField> fieldsOverridingAccessors,
			MemberList<TField> fieldsPurelyMixedInNotOverridingAccessor) {

		this.concreteInheritedMembers = concreteInheritedMembers;
		this.ownedAndMixedInConcreteMembers = ownedAndMixedInConcreteMembers;
		this.inlinedMembersFromShapes = inlinedMembersFromShapes;
		this.concreteAccessorTuples = concreteAccessorTuples;
		this.fieldsOverridingAccessors = fieldsOverridingAccessors;
		this.fieldsPurelyMixedInNotOverridingAccessor = fieldsPurelyMixedInNotOverridingAccessor;
	}

	/**
	 * Helper method, returns all concrete field accessors as tuples of the given classifier, which may even contain
	 * fields if they override concrete getters or setter. Since getter/setters can only be defined as pairs, in case of
	 * single owned getter with inherited setter, a delegate is created for the setter (and vice versa).
	 */
	private static List<AccessorTuple> getConcreteFieldAccessors(List<TMember> ownedAndMixedInConcreteMember,
			List<TMember> concreteInheritedMembers) {

		Set<TField> ownedAndMixedInFields = new HashSet<>();
		Set<TGetter> ownedAndMixedInGetters = new HashSet<>();
		Map<NameStaticPair, TSetter> ownedAndMixedInSetters = new HashMap<>();

		for (TMember m : ownedAndMixedInConcreteMember) {
			if (m instanceof TField) {
				ownedAndMixedInFields.add((TField) m);
			} else if (m instanceof TGetter) {
				ownedAndMixedInGetters.add((TGetter) m);
			} else if (m instanceof TSetter) {
				ownedAndMixedInSetters.put(NameStaticPair.of(m), (TSetter) m);
			}
		}

		Map<NameStaticPair, TGetter> inheritedGetters = new HashMap<>();
		Map<NameStaticPair, TSetter> inheritedSetters = new HashMap<>();

		for (TMember m : concreteInheritedMembers) {
			if (m instanceof TGetter) {
				if (!ownedAndMixedInGetters.contains(m)) {
					inheritedGetters.put(NameStaticPair.of(m), (TGetter) m);
				}
			} else if (m instanceof TSetter) {
				NameStaticPair nsp = NameStaticPair.of(m);
				if (ownedAndMixedInSetters.get(nsp) != m) {
					inheritedSetters.put(nsp, (TSetter) m);
				}
			}
		}

		List<AccessorTuple> ownedOrMixedInAccessorTouples = mapToAccessorTuples(ownedAndMixedInGetters,
				ownedAndMixedInSetters,
				ownedAndMixedInFields, inheritedGetters, inheritedSetters);
		return ownedOrMixedInAccessorTouples;
	}

	/**
	 * Maps getters and setters to {@link AccessorTuple}s, used to generate proper setters-getters-pairs for property.
	 * The passed maps may be changed!
	 *
	 * @param getters
	 *            list of getters
	 * @param fields
	 *            list of fields
	 * @param io_setters
	 *            map with owned getters, this map may be changed by this method
	 * @param io_inheritedGetters
	 *            map with inherited getters, this map may be changed by this method
	 * @param io_inheritedSetters
	 *            map with inherited getters, this map may be changed by this method
	 * @return list of accessor tuples.
	 */
	private static List<AccessorTuple> mapToAccessorTuples(Iterable<TGetter> getters,
			Map<NameStaticPair, TSetter> io_setters, Iterable<TField> fields,
			Map<NameStaticPair, TGetter> io_inheritedGetters,
			Map<NameStaticPair, TSetter> io_inheritedSetters) {
		List<AccessorTuple> tuples = new ArrayList<>();

		// add getters (alone & with corresponding setter)
		for (TGetter getter : getters) {
			AccessorTuple tuple = new AccessorTuple(getter.getName(), getter.isStatic());
			tuple.setGetter(getter);
			NameStaticPair nsp = NameStaticPair.of(getter);
			tuple.setSetter(io_setters.remove(nsp)); // do not handle the thing twice
			if (tuple.getSetter() == null) {
				tuple.setInheritedSetter(io_inheritedSetters.remove(nsp));
			}
			tuples.add(tuple);
		}

		// add setters w/o getter:
		for (TSetter setter : io_setters.values()) {
			AccessorTuple tuple = new AccessorTuple(setter.getName(), setter.isStatic());
			NameStaticPair nsp = NameStaticPair.of(setter);
			tuple.setSetter(setter);
			tuple.setInheritedGetter(io_inheritedGetters.remove(nsp));
			tuples.add(tuple);
		}

		// owned fields overriding inherited getters or setters
		// remove the inherited references - the field will overwrite them.
		for (TField field : fields) {
			NameStaticPair nsp = NameStaticPair.of(field);
			io_inheritedSetters.remove(nsp);
			io_inheritedGetters.remove(nsp);
		}

		// find getters/setters defined in interfaces which need to be combined:
		for (TSetter inhSetter : io_inheritedSetters.values()) {
			TGetter inhGetter = io_inheritedGetters.remove(NameStaticPair.of(inhSetter));
			if (inhGetter != null
					&& inhSetter.getContainingType() != inhGetter.getContainingType()
					&&
					(inhSetter.getContainingType() instanceof TInterface
							|| inhGetter.getContainingType() instanceof TInterface)) {
				// getter & setter are inherited from different types.
				AccessorTuple tuple = new AccessorTuple(inhSetter.getName(), inhSetter.isStatic());
				tuple.setInheritedGetter(inhGetter);
				tuple.setInheritedSetter(inhSetter);
				tuples.add(tuple);
			}
		}

		return tuples;
	}

	private static MemberList<TField> getFieldsOverridingAccessor(Iterable<TMember> ownedAndMixedInConcreteMember,
			List<TMember> concreteInheritedMembers) {
		MemberList<TField> ownedAndMixedInFields = new MemberList<>();
		for (TMember m : ownedAndMixedInConcreteMember) {
			if (m instanceof TField) {
				if (concreteInheritedMembers.stream().anyMatch(it -> {
					return (it.isGetter() || it.isSetter()) && it.getName().equals(m.getName());
				})) {
					ownedAndMixedInFields.add((TField) m);
				}
			}
		}
		return ownedAndMixedInFields;
	}

	/**
	 * Only for debugging purposes.
	 */
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder("Concrete members, ordered:\nMembers: ");
		strb.append(ownedAndMixedInConcreteMembers.stream().map(m -> m.getName()).collect(Collectors.joining(";")));
		strb.append("\nAccessors: ");
		strb.append(concreteAccessorTuples.stream().map(at -> at.toString()).collect(Collectors.joining(";")));
		strb.append("\nFields: ");
		strb.append(fieldsOverridingAccessors.stream().map(f -> f.getName()).collect(Collectors.joining(";")));
		return strb.toString();

	}
}
