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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.transpiler.utils.ScriptApiTracker.VirtualApiTMethod;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.util.AccessorTuple;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;

/**
 * to here (to isolate logic related to API/Impl stubs)
 */
public class MissingApiMembersForTranspiler {

	/** Method-stubs injected to be used with test-code written against API */
	public final MemberList<TMethod> missingApiMethods;

	/** Getter/Setter -stubs injected to be used with test-code written against API */
	public final List<AccessorTuple> missingApiAccessorTuples;

	/**
	 * Returns a tuple of collections used by transpiler to generate interface or class members.
	 */
	public static MissingApiMembersForTranspiler create(ContainerTypesHelper containerTypesHelper,
			ScriptApiTracker apiTracker, TClassifier type, ConcreteMembersOrderedForTranspiler cmoft, Script context) {

		MemberCollector collector = containerTypesHelper.fromContext(context);

		// IDE-1510 create missing API-methods here.
		MemberList<TMethod> missingApiMethods = new MemberList<>();
		if (type instanceof TClass) {
			List<TMethod> c = apiTracker.computeMissingApiMethods((TClass) type, context);
			missingApiMethods.addAll(c);
			List<VirtualApiTMethod> missingAPIfromInheritance = apiTracker.computeMethodDiff((TClass) type, collector,
					cmoft.ownedAndMixedInConcreteMembers, missingApiMethods);
			missingApiMethods.addAll(missingAPIfromInheritance);
		}
		if (type instanceof TInterface) {
			List<TMethod> c = apiTracker.computeMissingApiMethods((TInterface) type, context);
			missingApiMethods.addAll(c);
		}

		// IDE-1510 create missing API-fields here.
		List<AccessorTuple> missingApiAccessorTuples = new ArrayList<>();
		{
			List<AccessorTuple> computedMissingApiGetterSetter = apiTracker.computeMissingApiGetterSetter(
					(TN4Classifier) type,
					cmoft.concreteAccessorTuples);
			List<AccessorTuple> computedMissingApiFields = apiTracker.computeMissingApiFields((TN4Classifier) type);

			// In case of field vs. getter the implementation should be free to choose the form.
			// So filter out all missing set/get pairs which are backed by a field (either concrete or inherited)
			List<AccessorTuple> filteredMissingApiGetterSetter = filterOutTuplesImplementedByField(
					computedMissingApiGetterSetter,
					cmoft.ownedAndMixedInConcreteMembers, cmoft.concreteInheritedMembers);

			// Some logic applies to missing fields which are backed by a getter/setter. The situation here is a
			// little bit more complex as the set/get must be processed as a pair and one could be missing or they stem
			// from different (super-)types.
			// *Beware* not side-effect free since we have to mix in virtual getter/setter into an existing but
			// incomplete accessor-pairs
			List<AccessorTuple> filteredMissingApiFields0 = filterMissingApiFieldsAndEnrichExistingTuples(
					computedMissingApiFields, cmoft.concreteAccessorTuples);
			List<AccessorTuple> filteredMissingApiFields = filterMissingApiFieldsImplementedBySuperGetSet(
					filteredMissingApiFields0, cmoft.concreteInheritedMembers);

			missingApiAccessorTuples.addAll(filteredMissingApiGetterSetter);
			missingApiAccessorTuples.addAll(filteredMissingApiFields);
		}

		MemberList<TField> fieldsOverridingAccessors = getFieldsOverridingAccessor(cmoft.ownedAndMixedInConcreteMembers,
				cmoft.concreteInheritedMembers);

		// compute the list of mixed in fields, which do not override any Accessor (handled separately)
		MemberList<TField> fieldsPurelyMixedInNotOverridingAccessor = new MemberList<>();
		fieldsPurelyMixedInNotOverridingAccessor.addAll(
				cmoft.ownedAndMixedInConcreteMembers.stream()
						.filter(it -> it instanceof TField
								&& it.getContainingType() != type) // must stem from different type
						.map(it -> (TField) it)
						.filter(it -> !fieldsOverridingAccessors.contains(it)) // remove the ones overriding get/set
						.collect(Collectors.toList()));

		return new MissingApiMembersForTranspiler(missingApiMethods, missingApiAccessorTuples);
	}

