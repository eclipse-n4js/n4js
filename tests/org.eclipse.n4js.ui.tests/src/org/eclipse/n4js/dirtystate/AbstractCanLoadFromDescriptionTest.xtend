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
package org.eclipse.n4js.dirtystate

import java.util.Set
import org.eclipse.core.resources.IFile
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.xtext.util.concurrent.IUnitOfWork

/**
 * Base class for testing logic in {@link CanLoadFromDescriptionHelper}.
 */
abstract class AbstractCanLoadFromDescriptionTest extends AbstractBuilderParticipantTest {

	def protected IFile getOutputFileForTestFile(IFile file) {
		val name = file.name;
		val idx = name.lastIndexOf('.');
		val baseName = if(idx>=0) name.substring(0, idx) else name;
		val project = file.project;
		return project.getFile(
			N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT
			+ "/m/"
			+ baseName + ".js"
		);
	}
	
	/**
	 * Execute the given unit of work with the resource of the given editor.
	 */
	def protected void assertResource(XtextEditor editor, IUnitOfWork.Void<XtextResource> unit) {
		editor.getDocument().readOnly(unit);
	}
	
	/**
	 * Assert that the resources denoted by their URIs are loaded from source in the resource set of 
	 * the given editor.
	 */
	def protected void assertFromSource(XtextEditor editor, Set<URI> uris) {
		editor.assertResource [ resource |
			val resourceSet = resource.resourceSet;
			resourceSet.resources.forEach [ other |
				if (other instanceof N4JSResource) {
					assertEquals(other.URI.toString + ' was loaded from source', uris.contains(other.URI), !other.isLoadedFromDescription)	
				}
			]
		]
	}
	
	/**
	 * Assert that all but the primary resource in the editors resource set are loaded from the index.
	 */
	def protected void assertAllFromIndex(XtextEditor editor) {
		editor.assertResource [ resource |
			val resourceSet = resource.resourceSet;
			resourceSet.resources.forEach [ other |
				if (other instanceof N4JSResource) {
					assertEquals(other.URI.toString + ' was loaded from source', other !== resource, other.isLoadedFromDescription)	
				}
			]
		]
	}
}
