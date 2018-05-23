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
package org.eclipse.n4js.external.version

import org.eclipse.n4js.n4mf.VersionConstraint

import static extension java.util.Objects.nonNull
import org.eclipse.n4js.n4mf.DeclaredVersion

/**
 * Formats {@link VersionConstraint} according to npm install command format.
 *
 * Creates string representation of the project dependency version that has to be provided with npm package name.
 * <ul>
 * examples:
 * <li>no version: <code>sax</code></li>
 * <li>simple version: <code>sax@0.1.1</code></li>
 * <li>constrained version: <code>sax@">=0.1.0 <0.2.0"</code></li>
 * </ul>
 *
 * @see <a href="https://docs.npmjs.com/cli/install">npm install</a>
 * @see <a href="https://github.com/npm/node-semver">node-semver</a>
 * @see <a href="http://semver.org/">semver.org</a>
 */
class VersionConstraintFormatUtil {

	/////////////////////////////////
	// formatting based on N4MF model

	/**
	 * Formats provided {@link VersionConstraint} as string required by npm.
	 *
	 * <ul>
	 * possible combinations
	 * <li>no constrained: <code></code></li>
	 * <li>only lower bound: <code>@0.1.1</code></li>
	 * <li>both bounds: <code>@">=0.1.0 <0.2.0"</code></li>
	 * </ul>
	 */
	static def String npmFormat(VersionConstraint it)
		'''«IF nonNull»«formatExisting»«ENDIF»'''

	private static def formatExisting(VersionConstraint it)
		'''«IF upperVersion.nonNull»«constrainedFormat»«ELSE»«simpleFormat»«ENDIF»'''

	private static def constrainedFormat(VersionConstraint it)
		'''@">«IF !isExclLowerBound»=«ENDIF»«lowerVersion.fromExisting» <«IF !isExclUpperBound»=«ENDIF»«upperVersion.fromExisting»"'''

	private static def simpleFormat(VersionConstraint it)
		'''«lowerVersion.npmFormat»'''

	private static def String fromExisting(DeclaredVersion it)
		'''«IF nonNull»«simpleFormat»«ENDIF»'''

	/**
	 * Formats provided {@link DeclaredVersion} as string required by npm.
	 *
	 * <ul>
	 * possible combinations
	 * <li>no version: <code></code></li>
	 * <li>with version: <code>@0.1.1</code></li>
	 * </ul>
	 */
	static def String npmFormat(DeclaredVersion it)
		'''«IF nonNull»@«simpleFormat»«ENDIF»'''

	private static def simpleFormat(DeclaredVersion it)
		'''«major».«minor».«micro»«IF !qualifier.isNullOrEmpty»-«qualifier»«ENDIF»'''

	/////////////////////////////////
	// formatting based plain Strings

	/**
	 * Assumes provided string contains semantic version string, e.g. {@code 1.0.0-beta+exp.sha.5114f85}
	 * returns with string reformatted for npm , e.g. {@code @1.0.0-beta+exp.sha.5114f85}.
	 *
	 * <p><b>
	 * NOTE!! This method does not validate if the provided data complies with semantic versions syntax.
	 * Garbage in, garbage out.
	 * </b></p>
	 */
	static def String npmVersionFormat(String version)
		'''«IF !version.isNullOrEmpty»@«version»«ELSE»«ENDIF»'''

	/**
	 * Assumes provided parameters contain strings for lower and upper semantic versions and their inclusiveness,
	 * e.g. {@code (1.0.0-beta+exp.sha.5114f85, true, 2.0.0-alpha+exp.sha.e134a85, false)},
	 * returns with string reformatted for npm ,
	 * e.g. {@code @">=1.0.0-beta+exp.sha.5114f85 <2.0.0-alpha+exp.sha.e134a85"}
	 *
	 * <p><b>
	 * NOTE!! This method does not validate if the provided data complies with semantic versions syntax.
	 * Garbage in, garbage out.
	 * </b></p>
	 */
	static def String npmRangeFormat(String lower,boolean isExclLowerBound, String upper, boolean isExclUpperBound)
		'''«IF lower.nonNull»«formatExistingRange(lower, isExclLowerBound, upper, isExclUpperBound)»«ENDIF»'''

	private static def String formatExistingRange(String lower,boolean isExclLowerBound, String upper, boolean isExclUpperBound)
		'''«IF !upper.isNullOrEmpty»«formatRange(lower, isExclLowerBound, upper, isExclUpperBound)»«ELSE»«lower.npmVersionFormat»«ENDIF»'''

	private static def formatRange(String lower,boolean isExclLowerBound, String upper, boolean isExclUpperBound)
		'''@">«IF !isExclLowerBound»=«ENDIF»«lower» <«IF !isExclUpperBound»=«ENDIF»«upper»"'''
}
