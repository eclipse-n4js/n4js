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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Base class of the builders for import and export declarations.
 */
public abstract class AbstractModuleRefBuilder<T extends ParserRuleContext, R>
		extends AbstractDtsBuilder<T, R> {

	private final static Logger LOG = Logger.getLogger(DtsImportBuilder.class);

	/***/
	public AbstractModuleRefBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	/** Converts the given module specifier from .d.ts to N4JS and updates the given {@link ModuleRef} accordingly. */
	protected void setModuleSpecifier(ModuleRef moduleRef, TerminalNode moduleSpecifierLit) {
		String moduleSpecifier = ParserContextUtil.trimAndUnescapeStringLiteral(moduleSpecifierLit);
		if (moduleSpecifier == null) {
			return;
		}

		moduleSpecifier = convertModuleSpecifierToN4JS(moduleSpecifier);

		moduleRef.setModuleSpecifierAsText(moduleSpecifier);

		TModule tModuleProxy = TypesFactory.eINSTANCE.createTModule();
		EReference eRef = N4JSPackage.eINSTANCE.getModuleRef_Module();
		ParserContextUtil.installProxy(resource, moduleRef, eRef, tModuleProxy, moduleSpecifier);
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
			moduleSpecifier = moduleSpecifier.substring(2);
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
