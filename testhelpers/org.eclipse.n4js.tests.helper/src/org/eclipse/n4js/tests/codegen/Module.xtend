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
package org.eclipse.n4js.tests.codegen

import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.List
import java.util.Map
import java.util.Objects

/**
 * Generates code for a module containing imports and either given classifiers or contents.
 */
class Module {
	final String name;
	List<Classifier<?>> classifiers;
	String contents;
	Map<String, List<String>> imports;

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name the module name without extension
	 */
	public new(String name) {
		this.name = Objects.requireNonNull(name);
	}

	/**
	 * Returns the name of this module.
	 *
	 * @return the name of this module
	 */
	public def String getName() {
		return name
	}


	/**
	 * Sets the given string of contents to the module built by this builder.
	 * This will cause the classifiers to be ignored.
	 *
	 * @param contents the contents to add
	 */
	public def Module setContents(String contents) {
		this.contents = contents;
		return this;
	}


	/**
	 * Adds the given classifier to the module built by this builder.
	 * Note that the classifiers are ignored iff the contents are set.
	 *
	 * @param classifier the classifier to add
	 */
	public def Module addClassifier(Classifier<?> classifier) {
		if (classifiers === null)
			classifiers = newLinkedList();
		classifiers.add(Objects.requireNonNull(classifier));
		return this;
	}

	/**
	 * Adds an import to the module built by this builder.
	 *
	 * @param importedType the name of the type to be imported
	 * @param sourceModule the module containing the imported type
	 */
	public def Module addImport(String importedType, Module sourceModule) {
		return addImport(importedType, sourceModule.name)
	}

	/**
	 * Adds an import to the module built by this builder.
	 *
	 * @param importedType the classifier representing the type to be imported
	 * @param sourceModule the module containing the imported type
	 */
	public def Module addImport(Classifier<?> importedType, Module sourceModule) {
		return addImport(importedType.name, sourceModule)
	}


	/**
	 * Adds an import to the module built by this builder.
	 *
	 * @param importedType the name of the type to be imported
	 * @param sourceModule the name of the module containing the imported type
	 */
	public def Module addImport(String importedType, String sourceModule) {
		if (imports === null)
			imports = newHashMap();

		var List<String> importedTypesForModule = imports.get(Objects.requireNonNull(sourceModule));
		if (importedTypesForModule === null) {
			importedTypesForModule = newLinkedList();
			imports.put(sourceModule, importedTypesForModule);
		}

		importedTypesForModule.add(Objects.requireNonNull(importedType))
		return this;
	}

	/**
	 * Creates this module as a file in the given parent directory, which must already exist.
	 *
	 * @param parentDirectory a file representing the parent directory
	 */
	public def create(File parentDirectory) {
		Objects.requireNonNull(parentDirectory);
		if (!parentDirectory.exists)
			throw new IOException("Directory '" + parentDirectory + "' does not exist");
		if (!parentDirectory.directory)
			throw new IOException("'" + parentDirectory + "' is not a directory");

		val File filePath = new File(parentDirectory, this.name + ".n4js");
		var FileWriter out = null;
		try {
			out = new FileWriter(filePath);
			out.write(generate().toString());
		} finally {
			if (out !== null)
				out.close();
		}
	}

	/**
	 * Generates the N4JS code for this module.
	 */
	public def generate() '''
	«IF hasImports»
		«generateImports()»
	«ENDIF»	
	«IF hasContents»
		«contents»
	«ELSEIF hasClassifiers»
		«generateClassifiers()»
	«ENDIF»
	'''

	private def generateImports() '''
	«FOR entry: imports.entrySet()»
	import { «FOR type: entry.value SEPARATOR ', '»«type»«ENDFOR» } from "«entry.key»";
	«ENDFOR»
	'''

	private def generateClassifiers() '''
	«FOR classifier: classifiers»
	«classifier.generate()»
	«ENDFOR»
	'''

	private def boolean hasClassifiers() {
		return classifiers !== null && !classifiers.empty;
	}

	private def boolean hasImports() {
		return imports !== null && !imports.empty
	}

	private def boolean hasContents() {
		return contents !== null && !contents.empty
	}
}
