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

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.VersionedElement
import org.eclipse.n4js.n4idl.scoping.VersionScopeProvider
import org.eclipse.n4js.ts.typeRefs.VersionedReference
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TObjectPrototype
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope

/**
 * Contains helper methods to determine version related information of objects as well as to find a specific version
 * of a versionable element or members.
 */
class VersionHelper {

	@Inject
	VersionScopeProvider scopeProvider;

	@Inject
	IQualifiedNameConverter nameConverter;

	@Inject
	N4JSTypeSystem ts;

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
	 * @return the maximum version of the given object
	 */
	def int computeMaximumVersion(EObject object) {
		if (object === null) // TODO: This may be an error!
			return Integer.MAX_VALUE;

		switch (object) {
			VersionedReference case object.hasRequestedVersion:	return object.requestedVersionOrZero
			IdentifierRef:										return computeMaximumVersion(object.id)
			Expression:                  					   	return computeMaximumVersion(ts.tau(object))
			N4ClassDeclaration case isVersioned(object):	  	return computeMaximumVersion(object.definedType as TClass)
			N4InterfaceDeclaration case isVersioned(object):	return computeMaximumVersion(object.definedType as TInterface)
			N4EnumDeclaration case isVersioned(object):		return computeMaximumVersion(object.definedType as TEnum)
			TClassifier:                 					   	return computeMaximumVersion(object)
			default:                    					    return computeMaximumVersion(object.eContainer)
		}
	}

	/**
	 * Computes the version range of the given classifier. The lower limit of the version range is determined by the
	 * declared version of the given classifier while the upper limit is computed via a call to
	 * {@link #computeUpperLimit(TClassifier, int)}.
	 */
	private def int computeMaximumVersion(TClassifier classifier) {
		val int lowerLimit = classifier.version;
		return computeUpperLimit(classifier, lowerLimit);
	}

	/**
	 * Computes the upper limit of the version range of the given classifier using the given lower limit.
	 *
	 * <p>
	 * The upper limit is computed by looking at all versions of the given classifier that are visible in the module
	 * containing the given classifier and selecting the version with the lowest version number that is larger than the
	 * given lower limit.
	 * </p>
	 *
	 * <p>
	 * If no such version of the given classifier can be found, then {@link Integer#MAX_VALUE} is returned.
	 * </p>
	 */
	private def int computeUpperLimit(TClassifier classifier, int lowerLimit) {
		val IScope scope = scopeProvider.getVersionScope(classifier);
		val QualifiedName name = nameConverter.toQualifiedName(classifier.name);
		val Iterable<IEObjectDescription> elements = scope.getElements(name);

		return elements
			.filter[EObjectOrProxy instanceof TClassifier] // if the scope returned bogus elements
			.map[EObjectOrProxy as TClassifier] // convert to classifiers
			.filter[version > lowerLimit] // filter by their version
			.fold(Integer.MAX_VALUE)[u, c|Integer.min(u, c.version - 1)]; // select the smallest one
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
	def TClassifier findClassifierWithVersion(TClassifier classifier, int version) {
		if (classifier instanceof TObjectPrototype) {
			return classifier; // TObjectPrototypes are unversioned -> there exists only a single version
		}

		val IScope scope = scopeProvider.getVersionScope(classifier);
		val QualifiedName name = nameConverter.toQualifiedName(classifier.name);
		val Iterable<IEObjectDescription> elements = scope.getElements(name);

		return elements
			.map[EObjectOrProxy] // map to the described objects
			.filter(TClassifier) // only consider non-null classifiers
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
			val TClassifier containerInRequestedVersion = findClassifierWithVersion(container, version);
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

	/**
	 * Returns {@code true} if the given {@link VersionedElement} is
	 * considered to be versioned.
	 *
	 * A return value of {@code true} indicates a non-null value for the field {@link VersionedElement#declaredVersion}.
	 */
	private def boolean isVersioned(VersionedElement element) {
		return element.declaredVersion !== null && element.declaredVersion.intValue != 0;
	}
}
