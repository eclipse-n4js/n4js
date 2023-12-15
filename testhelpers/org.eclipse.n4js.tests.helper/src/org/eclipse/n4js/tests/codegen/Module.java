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
package org.eclipse.n4js.tests.codegen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.Strings;

/**
 * Generates code for a module containing imports and either given classifiers or contents.
 */
public class Module extends OtherFile {
	List<Classifier<?>> classifiers = new LinkedList<>();
	Map<String, List<String>> imports = new HashMap<>();

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name
	 *            the module name without extension
	 */
	public Module(String name) {
		super(name, N4JSGlobals.N4JS_FILE_EXTENSION, null);
	}

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name
	 *            the module name without extension
	 */
	public Module(String name, String fExtension, String fromFileName) {
		super(name, fExtension, fromFileName);
	}

	/**
	 * Adds the given classifier to the module built by this builder. Note that the classifiers are ignored iff the
	 * contents are set.
	 *
	 * @param classifier
	 *            the classifier to add
	 */
	public Module addClassifier(Classifier<?> classifier) {
		classifiers.add(Objects.requireNonNull(classifier));
		return this;
	}

	/**
	 * Adds an import to the module built by this builder.
	 *
	 * @param importedType
	 *            the name of the type to be imported
	 * @param sourceModule
	 *            the module containing the imported type
	 */
	public Module addImport(String importedType, Module sourceModule) {
		return addImport(importedType, sourceModule.name);
	}

	/**
	 * Adds an import to the module built by this builder.
	 *
	 * @param importedType
	 *            the classifier representing the type to be imported
	 * @param sourceModule
	 *            the module containing the imported type
	 */
	public Module addImport(Classifier<?> importedType, Module sourceModule) {
		return addImport(importedType.name, sourceModule);
	}

	/**
	 * Adds an import to the module built by this builder.
	 *
	 * @param importedType
	 *            the name of the type to be imported
	 * @param sourceModule
	 *            the name of the module containing the imported type
	 */
	public Module addImport(String importedType, String sourceModule) {
		List<String> importedTypesForModule = imports.get(Objects.requireNonNull(sourceModule));
		if (importedTypesForModule == null) {
			importedTypesForModule = new LinkedList<>();
			imports.put(sourceModule, importedTypesForModule);
		}

		importedTypesForModule.add(Objects.requireNonNull(importedType));
		return this;
	}

	/**
	 * Generates the N4JS code for this module.
	 */
	@Override
	public String generate() {
		String result = "";
		if (hasImports()) {
			result += generateImports();
		}
		if (hasContents()) {
			result += content;
		}
		if (hasClassifiers()) {
			result += generateClassifiers();
		}
		return result;
	}

	private String generateImports() {
		String result = "";
		for (Map.Entry<String, List<String>> entry : imports.entrySet()) {
			result += "import { ";
			boolean isFirst = true;
			for (String type : entry.getValue()) {
				if (!isFirst) {
					result += ", ";
				}
				result += type;
				isFirst = false;
			}
			result += " } from \"" + entry.getKey() + "\";";
		}
		return result;
	}

	private String generateClassifiers() {
		return Strings.join("", c -> c.generate(), classifiers);
	}

	private boolean hasClassifiers() {
		return !classifiers.isEmpty();
	}

	private boolean hasImports() {
		return !imports.isEmpty();
	}

	private boolean hasContents() {
		return !content.isEmpty();
	}
}