	/**
	 * Filter missingApiFields if super-implementation provides an implementation with get/set.
	 *
	 * If both getter and setter are inherited the corresponding AccessorTuple will be removed other wise the tuple will
	 * be modified to hold a reference to the inherited getter or setter.
	 *
	 * @param missingApiFields
	 *            the computed missing fields. Elements might get modified.
	 * @param concreteInheritedMembers
	 *            all inherited members
	 *
	 * @return filter and modified list.
	 */
	private static List<AccessorTuple> filterMissingApiFieldsImplementedBySuperGetSet(
			List<AccessorTuple> missingApiFields,
			List<TMember> concreteInheritedMembers) {
		return missingApiFields
				.stream()
				.filter(accTuple -> {
					boolean _static = accTuple.isStatic();
					String name = accTuple.getName();
					concreteInheritedMembers
							.stream()
							.filter(member -> _static == member.isStatic()
									&& (member.isGetter() || member.isSetter())
									&& name.equals(member.getName()))
							.forEach(member -> {
								if (member.isGetter()) {
									accTuple.replaceGetterByInheretedGetter_caseIncompletAPI((TGetter) member);
								} else {
									// setter
									accTuple.replaceSetterByInheretedSetter_caseIncompletAPI((TSetter) member);
								}
							});
					if (accTuple.getGetter() == null && accTuple.getSetter() == null) {
						// complete inheritance: field implemented by pair , can be removed
						return false;
					}
					return true;
				})
				.collect(Collectors.toList());
	}

	/**
	 * Removes all virtual missing API tuples for which a concrete accessor tuple is found in concreteAccessorTuples. If
	 * the concrete tuple is missing either the getter or setter, the virtual getter/setter will be transferred. From
	 * the filtered missingApiField-tuple to the existing one. Method has side-effect.
	 *
	 * @param computedMissingApiFields
	 *            proposed getter/setter for missing fields
	 * @param concreteAccessorTuples
	 *            concrete implemented or inherited getter/setter tuples. Content-elements will be modified!
	 * @return a filtered List of computedMissingApiFields with only those Elements remaining where no implementation
	 *         could be found at all (neither field nor getter nor setter)
	 */
	private static List<AccessorTuple> filterMissingApiFieldsAndEnrichExistingTuples(
			List<AccessorTuple> computedMissingApiFields, List<AccessorTuple> concreteAccessorTuples) {
		return computedMissingApiFields.stream().filter(
				(AccessorTuple tuple) -> {
					String name = tuple.getName();
					boolean isStatic = tuple.isStatic();
					Optional<AccessorTuple> concreteAccessor = concreteAccessorTuples
							.stream()
							.filter(concreteTuple -> isStatic == concreteTuple.isStatic()
									&& name.equals(concreteTuple.getName()))
							.findFirst();
					if (concreteAccessor.isPresent()) {
						AccessorTuple ca = concreteAccessor.get();
						if (ca.hasPair()) {
							// fully implemented, remove this field-missing-tuple
							return false;
						}

						// only one half is present, modify the existing tuple to include the missing part.:
						if (ca.getInheritedGetter() != null || ca.getGetter() != null) {
							// getter is present.
							ca.setSetter(tuple.getSetter());
						} else if (ca.getInheritedSetter() != null || ca.getSetter() != null) {
							// setter present.
							ca.setGetter(tuple.getGetter());
						}
						return false; // remove this entry.
					} else
						// no tuple implementing this field found, keep it.
						return true;
				}).collect(Collectors.toList());
	}

	/**
	 * Returns a new List containing all elements of computedMissingApiGetterSetter except those which have an field
	 * implementations in the current inheritance hierarchy.
	 *
	 * @param computedMissingApiGetterSetter
	 *            computedMissing
	 * @param ownedAndMixedInConcreteMember
	 *            actual Concrete members
	 * @param concreteInheritedMembers
	 *            actual inherited members
	 *
	 * @return Filtered List of computedMissingApiGetterSetter
	 */
	private static List<AccessorTuple> filterOutTuplesImplementedByField(
			List<AccessorTuple> computedMissingApiGetterSetter,
			List<TMember> ownedAndMixedInConcreteMember, List<TMember> concreteInheritedMembers) {
		return computedMissingApiGetterSetter.stream()
				.filter(accessTuple -> {
					String name = accessTuple.getName();
					boolean _static = accessTuple.isStatic();
					// Only accept if no field with same name exists:
					return !Stream
							.concat(ownedAndMixedInConcreteMember.stream(), concreteInheritedMembers.stream())
							.filter(m -> (m instanceof TField)
									&& (_static == m.isStatic())
									&& m.getName().equals(name))
							.findFirst().isPresent();

				}).collect(Collectors.toList());
	}

	private MissingApiMembersForTranspiler(MemberList<TMethod> missingApiMethods,
			List<AccessorTuple> missingApiAccessorTuples) {
		this.missingApiMethods = missingApiMethods;
		this.missingApiAccessorTuples = missingApiAccessorTuples;
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
}
