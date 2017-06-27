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
package org.eclipse.n4js.external.libraries

import com.google.common.base.Preconditions
import com.google.common.base.Strings
import org.eclipse.n4js.external.libraries.TargetPlatformModel.RepositoryType

/**
 * Factory for {@code package.json} file representing N4JS target platform.
 */
class TargetPlatformFactory {

	/**
	 * Creates and returns with the default instance.
	 */
	static def createN4Default() {
		return new PackageJson => [
			name = 'targetplatfrom';
			version = '0.0.1';
			description = 'Target platform for N4JS node development';
			main = 'index';
			scripts = newHashMap(
				new Pair('test', '''echo "Error: no test specified" && exit 1''')
			);
			repository = newHashMap(
				new Pair('type', 'git'),
				new Pair('url', 'https://github.com/eclipse/n4js.git')
			);
			keywords = newArrayList('n4js');
			author = new Person => [
				name = 'Eclipse Foundation'
			]
			license = 'ISC'
			bugs = newHashMap(
				new Pair('url', 'https://github.com/eclipse/n4js/issues')
			);
			homepage = 'https://github.com/eclipse/n4js/blob/master/plugins/org.eclipse.n4js.external.libraries/src/org/eclipse/n4js/external/libraries/readme.adoc';
		];
	}

	/**
	 * Creates and returns with the default instance with the dependencies specified in the N4JS target platform file.
	 */
	static def createN4DefaultWithDependencies(TargetPlatformModel model) {
		Preconditions.checkNotNull(model, 'model');
		val packageJson = createN4Default();
		if (null === packageJson.dependencies) {
			packageJson.dependencies = newHashMap();
		}
		if (!model.location.nullOrEmpty) {
			for (loc : model.location.filter[RepositoryType.npm === repoType]) {
				if (null !== loc.projects) {
					loc.projects.forEach [ projectId, property |
						val version = Strings.nullToEmpty(property?.version);
						packageJson.dependencies.put(projectId, version);
					];
				}
			}
		}
		return packageJson;
	}
}
