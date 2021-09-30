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
package org.eclipse.n4js.n4idl.versioning

import com.google.common.base.Optional
import com.google.inject.Inject
import java.util.Collections
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4idl.scoping.VersionScopeProvider
import org.eclipse.n4js.ts.typeRefs.VersionedReference
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TVersionable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.versions.VersionableUtils
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope

import static org.eclipse.n4js.n4idl.versioning.VersionUtils.isVersioned

/**
 * Contains helper methods to determine version related information of objects as well as to find a specific version
 * of a versionable element or members.
 */
class VersionHelper {

	@Inject
	VersionScopeProvider versionScopeProvider;

	@Inject
	IQualifiedNameConverter nameConverter;

	@Inject
	ContainerTypesHelper containerTypesHelper;

	/**
	 * Computes the maximum version for which the given object applies. The following cases are considered.
	 *
	 * <ul>
	 * <li>If the given object is <code>null</code> or has no version, then {@link Integer#MAX_VALUE} is returned.</li>
	 * <li>If the given object is the maximum available version, then {@link Integer#MAX_VALUE} is returned.</li>
	 * <li>Otherwise, the no of the next higher version of the given object minus one is returned.</li>
	 * </ul>
	 *
	 * <p>
	 * As an example, assume that there is a class <code>A</code> with versions 1 and 4, then calling this method with
	 * <code>A#1</code> as parameter, this method will return 3. If called with <code>A#4</code>, then this method will
	 * return {@link Integer#MAX_VALUE}.
	 * </p>
	 *
	 * @param object
	 *            the object for which to compute the maximum version
	 * @return the maximum version of the given object or {@link Optional.absent()} if no context version can be determined for the given object.
	 */
	def Optional<Integer> computeMaximumVersion(EObject object) {
		if (object === null) 
			return Optional.absent;

		switch (object) {
			VersionedReference case object.hasRequestedVersion:
				return Optional.of(object.requestedVersionOrZero)
			N4ClassDeclaration case isVersioned(object):
				return computeMaximumVersionForVersionable(object.definedType as TClass)
			N4InterfaceDeclaration case isVersioned(object):
				return computeMaximumVersionForVersionable(object.definedType as TInterface)
			N4EnumDeclaration case isVersioned(object):
				return computeMaximumVersionForVersionable(object.definedType as TEnum)
			FunctionDeclaration case isVersioned(object):
				return computeMaximumVersionForVersionable(object.definedType as TFunction)
			TClassifier:
				return computeMaximumVersionForVersionable(object)
			default:
				return computeMaximumVersion(object.eContainer)
		}
	}

	/**
	 * Computes the version range of the given versionable type. The lower limit of the version range is determined by the
	 * declared version of the given classifier while the upper limit is computed via a call to {@link #computeUpperLimit(TClassifier, int)}.
	 */
	private def Optional<Integer> computeMaximumVersionForVersionable(TVersionable versionableType) {
		if (versionableType === null) {
			return Optional.absent;
		}
		val int lowerLimit = versionableType.version;
		return computeUpperLimit(versionableType, lowerLimit);
	}

	/**
	 * Computes the upper limit of the version range of the given (versionable) type using the given lower limit.
	 *
	 * <p>
	 * The upper limit is computed by looking at all versions of the given type that are visible in the module
	 * containing the given classifier and selecting the version with the lowest version number that is larger than the
	 * given lower limit.
	 * </p>
	 *
	 * <p>
	 * If no such version of the given type can be found, then {@link Integer#MAX_VALUE} is returned.
	 * </p>
	 */
	private def Optional<Integer> computeUpperLimit(TVersionable versionable, int lowerLimit) {
		val IScope scope = versionScopeProvider.getVersionScope(versionable);
		val QualifiedName name = nameConverter.toQualifiedName(versionable.name);
		val Iterable<IEObjectDescription> elements = scope.getElements(name);

		return Optional.of(elements
			.filter[EObjectOrProxy instanceof Type && EObjectOrProxy instanceof TVersionable] // if the scope returned bogus elements
			.map[EObjectOrProxy as Type] // convert to types
			.filter[version > lowerLimit] // filter by their version
			.fold(Integer.MAX_VALUE)[u, c|Integer.min(u, c.version - 1)]); // select the smallest one
	}

	/**
	 * Finds the {@link IEObjectDescription} of all versions of the given TClassifier.
	 */
	def <T extends Type> Iterable<? extends T> findTypeVersions(T type) {
		if (!VersionableUtils.isTVersionable(type)) {
			// There are no other versions for an unversioned classifier
			return Collections.singleton(type);
		}

		val IScope scope = versionScopeProvider.getVersionScope(type);
		val QualifiedName name = nameConverter.toQualifiedName(type.name);
		val Iterable<IEObjectDescription> elements = scope.getElements(name);

		return elements
			.map[d | d.EObjectOrProxy]
			.filter[o | type.class.isInstance(o)]
			.map[t | type.class.cast(t) as T];
	}

	/**
	 * Finds the latest version of the given classifier that is not greater than the given version.
	 *
	 * @param classifier
	 *            the classifier to search for
	 * @param version
	 *            the maximum version to return
	 * @return the requested version of the given classifier, or <code>null</code> if no such version exists
	 */
	def <T extends Type> T findTypeWithVersion(T type, int version) {
		val elements = findTypeVersions(type);

		return elements
			.filter[it.version <= version] // filter by the given version limit
			.reduce[l, c | if (l.version > c.version) l else c]; // select an element with maximal version
	}


	/**
	 * Finds the latest version of the given member that is not greater than the given version. Since members are not
	 * versioned individually, this is equivalent to finding the container of the given member in the requested version,
	 * and returning the member with the same name and type from that container version.
	 *
	 * @param member
	 *            the member to search for
	 * @param version
	 *            the maximum version to return
	 * @return the requested version of the given member, or <code>null</code> if no such version exists
	 */
	def <T extends TMember> T findMemberWithVersion(T member, int version) {
		val ContainerType<?> container = member.containingType;
		if (container instanceof TClassifier) {
			val TClassifier containerInRequestedVersion = findTypeWithVersion(container, version);
			val MemberCollector memberCollector = containerTypesHelper.fromContext(containerInRequestedVersion);
			val TMember result = memberCollector.findMember(containerInRequestedVersion, member.name, false, member.static);

			/*
			 * If this cast fails, we ran into the situation where the type of the given member is different than the
			 * type of the member in the container of the requested version.
			 */
			return result as T;
		}
		return member;
	}

}
