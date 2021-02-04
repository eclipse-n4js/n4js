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

import java.util.List
import java.util.Map
import java.util.Objects
import org.eclipse.n4js.N4JSGlobals

/**
 * Generates code for a module containing imports and either given classifiers or contents.
 */
class Module extends OtherFile {
	List<Classifier<?>> classifiers;
	Map<String, List<String>> imports;

	/**
	 * Creates a new instance with the given parameters.
	 * 
	 * @param name the module name without extension
	 */
	public new(String name) {
		super(name, N4JSGlobals.N4JS_FILE_EXTENSION);
	}

	/**
	 * Creates a new instance with the given parameters.
	 * 
	 * @param name the module name without extension
	 */
	public new(String name, String fExtension) {
		super(name, fExtension);
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
	 * Generates the N4JS code for this module.
	 */
	public override generate() '''
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
		«FOR entry : imports.entrySet()»
			import { «FOR type: entry.value SEPARATOR ', '»«type»«ENDFOR» } from "«entry.key»";
		«ENDFOR»
	'''

	private def generateClassifiers() '''
		«FOR classifier : classifiers»
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
