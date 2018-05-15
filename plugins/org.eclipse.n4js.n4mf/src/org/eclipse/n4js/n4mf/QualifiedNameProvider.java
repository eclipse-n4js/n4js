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
package org.eclipse.n4js.n4mf;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4mf.util.N4mfSwitch;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.common.base.Function;
import com.google.inject.Singleton;

/**
 * A custom {@link IQualifiedNameProvider} for the {@code N4JS manifests}.
 *
 * The AST elements don't use the attribute {@code name} to identify objects that have to be exported to the index to
 * track file changes. Therefore a custom implementation handles all the named elements of the manifest and ensures that
 * they are properly exported.
 */
@Singleton
public class QualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	private static class NameSwitch extends N4mfSwitch<String> implements Function<EObject, String> {

		@Override
		public String caseDeclaredVersion(DeclaredVersion object) {
			return "[" + object.getMajor() + "." + object.getMinor() + "." + object.getMicro() + "."
					+ object.getQualifier() + "]";
		}

		@Override
		public String caseProjectDependency(ProjectDependency object) {
			return "dependency";
		}

		@Override
		public String caseProjectDescription(ProjectDescription object) {
			return object.getVendorId() + ":" + object.getProjectId();
		}

		@Override
		public String caseSourceContainerDescription(SourceContainerDescription object) {
			return object.getSourceContainerType() + ": " + object.getPaths().toString();
		}

		@Override
		public String caseModuleFilter(ModuleFilter object) {
			return object.getModuleFilterType() + ": " + caseModuleFilterSpecifiers(object.getModuleSpecifiers());
		}

		private String caseModuleFilterSpecifiers(List<ModuleFilterSpecifier> object) {
			StringBuilder sb = new StringBuilder();
			for (ModuleFilterSpecifier entry : object) {
				sb.append(caseModuleFilterSpecifier(entry));
				if (object.indexOf(entry) != object.size() - 1) {
					sb.append(", ");
				}
			}
			return sb.toString();
		}

		@Override
		public String caseModuleFilterSpecifier(ModuleFilterSpecifier object) {
			return (object.getSourcePath() == null ? "" : object.getSourcePath() + ": ") +
					object.getModuleSpecifierWithWildcard();
		}

		@Override
		public String caseProjectReference(ProjectReference object) {
			return object.getVendorId() + ":" + object.getProjectId();
		}

		@Override
		public String apply(EObject input) {
			return doSwitch(input);
		}

	}

	private final Function<EObject, String> localResolver = new NameSwitch();

	@Override
	protected Function<EObject, String> getResolver() {
		return localResolver;
	}
}
