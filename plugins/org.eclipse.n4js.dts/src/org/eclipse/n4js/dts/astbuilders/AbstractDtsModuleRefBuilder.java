/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts.astbuilders;

import java.nio.file.Path;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.utils.ParserContextUtils;
import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.utils.URIUtils;

/**
 * Base class of the builders for import and export declarations.
 */
public abstract class AbstractDtsModuleRefBuilder<T extends ParserRuleContext, R>
		extends AbstractDtsBuilder<T, R> {

	private final static Logger LOG = Logger.getLogger(AbstractDtsModuleRefBuilder.class);

	/***/
	public AbstractDtsModuleRefBuilder(AbstractDtsBuilder<?, ?> parent) {
		super(parent);
	}

	/** Converts the given module specifier from .d.ts to N4JS and updates the given {@link ModuleRef} accordingly. */
	protected void setModuleSpecifier(ModuleRef moduleRef, TerminalNode moduleSpecifierLit) {
		String moduleSpecifier = ParserContextUtils.trimAndUnescapeStringLiteral(moduleSpecifierLit);
		setModuleSpecifier(moduleRef, moduleSpecifier);
	}

	/** Converts the given module specifier from .d.ts to N4JS and updates the given {@link ModuleRef} accordingly. */
	protected void setModuleSpecifier(ModuleRef moduleRef, String moduleSpecifier) {
		if (moduleSpecifier == null) {
			return;
		}

		moduleSpecifier = convertModuleSpecifierToN4JS(moduleSpecifier);
		if (moduleSpecifier == null) {
			return;
		}

		moduleRef.setModuleSpecifierAsText(moduleSpecifier);

		TModule tModuleProxy = TypesFactory.eINSTANCE.createTModule();
		EReference eRef = N4JSPackage.eINSTANCE.getModuleRef_Module();
		ParserContextUtils.installProxy(resource, moduleRef, eRef, tModuleProxy, moduleSpecifier);
		moduleRef.setModule(tModuleProxy);
	}

	/**
	 * Converts the given module specifier from .d.ts to N4JS, i.e. from a relative path in the file system (possibly
	 * starting with a project name) to a valid N4JS qualified name of a module.
	 */
	// TODO implement proper conversion of module specifiers from .d.ts to N4JS
	private String convertModuleSpecifierToN4JS(String moduleSpecifier) {
		if (moduleSpecifier == null) {
			return null;
		}

		if (moduleSpecifier.startsWith("./")) {
			Path relLocation = srcFolder.relativize(Path.of(resource.getURI().toFileString()));
			if (relLocation.getNameCount() > 1) {
				relLocation = relLocation.subpath(0, relLocation.getNameCount() - 1);
			} else {
				relLocation = Path.of("");
			}
			Path msRelLoc = relLocation.resolve(moduleSpecifier.substring(2));
			moduleSpecifier = msRelLoc.toString();
		}

		if (moduleSpecifier.startsWith("../")) {
			URI uri = resource.getURI().trimSegments(1); // trim current file
			while (moduleSpecifier.startsWith("../")) {
				moduleSpecifier = moduleSpecifier.substring(3);
				uri = uri.trimSegments(1); // trim one parent folder
			}

			Path absModuleSpecifierPath = Path.of(uri.toFileString(), moduleSpecifier);
			Path relModuleSpecifierPath = srcFolder.relativize(absModuleSpecifierPath);
			moduleSpecifier = relModuleSpecifierPath.toString();
		}

		URI moduleSpecifierUri = null;
		try {
			moduleSpecifierUri = URI.createFileURI(moduleSpecifier);
		} catch (AssertionError ae) {
			// happens sometimes
			LOG.error("Could not create URI for module " + moduleSpecifier);
		}

		if (moduleSpecifierUri == null) {
			return null;
		}

		// trim extension
		moduleSpecifier = URIUtils.trimFileExtension(moduleSpecifierUri).toFileString();

		return moduleSpecifier;
	}
}
