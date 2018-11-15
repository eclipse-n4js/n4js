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
package org.eclipse.n4js.utils;

import static java.util.Collections.emptyList;
import static org.eclipse.n4js.scoping.members.TMemberEntry.MemberSource.INHERITED;
import static org.eclipse.n4js.scoping.members.TMemberEntry.MemberSource.MIXEDIN;
import static org.eclipse.n4js.scoping.members.TMemberEntry.MemberSource.OWNED;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isContainedInStaticPolyfillAware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.members.TMemberEntry;
import org.eclipse.n4js.scoping.members.TMemberEntry.MemberSource;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.NameAndAccess;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TObjectPrototype;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.util.AbstractHierachyTraverser;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.ts.types.util.NameStaticPair;
import org.eclipse.n4js.ts.types.util.NonSymetricMemberKey;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * Needs to be injected.
 */
public class ContainerTypesHelper {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ContainerTypesHelper.class);

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	/**
	 * Utilize a wrapping scope for recoding imported names, thus enable notifications. Internally uses global scope
	 * provider. We use the N4JS global scope provider here, since polyfills must be defined in N4JSD files, that is in
	 * the N4JS language.
	 */
	@Inject
	private ImportedNamesRecordingScopeAccess polyfillScopeAccess;

	@Inject
	private IResourceScopeCache cache;

	/**
	 * Returns a new member collector to collect members available in the current context. The available members are
	 * <b>not</b> determined by access modifiers, but by polyfills and implicit super types. These latter types may vary
	 * by project, depending on which runtime libraries and environments are used.
	 *
	 * @param contextResource
	 *            must not be null, used to load polyfills and implicit super types (i.e., used to access index and
	 *            resolve proxies)
	 */
	public MemberCollector fromContext(Resource contextResource) {
		if (contextResource == null) {
			throw new NullPointerException("Context resource used to collect members must not be null.");
		}
		if (!(contextResource instanceof N4JSResource)) {
			throw new IllegalArgumentException("polyfills etc are only supported by n4js");
		}
		return new MemberCollector(contextResource);
	}

	/**
	 * Convenience method, delegates to {@link #fromContext(Resource) fromContext(contextObject.eResource())}
	 */
	public MemberCollector fromContext(EObject contextObject) {
		if (contextObject == null) {
			throw new NullPointerException("Context object used to collect members must not be null.");
		}
		return fromContext(contextObject.eResource());
	}

	/**
	 * Member collector to retrieve members of a type from a given context. The member collector is created via the
	 * builder pattern with the ContainerTypesHelper, e.g.,
	 * <code>containerTypesHelper.fromContext(currentResource).allMembers(type)</code>
	 */
	public class MemberCollector {

		private final Resource contextResource;

		private MemberCollector(Resource contextResource) {
			this.contextResource = contextResource;
		}

		/**
		 * Convenience method, finds the constructor taking implicit super types and polyfills into account. Given a
		 * valid type, this method never returns <code>null</code>, because implicit super types are taken into account;
		 * however, in case of invalid type models (broken AST etc.), this method might return <code>null</code>.
		 */
		public TMethod findConstructor(ContainerType<?> type) {
			TMember member = findMember(type, "constructor", false, false, true, true);
			if (member instanceof TMethod) {
				return (TMethod) member;
			}
			return null;
		}

		/**
		 * Convenience method, finds a member with a given name in members (owned and members of super types and
		 * polyfills).
		 */
		public TMember findMember(ContainerType<?> type, String name, boolean writable, boolean staticAccess) {
			return findMember(type, name, writable, staticAccess, true, true);
		}

		/**
		 * Find an owned or inherited member with the given name and writable flag.
		 */
		public TMember findMember(ContainerType<?> type, String name, boolean writable, boolean staticAccess,
				boolean includeImplicitSuperTypes,
				boolean includePolyfills) {
			return cache.get(Arrays.asList("findMember", type, name, writable, staticAccess, includeImplicitSuperTypes,
					includePolyfills), contextResource,
					() -> new FindMemberHelper(type, name, writable, staticAccess, includeImplicitSuperTypes,
							includePolyfills).getResult());
		}

		/**
		 * Returns true if the given member is actually mixed in the given classifier. This is only the case if the
		 * member is (indirectly) contained in an interface which is directly implemented by the given classifier and if
		 * no other member with same name (and writeability/static modifier) is already inherited from the class.
		 */
		public boolean isMixedIn(TClassifier classifier, TMember member) {
			if (member == null || !(member.eContainer() instanceof TInterface)) {
				return false;
			}
			TMember actualMember = findMember(classifier, member.getName(), member.isWriteable(), member.isStatic(),
					true, true);
			return actualMember == member;
		}

		/**
		 * Returns the super type is effectively bequests the member, that is the direct super type of the the given
		 * classifier which contains the inheritedMember in its member list. Note that the actual member of the class
		 * must not necessarily be the requested member, since it could have been overridden (or inherited) by a super
		 * class or another interface.
		 * <p>
		 * E.g., in
		 *
		 * <pre>
		 * interface I { m() {} }
		 * interface J extends I {}
		 * class A implements J {}
		 * </pre>
		 *
		 * a (pseudo) call {@code directSuperTypeBequestingMember(A, m)} would return {@code J}.
		 *
		 * @param inheritedMember
		 *            must be an inherited member of classifier or overridden by the classifier or one of its super
		 *            types, otherwise an {@link IllegalArgumentException} is thrown
		 * @return the direct super type, never null (instead, an exception is thrown)
		 */
		public ContainerType<?> directSuperTypeBequestingMember(TClassifier classifier, TMember inheritedMember) {
			ContainerType<?> containingType = inheritedMember.getContainingType();
			for (ParameterizedTypeRef superTypeRef : classifier.getSuperClassifierRefs()) {
				if (superTypeRef.getDeclaredType() instanceof ContainerType<?>) {
					ContainerType<?> superType = (ContainerType<?>) superTypeRef.getDeclaredType();
					if (containingType == superType) { // <-- short cut
						return superType;
					}
					TMember superTypesMember = findMember(superType, inheritedMember.getName(),
							inheritedMember.isWriteable(), inheritedMember.isStatic());
					if (superTypesMember == inheritedMember) {
						// if (superTypesMember != null) {
						return superType;
					}
				} // else is ignored to make method robust in case of broken models during editing
			}
			throw new IllegalArgumentException("Member " + inheritedMember.getMemberAsString()
					+ " was not inherited by " + classifier.getTypeAsString());
		}

		/**
		 * Returns all the members of the given type including the inherited methods, fields, getters and setters. This
		 * includes all members defined in roles, interfaces and super types. It <b>does not contain any duplicates</b>,
		 * that is, there are no two members with same name and accessibility. That is, overridden members are not
		 * included in the list. The members reflect the members available at runtime, that is, owned members precede
		 * consumed members and consumed members precede inherited members. Members only defined in interfaces are added
		 * as well.
		 */
		public MemberList<TMember> members(ContainerType<?> type) {
			return members(type, true, true);
		}

		/**
		 * Similar to {@link #members(ContainerType)} but with a filter to only accept certain elements.
		 */
		private MemberList<TMember> members(ContainerType<?> type, Predicate<TMember> filter) {
			return new CollectMembersHelper(type, true, true, filter).getResult();
		}

		/**
		 * Returns all (real) members of the given type.
		 *
		 * @param includePolyfills
		 *            if set to {@code true}, members defined in dynamic or static polyfills are collected as well
		 */
		public MemberList<TMember> members(ContainerType<?> type, boolean includeImplicitSuperTypes,
				boolean includePolyfills) {
			return cache.get(Arrays.asList("members", type, includeImplicitSuperTypes, includePolyfills),
					contextResource,
					() -> new CollectMembersHelper(type, includeImplicitSuperTypes, includePolyfills, m -> true)
							.getResult());
		}

		/**
		 * Returns all members of given type, including members inherited from implicit super types and filled members
		 * from (dynamic or static) polyfills. The returned list includes overridden members.
		 */
		public MemberList<TMember> allMembers(ContainerType<?> type) {
			return allMembers(type, true, true);
		}

		/**
		 * Returns all members of given type.
		 *
		 * @param includePolyfills
		 *            if set to {@code true}, members defined in dynamic or static polyfills are collected as well
		 */
		public MemberList<TMember> allMembers(ContainerType<?> type, boolean includeImplicitSuperTypes,
				boolean includePolyfills) {
			return allMembers(type, includeImplicitSuperTypes, includePolyfills, true);
		}

		/**
		 * Returns all members of given type.
		 *
		 * @param includeInheritedMembers
		 *            if set to {@code false} neither inherited, nor mixed in members (from interfaces) will be included
		 *            at all.
		 * @param includePolyfills
		 *            if set to {@code true}, members defined in dynamic or static polyfills are collected as well
		 *
		 */
		public MemberList<TMember> allMembers(ContainerType<?> type, boolean includeImplicitSuperTypes,
				boolean includePolyfills, boolean includeInheritedMembers) {
			return cache.get(
					Arrays.asList("allMembers", type, includeImplicitSuperTypes, includePolyfills,
							includeInheritedMembers),
					contextResource, () -> new AllMembersCollector(type, includeImplicitSuperTypes, includePolyfills,
							includeInheritedMembers)
									.getResult());
		}

		/**
		 * Returns all member entries, that is all members clustered by name and static modifier, taking some override
		 * behavior into account. The result is basically used for validation purposes. Although the returning result is
		 * a collection, it is guaranteed that no duplicates are contained.
		 */
		public Collection<TMemberEntry> memberEntries(ContainerType<?> type) {
			return new MemberEntriesCollector(type, true, true).getResult();
		}

		/**
		 * Returns all inherited members, that is members of super class. This does <em>not</em> include members mixed
		 * in from interfaces!
		 */
		public MemberList<TMember> inheritedMembers(TClass clazz) {
			TClassifier superType = explicitOrImplicitSuperType(clazz);
			if (superType != null) {
				return members(superType, m -> m.getContainingType() != clazz); // avoid problems with cycles
			}
			return MemberList.emptyList();
		}

		/**
		 * Returns all members of (directly) implemented interfaces that are candidates for being consumed by an
		 * implementing class. This list does not contain any duplicates. Note that the members may not actually be
		 * consumed by the class, as they may either already be defined in a super class (in which case they do not get
		 * mixed in) or by the class itself.
		 * <p>
		 * For classes, this returns the list of interfaces in the "implements" section, for interfaces in the "extends"
		 * section.
		 * <p>
		 * Members of the implicit super type of interfaces (i.e. N4Object) are not included, because those members will
		 * never be consumed (currently, N4Object only has a single non-static member, i.e. its constructor, so this
		 * applies only to this one member).
		 */
		public MemberList<TMember> membersOfImplementedInterfacesForConsumption(TClassifier classifier) {

			Iterator<ParameterizedTypeRef> iter = classifier.getImplementedOrExtendedInterfaceRefs().iterator();
			if (!iter.hasNext()) {
				return MemberList.emptyList();
			}
			ParameterizedTypeRef first = iter.next();
			if (!iter.hasNext()) { // only one interface, simply create members of that interface directly
				if (first.getDeclaredType() instanceof TInterface) {
					TInterface tinterface = (TInterface) first.getDeclaredType();
					return members(tinterface, false, true);
				}
				return MemberList.emptyList();
			}

			MemberList<TMember> memberList = new MemberList<>();
			for (ParameterizedTypeRef interfaceRef : classifier.getImplementedOrExtendedInterfaceRefs()) {
				if (interfaceRef.getDeclaredType() instanceof TInterface) {
					TInterface tinterface = (TInterface) interfaceRef.getDeclaredType();
					memberList.addAll(members(tinterface, false, true));
				}
			}
			return memberList;
		}

		/**
		 * Returns the explicitly declared or implicit super type of a class. If the declared super type is not a
		 * TClassifier, the implicit super type is returned.
		 */
		private TClassifier explicitOrImplicitSuperType(TClassifier tclassifier) {
			Optional<TClassifier> expl = explicitSuperType(tclassifier);
			if (expl.isPresent())
				return expl.get();

			List<ParameterizedTypeRef> superTypes = implicitSuperTypes(tclassifier);
			if (!superTypes.isEmpty()) {
				return (TClassifier) superTypes.get(0).getDeclaredType();
			}

			return null;
		}

		/**
		 * Get explicitly declared supertype.
		 */
		private Optional<TClassifier> explicitSuperType(TClassifier tclassifier) {
			if (tclassifier instanceof TClass) {
				TClass tclass = (TClass) tclassifier;
				if (tclass.getSuperClassRef() != null
						&& tclass.getSuperClassRef().getDeclaredType() instanceof TClassifier) {
					return Optional.of((TClassifier) tclass.getSuperClassRef().getDeclaredType());
				}
			}
			return Optional.absent();
		}

		private List<Type> getPolyfillTypesFromScope(QualifiedName fqn) {

			IScope contextScope = polyfillScopeAccess.getRecordingPolyfillScope(contextResource);
			List<Type> types = new ArrayList<>();

			// contextScope.getElements(fqn) returns all polyfills, since shadowing is handled differently
			// for them!
			for (IEObjectDescription descr : contextScope.getElements(fqn)) {
				Type polyfillType = (Type) descr.getEObjectOrProxy();
				if (polyfillType.eIsProxy()) {
					// TODO review: this seems odd... is this a test setup problem (since we do not use the
					// index
					// there and load the resource separately)?
					polyfillType = (Type) EcoreUtil.resolve(polyfillType, contextResource);
					if (polyfillType.eIsProxy()) {
						throw new IllegalStateException("unexpected proxy");
					}
				}
				types.add(polyfillType);
			}
			// }

			return types;
		}

		/**
		 * Returns an iterable with all members of the given classifier, that is, owned member, inherited members, and
		 * mixed in members. The constructor is filtered out.
		 */
		public MemberList<TMember> computeOwnedAndMixedInConcreteMembers(TClassifier type) {

			TClassifier superType;
			List<ParameterizedTypeRef> interfaces;

			if (type instanceof TClass) {
				superType = explicitOrImplicitSuperType(type);
				interfaces = ((TClass) type).getImplementedInterfaceRefs();
			} else if (type instanceof TInterface) {
				superType = null;
				interfaces = ((TInterface) type).getSuperInterfaceRefs();
			} else if (type instanceof TObjectPrototype) {
				superType = explicitOrImplicitSuperType(type);
				interfaces = emptyList();
			} else {
				return MemberList.emptyList();
			}

			MemberList<TMember> ownedAndMixedInConcreteMembers = type.getOwnedMembers().stream()
					.filter(m -> {
						return !m.isAbstract() && !"constructor".equals(m.getName());
					})
					.collect(Collectors.toCollection(MemberList::new));
			Set<NonSymetricMemberKey> ownedTypeMemberKeys = ownedAndMixedInConcreteMembers.stream()
					.map(it -> NonSymetricMemberKey.of(it))
					.collect(Collectors.toSet());

			Collection<NonSymetricMemberKey> superTypeMemberKeys = (superType != null)
					? allMembers(superType, false, true).stream()
							.map(it -> NonSymetricMemberKey.of(it)).collect(Collectors.toSet())
					: Collections.emptySet();

			Map<NameStaticPair, MemberList<TMember>> mixedInMemberListsByNameStatic = new HashMap<>();
			interfaces.stream()
					.map(it -> it.getDeclaredType())
					.filter(it -> it instanceof TInterface) // at validation there might be bogus types contained
					.forEach(intf -> {
						members((TInterface) intf, false, true).stream()
								.filter(m -> !m.isAbstract() && !m.isStatic())
								.forEach(it -> {
									NameStaticPair nsp = NameStaticPair.of(it);
									MemberList<TMember> c = mixedInMemberListsByNameStatic.get(nsp);
									if (c == null) {
										c = new MemberList<>();
										mixedInMemberListsByNameStatic.put(nsp, c);
									}
									/*
									 * the exact same member inherited twice cause no problems, cf. IDEBUG-300
									 */
									if (!c.contains(it)) {
										c.add(it);
									}
								});
					});

			mixedInMemberListsByNameStatic
					.values()
					.stream()
					.filter(it -> {
						switch (it.size()) {
						case 1:
							return true;
						case 2:
							return TypeUtils.isAccessorPair(it.get(0), it.get(1));
						default:
							return false;
						}
					})
					.forEach(
							tmemberColl -> {
								for (TMember m : tmemberColl) {
									NonSymetricMemberKey key = NonSymetricMemberKey.of(m);
									if (!(ownedTypeMemberKeys.contains(key) || superTypeMemberKeys
											.contains(key))) {
										ownedAndMixedInConcreteMembers.add(m);
									}
								}
							});
			return ownedAndMixedInConcreteMembers;
		}

		/**
		 * Returns an iterable with all members of the given classifier + static-polyfills, that is, owned member,
		 * inherited members, and mixed in members. The constructor is filtered out.
		 *
		 * @param type
		 *            sd
		 * @param spolyBuddy
		 *            a staticPolyfill of type, not null.
		 */
		public MemberList<TMember> computeOwnedAndMixedInConcreteMembers(TClassifier type, TClassifier spolyBuddy) {

			// check precondition
			if (spolyBuddy == null)
				throw new IllegalArgumentException(
						"Parameter must not be null. If not static polyfill is available, call "
								+ "#computeOwnedAndMixedInConcreteMembers() with one arg only.Type="
								+ type.getRawTypeAsString());
			if (!(spolyBuddy.isStaticPolyfill()
					&& ((spolyBuddy instanceof TClass && type.equals(explicitSuperType(spolyBuddy).orNull()))
							|| (spolyBuddy instanceof TInterface
									&& ((TInterface) spolyBuddy).getSuperInterfaceRefs().stream()
											.filter(ptr -> type.equals(ptr.getDeclaredType())).findFirst().isPresent()) //
					) //
			)) {
				throw new IllegalArgumentException(
						"Passed in static polyfill " + spolyBuddy.getRawTypeAsString()
								+ " is not filling passed in type " + type.getRawTypeAsString());
			}
			// end preconditions.

			TClassifier superType;
			List<ParameterizedTypeRef> interfaces;

			if (type instanceof TClass) {
				superType = explicitOrImplicitSuperType(type);
				interfaces = new ArrayList<>(((TClass) type).getImplementedInterfaceRefs());
				interfaces.addAll(((TClass) spolyBuddy).getImplementedInterfaceRefs());
			} else if (type instanceof TInterface) {
				superType = null;
				interfaces = new ArrayList<>(((TInterface) type).getSuperInterfaceRefs());
				interfaces.addAll(((TInterface) spolyBuddy).getSuperInterfaceRefs());
			} else if (type instanceof TObjectPrototype) {
				superType = explicitOrImplicitSuperType(type);
				interfaces = emptyList();
			} else {
				return MemberList.emptyList();
			}

			// DONE: IDE-1735 enhance list of interfaces with additional interfaces from static-polyfill.

			final List<TMember> polyfilledMembers = spolyBuddy.getOwnedMembers().stream()
					.filter(m -> {
						return !m.isAbstract() && !"constructor".equals(m.getName());
					}).collect(Collectors.toList());

			Stream<TMember> origOwnedAndMixedInConcreteMembers = type.getOwnedMembers().stream()
					.filter(m -> {
						return !m.isAbstract() && !"constructor".equals(m.getName());
					})
					.filter(m -> { // Additionally filter out polyfilled things (overriden)
						// TODO improve filtering with better algo.
						return !polyfilledMembers.stream()
								.filter(c -> c.getMemberType() == m.getMemberType()
										&& c.isStatic() == m.isStatic()
										&& c.getName().equals(m.getName()))
								.findFirst().isPresent();
					});

			// combine polyfilled- and filtered owned members
			MemberList<TMember> ownedAndMixedInConcreteMembers = Stream
					.concat(polyfilledMembers.stream(), origOwnedAndMixedInConcreteMembers)
					.collect(Collectors.toCollection(MemberList::new));

			Set<NonSymetricMemberKey> ownedTypeMemberKeys = ownedAndMixedInConcreteMembers.stream()
					.map(it -> NonSymetricMemberKey.of(it))
					.collect(Collectors.toSet());

			Collection<NonSymetricMemberKey> superTypeMemberKeys = (superType != null)
					? allMembers(superType, false, true).stream()
							.map(it -> NonSymetricMemberKey.of(it)).collect(Collectors.toSet())
					: Collections.emptySet();

			Map<NameStaticPair, MemberList<TMember>> mixedInMemberListsByNameStatic = new HashMap<>();
			interfaces.stream()
					.map(it -> it.getDeclaredType())
					.filter(it -> it instanceof TInterface) // at validation there might be bogus types contained
					.forEach(intf -> {
						members((TInterface) intf, false, true).stream()
								.filter(m -> !m.isAbstract() && !m.isStatic())
								.forEach(it -> {
									NameStaticPair nsp = NameStaticPair.of(it);
									MemberList<TMember> c = mixedInMemberListsByNameStatic.get(nsp);
									if (c == null) {
										c = new MemberList<>();
										mixedInMemberListsByNameStatic.put(nsp, c);
									}
									/*
									 * the exact same member inherited twice cause no problems, cf. IDEBUG-300
									 */
									if (!c.contains(it)) {
										c.add(it);
									}
								});
					});

			mixedInMemberListsByNameStatic
					.values()
					.stream()
					.filter(it -> {
						switch (it.size()) {
						case 1:
							return true;
						case 2:
							return TypeUtils.isAccessorPair(it.get(0), it.get(1));
						default:
							return false;
						}
					})
					.forEach(
							tmemberColl -> {
								for (TMember m : tmemberColl) {
									NonSymetricMemberKey key = NonSymetricMemberKey.of(m);
									if (!(ownedTypeMemberKeys.contains(key) || superTypeMemberKeys
											.contains(key))) {
										ownedAndMixedInConcreteMembers.add(m);
									}
								}
							});
			return ownedAndMixedInConcreteMembers;
		}

		private List<ParameterizedTypeRef> implicitSuperTypes(Type type) {
			return RuleEnvironmentExtensions.collectAllImplicitSuperTypesOfType(
					RuleEnvironmentExtensions.newRuleEnvironment(contextResource),
					type);

		}

		private abstract class AbstractMemberCollector<Result> extends AbstractHierachyTraverser<Result> {

			private final boolean includeImplicitSuperTypes;
			/**
			 * Flag indicating whether members defined in dynamic or static polyfills are collected as well.
			 */
			protected final boolean includePolyfills;

			/**
			 * Creates a new collector with optional support for implicit super types.
			 *
			 * @param type
			 *            the base type. Must be contained in a resource set if <code>includeImplicitSuperTypes</code>
			 *            is set to <code>true</code>.
			 * @param includeImplicitSuperTypes
			 *            if true also members of implicit super types will be collected; otherwise only members of
			 *            declared super types are included.
			 * @throws IllegalArgumentException
			 *             if <code>includeImplicitSuperTypes</code> is set to <code>true</code> and <code>type</code>
			 *             is not contained in a properly initialized N4JS resource set.
			 */
			public AbstractMemberCollector(ContainerType<?> type,
					boolean includeImplicitSuperTypes, boolean includePolyfills) {
				super(type);
				this.includeImplicitSuperTypes = includeImplicitSuperTypes;
				this.includePolyfills = includePolyfills;
			}

			/**
			 * Subclasses should ignore all members for which this method returns <code>true</code>.
			 */
			protected boolean isIgnoredMember(TMember m) {
				// ignore static members of implemented/extended interfaces (no static polymorphism for interfaces)
				if (m.isStatic()
						&& m.getContainingType() instanceof TInterface
						&& m.getContainingType() != bottomType) {
					return true;
				}
				return false;
			}

			@Override
			protected List<ParameterizedTypeRef> getImplicitSuperTypes(Type type) {
				if (includeImplicitSuperTypes) {
					return implicitSuperTypes(type);
				}

				return Collections.emptyList();
			}

			@Override
			protected List<ParameterizedTypeRef> getPolyfills(Type filledType) {
				if (includePolyfills && (filledType instanceof TClass || filledType instanceof TObjectPrototype)) {
					TClassifier tClassifier = (TClassifier) filledType;
					if (filledType.isProvidedByRuntime() // only runtime types can be polyfilled, but
					) {
						QualifiedName qn = N4TSQualifiedNameProvider.getPolyfillFQN(tClassifier, qualifiedNameProvider);
						if (qn != null) { // may be a class expression which has no name,
							// if there is no name, there cannot be a polyfill

							// no need for filtering, the special FQN for polyfills ensures only polyfills are returned:
							List<Type> polyfills = getPolyfillTypesFromScope(qn);
							return polyfills.stream().map(
									polyFill -> TypeUtils.createTypeRef(polyFill)).collect(Collectors.toList());
						}
					}
					if (isContainedInStaticPolyfillAware(filledType) // static-polyfilled work as well
					) {
						QualifiedName qn = N4TSQualifiedNameProvider.getStaticPolyfillFQN(tClassifier,
								qualifiedNameProvider);
						if (qn != null) { // may be a class expression which has no name,
							// if there is no name, there cannot be a polyfill

							// no need for filtering, the special FQN for polyfills ensures only polyfills are returned:
							List<Type> polyfills = getPolyfillTypesFromScope(qn);
							return polyfills.stream().map(
									polyFill -> TypeUtils.createTypeRef(polyFill)).collect(Collectors.toList());
						}
					}
				}
				return Collections.emptyList();
			}
		}

		private class AllMembersCollector extends AbstractMemberCollector<MemberList<TMember>> {
			private final MemberList<TMember> result;
			private final boolean includeInheritedMembers;

			public AllMembersCollector(ContainerType<?> type,
					boolean includeImplicitSuperTypes,
					boolean includePolyfills,
					boolean includeInheritedMembers) {

				super(type, includeImplicitSuperTypes, includePolyfills);
				this.includeInheritedMembers = includeInheritedMembers;
				result = new MemberList<>();
			}

			@Override
			protected MemberList<TMember> doGetResult() {
				return result;
			}

			@Override
			protected boolean process(ContainerType<?> containerType) {
				if (includeInheritedMembers || bottomType.equals(containerType) ||
						(includePolyfills && isDirectPolyfill(containerType))) {
					for (TMember member : containerType.getOwnedMembers()) {
						if (!isIgnoredMember(member)) {
							result.add(member);
						}
					}
				}
				return false;
			}

			/**
			 * Returns true if the given container type is a polyfill of the bottom type.
			 * <p>
			 * Note: we compare via FQN here, since the bottom type may not stem from the index but from the type model
			 * derived from the AST. In that case, the type instance, although similar, differs from the type instance
			 * from the index.
			 */
			private boolean isDirectPolyfill(ContainerType<?> containerType) {
				if (!containerType.isStaticPolyfill() || !(bottomType instanceof TClassifier)) {
					return false;
				}
				QualifiedName qn = N4TSQualifiedNameProvider.getStaticPolyfillFQN((TClassifier) bottomType,
						qualifiedNameProvider);
				if (containerType instanceof TClass) { // short cut
					return qn.equals(qualifiedNameProvider.getFullyQualifiedName(containerType));
				}
				if (containerType instanceof TN4Classifier) {
					return Iterables.any(((TN4Classifier) containerType).getSuperClassifierRefs(),
							ref -> qn.equals(qualifiedNameProvider.getFullyQualifiedName(ref.getDeclaredType())));

				}
				return false;
			}
		}

		/**
		 * A simple utility that allows to collect all members while guarding against infinite recursion due to invalid
		 * type hierarchies. The result will contain all the members of the given type including the inherited methods,
		 * fields, getters and setters. This includes all members defined in roles, interfaces and super types. It does
		 * not contain any duplicates, that is, there is are no two members with same name and accessibility. The
		 * members reflect the members available at runtime, that is, owned members precede consumed members and
		 * consumed members precede inherited members. Members only defined in interfaces are added as well. Note that
		 * static member of interfaces are not mixed in.
		 */
		private class CollectMembersHelper extends AbstractMemberCollector<MemberList<TMember>> {

			private final Map<NameAndAccess, TMember> nameAccessToMember;
			private final Predicate<TMember> filter;

			/**
			 * Creates a new collector that is used to safely traverse a potentially cyclic inheritance tree and collect
			 * the members of the type.
			 *
			 * @filter only members passing the filter are added to the collection. If the filter is null, everything is
			 *         accepted
			 */
			public CollectMembersHelper(ContainerType<?> type,
					boolean includeImplicitSuperTypes, boolean includePolyfills, Predicate<TMember> filter) {
				super(type, includeImplicitSuperTypes, includePolyfills);
				this.filter = filter == null ? m -> true : filter;
				nameAccessToMember = Maps.newLinkedHashMap();
			}

			@Override
			protected MemberList<TMember> doGetResult() {
				return new MemberList<>(nameAccessToMember.values());
			}

			@Override
			protected boolean process(ContainerType<?> containerType) {
				for (Entry<NameAndAccess, ? extends TMember> entry : containerType
						.getOrCreateOwnedMembersByNameAndAccess()
						.entrySet()) {
					final NameAndAccess key = entry.getKey();
					final TMember m = entry.getValue();

					// do not add members that are readable AND writeable twice
					final boolean isDuplicate = m.isReadable() && m.isWriteable() && key.isWriteAccess();
					if (!isDuplicate && !isIgnoredMember(m) && filter.test(m)) {
						TMember prev = nameAccessToMember.put(key, m);
						if (prev != null) { // found prev, either from sub type or preceding interface
							if (!(prev.isAbstract() && !m.isAbstract()) // concrete members precede abstract members
									|| prev.getContainingType() == this.bottomType // owned members precede everything
							) {
								nameAccessToMember.put(key, prev);
							}
						}
					}
				}
				return false;
			}
		}

		private class MemberEntriesCollector extends AbstractMemberCollector<Collection<TMemberEntry>> {
			private final Map<Pair<String, Boolean>, TMemberEntry> memberEntries;
			private TMemberEntry.MemberSource source;

			public MemberEntriesCollector(ContainerType<?> type,
					boolean includeImplicitSuperTypes, boolean includePolyfills) {
				super(type, includeImplicitSuperTypes, includePolyfills);
				memberEntries = new HashMap<>();
				if (type instanceof TInterface) {
					source = MIXEDIN;
				} else {
					source = INHERITED;
				}
			}

			@Override
			protected Collection<TMemberEntry> doGetResult() {
				return memberEntries.values();
			}

			private TMemberEntry getOrCreateEntry(TMember member) {
				Pair<String, Boolean> p = Tuples.pair(member.getName(), member.isStatic());
				TMemberEntry entry = memberEntries.get(p);
				if (entry == null) {
					entry = new TMemberEntry(member.getName(), member.isStatic());
					memberEntries.put(p, entry);
				}
				return entry;
			}

			@Override
			protected boolean process(ContainerType<?> containerType) {
				final MemberSource currentSource = containerType == bottomType ? OWNED : source;
				for (TMember member : containerType.getOwnedMembers()) {
					if (!isIgnoredMember(member)) {
						final TMemberEntry entry = getOrCreateEntry(member);
						entry.add(member, currentSource);
					}
				}
				return false;
			}

			@Override
			protected boolean doProcessConsumedRoles(TClass object) {
				if (object == bottomType) {
					source = MIXEDIN;
				}
				return super.doProcessConsumedRoles(object);
			}
		}

		private class FindMemberHelper extends AbstractMemberCollector<TMember> {
			private TMember foundMember = null;
			private final String name;
			private final boolean writeAccess;
			private final boolean staticAccess;

			FindMemberHelper(ContainerType<?> type,
					String name, boolean writeAccess, boolean staticAccess,
					boolean includeImplicitSuperTypes, boolean includePolyfills) {
				super(type, includeImplicitSuperTypes, includePolyfills);
				this.name = name;
				this.writeAccess = writeAccess;
				this.staticAccess = staticAccess;
			}

			@Override
			protected boolean process(ContainerType<?> containerType) {
				final TMember m = containerType.findOwnedMember(name, writeAccess, staticAccess);
				if (m != null && !isIgnoredMember(m)) {
					if (foundMember == null //
							|| !m.isAbstract()) { // do not replace an abstract member with another abstract one
						foundMember = m;
					}
					if (foundMember.getContainingType() == bottomType // owned members always win
							|| !foundMember.isAbstract()) { // concrete members precede abstract members
						return true;
					}
				}
				return false;

			}

			@Override
			protected TMember doGetResult() {
				return foundMember;
			}
		}

	}

}
